using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.CustomInfo.Dto
{
    #region 查询参数传入dto
    public class CustomInfoPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 编码
        /// </summary>
        public string custom_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string custom_name { get; set; }
        /// <summary>
        /// 客户类型
        /// </summary>
        public virtual Guid? custom_type_id { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(CustomInfo))]
    public class CustomInfoCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string custom_code { get; set; }
        /// <summary>
        /// 外部系统编码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_fid { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string custom_name { get; set; }
        /// <summary>
        /// 简称
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_short_name { get; set; }
        /// <summary>
        /// 联系人
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_linkman { get; set; }
        /// <summary>
        /// 联系电话
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_phone { get; set; }
        /// <summary>
        /// 手机号码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_telephone { get; set; }
        /// <summary>
        /// 传真
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_fax { get; set; }
        /// <summary>
        /// 省
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_province { get; set; }
        /// <summary>
        /// 市
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_city { get; set; }
        /// <summary>
        /// 区
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_area { get; set; }
        /// <summary>
        /// 镇/街
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_town { get; set; }
        /// <summary>
        /// 具体地址
        /// </summary>
        [StringLength(BaseVerification.column100)]
        public string custom_address { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string custom_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled custom_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 客户类型
        /// </summary>
        public virtual Guid custom_type_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(CustomInfo))]
    public class CustomInfoUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string custom_code { get; set; }
        /// <summary>
        /// 外部系统编码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_fid { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string custom_name { get; set; }
        /// <summary>
        /// 简称
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_short_name { get; set; }
        /// <summary>
        /// 联系人
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_linkman { get; set; }
        /// <summary>
        /// 联系电话
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_phone { get; set; }
        /// <summary>
        /// 手机号码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_telephone { get; set; }
        /// <summary>
        /// 传真
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_fax { get; set; }
        /// <summary>
        /// 省
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_province { get; set; }
        /// <summary>
        /// 市
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_city { get; set; }
        /// <summary>
        /// 区
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_area { get; set; }
        /// <summary>
        /// 镇/街
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string custom_town { get; set; }
        /// <summary>
        /// 具体地址
        /// </summary>
        [StringLength(BaseVerification.column100)]
        public string custom_address { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string custom_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled custom_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 客户类型
        /// </summary>
        public virtual Guid custom_type_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(CustomInfo))]
    public class CustomInfoDto : EntityDto<Guid>
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
        /// 客户类型
        /// </summary>
        public virtual Guid custom_type_id { get; set; }
        [ForeignKey("custom_type_id")]
        public virtual CustomTypeInfo.CustomTypeInfo CustomType { get; set; }
        #endregion
    }
    #endregion
}
