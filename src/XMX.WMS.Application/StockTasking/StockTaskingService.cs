using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.StockTasking.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Threading.Tasks;
using Newtonsoft.Json.Linq;
using System.Collections.Generic;
using Abp.UI;
using XMX.WMS.Authorization;
using Abp.Authorization;
using Abp.Application.Services.Dto;
using XMX.WMS.Base.Session;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.StockTasking
{
    [AbpAuthorize(PermissionNames.InventoryStockingManage)]
    public class StockTaskingService : AsyncCrudAppService<StockTasking, StockTaskingDto, Guid, StockTaskingPagedRequest, StockTaskingCreatedDto, StockTaskingUpdatedDto>, IStockTaskingService
    {
        private readonly IRepository<StockTaskingDetail.StockTaskingDetail, Guid> _dRepository;
        private readonly IRepository<ExportBillhead.ExportBillhead, Guid> _ehRepository;
        private readonly IRepository<ExportBillbody.ExportBillbody, Guid> _ebRepository;
        private readonly IRepository<ExportOrder.ExportOrder, Guid> _eoRepository;
        private readonly IRepository<TaskMainInfo.TaskMainInfo, Guid> _tRepository;
        private readonly IRepository<InventoryInfo.InventoryInfo, Guid> _iRepository;
        private readonly IRepository<EncodingRule.EncodingRule, Guid> _erRepository;
        private readonly IRepository<SlotInfo.SlotInfo, Guid> _sRepository;
        private readonly Guid UserCompanyId;
        public StockTaskingService(IRepository<StockTasking, Guid> repository,
                                IRepository<StockTaskingDetail.StockTaskingDetail, Guid> dRepository,
                                IRepository<ExportBillhead.ExportBillhead, Guid> ehRepository,
                                IRepository<ExportBillbody.ExportBillbody, Guid> ebRepository,
                                IRepository<ExportOrder.ExportOrder, Guid> eoRepository,
                                IRepository<TaskMainInfo.TaskMainInfo, Guid> tRepository,
                                IRepository<InventoryInfo.InventoryInfo, Guid> iRepository,
                                IRepository<EncodingRule.EncodingRule, Guid> erRepository,
                                IRepository<SlotInfo.SlotInfo, Guid> sRepository) : base(repository)
        {
            _dRepository = dRepository;
            _ehRepository = ehRepository;
            _ebRepository = ebRepository;
            _eoRepository = eoRepository;
            _tRepository = tRepository;
            _iRepository = iRepository;
            _erRepository = erRepository;
            _sRepository = sRepository;
            UserCompanyId = AbpSession.GetCompanyId();
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        protected override IQueryable<StockTasking> CreateFilteredQuery(StockTaskingPagedRequest input)
        {
            return Repository.GetAll()
                    .WhereIf(AbpSession.UserId != 1, x => x.task_company_id == UserCompanyId)
                    .WhereIf(!input.stock_code.IsNullOrWhiteSpace(), x => x.task_code.Contains(input.stock_code))
                    .WhereIf(input.warehouse_id.HasValue, x => x.task_warehouse_id == input.warehouse_id)
                    .WhereIf(input.stock_state.HasValue, x => x.task_state == input.stock_state)
                    .WhereIf(input.stock_type.HasValue, x => x.task_type == input.stock_type)
                    .WhereIf(input.start_date.HasValue && input.end_date.HasValue, x => Convert.ToDateTime(input.start_date + " 00:00:00") <= x.CreationTime && x.CreationTime <= Convert.ToDateTime(input.end_date + " 23:59:59"))
                    .WhereIf(!input.goods_name.IsNullOrWhiteSpace(), x => x.Goods.goods_code.Contains(input.goods_name) || x.Goods.goods_name.Contains(input.goods_name))
                    ;
        }

        /// <summary>
        /// 获取盘点任务数
        /// </summary>
        /// <returns></returns>
        public GetNumDto GetNowTaskNum()
        {
            int count = Repository.GetAllIncluding(x => x.Warehouse).Where(x => x.Warehouse.warehouse_company_id == UserCompanyId)
                                      .Where(x => x.task_state != StockTaskingState.盘点结束).Count();
            return new GetNumDto { listCount = count };
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.MaterialQualityStatus_Get)]
        public override Task<StockTaskingDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 创建任务
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        public bool CreateTask(List<Guid> idList)
        {
            // 获取盘点单
            List<StockTasking> stockList = Repository.GetAll().Where(x => x.Id.IsIn(idList.ToArray())).ToList();
            if(stockList != null && stockList.Count > 0)
            {
                foreach (StockTasking stock in stockList)
                {
                    ExportBillhead.ExportBillhead head = new ExportBillhead.ExportBillhead();
                    head.exphead_code = stock.task_code;
                    head.exphead_external_id = stock.Id.ToString();
                    head.exphead_external_code = stock.task_code;
                    head.exphead_date = new DateTime();
                    head.exphead_execute_flag = ExecuteFlag.执行中;
                    head.exphead_noused_flag = NousedFlag.正常;
                    // 盘点出库
                    head.exphead_bill_id = new Guid("e3d2ff43-33b4-475f-38fc-08d7e4cebc51");
                    head.exphead_warehouse_id = stock.task_warehouse_id;
                    head.exphead_company_id = UserCompanyId;
                    //head.exphead_custom_id = 上下游;
                    head = _ehRepository.Insert(head);
                    // 获取盘点单明细
                    List<StockTaskingDetail.StockTaskingDetail> detailList = _dRepository.GetAll().Where(x => x.stock_tasking_id == stock.Id).ToList();
                    List<InventoryBatchDto> batchList = detailList.GroupBy(x => new { x.task_batch_no, x.task_goods_id })
                                                                  .Select(group => new InventoryBatchDto(group.Key.task_batch_no, group.Key.task_goods_id.Value)).ToList();
                    int i = 1;
                    foreach (InventoryBatchDto batch in batchList)
                    {
                        ExportBillbody.ExportBillbody body = new ExportBillbody.ExportBillbody();
                        body.expbody_list_id = head.exphead_code + "_" + i.ToString();
                        body.expbody_batch_no = batch.batch_no;
                        body.expbody_bill_bar = head.exphead_code;
                        body.expbody_plan_quantity = 0;
                        body.expbody_execute_flag = ExecuteFlag.执行中;
                        body.expbody_noused_flag = NousedFlag.正常;
                        body.expbody_head_id = head.Id;
                        body.expbody_goods_id = batch.goods_id;
                        body.expbody_company_id = head.exphead_company_id;
                        _ebRepository.Insert(body);
                        List<StockTaskingDetail.StockTaskingDetail> details = detailList.Where(x => x.task_batch_no == batch.batch_no)
                                                                                        .Where(x => x.task_goods_id == batch.goods_id).ToList();
                        foreach (StockTaskingDetail.StockTaskingDetail detail in details)
                        {
                            TaskMainInfo.TaskMainInfo task = _tRepository.FirstOrDefault(x => x.main_slot_code == detail.task_slot_id && x.main_stock_code == detail.task_stock_code);
                            if (task != null)
                            {
                                task = new TaskMainInfo.TaskMainInfo();
                                EncodingRule.EncodingRuleService er = new EncodingRule.EncodingRuleService(_erRepository, _tRepository);
                                string main_no = er.GetEncodingRule("TaskCode");
                                // 任务号5位
                                task.main_no = main_no;
                                // 优先级
                                task.main_priority = 5;
                                // 任务方式(1入库；2出库；3移库；4口对口)
                                task.main_mode = TaskType.出库;
                                // 库位
                                task.main_slot_code = detail.task_slot_id.Value;
                                // 托盘码
                                task.main_stock_code = detail.task_stock_code;
                                // 手自标志(1自动；2手动)                                                                                                                                               
                                task.main_manual_flag = TaskManualFlag.自动;
                                task.main_execute_flag = TaskExecuteFlag.待执行;
                                task.main_company_id = head.exphead_company_id;
                                task = _tRepository.Insert(task);
                                // 修改库位状态
                                SlotInfo.SlotInfo slot = _sRepository.Get(detail.task_slot_id.Value);
                                slot.slot_stock_status = SlotStock.出库中;
                                _sRepository.Update(slot);
                                InventoryInfo.InventoryInfo inventory = _iRepository.FirstOrDefault(x => x.inventory_slot_code == detail.task_slot_id && x.inventory_stock_code == detail.task_stock_code && x.inventory_status == InventoryStatus.可用);
                                // 库存状态修改
                                inventory.inventory_status = InventoryStatus.出库;
                                _iRepository.Update(inventory);
                            }
                            // 流水修改
                            ExportOrder.ExportOrder order = new ExportOrder.ExportOrder();
                            order.exporder_batch_no = detail.task_batch_no;
                            order.exporder_bill_bar = head.exphead_code;
                            order.exporder_quantity = 0;
                            order.exporder_stock_code = detail.task_stock_code;
                            order.exporder_execute_flag = ExecuteFlag.执行中;
                            order.exporder_noused_flag = NousedFlag.正常;
                            order.task_id = task.Id;
                            order.exporder_slot_code = detail.task_slot_id;
                            order.exporder_body_id = body.Id;
                            order.exporder_company_id = head.exphead_company_id;
                            _eoRepository.Insert(order);
                        }
                        i++;
                    }
                }
                return true;
            }
            return false;
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
