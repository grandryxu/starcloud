<template>
  <div class="iot-list" ref="page">
    <div class="iot-form">
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <el-form-item :label="$t('import.receiptno')">
            <el-input class="iot-w200" :placeholder="$t('import.pleaseinput')+$t('import.receiptno')" maxlength="50" v-model="searchForm.imphead_code" clearable></el-input>
          </el-form-item>
          <el-form-item :label="$t('import.contractno')">
            <el-input class="iot-w200" :placeholder="$t('import.pleaseinput')+$t('import.contractno')" maxlength="50" v-model="searchForm.imphead_external_code" clearable></el-input>
          </el-form-item>
          <el-form-item :label="$t('import.supplier')">
            <el-select class="iot-w200" v-model="searchForm.imphead_custom_id" :placeholder="$t('import.pleaseselect')+$t('import.supplier')" clearable>
              <el-option v-for="item in customList" :key="item.id" :label="item.custom_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item :label="$t('import.documenttype')">
            <el-select class="iot-w200" v-model="searchForm.imphead_bill_id" :placeholder="$t('import.pleaseselect')+$t('import.documenttype')"  clearable>
              <el-option v-for="item in billTypeList" :key="item.id" :label="item.bill_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item :label="$t('import.state')">
            <el-select class="iot-w200" v-model="searchForm.imphead_execute_flag" :placeholder="$t('import.pleaseselect')+$t('import.state')"  clearable>
              <el-option v-for="item in executeList" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item :label="$t('import.warehouse')">
            <el-select class="iot-w200" v-model="searchForm.imphead_warehouse_id" :placeholder="$t('import.pleaseselect')+$t('import.warehouse')" clearable >
              <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item :label="$t('import.documentdate')">
            <el-date-picker class="iot-w150" clearable v-model="searchForm.startDate" type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd" :placeholder="$t('import.startdate')"></el-date-picker>
            <span class="el-icon-minus" style="color:#ccc"></span>
            <el-date-picker class="iot-w150" clearable v-model="searchForm.endDate" type="date"  format="yyyy-MM-dd" value-format="yyyy-MM-dd" :placeholder="$t('import.endtdate')"></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">{{$t('import.search')}}</el-button>
          </el-form-item>
        </el-form>
      </div>

      <div class="layout__btns">
        <div ref="permissions">
          <el-button type="primary" plain icon="el-icon-plus" @click="click_add">新增</el-button>
          <el-button type="primary" plain icon="el-icon-edit-outline" @click="handleEdit">编辑</el-button>
          <el-button type="primary" plain icon="el-icon-delete" @click="handleDelete">删除</el-button>
          <el-button type="primary" plain @click="handleExamine">审核</el-button>
          <el-button type="primary" plain @click="handleUnexamine">弃审</el-button>
          <el-button type="primary" plain @click="handleCancel">作废</el-button>
          <el-button type="primary" plain @click="click_add">一键走账</el-button>
          <el-button type="primary" plain @click="handleFinish">单据完结</el-button>
<!--          <el-button type="primary" plain @click="handleMaintask">生成任务</el-button>-->
          <el-button type="primary" plain icon="el-icon-document" @click="exportExcel">导出</el-button>
          <el-button type="primary" plain icon="el-icon-printer" @click="printTable">打印</el-button>
          <el-button type="primary" plain @click="handleBillPrint">单据打印</el-button>
        </div>
      </div>
    </div>
    <div class="iot-table">
      <el-table id="out-table" ref="print" :data="tableData" :stripe="isCanselect?false : true" style="width: 100%" border :row-class-name="isCanselect ? tableRowClassName : ''" @row-dblclick="click_edit" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
        <el-table-column align="center" prop="imphead_code" label="入库单号" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="imphead_external_code" label="合同号" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="imphead_bill_id" label="单据类型" min-width="100" show-overflow-tooltip :formatter="toBillType"></el-table-column>
        <el-table-column align="center" prop="imphead_warehouse_id" label="仓库" min-width="100" show-overflow-tooltip :formatter="warehouseShow"></el-table-column>
        <el-table-column align="center" prop="imphead_date" label="单据日期" min-width="100" show-overflow-tooltip :formatter="dateTransform"></el-table-column>
        <el-table-column align="center" prop="imphead_custom_id" label="供应商" min-width="100" show-overflow-tooltip :formatter="customNameShow"></el-table-column>
        <el-table-column align="center" prop="imphead_date" label="入库日期" min-width="100" show-overflow-tooltip :formatter="dateTransform"></el-table-column>
        <el-table-column align="center" prop="imphead_execute_flag" label="状态" min-width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.imphead_execute_flag === 1">未执行</span>
            <span v-else-if="scope.row.imphead_execute_flag === 2">执行中</span>
            <span v-else-if="scope.row.imphead_execute_flag === 3">已完成</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="imphead_audit_flag" label="审核状态" min-width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.imphead_audit_flag === 2">已审核</span>
            <span v-else>未审核</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="imphead_noused_flag" label="作废状态" min-width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.imphead_noused_flag === 2">已作废</span>
            <span v-else>未作废</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="imphead_remark" label="备注" min-width="100" show-overflow-tooltip></el-table-column>
      </el-table>
    </div>
    <div class="iot-pagination">
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
    </div>
    <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
      <div v-if="dialogTitle !== '单据打印'">
        <el-form inline ref="dialogForm" :model="ruleForm" :rules="rules" :disabled="!isCanHandle" >
          <div class="iot-form-row">
            <el-form-item label="入库单号" label-width="100px" prop="imphead_code">
              <el-input class="iot-w240" v-model="ruleForm.imphead_code" placeholder="请输入入库单号" maxlength="50" clearable :disabled="true"></el-input>
            </el-form-item>
            <el-form-item label="合同号" label-width="100px" prop="imphead_external_code">
              <el-input class="iot-w240" v-model="ruleForm.imphead_external_code" placeholder="请输入合同号" maxlength="50" clearable :disabled="isHeadCanEdit"></el-input>
            </el-form-item>
          </div>
          <div class="iot-form-row">
            <el-form-item label="单据类型" label-width="100px" prop="imphead_bill_id">
              <el-select class="iot-w240" v-model="ruleForm.imphead_bill_id" placeholder="请选择" clearable :disabled="isHeadCanEdit">
                <el-option v-for="item in billTypeList" :key="item.id" :label="item.bill_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="供应商" label-width="100px" prop="imphead_custom_id">
              <el-select class="iot-w240" v-model="ruleForm.imphead_custom_id" placeholder="请选择" :disabled="isHeadCanEdit">
                <el-option v-for="item in customList" :key="item.id" :label="item.custom_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </div>
          <div class="iot-form-row">
            <el-form-item label="单据日期" label-width="100px" prop="imphead_date">
              <el-date-picker class="iot-w240" clearable v-model="ruleForm.imphead_date" type="date" placeholder="请选择" :disabled="isHeadCanEdit"></el-date-picker>
            </el-form-item>
            <!-- <el-form-item label="入库日期" label-width="100px" prop="imphead_date">
               <el-date-picker class="iot-w240" clearable v-model="ruleForm.imphead_date" type="date" placeholder="请选择"></el-date-picker>
             </el-form-item>-->
            <el-form-item label="仓库" label-width="100px" prop="imphead_warehouse_id">
              <el-select  class="iot-w240" v-model="ruleForm.imphead_warehouse_id" placeholder="请选择" @change="getGoodsList"  :disabled="isHeadCanEdit"   >
                <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </div>
          <div class="iot-form-row">
            <el-form-item label="备注" label-width="100px" prop="imphead_remark">
              <el-input type="textarea" :rows="4" :maxlength="200" class="iot-w240" show-word-limit v-model="ruleForm.imphead_remark" clearable :disabled="isHeadCanEdit"></el-input>
            </el-form-item>
          </div>
          <!-- <div class="iot-form-row">
            <el-form-item label="状态" label-width="100px" prop="imphead_execute_flag">
              <el-select class="iot-w240" v-model="ruleForm.imphead_execute_flag" placeholder="请选择">
                <el-option
                  v-for="item in executeList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </div>-->
        </el-form>
        <!-- 物料批次明细 -->
        <div class="iot-table">
          <h1 style="text-align:center;">{{$t('importWarehouseManage.goodsBatchDetail')}}</h1>
          <div class="layout__btns" >
            <div>
              <el-button type="primary" v-if="isCanHandle" plain icon="el-icon-plus" @click="click_add_goods">{{$t('permission.add')}}</el-button>
              <el-button type="primary" v-if="isCanHandle" plain icon="el-icon-edit-outline" @click="handleEditGoods">{{$t('table.edit')}}</el-button>
              <el-button type="primary" plain icon="el-icon-search" @click="handleAnalogScan">{{$t('importWarehouseManage.AnalogScanning')}}</el-button>
              <el-button type="primary" v-if="isCanHandle" plain icon="el-icon-edit-outline" @click="handleDeleteGoods">{{$t('permission.delete')}}</el-button>
            </div>
          </div>
          <el-form  ref="goodsDialogForm" :model="goodsTableData[goodsTableData.length - 1]" :rules="goodsRules" class="impCss">
            <el-table :data="goodsTableData" stripe style="width: 100%" border ref="goodsTableRef" @row-dblclick="click_edit_goods" @selection-change="handleSelectionChangeGoods"   :header-cell-class-name="must">
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column align="center" :label="$t('public.serialNumber')" width="50">
                <template slot-scope="scope">{{scope.row.impbody_list_id}}</template>
              </el-table-column>
              <el-table-column align="center" prop="impbody_goods_id" :label="$t('importWarehouseManage.goodsCode')" min-width="120" show-overflow-tooltip  >
                <template slot-scope="scope">
                  <span v-if="(!scope.row.isCanEdit || scope.row.impbody_binding_quantity!=0 || scope.row.impbody_fulfill_quantity!=0) && dialogTitle!='新增' ">{{toGoodsCode(scope.row)}}</span>
                  <el-select v-else v-model="scope.row.impbody_goods_id" :placeholder="$t('import.pleaseselect')"  clearable  filterable  >
                    <el-option v-for="item in goodsList" :key="item.id" :label="item.goods_code" :value="item.id"></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column align="center" prop="impbody_goods_id" :label="$t('importWarehouseManage.goodsName')" min-width="120" show-overflow-tooltip >
                <template slot-scope="scope">
                  <span v-if="(!scope.row.isCanEdit || scope.row.impbody_binding_quantity!=0 || scope.row.impbody_fulfill_quantity!=0) && dialogTitle!='新增' " >{{toGoodsName(scope.row)}}</span>
                  <el-select v-show="scope.row.isCanEdit" v-model="scope.row.impbody_goods_id" :placeholder="$t('import.pleaseselect')"  clearable filterable >
                    <el-option v-for="item in goodsList" :key="item.id" :label="item.goods_name" :value="item.id"></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column align="center" prop="impbody_batch_no" :label="$t('importWarehouseManage.bigBatchNum')" min-width="100" show-overflow-tooltip>
                <template slot-scope="scope">
                  <span v-if="(!scope.row.isCanEdit || scope.row.impbody_binding_quantity!=0 || scope.row.impbody_fulfill_quantity!=0) && dialogTitle!='新增' ">{{scope.row.impbody_batch_no}}</span>
                  <el-input v-show="scope.row.isCanEdit" v-model="scope.row.impbody_batch_no" maxlength="50" clearable></el-input>
                </template>
              </el-table-column>
              <el-table-column align="center" prop="impbody_batch_no" :label="$t('importWarehouseManage.smallBatchNum')" min-width="100" show-overflow-tooltip>
                <template slot-scope="scope">
                  <span v-if="(!scope.row.isCanEdit || scope.row.impbody_binding_quantity!=0 || scope.row.impbody_fulfill_quantity!=0) && dialogTitle!='新增' ">{{scope.row.impbody_lots_no}}</span>
                  <el-input v-show="scope.row.isCanEdit" v-model="scope.row.impbody_lots_no" maxlength="50" clearable></el-input>
                </template>
              </el-table-column>
              <el-table-column align="center" prop="impbody_plan_quantity" :label="$t('importWarehouseManage.planNum')" min-width="100" show-overflow-tooltip>
                <template slot-scope="scope">
                  <span v-show="!scope.row.isCanEdit">{{scope.row.impbody_plan_quantity}}</span>
                  <el-input type="number" v-show="scope.row.isCanEdit" v-model="scope.row.impbody_plan_quantity" maxlength="20" clearable @blur="changeNo(scope.row)" ></el-input>
                </template>
              </el-table-column>
              <el-table-column align="center" prop="impbody_binding_quantity" :label="$t('importWarehouseManage.boundNum')" min-width="100" show-overflow-tooltip>
                <template slot-scope="scope">
                  <span v-show="!scope.row.isCanEdit">{{scope.row.impbody_binding_quantity}}</span>
                  <el-input type="number" v-show="scope.row.isCanEdit" v-model="scope.row.impbody_binding_quantity" maxlength="20" clearable :disabled="true"></el-input>
                </template>
              </el-table-column>
              <el-table-column align="center" prop="impbody_fulfill_quantity" :label="$t('importWarehouseManage.completionNum')" min-width="100" show-overflow-tooltip>
                <template slot-scope="scope">
                  <span v-show="!scope.row.isCanEdit">{{scope.row.impbody_fulfill_quantity}}</span>
                  <el-input type="number" v-show="scope.row.isCanEdit" v-model="scope.row.impbody_fulfill_quantity" maxlength="20" clearable :disabled="true"></el-input>
                </template>
              </el-table-column>
              <el-table-column align="center" prop="impbody_quality_status" :label="$t('importWarehouseManage.qalityStatus')" min-width="100" show-overflow-tooltip>
                <template slot-scope="scope">
                  <span v-show="!scope.row.isCanEdit">{{toQuality(scope.row)}}</span>
                  <el-select v-show="scope.row.isCanEdit" v-model="scope.row.impbody_quality_status" :placeholder="$t('import.pleaseselect')"  clearable>
                    <el-option v-for="item in qualityList" :key="item.id" :label="item.quality_name" :value="item.id"></el-option>
                  </el-select>
                </template>
              </el-table-column>
              <el-table-column align="center" prop="impbody_remark" :label="$t('customTypeInfo.note')" min-width="100" maxlength="50" clearable show-overflow-tooltip>
                <template slot-scope="scope">
                  <span v-show="!scope.row.isCanEdit">{{scope.row.impbody_remark}}</span>
                  <el-input v-show="scope.row.isCanEdit" v-model="scope.row.impbody_remark"></el-input>
                </template>
              </el-table-column>
            </el-table>
          </el-form>
        </div>
      </div>
      <div v-else class="iot-form" ref="billPrint">
        <el-form inline label-width="100px" :model="searchForm">
          <el-form-item label="合同号">
            <el-input class="iot-w200" placeholder v-model="ruleForm.imphead_external_code" maxlength="50" clearable></el-input>
          </el-form-item>
          <el-form-item label="入库单编号">
            <el-input disabled class="iot-w200" placeholder v-model="ruleForm.imphead_code" maxlength="50" clearable></el-input>
          </el-form-item>
          <el-form-item label="仓库" label-width="100px" prop="imphead_warehouse_id">
            <el-select  class="iot-w240" v-model="ruleForm.imphead_warehouse_id" placeholder="请选择" @change="getGoodsList"  :disabled="isHeadCanEdit"   >
              <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="供应商" label-width="100px" prop="imphead_custom_id">
            <el-select class="iot-w240" v-model="ruleForm.imphead_custom_id" placeholder="请选择" :disabled="isHeadCanEdit">
              <el-option v-for="item in customList" :key="item.id" :label="item.custom_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="单据日期">
            <el-input class="iot-w200" placeholder v-model="ruleForm.imphead_date" maxlength="50" clearable></el-input>
          </el-form-item>
          <el-form-item label="单据类型" label-width="100px" prop="imphead_bill_id">
            <el-select class="iot-w240" v-model="ruleForm.imphead_bill_id" placeholder="请选择" clearable :disabled="isHeadCanEdit">
              <el-option v-for="item in billTypeList" :key="item.id" :label="item.bill_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div class="iot-table">
          <el-table :data="goodsTableData" stripe style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange">
            <!-- <el-table-column type="selection" width="55"></el-table-column> -->
            <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
            <el-table-column align="center" prop="impbody_goods_id" label="物资编码" min-width="100" show-overflow-tooltip :formatter="toGoodsCode"></el-table-column>
            <el-table-column align="center" prop="impbody_goods_id" label="物资名称" min-width="100" show-overflow-tooltip :formatter="toGoodsName"></el-table-column>
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
            <el-table-column align="center" prop="impbody_custom_id" label="供应商" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" prop="impbody_batch_no" label="批次号" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" prop="imphead_date" label="出厂日期" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" prop="imphead_date" label="出厂编号" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" prop="imphead_unit" label="单位" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" prop="impbody_binding_quantity" label="数量" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" prop="imphead_date" label="单价" min-width="100" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" prop="imphead_date" label="金额" min-width="100" show-overflow-tooltip></el-table-column>
          </el-table>
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button v-if="isCanHandle" size="small" type="primary" @click="click_submit">保 存</el-button>
      </span>
    </el-dialog>

    <importbilldialog :title="diatitle"   :dialogVisible="dialogVisibleA"  :rowlistinfo="rowlistinfo" @updataDialogVisible="updataDialogVisible($event)"    @GetAllGoods="GetAllGoods($event)"></importbilldialog>









  </div>
</template>

<script>
  import textConfig from "@/mixins/textConfig.js";
  import config from "@/mixins/config.js";
  import singlePictureUpload from "_c/singlePictureUpload";
  import importbilldialog from "./component/importbilldialog"
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
    addGoodsListApi,
    editGoodsListApi,
    deleteGoodsListApi,
    addMainTaskApi,
    GetWarehousematerialsInfo,
    GetEncodingRuleCode,
    CreateImportBillApi,
    UpdateImportBillApi,
    deleteListApi,
auditListApi,
NoauditListApi,

  } from "./api";
  import Importbilldialog from "./component/importbilldialog";
  export default {
    //全局混入提示文字
    mixins: [textConfig,config],
    components: {
      Importbilldialog,
      singlePictureUpload,
      importbilldialog
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
        rowlistinfo:[],
        multipleSelection: [],
        multipleSelectionGoods: [],
        radio1: "",
        searchForm: {},
        dialogTitle: "新增",
        dialogTitleGoods: "新增",
        tableData: [],
        goodsTableData: [],
        customList: [],
        warehouseList: [],
        billTypeList: [],
        goodsList: [],
        qualityList: [],
        pendingDeleteGoods: [],
        pendingCreateGoods: [],
        pendingEditGoods: [],
        head: {},
        idList: [],
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
        goodsTotal: 0,
        dialogVisible: false,
        dialogVisibleA:false,
        dialogVisibleGoods: false,
        isCanHandle: true,
        isCanselect:false,
        isNewAdd:false,
        //表单数据
        ruleForm: {
          imphead_code: "",
          imphead_external_code: "",
          imphead_date: "",
          imphead_bill_id: "",
          imphead_custom_id: "",
          imphead_warehouse_id: "",
          imphead_execute_flag: "1",
          imphead_remark: ""
        },
        //表单验证规则
        rules: {
          imphead_code: [
            {
              required: true,
              message: "请输入入库单号",
              trigger: "blur"
            }
          ],
          imphead_date: [
            {
              required: true,
              message: "请选择入库日期",
              trigger: "blur"
            }
          ],
          imphead_bill_id: [
            {
              required: true,
              message: "请选择单据类型",
              trigger: "blur"
            }
          ],
          imphead_warehouse_id: [
            {
              required: true,
              message: "请选择仓库",
              trigger: "blur"
            }
          ]
        },
        //物料表单数据
        goodsRuleForm: {
          impbody_goods_id: "",
          impbody_execute_flag: "",
          impbody_batch_no: "",
          impbody_stock_code: "",
          impbody_vaildate_date: "",
          remark: "",
          impbody_binding_quantity: "",
          impbody_external_listid: "",
          impbody_warehouse_id:""
        },
        //物料表单验证规则
        goodsRules: {
          impbody_execute_flag: [
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
        },
        isHeadCanEdit:false,
        diatitle:'',
      };
    },
    mounted() {
      this.GetAll();
      this.getCustomList();
      this.GetWarehouseList();
      this.getBillTypeList();
      this.getQualityList();
      this.btnInit();


    },
    computed:{

    },
    methods: {

      //修改模拟扫描隐藏显示状态
      updataDialogVisible(val){
        this.dialogVisibleA=val;
      },

      //点击模拟扫描按钮
      handleAnalogScan() {
        this.diatitle='模拟扫描';
        /*this.rowlistinfo.push({
                  name: '1231',
        })*/
        /* this.rowlistinfo={
           name: '1231111111',
         };*/
        if (this.multipleSelectionGoods.length == 1) {
          let impbody_bill_bar=this.multipleSelectionGoods[0].impbody_bill_bar
          let goods_name=this.multipleSelectionGoods[0].goodsInfo.goods_name
          let goods_standard=this.multipleSelectionGoods[0].goodsInfo.goods_standard
          let goods_unit=this.multipleSelectionGoods[0].goodsInfo.unit.unit_name
          let impbody_batch_no=this.multipleSelectionGoods[0].impbody_batch_no
          let impbody_lots_no=this.multipleSelectionGoods[0].impbody_lots_no
          let impbody_plan_quantity=this.multipleSelectionGoods[0].impbody_plan_quantity
          let impbody_binding_quantity=this.multipleSelectionGoods[0].impbody_binding_quantity
          let impbody_quality_status= this.toQualityName(this.multipleSelectionGoods[0].impbody_quality_status)
          let imporder_quality_status= this.multipleSelectionGoods[0].impbody_quality_status
          let imporder_goods_id=this.multipleSelectionGoods[0].goodsInfo.id
          let imporder_warehouse_id=this.ruleForm.imphead_warehouse_id
          let imporder_body_id=this.multipleSelectionGoods[0].id
          this.rowlistinfo={
            impbody_bill_bar:impbody_bill_bar,
            goods_name:goods_name,
            goods_standard:goods_standard,
            goods_unit:goods_unit,
            impbody_batch_no:impbody_batch_no,
            impbody_lots_no:impbody_lots_no,
            impbody_plan_quantity:impbody_plan_quantity,
            impbody_binding_quantity:impbody_binding_quantity,
            impbody_quality_status:impbody_quality_status,
            imporder_quality_status:imporder_quality_status,
            imporder_goods_id:imporder_goods_id,
            imporder_body_id:imporder_body_id,
            imporder_warehouse_id:imporder_warehouse_id,
          };
        } else {
          //this.text_selectOne  全局定义的提示  在textConfig.js中
          this.$message.error(this.text_selectOne);
          return;
        }
        this.dialogVisibleA=true;
      },
      //根据当前用户权限标识初始化按钮状态
      btnInit() {
        this.$Common.DistributePermission.call(this)
      },
      must(obj) {
        if (obj.columnIndex == 2 ||obj.columnIndex == 3 || obj.columnIndex == 4 || obj.columnIndex == 5 || obj.columnIndex == 6 || obj.columnIndex == 9) {
          return 'must';
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
        await this.$nextTick();
        this.$refs["dialogForm"].resetFields();
        this.searchForm = {};
        this.goodsTableData = [];
        this.pendingDeleteGoods = [];
        this.pendingCreateGoods = [];
        this.pendingEditGoods = [];
        this.$utils.traverseDialogDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.dialogForm);
      },
      //物料表单重置
      async resetGoodsForm() {
        this.dialogVisibleGoods = true;
        await this.$nextTick();
        this.$refs["goodsDialogForm"].resetFields();

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
          impbody_head_id: this.ruleForm.id
        };
        let data = await getListGoodsApi(params);
        if (data) {
          this.goodsTableData = data.items || [];
          this.goodsTotal = data.totalCount || 0;
          if (this.goodsTableData.length) {
            this.goodsTableData.map(ele => {
              ele.isCanEdit = false;
            });
          }
        }
        await this.getGoodsList();
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
      //获取单据类型列表
      async getBillTypeList() {
        let params={
          bill_type:1   //(1入库；2出库)
        }
        let res = await this.$DropBox.getBillTypeList(params);
        console.log('-----------------开始-----------------')
        console.log(res.items)
        console.log('-----------------结束-----------------')
        if (res.items) {
          this.billTypeList = res.items;
        }
      },
      //获取物料信息列表
      async getGoodsList() {
        let params = {
          guid: this.ruleForm.imphead_warehouse_id
        };
        let goodsList = await GetWarehousematerialsInfo(params);
        this.goodsList = goodsList;
        console.log(this.goodsList)
      },
      //获取入库单号
      async GetEncodingRuleCode() {
        let params = {
          code: 'RKCode'
        };
        let code = await GetEncodingRuleCode(params);
        this.ruleForm.imphead_code = code;
      },
      //获取质量状态列表
      async getQualityList() {
        let qualityList = await this.$DropBox.getQualitylist();
        this.qualityList = qualityList.items;
      },
      // 获取单据详情
      async getBillDetail(row) {
        await this.Get(row);
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
            h("span", { style: this.c_primary }, "新增"),
            h("span", null, "】吗？")
          ]),
          showCancelButton: true,
          type: "warning"
        }).then(async action => {
          this.classifyGoodsData();
          let res = await this.CreateImportBillApi()
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
      //创建立库任务
      CreateMainTask(rows) {
        const h = this.$createElement;
        this.$msgbox({
          title: "提示",
          message: h("p", null, [this.isCanselect &&
          h("span", { style: this.c_danger }, "【红色部分不可生成任务】"),
            h("span", null, "确定【"),
            h("span", { style: this.c_primary }, "生成任务"),
            h("span", null, "】吗？")
          ]),
          showCancelButton: true,
          type: "warning"
        }).then(async action => {
          let idList = [];
          this.isCanselect = false;
          rows.forEach(ele => {
            idList.push(ele.id);
          });
          let res = await addMainTaskApi(idList);
          if (res) {
            this.$message({
              type: "success",
              message: "立库成功"
            });
            this.GetAll();
          }
        },cancel=>{
          this.isCanselect = false;
          this.GetAll();
        });
      },
      //操作物料数据传送分类
      classifyGoodsData(id) {
        this.pendingCreateGoods = [];
        this.pendingEditGoods = [];
        this.goodsTableData.forEach(ele => {
          if (ele.isCreate) {
            if (id) {
              ele.impbody_head_id = id;
            }
            this.pendingCreateGoods.push(ele);
          } else if (ele.isEdit) {
            this.pendingEditGoods.push(ele);
          }
        });
      },
      //物料新建
      async CreateGoods() {
        if (this.pendingCreateGoods.length) {
          await addGoodsListApi(this.pendingCreateGoods);
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
          this.filterRows(rows,'只有未执行的任务才能删除！',this.Delete);
        }
      },
      //审核
      handleExamine(){
        if (this.multipleSelection.length == 0) {
          //this.text_deleteRow  全局定义的提示  在textConfig.js中
          this.$message.error(this.text_deleteRow);
        } else {
          this.$msgbox({
            title: "提示",
            message: '确定审核么？',
            showCancelButton: true,
            type: "warning"
          }).then(async action => {
            let rows = this.multipleSelection;  
             let params=[];
            rows.forEach(ele=>{
               params.push(ele.id);
            });
            let res = await auditListApi(params);
            if (res) {
              this.$message({
                type: "success",
                message: "审核成功"
              });
              this.GetAll();
            }
          })
        }
      },
      //弃审
      handleUnexamine(){
        if (this.multipleSelection.length == 0) {
          //this.text_deleteRow  全局定义的提示  在textConfig.js中
          this.$message.error(this.text_deleteRow);
        } else {
          this.multipleSelection = this.multipleSelection.filter(ele=>{
            return ele.imphead_execute_flag == 1          //1.未执行  2.执行中  3.已完成
          });
          if (this.multipleSelection.length === 0){
            this.$message({message:'只有未执行的任务才能弃审',type:'warning'});
            this.GetAll();
            return
          } else {
            this.$msgbox({
              title: "提示",
              message: '确定弃审么？',
              showCancelButton: true,
              type: "warning"
            }).then(async action => {
              let rows = this.multipleSelection;
              let params=[];
              rows.forEach(ele=>{
                params.push(ele.id);
              });
              let res = await NoauditListApi(params);
              if (res) {
                this.$message({
                  type: "success",
                  message: "弃审成功"
                });
                this.GetAll();
              }
            })
          }
        }
      },
      //作废
      handleCancel(){
        if (this.multipleSelection.length == 0) {
          //this.text_deleteRow  全局定义的提示  在textConfig.js中
          this.$message.error(this.text_deleteRow);
        } else {
          this.multipleSelection = this.multipleSelection.filter(ele=>{
            return ele.imphead_execute_flag == 1          //1.未执行  2.执行中  3.已完成
          });
          if (this.multipleSelection.length === 0){
            this.$message({message:'只有未执行的任务才能作废',type:'warning'});
            this.GetAll();
            return
          } else {
            this.$msgbox({
              title: "提示",
              message: '确定作废么？',
              showCancelButton: true,
              type: "warning"
            }).then(async action => {
              let rows = this.multipleSelection;
              rows.forEach(ele=>{
                ele.imphead_noused_flag=2;
              });
              let res = await editListApi(rows);
              if (res) {
                this.$message({
                  type: "success",
                  message: "作废成功"
                });
                this.GetAll();
              }
            })
          }
        }
      },
      //生成任务
      async handleMaintask() {
        if (this.multipleSelection.length == 0) {
          //this.text_deleteRow  全局定义的提示  在textConfig.js中
          this.$message.error(this.text_deleteRow);
        } else {
          let rows = this.multipleSelection;
          this.filterRows(rows,'没有可生成的任务',this.CreateMainTask);
        }
      },
      //筛选是否可以操作的行
      filterRows(rows,message,callback){
        this.multipleSelection = rows.filter(ele=>{
          return ele.imphead_execute_flag == 1          //1.未执行  2.执行中  3.已完成
        });
        if (this.multipleSelection.length != rows.length){
          this.isCanselect = true;
        }
        if (this.multipleSelection.length === 0){
          this.$message({message:message,type:'warning'});
          this.isCanselect = false;
          this.GetAll();
          return
        } else {
          callback(this.multipleSelection);
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
      //改变计划数量    必须大于等于完成数量和绑定数量
      changeNo(row){
        if(row.impbody_plan_quantity < row.impbody_binding_quantity || row.impbody_plan_quantity < row.impbody_fulfill_quantity) {
          this.$message({
            message: '计划数量必须大于等于实际数量和绑定数量！',
            type: 'warning'
          });
          row.impbody_plan_quantity='';
        }
      },
      //点击单据完结按钮
      handleFinish() {
        if (this.multipleSelection.length == 1) {
          this.Finish(this.multipleSelection[0]);
        } else {
          this.$message.error(this.text_selectOne);
        }
      },
      click_edit(row) {
        this.isHeadCanEdit=true;
        this.dialogTitle = "编辑";
        this.resetForm();
        this.Get(row);
        if (row.imphead_execute_flag == 1) {
          this.isCanHandle = true;
        } else {
          this.isCanHandle = false;
        }
        this.isNewAdd = false;
      },
      click_edit_goods(row) {
        row.isEdit = true;
        let goodsData = [];
        for (let item of this.goodsTableData) {
          if (item.id === row.id) {
            item.isCanEdit = true;
          }
          goodsData.push(item);
        }
        this.goodsTableData = goodsData;
      },
      click_search() {
        this.currentPage = 1;
        this.GetAll();
      },
      click_add() {
        this.dialogTitle = "新增";
        this.GetEncodingRuleCode();
        this.resetForm();
        this.isCanHandle = true;
        this.isNewAdd = false;
        this.isHeadCanEdit=false;
      },
      click_add_goods() {
        if(this.isNewAdd && !this.testGoodsForm(this.$refs["goodsDialogForm"].model)){
          this.$message({ message: "请将物料批次明细填写完整", type: "warning" });
          return;
        }
        let data = this.goodsTableData;
        this.isNewAdd = true;
        if (data.length === 0) {
          data.push({
            goodsId: +data.length + 1,
            impbody_list_id:(+data.length + 1),
            impbody_head_id: this.ruleForm.id,
            impbody_bill_bar: this.ruleForm.imphead_code,
            impbody_noused_flag: 1,
            impbody_external_listid: this.ruleForm.imphead_external_code,
            impbody_execute_flag: this.ruleForm.imphead_execute_flag,
            isCreate: true,
            isCanEdit: true,
            impbody_warehouse_id:this.ruleForm.imphead_warehouse_id,
            impbody_binding_quantity:0,
            impbody_fulfill_quantity:0,
          });
        } else {
          data.push({
            goodsId: +data[data.length - 1].goodsId + 1,
            impbody_list_id:(+data.length + 1),
            impbody_head_id: this.ruleForm.id,
            impbody_bill_bar: this.ruleForm.imphead_code,
            impbody_external_listid: this.ruleForm.imphead_external_code,
            impbody_noused_flag: 1,
            impbody_execute_flag: this.ruleForm.imphead_execute_flag,
            isCreate: true,
            isCanEdit: true,
            impbody_warehouse_id:this.ruleForm.imphead_warehouse_id,
            impbody_binding_quantity:0,
            impbody_fulfill_quantity:0,
          });
        }
      },
      click_bill_print() {
        this.dialogTitle = "单据打印";
        this.dialogVisible = true;
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
          } else if (!this.goodsTableData.length) {
            this.$message({ message: "请新增物料批次明细", type: "warning" });
            return;
          }else if(!this.testGoodsTableForm()){
            this.$message({ message: "请将物料批次明细填写完整", type: "warning" });
            return;
          }
          if (this.dialogTitle == "新增") {
            this.Create();
          } else {
            this.Update();
          }
        });
      },
      //判断单个物料信息是否填写完整
      testGoodsForm(model){
        let temModel = {};
        let validArr = [
          'impbody_goods_id',
          'impbody_batch_no',
          'impbody_lots_no',
          'impbody_plan_quantity',
          /*'impbody_binding_quantity',
          'impbody_fulfill_quantity',*/
          'impbody_quality_status',
        ];
        //this.warningGoodsForm();
        validArr.forEach(ele=>{
          if(Object.keys(model).includes(ele)){
            temModel[ele] = model[ele]
          }
        })
        if(validArr.every(ele=>{
          return Object.keys(model).includes(ele)
        }) && Object.keys(temModel).every(key=>{
          // if(key === 'impbody_head_id' || key === 'impbody_remark'){
          //   model[key] = true;
          // }
          return temModel[key]
        })){
          return true
        }else{
          return false
        }
      },
      //判断所有物料信息是否填写完整
      testGoodsTableForm(){
        let allGoods = this.goodsTableData;
        //this.warningGoodsForm();
        return allGoods.every(ele=>{
          return this.testGoodsForm(ele)
        })
      },
      //提示所有物料有无填写完整
      warningGoodsForm(){
        this.$nextTick()
        let goodsTable = this.$refs['goodsTableRef'].bodyWrapper.children[0].rows;
        goodsTable.forEach(ele=>{
          ele.cells.forEach((cell,index)=>{
            if(!cell.innerText && index!=0){
              cell.style.background = 'red';
            }else{
              cell.style.background = '';
            }
          })
        })
      },
      //点击物料保存
      click_submit_goods() {
        if (this.dialogTitleGoods == "查看") {
          this.dialogVisible = false;
          return;
        }
        this.$refs["goodsDialogForm"].validate(valid => {
          if (!valid) {
            return;
          }
          if (this.dialogTitleGoods == "新增") {
            this.CreateGoods();
          } else {
            this.UpdateGoods();
          }
        });
      },
      //根据主键删除
      Delete(rows) {
        let rowlist=[]
        const h = this.$createElement;
        this.$msgbox({
          title: "提示",
          message: h("p", null, [this.isCanselect &&
          h("span", { style: this.c_danger }, "【红色部分不可删除】"),
            h("span", null, "确定【"),
            h("span", { style: this.c_danger }, "删除"),
            h("span", null, "】吗？")
          ]),
          showCancelButton: true,
          type: "warning"
        }).then(async action => {
          let deleteCount = 0;
          this.isCanselect = false;
          for (let i = 0; i < rows.length; i++) {
            rowlist.push(rows[i].id);
          }
          let params = {
            idList: rowlist
          };
          let res = await deleteListApi(JSON.stringify(rowlist));
          if (res) {
            this.$message({
              type: "success",
              message: `删除成功！`
            });
            this.GetAll();
          }
        },cancel=>{
          this.isCanselect = false;
          this.GetAll();
        });
      },
      //新增（新增+编辑+删除走一个接口）
      async UpdateImportBill(){
        let idList = [];
        if (!this.pendingDeleteGoods.length) {
          idList = [];
        }else{
          this.pendingDeleteGoods.forEach(ele => {
            idList.push(ele.id);
          });
        }
        let params = {
          head:this.ruleForm,
          createList:this.pendingCreateGoods,
          updateList:this.pendingEditGoods,
          idList:idList
        }
        let res= await UpdateImportBillApi(params);
        return  res;
      },
      //新增（新增+编辑+删除走一个接口）
      async CreateImportBillApi(){
        let params = {
          head:this.ruleForm,
          createList:this.pendingCreateGoods,
        }
        let res=  await CreateImportBillApi(params)
        return res;
      },
      //删除临时物料
      DeleteTemGoods(rows) {
        let count=0;
        rows.forEach(ele=>{
          if(ele.impbody_binding_quantity>0 || ele.impbody_fulfill_quantity>0)
          {
            count++;
            this.$message({
              message: '绑定数量或完成数量大于零的数据无法删除',
              type: 'warning'
            });
            return
          }
        })
        if (count==0) {
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
          }).then(action => {
            rows.forEach(ele => {
              if(!ele.isCreate){
                this.pendingDeleteGoods.push(ele)
              }
            });
            this.goodsTableData = this.goodsTableData.filter(ele => {
              let tem = {};
              rows.forEach((row, index, arr) => {
                if (row.impbody_list_id === ele.impbody_list_id) {
                  tem = row;
                  arr.splice(index, 1);
                }
              });
              return ele.impbody_list_id !== tem.impbody_list_id;
            });
          });
        }
      },
      //根据idList删除物料
      async DeleteGoods() {
        if (!this.pendingDeleteGoods.length) {
          return;
        }
        let idList = [];
        this.pendingDeleteGoods.forEach(ele => {
          idList.push(ele.id);
        });
        await deleteGoodsListApi(idList);
      },
      //编辑
      Update() {
        const h = this.$createElement;
        this.$msgbox({
          title: "提示",
          message: h("p", null, [
            h("span", null, "确定【"),
            h("span", { style: this.c_primary }, "编辑"),
            h("span", null, "】吗？")
          ]),
          showCancelButton: true,
          type: "warning"
        }).then(async action => {
          // let params = {
          //   ...this.ruleForm
          // };
          this.classifyGoodsData();
          // let res = await editApi(params);
          // await this.CreateGoods();
          // await this.UpdateGoods();
          // await this.DeleteGoods();
          let res = await this.UpdateImportBill();
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
      //物料新建
      async UpdateGoods() {
        if (this.pendingEditGoods.length)
          await editGoodsListApi(this.pendingEditGoods);
      },
      //单据完结
      Finish(row) {
        const h = this.$createElement;
        this.$msgbox({
          title: "提示",
          message: h("p", null, [
            h("span", null, "确定【"),
            h("span", { style: this.c_primary }, "完结"),
            h("span", null, "】吗？")
          ]),
          showCancelButton: true,
          type: "warning"
        }).then(async action => {
          let goodslist=[];
          let count=0;
          let goodsparams = {
            MaxResultCount: 999,
            SkipCount: 0,
            impbody_head_id: row.id
          };
          let data = await getListGoodsApi(goodsparams);
          if (data) {
            goodslist = data.items || [];
          }
          if (goodslist.length) {
            goodslist.forEach(ele=>{
              if(ele.impbody_binding_quantity!=0 && ele.impbody_binding_quantity!=0 && ele.impbody_binding_quantity==ele.impbody_binding_quantity) {
                count++;
              }
            })
            if (count == goodslist.length) {
              /* await this.Get(row);
               let params = {
                 ...this.ruleForm
               };
               params.imphead_execute_flag = 3;*/
              this.multipleSelection[0].imphead_execute_flag=3
              let res = await editApi(this.multipleSelection[0]);
              if (res) {
                this.$message({
                  type: "success",
                  message: "完结成功"
                });
                this.GetAll();
              }
            } else {
              this.$message({
                message: '绑定数量需和完成数量相等且不等于零才能执行单据完结操作！',
                type: 'warning'
              });
            }
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
          imphead_code: res.imphead_code,
          imphead_external_code: res.imphead_external_code,
          imphead_date: res.imphead_date,
          imphead_bill_id: res.imphead_bill_id,
          imphead_warehouse_id: res.imphead_warehouse_id,
          imphead_custom_id: res.imphead_custom_id,
          imphead_remark: res.imphead_remark,
          imphead_execute_flag: res.imphead_execute_flag
        };
        this.ruleForm = obj;
        await this.GetAllGoods();
      },
      //根据物料主键获取信息
      async GetGoods(row) {
        let params = {
          id: row.id
        };
        let res = await getOneGoodsApi(params);
        let obj = {
          id: row.id,
          impbody_external_listid: this.ruleForm.imphead_external_code,
          impbody_goods_id: res.impbody_goods_id,
          impbody_execute_flag: res.impbody_execute_flag,
          impbody_batch_no: res.impbody_batch_no,
          impbody_stock_code: res.impbody_stock_code,
          impbody_vaildate_date: res.impbody_vaildate_date,
          impbody_remark: res.impbody_remark,
          impbody_binding_quantity: res.impbody_binding_quantity,
          impbody_warehouse_id:res.impbody_warehouse_id
        };
        this.goodsRuleForm = obj;
      },
      //仓库ID和name的转换
      warehouseShow(row) {
        let id = row.imphead_warehouse_id;
        let name;
        this.warehouseList.forEach(ele => {
          if (ele.id === id) {
            name = ele.warehouse_name;
          }
        });
        return name;
      },
      //客户类型转换
      customNameShow(row) {
        let id = row.imphead_custom_id;
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
        let date = row.imphead_date;
        return this.$moment(date).format("YYYY-MM-DD");
      },
      //物料编码转换
      toGoodsCode(row) {
        let goodsCode;
        this.goodsList.forEach(ele => {
          if (ele.id === row.impbody_goods_id) {
            goodsCode = ele.goods_code;
          }
        });
        return goodsCode;
      },
      //物料名称转换
      toGoodsName(row) {
        let goodsName;
        this.goodsList.forEach(ele => {
          if (ele.id === row.impbody_goods_id) {
            goodsName = ele.goods_name;
          }
        });
        return goodsName;
      },
      //执行状态转换
      toExecuteStatus(row) {
        let executeStatus;
        this.executeList.forEach(ele => {
          if (ele.value === row.impbody_execute_flag) {
            executeStatus = ele.label;
          }
        });
        return executeStatus;
      },
      //单据类型转换
      toBillType(row) {
        let billTyp;
        this.billTypeList.forEach(ele => {
          if (ele.id === row.imphead_bill_id) {
            billTyp = ele.bill_name;
          }
        });
        return billTyp;
      },
      //转换质量状态形式
      toQuality(row) {
        let qualityStatus;
        this.qualityList.forEach(ele => {
          if (ele.id === row.impbody_quality_status) {
            qualityStatus = ele.quality_name;
          }
        });
        return qualityStatus;
      },
      //转换质量状态形式
      toQualityName(val) {
        let qualityStatus;
        this.qualityList.forEach(ele => {
          if (ele.id === val) {
            qualityStatus = ele.quality_name;
          }
        });
        return qualityStatus;
      },
      //判断是否可选
      tableRowClassName({row, rowIndex}) {
        if (row.imphead_execute_flag != 1) {
          return 'warning-row';
        }
        return '';
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
<style>
  .impCss table th.must div:before {
    content: '*';
    color: #ff1818;
  }
</style>