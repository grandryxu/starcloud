using Abp.Application.Services;
using Abp.Domain.Repositories;
using Abp.Extensions;
using Abp.Linq.Extensions;
using Abp.Runtime.Validation;
using Abp.UI;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using XMX.WMS.Equipment.Dto;

namespace XMX.WMS.Equipment
{
    public class PalletizingInfoService : AsyncCrudAppService<PalletizingInfo, PalletizingInfoDto, Guid, PalletizingInfoPagedRequest, PalletizingInfoCreatedDto, PalletizingInfoUpdatedDto>, IPalletizingInfoService
    {
        public PalletizingInfoService(IRepository<PalletizingInfo, Guid> repository) : base(repository)
        {

        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [HttpPost]
        [DisableValidation]
        protected override IQueryable<PalletizingInfo> CreateFilteredQuery(PalletizingInfoPagedRequest input)
        {
            return Repository.GetAllIncluding(s => s.Warehouse)
                .WhereIf(!input.palletizing_code.IsNullOrWhiteSpace(), x => x.palletizing_code.Contains(input.palletizing_code))
                .WhereIf(input.palletizing_warehouse_id.HasValue, x => x.palletizing_warehouse_id == input.palletizing_warehouse_id)
                .WhereIf(input.online_state.HasValue, x => x.online_state == input.online_state)
                .WhereIf(input.alarm_state.HasValue, x => x.alarm_state == input.alarm_state)
                .WhereIf(!input.palletizing_name.IsNullOrWhiteSpace(), x => x.palletizing_name.Contains(input.palletizing_name));
        }

        public override async Task<PalletizingInfoDto> Create(PalletizingInfoCreatedDto input)
        {
            var is_recode = Repository.GetAll().Where(x => x.palletizing_code == input.palletizing_code).Any();
            var is_rename = Repository.GetAll().Where(x => x.palletizing_name == input.palletizing_name).Any();
            if (is_recode || is_rename)
                throw new UserFriendlyException("设备编号或设备名已存在！");
            return await base.Create(input);
        }

        public override async Task<PalletizingInfoDto> Update(PalletizingInfoUpdatedDto input)
        {
            var query = Repository.GetAll().Where(x => x.Id != input.Id);
            var is_rename_or_recode = query.Where(x => x.palletizing_code == input.palletizing_code || x.palletizing_name == input.palletizing_name).Any();
            if (is_rename_or_recode)
                throw new UserFriendlyException("设备编号或设备名已存在！");
            return await base.Update(input);
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
    }
}
