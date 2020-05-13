using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.QualityInfo
{
    /// <summary>
    /// 物料质量状态
    /// </summary>
    public class QualityInfo : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 名称
        /// </summary>
        public string quality_name { get; set; }
        /// <summary>
        /// 展示色
        /// </summary>
        public string quality_color { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled quality_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? quality_company_id { get; set; }
        [ForeignKey("quality_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        #endregion
    }
}
