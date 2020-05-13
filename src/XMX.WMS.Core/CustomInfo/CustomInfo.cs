using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.CustomInfo
{
    /// <summary>
    /// 客户信息
    /// </summary>
    public class CustomInfo : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        public string custom_code { get; set; }
        /// <summary>
        /// 外部系统编码
        /// </summary>
        public string custom_fid { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string custom_name { get; set; }
        /// <summary>
        /// 简称
        /// </summary>
        public string custom_short_name { get; set; }
        /// <summary>
        /// 联系人
        /// </summary>
        public string custom_linkman { get; set; }
        /// <summary>
        /// 联系电话
        /// </summary>
        public string custom_phone { get; set; }
        /// <summary>
        /// 手机号码
        /// </summary>
        public string custom_telephone { get; set; }
        /// <summary>
        /// 传真
        /// </summary>
        public string custom_fax { get; set; }
        /// <summary>
        /// 省
        /// </summary>
        public string custom_province { get; set; }
        /// <summary>
        /// 市
        /// </summary>
        public string custom_city { get; set; }
        /// <summary>
        /// 区
        /// </summary>
        public string custom_area { get; set; }
        /// <summary>
        /// 镇/街
        /// </summary>
        public string custom_town { get; set; }
        /// <summary>
        /// 具体地址
        /// </summary>
        public string custom_address { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string custom_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled custom_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? custom_company_id { get; set; }
        [ForeignKey("custom_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 客户类型
        /// </summary>
        public virtual Guid? custom_type_id { get; set; }
        [ForeignKey("custom_type_id")]
        public virtual CustomTypeInfo.CustomTypeInfo CustomType { get; set; }
        #endregion
    }
}
