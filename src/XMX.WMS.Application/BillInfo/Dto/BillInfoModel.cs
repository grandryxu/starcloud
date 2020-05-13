using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.BillInfo.Dto
{
    #region 查询参数传入dto
    public class BillInfoPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 名称
        /// </summary>
        public string bill_name { get; set; }
        /// <summary>
        ///  类型(1入库；2出库)
        /// </summary>
        public BillType? bill_type { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(BillInfo))]
    public class BillInfoCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string bill_name { get; set; }
        /// <summary>
        ///  类型(1入库；2出库)
        /// </summary>
        [Required]
        public BillType bill_type { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string bill_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        [Required]
        public WMSIsEnabled bill_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid bill_company_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(BillInfo))]
    public class BillInfoUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string bill_name { get; set; }
        /// <summary>
        ///  类型(1入库；2出库)
        /// </summary>
        [Required]
        public BillType bill_type { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string bill_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        [Required]
        public WMSIsEnabled bill_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid bill_company_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(BillInfo))]
    public class BillInfoDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 名称
        /// </summary>
        public string bill_name { get; set; }
        /// <summary>
        ///  类型(1入库；2出库)
        /// </summary>
        public BillType bill_type { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string bill_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled bill_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid bill_company_id { get; set; }
        [ForeignKey("bill_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        #endregion
    }
    #endregion
}
