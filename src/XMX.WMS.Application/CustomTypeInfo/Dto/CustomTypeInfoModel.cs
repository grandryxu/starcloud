using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.CustomTypeInfo.Dto
{
    #region 查询参数传入dto
    public class CustomTypeInfoPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 编号
        /// </summary>
        public string customtype_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string customtype_name { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(CustomTypeInfo))]
    public class CustomTypeInfoCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 编号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string customtype_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string customtype_name { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string customtype_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        [Required]
        public WMSIsEnabled customtype_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? customtype_company_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(CustomTypeInfo))]
    public class CustomTypeInfoUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 编号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string customtype_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string customtype_name { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string customtype_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        [Required]
        public WMSIsEnabled customtype_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? customtype_company_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(CustomTypeInfo))]
    public class CustomTypeInfoDto : EntityDto<Guid>
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
        public virtual Guid? customtype_company_id { get; set; }
        [ForeignKey("customtype_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        #endregion
    }
    #endregion
}
