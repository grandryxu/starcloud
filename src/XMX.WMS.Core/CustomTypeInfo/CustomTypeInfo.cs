using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.CustomTypeInfo
{
    /// <summary>
    /// 客户类别信息
    /// </summary>
    public class CustomTypeInfo : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 编号
        /// </summary>
        public string customtype_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string customtype_name { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string customtype_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled customtype_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid customtype_company_id { get; set; }
        [ForeignKey("customtype_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        #endregion
    }
}
