<template>
  <Modal id="individualfull-modal" v-model="individualListVisible" @on-visible-change="visibleChange"
    :mask-closable="false" :footer-hide="true" :closable="true" fullscreen :transfer="false">
    <div slot="header">
      <Breadcrumb>
        <BreadcrumbItem to="/project"><Button type="primary" shape="circle" size="small" icon="md-home">项目列表</Button>
        </BreadcrumbItem>
        <BreadcrumbItem>{{projectName}}</BreadcrumbItem>
        <BreadcrumbItem>管理人员列表</BreadcrumbItem>
      </Breadcrumb>
    </div>
    <div class="table-spec">
      <Row>
        <i-col span="6">
          <Input search placeholder="输入姓名或身份证号查询" style="width:200px;" v-model="keyword" @on-search="search"
            clearable />
        </i-col>
        <i-col span="18">
          <Tooltip content="添加管理人员">
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
          @on-selection-change="mlistChange" height="440" no-data-text="当前项目下暂无管理人员信息">
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
              <Button @click="show(index)" type="info" shape="circle" size="small">
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
    <individual-modal @reloadPage="reload"></individual-modal>
  </Modal>
</template>

<script>
  import IndividualModal from '../modals/individual/index.vue'

  export default {
    name: 'individual',
    components: {
      IndividualModal
    },
    computed: {
      baseURL: {
        get() {
          return this.$store.state.modals.settings.baseURL
        },
        set() {

        }
      },
      individualListVisible: {
        get() {
          return this.$store.state.modals.individualfullModal.isVisible
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
            title: '证件号',
            key: 'cardnumber',
            width: 200,
            ellipsis: true
          },
          {
            title: '姓名',
            key: 'name'
          },
          {
            title: '性别',
            slot: 'sex',
            width: 70,
          },
          {
            title: '地址',
            width: 220,
            key: 'haddress',
            ellipsis: true
          },
          {
            title: '证件照',
            slot: 'pic',
            width: 80,
          },
          {
            title: '现场照',
            slot: 'live',
            width: 80,
          },
          {
            title: '操作',
            slot: 'action',
            width: 120,
            align: 'center'
          }
        ],
        listdata: [],
        exportdata: [],
        prolist: [{
          title: '项目列表',
          expand: true,
          children: []
        }]
      }
    },
    beforeRouteEnter(to, from, next) {
      console.log('进入管理人员列表路由钩子')
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
        this.$store.dispatch('hideIndividualFullModal')
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
        that.$individualRepo.getCount(that.keyword, that.projectcc).then((res) => {
          that.total = res.num
          that.$individualRepo.getList(that.keyword, number, that.pagesize, that.projectcc).then((ret) => {
            //console.log('人员数据：', ret)
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
        this.$store.dispatch('showIndividualModal', {})
      },
      show(index) {
        this.$store.dispatch('showIndividualModal', this.listdata[index])
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
            url: '/appEmployee/selectEmployeesByProjectCode',
            params: {
              organizationCode: that.$store.state.modals.login.organizationCode,
              projectCode: that.projectcc,
              pageNumber: 1,
              pageSize: 60000
            },
            method: 'get',
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded',
              'Authorization': 'Bearer ' + token
            }
          })
          .then(function (data) {
            console.log('管理人员数据List：', data)
            if (data.data.code !== 200)
              return
            if (!Array.isArray(data.data.data))
              return
            var list = data.data.data
            var insert = []
            var moment = require('moment')
            
            list.forEach((v, i, a) => {
              if (v.idCardType === 1){
                insert.push({
                  cardtype: v.idCardType,
                  cardnumber: v.idCardNumber,
                  name: v.employeeName,
                  nation: v.nation,
                  haddress: v.address,
                  gender: that.genderExecute(v.idCardNumber),
                  projectCode: that.projectcc,
                  photo: v.idCardPhoto,
                  office: v.isSue,
                  livepic: v.ocvFace,
                  cbegin: null !== v.startTime ? moment(v.startTime).format('YYYYMMDD') : null,
                  cend: null !== v.endTime ? moment(v.endTime).format('YYYYMMDD') : null,
                  birthday: v.idCardNumber.substring(6, 14),
                  remoteId: v.id
                })
              }
            })
            console.log('insert = ', insert)
            if (insert.length > 0) {
              insert.forEach((value, index, array) => {
                that.$individualRepo.getLimitCount(value.cardnumber, value.projectCode).then((
                  res) => {
                  if (res.num === 0) {
                    console.log('插入当前人员信息：', value)
                    that.$individualRepo.create(value).then((ret) => {}).catch((error) => {
                      console.log(error)
                    })
                  } else {
                    console.log('更新当前人员信息：', value)
                    that.$individualRepo.update(value).then((ret) => {}).catch((error) => {
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
        that.$individualRepo.getCount(that.keyword, that.projectcc).then((res) => {
          that.total = res.num
          that.$individualRepo.getList(that.keyword, 1, that.pagesize, that.projectcc).then((ret) => {
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
          content: `<p>确认删除${this.listdata[index].name}吗？</p>`,
          onOk: () => {
            var that = this
            if(that.listdata[index].remoteId === ''){
              that.$Notice.error({
                title: '提醒',
                desc: `参数异常，请刷新后重试！`
              })
              return
            }
            var token = that.$store.state.modals.login.token
            that.$http({
                baseURL: that.baseURL,
                url: '/appEmployee/delete',
                data: {
                  updateUser: that.$store.state.modals.login.username,
                  employeeMasterIds: that.listdata[index].remoteId,
                  organizationCode: that.$store.state.modals.login.organizationCode
                },
                method: 'post',
                headers: {
                  'Content-Type': 'application/json',
                  'Authorization': 'Bearer ' + token
                }
              })
              .then(function (data) {
                if (data.data.success) {
                  that.$individualRepo.delById(that.listdata[index].id).then((res) => {
                    if (res.affected === 1) {
                      that.$Notice.success({
                        title: '提醒',
                        desc: `${that.listdata[index].name}删除成功！`
                      })
                    } else {
                      that.$Notice.error({
                        title: '提醒',
                        desc: `${that.listdata[index].name}删除失败！`
                      })
                    }
                  }).catch((err) => {
                    that.$Notice.error({
                      title: '提醒',
                      desc: `${that.listdata[index].name}本地删除异常！`
                    })
                  })
                } else {
                  that.$Notice.error({
                    title: '提醒',
                    desc: `${that.listdata[index].name}删除失败！`
                  })
                }
              }).catch(function (error) {
                that.$Notice.error({
                  title: '提醒',
                  desc: `${that.listdata[index].name}远程删除异常！`
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