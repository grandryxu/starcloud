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
            <el-form-item label="入库日期">
              <el-date-picker
                class="iot-w280"
                clearable
                v-model="searchForm.startDate"
                type="daterange"
                range-separator="-"
              ></el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button
                class="search__btn"
                type="primary"
                icon="el-icon-search"
                @click="click_search"
              >查询</el-button>
            </el-form-item>
          </div>
          <!-- <div class="iot-form-row">   
              <el-form-item label="总库位数">
              <el-input class="iot-w200" placeholder readonly v-model="searchForm.total1"></el-input>
            </el-form-item>
              <el-form-item label="有托盘库位数">
              <el-input class="iot-w200" placeholder readonly v-model="searchForm.total2"></el-input>
            </el-form-item>
              <el-form-item label="托盘有货数">
              <el-input class="iot-w200" placeholder readonly v-model="searchForm.total3"></el-input>
            </el-form-item>      
          </div>-->
        </el-form>
      </div>
      <div class="layout__btns">
        <div ref="permissions">
          <el-button type="primary" plain icon="el-icon-document" @click="exportExcel">导出</el-button>
          <el-button type="primary" plain icon="el-icon-printer" @click="printTable">打印</el-button>
        </div>
      </div>
    </div>
    <div class="iot-table">
      <el-table
        id="out-table"
        ref="print"
        :data="tableData"
        stripe
        style="width: 100%"
        border
        @row-dblclick="click_edit"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
        <el-table-column
          align="center"
          prop="slot_code"
          label="库位编码"
          min-width="100"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          align="center"
          prop="stock_code"
          label="托盘条码"
          min-width="100"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          align="center"
          prop="inventory_total"
          label="库存数"
          min-width="100"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          align="center"
          prop="inventory_status"
          label="库位状态"
          min-width="100"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <span v-if="scope.row.inventory_status === 1">可用</span>
            <span v-else-if="scope.row.inventory_status === 2">分配</span>
            <span v-else-if="scope.row.inventory_status === 3">出库</span>
            <span v-else-if="scope.row.inventory_status === 4">复核</span>
            <span v-else-if="scope.row.inventory_status === 5">暂存</span>
            <span v-else-if="scope.row.inventory_status === 6">回流</span>
            <span v-else-if="scope.row.inventory_status === 7">冻结</span>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="imp_lock"
          label="入库锁定状态"
          min-width="100"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <span v-if="scope.row.imp_lock===0">正常</span>
            <span v-else >锁定</span>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="ex_lock"
          label="出库锁定状态"
          min-width="100"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <span v-if="scope.row.ex_lock===0">正常</span>
            <span v-else >锁定</span>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="imptime"
          label="入库时间"
          min-width="100"
          show-overflow-tooltip
          :formatter="dateTimeTransform"
        ></el-table-column>
      </el-table>
    </div>
    <div class="iot-pagination">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="pageSize"
        :current-page.sync="currentPage"
      ></el-pagination>
    </div>
    <el-dialog
      class="iot-dialog"
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="824px"
      append-to-body
    >
      <el-form inline ref="dialogForm" :model="ruleForm">
        <div class="iot-form-row">
          <el-form-item label="库位编码" label-width="100px" prop="slot_code">
            <el-input class="iot-w240" placeholder="请输入库位编码" maxlength="50" v-model="ruleForm.slot_code" readonly clearable></el-input>
          </el-form-item>
          <el-form-item label="托盘条码" label-width="100px" prop="stock_code">
            <el-input class="iot-w240" placeholder="请输入托盘条码" maxlength="50" v-model="ruleForm.stock_code" readonly clearable></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="库位状态" label-width="100px" prop="inventory_status">
            <el-input class="iot-w240" placeholder="请输入库位状态" maxlength="50" v-model="ruleForm.inventory_status" readonly clearable></el-input>
          </el-form-item>
          <el-form-item label="入库日期" label-width="100px" prop="imptime">
            <el-input class="iot-w240" placeholder="请输入入库日期" maxlength="50" v-model="ruleForm.imptime" readonly clearable></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <div style="text-align: center;font-size: 30px;padding: 10px 0px;">
            <span>物料包装</span>
          </div>
          <div class="iot-table">
            <el-table :data="tableData2" stripe style="width: 100%" border>
              <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
              <el-table-column
                align="center"
                prop="good_code"
                label="物料编码"
                min-width="100"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                align="center"
                prop="good_name"
                label="物料名称"
                min-width="100"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                align="center"
                prop="batch_no"
                label="大批号"
                min-width="100"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                align="center"
                prop="package_code"
                label="包装码"
                min-width="100"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                align="center"
                prop="number"
                label="数量"
                min-width="100"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                align="center"
                prop="unit"
                label="单位"
                min-width="100"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                align="center"
                prop="imptime"
                label="入库日期"
                min-width="100"
                :formatter="dateTimeTransform"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                align="center"
                prop="status"
                label="质量状态"
                min-width="100"
                show-overflow-tooltip
              ></el-table-column>
            </el-table>
          </div>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer" style="display:flex;justify-content: center;">
        <el-button size="small" type="danger" @click="dialogVisible = false">
          <span class="el-icon-close"></span>取 消
        </el-button>
      </div>
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
      areaList: [],
      qualityList: [],
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
      tableData2: [],
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
        unit_name: "",
        unit_is_enable: ""
      },
      imptimeName:'',
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
    //日期时间转换
    dateTimeTransform(row) {
      let time = row.imptime;
      this.imptimeName=this.$utils.format(time,'yyyy-MM-dd hh:mm:ss')
      return this.$utils.format(time,'yyyy-MM-dd hh:mm:ss');

    },
    //根据当前用户权限标识初始化按钮状态
    btnInit() {
      this.$Common.DistributePermission.call(this);
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
      let ss = this.searchForm.startDate;
      if (ss) {
        this.$moment(ss[0]).format("YYYY-MM-DD") +
          this.$moment(ss[1]).format("YYYY-MM-DD");
        var params = {
          MaxResultCount: this.pageSize,
          SkipCount: (this.currentPage - 1) * this.pageSize,
          slot_code: this.searchForm.slot_code,
          stock_code: this.searchForm.stock_code,
          inventory_date_b: this.searchForm.startDate[0].format("YYYY-MM-DD"),
          inventory_date_e: this.searchForm.startDate[1].format("YYYY-MM-DD")
        };
      } else {
        var params = {
          MaxResultCount: this.pageSize,
          SkipCount: (this.currentPage - 1) * this.pageSize,
          slot_code: this.searchForm.slot_code,
          stock_code: this.searchForm.stock_code
        };
      }

      let data = await getListApi(params);
      if (data) {
        this.tableData = data.items || [];
        this.total = data.totalCount || 0;
        this.multipleSelection = [];
      }
      this.searchForm.total1 = 100;
      this.total = 0;
      this.multipleSelection = [];
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

      let inventory_statusName='';
      if(row.inventory_status=='1')
        inventory_statusName='可用';
      else if(row.inventory_status=='2')
        inventory_statusName='分配';
      else if(row.inventory_status=='3')
        inventory_statusName='出库';
      else if(row.inventory_status=='4')
        inventory_statusName='复核';
      else if(row.inventory_status=='5')
        inventory_statusName='暂存';
      else if(row.inventory_status=='6')
        inventory_statusName='回流';
      else if(row.inventory_status=='7')
        inventory_statusName='冻结';

      let obj = {
        slot_code: row.slot_code,
        stock_code: row.stock_code,
        inventory_status: inventory_statusName,
        imptime: this.imptimeName
      };



      this.ruleForm = obj;
      let params = {
        slot_code: row.slotid,
        stock_code: row.stock_code,
        warehouseid: row.warehouseid
      };
      let res = await getOneApi(params);
      this.tableData2 = res;
    },
    //导出表格
    exportExcel() {
      this.$Common.ExportExcel("#out-table");
    },
    //打印表格
    printTable() {
      this.$Common.PrintTable.call(this, "print");
    }
  }
};
</script>
