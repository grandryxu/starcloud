<template>
  <div class="iot-list" ref="page">
    <div class="iot-form">
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <div class="iot-form-row">
            <el-form-item label="抽检单号">
              <el-input class="iot-w200" placeholder clearable v-model="searchForm.check_bill"></el-input>
            </el-form-item>
            <el-form-item label="批次">
              <el-input class="iot-w200" placeholder clearable v-model="searchForm.check_batch_no"></el-input>
            </el-form-item>
            <el-form-item label="物资名称">
              <el-input class="iot-w200" placeholder clearable v-model="searchForm.check_goods_name"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">查询</el-button>
            </el-form-item>
          </div>
          <div class="iot-form-row">
            <el-form-item label="原质量状态">
              <el-select class="iot-w200" v-model="searchForm.origin_quality_status" placeholder="请选择" clearable>
                <el-option v-for="item in qualityList" :key="item.id" :label="item.quality_name" :value="item.quality_name"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="检测后质量状态">
              <el-select class="iot-w200" v-model="searchForm.checked_quality_status" placeholder="请选择" clearable>
                <el-option v-for="item in qualityList" :key="item.id" :label="item.quality_name" :value="item.quality_name"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="抽检日期">
              <el-date-picker class="iot-w280" clearable v-model="searchForm.check_time" type="daterange" range-separator="-"></el-date-picker>
            </el-form-item>
          </div>
        </el-form>
      </div>
      <div class="layout__btns">
        <div ref="permissions">
          <el-button type="primary" plain icon="el-icon-plus" @click="click_add">新增</el-button>
          <el-button type="primary" plain icon="el-icon-edit-outline" @click="handleEdit">编辑</el-button>
          <el-button type="primary" plain icon="el-icon-delete" @click="handleDelete">删除</el-button>
          <el-button type="primary" plain icon="el-icon-document" @click="exportExcel">导出</el-button>
          <el-button type="primary" plain icon="el-icon-printer" @click="printTable">打印</el-button>
          <el-button type="primary" plain icon="el-icon-tickets" @click="createOutBill">生成出库单</el-button>
          <el-button type="primary" plain icon="el-icon-edit" @click="handleCheck">检测</el-button>
          <el-button type="primary" plain icon="el-icon-s-flag" @click="handleRelease">质量放行</el-button>
        </div>
      </div>
    </div>
    <div class="iot-table">
      <el-table id="out-table" ref="print" :data="tableData" stripe style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
        <el-table-column align="center" prop="check_bill" label="抽检单号" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="check_batch_no" label="批次" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="check_goods_name" label="物资名称" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="check_num" label="抽检数量" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="origin_quality_status" label="原质量状态" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="checked_quality_status" label="检测后质量状态" min-width="100" show-overflow-tooltip :formatter="qualityStatusFormat"></el-table-column>
        <el-table-column align="center" prop="check_time" label="抽检日期" min-width="100" :formatter="dateTransform" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="quality_check_export_code" label="出库单号" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="remark" label="备注" min-width="100" show-overflow-tooltip></el-table-column>
      </el-table>
    </div>
    <div class="iot-pagination">
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
    </div>
    <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
      <!--新增抽检 -->
      <el-form inline ref="dialogForm" :model="addCheckForm" :rules="rules">
        <div class="iot-form-row">
          <el-form-item label="抽检单号" label-width="100px" prop="check_bill">
            <el-input class="iot-w240" v-model="addCheckForm.check_bill" clearable disabled></el-input>
          </el-form-item>
          <el-form-item label="仓库" label-width="100px" prop="inventory_warehouse_id">
            <el-select class="iot-w240" v-model="addCheckForm.inventory_warehouse_id" placeholder="请选择" @change="GetAllInventoryBatchno">
              <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="单据类型" label-width="100px" prop="bill_type">
            <el-select class="iot-w240" v-model="addCheckForm.bill_type" placeholder="请选择">
              <el-option v-for="item in billTypeList" :key="item.id" :label="item.typeName" :value="item.typeName"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="批次" label-width="100px" prop="check_batch_no">
            <el-select class="iot-w240" v-model="addCheckForm.check_batch_no" placeholder="请选择" @change="batch_change">
              <el-input class="iot-w240" v-model="dropDownValue" @keyup.native="drop_down_search" placeholder="请输入"></el-input>
              <el-option v-for="item in optionsMetaShow" :key="item.inventory_batch_no" :label="item.inventory_batch_no" :value="item.inventory_batch_no"></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="物资名称" label-width="100px" prop="check_batch_no">
            <el-select class="iot-w240" v-model="addCheckForm.check_batch_no" placeholder :disabled="true">
              <!---为了与批次号联动，prop 与v-modal选择与批次一致 --->
              <el-option v-for="item in optionsMetaShow" :key="item.inventory_batch_no" :label="item.goods_name" :value="item.inventory_batch_no"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="质量状态" label-width="100px" prop="check_batch_no">
            <el-select class="iot-w240" v-model="addCheckForm.check_batch_no" placeholder :disabled="true">
              <el-option v-for="item in optionsMetaShow" :key="item.inventory_batch_no" :label="item.quality_status" :value="item.inventory_batch_no"></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="抽检数量" label-width="100px" prop="check_num">
            <el-input class="iot-w240" v-model="addCheckForm.check_num" clearable readonly></el-input>
          </el-form-item>
          <el-form-item label="抽检日期" label-width="100px" prop="check_time">
            <el-input class="iot-w240" v-model="addCheckForm.check_time" readonly></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="备注" label-width="100px" prop="remark">
            <el-input type="textarea" :rows="4" :maxlength="200" class="iot-w240" v-model="addCheckForm.remark" clearable></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <div style="text-align: center;font-size: 30px;padding: 10px 0px;">
            <span>物料批次明细</span>
          </div>
          <div class="iot-table">
            <el-table :data="tableBatchDetail" stripe style="width: 100%" border @selection-change="handleDetailSelectionChange">
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
              <el-table-column align="center" prop="inventory_stock_code" label="托盘" min-width="100" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" prop="inventory_batch_no" label="批号" min-width="100" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" prop="goods.goods_name" label="物料名" min-width="100" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" prop="inventory_quantity" label="入库数量" min-width="100" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" prop="stock_check_quantity" label="抽检数量" min-width="100" show-overflow-tooltip>
                <template slot-scope="scope">
                  <div class="headerFirst">
                    {{ scope.row.stock_check_quantity }}
                    <i class="el-icon-edit-outline editIcon" @click="tableColumnEdit(scope.$index)"></i>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer" style="display:flex;justify-content: center;">
        <el-button size="small" type="primary" @click="click_submit">保 存</el-button>
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
      </div>
      <!-- <div slot="footer" class="dialog-footer" style="display:flex;justify-content: center;">		  
        <el-button size="small" type="danger" @click="dialogVisible = false"><span class="el-icon-close"></span>取 消</el-button>     
      </div>-->
    </el-dialog>
    <!--检测更新状态 -->
    <el-dialog class="iot-dialog" :title="checkDialogTitle" :visible.sync="checkDialogVisible" width="400px" append-to-body>
      <el-form inline ref="checkForm">
        <el-form-item label="检测后质量状态">
          <el-select class="iot-w200" v-model="addCheckForm.checked_quality_status_id" placeholder="请选择" clearable>
            <el-option v-for="item in qualityList" :key="item.id" :label="item.quality_name" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" style="display:flex;justify-content: center;">
        <el-button size="small" type="primary" @click="confirm_check">确 定</el-button>
        <el-button size="small" @click="checkDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import textConfig from "@/mixins/textConfig.js";
import singlePictureUpload from "_c/singlePictureUpload";
import {
  getListApi,
  getOneApi,
  editApi,
  deleteApi,
  addCheckApi,
  getCheckInventory,
  getBatchGoods,
  createCheckDetails,
  getAllCheckDetails,
  updateCheckDetails,
  updateInventoryQuality,
  createOutBillByChecked,
  checkRelease,
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
      //批次下拉列表数据
      dropDownValue: "",
      optionsMetaAll: [],
      optionsMetaShow: [],
      qualityList: [],
      warehouseList: [],
      multipleSelection: [],
      detailMultipleSelection: [],
      radio1: "",
      searchForm: {},
      addCheckForm: {
        check_bill: "",
        check_batch_no: "",
        bill_type: "",
        check_goods_name: "",
        checked_quality_status_id: "",
        checked_quality_status: "",
        origin_quality_status: "",
        inventory_warehouse_id: "",
        check_num: 0,
        check_time: "",
        remark: ""
      },
      dialogTitle: "新增",
      checkDialogTitle: "检测结果",
      tableBatchDetail: [],
      tableData: [],
      billTypeList: [
        { id: 0, typeName: "有损抽检" },
        { id: 1, typeName: "无损抽检" }
      ],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      checkDialogVisible: false,
      // 表单验证规则
      rules: {
        check_bill: [
          {
            required: true,
            message: "请输入抽检单号",
            trigger: "blur"
          }
        ],
        inventory_warehouse_id: [
          {
            required: true,
            message: "请选择仓库"
          }
        ]
      }
    };
  },
  mounted() {
    this.GetAll();
    this.GetWarehouseList();
    this.GetQualityList();
    this.btnInit();
  },
  methods: {
    //根据当前用户权限标识初始化按钮状态
    btnInit() {
      this.$Common.DistributePermission.call(this)
    },
    //表单重置
    async resetForm() {
      this.dialogVisible = true;
      await this.$nextTick();
      this.$refs["dialogForm"].resetFields();
      this.tableBatchDetail = [];
     // this.addCheckForm = { check_num: 0 };
     this.addCheckForm.check_num=0;
      this.get_current_time();
      this.$utils.traverseDialogDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.dialogForm);
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.GetAll();
    },
    //仓库列表
    async GetWarehouseList() {
      let data = await this.$DropBox.getWarehouselist();
      if (data) {
        this.warehouseList = data || [];
      }
    },
    //质量状态下拉
    async GetQualityList() {
      let data = await this.$DropBox.getQualitylist();
      if (data) {
        console.log(data);
        this.qualityList = data.items || [];
      }
    },

    //批次下拉列表
    async GetAllInventoryBatchno() {
      let params = { inventory_warehouse_id: this.addCheckForm.inventory_warehouse_id };
      let data = await getBatchGoods(params);
      if (data) {
        this.optionsMetaAll = data || [];
        this.optionsMetaShow = data || [];
      }
    },

    //批次select控件change事件
    async batch_change(e) {
      //根据选择的批次号，从optionsMetaAll列表中获得对应的物料名，质量状态名。
      this.optionsMetaAll.forEach(item => {
        if (item.inventory_batch_no == e) {
          this.addCheckForm.check_goods_name = item.goods_name;
          this.addCheckForm.check_goods_code = item.goods_code;
          this.addCheckForm.origin_quality_status = item.quality_status;
        }
      });
      let params = { inventory_batch_no: this.addCheckForm.check_batch_no };
      let data = await getCheckInventory(params);
      if (data) {
        this.tableBatchDetail = data || [];
      }
    },
    //日期时间转换
    dateTransform(row) {
      let date = row.check_time;
      return this.$moment(date).format("YYYY-MM-DD");
    },
    //列表
    async GetAll() {
      let rangDate = this.searchForm.check_time;
      if (rangDate) {
        this.searchForm.check_time_b = this.$moment(rangDate[0]).format(
          "YYYY-MM-DD"
        );
        this.searchForm.check_time_e = this.$moment(rangDate[1]).format(
          "YYYY-MM-DD"
        );
      }

      let params = {
        MaxResultCount: this.pageSize,
        SkipCount: (this.currentPage - 1) * this.pageSize,
        ...this.searchForm
      };

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

    // getQualityName() {
    //   if (this.qualityList.length > 0) {
    //     let len = this.qualityList.length;
    //     for (let i = 0; i < len; i++) {
    //       let quality_obj = this.qualityList[i];
    //       if (quality_obj.id == this.addCheckForm.checked_quality_status_id) {
    //         this.addCheckForm.checked_quality_status = quality_obj.quality_name;
    //         break;
    //       }
    //     }
    //   }
    // },
    //质量状态id与name的转换
    qualityStatusFormat(row) {
      let id = row.checked_quality_status_id;
      let name = "";
      this.qualityList.forEach(item => {
        if (item.id === id) {
          name = item.quality_name;
        }
      });
      return name;
    },
    //新建
    Create() {
      if (!Object.keys(this.addCheckForm).length) {
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
          ...this.addCheckForm
        };
        let backres = await addCheckApi(params);
        if (backres) {
          // 保存明细的时候，目前是用的抽检单的单号来关联的，没有用抽检Id。
          let res = await createCheckDetails(this.detailMultipleSelection);
          if (res) {
            this.$message({
              type: "success",
              message: "新增成功"
            });
            //新增成功后将抽检的质量状态同步到库存表中对应的物料质量状态。(此动作改到抽检放行中)
            // let refresh_params = {
            //   inventory_batch_no: this.addCheckForm.check_batch_no,
            //   checked_quality_status_id: this.addCheckForm
            //     .checked_quality_status_id
            // };
            // let refresh_quality_stauts = await updateInventoryQuality(
            //   refresh_params
            // );
            this.GetAll();
            this.dialogVisible = false;
          }
        }
      });
    },
    //当checkbox发生变化时
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //物料明细checkbox发生变化时
    handleDetailSelectionChange(val) {
      this.detailMultipleSelection = val;
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
      console.log(this.multipleSelection);
      if (this.multipleSelection.length == 0) {
        this.$message.error(this.text_deleteRow);
      } else {
        let rows = this.multipleSelection;
        this.Delete(rows);
      }
    },
    click_edit(row) {
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
      this.GetEncodingRuleCode();
    },

    //获取抽检单号
    async GetEncodingRuleCode() {
      let params = {
        code: 'CJCode'
      };
      let code = await GetEncodingRuleCode(params);
      this.addCheckForm.check_bill = code;
    },
    //点击检测，执行状态变更
    handleCheck() {
      if (this.multipleSelection.length == 0) {
        this.$message.error(this.text_deleteRow);
      } else {
        this.checkDialogVisible = true;
      }
    },
    //质量放行操作
    async handleRelease() {
      if (this.multipleSelection.length != 1) {
        this.$message.error(this.text_selectOne);
        return;
      } else {
        let res = await checkRelease(this.multipleSelection[0]);
        if (res) {
          this.$message("放行成功");
          this.GetAll();
        }
      }

    },
    //生成出库单按钮事件
    createOutBill() {
      if (this.multipleSelection.length != 1) {
        this.$message.error(this.text_selectOne);
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
            "保存检测结果"
          ),
          h("span", null, "】吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        let res = await createOutBillByChecked(this.multipleSelection[0]);
        if (res) {
          this.$message("生成出库单成功");
          this.GetAll();
        }
      });
    },
    confirm_check() {
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
            "保存检测结果"
          ),
          h("span", null, "】吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        let rows = this.multipleSelection;
        let checkcount = 0;
        for (let i = 0; i < rows.length; i++) {
          rows[
            i
          ].checked_quality_status_id = this.addCheckForm.checked_quality_status_id;
          let res = await editApi(rows[i]);
          if (res) {
            checkcount++;
          }
        }
        if (checkcount) {
          this.$message("更新保存成功");
          this.checkDialogVisible = false;
          this.GetAll();
        }
      });
    },
    get_current_time() {
      let current_time = new Date();
      this.addCheckForm.check_time =
        current_time.getFullYear() +
        "-" +
        (current_time.getMonth() + 1) +
        "-" +
        current_time.getDate();
    },
    change_check_time_format(timestamp) {
      var time = new Date(timestamp);
      this.addCheckForm.check_time =
        time.getFullYear() + "-" + (time.getMonth() + 1) + "-" + time.getDate();
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
        if (this.detailMultipleSelection.length == 0) {
          this.$message.error(this.text_selectOne);
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
          ...this.addCheckForm
        };
        let res = await editApi(params);
        if (res) {
          let update_check_detail_count = 0;
          for (let i = 0; i < this.tableBatchDetail.length; i++) {
            let detail_res = await updateCheckDetails(
              this.detailMultipleSelection[i]
            );
            if (detail_res) {
              update_check_detail_count++;
            }
          }
          if (update_check_detail_count) {
            this.$message({
              type: "success",
              message: "修改成功"
            });
            // 更新成功后将抽检的质量状态同步到库存表中对应的物料质量状态。(与新增一样，此动作改到抽检放行中)
            // let refresh_params = {
            //   inventory_batch_no: this.addCheckForm.check_batch_no,
            //   checked_quality_status_id: this.addCheckForm
            //     .checked_quality_status_id
            // };
            // let refresh_quality_stauts = await updateInventoryQuality(
            //   refresh_params
            // );
            this.GetAll();
            this.dialogVisible = false;
          }
        }
      });
    },
    click_search() {
      this.currentPage = 1;
      this.GetAll();
    },

    //根据主键获取信息
    async Get(row) {
      let params = {
        id: row.id
      };
      let res = await getOneApi(params);
      if (res) {
        this.addCheckForm = res;
        this.change_check_time_format(Date.parse(res.check_time));
        let detail_params = { check_bill_code: res.check_bill };
        let detail_res = await getAllCheckDetails(detail_params);
        if (detail_res) {
          this.tableBatchDetail = detail_res.items;
        }
      }
    },

    drop_down_search() {
      var _this = this;
      _this.optionsMetaShow = _this.optionsMetaAll.filter(_this.filter_search);
      if (_this.optionsMetaShow.length <= 0) {
        _this.optionsMetaShow = _this.optionsMetaAll;
      }
    },
    filter_search(item) {
      return item.inventory_batch_no.includes(this.dropDownValue);
    },

    //table 单元格编辑
    tableColumnEdit(index) {
      var cellabc = document.getElementsByClassName("headerFirst")[index];
      cellabc.innerHTML = "";
      let cellInput = document.createElement("el-input");
      let cellInputSon = document.createElement("input");
      let cellBut = document.createElement("button");
      let ibtu = document.createElement("i");
      //单元格数值不存在，则默认0，存在，则先扣减当前数值，以免抽检量总数重复叠加。
      cellInputSon.value = this.tableBatchDetail[index].stock_check_quantity;
      if (cellInputSon.value == "undefined") {
        cellInputSon.value = 0;
      } else {
        this.addCheckForm.check_num = parseInt(
          this.addCheckForm.check_num - cellInputSon.value
        );
      }
      cellabc.setAttribute("class", "cellBoxdev");
      ibtu.setAttribute("class", "el-icon-check");
      cellInputSon.setAttribute("class", "el-input__inner compileName");
      cellBut.setAttribute(
        "class",
        "el-button el-button--warning el-button--mini"
      );
      cellBut.innerText = "确定";
      cellInput.style.width = "100%";
      ibtu.style.marginLeft = "3px";
      cellInputSon.style.width = "60%";
      cellInputSon.style.padding = "0 5px 0 0";
      cellInputSon.style.border = "1px solid transparent";
      this.clickButSty = true;
      cellInput.appendChild(cellInputSon);
      cellabc.appendChild(cellInput);
      cellInput.appendChild(cellBut);
      cellBut.appendChild(ibtu);
      cellBut.onclick = () => {
        //成功按钮事件
        //输入值不能大于库存量
        if (
          cellInputSon.value > this.tableBatchDetail[index].inventory_quantity
        ) {
          this.$message.error("抽检量不能大于库存量");
          return;
        }
        cellabc.removeChild(cellInput);
        this.tableBatchDetail[index].stock_check_quantity = cellInputSon.value;
        cellabc.innerHTML = this.tableBatchDetail[index].stock_check_quantity;
        console.log("4", this.tableBatchDetail[index]);
        let ibtuIcon = document.createElement("i");
        ibtuIcon.setAttribute("class", "el-icon-edit-outline eidtIcon");
        cellabc.appendChild(ibtuIcon);
        cellabc.setAttribute("class", "headerFirst");
        this.addCheckForm.check_num += parseInt(
          this.tableBatchDetail[index].stock_check_quantity
        );
        //点击确定时候，将form表单中的抽检单号赋值给当前行（此操作与当前函数的逻辑无关系，只是将抽检单号赋值给当前行的一种取巧方式）
        this.tableBatchDetail[
          index
        ].check_bill_code = this.addCheckForm.check_bill;
        ibtuIcon.onclick = () => {
          this.tableColumnEdit(index);
        };
      };
    },
    //导出表格
    exportExcel() {
      this.$Common.ExportExcel("#out-table")
    },
    //打印表格
    printTable() {
      this.$Common.PrintTable.call(this, 'print')
    }
  }
};
</script>
