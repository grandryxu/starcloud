using Abp.Authorization;

namespace XMX.WMS.Authorization
{
    /// <summary>
    /// 仓库设备权限定义
    /// </summary>
    public class WarehouseEquipmentAuthorizationProvider : AuthorizationProvider
    {
        public override void SetPermissions(IPermissionDefinitionContext context)
        {
            //权限标识符的层级关系定义基本上完全参照系统模块目录结构
            //仓库设备管理权限标识
            var warehouseequipment = context.CreatePermission("WareEquipment");

            #region RGV
            var rgvManagement = warehouseequipment.CreateChildPermission(PermissionNames.Warehouse_Equipment_RGV_List);
            rgvManagement.CreateChildPermission(PermissionNames.Warehouse_Equipment_RGV_Get);
            rgvManagement.CreateChildPermission(PermissionNames.Warehouse_Equipment_RGV_Add);
            rgvManagement.CreateChildPermission(PermissionNames.Warehouse_Equipment_RGV_Delete);
            rgvManagement.CreateChildPermission(PermissionNames.Warehouse_Equipment_RGV_Update);
            #endregion

            #region 堆垛机
            var stackerManagement = warehouseequipment.CreateChildPermission(PermissionNames.Warehouse_Equipment_Stacker_List);
            stackerManagement.CreateChildPermission(PermissionNames.Warehouse_Equipment_Stacker_Get);
            stackerManagement.CreateChildPermission(PermissionNames.Warehouse_Equipment_Stacker_Add);
            stackerManagement.CreateChildPermission(PermissionNames.Warehouse_Equipment_Stacker_Delete);
            stackerManagement.CreateChildPermission(PermissionNames.Warehouse_Equipment_Stacker_Update);
            #endregion
        }

    }
}
