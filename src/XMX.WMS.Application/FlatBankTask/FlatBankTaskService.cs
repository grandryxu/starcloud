using Abp.Application.Services;
using Abp.Application.Services.Dto;
using Abp.Domain.Repositories;
using Abp.Extensions;
using Abp.Linq.Extensions;
using Abp.UI;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Dynamic.Core;
using System.Text;
using System.Threading.Tasks;
using XMX.WMS.Authorization.Users;
using XMX.WMS.Base.Session;
using XMX.WMS.FlatBankTask.Dto;
using XMX.WMS.TaskMainInfo.Dto;

namespace XMX.WMS.FlatBankTask
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-13 15:00:34
    /// 描 述：
    ///</summary>
    public class FlatBankTaskService : AsyncCrudAppService<FlatBankTask, FlatBankTaskDto, Guid, FlatBankTaskPagedRequest, FlatBankTaskCreatedDto, FlatBankTaskUpdatedDto>, IFlatBankTaskService
    {
        private readonly IRepository<PortInfo.PortInfo, Guid> _pRepository;
        private readonly IRepository<TunnelPort.TunnelPort, Guid> _tRepository;
        private readonly IRepository<GoodsInfo.GoodsInfo, Guid> _gRepository;
        private readonly IRepository<WarehouseInfo.WarehouseInfo, Guid> _wRepository;
        private readonly IRepository<ImportBillhead.ImportBillhead, Guid> _ihRepository;
        private readonly IRepository<ImportBillbody.ImportBillbody, Guid> _ibRepository;
        private readonly IRepository<ImportOrder.ImportOrder, Guid> _ioRepository;
        private readonly IRepository<ImportStock.ImportStock, Guid> _isRepository;
        private readonly IRepository<ExportBillhead.ExportBillhead, Guid> _ehRepository;
        private readonly IRepository<ExportBillbody.ExportBillbody, Guid> _ebRepository;
        private readonly IRepository<ExportOrder.ExportOrder, Guid> _eoRepository;
        private readonly IRepository<ExportStock.ExportStock, Guid> _esRepository;
        private readonly IRepository<InventoryInfo.InventoryInfo, Guid> _iRepository;
        private readonly IRepository<SlotInfo.SlotInfo, Guid> _sRepository;
        private readonly IRepository<EncodingRule.EncodingRule, Guid> _erRepository;
        private readonly IRepository<TaskMainInfo.TaskMainInfo, Guid> _taskRepository;
        private readonly Guid UserCompanyId;

        public FlatBankTaskService(IRepository<FlatBankTask, Guid> repository,
        IRepository<PortInfo.PortInfo, Guid> pRepository,
        IRepository<TunnelPort.TunnelPort, Guid> tRepository,
        IRepository<GoodsInfo.GoodsInfo, Guid> gRepository,
        IRepository<WarehouseInfo.WarehouseInfo, Guid> wRepository,
        IRepository<InventoryInfo.InventoryInfo, Guid> iRepository,
        IRepository<SlotInfo.SlotInfo, Guid> sRepository,
        IRepository<ImportBillhead.ImportBillhead, Guid> ihRepository,
        IRepository<ImportBillbody.ImportBillbody, Guid> ibRepository,
        IRepository<ImportOrder.ImportOrder, Guid> ioRepository,
        IRepository<ImportStock.ImportStock, Guid> isRepository,
        IRepository<ExportBillhead.ExportBillhead, Guid> ehRepository,
        IRepository<ExportBillbody.ExportBillbody, Guid> ebRepository,
        IRepository<ExportOrder.ExportOrder, Guid> eoRepository,
        IRepository<ExportStock.ExportStock, Guid> esRepository,
        IRepository<EncodingRule.EncodingRule, Guid> erRepository,
        IRepository<TaskMainInfo.TaskMainInfo, Guid> taskRepository) : base(repository)
        {
            _pRepository = pRepository;
            _tRepository = tRepository;
            _gRepository = gRepository;
            _wRepository = wRepository;
            _iRepository = iRepository;
            _sRepository = sRepository;
            _ihRepository = ihRepository;
            _ibRepository = ibRepository;
            _ioRepository = ioRepository;
            _isRepository = isRepository;
            _ehRepository = ehRepository;
            _ebRepository = ebRepository;
            _eoRepository = eoRepository;
            _esRepository = esRepository;
            _erRepository = erRepository;
            _taskRepository = taskRepository;
            UserCompanyId = AbpSession.GetCompanyId();
        }


        #region 任务逻辑

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        protected override IQueryable<FlatBankTask> CreateFilteredQuery(FlatBankTaskPagedRequest input)
        {
            //, x => x.Slot1.Row.Warehouse
            return Repository.GetAllIncluding(x => x.Port1, x => x.Slot1, x => x.Slot1.Warehouse)
                    .WhereIf(AbpSession.UserId != 1, x => x.flat_company_id == UserCompanyId)
                    .WhereIf(input.flat_mode != null, x => x.flat_mode == input.flat_mode)
                    .WhereIf(input.flat_execute_flag != TaskExecuteFlag.非完成, x => x.flat_execute_flag == input.flat_execute_flag)
                    .WhereIf(input.flat_execute_flag == TaskExecuteFlag.非完成, x => x.flat_execute_flag != TaskExecuteFlag.已完成)
                    .WhereIf(input.flat_manual_flag != null, x => x.flat_manual_flag == input.flat_manual_flag)
                    .WhereIf(input.flat_creat_datetime != null, x => x.CreationTime == input.flat_creat_datetime)
                    .OrderBy(x => x.flat_execute_flag);
        }

        /// <summary>
        /// 获取当前任务数
        /// </summary>
        /// <returns></returns>
        public int GetNowTaskNum()
        {
            return Repository.GetAllIncluding(x => x.Slot1.Row.Warehouse)
                                        .Where(x => x.Slot1.Row.Warehouse.warehouse_company_id == UserCompanyId)
                                        .Where(x => x.flat_execute_flag != TaskExecuteFlag.已完成).Count();
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override Task<FlatBankTaskDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 手动生成任务
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override async Task<FlatBankTaskDto> Create(FlatBankTaskCreatedDto input)
        {
            var flag = Repository.GetAll().Where(x => x.flat_no == input.flat_no).Any();
            if (flag)
            {
                EncodingRule.EncodingRuleService er = new EncodingRule.EncodingRuleService(_erRepository, _taskRepository);
                input.flat_no = er.GetEncodingRule("TaskCode");
            }
            input.flat_company_id = UserCompanyId;
            return await base.Create(input);
        }

        /// <summary>
        /// 更新任务优先级与执行标志
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override async Task<FlatBankTaskDto> Update(FlatBankTaskUpdatedDto input)
        {
            var res = await base.Update(input);
            SlotInfo.SlotInfo slot = _sRepository.FirstOrDefault(x => x.Id == input.flat_slot_code);
            switch (input.flat_execute_flag)
            {
                case TaskExecuteFlag.输送机:
                    if ((input.flat_mode == TaskType.出库 || input.flat_mode == TaskType.空托盘出库) && input.flat_manual_flag == TaskManualFlag.自动)//目前没有考虑双伸
                    {
                        slot.slot_stock_status = SlotStock.空闲;
                        _sRepository.Update(slot);
                    }
                    break;
                case TaskExecuteFlag.已完成:
                    if ((input.flat_mode == TaskType.入库 || input.flat_mode == TaskType.空托盘入库) && input.flat_manual_flag == TaskManualFlag.自动)
                    {
                        slot.slot_stock_status = SlotStock.有库存;
                        _sRepository.Update(slot);
                    }
                    TaskEnd(input.Id);
                    break;
            }
            return res;
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override async Task Delete(EntityDto<Guid> input)
        {
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }

        /// <summary>
        /// 批量删除
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        public Task DelteList(List<Guid> idList)
        {
            return Repository.DeleteAsync(x => x.Id.IsIn(idList.ToArray<Guid>()));
        }

        /// <summary>
        /// 任务完成
        /// </summary>
        /// <param name="taskid">任务号</param>
        /// <returns></returns>
        private void TaskEnd(Guid taskid)
        {
            // 查询任务
            FlatBankTask task = Repository.FirstOrDefault(x => x.Id == taskid && x.flat_execute_flag == TaskExecuteFlag.已完成 && x.IsDeleted == false);
            switch (task.flat_mode)
            {
                case TaskType.入库:
                    Taskimp(task.Id);
                    break;
                case TaskType.出库:
                    Taskexp(task.Id);
                    break;
                case TaskType.空托盘入库:
                    Taskimpstock(task.Id);
                    break;
                case TaskType.空托盘出库:
                    Taskexpstock(task.Id);
                    break;
                case TaskType.移库:
                    Taskmove(task);
                    break;
                case TaskType.口对口:
                    Taskport(task.Id);
                    break;
            }
        }

        /// <summary>
        /// 空托盘入库完成
        /// </summary>
        /// <param name="taskid">任务ID</param>
        private void Taskimpstock(Guid taskid)
        {
            // 查询任务关联的流水
            List<ImportStock.ImportStock> orderList = _isRepository.GetAllIncluding().Where(x => x.task_id == taskid).ToList();
            foreach (ImportStock.ImportStock order in orderList)
            {
                InventoryInfo.InventoryInfo inventory;
                // 没有库存信息,新增库存信息
                inventory = new InventoryInfo.InventoryInfo();
                inventory.inventory_batch_no = order.impstock_batch_no;
                inventory.inventory_lots_no = order.impstock_lots_no;
                inventory.inventory_quantity = order.impstock_quantity;
                inventory.inventory_stock_code = order.impstock_stock_code;
                inventory.inventory_status = InventoryStatus.可用;
                inventory.inventory_slot_code = Guid.Parse(order.impstock_slot_code.ToString());
                inventory.inventory_goods_id = order.impstock_goods_id.Value;
                inventory.inventory_quality_status = Guid.Parse("00000000-0000-0000-0000-000000000001");
                inventory.inventory_date = DateTime.Now;
                _iRepository.Insert(inventory);
                // 入库流水修改
                order.impstock_execute_flag = ExecuteFlag.已完成;
                _isRepository.Update(order);
            }
        }

        /// <summary>
        /// 空托盘出库完成
        /// </summary>
        /// <param name="taskid">任务ID</param>
        private void Taskexpstock(Guid taskid)
        {
            // 查询任务关联的流水
            List<ExportStock.ExportStock> orderList = _esRepository.GetAllIncluding().Where(x => x.expstock_task_id == taskid).ToList();
            foreach (ExportStock.ExportStock order in orderList)
            {
                InventoryInfo.InventoryInfo inventory = _iRepository.FirstOrDefault(x => x.inventory_stock_code == order.expstock_stock_code && x.inventory_status == InventoryStatus.出库 && x.inventory_slot_code == order.expstock_slot_code);
                if (inventory == null)
                {
                    throw new UserFriendlyException("不存在该托盘的库存，请检查！");
                }
                inventory.DeletionTime = DateTime.Now;
                inventory.DeleterUserId = AbpSession.UserId;
                //有库存信息，更新库存信息
                inventory.IsDeleted = true;
                _iRepository.Update(inventory);
                order.expstock_execute_flag = ExecuteFlag.已完成;
                _esRepository.Update(order);
            }
        }

        /// <summary>
        /// 移库任务完成
        /// </summary>
        /// <param name="task">任务信息</param>
        private void Taskmove(FlatBankTask task)
        {
            InventoryInfo.InventoryInfo inventory = _iRepository.FirstOrDefault(x => x.inventory_stock_code == task.flat_stock_code && x.inventory_status == InventoryStatus.出库 && x.inventory_slot_code == task.flat_slot_code);
            if (inventory != null)
            {
                //有库存信息，更新库存信息
                inventory.inventory_slot_code = Guid.Parse(task.flat_inslot_code.ToString());
                inventory.inventory_status = InventoryStatus.可用;
                _iRepository.Update(inventory);
            }
            SlotInfo.SlotInfo slot = _sRepository.FirstOrDefault(x => x.Id == task.flat_inslot_code);
            slot.slot_stock_status = SlotStock.有库存;
            _sRepository.Update(slot);
        }

        /// <summary>
        /// 口对口任务完成
        /// </summary>
        /// <param name="taskid">任务ID</param>
        private void Taskport(Guid taskid)
        {

        }

        /// <summary>
        /// 出库完成
        /// </summary>
        /// <param name="taskid">任务ID</param>
        private void Taskexp(Guid taskid)
        {
            // 查询任务关联的流水
            List<ExportOrder.ExportOrder> orderList = _eoRepository.GetAllIncluding().Where(x => x.task_id == taskid).ToList();
            foreach (ExportOrder.ExportOrder order in orderList)
            {
                // 查出库单表体信息
                ExportBillbody.ExportBillbody body = _ebRepository.FirstOrDefault(x => x.Id == order.exporder_body_id);
                // 设定需要出库的数量
                //decimal quantity = body.expbody_plan_quantity - body.expbody_fulfill_quantity;
                // 查询库存信息
                InventoryInfo.InventoryInfo inventory = _iRepository.FirstOrDefault(x => x.IsDeleted == false && x.inventory_slot_code == order.exporder_slot_code && x.inventory_stock_code == order.exporder_stock_code && x.inventory_quantity > 0);
                if (inventory == null)
                    continue;
                // 修改流水状态
                order.exporder_execute_flag = ExecuteFlag.已完成;
                _eoRepository.Update(order);
                // 出库单据表体修改
                body.expbody_fulfill_quantity += order.exporder_quantity;
                if (body.expbody_plan_quantity.CompareTo(body.expbody_fulfill_quantity) == 0)
                {
                    body.expbody_execute_flag = ExecuteFlag.已完成;
                }
                _ebRepository.Update(body);
                var expheadexist = _ebRepository.GetAllIncluding().Where(x => x.Id == order.exporder_body_id).Where(x => x.expbody_noused_flag == NousedFlag.正常).Where(x => x.expbody_execute_flag != ExecuteFlag.已完成).Any();
                if (!expheadexist)
                {
                    // 查询单据
                    ExportBillhead.ExportBillhead head = _ehRepository.FirstOrDefault(x => x.Id == body.expbody_head_id);
                    head.exphead_execute_flag = ExecuteFlag.已完成;
                    _ehRepository.Update(head);
                }
                // 修改库存表
                if (order.exporder_return_quantity > 0)
                {
                    inventory.inventory_quantity = order.exporder_return_quantity;
                    inventory.inventory_status = InventoryStatus.暂存;
                }
                else
                {
                    inventory.IsDeleted = true;
                }
                _iRepository.Update(inventory);
            }
        }

        /// <summary>
        /// 入库完成
        /// </summary>
        /// <param name="taskid">任务ID</param>
        private void Taskimp(Guid taskid)
        {
            // 查询任务关联的流水
            List<ImportOrder.ImportOrder> orderList = _ioRepository.GetAllIncluding(x => x.ImportBillbody, x => x.ImportBillbody.ImportBillhead).Where(x => x.task_id == taskid).ToList();
            foreach (ImportOrder.ImportOrder order in orderList)
            {

                InventoryInfo.InventoryInfo inventory = _iRepository.FirstOrDefault(x => x.inventory_stock_code == order.imporder_stock_code && x.inventory_status == InventoryStatus.回流);
                if (inventory != null)
                {
                    //有库存信息，更新库存信息
                    inventory.inventory_status = InventoryStatus.可用;
                    inventory.inventory_slot_code = Guid.Parse(order.imporder_slot_code.ToString());
                    _iRepository.Update(inventory);
                }
                else
                {
                    // 没有库存信息,新增库存信息
                    inventory = new InventoryInfo.InventoryInfo();
                    inventory.inventory_batch_no = order.imporder_batch_no;
                    inventory.inventory_lots_no = order.imporder_lots_no;
                    inventory.inventory_product_date = order.imporder_product_date;
                    inventory.inventory_product_lineid = order.imporder_product_lineid;
                    inventory.inventory_bill_bar = order.imporder_bill_bar;
                    inventory.inventory_vaildate_date = order.imporder_vaildate_date;
                    inventory.inventory_recheck_date = order.imporder_recheck_date;
                    inventory.inventory_quantity = order.imporder_quantity;
                    inventory.inventory_box_code = order.imporder_box_code;
                    inventory.inventory_stock_code = order.imporder_stock_code;
                    inventory.inventory_status = InventoryStatus.可用;
                    inventory.inventory_slot_code = Guid.Parse(order.imporder_slot_code.ToString());
                    inventory.inventory_stock_status = order.imporder_stock_status;
                    inventory.inventory_goods_id = order.imporder_goods_id.Value;
                    inventory.inventory_quality_status = order.imporder_quality_status.Value;
                    inventory.inventory_date = order.ImportBillbody.ImportBillhead.imphead_date;
                    _iRepository.Insert(inventory);
                    // 入库单据信息修改
                    ImportBillbody.ImportBillbody body = _ibRepository.FirstOrDefault(x => x.Id == order.imporder_body_id);
                    // 累计数量
                    body.impbody_fulfill_quantity += order.imporder_quantity;
                    // 入库量等于计划量
                    if (body.impbody_fulfill_quantity.CompareTo(body.impbody_plan_quantity) == 0)
                    {
                        body.impbody_execute_flag = ExecuteFlag.已完成;
                    }
                    _ibRepository.Update(body);
                    // 修改入库单据表头
                    var bodyexist = _ibRepository.GetAllIncluding().Where(x => x.impbody_head_id == body.impbody_head_id)
                                                               .Where(x => x.impbody_noused_flag == NousedFlag.正常)
                                                               .Where(x => x.impbody_execute_flag != ExecuteFlag.已完成).Any();
                    if (!bodyexist)
                    {
                        ImportBillhead.ImportBillhead head = _ihRepository.FirstOrDefault(x => x.Id == body.impbody_head_id);
                        head.imphead_execute_flag = ExecuteFlag.已完成;
                        _ihRepository.Update(head);
                    }
                }
                // 入库流水修改
                order.imporder_execute_flag = ExecuteFlag.已完成;
                _ioRepository.Update(order);
            }
        }

        /// <summary>
        /// 入库任务申请统一接口
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public bool CreateImportTask(StockcodePagedRequest input)
        {
            //托盘号码是否存在异常
            List<ImportStock.ImportStock> importStock = _isRepository.GetAll().Where(x => x.impstock_stock_code == input.imporder_stock_code)
                                                                        .Where(x => x.IsDeleted == false)
                                                                        .Where(x => x.impstock_noused_flag == NousedFlag.正常)
                                                                       .Where(x => x.impstock_execute_flag == ExecuteFlag.未执行).ToList();
            List<ImportOrder.ImportOrder> importOrders = _ioRepository.GetAll().Where(x => x.imporder_stock_code == input.imporder_stock_code)
                                                                               .Where(x => x.IsDeleted == false)
                                                                               .Where(x => x.imporder_noused_flag == NousedFlag.正常)
                                                                               .Where(x => x.imporder_execute_flag == ExecuteFlag.未执行).ToList();
            List<InventoryInfo.InventoryInfo> inventoryInfos = _iRepository.GetAll().Where(x => x.inventory_stock_code == input.imporder_stock_code)
                                                                                   .Where(x => x.IsDeleted == false)
                                                                                   .Where(x => x.inventory_status == InventoryStatus.暂存).ToList();
            if (importStock.Count == 0 && importOrders.Count == 0 && inventoryInfos.Count == 0)
            {
                throw new UserFriendlyException("没有该托盘流水信息，请检查！");
            }
            if (importStock.Count > 0 && importOrders.Count > 0 && inventoryInfos.Count > 0)
            {
                throw new UserFriendlyException("入库流水、空托盘入库流水、暂存区库存中均含有该托盘信息，请检查！");
            }
            if (importStock.Count > 0 && importOrders.Count > 0)
            {
                throw new UserFriendlyException("入库流水、空托盘入库流水均含有该托盘信息，请检查！");
            }
            if (importStock.Count > 0 && inventoryInfos.Count > 0)
            {
                throw new UserFriendlyException("空托盘入库流水、暂存区库存中均含有该托盘信息，请检查！");
            }
            if (importOrders.Count > 0 && inventoryInfos.Count > 0)
            {
                throw new UserFriendlyException("入库流水、暂存区库存中均含有该托盘信息，请检查！");
            }

            if (importStock.Count > 0)
            {
                // 空托盘入库
                CreateTask("ImpstockTask", importStock[0].Id.ToString(), "", "", importStock[0].impstock_warehouse_id.ToString(), null);
            }
            if (importOrders.Count > 0)
            {
                // 入库
                CreateTask("ImporderTask", importOrders[0].Id.ToString(), "", "", importOrders[0].imporder_warehouse_id.ToString(), importOrders[0].imporder_goods_id.ToString());
            }
            return true;
        }

        /// <summary>
        /// 出库任务生成接口-自动
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        public bool CreateAutomaticTask(List<Guid> idList)
        {
            if (idList == null || idList.Count == 0)
                throw new UserFriendlyException("参数错误！");
            // 取出表单信息
            List<ExportBillhead.ExportBillhead> headList = _ehRepository.GetAllIncluding(x => x.Warehouse).Where(x => x.Id.IsIn(idList.ToArray<Guid>())).ToList();
            if (headList == null || headList.Count == 0)
                throw new UserFriendlyException("未找到单据！");
            // 表头信息
            foreach (ExportBillhead.ExportBillhead head in headList)
            {
                // 取出表体信息
                List<ExportBillbody.ExportBillbody> bodyList = _ebRepository.GetAllIncluding().Where(x => x.expbody_head_id == head.Id && x.expbody_noused_flag == NousedFlag.正常).ToList();
                if (bodyList == null || bodyList.Count == 0)
                    throw new UserFriendlyException("未找到单据明细！");
                // 遍历表体信息
                foreach (ExportBillbody.ExportBillbody body in bodyList)
                {
                    // 需要出库的数量
                    decimal planQuantity = body.expbody_plan_quantity - body.expbody_binding_quantity;
                    // 找到库存,按库存大小排序--策略是优先清空
                    List<InventoryInfo.InventoryInfo> inventoryList = _iRepository.GetAllIncluding(x => x.Slot)
                                                                                    .Where(x => x.inventory_goods_id == body.expbody_goods_id && x.inventory_batch_no == body.expbody_batch_no && x.inventory_status == InventoryStatus.可用)
                                                                                    .Where(x => x.inventory_lots_no == body.expbody_lots_no).OrderBy(x => x.inventory_quantity).ToList();
                    if (inventoryList == null || inventoryList.Count == 0)
                        // 无可用库存
                        throw new UserFriendlyException("无可用库存！");
                    // 可用库存总量
                    decimal quantity = inventoryList.Sum(x => x.inventory_quantity);
                    if (quantity < planQuantity)
                        // 库存不足
                        throw new UserFriendlyException("库存不足！");
                    quantity = new decimal(0);// 初始化累计
                    foreach (InventoryInfo.InventoryInfo inventory in inventoryList)
                    {
                        // 查找是否有同托盘出库任务
                        FlatBankTask task = Repository.FirstOrDefault(x => x.flat_stock_code == inventory.inventory_stock_code && x.flat_mode == TaskType.出库 && x.flat_execute_flag != TaskExecuteFlag.已完成);
                        if (task != null)
                        {
                            throw new UserFriendlyException("已存在该托盘出库任务，数据异常请检查！");
                        }
                        CreateTask("ExporderTask", "", inventory.inventory_slot_code.ToString(), inventory.inventory_stock_code, null, null);
                        // 库存状态修改
                        inventory.inventory_status = InventoryStatus.出库;
                        _iRepository.Update(inventory);
                        // 流水变执行状态
                        ExportOrder.ExportOrder order = new ExportOrder.ExportOrder();
                        order.exporder_batch_no = inventory.inventory_batch_no;
                        order.exporder_lots_no = inventory.inventory_lots_no;
                        order.exporder_product_date = inventory.inventory_product_date;
                        order.exporder_product_lineid = inventory.inventory_product_lineid;
                        order.exporder_bill_bar = body.expbody_bill_bar;
                        order.exporder_vaildate_date = inventory.inventory_vaildate_date;
                        order.exporder_recheck_date = inventory.inventory_recheck_date;
                        // 数量
                        if (planQuantity < inventory.inventory_quantity)
                        {
                            order.exporder_quantity = planQuantity;
                            planQuantity = 0;
                        }
                        else
                        {
                            order.exporder_quantity = inventory.inventory_quantity;
                            planQuantity -= inventory.inventory_quantity;
                        }
                        order.exporder_box_code = inventory.inventory_box_code;
                        order.exporder_noused_flag = NousedFlag.正常;
                        order.exporder_body_id = body.Id;
                        order.exporder_slot_code = inventory.inventory_slot_code;
                        order.exporder_stock_code = inventory.inventory_stock_code;
                        order.task_id = task.Id;
                        order.exporder_execute_flag = ExecuteFlag.执行中;
                        order.exporder_company_id = head.exphead_company_id;
                        _eoRepository.Insert(order);
                        // 累计使用的库存量
                        quantity += inventory.inventory_quantity;
                        // 累计量满足后，推出库存循环
                        if (quantity >= body.expbody_plan_quantity && planQuantity == 0)
                            break;
                    }
                    // 批次变执行状态
                    body.expbody_execute_flag = ExecuteFlag.执行中;
                    _ebRepository.Update(body);
                }
                // 单据变执行状态
                head.exphead_execute_flag = ExecuteFlag.执行中;
                _ehRepository.Update(head);
            }
            return true;
        }

        /// <summary>
        /// 出库任务生成接口-手动
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public bool CreateManualTask(ExportBillhead.Dto.ExportBillCreateTaskDto input)
        {
            if (input.headId != null)
                throw new UserFriendlyException("参数错误！");
            // 取出表单信息
            ExportBillhead.ExportBillhead head = _ehRepository.FirstOrDefault(x => x.Id == input.headId);
            // 表单信息
            if (head == null)
                throw new UserFriendlyException("未找到单据！");
            // 取出批次信息
            List<ExportBillbody.ExportBillbody> bodyList = _ebRepository.GetAllIncluding().Where(x => x.expbody_head_id == head.Id && x.expbody_noused_flag == NousedFlag.正常).ToList();
            if (bodyList == null || bodyList.Count == 0)
                throw new UserFriendlyException("未找到单据明细！");
            // 遍历批次信息
            foreach (ExportBillbody.ExportBillbody body in bodyList)
            {
                // 遍历流水信息
                foreach (ExportOrder.Dto.ExportOrderUpdatedDto orderDto in input.orderList)
                {
                    // 找到库存
                    InventoryInfo.InventoryInfo inventory = _iRepository.FirstOrDefault(x => x.inventory_goods_id == body.expbody_goods_id && x.inventory_batch_no == body.expbody_batch_no && x.inventory_slot_code == orderDto.exporder_slot_code && x.inventory_stock_code == orderDto.exporder_stock_code && x.inventory_status == InventoryStatus.可用);
                    if (inventory == null)
                        throw new UserFriendlyException("无可用库存！");
                    // 查找是否有同托盘出库任务
                    FlatBankTask task = Repository.FirstOrDefault(x => x.flat_stock_code == orderDto.exporder_stock_code && x.flat_mode == TaskType.出库 && x.flat_execute_flag != TaskExecuteFlag.已完成);
                    if (task == null)
                    {
                        task = new FlatBankTask();
                        EncodingRule.EncodingRuleService er = new EncodingRule.EncodingRuleService(_erRepository, _taskRepository);
                        string flat_no = er.GetEncodingRule("TaskCode");
                        // 任务号5位
                        task.flat_no = flat_no;
                        // 优先级
                        task.flat_priority = 5;
                        // 任务方式(1入库；2出库；3移库；4口对口)
                        task.flat_mode = TaskType.出库;
                        // 库位
                        task.flat_slot_code = orderDto.exporder_slot_code.Value;
                        // 托盘码
                        task.flat_stock_code = orderDto.exporder_stock_code;
                        // 手自标志(1自动；2手动)                                                                                                                                               
                        task.flat_manual_flag = TaskManualFlag.自动;
                        task.flat_execute_flag = TaskExecuteFlag.待执行;
                        task.flat_company_id = head.exphead_company_id;
                        task = Repository.Insert(task);
                        // 修改库位状态
                        SlotInfo.SlotInfo slot = _sRepository.FirstOrDefault(x => x.Id == orderDto.exporder_slot_code);
                        slot.slot_stock_status = SlotStock.出库中;
                        _sRepository.Update(slot);
                    }
                    // 库存状态修改
                    inventory.inventory_status = InventoryStatus.出库;
                    _iRepository.Update(inventory);
                    // 流水变执行状态
                    ExportOrder.ExportOrder order = new ExportOrder.ExportOrder();
                    order.exporder_batch_no = body.expbody_batch_no;
                    order.exporder_lots_no = body.expbody_lots_no;
                    order.exporder_product_date = body.expbody_product_date;
                    order.exporder_product_lineid = body.expbody_product_lineid;
                    order.exporder_bill_bar = head.exphead_code;
                    order.exporder_vaildate_date = body.expbody_vaildate_date;
                    order.exporder_recheck_date = body.expbody_recheck_date;
                    //order.exporder_quantity = inventory.inventory_quantity;
                    order.exporder_box_code = inventory.inventory_box_code;
                    order.exporder_stock_code = inventory.inventory_stock_code;
                    order.exporder_execute_flag = ExecuteFlag.执行中;
                    order.exporder_noused_flag = NousedFlag.正常;
                    order.task_id = task.Id;
                    order.exporder_slot_code = inventory.inventory_slot_code;
                    order.exporder_body_id = body.Id;
                    order.exporder_company_id = head.exphead_company_id;
                    _eoRepository.Insert(order);
                }
                // 批次变执行状态
                body.expbody_execute_flag = ExecuteFlag.执行中;
                _ebRepository.Update(body);
            }
            // 单据变执行状态
            head.exphead_execute_flag = ExecuteFlag.执行中;
            _ehRepository.Update(head);
            return true;
        }

        /// <summary>
        /// WMS页面托盘入库流水生成任务
        /// </summary>
        /// <returns></returns>
        public bool CreateImportStockTask(List<ImportStockForTaskDto> list)
        {
            if (list == null || list.Count <= 0)
                return false;
            list.ForEach(item => { CreateTask("ImpstockTask", item.id.ToString(), "", "", item.warehouse_id.ToString(), null); });
            return true;
        }

        /// <summary>
        /// 入库任务统一生成接口
        /// </summary>
        /// <param name="taskType">入库任务类型</param>
        /// <param name="id">流水主键</param>
        /// <returns></returns>
        private FlatBankTask CreateTask(string taskType, string id, string slotcode, string stockcode, string warehouseId, string goodsId)
        {
            SlotInfo.SlotInfo slot = null;
            //入库需要自动分配库位
            if (taskType == "ImpstockTask" || taskType == "ImporderTask")
            {
                //查询库位
                SlotInfo.SlotInfoService si = new SlotInfo.SlotInfoService(_sRepository, _gRepository);
                slot = si.GetEmptySlot(Guid.Parse(warehouseId), goodsId);
                if (slot == null)
                    throw new UserFriendlyException("无法找到可以入库的库位！");
                slotcode = slot.Id.ToString();
                // 修改库位状态
                slot.slot_stock_status = SlotStock.入库中;
            }
            else
            {
                slot = _sRepository.FirstOrDefault(x => x.Id == Guid.Parse(slotcode));
                // 修改库位状态
                slot.slot_stock_status = SlotStock.出库中;
            }
            FlatBankTask task = new FlatBankTask();
            EncodingRule.EncodingRuleService er = new EncodingRule.EncodingRuleService(_erRepository, _taskRepository);
            // 任务号5位
            task.flat_no = er.GetEncodingRule("TaskCode");
            // 优先级
            task.flat_priority = 5;
            // 库位
            task.flat_slot_code = Guid.Parse(slotcode);
            // 手自标志(1自动；2手动)
            task.flat_manual_flag = TaskManualFlag.自动;
            task.flat_execute_flag = TaskExecuteFlag.待执行;
            task.flat_company_id = UserCompanyId;
            switch (taskType)
            {
                case "ImpstockTask":
                    ImportStock.ImportStock importStock = _isRepository.Single(x => x.Id == Guid.Parse(id));
                    task.flat_mode = TaskType.空托盘入库;
                    task.flat_stock_code = importStock.impstock_stock_code;// 托盘码
                    task = Repository.Insert(task);
                    importStock.task_id = task.Id;
                    importStock.impstock_execute_flag = ExecuteFlag.执行中;
                    importStock.impstock_slot_code = Guid.Parse(slotcode);
                    _isRepository.Update(importStock);
                    break;
                case "ImporderTask":
                    ImportOrder.ImportOrder importOrder = _ioRepository.Single(x => x.Id == Guid.Parse(id));
                    task.flat_mode = TaskType.入库;
                    task.flat_stock_code = importOrder.imporder_stock_code;// 托盘码
                    task = Repository.Insert(task);
                    importOrder.task_id = task.Id;
                    importOrder.imporder_execute_flag = ExecuteFlag.执行中;
                    importOrder.imporder_slot_code = Guid.Parse(slotcode);
                    _ioRepository.Update(importOrder);
                    break;
                case "ExpstockTask":
                    task.flat_mode = TaskType.空托盘出库;
                    task.flat_stock_code = stockcode;
                    task = Repository.Insert(task);
                    break;
                case "ExporderTask":
                    task.flat_mode = TaskType.出库;
                    task.flat_stock_code = stockcode;
                    task = Repository.Insert(task);
                    break;
            }
            _sRepository.Update(slot);
            return task;
        }

        /// <summary>
        /// 生成空托盘出库任务
        /// </summary>
        /// <param name="codeList"></param>
        /// <returns></returns>
        public bool CreateExportTask(List<string> codeList)
        {
            if (codeList == null || codeList.Count == 0)
                throw new UserFriendlyException("参数错误！");
            // 托盘库存
            List<InventoryInfo.InventoryInfo> inventoryList = _iRepository.GetAllIncluding(x => x.Slot)
                                                                            .Where(x => codeList.Contains(x.inventory_slot_code.ToString()))
                                                                            .Where(x => x.inventory_status == InventoryStatus.可用).ToList();
            if (inventoryList == null || inventoryList.Count == 0)
                throw new UserFriendlyException("未找到库存！");
            foreach (InventoryInfo.InventoryInfo inventory in inventoryList)
            {
                // 查找是否有同托盘出库任务
                FlatBankTask task = Repository.FirstOrDefault(x => x.flat_stock_code == inventory.inventory_stock_code && x.flat_mode == TaskType.空托盘出库 && x.flat_execute_flag != TaskExecuteFlag.已完成);
                if (task == null)
                {
                    FlatBankTask FlatBankTask = CreateTask("ExpstockTask", "", inventory.inventory_slot_code.ToString(), inventory.inventory_stock_code, null, null);
                    // 库存状态修改
                    inventory.inventory_status = InventoryStatus.出库;
                    _iRepository.Update(inventory);
                    ExportStock.ExportStock stock = new ExportStock.ExportStock();
                    stock.expstock_batch_no = inventory.inventory_batch_no;
                    stock.expstock_quantity = inventory.inventory_quantity;
                    stock.expstock_stock_code = inventory.inventory_stock_code;
                    stock.expstock_execute_flag = ExecuteFlag.执行中;
                    stock.expstock_goods_id = inventory.inventory_goods_id;
                    stock.expstock_slot_code = inventory.inventory_slot_code;
                    stock.expstock_warehouse_id = inventory.Slot.slot_warehouse_id;
                    stock.expstock_task_id = FlatBankTask.Id;
                    stock.expstock_company_id = task.flat_company_id;
                    _esRepository.Insert(stock);
                }
            }
            return true;
        }
        #endregion
    }

}
