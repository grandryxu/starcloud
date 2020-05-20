<template>
  <div class="iot-list">
    <div class="iot-form">
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <div class="iot-form-row">
            <el-form-item label="报表类型">
              <el-select class="iot-w200" v-model="searchForm.tempStyle" @change="selectReportFormType" clearable>
                <el-option v-for="item in tempStyleList" :key="item.id" :label="item.styleName" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </div>
          <div v-if="imgFromShow" class="iot-form-row" style="display:flex;">
            <span style="flex:1">折线图</span>
            <span style="flex:1">饼状图</span>
            <span style="flex:1">柱状图</span>
          </div>
        </el-form>
      </div>
      <div class="layout__btns">
        <div>
          <el-button type="primary" plain icon="el-icon-plus" @click="click_add">新增</el-button>
          <el-button type="primary" plain icon="el-icon-edit-outline" @click="handleEdit">编辑</el-button>
          <el-button type="primary" plain icon="el-icon-delete" @click="handleDelete">删除</el-button>
        </div>
      </div>
    </div>
    <div class="iot-table">
      <el-table :data="tableData" stripe style="width: 100%" border @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
        <el-table-column align="center" prop="fullName" label="报表名称" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="tempStyle" label="报表风格" min-width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.tempStyle === '1'">列表</span>
            <span v-else-if="scope.row.tempStyle === '2'">图表</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="tempType" label="图表类型" min-width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.tempType === 'pie'">饼图</span>
            <span v-else-if="scope.row.tempType === 'line'">折线图</span>
            <span v-else-if="scope.row.tempType === 'bar'">柱状图</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="paramJson" label="报表参数" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="description" label="报表描述" min-width="100" show-overflow-tooltip></el-table-column>
      </el-table>
    </div>
    <div class="iot-pagination">
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
    </div>
    <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
      <el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
        <div class="iot-form-row">
          <el-form-item label="报表名称" label-width="100px" prop="fullName">
            <el-input class="iot-w240" v-model="ruleForm.fullName" placeholder="请输入报表名称" maxlength="30" clearable></el-input>
          </el-form-item>
          <el-form-item label="报表风格" label-width="100px" prop="tempStyle">
            <el-select class="iot-w240" v-model="ruleForm.tempStyle" placeholder="请选择">
              <el-option v-for="item in tempStyleList" :key="item.id" :label="item.styleName" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="图表类型" label-width="100px" prop="tempType">
            <el-select class="iot-w240" v-model="ruleForm.tempType" placeholder="请选择">
              <el-option v-for="item in chartTypeList" :key="item.id" :label="item.typeName" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="报表介绍" label-width="100px" prop="description">
            <el-input type="textarea" :rows="4" :maxlength="200" show-word-limit class="iot-w240" v-model="ruleForm.description" clearable></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="报表参数" label-width="100px" prop="paramJson">
            <el-input type="textarea" :rows="6" :maxlength="600" show-word-limit class="iot-w240" v-model="ruleForm.paramJson" clearable></el-input>
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
  data() {
    return {
      chartTypeList: [
        { id: 'pie', typeName: '饼图' },
        { id: 'line', typeName: '折线图' },
        { id: 'bar', typeName: '柱状图' },
      ],
      tempStyleList: [
        { id: '1', styleName: '列表' },
        { id: '2', styleName: '图表' }
      ],
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
      //表单数据
      ruleForm: {
        fullName: "",
        tempStyle: "",
        tempType: "",
        description: "",
        paramJson: ""
      },
      //表单验证规则
      rules: {
        fullName: [
          {
            required: true,
            message: "请输入报表名称",
            trigger: "blur"
          }
        ],
        paramJson: [
          {
            required: true,
            message: "请设置查询语句",
            trigger: "blur"
          }
        ]
      },
      imgFromShow: false
    };
  },
  mounted() {
    this.GetAll();
    this.GetAreaList();
    this.GetWarehouseList();
    this.GetQualityList();
  },
  methods: {
    //选择报表类型
    selectReportFormType(val) {
      if (val == 1) {
        this.imgFromShow = false
      } else if (val == 2) {
        this.imgFromShow = true
      }
    },

    handleCurrentChange(val) {
      this.currentPage = val;
      this.GetAll();
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

    //表单重置
    async resetForm() {
      this.dialogVisible = true;
      await this.$nextTick();
      this.$refs["dialogForm"].resetFields();
    },
    //仓库列表
    async GetWarehouseList() {
      let data = await this.$DropBox.getWarehouselist();
      if (data) {
        this.warehouseList = data || [];
      }
    },
    //质量状态下拉
    async GetQualityList() {
      let data = await this.$DropBox.getQualitylist();
      if (data) {
        console.log(data);
        this.qualityList = data.items || [];
      }
    },
    //仓库区域
    async GetAreaList() {
      let data = await this.$DropBox.getArealist();
      if (data) {
        this.areaList = data || [];
      }
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
      this.$utils.traversePageDom.call(this.$utils, this.$store.state.currentLang, this.lang, this.$refs.page);
      this.$utils.traverseFormValidator(this.rules, this.lang)
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
    //点击入库锁定
    implock() {
      if (this.multipleSelection.length == 1) {
        this.Update(this.multipleSelection[0], 0);
      } else {
        //this.text_selectOne  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_selectOne);
      }
    },
    //点击出库锁定
    exitlock() {
      if (this.multipleSelection.length == 1) {
        this.Update(this.multipleSelection[0], 1);
      } else {
        //this.text_selectOne  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_selectOne);
      }
    },

    //点击出库锁定
    closed() {
      if (this.multipleSelection.length == 1) {
        this.Update(this.multipleSelection[0], 2);
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
    Update(row, val) {
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
        let params = row;
        if (val == 0) {
          if (params.slot_imp_status == 0) {
            params.slot_imp_status = 1;
          } else {
            params.slot_imp_status = 0;
          }
        } else if (val == 1) {
          if (params.slot_exp_status == 0) {
            params.slot_exp_status = 1;
          } else {
            params.slot_exp_status = 0;
          }
        } else if (val == 2) {
          if (params.slot_closed_status == 0) {
            params.slot_closed_status = 1;
          } else {
            params.slot_closed_status = 0;
          }
        } else if (val == 3) {
          params.slot_area_id = this.ruleForm.slot_area_id;
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
        fullName: res.fullName,
        tempStyle:res.tempStyle,
        tempType:res.tempType,
        description:res.description,
        paramJson:res.paramJson
      };
      this.ruleForm = obj;
    }
  }
};
</script>
<style scope>
td.el-table__expanded-cell {
  padding: 0px 0px 0px 100px !important;
}
</style>