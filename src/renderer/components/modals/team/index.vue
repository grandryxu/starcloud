<template>
    <Modal id="team-info-modal" v-model="isVisible" @on-visible-change="visibleChange" :mask-closable="false"
        :footer-hide="true" width="420" :closable="true">
        <p slot="header">
            <span style="font-weight:bolder;font-size:14px;">班组信息{{opt}}</span>
        </p>
        <Form ref="formInline" :model="formInline" :rules="ruleInline" :label-width="120">
            <FormItem prop="teamName" label="班组名称">
                <i-input type="text" v-model.trim="formInline.teamName" placeholder="班组名称" style="width: 200px">
                </i-input>
            </FormItem>
            <FormItem label="所在项目">
                <span>{{projectName}}</span>
            </FormItem>
            <Row>
                <i-col span="24">
                    <Button type="info" icon="md-checkmark" @click="handleSubmit('formInline')" shape="circle"
                        style="width:100px;margin:0 0 6px 120px;">信息提交</Button>
                </i-col>
            </Row>
        </Form>
    </Modal>
</template>
<script>
    export default {
        name: 'team-info-modal',
        computed: {
            isVisible: {
                get() {
                    return this.$store.state.modals.teamModal.isVisible
                },
                set() {}
            },
            opt() {
                return this.$store.state.modals.teamModal.formInline === null ? '新增' : '修改'
            },
            baseURL: {
                get() {
                    return this.$store.state.modals.settings.baseURL
                },
                set() {}
            }
        },
        data() {
            return {
                unteamWorkerList: [],
                projectName: '',
                formInline: {
                    id: 0,
                    teamName: '',
                    projectCode: '',
                    
                },
                ruleInline: {
                    teamName: [{
                        required: true,
                        message: '未填写班组名称'
                    }]
                }
            }
        },
        methods: {
            
            handleSubmit(name) {
                var that = this
                var token = that.$store.state.modals.login.token
                that.$refs[name].validate(valid => {
                    if (valid) {
                        if (that.formInline.id <= 0) {
                            that.$teamRepo.getCount(that.formInline.teamName, that.formInline.projectCode)
                                .then((res) => {
                                    var num = res.num
                                    if (num > 0) {
                                        that.$Notice.error({
                                            title: '提醒',
                                            desc: `${that.formInline.teamName}已存在！`
                                        })
                                        return
                                    }
                                    var adddata = {
                                        organizationCode: that.$store.state.modals.login.organizationCode,
                                        teamName: that.formInline.teamName,
                                        projectCode: that.formInline.projectCode
                                    }
                                    
                                    that.$http({
                                            baseURL: that.baseURL,
                                            url: '/appTeam/add',
                                            data: adddata,
                                            method: 'post',
                                            headers: {
                                                'Content-Type': 'application/json',
                                                'Authorization': 'Bearer ' + token
                                            }
                                        })
                                        .then(function (data) {
                                            console.log('uploadTeamInfo = ', data)
                                            if (data.data.success)
                                                that.$Notice.success({
                                                    title: '提醒',
                                                    desc: '新增成功'
                                                })
                                            else
                                                that.$Notice.error({
                                                    title: '提醒',
                                                    desc: '新增失败'
                                                })
                                        }).catch(function (error) {
                                            console.log(error)
                                            that.$Notice.error({
                                                title: '提醒',
                                                desc: '远程服务器异常'
                                            })
                                        }).finally(() => {
                                            that.$Spin.hide()
                                            that.closeModal()
                                        })
                                })
                        } else {
                            var updatedata = {
                                id: that.$store.state.modals.teamModal.formInline.remoteid,
                                teamName: that.formInline.teamName
                            }
                            that.$http({
                                    baseURL: that.baseURL,
                                    url: '/appTeam/update',
                                    data: updatedata,
                                    method: 'post',
                                    headers: {
                                        'Content-Type': 'application/json',
                                        'Authorization': 'Bearer ' + token
                                    }
                                })
                                .then(function (data) {
                                    console.log('uploadTeamInfo = ', data)
                                    if (data.data.success)
                                        that.$Notice.success({
                                            title: '提醒',
                                            desc: '修改成功'
                                        })
                                    else
                                        that.$Notice.error({
                                            title: '提醒',
                                            desc: '修改失败'
                                        })
                                }).catch(function (error) {
                                    console.log(error)
                                    that.$Notice.error({
                                        title: '提醒',
                                        desc: '远程服务器异常'
                                    })
                                }).finally(() => {
                                    that.$Spin.hide()
                                    that.closeModal()
                                })

                        }
                    } else {
                        that.$Notice.error({
                            title: '提醒',
                            desc: '输入数据不符合要求'
                        })
                    }
                })
            },
            visibleChange(isVisible) {
                if (isVisible) {
                    this.handleReset('formInline')
                    if (null !== this.$store.state.modals.teamModal.formInline) {
                        if (this.$store.state.modals.teamModal.formInline.id > 0)
                            this.formInline = this.$store.state.modals.teamModal.formInline
                    }
                    this.formInline.projectCode = this.$store.state.modals.selectedProjectCode.code
                    this.projectName = this.$store.state.modals.selectedProjectCode.name
                    
                } else {
                    this.closeModal()
                }
            },
            closeModal() {
                this.formInline.id = 0
                this.handleReset('formInline')
                this.$store.dispatch('hideTeamModal')
                this.$emit('reloadPage')
            },
            handleReset(name) {
                this.formInline.id = 0
                this.$refs[name].resetFields()
            }
        }
    }
</script>