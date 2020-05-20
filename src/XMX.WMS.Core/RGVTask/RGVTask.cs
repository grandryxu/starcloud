using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.RGVTask
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-12 15:08:32
    /// 描 述：RGV任务管理
    ///</summary>
   public class RGVTask: FullAuditedEntity<Guid>
    {
        //字段参照平库任务，拣选任务的字段
        #region 属性
        /// <summary>
        /// 任务号5位
        /// </summary>
        public string rgv_no { get; set; }
        /// <summary>
        /// 优先级
        /// </summary>
        public int rgv_priority { get; set; }
        /// <summary>
        /// 托盘码
        /// </summary>
        public string rgv_stock_code { get; set; }
        /// <summary>
        /// 故障
        /// </summary>
        public string rgv_malfunction { get; set; }
        /// <summary>
        /// 执行标志(1待执行；2输送机；3堆垛机；4RGV；5AGV；7暂停中；9已完成)
        /// </summary>
        public TaskExecuteFlag rgv_execute_flag { get; set; }
        /// <summary>
        /// 手自标志(1自动；2手动)
        /// </summary>
        public TaskManualFlag rgv_manual_flag { get; set; }
        #endregion
        #region 关联
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? rgv_slot_code { get; set; }
        [ForeignKey("rgv_slot_code")]
        public virtual SlotInfo.SlotInfo Slot1 { get; set; }
        /// <summary>
        /// 移入库位
        /// </summary>
        public virtual Guid? rgv_inslot_code { get; set; }
        [ForeignKey("rgv_inslot_code")]
        public virtual SlotInfo.SlotInfo Slot2 { get; set; }
        /// <summary>
        /// 出入口1
        /// </summary>
        public virtual Guid? rgv_port_id { get; set; }
        [ForeignKey("rgv_port_id")]
        public virtual PortInfo.PortInfo Port1 { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? rgv_platform_id { get; set; }
        [ForeignKey("rgv_platform_id")]
        public virtual PlatFormInfo.PlatFormInfo PlatForm { get; set; }
        /// <summary>
        /// 出入口2
        /// </summary>
        public virtual Guid? rgv_port_id2 { get; set; }
        [ForeignKey("rgv_port_id2")]
        public virtual PortInfo.PortInfo Port2 { get; set; }
        #endregion
    }
}
