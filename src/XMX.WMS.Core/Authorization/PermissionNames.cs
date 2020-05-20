namespace XMX.WMS.Authorization
{
    public static class PermissionNames
    {
        public const string Pages_Tenants = "Pages.Tenants";

        public const string Pages_Users = "Pages.Users";

        public const string Pages_Roles = "Pages.Roles";

        public const string Pages_Text = "Pages.Text";

        #region 物料基础信息
        public const string MaterialBasisInfo = "basic-materiel";
        public const string MaterialBasisInfo_Add = "BasicInfoManage.MaterialBasisInfo.Add";
        public const string MaterialBasisInfo_Update = "BasicInfoManage.MaterialBasisInfo.Update";
        public const string MaterialBasisInfo_Get = "BasicInfoManage.MaterialBasisInfo.Get";
        public const string MaterialBasisInfo_Delete = "BasicInfoManage.MaterialBasisInfo.Delete";
        public const string MaterialBasisInfo_Export = "BasicInfoManage.MaterialBasisInfo.Export";
        public const string MaterialBasisInfo_Print = "BasicInfoManage.MaterialBasisInfo.Print";
        #endregion

        #region 物料计量单位
        public const string MaterialMeasureUnit = "basic-unit";
        public const string MaterialMeasureUnit_Add = "BasicInfoManage.MaterialMeasureUnit.Add";
        public const string MaterialMeasureUnit_Update = "BasicInfoManage.MaterialMeasureUnit.Update";
        public const string MaterialMeasureUnit_Get = "BasicInfoManage.MaterialMeasureUnit.Get";
        public const string MaterialMeasureUnit_Delete = "BasicInfoManage.MaterialMeasureUnit.Delete";
        public const string MaterialMeasureUnit_Export = "BasicInfoManage.MaterialMeasureUnit.Export";
        public const string MaterialMeasureUnit_Print = "BasicInfoManage.MaterialMeasureUnit.Print";
        #endregion

        #region 物料质量状态
        public const string MaterialQualityStatus = "basic-quality";
        public const string MaterialQualityStatus_Add = "BasicInfoManage.MaterialQualityStatus.Add";
        public const string MaterialQualityStatus_Update = "BasicInfoManage.MaterialQualityStatus.Update";
        public const string MaterialQualityStatus_Get = "BasicInfoManage.MaterialQualityStatus.Get";
        public const string MaterialQualityStatus_Delete = "BasicInfoManage.MaterialQualityStatus.Delete";
        public const string MaterialQualityStatus_Export = "BasicInfoManage.MaterialQualityStatus.Export";
        public const string MaterialQualityStatus_Print = "BasicInfoManage.MaterialQualityStatus.Print";
        #endregion

        #region 客户类别信息
        public const string CustomerCategoryInfo = "basic-customType";
        //按钮权限标识
        public const string CustomTypeInfo_Add = "BasicInfoManage.CustomTypeInfo.Add";
        public const string CustomTypeInfo_Update = "BasicInfoManage.CustomTypeInfo.Update";
        public const string CustomTypeInfo_Get = "BasicInfoManage.CustomTypeInfo.Get";
        public const string CustomTypeInfo_Delete = "BasicInfoManage.CustomTypeInfo.Delete";
        public const string CustomTypeInfo_Export = "BasicInfoManage.CustomTypeInfo.Export";
        public const string CustomTypeInfo_Print = "BasicInfoManage.CustomTypeInfo.Print";

        #endregion

        #region 客户基础信息
        public const string CustomerBaseInfo = "basic-custom";
        //按钮权限标识
        public const string CustomInfo_Add = "BasicInfoManage.CustomInfo.Add";
        public const string CustomInfo_Update = "BasicInfoManage.CustomInfo.Update";
        public const string CustomInfo_Get = "BasicInfoManage.CustomInfo.Get";
        public const string CustomInfo_Delete = "BasicInfoManage.CustomInfo.Delete";
        public const string CustomInfo_Export = "BasicInfoManage.CustomInfo.Export";
        public const string CustomInfo_Print = "BasicInfoManage.CustomInfo.Print";
        #endregion

        #region 仓库基础信息
        public const string WarehouseBaseInfo = "basic-warehouse";
        public const string WarehoueInfo_Add = "BasicInfoManage.WarehouseInfo.Add";
        public const string WarehoueInfo_Update = "BasicInfoManage.WarehouseInfo.Update";
        public const string WarehoueInfo_Get = "BasicInfoManage.WarehouseInfo.Get";
        public const string WarehoueInfo_Delete = "BasicInfoManage.WarehouseInfo.Delete";
        public const string WarehoueInfo_Export = "BasicInfoManage.WarehouseInfo.Export";
        public const string WarehoueInfo_Print = "BasicInfoManage.WarehouseInfo.Print";
        #endregion

        #region 库区基础信息
        public const string AreaBasicInfo = "basic-area";
        public const string AreaBasicInfo_Add = "BasicInfoManage.AreaBasicInfo.Add";
        public const string AreaBasicInfo_Update = "BasicInfoManage.AreaBasicInfo.Update";
        public const string AreaBasicInfo_Get = "BasicInfoManage.AreaBasicInfo.Get";
        public const string AreaBasicInfo_Delete = "BasicInfoManage.AreaBasicInfo.Delete";
        public const string AreaBasicInfo_Export = "BasicInfoManage.AreaBasicInfo.Export";
        public const string AreaBasicInfo_Print = "BasicInfoManage.AreaBasicInfo.Print";
        #endregion

        #region 库位基础信息
        public const string SlotBasicInfo = "basic-slot";
        public const string SlotBasicInfo_Add = "BasicInfoManage.SlotBasicInfo.Add";
        public const string SlotBasicInfo_Update = "BasicInfoManage.SlotBasicInfo.Update";
        public const string SlotBasicInfo_Get = "BasicInfoManage.SlotBasicInfo.Get";
        public const string SlotBasicInfo_Delete = "BasicInfoManage.SlotBasicInfo.Delete";
        public const string SlotBasicInfo_Export = "BasicInfoManage.SlotBasicInfo.Export";
        public const string SlotBasicInfo_Print = "BasicInfoManage.SlotBasicInfo.Print";
        //入库锁定按钮
        public const string SlotBasicInfo_ImportLock = "BasicInfoManage.SlotBasicInfo.ImportLock";
        //出库锁定按钮
        public const string SlotBasicInfo_ExportLock = "BasicInfoManage.SlotBasicInfo.ExportLock";
        //屏蔽按钮
        public const string SlotBasicInfo_Shield = "BasicInfoManage.SlotBasicInfo.Shield";
        //区域选择
        public const string SlotBasicInfo_AreaSelect = "BasicInfoManage.SlotBasicInfo.AreaSelect";
        //批量修改
        public const string SlotBasicInfo_BatchUpdate = "BasicInfoManage.SlotBasicInfo.BatchUpdate";
        #endregion

        #region 单据类型信息
        public const string BillTypeInfo = "basic-bill";
        public const string BillTypeInfo_Add = "BasicInfoManage.BillTypeInfo.Add";
        public const string BillTypeInfo_Update = "BasicInfoManage.BillTypeInfo.Update";
        public const string BillTypeInfo_Get = "BasicInfoManage.BillTypeInfo.Get";
        public const string BillTypeInfo_Delete = "BasicInfoManage.BillTypeInfo.Delete";
        public const string BillTypeInfo_Export = "BasicInfoManage.BillTypeInfo.Export";
        public const string BillTypeInfo_Print = "BasicInfoManage.BillTypeInfo.Print";
        #endregion

        #region 出入口基础信息
        public const string InOutdBasicInfo = "basic-port";
        public const string InOutdBasicInfo_Add = "BasicInfoManage.InOutdBasicInfo.Add";
        public const string InOutdBasicInfo_Update = "BasicInfoManage.InOutdBasicInfo.Update";
        public const string InOutdBasicInfo_Get = "BasicInfoManage.InOutdBasicInfo.Get";
        public const string InOutdBasicInfo_Delete = "BasicInfoManage.InOutdBasicInfo.Delete";
        public const string InOutdBasicInfo_Export = "BasicInfoManage.InOutdBasicInfo.Export";
        public const string InOutdBasicInfo_Print = "BasicInfoManage.InOutdBasicInfo.Print";
        public const string InOutdBasicInfo_SetTunnel = "BasicInfoManage.InOutdBasicInfo.SetTunnel";
        #endregion

        #region 月台基础信息
        public const string PlatFormBasicInfo = "basic-platForm";
        public const string PlatFormBasicInfo_Add = "BasicInfoManage.PlatFormBasicInfo.Add";
        public const string PlatFormBasicInfo_Update = "BasicInfoManage.PlatFormBasicInfo.Update";
        public const string PlatFormBasicInfo_Get = "BasicInfoManage.PlatFormBasicInfo.Get";
        public const string PlatFormBasicInfo_Delete = "BasicInfoManage.PlatFormBasicInfo.Delete";
        public const string PlatFormBasicInfo_Export = "BasicInfoManage.PlatFormBasicInfo.Export";
        public const string PlatFormBasicInfo_Print = "BasicInfoManage.PlatFormBasicInfo.Print";
        #endregion

        #region 垛形基础信息
        public const string PackBasicInfo = "basic-pack";
        public const string PackBasicInfo_Add = "BasicInfoManage.PackBasicInfo.Add";
        public const string PackBasicInfo_Update = "BasicInfoManage.PackBasicInfo.Update";
        public const string PackBasicInfo_Get = "BasicInfoManage.PackBasicInfo.Get";
        public const string PackBasicInfo_Delete = "BasicInfoManage.PackBasicInfo.Delete";
        public const string PackBasicInfo_Export = "BasicInfoManage.PackBasicInfo.Export";
        public const string PackBasicInfo_Print = "BasicInfoManage.PackBasicInfo.Print";
        #endregion

        #region 系统管理中心
        //公司基础信息
        public const string CompanyBasicInfo = "system-company";
        public const string CompanyBasicInfo_Add = "SystemManageCenter.CompanyBasicInfo.Add";
        public const string CompanyBasicInfo_Update = "SystemManageCenter.CompanyBasicInfo.Update";
        public const string CompanyBasicInfo_Get = "SystemManageCenter.CompanyBasicInfo.Get";
        public const string CompanyBasicInfo_Delete = "SystemManageCenter.CompanyBasicInfo.Delete";
        public const string CompanyBasicInfo_Export = "SystemManageCenter.CompanyBasicInfo.Export";
        public const string CompanyBasicInfo_Print = "SystemManageCenter.CompanyBasicInfo.Print";

        //部门基础信息
        public const string DepartmentBasicInfo = "system-department";
        public const string DepartmentBasicInfo_Add = "SystemManageCenter.DepartmentBasicInfo.Add";
        public const string DepartmentBasicInfo_Update = "SystemManageCenter.DepartmentBasicInfo.Update";
        public const string DepartmentBasicInfo_Get = "SystemManageCenter.DepartmentBasicInfo.Get";
        public const string DepartmentBasicInfo_Delete = "SystemManageCenter.DepartmentBasicInfo.Delete";
        public const string DepartmentBasicInfo_Export = "SystemManageCenter.DepartmentBasicInfo.Export";
        public const string DepartmentBasicInfo_Print = "SystemManageCenter.DepartmentBasicInfo.Print";

        //人员基础信息
        public const string StaffBasicInfo = "system-staff";
        public const string StaffBasicInfo_Add = "SystemManageCenter.StaffBasicInfo.Add";
        public const string StaffBasicInfo_Update = "SystemManageCenter.StaffBasicInfo.Update";
        public const string StaffBasicInfo_Get = "SystemManageCenter.StaffBasicInfo.Get";
        public const string StaffBasicInfo_Delete = "SystemManageCenter.StaffBasicInfo.Delete";
        public const string StaffBasicInfo_Export = "SystemManageCenter.StaffBasicInfo.Export";
        public const string StaffBasicInfo_Print = "SystemManageCenter.StaffBasicInfo.Print";
        //启用按钮
        public const string StaffBasicInfo_Enable = "SystemManageCenter.StaffBasicInfo.Enable";
        //禁用按钮
        public const string StaffBasicInfo_Forbidden = "SystemManageCenter.StaffBasicInfo.Forbidden";


        //角色基础信息
        public const string RoleBasicInfo = "system-role";
        public const string RoleBasicInfo_Add = "SystemManageCenter.RoleBasicInfo.Add";
        public const string RoleBasicInfo_Update = "SystemManageCenter.RoleBasicInfo.Update";
        public const string RoleBasicInfo_Get = "SystemManageCenter.RoleBasicInfo.Get";
        public const string RoleBasicInfo_Delete = "SystemManageCenter.RoleBasicInfo.Delete";
        public const string RoleBasicInfo_Export = "SystemManageCenter.RoleBasicInfo.Export";
        public const string RoleBasicInfo_Print = "SystemManageCenter.RoleBasicInfo.Print";
        //启用按钮
        public const string RoleBasicInfo_Enable = "SystemManageCenter.RoleBasicInfo.Enable";
        //禁用按钮
        public const string RoleBasicInfo_Forbidden = "SystemManageCenter.RoleBasicInfo.Forbidden";


        //系统模块管理
        public const string SystemModuleManage = "system-system";
        public const string SystemModuleManage_Add = "SystemManageCenter.SystemModuleManage.Add";
        public const string SystemModuleManage_Update = "SystemManageCenter.SystemModuleManage.Update";
        public const string SystemModuleManage_Get = "SystemManageCenter.SystemModuleManage.Get";
        public const string SystemModuleManage_Delete = "SystemManageCenter.SystemModuleManage.Delete";
        public const string SystemModuleManage_Export = "SystemManageCenter.SystemModuleManage.Export";
        public const string SystemModuleManage_Print = "SystemManageCenter.SystemModuleManage.Print";
        //启用按钮
        public const string SystemModuleManage_Enable = "SystemManageCenter.SystemModuleManage.Enable";
        //禁用按钮
        public const string SystemModuleManage_Forbidden = "SystemManageCenter.SystemModuleManage.Forbidden";


        //移动模块管理
        public const string MoveModuleManage = "system-move";
        public const string MoveModuleManage_Add = "SystemManageCenter.MoveModuleManage.Add";
        public const string MoveModuleManage_Update = "SystemManageCenter.MoveModuleManage.Update";
        public const string MoveModuleManage_Get = "SystemManageCenter.MoveModuleManage.Get";
        public const string MoveModuleManage_Delete = "SystemManageCenter.MoveModuleManage.Delete";
        public const string MoveModuleManage_Export = "SystemManageCenter.MoveModuleManage.Export";
        public const string MoveModuleManage_Print = "SystemManageCenter.MoveModuleManage.Print";
        //启用按钮
        public const string MoveModuleManage_Enable = "SystemManageCenter.MoveModuleManage.Enable";
        //禁用按钮
        public const string MoveModuleManage_Forbidden = "SystemManageCenter.MoveModuleManage.Forbidden";


        //仓库基础管理
        public const string WarehouseBasicManage = "system-warehouse";
        public const string WarehouseBasicManage_Add = "SystemManageCenter.WarehouseBasicManage.Add";
        public const string WarehouseBasicManage_Update = "SystemManageCenter.WarehouseBasicManage.Update";
        public const string WarehouseBasicManage_Get = "SystemManageCenter.WarehouseBasicManage.Get";
        public const string WarehouseBasicManage_Delete = "SystemManageCenter.WarehouseBasicManage.Delete";
        public const string WarehouseBasicManage_Export = "SystemManageCenter.WarehouseBasicManage.Export";
        public const string WarehouseBasicManage_Print = "SystemManageCenter.WarehouseBasicManage.Print";


        //库位初始化
        public const string SlotInit = "system-slot";
        public const string SlotInit_Add = "SystemManageCenter.SlotInit.Add";
        public const string SlotInit_Update = "SystemManageCenter.SlotInit.Update";
        public const string SlotInit_Get = "SystemManageCenter.SlotInit.Get";
        public const string SlotInit_Delete = "SystemManageCenter.SlotInit.Delete";
        public const string SlotInit_Export = "SystemManageCenter.SlotInit.Export";
        public const string SlotInit_Print = "SystemManageCenter.SlotInit.Print";
        //库位生成按钮
        public const string SlotInit_Create = "SystemManageCenter.SlotInit.Create";

        #endregion

        #region  入库管理中心
        //入库单据管理
        public const string ImportBillManage = "importStock-bill";
        public const string ImportBillManage_Add = "ImportWarehouseManage.ImportBillManage.Add";
        public const string ImportBillManage_Update = "ImportWarehouseManage.ImportBillManage.Update";
        public const string ImportBillManage_Get = "ImportWarehouseManage.ImportBillManage.Get";
        public const string ImportBillManage_Delete = "ImportWarehouseManage.ImportBillManage.Delete";
        public const string ImportBillManage_Export = "ImportWarehouseManage.ImportBillManage.Export";
        public const string ImportBillManage_Print = "ImportWarehouseManage.ImportBillManage.Print";
        //审核按钮
        public const string ImportBillManage_Examine = "ImportWarehouseManage.ImportBillManage.Examine";
        //弃审按钮
        public const string ImportBillManage_UnExamined = "ImportWarehouseManage.ImportBillManage.UnExamined";
        //作废按钮
        public const string ImportBillManage_Cancle = "ImportWarehouseManage.ImportBillManage.Cancle";
        //一键走账
        public const string ImportBillManage_OneKey = "ImportWarehouseManage.ImportBillManage.OneKey";
        //单据完结
        public const string ImportBillManage_Finish = "ImportWarehouseManage.ImportBillManage.Finish";
        //生成任务
        public const string ImportBillManage_Task = "ImportWarehouseManage.ImportBillManage.Task";
        //单据打印
        public const string ImportBillManage_BillPrint = "ImportWarehouseManage.ImportBillManage.BillPrint";

       
        
        
        //入库流水管理
        public const string ImportBillBodyManage = "importStock-billBody";
        public const string ImportBillBodyManage_Add = "ImportWarehouseManage.ImportBillBodyManage.Add";
        public const string ImportBillBodyManage_Update = "ImportWarehouseManage.ImportBillBodyManage.Update";
        public const string ImportBillBodyManage_Get = "ImportWarehouseManage.ImportBillBodyManage.Get";
        public const string ImportBillBodyManage_Delete = "ImportWarehouseManage.ImportBillBodyManage.Delete";
        public const string ImportBillBodyManage_Export = "ImportWarehouseManage.ImportBillBodyManage.Export";
        public const string ImportBillBodyManage_Print = "ImportWarehouseManage.ImportBillBodyManage.Print";
        //模拟扫描按钮
        public const string ImportBillBodyManage_AnalogScan = "ImportWarehouseManage.ImportBillBodyManage.AnalogScan";
        //作废按钮
        public const string ImportBillBodyManage_Cancle = "ImportWarehouseManage.ImportBillBodyManage.Cancle";
        public const string ImportBillBodyManage_Task = "ImportWarehouseManage.ImportBillBodyManage.Task";


        //空托盘入库流水
        public const string EmptyStockImport = "importStock-stock";
        public const string EmptyStockImport_Add = "ImportWarehouseManage.EmptyStockImport.Add";
        public const string EmptyStockImport_Update = "ImportWarehouseManage.EmptyStockImport.Update";
        public const string EmptyStockImport_Get = "ImportWarehouseManage.EmptyStockImport.Get";
        public const string EmptyStockImport_Delete = "ImportWarehouseManage.EmptyStockImport.Delete";
        public const string EmptyStockImport_Export = "ImportWarehouseManage.EmptyStockImport.Export";
        public const string EmptyStockImport_Print = "ImportWarehouseManage.EmptyStockImport.Print";
        //生成任务按钮
        public const string EmptyStockImport_Task = "ImportWarehouseManage.EmptyStockImport.Task";
        #endregion

        #region  出库管理中心
        //出库单据管理
        public const string ExportBillManage = "exportStock-bill";
        public const string ExportBillManage_Add = "ExportWarehouseManage.ExportBillManage.Add";
        public const string ExportBillManage_Update = "ExportWarehouseManage.ExportBillManage.Update";
        public const string ExportBillManage_Get = "ExportWarehouseManage.ExportBillManage.Get";
        public const string ExportBillManage_Delete = "ExportWarehouseManage.ExportBillManage.Delete";
        public const string ExportBillManage_Export = "ExportWarehouseManage.ExportBillManage.Export";
        public const string ExportBillManage_Print = "ExportWarehouseManage.ExportBillManage.Print";
        //设定波次
        public const string ExportBillManage_Wave = "ExportWarehouseManage.ExportBillManage.Wave";
        //自动出库
        public const string ExportBillManage_Auto = "ExportWarehouseManage.ExportBillManage.Auto";
        //手动出库
        public const string ExportBillManage_Manual = "ExportWarehouseManage.ExportBillManage.Manual";
        //一键走账
        public const string ExportBillManage_OneKey = "ExportWarehouseManage.ExportBillManage.OneKey";
        //单据完结
        public const string ExportBillManage_Finish = "ExportWarehouseManage.ExportBillManage.Finish";
        //单据打印
        public const string ExportBillManage_BillPrint = "ExportWarehouseManage.ExportBillManage.BillPrint";

      
        
        //出库流水管理
        public const string ExportBillBodyManage = "exportStock-billBody";
        public const string ExportBillBodyManage_Add = "ExportWarehouseManage.ExportBillBodyManage.Add";
        public const string ExportBillBodyManage_Update = "ExportWarehouseManage.ExportBillBodyManage.Update";
        public const string ExportBillBodyManage_Get = "ExportWarehouseManage.ExportBillBodyManage.Get";
        public const string ExportBillBodyManage_Delete = "ExportWarehouseManage.ExportBillBodyManage.Delete";
        public const string ExportBillBodyManage_Export = "ExportWarehouseManage.ExportBillBodyManage.Export";
        public const string ExportBillBodyManage_Print = "ExportWarehouseManage.ExportBillBodyManage.Print";
        //复核按钮
        public const string ExportBillBodyManage_Review = "ExportWarehouseManage.ExportBillBodyManage.Review";

        //空托盘出库流水
        public const string EmptyStockExport = "exportStock-stock";
        public const string EmptyStockExport_Add = "ExportWarehouseManage.EmptyStockExport.Add";
        public const string EmptyStockExport_Update = "ExportWarehouseManage.EmptyStockExport.Update";
        public const string EmptyStockExport_Get = "ExportWarehouseManage.EmptyStockExport.Get";
        public const string EmptyStockExport_Delete = "ExportWarehouseManage.EmptyStockExport.Delete";
        public const string EmptyStockExport_Export = "ExportWarehouseManage.EmptyStockExport.Export";
        public const string EmptyStockExport_Print = "ExportWarehouseManage.EmptyStockExport.Print";
        #endregion

        #region 库存管理中心
        //盘点管理
        public const string InventoryStockingManage = "Inventory-stocking";
        public const string InventoryStockingManage_Add = "InventoryManage.InventoryStockingManage.Add";
        public const string InventoryStockingManage_Update = "InventoryManage.InventoryStockingManage.Update";
        public const string InventoryStockingManage_Get = "InventoryManage.InventoryStockingManage.Get";
        public const string InventoryStockingManage_Delete = "InventoryManage.InventoryStockingManage.Delete";
        public const string InventoryStockingManage_Export = "InventoryManage.InventoryStockingManage.Export";
        public const string InventoryStockingManage_Print = "InventoryManage.InventoryStockingManage.Print";
        //生成任务按钮
        public const string InventoryStockingManage_Task = "InventoryManage.InventoryStockingManage.Task";

        //可视化库存管理
        public const string VisualizationManage = "Inventory-visualization";
        public const string VisualizationManage_Add = "InventoryManage.VisualizationManage.Add";
        public const string VisualizationManage_Update = "InventoryManage.VisualizationManage.Update";
        public const string VisualizationManage_Get = "InventoryManage.VisualizationManage.Get";
        public const string VisualizationManage_Delete = "InventoryManage.VisualizationManage.Delete";
        public const string VisualizationManage_Export = "InventoryManage.VisualizationManage.Export";
        public const string VisualizationManage_Print = "InventoryManage.VisualizationManage.Print";
        //总库存管理
        public const string TotalInventoryManage = "Inventory-totalinventory";
        public const string TotalInventoryManage_Add = "InventoryManage.TotalInventoryManage.Add";
        public const string TotalInventoryManage_Update = "InventoryManage.TotalInventoryManage.Update";
        public const string TotalInventoryManage_Get = "InventoryManage.TotalInventoryManage.Get";
        public const string TotalInventoryManage_Delete = "InventoryManage.TotalInventoryManage.Delete";
        public const string TotalInventoryManage_Export = "InventoryManage.TotalInventoryManage.Export";
        public const string TotalInventoryManage_Print = "InventoryManage.TotalInventoryManage.Print";
        //库位库存管理
        public const string SlotInventoryManage = "Inventory-slotinventory";
        public const string SlotInventoryManage_Add = "InventoryManage.SlotInventoryManage.Add";
        public const string SlotInventoryManage_Update = "InventoryManage.SlotInventoryManage.Update";
        public const string SlotInventoryManage_Get = "InventoryManage.SlotInventoryManage.Get";
        public const string SlotInventoryManage_Delete = "InventoryManage.SlotInventoryManage.Delete";
        public const string SlotInventoryManage_Export = "InventoryManage.SlotInventoryManage.Export";
        public const string SlotInventoryManage_Print = "InventoryManage.SlotInventoryManage.Print";
        //托盘库存管理
        public const string StockInventoryManage = "Inventory-stockinventory";
        public const string StockInventoryManage_Add = "InventoryManage.StockInventoryManage.Add";
        public const string StockInventoryManage_Update = "InventoryManage.StockInventoryManage.Update";
        public const string StockInventoryManage_Get = "InventoryManage.StockInventoryManage.Get";
        public const string StockInventoryManage_Delete = "InventoryManage.StockInventoryManage.Delete";
        public const string StockInventoryManage_Export = "InventoryManage.StockInventoryManage.Export";
        public const string StockInventoryManage_Print = "InventoryManage.StockInventoryManage.Print";
        //生成任务按钮
        public const string StockInventoryManage_Task = "InventoryManage.StockInventoryManage.Task";
        #endregion

        #region 库存预警中心
        //呆滞库存预警 
        public const string DullStockWarning = "warehouseWarning-dull";
        public const string DullStockWarning_Export = "WarehouseWarning.DullStockWarning.Export";
        public const string DullStockWarning_Print = "WarehouseWarning.DullStockWarning.Print";

        //效期库存预警
        public const string ValidityStockWarning = "warehouseWarning-validity";
        public const string ValidityStockWarning_Export = "WarehouseWarning.ValidityStockWarning.Export";
        public const string ValidityStockWarning_Print = "WarehouseWarning.ValidityStockWarning.Print";

        //库存阈值预警
        public const string InventoryThresholdWarning = "warehouseWarning-threshold";
        public const string InventoryThresholdWarning_Export = "WarehouseWarning.InventoryThresholdWarning.Export";
        public const string InventoryThresholdWarning_Print = "WarehouseWarning.InventoryThresholdWarning.Print";

        //入库异常预警
        public const string ImportExceptionWarning = "warehouseWarning-imports";
        public const string ImportExceptionWarning_Export = "WarehouseWarning.ImportExceptionWarning.Export";
        public const string ImportExceptionWarning_Print = "WarehouseWarning.ImportExceptionWarning.Print";

        //出库异常预警
        public const string ExportExceptionWarning = "warehouseWarning-exports";
        public const string ExportExceptionWarning_Export = "WarehouseWarning.ExportExceptionWarning.Export";
        public const string ExportExceptionWarning_Print = "WarehouseWarning.ExportExceptionWarning.Print";

        //库存异常预警
        public const string InventoryExceptionWarning = "warehouseWarning-inventory";
        public const string InventoryExceptionWarning_Export = "WarehouseWarning.InventoryExceptionWarning.Export";
        public const string InventoryExceptionWarning_Print = "WarehouseWarning.InventoryExceptionWarning.Print";
        #endregion

        #region 仓库报表中心
        //出入库台账
        public const string ExportImportStandingBook = "warehouseReport-standingBook";
        public const string ExportImportStandingBook_Export = "WarehouseReport.ExportImportStandingBook.Export";
        public const string ExportImportStandingBook_Print = "WarehouseReport.ExportImportStandingBook.Print";
        //货位利用率
        public const string StorageUseRatio = "warehouseReport-useRatio";
        public const string StorageUseRatio_Export = "WarehouseReport.StorageUseRatio.Export";
        public const string StorageUseRatio_Print = "WarehouseReport.StorageUseRatio.Print";
        //入库报表
        public const string ImportReportForm = "warehouseReport-importReport";
        public const string ImportReportForm_Export = "WarehouseReport.ImportReportForm.Export";
        public const string ImportReportForm_Print = "WarehouseReport.ImportReportForm.Print";
        //出库报表
        public const string ExportReportForm = "warehouseReport-exportReport";
        public const string ExportReportForm_Export = "WarehouseReport.ExportReportForm.Export";
        public const string ExportReportForm_Print = "WarehouseReport.ExportReportForm.Print";
        //库存报表
        public const string InventoryReportForm = "warehouseReport-storageReport";
        public const string InventoryReportForm_Export = "WarehouseReport.InventoryReportForm.Export";
        public const string InventoryReportForm_Print = "WarehouseReport.InventoryReportForm.Print";
        //自定义报表
        public const string CustomReportForm = "warehouseReport-customReport";
        public const string CustomReportForm_Export = "WarehouseReport.CustomReportForm.Export";
        public const string CustomReportForm_Print = "WarehouseReport.CustomReportForm.Print";
        #endregion

        #region 任务管理中心
        //立库任务管理
        #region
        public const string MainTaskManage = "task-maintask";
        public const string MainTaskManage_Add = "TaskManage.MainTaskManage.Add";
        public const string MainTaskManage_Update = "TaskManage.MainTaskManage.Update";
        public const string MainTaskManage_Get = "TaskManage.MainTaskManage.Get";
        public const string MainTaskManage_Delete = "TaskManage.MainTaskManage.Delete";
        public const string MainTaskManage_Export = "TaskManage.MainTaskManage.Export";
        public const string MainTaskManage_Print = "TaskManage.MainTaskManage.Print";
        //开始按钮
        public const string MainTaskManage_Start = "TaskManage.MainTaskManage.Start";
        //暂停按钮
        public const string MainTaskManage_Pause = "TaskManage.MainTaskManage.Pause";
        //恢复按钮
        public const string MainTaskManage_Resume = "TaskManage.MainTaskManage.Resume";
        //完成按钮
        public const string MainTaskManage_Finish = "TaskManage.MainTaskManage.Finishs";
        //取消按钮
        public const string MainTaskManage_Cancle = "TaskManage.MainTaskManage.Cancles";
        //调整优先级
        public const string MainTaskManage_Order = "TaskManage.MainTaskManage.Order";
        //手动生成
        public const string MainTaskManage_Manual = "TaskManage.MainTaskManage.Manuals";
        //修改状态
        public const string MainTaskManage_Modify = "TaskManage.MainTaskManage.Modify";
        //查看
        public const string MainTaskManage_Look = "TaskManage.MainTaskManage.Look";
        #endregion


        //立库历史任务
        #region
        public const string MainHistorytask = "task-taskManage";
        public const string MainHistorytask_Add = "TaskManage.MainHistorytask.Add";
        public const string MainHistorytask_Update = "TaskManage.MainHistorytask.Update";
        public const string MainHistorytask_Get = "TaskManage.MainHistorytask.Get";
        public const string MainHistorytask_Delete = "TaskManage.MainHistorytask.Delete";
        public const string MainHistorytask_Export = "TaskManage.MainHistorytask.Export";
        public const string MainHistorytask_Print = "TaskManage.MainHistorytask.Print";
        #endregion

        //平库任务管理
        #region
        public const string FlatBankTask = "task-flatBankTask";
        public const string FlatBankTask_Add = "TaskManage.FlatBankTask.Add";
        public const string FlatBankTask_Update = "TaskManage.FlatBankTask.Update";
        public const string FlatBankTask_Get = "TaskManage.FlatBankTask.Get";
        public const string FlatBankTask_Delete = "TaskManage.FlatBankTask.Delete";
        public const string FlatBankTask_Export = "TaskManage.FlatBankTask.Export";
        public const string FlatBankTask_Print = "TaskManage.FlatBankTask.Print";
        //开始按钮
        public const string FlatBankTask_Start = "TaskManage.FlatBankTask.Start";
        //暂停按钮
        public const string FlatBankTask_Pause = "TaskManage.FlatBankTask.Pause";
        //恢复按钮
        public const string FlatBankTask_Resume = "TaskManage.FlatBankTask.Resume";
        //完成按钮
        public const string FlatBankTask_Finish = "TaskManage.FlatBankTask.Finishs";
        //取消按钮
        public const string FlatBankTask_Cancle = "TaskManage.FlatBankTask.Cancles";
        //调整优先级
        public const string FlatBankTask_Order = "TaskManage.FlatBankTask.Order";
        //手动生成
        public const string FlatBankTask_Manual = "TaskManage.FlatBankTask.Manuals";
        //修改状态
        public const string FlatBankTask_Modify = "TaskManage.FlatBankTask.Modify";
        //查看
        public const string FlatBankTask_Look = "TaskManage.FlatBankTask.Look";
        #endregion
        #endregion

        //拣选任务管理
        #region
        public const string PickingTask = "task-pickingTask";
        public const string PickingTask_Add = "TaskManage.PickingTask.Add";
        public const string PickingTask_Update = "TaskManage.PickingTask.Update";
        public const string PickingTask_Get = "TaskManage.PickingTask.Get";
        public const string PickingTask_Delete = "TaskManage.PickingTask.Delete";
        public const string PickingTask_Export = "TaskManage.PickingTask.Export";
        public const string PickingTask_Print = "TaskManage.PickingTask.Print";
        //开始按钮
        public const string PickingTask_Start = "TaskManage.PickingTask.Start";
        //暂停按钮
        public const string PickingTask_Pause = "TaskManage.PickingTask.Pause";
        //恢复按钮
        public const string PickingTask_Resume = "TaskManage.PickingTask.Resume";
        //完成按钮
        public const string PickingTask_Finish = "TaskManage.PickingTask.Finishs";
        //取消按钮
        public const string PickingTask_Cancle = "TaskManage.PickingTask.Cancles";
        //调整优先级
        public const string PickingTask_Order = "TaskManage.PickingTask.Order";
        //手动生成
        public const string PickingTask_Manual = "TaskManage.PickingTask.Manuals";
        //修改状态
        public const string PickingTask_Modify = "TaskManage.PickingTask.Modify";
        //查看
        public const string PickingTask_Look = "TaskManage.PickingTask.Look";
        #endregion

        //AGV任务管理
        #region
        public const string AGVTask = "task-AGVTask";
        public const string AGVTask_Add = "TaskManage.AGVTask.Add";
        public const string AGVTask_Update = "TaskManage.AGVTask.Update";
        public const string AGVTask_Get = "TaskManage.AGVTask.Get";
        public const string AGVTask_Delete = "TaskManage.AGVTask.Delete";
        public const string AGVTask_Export = "TaskManage.AGVTask.Export";
        public const string AGVTask_Print = "TaskManage.AGVTask.Print";
        //开始按钮
        public const string AGVTask_Start = "TaskManage.AGVTask.Start";
        //暂停按钮
        public const string AGVTask_Pause = "TaskManage.AGVTask.Pause";
        //恢复按钮
        public const string AGVTask_Resume = "TaskManage.AGVTask.Resume";
        //完成按钮
        public const string AGVTask_Finish = "TaskManage.AGVTask.Finishs";
        //取消按钮
        public const string AGVTask_Cancle = "TaskManage.AGVTask.Cancles";
        //调整优先级
        public const string AGVTask_Order = "TaskManage.AGVTask.Order";
        //手动生成
        public const string AGVTask_Manual = "TaskManage.AGVTask.Manuals";
        //修改状态
        public const string AGVTask_Modify = "TaskManage.AGVTask.Modify";
        //查看
        public const string AGVTask_Look = "TaskManage.AGVTask.Look";
        #endregion

        //RGV任务管理
        #region
        public const string RGVTask = "task-RGVTask";
        public const string RGVTask_Add = "TaskManage.RGVTask.Add";
        public const string RGVTask_Update = "TaskManage.RGVTask.Update";
        public const string RGVTask_Get = "TaskManage.RGVTask.Get";
        public const string RGVTask_Delete = "TaskManage.RGVTask.Delete";
        public const string RGVTask_Export = "TaskManage.RGVTask.Export";
        public const string RGVTask_Print = "TaskManage.RGVTask.Print";
        //开始按钮
        public const string RGVTask_Start = "TaskManage.RGVTask.Start";
        //暂停按钮
        public const string RGVTask_Pause = "TaskManage.RGVTask.Pause";
        //恢复按钮
        public const string RGVTask_Resume = "TaskManage.RGVTask.Resume";
        //完成按钮
        public const string RGVTask_Finish = "TaskManage.RGVTask.Finishs";
        //取消按钮
        public const string RGVTask_Cancle = "TaskManage.RGVTask.Cancles";
        //调整优先级
        public const string RGVTask_Order = "TaskManage.RGVTask.Order";
        //手动生成
        public const string RGVTask_Manual = "TaskManage.RGVTask.Manuals";
        //修改状态
        public const string RGVTask_Modify = "TaskManage.RGVTask.Modify";
        //查看
        public const string RGVTask_Look = "TaskManage.RGVTask.Look";
        #endregion

        #region 业务管理中心
        //监控策略管理
        public const string StrategyMonitorManage = "businessManage-strategyMonitor";
        public const string StrategyMonitorManage_Add = "BusinessManage.StrategyMonitorManage.Add";
        public const string StrategyMonitorManage_Update = "BusinessManage.StrategyMonitorManage.Update";
        public const string StrategyMonitorManage_Get = "BusinessManage.StrategyMonitorManage.Get";
        public const string StrategyMonitorManage_Delete = "BusinessManage.StrategyMonitorManage.Delete";
        public const string StrategyMonitorManage_Export = "BusinessManage.StrategyMonitorManage.Export";
        public const string StrategyMonitorManage_Print = "BusinessManage.StrategyMonitorManage.Print";

        //上架策略管理
        public const string StrategyPutawayManage = "businessManage-strategyWarehousing";
        public const string StrategyPutawayManage_Add = "BusinessManage.StrategyPutawayManage.Add";
        public const string StrategyPutawayManage_Update = "BusinessManage.StrategyPutawayManage.Update";
        public const string StrategyPutawayManage_Get = "BusinessManage.StrategyPutawayManage.Get";
        public const string StrategyPutawayManage_Delete = "BusinessManage.StrategyPutawayManage.Delete";
        public const string StrategyPutawayManage_Export = "BusinessManage.StrategyPutawayManage.Export";
        public const string StrategyPutawayManage_Print = "BusinessManage.StrategyPutawayManage.Print";

        //分配策略管理
        public const string StrategyDistriManage = "businessManage-strategyDistribution";
        public const string StrategyDistriManage_Add = "BusinessManage.StrategyDistriManage.Add";
        public const string StrategyDistriManage_Update = "BusinessManage.StrategyDistriManage.Update";
        public const string StrategyDistriManage_Get = "BusinessManage.StrategyDistriManage.Get";
        public const string StrategyDistriManage_Delete = "BusinessManage.StrategyDistriManage.Delete";
        public const string StrategyDistriManage_Export = "BusinessManage.StrategyDistriManage.Export";
        public const string StrategyDistriManage_Print = "BusinessManage.StrategyDistriManage.Print";

        //编码规则管理
        public const string EncodingRuleManage = "businessManage-encodingRule";
        public const string EncodingRuleManage_Add = "BusinessManage.EncodingRuleManage.Add";
        public const string EncodingRuleManage_Update = "BusinessManage.EncodingRuleManage.Update";
        public const string EncodingRuleManage_Get = "BusinessManage.EncodingRuleManage.Get";
        public const string EncodingRuleManage_Delete = "BusinessManage.EncodingRuleManage.Delete";
        public const string EncodingRuleManage_Export = "BusinessManage.EncodingRuleManage.Export";
        public const string EncodingRuleManage_Print = "BusinessManage.EncodingRuleManage.Print";
        #endregion

        #region 日志管理中心
        //登录日志管理
        public const string LoginLogManage = "logmanage-loginlog";
        public const string LoginLogManage_Export = "LogManage.LoginLogManage.Export";
        public const string LoginLogManage_Print = "LogManage.LoginLogManage.Print";
        //操作日志管理
        public const string OptLogManage = "logmanage-optlog";
        public const string OptLogManage_Export = "LogManage.OptLogManage.Export";
        public const string OptLogManage_Print = "LogManage.OptLogManage.Print";
        //设备报警日志
        public const string MachineAlertLog = "logmanage-machinealert";
        public const string MachineAlertLog_Export = "LogManage.MachineAlertLog.Export";
        public const string MachineAlertLog_Print = "LogManage.MachineAlertLog.Print";
        //入库申请日志
        public const string ImportApplyLog = "logmanage-importApplyLog";
        public const string ImportApplyLog_Export = "LogManage.ImportApplyLog.Export";
        public const string ImportApplyLog_Print = "LogManage.ImportApplyLog.Print";
        //出库单据同步日志
        public const string ExportBillSyncLog = "logmanage-exportBillSyncLog";
        public const string ExportBillSyncLog_Export = "LogManage.ExportBillSyncLog.Export";
        public const string ExportBillSyncLog_Print = "LogManage.ExportBillSyncLog.Print";
        #endregion

        #region 质量管理中心
        public const string QualityCheckout = "quality-qualitycheckout";
        public const string QualityCheckout_Add = "QualityManagement.QualityCheckout.Add";
        public const string QualityCheckout_Update = "QualityManagement.QualityCheckout.Update";
        public const string QualityCheckout_Get = "QualityManagement.QualityCheckout.Get";
        public const string QualityCheckout_Delete = "QualityManagement.QualityCheckout.Delete";
        public const string QualityCheckout_Export = "QualityManagement.QualityCheckout.Export";
        public const string QualityCheckout_Print = "QualityManagement.QualityCheckout.Print";
        //生成出库单按钮
        public const string QualityCheckout_Outbound = "QualityManagement.QualityCheckout.Outbound";
        //检测按钮
        public const string QualityCheckout_Check = "QualityManagement.QualityCheckout.Check";
        //质量放行
        public const string QualityCheckout_Released = "QualityManagement.QualityCheckout.Released";
        #endregion

        #region RGV设备状态
        public const string Warehouse_Equipment_RGV_List = "Warehouse.Equipment.RGV.List";
        public const string Warehouse_Equipment_RGV_Add = "Warehouse.Equipment.RGV.Add";
        public const string Warehouse_Equipment_RGV_Update = "Warehouse.Equipment.RGV.Update";
        public const string Warehouse_Equipment_RGV_Get = "Warehouse.Equipment.RGV.Get";
        public const string Warehouse_Equipment_RGV_Delete = "Warehouse.Equipment.RGV.Delete";
        public const string Warehouse_Equipment_RGV_Export = "Warehouse.Equipment.RGV.Export";
        public const string Warehouse_Equipment_RGV_Print = "Warehouse.Equipment.RGV.Print";


        #endregion

        #region 堆垛机状态
        public const string Warehouse_Equipment_Stacker_List = "Warehouse.Equipment.Stacker.List";
        public const string Warehouse_Equipment_Stacker_Add = "Warehouse.Equipment.Stacker.Add";
        public const string Warehouse_Equipment_Stacker_Update = "Warehouse.Equipment.Stacker.Update";
        public const string Warehouse_Equipment_Stacker_Get = "Warehouse.Equipment.Stacker.Get";
        public const string Warehouse_Equipment_Stacker_Delete = "Warehouse.Equipment.Stacker.Delete";
        public const string Warehouse_Equipment_Stacker_Export = "Warehouse.Equipment.Stacker.Export";
        public const string Warehouse_Equipment_Stacker_Print = "Warehouse.Equipment.Stacker.Print";
        #endregion

    }
}
