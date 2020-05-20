using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.AGVTask
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-12 15:17:58
    /// 描 述：AGV任务管理
    ///</summary>
    public class AGVTask : FullAuditedEntity<Guid>
    {
        //字段参照平库任务，拣选任务的字段
        #region 属性
        /// <summary>
        /// 任务号5位
        /// </summary>
        public string agv_no { get; set; }
        /// <summary>
        /// 优先级
        /// </summary>
        public int agv_priority { get; set; }
        /// <summary>
        /// 托盘码
        /// </summary>
        public string agv_stock_code { get; set; }
        /// <summary>
        /// 故障
        /// </summary>
        public string agv_malfunction { get; set; }
        /// <summary>
        /// 执行标志(1待执行；2输送机；3堆垛机；4agv；5AGV；7暂停中；9已完成)
        /// </summary>
        public TaskExecuteFlag agv_execute_flag { get; set; }
        /// <summary>
        /// 手自标志(1自动；2手动)
        /// </summary>
        public TaskManualFlag agv_manual_flag { get; set; }
        #endregion
        #region 关联
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? agv_slot_code { get; set; }
        [ForeignKey("agv_slot_code")]
        public virtual SlotInfo.SlotInfo Slot1 { get; set; }
        /// <summary>
        /// 移入库位
        /// </summary>
        public virtual Guid? agv_inslot_code { get; set; }
        [ForeignKey("agv_inslot_code")]
        public virtual SlotInfo.SlotInfo Slot2 { get; set; }
        /// <summary>
        /// 出入口1
        /// </summary>
        public virtual Guid? agv_port_id { get; set; }
        [ForeignKey("agv_port_id")]
        public virtual PortInfo.PortInfo Port1 { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? agv_platform_id { get; set; }
        [ForeignKey("agv_platform_id")]
        public virtual PlatFormInfo.PlatFormInfo PlatForm { get; set; }
        /// <summary>
        /// 出入口2
        /// </summary>
        public virtual Guid? agv_port_id2 { get; set; }
        [ForeignKey("agv_port_id2")]
        public virtual PortInfo.PortInfo Port2 { get; set; }
        #endregion
    }
}
