<template>
  <div class="iot-list">
    <div class="iot-form">
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <div class="iot-form-row">
            <el-form-item label="入库单">
              <el-input class="iot-w200" placeholder clearable v-model="searchForm.inventory_bill_bar"></el-input>
            </el-form-item>
            <el-form-item label="库位">
              <el-select class="iot-w200" v-model="searchForm.inventory_slot_code" placeholder="请选择" clearable>
                <el-option v-for="item in slotList" :key="item.id" :label="item.slot_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="物料">
              <el-input class="iot-w200" placeholder clearable v-model="searchForm.goods_name"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">查询</el-button>
            </el-form-item>
          </div>
          <div class="iot-form-row">
            <el-form-item label="质量状态">
              <el-select class="iot-w200" v-model="searchForm.inventory_quality_status" placeholder="请选择" clearable>
                <el-option v-for="item in qualityList" :key="item.id" :label="item.quality_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="批次">
              <el-input class="iot-w200" placeholder clearable v-model="searchForm.inventory_batch_no"></el-input>
            </el-form-item>
          </div>
        </el-form>
      </div>
      <div class="layout__btns">
        <div>
          <el-button type="primary" plain icon="el-icon-document" @click="click_checkout">检验放行</el-button>
          <el-button type="primary" plain icon="el-icon-document">导出</el-button>
          <el-button type="primary" plain icon="el-icon-printer">打印</el-button>
        </div>
      </div>
    </div>
    <div class="iot-table">
      <el-table :data="tableData" stripe style="width: 100%" border @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
        <el-table-column align="center" prop="inventory_bill_bar" label="入库单" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="inventory_batch_no" label="批次" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="goods.goods_name" label="物料" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="goods.goods_code" label="物料编码" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="slot.slot_name" label="库位" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="quality.quality_name" label="质量状态" min-width="100" show-overflow-tooltip></el-table-column>
      </el-table>
    </div>
    <div class="iot-pagination">
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
    </div>
    <!-- <el-dialog
      class="iot-dialog"
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="824px"
      append-to-body
    >
      <el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
        <div class="iot-form-row">
          <el-form-item label="入库单" label-width="100px" prop="stock_in_code">
            <el-input class="iot-w240" v-model="ruleForm.stock_in_code" readonly clearable></el-input>
          </el-form-item>
          <el-form-item label="批次" label-width="100px" prop="batch_no">
            <el-input class="iot-w240" v-model="ruleForm.batch_no" readonly clearable></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="物料" label-width="100px" prop="goods_name">
            <el-input class="iot-w240" v-model="ruleForm.goods_name" readonly clearable></el-input>
          </el-form-item>
          <el-form-item label="操作人" label-width="100px" prop="operator">
            <el-input class="iot-w240" v-model="ruleForm.operator" readonly clearable></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="更改前状态" label-width="100px" prop="before_status">
            <el-input class="iot-w240" v-model="ruleForm.before_status" readonly clearable></el-input>
          </el-form-item>
          <el-form-item label="更改后状态" label-width="100px" prop="after_status">
            <el-input class="iot-w240" v-model="ruleForm.after_status" readonly clearable></el-input>
          </el-form-item>
        </div>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="click_submit">保 存</el-button>
      </span>
    </el-dialog>-->
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
  getWaitForReleasedInventory,
  updateReleasedInventory
} from "./api";
export default {
  //全局混入提示文字
  mixins: [textConfig],
  components: {
    singlePictureUpload
  },
  data() {
    return {
      qualityList: [],
      slotList: [],
      warehouseList: [],
      multipleSelection: [],
      radio1: "",
      searchForm: {},
      dialogTitle: "新增",
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      //表单数据
      ruleForm: {},
      //表单验证规则
      rules: {}
    };
  },
  mounted() {
    this.GetAll();
    this.GetSolotList();
    this.GetQualityList();
  },
  methods: {
    handleCurrentChange(val) {
      this.currentPage = val;
      this.GetAll();
    },
    //表单重置
    async resetForm() {
      this.dialogVisible = true;
      await this.$nextTick();
      this.$refs["areaForm"].resetFields();
    },
    //库位下拉列表
    async GetSolotList() {
      let data = await this.$DropBox.getSlotList();
      if (data) {
        this.slotList = data || [];
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

    //列表
    async GetAll() {
      let params = {
        MaxResultCount: this.pageSize,
        SkipCount: (this.currentPage - 1) * this.pageSize,
        ...this.searchForm
      };
      let data = await getWaitForReleasedInventory(params);
      if (data) {
        // this.tableData = data || [];
        // this.total = data.length || 0;
        this.tableData = data.items || [];
        this.total = data.totalCount || 0;
        this.multipleSelection = [];
      }
      this.total = 0;
      this.multipleSelection = [];
    },
    //当checkbox发生变化时
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    click_checkout() {
      if (this.multipleSelection.length >= 1) {
        let row_array = [];
        this.multipleSelection.forEach(element => {
          row_array.push(element.id);
        });
        this.confirm_checkout(row_array);
      } else {
        //this.text_selectOne  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_selectOne);
      }
    },
    click_search() {
      this.currentPage = 1;
      this.GetAll();
    },

    confirm_checkout(rows_array) {
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
            "放行"
          ),
          h("span", null, "】吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        let res = await updateReleasedInventory(rows_array);
        if (res > 0) {
          this.$message({
            type: "success",
            message: "放行成功"
          });
          this.GetAll();
        }
      });
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
    //根据主键获取信息
    async Get(row) {
      let obj = {
        material_code: row.material_code,
        material_name: row.material_name,
        number: row.number,
        danwei: row.danwei
      };
      //   let params = { id: row.id };
      //   let res = await getOneApi(params);
      //   let obj = {

      //     slot_area_id: res.slot_area_id
      //   };
      //   this.ruleForm = obj;
    }
  }
};
</script>
