<template>
    <Modal id="worker-info-modal" v-model="isVisible" @on-visible-change="visibleChange" :mask-closable="false"
        :footer-hide="true" width="960" :closable="true" :styles="{top: '10px',marginBottom:'20px'}">
        <p slot="header">
            <span style="font-weight:bolder;font-size:14px;">工人信息{{opt}}</span>
        </p>
        <Form ref="formInline" :model="formInline" :rules="ruleInline" :label-width="90">
            <div class="container">
                <div>
                    <FormItem prop="cardnumber" label="身份证号">
                        <i-input type="text" v-model.trim="formInline.cardnumber" placeholder="读取身份证号"
                            style="width: 250px" :disabled="disabled">
                        </i-input>
                    </FormItem>
                </div>
                <div class="item-c">
                    <FormItem label="证件照" prop="photo">
                        <img :src="photosrc" width="100px">
                        <Input v-model="formInline.photo" style="display:none;" />
                    </FormItem>
                </div>
                <div class="item-b">
                    <FormItem label="现场照">
                        <video id="video1" autoplay></video>
                        <!--
                        <canvas id="overlay" width="120" height="150"></canvas>
                        -->
                        <canvas style="margin:0px" width="176" height="144" id="canvas1" />
                    </FormItem>
                </div>
                <div>
                    <FormItem prop="name" label="姓名">
                        <i-input type="text" v-model.trim="formInline.name" placeholder="读取姓名" style="width: 250px"
                            :disabled="disabled">
                        </i-input>
                    </FormItem>
                </div>
                <div>
                    <FormItem prop="haddress" label="地址">
                        <i-input type="text" v-model.trim="formInline.haddress" placeholder="读取地址" style="width: 250px"
                            :disabled="disabled">
                        </i-input>
                    </FormItem>
                </div>
                <div>
                    <FormItem prop="birthday" label="生日">
                        <i-input type="text" v-model.trim="formInline.birthday" placeholder="读取生日" style="width: 250px"
                            :disabled="disabled">
                        </i-input>
                    </FormItem>
                </div>
                <div>
                    <FormItem>
                        <Button shape="circle" icon="md-camera" @click="exportbase" size="small"
                            type="primary">单击拍照</Button>
                    </FormItem>
                </div>
                <div>
                    <FormItem prop="cbegin" label="开始日期">
                        <i-input type="text" v-model.trim="formInline.cbegin" placeholder="读取开始日期" style="width: 250px"
                            :disabled="disabled">
                        </i-input>
                    </FormItem>
                </div>
                <div>
                    <FormItem prop="cend" label="结束日期">
                        <i-input type="text" v-model.trim="formInline.cend" placeholder="读取结束日期" style="width: 250px"
                            :disabled="disabled">
                        </i-input>
                    </FormItem>
                </div>
                <div>
                    <FormItem prop="office" label="发证机关">
                        <i-input type="text" v-model.trim="formInline.office" placeholder="读取发证机关" style="width: 250px"
                            :disabled="disabled">
                        </i-input>
                    </FormItem>
                </div>
                <div>
                    <FormItem prop="nation" label="民族">
                        <i-input type="text" v-model.trim="formInline.nation" placeholder="读取民族" style="width: 250px"
                            :disabled="disabled">
                        </i-input>
                    </FormItem>
                </div>
                <div>
                    <FormItem label="所在项目">
                        <span>{{projectName}}</span>
                    </FormItem>
                </div>
                <div>
                    <FormItem prop="teamSysNo" label="所属班组">
                        <Select v-model="formInline.teamSysNo" style="width:250px" filterable label-in-value
                            @on-change="teamSysNoChange">
                            <Option v-for="item in grouplist" :value="item.value" :key="item.value">{{item.label}}
                            </Option>
                        </Select>
                    </FormItem>
                </div>
                <div>
                    <FormItem prop="workTypeCode" label="所属工种">
                        <Select v-model="formInline.workTypeCode" style="width:250px" filterable clearable
                            label-in-value @on-change="workTypeCodeChange">
                            <Option v-for="item in workertypeList" :value="item.num" :key="item.num">{{item.name}}
                            </Option>
                        </Select>
                    </FormItem>
                </div>
                <div>
                    <FormItem>
                        <Button icon="md-wifi" shape="circle" @click="pull" style="width:100px;"
                            type="warning">证件读取</Button>
                        <Button type="info" icon="md-checkmark" @click="handleSubmit('formInline')" shape="circle"
                            style="width:100px;margin-left:22px;">信息提交</Button>
                    </FormItem>
                </div>
            </div>
        </Form>
    </Modal>
</template>
<script>
    //import * as faceapi from 'face-api.js'
    /*"canvas": "^2.5.0", */
    // const {
    //     createCanvas,
    //     Canvas,
    //     Image,
    //     ImageData
    // } = require('canvas')

    // faceapi.env.monkeyPatch({
    //     Canvas: HTMLCanvasElement,
    //     Image: HTMLImageElement,
    //     ImageData: ImageData,
    //     Video: HTMLVideoElement,
    //     createCanvasElement: () => document.createElement('canvas'),
    //     createImageElement: () => document.createElement('img')
    // })
    // const fs = require('fs')
    // faceapi.env.setEnv(Object.assign(faceapi.env.createBrowserEnv(), faceapi.env.createFileSystem(fs)))

    var rloop

    export default {
        name: 'worker-info-modal',
        computed: {
            isVisible: {
                get() {
                    return this.$store.state.modals.machineModal.isVisible
                },
                set(value) {}
            },
            opt() {
                return this.$store.state.modals.machineModal.formInline === null ? '新增' : '修改'
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
                base64: '',
                grouplist: [],
                workertypeList: [],
                fprojectList: [],
                disabled: true,
                photosrc: 'resources/i.jpg',
                projectName: '',
                formInline: {
                    id: 0,
                    cardnumber: '',
                    name: '',
                    haddress: '',
                    birthday: '',
                    cend: '',
                    cbegin: '',
                    gender: '',
                    nation: '',
                    office: '',
                    photo: '',
                    projectCode: '',
                    teamSysNo: '',
                    teamName: '',
                    workTypeCode: '',
                    workTypeName: ''
                },
                ruleInline: {
                    cardnumber: [{
                        required: true,
                        message: '未读取身份证号'
                    }],
                    name: [{
                        required: true,
                        message: '未读取姓名'
                    }],
                    haddress: [{
                        required: true,
                        message: '未读取地址'
                    }],
                    birthday: [{
                        required: true,
                        message: '未读取出生日期'
                    }],
                    cbegin: [{
                        required: true,
                        message: '未读取开始日期'
                    }],
                    cend: [{
                        required: true,
                        message: '未读取结束日期'
                    }],
                    gender: [{
                        required: true,
                        message: '未读取性别'
                    }],
                    nation: [{
                        required: true,
                        message: '未读取民族'
                    }],
                    office: [{
                        required: true,
                        message: '未读取发证机关'
                    }],
                    teamSysNo: [{
                        required: true,
                        message: '请选择所属班组',
                        type: 'number'
                    }],
                    workTypeCode: [{
                        required: true,
                        message: '请选择所属工种',
                        type: 'number'
                    }],
                    livepic: [{
                        required: true,
                        message: '未读取结束日期'
                    }],
                }
            }
        },
        mounted() {
            var vm = this
            // faceapi.nets.tinyFaceDetector.loadFromDisk('./resources').then(() => {
            //     vm.videoReady()
            // })
            vm.videoReady()
        },
        methods: {
            videoReady() {
                var video = document.getElementById('video1');
                // video.addEventListener('loadeddata', (event) => {
                //     console.log('Yay! The readyState just increased to  ' +
                //         'HAVE_CURRENT_DATA or greater for the first time.');
                // });
                navigator.mediaDevices.getUserMedia({
                        video: {
                            width: {
                                exact: 176
                            },
                            height: {
                                exact: 144
                            }
                        }
                    }).then(stream => video.srcObject = stream)
                    .catch(err => console.log(err.name + ": " + err.message));

            },
            draw() {
                // const video = document.getElementById('video')
                // const con = document.getElementById("overlay")
                // var that = this
                // faceapi.detectAllFaces(video, new faceapi.TinyFaceDetectorOptions()).then((detectionsWithLandmarks) => {
                //     var detectionsWithLandmarksForSize = faceapi.resizeResults(
                //         detectionsWithLandmarks, {
                //             width: video.width,
                //             height: video.height
                //         })
                //     var ctx = con.getContext("2d")
                //     ctx.fillStyle = "white"
                //     ctx.clearRect(0, 0, video.width, video.height)

                //     faceapi.drawDetection(con, detectionsWithLandmarksForSize, {
                //         withScore: false,
                //         boxColor: '#2db7f5',
                //         lineWidth: 2
                //     })
                // }).catch((err) => {

                // })
                // rloop = setTimeout(() => that.draw())
            },
            resetCanvas() {
                var canvas = document.getElementById('canvas1')
                var context = canvas.getContext('2d')
                context.fillStyle = "#f8f8f9"
                context.fillRect(0, 0, 176, 144)
            },
            exportbase() {
                var video = document.getElementById('video1')
                var canvas = document.getElementById('canvas1')
                var context = canvas.getContext('2d')
                context.drawImage(video, 0, 0, 176, 144)
                this.base64 = canvas.toDataURL('image/jpeg', 1)

                var that = this
                that.$http({
                        baseURL: 'http://61.147.204.98:9500/',
                        url: 'faceDetect/',
                        data: {
                            image: that.base64.replace('data:image/jpeg;base64,', '')
                        },
                        method: 'post',
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    })
                    .then(function (data) {
                        if (data.data.code === 'success') {
                            if (data.data.faces <= 0) {
                                that.$Notice.info({
                                    title: '提醒',
                                    desc: "拍摄图片不规范，请重新拍摄！"
                                })
                                that.base64 = ''
                            }

                        }
                    }).catch((error) => {
                        console.log(error)
                    })
                    .finally(() => {

                    })

                that.$http({
                        baseURL: 'http://61.147.204.98:9500/',
                        url: 'faceContrast/',
                        data: {
                            image1: that.formInline.photo,
                            image2: that.base64.replace('data:image/jpeg;base64,', '')
                        },
                        method: 'post',
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    })
                    .then(function (data) {
                        if (data.data.code === 'success') {
                            that.$Notice.info({
                                title: '提醒',
                                desc: data.data.score > 0.7 ? '匹配' : '不是同一个人'
                            })
                        } else {
                            that.$Notice.error({
                                title: '提醒',
                                desc: '不是同一个人'
                            })
                        }
                    }).catch((error) => {

                    })
            },
            teamSysNoChange(sel) {
                if (typeof (sel) != 'undefined')
                    if (typeof (sel.label) != 'undefined')
                        this.formInline.teamName = sel.label
            },
            workTypeCodeChange(sel) {
                if (typeof (sel) != 'undefined')
                    if (typeof (sel.label) != 'undefined')
                        this.formInline.workTypeName = sel.label
            },
            //转换民族
            nationConvert(val) {
                var list = this.$store.state.modals.nationlist
                var ret = ''
                for (var i in list) {

                    if (list[i].name === val) {
                        ret = list[i].num
                        break
                    }
                }
                return ret
            },
            nationConvertToName(val) {
                var list = this.$store.state.modals.nationlist
                var ret = ''
                for (var i in list) {
                    if (list[i].num === val) {
                        ret = list[i].name
                        break
                    }
                }
                return ret
            },

            getGroupList(procode) {
                var that = this
                that.$http({
                        baseURL: that.baseURL,
                        url: '/appTeam/getTeamsByProjectCode',
                        params: {
                            organizationCode: that.$store.state.modals.login.organizationCode,
                            projectCode: procode
                        },
                        method: 'get',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded',
                            'Authorization': 'Bearer ' + that.$store.state.modals.login.token
                        }
                    })
                    .then(function (data) {
                        console.log('班组数据：',data)
                        var list = data.data.data
                        var tempdata = []
                        if (list.length > 0) {
                            list.forEach((v, i, a) => {
                                tempdata.push({
                                    label: v.teamName,
                                    value: v.teamSysNo
                                })
                            })
                        }
                        if (tempdata.length > 0) {
                            that.grouplist = tempdata

                        }
                    }).catch((err) => {
                        console.log(err)
                    }).finally(() => {

                    })
            },
            birthdayExecute(val) {
                var moment = require('moment')
                return moment(val).format('YYYY-MM-DD')
            },
            handleSubmit(name) {
                var that = this
                var token = that.$store.state.modals.login.token
                if (that.base64.replace('data:image/jpeg;base64,', '') === '') {
                    that.$Notice.error({
                        title: '提醒',
                        desc: '请录入现场照'
                    })
                    return
                }
                that.$refs[name].validate(valid => {
                    if (valid) {
                        var postdata = []
                        postdata.push({
                            LoginOrganizationCode: that.$store.state.modals.login.organizationCode,
                            idCardPhoto: that.formInline.photo,
                            ocvFace: that.base64.replace('data:image/jpeg;base64,', ''),
                            idCardType: 1,
                            idCardNumber: that.formInline.cardnumber,
                            workerName: that.formInline.name,
                            nation: that.nationConvert(that.formInline.nation),
                            address: that.formInline.haddress,
                            isSue: that.formInline.office,
                            startTime: that.formInline.cbegin,
                            endTime: that.formInline.cend === '长期' ? '99991231' : that.formInline.cend,
                            projectCode: that.formInline.projectCode,
                            teamSysNo: that.formInline.teamSysNo,
                            workTypeCode: that.formInline.workTypeCode,
                            gender: that.formInline.gender === '男' || that.formInline
                                .gender === 0 ? 0 : 1,
                            birthday: that.birthdayExecute(that.formInline.birthday)
                        })

                        that.$http({
                                baseURL: that.baseURL,
                                url: '/appPersonnel/addWorkers',
                                data: {
                                    workerListStr: JSON.stringify(postdata),
                                    type: 3
                                },
                                method: 'post',
                                headers: {
                                    'Content-Type': 'application/json',
                                    'Authorization': 'Bearer ' + token
                                }
                            })
                            .then(function (data) {
                                console.log(data);
                                if (data.data.success === true) {
                                    var localdata = {
                                        cardnumber: that.formInline.cardnumber,
                                        name: that.formInline.name,
                                        nation: that.nationConvert(that.formInline.nation),
                                        haddress: that.formInline.haddress,
                                        gender: that.formInline.gender === '男' || that.formInline
                                            .gender === 0 ? 0 : 1,
                                        teamSysNo: that.formInline.teamSysNo,
                                        teamName: that.formInline.teamName,
                                        workTypeCode: that.formInline.workTypeCode,
                                        workTypeName: that.formInline.workTypeName,
                                        projectCode: that.formInline.projectCode,
                                        photo: that.formInline.photo,
                                        office: that.formInline.office,
                                        livepic: that.base64.replace('data:image/jpeg;base64,', ''),
                                        cbegin: that.formInline.cbegin,
                                        cend: that.formInline.cend === '长期' ? '99991231' : that
                                            .formInline
                                            .cend,
                                        birthday: that.formInline.birthday
                                    }
                                    that.$workersRepo.getCount(localdata.cardnumber, localdata.projectCode)
                                        .then((
                                            res) => {
                                            if (res.num <= 0) {
                                                that.$workersRepo.create(localdata).then((ret) => {
                                                    that.$Notice.success({
                                                        title: '提醒',
                                                        desc: '新增操作成功'
                                                    })
                                                }).catch(
                                                    (error) => {
                                                        console.log(error)
                                                    })
                                            } else {
                                                that.$workersRepo.update(localdata).then((ret) => {
                                                    that.$Notice.success({
                                                        title: '提醒',
                                                        desc: '修改操作成功'
                                                    })
                                                }).catch(
                                                    (error) => {
                                                        console.log(error)
                                                    })
                                            }
                                        })
                                }
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
                    this.workertypeList = this.$store.state.modals.workerType.data
                    this.fprojectList = this.$store.state.modals.groupList.data
                    if (null !== this.$store.state.modals.machineModal.formInline) {
                        if (this.$store.state.modals.machineModal.formInline.id > 0) {
                            this.formInline = this.$store.state.modals.machineModal.formInline

                            this.formInline.nation = this.nationConvertToName(this.formInline.nation)
                            this.formInline.cend = this.formInline.cend === '99991231' ? '长期' : this.formInline.cend
                            this.photosrc = 'data:image/png;base64,' + this.formInline.photo

                            this.getGroupList(this.formInline.projectCode)

                            if (this.formInline.livepic !== '') {
                                this.base64 = 'data:image/jpeg;base64,' + this.formInline.livepic
                                var canvas = document.getElementById('canvas1')
                                var context = canvas.getContext('2d')
                                var e = document.createElement("img")
                                e.setAttribute("src", 'data:image/png;base64,' + this.formInline.livepic)
                                context.drawImage(e, 0, 0, 120, 150)
                            }
                        } else
                            this.handleReset('formInline')
                    } else {
                        this.handleReset('formInline')
                        this.formInline.projectCode = this.$store.state.modals.selectedProjectCode.code
                        this.projectName = this.$store.state.modals.selectedProjectCode.name
                        this.getGroupList(this.formInline.projectCode)
                    }
                    this.formInline.projectCode = this.$store.state.modals.selectedProjectCode.code
                    this.projectName = this.$store.state.modals.selectedProjectCode.name
                    this.draw()
                } else {
                    this.resetCanvas()
                    this.closeModal()
                }
            },
            closeModal() {
                this.photosrc = 'resources/i.jpg'
                this.formInline.id = 0
                this.handleReset('formInline')
                this.$store.dispatch('hideMachineModal')
                this.$emit('reloadPage')
                clearTimeout(rloop)
            },
            handleReset(name) {
                this.formInline.id = 0
                this.$refs[name].resetFields()
                this.base64 = ''
            },
            pull() {
                var edge = require('edge')
                var GetIDCardInfo = edge.func({
                    assemblyFile: "resources/IDCardLib.dll",
                    typeName: "IDCardClassLibrary.CardLib",
                    methodName: "GetIDCardInfo"
                })

                const Promise = require('bluebird')
                const promise = new Promise((resolve, reject) => {
                    GetIDCardInfo({
                        debug: 'no'
                    }, function (err, result) {
                        if (err) reject(`exception: ${err}`)
                        else
                        if (result !== '')
                            resolve(JSON.parse(result))
                        else
                            reject('err')
                    })

                })
                promise.then((res) => {

                    if (res.code == 0) {
                        if (this.formInline.cardnumber !== '' && this.formInline.id > 0) {
                            if (this.formInline.cardnumber !== res.des.cardnumber) {
                                this.$Notice.info({
                                    title: '提醒',
                                    desc: '人证不匹配，请检查后再修改！'
                                })
                                this.closeModal()
                            }
                        }
                        this.photosrc = 'data:image/png;base64,' + res.des.photo
                        this.formInline.name = res.des.name
                        this.formInline.photo = res.des.photo
                        this.formInline.cardnumber = res.des.cardnumber
                        this.formInline.haddress = res.des.address
                        this.formInline.cend = res.des.end
                        this.formInline.cbegin = res.des.begin
                        this.formInline.gender = res.des.gender
                        this.formInline.nation = res.des.nationality
                        this.formInline.office = res.des.office
                        this.formInline.birthday = res.des.birthday
                    } else {
                        this.$Notice.info({
                            title: '提醒',
                            desc: res.des
                        })
                    }
                }).catch(console.error)
            }
        }
    }
</script>
<style>
    #worker-info-modal .container {
        display: grid;
        grid-template-columns: 49% 49%;
        grid-auto-flow: row;
    }

    #worker-info-modal .item-b {
        grid-column: 2 / 3;
        grid-row: 1 / 4;
    }

    #worker-info-modal .item-c {
        grid-column: 2 / 3;
        grid-row: 5 / 8;
    }

    textarea.ivu-input {
        font-size: 12px !important;
    }

    #overlay,
    .overlay {
        position: absolute;
        top: 0;
        left: 0;
    }

    #video {
        z-index: 9999;
    }
</style>