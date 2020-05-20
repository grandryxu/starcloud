using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.PickingTask.Dto
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-13 16:51:01
    /// 描 述：
    ///</summary>
       #region 查询参数传入dto
    public class PickingTaskPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 所属仓库id
        /// </summary>
        public Guid? warhouse_id { get; set; }
        /// <summary>
        /// 任务类型(1入库；2出库；3移库；4口对口)
        /// </summary>
        public TaskType? picking_mode { get; set; }
        /// <summary>
        /// 巷道id
        /// </summary>
        public Guid? tunnel_id { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime? picking_creat_datetime { get; set; }
        /// <summary>
        /// 执行标志(1待执行；2输送机；3堆垛机；4RGV；5AGV；7暂停中；9已完成)
        /// </summary>
        public TaskExecuteFlag? picking_execute_flag { get; set; }
        /// <summary>
        /// 手自标志(1自动；2手动)
        /// </summary>
        public TaskManualFlag? picking_manual_flag { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(PickingTask))]
    public class PickingTaskCreatedDto : BaseCreateDto
    {
        #region 属性
        /// <summary>
        /// 任务号5位
        /// </summary>
        public string picking_no { get; set; }
        /// <summary>
        /// 优先级
        /// </summary>
        public int picking_priority { get; set; }
        /// <summary>
        /// 任务方式(1入库；2出库；3移库；4口对口)
        /// </summary>
        public TaskType picking_mode { get; set; }
        /// <summary>
        /// 托盘码
        /// </summary>
        public string picking_stock_code { get; set; }
        /// <summary>
        /// 执行标志(1待执行；2输送机；3堆垛机；4RGV；5AGV；7暂停中；9已完成)
        /// </summary>
        public TaskExecuteFlag picking_execute_flag { get; set; }
        /// <summary>
        /// 手自标志(1自动；2手动)
        /// </summary>
        public TaskManualFlag picking_manual_flag { get; set; }
        /// <summary>
        /// 故障
        /// </summary>
        public string picking_malfunction { get; set; }

        #endregion

        #region 关联
        /// <summary>
        /// 归属
        /// </summary>
        public virtual Guid? picking_company_id { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? picking_slot_code { get; set; }
        /// <summary>
        /// 移入库位
        /// </summary>
        public virtual Guid? picking_inslot_code { get; set; }
        /// <summary>
        /// 出入口1
        /// </summary>
        public virtual Guid? picking_port_id { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? picking_platform_id { get; set; }
        /// <summary>
        /// 出入口2
        /// </summary>
        public virtual Guid? picking_port_id2 { get; set; }
        #endregion

    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(PickingTask))]
    public class PickingTaskUpdatedDto : BaseUpdateDto
    {
        #region 属性
        /// <summary>
        /// 任务号5位
        /// </summary>
        public string picking_no { get; set; }
        /// <summary>
        /// 优先级
        /// </summary>
        public int picking_priority { get; set; }
        /// <summary>
        /// 任务方式(1入库；2出库；3移库；4口对口)
        /// </summary>
        public TaskType picking_mode { get; set; }
        /// <summary>
        /// 托盘码
        /// </summary>
        public string picking_stock_code { get; set; }
        /// <summary>
        /// 执行标志(1待执行；2输送机；3堆垛机；4RGV；5AGV；7暂停中；9已完成)
        /// </summary>
        public TaskExecuteFlag picking_execute_flag { get; set; }
        /// <summary>
        /// 手自标志(1自动；2手动)
        /// </summary>
        public TaskManualFlag picking_manual_flag { get; set; }
        /// <summary>
        /// 故障
        /// </summary>
        public string picking_malfunction { get; set; }

        #endregion

        #region 关联
        /// <summary>
        /// 归属
        /// </summary>
        public virtual Guid? picking_company_id { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? picking_slot_code { get; set; }
        /// <summary>
        /// 移入库位
        /// </summary>
        public virtual Guid? picking_inslot_code { get; set; }
        /// <summary>
        /// 出入口1
        /// </summary>
        public virtual Guid? picking_port_id { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? picking_platform_id { get; set; }
        /// <summary>
        /// 出入口2
        /// </summary>
        public virtual Guid? picking_port_id2 { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(PickingTask))]
    public class PickingTaskDto : EntityDto<Guid>
    {
        #region 属性
        /// <summary>
        /// 任务号5位
        /// </summary>
        public string picking_no { get; set; }
        /// <summary>
        /// 优先级
        /// </summary>
        public int picking_priority { get; set; }
        /// <summary>
        /// 任务方式(1入库；2出库；3移库；4口对口)
        /// </summary>
        public TaskType picking_mode { get; set; }
        /// <summary>
        /// 托盘码
        /// </summary>
        public string picking_stock_code { get; set; }
        /// <summary>
        /// 执行标志(1待执行；2输送机；3堆垛机；4RGV；5AGV；7暂停中；9已完成)
        /// </summary>
        public TaskExecuteFlag picking_execute_flag { get; set; }
        /// <summary>
        /// 手自标志(1自动；2手动)
        /// </summary>
        public TaskManualFlag picking_manual_flag { get; set; }
        /// <summary>
        /// 故障
        /// </summary>
        public string picking_malfunction { get; set; }
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 归属
        /// </summary>
        public virtual Guid? picking_company_id { get; set; }
        [ForeignKey("picking_company_id")]
        public virtual CompanyInfo.CompanyInfo company { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? picking_slot_code { get; set; }
        [ForeignKey("picking_slot_code")]
        public virtual SlotInfo.SlotInfo Slot1 { get; set; }
        /// <summary>
        /// 移入库位
        /// </summary>
        public virtual Guid? picking_inslot_code { get; set; }
        [ForeignKey("picking_inslot_code")]
        public virtual SlotInfo.SlotInfo Slot2 { get; set; }
        /// <summary>
        /// 出入口1
        /// </summary>
        public virtual Guid? picking_port_id { get; set; }
        [ForeignKey("picking_port_id")]
        public virtual PortInfo.PortInfo Port1 { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? picking_platform_id { get; set; }
        [ForeignKey("picking_platform_id")]
        public virtual PlatFormInfo.PlatFormInfo PlatForm { get; set; }
        /// <summary>
        /// 出入口2
        /// </summary>
        public virtual Guid? picking_port_id2 { get; set; }
        [ForeignKey("picking_port_id2")]
        public virtual PortInfo.PortInfo Port2 { get; set; }
        #endregion
    }
    #endregion
}
