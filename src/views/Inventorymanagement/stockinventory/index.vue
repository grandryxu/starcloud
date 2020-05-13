<template>
  <div class="iot-list" ref="page">
    <div class="iot-form">
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <div class="iot-form-row">
            <el-form-item label="库位编码">
              <el-input class="iot-w200" placeholder="请输入库位编码" maxlength="50" clearable v-model="searchForm.slot_code"></el-input>
            </el-form-item>
            <el-form-item label="托盘条码">
              <el-input class="iot-w200" placeholder="请输入托盘条码" maxlength="50" clearable v-model="searchForm.stock_code"></el-input>
            </el-form-item>
            <el-form-item label="所在仓库">
              <el-select class="iot-w200" v-model="searchForm.warehouse_id" placeholder="--所有--" clearable>
                <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="库存状态">
              <el-select class="iot-w200" v-model="searchForm.inventory_status" placeholder="--所有--" clearable>
                <el-option v-for="item in statusList" :key="item.id" :label="item.status_name" :value="item.id"></el-option>
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
        <el-table-column align="center" prop="slot_code" label="库位编码" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="stock_code" label="托盘条码" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="inventory_total" label="库存数" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="inventory_status" label="库位状态" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="imp_lock" label="入库锁定状态" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="exp_lock" label="出库锁定状态" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="imptime" :formatter="dateTimeTransform" label="入库日期" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="warehouse_name" label="所在仓库" min-width="100" show-overflow-tooltip></el-table-column>
      </el-table>
    </div>
    <div class="iot-pagination">
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage"></el-pagination>
    </div>
    <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
      <el-form inline ref="dialogForm" :model="ruleForm">
        <div class="iot-form-row">
          <el-form-item label="库位编码" label-width="100px" prop="slot_code">
            <el-input class="iot-w240" v-model="ruleForm.slot_code" placeholder="请输入库位编码" maxlength="50" readonly clearable></el-input>
          </el-form-item>
          <el-form-item label="托盘条码" label-width="100px" prop="stock_code">
            <el-input class="iot-w240" v-model="ruleForm.stock_code" readonly placeholder="请输入托盘编码" maxlength="50" clearable></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="库位状态" label-width="100px" prop="inventory_status">
            <el-input class="iot-w240" v-model="ruleForm.inventory_status" readonly placeholder="请输入库位状态" maxlength="50"  clearable></el-input>
          </el-form-item>
          <el-form-item label="入库日期" label-width="100px" prop="imptime">
            <el-input class="iot-w240" v-model="ruleForm.imptime" readonly  placeholder="请输入入库日期" maxlength="50"  clearable></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="所在仓库" label-width="100px" prop="warehouse_name">
            <el-input class="iot-w240" v-model="ruleForm.warehouse_name" readonly placeholder="请输入仓库" maxlength="50"  clearable></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <div style="text-align: center;font-size: 30px;padding: 10px 0px;">
            <span>物料包装</span>
          </div>
          <div class="iot-table">
            <el-table :data="tableData2" stripe style="width: 100%" border>  
              <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
              <el-table-column align="center" prop="good_code" label="物料编码" min-width="100" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" prop="good_name" label="物料名称" min-width="100" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" prop="bill_code" label="批号" min-width="100" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" prop="package_code" label="包装码" min-width="100" show-overflow-tooltip></el-table-column>
		          <el-table-column align="center" prop="number" label="数量" min-width="100" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" prop="unit" label="单位" min-width="100" show-overflow-tooltip></el-table-column>     
		          <el-table-column align="center" prop="status" label="质量状态" min-width="100" show-overflow-tooltip></el-table-column>
            </el-table>
          </div>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer" style="display:flex;justify-content: center;">
        <el-button size="small" type="danger" @click="dialogVisible = false"><span class="el-icon-close"></span>取 消</el-button>     
      </div>
    </el-dialog>
  </div>
</template>

<script>
import textConfig from "@/mixins/textConfig.js";
import singlePictureUpload from "_c/singlePictureUpload";
import { getListApi, addMainTaskApi, getOneApi } from "./api";
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
      statusList:[ {id:0,status_name:"空闲"}, {id:1,status_name: "有库存" },{ id: 2, status_name: "入库中" }, { id: 3, status_name: "出库中" }],
         tableData2:[],  
      warehouseList: [],
      multipleSelection: [],
      radio1: "",
      searchForm: {},
      dialogTitle: "新增",
      // tableData: [
      //   {
      //     material_code: "0001",
      //     material_name: "物料一",
      //     number: 12,
      //     danwei: "个"
      //   },
      //   {
      //     material_code: "0002",
      //     material_name: "物料二",
      //     number: 30,
      //     danwei: "包"
      //   }
      // ],
      tableData:[],
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
        unit_name: "",
        unit_is_enable: ""
      }
      //表单验证规则
      // rules: {
      // 	unit_name: [{
      // 		required: true,
      // 		message: "请输入单位名称",
      // 		trigger: "blur"
      // 	}],
      // 	unit_is_enable: [{
      // 		required: true,
      // 		message: "请选择启用状态",
      // 		trigger: "blur"
      // 	}]
      // }
    };
  },
  mounted() {
    this.GetAll();
    this.GetAreaList();
    this.GetWarehouseList();
    this.btnInit();
  },
  methods: {
    //根据当前用户权限标识初始化按钮状态
    btnInit() {
      this.$Common.DistributePermission.call(this)
    },
    dateTimeTransform(row) {
    let time = row.imptime;
    return this.$moment(time).format('YYYY-MM-DD hh:mm:ss')
   },
    //表单重置
    async resetForm() {
      this.dialogVisible = true;
      await this.$nextTick();
      this.$refs["areaForm"].resetFields();
    },
    //仓库列表
    async GetWarehouseList() {
      let data = await this.$DropBox.getWarehouselist();
      if (data) {
        this.warehouseList = data || [];
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
      				if(data){
      					this.tableData = data.items || [];
      					this.total = data.totalCount || 0;
      					this.multipleSelection=[];
      				}        
      this.multipleSelection = [];
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
      this.multipleSelection = rows.filter(ele => {
        return ele.inventory_status == 1;
      });
      if (this.multipleSelection.length != rows.length) {
        this.isCanselect = true;
      }
      if (this.multipleSelection.length === 0) {
        this.$message({ message: message, type: "warning" });
        this.isCanselect = false;
        this.GetAll();
        return;
      } else {
        callback(this.multipleSelection);
      }
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
          let codeList = [];
          this.isCanselect = false;
          rows.forEach(ele => {
            codeList.push(ele.slot_id);
          });
          let res = await addMainTaskApi(codeList);
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

    click_edit(row) {
      this.dialogTitle = "查阅";
      this.resetForm();
      this.Get(row);
    },
    click_search() {
      this.currentPage = 1;
      this.GetAll();
    },

    //点击保存
    click_submit() {
      if (this.dialogTitle == "查看") {
        this.dialogVisible = false;
        return;
      }
      this.$refs["areaForm"].validate(valid => {
        if (!valid) {
          return;
        }
        let parmas = this.multipleSelection[0];

        this.Update(parmas, 3);
      });
    },

    //编辑
    Update(row, val) {
      alert(111);
    },
    //根据主键获取信息
    async Get(row) {
		     let obj = {   
          slot_code: row.slot_code,
          stock_code: row.stock_code,
          inventory_status: row.inventory_status,
          imptime: row.imptime,
         warehouse_name:row.warehouse_name 
      };
	  this.ruleForm = obj;
	  // this.tableData2=[{slot_code:'0001',stock_code:"0212020",bill_code:"ph0001",package_code:"bzm0001",number:3131,imptime:"2020-02-02",status:"合格"},{slot_code:'0002',stock_code:"0313030",bill_code:"ph0002",package_code:"bzm0002",number:3131,imptime:"2020-02-02",status:"不合格"},{slot_code:'0001',stock_code:"0212020",bill_code:"ph0001",package_code:"bzm0001",number:3131,imptime:"2020-02-02",status:"合格"},{slot_code:'0002',stock_code:"0313030",bill_code:"ph0002",package_code:"bzm0002",number:3131,imptime:"2020-02-02",status:"不合格"}]
      let params = { 
        slot_code:row.slot_id,
        stock_code:row.stock_code,
        warehouseid:row.warehouseid
       };
      let res = await getOneApi(params);
     this.tableData2=res;
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
