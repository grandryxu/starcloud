using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.QualityInfo.Dto
{
    #region 查询参数传入dto
    public class QualityInfoPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 名称
        /// </summary>
        public string quality_name { get; set; }
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? quality_company_id { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(QualityInfo))]
    public class QualityInfoCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string quality_name { get; set; }
        /// <summary>
        /// 展示色
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string quality_color { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        [Required]
        public WMSIsEnabled quality_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? quality_company_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(QualityInfo))]
    public class QualityInfoUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string quality_name { get; set; }
        /// <summary>
        /// 展示色
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string quality_color { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        [Required]
        public WMSIsEnabled quality_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? quality_company_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(QualityInfo))]
    public class QualityInfoDto : EntityDto<Guid>
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
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? quality_company_id { get; set; }
        /// <summary>
        /// 公司
        /// </summary>
        [ForeignKey("quality_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        #endregion
    }
    #endregion
}
