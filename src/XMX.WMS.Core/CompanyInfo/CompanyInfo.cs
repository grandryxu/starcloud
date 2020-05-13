using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.CompanyInfo
{
    /// <summary>
    /// 公司
    /// </summary>
    public class CompanyInfo : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 公司名
        /// </summary>
        public string Name { get; set; }
        /// <summary>
        /// 公司名简称
        /// </summary>
        public string ShortName { get; set; }
        /// <summary>
        /// 负责人
        /// </summary>
        public string ManagerName { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string Remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled IsEnable { get; set; }
        /// <summary>
        /// 地址信息
        /// </summary>
        public string Address { get; set; }
        /// <summary>
        /// 地址明细信息
        /// </summary>
        public string AddressDetail { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 上级公司Id
        /// </summary>
        public virtual Guid? ParentId { get; set; }
        /// <summary>
        /// 上级公司
        /// </summary>
        [ForeignKey("ParentId")]
        public virtual CompanyInfo Parent { get; set; }
        #endregion
    }
}
