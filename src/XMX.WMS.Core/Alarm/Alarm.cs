using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.Alarm
{
    /// <summary>
    /// 报警
    /// </summary>
    public class Alarm : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 报警名称
        /// </summary>
        public string alarm_name { get; set; }
        /// <summary>
        /// 报警值
        /// </summary>
        public int alarm_value { get; set; }
        /// <summary>
        /// 单据条码
        /// </summary>
        public string impbody_bill_bar { get; set; }
        /// <summary>
        /// 阈值
        /// </summary>
        public int thresholdz_value { get; set; }   
        /// <summary>
        /// 备注
        /// </summary>
        public string area_remark { get; set; }
        /// <summary>
        ///预警类型
        /// </summary>
        public WarningType warning_type { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 监控策略
        /// </summary>
        public virtual Guid? strategy_id { get; set; }
        [ForeignKey("strategy_id")]
        public virtual StrategyMonitor.StrategyMonitor StrategyMonitor { get; set; }
        /// <summary>
        /// 物料id
        /// </summary>
        public virtual Guid goods_id { get; set; }
        [ForeignKey("goods_id")]
        public virtual GoodsInfo.GoodsInfo Goods { get; set; }
        /// <summary>
        /// 库存id
        /// </summary>
        public virtual Guid? inventory_id { get; set; }
        [ForeignKey("inventory_id")]
        public virtual InventoryInfo.InventoryInfo Inventory { get; set; }
        
        #endregion
    }

}
