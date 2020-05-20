using Abp.Application.Services;
using Abp.Domain.Repositories;
using System;
using System.Linq;
using XMX.WMS.RowInfo.Dto;
using Abp.Linq.Extensions;
using Abp.Extensions;
using System.Collections.Generic;
using Abp.Application.Services.Dto;
using XMX.WMS.Authorization;
using Abp.Authorization;
using System.Threading.Tasks;
using Abp.UI;
using XMX.WMS.EntityFrameworkCore.Dynamic;
using XMX.WMS.WMSOptLogInfo;
using Newtonsoft.Json;
using XMX.WMS.Base.Session;

namespace XMX.WMS.RowInfo
{
    [AbpAuthorize(PermissionNames.SlotInit)]
    public class RowInfoService : AsyncCrudAppService<RowInfo, RowInfoDto, Guid, RowInfoPagedRequest, RowInfoCreatedDto, RowInfoUpdatedDto>, IRowInfoService
    {
        private readonly IRepository<SlotInfo.SlotInfo, Guid> SlotRepository;
        private readonly IRepository<WarehouseInfo.WarehouseInfo, Guid> WhRepository;
        private readonly IRepository<TunnelInfo.TunnelInfo, Guid> tunnelRepository;
        private readonly Guid UserCompanyId;
        //日志
        private DynamicDbContext LogContext;
        private WMSOptLogInfo.WMSOptLogInfo logInfoEntity;
        public RowInfoService(IRepository<RowInfo, Guid> repository, IRepository<SlotInfo.SlotInfo, Guid> SlotRepository,
            IRepository<WarehouseInfo.WarehouseInfo, Guid> WhRepository,
            IRepository<TunnelInfo.TunnelInfo, Guid> tunnelRepository) : base(repository)
        {
            this.SlotRepository = SlotRepository;
            this.WhRepository = WhRepository;
            this.tunnelRepository = tunnelRepository;
            UserCompanyId = AbpSession.GetCompanyId();
            LogContext = DynamicDbContext.GetInstance(string.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
                CompanyId = UserCompanyId,
                OptPath = "XMX.WMS.RowInfo.RowInfoService.",
                OptModule = "库位初始化"
            };
        }

        /// <summary>
        /// 按照条件查询
        /// </summary>
        /// <param name="input"></param>
        /// <returns>分页数据列表</returns>
        [AbpAuthorize(PermissionNames.SlotInit_Get)]
        protected override IQueryable<RowInfo> CreateFilteredQuery(RowInfoPagedRequest input)
        {
            return Repository.GetAllIncluding(x => x.Warehouse, x => x.Row)
                   .WhereIf(AbpSession.UserId != 1, x => x.row_company_id == UserCompanyId)
                   .WhereIf(input.row_warehouse_id != null && input.row_warehouse_id != Guid.Empty, x => x.row_warehouse_id == input.row_warehouse_id)
                   .WhereIf(input.row_type != 0, x => x.row_type == input.row_type);
        }

        /// <summary>
        /// 重写排序方式
        /// </summary>
        /// <param name="query"></param>
        /// <param name="input"></param>
        /// <returns></returns>
        protected override IQueryable<RowInfo> ApplySorting(IQueryable<RowInfo> query, RowInfoPagedRequest input)
        {
            return query.OrderBy(r => r.row_order);
        }

        /// <summary>
        /// 新增保存
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.SlotInit_Add)]
        public override async Task<RowInfoDto> Create(RowInfoCreatedDto input)
        {
            input.row_company_id = UserCompanyId;
            RowInfoDto dto = await base.Create(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

       /// <summary>
       /// 更新
       /// </summary>
       /// <param name="input"></param>
       /// <returns></returns>
        public override async Task<RowInfoDto> Update(RowInfoUpdatedDto input)
        {
            RowInfo oldEntity = Repository.Get(input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            RowInfoDto dto = await base.Update(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Update", WMSOptLogInfo.WMSOptLogInfo.UPDATE, oldval, JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            return dto;
        }

        /// <summary>
        /// 删除
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.SlotInit_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, UserCompanyId, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
            LogContext.WMSOptLogInfo.Add(logInfoEntity);
            LogContext.SaveChanges();
            await Repository.DeleteAsync(x => x.Id == input.Id);
        }

        /// <summary>
        /// 通过类型以及仓库，查询已有的排位号
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public List<object> GetRowInfoByType(RowInfoPagedRequest input)
        {
            var query = Repository.GetAll()
                            .WhereIf(input.row_warehouse_id != Guid.Empty, x => x.row_warehouse_id == input.row_warehouse_id)
                            .WhereIf(input.row_type != 0, x => x.row_type == input.row_type)
                            .Select(x => new  { x.row_no })
                            .ToList();
            List<object> list = new List<object>();
            foreach (object obj in query)
                list.Add(obj);
            return list;
        }

        /// <summary>
        /// 通过仓库id获取对应库位排的排序
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public List<object> GetRowOrders(RowInfoPagedRequest input)
        {
            var query = Repository.GetAll()
                           .WhereIf(input.row_warehouse_id != Guid.Empty, x => x.row_warehouse_id == input.row_warehouse_id)
                           .Select(x => new { x.row_order }).ToList();
            List<object> list = new List<object>();
            foreach (object obj in query)
                list.Add(obj);
            return list;

        }

        /// <summary>
        /// 获取库位层
        /// </summary>
        /// <param name="warehouse_id"></param>
        /// <returns></returns>
        public int GetLayerCount(Guid warehouse_id)
        {
            var listlaycount = Repository.GetAll().Where(x => x.row_warehouse_id == warehouse_id)
                                              .Where(x => x.row_type == RowType.库位)
                                              .OrderByDescending(x => x.row_end_layer).ToList();
            return listlaycount.Count > 0 ? listlaycount[0].row_end_layer : throw new UserFriendlyException("该仓库尚未初始化库位！"); ;
        }

        /// <summary>
        /// 获取库位排
        /// </summary>
        /// <param name="warehouse_id"></param>
        /// <returns></returns>
        public List<RowInfo> GetRowCount(Guid warehouse_id)
        {
            return Repository.GetAll().Where(x => x.row_warehouse_id == warehouse_id)
                                               .Where(x => x.row_type == RowType.库位)
                                               .OrderBy(x => x.row_no).ToList();
        }

        /// <summary>
        /// 根据当前排位号查询附近（前后）库位排位号
        /// </summary>
        /// <param name="warehouse_id"></param>
        /// <returns></returns>
        public List<RowInfo> GetRowInfoByRowNo(Guid warehouse_id)
        {
            List<RowInfo> rowInfos = Repository.GetAll()
                                        .Where(x => x.row_warehouse_id == warehouse_id)
                                        .Where(x => x.row_inout_type == InOutType.外侧)
                                        .OrderBy(x => x.row_no)
                                        .ToList();
            return rowInfos;
        }

        /// <summary>
        /// 根据当前库位排生成库位
        /// </summary>
        /// <param name="id"></param>
        public int GetGenerateSlot(Guid id)
        {
            int count = -1;
            RowInfo rowInfo = Repository.Get(id);
            if (rowInfo == null)
                throw new Exception("找不到对应的库位排信息");
            //获取仓库信息
            WarehouseInfo.WarehouseInfo warehouseInfo = WhRepository.Get(rowInfo.row_warehouse_id.Value);
            if (warehouseInfo == null)
                throw new Exception("库位排对应仓库不存在");
            //根据RowType生成对应的库位或巷道
            if (rowInfo.row_type == RowType.巷道)
            {
                count = CreateTunnel(rowInfo, warehouseInfo);
            }
            else
            {
                List<SlotInfo.SlotInfo> slotList = null;
                //查看是否有外侧库位
                if (rowInfo.row_inout_type == InOutType.内侧)
                {
                    RowInfo outRow = Repository.Get(rowInfo.row_out_id.Value);
                    if (outRow == null || outRow.row_status == RowStatus.未初始化)
                        throw new Exception("需要先初始化外侧库位");
                    //找到外侧排的库位
                    slotList = SlotRepository.GetAll().Where(x => x.slot_row_id == outRow.Id).ToList();
                    if (slotList == null || slotList.Count == 0)
                        throw new Exception("未找到外侧库位信息");
                }
                count = CreateSlot(rowInfo, warehouseInfo, slotList);
            }
            rowInfo.row_status = RowStatus.已初始化;
            Repository.Update(rowInfo);
            return count;
        }

        /// <summary>
        /// 初始化巷道
        /// </summary>
        /// <param name="rowInfo"></param>
        /// <param name="warehouseInfo"></param>
        /// <returns></returns>
        private int CreateTunnel(RowInfo rowInfo, WarehouseInfo.WarehouseInfo warehouseInfo)
        {
            TunnelInfo.TunnelInfo tunnel = new TunnelInfo.TunnelInfo();
            tunnel.tunnel_name = rowInfo.row_name;
            tunnel.tunnel_in_state = LockType.未锁定;
            tunnel.tunnel_out_state = LockType.未锁定;
            tunnel.tunnel_is_enable = WMSIsEnabled.启用;
            tunnel.tunnel_company_id = warehouseInfo.warehouse_company_id;
            tunnel.slot_row_id = rowInfo.Id;
            tunnelRepository.Insert(tunnel);
            return 1;
        }

        /// <summary>
        /// 初始化库位
        /// </summary>
        /// <param name="rowInfo"></param>
        /// <param name="warehouseInfo"></param>
        /// <returns></returns>
        private int CreateSlot(RowInfo rowInfo, WarehouseInfo.WarehouseInfo warehouseInfo, List<SlotInfo.SlotInfo> slotList)
        {
            int row_no = rowInfo.row_no;
            int start_layer = rowInfo.row_start_layer;
            int end_layer = rowInfo.row_end_layer;
            int start_column = rowInfo.row_start_column;
            int end_column = rowInfo.row_end_column;
            for (var i = start_layer; i < end_layer + 1; i++)
            {
                for (var j = start_column; j < end_column + 1; j++)
                {
                    SlotInfo.SlotInfo slot = new SlotInfo.SlotInfo();
                    slot.slot_column = j;
                    slot.slot_layer = i;
                    slot.slot_name = JoinSlotName(row_no, j, i, warehouseInfo.warehouse_slot_type);
                    slot.slot_code = JoinSlotCode(row_no, j, i, warehouseInfo.warehouse_slot_type);
                    slot.slot_row = row_no;
                    slot.slot_stock_status = SlotStock.空闲;
                    slot.slot_closed_status = SlotClosed.正常;
                    slot.slot_imp_status = SlotImp.正常;
                    slot.slot_exp_status = SlotExp.正常;
                    if (rowInfo.row_inout_type == InOutType.内侧)
                        try
                        {
                            //找到外侧库位
                            slot.slot_out_id = slotList.FirstOrDefault(x => x.slot_column == j && x.slot_layer == i).Id;
                        }
                        catch (Exception)
                        { 
                            //未找到外侧库位
                        }
                    slot.slot_row_id = rowInfo.Id;
                    slot.slot_company_id = warehouseInfo.warehouse_company_id;
                    slot.slot_warehouse_id = warehouseInfo.Id;
                    SlotRepository.Insert(slot);
                }
            }
            return end_layer * end_column;
        }

        /// <summary>
        /// 组装库位名
        /// </summary>
        /// <param name="row_no">排</param>
        /// <param name="column">列</param>
        /// <param name="layer">层</param>
        /// <returns></returns>
        private string JoinSlotName(int row_no, int column, int layer, SlotType slotType)
        {
            string slotName;
            switch (slotType)
            {
                case SlotType.层列排:
                    slotName = layer + "层" + column + "列" + row_no + "排" + "库位";
                    break;
                case SlotType.排列层:
                    slotName = row_no + "排" + column + "列" + layer + "层" + "库位";
                    break;
                default:
                    slotName = layer + "层" + column + "列" + row_no + "排" + "库位";
                    break;
            }
            return slotName;
        }

        /// <summary>
        /// 组装库位号
        /// </summary>
        /// <param name="row_no"></param>
        /// <param name="column"></param>
        /// <param name="layer"></param>
        /// <returns></returns>
        private string JoinSlotCode(int row_no, int column, int layer, SlotType slotType)
        {
            string row = Convert.ToString(row_no);
            string col = Convert.ToString(column);
            string lyer = Convert.ToString(layer);

            row = row.Length >= 2 ? row : ("0" + row);
            col = col.Length >= 2 ? col : ("0" + col);
            lyer = lyer.Length >= 2 ? lyer : ("0" + lyer);

            string slotCode;
            switch (slotType)
            {
                case SlotType.层列排:
                    slotCode = lyer + col + row;
                    break;
                case SlotType.排列层:
                    slotCode = row + col + lyer;
                    break;
                default:
                    slotCode = lyer + col + row;
                    break;
            }
            return slotCode;

        }

    }
}
