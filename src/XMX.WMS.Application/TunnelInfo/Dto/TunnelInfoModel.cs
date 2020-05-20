using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.TunnelInfo.Dto
{
    #region 查询参数传入dto
    public class TunnelInfoPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 巷道名
        /// </summary>
        public string tunnel_name { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(TunnelInfo))]
    public class TunnelInfoCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 巷道名
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string tunnel_name { get; set; }
        /// <summary>
        /// 巷道入库锁定 1 锁定 2 未锁定
        /// </summary>
        [Required]
        public LockType tunnel_in_state { get; set; }
        /// <summary>
        /// 巷道出库锁定 1 锁定 2 未锁定
        /// </summary>
        [Required]
        public LockType tunnel_out_state { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled tunnel_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? tunnel_company_id { get; set; }
        /// <summary>
        /// 库位排
        /// </summary>
        public virtual Guid? slot_row_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(TunnelInfo))]
    public class TunnelInfoUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 巷道名
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string tunnel_name { get; set; }
        /// <summary>
        /// 巷道入库锁定 1 锁定 2 未锁定
        /// </summary>
        [Required]
        public LockType tunnel_in_state { get; set; }
        /// <summary>
        /// 巷道出库锁定 1 锁定 2 未锁定
        /// </summary>
        [Required]
        public LockType tunnel_out_state { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled tunnel_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? tunnel_company_id { get; set; }
        /// <summary>
        /// 库位排
        /// </summary>
        public virtual Guid? slot_row_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(TunnelInfo))]
    public class TunnelInfoDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 巷道名
        /// </summary>
        public string tunnel_name { get; set; }
        /// <summary>
        /// 巷道入库锁定 1 锁定 2 未锁定
        /// </summary>
        public LockType tunnel_in_state { get; set; }
        /// <summary>
        /// 巷道出库锁定 1 锁定 2 未锁定
        /// </summary>
        public LockType tunnel_out_state { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled tunnel_is_enable { get; set; }
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? tunnel_company_id { get; set; }
        [ForeignKey("tunnel_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 库位排
        /// </summary>
        public virtual Guid? slot_row_id { get; set; }
        [ForeignKey("slot_row_id")]
        public virtual RowInfo.RowInfo Row { get; set; }
        #endregion
    }
    #endregion
}
