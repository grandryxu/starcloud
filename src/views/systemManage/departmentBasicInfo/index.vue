<template>
  <div class="iot-list" ref="page">
    <div class="iot-form">
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <el-form-item label="部门名称">
            <el-input class="iot-w200" placeholder v-model="searchForm.name" @change="click_search" clearable></el-input>
          </el-form-item>
          <el-form-item label="负责人">
            <el-input class="iot-w200" placeholder v-model="searchForm.ManagerName" @change="click_search" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <div class="iot-table">
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="lay">
            <div class="filterBar">
              <el-input class="iot-w200" placeholder="输入关键字进行过滤" v-model="filterText" clearable></el-input>
            </div>
            <el-tree class="filter-tree" :data="parentCompanyTree" :props="defaultProps" accordion :filter-node-method="filterNode" ref="tree" @current-change="parentCompanyOptionsClicked" :render-content="renderContent"></el-tree>
          </div>
        </el-col>
        <!-- <el-col :span="6">
					<div class="lay">
						<div class="filterBar">
							<el-input class="iot-w200" placeholder="输入关键字进行过滤" v-model="departmentFilterText" clearable>
							</el-input>
						</div>
						<el-tree class="filter-tree" :data="parentDepartmentTree" :props="defaultProps" accordion
							:filter-node-method="departmentFilterNode" ref="departmentTree"
							@current-change="parentDepartmentOptionsClicked" :render-content="renderContent">
						</el-tree>
					</div>
        </el-col>-->
        <el-col :span="18">
          <div class="btns" ref="permissions">
            <el-button plain icon="el-icon-plus" @click="click_add">新增</el-button>
            <el-button plain icon="el-icon-edit-outline" @click="handleEdit">编辑</el-button>
            <el-button plain icon="el-icon-delete" @click="handleDelete">删除</el-button>
          </div>
          <div class="lay">
            <div class="iot-table">
              <el-table :data="tableData" stripe style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange" @row-click="clickRow" ref="pTable">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
                <el-table-column align="center" prop="departNo" label="部门编号" min-width="100" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="name" label="部门名称" min-width="100" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="company.name" label="所属公司" min-width="100" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="department.name" label="父级部门" min-width="100" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="managerName" label="负责人" min-width="100" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="remark" label="备注" min-width="100" show-overflow-tooltip></el-table-column>
              </el-table>
            </div>
            <div class="iot-pagination">
              <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
            </div>
            <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
              <el-form inline ref="dialogForm" :model="ruleForm" :rules="rules" v-if="dialogVisible">
                <div class="iot-form-row">
                  <el-form-item label="部门编号" label-width="100px" prop="departNo">
                    <el-input class="iot-w240" v-model="ruleForm.departNo" placeholder="请输入部门编号" clearable maxlength="30"></el-input>
                  </el-form-item>
                  <el-form-item label="部门名称" label-width="100px" prop="name">
                    <el-input class="iot-w240" v-model="ruleForm.name" placeholder="请输入部门名称" clearable maxlength="50"></el-input>
                  </el-form-item>
                </div>
                <div class="iot-form-row">
                  <el-form-item label="负责人" label-width="100px" prop="managerName">
                    <el-input class="iot-w240" v-model="ruleForm.managerName" placeholder="请输入负责人" clearable maxlength="30"></el-input>
                  </el-form-item>
                </div>
                <div class="iot-form-row">
                  <el-form-item label="是否父级" label-width="100px" prop="parentDepartment">
                    <el-radio-group class="iot-w240" v-model="ruleForm.parentDepartment" @change="parentDepartmentChanged">
                      <el-radio-button :label="1">是</el-radio-button>
                      <el-radio-button :label="2">否</el-radio-button>
                    </el-radio-group>
                  </el-form-item>
                  <el-form-item label="父级部门" label-width="100px" prop="departmentId" v-if="ruleForm.parentDepartment === 2">
                    <el-select v-model="ruleForm.departmentId" placeholder="请选择" class="iot-min-w240">
                      <el-option v-for="item in parentDepartmentOptions" :key="item.id" :label="item.name" :value="item.id" :disabled="item.disabled"></el-option>
                    </el-select>
                  </el-form-item>
                </div>
                <div class="iot-form-row">
                  <el-form-item label="备注" label-width="100px" prop="remark">
                    <el-input class="iot-w240" v-model="ruleForm.remark" type="textarea" :rows="4" maxlength="200" show-word-limit placeholder="请输入备注"></el-input>
                  </el-form-item>
                </div>
              </el-form>
              <span slot="footer" class="dialog-footer">
                <el-button size="small" @click="dialogVisible = false">取 消</el-button>
                <el-button size="small" type="primary" @click="click_submit">保 存</el-button>
              </span>
            </el-dialog>
          </div>
        </el-col>
      </el-row>
    </div>
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
  getCompanyTreeList,
  getDepartmentInfoSelectedList,
  getDepartmentTreeList
} from "./api";
export default {
  //全局混入提示文字
  mixins: [textConfig],
  components: {
    singlePictureUpload
  },
  props: ["lang"],
  data() {
    return {
      parentDepartmentOptions: [],
      parentCompanyTree: [],
      parentDepartmentTree: [],
      multipleSelection: [],
      departmentFilterText: "",
      filterText: "",
      radio1: "",
      searchForm: {
        CompanyId: ""
      },
      dialogTitle: "新增",
      tableData: [],
      productList: [
        {
          id: 1,
          salesName: "测试"
        }
      ],
      defaultProps: {
        children: "children",
        label: "label"
      },
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      ruleForm: {
        name: "",
        parentDepartment: 1,
        departNo: "",
        managerName: "",
        remark: "",
        departmentId: ""
      },
      //表单验证规则
      rules: {
        name: [
          {
            required: true,
            message: "请输入部门名称",
            trigger: "blur"
          },
          {
            min: 1,
            max: 30,
            message: "请输入1-30位字符",
            trigger: "blur"
          }
        ],
        departNo: [
          {
            required: true,
            message: "请输入部门编号",
            trigger: "blur"
          }
        ],
        managerName: [
          {
            required: true,
            message: "请输入负责人",
            trigger: "blur"
          }
        ]
      }
    };
  },
  mounted() {
    this.btnInit();
    this.GetAll();
    this.GetCompanyTreeList();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
    departmentFilterText(val) {
      this.$refs.departmentTree.filter(val);
    },
    lang: function(lang) {
      console.log(lang)
    this.$utils.traversePageDom.call(this.$utils,this.$store.state.currentLang,lang,this.$refs.page);
    this.$utils.traverseFormValidator(this.rules,this.lang)
    }
  },
  methods: {
    //根据当前用户权限标识初始化按钮状态
    btnInit() {
      this.$Common.DistributePermission.call(this)
    },
    parentCompanyOptionsClicked(obj, node) {
      this.searchForm.CompanyId = node.data.id;
      //this.GetDepartmentTreeList(node.data.id);
      this.GetDepartmentList(node.data.id);
      //this.resetForm();
      this.GetAll();
    },
    parentDepartmentOptionsClicked(obj, node) {
      this.multipleSelection[0] = node.data;
      this.resetForm();
      this.Get(node.data);
    },
    departmentFilterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1;
      this.GetAll();
    },
    indexMethod(index) {
      return this.pageSize * (this.currentPage - 1) + index + 1;
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
      this.$utils.traverseDialogDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.dialogForm);
    },
    async GetCompanyTreeList() {
      let params = {
        ...this.searchForm
      };
      let data = await getCompanyTreeList(params);
      if (data) {
        this.parentCompanyTree = data;
      }
    },
    async GetDepartmentTreeList(val) {
      let params = {
        guid: val
      };
      let data = await getDepartmentTreeList(params);
      if (data) {
        this.parentDepartmentTree = data;
      }
    },
    async GetDepartmentList(val) {
      let params = {
        guid: val
      };
      let data = await getDepartmentInfoSelectedList(params);
      if (data) {
        console.log(data);
        this.parentDepartmentOptions = data;
      }
    },
    //列表
    async GetAll() {
      let params = {
        MaxResultCount: this.pageSize,
        SkipCount: (this.currentPage - 1) * this.pageSize,
        ...this.searchForm
      };
      this.tableData = [];
      let data = await getListApi(params);
      if (data) {
        console.log("data = ", data);
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
          h("span", null, "确定"),
          h(
            "span",
            {
              style: this.c_primary
            },
            "保存"
          ),
          h("span", null, "吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        let params = {
          companyId: this.searchForm.CompanyId,
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
      if (this.searchForm.CompanyId === "") {
        this.$message({
          type: "error",
          message: "请先点击选择所在公司再添加部门"
        });
        return;
      }
      this.dialogTitle = "新增";
      this.GetDepartmentList(this.searchForm.CompanyId);
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
          h("span", null, "确定"),
          h(
            "span",
            {
              style: this.c_danger
            },
            "删除"
          ),
          h("span", null, "吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        let deleteCount = 0;
        for (let i = 0; i < rows.length; i++) {
          let res = await deleteApi({
            id: rows[i].id
          });
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
          h("span", null, "确定"),
          h(
            "span",
            {
              style: this.c_primary
            },
            "保存"
          ),
          h("span", null, "吗？")
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
        departNo: res.departNo,
        name: res.name,
        managerName: res.managerName,
        remark: res.remark,
        departmentId: res.departmentId,
        companyId: res.companyId
      };
      this.ruleForm = obj;
      if (obj.companyId == null) {
        this.$message({
          type: "error",
          message: "未获取到所在公司"
        });
        return;
      }
      if (obj.departmentId == null) this.ruleForm.parentDepartment = 1;
      else{
		  this.ruleForm.parentDepartment = 2;
		  console.log(this.ruleForm.companyId);
		  this.GetDepartmentList(this.ruleForm.companyId)
		}
      this.parentDepartmentOptions.forEach((v, i, a) => {
        v.disabled = v.id === obj.id;
      })
      console.log(
        "this.parentDepartmentOptions = ",
        this.parentDepartmentOptions
      );
    },
    parentDepartmentChanged(val) {
      if (val === 1) {
        this.ruleForm.departmentId = null;
        this.rules = Object.assign({}, this.rules);
      } else {
        if (this.searchForm.CompanyId === "") {
          this.dialogVisible = false;
          this.$message({
            type: "error",
            message: "请先点击选择所在公司"
          });
          return;
        }
        this.rules = Object.assign(
          {
            departmentId: [
              {
                required: true,
                message: "请选择父级部门",
                trigger: "change"
              }
            ]
          },
          this.rules
        );
      }
    },
    renderContent(h, { node, data, store }) {
      if (!node.isLeaf) {
        return h(
          "span",
          {
            style: "font-size: 14px;line-height:26px;"
          },
          [
            h("el-badge", {
              props: {
                value: node.childNodes.length,
                max: 10,
                type: "success"
              },
              style: "margin-top:-6px;"
            }),
            h("span", {}, node.label)
          ]
        );
      } else {
        return h(
          "span",
          {
            style: "font-size: 14px;line-height:26px;"
          },
          [h("span", {}, node.label)]
        );
      }
    },
    clickRow(row) {
      this.$refs.pTable.toggleRowSelection(row);
    }
  }
};
</script>
<style scoped>
.lay {
  min-height: 650px;
}

.lay:first-child {
  border-right: 1px solid #ebeef5;
}

.btns,
.filterBar {
  margin: 20px;
}
</style>