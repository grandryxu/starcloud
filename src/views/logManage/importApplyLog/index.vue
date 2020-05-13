<template>
  <div class="iot-list" ref="page">
    <div class="iot-form">
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <el-form-item label="模块">
            <!-- <el-select class="iot-w200" v-model="searchForm.OptModule" placeholder="请选择" clearable>
							<el-option v-for="item in modelList" :key="item.id" :label="item.customtype_name" :value="item.id"></el-option>
						</el-select> -->
            <el-input class="iot-w200" placeholder="请输入查询内容" maxlength="50" v-model="searchForm.OptModule" clearable></el-input>
          </el-form-item>
          <el-form-item label="操作类型">
            <el-select class="iot-w200" v-model="searchForm.OptAction" placeholder="请选择" clearable>
              <el-option v-for="item in optTypeList" :key="item.id" :label="item.optAction" :value="item.optAction"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="查询内容">
            <el-input class="iot-w200" placeholder="请输入查询内容" maxlength="50" v-model="searchForm.Content" clearable></el-input>
          </el-form-item>
          <el-form-item label="操作时间">
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
        <el-table-column align="center" prop="optModule" label="模块" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="optAction" label="操作类型" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="oldVal" label="修改前数据" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="newVal" label="修改后数据" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="optResult" label="操作结果" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="creationTime" label="操作时间" min-width="100" show-overflow-tooltip :formatter="dateFormat"></el-table-column>
        <el-table-column align="center" prop="optPath" label="接口名称" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="creatorUserId" label="操作用户Id" min-width="100" show-overflow-tooltip></el-table-column>
      </el-table>
    </div>
    <div class="iot-pagination">
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
    </div>
    <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
      <el-form ref="dialogForm" :model="ruleForm" :inline="true">
        <div class="iot-form-row">
          <el-form-item label="模块" label-width="100px" prop="operation_module_name">
            <el-input class="iot-w240" v-model="ruleForm.operation_module_name" placeholder="请输入客户编号" maxlength="50" disabled></el-input>
          </el-form-item>
          <el-form-item label="操作类型" label-width="100px" prop="operation_type_name">
            <el-input class="iot-w240" v-model="ruleForm.operation_type_name" placeholder="请输入客户名称" maxlength="50" disabled></el-input>
          </el-form-item>
          <el-form-item label="修改前数据" label-width="100px" prop="operation_modify_pre_data">
            <el-input class="iot-w240" v-model="ruleForm.operation_modify_pre_data" placeholder="请输入联系人" maxlength="50" disabled></el-input>
          </el-form-item>
          <el-form-item label="修改后数据" label-width="100px" prop="operation_modify_final_data">
            <el-input class="iot-w240" v-model="ruleForm.operation_modify_final_data" placeholder="请输入联系方式" maxlength="50" disabled></el-input>
          </el-form-item>
          <el-form-item label="操作时间" label-width="100px" prop="CreationTime">
            <el-date-picker class="iot-w240" v-model="ruleForm.CreationTime" type="date" placeholder="选择日期" disabled></el-date-picker>
          </el-form-item>
          <el-form-item label="备注" label-width="100px" prop="operation_remark">
            <el-input class="iot-w240" type="textarea" v-model="ruleForm.operation_remark" placeholder="请输入备注" disabled></el-input>
          </el-form-item>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
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
      optTypeList: [{
        id: 1,
        optAction: "新增"
      }, {
        id: 2,
        optAction: "更新"
      }, {
        id: 3,
        optAction: "删除"
      }],
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
      total: 0,
      //表单数据
      ruleForm: {
      }
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
    //根据主键获取信息
    async Get(row) {
      let params = {
        id: row.id
      };
      let res = await getOneApi(params);
      console.log('row = ', row);
      let obj = {
        id: row.id,
        operation_module_name: row.operation_module_name,
        operation_type_name: row.operation_type_name,
        operation_modify_pre_data: row.operation_modify_pre_data,
        operation_modify_final_data: row.operation_modify_final_data,
        operation_search_content: row.operation_search_content,
        operation_remark: row.operation_remark,
        CreationTime: this.dateFormatter(row.CreationTime)
      };
      this.ruleForm = obj;
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