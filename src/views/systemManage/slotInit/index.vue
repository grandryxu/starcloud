<template>
  <div class="iot-list" ref="page">
    <div class="iot-form">
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <div class="iot-form-row">
            <el-form-item label="仓库">
              <el-select class="iot-w200" v-model="searchForm.row_warehouse_id" placeholder="请选择" clearable>
                <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="类型">
              <el-select class="iot-w200" v-model="searchForm.row_type" placeholder="请选择" clearable>
                <el-option v-for="item in row_type_list" :key="item.id" :label="item.row_type_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">查询</el-button>
            </el-form-item>
          </div>
        </el-form>
      </div>

      <div class="layout__btns">
        <div ref="permissions">
          <el-button type="primary" plain icon="el-icon-plus" @click="click_add">新增</el-button>
          <el-button type="primary" plain icon="el-icon-edit-outline" @click="handleEdit">编辑</el-button>
          <el-button type="primary" plain icon="el-icon-delete" @click="handleDelete">删除</el-button>
          <el-button type="primary" plain icon="el-icon-document" @click="exportExcel">导出</el-button>
          <el-button type="primary" plain icon="el-icon-printer"  @click="printTable">打印</el-button>
          <el-button type="primary" plain icon="el-icon-edit" @click="generateSlot">库位生成</el-button>
        </div>
      </div>
    </div>
    <div class="iot-table">
      <el-table id="out-table" ref="print" :data="tableData" stripe style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
        <el-table-column align="center" prop="row_type" label="类型" min-width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.row_type === 1">库位</span>
            <span v-else-if="scope.row.row_type === 2">巷道</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="row_name" label="名称" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="row_no" label="库位排" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="row_tunnel_no" label="巷道排" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="row_inout_type" label="内外侧标识" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="row.row_name" label="对应外侧名" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="warehouse.warehouse_name" label="所在仓库" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="row_start_layer" label="起始层" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="row_end_layer" label="终止层" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="row_order" label="排序" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="row_start_column" label="起始列" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="row_end_column" label="终止列" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="row_is_enable" label="启用状态" min-width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.row_is_enable === 1">启用</span>
            <span v-else-if="scope.row.row_is_enable === 2">禁用</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="iot-pagination">
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
    </div>
    <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
      <!---新增库位初始化 --->
      <el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
        <div class="iot-form-row">
          <el-form-item label="仓库" label-width="100px" prop="row_warehouse_id">
            <el-select class="iot-w200" v-model="ruleForm.row_warehouse_id" placeholder="请选择" @change="searchRowByType">
              <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="类型" label-width="100px" prop="row_type">
            <el-select class="iot-w200" v-model="ruleForm.row_type" placeholder="请选择" @change="searchRowByType">
              <el-option v-for="item in row_type_list" :key="item.id" :label="item.row_type_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="排号" label-width="100px" prop="row_no">
            <el-input class="iot-w200" placeholder="请输入排号" clearable v-model="ruleForm.row_no" @input="row_no_input($event)" @blur="row_no_blur($event)" @keyup.native="row_no_keyup($event)"></el-input>
          </el-form-item>
          <el-form-item label="名称" label-width="100px" prop="row_name">
            <el-input class="iot-w200" placeholder="请输入名称" clearable v-model="ruleForm.row_name"></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="内外侧标识" label-width="100px" prop="row_inout_type">
            <el-select class="iot-w200" v-model="ruleForm.row_inout_type" placeholder="请选择" @change="searchOutRow" :disabled="ruleForm.row_type===1?false:true">
              <el-option v-for="item in inout_type_list" :key="item.id" :label="item.type_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="对应外侧名" label-width="100px" prop="row_out_id">
            <el-select class="iot-w200" v-model="ruleForm.row_out_id" placeholder="请选择" :disabled="ruleForm.row_inout_type===1?false:true">
              <el-option v-for="item in row_info_list" :key="item.id" :label="item.row_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="iot-form-row" v-if="ruleForm.row_type===1">
          <el-form-item label="起始层" label-width="100px" prop="row_start_layer">
            <el-input class="iot-w200" placeholder="请输入起始层" clearable v-model="ruleForm.row_start_layer" @keyup.native="row_no_keyup($event)"></el-input>
          </el-form-item>
          <el-form-item label="终止层" label-width="100px" prop="row_end_layer">
            <el-input class="iot-w200" placeholder="请输入终止层" clearable v-model="ruleForm.row_end_layer" @keyup.native="row_no_keyup($event)"></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row" v-if="ruleForm.row_type===1">
          <el-form-item label="起始列" label-width="100px" prop="row_start_column">
            <el-input class="iot-w200" placeholder="请输入起始列" clearable v-model="ruleForm.row_start_column" @keyup.native="row_no_keyup($event)"></el-input>
          </el-form-item>
          <el-form-item label="终止列" label-width="100px" prop="row_end_column">
            <el-input class="iot-w200" placeholder="请输入终止列" clearable v-model="ruleForm.row_end_column" @keyup.native="row_no_keyup($event)"></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="排序" label-width="100px" prop="row_order">
            <el-input class="iot-w200" placeholder="请输入排序" clearable v-model="ruleForm.row_order" @blur="search_row_order($event)" @keyup.native="row_no_keyup($event)"></el-input>
          </el-form-item>
          <el-form-item label="备注" label-width="100px" prop="row_remark">
            <el-input type="textarea" :rows="4" :maxlength="200" class="iot-w240" show-word-limit v-model="ruleForm.row_remark" clearable></el-input>
          </el-form-item>
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
import {
  getListApi,
  addApi,
  getOneApi,
  editApi,
  deleteApi,
  getRowInfoByRowNo,
  getRowInfoByType,
  getAllRowInfoQuery,
  generateSlotByStock,
  getRowOrder
} from "./api";
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
      slot_closed_statusList: [
        { id: 0, slot_closed_statusName: "正常" },
        { id: 1, slot_closed_statusName: "屏蔽" }
      ],
      row_type_list: [
        { id: 1, row_type_name: "库位" },
        { id: 2, row_type_name: "巷道" }
      ],
      inout_type_list: [
        { id: 1, type_name: "内测" },
        { id: 2, type_name: "外测" },
        { id: 3, type_name: "单排" }
      ],
      row_info_list: [],
      warehouseList: [],
      multipleSelection: [],
      radio1: "",
      searchForm: {},
      dialogTitle: "新增",
      tableData: [],
      isEnableList: [
        { id: 0, enableName: "正常" },
        { id: 1, enableName: "停用" }
      ],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      exist_row_no: [],
      //表单数据
      ruleForm: {
        row_warehouse_id: "",
        row_type: 0,
        row_no: "",
        row_name: "",
        row_inout_type: 0,
        row_out_id: "",
        row_start_layer: "",
        row_end_layer: "",
        row_start_column: "",
        row_end_column: "",
        row_order: 0
      },
      //表单验证规则
      rules: {
        row_no: [
          {
            required: true,
            message: "请输入排号",
            trigger: "blur"
          }
        ],
        row_start_layer: [
          {
            required: true,
            message: "请输入起始层",
            trigger: "blur"
          },
          {
            validator(rule, value, callback) {
              if (
                Number.isInteger(Number(value)) &&
                Number(value) > 0 &&
                Number(value) < 999
              ) {
                callback();
              } else {
                callback(new Error("请输入1-999的正整数"));
              }
            },
            trigger: "blur"
          }
        ],
        row_start_column: [
          {
            required: true,
            message: "请输入起始列",
            trigger: "blur"
          },
          {
            validator(rule, value, callback) {
              if (
                Number.isInteger(Number(value)) &&
                Number(value) > 0 &&
                Number(value) < 999
              ) {
                callback();
              } else {
                callback(new Error("请输入1-999的正整数"));
              }
            },
            trigger: "blur"
          }
        ],
        row_end_layer: [
          {
            required: true,
            message: "请输入终止层",
            trigger: "blur"
          },
          {
            validator(rule, value, callback) {
              if (
                Number.isInteger(Number(value)) &&
                Number(value) > 0 &&
                Number(value) < 999
              ) {
                callback();
              } else {
                callback(new Error("请输入1-999的正整数"));
              }
            },
            trigger: "blur"
          }
        ],
        row_end_column: [
          {
            required: true,
            message: "请输入终止列",
            trigger: "blur"
          },
          {
            validator(rule, value, callback) {
              if (
                Number.isInteger(Number(value)) &&
                Number(value) > 0 &&
                Number(value) < 999
              ) {
                callback();
              } else {
                callback(new Error("请输入1-999的正整数"));
              }
            },
            trigger: "blur"
          }
        ],
        row_order: [
          {
            required: true,
            message: "请输入排序",
            trigger: "blur"
          }
        ]
      }
    };
  },
  mounted() {
    this.btnInit();
    this.GetAll();
    this.GetWarehouseList();
  },
  methods: {
    //根据当前用户权限标识初始化按钮状态
    btnInit() {
      this.$Common.DistributePermission.call(this)
    },
    //库位排input事件
    row_no_input(e) {
      if (typeof this.ruleForm.row_type == "undefined") {
        this.$message("请先选择库位类型");
        this.ruleForm.row_no = "";
        return;
      }
      this.ruleForm.row_name =
        this.ruleForm.row_no +
        "排 " +
        (this.ruleForm.row_type == 1 ? "库位" : "巷道");
    },
    //库位排blur事件
    row_no_blur(e) {
      let is_exist = false; //是否有重复排位号
      if (this.exist_row_no.length > 0) {
        this.exist_row_no.forEach(item => {
          if (item.row_no == parseInt(e.target.value)) {
            is_exist = true;
          }
        });
      }
      if (is_exist) {
        this.$message("此排位号已经存在，请重新输入");
        this.ruleForm.row_no = "";
        this.ruleForm.row_name =
          this.ruleForm.row_no +
          "排 " +
          (this.ruleForm.row_type == 1 ? "库位" : "巷道");
        return;
      }
    },
    //输入框keyup事件
    row_no_keyup(e) {
      e.target.value = e.target.value.replace(/[^\d.]/g, ""); ///^\+?[1-9]\d*$/  大于0的正整数
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

    //排序输入框验证去重
    async search_row_order(e) {
      let params = {};
      params.row_warehouse_id = this.ruleForm.row_warehouse_id;
      let res = await getRowOrder(params);
      let is_exist_order = false;
      if (res && res.length > 0) {
        for (var i in res) {
          if (res[i].row_order == parseInt(e.target.value)) {
            this.$message("此排序已经存在，请重新输入");
            this.ruleForm.row_order = 0;
            break;
          }
        }
      }
    },
    //表单重置
    async resetForm() {
      this.dialogVisible = true;
      await this.$nextTick();
      this.$refs["dialogForm"].resetFields();
      this.ruleForm = {};
      this.$utils.traverseDialogDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.dialogForm);
    },
    //仓库列表
    async GetWarehouseList() {
      let data = await this.$DropBox.getWarehouselist();
      if (data) {
        this.warehouseList = data || [];
      }
    },
    //列表
    async GetAll() {
      let params = {
        MaxResultCount: this.pageSize,
        SkipCount: (this.currentPage - 1) * this.pageSize,
        ...this.searchForm
      };
      // let data = await getListApi(params);
      let data = await getAllRowInfoQuery(params);
      if (data) {
        this.tableData = data.items || [];
        this.total = data.totalCount || 0;
        // this.tableData = data || [];
        // this.total = data.length;
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
            "新增"
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
            message: "新增成功"
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
        let row = this.multipleSelection[0];
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

    //生成库位
    generateSlot() {
      if (this.multipleSelection.length == 1) {
        this.autoGenerate(this.multipleSelection[0]);
      } else {
        //this.text_selectOne  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_selectOne);
      }
    },

    async autoGenerate(row) {
      if (row.row_is_enable === 2) {
        let mes="当前选项已经生成"+(row.row_type==1?"库位":"巷道");
        this.$message(mes);
        return;
      }
      let params = {
        id: row.id
      };
      let res = await generateSlotByStock(params);
      if (res > 0) {
        this.$message({
          type: "success",
          message: "成功生成" + res + "个"+(row.row_type==1?"库位":"巷道")
        });
        this.GetAll();
      }
    },
    //根据库位类型搜索库位排
    async searchRowByType() {
      let row_no = "";
      if (this.ruleForm.row_no == undefined || this.ruleForm.row_no == "") {
        row_no = "";
      } else {
        row_no = this.ruleForm.row_no;
      }

      this.ruleForm.row_name =
        row_no + "排 " + (this.ruleForm.row_type == 1 ? "库位" : "巷道");

      let params = {};
      params.row_warehouse_id = this.ruleForm.row_warehouse_id;
      params.row_type = this.ruleForm.row_type;
      let res = await getRowInfoByType(params);
      if (res) {
        this.exist_row_no = res;
      }
    },
    //获取外侧标识（当前排是内测时候才需要）
    async searchOutRow() {
      let params = {
        ...this.ruleForm
      };
      let res = await getRowInfoByRowNo(params);
      if (res) {
        this.row_info_list = res;
      }
    },
    click_add() {
      this.dialogTitle = "新增";
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
        if (this.dialogTitle == "新增") {
          this.Create();
        } else {
          this.Update();
        }
      });
    },
    //根据主键删除
    Delete(row) {
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
        let params = {
          id: row.id
        };
        //使用await方法前要加async
        let res = await deleteApi(params);
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
      let res = await getOneApi(params);
      this.ruleForm = res;
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