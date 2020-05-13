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

namespace XMX.WMS.RowInfo
{
    [AbpAuthorize(PermissionNames.SlotInit)]
    public class RowInfoService : AsyncCrudAppService<RowInfo, RowInfoDto, Guid, RowInfoPagedRequest, RowInfoCreatedDto, RowInfoUpdatedDto>, IRowInfoService
    {
        private readonly IRepository<SlotInfo.SlotInfo, Guid> SlotRepository;
        private readonly IRepository<WarehouseInfo.WarehouseInfo, Guid> WhRepository;
        private readonly IRepository<TunnelInfo.TunnelInfo, Guid> tunnelRepository;

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

            LogContext = DynamicDbContext.GetInstance(String.Concat("WMSOptLogInfo", DateTime.Now.ToString("yyyyMM")));
            logInfoEntity = new WMSOptLogInfo.WMSOptLogInfo
            {
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
            /*  return Repository.GetAllIncluding(x => x.Warehouse, x => x.Row)
                  .WhereIf(!input.row_name.IsNullOrWhiteSpace(), x => x.row_name.Contains(input.row_name));*/
            return Repository.GetAllIncluding(x => x.Warehouse, x => x.Row)
           .WhereIf(!input.row_name.IsNullOrWhiteSpace(), x => x.row_name.Contains(input.row_name))
           .WhereIf(input.row_warehouse_id != null && input.row_warehouse_id != Guid.Empty, x => x.row_warehouse_id == input.row_warehouse_id)
           .WhereIf(input.row_type != 0, x => x.row_type == input.row_type);


        }

        /// <summary>
        /// 新增保存
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        [AbpAuthorize(PermissionNames.SlotInit_Add)]
        public override async Task<RowInfoDto> Create(RowInfoCreatedDto input)
        {

            RowInfoDto dto = await base.Create(input);
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Create", WMSOptLogInfo.WMSOptLogInfo.ADD, "", JsonConvert.SerializeObject(dto), WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
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
            RowInfo oldEntity = Repository.FirstOrDefault(x => x.Id == input.Id);
            string oldval = JsonConvert.SerializeObject(oldEntity);
            RowInfoDto dto = await base.Update(input);
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
        [AbpAuthorize(PermissionNames.SlotInit_Delete)]
        public override async Task Delete(EntityDto<Guid> input)
        {
            WMSOptLogInfoFactory.CreateWMSOptLogInfo(logInfoEntity, AbpSession.UserId.Value, "Delete", WMSOptLogInfo.WMSOptLogInfo.DELETE, input.Id.ToString(), "", WMSOptLogInfo.WMSOptLogInfo.SUCCESS);
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
            var rr = Repository.GetAllIncluding()
                .WhereIf(input.row_warehouse_id != Guid.Empty, x => x.row_warehouse_id == input.row_warehouse_id)
                .WhereIf(input.row_type != 0, x => x.row_type == input.row_type).Select(x => new  { x.row_no }) ;
            List<object> rows = new List<object>();
            foreach (object number in rr)
            {
                rows.Add(number);
            }
            return rows;
        }

        /// <summary>
        ///通过仓库id获取对应库位排的排序
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public List<object> GetRowOrders(RowInfoPagedRequest input)
        {
            var cc = Repository.GetAllIncluding()
               .WhereIf(input.row_warehouse_id != Guid.Empty, x => x.row_warehouse_id == input.row_warehouse_id)
               .Select(x => new { x.row_order }).ToList();
            List<object> orders = new List<object>();
            foreach (object order in cc)
            {
                orders.Add(order);
            }
            return orders;

        }

        /// <summary>
        /// 根据当前排位号查询附近（前后）库位排位号
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public List<RowInfo> GetRowInfoByRowNo(RowInfoPagedRequest input)
        {
            int pre = 0;
            int next = 0;
            if (input.row_no>1)
            {
                pre = input.row_no - 1;
                next = input.row_no + 1;
            }

            List<RowInfo> rowInfos = Repository.GetAllIncluding()
                .WhereIf(input.row_warehouse_id != Guid.Empty, x => x.row_warehouse_id == input.row_warehouse_id)
                .WhereIf(input.row_no == 1, x => x.row_no == 2)
                .WhereIf(input.row_no > 1, x => x.row_no.IsIn(new int[] { pre, next })).ToList();
            return rowInfos;
        }

        /// <summary>
        /// 获取所有库位排信息，转成特有对象返回。
        /// </summary>
        /// <param name="input"></param>
        /// <returns></returns>
        public PagedResultDto<object> GetAllRowInfoQuery(RowInfoPagedRequest input)
        {
            
            var results = Repository.GetAllIncluding(x => x.Warehouse, x => x.Row)
          .WhereIf(!input.row_name.IsNullOrWhiteSpace(), x => x.row_name.Contains(input.row_name))
          .WhereIf(input.row_warehouse_id != null && input.row_warehouse_id != Guid.Empty, x => x.row_warehouse_id == input.row_warehouse_id)
          .WhereIf(input.row_type != 0, x => x.row_type == input.row_type).OrderByDescending(x =>x.CreationTime);
            var totalCount = results.Count();
            var objs=results.Skip(input.SkipCount).Take(input.MaxResultCount).Select(x => new
               {
                   id=x.Id,
                   row_type = x.row_type,
                   row_name = x.row_name,
                   row_no = (x.row_type==RowType.库位?x.row_no:0),
                   row_tunnel_no= (x.row_type == RowType.巷道 ? x.row_no : 0),
                   Row = x.Row,
                   Warehouse = x.Warehouse,
                   row_start_layer = x.row_start_layer,
                   row_end_layer = x.row_end_layer,
                   row_start_column = x.row_start_column,
                   row_is_enable=x.row_is_enable,
                   row_order=x.row_order,
                   row_end_column = x.row_end_column,
                   row_inout_type = (x.row_inout_type == InOutType.内侧 ? "内测" : (x.row_inout_type == InOutType.外侧 ? "外侧" : (x.row_inout_type == InOutType.单排 ? "单排" : "")))
               });

            List<object> rowInfos = new List<object>();
            foreach (object rowInfo in objs)
            {
                rowInfos.Add(rowInfo);
            }

            /*return rowInfos;*/
            return new PagedResultDto<object>(totalCount, rowInfos);
        }
        /// <summary>
        /// 根据当前库位排生成库位
        /// </summary>
        /// <param name="id"></param>
        public int GetGenerateSlot(Guid id)
        {
            RowInfo rowInfo = Repository.Get(id);
            if (rowInfo == null)
                throw new Exception("找不到对应的库位排信息");
            //获取仓库信息
            WarehouseInfo.WarehouseInfo warehouseInfo = WhRepository.Get(rowInfo.row_warehouse_id);
            if (warehouseInfo == null)
                throw new Exception("库位排对应仓库不存在");
            //根据RowType生成对应的库位或巷道
            if (rowInfo.row_type==RowType.巷道)
            {
               return GenerateTunnel(rowInfo);
            }
            int row_no = rowInfo.row_no;
            int start_layer = Convert.ToInt32(rowInfo.row_start_layer);
            int end_layer = Convert.ToInt32(rowInfo.row_end_layer);
            int start_column = Convert.ToInt32(rowInfo.row_start_column);
            int end_column = Convert.ToInt32(rowInfo.row_end_column);
            List<SlotInfo.SlotInfo> slots = new List<SlotInfo.SlotInfo>();
            for (var i = start_layer; i < end_layer + 1; i++)
            {
                for (var j = start_column; j < end_column + 1; j++)
                {
                    SlotInfo.SlotInfo slot = new SlotInfo.SlotInfo();
                    slot.slot_column = j;
                    slot.slot_layer = i;
                    slot.slot_name = JoinSlotName(row_no, j, i,warehouseInfo.warehouse_slot_type);
                    slot.slot_code = JoinSlotCode(row_no, j, i, warehouseInfo.warehouse_slot_type);
                    slot.slot_row = row_no;
                    slot.slot_stock_status = SlotStock.空闲;
                    slot.slot_closed_status = SlotClosed.正常;
                    slot.slot_imp_status = SlotImp.正常;
                    slot.slot_exp_status = SlotExp.正常;
                    slot.slot_row_id = rowInfo.Id;
                    slots.Add(slot);

                }
            }
            List<SlotInfo.SlotInfo> slotlist = new List<SlotInfo.SlotInfo>();
            try
            {
                slots.ForEach(item => {
                    slotlist.Add(this.SlotRepository.Insert(item));
                });
                //库位生成后，将库位排设为禁用，即不可以再生成库位（先用禁用/启用这个字段来控制）
                rowInfo.row_is_enable = WMSIsEnabled.禁用;
                Repository.Update(rowInfo);
            }
            catch (Exception e)
            {

                return -1;
            }
            return slotlist.Count;


        }
        /// <summary>
        /// 组装库位名
        /// </summary>
        /// <param name="row_no">排</param>
        /// <param name="column">列</param>
        /// <param name="layer">层</param>
        /// <returns></returns>
        private string JoinSlotName(int row_no,int column,int layer,SlotType slotType)
        {
            string slotName = "";
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

            string slotCode = "";
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

        private int GenerateTunnel(RowInfo rowInfo)
        {
            TunnelInfo.TunnelInfo tunnelInfo = new TunnelInfo.TunnelInfo();
            tunnelInfo.tunnel_name = rowInfo.row_name;
            tunnelInfo.tunnel_in_state = LockType.未锁定;
            tunnelInfo.tunnel_out_state = LockType.未锁定;
            tunnelInfo.slot_row_id = rowInfo.Id;
           TunnelInfo.TunnelInfo info= this.tunnelRepository.Insert(tunnelInfo);
            if (info == null)
                return -1;
            rowInfo.row_is_enable = WMSIsEnabled.禁用;
            this.Repository.Update(rowInfo);
            return 1;
        }


        /// <summary>
        /// 获取库位层
        /// </summary>
        /// <param name="warehouse_id"></param>
        /// <returns></returns>
        public int GetLayerCount(Guid warehouse_id)
        {
             var listlaycount=Repository.GetAllIncluding().Where(x => x.row_warehouse_id == warehouse_id)
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
            return Repository.GetAllIncluding().Where(x => x.row_warehouse_id == warehouse_id)
                                               .Where(x => x.row_type == RowType.库位)
                                               .OrderBy(x => x.row_no).ToList();
        }

        /// <summary>
        /// 新增或更新时验证同一排是否存在已经占用的列和层。目前因为限制了同一排只能做一次初始化，所以此方法暂未使用。
        /// </summary>
        /// <param name="input"></param>
        private void verifyDuplicateLayerColumn(RowInfoCreatedDto input)
        {
            //找出当前排的所有初始化RowInfo,并取得已经占用的列和层，与当前列和层进行判断。
            //已经占用的列和层后期有条件可以放入缓存，保存时候直接从缓存中取出与当前列和层比对判断。
            var infos = Repository.GetAll().WhereIf(input.row_type != 0, x => x.row_type == input.row_type)
                 .WhereIf(input.row_no != 0, x => x.row_no == input.row_no)
                 .WhereIf(input.row_inout_type != 0, x => x.row_inout_type == input.row_inout_type).ToList();
            int[] exist_columns = { };
            int[] exist_layers = { };
            infos.ForEach(item => {
                int startlayer = Convert.ToInt32(item.row_start_layer);
                int endlayer = item.row_end_layer;
                int startColumn = item.row_start_column;
                int endColumn = item.row_end_column;
                List<int> item_layer_list = new List<int>();
                List<int> item_column_list = new List<int>();
                for (int a = startlayer; a < endlayer + 1; a++)
                {
                    item_layer_list.Add(a);
                }
                for (int a = startColumn; a < endColumn + 1; a++)
                {
                    item_column_list.Add(a);
                }
                exist_columns = exist_columns.Union(item_column_list.ToArray()).ToList().ToArray();
                exist_layers = exist_layers.Union(item_layer_list.ToArray()).ToList().ToArray();
            });

            int current_startlayer = Convert.ToInt32(input.row_start_layer);
            int current_endlayer = input.row_end_layer;
            int current_startcolumn = input.row_start_column;
            int current_endcolumn = input.row_end_column;
            List<int> current_layer_list = new List<int>();
            List<int> current_column_list = new List<int>();
            for (int a = current_startlayer; a < current_endlayer + 1; a++)
            {
                current_layer_list.Add(a);
            }
            for (int a = current_startcolumn; a < current_endcolumn + 1; a++)
            {
                current_column_list.Add(a);
            }
            // 判断已占用列和层与当前列和层是否有交集
            var intersect_layers = exist_layers.Intersect(current_layer_list.ToArray()).ToList();
            var intersect_columns = exist_columns.Intersect(current_column_list.ToArray()).ToList();
            string msg = "层或列重复 ";
            if (intersect_layers.Count > 0)
            {
                msg=String.Concat(msg, "重复层：");
                intersect_layers.ForEach(number => {
                    msg = String.Concat(msg, number + " ");
                });
            }
            if (intersect_columns.Count > 0)
            {
                msg = String.Concat(msg, "重复列:");
                intersect_columns.ForEach(number => {
                    msg = String.Concat(msg, number + " ");
                });
            }
            if (msg.IndexOf("重复层") > 0 || msg.IndexOf("重复列") > 0)
                throw new UserFriendlyException(msg);
        }
    }
}
