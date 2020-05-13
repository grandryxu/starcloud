using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.PlatFormInfo.Dto
{
    #region 查询参数传入dto
    public class PlatFormInfoPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 编码
        /// </summary>
        public string platform_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string platform_name { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public Guid? warehouse_id { get; set; }
        /// <summary>
        /// 月台状态
        /// </summary>
        public PlatformState? platform_state { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(PlatFormInfo))]
    public class PlatFormInfoCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string platform_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string platform_name { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string platform_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled platform_is_enable { get; set; }
        /// <summary>
        /// 月台状态
        /// </summary>
        public PlatformState platform_state { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? platform_company_id { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? platform_warehouse_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(PlatFormInfo))]
    public class PlatFormInfoUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string platform_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string platform_name { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string platform_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled platform_is_enable { get; set; }
        /// <summary>
        /// 月台状态
        /// </summary>
        public PlatformState platform_state { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? platform_company_id { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? platform_warehouse_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(PlatFormInfo))]
    public class PlatFormInfoDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        public string platform_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string platform_name { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string platform_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled platform_is_enable { get; set; }
        /// <summary>
        /// 月台状态
        /// </summary>
        public PlatformState platform_state { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? platform_company_id { get; set; }
        [ForeignKey("platform_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? platform_warehouse_id { get; set; }
        [ForeignKey("platform_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        #endregion
    }
    #endregion
}
