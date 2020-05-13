using Microsoft.EntityFrameworkCore;
using Abp.Zero.EntityFrameworkCore;
using XMX.WMS.Authorization.Roles;
using XMX.WMS.Authorization.Users;
using XMX.WMS.MultiTenancy;

namespace XMX.WMS.EntityFrameworkCore
{
    public class WMSDbContext : AbpZeroDbContext<Tenant, Role, User, WMSDbContext>
    {
        /* Define a DbSet for each entity of the application */
        public WMSDbContext(DbContextOptions<WMSDbContext> options)
            : base(options)
        {
        }
        /// <summary>
        /// 物料基础信息
        /// </summary>
        public virtual DbSet<GoodsInfo.GoodsInfo> GoodsInfo { get; set; }
        /// <summary>
        /// 物料单位信息
        /// </summary>
        public virtual DbSet<UnitInfo.UnitInfo> UnitInfo { get; set; }
        /// <summary>
        /// 物料质量状态
        /// </summary>
        public virtual DbSet<QualityInfo.QualityInfo> QualityInfo { get; set; }
        /// <summary>
        /// 客户类别信息
        /// </summary>
        public virtual DbSet<CustomTypeInfo.CustomTypeInfo> CustomTypeInfo { get; set; }
        /// <summary>
        /// 客户信息
        /// </summary>
        public virtual DbSet<CustomInfo.CustomInfo> CustomInfo { get; set; }
        /// <summary>
        /// 仓库信息
        /// </summary>
        public virtual DbSet<WarehouseInfo.WarehouseInfo> WarehouseInfo { get; set; }
        /// <summary>
        /// 库区信息
        /// </summary>
        public virtual DbSet<AreaInfo.AreaInfo> AreaInfo { get; set; }
        /// <summary>
        /// 库位信息
        /// </summary>
        public virtual DbSet<SlotInfo.SlotInfo> SlotInfo { get; set; }
        /// <summary>
        /// 库位大小
        /// </summary>
        public virtual DbSet<SlotSize.SlotSize> SlotSize { get; set; }
        /// <summary>
        /// 库位排设置
        /// </summary>
        public virtual DbSet<RowInfo.RowInfo> RowInfo { get; set; }
        /// <summary>
        /// 巷道
        /// </summary>
        public virtual DbSet<TunnelInfo.TunnelInfo> TunnelInfo { get; set; }
        /// <summary>
        /// 巷道出入口绑定
        /// </summary>
        public virtual DbSet<TunnelPort.TunnelPort> TunnelPort { get; set; }
        /// <summary>
        /// 单据类型信息
        /// </summary>
        public virtual DbSet<BillInfo.BillInfo> BillInfo { get; set; }
        /// <summary>
        /// 入出口信息
        /// </summary>
        public virtual DbSet<PortInfo.PortInfo> PortInfo { get; set; }
        /// <summary>
        /// 月台信息
        /// </summary>
        public virtual DbSet<PlatFormInfo.PlatFormInfo> PlatFormInfo { get; set; }
        /// <summary>
        /// 垛形信息
        /// </summary>
        public virtual DbSet<PackInfo.PackInfo> PackInfo { get; set; }
        /// <summary>
        /// 公司基础信息
        /// </summary>
        public virtual DbSet<CompanyInfo.CompanyInfo> CompanyInfo { get; set; }
        /// <summary>
        /// 部门基础信息
        /// </summary>
        public virtual DbSet<DepartmentInfo.DepartmentInfo> DepartmentInfo { get; set; }
        /// <summary>
        /// 监控策略
        /// </summary>
        public virtual DbSet<StrategyMonitor.StrategyMonitor> StrategyMonitor { get; set; }
        /// <summary>
        /// 上架策略
        /// </summary>
        public virtual DbSet<StrategyWarehousing.StrategyWarehousing> StrategyWarehousing { get; set; }
        /// <summary>
        /// 分配策略
        /// </summary>
        public virtual DbSet<StrategyDistribution.StrategyDistribution> StrategyDistribution { get; set; }
        /// <summary>
        /// 编码规则
        /// </summary>
        public virtual DbSet<EncodingRule.EncodingRule> EncodingRule { get; set; }
        /// <summary>
        /// 单据头
        /// </summary>
        public virtual DbSet<ImportBillhead.ImportBillhead> ImportBillhead { get; set; }
        /// <summary>
        /// 单据体
        /// </summary>
        public virtual DbSet<ImportBillbody.ImportBillbody> ImportBillbody { get; set; }
        /// <summary>
        /// 入库流水表
        /// </summary>
        public virtual DbSet<ImportOrder.ImportOrder> ImportOrder { get; set; }
        /// <summary>
        /// 空托盘入库流水表
        /// </summary>
        public virtual DbSet<ImportStock.ImportStock> ImportStock { get; set; }
        /// <summary>
        /// 出库表头表
        /// </summary>
        public virtual DbSet<ExportBillhead.ExportBillhead> ExportBillhead { get; set; }
        /// <summary>
        /// 出库表头表
        /// </summary>
        public virtual DbSet<ExportBillbody.ExportBillbody> ExportBillbody { get; set; }
        /// <summary>
        /// 出库流水表
        /// </summary>
        public virtual DbSet<ExportOrder.ExportOrder> ExportOrder { get; set; }
        /// <summary>
        /// 出库复核表
        /// </summary>
        public virtual DbSet<ExportConfirm.ExportConfirm> ExportConfirm { get; set; }
        /// <summary>
        /// 空托盘出库流水表
        /// </summary>
        public virtual DbSet<ExportStock.ExportStock> ExportStock { get; set; }
        /// <summary>
        /// RGV设备
        /// </summary>
        public virtual DbSet<Equipment.RGVInfo> RGVInfo { get; set; }
        /// <summary>
        /// 堆垛机设备
        /// </summary>
        public virtual DbSet<Equipment.StackerInfo> StackerInfo { get; set; }
        /// <summary>
        /// 质量放行管理
        /// </summary>
        public virtual DbSet<QualityReleased.QualityReleased> QualityReleased { get; set; }
        /// <summary>
        /// 质量抽检
        /// </summary>
        public virtual DbSet<QualityCheck.QualityCheck> QualityCheck { get; set; }
        /// <summary>
        /// 质量抽检明细
        /// </summary>
        public virtual DbSet<QualityCheckDetail.QualityCheckDetail> QualityCheckDetail { get; set; }
        /// <summary>
        /// 判断如果是Oracle，需要执行Schema
        /// </summary>
        /// <param name="modelBuilder"></param>
        //protected override void OnModelCreating(ModelBuilder modelBuilder)
        //{
        //    //判断当前数据库是Oracle 需要手动添加Schema(DBA提供的数据库账号名称)
        //    if (this.Database.IsOracle())
        //    {
        //        modelBuilder.HasDefaultSchema("NETCORE");
        //    }
        //    base.OnModelCreating(modelBuilder);
        //}
        /// <summary>
        /// 立库历史任务
        /// </summary>
        public virtual DbSet<HistoryTaskMainInfo.HistoryTaskMainInfo> HistoryTaskMainInfo { get; set; }
        /// <summary>
        /// 立库任务
        /// </summary>
        public virtual DbSet<TaskMainInfo.TaskMainInfo> TaskMainInfo { get; set; }
        /// <summary>
        /// 库存
        /// </summary>
        public virtual DbSet<InventoryInfo.InventoryInfo> InventoryInfo { get; set; }
        /// <summary>
        /// 设备日志
        /// </summary>
        public virtual DbSet<Equipment.EquipmentLogInfo> EquipmentLogInfo { get; set; }
        /// <summary>
        /// 操作日志
        /// </summary>
        public virtual DbSet<Operation.OperationLogInfo> OperationLogInfo { get; set; }
        /// <summary>
        /// 报警表
        /// </summary>
        public virtual DbSet<Alarm.Alarm> Alarm { get; set; }
        /// <summary>
        /// 盘点
        /// </summary>
        public virtual DbSet<StockTasking.StockTasking> StockTasking { get; set; }
        /// <summary>
        /// 盘点详情
        /// </summary>
        public virtual DbSet<StockTaskingDetail.StockTaskingDetail> StockTaskingDetail { get; set; }
        /// <summary>
        /// 系统模块
        /// </summary>
        public virtual DbSet<SystemMenuInfo.SystemMenuInfo> SystemMenuInfo { get; set; }

        /// <summary>
        /// 移动端模块
        /// </summary>
        public virtual DbSet<MoveModelMenu.MoveModelMenu> MoveModelMenu { get; set; }
        /// <summary>
        /// 库存统计
        /// </summary>
        public virtual DbSet<WarehouseStock.WarehouseStock> WarehouseStock { get; set; }
        /// <summary>
        /// 分表测试表
        /// </summary>
        public virtual DbSet<Fenbiao.Fenbiao> Fenbiao { get; set; }
        /// <summary>
        /// 操作日志
        /// </summary>
        public virtual DbSet<WMSOptLogInfo.WMSOptLogInfo> WMSOptLogInfo { get; set; }

        /// <summary>
        /// 入库申请日志
        /// </summary>
        public virtual DbSet<ImportApplyLog.ImportApplyLog> ImportApplyLog { get; set; }
        /// <summary>
        /// 出库单据同步日志
        /// </summary>
        public virtual DbSet<ExportBillSyncLog.ExportBillSyncLog> ExportBillSyncLog { get; set; }
        /// <summary>
        /// 平库任务管理
        /// </summary>
        public virtual DbSet<FlatBankTask.FlatBankTask> FlatBankTask { get; set; }
        /// <summary>
        /// 拣选任务管理
        /// </summary>
        public virtual DbSet<PickingTask.PickingTask> PickingTask { get; set; }
        /// <summary>
        /// AGV任务管理
        /// </summary>
        public virtual DbSet<AGVTask.AGVTask> AGVTask { get; set; }
        /// <summary>
        /// RGV任务管理
        /// </summary>
        public virtual DbSet<RGVTask.RGVTask> RGVTask { get; set; }
    }
}
