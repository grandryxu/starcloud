using Abp.Authorization;

namespace XMX.WMS.Authorization
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-04-17 11:03:30
    /// 描 述：
    ///</summary>
   public  class WarehouseManageAuthorizationProvider: AuthorizationProvider
    {
        public override void SetPermissions(IPermissionDefinitionContext context)
        {
            //入库管理中心
            var ImportWarehouseManage = context.CreatePermission("ImportWarehouseManage");

            //入库单据管理
            var ImportBillManage = ImportWarehouseManage.CreateChildPermission(PermissionNames.ImportBillManage);
            ImportBillManage.CreateChildPermission(PermissionNames.ImportBillManage_Add);
            ImportBillManage.CreateChildPermission(PermissionNames.ImportBillManage_Update);
            ImportBillManage.CreateChildPermission(PermissionNames.ImportBillManage_Get);
            ImportBillManage.CreateChildPermission(PermissionNames.ImportBillManage_Delete);
            ImportBillManage.CreateChildPermission(PermissionNames.ImportBillManage_Export);
            ImportBillManage.CreateChildPermission(PermissionNames.ImportBillManage_Print);
            ImportBillManage.CreateChildPermission(PermissionNames.ImportBillManage_BillPrint);
            ImportBillManage.CreateChildPermission(PermissionNames.ImportBillManage_Examine);
            ImportBillManage.CreateChildPermission(PermissionNames.ImportBillManage_UnExamined);
            ImportBillManage.CreateChildPermission(PermissionNames.ImportBillManage_Cancle);
            ImportBillManage.CreateChildPermission(PermissionNames.ImportBillManage_OneKey);
            ImportBillManage.CreateChildPermission(PermissionNames.ImportBillManage_Finish);
            ImportBillManage.CreateChildPermission(PermissionNames.ImportBillManage_Task);


            //入库流水管理
            var ImportBillBodyManage = ImportWarehouseManage.CreateChildPermission(PermissionNames.ImportBillBodyManage);
            ImportBillBodyManage.CreateChildPermission(PermissionNames.ImportBillBodyManage_Add);
            ImportBillBodyManage.CreateChildPermission(PermissionNames.ImportBillBodyManage_Update);
            ImportBillBodyManage.CreateChildPermission(PermissionNames.ImportBillBodyManage_Get);
            ImportBillBodyManage.CreateChildPermission(PermissionNames.ImportBillBodyManage_Delete);
            ImportBillBodyManage.CreateChildPermission(PermissionNames.ImportBillBodyManage_Export);
            ImportBillBodyManage.CreateChildPermission(PermissionNames.ImportBillBodyManage_Print);
            ImportBillBodyManage.CreateChildPermission(PermissionNames.ImportBillBodyManage_AnalogScan);
            ImportBillBodyManage.CreateChildPermission(PermissionNames.ImportBillBodyManage_Cancle);
            ImportBillBodyManage.CreateChildPermission(PermissionNames.ImportBillBodyManage_Task);


            //空托盘入库流水
            var EmptyStockImport = ImportWarehouseManage.CreateChildPermission(PermissionNames.EmptyStockImport);
            EmptyStockImport.CreateChildPermission(PermissionNames.EmptyStockImport_Add);
            EmptyStockImport.CreateChildPermission(PermissionNames.EmptyStockImport_Update);
            EmptyStockImport.CreateChildPermission(PermissionNames.EmptyStockImport_Get);
            EmptyStockImport.CreateChildPermission(PermissionNames.EmptyStockImport_Delete);
            EmptyStockImport.CreateChildPermission(PermissionNames.EmptyStockImport_Export);
            EmptyStockImport.CreateChildPermission(PermissionNames.EmptyStockImport_Print);
            EmptyStockImport.CreateChildPermission(PermissionNames.EmptyStockImport_Task);



            //出库管理中心
            var ExportWarehouseManage = context.CreatePermission("ExportWarehouseManage");

            //出库单据管理
            var ExportBillManage = ExportWarehouseManage.CreateChildPermission(PermissionNames.ExportBillManage);
            ExportBillManage.CreateChildPermission(PermissionNames.ExportBillManage_Add);
            ExportBillManage.CreateChildPermission(PermissionNames.ExportBillManage_Update);
            ExportBillManage.CreateChildPermission(PermissionNames.ExportBillManage_Get);
            ExportBillManage.CreateChildPermission(PermissionNames.ExportBillManage_Delete);
            ExportBillManage.CreateChildPermission(PermissionNames.ExportBillManage_Export);
            ExportBillManage.CreateChildPermission(PermissionNames.ExportBillManage_Print);
            ExportBillManage.CreateChildPermission(PermissionNames.ExportBillManage_Wave);
            ExportBillManage.CreateChildPermission(PermissionNames.ExportBillManage_Auto);
            ExportBillManage.CreateChildPermission(PermissionNames.ExportBillManage_Manual);
            ExportBillManage.CreateChildPermission(PermissionNames.ExportBillManage_OneKey);
            ExportBillManage.CreateChildPermission(PermissionNames.ExportBillManage_Finish);
            ExportBillManage.CreateChildPermission(PermissionNames.ExportBillManage_BillPrint);

            //出库流水管理
            var ExportBillBodyManage = ExportWarehouseManage.CreateChildPermission(PermissionNames.ExportBillBodyManage);
            ExportBillBodyManage.CreateChildPermission(PermissionNames.ExportBillBodyManage_Add);
            ExportBillBodyManage.CreateChildPermission(PermissionNames.ExportBillBodyManage_Update);
            ExportBillBodyManage.CreateChildPermission(PermissionNames.ExportBillBodyManage_Get);
            ExportBillBodyManage.CreateChildPermission(PermissionNames.ExportBillBodyManage_Delete);
            ExportBillBodyManage.CreateChildPermission(PermissionNames.ExportBillBodyManage_Export);
            ExportBillBodyManage.CreateChildPermission(PermissionNames.ExportBillBodyManage_Print);
            ExportBillBodyManage.CreateChildPermission(PermissionNames.ExportBillBodyManage_Review);

            //空托盘出库流水
            var EmptyStockExport = ExportWarehouseManage.CreateChildPermission(PermissionNames.EmptyStockExport);
            EmptyStockExport.CreateChildPermission(PermissionNames.EmptyStockExport_Add);
            EmptyStockExport.CreateChildPermission(PermissionNames.EmptyStockExport_Update);
            EmptyStockExport.CreateChildPermission(PermissionNames.EmptyStockExport_Get);
            EmptyStockExport.CreateChildPermission(PermissionNames.EmptyStockExport_Delete);
            EmptyStockExport.CreateChildPermission(PermissionNames.EmptyStockExport_Export);
            EmptyStockExport.CreateChildPermission(PermissionNames.EmptyStockExport_Print);

            //库存管理中心
            var InventoryManage = context.CreatePermission("InventoryManage");

            //盘点管理
            var InventoryStockingManage = InventoryManage.CreateChildPermission(PermissionNames.InventoryStockingManage);
            InventoryStockingManage.CreateChildPermission(PermissionNames.InventoryStockingManage_Add);
            InventoryStockingManage.CreateChildPermission(PermissionNames.InventoryStockingManage_Update);
            InventoryStockingManage.CreateChildPermission(PermissionNames.InventoryStockingManage_Get);
            InventoryStockingManage.CreateChildPermission(PermissionNames.InventoryStockingManage_Delete);
            InventoryStockingManage.CreateChildPermission(PermissionNames.InventoryStockingManage_Export);
            InventoryStockingManage.CreateChildPermission(PermissionNames.InventoryStockingManage_Print);
            InventoryStockingManage.CreateChildPermission(PermissionNames.InventoryStockingManage_Task);

            //可视化库存管理
            var VisualizationManage = InventoryManage.CreateChildPermission(PermissionNames.VisualizationManage);
            VisualizationManage.CreateChildPermission(PermissionNames.VisualizationManage_Add);
            VisualizationManage.CreateChildPermission(PermissionNames.VisualizationManage_Update);
            VisualizationManage.CreateChildPermission(PermissionNames.VisualizationManage_Get);
            VisualizationManage.CreateChildPermission(PermissionNames.VisualizationManage_Delete);
            VisualizationManage.CreateChildPermission(PermissionNames.VisualizationManage_Export);
            VisualizationManage.CreateChildPermission(PermissionNames.VisualizationManage_Print);

            //总库存管理
            var TotalInventoryManage = InventoryManage.CreateChildPermission(PermissionNames.TotalInventoryManage);
            TotalInventoryManage.CreateChildPermission(PermissionNames.TotalInventoryManage_Add);
            TotalInventoryManage.CreateChildPermission(PermissionNames.TotalInventoryManage_Update);
            TotalInventoryManage.CreateChildPermission(PermissionNames.TotalInventoryManage_Get);
            TotalInventoryManage.CreateChildPermission(PermissionNames.TotalInventoryManage_Delete);
            TotalInventoryManage.CreateChildPermission(PermissionNames.TotalInventoryManage_Export);
            TotalInventoryManage.CreateChildPermission(PermissionNames.TotalInventoryManage_Print);

            //库位库存管理
            var SlotInventoryManage = InventoryManage.CreateChildPermission(PermissionNames.SlotInventoryManage);
            SlotInventoryManage.CreateChildPermission(PermissionNames.SlotInventoryManage_Add);
            SlotInventoryManage.CreateChildPermission(PermissionNames.SlotInventoryManage_Update);
            SlotInventoryManage.CreateChildPermission(PermissionNames.SlotInventoryManage_Get);
            SlotInventoryManage.CreateChildPermission(PermissionNames.SlotInventoryManage_Delete);
            SlotInventoryManage.CreateChildPermission(PermissionNames.SlotInventoryManage_Export);
            SlotInventoryManage.CreateChildPermission(PermissionNames.SlotInventoryManage_Print);

            //托盘库存管理
            var StockInventoryManage = InventoryManage.CreateChildPermission(PermissionNames.StockInventoryManage);
            StockInventoryManage.CreateChildPermission(PermissionNames.StockInventoryManage_Add);
            StockInventoryManage.CreateChildPermission(PermissionNames.StockInventoryManage_Update);
            StockInventoryManage.CreateChildPermission(PermissionNames.StockInventoryManage_Get);
            StockInventoryManage.CreateChildPermission(PermissionNames.StockInventoryManage_Delete);
            StockInventoryManage.CreateChildPermission(PermissionNames.StockInventoryManage_Export);
            StockInventoryManage.CreateChildPermission(PermissionNames.StockInventoryManage_Print);
            StockInventoryManage.CreateChildPermission(PermissionNames.StockInventoryManage_Task);

            //库存预警中心
            var WarehouseWarning = context.CreatePermission("WarehouseWarning");

            //呆滞库存预警
            var DullStockWarning = WarehouseWarning.CreateChildPermission(PermissionNames.DullStockWarning);
            DullStockWarning.CreateChildPermission(PermissionNames.DullStockWarning_Export);
            DullStockWarning.CreateChildPermission(PermissionNames.DullStockWarning_Print);

            //效期库存预警
            var ValidityStockWarning = WarehouseWarning.CreateChildPermission(PermissionNames.ValidityStockWarning);
            ValidityStockWarning.CreateChildPermission(PermissionNames.ValidityStockWarning_Export);
            ValidityStockWarning.CreateChildPermission(PermissionNames.ValidityStockWarning_Print);

            //库存阈值预警
            var InventoryThresholdWarning = WarehouseWarning.CreateChildPermission(PermissionNames.InventoryThresholdWarning);
            InventoryThresholdWarning.CreateChildPermission(PermissionNames.InventoryThresholdWarning_Export);
            InventoryThresholdWarning.CreateChildPermission(PermissionNames.InventoryThresholdWarning_Print);

            //入库异常预警
            var ImportExceptionWarning = WarehouseWarning.CreateChildPermission(PermissionNames.ImportExceptionWarning);
            ImportExceptionWarning.CreateChildPermission(PermissionNames.ImportExceptionWarning_Export);
            ImportExceptionWarning.CreateChildPermission(PermissionNames.ImportExceptionWarning_Print);

            //出库异常预警
            var ExportExceptionWarning = WarehouseWarning.CreateChildPermission(PermissionNames.ExportExceptionWarning);
            ExportExceptionWarning.CreateChildPermission(PermissionNames.ExportExceptionWarning_Export);
            ExportExceptionWarning.CreateChildPermission(PermissionNames.ExportExceptionWarning_Print);

            //库存异常预警
            var InventoryExceptionWarning = WarehouseWarning.CreateChildPermission(PermissionNames.InventoryExceptionWarning);
            InventoryExceptionWarning.CreateChildPermission(PermissionNames.InventoryExceptionWarning_Export);
            InventoryExceptionWarning.CreateChildPermission(PermissionNames.InventoryExceptionWarning_Print);

            //仓库报表中心
            var WarehouseReport = context.CreatePermission("WarehouseReport");

            //出入库台账
            var ExportImportStandingBook = WarehouseReport.CreateChildPermission(PermissionNames.ExportImportStandingBook);
            ExportImportStandingBook.CreateChildPermission(PermissionNames.ExportImportStandingBook_Export);
            ExportImportStandingBook.CreateChildPermission(PermissionNames.ExportImportStandingBook_Print);
            //货位利用率
            var StorageUseRatio = WarehouseReport.CreateChildPermission(PermissionNames.StorageUseRatio);
            StorageUseRatio.CreateChildPermission(PermissionNames.StorageUseRatio_Export);
            StorageUseRatio.CreateChildPermission(PermissionNames.StorageUseRatio_Print);
            //入库报表
            var ImportReportForm = WarehouseReport.CreateChildPermission(PermissionNames.ImportReportForm);
            ImportReportForm.CreateChildPermission(PermissionNames.ImportReportForm_Export);
            ImportReportForm.CreateChildPermission(PermissionNames.ImportReportForm_Print);
            //出库报表
            var ExportReportForm = WarehouseReport.CreateChildPermission(PermissionNames.ExportReportForm);
            ExportReportForm.CreateChildPermission(PermissionNames.ExportReportForm_Export);
            ExportReportForm.CreateChildPermission(PermissionNames.ExportReportForm_Print);
            //库存报表
            var InventoryReportForm = WarehouseReport.CreateChildPermission(PermissionNames.InventoryReportForm);
            InventoryReportForm.CreateChildPermission(PermissionNames.InventoryReportForm_Export);
            InventoryReportForm.CreateChildPermission(PermissionNames.InventoryReportForm_Print);
            //自定义报表
            var CustomReportForm = WarehouseReport.CreateChildPermission(PermissionNames.CustomReportForm);
            CustomReportForm.CreateChildPermission(PermissionNames.CustomReportForm_Export);
            CustomReportForm.CreateChildPermission(PermissionNames.CustomReportForm_Print);


        }
    }
}
