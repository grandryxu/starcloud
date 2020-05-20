using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.AreaInfo.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Threading.Tasks;
using System.Collections.Generic;
using Abp.UI;
using XMX.WMS.Authorization;
using Abp.Authorization;
using Abp.Application.Services.Dto;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;
using XMX.WMS.Base.Session;

namespace XMX.WMS.AreaInfo
{
    [AbpAuthorize(PermissionNames.AreaBasicInfo)]
    public class AreaInfoService : AsyncCrudAppService<AreaInfo, AreaInfoDto, Guid, AreaInfoPagedRequest, AreaInfoCreatedDto, AreaInfoUpdatedDto>, IAreaInfoService
    {
        private readonly Guid UserCompanyId;
        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public AreaInfoService(IRepository<AreaInfo, Guid> repository) : base(repository)
        {
            UserCompanyId = AbpSession.GetCompanyId();
            LogContext = DynamicDbContext.GetInstance(string.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                CompanyId = UserCompanyId,
                OptPath = "XMX.WMS.AreaInfo.AreaInfoService.",
                OptModule = "库区基础信息"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.AreaBasicInfo_Get)]
        protected override IQueryable<AreaInfo> CreateFilteredQuery(AreaInfoPagedRequest input)
        {
            return Repository.GetAllIncluding(x => x.Warehouse)
                        .WhereIf(AbpSession.UserId != 1, x => x.area_company_id == UserCompanyId)
                        .WhereIf(!input.area_name.IsNullOrWhiteSpace(), x => x.area_name.Contains(input.area_name))
                        .WhereIf(!input.area_code.IsNullOrWhiteSpace(), x => x.area_code.Contains(input.area_code))
                        .WhereIf(input.area_warehouse_id.HasValue, x => x.area_warehouse_id == input.area_warehouse_id)
                        ;
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override Task<AreaInfoDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.AreaBasicInfo_Add)]
        public override async Task<AreaInfoDto> Create(AreaInfoCreatedDto input)
        {
            var isrename = Repository.GetAll().Where(x => x.area_name == input.area_name).Any();
            var isrecode = Repository.GetAll().Where(x => x.area_code == input.area_code).Any();
            if (!isrename && !isrecode)
            {
                input.area_company_id = UserCompanyId;
                AreaInfoDto dto = await base.Create(input);
                WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                return dto;
            }
            else
            {
                if (isrename && isrecode)
                {
                    throw new UserFriendlyException("区域名、区域编码重复！");
                }
                else if (isrename)
                {
                    throw new UserFriendlyException("区域名重复！");
                }
                else
                {
                    throw new UserFriendlyException("区域编码重复！");
                }
            }

        }
        /// <summary>
        /// 更新
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.AreaBasicInfo_Update)]
        public override async Task<AreaInfoDto> Update(AreaInfoUpdatedDto input)
        {
            var query = Repository.GetAll().Where(x => x.Id != input.Id);
            var isrenamecode = query.Where(x => x.area_code == input.area_code || x.area_name == input.area_name).Any();
            if (isrenamecode)
            {
                throw new UserFriendlyException("区域名、区域编码重复！");
            }
            else
            {
                AreaInfo oldEntity = Repository.Get(input.Id);
                string oldval = JsonConvert.SerializeObject(oldEntity);
                AreaInfoDto dto = await base.Update(input);
                WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                return dto;
            }
        }

        /// <summary>
        /// 区域下拉列表
        /// </summary>
        /// <returns></returns>
        public List<AreaListDto> GetAreaList()
        {
            var list = Repository.GetAll().Select(item => new { item.Id, item.area_name }).ToList();
            var relist = new List<AreaListDto>();
            foreach (var item in list)
            {
                relist.Add(new AreaListDto { Id = item.Id, area_name = item.area_name });
            }
            return relist;
        }

        /// <summary>
        /// 批量删除
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.AreaBasicInfo_Delete)]
        public Task DelteList(List<Guid> idList)
        {
            //dynamic jsonValues = idList;
            //JArray jsonInput = jsonValues.idList;
            //List<Guid> list = jsonInput.ToObject<List<Guid>>();
            if (null == idList)
                throw new UserFriendlyException("参数解析异常，请联系管理员！");
            return Repository.DeleteAsync(x => x.Id.IsIn(idList.ToArray<Guid>()));
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.AreaBasicInfo_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }
    }
}
