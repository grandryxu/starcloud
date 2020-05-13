import Vue from 'vue'
import axios from 'axios'
import App from './App'
import router from './router'
import store from './store'
import iView from 'iview'

import 'iview/dist/styles/iview.css'

import SQLiteAppDAO from './store/sqlitedb'
import WorkersRepository from './repositories/workersRepository'
import IndividualRepository from './repositories/individualRepository'
import TeamRepository from './repositories/teamRepository'

const sqlitedao = new SQLiteAppDAO('labour.db')
const workersRepo = new WorkersRepository(sqlitedao)
const individualRepo = new IndividualRepository(sqlitedao)
const teamRepo = new TeamRepository(sqlitedao)

Object.defineProperties(Vue.prototype, {
  $workersRepo: {
    get: function () {
      return workersRepo
    }
  },
  $individualRepo: {
    get: function () {
      return individualRepo
    }
  },
  $teamRepo: {
    get: function () {
      return teamRepo
    }
  }
})

if (!process.env.IS_WEB) Vue.use(require('vue-electron'))
Vue.http = Vue.prototype.$http = axios
Vue.config.productionTip = false
Vue.use(iView)

new Vue({
  components: {
    App
  },
  router,
  store,
  template: '<App/>'
}).$mount('#app')