using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.ExportOrder
{
    /// <summary>
    /// 出库流水表
    /// </summary>
    public class ExportOrder : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 大批号
        /// </summary>
        public string exporder_batch_no { get; set; }
        /// <summary>
        /// 小批号
        /// </summary>
        public string exporder_lots_no { get; set; }
        /// <summary>
        /// 生产日期
        /// </summary>
        public DateTime exporder_product_date { get; set; }
        /// <summary>
        /// 生产线
        /// </summary>
        public string exporder_product_lineid { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string exporder_remark { get; set; }
        /// <summary>
        /// 单据条码
        /// </summary>
        public string exporder_bill_bar { get; set; }
        /// <summary>
        /// 失效日期
        /// </summary>
        public DateTime exporder_vaildate_date { get; set; }
        /// <summary>
        /// 复检日期
        /// </summary>
        public DateTime exporder_recheck_date { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal exporder_quantity { get; set; }
        /// <summary>
        /// 回流数量
        /// </summary>
        public decimal exporder_return_quantity { get; set; }
        /// <summary>
        /// 箱码
        /// </summary>
        public string exporder_box_code { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string exporder_stock_code { get; set; }
        /// <summary>
        /// 执行标志(1未执行；2执行中；3已完成；4已复核)
        /// </summary>
        public ExecuteFlag exporder_execute_flag { get; set; }
        /// <summary>
        /// 作废标志(1未作废；2已作废)
        /// </summary>
        public NousedFlag exporder_noused_flag { get; set; }
        /// <summary>
        /// 作废人
        /// </summary>
        public string exporder_noused_uid { get; set; }
        /// <summary>
        /// 作废时间
        /// </summary>
        public DateTime exporder_noused_datetime { get; set; }
        /// <summary>
        /// 上传标志(1未上传；2已上传)
        /// </summary>
        public UploadFlag exporder_upload_flag { get; set; }
        /// <summary>
        /// 上传时间
        /// </summary>
        public DateTime exporder_upload_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled exporder_is_enable { get; set; }

        #endregion

        #region 关联
        /// <summary>
        /// 历史任务
        /// </summary>
        public virtual Guid? history_task_id { get; set; }
        [ForeignKey("history_task_id")]
        public virtual HistoryTaskMainInfo.HistoryTaskMainInfo HistoryTask { get; set; }
        /// <summary>
        /// 立库任务
        /// </summary>
        public virtual Guid? task_id { get; set; }
        [ForeignKey("task_id")]
        public virtual TaskMainInfo.TaskMainInfo Task { get; set; }
        /// <summary>
        /// 所属公司
        /// </summary>
        public virtual Guid? exporder_company_id { get; set; }
        [ForeignKey("exporder_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 物料代码
        /// </summary>
        public virtual Guid? exporder_goods_id { get; set; }
        [ForeignKey("exporder_goods_id")]
        public virtual GoodsInfo.GoodsInfo Goods { get; set; }
        /// <summary>
        /// 质量状态
        /// </summary>
        public virtual Guid? exporder_quality_status { get; set; }
        [ForeignKey("exporder_quality_status")]
        public virtual QualityInfo.QualityInfo Quality { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? exporder_slot_code { get; set; }
        [ForeignKey("exporder_slot_code")]
        public virtual SlotInfo.SlotInfo Slot { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid? exporder_warehouse_id { get; set; }
        [ForeignKey("exporder_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        /// <summary>
        /// 口号
        /// </summary>
        public virtual Guid? exporder_port_id { get; set; }
        [ForeignKey("exporder_port_id")]
        public virtual PortInfo.PortInfo Port { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? exporder_platform_id { get; set; }
        [ForeignKey("exporder_platform_id")]
        public virtual PlatFormInfo.PlatFormInfo PlatForm { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public virtual Guid? exporder_body_id { get; set; }
        [ForeignKey("exporder_body_id")]
        public virtual ExportBillbody.ExportBillbody ExportBillbody { get; set; }
        #endregion
    }
}
