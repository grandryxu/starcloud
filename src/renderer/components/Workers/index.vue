<template>
  <div>
    <Modal id="workerfull-modal" v-model="workerListVisible" @on-visible-change="visibleChange" :mask-closable="false"
      :footer-hide="true" :closable="true" fullscreen :transfer="false">
      <div slot="header">
        <Breadcrumb>
          <BreadcrumbItem to="/project"><Button type="primary" shape="circle" size="small" icon="md-home">项目列表</Button>
          </BreadcrumbItem>
          <BreadcrumbItem>{{projectName}}</BreadcrumbItem>
          <BreadcrumbItem>工人列表</BreadcrumbItem>
        </Breadcrumb>
      </div>
      <div class="table-spec">
        <Row>
          <i-col span="6">
            <Input search placeholder="输入姓名或身份证号查询" style="width:200px;" v-model="keyword" @on-search="search"
              clearable />
          </i-col>
          <i-col span="18">
            <Tooltip content="添加工人">
              <Button @click="addWorker" icon="md-add" shape="circle" type="success">
                添加
              </Button>
            </Tooltip>
            <Tooltip content="刷新列表">
              <Button @click="prolistChange" icon="md-refresh" shape="circle" class="btnspace">
                刷新
              </Button>
            </Tooltip>
          </i-col>
        </Row>
      </div>
      <Row>
        <i-col span="24">
          <Table stripe highlight-row :columns="columns" :data="listdata" ref="selecteAll"
            @on-selection-change="mlistChange" height="440" no-data-text="当前项目下暂无工人信息">
            <template slot-scope="{ row }" slot="live">
              <img :src="'data:image/png;base64,'+row.livepic" height="40" />
            </template>
            <template slot-scope="{ row }" slot="pic">
              <img :src="'data:image/png;base64,'+row.photo" height="40" />
            </template>
            <template slot-scope="{ row }" slot="sex">
              <span>{{row.gender===0?'男':'女'}}</span>
            </template>
            <template slot-scope="{ row, index }" slot="action">
              <Tooltip content="编辑">
                <Button @click="show(index)" shape="circle" size="small" type="info">
                  编辑
                </Button>
              </Tooltip>
              <Tooltip content="删除">
                <Button @click="drop(index)" shape="circle" size="small" type="error">
                  删除
                </Button>
              </Tooltip>
            </template>
          </Table>
          <div id="pager">
            <Page :total="total" show-total size="small" :page-size="pagesize" @on-change="listChange" />
          </div>
        </i-col>
      </Row>
    </Modal>
    <worker-info-modal @reloadPage="reload"></worker-info-modal>
  </div>
</template>

<script>
  import WorkerInfoModal from '../modals/worker/WorkerInfo.vue'

  export default {
    name: 'worker',
    components: {
      WorkerInfoModal
    },
    computed: {
      baseURL: {
        get() {
          return this.$store.state.modals.settings.baseURL
        },
        set() {}
      },
      workerListVisible: {
        get() {
          return this.$store.state.modals.workerfullModal.isVisible
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
        grouplist: [],
        projectcc: '',
        columns: [{
            type: 'index',
            width: 50,
            align: 'center'
          },
          {
            title: '身份证号',
            width: 200,
            key: 'cardnumber',
            ellipsis: true
          },
          {
            title: '姓名',
            width: 90,
            key: 'name'
          },
          {
            title: '性别',
            slot: 'sex'
          },
          {
            title: '班组',
            key: 'teamName',
            ellipsis: true
          },
          {
            title: '工种',
            key: 'workTypeName',
            ellipsis: true
          },
          {
            title: '证件照',
            slot: 'pic',
            width: 80
          },
          {
            title: '现场照',
            slot: 'live',
            width: 80
          },
          {
            title: '操作',
            slot: 'action',
            width: 140,
            align: 'center'
          }
        ],
        listdata: [],
        exportdata: []
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
        this.$store.dispatch('hideWorkerFullModal')
        this.$router.replace({
          name: 'project'
        })
      },
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
      listChange(number) {
        var that = this
        that.$Spin.show()
        that.$workersRepo.getWorkersCount(that.keyword, that.projectcc).then((res) => {
          that.total = res.num
          that.$workersRepo.getWorkers(that.keyword, number, that.pagesize, that.projectcc).then((ret) => {
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
      addWorker() {
        if (typeof this.$store.state.modals.selectedProjectCode.code === 'undefined') {
          this.$Notice.info({
            title: '提醒',
            desc: '请先选择项目再添加人员！'
          })
          return
        }
        this.$store.dispatch('showMachineModal', {})
      },
      show(index) {
        this.$store.dispatch('showMachineModal', this.listdata[index])
      },
      prolistChange() {
        if (typeof this.$store.state.modals.selectedProjectCode.code === 'undefined')
          return
        this.projectcc = this.$store.state.modals.selectedProjectCode.code
        var token = this.$store.state.modals.login.token
        var that = this
        that.$Spin.show({
          render: (h) => {
            return h('div', [
              h('div', {
                'class': 'alert-text'
              }, '数据更新中，请稍后...')
            ])
          }
        })

        that.$http({
            baseURL: that.baseURL,
            url: '/v116/appPersonnel/v116GetAppWorkerMasterByProjectCode',
            params: {
              organizationCode: that.$store.state.modals.login.organizationCode,
              projectCode: that.projectcc,
              pageNo: 1,
              pageSize: 60000
            },
            method: 'get',
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
              'Authorization': 'Bearer ' + token
            }
          })
          .then(function (data) {
            console.log('拉取工人：', data)
            if (data.data.code !== 200)
              return
            if (!Array.isArray(data.data.data.workerList))
              return
            var insert = []
            var moment = require('moment')
            data.data.data.workerList.forEach((v, i, a) => {
              if (v.idCardType === '1') {
                
                insert.push({
                  cardnumber: v.idCardNumber,
                  name: v.workerName,
                  nation: v.nation,
                  haddress: v.address,
                  gender: that.genderExecute(v.idCardNumber),
                  teamSysNo: v.teamSysNo,
                  teamName: v.teamName,
                  workTypeCode: v.workTypeCode,
                  workTypeName: v.workTypeName,
                  projectCode: v.projectCode,
                  photo: v.idCardPhoto,
                  office: v.is_sue,
                  livepic: v.ocvFace,
                  cbegin: moment(v.start_time).format('YYYYMMDD'),
                  cend: moment(v.end_time).format('YYYYMMDD'),
                  birthday: v.idCardNumber.substring(6, 14),
                  pwId: v.pwId
                })
              }
            })
            if (insert.length > 0) {
              insert.forEach((value, index, array) => {
                that.$workersRepo.getCount(value.cardnumber, value.projectCode).then((res) => {

                  if (res.num === 0) {
                    that.$workersRepo.create(value).then((ret) => {}).catch((error) => {
                      console.log(error)
                    })
                  } else {
                    that.$workersRepo.update(value).then((ret) => {}).catch((error) => {
                      console.log(error)
                    })
                  }
                })
              })
            }
          }).catch(function (error) {
            console.log(error)
          }).finally(() => {
            that.reload()
          })
      },
      genderExecute(cardnumber) {
        var v = cardnumber.slice(-2, -1)
        if (v % 2 === 0)
          return 1
        else
          return 0
      },
      reload() {
        var that = this
        that.$workersRepo.getWorkersCount(that.keyword, that.projectcc).then((res) => {
          that.total = res.num
          that.$workersRepo.getWorkers(that.keyword, 1, that.pagesize, that.projectcc).then((ret) => {
            that.listdata = ret
          })
        }).catch((err) => {
          console.log(err)
        }).finally(() => {
          that.$Spin.hide()
        })
      },
      drop(index) {
        this.$Modal.confirm({
          title: '确认',
          content: `<p>确认删除工人 ${this.listdata[index].name}？</p>`,
          onOk: () => {
            var that = this
            var token = that.$store.state.modals.login.token
            that.$http({
                baseURL: that.baseURL,
                url: '/appPersonnel/deleteSingle',
                data: {
                  projectCode: that.listdata[index].projectCode,
                  teamSysNo: that.listdata[index].teamSysNo,
                  idCardType: 1,
                  idCardNumber: that.listdata[index].cardnumber
                },
                method: 'post',
                headers: {
                  'Content-Type': 'application/json',
                  'Authorization': 'Bearer ' + token
                }
              })
              .then(function (data) {
                if (data.data.success) {
                  that.$workersRepo.delById(that.listdata[index].id).then((res) => {
                    if (res.affected === 1) {
                      that.$Notice.success({
                        title: '提醒',
                        desc: `${that.listdata[index].name} 删除成功！`
                      })
                    } else {
                      that.$Notice.error({
                        title: '提醒',
                        desc: `${that.listdata[index].name} 删除失败！`
                      })
                    }
                  }).catch((err) => {
                    that.$Notice.error({
                      title: '提醒',
                      desc: `${that.listdata[index].name} 删除异常！`
                    })
                  })
                } else {
                  that.$Notice.error({
                    title: '提醒',
                    desc: `${that.listdata[index].name} 删除失败！`
                  })
                }
              }).catch(function (error) {
                that.$Notice.error({
                  title: '提醒',
                  desc: `${that.listdata[index].name} 删除异常！`
                })
              }).finally(() => {
                that.reload()
              })
          }
        })
      }
    }
  }
</script>