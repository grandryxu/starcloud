namespace XMX.WMS
{
    public class WMSConsts
    {
        public const string LocalizationSourceName = "WMS";

        public const string ConnectionStringName = "SQLConn";

        public const bool MultiTenancyEnabled = true;
    }

    /// <summary>
    /// 启用状态
    /// </summary>
    public enum WMSIsEnabled : int
    {
        启用 = 1,
        禁用 = 2
    }

    /// <summary>
    /// 托盘状态
    /// </summary>
    public enum WMSIsFlag : int
    {
        正常 = 1,
        抽检 = 2
    }

    /// <summary>
    /// 删除状态
    /// </summary>
    public enum WMSIsDeleted : int
    {
        正常 = 1,
        删除 = 2
    }

    /// <summary>
    /// 物料类型(1固体；2粉剂；3液体；4气体)
    /// </summary>
    public enum GoodsType : int
    {
        固体 = 1,
        粉剂 = 2,
        液体 = 3,
        气体 = 4
    }

    /// <summary>
    /// 单据类型(1入库；2出库)
    /// </summary>
    public enum BillType : int
    {
        入库 = 1,
        出库 = 2
    }

    /// <summary>
    /// 类型(1入；2出；3双向)
    /// </summary>
    public enum PortType : int
    {
        入 = 1,
        出 = 2,
        双向 = 3
    }

    /// <summary>
    /// 仓库类型(1立库；2平库；3密集库)
    /// </summary>
    public enum WarehouseType : int
    {
        立库 = 1,
        平库 = 2,
        密集库 = 3
    }

    /// <summary>
    /// 库位类型(1层列排；2排列层)
    /// </summary>
    public enum SlotType : int
    {
        层列排 = 1,
        排列层 = 2
    }

    /// <summary>
    /// 库存状态 0 空闲 1 有库存 2 入库中 3 出库中
    /// </summary>
    public enum SlotStock : int
    {
        空闲 = 0,
        有库存 = 1,
        入库中 = 2,
        出库中 = 3
    }

    /// <summary>
    /// 屏蔽状态 0 正常 1 屏蔽
    /// </summary>
    public enum SlotClosed : int
    {
        正常 = 0,
        屏蔽 = 1
    }

    /// <summary>
    /// 入库锁定 0 正常 1锁定
    /// </summary>
    public enum SlotImp : int
    {
        正常 = 0,
        锁定 = 1
    }

    /// <summary>
    /// 出库锁定 0 正常 1锁定
    /// </summary>
    public enum SlotExp : int
    {
        正常 = 0,
        锁定 = 1
    }

    /// <summary>
    /// 是否可移动 0 不可移动 1 可移动
    /// </summary>
    public enum SlotMoveable : int
    {
        不可移动 = 0,
        可移动 = 1
    }

    /// <summary>
    /// 是否规避繁忙巷道 1 是 0 否
    /// </summary>
    public enum BuzyFlag : int
    {
        是 = 1,
        否 = 0
    }

    /// <summary>
    /// 按排选择 1 从小到大 2 从大到小
    /// </summary>
    public enum SelecType : int
    {
        从小到大 = 1,
        从大到小 = 2
    }

    /// <summary>
    /// 入出顺序 1 先入先出 2 后进先出
    /// </summary>
    public enum OrderType : int
    {
        先入先出 = 1,
        后进先出 = 2
    }

    /// <summary>
    /// 筛选方案	1 整托盘 2 清仓
    /// </summary>
    public enum UnpackType : int
    {
        整托盘 = 1,
        清仓 = 2
    }

    /// <summary>
    /// 先到期先出 1 是 0 否
    /// </summary>
    public enum FefoType : int
    {
        是 = 1,
        否 = 0
    }

    /// <summary>
    /// 日期类型 1无；2年月日；3年月日小时分钟秒
    /// </summary>
    public enum DateType : int
    {
        无 = 1,
        年月日 = 2,
        年月日小时分钟秒 = 3
    }

    /// <summary>
    /// 执行标志(1未执行；2执行中；3已完成)
    /// </summary>
    public enum ExecuteFlag : int
    {
        未执行 = 1,
        执行中 = 2,
        已完成 = 3
    }

    /// <summary>
    /// 审核标志(1未审核；2已审核)
    /// </summary>
    public enum AuditFlag : int
    {
        未审核 = 1,
        已审核 = 2
    }

    /// <summary>
    /// 作废标志(1正常；2作废)
    /// </summary>
    public enum NousedFlag : int
    {
        正常 = 1,
        作废 = 2
    }

    /// <summary>
    /// 上传标志(1未上传；2已上传)
    /// </summary>
    public enum UploadFlag : int
    {
        未上传 = 1,
        已上传 = 2
    }

    /// <summary>
    /// 是否抽检托盘 1 是 0 否
    /// </summary>
    public enum StockStatus : int
    {
        是 = 1,
        否 = 0
    }

    /// <summary>
    /// 类型 1 库位 2 巷道
    /// </summary>
    public enum RowType : int
    {
        库位 = 1,
        巷道 = 2
    }

    /// <summary>
    /// 状态 1 未初始化 2 已初始化
    /// </summary>
    public enum RowStatus : int
    {
        未初始化 = 1,
        已初始化 = 2
    }

    /// <summary>
    /// 内外侧标志 1 内侧 2 外侧 3单排
    /// </summary>
    public enum InOutType : int
    {
        内侧 = 1,
        外侧 = 2,
        单排 = 3
    }

    /// <summary>
    /// 巷道锁定 1 锁定 2 未锁定
    /// </summary>
    public enum LockType : int
    {
        锁定 = 1,
        未锁定 = 2
    }

    /// <summary>
    /// 任务方式(1入库；2出库；3移库；4口对口；5回流；6空托盘入库；7空托盘出库)
    /// </summary>
    public enum TaskType : int
    {
        入库 = 1,
        出库 = 2,
        移库 = 3,
        口对口 = 4,
        回流 = 5,
        空托盘入库 = 6,
        空托盘出库 = 7
    }

    /// <summary>
    ///  执行标志(1待执行；2输送机；3堆垛机；4RGV；5AGV；7暂停中；9已完成；10非完成)
    /// </summary>
    public enum TaskExecuteFlag : int
    {
        待执行 = 1,
        输送机 = 2,
        堆垛机 = 3,
        RGV = 4,
        AGV = 5,
        暂停中 = 7,
        已完成 = 9,
        非完成=10
    }

    /// <summary>
    ///  手自标志(1自动；2手动
    /// </summary>
    public enum TaskManualFlag : int
    {
        自动 = 1,
        手动 = 0
    }

    /// <summary>
    /// 在线状态
    /// </summary>
    public enum OnlineState : int
    {
        在线 = 1,
        离线 = 2
    }

    /// <summary>
    /// 报警状态
    /// </summary>
    public enum AlarmState : int
    {
        正常 = 1,
        异常 = 2
    }

    /// <summary>
    /// 状态(1可用；2分配；3出库；4复核；5暂存；6回流；7冻结)
    /// </summary>
    public enum InventoryStatus : int
    {
        可用 = 1,
        分配 = 2,
        出库 = 3,
        复核 = 4,
        暂存 = 5,
        回流 = 6,
        冻结 = 7
    }

    /// <summary>
    /// 设备类型
    /// </summary>
    public enum EquipmentType : int
    {
        堆垛机 = 1,
        RGV = 2,
        码垛机 = 3,
        拆垛机 = 4,
        AGV = 5,
        LED = 6
    }

    /// <summary>
    /// 设备日志类型
    /// </summary>
    public enum EquipmentLogType : int
    {
        正常 = 1,
        报警 = 2,
        异常 = 3
    }

    /// <summary>
    /// 设备执行状态
    /// </summary>
    public enum EquipmentExecutionState : int
    {
        开始执行 = 1,
        执行中 = 2,
        执行完成 = 3
    }

    /// <summary>
    /// 月台状态
    /// </summary>
    public enum PlatformState : int
    {
        空闲 = 1,
        卸货 = 2
    }

    /// <summary>
    /// 盘点类型 1整库盘点 2动态盘点 3物资盘点
    /// </summary>
    public enum StockTaskingType : int
    {
        整库盘点 = 1,
        动态盘点 = 2,
        物资盘点 = 3
    }

    /// <summary>
    /// 盘点单状态
    /// </summary>
    public enum StockTaskingState : int
    {
        未盘点 = 0,
        盘点结束 = 1,
        盘点中 = 2
    }

    /// <summary>
    /// 盘点详情单状态
    /// </summary>
    public enum StockTaskingDetailState : int
    {
        初始化 = 0,
        已盘点 = 1
    }

    /// <summary>
    /// 模块菜单类型
    /// </summary>
    public enum MenuType : int
    {
        菜单 = 1,
        链接 = 2,
        按钮 = 3
    }

    /// <summary>
    /// 预警类型
    /// </summary>
    public enum WarningType:int
    {
        呆滞库存预警 = 1,
        效期库存预警 = 2,
        库存阈值预警 = 3,
        入库异常预警 = 4,
        出库异常预警 = 5,
        库存异常预警 = 6
    }

    /// <summary>
    /// 角色类型
    /// </summary>
    public enum WMSRoleType:int
    {
        Web角色=1,
        Api角色=2
    }

    /// <summary>
    /// 抽检类型
    /// </summary>
    public enum CheckType : int
    {
        无损抽检 = 1,
        有损抽检 = 2
    }

    /// <summary>
    /// 是否生成出库单
    /// </summary>
    public enum CheckBillStatus : int
    {
        未生成 = 0,
        已生成 = 1
    }

    /// <summary>
    /// 是否已经检测放行
    /// </summary>
    public enum CheckReleasedStatus : int
    {
        未放行 = 0,
        已放行 = 1
    }
}
