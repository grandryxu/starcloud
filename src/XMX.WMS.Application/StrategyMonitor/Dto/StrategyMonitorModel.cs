using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.StrategyMonitor.Dto
{
    #region 查询参数传入dto
    public class StrategyMonitorPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 策略名称
        /// </summary>
        public string monitor_name { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(StrategyMonitor))]
    public class StrategyMonitorCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 策略名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string monitor_name { get; set; }
        /// <summary>
        /// 过期天数
        /// </summary>
        [Required]
        public int monitor_expired_days { get; set; }
        /// <summary>
        /// 最大库龄天数
        /// </summary>
        [Required]
        public int monitor_days_max { get; set; }
        /// <summary>
        /// 最大库存
        /// </summary>
        [Required]
        public int monitor_stock_max { get; set; }
        /// <summary>
        /// 最小库存
        /// </summary>
        [Required]
        public int monitor_stock_min { get; set; }
        /// <summary>
        /// 复检天数
        /// </summary>
        [Required]
        public int monitor_recheck_days { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)] 
        public string monitor_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled monitor_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? monitor_company_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(StrategyMonitor))]
    public class StrategyMonitorUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 策略名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string monitor_name { get; set; }
        /// <summary>
        /// 过期天数
        /// </summary>
        [Required]
        public int monitor_expired_days { get; set; }
        /// <summary>
        /// 最大库龄天数
        /// </summary>
        [Required]
        public int monitor_days_max { get; set; }
        /// <summary>
        /// 最大库存
        /// </summary>
        [Required]
        public int monitor_stock_max { get; set; }
        /// <summary>
        /// 最小库存
        /// </summary>
        [Required]
        public int monitor_stock_min { get; set; }
        /// <summary>
        /// 复检天数
        /// </summary>
        [Required]
        public int monitor_recheck_days { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string monitor_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled monitor_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? monitor_company_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(StrategyMonitor))]
    public class StrategyMonitorDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 策略名称
        /// </summary>
        public string monitor_name { get; set; }
        /// <summary>
        /// 过期天数
        /// </summary>
        public int monitor_expired_days { get; set; }
        /// <summary>
        /// 最大库龄天数
        /// </summary>
        public int monitor_days_max { get; set; }
        /// <summary>
        /// 最大库存
        /// </summary>
        public int monitor_stock_max { get; set; }
        /// <summary>
        /// 最小库存
        /// </summary>
        public int monitor_stock_min { get; set; }
        /// <summary>
        /// 复检天数
        /// </summary>
        public int monitor_recheck_days { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string monitor_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled monitor_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? monitor_company_id { get; set; }
        [ForeignKey("monitor_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        #endregion
    }
    #endregion
}
