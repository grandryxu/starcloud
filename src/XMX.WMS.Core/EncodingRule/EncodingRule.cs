using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.EncodingRule
{
    /// <summary>
    /// 编码规则
    /// </summary>
    public class EncodingRule : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 编码编码
        /// </summary>
        public string code_code { get; set; }
        /// <summary>
        /// 编码名称
        /// </summary>
        public string code_name { get; set; }
        /// <summary>
        /// 前缀
        /// </summary>
        public string code_prefix { get; set; }
        /// <summary>
        /// 日期类型 1无；2年月日；3年月日小时分钟秒
        /// </summary>
        public DateType code_date_type { get; set; }
        /// <summary>
        /// 后缀序列长度
        /// </summary>
        public int code_suffix_length { get; set; }
        /// <summary>
        /// 上次序列号
        /// </summary>
        public int code_record { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled code_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? code_company_id { get; set; }
        [ForeignKey("code_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        #endregion
    }
}
