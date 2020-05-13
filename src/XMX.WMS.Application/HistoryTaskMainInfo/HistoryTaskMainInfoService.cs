using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.HistoryTaskMainInfo.Dto;
using Abp.Linq.Extensions;
using System.Linq.Dynamic.Core;
using System.Collections.Generic;
using Abp.Application.Services.Dto;
using System.Threading.Tasks;
using Abp.AutoMapper;
using XMX.WMS.Authorization;
using Abp.Authorization;

namespace XMX.WMS.HistoryTaskMainInfo
{
    [AbpAuthorize(PermissionNames.MainHistorytask)]
    public class HistoryTaskMainInfoService : AsyncCrudAppService<HistoryTaskMainInfo, HistoryTaskMainInfoDto, Guid, HistoryTaskMainInfoPagedRequest, HistoryTaskMainInfoCreatedDto, HistoryTaskMainInfoUpdatedDto>, IHistoryTaskMainInfoService
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
        public HistoryTaskMainInfoService(IRepository<HistoryTaskMainInfo, Guid> repository,
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
            IRepository<ExportStock.ExportStock, Guid> esRepository) : base(repository)
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
        }

        /// <summary>
        /// 获取
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override Task<HistoryTaskMainInfoDto> Get(EntityDto<Guid> input)
        {
            return base.Get(input);
        }

        /// <summary>
        /// 分页条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.MainHistorytask_Get)]
        public async override Task<PagedResultDto<HistoryTaskMainInfoDto>> GetAll(HistoryTaskMainInfoPagedRequest input)
        {
            List<HistoryTaskMainInfoDto> list = Repository.GetAllIncluding(x => x.Port1, x => x.Slot1, x => x.Slot1.Row.Warehouse)
                                                    .WhereIf(input.main_mode != null, x => x.main_mode == input.main_mode)
                                                    .WhereIf(input.main_execute_flag != null, x => x.main_execute_flag == input.main_execute_flag)
                                                    .WhereIf(input.main_manual_flag != null, x => x.main_manual_flag == input.main_manual_flag)
                                                    .WhereIf(input.main_creat_datetime != null, x => x.CreationTime == input.main_creat_datetime)
                                                    .OrderBy(x => x.main_execute_flag)
                                                    .Select(x => new HistoryTaskMainInfoDto(x)).ToList();
            foreach (HistoryTaskMainInfoDto task in list)
            {
                if (task.main_mode == TaskType.入库)
                {
                    ImportOrder.ImportOrder order = _ioRepository.GetAllIncluding(x => x.Goods).Where(x => x.task_id == task.Id).ToList()[0];
                    task.material_id = order.Goods.Id;
                    task.material_name = order.Goods.goods_name;
                    task.exporder_batch_no = order.imporder_batch_no;
                    task.exporder_quantity = order.imporder_quantity;
                }
                else if (task.main_mode == TaskType.出库)
                {
                    ExportOrder.ExportOrder order = _eoRepository.GetAllIncluding(x => x.ExportBillbody.Goods).Where(x => x.task_id == task.Id).ToList()[0];
                    task.material_id = order.ExportBillbody.Goods.Id;
                    task.material_name = order.ExportBillbody.Goods.goods_name;
                    task.exporder_batch_no = order.exporder_batch_no;
                    task.exporder_quantity = order.exporder_quantity;
                }
                else if (task.main_mode == TaskType.空托盘入库)
                {
                    ImportStock.ImportStock stock = _isRepository.GetAllIncluding(x => x.Goods).Where(x => x.task_id == task.Id).ToList()[0];
                    task.material_id = stock.Goods.Id;
                    task.material_name = stock.Goods.goods_name;
                    task.exporder_quantity = stock.impstock_quantity;
                }
                else if (task.main_mode == TaskType.空托盘出库)
                {
                    ExportStock.ExportStock stock = _esRepository.GetAllIncluding(x => x.Goods).Where(x => x.expstock_task_id == task.Id).ToList()[0];
                    task.material_id = stock.Goods.Id;
                    task.material_name = stock.Goods.goods_name;
                    task.exporder_quantity = stock.expstock_quantity;
                }
            }
            //查询总数
            var tasksCount = list.Count();
            //默认的分页方式
            var taskList = list.Skip(input.SkipCount).Take(input.MaxResultCount).ToList();
            await Task.Delay(1);
            return new PagedResultDto<HistoryTaskMainInfoDto>(tasksCount, taskList);
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.MainHistorytask_Add)]
        public override async Task<HistoryTaskMainInfoDto> Create(HistoryTaskMainInfoCreatedDto input)
        {
            return null;
        }

        /// <summary>
        /// 编辑
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.MainHistorytask_Update)]
        public override async Task<HistoryTaskMainInfoDto> Update(HistoryTaskMainInfoUpdatedDto input)
        {
            return null;
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.MainHistorytask_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            
        }
    }
}
