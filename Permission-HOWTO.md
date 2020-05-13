
## 权限控制简单实现参考

*本文档介绍WMS系统权限控制基础代码实现，仅供参考*

>通常，权限控制需要根据前后端配置的结合来实现，本系统前端部分主要依据当前登录用户的权限控制符列表进行前端控制隐藏或者disable；后端主要依据权限控制符注解实现后端权限验证。

#### 参考步骤

以客户信息模块为例加以说明：

1. 后端权限标识符定义

#### 定义权限标识

在PermissionNames类中按模块添加权限标识符，如：

```c#
#region 客户信息
        public const string CustomInfo_List = "BasicInfoManage.CustomInfo";

        public const string CustomInfo_Add = "BasicInfoManage.CustomInfo.Add";

        public const string CustomInfo_Update = "BasicInfoManage.CustomInfo.Update";

        public const string CustomInfo_Get = "BasicInfoManage.CustomInfo.Get";

        public const string CustomInfo_Delete = "BasicInfoManage.CustomInfo.Delete";
        #endregion
```

这样，在具体实际权限控制的注解中就可以引用，减少字符串输入出错概率，如：

```c#
[AbpAuthorize(PermissionNames.CustomInfo_Add)]
        public override async Task<CustomInfoDto> Create(CustomInfoCreatedDto input)
        {
            ///具体实际业务略
        }
```

按模块定义权限标识，新建一个类，继承AuthorizationProvider，在SetPermissions方法中具体定义标识符：

```c#
public class BasicInfoManageAuthorizationProvider : AuthorizationProvider
    {
        public override void SetPermissions(IPermissionDefinitionContext context)
        {
            //权限标识符的层级关系定义基本上完全参照系统模块目录结构
            //基础信息管理权限标识
            var basicinfomanage = context.CreatePermission("BasicInfoManage");

            #region 客户信息管理
            var custominfo = basicinfomanage.CreateChildPermission(PermissionNames.CustomInfo_List);
            custominfo.CreateChildPermission(PermissionNames.CustomInfo_Get);
            custominfo.CreateChildPermission(PermissionNames.CustomInfo_Add);
            custominfo.CreateChildPermission(PermissionNames.CustomInfo_Delete);
            custominfo.CreateChildPermission(PermissionNames.CustomInfo_Update);
            #endregion

            #region 客户类型信息管理
            var customtypeinfo = basicinfomanage.CreateChildPermission(PermissionNames.CustomTypeInfo_List);
            customtypeinfo.CreateChildPermission(PermissionNames.CustomTypeInfo_Get);
            customtypeinfo.CreateChildPermission(PermissionNames.CustomTypeInfo_Add);
            customtypeinfo.CreateChildPermission(PermissionNames.CustomTypeInfo_Delete);
            customtypeinfo.CreateChildPermission(PermissionNames.CustomTypeInfo_Update);
            #endregion
        }
    }
```

请注意当涉及列表权限的标识符时，与前端路由定义name节点值保持一致，如仓库设备中心路由定义Warehouse_Equipment_RGV_List：

```javascript
// 仓库设备中心
import Container from "@/views/Home/Container.vue";
export default [{
    path: "/warehouseEquipment",
    component: Container,
    meta: {
        title: "WareEquipment",
        icon: "el-icon-menu_icon_systemmanagement"
    },
    children: [{
        path: "/warehouseEquipment/rgvManagement",
        component: () => import("@/views/warehouseEquipmentCenter/rgvStateManagement/index.vue"),
        name: "Warehouse.Equipment.RGV.List",
        meta: {
            title: "RGVStateManage"
        }
    },
    {
        path: "/warehouseEquipment/stackerManagement",
        component: () => import("@/views/warehouseEquipmentCenter/stackerStateManagement/index.vue"),
        name: "warehouseEquipment-stackerManagement",
        meta: {
            title: "StackerStateManage"
        }
    }
    ]
}]
```

另外，在系统管理-系统模块管理添加权限标识符时，也要前后端保持一致，否则无法生效。

定义好的权限符类需要注入应用方可生效，请在WMSApplicationModule类的PreInitialize方法中添加，如：

#### 权限注入

```c#
public override void PreInitialize()
        {
            Configuration.Authorization.Providers.Add<WMSAuthorizationProvider>();
            //注入仓库设备管理权限标识符集
            Configuration.Authorization.Providers.Add<WarehouseEquipmentAuthorizationProvider>();
            //注入基础信息管理权限标识符集
            Configuration.Authorization.Providers.Add<BasicInfoManageAuthorizationProvider>();
        }
```

2. 前端启用权限路由控制

#### 前端路由显示控制核心

```html
<template v-for="child in route.children">
  <el-menu-item v-if="!child.hidden" :index="child.path" :key="child.path">
    {{$t('route.' + child.meta.title)}}
  </el-menu-item>
</template>
```

可见前端路由显示是根据children节点的hidden值控制的。
若要启用前端的路由权限控制，请取消rouer文件夹下index.js文件如下代码片段注释：

```javascript
let userData = Utils.getStorage("userInfo");
if (null !== userData) {
    let p = userData.data.result.permissions;
    routes.forEach((v, i, a) => {
        if (v.path !== '/' && v.path !== '/login' && v.path !== '/home') {
            v.children.forEach((vv, ii, aa) => {
                if (p.indexOf(vv.name) !== -1) {
                    vv.hidden = false;
                } else {
                    vv.hidden = true;
                }
            })
        }
    })
}
```

3. 其他
   按钮的控制显示可参考路由的实现方式实现，可结合导航守卫（路由独享守卫），或者将添加、删除、修改按钮归类为通用的component，进行显示、隐藏控制。

   以下是客户信息模块参考实现：

   ```html
   <el-button type="primary" plain icon="el-icon-plus" @click="click_add" v-if="canAdd">新增</el-button>
   					<el-button type="primary" plain icon="el-icon-edit-outline" @click="handleEdit" v-if="canEdit">编辑</el-button>
   					<el-button type="primary" plain icon="el-icon-delete" @click="handleDelete" v-if="canDelete">删除</el-button>
   					<el-button type="primary" plain icon="el-icon-document" v-if="canExport">导出</el-button>
   					<el-button type="primary" plain icon="el-icon-printer" v-if="canPrint">打印</el-button>
   ```

   ```javascript
   //根据当前用户权限标识初始化按钮状态，供参考
   			btnInit() {
   				let permissions = Utils.getStorage("userInfo").data.result.permissions;
   				if (permissions.indexOf("BasicInfoManage.CustomInfo.Add") !== -1) {
   					this.canAdd = true;
   				} else {
   					this.canAdd = false;
   				}
   				if (permissions.indexOf("BasicInfoManage.CustomInfo.Delete") !== -1) {
   					this.canDelete = true;
   				} else {
   					this.canDelete = false;
   				}
   				if (permissions.indexOf("BasicInfoManage.CustomInfo.Update") !== -1) {
   					this.canUpdate = true;
   				} else {
   					this.canUpdate = false;
   				}
   				if (permissions.indexOf("BasicInfoManage.CustomInfo.Print") !== -1) {
   					this.canPrint = true;
   				} else {
   					this.canPrint = false;
   				}
   				if (permissions.indexOf("BasicInfoManage.CustomInfo.Export") !== -1) {
   					this.canExport = true;
   				} else {
   					this.canExport = false;
   				}
   			}
   ```

   