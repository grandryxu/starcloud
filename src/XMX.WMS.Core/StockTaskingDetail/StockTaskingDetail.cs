using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.StockTaskingDetail
{
    /// <summary>
    /// 盘点详情单
    /// </summary>
    public class StockTaskingDetail: FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 盘点类型（1初始化2已盘点）
        /// </summary>
        public StockTaskingDetailState task_state { get; set; }
        /// <summary>
        /// 库存数量
        /// </summary>
        public decimal task_count { get; set; }
        /// <summary>
        /// 盘盈数量
        /// </summary>
        public decimal task_acount { get; set; }
        /// <summary>
        /// 盘亏数量
        /// </summary>
        public decimal task_dcount { get; set; }
        /// <summary>
        /// 最后盘点人ID
        /// </summary>
        public string task_operate_person { get; set; }
        /// <summary>
        /// 最后盘点时间
        /// </summary>
        public DateTime task_operate_time { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string task_stock_code { get; set; }
        /// <summary>
        /// 批号
        /// </summary>
        public string task_batch_no { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属盘点单
        /// </summary>
        public virtual Guid stock_tasking_id { get; set; }
        [ForeignKey("stock_tasking_id")]
        public virtual StockTasking.StockTasking StockTasking { get; set; }
        /// <summary>
        /// 物料
        /// </summary>
        public virtual Guid task_goods_id { get; set; }
        [ForeignKey("task_goods_id")]
        public virtual GoodsInfo.GoodsInfo Goods { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid task_slot_id { get; set; }
        [ForeignKey("task_slot_id")]
        public virtual SlotInfo.SlotInfo Slot { get; set; }
        #endregion
    }
}
