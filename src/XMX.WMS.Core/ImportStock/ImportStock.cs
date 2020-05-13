using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.ImportStock
{
    /// <summary>
    /// 空托盘入库流水表
    /// </summary>
    public class ImportStock : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 大批号
        /// </summary>
        public string impstock_batch_no { get; set; }
        /// <summary>
        /// 小批号
        /// </summary>
        public string impstock_lots_no { get; set; }

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
}
