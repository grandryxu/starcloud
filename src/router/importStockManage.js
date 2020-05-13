// 入库管理中心
import Container from "@/views/Home/Container.vue";
import Store from '@/store/index';
export default [
    {
        path: "/importStock",
        component: Container,
        meta: {
          title: "ImportWarehouseManage",
          icon: "el-icon-s-unfold"
        },
        children: [
          {
            path: "/importStock/bill",
            component: () => import("@/views/importStockManage/importBillManage/index.vue"),
            name: "importStock-bill",
            meta: {
              title: "ImportBillManage"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','ImportWarehouseManage.ImportBillManage')
              next()
            }
          },
          {
            path: "/importStock/billBody",
            component: () => import("@/views/importStockManage/importBillBodyManage/index.vue"),
            name: "importStock-billBody",
            meta: {
              title: "ImportBillBodyManage"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','ImportWarehouseManage.ImportBillBodyManage')
              next()
            }
          },
          {
            path: "/importStock/stock",
            component: () => import("@/views/importStockManage/emptyStockImport/index.vue"),
            name: "importStock-stock",
            meta: {
              title: "EmptyStockImport"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','ImportWarehouseManage.EmptyStockImport')
              next()
            }
          }
        ]
      }
]