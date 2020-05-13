import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import locale from 'element-ui/lib/locale/lang/en'
import i18n from '@/lang'
// import 'element-ui/lib/theme-chalk/index.css';
import '@/assets/style/element-variables.scss'
import '@/assets/style/index.scss'
// import Pagination from "_c/pagination/index.js"
// import config from '@/mixins/config.js'


import moment from "moment";
import Print from 'vue-printjs'
import echarts from 'echarts'


import utils from '@/utils/index.js'
import DropBox from '@/utils/dropBox.js'
import Clone from '@/utils/clone.js'
import Common from '@/utils/common.js'


Vue.use(ElementUI, {
  size: 'small', zIndex: 3000,
  locale,
  i18n: (key, value) => i18n.t(key, value)
});

Print.install(Vue);

Vue.config.productionTip = false

Vue.prototype.$moment = moment;
Vue.prototype.$utils = utils;
Vue.prototype.$DropBox = DropBox;
Vue.prototype.$echarts = echarts;
Vue.prototype.$Clone = Clone;
Vue.prototype.$Common = Common;

// Vue.mixin(config);

window.rootApp = new Vue({
  i18n,
  router,
  store,
  render: h => h(App),
  watch: {
    // 利用watch方法检测路由变化：
    '$route': function (to, from) {
      let userData = utils.getStorage("userInfo");
      console.log(userData)
      if (userData != null) {
        let permissions = userData.data.result.permissions;
        let routes=store.state.originRouters;
        this.$router.options.routes = utils.filterAsyncRouter(routes, permissions);
        console.log(this.$router.options.routes)
      }
    }
  },
}).$mount('#app')
