<template>
  <div class="iot-list" ref="page">
    <div class="iot-form">
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <el-form-item label="角色名称">
            <el-input class="iot-w200" placeholder v-model="searchForm.name" @change="click_search" clearable></el-input>
          </el-form-item>
          <el-form-item label="启用状态">
            <el-select class="iot-w200" v-model="searchForm.isEnable" placeholder="全部" @change="click_search" clearable>
              <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
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
          <el-button type="primary" plain icon="el-icon-delete" @click="handleDelete">删除</el-button>
          <el-button type="primary" plain icon="el-icon-circle-check" @click="handleStart">启用</el-button>
          <el-button type="primary" plain icon="el-icon-warning-outline" @click="handleForbidden">禁用</el-button>
          <!-- <el-button type="primary" plain icon="el-icon-warning-outline" @click="getPermissions">禁用低调点</el-button> -->
        </div>
      </div>
    </div>
    <div class="iot-table">
      <el-table :data="tableData" stripe style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
        <el-table-column align="center" prop="name" label="角色名称" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="isEnable" label="角色类型" min-width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.roleType == 1">Web角色</span>
            <span v-else-if="scope.row.roleType == 2">Api角色</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="isEnable" label="启用状态" min-width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.isEnable == 1">启用</span>
            <span v-else-if="scope.row.isEnable == 2">禁用</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="creationTime" :formatter="dateTimeTransform" label="创建时间" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="description" label="备注" min-width="100" show-overflow-tooltip></el-table-column>
      </el-table>
    </div>
    <div class="iot-pagination">
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
    </div>

    <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="1004px" append-to-body>
      <el-form ref="dialogForm" :model="ruleForm" :rules="rules" inline>
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="基础信息" name="first">
            <el-form-item :label="$t('roleInfo.roleName')" label-width="100px" prop="name">
              <el-input class="iot-w240" v-model="ruleForm.name" :placeholder="$t('roleInfo.roleName')" maxlength="50" clearable></el-input>
            </el-form-item>
            <el-form-item :label="$t('materialUnit.enabled')" label-width="100px" prop="isEnable">
              <el-switch class="iot-w240" v-model="ruleForm.isEnable" active-color="#13ce66" inactive-color="#ff4949" :active-value="1" :inactive-value="2"></el-switch>
            </el-form-item>
            <el-form-item :label="$t('roleInfo.roleType')" label-width="100px" prop="roleType">
              <el-select class="iot-w240" v-model="ruleForm.roleType" :placeholder="$t('import.pleaseselect')">
                <el-option v-for="item in roleTypes" :key="item.id" :label="item.desc" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item :label="$t('customTypeInfo.note')" label-width="100px" prop="description">
              <el-input class="iot-w240" v-model="ruleForm.description" type="textarea" clearable :rows="5" maxlength="200" show-word-limit></el-input>
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="角色权限" name="second">
            <el-form-item label-width="100px" prop="grantedPermissions">
              <span class="custom-tree-node" style="font-weight:bold;">
                <span style="min-width:200px;text-align:center;margin:0">名称</span>
                <span style="min-width:60px;text-align:center;">图标</span>
                <span style="min-width:160px;text-align:center;">链接</span>
                <span style="min-width:60px;text-align:center;">类型</span>
                <span style="min-width:300px;text-align:center;">权限标识</span>
                <!-- <span style="min-width:90px;text-align:center;">sortId</span> -->
              </span>
              <el-tree :data="tree" show-checkbox node-key="sortId" :default-checked-keys="grantedPermissionNames" :props="defaultProps" ref="tree" :render-content="renderContent" @check="permissionCheck">
              </el-tree>
              <div class="buttons grid-content">
                <el-button @click="resetChecked" type="danger">清空</el-button>
              </div>
            </el-form-item>
          </el-tab-pane>
        </el-tabs>
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
import Utils from "@/utils/index";
import {
  getListApi,
  addApi,
  getOneApi,
  editApi,
  deleteApi,
  updateRolePermissions,
  getPermissionList,
  getRoleForEdit
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
      activeName: "first",
      multipleSelection: [],
      tree: [],
      plist: [],
      searchForm: {},
      dialogTitle: "新增",
      tableData: [],
      defaultProps: {
        children: "children",
        label: "label"
      },
      currentPage: 1,
      roleTypes:[{
        id:1,
        desc:"Web"
      },
      {id:2,
      desc:"Api"}],
      pageSize: 10,
      total: 0,
      props: {
        value: "id",
        label: "label",
        children: "children",
        multiple: true
      },
      dialogVisible: false,
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
      grantedPermissionsList: [],
      grantedPermissionNames: [],
      forbidden: false,
      //表单数据
      ruleForm: {
        name: "",
        limit: "",
        isEnable: 1,
        description: "",
        roleType:"",
        displayName: "",
        normalizedName: "222",
        grantedPermissions: [],
        creationTime: ""
      },
      //表单验证规则
      rules: {
        displayName: [
          {
            required: true,
            message: this.$t('roleInfo.roleName'),
            trigger: "blur"
          },
          {
            min: 1,
            max: 30,
            message: "请输入1-30位字符",
            trigger: "blur"
          }
        ],
        grantedPermissions: [
          {
            required: true,
            message: this.$t('roleInfo.roleAuthorization'),
            trigger: "blur"
          }
        ],
        isEnable: [
          {
            required: true,
            message: "请选择启用状态",
            trigger: "change"
          }
        ],
        name: [
          {
            required: true,
            message: "请输入角色名称",
            trigger: "blur"
          }
        ]
      }
    };
  },
  mounted() {
    this.btnInit();
    this.GetAll();
    this.GetPermissionList();
  },
  methods: {

    //根据当前用户权限标识初始化按钮状态
    btnInit() {
      this.$Common.DistributePermission.call(this)
    },
    permissionCheck(obj, chc) {
      console.log("chc = ", chc);
      let p = [];
      chc.checkedNodes.forEach((v, i, a) => {
        if (v.permission) {
          p.push(v.permission);
        }
      });
      this.ruleForm.grantedPermissions = p;
    },
    handleConnectionSearch(row) {
      this.$refs.permissionTable.toggleRowExpansion(row, true);
      this.$refs.permissionTable.toggleRowSelection(row, true);
    },
    getCheckedNodes() {
      console.log(this.$refs.tree.getCheckedNodes());
    },
    resetChecked() {
      this.$refs.tree.setCheckedKeys([]);
    },
    async GetPermissionList() {
      let params = {};
      let res = await getPermissionList(params);
      if (res) {
        console.log("权限树：", res);
        this.tree = res;
      }
    },
    //远程获取权限
    async getPermissions() {
      let res = await this.$DropBox.getAllPermissions();
      if (res) {
        this.grantedPermissionsList = res;
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
      this.activeName = 'first'
      await this.$nextTick();
      this.$refs["dialogForm"].resetFields();
    },
    //列表
    async GetAll() {
      let params = {
        MaxResultCount: this.pageSize,
        SkipCount: (this.currentPage) * this.pageSize,
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
      //新增 获取半选中的节点
      let half_keys = this.$refs.tree.getHalfCheckedKeys();
      if (half_keys) {
        this.tree.forEach((item, index, obj) => {
          if (half_keys.indexOf(item.sortId) != -1) {
            this.ruleForm.grantedPermissions.push(item.permission);
          }
          if (Array.isArray(item.children)) {
            item.children.forEach((it, i, arr) => {
              if (half_keys.indexOf(it.sortId) != -1) {
                this.ruleForm.grantedPermissions.push(it.permission);
              }
            });
          }
        });
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
        this.ruleForm.displayName = this.ruleForm.name;
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
        let rows = this.multipleSelection;
        this.Delete(rows);
      }
    },
    //点击启用按钮
    handleStart() {
      if (this.multipleSelection.length == 1) {
        this.forbidden = false;
        this.click_enableOrForbidden(this.multipleSelection[0]);
      } else {
        //this.text_selectOne  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_selectOne);
      }
    },
    //点击禁用按钮
    handleForbidden() {
      if (this.multipleSelection.length == 1) {
        this.forbidden = true;
        this.click_enableOrForbidden(this.multipleSelection[0]);
      } else {
        //this.text_selectOne  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_selectOne);
      }
    },
    click_edit(row) {
      this.dialogTitle = "编辑";
      this.resetForm();
      this.GetPermissionList();
      this.Get(row);
    },
    async click_enableOrForbidden(row) {
      // await this.resetForm();
      this.dialogVisible = false;
      await this.Get(row);
      await this.UpdateStatus()
    },
    click_search() {
      this.currentPage = 1;
      this.GetAll();
    },
    click_add() {
      this.dialogTitle = "新增";
      this.resetForm();
      //this.resetChecked();
      this.GetPermissionList();
    },
    //点击保存
    click_submit() {
      if (this.dialogTitle == "查看") {
        this.dialogVisible = false;
        return;
      } else if (!this.$refs["dialogForm"].model.name) {
        this.$message({
          message: '请输入角色名称',
          type: 'warning'
        })
        this.activeName = 'first'
      } else if (this.$refs["dialogForm"].model.grantedPermissions.length === 0) {
        this.$message({
          message: '请先勾选系统管理中心中的公司或者部门权限',
          type: 'warning'
        })
        this.activeName = 'second'
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
    handleClick(value){
      console.log(value)
    },
    //根据主键删除
    Delete(rows) {
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
            "删除"
          ),
          h("span", null, "】吗？")
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
      //更新 获取半选中的节点
      let half_keys = this.$refs.tree.getHalfCheckedKeys();
      if (half_keys) {
        this.tree.forEach((item, index, obj) => {
          if (half_keys.indexOf(item.sortId) != -1) {
            this.ruleForm.grantedPermissions.push(item.permission);
          }
          if (Array.isArray(item.children)) {
            item.children.forEach((it, i, arr) => {
              if (half_keys.indexOf(it.sortId) != -1) {
                this.ruleForm.grantedPermissions.push(it.permission);
              }
            });
          }
        });
      }
      // console.log(this.ruleForm.grantedPermissions);
      // return;
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
        }
        let res = await editApi(params);
        if (res) {
          let user = Utils.getStorage("userInfo");
          let pra = {
            RoleId: this.ruleForm.id,
            GrantedPermissionNames: this.ruleForm.grantedPermissions
          };
          //  alert(JSON.stringify(pra));
          let response = await updateRolePermissions(pra);
          this.forbidden = false;
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
    UpdateStatus() {
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
            this.forbidden ? "禁用" : "启用"
          ),
          h("span", null, "吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        let params = {
          ...this.ruleForm
        };
        let message = '';
        if (this.forbidden) {
          params.isEnable = 2
          message = '禁用成功';
        } else {
          params.isEnable = 1;
          message = '启用成功';
        }
        let res = await editApi(params);
        if (res) {
          this.forbidden = false;
          this.$message({
            type: "success",
            message: message
          });
          this.GetAll();
        }
      });
    },
    //根据主键获取信息
    async Get(row) {
      let params = {
        id: row.id
      };
      let res = await getOneApi(params);
      console.log("this.tree = ", this.tree);
      let obj = {
        id: row.id,
        name: res.name,
        limit: res.limit,
        isEnable: res.isEnable,
        description: res.description,
        displayName: res.displayName,
        normalizedName: res.normalizedName,
        description: res.description,
        grantedPermissions: res.grantedPermissions,
        creationTime: res.creationTime,
        roleType: res.roleType
      };
      this.ruleForm = obj;
      this.grantedPermissionNames = [];
      this.tree.forEach((v, i, a) => {
        if (Array.isArray(v.children)) {
          v.children.forEach((vv, ii, aa) => {
            // if (obj.grantedPermissions.indexOf(vv.permission) !== -1) {
            //   console.log(vv.permission);
            //   this.grantedPermissionNames.push(vv.sortId);
            // }
            if (Array.isArray(vv.children)) {
              vv.children.forEach((vvv, iii, aaa) => {
                if (obj.grantedPermissions.indexOf(vvv.permission) !== -1) {
                  console.log(vvv.permission);
                  this.grantedPermissionNames.push(vvv.sortId);
                }
              });
            }
          });
        }
      });
      console.log("grantedPermissionNames = ", this.grantedPermissionNames);
    },
    //日期时间转换
    dateTimeTransform(row) {
      let time = row.creationTime;
      return this.$moment(time).format("YYYY-MM-DD hh:mm:ss");
    },
    renderContent(h, { node, data, store }) {
      return h(
        "span",
        {
          class: "custom-tree-node"
        },
        [
          h("span", { style: "min-width:153px;text-align:center;" }, node.data.label),
          h("span", { style: "min-width:60px;text-align:center;" }, [
            h("i", {
              class: node.data.icon
            })
          ]),
          h("span", { style: "width:160px;overflow:hidden;text-align:center;" }, node.data.link),
          h(
            "span",
            { style: "min-width:60px;text-align:center;" },
            node.data.menuType > 2
              ? "按钮"
              : node.data.menuType === 1
                ? "菜单"
                : "链接"
          ),
          h(
            "span",
            { style: "width:300px;overflow:hidden;text-align:center;" },
            node.data.permission
          )
          // h( "span",
          //   { style: "width:90px;overflow:hidden;text-align:center;" },
          //   node.data.sortId)
        ]
      );
    }
  }
};
</script>
<style>
.grid-content {
  margin: 20px;
}
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  /* justify-content: space-between; */
  font-size: 14px;
}
</style>