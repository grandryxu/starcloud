using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.InventoryInfo.Dto
{
    #region 查询参数传入dto
    public class InventoryInfoPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 单据条码
        /// </summary>
        public string inventory_bill_bar { get; set; }
        /// <summary>
        /// 大批号
        /// </summary>
        public string inventory_batch_no { get; set; }
        /// <summary>
        /// 小批号
        /// </summary>
        public string inventory_lots_no { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string goods_name { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string quality_name { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string warehouse_name { get; set; }
        /// <summary>
        /// 日期
        /// </summary>
        public string inventory_date_b { get; set; }
        /// <summary>
        /// 日期
        /// </summary>
        public string inventory_date_e { get; set; }
        /// <summary>
        /// 类型
        /// </summary>
        public BillType? bill_type { get; set; }
        /// <summary>
        /// 出库ID
        /// </summary>
        public Guid? head_id { get; set; }
        /// <summary>
        /// 盘点类型 1整库盘点 2动态盘点 3物资盘点
        /// </summary>
        public StockTaskingType? task_type { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public Guid? task_warehouse_id { get; set; }
        /// <summary>
        /// 物料信息
        /// </summary>
        public Guid? task_goods_id { get; set; }
    }
    #endregion

    #region 总库存查询参数传入dto
    public class TotalInventory : PagedResultRequestDto
    {
        /// <summary>
        /// 名称
        /// </summary>
        public string goods_name { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public Guid? warehouse_id { get; set; }
        /// <summary>
        /// 区域
        /// </summary>
        public Guid? area_id { get; set; }
        /// <summary>
        /// 日期
        /// </summary>
        public string inventory_date_b { get; set; }
        /// <summary>
        /// 日期
        /// </summary>
        public string inventory_date_e { get; set; }
    }
    #endregion

    #region 总库存详情查询参数传入dto
    public class TotalInventoryDetail
    {
        /// <summary>
        /// 编码
        /// </summary>
        public string inventory_good_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string inventory_good_name { get; set; }
        /// <summary>
        /// 单位
        /// </summary>
        public Guid? inventory_unit { get; set; }
    }
    #endregion

    #region 库位库存详情查询参数传入dto
    public class SlotInventoryDetail
    {
        /// <summary>
        /// 库位编码
        /// </summary>
        public Guid? slot_code { get; set; }
        /// <summary>
        /// 托盘码
        /// </summary>
        public string stock_code { get; set; }
        /// <summary>
        /// 仓库id
        /// </summary>
        public Guid? warehouseid { get; set; }
    }
    #endregion

    #region 库位库存查询参数传入dto
    public class SlotInventory : PagedResultRequestDto
    {
        /// <summary>
        /// 库位
        /// </summary>
        public string slot_code { get; set; }
        /// <summary>
        /// 托盘
        /// </summary>
        public string stock_code { get; set; }
        /// <summary>
        /// 日期
        /// </summary>
        public string inventory_date_b { get; set; }
        /// <summary>
        /// 日期
        /// </summary>
        public string inventory_date_e { get; set; }
    }
    #endregion

    #region 托盘库存查询参数传入dto
    public class StockInventory : PagedResultRequestDto
    {
        /// <summary>
        /// 库位
        /// </summary>
        public string slot_code { get; set; }
        /// <summary>
        /// 托盘
        /// </summary>
        public string stock_code { get; set; }
        /// <summary>
        /// 所在仓库
        /// </summary>
        public Guid? warehouse_id { get; set; }
        /// <summary>
        /// 库存状态
        /// </summary>
        public SlotStock? inventory_status { get; set; }
    }
    #endregion

    #region 托盘库存详情查询参数传入dto
    public class StockInventoryDetail
    {
        /// <summary>
        /// 库位编码
        /// </summary>
        public Guid? slot_code { get; set; }
        /// <summary>
        /// 托盘码
        /// </summary>
        public string stock_code { get; set; }
        /// <summary>
        /// 仓库id
        /// </summary>
        public Guid? warehouseid { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(InventoryInfo))]
    public class InventoryInfoCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 大批号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string inventory_batch_no { get; set; }
        /// <summary>
        /// 小批号
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string inventory_lots_no { get; set; }
        /// <summary>
        /// 生产日期
        /// </summary>
        public DateTime inventory_product_date { get; set; }
        /// <summary>
        /// 生产线
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string inventory_product_lineid { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string inventory_remark { get; set; }
        /// <summary>
        /// 单据条码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string inventory_bill_bar { get; set; }
        /// <summary>
        /// 失效日期
        /// </summary>
        public DateTime inventory_vaildate_date { get; set; }
        /// <summary>
        /// 复检日期
        /// </summary>
        public DateTime inventory_recheck_date { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        [Required]
        public decimal inventory_quantity { get; set; }
        /// <summary>
        /// 箱码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string inventory_box_code { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string inventory_stock_code { get; set; }
        /// <summary>
        /// 状态(1可用；2分配；3出库；4复核；5暂存；6回流；7冻结)
        /// </summary>
        [Required]
        public InventoryStatus inventory_status { get; set; }
        /// <summary>
        /// 是否抽检托盘 1 是 0 否
        /// </summary>
        public StockStatus inventory_stock_status { get; set; }
        /// <summary>
        /// 入库日期
        /// </summary>
        public DateTime inventory_date { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled inventory_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 公司
        /// </summary>
        public virtual Guid? inventory_company_id { get; set; }
        /// <summary>
        /// 物料代码
        /// </summary>
        public virtual Guid inventory_goods_id { get; set; }
        /// <summary>
        /// 质量状态
        /// </summary>
        public virtual Guid inventory_quality_status { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid inventory_warehouse_id { get; set; }
        /// <summary>
        /// 入库口号
        /// </summary>
        public virtual Guid? inventory_port_id { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid inventory_slot_code { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(InventoryInfo))]
    public class InventoryInfoUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 大批号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string inventory_batch_no { get; set; }
        /// <summary>
        /// 小批号
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string inventory_lots_no { get; set; }
        /// <summary>
        /// 生产日期
        /// </summary>
        public DateTime inventory_product_date { get; set; }
        /// <summary>
        /// 生产线
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string inventory_product_lineid { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string inventory_remark { get; set; }
        /// <summary>
        /// 单据条码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string inventory_bill_bar { get; set; }
        /// <summary>
        /// 失效日期
        /// </summary>
        public DateTime inventory_vaildate_date { get; set; }
        /// <summary>
        /// 复检日期
        /// </summary>
        public DateTime inventory_recheck_date { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        [Required]
        public decimal inventory_quantity { get; set; }
        /// <summary>
        /// 箱码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string inventory_box_code { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string inventory_stock_code { get; set; }
        /// <summary>
        /// 状态(1可用；2分配；3出库；4复核；5暂存；6回流；7冻结)
        /// </summary>
        [Required]
        public InventoryStatus inventory_status { get; set; }
        /// <summary>
        /// 是否抽检托盘 1 是 0 否
        /// </summary>
        public StockStatus inventory_stock_status { get; set; }
        /// <summary>
        /// 入库日期
        /// </summary>
        public DateTime inventory_date { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled inventory_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 公司
        /// </summary>
        public virtual Guid? inventory_company_id { get; set; }
        /// <summary>
        /// 物料代码
        /// </summary>
        public virtual Guid inventory_goods_id { get; set; }
        /// <summary>
        /// 质量状态
        /// </summary>
        public virtual Guid inventory_quality_status { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid inventory_warehouse_id { get; set; }
        /// <summary>
        /// 入库口号
        /// </summary>
        public virtual Guid? inventory_port_id { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid inventory_slot_code { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(InventoryInfo))]
    public class InventoryInfoDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 大批号
        /// </summary>
        public string inventory_batch_no { get; set; }
        /// <summary>
        /// 小批号
        /// </summary>
        public string inventory_lots_no { get; set; }
        /// <summary>
        /// 生产日期
        /// </summary>
        public DateTime inventory_product_date { get; set; }
        /// <summary>
        /// 生产线
        /// </summary>
        public string inventory_product_lineid { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string inventory_remark { get; set; }
        /// <summary>
        /// 单据条码
        /// </summary>
        public string inventory_bill_bar { get; set; }
        /// <summary>
        /// 失效日期
        /// </summary>
        public DateTime inventory_vaildate_date { get; set; }
        /// <summary>
        /// 复检日期
        /// </summary>
        public DateTime inventory_recheck_date { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal inventory_quantity { get; set; }
        /// <summary>
        /// 箱码
        /// </summary>
        public string inventory_box_code { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string inventory_stock_code { get; set; }
        /// <summary>
        /// 状态(1可用；2分配；3出库；4复核；5暂存；6回流；7冻结)
        /// </summary>
        public InventoryStatus inventory_status { get; set; }
        /// <summary>
        /// 是否抽检托盘 1 是 0 否
        /// </summary>
        public StockStatus inventory_stock_status { get; set; }
        /// <summary>
        /// 入库日期
        /// </summary>
        public DateTime inventory_date { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled inventory_is_enable { get; set; }
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 公司
        /// </summary>
        public virtual Guid? inventory_company_id { get; set; }
        [ForeignKey("inventory_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 物料代码
        /// </summary>
        public virtual Guid inventory_goods_id { get; set; }
        [ForeignKey("inventory_goods_id")]
        public virtual GoodsInfo.GoodsInfo Goods { get; set; }
        /// <summary>
        /// 质量状态
        /// </summary>
        public virtual Guid inventory_quality_status { get; set; }
        [ForeignKey("inventory_quality_status")]
        public virtual QualityInfo.QualityInfo Quality { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid inventory_warehouse_id { get; set; }
        [ForeignKey("inventory_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        /// <summary>
        /// 入库口号
        /// </summary>
        public virtual Guid? inventory_port_id { get; set; }
        [ForeignKey("inventory_port_id")]
        public virtual PortInfo.PortInfo Port { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid inventory_slot_code { get; set; }
        [ForeignKey("inventory_slot_code")]
        public virtual SlotInfo.SlotInfo Slot { get; set; }
        #endregion
    }
    #endregion

    #region 总库存输出dto
    public class TotalInventorydto
    {
        /// <summary>
        /// 编码
        /// </summary>
        public string inventory_good_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string inventory_good_name { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal inventory_total { get; set; }
        /// <summary>
        /// 单位
        /// </summary>
        public string inventory_unit { get; set; }
        /// <summary>
        /// 单位ID
        /// </summary>
        public Guid? unitid { get; set; }
        /// <summary>
        /// 质量状态
        /// </summary>
        public string inventory_quality { get; set; }
        /// <summary>
        /// 大批号
        /// </summary>
        public string inventory_batch_no { get; set; }
        /// <summary>
        /// 小批号
        /// </summary>
        public string inventory_lots_no { get; set; }
    }
    #endregion

    #region 总库存详情输出dto
    public class TotalInventoryDetailDto
    {
        /// <summary>
        /// 库位
        /// </summary>
        public string slot_code { get; set; }
        /// <summary>
        /// 托盘
        /// </summary>
        public string stock_code { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal number { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public string bill_code { get; set; }
        /// <summary>
        /// 入库时间
        /// </summary>
        public DateTime imptime { get; set; }
        /// <summary>
        /// 质量状态
        /// </summary>
        public string status { get; set; }
        /// <summary>
        /// 包装码
        /// </summary>
        public string package_code { get; set; }
    }
    #endregion

    #region 库位库存详情输出dto
    public class SlotInventoryDetailDto
    {
        /// <summary>
        /// 物料编码
        /// </summary>
        public string good_code { get; set; }
        /// <summary>
        /// 物料名称
        /// </summary>
        public string good_name { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal number { get; set; }
        /// <summary>
        /// 批次号
        /// </summary>
        public string batch_no { get; set; }
        /// <summary>
        /// 入库时间
        /// </summary>
        public DateTime imptime { get; set; }
        /// <summary>
        /// 质量状态
        /// </summary>
        public string status { get; set; }
        /// <summary>
        /// 包装码
        /// </summary>
        public string package_code { get; set; }
        /// <summary>
        /// 单位
        /// </summary>
        public string unit { get; set; }
    }
    #endregion

    #region 库位库存输出dto
    public class SlotInventorydto
    {
        /// <summary>
        /// 库位编码
        /// </summary>
        public string slot_code { get; set; }
        /// <summary>
        /// 库位ID
        /// </summary>
        public Guid? slotid { get; set; }
        /// <summary>
        /// 托盘条码
        /// </summary>
        public string stock_code { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal inventory_total { get; set; }
        /// <summary>
        /// 库位状态
        /// </summary>
        public InventoryStatus inventory_status { get; set; }
        /// <summary>
        /// 入库锁
        /// </summary>
        public SlotImp imp_lock { get; set; }
        /// <summary>
        /// 出库锁
        /// </summary>
        public SlotExp ex_lock { get; set; }
        /// <summary>
        /// 入库时间
        /// </summary>
        public DateTime imptime { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public Guid? warehouseid { get; set; }
    }
    #endregion

    #region 托盘库存输出dto
    public class StockInventoryDto
    {
        /// <summary>
        /// 库位编码
        /// </summary>
        public string slot_code { get; set; }
        /// <summary>
        /// 库位ID
        /// </summary>
        public Guid? slot_id { get; set; }
        /// <summary>
        /// 托盘条码
        /// </summary>
        public string stock_code { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal inventory_total { get; set; }
        /// <summary>
        /// 库存状态
        /// </summary>
        public InventoryStatus inventory_status { get; set; }
        /// <summary>
        /// 入库锁
        /// </summary>
        public SlotImp imp_lock { get; set; }
        /// <summary>
        /// 出库锁
        /// </summary>
        public SlotExp exp_lock { get; set; }
        /// <summary>
        /// 入库时间
        /// </summary>
        public DateTime imptime { get; set; }
        /// <summary>
        /// 仓库id
        /// </summary>
        public Guid? warehouseid { get; set; }
        /// <summary>
        /// 仓库名
        /// </summary>
        public string warehouse_name { get; set; }
    }
    #endregion

    #region 托盘库存详情输出dto
    public class StockInventoryDetailDto
    {
        /// <summary>
        /// 物料编码
        /// </summary>
        public string good_code { get; set; }
        /// <summary>
        /// 物料名称
        /// </summary>
        public string good_name { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal number { get; set; }
        /// <summary>
        /// 批次号
        /// </summary>
        public string bill_code { get; set; }

        /// <summary>
        /// 质量状态
        /// </summary>
        public string status { get; set; }
        /// <summary>
        /// 包装码
        /// </summary>
        public string package_code { get; set; }
        /// <summary>
        /// 单位
        /// </summary>
        public string unit { get; set; }
    }
    #endregion

    #region 库存统计dto
    public class InventoryStatistics
    {
        #region 属性
        /// <summary>
        /// 物料
        /// </summary>
        public Guid? goods_id { get; set; }
        /// <summary>
        /// 编码
        /// </summary>
        public string goods_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string goods_name { get; set; }
        /// <summary>
        /// 规格
        /// </summary>
        public string goods_standard { get; set; }
        /// <summary>
        /// 单位
        /// </summary>
        public string goods_unit { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public Guid? quality_id { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string quality_name { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal inventory_quantity { get; set; }
        /// <summary>
        /// 大批次
        /// </summary>
        public string inventory_batch_no { get; set; }
        /// <summary>
        /// 小批次
        /// </summary>
        public string inventory_lots_no { get; set; }
        #endregion

        /// <summary>
        /// 初始化
        /// </summary>
        /// <param name="goods_code"></param>
        /// <param name="goods_name"></param>
        /// <param name="goods_standard"></param>
        /// <param name="goods_unit"></param>
        /// <param name="quality_name"></param>
        /// <param name="inventory_quantity"></param>
        public InventoryStatistics(string goods_code, string goods_name, string goods_standard, string goods_unit, string quality_name, decimal inventory_quantity)
        {
            this.goods_code = goods_code;
            this.goods_name = goods_name;
            this.goods_standard = goods_standard;
            this.goods_unit = goods_unit;
            this.quality_name = quality_name;
            this.inventory_quantity = inventory_quantity;
        }

        /// <summary>
        /// 初始化
        /// </summary>
        /// <param name="goods_id"></param>
        /// <param name="goods_code"></param>
        /// <param name="goods_name"></param>
        /// <param name="goods_standard"></param>
        /// <param name="goods_unit"></param>
        /// <param name="quality_name"></param>
        /// <param name="inventory_quantity"></param>
        public InventoryStatistics(Guid? goods_id, string goods_code, string goods_name, string goods_standard, string goods_unit, Guid? quality_id, string quality_name, string inventory_batch_no, string inventory_lots_no, decimal inventory_quantity)
        {
            this.goods_id = goods_id;
            this.goods_code = goods_code;
            this.goods_name = goods_name;
            this.goods_standard = goods_standard;
            this.goods_unit = goods_unit;
            this.quality_id = quality_id;
            this.quality_name = quality_name;
            this.inventory_batch_no = inventory_batch_no;
            this.inventory_lots_no = inventory_lots_no;
            this.inventory_quantity = inventory_quantity;
            
        }
    }
    #endregion

    #region 出入库存报表dto
    public class InOutStatistics
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        public string goods_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string goods_name { get; set; }
        /// <summary>
        /// 日期
        /// </summary>
        public DateTime inOut_date { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public string inOut_batch { get; set; }
        /// <summary>
        /// 单据
        /// </summary>
        public string inOut_bar { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal inOut_quantity { get; set; }
        /// <summary>
        /// 单据类型
        /// </summary>
        public BillType bill_type { get; set; }
        #endregion

        /// <summary>
        /// 初始化
        /// </summary>
        /// <param name="goods_code"></param>
        /// <param name="goods_name"></param>
        /// <param name="inOut_date"></param>
        /// <param name="inOut_batch"></param>
        /// <param name="inOut_bar"></param>
        /// <param name="inOut_quantity"></param>
        /// <param name="bill_type"></param>
        public InOutStatistics(string goods_code, string goods_name, DateTime inOut_date, string inOut_batch, string inOut_bar, decimal inOut_quantity, BillType bill_type)
        {
            this.goods_code = goods_code;
            this.goods_name = goods_name;
            this.inOut_date = inOut_date;
            this.inOut_batch = inOut_batch;
            this.inOut_bar = inOut_bar;
            this.inOut_quantity = inOut_quantity;
            this.bill_type = bill_type;
        }
    }
    #endregion

    #region 查询inventory 输出批次，物料Dto
    public class BatchGoodsDto
    {
        #region 属性
        /// <summary>
        /// 物料ID
        /// </summary>
        public Guid goods_id { get; set; }
        /// <summary>
        /// 批次号
        /// </summary>
        public string inventory_batch_no { get; set; }
        /// <summary>
        /// 物料号
        /// </summary>
        public string goods_code { get; set; }
        /// <summary>
        /// 物料名
        /// </summary>
        public string goods_name { get; set; }
        /// <summary>
        /// 物料的原质量状态
        /// </summary>
        public string quality_status { get; set; }
        /// <summary>
        /// 质量状态对应的id
        /// </summary>
        public Guid? quality_status_id { get; set; }
        #endregion
    }
    #endregion
}
