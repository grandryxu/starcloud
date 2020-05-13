<template>
  <div class="iot-list" ref="page">
    <div class="iot-form">
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <el-form-item label="入库单号">
            <el-input class="iot-w200" placeholder v-model="searchForm.impbody_bill_bar" clearable></el-input>
          </el-form-item>
          <el-form-item label="合同号">
            <el-input class="iot-w200" placeholder v-model="searchForm.impbody_batch_no" clearable></el-input>
          </el-form-item>
          <el-form-item label="经销商">
            <el-select class="iot-w200" v-model="searchForm.custom_name" placeholder="请选择" clearable>
              <el-option v-for="(item,index) in customList" :key="index" :label="item.custom_name" :value="item.custom_name"></el-option>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="托盘">
            <el-input class="iot-w200" placeholder v-model="searchForm.warehouse_name"></el-input>
          </el-form-item> -->
          <el-form-item label="单据类型">
            <el-select class="iot-w200" v-model="searchForm.impbody_type" placeholder="请选择" clearable>
              <el-option v-for="item in impbodyTypeList" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-select class="iot-w200" v-model="searchForm.impbody_execute_flag" placeholder="请选择" clearable>
              <el-option v-for="item in executeList" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="单据日期">
            <el-date-picker class="iot-w150" clearable v-model="searchForm.startDate" type="date" placeholder="起始日期"></el-date-picker>
            <span class="el-icon-minus" style="color:#ccc"></span>
            <el-date-picker class="iot-w150" clearable v-model="searchForm.endDate" type="date" placeholder="结束日期"></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">查询</el-button>
          </el-form-item>
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
      <el-table id="out-table" ref="print" :data="tableData" stripe style="width: 100%" border @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
        <!-- <el-table-column align="center" prop="impbody_list_id" label="入库流水号" min-width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{scope.row.impbody_bill_bar + '-' + scope.$index}}</span>
          </template>
        </el-table-column> -->
        <el-table-column align="center" prop="impbody_bill_bar" label="入库单号" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="impbody_external_listid" label="合同号" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="impbody_goods_id" label="物料编码" min-width="100" show-overflow-tooltip :formatter="toGoodsCode"></el-table-column>
        <el-table-column align="center" prop="impbody_goods_id" label="物料名称" min-width="100" show-overflow-tooltip :formatter="toGoodsName"></el-table-column>
        <el-table-column align="center" prop="impbody_batch_no" label="批次" min-width="100" show-overflow-tooltip></el-table-column>
        <!-- <el-table-column
          align="center"
          prop="impbody_stock_code"
          label="托盘"
          min-width="100"
          show-overflow-tooltip
        ></el-table-column> -->
        <el-table-column align="center" prop="impbody_slot_code" label="库位" min-width="100" show-overflow-tooltip></el-table-column>
        <!-- <el-table-column
          align="center"
          prop="warehouse_is_enable"
          label="经销商"
          min-width="100"
          show-overflow-tooltip
        ></el-table-column> -->
        <el-table-column align="center" prop="impbody_binding_quantity" label="数量" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="goodsInfo" label="单位名称" min-width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{scope.row.goodsInfo&&scope.row.goodsInfo.unit?scope.row.goodsInfo.unit.unit_name : '无'}}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="impbody_date" label="入库日期" min-width="100" show-overflow-tooltip :formatter="dateTransform"></el-table-column>
        <el-table-column align="center" prop="impbody_execute_flag" label="执行状态" min-width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.impbody_execute_flag === 1">未执行</span>
            <span v-else-if="scope.row.impbody_execute_flag === 2">执行中</span>
            <span v-else-if="scope.row.impbody_execute_flag === 3">已完成</span>
          </template>
        </el-table-column>
        <!-- <el-table-column
          align="center"
          prop="impbody_noused_flag"
          label="是否作废"
          min-width="100"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <span v-if="scope.row.impbody_noused_flag === 1">正常</span>
            <span v-else-if="scope.row.impbody_noused_flag === 2">作废</span>
          </template>
        </el-table-column> -->
        <el-table-column align="center" prop="impbody_remark" label="备注" min-width="100" show-overflow-tooltip></el-table-column>
      </el-table>
    </div>
    <div class="iot-pagination">
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
    </div>
    <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
      <el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
        <div class="iot-form-row">
          <el-form-item label="物料名称" label-width="100px" prop="impbody_goods_id">
            <el-select class="iot-w240" v-model="ruleForm.impbody_goods_id" placeholder="请选择" disabled>
              <el-option v-for="item in goodsList" :key="item.id" :label="item.goods_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="物料编码" label-width="100px" prop="impbody_goods_id">
            <el-select class="iot-w240" v-model="ruleForm.impbody_goods_id" placeholder="请选择" disabled>
              <el-option v-for="item in goodsList" :key="item.id" :label="item.goods_code" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="批次" label-width="100px" prop="impbody_batch_no">
            <el-input class="iot-w240" v-model="ruleForm.impbody_batch_no" placeholder="请输入批次" clearable disabled></el-input>
          </el-form-item>
          <el-form-item label="数量" label-width="100px" prop="impbody_binding_quantity">
            <el-input class="iot-w240" v-model="ruleForm.impbody_binding_quantity" placeholder="请输入数量" clearable disabled></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="有效期" label-width="100px" prop="impbody_vaildate_date">
            <el-date-picker class="iot-w240" clearable v-model="ruleForm.impbody_vaildate_date" type="date" placeholder="请选择" disabled></el-date-picker>
          </el-form-item>
          <el-form-item label="质量状态" label-width="100px" prop="impbody_quality_status">
            <el-select class="iot-w240" v-model="ruleForm.impbody_quality_status" placeholder="请选择" disabled>
              <el-option v-for="item in qualityList" :key="item.id" :label="item.quality_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div v-if="dialogTitle === '修改'">
          <h1 style="text-align:center;font-size:16px;font-weight:bolder;">物料包装明细</h1>
          <div class="layout__btns">
            <div>
              <el-button type="primary" plain icon="el-icon-edit" @click="click_order_edit">编辑</el-button>
            </div>
          </div>
          <div class="iot-table">
            <el-table :data="tableScanData" stripe style="width: 100%" border @row-dblclick="handleScanEdit" @selection-change="handleSelectionChangeOrder">
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
              <el-table-column align="center" prop="imporder_box_code" label="包装码" min-width="100" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" prop="impbody_code" label="数量" min-width="100" show-overflow-tooltip>
                <template slot-scope="scope">
                  <span v-show="!scope.row.isCanEdit">{{scope.row.imporder_quantity}}</span>
                  <el-input v-show="scope.row.isCanEdit" v-model="scope.row.imporder_quantity" clearable></el-input>
                </template>
              </el-table-column>
              <!-- <el-table-column
                align="center"
                prop="impbody_quality_status"
                label="质量状态"
                min-width="100"
                show-overflow-tooltip
              ></el-table-column> -->
              <!-- <el-table-column
                align="center"
                prop="imporder_remark"
                label="备注"
                min-width="100"
                show-overflow-tooltip
              ></el-table-column> -->
            </el-table>
          </div>
        </div>
        <div v-else-if="dialogTitle === '模拟扫描'">
          <div class="layout__btns">
            <div>
              <el-button type="primary" plain icon="el-icon-search" @click="handleScan">扫描</el-button>
              <el-button type="primary" plain icon="el-icon-search" @click="handleDeleteScan">删除</el-button>
            </div>
          </div>
          <div class="iot-table">
            <el-table :data="tableScanData.slice((currentScanPage-1)*scanPageSize,currentScanPage*scanPageSize)" stripe style="width: 100%" border @selection-change="handleSelectionScanChange">
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column align="center" prop="scanId" label="序号" width="50">
                <template slot-scope="scope">
                  {{scope.row.scanId = +scope.$index + 1}}
                </template>
              </el-table-column>
              <el-table-column align="center" prop="imporder_stock_code" label="托盘码" min-width="100" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-input class="iot-w150" v-model="scope.row.imporder_stock_code" clearable></el-input>
                </template>
              </el-table-column>
              <el-table-column align="center" prop="imporder_box_code" label="箱码" min-width="100" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-input class="iot-w150" v-model="scope.row.imporder_box_code" clearable></el-input>
                </template>
              </el-table-column>
              <el-table-column align="center" prop="imporder_binding_quantity" label="数量" min-width="100" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-input class="iot-w150" v-model="scope.row.imporder_binding_quantity" clearable></el-input>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="iot-pagination">
            <el-pagination background layout="prev, pager, next" :total="tableScanData.length" :page-size="scanPageSize" :current-page.sync="currentScanPage" @current-change="handleCurrentChangeScan"></el-pagination>
          </div>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="click_submit_scan">保 存</el-button>
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
  addListApi,
  editListApi,
  deleteListApi,
  getOrderListApi
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
      multipleSelection: [],
      multipleSelectionScan:[],
      multipleSelectionOrder:[],
      radio1: "",
      searchForm: {},
      dialogTitle: "模拟扫描",
      tableData: [],
      tableScanData:[],
      customList: [],
      goodsList: [],
      pendingDeleteOrders:[],
      pendingCreateOrders:[],
      pendingEditOrders:[],
      typeList: this.$DropBox.getAllWarehouseType(),
      impbodyTypeList: [{ value: 1, label: "一般入库" }],
      executeList: [
        { value: 1, label: "未执行" },
        { value: 2, label: "执行中" },
        { value: 3, label: "已完成" }
      ],
      qualityList: [],
      slotList: [
        { id: 1, slotName: "层列排" },
        { id: 2, slotName: "排列层" }
      ],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      scanTotal:0,
      dialogVisible: false,
      scanPageSize:10 ,
      currentScanPage:1,
      //表单数据
      ruleForm: {
        impbody_goods_id: "",
        imphead_execute_flag: "",
        impbody_batch_no: "",
        impbody_stock_code: "",
        impbody_vaildate_date: "",
        impbody_remark: "",
        impbody_binding_quantity: "",
        impbody_bill_bar:"",
        impbody_noused_flag:""
      },
      //表单验证规则
      rules: {
        impbody_goods_id: [
          {
            required: true,
            message: "请输入物料名称和物料编码",
            trigger: "blur"
          }
        ],
        imphead_execute_flag: [
          {
            required: true,
            message: "请选择状态",
            trigger: "blur"
          }
        ],
        impbody_vaildate_date: [
          {
            required: true,
            message: "有效日期",
            trigger: "blur"
          }
        ],
        impbody_batch_no: [
          {
            required: true,
            message: "请输入批次号",
            trigger: "blur"
          }
        ],
        impbody_stock_code: [
          {
            required: true,
            message: "请输入托盘条码",
            trigger: "blur"
          }
        ],
        impbody_binding_quantity: [
          {
            required: true,
            message: "请输入数量",
            trigger: "blur"
          }
        ]
      }
    };
  },
  mounted() {
    this.GetAll();
    this.getCustomList();
    this.getGoodsList();
    this.getQualityList();
    this.btnInit();
  },
  methods: {
    //根据当前用户权限标识初始化按钮状态
    btnInit() {
      this.$Common.DistributePermission.call(this)
    },
    async getCustomList() {
      let res = await this.$DropBox.getAllCustoms();
      if (res.items) {
        this.customList = res.items;
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
    handleCurrentChangeScan(val){
      this.currentScanPage = val;
      this.GetAll();
    },
    //表单重置
    async resetForm() {
      this.dialogVisible = true;
      await this.$nextTick();
      this.$refs["dialogForm"].resetFields();
      // this.tableScanData = [];
      this.pendingDeleteOrders=[];
      this.pendingCreateOrders=[];
      this.pendingEditOrders=[];
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
     //流水列表
    async GetAllOrders() {
      let params = {
        MaxResultCount: this.pageSize,
        SkipCount: (this.currentPage - 1) * this.pageSize,
        imporder_body_id:this.ruleForm.id
,
      };
      let data = await getOrderListApi(params);
      if (data) {
        this.tableScanData = data.items || [];
        this.scanTotal = data.totalCount || 0;
        if(this.tableScanData.length){
          this.tableScanData.map(ele=>{
            ele.isCanEdit = false;
          })
        }
      }
    },
    //获取物料信息列表
    async getGoodsList() {
      let goodsList = await this.$DropBox.getGoodsInfoList();
      this.goodsList = goodsList.items;
    },
     //获取质量状态列表
    async getQualityList() {
      let qualityList = await this.$DropBox.getQualitylist();
      this.qualityList = qualityList.items;
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
            "模拟扫描"
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
            message: "模拟扫描成功"
          });
          this.GetAll();
          this.dialogVisible = false;
        }
      });
    },
     //操作物料数据传送分类
    classifyOrdersData(){
      this.tableScanData.forEach(ele=>{
        if(ele.isCreate){
          this.pendingCreateOrders.push(ele)
        }else if(ele.isEdit){
          this.pendingEditOrders.push(ele)
        }
      })
    },
    //物料新建
    async CreateOrders() {
      if(this.pendingCreateOrders.length){
        await addListApi(JSON.stringify(this.pendingCreateOrders))
      }
    },
    //当checkbox发生变化时
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //当新增扫描checkbox发生变化时
    handleSelectionScanChange(val) {
      this.multipleSelectionScan = val;
    },
    handleSelectionChangeOrder(val){
      this.multipleSelectionOrder = val;
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
    //作废
    handleInvalid() {
      if (this.multipleSelection.length == 1) {
        this.dialogTitle = '作废';
        this.Get(this.multipleSelection[0]);
      } else {
        //this.text_selectOne  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_selectOne);
      }
    },
     //点击模拟扫描按钮
    handleAnalogScan() {
      if (this.multipleSelection.length == 1) {
        this.click_scan(this.multipleSelection[0]);
        this.tableScanData = [];
      } else {
        //this.text_selectOne  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_selectOne);
      }
    },
     //点击扫描按钮
    handleScan() {
      let data = this.tableScanData;
      if(data.length === 0){
          data.push({
          scanId:+data.length + 1,
          imporder_bill_bar:this.ruleForm.impbody_bill_bar,
          imporder_goods_id:this.ruleForm.impbody_goods_id,
          imporder_body_id:this.ruleForm.id,
          imporder_noused_flag:this.ruleForm.impbody_noused_flag,
          isCreate:true,
          isCanEdit:true,
          imporder_batch_no:this.ruleForm.impbody_batch_no,
          imporder_warehouse_id:this.ruleForm.impbody_warehouse_id
        })
      }else{
        data.push({
          scanId:+data[data.length-1].scanId + 1,
          imporder_bill_bar:this.ruleForm.impbody_bill_bar,
          imporder_goods_id:this.ruleForm.impbody_goods_id,
          imporder_body_id:this.ruleForm.id,
          imporder_noused_flag:this.ruleForm.impbody_noused_flag,
          isCreate:true,
          isCanEdit:true,
          imporder_batch_no:this.ruleForm.impbody_batch_no,
          imporder_warehouse_id:this.ruleForm.impbody_warehouse_id
        })
      }
    },
    //点击删除扫描按钮
    handleDeleteScan(){
       if (this.multipleSelectionScan.length >= 1) {
        this.DeleteScan(this.multipleSelectionScan);
      } else {
        //this.text_selectOne  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_selectOne);
      }
    },

    //点击作废按钮
    handleDelete() {
      if (this.multipleSelection.length == 0) {
        //this.text_deleteRow  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_deleteRow);
      } else {
        let row = this.multipleSelection;
        this.Delete(row);
      }
    },
    //点击修改（内）
    click_order_edit(){
       if (this.multipleSelectionOrder.length == 0) {
        //this.text_deleteRow  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_deleteRow);
      } else {
        let row = this.multipleSelectionOrder[0];
        this.handleScanEdit(row);
      }
    },
    handleScanEdit(row){
      let ordersData = [];
      row.isEdit = true;
      for(let item of this.tableScanData){
        if(item.id === row.id){
          item.isCanEdit = true;
        }
        ordersData.push(item);
      }
      this.tableScanData = ordersData
    },
    click_edit(row) {
      this.dialogTitle = "修改";
      this.resetForm();
      this.Get(row);
     
    },
    click_scan(row) {
      this.dialogTitle = "模拟扫描";
      this.resetForm();
      this.Get(row);
    },
    click_search() {
      this.currentPage = 1;
      this.GetAll();
    },
    // click_add() {
    //   this.dialogTitle = "模拟扫描";
    //   this.resetForm();
    // },
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
        if (this.dialogTitle == "模拟扫描") {
          this.Create();
        } else {
          this.Update();
        }
      });
    },
    click_submit_scan() {
      if (this.dialogTitle == "模拟扫描"){
        const h = this.$createElement;
        this.$msgbox({
          title: "提示",
          message: h("p", null, [
            h("span", null, "确定【"),
            h("span", { style: this.c_danger }, "新增"),
            h("span", null, "】吗？")
          ]),
          showCancelButton: true,
          type: "warning"
        }).then(async action => {
          this.classifyOrdersData();
          await this.CreateOrders();
          this.dialogVisible = false;
        })
      }else if(this.dialogTitle == "修改"){
        this.classifyOrdersData();
        this.UpdateOrders();
        this.dialogVisible = false;
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
    //删除扫描
    DeleteScan(rows){
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
      }).then(action=>{
         this.tableScanData = this.tableScanData.filter(ele=>{
          let data ={};
          rows.forEach((row,index,arr)=>{
            if(ele.scanId === row.scanId){
              data = row;
              arr.splice(index,1);
            }
          });
          return ele.scanId !== data.scanId
        })
      })
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
    //作废
    Invalid() {
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
            "作废"
          ),
          h("span", null, "】吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        let params = {
          ...this.ruleForm
        };
        params.impbody_noused_flag = 2;
        let res = await editApi(params);
        if (res) {
          this.$message({
            type: "success",
            message: "作废成功"
          });
          this.GetAll();
        }
      });
    },
    //编辑流水
    async UpdateOrders(){
      if(this.pendingEditOrders.length){
        await editListApi(this.pendingEditOrders)
      }
    },
    //根据主键获取信息
    async Get(row) {
      let params = {
        id: row.id
      };
      let res = await getOneApi(params);
      let obj = {
        id: row.id,
        impbody_code: res.impbody_code,
        imphead_execute_flag: res.imphead_execute_flag,
        impbody_goods_id:res.impbody_goods_id,
        impbody_batch_no: res.impbody_batch_no,
        impbody_stock_code: res.impbody_stock_code,
        impbody_vaildate_date: res.impbody_vaildate_date,
        impbody_remark: res.impbody_remark,
        impbody_binding_quantity: res.impbody_binding_quantity,
        impbody_quality_status:res.impbody_quality_status,
        impbody_bill_bar:res.impbody_bill_bar,
        impbody_noused_flag:res.impbody_noused_flag,
        impbody_warehouse_id:res.impbody_warehouse_id
      };
      this.ruleForm = obj;
      if(this.dialogTitle === '修改'){
        await this.GetAllOrders()
      }else if(this.dialogTitle ==='作废'){
        await this.Invalid()
      }
      
    },
    //日期时间转换
    dateTransform(row) {
      let date = row.impbody_date;
      return this.$moment(date).format("YYYY-MM-DD");
    },
    //物料名称
    toGoodsCode(row){
      let goodsCode;
      this.goodsList.forEach((ele)=>{
        if(ele.id === row.impbody_goods_id){
          goodsCode = ele.goods_code
        }
      })
      return goodsCode
    },
    toGoodsName(row){
      let goodsName;
      this.goodsList.forEach((ele)=>{
        if(ele.id === row.impbody_goods_id){
          goodsName = ele.goods_name
        }
      })
      return goodsName
    },
    //质量状态
    toQuality(row){
      let qualityStatus;
      this.qualityList.forEach((ele)=>{
        if(ele.id === row.impbody_quality_status){
          qualityStatus = ele.quality_name
        }
      });
      return qualityStatus
    },
    //导出表格
    exportExcel() {
      this.$Common.ExportExcel("#out-table")
    },
    //打印表格
    printTable(){
      this.$Common.PrintTable.call(this,'print')
    }
  },
  computed:{
  }
};
</script>