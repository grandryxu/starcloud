using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.UnitInfo
{
    /// <summary>
    /// 单位
    /// </summary>
    public class UnitInfo : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 名称
        /// </summary>
        public string unit_name { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled unit_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? unit_company_id { get; set; }
        [ForeignKey("unit_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        #endregion
    }
}
