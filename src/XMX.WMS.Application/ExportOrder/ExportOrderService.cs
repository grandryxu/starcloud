﻿using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.ExportOrder.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using Abp.Application.Services.Dto;
using System.Threading.Tasks;
using System.Collections.Generic;
using Abp.UI;
using Abp.Authorization;
using XMX.WMS.Authorization;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;

namespace XMX.WMS.ExportOrder
{
    [AbpAuthorize(PermissionNames.ExportBillBodyManage)]
    public class ExportOrderService : AsyncCrudAppService<ExportOrder, ExportOrderDto, Guid, ExportOrderPagedRequest, ExportOrderCreatedDto, ExportOrderUpdatedDto>, IExportOrderService
    {
        private readonly IRepository<ExportBillhead.ExportBillhead, Guid> _ehRepository;
        private readonly IRepository<ExportBillbody.ExportBillbody, Guid> _ebRepository;
        private readonly IRepository<TaskMainInfo.TaskMainInfo, Guid> _tRepository;
        private readonly IRepository<SlotInfo.SlotInfo, Guid> _sRepository;
        private readonly IRepository<InventoryInfo.InventoryInfo, Guid> _iRepository;
        private readonly IRepository<EncodingRule.EncodingRule, Guid> _erRepository;

        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public ExportOrderService(IRepository<ExportOrder, Guid> repository,
            IRepository<ExportBillhead.ExportBillhead, Guid> ehRepository,
            IRepository<ExportBillbody.ExportBillbody, Guid> ebRepository,
            IRepository<TaskMainInfo.TaskMainInfo, Guid> tRepository,
            IRepository<SlotInfo.SlotInfo, Guid> sRepository,
            IRepository<InventoryInfo.InventoryInfo, Guid> iRepository,
            IRepository<EncodingRule.EncodingRule, Guid> erRepository) : base(repository)
        {
            _ehRepository = ehRepository;
            _ebRepository = ebRepository;
            _tRepository = tRepository;
            _sRepository = sRepository;
            _iRepository = iRepository;
            _erRepository = erRepository;

            LogContext = DynamicDbContext.GetInstance(String.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                OptPath = "XMX.WMS.ExportOrder.ExportOrderService.",
                OptModule = "出库流水管理"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.ExportBillBodyManage_Get)]
        protected override IQueryable<ExportOrder> CreateFilteredQuery(ExportOrderPagedRequest input)
        {
            return Repository.GetAllIncluding()
                .WhereIf(!input.exporder_bill_bar.IsNullOrWhiteSpace(), x => x.exporder_bill_bar.Contains(input.exporder_bill_bar))
                .WhereIf(input.exporder_body_id.HasValue, x => x.exporder_body_id == input.exporder_body_id)
                ;
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillBodyManage_Get)]
        public override Task<ExportOrderDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillBodyManage_Add)]
        public override async Task<ExportOrderDto> Create(ExportOrderCreatedDto input)
        {
            ExportOrderDto dto = await base.Create(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 编辑
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillBodyManage_Update)]
        public override async Task<ExportOrderDto> Update(ExportOrderUpdatedDto input)
        {
            ExportOrder oldEntity = Repository.FirstOrDefault(x => x.Id == input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            ExportOrderDto dto = await base.Update(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillBodyManage_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            var flag = Repository.GetAll().Where(x => x.Id == input.Id).Where(x => x.exporder_execute_flag != ExecuteFlag.未执行).Any();
            if (flag)
                throw new UserFriendlyException("数据状态异常，无法删除！");
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }

        /// <summary>
        /// 批量删除
        /// </summary>
        /// <param name="idList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillBodyManage_Delete)]
        public Task DelteList(List<Guid> idList)
        {
            var flag = Repository.GetAll().Where(x => x.Id.IsIn(idList.ToArray<Guid>())).Where(x => x.exporder_execute_flag != ExecuteFlag.未执行).Any();
            if (flag)
                throw new UserFriendlyException("数据占用，无法删除！");
            if (null == idList)
                throw new UserFriendlyException("数据状态异常，请联系管理员！");
            return Repository.DeleteAsync(x => x.Id.IsIn(idList.ToArray<Guid>()));
        }

        /// <summary>
        /// 批量新增
        /// </summary>
        /// <param name="inputList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillBodyManage_Add)]
        public async Task<ListResultDto<ExportOrderDto>> CreateList(List<ExportOrderCreatedDto> inputList)
        {
            List<ExportOrderDto> list = new List<ExportOrderDto>();
            foreach (ExportOrderCreatedDto input in inputList)
            {
                list.Add(await base.Create(input));
            }
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "CreateList", WMSOptLogInfo.WMSOptLogInfo.ADD, "", "批量新增", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return new ListResultDto<ExportOrderDto>(list);
        }

        /// <summary>
        /// 批量修改
        /// </summary>
        /// <param name="inputList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillBodyManage_Update)]
        public async Task<ListResultDto<ExportOrderDto>> UpdateList(List<ExportOrderUpdatedDto> inputList)
        {
            List<ExportOrderDto> list = new List<ExportOrderDto>();
            foreach (ExportOrderUpdatedDto input in inputList)
            {
                list.Add(await base.Update(input));
            }
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "UpdateList", WMSOptLogInfo.WMSOptLogInfo.UPDATE, "批量更新", "批量更新", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return new ListResultDto<ExportOrderDto>(list);
        }

        /// <summary>
        /// 出库复核
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ExportBillBodyManage_Update)]
        public bool ReviewCount(ExportOrderReviewDto input)
        {
            // 查复核的流水
            ExportOrder order = Repository.FirstOrDefault(x => x.Id == input.order_id && x.exporder_stock_code == input.stock_code && x.ExportBillbody.expbody_goods_id == input.goods_id && x.exporder_batch_no == input.batch_no);
            if (order != null)
            {
                // 查询任务
                TaskMainInfo.TaskMainInfo task = _tRepository.FirstOrDefault(x => x.Id == order.task_id);
                // 查批次
                ExportBillbody.ExportBillbody body = _ebRepository.FirstOrDefault(x => x.Id == order.exporder_body_id);
                // 查询库存信息
                InventoryInfo.InventoryInfo inventory = _iRepository.FirstOrDefault(x => x.IsDeleted == false && x.inventory_slot_code == order.exporder_slot_code && x.inventory_stock_code == order.exporder_stock_code && x.inventory_quantity > 0);
                if (inventory == null)
                    throw new UserFriendlyException("未找到库存信息，请联系管理员！");
                SlotInfo.SlotInfo slot = _sRepository.FirstOrDefault(x => x.Id == inventory.inventory_slot_code);
                body.expbody_fulfill_quantity += input.quantity;
                if (inventory.inventory_quantity.CompareTo(input.quantity) > 0)
                {
                    inventory.inventory_quantity -= input.quantity;
                    inventory.inventory_status = InventoryStatus.回流;
                    // TODO回流
                    TaskMainInfo.TaskMainInfo newTask = new TaskMainInfo.TaskMainInfo();
                    string main_no = GetEncodingRule("TaskCode");
                    // 任务号5位
                    newTask.main_no = main_no;
                    // 优先级
                    newTask.main_priority = 5;
                    // 任务方式(1入库；2出库；3移库；4口对口)
                   // newTask.main_mode = TaskType.回流;
                    // 库位
                    newTask.main_slot_code = slot.Id;
                    // 托盘码
                    newTask.main_stock_code = inventory.inventory_stock_code;
                    // 手自标志(1自动；2手动)
                    newTask.main_manual_flag = TaskManualFlag.自动;
                    newTask.main_execute_flag = TaskExecuteFlag.待执行;
                    _tRepository.Insert(newTask);
                    // 修改库位状态
                    slot.slot_stock_status = SlotStock.入库中;
                    _sRepository.Update(slot);
                }
                else
                {
                    inventory.inventory_quantity = 0;
                    inventory.inventory_status = InventoryStatus.冻结;
                    inventory.IsDeleted = true;
                    slot.slot_stock_status = SlotStock.空闲;
                }
                // 复核数量等于流水数量
                if (input.quantity.CompareTo(order.exporder_quantity) == 0)
                {
                    order.exporder_execute_flag = ExecuteFlag.已完成;
                    Repository.Update(order);
                }
                // 库存变化
                _iRepository.Update(inventory);
                // 库位状态变化
                _sRepository.Update(slot);
                // 修改批次信息
                _ebRepository.Update(body);
                int count = _ebRepository.GetAllIncluding().Where(x => x.Id == order.exporder_body_id).Where(x => x.expbody_noused_flag == NousedFlag.正常).Where(x => x.expbody_execute_flag != ExecuteFlag.已完成).Count();
                if (count == 0)
                {
                    // 查询单据
                    ExportBillhead.ExportBillhead head = _ehRepository.FirstOrDefault(x => x.Id == body.expbody_imphead_id);
                    head.exphead_execute_flag = ExecuteFlag.已完成;
                    _ehRepository.Update(head);
                }
                int orderCount = Repository.GetAllIncluding().Where(x => x.task_id == task.Id).Where(x => x.exporder_noused_flag == NousedFlag.正常).Where(x => x.exporder_execute_flag != ExecuteFlag.已完成).Count();
                if (orderCount == 0)
                {
                    task.main_execute_flag = TaskExecuteFlag.已完成;
                    _tRepository.Update(task);
                }
                return true;
            }
            throw new UserFriendlyException("查询流水错误，请联系管理员！");
        }

        /// <summary>
        /// 获取编号
        /// </summary>
        /// <param name="code"></param>
        /// <returns></returns>
        private string GetEncodingRule(string code)
        {
            string erCode = null;
            EncodingRule.EncodingRule er = _erRepository.FirstOrDefault(x => x.code_code == code);
            if (er != null)
            {
                //前缀
                erCode = er.code_prefix;
                //日期类型 1无；2年月日；3年月日小时分钟秒
                if (er.code_date_type != DateType.无)
                {
                    erCode += DateTime.Now.Year + DateTime.Now.Month + DateTime.Now.Day;
                    if (er.code_date_type == DateType.年月日小时分钟秒)
                        erCode += DateTime.Now.Hour + DateTime.Now.Minute + DateTime.Now.Second;
                }
                // 后缀序列长度
                int code_suffix_length = er.code_suffix_length;
                // 上次序列号
                int code_record = er.code_record;
                int new_code_record = code_record + 1;
                // 生成的后缀
                string codeEnd = new_code_record.ToString();
                if (codeEnd.Length > code_suffix_length)
                    codeEnd = codeEnd.Substring(codeEnd.Length - code_suffix_length);
                codeEnd = codeEnd.PadLeft(code_suffix_length, '0');
                erCode += codeEnd;
                //修改序列号
                er.code_record = new_code_record;
                _erRepository.Update(er);
            }
            return erCode;
        }
    }
}
