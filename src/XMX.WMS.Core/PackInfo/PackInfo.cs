using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.PackInfo
{
    /// <summary>
    /// 垛形信息
    /// </summary>
    public class PackInfo : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        public string pack_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string pack_name { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string pack_remark { get; set; }
        /// <summary>
        /// 图片
        /// </summary>
        public string pack_picture { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled pack_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 物料所属公司
        /// </summary>
        public virtual Guid? pack_company_id { get; set; }
        [ForeignKey("pack_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        #endregion
    }
}
