
import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({

  routes: [
    {
      path: '/',
      name: 'login',
      component: require('@/components/modals/setting/Login.vue').default
    },
    {
      path: '/project',
      name: 'project',
      component: require('@/components/LandingPage/').default
    },
    {
      path: '/worker',
      name: 'worker',
      component: require('@/components/Workers/index.vue').default
    },
    {
      path: '/individual',
      name: 'individual',
      component: require('@/components/Individual/index.vue').default
    },
    {
      path: '/team',
      name: 'team',
      component: require('@/components/Team/index.vue').default
    }
  ]
})
