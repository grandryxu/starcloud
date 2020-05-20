﻿using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.RGVTask.Dto
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-05-14 9:53:49
    /// 描 述：
    ///</summary>
    #region 查询输入参数
    public class RGVTaskPagedRequest : PagedResultRequestDto
    {
        #region 属性
        /// <summary>
        /// 所属仓库id
        /// </summary>
        public Guid? warhouse_id { get; set; }
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
        /// 创建时间
        /// </summary>
        public DateTime? rgv_creat_datetime { get; set; }
        /// <summary>
        /// 执行标志(1待执行；2输送机；3堆垛机；4RGV；5AGV；7暂停中；9已完成)
        /// </summary>
        public TaskExecuteFlag? rgv_execute_flag { get; set; }
        /// <summary>
        /// 手自标志(1自动；2手动)
        /// </summary>
        public TaskManualFlag? rgv_manual_flag { get; set; }
        #endregion
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(RGVTask))]
    public class RGVTaskCreatedDto : BaseCreateDto
    {
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
        /// 对应库位对应的仓库id
        /// </summary>
        public Guid? warehouse_id { get; set; }
        /// <summary>
        /// 库位编号5位
        /// </summary>
        public string slot_code_code { get; set; }
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
        /// <summary>
        /// 移入库位
        /// </summary>
        public virtual Guid? rgv_inslot_code { get; set; }
        /// <summary>
        /// 出入口1
        /// </summary>
        public virtual Guid? rgv_port_id { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? rgv_platform_id { get; set; }
        /// <summary>
        /// 出入口2
        /// </summary>
        public virtual Guid? rgv_port_id2 { get; set; }
        #endregion
    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(RGVTask))]
    public class RGVTaskUpdatedDto : BaseUpdateDto
    {
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
        /// 对应库位对应的仓库id
        /// </summary>
        public Guid? warehouse_id { get; set; }
        /// <summary>
        /// 库位编号5位
        /// </summary>
        public string slot_code_code { get; set; }
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
        /// <summary>
        /// 移入库位
        /// </summary>
        public virtual Guid? rgv_inslot_code { get; set; }
        /// <summary>
        /// 出入口1
        /// </summary>
        public virtual Guid? rgv_port_id { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? rgv_platform_id { get; set; }
        /// <summary>
        /// 出入口2
        /// </summary>
        public virtual Guid? rgv_port_id2 { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(RGVTask))]
    public class RGVTaskDto : EntityDto<Guid>
    {
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
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
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
    #endregion

}
