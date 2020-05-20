using Abp.Application.Services.Dto;
using Abp.AutoMapper;
using System;
using System.ComponentModel.DataAnnotations.Schema;
using XMX.WMS.Base.Dto;

namespace XMX.WMS.TaskMainInfo.Dto
{
    #region 查询参数传入dto
    public class TaskMainInfoPagedRequest : PagedResultRequestDto
    {
        /// <summary>
        /// 所属仓库id
        /// </summary>
        public Guid? warhouse_id { get; set; }
        /// <summary>
        /// 任务类型(1入库；2出库；3移库；4口对口；6空托盘入库；7空托盘出库)
        /// </summary>
        public TaskType? main_mode { get; set; }
        /// <summary>
        /// 巷道id
        /// </summary>
        public Guid? tunnel_id { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime? main_creat_datetime { get; set; }
        /// <summary>
        /// 执行标志(1待执行；2输送机；3堆垛机；4RGV；5AGV；7暂停中；9已完成；10非完成)
        /// </summary>
        public TaskExecuteFlag? main_execute_flag { get; set; }
        /// <summary>
        /// 手自标志(1自动；2手动)
        /// </summary>
        public TaskManualFlag? main_manual_flag { get; set; }
        /// <summary>
        /// 任务ID
        /// </summary>
        public Guid? task_id { get; set; }
    }
    #endregion

    #region 创建CreateDto
    [AutoMapTo(typeof(TaskMainInfo))]
    public class TaskMainInfoCreatedDto : BaseCreateDto
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
        /// 执行标志(1待执行；2输送机；3堆垛机；4RGV；5AGV；7暂停中；9已完成；10非完成)
        /// </summary>
        public TaskExecuteFlag main_execute_flag { get; set; }
        /// </summary>
        /// 手自标志(1自动；2手动)
        /// </summary>
        public TaskManualFlag main_manual_flag { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 归属
        /// </summary>
        public virtual Guid? main_company_id { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? main_slot_code { get; set; }
        /// <summary>
        /// 移入库位
        /// </summary>
        public virtual Guid? main_inslot_code { get; set; }
        /// <summary>
        /// 出入口1
        /// </summary>
        public virtual Guid? main_port_id { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? main_platform_id { get; set; }
        /// <summary>
        /// 出入口2
        /// </summary>
        public virtual Guid? main_port_id2 { get; set; }
        #endregion

    }
    #endregion

    #region 修改UpdateDto
    [AutoMapTo(typeof(TaskMainInfo))]
    public class TaskMainInfoUpdatedDto : BaseUpdateDto
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
        /// 执行标志(1待执行；2输送机；3堆垛机；4RGV；5AGV；7暂停中；9已完成；10非完成)
        /// </summary>
        public TaskExecuteFlag main_execute_flag { get; set; }
        /// <summary>
        /// 手自标志(1自动；0手动)
        /// </summary>
        public TaskManualFlag main_manual_flag { get; set; }
        #endregion

        #region 关联
        /// <summary>
        /// 归属
        /// </summary>
        public virtual Guid? main_company_id { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public virtual Guid? main_slot_code { get; set; }
        /// <summary>
        /// 移入库位
        /// </summary>
        public virtual Guid? main_inslot_code { get; set; }
        /// <summary>
        /// 出入口1
        /// </summary>
        public virtual Guid? main_port_id { get; set; }
        /// <summary>
        /// 月台
        /// </summary>
        public virtual Guid? main_platform_id { get; set; }
        /// <summary>
        /// 出入口2
        /// </summary>
        public virtual Guid? main_port_id2 { get; set; }
        #endregion
    }
    #endregion

    #region 查询输出dto
    [AutoMapFrom(typeof(TaskMainInfo))]
    public class TaskMainInfoDto : EntityDto<Guid>
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
        /// 执行标志(1待执行；2输送机；3堆垛机；4RGV；5AGV；7暂停中；9已完成；10非完成)
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
        /// <summary>
        ///创建时间
        /// </summary>
        public DateTime CreationTime { get; set; }
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
    #endregion

    #region 空托盘入库流水dto
    public class ImportStockForTaskDto
    {
        /// <summary>
        /// id
        /// </summary>
        public Guid id { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public Guid? warehouse_id { get; set; }
    }
    #endregion

    #region 入库生成任务传入参数 托盘条码
    public class StockcodePagedRequest 
    {
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string imporder_stock_code { get; set; }
    }
    #endregion

    #region 流水明细
    public class DetailRequestDto
    {
        /// <summary>
        /// 托盘号码
        /// </summary>
        public string stock_code { get; set; }
        /// <summary>
        /// 库位
        /// </summary>
        public string slot_code { get; set; }
        /// <summary>
        /// 仓库
        /// </summary>
        public string warehouse_name { get; set; }
        /// <summary>
        /// 托盘数量
        /// </summary>
        public decimal order_quantity { get; set; }
        /// <summary>
        /// 状态(1未执行；2执行中；3已完成)
        /// </summary>
        public ExecuteFlag execute_flag{get;set;}
        /// <summary>
        /// 物料编码
        /// </summary>
        public string goods_code { get; set; }
        /// <summary>
        /// 物料名称
        /// </summary>
        public string goods_name { get; set; }
        /// <summary>
        /// 创建时间
        /// </summary>
        public DateTime creationTime { get; set; }
    }
    #endregion

    #region 出库复核dto
    public class ExportReviewPagedRequest
    {
        /// <summary>
        /// 任务ID
        /// </summary>
       public Guid  task_id { get; set; }
    }
    #endregion
}
