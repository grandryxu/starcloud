using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.InventoryInfo.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using Abp.Application.Services.Dto;
using AutoMapper;
using System.Collections.Generic;
using Abp.AutoMapper;
using System.Threading.Tasks;

namespace XMX.WMS.InventoryInfo
{
    public class InventoryInfoService : AsyncCrudAppService<InventoryInfo, InventoryInfoDto, Guid, InventoryInfoPagedRequest, InventoryInfoCreatedDto, InventoryInfoUpdatedDto>, IInventoryInfoService
    {
        private readonly IRepository<ImportBillbody.ImportBillbody, Guid> _intRepository;
        private readonly IRepository<ExportBillbody.ExportBillbody, Guid> _outRepository;
        private readonly IRepository<AreaInfo.AreaInfo, Guid> _area;
        private readonly IRepository<SlotInfo.SlotInfo, Guid> _slotInfo;

        public InventoryInfoService(IRepository<InventoryInfo, Guid> repository,
            IRepository<ImportBillbody.ImportBillbody, Guid> intRepository,
            IRepository<ExportBillbody.ExportBillbody, Guid> outRepository,
            IRepository<AreaInfo.AreaInfo, Guid> areaRepository,
            IRepository<SlotInfo.SlotInfo, Guid> slotRepository) : base(repository)
        {
            _intRepository = intRepository;
            _outRepository = outRepository;
            _area = areaRepository;
            _slotInfo = slotRepository;
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        protected override IQueryable<InventoryInfo> CreateFilteredQuery(InventoryInfoPagedRequest input)
        {
            return Repository.GetAllIncluding()
                    .WhereIf(!input.inventory_bill_bar.IsNullOrWhiteSpace(), x => x.inventory_bill_bar.Contains(input.inventory_bill_bar))
                    .WhereIf(!input.inventory_batch_no.IsNullOrWhiteSpace(), x => x.inventory_batch_no.Contains(input.inventory_batch_no))
                    .WhereIf(!input.inventory_lots_no.IsNullOrWhiteSpace(), x => x.inventory_lots_no.Contains(input.inventory_lots_no))
                    ;
        }

        /// <summary>
        /// 盘点筛选库存
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public List<InventoryInfo> GetInventoryForStockTasking(InventoryInfoPagedRequest input)
        {
            return Repository.GetAllIncluding(x => x.Goods)
                    .Where(x => x.inventory_status == InventoryStatus.可用)
                    .WhereIf(input.task_warehouse_id.HasValue, x => x.inventory_warehouse_id == input.task_warehouse_id)
                    .WhereIf(input.task_type.HasValue, x => (input.task_type == StockTaskingType.动态盘点
                                                                && ((Convert.ToDateTime(input.inventory_date_b + " 00:00:00") <= x.CreationTime && x.CreationTime <= Convert.ToDateTime(input.inventory_date_e + " 23:59:59"))
                                                                || (Convert.ToDateTime(input.inventory_date_b + " 00:00:00") <= x.LastModificationTime && x.LastModificationTime <= Convert.ToDateTime(input.inventory_date_e + " 23:59:59"))))
                                                            || (input.task_type == StockTaskingType.物资盘点 && x.inventory_goods_id == input.task_goods_id))
                    .ToList();
        }

        /// <summary>
        /// 手动出库查询库存
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public PagedResultDto<InventoryInfoDto> GetInventoryInfoForHead(InventoryInfoPagedRequest input)
        {
            List<ExportBillbody.ExportBillbody> bodyList = _outRepository.GetAllIncluding().Where(x => x.expbody_imphead_id == input.head_id).ToList();
            var list = from m in Repository.GetAllIncluding(x => x.Goods, x => x.Quality, x => x.Warehouse, x => x.Port, x => x.Slot)
                       join s in _outRepository.GetAllIncluding().Where(x => x.expbody_imphead_id == input.head_id)
                       on new { a = m.inventory_batch_no, b = m.inventory_goods_id } equals new { a = s.expbody_batch_no, b = s.expbody_goods_id }
                       select new InventoryInfoDto
                       {
                           Id = m.Id,
                           inventory_batch_no = m.inventory_batch_no,
                           inventory_lots_no = m.inventory_lots_no,
                           inventory_product_date = m.inventory_product_date,
                           inventory_product_lineid = m.inventory_product_lineid,
                           inventory_remark = m.inventory_remark,
                           inventory_bill_bar = m.inventory_bill_bar,
                           inventory_vaildate_date = m.inventory_vaildate_date,
                           inventory_recheck_date = m.inventory_recheck_date,
                           inventory_quantity = m.inventory_quantity,
                           inventory_box_code = m.inventory_box_code,
                           inventory_stock_code = m.inventory_stock_code,
                           inventory_status = m.inventory_status,
                           inventory_stock_status = m.inventory_stock_status,
                           inventory_date = m.inventory_date,
                           inventory_goods_id = m.inventory_goods_id,
                           Goods = m.Goods,
                           inventory_quality_status = m.inventory_quality_status,
                           Quality = m.Quality,
                           inventory_warehouse_id = m.inventory_warehouse_id,
                           Warehouse = m.Warehouse,
                           inventory_port_id = m.inventory_port_id,
                           Port = m.Port,
                           inventory_slot_code = m.inventory_slot_code,
                           Slot = m.Slot
                       };
            //获取总数
            var listCount = list.Count();
            //默认的分页方式
            var inventoryList = list.Skip(input.SkipCount).Take(input.MaxResultCount).ToList();
            //ABP提供了扩展方法PageBy分页方式
            //var taskList = query.PageBy(input).ToList();
            return new PagedResultDto<InventoryInfoDto>(listCount, inventoryList);
        }

        /// <summary>
        /// 按照条件查询统计-按物料，质量状态合计
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        public PagedResultDto<InventoryStatistics> GetInventoryStatistics(InventoryInfoPagedRequest input)
        {
            //初步过滤排序
            List<InventoryInfo> iiList = Repository.GetAllIncluding(x => x.Goods, x => x.Goods.Unit, x => x.Quality, x => x.Warehouse)
                                            .Where(x => !x.inventory_goods_id.ToString().Equals("00000000-0000-0000-0000-000000000001"))
                                            .WhereIf(!input.goods_name.IsNullOrWhiteSpace(), x => (x.Goods.goods_code.Contains(input.goods_name) || x.Goods.goods_name.Contains(input.goods_name)))
                                            .WhereIf(!input.quality_name.IsNullOrWhiteSpace(), x => x.Quality.quality_name.Contains(input.quality_name))
                                            .WhereIf(!input.warehouse_name.IsNullOrWhiteSpace(), x => x.Warehouse.warehouse_name.Contains(input.warehouse_name))
                                            .WhereIf(!(input.inventory_date_b.IsNullOrWhiteSpace() && input.inventory_date_e.IsNullOrWhiteSpace()), x => Convert.ToDateTime(input.inventory_date_b + " 00:00:00") <= x.CreationTime && x.CreationTime <= Convert.ToDateTime(input.inventory_date_e + " 23:59:59")).ToList();
            List<InventoryStatistics> list = new List<InventoryStatistics>();
            if (iiList.Count > 0)
                list = iiList.GroupBy(x => new
                {
                    x.inventory_goods_id,
                    x.Goods.goods_code,
                    x.Goods.goods_name,
                    x.Goods.goods_standard,
                    x.Goods.goods_unit,
                    x.Goods.Unit.unit_name,
                    x.inventory_quality_status,
                    x.Quality.quality_name
                })
                                            .Select(group => new InventoryStatistics(group.Key.goods_code,
                                                                                     group.Key.goods_name,
                                                                                     group.Key.goods_standard,
                                                                                     group.Key.unit_name,
                                                                                     group.Key.quality_name,
                                                                                     group.Sum(g => g.inventory_quantity))).ToList();
            //获取总数
            var tasksCount = list.Count();
            //默认的分页方式
            var taskList = list.Skip(input.SkipCount).Take(input.MaxResultCount).ToList();
            //ABP提供了扩展方法PageBy分页方式
            //var taskList = query.PageBy(input).ToList();
            return new PagedResultDto<InventoryStatistics>(tasksCount, taskList);
        }

        /// <summary>
        /// 按照条件查询统计-按物料，质量状态，批次合计
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        public PagedResultDto<InventoryStatistics> GetInventoryStatisticsBatch(InventoryInfoPagedRequest input)
        {
            //初步过滤排序
            List<InventoryInfo> iiList = Repository.GetAllIncluding(x => x.Goods, x => x.Goods.Unit, x => x.Quality, x => x.Warehouse)
                                            .Where(x => !x.inventory_goods_id.ToString().Equals("00000000-0000-0000-0000-000000000001"))
                                            .WhereIf(!input.goods_name.IsNullOrWhiteSpace(), x => (x.Goods.goods_code.Contains(input.goods_name) || x.Goods.goods_name.Contains(input.goods_name)))
                                            .WhereIf(!input.quality_name.IsNullOrWhiteSpace(), x => x.Quality.quality_name.Contains(input.quality_name))
                                            .WhereIf(!input.warehouse_name.IsNullOrWhiteSpace(), x => x.Warehouse.warehouse_name.Contains(input.warehouse_name))
                                            .WhereIf(!(input.inventory_date_b.IsNullOrWhiteSpace() && input.inventory_date_e.IsNullOrWhiteSpace()), x => Convert.ToDateTime(input.inventory_date_b + " 00:00:00") <= x.CreationTime && x.CreationTime <= Convert.ToDateTime(input.inventory_date_e + " 23:59:59")).ToList();
            List<InventoryStatistics> list = new List<InventoryStatistics>();
            if (iiList.Count > 0)
                list = iiList.GroupBy(x => new
                {
                    x.inventory_goods_id,
                    x.Goods.goods_code,
                    x.Goods.goods_name,
                    x.Goods.goods_standard,
                    x.Goods.goods_unit,
                    x.Goods.Unit.unit_name,
                    x.inventory_quality_status,
                    x.Quality.quality_name,
                    x.inventory_batch_no
                })
                                            .Select(group => new InventoryStatistics(group.Key.inventory_goods_id,
                                                                                     group.Key.goods_code,
                                                                                     group.Key.goods_name,
                                                                                     group.Key.goods_standard,
                                                                                     group.Key.unit_name,
                                                                                     group.Key.inventory_quality_status,
                                                                                     group.Key.quality_name,
                                                                                     group.Key.inventory_batch_no,
                                                                                     group.Sum(g => g.inventory_quantity))).ToList();
            //获取总数
            var tasksCount = list.Count();
            //默认的分页方式
            var taskList = list.Skip(input.SkipCount).Take(input.MaxResultCount).ToList();
            //ABP提供了扩展方法PageBy分页方式
            //var taskList = query.PageBy(input).ToList();
            return new PagedResultDto<InventoryStatistics>(tasksCount, taskList);
        }

        /// <summary>
        /// 查询空托盘库存
        /// </summary>
        /// <returns></returns>
        public async Task<ListResultDto<InventoryInfo>> GetStockService()
        {
            List<InventoryInfo> list = Repository.GetAllIncluding(x => x.Goods).Where(x => x.inventory_goods_id.ToString().Equals("00000000-0000-0000-0000-000000000001"))
                                                                               .Where(x => x.inventory_quantity > 0).ToList();
            return new ListResultDto<InventoryInfo>(list);
        }

        /// <summary>
        /// 入出库统计
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        public PagedResultDto<InOutStatistics> GetInOutStatistics(InventoryInfoPagedRequest input)
        {
            List<InOutStatistics> list = new List<InOutStatistics>();
            List<InOutStatistics> inList = new List<InOutStatistics>();
            List<InOutStatistics> outList = new List<InOutStatistics>();
            if (!input.bill_type.HasValue || input.bill_type == BillType.入库)
                //初步过滤排序
                inList = Mapper.Map<List<InOutStatistics>>(_intRepository.GetAllIncluding(x => x.GoodsInfo)
                                            .WhereIf(!input.goods_name.IsNullOrWhiteSpace(), x => (x.GoodsInfo.goods_code.Contains(input.goods_name) || x.GoodsInfo.goods_name.Contains(input.goods_name)))
                                            .WhereIf(!(input.inventory_date_b.IsNullOrWhiteSpace() && input.inventory_date_e.IsNullOrWhiteSpace()), x => Convert.ToDateTime(input.inventory_date_b + " 00:00:00") <= x.CreationTime && x.CreationTime <= Convert.ToDateTime(input.inventory_date_e + " 23:59:59"))
                                            .Select(x => new InOutStatistics(x.GoodsInfo.goods_code, x.GoodsInfo.goods_name, x.CreationTime,
                                                                            x.impbody_batch_no, x.impbody_bill_bar, x.impbody_fulfill_quantity, BillType.入库)));
            if (!input.bill_type.HasValue || input.bill_type == BillType.出库)
                outList = Mapper.Map<List<InOutStatistics>>(_outRepository.GetAllIncluding(x => x.Goods)
                                            .WhereIf(!input.goods_name.IsNullOrWhiteSpace(), x => (x.Goods.goods_code.Contains(input.goods_name) || x.Goods.goods_name.Contains(input.goods_name)))
                                            .WhereIf(!(input.inventory_date_b.IsNullOrWhiteSpace() && input.inventory_date_e.IsNullOrWhiteSpace()), x => Convert.ToDateTime(input.inventory_date_b + " 00:00:00") <= x.CreationTime && x.CreationTime <= Convert.ToDateTime(input.inventory_date_e + " 23:59:59"))
                                            .Select(x => new InOutStatistics(x.Goods.goods_code, x.Goods.goods_name, x.CreationTime,
                                                            x.expbody_batch_no, x.expbody_bill_bar, x.expbody_fulfill_quantity, BillType.出库)));
            if (inList.Count > 0)
                list.Union(inList);
            if (outList.Count > 0)
                list.Union(outList);
            //获取总数
            var tasksCount = list.Count();
            //默认的分页方式
            var taskList = list.Skip(input.SkipCount).Take(input.MaxResultCount).ToList();
            //ABP提供了扩展方法PageBy分页方式
            //var taskList = query.PageBy(input).ToList();
            return new PagedResultDto<InOutStatistics>(tasksCount, taskList);
        }

        /// <summary>
        /// 总库存查询页面
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public PagedResultDto<TotalInventorydto> GetTotalInventory(TotalInventory input)
        {
            var iquery = Repository.GetAllIncluding(x => x.Goods, x => x.Goods.Unit)
                           .WhereIf(!input.goods_name.IsNullOrWhiteSpace(), x => x.Goods.goods_name.Contains(input.goods_name))
                           .WhereIf(!(input.inventory_date_b.IsNullOrWhiteSpace() && input.inventory_date_e.IsNullOrWhiteSpace()), x => Convert.ToDateTime(input.inventory_date_b + " 00:00:00") <= x.CreationTime && x.CreationTime <= Convert.ToDateTime(input.inventory_date_e + " 23:59:59"))
                           .WhereIf(input.warehouse_id != null, x => x.inventory_warehouse_id == input.warehouse_id).GroupBy(x => new
                           {
                               inventory_good_code = x.Goods.goods_code,
                               inventory_good_name = x.Goods.goods_name,
                               inventory_unit = x.Goods.Unit.unit_name,
                               unitid = x.Goods.Unit.Id,
                               inventory_batch_no = x.inventory_batch_no,
                               inventory_lots_no = x.inventory_lots_no,
                               inventory_quality = x.Quality.quality_name
                           }).Select(g => new TotalInventorydto
                           {
                               inventory_good_code = g.Key.inventory_good_code,
                               inventory_good_name = g.Key.inventory_good_name,
                               unitid = g.Key.unitid,
                               inventory_unit = g.Key.inventory_unit,
                               inventory_batch_no = g.Key.inventory_batch_no,
                               inventory_lots_no = g.Key.inventory_lots_no,
                               inventory_quality = g.Key.inventory_quality,
                               inventory_total = g.Sum(y => y.inventory_quantity)
                           });
            //获取总数
            var tasksCount = iquery.Count();
            //默认的分页方式
            var taskList = iquery.Skip(input.SkipCount).Take(input.MaxResultCount).ToList();
            //ABP提供了扩展方法PageBy分页方式
            //var taskList = query.PageBy(input).ToList();
            return new PagedResultDto<TotalInventorydto>(tasksCount, taskList);
        }

        /// <summary>
        /// 总库存详情查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public List<TotalInventoryDetailDto> GetTotalInventoryDetail(TotalInventoryDetail input)
        {
            List<TotalInventoryDetailDto> list = new List<TotalInventoryDetailDto>();
            var iquery = Repository.GetAllIncluding(x => x.Goods, x => x.Quality, x => x.Slot)
                .WhereIf(!input.inventory_good_code.IsNullOrWhiteSpace(), x => x.Goods.goods_code == input.inventory_good_code)
                .WhereIf(!input.inventory_good_name.IsNullOrWhiteSpace(), x => x.Goods.goods_name == input.inventory_good_name)
                .WhereIf(input.inventory_unit.HasValue, x => x.Goods.goods_unit == input.inventory_unit)
                .Select(x => new TotalInventoryDetailDto
                {
                    slot_code = x.Slot.slot_code,
                    stock_code = x.inventory_stock_code,
                    bill_code = x.inventory_batch_no,
                    package_code = x.inventory_box_code,
                    number = x.inventory_quantity,
                    imptime = x.inventory_date,
                    status = x.Quality.quality_name
                }).ToList();
            if (iquery != null && iquery.Count > 0)
                list = iquery;
            return list;
        }

        /// <summary>
        /// 库位库存查询页面
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public PagedResultDto<SlotInventorydto> GetSlotInventory(SlotInventory input)
        {
            var iquery = Repository.GetAllIncluding(x => x.Slot)
                .WhereIf(!input.slot_code.IsNullOrWhiteSpace(), x => x.Slot.slot_code.Contains(input.slot_code))
                .WhereIf(!(input.inventory_date_b.IsNullOrWhiteSpace() && input.inventory_date_e.IsNullOrWhiteSpace()), x => Convert.ToDateTime(input.inventory_date_b + " 00:00:00") <= x.CreationTime && x.CreationTime <= Convert.ToDateTime(input.inventory_date_e + " 23:59:59"))
                .WhereIf(!input.stock_code.IsNullOrWhiteSpace(), x => x.inventory_stock_code.Contains(input.stock_code)).GroupBy(x => new
                {
                    slotid = x.inventory_slot_code,
                    stock_code = x.inventory_stock_code,
                    inventory_status = x.inventory_status,
                    slot_code = x.Slot.slot_code,
                    imp_lock = x.Slot.slot_imp_status,
                    ex_lock = x.Slot.slot_exp_status,
                    imptime = x.inventory_date,
                    warehouseid = x.inventory_warehouse_id,
                })
                .Select(g => new SlotInventorydto
                {
                    slotid = g.Key.slotid,
                    stock_code = g.Key.stock_code,
                    inventory_status = g.Key.inventory_status,
                    slot_code = g.Key.slot_code,
                    imp_lock = g.Key.imp_lock,
                    ex_lock = g.Key.ex_lock,
                    imptime = g.Key.imptime,
                    warehouseid = g.Key.warehouseid,
                    inventory_total = g.Sum(y => y.inventory_quantity)
                });

            // 获取总数
            var tasksCount = iquery.Count();
            //默认的分页方式
            var taskList = iquery.Skip(input.SkipCount).Take(input.MaxResultCount).ToList();
            //ABP提供了扩展方法PageBy分页方式
            //var taskList = query.PageBy(input).ToList();
            return new PagedResultDto<SlotInventorydto>(tasksCount, taskList);
        }

        /// <summary>
        /// 库位库存详情查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public List<SlotInventoryDetailDto> GetSlotInventoryDetail(SlotInventoryDetail input)
        {
            //List<SlotInventoryDetailDto> list = new List<SlotInventoryDetailDto>();
            var iquery = Repository.GetAllIncluding(x => x.Goods, x => x.Quality)
                .WhereIf(input.slot_code.HasValue, x => x.inventory_slot_code == input.slot_code)
                .WhereIf(!input.stock_code.IsNullOrWhiteSpace(), x => x.inventory_stock_code == input.stock_code)
                .WhereIf(input.warehouseid != null, x => x.inventory_warehouse_id == input.warehouseid)
                .Select(x => new SlotInventoryDetailDto
                {
                    good_code = x.Goods.goods_code,
                    good_name = x.Goods.goods_name,
                    number = x.inventory_quantity,
                    batch_no = x.inventory_batch_no,
                    imptime = x.inventory_date,
                    status = x.Quality.quality_name,
                    package_code = x.inventory_box_code,
                    unit = x.Goods.Unit.unit_name
                }).ToList();
            //if (iquery != null && iquery.Count > 0)
            return iquery.ToList();
        }

        /// <summary>
        /// 托盘库存查询页面
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public PagedResultDto<StockInventoryDto> GetStockInventory(StockInventory input)
        {
            //条件查询
            var iquery = Repository.GetAllIncluding(x => x.Warehouse, x => x.Slot)
                                    .Where(x => x.inventory_goods_id.ToString().Equals("00000000-0000-0000-0000-000000000001"))
                                    .WhereIf(!input.stock_code.IsNullOrWhiteSpace(), x => x.inventory_stock_code.Contains(input.stock_code))
                                    .GroupBy(x => new
                                    {
                                        slot_code = x.Slot.slot_code,
                                        slot_id = x.inventory_slot_code,
                                        stock_code = x.inventory_stock_code,
                                        inventory_status = x.inventory_status,
                                        imp_lock = x.Slot.slot_imp_status,
                                        exp_lock = x.Slot.slot_exp_status,
                                        imptime = x.CreationTime,
                                        warehouseid = x.inventory_warehouse_id,
                                        warehouse_name = x.Warehouse.warehouse_name
                                    })
                                    .Select(g => new StockInventoryDto
                                    {
                                        slot_code = g.Key.slot_code,
                                        slot_id = g.Key.slot_id,
                                        stock_code = g.Key.stock_code,
                                        inventory_total = g.Sum(y => y.inventory_quantity),
                                        inventory_status= g.Key.inventory_status,
                                        imp_lock=g.Key.imp_lock,
                                        exp_lock=g.Key.exp_lock,
                                        imptime=g.Key.imptime,
                                        warehouseid=g.Key.warehouseid,
                                        warehouse_name=g.Key.warehouse_name
                                    });

            // 获取总数
            var tasksCount = iquery.Count();
            //默认的分页方式
            var taskList = iquery.Skip(input.SkipCount).Take(input.MaxResultCount).ToList();
            //ABP提供了扩展方法PageBy分页方式
            //var taskList = query.PageBy(input).ToList();
            return new PagedResultDto<StockInventoryDto>(tasksCount, taskList.MapTo<List<StockInventoryDto>>());
        }

        /// <summary>
        /// 托盘库存详情查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public List<StockInventoryDetailDto> GetStockInventoryDetail(StockInventoryDetail input)
        {
            List<StockInventoryDetailDto> list = new List<StockInventoryDetailDto>();
            var iquery = Repository.GetAllIncluding(x => x.Goods, x => x.Quality, x => x.Goods.Unit)
                .Where(x => x.inventory_goods_id.ToString().Equals("00000000-0000-0000-0000-000000000001"))
                .WhereIf(input.slot_code.HasValue, x => x.inventory_slot_code == input.slot_code)
                .WhereIf(!input.stock_code.IsNullOrWhiteSpace(), x => x.inventory_stock_code == input.stock_code)
                .WhereIf(input.warehouseid != null, x => x.inventory_warehouse_id == input.warehouseid)
                .Select(x => new StockInventoryDetailDto
                {
                    good_code = x.Goods.goods_code,
                    good_name = x.Goods.goods_name,
                    number = x.inventory_quantity,
                    bill_code = x.inventory_batch_no,
                    //imptime = x.inventory_datetime,
                    status = x.Quality.quality_name,
                    package_code = x.inventory_box_code,
                    unit = x.Goods.Unit.unit_name
                }).ToList();
            if (iquery != null && iquery.Count > 0)
                list = iquery;
            return list;
        }
    }
}
