using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.PlatFormInfo.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Threading.Tasks;
using Abp.UI;
using Newtonsoft.Json.Linq;
using System.Collections.Generic;
using XMX.WMS.Authorization;
using Abp.Authorization;
using Abp.Application.Services.Dto;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;

namespace XMX.WMS.PlatFormInfo
{
    [AbpAuthorize(PermissionNames.PlatFormBasicInfo)]
    public class PlatFormInfoService : AsyncCrudAppService<PlatFormInfo, PlatFormInfoDto, Guid, PlatFormInfoPagedRequest, PlatFormInfoCreatedDto, PlatFormInfoUpdatedDto>, IPlatFormInfoService
    {
        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public PlatFormInfoService(IRepository<PlatFormInfo, Guid> repository) : base(repository)
        {

            LogContext = DynamicDbContext.GetInstance(String.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                OptPath = "XMX.WMS.PlatFormInfo.PlatFormInfoService.",
                OptModule = "月台基础信息"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.PlatFormBasicInfo_Get)]
        protected override IQueryable<PlatFormInfo> CreateFilteredQuery(PlatFormInfoPagedRequest input)
        {
            return Repository.GetAllIncluding(s => s.Warehouse)
                .WhereIf(!input.platform_code.IsNullOrWhiteSpace(), x => x.platform_code.Contains(input.platform_code))
                .WhereIf(!input.platform_name.IsNullOrWhiteSpace(), x => x.platform_name.Contains(input.platform_name))
                .WhereIf(input.warehouse_id.HasValue, x => x.platform_warehouse_id == input.warehouse_id)
                .WhereIf(input.platform_state.HasValue, x => x.platform_state == input.platform_state)
                .OrderByDescending(x => x.CreationTime);
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override Task<PlatFormInfoDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.PlatFormBasicInfo_Add)]
        public override async Task<PlatFormInfoDto> Create(PlatFormInfoCreatedDto input)
        {
            var is_recode = Repository.GetAll().Where(x => x.platform_code == input.platform_code).Where(x => x.IsDeleted == false).Any();
            var is_rename = Repository.GetAll().Where(x => x.platform_name == input.platform_name).Where(x => x.IsDeleted == false).Any();
            if (is_recode || is_rename)
                throw new UserFriendlyException("月台编号或月台名称已存在！");
            PlatFormInfoDto dto = await base.Create(input);
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
        [AbpAuthorize(PermissionNames.PlatFormBasicInfo_Update)]
        public override async Task<PlatFormInfoDto> Update(PlatFormInfoUpdatedDto input)
        {
            var query = Repository.GetAll().Where(x => x.Id != input.Id);
            var is_rename_or_recode = query.Where(x => x.platform_code == input.platform_code || x.platform_name == input.platform_name).Where(x => x.IsDeleted == false).Any();
            if (is_rename_or_recode)
            {
                WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, "", "", WMSOptLogInfo.WMSOptLogInfo.FAIL);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                throw new UserFriendlyException("月台编号或月台名称已存在！");
            }
                
            PlatFormInfo oldEntity = Repository.FirstOrDefault(x => x.Id == input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            PlatFormInfoDto dto = await base.Update(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 批量删除
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.PlatFormBasicInfo_Delete)]
        public Task CreateDropAll(JObject idList)
        {
            dynamic jsonValues = idList;
            JArray jsonInput = jsonValues.idList;

            List<Guid> list = jsonInput.ToObject<List<Guid>>();
            if (null == list)
                throw new UserFriendlyException("参数解析异常，请联系管理员！");
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "CreateDropAll", WMSOptLogInfo.WMSOptLogInfo.DELETE, "批量删除", "批量删除", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return Repository.DeleteAsync(x => x.Id.IsIn(list.ToArray<Guid>()));
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.PlatFormBasicInfo_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }
    }
}
