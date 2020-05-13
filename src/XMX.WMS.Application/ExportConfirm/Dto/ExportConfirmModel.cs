using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.ExportConfirm.Dto
{
    #region 查询参数传入dto
    public class ExportConfirmPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 单据条码
        /// </summary>
        public string confirm_bill_bar { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(ExportConfirm))]
    public class ExportConfirmCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 大批号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string confirm_batch_no { get; set; }
        /// <summary>
        /// 小批号
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string confirm_lots_no { get; set; }
        /// <summary>
        /// 生产日期
        /// </summary>
        public DateTime confirm_product_date { get; set; }
        /// <summary>
        /// 生产线
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string confirm_product_lineid { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string confirm_remark { get; set; }
        /// <summary>
        /// 单据条码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string confirm_bill_bar { get; set; }
        /// <summary>
        /// 失效日期
        /// </summary>
        public DateTime confirm_vaildate_date { get; set; }
        /// <summary>
        /// 复检日期
        /// </summary>
        public DateTime confirm_recheck_date { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        [Required]
        public decimal confirm_quantity { get; set; }
        /// <summary>
        /// 箱码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string confirm_box_code { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string confirm_stock_code { get; set; }
        /// <summary>
        /// 上传标志(1未上传；2已上传)
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string confirm_upload_flag { get; set; }
        /// <summary>
        /// 上传时间
        /// </summary>
        public DateTime confirm_upload_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled confirm_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? confirm_company_id { get; set; }
        /// <summary>
        /// 物料代码
        /// </summary>
        public virtual Guid? confirm_goods_id { get; set; }
        /// <summary>
        /// 质量状态
        /// </summary>
        public virtual Guid? confirm_quality_status { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? confirm_slot_code { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid? confirm_warehouse_id { get; set; }
        /// <summary>
        /// 口号
        /// </summary>
        public virtual Guid? confirm_port_id { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? confirm_platform_id { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(ExportConfirm))]
    public class ExportConfirmUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 大批号
        /// </summary>
        [Required]
        [StringLength(BaseVerification.column50)]
        public string confirm_batch_no { get; set; }
        /// <summary>
        /// 小批号
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string confirm_lots_no { get; set; }
        /// <summary>
        /// 生产日期
        /// </summary>
        public DateTime confirm_product_date { get; set; }
        /// <summary>
        /// 生产线
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string confirm_product_lineid { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        [StringLength(BaseVerification.column200)]
        public string confirm_remark { get; set; }
        /// <summary>
        /// 单据条码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string confirm_bill_bar { get; set; }
        /// <summary>
        /// 失效日期
        /// </summary>
        public DateTime confirm_vaildate_date { get; set; }
        /// <summary>
        /// 复检日期
        /// </summary>
        public DateTime confirm_recheck_date { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        [Required]
        public decimal confirm_quantity { get; set; }
        /// <summary>
        /// 箱码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string confirm_box_code { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string confirm_stock_code { get; set; }
        /// <summary>
        /// 上传标志(1未上传；2已上传)
        /// </summary>
        [StringLength(BaseVerification.column50)]
        public string confirm_upload_flag { get; set; }
        /// <summary>
        /// 上传时间
        /// </summary>
        public DateTime confirm_upload_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled confirm_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? confirm_company_id { get; set; }
        /// <summary>
        /// 物料代码
        /// </summary>
        public virtual Guid? confirm_goods_id { get; set; }
        /// <summary>
        /// 质量状态
        /// </summary>
        public virtual Guid? confirm_quality_status { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? confirm_slot_code { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid? confirm_warehouse_id { get; set; }
        /// <summary>
        /// 口号
        /// </summary>
        public virtual Guid? confirm_port_id { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? confirm_platform_id { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(ExportConfirm))]
    public class ExportConfirmDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 大批号
        /// </summary>
        public string confirm_batch_no { get; set; }
        /// <summary>
        /// 小批号
        /// </summary>
        public string confirm_lots_no { get; set; }
        /// <summary>
        /// 生产日期
        /// </summary>
        public DateTime confirm_product_date { get; set; }
        /// <summary>
        /// 生产线
        /// </summary>
        public string confirm_product_lineid { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string confirm_remark { get; set; }
        /// <summary>
        /// 单据条码
        /// </summary>
        public string confirm_bill_bar { get; set; }
        /// <summary>
        /// 失效日期
        /// </summary>
        public DateTime confirm_vaildate_date { get; set; }
        /// <summary>
        /// 复检日期
        /// </summary>
        public DateTime confirm_recheck_date { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal confirm_quantity { get; set; }
        /// <summary>
        /// 箱码
        /// </summary>
        public string confirm_box_code { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string confirm_stock_code { get; set; }
        /// <summary>
        /// 上传标志(1未上传；2已上传)
        /// </summary>
        public string confirm_upload_flag { get; set; }
        /// <summary>
        /// 上传时间
        /// </summary>
        public DateTime confirm_upload_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled confirm_is_enable { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? confirm_company_id { get; set; }
        [ForeignKey("confirm_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 物料代码
        /// </summary>
        public virtual Guid? confirm_goods_id { get; set; }
        [ForeignKey("confirm_goods_id")]
        public virtual GoodsInfo.GoodsInfo Goods { get; set; }
        /// <summary>
        /// 质量状态
        /// </summary>
        public virtual Guid? confirm_quality_status { get; set; }
        [ForeignKey("confirm_quality_status")]
        public virtual QualityInfo.QualityInfo Quality { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? confirm_slot_code { get; set; }
        [ForeignKey("confirm_slot_code")]
        public virtual SlotInfo.SlotInfo Slot { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid? confirm_warehouse_id { get; set; }
        [ForeignKey("confirm_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        /// <summary>
        /// 口号
        /// </summary>
        public virtual Guid? confirm_port_id { get; set; }
        [ForeignKey("confirm_port_id")]
        public virtual PortInfo.PortInfo Port { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? confirm_platform_id { get; set; }
        [ForeignKey("confirm_platform_id")]
        public virtual PlatFormInfo.PlatFormInfo PlatForm { get; set; }
        #endregion
    }
    #endregion
}
