using Abp.Application.Services;
using Abp.Authorization;
using Abp.Domain.Repositories;
using Abp.Extensions;
using Abp.Linq.Extensions;
using Abp.UI;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using XMX.WMS.Authorization;
using XMX.WMS.Equipment.Dto;
using Abp.Application.Services.Dto;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;
using XMX.WMS.Base.Session;

namespace XMX.WMS.Equipment
{
    public class RGVInfoService : AsyncCrudAppService<RGVInfo, RGVInfoDto, Guid, RGVInfoPagedRequest, RGVInfoCreatedDto, RGVInfoUpdatedDto>, IRGVInfoService
    {
        private readonly Guid UserCompanyId;
        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public RGVInfoService(IRepository<RGVInfo, Guid> repository) : base(repository)
        {
            UserCompanyId = AbpSession.GetCompanyId();
            LogContext = DynamicDbContext.GetInstance(string.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                CompanyId = UserCompanyId,
                OptPath = "XMX.WMS.Equipment.RGVInfoService.",
                OptModule = "RGV状态管理"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        protected override IQueryable<RGVInfo> CreateFilteredQuery(RGVInfoPagedRequest input)
        {
            return Repository.GetAllIncluding(s => s.Warehouse)
                .WhereIf(!input.rgv_code.IsNullOrWhiteSpace(), x => x.rgv_code.Contains(input.rgv_code))
                .WhereIf(input.rgv_warehouse_id.HasValue, x => x.rgv_warehouse_id == input.rgv_warehouse_id)
                .WhereIf(input.online_state.HasValue, x => x.online_state == input.online_state)
                .WhereIf(input.alarm_state.HasValue, x => x.alarm_state == input.alarm_state)
                .WhereIf(!input.rgv_name.IsNullOrWhiteSpace(), x => x.rgv_name.Contains(input.rgv_name));
        }

        [AbpAuthorize(PermissionNames.Warehouse_Equipment_RGV_Add)]
        public override async Task<RGVInfoDto> Create(RGVInfoCreatedDto input)
        {
            var is_recode = Repository.GetAll().Where(x => x.rgv_code == input.rgv_code).Where( ele => ele.IsDeleted == false).Any();
            var is_rename = Repository.GetAll().Where(x => x.rgv_name == input.rgv_name).Where(ele => ele.IsDeleted == false).Any();
            if (is_recode || is_rename)
                throw new UserFriendlyException("设备编号或设备名已存在！");
            RGVInfoDto dto = await base.Create(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        [AbpAuthorize(PermissionNames.Warehouse_Equipment_RGV_Update)]
        public override async Task<RGVInfoDto> Update(RGVInfoUpdatedDto input)
        {
            var query = Repository.GetAll().Where(x => x.Id != input.Id);
            var is_rename_or_recode = query.Where(x => x.rgv_code == input.rgv_code || x.rgv_name == input.rgv_name).Where(ele => ele.IsDeleted == false).Any();
            if (is_rename_or_recode)
            {
                WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, "", "", WMSOptLogInfo.WMSOptLogInfo.FAIL);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                throw new UserFriendlyException("设备编号或设备名已存在！");
            }         
            RGVInfo oldEntity = Repository.Get(input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            RGVInfoDto dto = await base.Update(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override Task<RGVInfoDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 批量删除
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        public Task CreateDropAll(JObject idList)
        {
            dynamic jsonValues = idList;
            JArray jsonInput = jsonValues.idList;

            List<Guid> list = jsonInput.ToObject<List<Guid>>();
            if (null == list)
                throw new UserFriendlyException("参数解析异常，请联系管理员！");
            return Repository.DeleteAsync(x => x.Id.IsIn(list.ToArray<Guid>()));
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override async Task Delete(EntityDto<Guid> input)
        {
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }
    }
}
