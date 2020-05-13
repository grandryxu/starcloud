using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.ExportStock.Dto
{
    #region 查询参数传入dto
    public class ExportStockPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 批号
        /// </summary>
        public string expstock_batch_no { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(ExportStock))]
    public class ExportStockCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 批号
        /// </summary>
        [StringLength(BaseVerification.column50)] 
        public string expstock_batch_no { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string expstock_remark { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        [Required]
        public decimal expstock_quantity { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string expstock_stock_code { get; set; }
        /// <summary>
        /// 执行标志(1未执行；2执行中；3已完成；4已复核)
        /// </summary>
        [Required]
        public ExecuteFlag expstock_execute_flag { get; set; }
        /// <summary>
        /// 作废标志(1未作废；2已作废)
        /// </summary>
        [Required]
        public NousedFlag expstock_noused_flag { get; set; }
        /// <summary>
        /// 作废人
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string expstock_noused_uid { get; set; }
        /// <summary>
        /// 作废时间
        /// </summary>
        public DateTime expstock_noused_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled expstock_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 物料代码
        /// </summary>
        public virtual Guid expstock_goods_id { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid expstock_slot_code { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid expstock_warehouse_id { get; set; }
        /// <summary>
        /// 口号
        /// </summary>
        public virtual Guid? expstock_port_id { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? expstock_platform_id { get; set; }
        /// <summary>
        /// 任务
        /// </summary>
        public virtual Guid? expstock_task_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(ExportStock))]
    public class ExportStockUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 批号
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string expstock_batch_no { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string expstock_remark { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        [Required]
        public decimal expstock_quantity { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string expstock_stock_code { get; set; }
        /// <summary>
        /// 执行标志(1未执行；2执行中；3已完成；4已复核)
        /// </summary>
        [Required]
        public ExecuteFlag expstock_execute_flag { get; set; }
        /// <summary>
        /// 作废标志(1未作废；2已作废)
        /// </summary>
        [Required]
        public NousedFlag expstock_noused_flag { get; set; }
        /// <summary>
        /// 作废人
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string expstock_noused_uid { get; set; }
        /// <summary>
        /// 作废时间
        /// </summary>
        public DateTime expstock_noused_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled expstock_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 物料代码
        /// </summary>
        public virtual Guid expstock_goods_id { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid expstock_slot_code { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid expstock_warehouse_id { get; set; }
        /// <summary>
        /// 口号
        /// </summary>
        public virtual Guid? expstock_port_id { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? expstock_platform_id { get; set; }
        /// <summary>
        /// 任务
        /// </summary>
        public virtual Guid? expstock_task_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(ExportStock))]
    public class ExportStockDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 批号
        /// </summary>
        public string expstock_batch_no { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string expstock_remark { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal expstock_quantity { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string expstock_stock_code { get; set; }
        /// <summary>
        /// 执行标志(1未执行；2执行中；3已完成；4已复核)
        /// </summary>
        public ExecuteFlag expstock_execute_flag { get; set; }
        /// <summary>
        /// 作废标志(1未作废；2已作废)
        /// </summary>
        public NousedFlag expstock_noused_flag { get; set; }
        /// <summary>
        /// 作废人
        /// </summary>
        public string expstock_noused_uid { get; set; }
        /// <summary>
        /// 作废时间
        /// </summary>
        public DateTime expstock_noused_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled expstock_is_enable { get; set; }
        //// <summary>
        //// 创建时间
        //// </summary>
        public DateTime? CreationTime { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 物料代码
        /// </summary>
        public virtual Guid expstock_goods_id { get; set; }
        [ForeignKey("expstock_goods_id")]
        public virtual GoodsInfo.GoodsInfo Goods { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid expstock_slot_code { get; set; }
        [ForeignKey("expstock_slot_code")]
        public virtual SlotInfo.SlotInfo Slot { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid expstock_warehouse_id { get; set; }
        [ForeignKey("expstock_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        /// <summary>
        /// 口号
        /// </summary>
        public virtual Guid? expstock_port_id { get; set; }
        [ForeignKey("expstock_port_id")]
        public virtual PortInfo.PortInfo Port { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? expstock_platform_id { get; set; }
        [ForeignKey("expstock_platform_id")]
        public virtual PlatFormInfo.PlatFormInfo PlatForm { get; set; }
        /// <summary>
        /// 任务
        /// </summary>
        public virtual Guid? expstock_task_id { get; set; }
        [ForeignKey("expstock_task_id")]
        public virtual TaskMainInfo.TaskMainInfo Task { get; set; }
        #endregion
    }
    #endregion
}
