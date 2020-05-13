// 库存预警中心
import Container from "@/views/Home/Container.vue";
import Store from '@/store/index';
export default [
    {
        path: "/warehouseWarning",
        component: Container,
        meta: {
          title: "WarehouseWarning",
          icon: "el-icon-warning"
        },
        children: [
          {
            path: "/warehouseWarning/dull",
            component: () => import("@/views/warehouseWarning/dullStockWarning/index.vue"),
            name: "warehouseWarning-dull",
            meta: {
              title: "DullStockWarning",
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','WarehouseWarning.DullStockWarning')
              next()
            }
          },
          {
            path: "/warehouseWarning/validity",
            component: () => import("@/views/warehouseWarning/validityStockWarning/index.vue"),
            name: "warehouseWarning-validity",
            meta: {//效期库存预警
              title: "ValidityStockWarning"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','WarehouseWarning.ValidityStockWarning')
              next()
            }
          },
          {
            path: "/warehouseWarning/threshold",
            component: () => import("@/views/warehouseWarning/inventoryThresholdWarning/index.vue"),
            name: "warehouseWarning-threshold",
            meta: {//库存阈值预警
              title: "InventoryThresholdWarning"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','WarehouseWarning.InventoryThresholdWarning')
              next()
            }
          },
          {
            path: "/warehouseWarning/import",
            component: () => import("@/views/warehouseWarning/importExceptionWarning/index.vue"),
            name: "warehouseWarning-imports",
            meta: {//入库异常预警
              title: "ImportExceptionWarning"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','WarehouseWarning.ImportExceptionWarning')
              next()
            }
          },
          {
            path: "/warehouseWarning/export",
            component: () => import("@/views/warehouseWarning/exportExceptionWarning/index.vue"),
            name: "warehouseWarning-exports",
            meta: {//出库异常预警
              title: "ExportExceptionWarning"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','WarehouseWarning.ExportExceptionWarning')
              next()
            }
          },
          {
            path: "/warehouseWarning/inventory",
            component: () => import("@/views/warehouseWarning/inventoryExceptionWarning/index.vue"),
            name: "warehouseWarning-inventory",
            meta: {//库存异常预警
              title: "InventoryExceptionWarning"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','WarehouseWarning.InventoryExceptionWarning')
              next()
            }
          },
        ]
      }
]