using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.StockTasking
{
    /// <summary>
    /// 盘点单
    /// </summary>
   public class StockTasking : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 编号
        /// </summary>
        public string task_code { get; set; }
        /// <summary>
        /// 盘点类型 1整库盘点 2动态盘点 3物资盘点
        /// </summary>
        public StockTaskingType task_type { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal task_count { get; set; }
        /// <summary>
        /// 盘点状态
        /// </summary>
        public StockTaskingState task_state { get; set; }
        /// <summary>
        /// 单据日期
        /// </summary>
        public DateTime? task_start_date { get; set; }
        /// <summary>
        /// 单据日期
        /// </summary>
        public DateTime? task_end_date { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string task_remark { get; set; }
        /// <summary>
        /// 最后盘点人ID
        /// </summary>
        public string task_operate_person { get; set; }
        /// <summary>
        /// 最后盘点时间
        /// </summary>
        public DateTime task_operate_time { get; set; }     
        #endregion

        #region 关联
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid task_warehouse_id { get; set; }
        [ForeignKey("task_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        /// <summary>
        /// 物料
        /// </summary>
        public virtual Guid? task_goods_id { get; set; }
        [ForeignKey("task_goods_id")]
        public virtual GoodsInfo.GoodsInfo Goods { get; set; }
        #endregion
    }
}
