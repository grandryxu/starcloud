using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.SlotSize.Dto
{
    #region 查询参数传入dto
    public class SlotSizePagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 名称
        /// </summary>
        public string size_name { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(SlotSize))]
    public class SlotSizeCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string size_name { get; set; }
        /// <summary>
        /// 长度
        /// </summary>
        [Required]
        public decimal size_length { get; set; }
        /// <summary>
        /// 高度
        /// </summary>
        [Required]
        public decimal size_height { get; set; }
        /// <summary>
        /// 宽度
        /// </summary>
        [Required]
        public decimal size_width { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string size_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled size_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? size_company_id { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? size_warehouse_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(SlotSize))]
    public class SlotSizeUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string size_name { get; set; }
        /// <summary>
        /// 长度
        /// </summary>
        [Required]
        public decimal size_length { get; set; }
        /// <summary>
        /// 高度
        /// </summary>
        [Required]
        public decimal size_height { get; set; }
        /// <summary>
        /// 宽度
        /// </summary>
        [Required]
        public decimal size_width { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string size_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled size_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? size_company_id { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? size_warehouse_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(SlotSize))]
    public class SlotSizeDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 名称
        /// </summary>
        public string size_name { get; set; }
        /// <summary>
        /// 长度
        /// </summary>
        public decimal size_length { get; set; }
        /// <summary>
        /// 高度
        /// </summary>
        public decimal size_height { get; set; }
        /// <summary>
        /// 宽度
        /// </summary>
        public decimal size_width { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string size_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled size_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? size_company_id { get; set; }
        [ForeignKey("size_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 所属仓库
        /// </summary>
        public virtual Guid? size_warehouse_id { get; set; }
        [ForeignKey("size_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        #endregion
    }
    #endregion

    #region 库位容积大小列表输出dto
    [AutoMapFrom(typeof(SlotSize))]
    public class SlotSizeListDto : EntityDto<Guid>
    {
        /// <summary>
        /// 名称
        /// </summary>
        public string size_name { get; set; }
    }
    #endregion
}
