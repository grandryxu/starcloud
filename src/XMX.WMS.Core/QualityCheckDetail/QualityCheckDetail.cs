using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.QualityCheckDetail
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-03-25 14:07:45
    /// 描 述：
    ///</summary>
    public class QualityCheckDetail : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 抽检单号
        /// </summary>
        public string check_bill_code { get; set; }
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
        /// 数量
        /// </summary>
        public decimal inventory_quantity { get; set; }
        /// <summary>
        /// 托盘上物料抽检量
        /// </summary>
        public decimal stock_check_quantity { get; set; }
        /// <summary>
        /// 箱码
        /// </summary>
        public string inventory_box_code { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string inventory_stock_code { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public string inventory_slot_code { get; set; }
        /// <summary>
        /// 状态(1可用；2分配；3出库；4复核；5暂存；6回流；7冻结)
        /// </summary>
        public InventoryStatus inventory_status { get; set; }
        /// <summary>
        /// 是否抽检托盘 1 是 0 否
        /// </summary>
        public StockStatus inventory_stock_status { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 关联QualityCheck
        /// </summary>
        public virtual Guid? quality_check_id { get; set; }
        [ForeignKey("quality_check_id")]
        public virtual QualityCheck.QualityCheck qualityCheck { get; set; }
        /// <summary>
        /// 物料代码
        /// </summary>
        public virtual Guid? inventory_goods_id { get; set; }
        [ForeignKey("inventory_goods_id")]
        public virtual GoodsInfo.GoodsInfo Goods { get; set; }
        #endregion
    }
}
