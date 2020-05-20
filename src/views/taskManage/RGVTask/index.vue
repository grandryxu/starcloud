<template>
  <div class="iot-list" ref="page">
    <div class="iot-form">
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <div class="iot-form-row">
            <el-form-item label="任务号">
              <el-input class="iot-w200" placeholder v-model="searchForm.rgv_no" clearable></el-input>
            </el-form-item>
            <el-form-item label="来源">
              <el-select class="iot-w200" v-model="searchForm.rgv_manual_flag" placeholder="请选择" clearable>
                <el-option v-for="item in sourceList" :key="item.id" :label="item.modeName" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="仓库">
              <el-select class="iot-w200" v-model="searchForm.warhouse_id" placeholder="请选择" clearable>
                <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="托盘码">
              <el-input class="iot-w200" placeholder v-model="searchForm.rgv_stock_code" clearable></el-input>
            </el-form-item>
            <el-form-item label="下发时间">
              <el-date-picker class="iot-w200" clearable v-model="searchForm.startDate" type="datetime"></el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">
                查询
              </el-button>
            </el-form-item>
          </div>
        </el-form>
      </div>
      <div class="layout__btns">
        <div ref="permissions">
          <el-button type="primary" plain icon="el-icon-plus" @click="click_add">新增</el-button>
          <el-button type="primary" plain icon="el-icon-edit-outline" @click="handleEdit">编辑</el-button>
          <el-button type="primary" plain icon="el-icon-delete" @click="handleDelete">删除</el-button>
        </div>
      </div>
    </div>
    <div class="iot-table">
      <el-table :data="tableData" stripe style="width: 100%" border @selection-change="handleSelectionChange" @row-dblclick="click_edit">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
        <el-table-column align="center" prop="rgv_no" label="任务号" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="rgv_priority" label="优先级" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="slot1.warehouse.warehouse_name" label="仓库" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="slot1.slot_code" label="库位编码" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="rgv_stock_code" label="托盘" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="CreationTime" label="下发时间" min-width="100" show-overflow-tooltip :formatter="dateTimeTransform"></el-table-column>
      </el-table>
    </div>
    <div class="iot-pagination">
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
    </div>

    <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="844px" append-to-body>
      <el-form inline ref="dialogForm" :model="TaskForm" :rules="TaskFormrules">
        <div class="iot-form-row">
          <el-form-item label="任务号" label-width="120px" prop="rgv_no">
            <el-input class="iot-w240" v-model="TaskForm.rgv_no" placeholder="请输入任务号" maxlength="30" clearable :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="仓库" label-width="120px">
            <el-select class="iot-w200" v-model="TaskForm.warhouse_id" placeholder="请选择" clearable>
              <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="优先级" label-width="120px" prop="rgv_priority">
            <el-select class="iot-w240" v-model="TaskForm.rgv_priority" placeholder="请选择" clearable>
              <el-option v-for="item in sortList" :key="item.id" :label="item.id" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="库位码" label-width="120px" prop="rgv_stock_code">
            <el-input class="iot-w240" v-model="TaskForm.slot_code_code" placeholder="请输入库位码" maxlength="50" clearable></el-input>
          </el-form-item>
          <el-form-item label="托盘" label-width="120px" prop="rgv_stock_code">
            <el-input class="iot-w240" v-model="TaskForm.rgv_stock_code" placeholder="请输入托盘编码" maxlength="50" clearable></el-input>
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
  startApi,
  finishApi,
  getrkListApi,
  getkyprkListApi,
  UpdateStateApi,
  GetSlotCodeIdApi,
  GetEncodingRuleCode,
} from "./api";

export default {
  //全局混入提示文字
  mixins: [textConfig],
  components: {
    singlePictureUpload
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
      warehouseList: [],
      tunnelInfoList: [],
      sortList: [{ id: 1 }, { id: 2 }, { id: 3 }, { id: 4 }, { id: 5 }],
      sourceList: [
        { id: 1, modeName: "自动" },
        { id: 2, modeName: "手动" }
      ],
      multipleSelection: [],
      radio1: "",
      searchForm: {
      },
      dialogTitle: "查看",
      singleDialogTitle: "修改状态",
      tableData: [],
      lstableData: [],
      isEnableList: [
        { id: 0, enableName: "正常" },
        { id: 1, enableName: "停用" }
      ],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      singleDialogVisible: false,
      //表单数据
      ruleForm: {
        rgv_priority: "",
        rgv_execute_flag: ""
      },
      dialogForm: {},
      //表单验证规则
      rules: {
        unit_name: [
          {
            required: true,
            message: "请输入单位名称",
            trigger: "blur"
          }
        ],
        unit_is_enable: [
          {
            required: true,
            message: "请选择启用状态",
            trigger: "blur"
          }
        ]
      },
      ImportOrderId: '',
      tableData2: [],
      rgv_mode: '',
      dialogTitleA: '手动生成',
      dialogVisibleA: false,
      TaskForm: {
        rgv_no: "",
        rgv_priority: "",
        rgv_stock_code: "",
      },
      TaskFormrules: {
        rgv_mode: [
          {
            required: true,
            message: "请选择类型",
            trigger: "blur"
          }
        ],
        warehouse_id: [
          {
            required: true,
            message: "请选择仓库",
            trigger: "blur"
          }
        ],
        rgv_slot_code: [
          {
            required: true,
            message: "请选择库位编码",
            trigger: "blur"
          }
        ],
      },

    };
  },
  mounted() {
    this.GetAll();
    this.GetWarehouseList();
    this.btnInit();

  },
  methods: {
    //获取单号
    async GetEncodingRuleCode() {
      let params = {
        code: 'TaskCode'
      };
      let code = await GetEncodingRuleCode(params);
      this.TaskForm.rgv_no = code;
    },
    //新增按钮
    click_add() {
      this.dialogTitle = "新增";
      this.GetEncodingRuleCode();
      this.resetForm();
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
    click_edit(row) {
      this.dialogTitle = "编辑";
      this.resetForm();
      this.Get(row);
    },
    //保存提交
    click_submit() {
      this.$refs["dialogForm"].validate(valid => {
        if (!valid) {
          return;
        }
        if (!Object.keys(this.TaskForm).length) {
          this.dialogVisible = false;
          return;
        }
        if (this.dialogTitle == "新增") {
          this.Create();
        } else {
          this.Update();
        }


      })


    },
    //根据当前用户权限标识初始化按钮状态
    btnInit() {
      this.$Common.DistributePermission.call(this)
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.GetAll();
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
      if (params.rgv_execute_flag == null) {
        params.rgv_execute_flag = 10;
      }

      let data = await getListApi(params);
      if (data) {
        this.tableData = data.items || [];
        this.total = data.totalCount || 0;
        this.multipleSelection = [];
      }
      this.$utils.traversePageDom.call(this.$utils, this.$store.state.currentLang, this.lang, this.$refs.page);
      this.$utils.traverseFormValidator(this.rules, this.lang)
    },
    //新建
    Create() {
      if (!Object.keys(this.TaskForm).length) {
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
          ...this.TaskForm
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
    click_search() {
      this.currentPage = 1;
      this.GetAll();
    },
    //表单重置
    async resetForm() {
      this.dialogVisible = true;
      await this.$nextTick();
      this.$refs["dialogForm"].resetFields();

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
            message: "删除成功"
          });
          this.GetAll();
          this.dialogVisible = false;
        }
      });
    },
    //编辑
    Update(row) {
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
            "修改"
          ),
          h("span", null, "】吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        let params = {
          ...this.TaskForm
        }
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
      let params = { id: row.id };
      let res = await getOneApi(params);
      let obj = {
        id: row.id,
        rgv_priority: res.rgv_priority,
        rgv_no: res.rgv_no,
        rgv_stock_code: res.rgv_stock_code,
      };
      this.TaskForm = obj;
    },
    //日期时间转换
    dateTimeTransform(row) {
      let time = row.creationTime;
      return this.$moment(time).format("YYYY-MM-DD HH:mm:ss");
    }
  }
};
</script>