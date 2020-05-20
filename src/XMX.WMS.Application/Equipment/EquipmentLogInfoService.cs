using Abp.Application.Services;
using Abp.Domain.Repositories;
using Abp.Extensions;
using Abp.Linq.Extensions;
using System;
using System.Linq;
using XMX.WMS.Equipment.Dto;
using XMX.WMS.Authorization;
using Abp.Authorization;
using Abp.Application.Services.Dto;
using System.Threading.Tasks;

namespace XMX.WMS.Equipment
{
    [AbpAuthorize(PermissionNames.MachineAlertLog)]
    public class EquipmentLogInfoService : AsyncCrudAppService<EquipmentLogInfo, EquipmentLogInfoDto, Guid, EquipmentLogInfoPagedRequest, EquipmentLogInfoCreatedDto, EquipmentLogInfoUpdatedDto>, IEquipmentLogInfoService
    {
        public EquipmentLogInfoService(IRepository<EquipmentLogInfo, Guid> repository) : base(repository)
        {

        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        protected override IQueryable<EquipmentLogInfo> CreateFilteredQuery(EquipmentLogInfoPagedRequest input)
        {
            var query = Repository.GetAll()
                .WhereIf(!input.equipment_code.IsNullOrWhiteSpace(), x => x.equipment_code.Contains(input.equipment_code))
                .WhereIf(input.equipment_type.HasValue, x => x.equipment_type == input.equipment_type)
                .WhereIf(!input.equipment_name.IsNullOrWhiteSpace(), x => x.equipment_name.Contains(input.equipment_name))
                .WhereIf(!input.opt_user_name.IsNullOrWhiteSpace(), x => x.opt_user_name.Contains(input.opt_user_name));
            string[] dt = input.date_range?.Split("$");
            if (dt?.Length == 2)
            {
                DateTime t1 = Convert.ToDateTime(dt[0]);
                DateTime t2 = Convert.ToDateTime(dt[1]);
                query = query.Where(x => DateTime.Compare(Convert.ToDateTime(x.CreationTime.ToString("yyyy-MM-dd")), t1) >= 0)
                    .Where(x => DateTime.Compare(Convert.ToDateTime(x.CreationTime.ToString("yyyy-MM-dd")), t2) <= 0);
            }
            return query;
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override Task<EquipmentLogInfoDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }
    }
}
