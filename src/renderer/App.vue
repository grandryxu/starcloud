<template>
  <div id="app">
    <top-bar />
    <router-view></router-view>
    <div id="crl">{{appname}}<a @click="open">访问官网</a>
    </div>
  </div>
</template>
<script>
  import TopBar from './components/bar/TopBar.vue'

  export default {
    name: 'istack',
    computed: {
      appname() {
        return this.$store.state.modals.settings.appName
      },
      site() {
        return this.$store.state.modals.settings.site
      }
    },
    components: {
      TopBar
    },
    created() {
      this.$router.push({
        name: 'login'
      })
    },
    methods: {
      open() {
        this.$electron.shell.openExternal(this.site)
      }
    }
  }
</script>

<style>
  #app {
    font-family: 'Microsoft Yahei', sans-serif;
    height: 100vh;
    padding: 0;
  }

  .ivu-modal-mask {
    background: linear-gradient(to top left, #f6f6f6 45%, #f8f8f9 50%, #fff 100%);
  }

  .ivu-modal-content {
    border: 1px solid #dcdee2 !important;
    border-radius: 6px;
    box-shadow: rgba(0, 0, 0, 0.08) 0px 3px 62px 0px !important;
    background: -webkit-linear-gradient(135deg, #f8f8f9, #f8f8f9 66%, white);
  }

  .ivu-modal-confirm-head-title {
    font-weight: normal !important;
  }

  .ivu-page-item a {
    font-family: "Microsoft Yahei" !important;
  }

  .ivu-modal-header {
    -webkit-app-region: no-drag;
    border: none !important;
  }

  #worker-info-modal .ivu-modal,
  #individual-info-modal .ivu-modal {
    top: 20px;
  }

  #new-board-modal .ivu-modal-body {
    padding: 20px 10px 15px 10px !important;
  }

  .ivu-menu-horizontal .ivu-menu-item,
  .ivu-menu-horizontal .ivu-menu-submenu {
    margin: 0 10px;
  }

  .ivu-breadcrumb {
    font-size: 12px !important;
  }

  .ivu-breadcrumb a {
    color: #2b85e4 !important;
    font-weight: bolder;
    text-decoration: underline;
  }

  ::-webkit-scrollbar {
    width: 4px;
    height: 0px;
  }

  /*定义滚动条轨道 内阴影+圆角*/
  ::-webkit-scrollbar-track {
    box-shadow: inset 0 0 3px rgba(70, 70, 70, 0.3);
    background-color: rgb(255, 255, 255);
  }

  /*定义滑块 内阴影+圆角*/
  ::-webkit-scrollbar-thumb {
    border-radius: 10px;
    background: #515a6e;
  }

  .alert-text {
    font-size: 24px;
    color: #000;
  }

  .table-spec {
    margin: 0 auto 14px auto;
  }

  .btnspace {
    margin-left: 10px;
  }

  #pager {
    margin: 20px 0;
    float: right;
  }

  #crl {
    position: fixed;
    bottom: 0px;
    left: 0px;
    right: 0px;
    width: 100vw;
    height: 40px;
    line-height: 40px;
    margin-left: auto;
    margin-right: auto;
    text-align: center;
    padding: 0;
    z-index: 1003;
    background: #f8f8f9;
  }

  #crl a {
    margin-left: 20px;
    color: #000;
  }

  #crl a:hover {
    color: #555;
  }

  video,canvas{
    object-fit: scale-down;
    width: 176px;
    height: 144px;
    background: #f8f8f9;
    overflow: hidden;
  }


</style>