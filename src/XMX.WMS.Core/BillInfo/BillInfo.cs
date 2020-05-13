using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.BillInfo
{
    /// <summary>
    /// 单据类型信息
    /// </summary>
    public class BillInfo : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 名称
        /// </summary>
        public string bill_name { get; set; }
        /// <summary>
        ///  类型(1入库；2出库)
        /// </summary>
        public BillType bill_type { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string bill_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled bill_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid bill_company_id { get; set; }
        [ForeignKey("bill_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        #endregion
    }
}
