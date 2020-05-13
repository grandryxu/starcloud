<template>
    <div class="iot-list" ref="page">
        <div class="iot-form">
            <!-- 查询区域 -->
            <div class="layout__search">
                <el-form inline label-width="100px" :model="searchForm">
                    <el-form-item label="单据号">
                        <el-input class="iot-w200" placeholder="请输入单据号" v-model="searchForm.stock_code" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="所在仓库">
                        <el-select class="iot-w200" v-model="searchForm.warehouse_id" placeholder="--所有--" clearable>
                            <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="状态">
                        <el-select class="iot-w200" v-model="searchForm.stock_state" placeholder="--所有--" clearable>
                            <el-option v-for="item in StockTaskingState" :key="item.id" :label="item.stockState" :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                    <div class="iot-form-row">
                        <el-form-item label="类型">
                            <el-select class="iot-w200" @change="changeSelect" v-model="searchForm.stock_type" placeholder="--所有--" clearable>
                                <el-option v-for="item in StockTaskingType" :key="item.id" :label="item.stockType" :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="单据日期">
                            <el-date-picker class="iot-w200" start-placeholder="起始日期" end-placeholder="结束日期" v-model="searchForm.startDate" type="daterange" range-separator="-" clearable></el-date-picker>
                        </el-form-item>
                        <el-form-item v-if="selectValue==3" label="物料信息">
                            <el-input class="iot-w200" placeholder v-model="searchForm.goods_name" clearable></el-input>
                            <!-- <el-select class="iot-w200" filterable v-model="searchForm.goods_id" placeholder="请选择物料信息" clearable>
                                <el-option v-for="item in goodsInfoList" :key="item.id" :label="item.goods_name" :value="item.id"></el-option>
                            </el-select> -->
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
                    <el-button type="primary" plain icon="el-icon-share" @click="create_task">生成任务</el-button>
                    <el-button type="primary" plain icon="el-icon-document" @click="exportExcel">导出</el-button>
                    <el-button type="primary" plain icon="el-icon-printer" @click="printTable">打印</el-button>
                </div>
            </div>
        </div>
        <!-- 列表区域 -->
        <div class="iot-table">
            <el-table id="out-table" ref="print" :data="tableData" stripe style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
                <el-table-column align="center" prop="task_code" label="单据号" min-width="100" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="task_type" label="类型" min-width="100" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <span v-if="scope.row.task_type === 1">整库盘点</span>
                        <span v-else-if="scope.row.task_type === 2">动态盘点</span>
                        <span v-else-if="scope.row.task_type === 3">物资盘点</span>
                    </template>
                </el-table-column>
                <el-table-column v-if="selectValue==2" align="center" prop="task_start_date" label="开始日期" min-width="100" show-overflow-tooltip></el-table-column>
                <el-table-column v-if="selectValue==2" align="center" prop="task_end_date" label="结束日期" min-width="100" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="warehouse.warehouse_name" label="所属仓库" min-width="100" show-overflow-tooltip></el-table-column>
                <el-table-column v-if="selectValue==3" align="center" prop="goods.goods_name" label="物料" min-width="100" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="task_state" label="盘点状态" min-width="100" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <span v-if="scope.row.task_state === 0">未盘点</span>
                        <span v-else-if="scope.row.task_state === 1">盘点结束</span>
                        <span v-else-if="scope.row.task_state === 2">盘点中</span>
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="task_remark" label="备注" min-width="100" show-overflow-tooltip></el-table-column>
            </el-table>
        </div>
        <!-- 页码区域 -->
        <div class="iot-pagination">
            <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
        </div>
        <!-- 新增区域 -->
        <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
            <el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
                <div class="iot-form-row">
                    <el-form-item label="单据号" label-width="100px" prop="task_code">
                        <el-input class="iot-w240" v-model="ruleForm.task_code" placeholder="请输入单据号" maxlength="50" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="仓库" label-width="100px" prop="task_warehouse_id">
                        <el-select class="iot-w240" v-model="ruleForm.task_warehouse_id" placeholder="--所有--" clearable>
                            <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                </div>
                <div class="iot-form-row">
                    <el-form-item label="类型" label-width="100px" prop="task_type">
                        <el-select class="iot-w240" @change="typeSelect" v-model="ruleForm.task_type" placeholder="请选择">
                            <el-option v-for="item in StockTaskingType" :key="item.id" :label="item.stockType" :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item v-if="typeValue==2" label="日期" label-width="100px" prop="start_date">
                        <el-date-picker class="iot-w240" v-model="ruleForm.start_date" type="daterange" range-separator="-" clearable></el-date-picker>
                        <!-- <el-date-picker class="iot-w120" clearable v-model="ruleForm.task_start_date" placeholder="请输入开始日期" type="date"></el-date-picker> --
                        <el-date-picker class="iot-w120" clearable v-model="ruleForm.task_end_date" placeholder="请输入结束日期" type="date"></el-date-picker> -->
                    </el-form-item>
                    <el-form-item v-else-if="typeValue==3" label="物料信息" label-width="100px" prop="task_goods_id">
                        <el-select class="iot-w240" filterable v-model="ruleForm.task_goods_id" placeholder="请选择物料信息" clearable>
                            <el-option v-for="item in goodsInfoList" :key="item.id" :label="item.goods_name" :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                </div>
                <div class="iot-form-row">
                    <!-- <el-form-item label="总数量" label-width="100px" prop="warehouse_code">
                        <el-input class="iot-w240" v-model="ruleForm.warehouse_code"></el-input>
                    </el-form-item> -->
                    <el-form-item label="备注" label-width="100px" prop="task_remark">
                        <el-input type="textarea" :rows="4" :maxlength="200" class="iot-w240" show-word-limit v-model="ruleForm.task_remark" clearable></el-input>
                    </el-form-item>
                </div>
                <div class="iot-form-row">
                    <div class="layout__btns">
                        <div>
                            <el-button v-if="dialogTitle=='新增'" type="success" @click="click_screen">{{$t('inventoryManagement.filtrate')}}</el-button>
                            <el-button v-if="dialogTitle=='编辑'" type="success" @click="click_aCount">{{$t('inventoryManagement.checkProfit')}}</el-button>
                            <el-button v-if="dialogTitle=='编辑'" type="success" @click="click_dCount">{{$t('inventoryManagement.checkLosses')}}</el-button>              
                        </div>
                    </div>    
                    <div class="iot-table">
                        <el-table :data="tableData2" stripe style="width: 100%" border @selection-change="inventorySelectionChange">
                            <el-table-column type="selection" width="55" show-overflow-tooltip></el-table-column>
                            <el-table-column align="center" type="index" :label="$t('public.serialNumber')" width="50"></el-table-column>
                            <el-table-column align="center" prop="slot.slot_code" :label="$t('public.slot')" min-width="100" show-overflow-tooltip></el-table-column>
                            <el-table-column align="center" prop="task_stock_code" :label="$t('exportBillBodyManage.stock')" min-width="100" show-overflow-tooltip></el-table-column>
                            <el-table-column align="center" prop="goods.goods_name" :label="$t('inventoryManagement.goods')" min-width="100" show-overflow-tooltip></el-table-column>
                            <el-table-column align="center" prop="task_batch_no" :label="$t('importBillBody.batch')" min-width="100" show-overflow-tooltip></el-table-column>
                            <el-table-column align="center" prop="task_count" :label="$t('public.quantity')" min-width="100" show-overflow-tooltip></el-table-column>
                            <el-table-column v-if="dialogTitle!='新增'" align="center" prop="task_acount" label="盘盈数量" min-width="100" show-overflow-tooltip></el-table-column>
                            <el-table-column v-if="dialogTitle!='新增'" align="center" prop="task_dcount" label="盘亏数量" min-width="100" show-overflow-tooltip></el-table-column>
                            <el-table-column align="center" prop="inventory_date" :label="$t('importWarehouseManage.importDate')" min-width="100" show-overflow-tooltip></el-table-column>
                        </el-table>
                    </div>
                </div>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" @click="dialogVisible = false">取 消</el-button>
                <el-button size="small" type="primary" @click="click_submit">保 存</el-button>
            </span>
        </el-dialog>
        <!-- 盘盈盘亏 -->
        <el-dialog class="iot-dialog" :title="dialogTitle2" :visible.sync="dialogVisible2" width="424px" append-to-body>
            <el-form inline ref="dialogForm2" :model="ruleForm2" :rules="rules2">
                <div class="iot-form-row">
                    <el-form-item :label="dialogTitle2+'数量'" label-width="100px" prop="ad_count">
                        <el-input class="iot-w240" v-model="ruleForm2.ad_count" :placeholder="'请填写'+dialogTitle2+'数量'" maxlength="50" clearable></el-input>
                    </el-form-item>
                </div>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" @click="dialogVisible2 = false">取 消</el-button>
                <el-button size="small" type="primary" @click="click_submit2">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import textConfig from "@/mixins/textConfig.js";
import singlePictureUpload from "_c/singlePictureUpload";
import { getListApi, addApi, getOneApi, editApi, deleteApi, getInventoryListApi, addDetailApi, editDetailApi, getDetailListApi, createTaskApi } from "./api";
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
            selectValue: "1",
            typeValue: "1",
            multipleSelection: [],
            inventorySelection: [],
            warehouseList: [],
            goodsInfoList: [],
            radio1: "",
            searchForm: {},
            dialogTitle: "新增",
            dialogTitle2: "盘盈",
            tableData: [],
            tableData2: [],
            StockTaskingState: [
                { id: 0, stockState: "未盘点" },
                { id: 1, stockState: "盘点结束" },
                { id: 2, stockState: "盘点中" }
            ],
            StockTaskingType: [
                { id: 1, stockType: "整库盘点" },
                { id: 2, stockType: "动态盘点" },
                { id: 3, stockType: "物资盘点" }
            ],
            currentPage: 1,
            pageSize: 10,
            total: 0,
            dialogVisible: false,
            dialogVisible2: false,
            //表单数据
            ruleForm: {
                task_code: "",
                task_warehouse_id: "",
                task_type: "",
                start_date: [],
                task_goods_id: "",
                task_remark: ""
            },
            //表单验证规则
            rules: {
                task_code: [
                    {
                        required: true,
                        message: "请输入单据号",
                        trigger: "blur"
                    }
                ],
                task_warehouse_id: [
                    {
                        required: true,
                        message: "请选择仓库",
                        trigger: "blur"
                    }
                ],
                task_type: [
                    {
                        required: true,
                        message: "请选择类型",
                        trigger: "blur"
                    }
                ],
                start_date: [
                    {
                        required: true,
                        message: "请填开始日期",
                        trigger: "blur"
                    }
                ],
                task_goods_id: [
                    {
                        required: true,
                        message: "请选择物料信息",
                        trigger: "blur"
                    }
                ]
            },
            ruleForm2: { ad_count: "" },
            rules2: {
                ad_count: [
                    {
                        required: true,
                        message: "请填写数量",
                        trigger: "blur"
                    }//,
                    //{ type: 'number', message: '必须输入数字' }
                ]
            }
        };
    },
    mounted() {
        this.GetAll();
        this.GetWarehouseList();
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
            this.tableData2 = [];
            this.$utils.traverseDialogDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.dialogForm);
        },
        //列表
        async GetAll() {
            let ss = this.searchForm.startDate;
            let params = {};
            if (ss) {
                params = {
                    MaxResultCount: this.pageSize,
                    SkipCount: (this.currentPage - 1) * this.pageSize,
                    stock_code: this.searchForm.stock_code,
                    warehouse_id: this.searchForm.warehouse_id,
                    stock_state: this.searchForm.stock_state,
                    stock_type: this.searchForm.stock_type,
                    goods_name: this.searchForm.goods_name,
                    start_date:this.searchForm.startDate[0],
                    end_date:this.searchForm.startDate[1]
                };
            } else {
                params = {
                    MaxResultCount: this.pageSize,
                    SkipCount: (this.currentPage - 1) * this.pageSize,
                    stock_code: this.searchForm.stock_code,
                    warehouse_id: this.searchForm.warehouse_id,
                    stock_state: this.searchForm.stock_state,
                    stock_type: this.searchForm.stock_type,
                    goods_name: this.searchForm.goods_name
                };
            }
            let data = await getListApi(params);
            if (data) {
                this.tableData = data.items || [];
                this.total = data.totalCount || 0;
            }
             this.$utils.traversePageDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.page);
            this.$utils.traverseFormValidator(this.rules,this.lang)
        },
        //物料列表
        async GetDetails() {
            let params = {
                Id: this.ruleForm.id
            };
            let data = await getDetailListApi(params);
            if (data) {
                this.tableData2 = data || [];
            }
        },
        //仓库列表
        async GetWarehouseList() {
            let data = await this.$DropBox.getWarehouselist();
            if (data) {
                this.warehouseList = data || [];
            }
        },
        //物料列表
        async GetGoodsInfoList() {
            let data = await this.$DropBox.getGoodsList();
            if (data) {
                this.goodsInfoList = data || [];
            }
        },
        //查询类型选择
        changeSelect(val) {
            if (val != 3)
                this.searchForm.goods_name = null;
            this.selectValue = val
        },
        //类型选择
        typeSelect(val) {
            if (val != 3) {
                this.ruleForm.start_date = null;
            }
            if (val != 3)
                this.ruleForm.task_goods_id = null;
            this.typeValue = val
        },
        //新建
        Create() {
            if (!Object.keys(this.ruleForm).length) {
                this.dialogVisible = false;
                return;
            }
            if (this.inventorySelection.length == 0) {
                this.$message({
                    type: "success",
                    message: "请选择需要盘点的库存。"
                });
                return false;
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
                let params = {
                    task_code: this.ruleForm.task_code,
                    task_warehouse_id: this.ruleForm.task_warehouse_id,
                    task_type: this.ruleForm.task_type,
                    task_start_date: this.ruleForm.start_date[0],
                    task_end_date: this.ruleForm.start_date[1],
                    task_goods_id: this.ruleForm.task_goods_id,
                    task_remark: this.ruleForm.task_remark
                };
                let res = await addApi(params);
                if (res) {
                    this.inventorySelection.forEach(ele => {
                        ele.stock_tasking_id = res.id;
                    });
                    await addDetailApi(this.inventorySelection);
                }
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
        //当选择托盘信息时
        inventorySelectionChange(val) {
            this.inventorySelection = val;
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
        //生成任务
        async create_task() {
            let rows = this.multipleSelection;
            let ids = [];
            this.multipleSelection.forEach(ele=>{
                ids.push(ele.id)
            })
            let flag = await createTaskApi(ids);
            if (flag.result) {
                this.$message({
                    type: "success",
                    message: "生成任务成功"
                });
                this.GetAll();
            } else {
                this.$message({
                    type: "error",
                    message: "生成任务失败"
                });
            }
        },
        click_edit(row) {
            this.dialogTitle = "编辑";
            this.resetForm();
            this.GetGoodsInfoList();
            this.Get(row);
        },
        click_search() {
            this.currentPage = 1;
            this.GetAll();
        },
        click_add() {
            this.dialogTitle = "新增";
            this.resetForm();
            this.GetGoodsInfoList();
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
                if (this.dialogTitle == "新增") {
                    this.Create();
                } else {
                    this.Update();
                }
            });
        },
        //盘盈盘亏
        click_submit2() {
            this.$refs["dialogForm2"].validate(valid => {
                if (!valid) {
                    return;
                }
                if (this.dialogTitle2 == "盘盈") {
                    this.inventorySelection[0].task_acount = this.ruleForm2.ad_count;
                    this.inventorySelection[0].task_dcount = 0;
                } else if (this.dialogTitle2 == "盘亏") {
                    if (this.inventorySelection[0].task_count < this.ruleForm2.ad_count) {
                        this.$message({
                            type: "success",
                            message: "盘亏数量不可超过库存数。"
                        });
                        return false;
                    }
                    this.inventorySelection[0].task_acount = 0;
                    this.inventorySelection[0].task_dcount = this.ruleForm2.ad_count;
                }
                this.dialogVisible2 = false;
            });
        },
        //筛选
        click_screen() {
            this.$refs["dialogForm"].validate(valid => {
                if (!valid) {
                    return;
                }
                this.screenInventory();
            });
        },
        //盘盈
        click_aCount() {
            if (this.inventorySelection.length == 0) {
                this.$message({
                    type: "success",
                    message: "请选择需要盘点的库存。"
                });
                return false;
            }
            if (this.inventorySelection.length > 1) {
                this.$message({
                    type: "success",
                    message: "请选择一条数据。"
                });
                return false;
            }
            this.ruleForm2.ad_count = 0;
            this.dialogTitle2 = "盘盈";
            this.dialogVisible2 = true;
        },
        //盘亏
        click_dCount() {
            if (this.inventorySelection.length == 0) {
                this.$message({
                    type: "success",
                    message: "请选择需要盘点的库存。"
                });
                return false;
            }
            if (this.inventorySelection.length > 1) {
                this.$message({
                    type: "success",
                    message: "请选择一条数据。"
                });
                return false;
            }
            this.ruleForm2.ad_count = 0;
            this.dialogTitle2 = "盘亏";
            this.dialogVisible2 = true;
        },
        //表单重置
        async screenInventory() {
            //查询库存信息
            let params = {
                ...this.ruleForm
            };
            let data = await getInventoryListApi(params);
            if (data) {
                this.tableData2 = data || [];
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
                        message: `成功删除${deleteCount}条,删除失败${rows.length - deleteCount}条`
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
                    h("span", { style: this.c_primary }, "编辑"),
                    h("span", null, "】吗？")
                ]),
                showCancelButton: true,
                type: "warning"
            }).then(async action => {
                let params = {
                    id: this.ruleForm.id,
                    task_code: this.ruleForm.task_code,
                    task_warehouse_id: this.ruleForm.task_warehouse_id,
                    task_type: this.ruleForm.task_type,
                    task_start_date: this.ruleForm.start_date[0],
                    task_end_date: this.ruleForm.start_date[1],
                    task_goods_id: this.ruleForm.task_goods_id,
                    task_remark: this.ruleForm.task_remark
                };
                let res = await editApi(params);
                if (res) {
                    await editDetailApi(this.tableData2);
                }
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
                task_code: res.task_code,
                task_warehouse_id: res.task_warehouse_id,
                task_type: res.task_type,
                //task_start_date: res.task_start_date,
                //task_end_date: res.task_end_date,
                task_goods_id: res.task_goods_id,
                task_remark: res.task_remark
            };
            this.ruleForm = obj;
            this.ruleForm.start_date[0] = res.task_start_date;
            this.ruleForm.start_date[1] = res.task_end_date;
            if (this.dialogTitle === '编辑') {
                this.GetDetails();
            }
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