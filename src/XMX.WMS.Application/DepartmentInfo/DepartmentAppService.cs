using Abp.Application.Services;
using Abp.Domain.Repositories;
using Abp.Extensions;
using Abp.Linq.Extensions;
using Abp.UI;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using XMX.WMS.DepartmentInfo.Dto;
using XMX.WMS.Authorization;
using Abp.Authorization;
using Abp.Application.Services.Dto;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;

namespace XMX.WMS.DepartmentInfo
{
    [AbpAuthorize(PermissionNames.DepartmentBasicInfo)]
    public class DepartmentAppService : AsyncCrudAppService<DepartmentInfo, DepartmentInfoDto, Guid, DepartmentInfoPagedRequest, DepartmentCreatedModel, DepartmentUpdatedModel>, IDepartmentAppService
    {
        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public DepartmentAppService(IRepository<DepartmentInfo, Guid> repository) : base(repository)
        {
            LogContext = DynamicDbContext.GetInstance(string.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                OptPath = "XMX.WMS.DepartmentInfo.DepartmentAppService.",
                OptModule = "部门基础信息"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.DepartmentBasicInfo_Get)]
        protected override IQueryable<DepartmentInfo> CreateFilteredQuery(DepartmentInfoPagedRequest input)
        {
            return Repository.GetAllIncluding(x => x.Department, x => x.Company)
                .WhereIf(!input.DepartNo.IsNullOrWhiteSpace(), x => x.DepartNo.Contains(input.DepartNo))
                .WhereIf(!input.Name.IsNullOrWhiteSpace(), x => x.Name.Contains(input.Name))
                .WhereIf(!input.ManagerName.IsNullOrWhiteSpace(), x => x.ManagerName.Contains(input.ManagerName))
                .WhereIf(input.CompanyId.HasValue, x => x.CompanyId.Equals(input.CompanyId));
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.DepartmentBasicInfo_Get)]
        public override Task<DepartmentInfoDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 父级部门下拉列表
        /// </summary>
        /// <returns></returns>
        //[AbpAuthorize("Administration.UserManagement.CreateUser")]
        public List<DepartmentParentInfoDto> GetDepartmentInfoSelectedList(Guid guid)
        {
            try
            {
                var list = Repository.GetAll().Select(
                    item => new {
                        item.Id,
                        item.Name,
                        item.DepartmentId,
                        item.CompanyId
                    }).Where(x => x.DepartmentId == null && x.CompanyId.Equals(guid)).ToList();
                var relist = new List<DepartmentParentInfoDto>();
                foreach (var item in list)
                    relist.Add(new DepartmentParentInfoDto { Id = item.Id, Name = item.Name });
                return relist;
            }
            catch (Exception ex)
            {
                Logger.DebugFormat("GetCompanyInfoSelectedList Exception:{0}", ex.Message);
                throw new UserFriendlyException("公司参数异常，无法获取父级部门下拉列表！");
            }

        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.DepartmentBasicInfo_Add)]
        public override async Task<DepartmentInfoDto> Create(DepartmentCreatedModel input)
        {
            var is_existed = Repository.GetAll().Where(x => x.Name == input.Name || x.DepartNo == input.DepartNo)
                .Where(ele => ele.IsDeleted == false && ele.CompanyId == input.CompanyId).Any();
            if (is_existed)
                throw new UserFriendlyException("已存在相同编号或相同名称的部门！");
            DepartmentInfoDto dto = await base.Create(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 更新
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.DepartmentBasicInfo_Update)]
        public override async Task<DepartmentInfoDto> Update(DepartmentUpdatedModel input)
        {
            var query = Repository.GetAll().Where(x => x.Id != input.Id);
            var is_rename = query.Where(x => x.Name == input.Name || x.DepartNo == input.DepartNo)
                .Where(ele => ele.IsDeleted == false && ele.CompanyId == input.CompanyId).Any();
            if (is_rename)
            {
                WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, "", "", WMSOptLogInfo.WMSOptLogInfo.FAIL);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                throw new UserFriendlyException("已存在相同编号或相同名称的部门！");
            }
                
            DepartmentInfo oldEntity = Repository.FirstOrDefault(x => x.Id == input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            DepartmentInfoDto dto = await base.Update(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }
        /// <summary>
        /// 获取公司下部门树形数据
        /// </summary>
        /// <param name="guid">所属公司主键值</param>
        /// <returns></returns>
        public List<DepartmentInfoTreeList> GetDepartmentTree(Guid guid)
        {
            //父级
            var olist = Repository.GetAll()
                .Select(item => new { item.Id, item.Name, item.DepartmentId, item.CompanyId })
                .Where(x => x.CompanyId.Equals(guid)).ToList();
            var plist = olist.FindAll(x => x.DepartmentId == null);
            //子级
            var list = olist.FindAll(x => x.DepartmentId != null);
            var relist = new List<DepartmentInfoTreeList>();
            foreach (var item in plist)
            {
                var arr = list.FindAll(x => x.DepartmentId == item.Id).ToList();
                List<DepartmentInfoNode> output = new List<DepartmentInfoNode>();
                foreach (var i in arr)
                    output.Add(new DepartmentInfoNode { id = i.Id, label = i.Name });
                relist.Add(new DepartmentInfoTreeList { id = item.Id, label = item.Name, children = output });
            }
            return relist;
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.DepartmentBasicInfo_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }
    }
}
