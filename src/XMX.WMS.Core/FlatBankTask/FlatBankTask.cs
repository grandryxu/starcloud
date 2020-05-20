﻿using Abp.Domain.Entities.Auditing;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace XMX.WMS.FlatBankTask
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-12 15:22:29
    /// 描 述：平库任务管理
    ///</summary>
    public class FlatBankTask : FullAuditedEntity<Guid>
    {
        #region 属性
        /// <summary>
        /// 任务号5位
        /// </summary>
        public string flat_no { get; set; }
        /// <summary>
        /// 优先级
        /// </summary>
        public int flat_priority { get; set; }
        /// <summary>
        /// 任务方式(1入库；2出库；3移库；4口对口)
        /// </summary>
        public TaskType flat_mode { get; set; }
        /// <summary>
        /// 托盘码
        /// </summary>
        public string flat_stock_code { get; set; }
        /// <summary>
        /// 执行标志(1待执行；2输送机；3堆垛机；4RGV；5AGV；7暂停中；9已完成)
        /// </summary>
        public TaskExecuteFlag flat_execute_flag { get; set; }
        /// <summary>
        /// 手自标志(1自动；2手动)
        /// </summary>
        public TaskManualFlag flat_manual_flag { get; set; }
        /// <summary>
        /// 故障
        /// </summary>
        public string flat_malfunction { get; set; }

        #endregion

        #region 关联
        /// <summary>
        /// 归属
        /// </summary>
        public virtual Guid? flat_company_id { get; set; }
        [ForeignKey("flat_company_id")]
        public virtual CompanyInfo.CompanyInfo company { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? flat_slot_code { get; set; }
        [ForeignKey("flat_slot_code")]
        public virtual SlotInfo.SlotInfo Slot1 { get; set; }
        /// <summary>
        /// 移入库位
        /// </summary>
        public virtual Guid? flat_inslot_code { get; set; }
        [ForeignKey("flat_inslot_code")]
        public virtual SlotInfo.SlotInfo Slot2 { get; set; }
        /// <summary>
        /// 出入口1
        /// </summary>
        public virtual Guid? flat_port_id { get; set; }
        [ForeignKey("flat_port_id")]
        public virtual PortInfo.PortInfo Port1 { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? flat_platform_id { get; set; }
        [ForeignKey("flat_platform_id")]
        public virtual PlatFormInfo.PlatFormInfo PlatForm { get; set; }
        /// <summary>
        /// 出入口2
        /// </summary>
        public virtual Guid? flat_port_id2 { get; set; }
        [ForeignKey("flat_port_id2")]
        public virtual PortInfo.PortInfo Port2 { get; set; }
        #endregion
    }
}
