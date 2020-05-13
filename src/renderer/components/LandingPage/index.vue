<template>
  <div>
    <Row>
      <i-col span="24" style="margin:40px auto 0px auto;">
        <Table stripe highlight-row :columns="columns" :data="listdata" ref="selecteAll" height="600"
          no-data-text="当前账户下暂无项目信息">
          <template slot-scope="{ row, index }" slot="action">
            <Button @click="team(index)" shape="circle" type="info" size="small">
              班组列表
            </Button>
            <Button @click="worker(index)" shape="circle" type="primary" size="small">
              工人列表
            </Button>
            <Button @click="individual(index)" shape="circle" type="warning" size="small">
              管理人员列表
            </Button>
          </template>
          <template slot-scope="{ row }" slot="sname">
            <Tag type="dot" color="success" v-if="row.projectStatus <= 4">{{ row.projectStatusName }}</Tag>
            <Tag type="dot" color="error" v-else>{{ row.projectStatusName }}</Tag>
        </template>
        </Table>
      </i-col>
    </Row>
  </div>
</template>
<script>
  export default {
    name: 'project',
    computed: {
      baseURL: {
        get() {
          return this.$store.state.modals.settings.baseURL
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
        columns: [{
            type: 'index',
            width: 50,
            align: 'center'
          },
          {
            title: '项目名称',
            key: 'projectName',
            ellipsis: true
          },
          {
            title: '项目编号',
            width: 180,
            key: 'projectCode',
            ellipsis: true
          },
          {
            title: '项目区域',
            width: 200,
            key: 'area',
            ellipsis: true
          },
          {
            title: '项目状态',
            width: 120,
            slot: 'sname'
          },
          {
            title: '操作',
            slot: 'action',
            width: 340,
            align: 'center'
          }
        ],
        listdata: []
      }
    },
    mounted() {
      this.caretedTable()
    },
    beforeRouteEnter(to, from, next) {
      next(vm => {
        vm.getProjects()
      })
    },
    methods: {
      caretedTable() {
        this.$workersRepo.createTable().then((res) => {
          console.log('workersRepo.createTable:', res)
        }).catch((err) => {
          console.log(err)
        })
        this.$individualRepo.createTable().then((res) => {
          console.log('individualRepo.createTable:', res)
        }).catch((err) => {
          console.log(err)
        })
        this.$teamRepo.createTable().then((res) => {
          console.log('teamRepo.createTable:', res)
        }).catch((err) => {
          console.log(err)
        })
      },
      worker(index) {
        var data = this.listdata[index]
        this.$store.dispatch('setProjectCode', {
          code: data.projectCode,
          name: data.projectName
        })
        this.$store.dispatch('showWorkerFullModal')
        this.$router.push({
          name: 'worker'
        })
      },
      individual(index) {
        var data = this.listdata[index]
        this.$store.dispatch('setProjectCode', {
          code: data.projectCode,
          name: data.projectName
        })
        this.$store.dispatch('showIndividualFullModal')
        this.$router.push({
          name: 'individual'
        })
      },
      team(index) {
        var data = this.listdata[index]
        this.$store.dispatch('setProjectCode', {
          code: data.projectCode,
          name: data.projectName
        })
        this.$store.dispatch('showTeamFullModal')
        this.$router.push({
          name: 'team'
        })
      },
      //获取最新项目数据
      getProjects() {
        if (this.$store.state.modals.groupList.data.length > 0) {
          this.listdata = this.$store.state.modals.groupList.data
          return
        }
        var that = this
        var token = that.$store.state.modals.login.token

        that.$http({
          baseURL: that.baseURL,
          url: '/v116/appProject/v116GetProjectsByCompany',
          params: {
            organizationCode: that.$store.state.modals.login.organizationCode,
            isEnterprise: 0,
            account: that.$store.state.modals.login.loginname,
            pageNo: 1,
            pageSize: 10000
          },
          method: 'get',
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': 'Bearer ' + token
          }
        }).then((res) => {
          console.log('v116GetProjectsByCompany = ', res)
          if (res.data.code !== 200)
            return
          if (res.data.data.length === 0)
            return
          that.$store.dispatch('setGroupList', res.data.data)
          that.listdata = res.data.data
        }).catch((err) => {
          console.log(`getCount err : ${err}`)
        })
      }
    }
  }
</script>

<style>
  .btns {
    width: 50px;
  }
</style>