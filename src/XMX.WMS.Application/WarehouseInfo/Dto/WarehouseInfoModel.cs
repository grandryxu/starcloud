using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.WarehouseInfo.Dto
{
    #region 查询参数传入dto
    public class WarehouseInfoPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 名称
        /// </summary>
        public string warehouse_name { get; set; }
        /// <summary>
        /// 类型(1立库；2平库；3密集库)
        /// </summary>
        public WarehouseType? warehouse_type { get; set; }
        /// <summary>
        /// 编码
        /// </summary>
        public string warehouse_code { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(WarehouseInfo))]
    public class WarehouseInfoCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string warehouse_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string warehouse_name { get; set; }
        /// <summary>
        /// 仓库类型(1立库；2平库；3密集库)
        /// </summary>
        [Required]
        public WarehouseType warehouse_type { get; set; }
        /// <summary>
        /// 库位类型(1层列排；2排列层)
        /// </summary>
        [Required]
        public SlotType warehouse_slot_type { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled warehouse_is_enable { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string warehouse_remark { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid warehouse_company_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(WarehouseInfo))]
    public class WarehouseInfoUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string warehouse_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string warehouse_name { get; set; }
        /// <summary>
        /// 仓库类型(1立库；2平库；3密集库)
        /// </summary>
        [Required]
        public WarehouseType warehouse_type { get; set; }
        /// <summary>
        /// 库位类型(1层列排；2排列层)
        /// </summary>
        [Required]
        public SlotType warehouse_slot_type { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled warehouse_is_enable { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string warehouse_remark { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid warehouse_company_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(WarehouseInfo))]
    public class WarehouseInfoDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 编码
        /// </summary>
        public string warehouse_code { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        public string warehouse_name { get; set; }
        /// <summary>
        /// 仓库类型(1立库；2平库；3密集库)
        /// </summary>
        public WarehouseType warehouse_type { get; set; }
        /// <summary>
        /// 库位类型(1层列排；2排列层)
        /// </summary>
        public SlotType warehouse_slot_type { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled warehouse_is_enable { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string warehouse_remark { get; set; }
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid warehouse_company_id { get; set; }
        [ForeignKey("warehouse_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        #endregion
    }
    #endregion

    #region 仓库下拉列表输出dto
    [AutoMapFrom(typeof(WarehouseInfo))]
    public class WarehouseListDto : EntityDto<Guid>
    {     
        /// <summary>
        /// 名称
        /// </summary>
        public string warehouse_name { get; set; }
    }
    #endregion
}
