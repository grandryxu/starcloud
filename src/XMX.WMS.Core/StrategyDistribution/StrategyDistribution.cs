using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.StrategyDistribution
{
    /// <summary>
    /// 分配策略
    /// </summary>
    public class StrategyDistribution : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 策略名称
        /// </summary>
        public string distribution_name { get; set; }
        /// <summary>
        /// 入出顺序 1 先入先出 2 后进先出
        /// </summary>
        public OrderType distribution_order { get; set; }
        /// <summary>
        /// 优先级
        /// </summary>
        public int distribution_order_priority { get; set; }
        /// <summary>
        /// 筛选方案	1 整托盘 2 清仓
        /// </summary>
        public UnpackType distribution_unpack { get; set; }
        /// <summary>
        /// 优先级
        /// </summary>
        public int distribution_unpack_priority { get; set; }
        /// <summary>
        /// 是否先到期先出 1 是 0 否
        /// </summary>
        public FefoType distribution_fefo { get; set; }
        /// <summary>
        /// 优先级
        /// </summary>
        public int distribution_fefo_priority { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string distribution_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled distribution_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? distribution_company_id { get; set; }
        [ForeignKey("distribution_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        #endregion
    }
}
