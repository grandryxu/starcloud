using Abp.Application.Services;
using Abp.Domain.Repositories;
using Abp.Extensions;
using Abp.Linq.Extensions;
using Abp.UI;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using XMX.WMS.CompanyInfo.Dto;
using XMX.WMS.Authorization;
using Abp.Authorization;
using Abp.Application.Services.Dto;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using Newtonsoft.Json;

namespace XMX.WMS.CompanyInfo
{
    [AbpAuthorize(PermissionNames.CompanyBasicInfo)]
    public class CompanyInfoAppService : AsyncCrudAppService<CompanyInfo, CompanyInfoDto, Guid, CompanyInfoPagedRequest, CompanyCreatedDto, CompanyUpdatedDto>, ICompanyInfoAppService
    {
        private readonly IRepository<DepartmentInfo.DepartmentInfo, Guid> _departmentInfoRepository;

        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public CompanyInfoAppService(IRepository<CompanyInfo, Guid> repository,
            IRepository<DepartmentInfo.DepartmentInfo, Guid> departmentInfoRepository) : base(repository)
        {
            _departmentInfoRepository = departmentInfoRepository;
            LogContext = DynamicDbContext.GetInstance(String.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                OptPath = "XMX.WMS.CompanyInfo.CompanyInfoAppService.",
                OptModule = "公司基础信息"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.CompanyBasicInfo_Get)]
        protected override IQueryable<CompanyInfo> CreateFilteredQuery(CompanyInfoPagedRequest input)
        {
            return Repository.GetAllIncluding(x => x.Parent)
                .WhereIf(!input.Name.IsNullOrWhiteSpace(), x => x.Name.Contains(input.Name))
                .WhereIf(!input.ManagerName.IsNullOrWhiteSpace(), x => x.ManagerName.Contains(input.ManagerName))
                .WhereIf(input.ParentId.HasValue, x => x.ParentId.Equals(input.ParentId));
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.CompanyBasicInfo_Get)]
        public override Task<CompanyInfoDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 父级公司下拉列表
        /// </summary>
        /// <returns></returns>
        public List<CompanyInfoSelectedListDto> GetCompanyInfoSelectedList()
        {
            var list = Repository.GetAll().Select(item => new { item.Id, item.Name, item.ParentId }).Where(x => x.ParentId == null ).ToList();
            var relist = new List<CompanyInfoSelectedListDto>();
            foreach (var item in list)
                relist.Add(new CompanyInfoSelectedListDto { Id = item.Id, Name = item.Name });
            return relist;
        }
        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.CompanyBasicInfo_Add)]
        public override async Task<CompanyInfoDto> Create(CompanyCreatedDto input)
        {
            var is_rename = Repository.GetAll().Where(x => x.Name == input.Name).Where(ele => ele.IsDeleted == false).Any();
            if (is_rename)
                throw new UserFriendlyException("已存在相同名称的公司！");
            CompanyInfoDto dto= await base.Create(input);
            WMSOptLogInfo.WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }
        /// <summary>
        /// 更新
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.CompanyBasicInfo_Update)]
        public override async Task<CompanyInfoDto> Update(CompanyUpdatedDto input)
        {
            var query = Repository.GetAll().Where(x => x.Id != input.Id);
            var is_rename = query.Where(x => x.Name == input.Name).Where(ele => ele.IsDeleted == false).Any();
            if (is_rename)
            {
                WMSOptLogInfo.WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, "", "", WMSOptLogInfo.WMSOptLogInfo.FAIL);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                throw new UserFriendlyException("已存在相同名称的公司！");
            }
            CompanyInfo oldEntity = Repository.FirstOrDefault(x => x.Id == input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            CompanyInfoDto dto = await base.Update(input);
            WMSOptLogInfo.WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        public List<CompanyInfoTreeList> GetCompanyTree(CompanyInfoPagedRequest input)
        {
            //父级
            var olist = Repository.GetAll().WhereIf(!input.Name.IsNullOrWhiteSpace(), x => x.Name.Contains(input.Name))
                .WhereIf(!input.ManagerName.IsNullOrWhiteSpace(), x => x.ManagerName.Contains(input.ManagerName)).Select(item => new { item.Id, item.Name, item.ParentId }).ToList();
            var plist = olist.FindAll(x => x.ParentId == null);
            //子级
            var list = olist.FindAll(x => x.ParentId != null);
            var relist = new List<CompanyInfoTreeList>();
            foreach (var item in plist)
            {
                var arr = list.FindAll(x => x.ParentId == item.Id).ToList();
                List<CompanyInfoNode> output = new List<CompanyInfoNode>();
                if (input.Type == "companytree")
                {
                    foreach (var i in arr)
                        output.Add(new CompanyInfoNode { value = i.Id, label = i.Name });
                    relist.Add(new CompanyInfoTreeList { value = item.Id, label = item.Name, children = output });
                }
                else
                {
                    foreach (var i in arr)
                        output.Add(new CompanyInfoNode { id = i.Id, label = i.Name });
                    relist.Add(new CompanyInfoTreeList { id = item.Id, label = item.Name, children = output });
                }
               
            }
            return relist;
        }
        /// <summary>
        /// 获取部门目录
        /// </summary>
        /// <param name="companyInfoSerchRequest"></param>
        /// <returns></returns>
        public List<object> GetCompanyAndDepartmentInfoTree(CompanyInfoSerchRequest companyInfoSerchRequest)
        {
            //公司列表
            var olist = Repository.GetAll().Select(item => new { item.Id, item.Name, item.ParentId })
                .WhereIf(companyInfoSerchRequest.companyId.HasValue, x => x.Id.Equals(companyInfoSerchRequest.companyId)).ToList();
            var relist = new List<object>();
            foreach (var item in olist)
            {
                //根据公司获取部门列表
                var master = _departmentInfoRepository.GetAllList(x => x.CompanyId == item.Id)
                    .Select(masteritem => new { masteritem.Id, masteritem.Name });
                List<object> masterDeptList = new List<object>();
                foreach (var index in master)
                    masterDeptList.Add(new { id = index.Id, label = index.Name});
                if(masterDeptList.ToList().Count > 0)
                    relist.Add(new { id = item.Id, label = item.Name, children = masterDeptList });
            }
            return relist;
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.CompanyBasicInfo_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            //判断当前公司是否有子级
            var count = Repository.Count(x=>x.ParentId==input.Id);
            if (count > 0)
                throw new UserFriendlyException("该公司存在子公司，请先删除子公司");
            WMSOptLogInfo.WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }
    }
}
