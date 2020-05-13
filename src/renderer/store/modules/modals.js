import settingsRepository from '@/repositories/settingsRepository'
const state = {
  login: {
    isVisible: true,
    token: '',
    organizationCode: '',
    loginname: '',
    username: ''
  },
  machineModal: {
    isVisible: false,
    formInline: null
  },
  individualModal: {
    isVisible: false,
    formInline: null
  },
  teamModal: {
    isVisible: false,
    formInline: null
  },
  dbsettingModal: {
    isVisible: false
  },
  workerfullModal:{
    isVisible: false
  },
  individualfullModal:{
    isVisible: false
  },
  teamfullModal:{
    isVisible: false
  },
  captype:'worker',
  workerType: {
    data: []
  },
  groupList: {
    data: []
  },
  selectedProjectCode:{},
  settings: {
    //site:'http://202.103.56.37:8081',
    site: 'http://202.103.56.37:18083',
    currentVersion: '0.0.5',
    appName: '建筑工人实名信息采集端',
    //baseURL: 'http://58.221.137.62:13691/'
    //baseURL: 'http://192.168.20.28:8086/'
    //baseURL: 'http://192.168.113.222:8086'
   // baseURL: 'http://sqint.jsxywg.cn'
      baseURL: 'http://202.103.56.37:8081/reportRest'
    //baseURL: 'http://192.168.20.178:8081/'
    //baseURL: 'http://192.168.20.152:8085/'
    //baseURL: 'http://192.168.20.176:8081/'
  },
  nationlist: [{
    "name": "汉",
    "num": 1
  }, {
    "name": "壮",
    "num": 2
  }, {
    "name": "满",
    "num": 3
  }, {
    "name": "回",
    "num": 4
  }, {
    "name": "苗",
    "num": 5
  }, {
    "name": "维吾尔",
    "num": 6
  }, {
    "name": "土家",
    "num": 7
  }, {
    "name": "彝",
    "num": 8
  }, {
    "name": "蒙古",
    "num": 9
  }, {
    "name": "藏",
    "num": 10
  }, {
    "name": "布依",
    "num": 11
  }, {
    "name": "侗",
    "num": 12
  }, {
    "name": "瑶",
    "num": 13
  }, {
    "name": "朝鲜",
    "num": 14
  }, {
    "name": "白",
    "num": 15
  }, {
    "name": "哈尼",
    "num": 16
  }, {
    "name": "哈萨克",
    "num": 17
  }, {
    "name": "黎",
    "num": 18
  }, {
    "name": "傣",
    "num": 19
  }, {
    "name": "畲",
    "num": 20
  }, {
    "name": "傈僳",
    "num": 21
  }, {
    "name": "仡佬",
    "num": 22
  }, {
    "name": "东乡",
    "num": 23
  }, {
    "name": "高山",
    "num": 24
  }, {
    "name": "拉祜",
    "num": 25
  }, {
    "name": "水",
    "num": 26
  }, {
    "name": "佤",
    "num": 27
  }, {
    "name": "纳西",
    "num": 28
  }, {
    "name": "羌",
    "num": 29
  }, {
    "name": "土",
    "num": 30
  }, {
    "name": "仫佬",
    "num": 31
  }, {
    "name": "锡伯",
    "num": 32
  }, {
    "name": "柯尔克孜",
    "num": 33
  }, {
    "name": "达斡尔",
    "num": 34
  }, {
    "name": "景颇",
    "num": 35
  }, {
    "name": "毛南",
    "num": 36
  }, {
    "name": "撒拉",
    "num": 37
  }, {
    "name": "布朗",
    "num": 38
  }, {
    "name": "塔吉克",
    "num": 39
  }, {
    "name": "阿昌",
    "num": 40
  }, {
    "name": "普米",
    "num": 41
  }, {
    "name": "鄂温克",
    "num": 42
  }, {
    "name": "怒",
    "num": 43
  }, {
    "name": "京",
    "num": 44
  }, {
    "name": "基诺",
    "num": 45
  }, {
    "name": "德昂",
    "num": 46
  }, {
    "name": "保安",
    "num": 47
  }, {
    "name": "俄罗斯",
    "num": 48
  }, {
    "name": "裕固",
    "num": 49
  }, {
    "name": "乌孜别克",
    "num": 50
  }, {
    "name": "门巴",
    "num": 51
  }, {
    "name": "鄂伦春",
    "num": 52
  }, {
    "name": "独龙",
    "num": 53
  }, {
    "name": "塔塔尔",
    "num": 54
  }, {
    "name": "赫哲",
    "num": 55
  }, {
    "name": "珞巴",
    "num": 56
  }]
}

const mutations = {
  SET_PROJECTCODE(state, val) {
    state.selectedProjectCode = val
  },
  SET_CAPTYPE(state, val) {
    state.captype = val
  },
  SET_WORKERTYPE(state, val) {
    state.workerType.data = val
  },
  SET_GROUPLIST(state, val) {
    state.groupList.data = val
  },
  SHOW_MACHINEINFO(state, val) {
    state.machineModal.isVisible = true
    if (typeof val.cardnumber !== 'undefined')
      state.machineModal.formInline = val
    else
      state.machineModal.formInline = null
  },
  HIDE_MACHINEINFO(state) {
    state.machineModal.isVisible = false
    state.machineModal.formInline = null
  },
  SHOW_INDIVIDUAL(state, val) {
    state.individualModal.isVisible = true
    if (typeof val.cardnumber !== 'undefined')
      state.individualModal.formInline = val
    else
      state.individualModal.formInline = null
  },
  HIDE_INDIVIDUAL(state) {
    state.individualModal.isVisible = false
    state.individualModal.formInline = null
  },
  SHOW_TEAM(state, val) {
    state.teamModal.isVisible = true
    if (typeof val.teamName !== 'undefined')
      state.teamModal.formInline = val
    else
      state.teamModal.formInline = null
  },
  HIDE_TEAM(state) {
    state.teamModal.isVisible = false
    state.teamModal.formInline = null
  },
  SHOW_DBSETTINGMODAL(state, val) {
    state.dbsettingModal.isVisible = true
  },
  HIDE_DBSETTINGMODAL(state) {
    state.dbsettingModal.isVisible = false
  },
  SHOW_WORKERFULLMODAL(state, val) {
    state.workerfullModal.isVisible = true
  },
  HIDE_WORKERFULLMODAL(state) {
    state.workerfullModal.isVisible = false
  },
  SHOW_INDIVIDUALFULLMODAL(state, val) {
    state.individualfullModal.isVisible = true
  },
  HIDE_INDIVIDUALFULLMODAL(state) {
    state.individualfullModal.isVisible = false
  },
  SHOW_TEAMFULLMODAL(state, val) {
    state.teamfullModal.isVisible = true
  },
  HIDE_TEAMFULLMODAL(state) {
    state.teamfullModal.isVisible = false
  },
  SHOW_LOGIN(state) {
    state.login.isVisible = true
  },
  HIDE_LOGIN(state) {
    state.login.isVisible = false
  },
  SET_TOKEN(state, token) {
    state.login.token = token
  },
  SET_OGZCODE(state, code) {
    state.login.organizationCode = code
  },
  SET_LOGINNAME(state, name) {
    state.login.loginname = name
  },
  SET_LOGIN_USERNAME(state, username){
    state.login.username = username
  }
}

const actions = {
  showMachineModal({
    commit
  }, val) {
    commit('SHOW_MACHINEINFO', val)
  },
  hideMachineModal({
    commit
  }) {
    commit('HIDE_MACHINEINFO')
  },
  showIndividualModal({
    commit
  }, val) {
    commit('SHOW_INDIVIDUAL', val)
  },
  hideIndividualModal({
    commit
  }) {
    commit('HIDE_INDIVIDUAL')
  },
  showTeamModal({
    commit
  }, val) {
    commit('SHOW_TEAM', val)
  },
  hideTeamModal({
    commit
  }) {
    commit('HIDE_TEAM')
  },
  showDBSettingModal({
    commit
  }, val) {
    commit('SHOW_DBSETTINGMODAL', val)
  },
  hideDBSettingModal({
    commit
  }) {
    commit('HIDE_DBSETTINGMODAL')
  },
  showLoginModal({
    commit
  }) {
    commit('SHOW_LOGIN')
  },
  hideLoginModal({
    commit
  }) {
    commit('HIDE_LOGIN')
  },
  showWorkerFullModal({
    commit
  }) {
    commit('SHOW_WORKERFULLMODAL')
  },
  hideWorkerFullModal({
    commit
  }) {
    commit('HIDE_WORKERFULLMODAL')
  },
  showIndividualFullModal({
    commit
  }) {
    commit('SHOW_INDIVIDUALFULLMODAL')
  },
  hideIndividualFullModal({
    commit
  }) {
    commit('HIDE_INDIVIDUALFULLMODAL')
  },
  showTeamFullModal({
    commit
  }) {
    commit('SHOW_TEAMFULLMODAL')
  },
  hideTeamFullModal({
    commit
  }) {
    commit('HIDE_TEAMFULLMODAL')
  },
  resetToken({
    commit
  }) {
    commit('SET_TOKEN', '')
  },
  setToken({
    commit
  }, val) {
    commit('SET_TOKEN', val)
  },
  setProjectCode({
    commit
  }, val) {
    commit('SET_PROJECTCODE', val)
  },
  setCaptype({
    commit
  }, val) {
    commit('SET_CAPTYPE', val)
  },
  resetOrgcode({
    commit
  }) {
    commit('SET_OGZCODE', '')
  },
  setOrgcode({
    commit
  }, val) {
    commit('SET_OGZCODE', val)
  },
  setLoginUserName({
    commit
  }, val) {
    commit('SET_LOGIN_USERNAME', val)
  },
  resetLoginName({
    commit
  }) {
    commit('SET_LOGINNAME', '')
  },
  setLoginName({
    commit
  }, val) {
    commit('SET_LOGINNAME', val)
  },
  setWorkerType({
    commit
  }, val) {
    commit('SET_WORKERTYPE', val)
  },
  setGroupList({
    commit
  }, val) {
    commit('SET_GROUPLIST', val)
  },
  setUserLoginMode({
    commit
  }, mode) {
    settingsRepository.updateUserlog({
      mode
    })
  },
  setUserLoginName({
    commit
  }, username) {
    settingsRepository.updateUserlog({
      username
    })
  },
  setUserLoginPassword({
    commit
  }, password) {
    settingsRepository.updateUserlog({
      password
    })
  },
}

export default {
  state,
  mutations,
  actions
}