<template>
    <div>
        <div class="topBar">
            <div id="logocontent">
                <span id="logot">{{appname}}</span>
                <span id="logov">v{{version}}</span>
            </div>
            <div class="actionBtn-container">
                <div class="minimize actionBtn" @click="minimize">
                    <Tooltip content="最小化窗口" placement="bottom-end" :transfer="true" :delay="500">
                        <Icon type="md-remove" color="#555" :size="20"></Icon>
                    </Tooltip>
                </div>
                <div class="close actionBtn" @click="closeApp">
                    <Tooltip content="关闭并退出系统" placement="bottom-end" :transfer="true" :delay="500">
                        <Icon type="md-close" color="#ed4014" :size="20"></Icon>
                    </Tooltip>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    const remote = require('electron').remote
    export default {
        name: 'TopBar',
        components: {

        },
        computed: {
            version() {
                return this.$store.state.modals.settings.currentVersion
            },
            appname() {
                return this.$store.state.modals.settings.appName
            },
            baseURL: {
                get() {
                    return this.$store.state.modals.settings.baseURL
                },
                set() {
                    // TODO: when implementing customizing db location
                }
            }
        },
        data() {
            return {
                grouplist: []
            }
        },
        methods: {
            closeApp() {
                this.$Modal.confirm({
                    title: '确认',
                    content: '<p>确认关闭并退出本系统？</p>',
                    onOk: () => {
                        remote.app.quit()
                    }
                })
            },
            minimize() {
                remote.BrowserWindow.getFocusedWindow().minimize()
            }
        }
    }
</script>
<style>
    .topBar {
        -webkit-app-region: drag;
        cursor: pointer;
        height: 40px;
        position: fixed;
        z-index: 11;
        top: 0px;
        left: 0px;
        width: 100%;
        padding: 6px;
        transition: all .3s;
        user-select: none;
        text-align: left;
        font-size: 14px;
        line-height: 30px;
        background: #f8f8f9;
        box-shadow: 0 1px 4px 0 #f8f8f8;
    }

    .nav {
        height: 40px;
        position: fixed;
        z-index: 11;
        top: 40px;
        left: 0px;
        width: 100vw;
        padding: 0px;
        transition: all .3s;
        user-select: none;
        text-align: left;
        background: #fff;
    }

    #logocontent {
        color: #515a6e;
        padding: 0 0 0 8px;
        cursor: -webkit-grab;
        cursor: -webkit-grabbing;
        cursor: grab;
        cursor: grabbing;

    }

    #logot {
        font-weight: bold;
    }

    .actionBtn-container {
        position: fixed;
        right: 0;
        top: 0;
        display: flex;
    }

    .actionBtn {
        -webkit-app-region: no-drag;
        font-size: 18px;
        width: 45px;
        height: 35px;
        text-align: center;
        -webkit-transition: all .3s;
        transition: all .3s;
        opacity: .7;
        margin: 0 6px;
    }

    .actionBtn:hover {
        opacity: 1;
    }

    .close {
        margin: 0 5px 0 0;
    }
</style>