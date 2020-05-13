using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.ImportStock.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Collections.Generic;
using Abp.UI;
using XMX.WMS.Authorization;
using Abp.Authorization;
using System.Threading.Tasks;
using Abp.Application.Services.Dto;
using XMX.WMS.Authorization.Users;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;

namespace XMX.WMS.ImportStock
{
    [AbpAuthorize(PermissionNames.EmptyStockImport)]
    public class ImportStockService : AsyncCrudAppService<ImportStock, ImportStockDto, Guid, ImportStockPagedRequest, ImportStockCreatedDto, ImportStockUpdatedDto>, IImportStockService
    {
        private readonly IRepository<ExportStock.ExportStock, Guid> _esRepository;
        private readonly IRepository<InventoryInfo.InventoryInfo, Guid> _iRepository;
        private readonly IRepository<ImportOrder.ImportOrder, Guid> _ioRepository;
        private readonly IRepository<ExportOrder.ExportOrder, Guid> _eoRepository;
        private readonly IRepository<TaskMainInfo.TaskMainInfo, Guid> _tRepository;
        private readonly IRepository<SlotInfo.SlotInfo, Guid> _sRepository;
        private readonly UserManager _userManager;
        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public ImportStockService(IRepository<ImportStock, Guid> repository,
            IRepository<ExportStock.ExportStock, Guid> esrepository,
            IRepository<InventoryInfo.InventoryInfo, Guid> irepository,
            IRepository<ImportOrder.ImportOrder, Guid> iorepository,
            IRepository<ExportOrder.ExportOrder, Guid> eorepository,
            IRepository<TaskMainInfo.TaskMainInfo, Guid> trepository,
            IRepository<SlotInfo.SlotInfo, Guid> srepository,
            UserManager userManager) : base(repository)
        {
            _esRepository = esrepository;
            _iRepository = irepository;
            _ioRepository = iorepository;
            _eoRepository = eorepository;
            _tRepository = trepository;
            _sRepository = srepository;
            _userManager = userManager;

            LogContext = DynamicDbContext.GetInstance(String.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                OptPath = "XMX.WMS.ImportStock.ImportStockService.",
                OptModule = "空托盘入库流水"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.EmptyStockImport_Get)]
        protected override IQueryable<ImportStock> CreateFilteredQuery(ImportStockPagedRequest input)
        {
            return Repository.GetAllIncluding(x => x.Slot, x => x.Goods, x => x.Task, x => x.Warehouse)
                .WhereIf(!input.impstock_batch_no.IsNullOrWhiteSpace(), x => x.impstock_batch_no.Contains(input.impstock_batch_no))
                .WhereIf(!input.impstock_stock_code.IsNullOrWhiteSpace(), x => x.impstock_stock_code.Contains(input.impstock_stock_code))
                 .WhereIf(input.task_id.HasValue, x => x.task_id == input.task_id)
                ;
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.EmptyStockImport_Get)]
        public override Task<ImportStockDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.EmptyStockImport_Add)]
        public override async Task<ImportStockDto> Create(ImportStockCreatedDto input)
        {
            CheckDuplicateStock(input.impstock_stock_code);
            var slotexist = _sRepository.GetAll().Where(x => x.Id == input.impstock_slot_code && x.slot_stock_status != SlotStock.空闲).Any();
            if (slotexist)
            {
                throw new UserFriendlyException("库位非可用状态");
            }
            //公司ID
            User loginuser = _userManager.GetUserByIdAsync(AbpSession.UserId.Value).Result;
            input.impstock_company_id = loginuser.CompanyId;
            input.impstock_goods_id = Guid.Parse("00000000-0000-0000-0000-000000000001");
            input.impstock_execute_flag = ExecuteFlag.未执行;
            ImportStockDto dto = await base.Create(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
            //if (input.impstock_slot_code.ToString() != "")
            //{
            //    SlotInfo.SlotInfo slot = _sRepository.Single(x => x.Id == input.impstock_slot_code);
            //    ImportStock importStock = Repository.Single(x => x.impstock_slot_code == input.impstock_slot_code && x.impstock_stock_code == input.impstock_stock_code && x.impstock_execute_flag == ExecuteFlag.未执行);
            //    //插入入库任务
            //    CreatStockTask(importStock, slot);
            //}
        }

        /// <summary>
        /// 编辑
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.EmptyStockImport_Update)]
        public override async Task<ImportStockDto> Update(ImportStockUpdatedDto input)
        {
            CheckDuplicateStock(input.impstock_stock_code);
            var slotexist = _sRepository.GetAll().Where(x => x.Id == input.impstock_slot_code && x.slot_stock_status != SlotStock.空闲).Any();
            if (slotexist)
            {
                throw new UserFriendlyException("库位非可用状态");
            }
            //公司ID
            User loginuser = _userManager.GetUserByIdAsync(AbpSession.UserId.Value).Result;
            input.impstock_company_id = loginuser.CompanyId;
            input.impstock_goods_id = Guid.Parse("00000000-0000-0000-0000-000000000001");

            ImportStock oldEntity = Repository.FirstOrDefault(x => x.Id == input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            ImportStockDto dto = await base.Update(input);
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
        [AbpAuthorize(PermissionNames.EmptyStockImport_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            var flag = Repository.GetAll().Where(x => x.Id == input.Id).Where(x => x.impstock_execute_flag != ExecuteFlag.未执行).Any();
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
        [AbpAuthorize(PermissionNames.EmptyStockImport_Delete)]
        public Task DelteList(List<Guid> idList)
        {
            var flag = Repository.GetAll().Where(x => x.Id.IsIn(idList.ToArray<Guid>())).Where(x => x.impstock_execute_flag != ExecuteFlag.未执行).Any();
            if (flag)
                throw new UserFriendlyException("数据占用，无法删除！");
            if (null == idList)
                throw new UserFriendlyException("数据状态异常，请联系管理员！");
            return Repository.DeleteAsync(x => x.Id.IsIn(idList.ToArray<Guid>()));
        }


        /// <summary>
        /// 判断库存中是否存在重复托盘
        /// </summary>
        /// <param name="stockCode">托盘号码</param>
        /// <returns></returns>
        private bool CheckDuplicateStock(string stockCode)
        {
            //任务表中是否存在非完成状态该托盘号码的任务
            var taskexist = _tRepository.GetAllIncluding()
                                        .Where(x => x.main_stock_code == stockCode && x.main_execute_flag != TaskExecuteFlag.已完成).Any();
            if (taskexist)
            {
                throw new UserFriendlyException("任务表存在该托盘号码的任务！");
            }
            //入库流水表是否存在
            var imporderexist = _ioRepository.GetAllIncluding()
                                             .Where(x => x.imporder_stock_code == stockCode && x.imporder_execute_flag != ExecuteFlag.已完成 && x.IsDeleted == false).Any();
            if (imporderexist)
            {
                throw new UserFriendlyException("入库流水表中存在该托盘号码未完成的流水信息！");
            }
            //出库流水表是否存在
            var exporderexist = _eoRepository.GetAllIncluding()
                                             .Where(x => x.exporder_stock_code == stockCode && x.exporder_execute_flag != ExecuteFlag.已完成 && x.IsDeleted == false).Any();
            if (exporderexist)
            {
                throw new UserFriendlyException("出库流水表中存在该托盘号码未完成的流水信息！");
            }
            //托盘入库流水表是否存在
            var impstockexist = Repository.GetAllIncluding()
                                          .Where(x => x.impstock_stock_code == stockCode && x.impstock_execute_flag != ExecuteFlag.已完成 && x.IsDeleted == false).Any();
            if (impstockexist)
            {
                throw new UserFriendlyException("入库托盘流水表中存在该托盘号码未完成的流水信息！");
            }
            //托盘出库流水表是否存在
            var expstockexist = _esRepository.GetAllIncluding()
                                             .Where(x => x.expstock_stock_code == stockCode && x.expstock_execute_flag != ExecuteFlag.已完成 && x.IsDeleted == false).Any();
            if (expstockexist)
            {
                throw new UserFriendlyException("出库托盘流水表中存在该托盘号码未完成的流水信息！");
            }
            //库存表是否存在
            var inverexist = _iRepository.GetAllIncluding()
                                        .Where(x => x.inventory_stock_code == stockCode && x.IsDeleted == false).Any();
            if (inverexist)
            {
                throw new UserFriendlyException("托盘已存在库存中！");
            }
            return true;
        }
    }
}
