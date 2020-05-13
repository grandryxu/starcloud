// 出库管理中心
import Container from "@/views/Home/Container.vue";
import Store from '@/store/index';
export default [
    {
        path: "/exportStock",
        component: Container,
        meta: {
          title: "ExportWarehouseManage",
          icon: "el-icon-s-fold"
        },
        children: [
          {
            path: "/exportStock/bill",
            component: () => import("@/views/exportStockManage/exportBillManage/index.vue"),
            name: "exportStock-bill",
            meta: {
              title: "ExportBillManage"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','ExportWarehouseManage.ExportBillManage')
              next()
            }
          },
          {
            path: "/exportStock/billBody",
            component: () => import("@/views/exportStockManage/exportBillBodyManage/index.vue"),
            name: "exportStock-billBody",
            meta: {
              title: "ExportBillBodyManage"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','ExportWarehouseManage.ExportBillBodyManage')
              next()
            }
          },
          {
            path: "/exportStock/stock",
            component: () => import("@/views/exportStockManage/emptyStockExport/index.vue"),
            name: "exportStock-stock",
            meta: {
              title: "EmptyStockExport"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','ExportWarehouseManage.EmptyStockExport')
              next()
            }
          }
        ]
      }
]