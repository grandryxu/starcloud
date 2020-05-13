import Vue from "vue";
import VueRouter from "vue-router";
import Container from "@/views/Home/Container.vue";
import store from '@/store'
import Utils from '@/utils/index'
// 基础信息管理
import basicInfo from './basicInfo'
// 系统管理中心
import systemManage from './systemManage'
// 入库管理中心
import importStockManage from './importStockManage'
// 出库管理中心
import exportStockManage from './exportStockManage'
// 任务管理中心
import taskManage from './taskManage'
// 业务管理中心
import businessManage from './businessManage'
// 库存管理中心
import Inventorymanagement from './Inventorymanagement'
// 日志管理中心
import logManage from './logManage'
// 库存预警中心
import warehouseWarning from './warehouseWarning'
// 仓库报表中心
import warehouseReportCenter from './warehouseReportCenter'
// 仓库设备中心
import warehouseEquipmentCenter from './warehouseEquipmentCenter'
// 质量管理中心
import qualityManagement from './qualityManagement'

Vue.use(VueRouter);


let routes = [{
    path: '/',
    redirect: '/home'
},
{
    path: "/login",
    name: "Login",
    component: () => import("@/views/Login/index.vue"),
    meta: {
        requireAuth: false
    }
},
{
    path: "/home",
    component: Container,
    meta: {
        icon: "el-icon-menu_icon_home",
        requireAuth: true
    },
    children: [{
        path: "/home/index",
        component: () => import("@/views/Home/Home.vue"),
        name: "home",
        meta: {
            title: "dashboard"
        }
    }]
},
...basicInfo,
...systemManage,
...importStockManage,
...exportStockManage,
...taskManage,
...businessManage,
...Inventorymanagement,
...logManage,
...warehouseWarning,
...warehouseReportCenter,
...warehouseEquipmentCenter,
...qualityManagement
];
// Object.assign(routes, [{
//     path: '/home', meta: { dispaly: true }
// }]);



// let userData = Utils.getStorage("userInfo");
// if (userData != null) {
//     let permissions = userData.data.result.permissions;
//     routes = Utils.filterAsyncRouter(routes, permissions);
//     console.log(routes)
// }
store.commit('COPYORIGINROUTERS',routes);
const router = new VueRouter({
    routes
});


router.afterEach((to, from) => {
    let obj = {
        name: to.name,
        path: to.path,
        meta: to.meta
    }
    let index = store.state.route_record.findIndex(el => el.path == obj.path);
    if (index === -1 && obj.name) {
        store.commit('addRouteRecord', obj)
    }
})

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requireAuth)) {
        if (Utils.getStorage('userInfo') && Utils.getStorage('userInfo').data.result.accessToken) {
            next();
        } else {
            if (to.path === '/login') {
                next();
            } else {
                next({
                    path: '/login'
                });
            }
        }
    } else {
        next();
    }
})

export default router;