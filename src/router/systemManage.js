// 系统管理中心
import Container from "@/views/Home/Container.vue";
import Store from '@/store/index';
export default [
    {
        path: "/system",
        component: Container,
        meta: {
          title: "SystemManageCenter",
          icon: "el-icon-menu_icon_systemmanagement"
        },
        children: [
          {
            path: "/system/company",
            component: () => import("@/views/systemManage/companyBasicInfo/index.vue"),
            name: "system-company",
            meta: {
              title: "CompanyBasicInfo"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','SystemManageCenter.CompanyBasicInfo')
              next()
            }
          },
          {
            path: "/system/department",
            component: () => import("@/views/systemManage/departmentBasicInfo/index.vue"),
            name: "system-department",
            meta: {
              title: "DepartmentBasicInfo"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','SystemManageCenter.DepartmentBasicInfo')
              next()
            }
          },
          {
            path: "/system/staff",
            component: () => import("@/views/systemManage/staffBasicInfo/index.vue"),
            name: "system-staff",
            meta: {
              title: "StaffBasicInfo"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','SystemManageCenter.StaffBasicInfo')
              next()
            }
          },
          {
            path: "/system/role",
            component: () => import("@/views/systemManage/roleBasicInfo/index.vue"),
            name: "system-role",
            meta: {
              title: "RoleBasicInfo"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','SystemManageCenter.RoleBasicInfo')
              next()
            }
          },
          {
            path: "/system/system",
            component: () => import("@/views/systemManage/systemModelManage/index.vue"),
            name: "system-system",
            meta: {
              title: "SystemModuleManage"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','SystemManageCenter.SystemModuleManage')
              next()
            }
          },
          {
            path: "/system/move",
            component: () => import("@/views/systemManage/moveModelManage/index.vue"),
            name: "system-move",
            meta: {
              title: "MoveModuleManage"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','SystemManageCenter.MoveModuleManage')
              next()
            }
          },
          // {
          //   path: "/system/warehouse",
          //   component: () => import("@/views/systemManage/warehouseBasicInfo/index.vue"),
          //   name: "system-warehouse",
          //   meta: {
          //     title: "WarehouseBasicManage"
          //   },
          //   beforeEnter: (to,from,next) => {
          //     Store.commit('filterEnterTargetPermissions','SystemManageCenter.WarehouseBasicManage')
          //     next()
          //   }
          // },
          {
            path: "/system/slot",
            component: () => import("@/views/systemManage/slotInit/index.vue"),
            name: "system-slot",
            meta: {
              title: "SlotInit"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','SystemManageCenter.SlotInit')
              next()
            }
          },
        ]
      }
]