<template>
  <div class="iot-list" ref="page">
    <div class="iot-form">
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <el-form-item label="入库信息">
            <el-input class="iot-w200" placeholder="请输入查询内容" maxlength="50" v-model="searchForm.import_info" clearable></el-input>
          </el-form-item>
          <el-form-item label="入库结果">
            <el-input class="iot-w200" placeholder="请输入查询内容" maxlength="50" v-model="searchForm.import_result" clearable></el-input>
          </el-form-item>
          <el-form-item label="入库时间">
            <el-date-picker v-model="searchForm.CreationTime" type="daterange" range-separator="-" start-placeholder="起始日期" end-placeholder="结束日期" format="yyyy-MM-dd" value-format="yyyy-MM-dd"></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="layout__btns">
        <div ref="permissions">
          <el-button type="primary" plain icon="el-icon-s-flag" @click="handleView">查阅</el-button>
          <el-button type="primary" plain icon="el-icon-document" @click="exportExcel">导出</el-button>
          <el-button type="primary" plain icon="el-icon-printer" @click="printTable">打印</el-button>
        </div>
      </div>
    </div>
    <div class="iot-table">
      <el-table id="out-table" ref="print" :data="tableData" stripe style="width: 100%" border @selection-change="handleSelectionChange" @row-click="clickRow">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
        <el-table-column align="center" prop="import_info" label="入库信息" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="import_result" label="入库结果" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="import_creat_datetime" label="入库时间" min-width="100" show-overflow-tooltip :formatter="dateFormat"></el-table-column>
      </el-table>
    </div>
    <div class="iot-pagination">
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
    </div>
  </div>
</template>

<script>
import textConfig from '@/mixins/textConfig.js'
import {
  getListApi,
  getOneApi
} from "./api";
export default {
  //全局混入提示文字
  mixins: [textConfig],
  components: {
  },
  props: ["lang"],

  watch: {
    lang: function (lang) {
      this.$utils.traversePageDom.call(this.$utils, this.$store.state.currentLang, lang, this.$refs.page);
      this.$utils.traverseFormValidator(this.rules, this.lang)
    }
  },
  data() {
    return {
      modelList: [],
      multipleSelection: [],
      radio1: "",
      searchForm: {
      },
      dialogTitle: "查阅",
      dialogVisible: false,
      tableData: [],
      isEnableList: [{ id: 0, enableName: "正常" }, { id: 1, enableName: "停用" }],
      currentPage: 1,
      pageSize: 10,
      total: 0
    };
  },
  mounted() {
    this.GetAll();
    this.btnInit();
  },
  methods: {
    //根据当前用户权限标识初始化按钮状态
    btnInit() {
      this.$Common.DistributePermission.call(this)
    },
    clickRow(row) {
      this.$refs.print.toggleRowSelection(row);
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1;
      this.GetAll();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.GetAll();
    },
    handleView() {
      if (this.multipleSelection.length == 1) {
        this.click_edit(this.multipleSelection[0]);
      } else {
        //this.text_selectOne  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_selectOne);
      }
    },
    click_edit(row) {
      //this.dialogTitle = "编辑";
      this.resetForm();
      this.Get(row);
    },
    //表单重置
    async resetForm() {
      //当表单信息量比较大时需要添加宿主任务以强制loading提前加载
      setTimeout(async () => {
        this.dialogVisible = true;
        await this.$nextTick(() => {
          if (typeof myLodading !== 'undefined')
            myLodading.close()
        });
        this.$refs["dialogForm"].resetFields();
      }, 100);
    },
    //列表
    async GetAll() {
      if ( typeof this.searchForm.CreationTime != "undefined") {
        this.searchForm.DateRange = this.searchForm.CreationTime[0] + '/' + this.searchForm.CreationTime[1];
      } else {
        this.searchForm.DateRange = '';
      }
      let params = {
        MaxResultCount: this.pageSize,
        SkipCount: (this.currentPage - 1) * this.pageSize,
        ...this.searchForm
      };
      let data = await getListApi(params);
      console.log('data = ', data);
      if (data) {
        this.tableData = data.items || [];

        this.total = data.totalCount || 0;
      }
      this.$utils.traversePageDom.call(this.$utils, this.$store.state.currentLang, this.lang, this.$refs.page);
      this.$utils.traverseFormValidator(this.rules, this.lang)
    },
    //当checkbox发生变化时
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    click_search() {
      this.currentPage = 1;
      this.GetAll();
    },
    //时间格式化
    dateFormat: function (row, column) {
      return this.dateFormatter(row.creationTime);
    },
    dateFormatter(val) {
      return this.$moment(val).format('YYYY-MM-DD HH:mm')
    },
    //导出表格
    exportExcel() {
      this.$Common.ExportExcel("#out-table")
    },
    //打印表格
    printTable() {
      this.$Common.PrintTable.call(this, 'print')
    }
  }
};
</script>