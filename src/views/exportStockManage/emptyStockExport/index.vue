<template>
  <div class="iot-list" ref="page">
    <div class="iot-form">
      <!-- 搜索条件 -->
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <div class="iot-form-row">
            <el-form-item label="托盘号码">
              <el-input class="iot-w200" placeholder="请输入托盘号码" maxlength="50" v-model="searchForm.imphead_code" clearable></el-input>
            </el-form-item>
            <el-form-item>
              <el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">查询</el-button>
            </el-form-item>
          </div>
        </el-form>
      </div>
      <!-- 按钮 -->
      <div class="layout__btns">
        <div ref="permissions">
          <el-button type="primary" plain icon="el-icon-plus" @click="click_add">新增</el-button>
          <el-button type="primary" plain icon="el-icon-delete" @click="handleDelete">删除</el-button>
          <el-button type="primary" plain icon="el-icon-document" @click="exportExcel">导出</el-button>
          <el-button type="primary" plain icon="el-icon-printer" @click="printTable">打印</el-button>
        </div>
      </div>
    </div>
    <!-- 列表格 -->
    <div class="iot-table">
      <el-table id="out-table" ref="print" :data="tableData" stripe style="width: 100%" border @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"/>
        <el-table-column align="center" type="index" label="序号" width="50"/>
        <el-table-column align="center" prop="expstock_stock_code" label="托盘码" min-width="100" show-overflow-tooltip />
        <el-table-column align="center" prop="warehouse.warehouse_name" label="仓库" min-width="100" show-overflow-tooltip />
        <el-table-column align="center" prop="slot.slot_code" label="库位" min-width="100" show-overflow-tooltip/>

        <el-table-column align="center" prop="expstock_quantity" label="托盘数量" min-width="100" show-overflow-tooltip />
        <el-table-column align="center" prop="impstock_execute_flag" label="执行状态" min-width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.expstock_execute_flag === 0">未生成</span>
            <span v-if="scope.row.expstock_execute_flag === 1">待执行</span>
            <span v-else-if="scope.row.expstock_execute_flag === 2">执行中</span>
            <span v-else-if="scope.row.expstock_execute_flag === 3">已完成</span>
          </template>
        </el-table-column>
        <el-table-column align="center" prop="goods.goods_name" label="物资编码" min-width="100" show-overflow-tooltip/>
        <el-table-column align="center" prop="goods.goods_name" label="物资名称" min-width="100" show-overflow-tooltip />
        <el-table-column align="center" prop="creationTime" label="生成时间" min-width="100" show-overflow-tooltip :formatter="dateTimeTransform" />


<!--        <el-table-column align="center" prop="expstock_remark" label="备注" min-width="100" show-overflow-tooltip/>-->
      </el-table>
    </div>
    <!-- 页码 -->
    <div class="iot-pagination">
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"/>
    </div>
    <!-- 新增页面 -->
    <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
      <el-table :data="goodsTableData" stripe style="width: 100%" border @selection-change="handleSelectionChangeGoods">
        <el-table-column type="selection" width="55"/>
        <el-table-column align="center" :label="$t('public.serialNumber')" width="50"><template slot-scope="scope">
          {{scope.row.goodsId = +scope.$index + 1}}</template>
        </el-table-column>
        <el-table-column align="center" prop="goods.goods_code" :label="$t('importWarehouseManage.goodsCode')" min-width="150" show-overflow-tooltip/>
        <el-table-column align="center" prop="goods.goods_name" :label="$t('importWarehouseManage.goodsName')" min-width="150" show-overflow-tooltip/>
        <el-table-column align="center" prop="inventory_quantity" :label="$t('importWarehouseManage.planNum')" min-width="100" show-overflow-tooltip/>
        <!-- <el-table-column align="center" prop="inventory_quality_status" label="质量状态" min-width="100" show-overflow-tooltip :formatter="toQuality"/> -->
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="dialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="click_submit">保 存</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import textConfig from "@/mixins/textConfig.js";
import singlePictureUpload from "_c/singlePictureUpload";
import { getListApi, addApi, getOneApi, editApi, deleteApi, getInventoryApi } from "./api";
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
      soltList:[],
      multipleSelection: [],
      multipleSelectionInventory:[],
      goodsTableData: [],
      searchForm: {},
      dialogTitle: "选择库存",
      tableData: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      idList: []
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
      //return this.$moment(time).format('YYYY-MM-DD hh:mm:ss')
      return this.$utils.datetransSecond(time)
    },
    //根据当前用户权限标识初始化按钮状态
    btnInit() {
      this.$Common.DistributePermission.call(this)
    },
    handleSizeChange(val) {
      //测试
      this.pageSize = val;
      this.currentPage = 1;
      this.GetAll();
    },
    //当物料checkbox发生变化时
    handleSelectionChangeGoods(val) {
      this.multipleSelectionInventory = val;
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
    //库位列表
    async GetSlotList() {
      let data = await this.$DropBox.getSlotList();
      if (data) {
        this.slotList = data || [];
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
    //获取库存列表
    async getInventoryList(){
      let res = await getInventoryApi();
      if(res){
        this.goodsTableData = res.items
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
        message: 
          h("p", null, [
          h("span", null, "确定【"),
          h("span", { style: this.c_primary }, "新增"),
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
    //查询按钮
    click_search() {
      this.currentPage = 1;
      this.GetAll();
    },
    //点击选择按钮
    click_add() {
      this.dialogTitle = "选择库存";
      this.dialogVisible = true;
      this.getInventoryList();
      this.idList=[];
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
    //点击保存
    click_submit() {
      if (this.dialogTitle == "查看") {
        this.dialogVisible = false;
        return;
      }
      if (this.multipleSelectionInventory.length == 0) {
        //this.text_deleteRow  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_deleteRow);
      } else {
        let rows=this.multipleSelectionInventory;
        this.CreateStockTask(rows);
      }
    },
    //根据主键生成任务
    CreateStockTask(rows) {
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
        let stockCount = 0;
        this.idList = [];
        rows.forEach(ele=>{
          if(!ele.isCreate){
            this.idList.push(ele.id)
            stockCount++;
          }
        });
        if(this.idList.length){
						let res = await createStockTaskApi(this.idList);
						if(res){
							this.$message({
								type: "success",
								message: '出库任务生成成功'
              });
              this.dialogVisible = false;
              return;
						}else{
							this.$message({
								type: "error",
								message: '系统繁忙请稍后再试'
							});
						}
					}
					if((this.total-stockCount)%10==0 && this.currentPage!=1) {
						this.currentPage-=1;
					}
					this.GetAll();
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
					for(let i = 0;i<rows.length;i++){
						let res = await deleteApi({id:rows[i].id});
						if(res){
							deleteCount++;
						}
					}
        if(deleteCount){
          this.$message({
            type: "success",
            message: `成功删除${deleteCount}条,删除失败${rows.length-deleteCount}条`
          });
          this.GetAll();
        }
      });
    },
    //仓库ID和name的转换
    warehouseShow(row){
      let id = row.impstock_warehouse_id;
      let name;
      this.warehouseList.forEach((ele)=>{
        if(ele.id === id){
         name = ele.warehouse_name
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