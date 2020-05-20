using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.AreaInfo.Dto
{
    #region 查询参数传入dto
    public class AreaInfoPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 名称
        /// </summary>
        public string area_name { get; set; }
        /// <summary>
        /// 库区编码
        /// </summary>
        public string area_code { get; set; }
        /// <summary>
        /// 仓库ID
        /// </summary>
        public virtual Guid? area_warehouse_id { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(AreaInfo))]
    public class AreaInfoCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)] 
        public string area_name { get; set; }
        /// <summary>
        /// 库区编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string area_code { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled area_is_enable { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string area_remark { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid area_warehouse_id { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? area_company_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(AreaInfo))]
    public class AreaInfoUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string area_name { get; set; }
        /// <summary>
        /// 库区编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string area_code { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled area_is_enable { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string area_remark { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? area_company_id { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid area_warehouse_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(AreaInfo))]
    public class AreaInfoDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 名称
        /// </summary>
        public string area_name { get; set; }
        /// <summary>
        /// 库区编码
        /// </summary>
        public string area_code { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled area_is_enable { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string area_remark { get; set; }
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? area_company_id { get; set; }
        [ForeignKey("area_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? area_warehouse_id { get; set; }
        [ForeignKey("area_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        #endregion
    }
    #endregion

    #region 区域下拉列表输出dto
    [AutoMapFrom(typeof(AreaInfo))]
    public class AreaListDto : EntityDto<Guid>
    {
        /// <summary>
        /// 名称
        /// </summary>
        public string area_name { get; set; }
    }
    #endregion
}
