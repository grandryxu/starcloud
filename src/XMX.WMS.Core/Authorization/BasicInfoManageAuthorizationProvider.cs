using Abp.Authorization;

namespace XMX.WMS.Authorization
{
    /// <summary>
    /// 基础信息权限
    /// </summary>
    public class BasicInfoManageAuthorizationProvider : AuthorizationProvider
    {
        public override void SetPermissions(IPermissionDefinitionContext context)
        {
            //权限标识符的层级关系定义基本上完全参照系统模块目录结构
            //基础信息管理权限标识
            var basicinfomanage = context.CreatePermission("BasicInfoManage");
            

            #region 物料基础信息
            var materialinfo = basicinfomanage.CreateChildPermission(PermissionNames.MaterialBasisInfo);
            materialinfo.CreateChildPermission(PermissionNames.MaterialBasisInfo_Get);
            materialinfo.CreateChildPermission(PermissionNames.MaterialBasisInfo_Add);
            materialinfo.CreateChildPermission(PermissionNames.MaterialBasisInfo_Delete);
            materialinfo.CreateChildPermission(PermissionNames.MaterialBasisInfo_Update);
            materialinfo.CreateChildPermission(PermissionNames.MaterialBasisInfo_Export);
            materialinfo.CreateChildPermission(PermissionNames.MaterialBasisInfo_Print);
            #endregion

            #region 物料计量单位
            var unitinfo = basicinfomanage.CreateChildPermission(PermissionNames.MaterialMeasureUnit);
            unitinfo.CreateChildPermission(PermissionNames.MaterialMeasureUnit_Get);
            unitinfo.CreateChildPermission(PermissionNames.MaterialMeasureUnit_Add);
            unitinfo.CreateChildPermission(PermissionNames.MaterialMeasureUnit_Delete);
            unitinfo.CreateChildPermission(PermissionNames.MaterialMeasureUnit_Update);
            unitinfo.CreateChildPermission(PermissionNames.MaterialMeasureUnit_Export);
            unitinfo.CreateChildPermission(PermissionNames.MaterialMeasureUnit_Print);
            #endregion

            #region 物料质量状态
            var MaterialQualityStatus = basicinfomanage.CreateChildPermission(PermissionNames.MaterialQualityStatus);
            MaterialQualityStatus.CreateChildPermission(PermissionNames.MaterialQualityStatus_Get);
            MaterialQualityStatus.CreateChildPermission(PermissionNames.MaterialQualityStatus_Add);
            MaterialQualityStatus.CreateChildPermission(PermissionNames.MaterialQualityStatus_Delete);
            MaterialQualityStatus.CreateChildPermission(PermissionNames.MaterialQualityStatus_Update);
            MaterialQualityStatus.CreateChildPermission(PermissionNames.MaterialQualityStatus_Export);
            MaterialQualityStatus.CreateChildPermission(PermissionNames.MaterialQualityStatus_Print);
            #endregion

            #region 客户类别信息
            var CustomerCategoryInfo = basicinfomanage.CreateChildPermission(PermissionNames.CustomerCategoryInfo);
            CustomerCategoryInfo.CreateChildPermission(PermissionNames.CustomTypeInfo_Get);
            CustomerCategoryInfo.CreateChildPermission(PermissionNames.CustomTypeInfo_Add);
            CustomerCategoryInfo.CreateChildPermission(PermissionNames.CustomTypeInfo_Delete);
            CustomerCategoryInfo.CreateChildPermission(PermissionNames.CustomTypeInfo_Update);
            CustomerCategoryInfo.CreateChildPermission(PermissionNames.CustomTypeInfo_Export);
            CustomerCategoryInfo.CreateChildPermission(PermissionNames.CustomTypeInfo_Print);
            #endregion

            #region 客户基础信息
            var CustomerBaseInfo = basicinfomanage.CreateChildPermission(PermissionNames.CustomerBaseInfo);
            CustomerBaseInfo.CreateChildPermission(PermissionNames.CustomInfo_Get);
            CustomerBaseInfo.CreateChildPermission(PermissionNames.CustomInfo_Add);
            CustomerBaseInfo.CreateChildPermission(PermissionNames.CustomInfo_Delete);
            CustomerBaseInfo.CreateChildPermission(PermissionNames.CustomInfo_Update);
            CustomerBaseInfo.CreateChildPermission(PermissionNames.CustomInfo_Export);
            CustomerBaseInfo.CreateChildPermission(PermissionNames.CustomInfo_Print);
            #endregion

            #region 仓库基础信息
            var WarehouseBaseInfo = basicinfomanage.CreateChildPermission(PermissionNames.WarehouseBaseInfo);
            WarehouseBaseInfo.CreateChildPermission(PermissionNames.WarehoueInfo_Add);
            WarehouseBaseInfo.CreateChildPermission(PermissionNames.WarehoueInfo_Update);
            WarehouseBaseInfo.CreateChildPermission(PermissionNames.WarehoueInfo_Get);
            WarehouseBaseInfo.CreateChildPermission(PermissionNames.WarehoueInfo_Delete);
            WarehouseBaseInfo.CreateChildPermission(PermissionNames.WarehoueInfo_Export);
            WarehouseBaseInfo.CreateChildPermission(PermissionNames.WarehoueInfo_Print);
            #endregion

            #region 库区基础信息
            var AreaBasicInfo = basicinfomanage.CreateChildPermission(PermissionNames.AreaBasicInfo);
            AreaBasicInfo.CreateChildPermission(PermissionNames.AreaBasicInfo_Add);
            AreaBasicInfo.CreateChildPermission(PermissionNames.AreaBasicInfo_Update);
            AreaBasicInfo.CreateChildPermission(PermissionNames.AreaBasicInfo_Get);
            AreaBasicInfo.CreateChildPermission(PermissionNames.AreaBasicInfo_Delete);
            AreaBasicInfo.CreateChildPermission(PermissionNames.AreaBasicInfo_Export);
            AreaBasicInfo.CreateChildPermission(PermissionNames.AreaBasicInfo_Print);
            #endregion

            #region 库位基础信息
            var SlotBasicInfo = basicinfomanage.CreateChildPermission(PermissionNames.SlotBasicInfo);
            SlotBasicInfo.CreateChildPermission(PermissionNames.SlotBasicInfo_Add);
            SlotBasicInfo.CreateChildPermission(PermissionNames.SlotBasicInfo_Update);
            SlotBasicInfo.CreateChildPermission(PermissionNames.SlotBasicInfo_Get);
            SlotBasicInfo.CreateChildPermission(PermissionNames.SlotBasicInfo_Delete);
            SlotBasicInfo.CreateChildPermission(PermissionNames.SlotBasicInfo_Export);
            SlotBasicInfo.CreateChildPermission(PermissionNames.SlotBasicInfo_Print);
            SlotBasicInfo.CreateChildPermission(PermissionNames.SlotBasicInfo_ImportLock);
            SlotBasicInfo.CreateChildPermission(PermissionNames.SlotBasicInfo_ExportLock);
            SlotBasicInfo.CreateChildPermission(PermissionNames.SlotBasicInfo_AreaSelect);
            SlotBasicInfo.CreateChildPermission(PermissionNames.SlotBasicInfo_Shield);
            SlotBasicInfo.CreateChildPermission(PermissionNames.SlotBasicInfo_BatchUpdate);
            #endregion

            #region 单据类型信息
            var BillTypeInfo = basicinfomanage.CreateChildPermission(PermissionNames.BillTypeInfo);
            BillTypeInfo.CreateChildPermission(PermissionNames.BillTypeInfo_Add);
            BillTypeInfo.CreateChildPermission(PermissionNames.BillTypeInfo_Update);
            BillTypeInfo.CreateChildPermission(PermissionNames.BillTypeInfo_Get);
            BillTypeInfo.CreateChildPermission(PermissionNames.BillTypeInfo_Delete);
            BillTypeInfo.CreateChildPermission(PermissionNames.BillTypeInfo_Export);
            BillTypeInfo.CreateChildPermission(PermissionNames.BillTypeInfo_Print);
            #endregion

            #region 出入口基础信息
            var InOutdBasicInfo = basicinfomanage.CreateChildPermission(PermissionNames.InOutdBasicInfo);
            InOutdBasicInfo.CreateChildPermission(PermissionNames.InOutdBasicInfo_Add);
            InOutdBasicInfo.CreateChildPermission(PermissionNames.InOutdBasicInfo_Update);
            InOutdBasicInfo.CreateChildPermission(PermissionNames.InOutdBasicInfo_Get);
            InOutdBasicInfo.CreateChildPermission(PermissionNames.InOutdBasicInfo_Delete);
            InOutdBasicInfo.CreateChildPermission(PermissionNames.InOutdBasicInfo_Export);
            InOutdBasicInfo.CreateChildPermission(PermissionNames.InOutdBasicInfo_Print);
            InOutdBasicInfo.CreateChildPermission(PermissionNames.InOutdBasicInfo_SetTunnel);
            #endregion

            #region 月台基础信息
            var PlatFormBasicInfo = basicinfomanage.CreateChildPermission(PermissionNames.PlatFormBasicInfo);
            PlatFormBasicInfo.CreateChildPermission(PermissionNames.PlatFormBasicInfo_Add);
            PlatFormBasicInfo.CreateChildPermission(PermissionNames.PlatFormBasicInfo_Update);
            PlatFormBasicInfo.CreateChildPermission(PermissionNames.PlatFormBasicInfo_Get);
            PlatFormBasicInfo.CreateChildPermission(PermissionNames.PlatFormBasicInfo_Delete);
            PlatFormBasicInfo.CreateChildPermission(PermissionNames.PlatFormBasicInfo_Export);
            PlatFormBasicInfo.CreateChildPermission(PermissionNames.PlatFormBasicInfo_Print);
            #endregion

            #region 垛形基础信息
            var PackBasicInfo = basicinfomanage.CreateChildPermission(PermissionNames.PackBasicInfo);
            PackBasicInfo.CreateChildPermission(PermissionNames.PackBasicInfo_Add);
            PackBasicInfo.CreateChildPermission(PermissionNames.PackBasicInfo_Update);
            PackBasicInfo.CreateChildPermission(PermissionNames.PackBasicInfo_Get);
            PackBasicInfo.CreateChildPermission(PermissionNames.PackBasicInfo_Delete);
            PackBasicInfo.CreateChildPermission(PermissionNames.PackBasicInfo_Export);
            PackBasicInfo.CreateChildPermission(PermissionNames.PackBasicInfo_Print);
            #endregion


        }
    }
}
