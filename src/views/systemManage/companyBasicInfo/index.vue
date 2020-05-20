<template>
  <div class="iot-list" ref="page">
    <div class="iot-form">
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <el-form-item label="公司名称">
            <el-input class="iot-w200" placeholder v-model="searchForm.Name" @change="click_search" clearable></el-input>
          </el-form-item>
          <el-form-item label="负责人">
            <el-input class="iot-w200" placeholder v-model="searchForm.ManagerName" @change="click_search" clearable></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="treearea">
        <el-row :gutter="20">
          <el-col :span="6">
            <div class="lay">
              <div class="filterBar">
                <el-input class="iot-w200" placeholder="输入关键字进行过滤" v-model="filterText" clearable></el-input>
              </div>
              <el-tree class="filter-tree" :data="parentCompanyTree" :props="defaultProps" accordion :filter-node-method="filterNode" ref="tree" @current-change="parentCompanyOptionsClicked" :render-content="renderContent"></el-tree>
            </div>
          </el-col>
          <el-col :span="18">
            <div class="lay">
              <div class="btns" ref="permissions">
                <el-button plain icon="el-icon-plus" @click="click_add">新增</el-button>
                <el-button plain icon="el-icon-edit-outline" @click="handleEdit">编辑</el-button>
                <el-button plain icon="el-icon-delete" @click="handleDelete">删除</el-button>
              </div>
              <el-form ref="dialogForm" :model="ruleForm" :rules="rules" :disabled="cannotEdit">
                <el-form-item label="公司名称" label-width="100px" prop="name">
                  <el-input class="iot-w240" v-model="ruleForm.name" placeholder="请输入公司名称" clearable maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="负责人" label-width="100px" prop="managerName">
                  <el-input class="iot-w240" v-model="ruleForm.managerName" placeholder="请输入负责人" clearable maxlength="30"></el-input>
                </el-form-item>
                <el-form-item label="父级" label-width="100px" prop="parentCompany">
                  <el-radio-group v-model="ruleForm.parentCompany" @change="parentCompanyChanged">
                    <el-radio-button :label="1">是</el-radio-button>
                    <el-radio-button :label="2">否</el-radio-button>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="父公司" label-width="100px" prop="parentId" v-if="ruleForm.parentCompany === 2">
                  <el-select v-model="ruleForm.parentId" placeholder="请选择" class="iot-min-w240">
                    <el-option v-for="item in parentCompanyOptions" :key="item.id" :label="item.name" :value="item.id"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="地址" label-width="100px" prop="address">
                  <el-cascader class="iot-min-w240" v-model="ruleForm.address" :options="divisions" :props="props" clearable></el-cascader>
                </el-form-item>
                <el-form-item label="详细地址" label-width="100px" prop="addressDetail">
                  <el-input class="iot-w240" v-model="ruleForm.addressDetail" placeholder="详细地址" clearable maxlength="30"></el-input>
                </el-form-item>
                <el-form-item label="备注" label-width="100px" prop="remark">
                  <el-input class="iot-w240" v-model="ruleForm.remark" type="textarea" :rows="5" show-word-limit :maxlength="200"></el-input>
                </el-form-item>
                <el-form-item label-width="100px">
                  <el-button size="small" type="primary" @click="click_submit">保 存</el-button>
                </el-form-item>
              </el-form>
            </div>
          </el-col>
        </el-row>
      </div>
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
  getSelectedParentCompanyList,
  getCompanyTreeList
} from "./api";
import Divisions from "@/utils/chinaDivisions.js";
import { Loading } from "element-ui";
let myLodading;
export default {
  //全局混入提示文字
  mixins: [textConfig],
  components: {
    singlePictureUpload
  },
  props: ["lang"],
  data() {
    return {
      cannotEdit: true,
      canSubmit: false,
      parentCompanyTree: [],
      multipleSelection: [],
      parentCompanyOptions: [],
      filterText: "",
      searchForm: {},
      dialogTitle: "新增",
      tableData: [],
      productList: [
        {
          id: 1,
          salesName: "测试"
        }
      ],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      divisions: [],
      defaultProps: {
        children: "children",
        label: "label"
      },
      props: {
        value: "name",
        label: "name",
        children: "children"
      },
      //表单数据
      ruleForm: {
        name: "",
        shortName: "",
        managerName: "",
        remark: "",
        addressDetail: "",
        address: "",
        parentCompany: 1,
        parentId: ""
      },
      //表单验证规则
      rules: {
        name: [
          {
            required: true,
            message: "请输入公司名称",
            trigger: "blur"
          },
          {
            min: 1,
            max: 30,
            message: "请输入1-30位字符",
            trigger: "blur"
          }
        ],
        managerName: [
          {
            required: true,
            message: "请输入负责人",
            trigger: "blur"
          }
        ],
        /*address: [
          {
            required: true,
            message: "请选择行政区域",
            trigger: "change"
          }
        ],
        addressDetail: [
          {
            required: true,
            message: "请输入详细地址",
            trigger: "blur"
          }
        ]*/
      }
    };
  },
  mounted() {
    this.btnInit();
    this.GetCompanyTreeList();
    this.GetSelectedParentCompanyList();
    this.divisions = Divisions;
    this.$utils.traversePageDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.page);
    this.$utils.traverseFormValidator(this.rules,this.lang)
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
     lang: function(lang) {
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
      this.multipleSelection[0] = node.data;
      this.resetForm();
      this.Get(node.data);
      this.cannotEdit = true;
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    indexMethod(index) {
      return this.pageSize * (this.currentPage - 1) + index + 1;
    },
    clickRow(row) {
      this.$refs.pTable.toggleRowSelection(row);
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
      //当表单信息量比较大时需要添加宿主任务以强制loading提前加载
      setTimeout(async () => {
        //this.dialogVisible = true;
        await this.$nextTick(() => {
          if (typeof myLodading !== "undefined") myLodading.close();
        });
        this.$refs["dialogForm"].resetFields();
        this.ruleForm.address = "";
        this.ruleForm.parentId = null;
      }, 100);
      this.$utils.traverseDialogDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.dialogForm);
    },
    //列表
    async GetAll() {
      // let params = {
      // 	MaxResultCount: this.pageSize,
      // 	SkipCount: (this.currentPage - 1) * this.pageSize,
      // 	...this.searchForm
      // };
      // this.tableData = [];
      // let data = await getListApi(params);
      // if (data) {
      // 	this.tableData = data.items || [];
      // 	this.total = data.totalCount || 0;
      // }
      // this.GetCompanyTreeList();
      // this.GetSelectedParentCompanyList();
      this.GetCompanyTreeList();
      this.GetSelectedParentCompanyList();
    },
    async GetSelectedParentCompanyList() {
      let params = {};
      let data = await getSelectedParentCompanyList(params);
      if (data) {
        this.parentCompanyOptions = data;
      }
      this.resetForm();
    },
    async GetCompanyTreeList() {
      let params = {
        ...this.searchForm
      };
      let data = await getCompanyTreeList(params);
      if (data) {
        this.parentCompanyTree = data;
      }
      this.resetForm();
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
        this.$utils.arrayAndStringTransform(params, "address");
        let res = await addApi(params);
        if (res) {
          this.$message({
            type: "success",
            message: "新增成功"
          });
          this.GetAll();
          this.dialogVisible = false;
          this.cannotEdit = true;
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
	  this.dialogTitle = "修改";
      this.resetForm();
	  this.Get(row);
    this.cannotEdit = false;
    },
    click_search() {
      this.currentPage = 1;
      this.GetAll();
    },
    click_add() {
      this.dialogTitle = "新增";
      this.cannotEdit = false;
      myLodading = Loading.service({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)"
      });
      this.GetSelectedParentCompanyList();
      this.resetForm();
    },
    //点击保存
    click_submit() {
      console.log(this.dialogTitle);
      if (this.dialogTitle == "查看") {
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
    async Delete(rows) {
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
            "保存更新"
          ),
          h("span", null, "吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        let params = {
          ...this.ruleForm
        };
        this.$utils.arrayAndStringTransform(params, "address");
        let res = await editApi(params);
        if (res) {
          this.$message({
            type: "success",
            message: "修改成功"
          });
          this.GetAll();
          this.cannotEdit = true;
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
        name: res.name,
        managerName: res.managerName,
        remark: res.remark,
        addressDetail: res.addressDetail,
        address: res.address,
        parentId: res.parentId
      };
      this.$utils.arrayAndStringTransform(obj, "address");
      if (obj.parentId !== null || obj.parentId === "") obj.parentCompany = 2;
      else obj.parentCompany = 1;
      this.ruleForm = obj;
      this.parentCompanyOptions.forEach((v, i, a) => {
        v.disabled = v.id === obj.id;
      });
    },
    parentCompanyChanged(val) {
      if (val === 1) {
        this.ruleForm.parentId = null;
        this.rules = Object.assign({}, this.rules);
      } else {
        this.rules = Object.assign(
          {
            parentId: [
              {
                required: true,
                message: "请选择父级公司",
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
  border-right: 1px solid #ebeef5;
  padding: 0 20px;
}

.btns,
.filterBar {
  margin: 20px;
}
</style>