<template>
  <div class="iot-list" ref="page">
    <div class="iot-form">
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <el-form-item label="物资名称">
            <el-input class="iot-w200" placeholder v-model="searchForm.goods_name" clearable @change="click_search"></el-input>
          </el-form-item>
          <el-form-item label="批次">
            <el-input class="iot-w200" placeholder v-model="searchForm.inventory_batch_no" clearable @change="click_search"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="layout__btns">
        <div ref="permissions">
          <el-button type="primary" plain icon="el-icon-document" @click="exportExcel">导出</el-button>
          <el-button type="primary" plain icon="el-icon-printer" @click="printTable">打印</el-button>
        </div>
      </div>
    </div>
    <div class="iot-table">
      <el-table id="out-table" ref="print" :data="tableData" stripe style="width: 100%" border @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
        <el-table-column align="center" prop="alarm_name" label="报警名称" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="goods_name" label="物资名称" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="goods_code" label="物资编码" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="stock_code" label="托盘" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="slot_code" label="库位码" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="impbody_bill_bar" label="入库单号" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="inventory_batch_no" label="批次" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="thresholdz_value" label="阈值" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="alarm_value" label="报警值" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="creationTime" label="报警开始时间" min-width="100" show-overflow-tooltip :formatter="dateTransform"></el-table-column>
      </el-table>
    </div>
    <div class="iot-pagination">
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
    </div>
    <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
      <el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
        <div class="iot-form-row">
          <el-form-item label="物资名称" label-width="100px" prop="imphead_code">
            <el-input class="iot-w240" v-model="ruleForm.imphead_code" placeholder="请输入物资名称" clearable></el-input>
          </el-form-item>
          <el-form-item label="物资编码" label-width="100px" prop="imphead_external_code">
            <el-input class="iot-w240" v-model="ruleForm.imphead_external_code" placeholder="请输入物资编码" clearable></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="批次" label-width="100px" prop="imphead_external_code">
            <el-input class="iot-w240" v-model="ruleForm.imphead_external_code" placeholder="请输入批次" clearable></el-input>
          </el-form-item>
          <el-form-item label="数量" label-width="100px" prop="imphead_external_code">
            <el-input class="iot-w240" v-model="ruleForm.imphead_external_code" placeholder="请输入数量" clearable></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="托盘条码" label-width="100px" prop="imphead_date">
            <el-input class="iot-w240" v-model="ruleForm.imphead_external_code" placeholder="请输入托盘条码" clearable></el-input>
          </el-form-item>
          <el-form-item label="有效期" label-width="100px" prop="imphead_date">
            <el-date-picker class="iot-w240" clearable v-model="ruleForm.imphead_date" type="date" placeholder="请选择"></el-date-picker>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="质量状态" label-width="100px" prop="imphead_is_enable">
            <el-select class="iot-w240" v-model="ruleForm.imphead_is_enable" placeholder="请选择">
              <el-option v-for="item in isEnableList" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div v-if="dialogTitle !== '模拟扫描'">
           <h1 style="text-align:center;font-size:16px;font-weight:bolder;">物资包装明细</h1>
           <div class="iot-table">
            <el-table :data="tableData" stripe style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange">
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
              <el-table-column align="center" prop="imphead_code" label="包装码" min-width="100" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" prop="imphead_code" label="数量" min-width="100" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-input class="iot-w120" v-model="scope.row.imphead_code" clearable></el-input>
                </template>
              </el-table-column>
              <el-table-column align="center" prop="imphead_code" label="质量状态" min-width="100" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" prop="imphead_code" label="备注" min-width="100" show-overflow-tooltip></el-table-column>
            </el-table>
          </div>
        </div>
        <div v-else>
           <div class="iot-table">
            <el-table :data="tableData" stripe style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange">
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
              <el-table-column align="center" prop="imphead_code" label="箱码" min-width="100" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-input class="iot-w240" v-model="scope.row.imphead_code" clearable></el-input>
                </template>
              </el-table-column>
              <el-table-column align="center" prop="imphead_code" label="数量" min-width="100" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-input class="iot-w240" v-model="scope.row.imphead_code" clearable></el-input>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="click_submit">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import textConfig from "@/mixins/textConfig.js";
import singlePictureUpload from "_c/singlePictureUpload";
import { getListApi, addApi, getOneApi, editApi, deleteApi } from "./api";
export default {
  //全局混入提示文字
  mixins: [textConfig],
  components: {
    singlePictureUpload
  },
  props: ["lang"],

        watch: {
            lang: function(lang) {
            this.$utils.traversePageDom.call(this.$utils,this.$store.state.currentLang,lang,this.$refs.page);
            this.$utils.traverseFormValidator(this.rules,this.lang)
            }
        },
  data() {
    return {
      multipleSelection: [],
      radio1: "",
      searchForm: {},
      dialogTitle: "模拟扫描",
      tableData: [],
      customList:[],
      typeList: this.$DropBox.getAllWarehouseType(),
      impheadTypeList:[
        { value: 1, label: "一般入库" }
      ],
      isEnableList: [
        { value: 1, label: "启用" },
        { value: 2, label: "禁用" }
      ],
      slotList: [
        { id: 1, slotName: "层列排" },
        { id: 2, slotName: "排列层" }
      ],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      //表单数据
      ruleForm: {
        imphead_code: "",
        imphead_external_code: "",
        imphead_date:"",
        imphead_type:"",
        warehouse_type:"",
        imphead_custom_name:"",
        remark:"",
        imphead_is_enable:""
      },
      //表单验证规则
      rules: {
        imphead_code: [
          {
            required: true,
            message: "请输入入库单号",
            trigger: "blur"
          }
        ],
        imphead_external_code: [
          {
            required: true,
            message: "请输入合同号",
            trigger: "blur"
          }
        ],
        imphead_date: [
          {
            required: true,
            message: "请选择入库日期",
            trigger: "blur"
          }
        ],
        imphead_type: [
          {
            required: true,
            message: "请选择单据类型",
            trigger: "blur"
          }
        ],
        warehouse_type:[
          {
            required: true,
            message: "请选择仓库类型",
            trigger: "blur"
          }
        ],
        imphead_custom_name:[
          {
            required: true,
            message: "请选择供应商",
            trigger: "blur"
          }
        ],
        imphead_is_enable:[
          {
            required: true,
            message: "请选择是否启用",
            trigger: "blur"
          }
        ]
      }
    };
  },
  mounted() {
    this.GetAll();
    this.getCustomList();
    this.btnInit();
  },
  methods: {
    //根据当前用户权限标识初始化按钮状态
    btnInit() {
      this.$Common.DistributePermission.call(this)
    },
    async getCustomList(){
				let res =await this.$DropBox.getAllCustoms();
				if(res.items){
					this.customList = res.items;
				}
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
    //表单重置
    async resetForm() {
      this.dialogVisible = true;
      await this.$nextTick();
      this.$refs["dialogForm"].resetFields();
    },

    //列表
    async GetAll() {
      let params = {
        MaxResultCount: this.pageSize,
        SkipCount: (this.currentPage - 1) * this.pageSize,
        type:3,
        ...this.searchForm
      };
      let data = await getListApi(params);
      if (data) {
        this.tableData = data.items || [];
        this.total = data.totalCount || 0;
      }
      this.$utils.traversePageDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.page);
      this.$utils.traverseFormValidator(this.rules,this.lang)
    },
    //新建
    Create() {
      if (!Object.keys(this.ruleForm).length) {
        this.dialogVisible = false;
        return;
      }
      const h = this.$createElement;
      this.$msgbox({
        title: "提示",
        message: h("p", null, [
          h("span", null, "确定【"),
          h(
            "span",
            {
              style: this.c_primary
            },
            "模拟扫描"
          ),
          h("span", null, "】吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        let params = {
          ...this.ruleForm
        };
        let res = await addApi(params);
        if (res) {
          this.$message({
            type: "success",
            message: "模拟扫描成功"
          });
          this.GetAll();
          this.dialogVisible = false;
        }
      });
    },
    //当checkbox发生变化时
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //点击编辑按钮
    handleEdit() {
      if (this.multipleSelection.length == 1) {
        this.click_edit(this.multipleSelection[0]);
      } else {
        //this.text_selectOne  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_selectOne);
      }
    },
    //点击删除按钮
    handleDelete() {
      if (this.multipleSelection.length == 0) {
        //this.text_deleteRow  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_deleteRow);
      } else {
        let row = this.multipleSelection;
        this.Delete(row);
      }
    },
    click_edit(row) {
      this.dialogTitle = "编辑";
      this.resetForm();
      this.Get(row);
    },
    click_search() {
      this.currentPage = 1;
      this.GetAll();
    },
    click_add() {
      this.dialogTitle = "模拟扫描";
      this.resetForm();
    },
    //点击保存
    click_submit() {
      if (this.dialogTitle == "查看") {
        this.dialogVisible = false;
        return;
      }
      this.$refs["dialogForm"].validate(valid => {
        if (!valid) {
          return;
        }
        if (this.dialogTitle == "模拟扫描") {
          this.Create();
        } else {
          this.Update();
        }
      });
    },
    //根据主键删除
    Delete(rows) {
      const h = this.$createElement;
      this.$msgbox({
        title: "提示",
        message: h("p", null, [
          h("span", null, "确定【"),
          h("span", { style: this.c_danger }, "删除"),
          h("span", null, "】吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        let deleteCount = 0;
        for (let i = 0; i < rows.length; i++) {
          let res = await deleteApi({ id: rows[i].id });
          if (res) {
            deleteCount++;
          }
        }
        if (deleteCount) {
          this.$message({
            type: "success",
            message: `成功删除${deleteCount}条,删除失败${rows.length -
              deleteCount}条`
          });
          this.GetAll();
        }
      });
    },
    //编辑
    Update() {
      const h = this.$createElement;
      this.$msgbox({
        title: "提示",
        message: h("p", null, [
          h("span", null, "确定【"),
          h(
            "span",
            {
              style: this.c_primary
            },
            "编辑"
          ),
          h("span", null, "】吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        let params = {
          ...this.ruleForm
        };
        let res = await editApi(params);
        if (res) {
          this.$message({
            type: "success",
            message: "修改成功"
          });
          this.GetAll();
          this.dialogVisible = false;
        }
      });
    },
    //根据主键获取信息
    async Get(row) {
      let params = {
        id: row.id
      };
      let res =await getOneApi(params);
      let obj = {
        id: row.id,
        imphead_code: res.imphead_code,
        imphead_external_code: res.imphead_external_code,
        imphead_date:res.imphead_date,
        imphead_type:res.imphead_type,
        warehouse_type:res.warehouse_type,
        imphead_custom_name:res.imphead_custom_name,
        remark:res.remark,
        imphead_is_enable:res.imphead_is_enable
      };
      this.ruleForm = obj;
    },
    //日期时间转换
    dateTransform(row){
      let date = row.creationTime;
      return this.$moment(date).format('YYYY-MM-DD HH:MM:SS')
    }, 
    //导出表格
    exportExcel() {
      this.$Common.ExportExcel("#out-table")
    },
    //打印表格
    printTable(){
      this.$Common.PrintTable.call(this,'print')
    }
  }
};
</script>