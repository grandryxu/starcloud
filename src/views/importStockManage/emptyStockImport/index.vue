<template>
  <div class="iot-list" ref="page">
    <div class="iot-form">
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <div class="iot-form-row">
            <el-form-item label="托盘码">
              <el-input class="iot-w200" placeholder="请输入" maxlength="50" v-model="searchForm.impstock_stock_code" clearable></el-input>
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
          <el-button type="primary" plain icon="el-icon-edit" @click="handleMaintask">生成任务</el-button>
          <el-button type="primary" plain icon="el-icon-document" @click="exportExcel">导出</el-button>
          <el-button type="primary" plain icon="el-icon-printer" @click="printTable">打印</el-button>
        </div>
      </div>
    </div>
    <div class="iot-table">
    <el-table id="out-table" ref="print" :data="tableData" stripe style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
      <el-table-column align="center" prop="impstock_stock_code" label="托盘码" min-width="100" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" prop="impstock_warehouse_id" label="仓库" min-width="100" show-overflow-tooltip :formatter="warehouseShow"></el-table-column>
      <el-table-column align="center" prop="slot.slot_code" label="库位" min-width="100" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" prop="impstock_quantity" label="托盘数量" min-width="100" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" prop="impstock_execute_flag" label="执行状态" min-width="100" show-overflow-tooltip>
        <template slot-scope="scope">
          <span v-if="scope.row.impstock_execute_flag === 0">未生成</span>
          <span v-if="scope.row.impstock_execute_flag === 1">待执行</span>
          <span v-else-if="scope.row.impstock_execute_flag === 2">执行中</span>
          <span v-else-if="scope.row.impstock_execute_flag === 3">已完成</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="goods.goods_code" label="物资编码" min-width="100" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" prop="goods.goods_name" label="物资名称" min-width="100" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" prop="creationTime" label="创建时间" :formatter="dateTimeTransform" min-width="100" show-overflow-tooltip></el-table-column>
      <!-- <el-table-column align="center" prop="impstock_is_enable" label="状态" min-width="100" show-overflow-tooltip>
         <template slot-scope="scope">
           <span v-if="scope.row.impstock_is_enable === 1">启用</span>
           <span v-else-if="scope.row.impstock_is_enable === 2">禁用</span>
         </template>
       </el-table-column>-->
      <!--<el-table-column align="center" prop="unit_is_enable" label="打印日期" min-width="100" show-overflow-tooltip></el-table-column>
      <el-table-column align="center" prop="impstock_remark" label="备注" min-width="100" show-overflow-tooltip></el-table-column>-->
    </el-table>
  </div>
    <div class="iot-pagination">
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
    </div>
    <!---新增托盘流水-->
    <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
      <el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
        <div class="iot-form-row">
          <el-form-item :label="$t('public.stockcode')" label-width="100px" prop="impstock_stock_code">
            <el-input class="iot-w240" v-model="ruleForm.impstock_stock_code" :placeholder="$t('import.stocknull')" maxlength="8" clearable></el-input>
          </el-form-item>
          <el-form-item :label="$t('public.warehouse')" label-width="100px" prop="impstock_warehouse_id">
            <el-select class="iot-w240" v-model="ruleForm.impstock_warehouse_id" :placeholder="$t('public.select')">
              <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item :label="$t('public.slot')" label-width="100px" prop="impstock_slot_code">
            <el-input class="iot-w240" v-model="ruleForm.impstock_slot_code" :placeholder="$t('import.slotnull')" clearable    ></el-input>
          </el-form-item>
          <el-form-item :label="$t('public.quantity')" label-width="100px" prop="impstock_quantity">
            <el-input type="number" class="iot-w240" v-model="ruleForm.impstock_quantity" :placeholder="$t('import.quantitynull')" maxlength="20" clearable></el-input>
          </el-form-item>
          <!-- <el-form-item label="状态" label-width="100px" prop="impstock_is_enable" >
            <el-select class="iot-w240" v-model="ruleForm.impstock_is_enable" placeholder="请选择">
              <el-option v-for="item in isEnableList" :key="item.id" :label="item.enableName" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="备注" label-width="100px" prop="impstock_remark" >
            <el-input class="iot-w240" type="textarea" :rows="4" v-model="ruleForm.impstock_remark" clearable></el-input>
          </el-form-item>-->
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">{{ $t('permission.cancel') }}</el-button>
        <el-button size="small" type="primary" @click="click_submit">{{ $t('permission.confirm') }}</el-button>
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
  checkDuplicateApi,
  addMainTaskApi,
  GetSlotCodeIdApi,
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
      warehouseList: [],
      soltList: [],
      multipleSelection: [],
      radio1: "",
      searchForm: {},
      dialogTitle: "新增",
      tableData: [],
      isEnableList: [
        { id: 1, enableName: "启用" },
        { id: 2, enableName: "禁用" }
      ],
      is_duplicate_stock_code: false,
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      //表单数据
      ruleForm: {
        impstock_remark: "",
        impstock_quantity: "",
        impstock_stock_code: "",
        impstock_is_enable: 1,
        impstock_slot_code: "",
        impstock_warehouse_id: ""
      },
      //表单验证规则
      rules: {
        impstock_stock_code: [
          {
            required: true,
            message: "请输入托盘号",
            trigger: "blur"
          }
        ],
        impstock_quantity: [
          {
            required: true,
            message: "请输入数量",
            trigger: "blur"
          }
        ],
        // impstock_slot_code: [
        //   {
        //     required: true,
        //     message: "请输入库位号",
        //     trigger: "blur"
        //   }
        // ],
        impstock_warehouse_id: [
          {
            required: true,
            message: "请选择仓库",
            trigger: "blur"
          }
        ],
        checkRes:'',
       /* impstock_is_enable: [
          {
            required: true,
            message: "请选择状态",
            trigger: "blur"
          }
        ]*/
      }
    };
  },
  mounted() {
    this.GetAll();
    this.GetWarehouseList();
    this.GetSlotList();
    this.btnInit();
  },
  methods: {
    //日期时间转换
    dateTimeTransform(row) {
      let time = row.creationTime;
      return this.$moment(time).format('YYYY-MM-DD hh:mm:ss')
    },
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
      this.$utils.traverseDialogDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.dialogForm);
    },
    //仓库列表
    async GetWarehouseList() {
      let data = await this.$DropBox.getWarehouselist();
      if (data) {
        this.warehouseList = data || [];
      }
    },
    //库位列表
    async GetSlotList() {
      let data = await this.$DropBox.getSlotList();
      if (data) {
        this.slotList = data || [];
      }
    },
    //判断托盘是否重复
    async checkDuplicate() {
      let params = {
        ...this.ruleForm
      };
      let res = await checkDuplicateApi(params);
      if (res && res.length > 0) {
        this.is_duplicate_stock_code = true;
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
      this.$utils.traversePageDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.page);
      this.$utils.traverseFormValidator(this.rules,this.lang)
    },
    //生成任务
    async handleMaintask() {
      if (this.multipleSelection.length == 0) {
        //this.text_deleteRow  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_deleteRow);
      } else {
        let rows = this.multipleSelection;
        this.filterRows(rows, "没有可生成的任务", this.CreateMainTask);
      }
    },
    //筛选是否可以操作的行
    filterRows(rows, message, callback) {
      // this.multipleSelection = rows.filter(ele => {
      //   return ele.imphead_execute_flag == 1;
      // });
      // if (this.multipleSelection.length != rows.length) {
      //   this.isCanselect = true;
      // }
      // if (this.multipleSelection.length === 0) {
      //   this.$message({ message: message, type: "warning" });
      //   this.isCanselect = false;
      //   this.GetAll();
      //   return;
      // } else {
      //   callback(this.multipleSelection);
      // }
      callback(this.multipleSelection);
    },
    //创建流水任务
    CreateMainTask(rows) {
      const h = this.$createElement;
      this.$msgbox({
        title: "提示",
        message: h("p", null, [
          this.isCanselect &&
            h(
              "span",
              {
                style: this.c_danger
              },
              "【红色部分不可生成任务】"
            ),
          h("span", null, "确定【"),
          h(
            "span",
            {
              style: this.c_primary
            },
            "生成任务"
          ),
          h("span", null, "】吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(
        async action => {
          let idList = [];
          this.isCanselect = false;
          rows.forEach(ele => {
            idList.push(ele.id);
          });
          let res = await addMainTaskApi(idList);
          if (res) {
            this.$message({
              type: "success",
              message: "生成任务成功"
            });
            this.GetAll();
          }
        },
        cancel => {
          this.isCanselect = false;
          this.GetAll();
        }
      );
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
        params.impstock_slot_code=this.checkRes;
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
      if(row.impstock_execute_flag != 0){
          this.$message.warning("已经生成任务，不可编辑");
          return;
      }
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
    async click_submit() {
      // await this.checkDuplicate();
      // if (this.is_duplicate_stock_code) {
      //   this.$message.error("托盘号码重复，请重新输入");
      //   return;
      // }
      if (this.dialogTitle == "查看") {
        this.dialogVisible = false;
        return;
      }
      this.$refs["dialogForm"].validate(valid => {
        if (!valid) {
          return;
        }
        if(this.ruleForm.impstock_slot_code){
          this.checkExsit();
        }
        else
        {
          if (this.dialogTitle == "新增") {
            this.Create();
          } else {
            this.Update();
          }
        }







      });
    },
    //查询该仓库下的库位是否存在
    async checkExsit() {
      let params = {
        slot_code: this.ruleForm.impstock_slot_code,
        slot_warehouse_id:this.ruleForm.impstock_warehouse_id
      };
      let res = await GetSlotCodeIdApi(params);
      if(res)
      {
         let num=res.split("@")[0];
         let result=res.split("@")[1];
         if(num=="0")
         {
           this.$message({
             message: result,
             type: 'warning'
           });
           return
         }
         else
         {
           this.checkRes=result;


           if (this.dialogTitle == "新增") {
             this.Create();
           } else {
             this.Update();
           }
         }
      }


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
        params.impstock_slot_code=this.checkRes;
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
console.log(row)
      let params = {
        id: row.id
      };

      let res = await getOneApi(params);
      let obj = {
        id: row.id,
        impstock_remark: res.impstock_remark,
        impstock_quantity: res.impstock_quantity,
        impstock_stock_code: res.impstock_stock_code,
        impstock_is_enable: res.impstock_is_enable,
        impstock_slot_code: row.slot!=null ? row.slot.slot_code : '',
        impstock_warehouse_id: res.impstock_warehouse_id
      };
      this.ruleForm = obj;

    },
    //仓库ID和name的转换
    warehouseShow(row) {
      let id = row.impstock_warehouse_id;
      let name;
      this.warehouseList.forEach(ele => {
        if (ele.id === id) {
          name = ele.warehouse_name;
        }
      });
      return name;
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