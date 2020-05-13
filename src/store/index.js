import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate"
import { setLanguage } from '@/utils/cookies'
import { getLocale } from '@/lang'
import actions from './actions'
import mutations from './mutations'

Vue.use(Vuex)
export default new Vuex.Store({
  state: {
    api_BaseURL:process.env.NODE_ENV === "production" ?'http://localhost:21022':'./',//将“http://localhost:21022”替换成api接口地址即可
    c_pageSize: 20,
    route_record: [
      {
        name: 'home',
        path: '/home/index',
        meta: {
          title: 'dashboard'
        }
      }
    ],
    enterTargetPagePermissions:[],
    originRouters:[],
    currentPage:null,
    currentLang:getLocale()
  },
  mutations,
  actions,
  modules: {
    AppMoudule: {
      state: {
        language: getLocale()
      },
      actions: {
        SetLanguage(state,language) {
          this.commit('SET_LANGUAGE',language);
          // this.commit('INTERNATIONALIZATION')
        },
        GETCURRENTPAGE(state,currentPage){
          //state.currentPage = currentPage; 
          this.commit('GETCURRENTPAGE',currentPage) 
        },
      },
      mutations: {
        SET_LANGUAGE(state,language) {
          state.language = language
          setLanguage(this.state.language)
        },
        GETCURRENTPAGE(state,currentPage){
          state.currentPage = currentPage;
          
        },
      }
    }
  },
  plugins: [
    createPersistedState({
      storage: window.sessionStorage
    })
  ]
})
