<template>
  <div class="iot-list" ref="page">
    <div class="iot-form">
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <el-form-item label="模块名称">
            <el-input class="iot-w200" placeholder v-model="searchForm.function_name" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="layout__btns">
        <div ref="permissions">
          <el-button type="primary" plain icon="el-icon-plus" @click="click_add">新增</el-button>
          <el-button type="primary" plain icon="el-icon-edit-outline" @click="handleEdit">编辑</el-button>
          <el-button type="primary" plain icon="el-icon-circle-check" @click="handleStart">启用</el-button>
          <el-button type="primary" plain icon="el-icon-warning-outline" @click="handleForbidden">禁用</el-button>
        </div>
      </div>
    </div>
    <div class="iot-table">
      <el-table :data="tableData" style="width: 100%;" row-key="id" border lazy :load="load" stripe :tree-props="{children: 'children', hasChildren: 'hasChildren'}" @row-dblclick="click_edit" @selection-change="handleSelectionChange" @row-click="clickRow" ref="pTable">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="menu_function_name" label="名称" width="180"></el-table-column>
        <el-table-column label="图标" width="50">
          <template slot-scope="scope">
            <i :class="scope.row.menu_icon"></i>
          </template>
        </el-table-column>
        <el-table-column prop="masterMenu" label="上级" width="150"></el-table-column>
        <el-table-column prop="menu_url" label="地址"></el-table-column>
        <el-table-column prop="menu_remark" label="权限标识"></el-table-column>
        <el-table-column prop="menu_is_enable" label="启用状态" width="80">
          <template slot-scope="scope">
            <span v-if="scope.row.menu_is_enable == 1">启用</span>
            <span v-else-if="scope.row.menu_is_enable == 2">禁用</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="iot-pagination">
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
    </div>
    <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
      <el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
        <el-form-item label="类型" label-width="100px" prop="menu_type">
          <el-radio-group v-model="ruleForm.menu_type" class="iot-w240" @change="menuTypeChange">
            <el-radio-button :label="1">菜单</el-radio-button>
            <el-radio-button :label="2">链接</el-radio-button>
            <el-radio-button :label="3">按钮</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="启用状态" label-width="100px" prop="menu_is_enable">
          <el-switch class="iot-w240" v-model="ruleForm.menu_is_enable" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="2"></el-switch>
        </el-form-item>
        <el-form-item label="功能名称" label-width="100px" prop="menu_function_name">
          <el-input class="iot-w240" v-model="ruleForm.menu_function_name" placeholder="请输入功能名称" clearable :maxlength="50"></el-input>
        </el-form-item>
        <el-form-item label="URL地址" label-width="100px" prop="menu_url" v-if="ruleForm.menu_type <= 2">
          <el-input class="iot-w240" v-model="ruleForm.menu_url" placeholder="请输入URL地址" clearable :maxlength="50"></el-input>
        </el-form-item>
        <el-form-item label="父节点" label-width="100px" prop="menu_parent_id" v-if="ruleForm.menu_type === 3">
          <el-cascader v-model="ruleForm.menu_parent_id" :options="parentTree" @change="handleChange" emitPath :props="{ value: 'id' }" class="iot-w240"></el-cascader>
        </el-form-item>
        <el-form-item label="父节点" label-width="100px" prop="menu_parent_id" v-if="ruleForm.menu_type === 2">
          <el-select v-model="ruleForm.menu_parent_id" placeholder="请选择" class="iot-w240">
            <el-option v-for="item in parentOptions" :key="item.id" :label="item.menu_function_name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="样式" label-width="100px" prop="menu_icon" v-if="ruleForm.menu_type === 1">
          <el-input class="iot-w240" v-model="ruleForm.menu_icon" placeholder="请输入图标样式名" clearable :maxlength="20"></el-input>
        </el-form-item>
        <el-form-item label="顺序" label-width="100px" prop="menu_order">
          <el-input type="number" class="iot-w240" v-model="ruleForm.menu_order" placeholder="请输入顺序" clearable :maxlength="10"></el-input>
        </el-form-item>
        <el-form-item label="权限标注" label-width="100px" prop="menu_remark" v-if="ruleForm.menu_type > 1">
          <el-input class="iot-w240" type="textarea" v-model="ruleForm.menu_remark" :rows="3" :maxlength="300" show-word-limit placeholder="请输入权限标注" clearable></el-input>
        </el-form-item>
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
import {
  getListApi,
  addApi,
  getOneApi,
  editApi,
  deleteApi,
  getSystemMenuInfoTree,
  getSystemMenuInfoSelectedList,
  getSystemSubMenuInfoList,
  getPermissionList
} from "./api";
export default {
  //全局混入提示文字
  mixins: [textConfig],
  components: {},
   props: ["lang"],
        watch: {
            lang: function(lang) {
            this.$utils.traversePageDom.call(this.$utils,this.$store.state.currentLang,lang,this.$refs.page);
            this.$utils.traverseFormValidator(this.rules,this.lang)
            }
        },
  data() {
    return {
      cannotEdit: true,
      dialogVisible: false,
      multipleSelection: [],
      parentOptions: [],
      searchForm: {},
      dialogTitle: "新增",
      tableData: [],
      parentTree: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      statusOptions: [
        {
          value: 1,
          label: "启用"
        },
        {
          value: 2,
          label: "禁用"
        }
      ],
      typeOptions: [
        {
          value: 1,
          label: "菜单"
        },
        {
          value: 2,
          label: "链接"
        },
        {
          value: 3,
          label: "按钮"
        }
      ],
      defaultProps: {
        children: "children",
        label: "label"
      },
      roleNamesArray: [],
      departmentOptions: [],
      reloadMasterFunctionName: "",
      //table上点击展开的那一行
      uploadData: {
        tree: null,
        treeNode: null,
        resolve: null
      },
      //表单数据
      ruleForm: {
        menu_url: null,
        menu_type: 1,
        menu_order: 0,
        menu_function_name: "",
        menu_icon: "",
        menu_is_enable: 1,
        menu_remark: "",
        parent: 1,
        menu_parent_id: ""
      },
      //表单验证规则
      rules: {
        menu_url: [
          {
            required: true,
            message: "请输入链接地址",
            trigger: "blur"
          },
          {
            min: 1,
            max: 30,
            message: "请输入1-30位字符",
            trigger: "blur"
          }
        ],
        menu_function_name: [
          {
            required: true,
            message: "请输入功能名称",
            trigger: "blur"
          }
        ],
        menu_icon: [
          {
            required: true,
            message: "请输入图标样式名称",
            trigger: "blur"
          }
        ],
        menu_remark: [
          {
            required: true,
            message: "请输入权限标识",
            trigger: "blur"
          }
        ],
        menu_parent_id: [
          {
            required: true,
            message: "请请选择父级",
            trigger: "change"
          }
        ]
      }
    };
  },
  mounted() {
    this.GetAll();
    this.GetPermissionList();
    this.btnInit();
  },
  methods: {
    //根据当前用户权限标识初始化按钮状态
    btnInit() {
      this.$Common.DistributePermission.call(this)
    },
    async GetPermissionList() {
      let params = {};
      let res = getPermissionList(params);
      if (res) {
        console.log("权限树：", res);
      }
    },
    //当checkbox发生变化时
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    clickRow(row) {
      this.$refs.pTable.toggleRowSelection(row);
    },
    menuTypeChange(val) {
      this.ruleForm.menu_parent_id = "";
      if (val === 2) {
        this.GetSystemMenuInfoSelectedList();
      }
      if (val === 3) {
        this.GetSystemMenuInfoTree();
      }
    },
    handleChange(value) {
      if (typeof value !== "undefined") {
        if (value.length === 2) {
          this.ruleForm.menu_parent_id = value[1];
          this.parentOptions.forEach((v, i, a) => {
            if (v.id === value[0]) {
              this.reloadMasterFunctionName = v.menu_function_name;
            }
          });
        }
      }
    },
    parentTreeOptionsClicked(obj, node) {
      this.multipleSelection[0] = node.data;
      this.resetForm();
      this.Get(this.multipleSelection[0]);
      this.cannotEdit = true;
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
      await this.$nextTick(() => {
        if (typeof this.$refs["dialogForm"] !== "undefined") {
          let defaultFormData = {
            menu_url: null,
            menu_type: 1,
            menu_order: 0,
            menu_function_name: "",
            menu_icon: "",
            menu_is_enable: 1,
            menu_remark: "",
            parent: 1,
            menu_parent_id: ""
          };
          this.ruleForm = Object.assign({}, {}, defaultFormData);
          this.$refs["dialogForm"].resetFields();
        }
      });
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
        this.tableData.forEach((v, i, a) => {
          v.hasChildren = true;
        });
      }
      this.$utils.traversePageDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.page);
      this.$utils.traverseFormValidator(this.rules,this.lang)
    },
    async load(tree, treeNode, resolve) {
      this.uploadData.tree = tree;
      this.uploadData.treeNode = treeNode;
      this.uploadData.resolve = resolve;
      let params = {
        parentId: tree.id,
        menu_type: tree.menu_type + 1
      };
      let data = await getSystemSubMenuInfoList(params);
      if (data) {
        data.forEach((v, i, a) => {
          v.masterMenu = tree.menu_function_name;
          if (v.menu_type < 3) v.hasChildren = true;
        });
        resolve(data);
      }
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
          ...this.ruleForm
        };
        let res = await addApi(params);
        if (res) {
          this.$message({
            type: "success",
            message: "新增成功"
          });
          this.GetAll();
          this.load(this.uploadData.tree, this.uploadData.treeNode, this.uploadData.resolve);
          this.resetForm();
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
    //点击启用按钮
    handleStart() {
      if (this.multipleSelection.length == 0) {
        //this.text_deleteRow  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_deleteRow);
      } else {
        let rows = this.multipleSelection;
        this.Start(rows);
      }
    },
    //点击禁用按钮
    handleForbidden() {
      if (this.multipleSelection.length == 0) {
        //this.text_deleteRow  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_deleteRow);
      } else {
        let rows = this.multipleSelection;
        this.Delete(rows);
      }
    },
    click_edit(row) {
      this.dialogTitle = "修改";
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
      this.dialogVisible = true;
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
        this.$utils.batcheHandle(
          rows,
          {
            id: "id"
          },
          deleteApi,
          "删除",
          this.GetAll
        );
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
          this.load(this.uploadData.tree, this.uploadData.treeNode, this.uploadData.resolve);
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
      if (res) {
        this.dialogVisible = true;
      } else {
        this.dialogVisible = false;
        this.$message({
          type: "error",
          message: "操作的记录不存在或者已被删除！"
        });
        return;
      }
      let obj = {
        id: row.id,
        menu_function_name: res.menu_function_name,
        menu_url: res.menu_url,
        menu_icon: res.menu_icon,
        menu_is_enable: res.menu_is_enable,
        menu_order: res.menu_order,
        menu_type: res.menu_type,
        menu_remark: res.menu_remark,
        menu_parent_id: res.menu_parent_id
      };
      this.ruleForm = obj;
      if (this.ruleForm.menu_type === 3) {
        this.GetSystemMenuInfoTree();
      }
      if (this.ruleForm.menu_type === 2) {
        this.GetSystemMenuInfoSelectedList();
      }
    },
    //批量启用
    Start(rows) {
      const h = this.$createElement;
      this.$msgbox({
        title: "提示",
        message: h("p", null, [
          h("span", null, "确定【"),
          h(
            "span",
            {
              style: this.c_danger
            },
            "启用"
          ),
          h("span", null, "】吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        this.$utils.batcheHandle(
          rows,
          {
            id: "id"
          },
          editApi,
          "启用",
          this.GetAll
        );
      });
    },
    async GetSystemMenuInfoTree() {
      let params = {};
      let res = await getSystemMenuInfoTree(params);
      if (res) {
        this.parentTree = res;
      }
    },
    async GetSystemMenuInfoSelectedList() {
      let params = {};
      let res = await getSystemMenuInfoSelectedList(params);
      if (res) {
        this.parentOptions = res;
      }
    },
    parentChanged(val) {
      if (val === 1) {
        this.ruleForm.menu_parent_id = null;
        this.rules = Object.assign({}, this.rules);
      } else {
        this.rules = Object.assign(
          {
            menu_parent_id: [
              {
                required: true,
                message: "请选择父级项",
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