<template>
  <div class="iot-list" ref="page">
    <div class="iot-form">
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <el-form-item label="物资名称">
            <el-input class="iot-w200" placeholder v-model="searchForm.goods_name"></el-input>
          </el-form-item>
          <el-form-item label="质量状态">
            <el-input class="iot-w200" placeholder v-model="searchForm.quality_name"></el-input>
          </el-form-item>
          <el-form-item label="仓库">
            <el-input class="iot-w200" placeholder v-model="searchForm.warehouse_name"></el-input>
          </el-form-item>
          <el-form-item label="入库日期">
            <el-input class="iot-w200" placeholder v-model="searchForm.inventory_date_b"></el-input>  --  
            <el-input class="iot-w200" placeholder v-model="searchForm.inventory_date_e"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">查询</el-button>
          </el-form-item>
        </el-form>
      </div>

      <div class="layout__btns">
        <div ref="permissions">
          <!-- <el-button type="primary" plain icon="el-icon-plus" @click="click_add">新增</el-button>
          <el-button type="primary" plain icon="el-icon-edit-outline" @click="handleEdit">编辑</el-button>
          <el-button type="primary" plain icon="el-icon-delete" @click="handleDelete">删除</el-button> -->
          <el-button type="primary" plain icon="el-icon-document" @click="exportExcel">导出</el-button>
          <el-button type="primary" plain icon="el-icon-printer" @click="printTable">打印</el-button>
        </div>
      </div>
    </div>
    <div class="iot-table">
      <el-table id="out-table" ref="print" :data="tableData" stripe style="width: 100%" border @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
        <el-table-column align="center" prop="goods_code" label="物资编码" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="goods_name" label="物资名称" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="goods_standard" label="规格" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="goods_unit" label="单位名称" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="quality_name" label="质量状态" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="inventory_quantity" label="库存数量" min-width="100" show-overflow-tooltip></el-table-column>
      </el-table>
    </div>
    <div class="iot-pagination">
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
    </div>
    <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
      <el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
        <div class="iot-form-row">
          <el-form-item label="大批号" label-width="100px" prop="inventory_batch_no">
            <el-input class="iot-w240" v-model="ruleForm.inventory_batch_no" placeholder="请输入大批号" clearable></el-input>
          </el-form-item>
          <el-form-item label="小批号" label-width="100px" prop="inventory_lots_no">
            <el-input class="iot-w240" v-model="ruleForm.inventory_lots_no" placeholder="请输入小批号" clearable></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="生产日期" label-width="100px" prop="inventory_product_date">
            <el-input class="iot-w240" v-model="ruleForm.inventory_product_date" placeholder="请输入生产日期" clearable></el-input>
          </el-form-item>
          <el-form-item label="生产线" label-width="100px" prop="inventory_product_lineid">
            <el-input class="iot-w240" v-model="ruleForm.inventory_product_lineid" placeholder="请输入生产线" clearable></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="单据条码" label-width="100px" prop="inventory_bill_bar">
            <el-input class="iot-w240" v-model="ruleForm.inventory_bill_bar" placeholder="请输入单据条码" clearable></el-input>
          </el-form-item>
          <el-form-item label="失效日期" label-width="100px" prop="inventory_vaildate_date">
            <el-input class="iot-w240" v-model="ruleForm.inventory_vaildate_date" placeholder="请输入失效日期" clearable></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="复检日期" label-width="100px" prop="inventory_recheck_date">
            <el-input class="iot-w240" v-model="ruleForm.inventory_recheck_date" placeholder="请输入复检日期" clearable></el-input>
          </el-form-item>
          <el-form-item label="数量" label-width="100px" prop="inventory_quantity">
            <el-input class="iot-w240" v-model="ruleForm.inventory_quantity" placeholder="请输入数量" clearable></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="箱码" label-width="100px" prop="inventory_box_code">
            <el-input class="iot-w240" v-model="ruleForm.inventory_box_code" placeholder="请输入箱码" clearable></el-input>
          </el-form-item>
          <el-form-item label="托盘号码" label-width="100px" prop="inventory_stock_code">
            <el-input class="iot-w240" v-model="ruleForm.inventory_stock_code" placeholder="请输入托盘号码" clearable></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="库位" label-width="100px" prop="inventory_slot_code">
            <el-input class="iot-w240" v-model="ruleForm.inventory_slot_code" placeholder="请输入库位" clearable></el-input>
          </el-form-item>
          <el-form-item label="状态" label-width="100px" prop="inventory_status">
            <el-select class="iot-w240" v-model="ruleForm.inventory_status" placeholder="请选择状态">
              <el-option v-for="item in inventoryStatusList" :key="item.id" :label="item.statusName" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="是否抽检托盘 1 是 0 否" label-width="100px" prop="inventory_stock_status">
            <el-select class="iot-w240" v-model="ruleForm.inventory_stock_status" placeholder="请选择是否抽检托盘">
              <el-option v-for="item in stockStatusList" :key="item.id" :label="item.stockStatus" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="入库日期" label-width="100px" prop="inventory_date">
            <el-input class="iot-w240" v-model="ruleForm.inventory_date" placeholder="请输入入库日期" clearable></el-input>
          </el-form-item>
        </div>
        <!-- <div class="iot-form-row">
          <el-form-item label="入库时间" label-width="100px" prop="inventory_datetime">
            <el-input class="iot-w240" v-model="ruleForm.inventory_datetime" placeholder="请输入入库时间" clearable></el-input>
          </el-form-item>
        </div> -->
        <div class="iot-form-row">
          <el-form-item label="备注" label-width="100px" prop="inventory_remark">
            <el-input type="textarea" :rows="4" :maxlength="200" class="iot-w240" v-model="ruleForm.inventory_remark" clearable></el-input>
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
      dialogTitle: "新增",
      tableData: [],
      inventoryStatusList: [
        { id: 1, statusName: "可用" },
        { id: 2, statusName: "分配" },
        { id: 3, statusName: "出库" },
        { id: 4, statusName: "复核" },
        { id: 5, statusName: "暂存" },
        { id: 6, statusName: "回流" },
        { id: 7, statusName: "冻结" }
      ],
      stockStatusList: [
        { id: 1, stockStatus: "是" },
        { id: 0, stockStatus: "否" }
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
        warehouse_code: "",
        warehouse_name: "",
        warehouse_type: "",
        warehouse_slot_type: "",
        warehouse_is_enable: "",
        warehouse_remark: ""
      },
      //表单验证规则
      rules: {
        warehouse_name: [
          {
            required: true,
            message: "请输入仓库名称",
            trigger: "blur"
          }
        ],
        warehouse_code: [
          {
            required: true,
            message: "请输入仓库编码",
            trigger: "blur"
          }
        ],
        warehouse_type: [
          {
            required: true,
            message: "请选择仓库类型",
            trigger: "blur"
          }
        ],
        warehouse_slot_type: [
          {
            required: true,
            message: "请选择库位类型",
            trigger: "blur"
          }
        ],
        warehouse_is_enable: [
          {
            required: true,
            message: "请选择启用状态",
            trigger: "blur"
          }
        ]
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
      console.log(this.multipleSelection);
      if (this.multipleSelection.length == 0) {
        //this.text_deleteRow  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_deleteRow);
      } else {
        let rows = this.multipleSelection;
        this.Delete(rows);
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
      let res = await getOneApi(params);
      let obj = {
        id: row.id,
        warehouse_code: res.warehouse_code,
        warehouse_is_enable: res.warehouse_is_enable,
        warehouse_name: res.warehouse_name,
        warehouse_remark: res.warehouse_remark,
        warehouse_slot_type: res.warehouse_slot_type,
        warehouse_type: res.warehouse_type
      };
      this.ruleForm = obj;
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