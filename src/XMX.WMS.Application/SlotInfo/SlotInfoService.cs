﻿using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.SlotInfo.Dto;
using Abp.Linq.Extensions;
using Abp.Application.Services.Dto;
using System.Threading.Tasks;
using System.Collections.Generic;
using XMX.WMS.Authorization;
using Abp.Authorization;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using XMX.WMS.Base.Session;

namespace XMX.WMS.SlotInfo
{
    [AbpAuthorize(PermissionNames.SlotBasicInfo)]
    public class SlotInfoService : AsyncCrudAppService<SlotInfo, SlotInfoDto, Guid, SlotInfoPagedRequest, SlotInfoCreatedDto, SlotInfoUpdatedDto>, ISlotInfoService
    {
        private readonly IRepository<RowInfo.RowInfo, Guid> _rRepository;
        private readonly IRepository<InventoryInfo.InventoryInfo, Guid> _iRepository;
        private readonly IRepository<GoodsInfo.GoodsInfo, Guid> _gRepository;
        private readonly Guid UserCompanyId;
        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public SlotInfoService(IRepository<SlotInfo, Guid> repository,
                               IRepository<RowInfo.RowInfo, Guid> rRepository,
                               IRepository<InventoryInfo.InventoryInfo, Guid> iRepository,
                               IRepository<GoodsInfo.GoodsInfo, Guid> gRepository) : base(repository)
        {
            _rRepository = rRepository;
            _iRepository = iRepository;
            _gRepository = gRepository;
            UserCompanyId = AbpSession.GetCompanyId();
            LogContext = DynamicDbContext.GetInstance(string.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                CompanyId = UserCompanyId,
                OptPath = "XMX.WMS.SlotInfo.SlotInfoService.",
                OptModule = "库位基础信息"
            };
        }

        /// <summary>
        /// 获取空库位需要的构造函数
        /// </summary>
        /// <param name="repository"></param>
        public SlotInfoService(IRepository<SlotInfo, Guid> repository,
                                IRepository<GoodsInfo.GoodsInfo, Guid> gRepository) : base(repository)
        {
            _gRepository = gRepository;
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.SlotBasicInfo_Get)]
        protected override IQueryable<SlotInfo> CreateFilteredQuery(SlotInfoPagedRequest input)
        {
            return Repository.GetAllIncluding(x => x.Area, x => x.Size, x => x.Row)
                                .WhereIf(AbpSession.UserId != 1, x => x.slot_company_id == UserCompanyId)
                                .WhereIf(input.slot_warehouse_id.HasValue, x => x.Row.row_warehouse_id == input.slot_warehouse_id)
                                .WhereIf(input.slot_area_id.HasValue, x => x.slot_area_id == input.slot_area_id)
                                .WhereIf(input.slot_stock_status != null, x => x.slot_stock_status == input.slot_stock_status)
                                .WhereIf(input.slot_closed_status != null, x => x.slot_closed_status == input.slot_closed_status)
                                .WhereIf(input.slot_imp_status != null, x => x.slot_imp_status == input.slot_imp_status)
                                .WhereIf(input.slot_exp_status != null, x => x.slot_exp_status == input.slot_exp_status)
                                .WhereIf(input.slot_size_level.HasValue, x => x.slot_size_level == input.slot_size_level)
                                .WhereIf(input.slot_size_level.HasValue, x => x.slot_size_level == input.slot_size_level)
                                .WhereIf(input.slot_layer0 != null, x => x.slot_layer >= input.slot_layer0)
                                .WhereIf(input.slot_layer1 != null, x => x.slot_layer <= input.slot_layer1)
                                .WhereIf(input.slot_column0 != null, x => x.slot_column >= input.slot_column0)
                                .WhereIf(input.slot_column1 != null, x => x.slot_column <= input.slot_column1)
                                .WhereIf(input.slot_row0 != null, x => x.slot_row >= input.slot_row0)
                                .WhereIf(input.slot_row1 != null, x => x.slot_row <= input.slot_row1)
                ;
        }

        /// <summary>
        /// 新增
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override async Task<SlotInfoDto> Create(SlotInfoCreatedDto input)
        {
            return null;
        }

        /// <summary>
        /// 修改
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public override async Task<SlotInfoDto> Update(SlotInfoUpdatedDto input)
        {
            return await base.Update(input);
        }

        /// <summary>
        /// 批量修改
        /// </summary>
        /// <param name="inputList"></param>
        /// <returns></returns>
        public async Task<ListResultDto<SlotInfoDto>> UpdateList(List<SlotInfoUpdatedDto> inputList)
        {
            List<SlotInfoDto> list = new List<SlotInfoDto>();
            foreach (SlotInfoUpdatedDto input in inputList)
            {
                list.Add(await base.Update(input));
            }
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "UpdateList", WMSOptLogInfo.WMSOptLogInfo.UPDATE, "批量更新", "批量更新", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return new ListResultDto<SlotInfoDto>(list);
        }

        /// <summary>
        /// 仓库使用率饼图
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public async Task<ListResultDto<UtilizationRateDto>> GetUtilizationRatePieChart(SlotInfoPagedRequest input)
        {
            var list = Repository.GetAllIncluding(x => x.Warehouse)
                            .Where(x => x.slot_company_id == UserCompanyId)
                            .Where(x => x.slot_closed_status == SlotClosed.正常)
                            .WhereIf(input.slot_warehouse_id != null, x => x.slot_warehouse_id == input.slot_warehouse_id)
                            .GroupBy(x => new { x.slot_warehouse_id, x.Warehouse.warehouse_code, x.Warehouse.warehouse_name, x.Warehouse.warehouse_type})
                            .Select(g => new UtilizationRateDto{
                                warehouse_id = g.Key.slot_warehouse_id,
                                warehouse_code = g.Key.warehouse_code, 
                                warehouse_name = g.Key.warehouse_name, 
                                warehouse_type = g.Key.warehouse_type,
                                empty_count =  g.Count(x => x.slot_stock_status == 0), 
                                not_empty_count = g.Count(x => x.slot_stock_status != 0)}).ToList();
            return new ListResultDto<UtilizationRateDto>(list);
            throw new NotImplementedException();
        }

        /// <summary>
        /// 获取库位使用率
        /// </summary>
        /// <returns></returns>
        public PercentDto GetNowSlotPercent()
        {
            var query = Repository.GetAll().Where(x => x.slot_company_id == UserCompanyId)
                                            .Where(x => x.slot_closed_status == SlotClosed.正常);
            PercentDto percent = new PercentDto();
            percent.empty_count = query.Where(x => x.slot_stock_status == 0).Count();
            percent.not_empty_count = query.Where(x => x.slot_stock_status != 0).Count();
            return percent;
        }

        /// <summary>
        /// 获取层库位信息
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.SlotBasicInfo_Get)]
        public SlotInfoTable GetSlotByLayer(SlotInfoRequest input)
        {
            SlotInfoTable sit = new SlotInfoTable();
            // 库位初始排信息
            List<RowInfo.RowInfo> rowList = _rRepository.GetAll().Where(x => x.row_warehouse_id == input.slot_warehouse_id).OrderBy(x => x.row_order).ToList();
            // 表头
            sit.clos = rowList.Select(x => new SlotTH(x.Id.ToString(), x.row_name, x.row_type)).ToList();
            // 最大列数据
            RowInfo.RowInfo row = rowList.Where(x => x.row_type == RowType.库位).OrderByDescending(x => x.row_end_column).ToList()[0];
            // 库位排长度
            int row_count = rowList.Where(x => x.row_type == RowType.库位).Count();
            // 库位信息
            List<SlotInfo> slotList = Repository.GetAllIncluding(x => x.Row).Where(x => x.Row.row_warehouse_id == input.slot_warehouse_id)
                                                                  .Where(x => x.slot_layer == input.slot_layer)
                                                                  .OrderBy(x => x.slot_row)
                                                                  .OrderBy(x => x.slot_column).ToList();
            // 列,排二维数组
            SlotInfoGoods[, ] slotArr = new SlotInfoGoods[row.row_end_column, row_count];
            SlotInfoGoods sg;
            foreach (SlotInfo slot in slotList)
            {
                if (slot.slot_stock_status != SlotStock.空闲)
                    sg = new SlotInfoGoods(slot, _iRepository.GetAllIncluding(x => x.Goods, x => x.Quality).Where(x => x.inventory_slot_code == slot.Id).ToList());
                else
                    sg = new SlotInfoGoods(slot, null);
                slotArr[slot.slot_column - 1, slot.slot_row - 1] = sg;
            }
            // 库位信息
            sit.slotArr = slotArr;
            return sit;
        }

        /// <summary>
        /// 获取排库位信息
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.SlotBasicInfo_Get)]
        public SlotInfoTable GetSlotByRow(SlotInfoRequest input)
        {
            SlotInfoTable sit = new SlotInfoTable();
            // 库位初始排信息
            List<RowInfo.RowInfo> rowList = _rRepository.GetAll().Where(x => x.row_warehouse_id == input.slot_warehouse_id).OrderBy(x => x.row_order).ToList();
            // 库位最大列
            RowInfo.RowInfo column_row = rowList.Where(x => x.row_type == RowType.库位).OrderByDescending(x => x.row_end_column).ToList()[0];
            // 表头
            List<SlotTH> columnList = new List<SlotTH>();
            for (int i = 1; i <= column_row.row_end_column; i++)
                columnList.Add(new SlotTH("column" + i, i + "列"));
            // 表头
            sit.clos = columnList;
            // 库位最大层
            RowInfo.RowInfo layer_row = rowList.Where(x => x.row_type == RowType.库位).OrderByDescending(x => x.row_end_layer).ToList()[0];
            // 库位信息
            List<SlotInfo> slotList = Repository.GetAll().Where(x => x.slot_warehouse_id == input.slot_warehouse_id)
                                                        .Where(x => x.slot_row_id == input.slot_row_id)
                                                        .OrderBy(x => x.slot_layer)
                                                        .OrderBy(x => x.slot_column).ToList();
            // 排,列二维数组
            SlotInfoGoods[,] slotArr = new SlotInfoGoods[layer_row.row_end_layer, column_row.row_end_column];
            SlotInfoGoods sg;
            foreach (SlotInfo slot in slotList)
            {
                if (slot.slot_stock_status != SlotStock.空闲)
                    sg = new SlotInfoGoods(slot, _iRepository.GetAllIncluding(x => x.Goods, x => x.Quality).Where(x => x.inventory_slot_code == slot.Id).ToList());
                else
                    sg = new SlotInfoGoods(slot, null);
                slotArr[slot.slot_layer - 1, slot.slot_column - 1] = sg;
            }
            // 库位信息
            sit.slotArr = slotArr;
            return sit;
        }

        /// <summary>
        /// 判断该仓库下库位是否存在
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public string GetSlotCodeId(SlotInfoRequest input)
        {
            var strName = "";
            var rkis_recode = Repository.GetAll().Where(x => x.slot_code == input.slot_code).Where(x => x.slot_warehouse_id == input.slot_warehouse_id).Any();
            if (rkis_recode)
            {
                var SlotInfoId = Repository.FirstOrDefault(x => x.slot_code == input.slot_code &&  x.slot_warehouse_id == input.slot_warehouse_id).Id;
                strName = "1@" + SlotInfoId;
            }
            else
            {
                strName = "0@该库位不存在";
            }
            return strName;
        }

        /// <summary>
        /// 获取空库位
        /// </summary>
        /// <returns></returns>
        public SlotInfo GetEmptySlot(Guid warehouseId, string goodsId)
        {
            SlotInfo slot = null;
            //查找仓库下可用库位
            List<SlotInfo> list = Repository.GetAll().Where(x => x.slot_warehouse_id == warehouseId)
                                        .Where(x => x.slot_stock_status == SlotStock.空闲)
                                        .Where(x => x.slot_closed_status == SlotClosed.正常)
                                        .Where(x => x.slot_imp_status == SlotImp.正常).ToList();
            if (!string.IsNullOrWhiteSpace(goodsId))
            {
                try 
                {
                    StrategyWarehousing.StrategyWarehousing sw = _gRepository.GetAllIncluding(x => x.StrategyWarehousing)
                                                                    .Where(x => x.Id == Guid.Parse(goodsId))
                                                                    .FirstOrDefault().StrategyWarehousing;
                    if (sw != null && sw.warehousing_select == SelecType.从小到大)
                        slot = list.OrderBy(x => x.slot_column)
                                    .OrderBy(x => x.slot_layer)
                                    .OrderBy(x => x.slot_row)
                                    .FirstOrDefault();
                    else if(sw != null && sw.warehousing_select == SelecType.从大到小)
                        slot = list.OrderBy(x => x.slot_column)
                                    .OrderBy(x => x.slot_layer)
                                    .OrderByDescending(x => x.slot_row)
                                    .FirstOrDefault();
                    else
                        slot = list.OrderBy(x => x.slot_column)
                                    .OrderBy(x => x.slot_layer)
                                    .OrderBy(x => x.slot_row)
                                    .FirstOrDefault();
                } 
                catch (Exception)
                {
                    slot = list.OrderBy(x => x.slot_column)
                                    .OrderBy(x => x.slot_layer)
                                    .OrderBy(x => x.slot_row)
                                    .FirstOrDefault();
                }
            }
            return slot;
        }
    }
}
