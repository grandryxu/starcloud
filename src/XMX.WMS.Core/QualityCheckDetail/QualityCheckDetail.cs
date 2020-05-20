using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.QualityCheckDetail
{
    ///<summary>
    /// 版 本：
    /// 创建人：lunan
    /// 日 期：2020-05-18 14:07:45
    /// 描 述：抽检明细
    ///</summary>
    public class QualityCheckDetail : FullAuditedEntity<Guid>
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
        /// 箱码
        /// </summary>
        public string inventory_box_code { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal inventory_quantity { get; set; }
        /// <summary>
        /// 托盘上物料抽检量
        /// </summary>
        public decimal check_quantity { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string inventory_stock_code { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 关联QualityCheck
        /// </summary>
        public virtual Guid quality_check_id { get; set; }
        [ForeignKey("quality_check_id")]
        public virtual QualityCheck.QualityCheck qualityCheck { get; set; }
        /// <summary>
        /// 物料代码
        /// </summary>
        public virtual Guid? inventory_goods_id { get; set; }
        [ForeignKey("inventory_goods_id")]
        public virtual GoodsInfo.GoodsInfo Goods { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? inventory_slot_code { get; set; }
        [ForeignKey("inventory_slot_code")]
        public virtual SlotInfo.SlotInfo Slot { get; set; }
        #endregion
    }
}
