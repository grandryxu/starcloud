// 仓库设备中心
import Container from "@/views/Home/Container.vue";
import Store from '@/store/index';
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
        },
        beforeEnter: (to,from,next) => {
            Store.commit('filterEnterTargetPermissions','Warehouse.Equipment.RGV')
            next()
        }
    },
    {
        path: "/warehouseEquipment/stackerManagement",
        component: () => import("@/views/warehouseEquipmentCenter/stackerStateManagement/index.vue"),
        // name: "warehouseEquipment-stackerManagement",
        name:"Warehouse.Equipment.Stacker.List",
        meta: {
            title: "StackerStateManage"
        },
        beforeEnter: (to,from,next) => {
            Store.commit('filterEnterTargetPermissions','Warehouse.Equipment.Stacker')
            next()
        }
    }
    ]
}]