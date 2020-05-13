using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.ImportOrder
{
    /// <summary>
    /// 入库流水表
    /// </summary>
    public class ImportOrder : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 大批号
        /// </summary>
        public string imporder_batch_no { get; set; }
        /// <summary>
        /// 小批号
        /// </summary>
        public string imporder_lots_no { get; set; }
        /// <summary>
        /// 生产日期
        /// </summary>
        public DateTime imporder_product_date { get; set; }
        /// <summary>
        /// 生产线
        /// </summary>
        public string imporder_product_lineid { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        public string imporder_remark { get; set; }
        /// <summary>
        /// 单据条码
        /// </summary>
        public string imporder_bill_bar { get; set; }
        /// <summary>
        /// 失效日期
        /// </summary>
        public DateTime imporder_vaildate_date { get; set; }
        /// <summary>
        /// 复检日期
        /// </summary>
        public DateTime imporder_recheck_date { get; set; }
        /// <summary>
        /// 是否抽检托盘 1 是 0 否
        /// </summary>
        public StockStatus imporder_stock_status { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal imporder_quantity { get; set; }
        /// <summary>
        /// 箱码
        /// </summary>
        public string imporder_box_code { get; set; }
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string imporder_stock_code { get; set; }
        /// <summary>
        /// 执行标志(1待执行；2执行中；3已完成)
        /// </summary>
        public ExecuteFlag imporder_execute_flag { get; set; }
        /// <summary>
        /// 作废标志(1正常；2作废)
        /// </summary>
        public NousedFlag imporder_noused_flag { get; set; }
        /// <summary>
        /// 作废人
        /// </summary>
        public string imporder_noused_uid { get; set; }
        /// <summary>
        /// 作废时间
        /// </summary>
        public DateTime imporder_noused_datetime { get; set; }
        /// <summary>
        /// 上传标志(1未上传；2已上传)
        /// </summary>
        public string imporder_upload_flag { get; set; }
        /// <summary>
        /// 上传时间
        /// </summary>
        public DateTime imporder_upload_datetime { get; set; }
        /// <summary>
        /// 是否禁用(1启用；2禁用)
        /// </summary>
        public WMSIsEnabled imporder_is_enable { get; set; }
        /// <summary>
        /// 托盘状态(1正常；2抽检)
        /// </summary>
        public WMSIsFlag imporder_stock_flag { get; set; }
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
        public virtual Guid? imporder_company_id { get; set; }
        [ForeignKey("imporder_company_id")]
        public virtual CompanyInfo.CompanyInfo Company { get; set; }
        /// <summary>
        /// 物料代码
        /// </summary>
        public virtual Guid? imporder_goods_id { get; set; }
        [ForeignKey("imporder_goods_id")]
        public virtual GoodsInfo.GoodsInfo Goods { get; set; }
        /// <summary>
        /// 质量状态
        /// </summary>
        public virtual Guid? imporder_quality_status { get; set; }
        [ForeignKey("imporder_quality_status")]
        public virtual QualityInfo.QualityInfo QualityInfo { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? imporder_slot_code { get; set; }
        [ForeignKey("imporder_slot_code")]
        public virtual SlotInfo.SlotInfo Slot { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public virtual Guid? imporder_warehouse_id { get; set; }
        [ForeignKey("imporder_warehouse_id")]
        public virtual WarehouseInfo.WarehouseInfo Warehouse { get; set; }
        /// <summary>
        /// 口号
        /// </summary>
        public virtual Guid? imporder_port_id { get; set; }
        [ForeignKey("imporder_port_id")]
        public virtual PortInfo.PortInfo Port { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public virtual Guid? imporder_body_id { get; set; }
        [ForeignKey("imporder_body_id")]
        public virtual ImportBillbody.ImportBillbody ImportBillbody { get; set; }
        #endregion
    }
}
