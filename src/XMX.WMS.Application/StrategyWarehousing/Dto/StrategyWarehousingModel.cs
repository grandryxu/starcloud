using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.StrategyWarehousing.Dto
{
    #region 查询参数传入dto
    public class StrategyWarehousingPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 策略名称
        /// </summary>
        public string warehousing_name { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(StrategyWarehousing))]
    public class StrategyWarehousingCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 策略名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)] 
        public string warehousing_name { get; set; }
        /// <summary>
        /// 是否规避繁忙巷道 1 是 0 否
        /// </summary>
        [Required]
        public BuzyFlag warehousing_buzy { get; set; }
        /// <summary>
        /// 是否规避繁忙巷道 优先级
        /// </summary>
        [Required]
        public int warehousing_buzy_priority { get; set; }
        /// <summary>
        /// 按排选择 1 从小到大 2 从大到小
        /// </summary>
        [Required]
        public SelecType warehousing_select { get; set; }
        /// <summary>
        /// 按排选择 优先级
        /// </summary>
        [Required]
        public int warehousing_select_priority { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)] 
        public string warehousing_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled warehousing_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 物料所属公司
        /// </summary>
        public virtual Guid? warehousing_company_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(StrategyWarehousing))]
    public class StrategyWarehousingUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 策略名称
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string warehousing_name { get; set; }
        /// <summary>
        /// 是否规避繁忙巷道 1 是 0 否
        /// </summary>
        [Required]
        public BuzyFlag warehousing_buzy { get; set; }
        /// <summary>
        /// 是否规避繁忙巷道 优先级
        /// </summary>
        [Required]
        public int warehousing_buzy_priority { get; set; }
        /// <summary>
        /// 按排选择 1 从小到大 2 从大到小
        /// </summary>
        [Required]
        public SelecType warehousing_select { get; set; }
        /// <summary>
        /// 按排选择 优先级
        /// </summary>
        [Required]
        public int warehousing_select_priority { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string warehousing_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled warehousing_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 物料所属公司
        /// </summary>
        public virtual Guid? warehousing_company_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(StrategyWarehousing))]
    public class StrategyWarehousingDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 策略名称
        /// </summary>
        public string warehousing_name { get; set; }
        /// <summary>
        /// 是否规避繁忙巷道 1 是 0 否
        /// </summary>
        public BuzyFlag warehousing_buzy { get; set; }
        /// <summary>
        /// 是否规避繁忙巷道 优先级
        /// </summary>
        public int warehousing_buzy_priority { get; set; }
        /// <summary>
        /// 按排选择 1 从小到大 2 从大到小
        /// </summary>
        public SelecType warehousing_select { get; set; }
        /// <summary>
        /// 按排选择 优先级
        /// </summary>
        public int warehousing_select_priority { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string warehousing_remark { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled warehousing_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 物料所属公司
        /// </summary>
        public virtual Guid? warehousing_company_id { get; set; }
        [ForeignKey("warehousing_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        #endregion
    }
    #endregion
}
