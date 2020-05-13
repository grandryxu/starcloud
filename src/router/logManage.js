//日志管理中心
import Container from "@/views/Home/Container.vue";
import Store from '@/store/index';
export default [{
    path: "/logmanage",
    component: Container,
    meta: {
        title: "LogManage",
        icon: "el-icon-message-solid"
    },
    children: [{
        path: "/logmanage/loginlog",
        component: () => import("@/views/logManage/loginLog/index.vue"),
        name: "logmanage-loginlog",
        meta: {
            title: "LoginLogManage"
        },
        beforeEnter: (to,from,next) => {
            Store.commit('filterEnterTargetPermissions','LogManage.LoginLogManage')
            next()
        }
    },
    {
        path: "/logmanage/optlog",
        component: () => import("@/views/logManage/optLog/index.vue"),
        name: "logmanage-optlog",
        meta: {
            title: "OptLogManage"
        },
        beforeEnter: (to,from,next) => {
            Store.commit('filterEnterTargetPermissions','LogManage.OptLogManage')
            next()
        }
    },
    {
        path: "/logmanage/machinealert",
        component: () => import("@/views/logManage/machineAlert/index.vue"),
        name: "logmanage-machinealert",
        meta: {
            title: "MachineAlertLog"
        },
        beforeEnter: (to,from,next) => {
            Store.commit('filterEnterTargetPermissions','LogManage.MachineAlertLog')
            next()
        }
    },
    {
        path: "/logmanage/importApplyLog",
        component: () => import("@/views/logManage/importApplyLog/index.vue"),
        name: "logmanage-importApplyLog",
        meta: {
            title: "ImportApplyLog"
        },
        beforeEnter: (to,from,next) => {
            Store.commit('filterEnterTargetPermissions','LogManage.ImportApplyLog')
            next()
        }
    },
    {
        path: "/logmanage/exportBillSyncLog",
        component: () => import("@/views/logManage/exportBillSyncLog/index.vue"),
        name: "logmanage-exportBillSyncLog",
        meta: {
            title: "ExportBillSyncLog"
        },
        beforeEnter: (to,from,next) => {
            Store.commit('filterEnterTargetPermissions','LogManage.ExportBillSyncLog')
            next()
        }
    }
]
}]