// 业务管理中心
import Container from "@/views/Home/Container.vue";
import Store from '@/store/index';
export default [
    {
        path: "/businessManage",
        component: Container,
        meta: {
          title: "BusinessManage",
          icon: "el-icon-s-claim"
        },
        children: [
          {
            path: "/businessManage/strategyMonitor",
            component: () => import("@/views/businessManage/strategyMonitor/index.vue"),
            name: "businessManage-strategyMonitor",
            meta: {
              title: "StrategyMonitorManage"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','BusinessManage.StrategyMonitorManage')
              next()
            }
          },
          {
            path: "/businessManage/strategyWarehousing",
            component: () => import("@/views/businessManage/strategyWarehousing/index.vue"),
            name: "businessManage-strategyWarehousing",
            meta: {
              title: "StrategyPutawayManage"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','BusinessManage.StrategyPutawayManage')
              next()
            }
          },
          {
            path: "/businessManage/strategyDistribution",
            component: () => import("@/views/businessManage/strategyDistribution/index.vue"),
            name: "businessManage-strategyDistribution",
            meta: {
              title: "StrategyDistriManage"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','BusinessManage.StrategyDistriManage')
              next()
            }
          },
          {
            path: "/businessManage/encodingRule",
            component: () => import("@/views/businessManage/encodingRule/index.vue"),
            name: "businessManage-encodingRule",
            meta: {
              title: "EncodingRuleManage"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','BusinessManage.EncodingRuleManage')
              next()
            }
          }
        ]
      }
]