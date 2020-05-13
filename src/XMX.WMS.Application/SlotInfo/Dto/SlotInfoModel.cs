using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.SlotInfo.Dto
{
    #region 查询参数传入dto
    public class SlotInfoPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? slot_warehouse_id { get; set; }  
        /// <summary>
        /// 库区
        /// </summary>
        public virtual Guid? slot_area_id { get; set; }
        /// <summary>
        /// 库存状态 0 空闲 1 有库存 2 入库中 3 出库中
        /// </summary>
        public SlotStock? slot_stock_status { get; set; }
        /// <summary>
        /// 屏蔽状态 0 正常 1 屏蔽
        /// </summary>
        public SlotClosed? slot_closed_status { get; set; }
        /// <summary>
        /// 入库锁定 0 正常 1锁定
        /// </summary>
        public SlotImp? slot_imp_status { get; set; }
        /// <summary>
        /// 出库锁定 0 正常 1锁定
        /// </summary>
        public SlotExp? slot_exp_status { get; set; }
        /// <summary>
        /// 容积大小
        /// </summary>
        public virtual Guid? slot_size_level { get; set; }
        /// <summary>
        /// 层
        /// </summary>
        [Range(1,int.MaxValue)]
        public int? slot_layer0 { get; set; }
        [Range(1, int.MaxValue)]
        public int? slot_layer1 { get; set; }
        /// <summary>
        /// 列
        /// </summary>
         [Range(1, int.MaxValue)]
        public int? slot_column0 { get; set; }
        [Range(1, int.MaxValue)]
        public int? slot_column1 { get; set; }
        /// <summary>
        /// 排
        /// </summary>
        [Range(1, int.MaxValue)]
        public int? slot_row0{ get; set; }
        [Range(1, int.MaxValue)]
        public int? slot_row1 { get; set; }
    }
    #endregion

    #region 按排或层查询
    public class SlotInfoRequest
    {
        /// <summary>
        /// 所属仓库
        /// </summary>
        public Guid slot_warehouse_id { get; set; }
        /// <summary>
        /// 按层
        /// </summary>
        [Range(1, int.MaxValue)]
        public int? slot_layer { get; set; }
        /// <summary>
        /// 按排ID
        /// </summary>
        public Guid? slot_row_id { get; set; }
        /// <summary>
        /// 库位编码
        /// </summary>
        public string slot_code { get; set; }
    }
    #endregion

    #region 层排库位信息
    public class SlotInfoTable
    {
        /// <summary>
        /// 表头数据-列
        /// </summary>
        public List<SlotTH> clos { get; set; }
        /// <summary>
        /// 库位信息
        /// </summary>
        public SlotInfoGoods[,] slotArr { get; set; }
    }
    #endregion

    #region 表头信息
    public class SlotTH
    { 
        /// <summary>
        /// 列ID
        /// </summary>
        public string prop { get; set; }
        /// <summary>
        /// 列名
        /// </summary>
        public string label { get; set; }
        /// <summary>
        /// 类型
        /// </summary>
        public RowType? type { get; set; }
        /// <summary>
        /// 初始化列
        /// </summary>
        public SlotTH(string prop, string label, RowType type)
        {
            this.prop = prop;
            this.label = label;
            this.type = type;
        }
        /// <summary>
        /// 初始化排
        /// </summary>
        public SlotTH(string prop, string label)
        {
            this.prop = prop;
            this.label = label;
        }
    }
    #endregion

    #region 表头信息
    public class SlotInfoGoods
    {
        /// <summary>
        /// 库位信息
        /// </summary>
        public SlotInfo slot { get; set; }
        /// <summary>
        /// 库存信息
        /// </summary>
        public List<InventoryInfo.InventoryInfo> inventorys { get; set; }
        /// <summary>
        /// 初始化
        /// </summary>
        /// <param name="slot"></param>
        /// <param name="inventorys"></param>
        public SlotInfoGoods (SlotInfo slot, List<InventoryInfo.InventoryInfo> inventorys)
        {
            this.slot = slot;
            this.inventorys = inventorys;
        }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(SlotInfo))]
    public class SlotInfoCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 库位名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string slot_name { get; set; }
        /// <summary>
        /// 库位编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string slot_code { get; set; }
        /// <summary>
        /// 层
        /// </summary>
        [Required]
        public int slot_layer { get; set; }
        /// <summary>
        /// 列
        /// </summary>
        [Required]
        public int slot_column { get; set; }
        /// <summary>
        /// 排
        /// </summary>
        [Required]
        public int slot_row { get; set; }
        /// <summary>
        /// 库存状态 0 空闲 1 有库存 2 入库中 3 出库中
        /// </summary>
        [Required]
        public SlotStock slot_stock_status { get; set; }
        /// <summary>
        /// 屏蔽状态 0 正常 1 屏蔽
        /// </summary>
        [Required]
        public SlotClosed slot_closed_status { get; set; }
        /// <summary>
        /// 入库锁定 0 正常 1锁定
        /// </summary>
        [Required]
        public SlotImp slot_imp_status { get; set; }
        /// <summary>
        /// 出库锁定 0 正常 1锁定
        /// </summary>
        [Required]
        public SlotExp slot_exp_status { get; set; }
        /// <summary>
        /// 是否可移动 0 不可移动 1 可移动
        /// </summary>
        public SlotMoveable slot_moveable_status { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled slot_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? slot_company_id { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? slot_warehouse_id { get; set; }
        /// <summary>
        /// 库区
        /// </summary>
        public virtual Guid? slot_area_id { get; set; }
        /// <summary>
        /// 库位排
        /// </summary>
        public virtual Guid? slot_row_id { get; set; }
        /// <summary>
        /// 容积大小
        /// </summary>
        public virtual Guid? slot_size_level { get; set; }
        /// <summary>
        /// 库位对应的巷道ID
        /// </summary>
        public virtual Guid? slot_tunnel_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(SlotInfo))]
    public class SlotInfoUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 库位名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string slot_name { get; set; }
        /// <summary>
        /// 库位编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string slot_code { get; set; }
        /// <summary>
        /// 层
        /// </summary>
        [Required]
        public int slot_layer { get; set; }
        /// <summary>
        /// 列
        /// </summary>
        [Required]
        public int slot_column { get; set; }
        /// <summary>
        /// 排
        /// </summary>
        [Required]
        public int slot_row { get; set; }
        /// <summary>
        /// 库存状态 0 空闲 1 有库存 2 入库中 3 出库中
        /// </summary>
        [Required]
        public SlotStock slot_stock_status { get; set; }
        /// <summary>
        /// 屏蔽状态 0 正常 1 屏蔽
        /// </summary>
        [Required]
        public SlotClosed slot_closed_status { get; set; }
        /// <summary>
        /// 入库锁定 0 正常 1锁定
        /// </summary>
        [Required]
        public SlotImp slot_imp_status { get; set; }
        /// <summary>
        /// 出库锁定 0 正常 1锁定
        /// </summary>
        [Required]
        public SlotExp slot_exp_status { get; set; }
        /// <summary>
        /// 是否可移动 0 不可移动 1 可移动
        /// </summary>
        public SlotMoveable slot_moveable_status { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled slot_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? slot_company_id { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? slot_warehouse_id { get; set; }
        /// <summary>
        /// 库区
        /// </summary>
        public virtual Guid? slot_area_id { get; set; }
        /// <summary>
        /// 库位排
        /// </summary>
        public virtual Guid? slot_row_id { get; set; }
        /// <summary>
        /// 容积大小
        /// </summary>
        public virtual Guid? slot_size_level { get; set; }
        /// <summary>
        /// 库位对应的巷道ID
        /// </summary>
        public virtual Guid? slot_tunnel_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(SlotInfo))]
    public class SlotInfoDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 库位名称
        /// </summary>
        public string slot_name { get; set; }
        /// <summary>
        /// 库位编码
        /// </summary>
        public string slot_code { get; set; }
        /// <summary>
        /// 层
        /// </summary>
        public int slot_layer { get; set; }
        /// <summary>
        /// 列
        /// </summary>
        public int slot_column { get; set; }
        /// <summary>
        /// 排
        /// </summary>
        public int slot_row { get; set; }
        /// <summary>
        /// 库存状态 0 空闲 1 有库存 2 入库中 3 出库中
        /// </summary>
        public SlotStock slot_stock_status { get; set; }
        /// <summary>
        /// 屏蔽状态 0 正常 1 屏蔽
        /// </summary>
        public SlotClosed slot_closed_status { get; set; }
        /// <summary>
        /// 入库锁定 0 正常 1锁定
        /// </summary>
        public SlotImp slot_imp_status { get; set; }
        /// <summary>
        /// 出库锁定 0 正常 1锁定
        /// </summary>
        public SlotExp slot_exp_status { get; set; }
        /// <summary>
        /// 是否可移动 0 不可移动 1 可移动
        /// </summary>
        public SlotMoveable slot_moveable_status { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled slot_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? slot_company_id { get; set; }
        [ForeignKey("slot_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? slot_warehouse_id { get; set; }
        [ForeignKey("slot_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        /// <summary>
        /// 库区
        /// </summary>
        public virtual Guid? slot_area_id { get; set; }
        [ForeignKey("slot_area_id")]
        public virtual AreaInfo.AreaInfo Area { get; set; }
        /// <summary>
        /// 库位排
        /// </summary>
        public virtual Guid? slot_row_id { get; set; }
        [ForeignKey("slot_row_id")]
        public virtual RowInfo.RowInfo Row { get; set; }
        /// <summary>
        /// 容积大小
        /// </summary>
        public virtual Guid? slot_size_level { get; set; }
        [ForeignKey("slot_size_level")]
        public virtual SlotSize.SlotSize Size { get; set; }
        /// <summary>
        /// 库位对应的巷道ID
        /// </summary>
        public virtual Guid? slot_tunnel_id { get; set; }
        [ForeignKey("slot_tunnel_id")]
        public virtual TunnelInfo.TunnelInfo Tunnel { get; set; }
        #endregion
    }
    #endregion

    public class PercentDto
    {
        /// <summary>
        /// 空库位数
        /// </summary>
        public int empty_count { get; set; }
        /// <summary>
        /// 非空库位数
        /// </summary>
        public int not_empty_count { get; set; }
    }

    #region 仓库使用率
    public class UtilizationRateDto
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        public string warehouse_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string warehouse_name { get; set; }
        /// <summary>
        /// 仓库类型(1立库；2平库；3密集库)
        /// </summary>
        public WarehouseType warehouse_type { get; set; }
        /// <summary>
        /// 库存状态 0 空闲 1 有库存 2 入库中 3 出库中
        /// </summary>
        //public SlotStock slot_stock_status { get; set; }
        /// <summary>
        /// 空库位数
        /// </summary>
        public int empty_count { get; set; }
        /// <summary>
        /// 非空库位数
        /// </summary>
        public int not_empty_count { get; set; }
        #endregion

        public UtilizationRateDto(string warehouse_code, string warehouse_name, WarehouseType warehouse_type, int empty_count, int not_empty_count)
        {
            this.warehouse_code = warehouse_code;
            this.warehouse_name = warehouse_name;
            this.warehouse_type = warehouse_type;
            //this.slot_stock_status = slot_stock_status;
            this.empty_count = empty_count;
            this.not_empty_count = not_empty_count;
        }
    }
    #endregion
}
