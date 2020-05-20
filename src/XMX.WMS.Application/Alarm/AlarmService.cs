using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Threading.Tasks;
using XMX.WMS.Alarm.Dto;
using Abp.Application.Services.Dto;
using Microsoft.AspNetCore.Mvc;
using XMX.WMS.Authorization;
using Abp.Authorization;
using XMX.WMS.Base.Session;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.Alarm
{
    [AbpAuthorize(PermissionNames.DullStockWarning,PermissionNames.ValidityStockWarning,PermissionNames.InventoryThresholdWarning)]
    public class AlarmService : AsyncCrudAppService<Alarm, AlarmDto, Guid, AlarmPagedRequest, AlarmCreatedDto, AlarmUpdatedDto>, IAlarmService
    {
        private readonly IRepository<InventoryInfo.InventoryInfo, Guid> _inventoryInfo;
        private readonly Guid UserCompanyId;
        public AlarmService(IRepository<Alarm, Guid> repository, 
                            IRepository<InventoryInfo.InventoryInfo, Guid> InventoryInfo) : base(repository)
        {
            _inventoryInfo = InventoryInfo;
            UserCompanyId = AbpSession.GetCompanyId();
        }

        public override async Task<AlarmDto> Create(AlarmCreatedDto input)
        {
            return null;
        }

        public override async Task<AlarmDto> Update(AlarmUpdatedDto input) 
        {
            return null;
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override Task<AlarmDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 分页条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public async override Task<PagedResultDto<AlarmDto>> GetAll(AlarmPagedRequest input)
        {
            await Task.Delay(1);
            var query = Repository.GetAllIncluding(x => x.Goods, x => x.Inventory, x => x.Inventory.Slot)
                                .WhereIf(AbpSession.UserId != 1, x => x.company_id == UserCompanyId)
                                .WhereIf(!input.goods_name.IsNullOrWhiteSpace(), x => x.Goods.goods_name.Contains(input.goods_name))
                                .WhereIf(!input.batch_no.IsNullOrWhiteSpace(), x => x.Inventory.inventory_batch_no.Contains(input.batch_no))
                                .Where(x => x.warning_type == input.type)
                                .Select(x => new AlarmDto { Id = x.Id, 
                                                            alarm_name = x.alarm_name,  
                                                            alarm_value = x.alarm_value, 
                                                            impbody_bill_bar = x.impbody_bill_bar, 
                                                            thresholdz_value = x.thresholdz_value, 
                                                            CreationTime = x.CreationTime, 
                                                            inventory_batch_no = x.Inventory.inventory_batch_no, 
                                                            goods_name = x.Goods.goods_name, 
                                                            goods_code = x.Goods.goods_code, 
                                                            stock_code = x.Inventory.inventory_stock_code, 
                                                            slot_code = x.Inventory.Slot.slot_code })
                ;
            //查询总数
            var tasksCount = query.Count();
            //默认的分页方式
            var taskList = query.Skip(input.SkipCount).Take(input.MaxResultCount).ToList();

            return new PagedResultDto<AlarmDto>(tasksCount, taskList);
        }

        /// <summary>
        /// 获取当前报警数
        /// </summary>
        /// <returns></returns>
        public GetNumDto GetNowAlarmNum()
        {
            int count = Repository.GetAll().WhereIf(AbpSession.UserId != 1, x => x.company_id == UserCompanyId).Count();
            return new GetNumDto { listCount = count };
        }

        /// <summary>
        /// 触发报警
        /// </summary>
        /// <returns></returns>
        [HttpGet]
        public bool TiggerAlarm()
        {
            var iquery = _inventoryInfo.GetAllIncluding(x => x.Goods, x => x.Goods.StrategyMonitor).ToList();
            foreach (var item in iquery)
            {
                Alarm alarm = new Alarm();
                //效期判断
                DateTime d1 = Convert.ToDateTime(string.Format("{0}-{1}-{2}", item.inventory_vaildate_date.Year, item.inventory_vaildate_date.Month, item.inventory_vaildate_date.Day));
                DateTime d2 = Convert.ToDateTime(string.Format("{0}-{1}-{2}", DateTime.Now.Year, DateTime.Now.Month, DateTime.Now.Day));
                DateTime d3 = Convert.ToDateTime(string.Format("{0}-{1}-{2}", item.inventory_date.Year, item.inventory_date.Month, item.inventory_date.Day));
                int days = (d1 - d2).Days;
                int daysd = (d2 - d3).Days;
                if (item.Goods.goods_monitor_id != null)
                {
                    //设置了效期策略
                    if (item.Goods.StrategyMonitor.monitor_expired_days != 0)
                    {
                        if (days >= item.Goods.StrategyMonitor.monitor_expired_days)
                        {
                            //报警
                            alarm.alarm_name = "效期库存预警";
                            alarm.alarm_value = days;
                            alarm.impbody_bill_bar = item.inventory_bill_bar;
                            alarm.thresholdz_value = item.Goods.StrategyMonitor.monitor_expired_days;
                            alarm.strategy_id = item.Goods.goods_monitor_id;
                            alarm.goods_id = item.inventory_goods_id;
                            alarm.inventory_id = item.Id;
                            alarm.warning_type = WarningType.效期库存预警;
                            alarm.company_id = item.inventory_company_id;
                            Repository.Insert(alarm);
                        }
                    }
                    else
                    {
                        //过期
                        if (days < 0)
                        {
                            alarm.alarm_name = "效期库存预警";
                            alarm.alarm_value = days;
                            alarm.impbody_bill_bar = item.inventory_bill_bar;
                            alarm.thresholdz_value = 0;
                            alarm.strategy_id = item.Goods.goods_monitor_id;
                            alarm.goods_id = item.inventory_goods_id;
                            alarm.inventory_id = item.Id;
                            alarm.warning_type = WarningType.效期库存预警;
                            alarm.company_id = item.inventory_company_id;
                            Repository.Insert(alarm);
                        }
                    }
                    //呆滞库存报警
                    //设置了呆滞时间
                    if (item.Goods.StrategyMonitor.monitor_days_max != 0)
                    {
                        if (daysd > item.Goods.StrategyMonitor.monitor_days_max)
                        {
                            //报警
                            alarm.alarm_name = "呆滞库存预警";
                            alarm.alarm_value = daysd;
                            alarm.impbody_bill_bar = item.inventory_bill_bar;
                            alarm.thresholdz_value = item.Goods.StrategyMonitor.monitor_days_max;
                            alarm.strategy_id = item.Goods.goods_monitor_id;
                            alarm.goods_id = item.inventory_goods_id;
                            alarm.inventory_id = item.Id;
                            alarm.warning_type = WarningType.呆滞库存预警;
                            alarm.company_id = item.inventory_company_id;
                            Repository.Insert(alarm);
                        }
                    }
                }
            }
            return true;
        }

    }
}
