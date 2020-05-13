<template>
  <div class="iot-list" ref="page">
    <div class="iot-form">
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <el-form-item label="物料编号">
            <el-input
              class="iot-w200"
              placeholder="请输入物料编号"
              maxlength="50"
              v-model="searchForm.goods_code"
            ></el-input>
          </el-form-item>
          <el-form-item label="物料名称">
            <el-input
              class="iot-w200"
              placeholder="请输入物料名称"
              maxlength="50"
              v-model="searchForm.goods_name"
            ></el-input>
          </el-form-item>
          <!--<el-form-item label="存放区域">
						<el-select class="iot-w200" v-model="searchForm.goods_area_id" placeholder="请选择存放区域" clearable>
							<el-option v-for="item in area" :key="item.id" :label="item.area_name" :value="item.id"></el-option>
						</el-select>
          </el-form-item>-->
          <el-form-item label="存放区域">
            <el-cascader
              class="iot-w200"
              v-model="searchForm.goods_area_id"
              :options="diaareaOptions"
			  placeholder="请选择存放区域"
              clearable
            ></el-cascader>
          </el-form-item>
          <el-form-item label="监控策略">
            <el-select
              class="iot-w200"
              v-model="searchForm.goods_monitor_id"
              placeholder="请选择监控策略"
              clearable
            >
              <el-option
                v-for="item in strategyMonitor"
                :key="item.id"
                :label="item.monitor_name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="上架策略">
            <el-select
              class="iot-w200"
              v-model="searchForm.goods_warehousing_id"
              placeholder="请选择上架策略"
              clearable
            >
              <el-option
                v-for="item in strategyWarehousing"
                :key="item.id"
                :label="item.warehousing_name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="分配策略">
            <el-select
              class="iot-w200"
              v-model="searchForm.goods_distribution_id"
              placeholder="请选择分配策略"
              clearable
            >
              <el-option
                v-for="item in strategyDistribution"
                :key="item.id"
                :label="item.distribution_name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button
              class="search__btn"
              type="primary"
              icon="el-icon-search"
              @click="click_search"
            >查询</el-button>
          </el-form-item>
        </el-form>
      </div>

      <div class="layout__btns">
        <div ref="permissions">
          <el-button type="primary" plain icon="el-icon-plus" @click="click_add">新增</el-button>
          <el-button type="primary" plain icon="el-icon-edit-outline" @click="handleEdit">编辑</el-button>
          <el-button type="primary" plain icon="el-icon-delete" @click="handleDelete">删除</el-button>
          <el-button type="primary" plain icon="el-icon-document" @click="exportExcel">导出</el-button>
          <el-button type="primary" plain icon="el-icon-printer" @click="printTable">打印</el-button>
          <el-button
            type="primary"
            plain
            icon="el-icon-edit-outline"
            @click="handleStrategy(1)"
          >监控策略</el-button>
          <el-button
            type="primary"
            plain
            icon="el-icon-edit-outline"
            @click="handleStrategy(2)"
          >上架策略</el-button>
          <el-button
            type="primary"
            plain
            icon="el-icon-edit-outline"
            @click="handleStrategy(3)"
          >分配策略</el-button>
        </div>
        <!-- <div>
					<el-radio-group v-model="radio1">
						<el-button type="primary" plain icon="el-icon-edit-outline" @click="handleStrategy(1)" >监控策略配置</el-button>
						<el-button type="primary" plain icon="el-icon-edit-outline" @click="handleStrategy(2)">上架策略配置</el-button>
						<el-button type="primary" plain icon="el-icon-edit-outline" @click="handleStrategy(3)">分配策略配置</el-button>
					</el-radio-group>
        </div>-->
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
        <el-table-column v-if="visibleSelection" type="selection" width="55"></el-table-column>
        <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
        <el-table-column
          align="center"
          prop="goods_code"
          label="物料编号"
          min-width="100"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          align="center"
          prop="goods_name"
          label="物料名称"
          min-width="100"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          align="center"
          prop="goods_large_qty"
          label="大包装数"
          min-width="100"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          align="center"
          prop="goods_medium_qty"
          label="中包装数"
          min-width="100"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          align="center"
          prop="goods_small_qty"
          label="小包装数"
          min-width="100"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          align="center"
          prop="goods_type"
          label="物料类型"
          min-width="100"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <div v-for="(item,index) in productList" :key="index">
              <span v-if="scope.row.goods_type == item.id">{{item.salesName}}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="goods_model"
          label="型号"
          min-width="100"
          show-overflow-tooltip
        ></el-table-column>
        <el-table-column
          align="center"
          prop="goods_unit"
          label="计量单位"
          min-width="100"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <div v-for="(item,index) in unitList" :key="index">
              <span v-if="scope.row.goods_unit == item.id">{{item.unit_name}}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="goods_area_id"
          label="存放区域"
          min-width="100"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <div v-for="(item,index) in area" :key="index">
              <span v-if="scope.row.goods_area_id == item.id">{{item.area_name}}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="goods_monitor_id"
          label="监控策略"
          min-width="100"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <div v-for="(item,index) in strategyMonitor" :key="index">
              <span v-if="scope.row.goods_monitor_id == item.id">{{item.monitor_name}}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="goods_warehousing_id"
          label="上架策略"
          min-width="100"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <div v-for="(item,index) in strategyWarehousing" :key="index">
              <span v-if="scope.row.goods_warehousing_id == item.id">{{item.warehousing_name}}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="goods_distribution_id"
          label="分配策略"
          min-width="100"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <div v-for="(item,index) in strategyDistribution" :key="index">
              <span v-if="scope.row.goods_distribution_id == item.id">{{item.distribution_name}}</span>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="iot-pagination">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total"
        :page-size="pageSize"
        :current-page.sync="currentPage"
        @current-change="handleCurrentChange"
      ></el-pagination>
    </div>
    <el-dialog
      class="iot-dialog"
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="824px"
      append-to-body
    >
      <el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
        <div class="iot-form-row">
          <el-form-item label="物料编号" label-width="100px" prop="goods_code">
            <el-input
              class="iot-w240"
              v-model="ruleForm.goods_code"
              placeholder="请输入物料编号"
              maxlength="30"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="物料名称" label-width="170px" prop="goods_name">
            <el-input
              class="iot-w240"
              v-model="ruleForm.goods_name"
              placeholder="请输入物料名称"
              maxlength="50"
              clearable
            ></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="简称" label-width="100px" prop="goods_short_name">
            <el-input
              class="iot-w240"
              v-model="ruleForm.goods_short_name"
              placeholder="请输入简称"
              maxlength="50"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="英文名" label-width="170px" prop="goods_en_name">
            <el-input
              class="iot-w240"
              v-model="ruleForm.goods_en_name"
              placeholder="请输入英文名"
              maxlength="50"
              clearable
            ></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="大包装数量" label-width="100px" prop="goods_large_qty">
            <MyNumberInput
              :point="2"
              :max="99999"
              placeholder="请输入大包装数量"
              v-model.number="ruleForm.goods_large_qty"
            ></MyNumberInput>

            <!--						<el-input class="iot-w240" v-model="ruleForm.goods_large_qty" type="number" placeholder="请输入大包装数量" clearable @blur="BlurText($event)"  ></el-input>-->
          </el-form-item>
          <el-form-item label="中包装数量" label-width="170px" prop="goods_medium_qty">
            <MyNumberInput
              :point="2"
              :max="99999"
              placeholder="请输入中包装数量"
              v-model.number="ruleForm.goods_medium_qty"
            ></MyNumberInput>
            <!--						<el-input class="iot-w240" v-model="ruleForm.goods_medium_qty" type="number" placeholder="请输入中包装数量" clearable  @blur="BlurText($event)"></el-input>-->
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="小包装数量" label-width="100px" prop="goods_small_qty">
            <MyNumberInput
              :point="2"
              :max="99999"
              placeholder="请输入小包装数量"
              v-model.number="ruleForm.goods_small_qty"
            ></MyNumberInput>
            <!--						<el-input class="iot-w240" v-model="ruleForm.goods_small_qty" type="number" placeholder="请输入小包装数量" clearable  @blur="BlurText($event)"></el-input>-->
          </el-form-item>
          <el-form-item label="计量单位" label-width="170px" prop="goods_unit">
            <el-select class="iot-w240" v-model="ruleForm.goods_unit" placeholder="请选择计量单位">
              <el-option
                v-for="item in unitList"
                :key="item.id"
                :label="item.unit_name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="最高水位" label-width="100px" prop="goods_water_high">
            <el-input
              class="iot-w240"
              v-model="ruleForm.goods_water_high"
              type="number"
              placeholder="请输入最高水位"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="最低水位" label-width="170px" prop="goods_water_low">
            <el-input
              class="iot-w240"
              v-model="ruleForm.goods_water_low"
              type="number"
              placeholder="请输入最低水位"
              clearable
            ></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="单盘数量" label-width="100px" prop="goods_stock_qty">
            <MyNumberInput
              :point="2"
              :max="99999"
              placeholder="请输入单盘数量"
              v-model.number="ruleForm.goods_stock_qty"
            ></MyNumberInput>
            <!--						<el-input class="iot-w240" v-model="ruleForm.goods_stock_qty" type="number" placeholder="请输入单盘数量" clearable  @blur="BlurText($event)"></el-input>-->
          </el-form-item>
          <el-form-item label="单件重量" label-width="170px" prop="goods_weight">
            <el-input
              class="iot-w240"
              v-model="ruleForm.goods_weight"
              type="number"
              placeholder="请输入单件重量"
              maxlength="30"
              clearable
            ></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="单价" label-width="100px" prop="goods_price">
            <el-input
              class="iot-w240"
              v-model="ruleForm.goods_price"
              type="number"
              placeholder="请输入单价"
              maxlength="30"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="辅助计量单位" label-width="170px" prop="goods_unit2">
            <el-select class="iot-w240" v-model="ruleForm.goods_unit2" placeholder="请输入辅助计量单位">
              <el-option
                v-for="item in unitList"
                :key="item.id"
                :label="item.unit_name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="物料类型" label-width="100px" prop="goods_type">
            <el-select class="iot-w240" v-model="ruleForm.goods_type" placeholder="请选择物料类型">
              <el-option
                v-for="item in productList"
                :key="item.id"
                :label="item.salesName"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="有效期(保质期、天)" label-width="170px" prop="goods_expiry_date">
            <el-input
              class="iot-w240"
              v-model="ruleForm.goods_expiry_date"
              type="number"
              maxlength="30"
              placeholder="请选择有效期(保质期、天)"
              clearable
            ></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="型号" label-width="100px" prop="goods_model">
            <el-input
              class="iot-w240"
              v-model="ruleForm.goods_model"
              placeholder="请输入型号"
              maxlength="30"
              clearable
            ></el-input>
          </el-form-item>
          <!--<el-form-item label="存放区域" label-width="170px" prop="goods_area_id">
						<el-select class="iot-w240" v-model="ruleForm.goods_area_id" placeholder="请选择存放区域">
							<el-option v-for="item in area" :key="item.id" :label="item.area_name" :value="item.id"></el-option>
						</el-select>
          </el-form-item>-->
          <el-form-item label="存放区域" label-width="170px">
            <el-cascader
              class="iot-w240"
              v-model="ruleForm.goods_area_id"
              :options="areaOptions"
              clearable
            ></el-cascader>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="垛型" label-width="100px" prop="goods_pack">
            <el-select class="iot-w240" v-model="ruleForm.goods_pack" placeholder="请选择垛型">
              <el-option
                v-for="item in pack"
                :key="item.id"
                :label="item.pack_name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="监控策略" label-width="170px" prop="goods_monitor_id">
            <el-select class="iot-w240" v-model="ruleForm.goods_monitor_id" placeholder="请选择监控策略">
              <el-option
                v-for="item in strategyMonitor"
                :key="item.id"
                :label="item.monitor_name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="上架策略" label-width="100px" prop="goods_warehousing_id">
            <el-select
              class="iot-w240"
              v-model="ruleForm.goods_warehousing_id"
              placeholder="请选择上架策略"
            >
              <el-option
                v-for="item in strategyWarehousing"
                :key="item.id"
                :label="item.warehousing_name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="分配策略" label-width="170px" prop="goods_distribution_id">
            <el-select
              class="iot-w240"
              v-model="ruleForm.goods_distribution_id"
              placeholder="请选择分配策略"
            >
              <el-option
                v-for="item in strategyDistribution"
                :key="item.id"
                :label="item.distribution_name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="物料描述" label-width="100px" prop="goods_describe">
            <el-input
              class="iot-w240"
              v-model="ruleForm.goods_describe"
              placeholder="请输入物料描述"
              type="textarea"
              :rows="4"
              :maxlength="200"
              show-word-limit
            ></el-input>
          </el-form-item>
          <el-form-item label="外部系统ID" label-width="170px" prop="goods_external_id">
            <el-input
              class="iot-w240"
              v-model="ruleForm.goods_external_id"
              placeholder="请输入外部系统ID"
              maxlength="30"
              clearable
            ></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="物料图片" label-width="100px" prop="applicableVersion">
            <singlePictureUpload></singlePictureUpload>
          </el-form-item>
        </div>
        <!-- <div class="iot-form-row">
					<el-form-item label="修改备注" label-width="100px" prop="remark">GetAll
						<el-input class="iot-w660" v-model="ruleForm.remark" type="textarea" :rows="4" :maxlength="200">
						</el-input>
					</el-form-item>
        </div>-->
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="click_submit">保 存</el-button>
      </span>
    </el-dialog>
    <el-dialog
      class="iot-dialog"
      :title="strategyDialogTitle"
      :visible.sync="strategyDialogVisible"
      width="400px"
      append-to-body
    >
      <el-form inline ref="strategyDialogForm" :model="strategyRuleForm" :rules="strategyRules">
        <div class="iot-form-row">
          <el-form-item
            v-if="strategyRuleForm.strategy_status == 1"
            label="监控策略"
            label-width="100px"
            prop="goods_monitor_id"
          >
            <el-select
              class="iot-w240"
              v-model="strategyRuleForm.strategy_id"
              placeholder="请选择监控策略"
            >
              <el-option
                v-for="item in strategyMonitor"
                :key="item.id"
                :label="item.monitor_name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            v-else-if="strategyRuleForm.strategy_status == 2"
            label="上架策略"
            label-width="100px"
            prop="goods_warehousing_id"
          >
            <el-select
              class="iot-w240"
              v-model="strategyRuleForm.strategy_id"
              placeholder="请选择上架策略"
            >
              <el-option
                v-for="item in strategyWarehousing"
                :key="item.id"
                :label="item.warehousing_name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item
            v-else-if="strategyRuleForm.strategy_status == 3"
            label="分配策略"
            label-width="100px"
            prop="goods_distribution_id"
          >
            <el-select
              class="iot-w240"
              v-model="strategyRuleForm.strategy_id"
              placeholder="请选择分配策略"
            >
              <el-option
                v-for="item in strategyDistribution"
                :key="item.id"
                :label="item.distribution_name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="strategyDialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="click_strategySubmit">保 存</el-button>
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
  getstrategyMonitorListApi,
  getstrategyWarehousingListApi,
  getstrategyDistributionListApi,
  getAreaListApi,
  getPackListApi,
  getUnitListApi,
  editStrategyApi,
  deleteListApi,
  getWarehouseAreaListApi
} from "./api";
import MyNumberInput from "../../MyNumberInput";
import Utils from "@/utils/index";
export default {
  //全局混入提示文字
  mixins: [textConfig],
  components: {
    MyNumberInput,
    singlePictureUpload
  },
  props: ["lang"],
  data() {
    return {
      warehousearea: "",
      visibleSelection: true,
      multipleSelection: [],
      strategyMonitor: [],
      strategyWarehousing: [],
      strategyDistribution: [],
      area: [],
      pack: [],
      unitList: [],
      radio1: "",
      searchForm: {},
      dialogTitle: "新增",
      strategyDialogTitle: "监控策略",
      tableData: [],
      productList: [
        { id: 1, salesName: "成品" },
        { id: 2, salesName: "原辅料" },
        { id: 3, salesName: "半成品" }
      ],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      strategyDialogVisible: false,
      //表单数据
      ruleForm: {
        goods_code: "",
        goods_name: "",
        goods_short_name: "",
        goods_en_name: "",
        goods_large_qty: "",
        goods_medium_qty: "",
        goods_small_qty: "",
        goods_model: "",
        goods_unit: "",
        goods_pack: "",
        goods_external_id: "",
        goods_water_high: "",
        goods_water_low: "",
        goods_stock_qty: "",
        goods_weight: "",
        goods_price: "",
        goods_unit2: "",
        goods_type: "",
        goods_expiry_date: 0,
        goods_area_id: "",
        goods_monitor_id: "",
        goods_warehousing_id: "",
        goods_distribution_id: "",
        goods_describe: ""
      },
      areaOptions: [],
      diaareaOptions: [],
      strategyRuleForm: {
        strategy_status: 1,
        idList: [],
        strategy_id: ""
      },
      //表单验证规则
      rules: {
        goods_code: [
          {
            required: true,
            message: "请输入物料编号",
            trigger: "blur"
          },
          {
            min: 1,
            max: 30,
            message: "请输入1-30位字符",
            trigger: "blur"
          }
        ],
        goods_name: [
          {
            required: true,
            message: "请输入物料名称",
            trigger: "blur"
          }
        ],
        goods_unit: [
          {
            required: true,
            message: "请选择计量单位",
            trigger: "blur"
          }
        ]
        /*goods_short_name: [{
						required: true,
						message: "请输入简称",
						trigger: "blur"
					}],
					goods_en_name: [{
						required: true,
						message: "请输入英文名",
						trigger: "blur"
					}],
					goods_large_qty: [{
						required: true,
						message: "请输入大包装数量",
						trigger: "blur"
					}],
					goods_medium_qty: [{
						required: true,
						message: "请输入中包装数量",
						trigger: "blur"
					}],
					goods_small_qty: [{
						required: true,
						message: "请输入小包装数量",
						trigger: "blur"
					}],

					goods_water_high: [{
						required: true,
						message: "请输入最高水位",
						trigger: "blur"
					}],
					goods_water_low: [{
						required: true,
						message: "请输入最低水位",
						trigger: "blur"
					}],
					goods_stock_qty: [{
						required: true,
						message: "请输入单盘数量",
						trigger: "blur"
					}],
					goods_weight: [{
						required: true,
						message: "请输入单件重量",
						trigger: "blur"
					}],
					goods_price: [{
						required: true,
						message: "请输入单价",
						trigger: "blur"
					}],
					goods_unit2: [{
						required: true,
						message: "请输入辅计量单位",
						trigger: "blur"
					}],
					goods_type: [{
						required: true,
						message: "请输入类型",
						trigger: "blur"
					}],
					goods_expiry_date: [{
						required: true,
						message: "请选择有效期(保质期、天)",
						trigger: "blur"
					}],
					goods_model: [{
						required: true,
						message: "请输入型号",
						trigger: "blur"
					}],
					goods_area_id: [{
						required: true,
						message: "请选择存放区域",
						trigger: "blur"
					}],
					goods_pack: [{
						required: true,
						message: "请选择垛型",
						trigger: "blur"
					}],
					goods_monitor_id: [{
						required: true,
						message: "请选择监控策略",
						trigger: "blur"
					}],
					goods_warehousing_id: [{
						required: true,
						message: "请选择上架策略",
						trigger: "blur"
					}],
					goods_distribution_id: [{
						required: true,
						message: "请选择分配策略",
						trigger: "blur"
					}],
					goods_describe: [{
						required: true,
						message: "请输入物料描述",
						trigger: "blur"
					}],
					goods_external_id: [{
						required: true,
						message: "请输入外部系统ID",
						trigger: "blur"
					}]*/
      },
      strategyRules: {
        /*goods_monitor_id: [{
						required: true,
						message: "请选择监控策略",
						trigger: "blur"
					}],
					goods_warehousing_id: [{
						required: true,
						message: "请选择上架策略",
						trigger: "blur"
					}],
					goods_distribution_id: [{
						required: true,
						message: "请选择分配策略",
						trigger: "blur"
					}]*/
      },
      deleteList: [],
      keyDownDel: false,
      companyId: ""
    };
  },
  watch: {
    lang: function(lang) {
      console.log(lang)
      this.$utils.traversePageDom.call(this.$utils,this.$store.state.currentLang,lang,this.$refs.page);
      this.$utils.traverseFormValidator(this.rules,this.lang)
    }
  },
  mounted() {
	console.log(this.$refs.page)
    this.btnInit();
    this.getstrategyMonitorList();
    this.getstrategyWarehousingList();
    this.getstrategyDistributionList();
    this.getAreaList();
    this.getPackList();
    this.getUnitList();
	  this.GetAll();
    if (Utils.getStorage("userInfo")) {
      this.companyId = Utils.getStorage("userInfo").data.result.companyId;
    }

    var that = this;
    window.document.onkeydown = function(event) {
      let e = event || window.event || arguments.callee.caller.arguments[0];
      if (e.keyCode == 8 || e.keyCode == 46) {
        that.keyDownDel = true;
      } else {
        that.keyDownDel = false;
      }
  };
	
  },
  methods: {
    //根据当前用户权限标识初始化按钮状态1
    btnInit() {
      this.$Common.DistributePermission.call(this);
    },
    BlurText(e) {
      if (e.target.value) {
        /*let boolean= new RegExp("^[1-9][0-9]*$").test(e.target.value)
					if(!boolean)
					{
						this.$message.warning('请输入正整数');
						e.target.value='';
					}*/
        let val = e.target.value;
        // 解决首位直接输入 '0开头的数字'问题
        if (val.charAt(0) == 0 && val.charAt(0) == ".") {
          return;
        }
        // 解决数字键盘可以输入输入多个小数点问题
        if (Math.abs(this.value) > 0 && val === "" && value === "") {
          if (this.keyDownDel) {
            this.$el.value = ""; // 正常删除
            console.log("---正常删除---" + this.value);
          } else {
            this.$el.value = this.value; // 多次输入小数点时
            console.log("---多次输入小数点---" + this.value);
          }
          return;
        }
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
      await this.$refs["dialogForm"].resetFields();
	    this.$utils.traverseDialogDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.dialogForm);
    },
    async resetStrategyForm() {
      this.strategyDialogVisible = true;
      await this.$nextTick();
      this.$refs["strategyDialogForm"].resetFields();
    },
    //列表
    async GetAll() {
      if (this.searchForm.goods_area_id) {
        this.searchForm.goods_area_id = this.searchForm.goods_area_id[1];
      }

      let params = {
        MaxResultCount: this.pageSize,
        SkipCount: (this.currentPage - 1) * this.pageSize,
        ...this.searchForm
      };

      /*let areaid=this.warehousearea[1]

				params.goods_area_id=areaid;*/

      let data = await getListApi(params);
      if (data) {
        this.tableData = data.items || [];
        this.total = data.totalCount || 0;
      }
      this.$utils.traversePageDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.page);
      this.$utils.traverseFormValidator(this.rules,this.lang)
    },
    //获取监控策略
    async getstrategyMonitorList() {
      let params = {};
      let data = await getstrategyMonitorListApi(params);
      if (data) {
        this.strategyMonitor = data.items || [];
      }
    },
    //获取上架策略
    async getstrategyWarehousingList() {
      let params = {};
      let data = await getstrategyWarehousingListApi(params);
      if (data) {
        this.strategyWarehousing = data.items || [];
      }
    },
    //获取分配策略
    async getstrategyDistributionList() {
      let params = {};
      let data = await getstrategyDistributionListApi(params);
      if (data) {
        this.strategyDistribution = data.items || [];
      }
    },
    //获取区域
    async getAreaList() {
      let params = {};
      let data = await getWarehouseAreaListApi(params);
      if (data) {
        this.areaOptions = data;
        this.diaareaOptions = data;
      }
      let areadata = await getAreaListApi(params);
      if (areadata) {
        this.area = areadata;
      }
    },
    //获取垛型
    async getPackList() {
      let params = {};
      let data = await getPackListApi(params);
      if (data) {
        this.pack = data.items || [];
      }
    },
    //获取单位
    async getUnitList() {
      let params = {
        unit_is_enable: 1
      };
      let data = await getUnitListApi(params);
      if (data) {
        this.unitList = data.items || [];
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
        if (this.ruleForm.goods_area_id) {
          this.ruleForm.goods_area_id = this.ruleForm.goods_area_id[1];
        }

        let params = {
          ...this.ruleForm
        };
        params.goods_company_id = this.companyId;
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
    //点击策略配置按钮
    handleStrategy(strategy) {
      if (this.multipleSelection.length == 0) {
        //this.text_deleteRow  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_deleteRow);
      } else {
        let rows = this.multipleSelection;
        this.click_strategy(rows, strategy);
      }
    },
    click_edit(row) {
      this.dialogTitle = "编辑";
      this.getstrategyMonitorList();
      this.getstrategyWarehousingList();
      this.getstrategyDistributionList();
      this.getAreaList();
      this.getPackList();
      this.getUnitList();
      this.resetForm();
      this.Get(row);
    },
    click_search() {
      this.currentPage = 1;
      this.GetAll();
    },
    click_add() {
      this.dialogTitle = "新增";
      this.getstrategyMonitorList();
      this.getstrategyWarehousingList();
      this.getstrategyDistributionList();
      this.getAreaList();
      this.getPackList();
      this.getUnitList();
      this.resetForm();
    },
    click_strategy(rows, strategy) {
      this.strategyRuleForm.strategy_status = strategy;
      this.strategyRuleForm.idList = [];
      this.strategyRuleForm.strategy_id = "";
      if (strategy == 1) {
        this.strategyDialogTitle = "监控策略配置";
        this.getstrategyMonitorList();
      } else if (strategy == 2) {
        this.strategyDialogTitle = "上架策略配置";
        this.getstrategyWarehousingList();
      } else {
        this.strategyDialogTitle = "分配策略配置";
        this.getstrategyDistributionList();
      }
      for (let i = 0; i < rows.length; i++) {
        this.strategyRuleForm.idList[i] = rows[i].id;
      }
      this.resetStrategyForm();
    },
    //点击保存
    click_submit() {
      if (this.dialogTitle == "查看") {
        this.dialogVisible = false;
        return;
      }
      this.$refs["dialogForm"].validate(valid => {
        if (!valid) {
          console.log(this.$refs.dialogForm)
          return;
        }
        if (this.dialogTitle == "新增") {
          this.Create();
        } else {
          this.Update();
        }
      });
    },
    //批量配置策略 保存
    click_strategySubmit() {
      const h = this.$createElement;
      this.$msgbox({
        title: "提示",
        message: h("p", null, [
          h("span", null, "确定【"),
          h("span", { style: this.c_danger }, this.strategyDialogTitle),
          h("span", null, "】吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        let params = {
          ...this.strategyRuleForm
        };

        let res = await editStrategyApi(params);
        if (res) {
          this.$message({
            type: "success",
            message: "新增成功"
          });
          this.GetAll();
          this.strategyDialogVisible = false;
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
          h("span", { style: this.c_danger }, "删除"),
          h("span", null, "吗？")
        ]),
        showCancelButton: true,
        type: "error"
      }).then(async action => {
        let deleteCount = 0;
        this.deleteList = "";
        for (let i = 0; i < rows.length; i++) {
          /*let res = await deleteApi({id:rows[i].id});
						if(res){
							deleteCount++
						}*/
          this.deleteList.push(rows[i].id);
          deleteCount++;
        }
        /* if(deleteCount){
                        this.$message({
                            type: "success",
                            message: `成功删除${deleteCount}条,删除失败${rows.length-deleteCount}条`
                        });
                    }*/
        if (this.deleteList.length) {
          let res = await deleteListApi(JSON.stringify(this.deleteList));
          if (res) {
            this.$message({
              type: "success",
              message: "删除成功"
            });
          }
          /*else
                        {
                            this.$message({
                                type: "error",
                                message: '系统繁忙请稍后再试'
                            });
                        }*/
        }

        if ((this.total - deleteCount) % 10 == 0 && this.currentPage != 1) {
          this.currentPage -= 1;
        }
        this.GetAll();
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
        if (this.ruleForm.goods_area_id) {
          this.ruleForm.goods_area_id = this.ruleForm.goods_area_id[1];
        }

        let params = {
          ...this.ruleForm
        };
        params.goods_company_id = this.companyId;
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
        goods_code: res.goods_code,
        goods_name: res.goods_name,
        goods_short_name: res.goods_short_name,
        goods_en_name: res.goods_en_name,
        goods_large_qty: res.goods_large_qty,
        goods_medium_qty: res.goods_medium_qty,
        goods_small_qty: res.goods_small_qty,
        goods_model: res.goods_model,
        goods_unit: res.goods_unit,
        goods_pack: res.goods_pack,
        goods_external_id: res.goods_external_id,
        goods_water_high: res.goods_water_high,
        goods_water_low: res.goods_water_high,
        goods_stock_qty: res.goods_stock_qty,
        goods_weight: res.goods_weight,
        goods_price: res.goods_price,
        goods_unit2: res.goods_unit2,
        goods_type: res.goods_type,
        goods_expiry_date: res.goods_expiry_date,
        goods_area_id: res.goods_area_id,
        goods_monitor_id: res.goods_monitor_id,
        goods_warehousing_id: res.goods_warehousing_id,
        goods_distribution_id: res.goods_distribution_id,
        goods_describe: res.goods_describe
      };
      this.ruleForm = obj;
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