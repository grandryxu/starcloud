// 仓库报表中心
import Container from "@/views/Home/Container.vue";
import Store from '@/store/index';
export default [
    {
        path: "/warehouseReport",
        component: Container,
        meta: {
          title: "WarehouseReport",
          icon: "el-icon-s-order"
        },
        children: [
          {
            path: "/warehouseReport/standingBook",
            component: () => import("@/views/warehouseReportCenter/exportImportStandingBook/index.vue"),
            name: "warehouseReport-standingBook",
            meta: {
              title: "ExportImportStandingBook"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','WarehouseReport.ExportImportStandingBook')
              next()
            }
          },
          {
            path: "/warehouseReport/useRatio",
            component: () => import("@/views/warehouseReportCenter/storageUseRatio/index.vue"),
            name: "warehouseReport-useRatio",
            meta: {
              title: "StorageUseRatio"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','WarehouseReport.StorageUseRatio')
              next()
            }
          },
          {
            path: "/warehouseReport/department",
            component: () => import("@/views/warehouseReportCenter/importReportForm/index.vue"),
            name: "warehouseReport-importReport",
            meta: {
              title: "ImportReportForm"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','WarehouseReport.ImportReportForm')
              next()
            }
          },
          {
            path: "/warehouseReport/exportReport",
            component: () => import("@/views/warehouseReportCenter/exportReportForm/index.vue"),
            name: "warehouseReport-exportReport",
            meta: {
              title: "ExportReportForm"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','WarehouseReport.ExportReportForm')
              next()
            }
          },
          {
            path: "/warehouseReport/inventoryInfo",
            component: () => import("@/views/warehouseReportCenter/inventoryInfo/index.vue"),
            name: "warehouseReport-storageReport",
            meta: {
              title: "inventoryReportForm"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','WarehouseReport.InventoryReportForm')
              next()
            }
          },
          {
            path: "/warehouseReport/customReport",
            component: () => import("@/views/warehouseReportCenter/CustomReport/index.vue"),
            name: "warehouseReport-customReport",
            meta: {
              title: "customReport"
            },
            // beforeEnter: (to,from,next) => {
            //   Store.commit('filterEnterTargetPermissions','WarehouseReport.InventoryReportForm')
            //   next()
            // }
          },
        ]
      }
]