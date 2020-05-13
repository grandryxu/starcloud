using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.StrategyDistribution.Dto
{
    #region 查询参数传入dto
    public class StrategyDistributionPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 策略名称
        /// </summary>
        public string distribution_name { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(StrategyDistribution))]
    public class StrategyDistributionCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 策略名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)] 
        public string distribution_name { get; set; }
        /// <summary>
        /// 入出顺序 1 先入先出 2 后进先出
        /// </summary>
        [Required]
        public OrderType distribution_order { get; set; }
        /// <summary>
        /// 优先级
        /// </summary>
        [Required]
        public int distribution_order_priority { get; set; }
        /// <summary>
        /// 筛选方案	1 整托盘 2 清仓
        /// </summary>
        [Required]
        public UnpackType distribution_unpack { get; set; }
        /// <summary>
        /// 优先级
        /// </summary>
        [Required]
        public int distribution_unpack_priority { get; set; }
        /// <summary>
        /// 先到期先出 1 是 0 否
        /// </summary>
        [Required]
        public FefoType distribution_fefo { get; set; }
        /// <summary>
        /// 优先级
        /// </summary>
        [Required]
        public int distribution_fefo_priority { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)] 
        public string distribution_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled distribution_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? distribution_company_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(StrategyDistribution))]
    public class StrategyDistributionUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 策略名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string distribution_name { get; set; }
        /// <summary>
        /// 入出顺序 1 先入先出 2 后进先出
        /// </summary>
        [Required]
        public OrderType distribution_order { get; set; }
        /// <summary>
        /// 优先级
        /// </summary>
        [Required]
        public int distribution_order_priority { get; set; }
        /// <summary>
        /// 筛选方案	1 整托盘 2 清仓
        /// </summary>
        [Required]
        public UnpackType distribution_unpack { get; set; }
        /// <summary>
        /// 优先级
        /// </summary>
        [Required]
        public int distribution_unpack_priority { get; set; }
        /// <summary>
        /// 先到期先出 1 是 0 否
        /// </summary>
        [Required]
        public FefoType distribution_fefo { get; set; }
        /// <summary>
        /// 优先级
        /// </summary>
        [Required]
        public int distribution_fefo_priority { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string distribution_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled distribution_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? distribution_company_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(StrategyDistribution))]
    public class StrategyDistributionDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 策略名称
        /// </summary>
        public string distribution_name { get; set; }
        /// <summary>
        /// 入出顺序 1 先入先出 2 后进先出
        /// </summary>
        public OrderType distribution_order { get; set; }
        /// <summary>
        /// 优先级
        /// </summary>
        public int distribution_order_priority { get; set; }
        /// <summary>
        /// 筛选方案	1 整托盘 2 清仓
        /// </summary>
        public UnpackType distribution_unpack { get; set; }
        /// <summary>
        /// 优先级
        /// </summary>
        public int distribution_unpack_priority { get; set; }
        /// <summary>
        /// 先到期先出 1 是 0 否
        /// </summary>
        public FefoType distribution_fefo { get; set; }
        /// <summary>
        /// 优先级
        /// </summary>
        public int distribution_fefo_priority { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string distribution_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled distribution_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? distribution_company_id { get; set; }
        [ForeignKey("distribution_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        #endregion
    }
    #endregion
}
