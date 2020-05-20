using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.EncodingRule.Dto
{
    #region 查询参数传入dto
    public class EncodingRulePagedRequest : PagedResultRequestDto
    {
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
        public DateType? code_date_type { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(EncodingRule))]
    public class EncodingRuleCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 编码编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)] 
        public string code_code { get; set; }
        /// <summary>
        /// 编码名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)] 
        public string code_name { get; set; }
        /// <summary>
        /// 前缀
        /// </summary>
        [StringLength(BaseVerification.column10)]
        public string code_prefix { get; set; }
        /// <summary>
        /// 日期类型 1无；2年月日；3年月日小时分钟秒
        /// </summary>
        [Required]
        public DateType code_date_type { get; set; }
        /// <summary>
        /// 后缀序列长度
        /// </summary>
        [Required]
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
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(EncodingRule))]
    public class EncodingRuleUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 编码编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string code_code { get; set; }
        /// <summary>
        /// 编码名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string code_name { get; set; }
        /// <summary>
        /// 前缀
        /// </summary>
        [StringLength(BaseVerification.column10)]
        public string code_prefix { get; set; }
        /// <summary>
        /// 日期类型 1无；2年月日；3年月日小时分钟秒
        /// </summary>
        [Required]
        public DateType code_date_type { get; set; }
        /// <summary>
        /// 后缀序列长度
        /// </summary>
        [Required]
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
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(EncodingRule))]
    public class EncodingRuleDto : EntityDto<Guid>
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
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
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
    #endregion
}
