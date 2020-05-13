// 任务管理中心
import Container from "@/views/Home/Container.vue";
import Store from '@/store/index';
export default [
    {
        path: "/task",
        component: Container,
        meta: {
          title: "TaskManage",
          icon: "el-icon-s-grid"
        },
    children: [
          {
            path: "/task/maintask",          
            component: () => import("@/views/taskManage/mainTask/index.vue"),
            name: "task-maintask",
            meta: {
              title: "MainTaskManage"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','TaskManage.MainTaskManage')
              next()
            }
          },
          {
            path: "/task/historytask",          
            component: () => import("@/views/taskManage/historytask/index.vue"),
            name: "task-taskManage",
            meta: {
              title: "MainHistorytask"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','TaskManage.MainHistorytask')
              next()
            }
          },
          {
            path: "/task/flatBankTask",          
            component: () => import("@/views/taskManage/flatBankTask/index.vue"),
            name: "task-flatBankTask",
            meta: {
              title: "FlatBankTask"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','TaskManage.FlatBankTask')
              next()
            }
          },
          {
            path: "/task/pickingTask",          
            component: () => import("@/views/taskManage/pickingTask/index.vue"),
            name: "task-pickingTask",
            meta: {
              title: "PickingTask"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','TaskManage.PickingTask')
              next()
            }
          },
          {
            path: "/task/AGVTask",          
            component: () => import("@/views/taskManage/AGVTask/index.vue"),
            name: "task-AGVTask",
            meta: {
              title: "AGVTask"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','TaskManage.AGVTask')
              next()
            }
          },
          {
            path: "/task/RGVTask",          
            component: () => import("@/views/taskManage/RGVTask/index.vue"),
            name: "task-RGVTask",
            meta: {
              title: "RGVTask"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','TaskManage.RGVTask')
              next()
            }
          }
        ]
      }
]