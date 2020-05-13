// 质量管理中心
import Container from "@/views/Home/Container.vue";
import Store from '@/store/index';
export default [
    {
        path: "/quality",
        component: Container,
        meta: {
          title: "QualityManagement",
          icon: "el-icon-menu_icon_systemmanagement"
        },
        children: [
          {
            path: "/quality/qualitycheckout",          
            component: () => import("@/views/qualityManagement/qualitycheckout/index.vue"),
            name: "quality-qualitycheckout",
            meta: {
              title: "QualityCheckout"
            },
            beforeEnter: (to,from,next) => {
              Store.commit('filterEnterTargetPermissions','QualityManagement.QualityCheckout')
              next()
            }
          },
          {
            path: "/quality/qualityreleased",          
            component: () => import("@/views/qualityManagement/qualityreleased/index.vue"),
            name: "quality-qualityreleased",
            meta: {
              title: "QualityReleased"
            }
          }
          
                
        ]
      }
]