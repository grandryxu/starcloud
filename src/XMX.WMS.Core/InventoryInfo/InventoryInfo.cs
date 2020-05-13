using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.InventoryInfo
{
    /// <summary>
    /// 库存
    /// </summary>
    public class InventoryInfo : FullAuditedEntity<Guid>
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
        #endregion

        #region 关联
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
        /// 库位
        /// </summary>
        public virtual Guid inventory_slot_code { get; set; }
        [ForeignKey("inventory_slot_code")]
        public virtual SlotInfo.SlotInfo Slot { get; set; }
        #endregion
    }
}
