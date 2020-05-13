<template>
    <Modal id="teamfull-modal" v-model="teamListVisible" @on-visible-change="visibleChange" :mask-closable="false"
        :footer-hide="true" :closable="true" fullscreen :transfer="false">
        <div slot="header">
            <Breadcrumb>
                <BreadcrumbItem to="/project"><Button type="primary" shape="circle" size="small"
                        icon="md-home">项目列表</Button></BreadcrumbItem>
                <BreadcrumbItem>{{projectName}}</BreadcrumbItem>
                <BreadcrumbItem>班组列表</BreadcrumbItem>
            </Breadcrumb>
        </div>
        <div class="table-spec">
            <Row>
                <i-col span="6">
                    <Input search placeholder="输入名称查询" style="width:200px;" v-model="keyword" @on-search="search"
                        clearable />
                </i-col>
                <i-col span="18">
                    <Tooltip content="添加班组">
                        <Button @click="addTeam" icon="md-add" shape="circle" type="success">
                            添加
                        </Button>
                    </Tooltip>
                    <!-- <Tooltip content="刷新列表">
                        <Button @click="prolistChange" icon="md-refresh" shape="circle" class="btnspace">
                            刷新
                        </Button>
                    </Tooltip> -->
                </i-col>
            </Row>
        </div>
        <Row>
            <i-col span="24">
                <Table stripe highlight-row :columns="columns" :data="listdata" ref="selecteAll"
                    @on-selection-change="mlistChange" height="400" no-data-text="当前项目下暂无班组信息">
                    <template slot-scope="{ row, index }" slot="action">
                        <Button @click="show(index)" shape="circle" type="info" size="small">
                            编辑
                        </Button>
                        <Button @click="del(index)" shape="circle" type="error" size="small">
                            删除
                        </Button>
                    </template>
                </Table>
                <div id="pager">
                    <Page :total="total" show-total size="small" :page-size="pagesize" @on-change="listChange" />
                </div>
            </i-col>
        </Row>
        <team-modal @reloadPage="prolistChange"></team-modal>
    </Modal>
</template>
<script>
    import TeamModal from '../modals/team/index.vue'

    export default {
        name: 'team',
        components: {
            TeamModal
        },
        computed: {
            baseURL: {
                get() {
                    return this.$store.state.modals.settings.baseURL
                },
                set() {}
            },
            teamListVisible: {
                get() {
                    return this.$store.state.modals.teamfullModal.isVisible
                },
                set() {}
            },
            projectName: {
                get() {
                    return this.$store.state.modals.selectedProjectCode.name
                },
                set() {}
            }
        },
        data() {
            return {
                total: 0,
                pagesize: 12,
                keyword: '',
                listsel: [],
                projectcc: '',
                columns: [{
                        type: 'index',
                        width: 50,
                        align: 'center'
                    },
                    {
                        title: '班组名称',
                        key: 'teamName'
                    },
                    {
                        title: '班组长',
                        key: 'teamLeader'
                    },
                    {
                        title: '班组联系电话',
                        key: 'teamPhoneCall'
                    },
                    {
                        title: '班组总人数',
                        key: 'teamCount'
                    },
                    {
                        title: '操作',
                        slot: 'action',
                        width: 180,
                        align: 'center'
                    }
                ],
                listdata: []
            }
        },
        beforeRouteEnter(to, from, next) {
            next(vm => {
                vm.prolistChange()
            })
        },
        methods: {
            visibleChange(isVisible) {
                if (!isVisible) {
                    this.closeModal()
                }
            },
            closeModal() {
                this.$store.dispatch('hideTeamFullModal')
                this.$router.replace({
                    name: 'project'
                })
            },
            listChange(number) {
                var that = this
                that.$Spin.show()
                that.$teamRepo.getCount(that.keyword, that.projectcc).then((res) => {
                    that.total = res.num
                    that.$teamRepo.getList(that.keyword, number, that.pagesize, that.projectcc).then((ret) => {
                        that.listdata = ret
                    })
                }).catch((err) => {
                    console.log(err)
                }).finally(() => {
                    that.$Spin.hide()
                })
            },
            search() {
                this.listChange(1)
            },
            mlistChange(sel) {
                this.listsel = sel
            },
            addTeam() {
                if (typeof this.$store.state.modals.selectedProjectCode.code === 'undefined') {
                    this.$Notice.info({
                        title: '提醒',
                        desc: '请先选择项目再添加班组！'
                    })
                    return
                }
                this.$store.dispatch('showTeamModal', {})
            },
            show(index) {
                this.$store.dispatch('showTeamModal', this.listdata[index])
            },
            del(index) {
                this.$Modal.confirm({
                    title: '确认',
                    content: `<p>确认删除 ${this.listdata[index].teamName} 吗？</p>`,
                    onOk: () => {
                        var that = this
                        var token = that.$store.state.modals.login.token
                        var updatedata = {
                            id: that.listdata[index].remoteid
                        }
                        
                        that.$http({
                                baseURL: that.baseURL,
                                url: '/appTeam/delete',
                                params: updatedata,
                                method: 'post',
                                headers: {
                                    'Content-Type': 'application/json',
                                    'Authorization': 'Bearer ' + token
                                }
                            })
                            .then(function (data) {
                                
                                if (data.data.success)
                                    that.teamDelete(that.listdata[index].id)
                                else{
                                    that.$Notice.error({
                                        title: '提醒',
                                        desc: data.data.message
                                    })
                                }
                                
                            }).catch(function (error) {
                                
                                that.$Notice.error({
                                    title: '提醒',
                                    desc: '远程服务器异常'
                                })
                            }).finally(() => {
                                that.prolistChange()
                            })
                    }
                })
            },
            teamDelete(id) {
                var that = this
                that.$teamRepo.drop(id).then((res) => {
                    
                    if (res.affected === 1)
                        that.$Notice.success({
                            title: '提醒',
                            desc: '删除成功'
                        })
                }).catch((err) => {
                    console.log(err)
                })
            },
            prolistChange() {
                if (typeof this.$store.state.modals.selectedProjectCode.code === 'undefined')
                    return
                this.projectcc = this.$store.state.modals.selectedProjectCode.code
                var token = this.$store.state.modals.login.token
                var that = this

                that.$http({
                        baseURL: that.baseURL,
                        url: '/appTeam/getTeamsByProjectCode',
                        params: {
                            organizationCode: that.$store.state.modals.login.organizationCode,
                            projectCode: that.projectcc
                        },
                        method: 'get',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded',
                            'Authorization': 'Bearer ' + token
                        }
                    })
                    .then(function (data) {
                        console.log(data);
                        that.$Spin.show({
                            render: (h) => {
                                return h('div', [
                                    h('div', {
                                        'class': 'alert-text'
                                    }, '数据更新中，请稍后...')
                                ])
                            }
                        })
                        if (data.data.code !== 200)
                            return
                        if (!Array.isArray(data.data.data))
                            return
                        var list = data.data.data
                        var insert = []

                        list.forEach((v, i, a) => {
                            if (v.isDel === 0)
                                insert.push({
                                    teamName: v.teamName,
                                    projectCode: v.projectCode,
                                    remoteid: v.id,
                                    teamLeader: v.teamLeader,
                                    teamLeaderIdNumber: v.teamLeaderIdNumber,
                                    teamPhoneCall: v.contact,
                                    teamCount: v.count
                                })
                        })
                        if (insert.length > 0) {
                            insert.forEach((value, index, array) => {
                                that.$teamRepo.getLimitCount(value.projectCode, value
                                    .remoteid).then((
                                    res) => {
                                    if (res.num <= 0) {
                                        that.$teamRepo.create(value).then((ret) => {}).catch((
                                            error) => {
                                            console.log(error)
                                        })
                                    }else{
                                        that.$teamRepo.update(value).then((ret) => {}).catch((
                                            error) => {
                                            console.log(error)
                                        })
                                    }
                                })
                            })
                        }
                    }).catch(function (error) {
                        console.log(error)
                    }).finally(() => {
                        that.$teamRepo.getCount(that.keyword, that.projectcc).then((res) => {
                            that.total = res.num
                            that.$teamRepo.getList(that.keyword, 1, that.pagesize, that.projectcc).then((
                                ret) => {
                                that.listdata = ret
                            })
                        }).catch((err) => {
                            console.log(err)
                        }).finally(() => {
                            that.$Spin.hide()
                        })
                    })
            }
        }
    }
</script>