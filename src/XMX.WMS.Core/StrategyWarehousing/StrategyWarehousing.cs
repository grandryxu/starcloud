using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.StrategyWarehousing
{
    /// <summary>
    /// 上架策略
    /// </summary>
    public class StrategyWarehousing : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 策略名称
        /// </summary>
        public string warehousing_name { get; set; }
        /// <summary>
        /// 是否规避繁忙巷道 1 是 0 否
        /// </summary>
        public BuzyFlag warehousing_buzy { get; set; }
        /// <summary>
        /// 是否规避繁忙巷道 优先级
        /// </summary>
        public int warehousing_buzy_priority { get; set; }
        /// <summary>
        /// 按排选择顺序 1 从小到大 2 从大到小
        /// </summary>
        public SelecType warehousing_select { get; set; }
        /// <summary>
        /// 按排选择 优先级
        /// </summary>
        public int warehousing_select_priority { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string warehousing_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled warehousing_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 物料所属公司
        /// </summary>
        public virtual Guid? warehousing_company_id { get; set; }
        [ForeignKey("warehousing_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        #endregion
    }
}
