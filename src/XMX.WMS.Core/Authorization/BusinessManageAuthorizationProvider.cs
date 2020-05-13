using Abp.Authorization;

namespace XMX.WMS.Authorization
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-04-17 13:19:44
    /// 描 述：
    ///</summary>
   public  class BusinessManageAuthorizationProvider : AuthorizationProvider
    {
        public override void SetPermissions(IPermissionDefinitionContext context)
        {
            //任务管理中心
            var TaskManage = context.CreatePermission("TaskManage");

            //立库任务管理
            #region
            var MainTaskManage = TaskManage.CreateChildPermission(PermissionNames.MainTaskManage);
            MainTaskManage.CreateChildPermission(PermissionNames.MainTaskManage_Add);
            MainTaskManage.CreateChildPermission(PermissionNames.MainTaskManage_Update);
            MainTaskManage.CreateChildPermission(PermissionNames.MainTaskManage_Get);
            MainTaskManage.CreateChildPermission(PermissionNames.MainTaskManage_Delete);
            MainTaskManage.CreateChildPermission(PermissionNames.MainTaskManage_Export);
            MainTaskManage.CreateChildPermission(PermissionNames.MainTaskManage_Print);
            MainTaskManage.CreateChildPermission(PermissionNames.MainTaskManage_Modify);
            MainTaskManage.CreateChildPermission(PermissionNames.MainTaskManage_Start);
            MainTaskManage.CreateChildPermission(PermissionNames.MainTaskManage_Pause);
            MainTaskManage.CreateChildPermission(PermissionNames.MainTaskManage_Resume);
            MainTaskManage.CreateChildPermission(PermissionNames.MainTaskManage_Finish);
            MainTaskManage.CreateChildPermission(PermissionNames.MainTaskManage_Cancle);
            MainTaskManage.CreateChildPermission(PermissionNames.MainTaskManage_Order);
            MainTaskManage.CreateChildPermission(PermissionNames.MainTaskManage_Manual);
            MainTaskManage.CreateChildPermission(PermissionNames.MainTaskManage_Look);
            #endregion


            //立库历史任务
            #region
            var MainHistorytask = TaskManage.CreateChildPermission(PermissionNames.MainHistorytask);
            MainHistorytask.CreateChildPermission(PermissionNames.MainHistorytask_Add);
            MainHistorytask.CreateChildPermission(PermissionNames.MainHistorytask_Update);
            MainHistorytask.CreateChildPermission(PermissionNames.MainHistorytask_Get);
            MainHistorytask.CreateChildPermission(PermissionNames.MainHistorytask_Delete);
            MainHistorytask.CreateChildPermission(PermissionNames.MainHistorytask_Export);
            MainHistorytask.CreateChildPermission(PermissionNames.MainHistorytask_Print);
            #endregion


            //平库任务管理
            #region
            var FlatBankTask = TaskManage.CreateChildPermission(PermissionNames.FlatBankTask);
            FlatBankTask.CreateChildPermission(PermissionNames.FlatBankTask_Add);
            FlatBankTask.CreateChildPermission(PermissionNames.FlatBankTask_Update);
            FlatBankTask.CreateChildPermission(PermissionNames.FlatBankTask_Get);
            FlatBankTask.CreateChildPermission(PermissionNames.FlatBankTask_Delete);
            FlatBankTask.CreateChildPermission(PermissionNames.FlatBankTask_Export);
            FlatBankTask.CreateChildPermission(PermissionNames.FlatBankTask_Print);
            FlatBankTask.CreateChildPermission(PermissionNames.FlatBankTask_Modify);
            FlatBankTask.CreateChildPermission(PermissionNames.FlatBankTask_Start);
            FlatBankTask.CreateChildPermission(PermissionNames.FlatBankTask_Pause);
            FlatBankTask.CreateChildPermission(PermissionNames.FlatBankTask_Resume);
            FlatBankTask.CreateChildPermission(PermissionNames.FlatBankTask_Finish);
            FlatBankTask.CreateChildPermission(PermissionNames.FlatBankTask_Cancle);
            FlatBankTask.CreateChildPermission(PermissionNames.FlatBankTask_Order);
            FlatBankTask.CreateChildPermission(PermissionNames.FlatBankTask_Manual);
            FlatBankTask.CreateChildPermission(PermissionNames.FlatBankTask_Look);
            #endregion

            //拣选任务管理
            #region
            var PickingTask = TaskManage.CreateChildPermission(PermissionNames.PickingTask);
            PickingTask.CreateChildPermission(PermissionNames.PickingTask_Add);
            PickingTask.CreateChildPermission(PermissionNames.PickingTask_Update);
            PickingTask.CreateChildPermission(PermissionNames.PickingTask_Get);
            PickingTask.CreateChildPermission(PermissionNames.PickingTask_Delete);
            PickingTask.CreateChildPermission(PermissionNames.PickingTask_Export);
            PickingTask.CreateChildPermission(PermissionNames.PickingTask_Print);
            PickingTask.CreateChildPermission(PermissionNames.PickingTask_Modify);
            PickingTask.CreateChildPermission(PermissionNames.PickingTask_Start);
            PickingTask.CreateChildPermission(PermissionNames.PickingTask_Pause);
            PickingTask.CreateChildPermission(PermissionNames.PickingTask_Resume);
            PickingTask.CreateChildPermission(PermissionNames.PickingTask_Finish);
            PickingTask.CreateChildPermission(PermissionNames.PickingTask_Cancle);
            PickingTask.CreateChildPermission(PermissionNames.PickingTask_Order);
            PickingTask.CreateChildPermission(PermissionNames.PickingTask_Manual);
            PickingTask.CreateChildPermission(PermissionNames.PickingTask_Look);
            #endregion

            //AGV任务管理
            #region
            var AGVTask = TaskManage.CreateChildPermission(PermissionNames.AGVTask);
            AGVTask.CreateChildPermission(PermissionNames.AGVTask_Add);
            AGVTask.CreateChildPermission(PermissionNames.AGVTask_Update);
            AGVTask.CreateChildPermission(PermissionNames.AGVTask_Get);
            AGVTask.CreateChildPermission(PermissionNames.AGVTask_Delete);
            AGVTask.CreateChildPermission(PermissionNames.AGVTask_Export);
            AGVTask.CreateChildPermission(PermissionNames.AGVTask_Print);
            AGVTask.CreateChildPermission(PermissionNames.AGVTask_Modify);
            AGVTask.CreateChildPermission(PermissionNames.AGVTask_Start);
            AGVTask.CreateChildPermission(PermissionNames.AGVTask_Pause);
            AGVTask.CreateChildPermission(PermissionNames.AGVTask_Resume);
            AGVTask.CreateChildPermission(PermissionNames.AGVTask_Finish);
            AGVTask.CreateChildPermission(PermissionNames.AGVTask_Cancle);
            AGVTask.CreateChildPermission(PermissionNames.AGVTask_Order);
            AGVTask.CreateChildPermission(PermissionNames.AGVTask_Manual);
            AGVTask.CreateChildPermission(PermissionNames.AGVTask_Look);
            #endregion

            //RGV任务管理
            #region
            var RGVTask = TaskManage.CreateChildPermission(PermissionNames.RGVTask);
            RGVTask.CreateChildPermission(PermissionNames.RGVTask_Add);
            RGVTask.CreateChildPermission(PermissionNames.RGVTask_Update);
            RGVTask.CreateChildPermission(PermissionNames.RGVTask_Get);
            RGVTask.CreateChildPermission(PermissionNames.RGVTask_Delete);
            RGVTask.CreateChildPermission(PermissionNames.RGVTask_Export);
            RGVTask.CreateChildPermission(PermissionNames.RGVTask_Print);
            RGVTask.CreateChildPermission(PermissionNames.RGVTask_Modify);
            RGVTask.CreateChildPermission(PermissionNames.RGVTask_Start);
            RGVTask.CreateChildPermission(PermissionNames.RGVTask_Pause);
            RGVTask.CreateChildPermission(PermissionNames.RGVTask_Resume);
            RGVTask.CreateChildPermission(PermissionNames.RGVTask_Finish);
            RGVTask.CreateChildPermission(PermissionNames.RGVTask_Cancle);
            RGVTask.CreateChildPermission(PermissionNames.RGVTask_Order);
            RGVTask.CreateChildPermission(PermissionNames.RGVTask_Manual);
            RGVTask.CreateChildPermission(PermissionNames.RGVTask_Look);
            #endregion


            //业务管理中心
            var BusinessManage = context.CreatePermission("BusinessManage");
            //监控策略管理
            var StrategyMonitorManage = BusinessManage.CreateChildPermission(PermissionNames.StrategyMonitorManage);
            StrategyMonitorManage.CreateChildPermission(PermissionNames.StrategyMonitorManage_Add);
            StrategyMonitorManage.CreateChildPermission(PermissionNames.StrategyMonitorManage_Update);
            StrategyMonitorManage.CreateChildPermission(PermissionNames.StrategyMonitorManage_Get);
            StrategyMonitorManage.CreateChildPermission(PermissionNames.StrategyMonitorManage_Delete);
            StrategyMonitorManage.CreateChildPermission(PermissionNames.StrategyMonitorManage_Export);
            StrategyMonitorManage.CreateChildPermission(PermissionNames.StrategyMonitorManage_Print);

            //上架策略管理
            var StrategyPutawayManage = BusinessManage.CreateChildPermission(PermissionNames.StrategyPutawayManage);
            StrategyPutawayManage.CreateChildPermission(PermissionNames.StrategyPutawayManage_Add);
            StrategyPutawayManage.CreateChildPermission(PermissionNames.StrategyPutawayManage_Update);
            StrategyPutawayManage.CreateChildPermission(PermissionNames.StrategyPutawayManage_Get);
            StrategyPutawayManage.CreateChildPermission(PermissionNames.StrategyPutawayManage_Delete);
            StrategyPutawayManage.CreateChildPermission(PermissionNames.StrategyPutawayManage_Export);
            StrategyPutawayManage.CreateChildPermission(PermissionNames.StrategyPutawayManage_Print);

            //分配策略管理
            var StrategyDistriManage = BusinessManage.CreateChildPermission(PermissionNames.StrategyDistriManage);
            StrategyDistriManage.CreateChildPermission(PermissionNames.StrategyDistriManage_Add);
            StrategyDistriManage.CreateChildPermission(PermissionNames.StrategyDistriManage_Update);
            StrategyDistriManage.CreateChildPermission(PermissionNames.StrategyDistriManage_Get);
            StrategyDistriManage.CreateChildPermission(PermissionNames.StrategyDistriManage_Delete);
            StrategyDistriManage.CreateChildPermission(PermissionNames.StrategyDistriManage_Export);
            StrategyDistriManage.CreateChildPermission(PermissionNames.StrategyDistriManage_Print);

            //编码规则管理
            var EncodingRuleManage = BusinessManage.CreateChildPermission(PermissionNames.EncodingRuleManage);
            EncodingRuleManage.CreateChildPermission(PermissionNames.EncodingRuleManage_Add);
            EncodingRuleManage.CreateChildPermission(PermissionNames.EncodingRuleManage_Update);
            EncodingRuleManage.CreateChildPermission(PermissionNames.EncodingRuleManage_Get);
            EncodingRuleManage.CreateChildPermission(PermissionNames.EncodingRuleManage_Delete);
            EncodingRuleManage.CreateChildPermission(PermissionNames.EncodingRuleManage_Export);
            EncodingRuleManage.CreateChildPermission(PermissionNames.EncodingRuleManage_Print);

            //日志管理中心
            var LogManage = context.CreatePermission("LogManage");
            //登录日志管理
            var LoginLogManage = LogManage.CreateChildPermission(PermissionNames.LoginLogManage);
            LoginLogManage.CreateChildPermission(PermissionNames.LoginLogManage_Export);
            LoginLogManage.CreateChildPermission(PermissionNames.LoginLogManage_Print);
            //操作日志管理
            var OptLogManage = LogManage.CreateChildPermission(PermissionNames.OptLogManage);
            OptLogManage.CreateChildPermission(PermissionNames.OptLogManage_Export);
            OptLogManage.CreateChildPermission(PermissionNames.OptLogManage_Print);
            //设备报警日志
            var MachineAlertLog = LogManage.CreateChildPermission(PermissionNames.MachineAlertLog);
            MachineAlertLog.CreateChildPermission(PermissionNames.MachineAlertLog_Export);
            MachineAlertLog.CreateChildPermission(PermissionNames.MachineAlertLog_Print);
            //入库申请日志
            var ImportApplyLog = LogManage.CreateChildPermission(PermissionNames.ImportApplyLog);
            ImportApplyLog.CreateChildPermission(PermissionNames.ImportApplyLog_Export);
            ImportApplyLog.CreateChildPermission(PermissionNames.ImportApplyLog_Print);
            //出库单据同步日志
            var ExportBillSyncLog = LogManage.CreateChildPermission(PermissionNames.ExportBillSyncLog);
            ExportBillSyncLog.CreateChildPermission(PermissionNames.ExportBillSyncLog_Export);
            ExportBillSyncLog.CreateChildPermission(PermissionNames.ExportBillSyncLog_Print);


            //质量管理中心
            var QualityManagement = context.CreatePermission("QualityManagement");
            var QualityCheckout = QualityManagement.CreateChildPermission(PermissionNames.QualityCheckout);
            QualityCheckout.CreateChildPermission(PermissionNames.QualityCheckout_Add);
            QualityCheckout.CreateChildPermission(PermissionNames.QualityCheckout_Update);
            QualityCheckout.CreateChildPermission(PermissionNames.QualityCheckout_Get);
            QualityCheckout.CreateChildPermission(PermissionNames.QualityCheckout_Delete);
            QualityCheckout.CreateChildPermission(PermissionNames.QualityCheckout_Export);
            QualityCheckout.CreateChildPermission(PermissionNames.QualityCheckout_Print);
            QualityCheckout.CreateChildPermission(PermissionNames.QualityCheckout_Outbound);
            QualityCheckout.CreateChildPermission(PermissionNames.QualityCheckout_Check);
            QualityCheckout.CreateChildPermission(PermissionNames.QualityCheckout_Released);


        }
    }
}
