<template>
  <div class="iot-list" ref="page">
    <div class="iot-form">
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <el-form-item label="出库单号">
            <el-input class="iot-w200" placeholder="请输入出库单号" maxlength="50" v-model="searchForm.exphead_code" clearable></el-input>
          </el-form-item>
          <el-form-item label="合同号">
            <el-input class="iot-w200" placeholder="请输入合同号" maxlength="50" v-model="searchForm.exphead_external_code" clearable></el-input>
          </el-form-item>
          <el-form-item label="供应商">
            <el-select class="iot-w200" v-model="searchForm.exphead_custom_id" placeholder="请选择" clearable>
              <el-option v-for="item in customList" :key="item.id" :label="item.custom_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="单据类型">
            <el-select class="iot-w200" v-model="searchForm.exphead_bill_id" placeholder="请选择" clearable>
              <el-option v-for="item in billTypeList" :key="item.id" :label="item.bill_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="状态">
            <el-select class="iot-w200" v-model="searchForm.exphead_execute_flag" placeholder="请选择" clearable>
              <el-option v-for="item in executeList" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="仓库">
            <el-select class="iot-w200" v-model="searchForm.exphead_warehouse_id" placeholder="请选择" clearable>
              <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
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
          <el-button type="primary" plain icon="el-icon-plus" @click="click_add">新增</el-button>
          <el-button type="primary" plain icon="el-icon-edit-outline" @click="handleEdit">编辑</el-button>
          <el-button type="primary" plain icon="el-icon-delete" @click="handleDelete">删除</el-button>
          <el-button type="primary" plain @click="handleWave">设定波次</el-button>
          <el-button type="primary" plain @click="handleAuto">自动出库</el-button>
          <el-button type="primary" plain @click="click_manual">手动出库</el-button>
          <el-button type="primary" plain @click="click_add">一键走账</el-button>
          <el-button type="primary" plain @click="handleFinish">单据完结</el-button>
          <el-button type="primary" plain @click="click_bill_print">单据打印</el-button>
          <el-button type="primary" plain icon="el-icon-document" @click="exportExcel">导出</el-button>
          <el-button type="primary" plain icon="el-icon-printer" @click="printTable">打印</el-button>
        </div>
      </div>
    </div>
    <div class="iot-table">
      <el-table id="out-table" ref="print" :data="tableData" stripe style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
        <el-table-column align="center" prop="exphead_wave_no" label="波次号" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="exphead_code" label="出库单号" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="exphead_external_code" label="合同号" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="exphead_bill_id" label="单据类型" min-width="100" show-overflow-tooltip :formatter="toBillType"></el-table-column>
        <el-table-column align="center" prop="exphead_warehouse_id" label="仓库" min-width="100" show-overflow-tooltip :formatter="warehouseShow"></el-table-column>
        <el-table-column align="center" prop="exphead_custom_id" label="供应商" min-width="100" show-overflow-tooltip :formatter="customNameShow"></el-table-column>
        <el-table-column align="center" prop="exphead_date" label="出库日期" min-width="100" show-overflow-tooltip :formatter="dateTransform"></el-table-column>
        <el-table-column align="center" prop="exphead_execute_flag" label="状态" min-width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.exphead_execute_flag === 1">未执行</span>
            <span v-else-if="scope.row.exphead_execute_flag === 2">执行中</span>
            <span v-else-if="scope.row.exphead_execute_flag === 3">已完成</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="exphead_remark" label="备注" min-width="100" show-overflow-tooltip></el-table-column>
      </el-table>
    </div>
    <div class="iot-pagination">
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
    </div>
    <el-dialog ref="print" class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
      <div v-if="dialogTitle !== '单据打印'">
        <el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
          <div class="iot-form-row">
            <el-form-item label="出库单号" label-width="100px" prop="exphead_code">
              <el-input class="iot-w240" v-model="ruleForm.exphead_code" placeholder="请输入出库单号" maxlength="50" clearable :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="合同号" label-width="100px" prop="exphead_external_code">
              <el-input class="iot-w240" v-model="ruleForm.exphead_external_code" placeholder="请输入合同号" maxlength="50" clearable></el-input>
            </el-form-item>
          </div>
          <div class="iot-form-row">
            <el-form-item label="单据类型" label-width="100px" prop="exphead_bill_id">
              <el-select class="iot-w240" v-model="ruleForm.exphead_bill_id" placeholder="请选择" clearable>
                <el-option v-for="item in billTypeList" :key="item.id" :label="item.bill_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="供应商" label-width="100px" prop="exphead_custom_id">
              <el-select class="iot-w240" v-model="ruleForm.exphead_custom_id" placeholder="请选择">
                <el-option v-for="item in customList" :key="item.id" :label="item.custom_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </div>
          <div class="iot-form-row">
            <el-form-item label="单据日期" label-width="100px" prop="exphead_date">
              <el-date-picker class="iot-w240" clearable v-model="ruleForm.exphead_date" type="date" placeholder="请选择"></el-date-picker>
            </el-form-item>
            <el-form-item label="仓库" label-width="100px" prop="exphead_warehouse_id">
              <el-select class="iot-w240" v-model="ruleForm.exphead_warehouse_id" placeholder="请选择">
                <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </div>
          <div class="iot-form-row">
            <el-form-item label="状态" label-width="100px" prop="exphead_execute_flag">
              <el-select class="iot-w240" v-model="ruleForm.exphead_execute_flag" placeholder="请选择">
                <el-option v-for="item in executeList" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </div>
          <div class="iot-form-row">
            <el-form-item label="备注" label-width="100px" prop="exphead_remark">
              <el-input type="textarea" :rows="4" :maxlength="200" class="iot-w240" show-word-limit v-model="ruleForm.exphead_remark" clearable></el-input>
            </el-form-item>
          </div>
        </el-form>

        <!-- 物料批次明细 -->
        <div class="iot-table">
          <h1 style="text-align:center;">{{$t('importWarehouseManage.goodsBatchDetail')}}</h1>
          <div class="layout__btns">
            <div>
              <el-button type="primary" plain icon="el-icon-plus" @click="click_add_goods">{{$t('permission.add')}}</el-button>
              <el-button type="primary" plain icon="el-icon-edit-outline" @click="handleEditGoods">{{$t('table.edit')}}</el-button>
              <el-button type="primary" plain icon="el-icon-edit-outline" @click="handleDeleteGoods">{{$t('permission.delete')}}</el-button>
            </div>
          </div>
          <el-table :data="goodsTableData" stripe style="width: 100%" border @row-dblclick="click_edit_goods" @selection-change="handleSelectionChangeGoods">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column align="center" label="序号" width="50">
              <template slot-scope="scope">{{scope.row.goodsId = +scope.$index + 1}}</template>
            </el-table-column>
            <el-table-column align="center" prop="expbody_goods_id" :label="$t('importWarehouseManage.goodsCode')" min-width="150" show-overflow-tooltip :formatter="toGoodsCode"></el-table-column>
            <el-table-column align="center" prop="expbody_goods_id" :label="$t('importWarehouseManage.goodsName')" min-width="150" show-overflow-tooltip :formatter="toGoodsName"></el-table-column>
            <el-table-column align="center" prop="expbody_batch_no" :label="$t('importWarehouseManage.bigBatchNum')" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" prop="expbody_plan_quantity" :label="$t('importWarehouseManage.planNum')" min-width="100" show-overflow-tooltip>
              <template slot-scope="scope">
                <span v-show="!scope.row.isCanEdit">{{scope.row.expbody_plan_quantity}}</span>
                <el-input v-show="scope.row.isCanEdit" v-model="scope.row.expbody_plan_quantity"></el-input>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="expbody_quality_status" :label="$t('importWarehouseManage.qalityStatus')" min-width="100" show-overflow-tooltip :formatter="toQuality"></el-table-column>
          </el-table>
        </div>
        <el-dialog class="iot-dialog" :title="dialogInventoryTitle" :visible.sync="dialogInventoryVisible" width="824px" append-to-body>
          <el-table :data="inventoryTableData" stripe style="width: 100%" border @selection-change="handleSelectionChangeInventory">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
            <el-table-column align="center" prop="goods_id" label="物资编码" min-width="150" show-overflow-tooltip :formatter="toGoodsCode"></el-table-column>
            <el-table-column align="center" prop="goods_id" label="物资名称" min-width="150" show-overflow-tooltip :formatter="toGoodsName"></el-table-column>
            <el-table-column align="center" prop="inventory_batch_no" label="批次号" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" prop="inventory_quantity" label="计划数量" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" prop="quality_id" label="质量状态" min-width="100" show-overflow-tooltip :formatter="toQuality"></el-table-column>
          </el-table>
          <span slot="footer" class="dialog-footer">
            <el-button size="small" @click="dialogInventoryVisible = false">取 消</el-button>
            <el-button size="small" type="primary" @click="click_submit_inventory">确 定</el-button>
          </span>
        </el-dialog>
      </div>
      <div v-else class="iot-form" ref="billPrint">
        <el-form inline label-width="100px" :model="searchForm">
          <el-form-item label="合同号" label-width="100px" prop="exphead_external_code">
            <el-input class="iot-w240" v-model="ruleForm.exphead_external_code" placeholder="请输入合同号" maxlength="50" clearable></el-input>
          </el-form-item>
          <el-form-item label="出库单号" label-width="100px" prop="exphead_code">
            <el-input class="iot-w240" v-model="ruleForm.exphead_code" placeholder="请输入出库单号" maxlength="50" clearable :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="仓库" label-width="100px" prop="exphead_warehouse_id">
            <el-select class="iot-w240" v-model="ruleForm.exphead_warehouse_id" placeholder="请选择">
              <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="供应商" label-width="100px" prop="exphead_custom_id">
            <el-select class="iot-w240" v-model="ruleForm.exphead_custom_id" placeholder="请选择">
              <el-option v-for="item in customList" :key="item.id" :label="item.custom_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
         <el-form-item label="单据日期" label-width="100px" prop="exphead_date">
            <el-date-picker class="iot-w240" clearable v-model="ruleForm.exphead_date" type="date" placeholder="请选择"></el-date-picker>
          </el-form-item>
          <el-form-item label="单据类型" label-width="100px" prop="exphead_bill_id">
            <el-select class="iot-w240" v-model="ruleForm.exphead_bill_id" placeholder="请选择" clearable>
              <el-option v-for="item in billTypeList" :key="item.id" :label="item.bill_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-form>

        <div class="iot-table">
          <el-table :data="tableData" stripe style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange">
            <!-- <el-table-column type="selection" width="55"></el-table-column> -->
            <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
            <el-table-column align="center" prop="exphead_code" label="物资编码" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" prop="exphead_external_code" label="物资名称" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" prop="billInfo.bill_type" label="规格型号" min-width="100" show-overflow-tooltip>
              <template slot-scope="scope">
                <span v-if="scope.row.warehouse_type === 1">立库</span>
                <span v-else-if="scope.row.warehouse_type === 2">平库</span>
                <span v-else-if="scope.row.warehouse_type === 3">密集库</span>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="warehouseInfo.warehouse_type" label="制造厂商" min-width="100" show-overflow-tooltip>
              <template slot-scope="scope">
                <span v-if="scope.row.warehouse_slot_type === 1">层列排</span>
                <span v-else-if="scope.row.warehouse_slot_type === 2">排列层</span>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="exphead_custom_id" label="供应商" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" prop="warehouse_slot_type" label="批次号" min-width="100" show-overflow-tooltip>
              <template slot-scope="scope">
                <span v-if="scope.row.warehouse_slot_type === 1">层列排</span>
                <span v-else-if="scope.row.warehouse_slot_type === 2">排列层</span>
              </template>
            </el-table-column>
            <el-table-column align="center" prop="exphead_date" label="出厂日期" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" prop="exphead_date" label="出厂编号" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" prop="exphead_date" label="单位" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" prop="exphead_date" label="数量" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" prop="exphead_date" label="单价" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" prop="exphead_date" label="金额" min-width="100" show-overflow-tooltip></el-table-column>
          </el-table>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="click_submit">保 存</el-button>
      </span>
    </el-dialog>

    <!-- 手动出库 -->
    <el-dialog ref="print" class="iot-dialog" :title="dialogManualTitle" :visible.sync="dialogManualVisible" width="824px" append-to-body>
      <!-- 物料批次明细 -->
      <div class="iot-table">
        <h1 style="text-align:center;">物料批次明细</h1>
        <el-checkbox-group style="text-align:right;" v-model="checked" @change="handleCheckedChange">
          <el-checkbox label="全部"></el-checkbox>
          <el-checkbox label="未满"></el-checkbox>
          <el-checkbox label="已满"></el-checkbox>
        </el-checkbox-group>
        <el-table :data="goodsTableData" stripe style="width: 100%" height="150" border>
          <el-table-column align="center" label="序号" width="50">
            <template slot-scope="scope">{{scope.row.goodsId = +scope.$index + 1}}</template>
          </el-table-column>
          <el-table-column align="center" prop="expbody_goods_id" label="物资编码" min-width="150" show-overflow-tooltip :formatter="toGoodsCode"></el-table-column>
          <el-table-column align="center" prop="expbody_goods_id" label="物资名称" min-width="150" show-overflow-tooltip :formatter="toGoodsName"></el-table-column>
          <el-table-column align="center" prop="expbody_batch_no" label="批次号" min-width="100" show-overflow-tooltip></el-table-column>
          <el-table-column align="center" prop="expbody_plan_quantity" label="计划数量" min-width="100" show-overflow-tooltip></el-table-column>
          <el-table-column align="center" prop="expbody_quality_status" label="质量状态" min-width="100" show-overflow-tooltip :formatter="toQuality"></el-table-column>
        </el-table>
      </div>

      <!-- 待选托盘 -->
      <div class="iot-table">
        <h1 style="text-align:center;">待选库存</h1>
        <el-table :data="manualInventoryTableData" stripe style="width: 100%" height="150" border @selection-change="handleSelectionChangeManual">
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column align="center" label="序号" width="50">
            <template slot-scope="scope">{{scope.row.goodsId = +scope.$index + 1}}</template>
          </el-table-column>
          <el-table-column align="center" prop="goods" label="物资编码" min-width="150" show-overflow-tooltip :formatter="toGoodsCode"></el-table-column>
          <el-table-column align="center" prop="goods" label="物资名称" min-width="150" show-overflow-tooltip :formatter="toGoodsName"></el-table-column>
          <el-table-column align="center" prop="inventory_batch_no" label="批次号" min-width="100" show-overflow-tooltip></el-table-column>
          <el-table-column align="center" prop="inventory_quantity" label="数量" min-width="100" show-overflow-tooltip></el-table-column>
          <el-table-column align="center" prop="quality" label="质量状态" min-width="100" show-overflow-tooltip :formatter="toQuality"></el-table-column>
          <el-table-column align="center" prop="inventory_stock_code" label="托盘" min-width="100" show-overflow-tooltip></el-table-column>
        </el-table>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogManualVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="click_manual_submit">保 存</el-button>
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
  getListGoodsApi,
  addGoodsApi,
  getOneGoodsApi,
  editGoodsApi,
  deleteGoodsApi,
  saveGoodsListApi,
  addGoodsListApi,
  editGoodsListApi,
  deleteGoodsListApi,
  setWaveNoApi,
  getInventoryApi,
  autoExportApi,
  manualExportApi,
  manualInventoryApi,
  GetEncodingRuleCode
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
      multipleSelectionInventory:[],
      multipleSelectionManual:[],
      radio1: "",
      searchForm: {},
      dialogTitle: "新增",
      dialogInventoryTitle:"新增物料",
      dialogManualTitle:"手动出库",
      tableData: [],
      goodsTableData: [],
      temGoodsTableData:[],
      underFullGoods:[],
      exceedFullGoods:[],
      goodsList: [],
      inventoryTableData:[],
      manualInventoryTableData:[],
      customList: [],
      warehouseList: [],
      billTypeList: [],
      pendingDeleteGoods:[],
      pendingCreateGoods:[],
      pendingEditGoods:[],
      qualityList: [],
      checked:['全部'],
      executeList: [
        { value: 1, label: "未执行" },
        { value: 2, label: "执行中" },
        { value: 3, label: "已完成" }
      ],
      slotList: [
        { id: 1, slotName: "层列排" },
        { id: 2, slotName: "排列层" }
      ],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      dialogInventoryVisible:false,
      dialogManualVisible:false,
      //表单数据
      ruleForm: {
        exphead_code: "",
        exphead_external_code: "",
        exphead_date: "",
        exphead_bill_id: "",
        exphead_custom_id: "",
        exphead_warehouse_id: "",
        exphead_remark: "",
        exphead_execute_flag: "1",
        exphead_wave_no:""
      },
      //表单验证规则
      rules: {
        exphead_code: [
          {
            required: true,
            message: "请输入出库单号",
            trigger: "blur"
          }
        ],
        exphead_external_code: [
          {
            required: true,
            message: "请输入合同号",
            trigger: "blur"
          }
        ],
        exphead_date: [
          {
            required: true,
            message: "请选择出库日期",
            trigger: "blur"
          }
        ],
        exphead_bill_id: [
          {
            required: true,
            message: "请选择单据类型",
            trigger: "blur"
          }
        ],
        exphead_warehouse_id: [
          {
            required: true,
            message: "请选择仓库",
            trigger: "blur"
          }
        ],
        exphead_custom_id: [
          {
            required: true,
            message: "请选择供应商",
            trigger: "blur"
          }
        ],
        exphead_execute_flag: [
          {
            required: true,
            message: "请选择是否启用",
            trigger: "blur"
          }
        ]
      }
    };
  },
  mounted() {
    this.GetAll();
    this.getCustomList();
    this.GetWarehouseList();
    this.getBillTypeList();
    this.getGoodsList();
    this.getInventoryList();
    this.getQualityList();
    this.btnInit();
  },
  methods: {
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
      this.goodsTableData = [];
      this.pendingCreateGoods = [];
      this.pendingEditGoods=[];
      this.pendingDeleteGoods=[];
      this.$utils.traverseDialogDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.dialogForm);
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
    //物料列表
    async GetAllGoods() {
      let params = {
        MaxResultCount: this.pageSize,
        SkipCount: (this.currentPage - 1) * this.pageSize,
        expbody_imphead_id: this.ruleForm.id
      };
      let data = await getListGoodsApi(params);
      if (data) {
        this.goodsTableData = data.items || [];
        this.goodsTotal = data.totalCount || 0;
        if(this.goodsTableData.length){
          this.goodsTableData.map(ele=>{
            ele.isCanEdit = false
          })
        }
      }
    },
    //仓库列表
    async GetWarehouseList() {
      let data = await this.$DropBox.getWarehouselist();
      if (data) {
        this.warehouseList = data || [];
      }
    },
    //获取供应商列表
    async getCustomList() {
      let res = await this.$DropBox.getAllCustoms();
      if (res.items) {
        this.customList = res.items;
      }
    },
    //获取物料信息列表
    async getGoodsList() {
      let inventoryGoodsList = await this.$DropBox.getInventoryGoodsInfoList();
      let goodsList = await this.$DropBox.getGoodsInfoList({MaxResultCount:'100'});
      this.goodsList = goodsList.items.filter(ele=>{
        let arrGoods = {}
        inventoryGoodsList.items.forEach(goods=>{
          if(goods.inventory_goods_id === ele.id){
            arrGoods = goods
          }
        });
        return arrGoods.inventory_goods_id === ele.id
      })
    },
    //获取入库单号
    async GetEncodingRuleCode() {
      let params = {
        code: 'CKCode'
      };
      let code = await GetEncodingRuleCode(params);
      this.ruleForm.exphead_code = code;
    },
    //获取单据类型列表
    async getBillTypeList() {
      let params={
        bill_type:2   //(1入库；2出库)
      }
      let res = await this.$DropBox.getBillTypeList(params);
      if (res.items) {
        this.billTypeList = res.items;
      }
    },
    //获取库存数据
    async getInventoryList(){
      let res = await getInventoryApi();
      if(res){
        this.inventoryTableData = res.items
      }
    },
    //获取质量状态列表
    async getQualityList() {
      let qualityList = await this.$DropBox.getQualitylist();
      this.qualityList = qualityList.items;
      console.log(this.qualityList)
    },
    //获取手动出库待选库存
    async getManualInventory(head_id){
      let res = await manualInventoryApi({head_id:head_id});
      if(res){
        this.manualInventoryTableData = res.items;
      }
    },
    //操作物料数据传送分类
    classifyGoodsData(id){
      this.goodsTableData.forEach(ele=>{
        if(ele.isCreate){
          if(id){
            ele.expbody_imphead_id = id
          }
          this.pendingCreateGoods.push(ele)
        }else if(ele.isEdit){
          this.pendingEditGoods.push(ele)
        }
      })
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
        let res1;
        let res2 =await addApi(params);
        if(res2){
          this.classifyGoodsData(res2.id);
          res1 = await this.CreateGoods();
        }
        Promise.all([res1,res2]).then((resloved)=>{
          this.$message({
            type: "success",
            message: "新增成功"
          });
          this.GetAll();
          this.dialogVisible = false;
        }).catch((error)=>{
          this.$message({
            type: "success",
            message: error
          });
        })
      });
    },
    //物料新建
   async CreateGoods() {
      if(this.pendingCreateGoods.length){
       await addGoodsListApi(this.pendingCreateGoods)
      }
    },
    //物料编辑
   async EditGoods() {
      if(this.pendingEditGoods.length){
       await editGoodsListApi(this.pendingEditGoods)
      }
    },
    //物料删除
    async DeleteGoods() {
      if(this.pendingDeleteGoods.length){
        await deleteGoodsListApi(this.pendingDeleteGoods)
      }
    },
    //当checkbox发生变化时
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //当物料checkbox发生变化时
    handleSelectionChangeGoods(val) {
      this.multipleSelectionGoods = val;
    },
    //当待选库存checkbox发生变化时
    handleSelectionChangeManual(val){
      this.multipleSelectionManual = val;
      if(val.length){
        this.filterPendingInventory();
      }else{
        this.manualInit();
        this.filterGoodsStatus(this.checked);
      }
    },
    //点击库存列表
    handleSelectionChangeInventory(val){
      this.multipleSelectionInventory = val;
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
    //点击选择库存的按钮
    click_submit_inventory(){
      if (this.multipleSelectionInventory.length == 1) {
        this.dialogInventoryVisible =false;
        this.handleAddGoods();
      } else {
        //this.text_selectOne  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_selectOne);
      }
    },
    //点击手动出库
    click_manual(){
      if (this.multipleSelection.length == 1) {
        this.Get(this.multipleSelection[0]);
        this.checked = ['全部'];
        this.$nextTick(()=>{
          this.dialogManualVisible =true;
        })
        
      } else {
        //this.text_selectOne  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_selectOne);
      }
    },
    //设定波次号
    async handleWave(rows){
      if (this.multipleSelection.length >= 1) {
        let res = await setWaveNoApi(this.multipleSelection);
        if(res){
          this.GetAll();
        }
      } else {
        //this.text_selectOne  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_selectOne);
      }
    },
    //自动出库
    async handleAuto(){
      if (this.multipleSelection.length >= 1) {
        let idList = [];
        this.multipleSelection.forEach(ele=>{
          idList.push(ele.id)
        })
        let res = await autoExportApi(idList);
        if(res){
          this.GMetAll();
        }
      } else {
        //this.text_selectOne  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_selectOne);
      }
    },
    //手动出库
    async handleManual(){
      if (this.multipleSelectionManual.length >= 1) {
       let obj =[];
       let params = this.multipleSelectionManual;
       params.forEach(ele=>{
         obj.push({
          exporder_batch_no: ele.inventory_batch_no,
          exporder_bill_bar: ele.inventory_bill_bar,
          exporder_quantity: ele.inventory_quantity,
          exporder_stock_code: ele.inventory_stock_code,
          exporder_goods_id: ele.goods.id,
          exporder_quality_status: ele.quality.id,
          exporder_warehouse_id: ele.nventory_warehouse_id,
          exporder_slot_code:ele.inventory_slot_code
         })
       })
        let res = await manualExportApi({
          headId:this.ruleForm.id,
          orderList:obj
        });
        if(res){
          this.$message({
            type: "success",
            message:'手动出库成功'
          });
          this.GetAll();
        }
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
    //点击编辑物料按钮
    handleEditGoods() {
      if (this.multipleSelectionGoods.length == 1) {
        this.click_edit_goods(this.multipleSelectionGoods[0]);
      } else {
        //this.text_selectOne  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_selectOne);
      }
    },
    //点击删除物料按钮
    handleDeleteGoods() {
      if (this.multipleSelectionGoods.length == 0) {
        //this.text_deleteRow  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_deleteRow);
      } else {
        let rows = this.multipleSelectionGoods;
        this.DeleteTemGoods(rows);
      }
    },
    //点击单据打印按钮
    handleBillPrint() {
      if (this.multipleSelection.length == 0) {
        //this.text_deleteRow  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_deleteRow);
      } else {
        let row = this.multipleSelection;
      }
    },
    //点击单据完结按钮
    handleFinish() {
      if (this.multipleSelection.length == 0) {
        //this.text_deleteRow  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_deleteRow);
      } else {
        let rows = this.multipleSelection;
        this.Finish(rows);
      }
    },
    click_edit(row) {
      this.dialogTitle = "编辑";
      this.resetForm();
      this.Get(row);
    },
    click_edit_goods(row) {
      row.isEdit = true;
      let goodsData = [];
      for(let item of this.goodsTableData){
        if(item.id === row.id){
          item.isCanEdit = true;
        }
        goodsData.push(item);
      }
      this.goodsTableData = goodsData
    },
    click_search() {
      this.currentPage = 1;
      this.GetAll();
    },
    click_add() {
      this.dialogTitle = "新增";
      this.GetEncodingRuleCode();
      this.resetForm();
    },
    click_add_goods() {
      this.$refs["dialogForm"].validate(async valid => {
        if (!valid) {
          return;
        }
        // let temArr =this.inventoryTableData;
        // this.inventoryTableData = [];
        // this.inventoryTableData.push(...temArr);
        await this.getInventoryList();
        this.multipleSelectionInventory = [];
        this.dialogInventoryVisible = true;

      });
    },
    handleAddGoods(){
       let data = this.goodsTableData;
       let {
         goods_id,
         inventory_batch_no,
         quality_id
       } = this.multipleSelectionInventory[0];
        if (data.length === 0) {
          data.push({
            goodsId: +data.length + 1,
            expbody_imphead_id:this.ruleForm.id,
            expbody_bill_bar:this.ruleForm.exphead_code,
            expbody_imphead_id:this.ruleForm.id,
            expbody_external_listid:this.ruleForm.exphead_external_code,
            isCreate:true,
            isCanEdit:true,
            expbody_goods_id:goods_id,
            expbody_batch_no:inventory_batch_no,
            expbody_quality_status:quality_id,
            expbody_noused_flag:'1',
          });
        } else {
          data.push({
            goodsId: +data[data.length - 1].goodsId + 1,
            expbody_imphead_id:this.ruleForm.id,
            expbody_bill_bar:this.ruleForm.exphead_code,
            expbody_imphead_id:this.ruleForm.id,
            expbody_external_listid:this.ruleForm.exphead_external_code,
            isCreate:true,
            isCanEdit:true,
            expbody_goods_id:goods_id,
            expbody_batch_no:inventory_batch_no,
            expbody_quality_status:quality_id,
            expbody_noused_flag:'1',
          });
        }
    },
    click_bill_print() {
      if (this.multipleSelection.length == 1) {
        let row = this.multipleSelection[0];
        this.dialogTitle = "单据打印";
        this.dialogVisible = true;
        this.getBillDetail(row);
      } else {
        //this.text_selectOne  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_selectOne);
      }
    },
     // 获取单据详情
    async getBillDetail(row) {
      await this.Get(row);
    },
    //点击保存
    click_submit() {
      if (this.dialogTitle == "查看") {
        this.dialogVisible = false;
        return;
      } else if (this.dialogTitle == "单据打印") {
        this.$print(this.$refs.billPrint);
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
    //点击保存手动出库
    click_manual_submit(){
      this.verifyManualExport()
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
     //删除临时物料
    DeleteTemGoods(rows) {
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
        rows.forEach(ele=>{
          if(!ele.isCreate){
            this.pendingDeleteGoods.push(ele.id)
          }
        });
        
        this.goodsTableData = this.goodsTableData.filter(ele=>{
          let tem = {};
          rows.forEach((row,index,arr)=>{
              if(row.goodsId === ele.goodsId){
                tem = row;
                arr.splice(index,1);
              }
          })
          return ele.goodsId !== tem.goodsId
        });
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
        this.classifyGoodsData();
        let res1 = this.CreateGoods();
        let res2 = this.EditGoods();
        let res3 = this.DeleteGoods();
        let res4 = editApi(params);
        Promise.all([res1,res2,res3,res4]).then((resloved)=>{
          this.$message({
            type: "success",
            message: "修改成功"
          });
          this.GetAll();
          this.dialogVisible = false;
        }).catch((error)=>{
          this.$message({
            type: "warning",
            message: error
          });
        })
      });
    },
   async UpdateGoods(){
      this.classifyGoodsData();
      let arrs = {
        createList:this.pendingCreateGoods,
        updateList:this.pendingEditGoods,
        idList:this.pendingDeleteGoods
      };
      await saveGoodsListApi(arrs);
    },
    //单据完结
    Finish(rows) {
      const h = this.$createElement;
      this.$msgbox({
        title: "提示",
        message: h("p", null, [
          h("span", null, "确定【"),
          h("span", { style: this.c_danger }, "完结"),
          h("span", null, "】吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        // let deleteCount = 0;
        // for (let i = 0; i < rows.length; i++) {
        //   let res = await editApi({ id: rows[i].id });
        //   if (res) {
        //     deleteCount++;
        //   }
        // }
        // if (deleteCount) {
        //   this.$message({
        //     type: "success",
        //     message: `完结删除${deleteCount}条,完结失败${rows.length -
        //       deleteCount}条`
        //   });
        //   this.GetAll();
        // }
        this.$utils.batcheHandle(rows, {});
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
        exphead_code: res.exphead_code,
        exphead_external_code: res.exphead_external_code,
        exphead_date: res.exphead_date,
        exphead_bill_id: res.exphead_bill_id,
        exphead_warehouse_id: res.exphead_warehouse_id,
        exphead_custom_id: res.exphead_custom_id,
        exphead_remark: res.exphead_remark,
        exphead_execute_flag: res.exphead_execute_flag,
        exphead_wave_no:res.exphead_wave_no,
      };
      this.ruleForm = obj;
      if(this.dialogTitle === '编辑'){
        this.GetAllGoods();
      }else if(this.dialogManualTitle === "手动出库"){
        await this.GetAllGoods();
        await this.getManualInventory(obj.id);
        this.temGoodsTableData = this.$Clone(this.goodsTableData);
      }
    },
    //仓库ID和name的转换
    warehouseShow(row) {
      let id = row.exphead_warehouse_id;
      let name;
      this.warehouseList.forEach(ele => {
        if (ele.id === id) {
          name = ele.warehouse_name;
        }
      });
      return name;
    },
    customNameShow(row) {
      let id = row.exphead_custom_id;
      let name;
      this.customList.forEach(ele => {
        if (ele.id === id) {
          name = ele.custom_name;
        }
      });
      return name;
    },
    //日期时间转换
    dateTransform(row) {
      let date = row.exphead_date;
      return this.$moment(date).format("YYYY-MM-DD");
    },
    //转换物料名称
    toGoodsCode(row) {
      let goodsCode;
      this.goodsList.forEach(ele => {
        if (ele.id === row.expbody_goods_id || ele.id === row.goods_id || (row.goods && ele.id === row.goods.id)) {
          goodsCode = ele.goods_code;
        }
      });
      return goodsCode;
    },
    toGoodsName(row) {
      let goodsName;
      this.goodsList.forEach(ele => {
        if (ele.id === row.expbody_goods_id || ele.id === row.goods_id || (row.goods && ele.id === row.goods.id)) {
          goodsName = ele.goods_name;
        }
      }); 
      return goodsName;
    },
    toExecuteStatus(row) {
      let executeStatus;
      this.executeList.forEach(ele => {
        if (ele.value === row.expbody_execute_flag) {
          executeStatus = ele.label;
        }
      });
      return executeStatus;
    },
    //合计总数
    getSummaries(){
      let sum = 0;
      this.goodsTableData.forEach(ele=>{
        if(ele.expbody_plan_quantity){
          sum += +ele.expbody_plan_quantity
        }
      })
      return sum
    },
    //转换单据类型
    toBillType(row){
      let billType;
      this.billTypeList.forEach(ele => {
        if (ele.id === row.exphead_bill_id) {
          billType = ele.bill_name;
        }
      });
      return billType;
    },
    //转换质量状态
    toQuality(row){
      let qualityStatus;
      this.qualityList.forEach((ele)=>{
        if(ele.id === row.quality_id || ele.id === row.expbody_quality_status || (row.quality && ele.id === row.quality.id)){
          qualityStatus = ele.quality_name
        }
      });
      return qualityStatus
    },
    //手动状态初始化
    manualInit(){
      this.underFullGoods = [];
      this.exceedFullGoods = [];
    },
    //手动出库分类
    handleCheckedChange(val){
      if(val.length >= 1){
        this.checked = val;
        this.checked.shift();
      }else{
        this.checked = ['全部']
      }
      this.filterGoodsStatus(this.checked)
    },
    //手动出库筛选
    filterGoodsStatus(checked){
      if(checked[0] === '全部'){
        this.goodsTableData = this.temGoodsTableData;
      }else if(checked[0] === '未满'){
        if(this.multipleSelectionManual.length === 0){
          this.goodsTableData = this.temGoodsTableData
        }else{
          this.goodsTableData = this.underFullGoods
        }
      }else if(checked[0] === '已满'){
        if(this.multipleSelectionManual.length === 0){
          this.goodsTableData = []
        }else{
          this.goodsTableData = this.exceedFullGoods
        }
      }
    },
    //筛选已满和未满状态数据
    filterPendingInventory(){
      let selectedInventory = this.multipleSelectionManual;
      let goodsData = this.temGoodsTableData;
      let temSelectedSame = [];
      this.manualInit();

      //将已选库存相同物料id和批次合并，并将数量累加
      for(let i = 0;i<selectedInventory.length;i++){
        if(temSelectedSame.length === 0){
          temSelectedSame.push(selectedInventory[i])
        }else{
          for(let j =0;j<temSelectedSame.length;j++){
          if(
            selectedInventory[i].goods.id === temSelectedSame[j].goods.id
            &&
            selectedInventory[i].inventory_batch_no === temSelectedSame[j].inventory_batch_no
            ){
              temSelectedSame[j].inventory_quantity += electedInventory[i].inventory_quantity
            }else{
              temSelectedSame.push(selectedInventory[i])
            }
          }
        }
      }
      //将已合并的库存物料与待出库物料进行对比筛选出未满和已满的数据
      if(temSelectedSame.length === 0){
        this.underFullGoods.push(goods)
      }else{
         goodsData.forEach(goods=>{
          temSelectedSame.forEach(tem=>{
            if(tem.goods.id === goods.expbody_goods_id && tem.inventory_batch_no === goods.expbody_batch_no){
              if(tem.inventory_quantity < goods.expbody_plan_quantity){
                this.underFullGoods.push(goods)
              }else{
                this.exceedFullGoods.push(goods)
              }
            }else{
              this.underFullGoods.push(goods)
            }
          })
        })
      }
      this.filterGoodsStatus(this.checked)
    },
    verifyManualExport(){
      if(this.exceedFullGoods.length === 0){
        const h = this.$createElement;
        this.$msgbox({
        title: "提示",
        message: h("p", null, [
          h("span", { style: this.c_danger }, "【物料存在未选择完全】"),
          h("span", null, "确定【"),
          h("span", { style: this.c_danger }, "出库"),
          h("span", null, "】吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        this.handleManual()
      })
      }else{
        const h = this.$createElement;
        this.$msgbox({
        title: "提示",
        message: h("p", null, [
          h("span", null, "确定【"),
          h("span", { style: this.c_danger }, "出库"),
          h("span", null, "】吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        this.handleManual()
      })
      }
    },
    //导出表格
    exportExcel() {
      this.$Common.ExportExcel("#out-table")
    },
    //打印表格
    printTable(){
      this.$Common.PrintTable.call(this,'print')
    },
  }
}
</script>