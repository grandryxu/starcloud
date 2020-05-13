using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.ImportOrder.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using Abp.Application.Services.Dto;
using System.Threading.Tasks;
using System.Collections.Generic;
using Abp.UI;
using XMX.WMS.Authorization.Users;
using XMX.WMS.Authorization;
using Abp.Authorization;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;

namespace XMX.WMS.ImportOrder
{
    [AbpAuthorize(PermissionNames.ImportBillBodyManage)]
    public class ImportOrderService : AsyncCrudAppService<ImportOrder, ImportOrderDto, Guid, ImportOrderPagedRequest, ImportOrderCreatedDto, ImportOrderUpdatedDto>, IImportOrderService
    {
        private readonly IRepository<ImportStock.ImportStock, Guid> _isRepository;
        private readonly IRepository<ImportBillbody.ImportBillbody, Guid> _ibRepository;
        private readonly IRepository<ImportBillhead.ImportBillhead, Guid> _ihRepository;
        private readonly IRepository<ExportOrder.ExportOrder, Guid> _eoRepository;
        private readonly IRepository<ExportStock.ExportStock, Guid> _esRepository;
        private readonly IRepository<TaskMainInfo.TaskMainInfo, Guid> _tRepository;
        private readonly IRepository<InventoryInfo.InventoryInfo, Guid> _iRepository;
        private readonly UserManager _userManager;
        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public ImportOrderService(IRepository<ImportOrder, Guid> repository,
            IRepository<ImportBillbody.ImportBillbody, Guid> ibRepository,
            IRepository<ImportBillhead.ImportBillhead, Guid> ihRepository,
            IRepository<ExportOrder.ExportOrder, Guid> eoRepository,
            IRepository<ExportStock.ExportStock, Guid> esRepository,
            IRepository<TaskMainInfo.TaskMainInfo, Guid> tRepository,
            IRepository<ImportStock.ImportStock, Guid> isRepository,
            IRepository<InventoryInfo.InventoryInfo, Guid> iRepository,
        UserManager userManager) : base(repository)
        {
            _userManager = userManager;
            _ibRepository = ibRepository;
            _ihRepository = ihRepository;
            _tRepository = tRepository;
            _eoRepository = eoRepository;
            _esRepository = esRepository;
            _iRepository = iRepository;
            _isRepository = isRepository;

            LogContext = DynamicDbContext.GetInstance(String.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                OptPath = "XMX.WMS.ImportOrder.ImportOrderService.",
                OptModule = "入库流水管理"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Get)]
        protected override IQueryable<ImportOrder> CreateFilteredQuery(ImportOrderPagedRequest input)
        {
            return Repository.GetAllIncluding(x => x.Goods, x => x.Slot, x => x.ImportBillbody, x => x.ImportBillbody.ImportBillhead)
                 .WhereIf(!input.imphead_code.IsNullOrWhiteSpace(), x => x.ImportBillbody.ImportBillhead.imphead_code.Contains(input.imphead_code))
                 .WhereIf(!input.imporder_batch_no.IsNullOrWhiteSpace(), x => x.imporder_batch_no.Contains(input.imporder_batch_no))
                 .WhereIf(!input.imporder_lots_no.IsNullOrWhiteSpace(), x => x.imporder_lots_no.Contains(input.imporder_lots_no))
                 .WhereIf(!input.imporder_stock_code.IsNullOrWhiteSpace(), x => x.imporder_stock_code.Contains(input.imporder_stock_code))
                 .WhereIf(!input.imporder_slot_code.IsNullOrWhiteSpace(), x => x.Slot.slot_code.Contains(input.imporder_slot_code))
                 .WhereIf(input.imporder_execute_flag != null, x => x.imporder_execute_flag == input.imporder_execute_flag)
              .Where(x => x.imporder_noused_flag == NousedFlag.正常);
            //.WhereIf(!input.startDate.IsNullOrWhiteSpace(), x => Convert.ToDateTime(input.startDate + " 00:00:00") <= )
            //    .WhereIf(!input.endDate.IsNullOrWhiteSpace(), x => x.imphead_date <= Convert.ToDateTime(input.endDate + " 23:59:59"));
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Get)]
        public override Task<ImportOrderDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Add)]
        public override async Task<ImportOrderDto> Create(ImportOrderCreatedDto input)
        {
            var exist = _ibRepository.GetAll().Where(x => x.Id == input.imporder_body_id && x.impbody_audit_flag == AuditFlag.未审核 && x.impbody_noused_flag == NousedFlag.正常).Any();
            if (exist)
                throw new UserFriendlyException("单据未审核！");
            exist = _ibRepository.GetAll().Where(x => x.Id == input.imporder_body_id && x.impbody_execute_flag == ExecuteFlag.已完成 && x.impbody_noused_flag == NousedFlag.正常).Any();
            if (exist)
                throw new UserFriendlyException("单据已完成！");

            CheckDuplicateStock(input.imporder_stock_code);
            //公司ID
            User loginuser = _userManager.GetUserByIdAsync(AbpSession.UserId.Value).Result;
            //更新单据信息
            ImportBillbody.ImportBillbody body = _ibRepository.FirstOrDefault(x => x.Id == input.imporder_body_id);
            ImportBillhead.ImportBillhead head = _ihRepository.FirstOrDefault(x => x.Id == body.impbody_imphead_id);
            body.impbody_binding_quantity += input.imporder_quantity;
            if (body.impbody_binding_quantity > body.impbody_plan_quantity)
            {
                throw new UserFriendlyException("不允许超计划数量入库，请检查！");
            }
            body.impbody_execute_flag = ExecuteFlag.执行中;
            _ibRepository.Update(body);
            if (head.imphead_execute_flag == ExecuteFlag.未执行)
            {
                head.imphead_execute_flag = ExecuteFlag.执行中;
                _ihRepository.Update(head);
            }
            input.imporder_company_id = loginuser.CompanyId;
            input.imporder_execute_flag = ExecuteFlag.未执行;
            input.imporder_noused_flag = NousedFlag.正常;
            ImportOrderDto dto = await base.Create(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 判断库存中是否存在重复托盘
        /// </summary>
        /// <param name="stockCode">托盘号码</param>
        /// <returns></returns>
        private void CheckDuplicateStock(string stockCode)
        {
            //任务表中是否存在非完成状态该托盘号码的任务
            var taskexist = _tRepository.GetAllIncluding()
                                        .Where(x => x.main_stock_code == stockCode && x.main_execute_flag != TaskExecuteFlag.已完成).Any();
            if (taskexist)
                throw new UserFriendlyException("任务表存在该托盘号码的任务！");
            //入库流水表是否存在
            var imporderexist = Repository.GetAllIncluding()
                                             .Where(x => x.imporder_stock_code == stockCode && x.imporder_execute_flag != ExecuteFlag.已完成 && x.IsDeleted == false && x.imporder_noused_flag == NousedFlag.正常).Any();
            if (imporderexist)
                throw new UserFriendlyException("入库流水表中存在该托盘号码未完成的流水信息！");
            //出库流水表是否存在
            var exporderexist = _eoRepository.GetAllIncluding()
                                             .Where(x => x.exporder_stock_code == stockCode && x.exporder_execute_flag != ExecuteFlag.已完成 && x.IsDeleted == false && x.exporder_noused_flag == NousedFlag.正常).Any();
            if (exporderexist)
                throw new UserFriendlyException("出库流水表中存在该托盘号码未完成的流水信息！");
            //托盘入库流水表是否存在
            var impstockexist = _isRepository.GetAllIncluding()
                                          .Where(x => x.impstock_stock_code == stockCode && x.impstock_execute_flag != ExecuteFlag.已完成 && x.IsDeleted == false && x.impstock_noused_flag == NousedFlag.正常).Any();
            if (impstockexist)
                throw new UserFriendlyException("入库托盘流水表中存在该托盘号码未完成的流水信息！");
            //托盘出库流水表是否存在
            var expstockexist = _esRepository.GetAllIncluding()
                                             .Where(x => x.expstock_stock_code == stockCode && x.expstock_execute_flag != ExecuteFlag.已完成 && x.IsDeleted == false && x.expstock_noused_flag == NousedFlag.正常).Any();
            if (expstockexist)
                throw new UserFriendlyException("出库托盘流水表中存在该托盘号码未完成的流水信息！");
            //库存表是否存在
            var inverexist = _iRepository.GetAllIncluding()
                                        .Where(x => x.inventory_stock_code == stockCode && x.IsDeleted == false).Any();
            if (inverexist)
                throw new UserFriendlyException("托盘已存在库存中！");
        }

        /// <summary>
        /// 编辑
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Update)]
        public override async Task<ImportOrderDto> Update(ImportOrderUpdatedDto input)
        {
            if (input.imporder_noused_flag == NousedFlag.作废)
            {
                ImportBillbody.ImportBillbody body = _ibRepository.FirstOrDefault(x => x.Id == input.imporder_body_id);
                body.impbody_binding_quantity = body.impbody_binding_quantity - input.imporder_quantity;
                if (body.impbody_binding_quantity <= 0)
                {
                    body.impbody_binding_quantity = 0;
                    body.impbody_execute_flag = ExecuteFlag.未执行;
                }
                _ibRepository.Update(body);
                var bodyFlag = false;
                if (body.impbody_execute_flag == ExecuteFlag.未执行)
                {
                    bodyFlag = _ibRepository.GetAllIncluding().Where(x => x.impbody_imphead_id == body.impbody_imphead_id)
                                                                  .Where(x => x.Id != body.Id)
                                                                 .Where(x => x.impbody_execute_flag != ExecuteFlag.未执行).Any();
                }
                else
                {
                    bodyFlag = _ibRepository.GetAllIncluding().Where(x => x.impbody_imphead_id == body.impbody_imphead_id)
                                                                     .Where(x => x.impbody_execute_flag != ExecuteFlag.未执行).Any();
                }
                if (!bodyFlag)
                {
                    //无执行中的体，修改头状态
                    ImportBillhead.ImportBillhead head = _ihRepository.FirstOrDefault(x => x.Id == body.impbody_imphead_id);
                    head.imphead_execute_flag = ExecuteFlag.未执行;
                    _ihRepository.Update(head);
                }
            }
            ImportOrder oldEntity = Repository.FirstOrDefault(x => x.Id == input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            ImportOrderDto dto = await base.Update(input);
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
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            var flag = Repository.GetAll().Where(x => x.Id == input.Id).Where(x => x.imporder_execute_flag != ExecuteFlag.未执行).Any();
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
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Delete)]
        public Task DelteList(List<Guid> idList)
        {
            var flag = Repository.GetAll().Where(x => x.Id.IsIn(idList.ToArray<Guid>())).Where(x => x.imporder_execute_flag != ExecuteFlag.未执行).Any();
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
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Add)]
        public async Task<ListResultDto<ImportOrderDto>> CreateList(List<ImportOrderCreatedDto> inputList)
        {
            List<ImportOrderDto> list = new List<ImportOrderDto>();
            foreach (ImportOrderCreatedDto input in inputList)
            {
                list.Add(await base.Create(input));
            }
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "CreateList", WMSOptLogInfo.WMSOptLogInfo.ADD, "", "批量新增", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return new ListResultDto<ImportOrderDto>(list);
        }

        /// <summary>
        /// 批量修改
        /// </summary>
        /// <param name="inputList"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.ImportBillBodyManage_Update)]
        public async Task<ListResultDto<ImportOrderDto>> UpdateList(List<ImportOrderUpdatedDto> inputList)
        {
            List<ImportOrderDto> list = new List<ImportOrderDto>();
            foreach (ImportOrderUpdatedDto input in inputList)
            {
                list.Add(await base.Update(input));
            }
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "UpdateList", WMSOptLogInfo.WMSOptLogInfo.UPDATE, "批量更新", "批量更新", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return new ListResultDto<ImportOrderDto>(list);
        }
    }
}
