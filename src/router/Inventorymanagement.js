// 库存管理中心
import Container from "@/views/Home/Container.vue";
import Store from '@/store/index';
export default [
    {
        path: "/Inventory",
        component: Container,
        meta: {
          title: "InventoryManage",
          icon: "el-icon-s-operation"
        },
        children: [
          {
            path: "/Inventory/stocking",          
            component: () => import("@/views/Inventorymanagement/stocking/index.vue"),
            name: "Inventory-stocking",
            meta: {// 盘点管理
              title: "InventoryStockingManage"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','InventoryManage.InventoryStockingManage')
              next()
            }
          },
          {
            path: "/Inventory/visualization",          
            component: () => import("@/views/Inventorymanagement/visualizationManage/index.vue"),
            name: "Inventory-visualization",
            meta: {//可视化库存
              title: "VisualizationManage"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','InventoryManage.VisualizationManage')
              next()
            }
          },
          {
            path: "/Inventory/totalinventory",          
            component: () => import("@/views/Inventorymanagement/totalinventory/index.vue"),
            name: "Inventory-totalinventory",
            meta: {//总库存管理
              title: "TotalInventoryManage"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','InventoryManage.TotalInventoryManage')
              next()
            }
          },
          {
            path: "/Inventory/slotinventory",          
            component: () => import("@/views/Inventorymanagement/slotinventory/index.vue"),
            name: "Inventory-slotinventory",
            meta: {//库位库存管理
              title: "SlotInventoryManage"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','InventoryManage.SlotInventoryManage')
              next()
            }
          },
          {
            path: "/Inventory/stockinventory",          
            component: () => import("@/views/Inventorymanagement/stockinventory/index.vue"),
            name: "Inventory-stockinventory",
            meta: {//托盘库存管理
              title: "StockInventoryManage"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','InventoryManage.StockInventoryManage')
              next()
            }
          }                                     
        ]
      }
]