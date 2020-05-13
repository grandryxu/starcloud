using Abp.Authorization;
namespace XMX.WMS.Authorization
{
    ///<summary>
    /// 版 本：
    /// 创建人：Haoran
    /// 日 期：2020-04-17 10:37:30
    /// 描 述：
    ///</summary>
    public class SysManageAuthorizationProvider : AuthorizationProvider
    {
        public override void SetPermissions(IPermissionDefinitionContext context)
        {
            //系统管理中心权限标识
            var sysmanage = context.CreatePermission("SystemManageCenter");

            //公司基础信息
            var CompanyBasicInfo = sysmanage.CreateChildPermission(PermissionNames.CompanyBasicInfo);
            CompanyBasicInfo.CreateChildPermission(PermissionNames.CompanyBasicInfo_Add);
            CompanyBasicInfo.CreateChildPermission(PermissionNames.CompanyBasicInfo_Update);
            CompanyBasicInfo.CreateChildPermission(PermissionNames.CompanyBasicInfo_Get);
            CompanyBasicInfo.CreateChildPermission(PermissionNames.CompanyBasicInfo_Delete);
            CompanyBasicInfo.CreateChildPermission(PermissionNames.CompanyBasicInfo_Export);
            CompanyBasicInfo.CreateChildPermission(PermissionNames.CompanyBasicInfo_Print);

            //部门基础信息
            var DepartmentBasicInfo = sysmanage.CreateChildPermission(PermissionNames.DepartmentBasicInfo);
            DepartmentBasicInfo.CreateChildPermission(PermissionNames.DepartmentBasicInfo_Add);
            DepartmentBasicInfo.CreateChildPermission(PermissionNames.DepartmentBasicInfo_Update);
            DepartmentBasicInfo.CreateChildPermission(PermissionNames.DepartmentBasicInfo_Get);
            DepartmentBasicInfo.CreateChildPermission(PermissionNames.DepartmentBasicInfo_Delete);
            DepartmentBasicInfo.CreateChildPermission(PermissionNames.DepartmentBasicInfo_Export);
            DepartmentBasicInfo.CreateChildPermission(PermissionNames.DepartmentBasicInfo_Print);

            //人员基础信息
            var StaffBasicInfo = sysmanage.CreateChildPermission(PermissionNames.StaffBasicInfo);
            StaffBasicInfo.CreateChildPermission(PermissionNames.StaffBasicInfo_Add);
            StaffBasicInfo.CreateChildPermission(PermissionNames.StaffBasicInfo_Update);
            StaffBasicInfo.CreateChildPermission(PermissionNames.StaffBasicInfo_Get);
            StaffBasicInfo.CreateChildPermission(PermissionNames.StaffBasicInfo_Delete);
            StaffBasicInfo.CreateChildPermission(PermissionNames.StaffBasicInfo_Export);
            StaffBasicInfo.CreateChildPermission(PermissionNames.StaffBasicInfo_Print);
            StaffBasicInfo.CreateChildPermission(PermissionNames.StaffBasicInfo_Enable);
            StaffBasicInfo.CreateChildPermission(PermissionNames.StaffBasicInfo_Forbidden);

            //角色基础信息
            var RoleBasicInfo = sysmanage.CreateChildPermission(PermissionNames.RoleBasicInfo);
            RoleBasicInfo.CreateChildPermission(PermissionNames.RoleBasicInfo_Add);
            RoleBasicInfo.CreateChildPermission(PermissionNames.RoleBasicInfo_Update);
            RoleBasicInfo.CreateChildPermission(PermissionNames.RoleBasicInfo_Get);
            RoleBasicInfo.CreateChildPermission(PermissionNames.RoleBasicInfo_Delete);
            RoleBasicInfo.CreateChildPermission(PermissionNames.RoleBasicInfo_Export);
            RoleBasicInfo.CreateChildPermission(PermissionNames.RoleBasicInfo_Print);
            RoleBasicInfo.CreateChildPermission(PermissionNames.RoleBasicInfo_Enable);
            RoleBasicInfo.CreateChildPermission(PermissionNames.RoleBasicInfo_Forbidden);

            //系统模块管理
            var SystemModuleManage = sysmanage.CreateChildPermission(PermissionNames.SystemModuleManage);
            SystemModuleManage.CreateChildPermission(PermissionNames.SystemModuleManage_Add);
            SystemModuleManage.CreateChildPermission(PermissionNames.SystemModuleManage_Update);
            SystemModuleManage.CreateChildPermission(PermissionNames.SystemModuleManage_Get);
            SystemModuleManage.CreateChildPermission(PermissionNames.SystemModuleManage_Delete);
            SystemModuleManage.CreateChildPermission(PermissionNames.SystemModuleManage_Export);
            SystemModuleManage.CreateChildPermission(PermissionNames.SystemModuleManage_Print);
            SystemModuleManage.CreateChildPermission(PermissionNames.SystemModuleManage_Enable);
            SystemModuleManage.CreateChildPermission(PermissionNames.SystemModuleManage_Forbidden);

            //移动模块管理
            var MoveModuleManage = sysmanage.CreateChildPermission(PermissionNames.MoveModuleManage);
            MoveModuleManage.CreateChildPermission(PermissionNames.MoveModuleManage_Add);
            MoveModuleManage.CreateChildPermission(PermissionNames.MoveModuleManage_Update);
            MoveModuleManage.CreateChildPermission(PermissionNames.MoveModuleManage_Get);
            MoveModuleManage.CreateChildPermission(PermissionNames.MoveModuleManage_Delete);
            MoveModuleManage.CreateChildPermission(PermissionNames.MoveModuleManage_Export);
            MoveModuleManage.CreateChildPermission(PermissionNames.MoveModuleManage_Print);
            MoveModuleManage.CreateChildPermission(PermissionNames.MoveModuleManage_Enable);
            MoveModuleManage.CreateChildPermission(PermissionNames.MoveModuleManage_Forbidden);

            //仓库基础管理
            var WarehouseBasicManage = sysmanage.CreateChildPermission(PermissionNames.WarehouseBasicManage);
            WarehouseBasicManage.CreateChildPermission(PermissionNames.WarehouseBasicManage_Add);
            WarehouseBasicManage.CreateChildPermission(PermissionNames.WarehouseBasicManage_Update);
            WarehouseBasicManage.CreateChildPermission(PermissionNames.WarehouseBasicManage_Get);
            WarehouseBasicManage.CreateChildPermission(PermissionNames.WarehouseBasicManage_Delete);
            WarehouseBasicManage.CreateChildPermission(PermissionNames.WarehouseBasicManage_Export);
            WarehouseBasicManage.CreateChildPermission(PermissionNames.WarehouseBasicManage_Print);

            //库位初始化
            var SlotInit = sysmanage.CreateChildPermission(PermissionNames.SlotInit);
            SlotInit.CreateChildPermission(PermissionNames.SlotInit_Add);
            SlotInit.CreateChildPermission(PermissionNames.SlotInit_Update);
            SlotInit.CreateChildPermission(PermissionNames.SlotInit_Get);
            SlotInit.CreateChildPermission(PermissionNames.SlotInit_Delete);
            SlotInit.CreateChildPermission(PermissionNames.SlotInit_Export);
            SlotInit.CreateChildPermission(PermissionNames.SlotInit_Print);
            SlotInit.CreateChildPermission(PermissionNames.SlotInit_Create);





        }
    }
}
