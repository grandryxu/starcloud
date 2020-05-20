using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.ImportStock.Dto
{
    #region 查询参数传入dto
    public class ImportStockPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 批号
        /// </summary>
        public string impstock_batch_no { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string impstock_stock_code { get; set; }
        /// <summary>
        /// 流水任务
        /// </summary>
        public virtual Guid? task_id { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(ImportStock))]
    public class ImportStockCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 批号
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string impstock_batch_no { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string impstock_remark { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        [Required]
        public int impstock_quantity { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column8)]
        public string impstock_stock_code { get; set; }
        /// <summary>
        /// 执行标志(1待执行；2执行中；3已完成)
        /// </summary>
        public ExecuteFlag impstock_execute_flag { get; set; }
        /// <summary>
        /// 作废标志(1正常；2作废)
        /// </summary>
        public NousedFlag impstock_noused_flag { get; set; }
        /// <summary>
        /// 作废人
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string impstock_noused_uid { get; set; }
        /// <summary>
        /// 作废时间
        /// </summary>
        public DateTime impstock_noused_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled impstock_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? impstock_company_id { get; set; }
        /// <summary>
        /// 物料
        /// </summary>
        public virtual Guid? impstock_goods_id { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? impstock_slot_code { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        [Required]
        public virtual Guid? impstock_warehouse_id { get; set; }
        /// <summary>
        /// 口号
        /// </summary>
        public virtual Guid? impstock_port_id { get; set; }
        /// <summary>
        /// 流水任务
        /// </summary>
        public virtual Guid? task_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(ImportStock))]
    public class ImportStockUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 批号
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string impstock_batch_no { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string impstock_remark { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        [Required]
        public int impstock_quantity { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string impstock_stock_code { get; set; }
        /// <summary>
        /// 执行标志(1待执行；2执行中；3已完成)
        /// </summary>
        public ExecuteFlag impstock_execute_flag { get; set; }
        /// <summary>
        /// 作废标志(1正常；2作废)
        /// </summary>
        public NousedFlag impstock_noused_flag { get; set; }
        /// <summary>
        /// 作废人
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string impstock_noused_uid { get; set; }
        /// <summary>
        /// 作废时间
        /// </summary>
        public DateTime impstock_noused_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled impstock_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? impstock_company_id { get; set; }
        /// <summary>
        /// 物料
        /// </summary>
        public virtual Guid? impstock_goods_id { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? impstock_slot_code { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        [Required]
        public virtual Guid? impstock_warehouse_id { get; set; }
        /// <summary>
        /// 口号
        /// </summary>
        public virtual Guid? impstock_port_id { get; set; }
        /// <summary>
        /// 流水任务
        /// </summary>
        public virtual Guid? task_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(ImportStock))]
    public class ImportStockDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 批号
        /// </summary>
        public string impstock_batch_no { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string impstock_remark { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public int impstock_quantity { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string impstock_stock_code { get; set; }
        /// <summary>
        /// 执行标志(1待执行；2执行中；3已完成)
        /// </summary>
        public ExecuteFlag impstock_execute_flag { get; set; }
        /// <summary>
        /// 作废标志(1正常；2作废)
        /// </summary>
        public NousedFlag impstock_noused_flag { get; set; }
        /// <summary>
        /// 作废人
        /// </summary>
        public string impstock_noused_uid { get; set; }
        /// <summary>
        /// 作废时间
        /// </summary>
        public DateTime impstock_noused_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled impstock_is_enable { get; set; }
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? impstock_company_id { get; set; }
        [ForeignKey("impstock_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 物料
        /// </summary>
        public virtual Guid? impstock_goods_id { get; set; }
        [ForeignKey("impstock_goods_id")]
        public virtual GoodsInfo.GoodsInfo Goods { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? impstock_slot_code { get; set; }
        [ForeignKey("impstock_slot_code")]
        public virtual SlotInfo.SlotInfo Slot { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid? impstock_warehouse_id { get; set; }
        [ForeignKey("impstock_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        /// <summary>
        /// 口号
        /// </summary>
        public virtual Guid? impstock_port_id { get; set; }
        [ForeignKey("impstock_port_id")]
        public virtual PortInfo.PortInfo Port { get; set; }
        /// <summary>
        /// 流水任务
        /// </summary>
        public virtual Guid? task_id { get; set; }
        [ForeignKey("task_id")]
        public virtual TaskMainInfo.TaskMainInfo Task { get; set; }
        #endregion
    }
    #endregion

    #region 查询是否存在重复托盘 输入参数
    public class CheckDuplicateDto
    {
        /// <summary>
        /// 仓库id
        /// </summary>
        public Guid? impstock_warehouse_id { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string impstock_stock_code { get; set; }
    }
    #endregion
}
