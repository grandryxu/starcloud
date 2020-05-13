using Abp.Application.Services;
using Abp.Domain.Repositories;
using Abp.Extensions;
using Abp.Linq.Extensions;
using Abp.UI;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using XMX.WMS.Equipment.Dto;
using XMX.WMS.Authorization;
using Abp.Authorization;
using Abp.Application.Services.Dto;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;

namespace XMX.WMS.Equipment
{
    [AbpAuthorize(PermissionNames.Warehouse_Equipment_Stacker_List)]
    public class StackerInfoService : AsyncCrudAppService<StackerInfo, StackerInfoDto, Guid, StackerInfoPagedRequest, StackerInfoCreatedDto, StackerInfoUpdatedDto>, IStackerInfoService
    {
        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public StackerInfoService(IRepository<StackerInfo, Guid> repository) : base(repository)
        {
            LogContext = DynamicDbContext.GetInstance(String.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                OptPath = "XMX.WMS.Equipment.StackerInfoService.",
                OptModule = "堆垛机状态管理"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.Warehouse_Equipment_Stacker_Get)]
        protected override IQueryable<StackerInfo> CreateFilteredQuery(StackerInfoPagedRequest input)
        {
            return Repository.GetAllIncluding(s => s.Warehouse)
                .WhereIf(!input.stacker_code.IsNullOrWhiteSpace(), x => x.stacker_code.Contains(input.stacker_code))
                .WhereIf(input.stacker_warehouse_id.HasValue, x => x.stacker_warehouse_id == input.stacker_warehouse_id)
                .WhereIf(input.online_state.HasValue, x => x.online_state == input.online_state)
                .WhereIf(input.alarm_state.HasValue, x => x.alarm_state == input.alarm_state)
                .WhereIf(!input.stacker_name.IsNullOrWhiteSpace(), x => x.stacker_name.Contains(input.stacker_name));
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override Task<StackerInfoDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.Warehouse_Equipment_Stacker_Add)]
        public override async Task<StackerInfoDto> Create(StackerInfoCreatedDto input)
        {
            var is_recode = Repository.GetAll().Where(x => x.stacker_code == input.stacker_code).Any();
            var is_rename = Repository.GetAll().Where(x => x.stacker_name == input.stacker_name).Any();
            if (is_recode || is_rename)
                throw new UserFriendlyException("设备编号或设备名已存在！");
            StackerInfoDto dto = await base.Create(input);
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
        [AbpAuthorize(PermissionNames.Warehouse_Equipment_Stacker_Update)]
        public override async Task<StackerInfoDto> Update(StackerInfoUpdatedDto input)
        {
            var query = Repository.GetAll().Where(x => x.Id != input.Id);
            var is_rename_or_recode = query.Where(x => x.stacker_code == input.stacker_code || x.stacker_name == input.stacker_name).Any();
            if (is_rename_or_recode)
            {
                WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, "", "", WMSOptLogInfo.WMSOptLogInfo.FAIL);
                LogContext.WMSOptLogInfo.Add(logInfoEntity);
                LogContext.SaveChanges();
                throw new UserFriendlyException("设备编号或设备名已存在！");
            }
                
            StackerInfo oldEntity = Repository.FirstOrDefault(x => x.Id == input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            StackerInfoDto dto = await base.Update(input);
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
        [AbpAuthorize(PermissionNames.Warehouse_Equipment_Stacker_Delete)]
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
        [AbpAuthorize(PermissionNames.Warehouse_Equipment_Stacker_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }
    }
}
