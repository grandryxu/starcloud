using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.HistoryTaskMainInfo
{
    /// <summary>
    /// 历史任务
    /// </summary>
    public class HistoryTaskMainInfo : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 任务号5位
        /// </summary>
        public string main_no { get; set; }
        /// <summary>
        /// 优先级
        /// </summary>
        public int main_priority { get; set; }
        /// <summary>
        /// 任务方式(1入库；2出库；3移库；4口对口)
        /// </summary>
        public TaskType main_mode { get; set; }
        /// <summary>
        /// 托盘码
        /// </summary>
        public string main_stock_code { get; set; }
        /// <summary>
        /// 故障
        /// </summary>
        public string main_malfunction { get; set; }
        /// <summary>
        /// 执行标志(1待执行；2输送机；3堆垛机；4RGV；5AGV；7暂停中；9已完成)
        /// </summary>
        public TaskExecuteFlag main_execute_flag { get; set; }
        /// <summary>
        /// 手自标志(1自动；2手动)
        /// </summary>
        public TaskManualFlag main_manual_flag { get; set; }
        /// <summary>
        /// 物料ID
        /// </summary>
        public Guid? material_id { get; set; }
        /// <summary>
        /// 物料
        /// </summary>
        public string material_name { get; set; }
        /// <summary>
        /// 批次
        /// </summary>
        public string exporder_batch_no { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        public decimal exporder_quantity { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 归属
        /// </summary>
        public virtual Guid? main_company_id { get; set; }
        [ForeignKey("main_company_id")]
        public virtual CompanyInfo.CompanyInfo company { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? main_slot_code { get; set; }
        [ForeignKey("main_slot_code")]
        public virtual SlotInfo.SlotInfo Slot1 { get; set; }
        /// <summary>
        /// 移入库位
        /// </summary>
        public virtual Guid? main_inslot_code { get; set; }
        [ForeignKey("main_inslot_code")]
        public virtual SlotInfo.SlotInfo Slot2 { get; set; }
        /// <summary>
        /// 出入口1
        /// </summary>
        public virtual Guid? main_port_id { get; set; }
        [ForeignKey("main_port_id")]
        public virtual PortInfo.PortInfo Port1 { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? main_platform_id { get; set; }
        [ForeignKey("main_platform_id")]
        public virtual PlatFormInfo.PlatFormInfo PlatForm { get; set; }
        /// <summary>
        /// 出入口2
        /// </summary>
        public virtual Guid? main_port_id2 { get; set; }
        [ForeignKey("main_port_id2")]
        public virtual PortInfo.PortInfo Port2 { get; set; }
        #endregion
    }
}
