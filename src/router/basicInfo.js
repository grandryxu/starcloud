// 基础信息管理
import Container from "@/views/Home/Container.vue";
import Store from '@/store/index'
export default [
  {
    path: "/basic",
    component: Container,
    meta: {
      title: "BasicInfoManage",
      icon: "el-icon-menu_icon_usermanagement"
    },
    children: [
      {
        path: "/basic/materiel",
        component: () => import("@/views/basicInfo/materiel/index.vue"),
        name: "basic-materiel",
        meta: {
          title: "MaterialBasisInfo"
        },
        beforeEnter: (to,from,next) => {
          Store.commit('filterEnterTargetPermissions','BasicInfoManage.MaterialBasisInfo')
          next()
        }
      },
      {
        path: "/basic/unit",
        component: () => import("@/views/basicInfo/unit/index.vue"),
        name: "basic-unit",
        meta: {
          title: "MaterialMeasureUnit"
        },
        beforeEnter: (to,from,next) => {
          Store.commit('filterEnterTargetPermissions','BasicInfoManage.MaterialMeasureUnit')
          next()
        }
      },
      {
        path: "/basic/quality",
        component: () => import("@/views/basicInfo/quality/index.vue"),
        name: "basic-quality",
        meta: {
          title: "MaterialQualityStatus"
        },
        beforeEnter: (to,from,next) => {
          Store.commit('filterEnterTargetPermissions','BasicInfoManage.MaterialQualityStatus')
          next()
        }
      },
      {
        path: "/basic/customType",
        component: () => import("@/views/basicInfo/customType/index.vue"),
        name: "basic-customType",
        meta: {
          title: "CustomerCategoryInfo"
        },
        beforeEnter: (to,from,next) => {
          Store.commit('filterEnterTargetPermissions','BasicInfoManage.CustomTypeInfo')
          next()
        }
      },
      {
        path: "/basic/custom",
        component: () => import("@/views/basicInfo/custom/index.vue"),
        name: "basic-custom",
        meta: {
          title: "CustomerBaseInfo"
        },
        beforeEnter: (to,from,next) => {
          Store.commit('filterEnterTargetPermissions','BasicInfoManage.CustomInfo')
          next()
        }
      },
      {
        path: "/basic/warehouse",
        component: () => import("@/views/basicInfo/warehouse/index.vue"),
        name: "basic-warehouse",
        meta: {
          title: "WarehouseBaseInfo"
        },
        beforeEnter: (to,from,next) => {
          Store.commit('filterEnterTargetPermissions','BasicInfoManage.WarehouseInfo')
          next()
        },
      },
      {
        path: "/basic/area",
        component: () => import("@/views/basicInfo/area/index.vue"),
        name: "basic-area",
        meta: {
          title: "AreaBasicInfo"
        },
        beforeEnter: (to,from,next) => {
          Store.commit('filterEnterTargetPermissions','BasicInfoManage.AreaBasicInfo')
          next()
        }
      },
      {
        path: "/basic/slot",
        component: () => import("@/views/basicInfo/slot/index.vue"),
        name: "basic-slot",
        meta: {
          title: "SlotBasicInfo"
        },
        beforeEnter: (to,from,next) => {
          Store.commit('filterEnterTargetPermissions','BasicInfoManage.SlotBasicInfo')
          next()
        }
      },
      {
        path: "/basic/bill",
        component: () => import("@/views/basicInfo/bill/index.vue"),
        name: "basic-bill",
        meta: {
          title: "BillTypeInfo"
        },
        beforeEnter: (to,from,next) => {
          Store.commit('filterEnterTargetPermissions','BasicInfoManage.BillTypeInfo')
          next()
        }
      },
      {
        path: "/basic/port",
        component: () => import("@/views/basicInfo/port/index.vue"),
        name: "basic-port",
        meta: {
          title: "InOutdBasicInfo"
        },
        beforeEnter: (to,from,next) => {
          Store.commit('filterEnterTargetPermissions','BasicInfoManage.InOutdBasicInfo')
          next()
        }
      },
      {
        path: "/basic/platForm",
        component: () => import("@/views/basicInfo/platForm/index.vue"),
        name: "basic-platForm",
        meta: {
          title: "PlatFormBasicInfo"
        },
        beforeEnter: (to,from,next) => {
          Store.commit('filterEnterTargetPermissions','BasicInfoManage.PlatFormBasicInfo')
          next()
        }
      },
      {
        path: "/basic/pack",
        component: () => import("@/views/basicInfo/pack/index.vue"),
        name: "basic-pack",
        meta: {
          title: "packBasicInfo"
        },
        beforeEnter: (to,from,next) => {
          Store.commit('filterEnterTargetPermissions','BasicInfoManage.PackBasicInfo')
          next()
        }
      }
    ]
  }
]