using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.StrategyMonitor
{
    /// <summary>
    /// 监控策略
    /// </summary>
    public class StrategyMonitor : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 策略名称
        /// </summary>
        public string monitor_name { get; set; }
        /// <summary>
        /// 过期天数
        /// </summary>
        public int monitor_expired_days { get; set; }
        /// <summary>
        /// 最大库龄天数
        /// </summary>
        public int monitor_days_max { get; set; }
        /// <summary>
        /// 最大库存
        /// </summary>
        public int monitor_stock_max { get; set; }
        /// <summary>
        /// 最小库存
        /// </summary>
        public int monitor_stock_min { get; set; }
        /// <summary>
        /// 复检天数
        /// </summary>
        public int monitor_recheck_days { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string monitor_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled monitor_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? monitor_company_id { get; set; }
        [ForeignKey("monitor_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        #endregion
    }
}
