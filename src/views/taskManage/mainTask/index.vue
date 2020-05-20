<!--立库任务管理-->
<template>
    <div class="iot-list" ref="page">
        <div class="iot-form">
            <div class="layout__search">
                <el-form inline label-width="100px" :model="searchForm">
                    <div class="iot-form-row">
                        <el-form-item label="状态">
                            <el-select class="iot-w200" v-model="searchForm.main_execute_flag" placeholder="请选择"
                                       clearable>
                                <el-option v-for="item in statusList" :key="item.id" :label="item.modeName"
                                           :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="类型">
                            <el-select class="iot-w200" v-model="searchForm.main_mode" placeholder="请选择" clearable>
                                <el-option v-for="item in mainmodeList" :key="item.id" :label="item.modeName"
                                           :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="仓库">
                            <el-select class="iot-w200" v-model="searchForm.slot_warehouse_id" placeholder="请选择"
                                       clearable>
                                <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name"
                                           :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="下发时间">
                            <el-date-picker class="iot-w200" clearable v-model="searchForm.startDate"
                                            type="datetime"></el-date-picker>
                        </el-form-item>
                        <el-form-item label="来源">
                            <el-select class="iot-w200" v-model="searchForm.main_manual_flag" placeholder="请选择"
                                       clearable>
                                <el-option v-for="item in sourceList" :key="item.id" :label="item.modeName"
                                           :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="巷道">
                            <el-select class="iot-w200" v-model="searchForm.tunnelInfo_id" placeholder="请选择" clearable>
                                <el-option v-for="item in tunnelInfoList" :key="item.id" :label="item.tunnel_name"
                                           :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item>
                            <el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">
                                查询
                            </el-button>
                        </el-form-item>
                    </div>
                </el-form>
            </div>
            <div class="layout__btns">
                <div ref="permissions">
                    <!-- <el-button type="primary" plain icon="el-icon-video-play"  @click="handleStart">开始</el-button>
                     <el-button type="primary" plain icon="el-icon-video-pause">暂停</el-button>
                     <el-button type="primary" plain icon="el-icon-refresh">恢复</el-button>
                     <el-button type="primary" plain icon="el-icon-circle-check" @click="handleFinish">完成</el-button>
                     <el-button type="primary" plain icon="el-icon-circle-close">取消</el-button>
                     <el-button type="primary" plain icon="el-icon-delete" @click="handleDelete">删除</el-button>
                    <el-button type="primary" plain icon="el-icon-sort" @click="handleSort">调整优先级</el-button>-->
                    <el-button type="primary" plain icon="el-icon-finished" @click="click_create">手动生成</el-button>
                    <el-button type="primary" plain icon="el-icon-timer" @click="handleModify">修改状态</el-button>
                    <el-button type="primary" plain icon="el-icon-video-play" @click="showdialog">查看</el-button>
                </div>
            </div>
        </div>
        <div class="iot-table">
            <el-table :data="tableData" stripe style="width: 100%" border @selection-change="handleSelectionChange"
                      @row-dblclick="dbshowdialog">
                <el-table-column type="selection" width="55"></el-table-column>
                <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
                <el-table-column align="center" prop="main_no" label="任务号" min-width="100"
                                 show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="main_priority" label="优先级" min-width="100"
                                 show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="main_mode" label="类型" min-width="100" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <span v-if="scope.row.main_mode === 1">入库</span>
                        <span v-else-if="scope.row.main_mode === 2">出库</span>
                        <span v-else-if="scope.row.main_mode === 3">移库</span>
                        <span v-else-if="scope.row.main_mode === 4">口对口</span>
                        <span v-else-if="scope.row.main_mode === 5">回流</span>
                        <span v-else-if="scope.row.main_mode === 6">空托盘入库</span>
                        <span v-else-if="scope.row.main_mode === 7">空托盘出库</span>
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="slot1.warehouse.warehouse_name" label="仓库" min-width="100"
                                 show-overflow-tooltip></el-table-column>
                <!-- <el-table-column
                  align="center"
                  prop="tunnel"
                  label="巷道"
                  min-width="100"
                  show-overflow-tooltip
                ></el-table-column>
                <el-table-column
                  align="center"
                  prop="main_port"
                  label="输送口"
                  min-width="100"
                  show-overflow-tooltip
                ></el-table-column> -->
                <el-table-column align="center" prop="slot1.slot_code" label="库位编码" min-width="100"
                                 show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="main_stock_code" label="托盘" min-width="100"
                                 show-overflow-tooltip></el-table-column>
                <!-- <el-table-column align="center" prop="material_name" label="物料名称" min-width="100"
                                  show-overflow-tooltip></el-table-column>
                 <el-table-column align="center" prop="exporder_batch_no" label="批次号" min-width="100"
                                  show-overflow-tooltip></el-table-column>
                 <el-table-column align="center" prop="exporder_quantity" label="数量" min-width="100"
                                  show-overflow-tooltip></el-table-column>-->
                <el-table-column align="center" prop="main_execute_flag" label="执行状态" min-width="100"
                                 show-overflow-tooltip :formatter="executeTransform"></el-table-column>
                <el-table-column align="center" prop="creationTime" label="下发时间" min-width="100"
                                 show-overflow-tooltip :formatter="dateTimeTransform"></el-table-column>
            </el-table>
        </div>
        <div class="iot-pagination">
            <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize"
                           :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
        </div>
        <el-dialog class="iot-dialog" title="修改" :visible.sync="singleDialogVisible" width="488px"
                   append-to-body>
            <el-form inline ref="areaForm" :model="ruleForm">
                <div class="iot-form-row">
                    <el-form-item label="状态" label-width="100px" prop="main_mode">
                        <el-select class="iot-w240" v-model="ruleForm.main_execute_flag" placeholder="请选择" clearable>
                            <el-option v-for="item in statusList" :key="item.id" :label="item.modeName"
                                       :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item label="优先级" label-width="100px" prop="main_mode">
                        <el-select class="iot-w240" v-model="ruleForm.main_priority" placeholder="请选择" clearable>
                            <el-option v-for="item in sortList" :key="item.id" :label="item.id"
                                       :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                </div>


            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button size="small" @click="singleDialogVisible = false">取 消</el-button>
        <el-button size="small" type="primary" @click="click_modify">执 行</el-button>
      </span>
        </el-dialog>


        <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
            <el-form inline ref="dialogForm" :model="dialogForm">
                <div class="iot-form-row">
                    <el-form-item label="托盘号" label-width="100px" prop="material_code">
                        <el-input class="iot-w240" placeholder="请输入托盘号" maxlength="50"
                                  v-model="dialogForm.main_stock_code" :disabled="true" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="库位号" label-width="100px" prop="material_name">
                        <el-input class="iot-w240" placeholder="请输入库位号" maxlength="50" v-model="dialogForm.slot_code"
                                  :disabled="true" clearable></el-input>
                    </el-form-item>
                </div>
                <div class="iot-form-row">
                    <el-form-item label="任务号" label-width="100px" prop="number">
                        <el-input class="iot-w240" placeholder="请输入任务号" maxlength="50" v-model="dialogForm.main_no"
                                  :disabled="true" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="任务状态" label-width="100px" prop="danwei">
                        <el-input class="iot-w240" placeholder="请输入任务状态" maxlength="50"
                                  v-model="dialogForm.main_execute_flag" :disabled="true" clearable></el-input>
                    </el-form-item>
                </div>
                <div class="iot-form-row">
                    <div style="text-align: center;font-size: 30px;padding: 10px 0px;">
                        <template>
                            <span v-if="main_mode === 1">入库流水</span>
                            <span v-else-if="main_mode === 2">出库流水</span>
                            <span v-else-if="main_mode === 3">移库流水</span>
                            <span v-else-if="main_mode === 4">口对口流水</span>
                            <span v-else-if="main_mode === 5">回流流水</span>
                            <span v-else-if="main_mode === 6">空托盘入库流水</span>
                            <span v-else-if="main_mode === 7">空托盘出库流水</span>
                        </template>

                    </div>
                    <div class="iot-table">
                        <el-table id="out-table" :data="lstableData" stripe style="width: 100%" border>
                            <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
                            <el-table-column align="center" prop="warehouse_name" label="仓库" min-width="100" show-overflow-tooltip :formatter="warehouseShow"></el-table-column>
                            <el-table-column align="center" prop="slot_code" label="库位" min-width="100" show-overflow-tooltip></el-table-column>
                            <el-table-column align="center" prop="stock_code" label="托盘码" min-width="100" show-overflow-tooltip></el-table-column>
                            <el-table-column align="center" prop="goods_code" label="物料编码" min-width="100" show-overflow-tooltip></el-table-column>
                            <el-table-column align="center" prop="goods_name" label="物料名称" min-width="100" show-overflow-tooltip></el-table-column>
                            <el-table-column align="center" prop="order_quantity" label="数量" min-width="100" show-overflow-tooltip></el-table-column>
                            <el-table-column align="center" prop="execute_flag" label="执行状态" min-width="100" show-overflow-tooltip>
                                <template slot-scope="scope">
                                    <span v-if="scope.row.execute_flag === 1">未执行</span>
                                    <span v-else-if="scope.row.execute_flag === 2">执行中</span>
                                    <span v-else-if="scope.row.execute_flag === 3">已完成</span>
                                </template>
                            </el-table-column>
                            <el-table-column align="center" prop="creationTime" label="创建时间" :formatter="dateTimeTransform" min-width="100" show-overflow-tooltip></el-table-column>
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


        <el-dialog class="iot-dialog" :title="dialogTitleA" :visible.sync="dialogVisibleA" width="844px" append-to-body>
            <el-form inline ref="dialogFormA" :model="ManualForm" :rules="ManualFormrules">
                <div class="iot-form-row">
                    <el-form-item label="任务号" label-width="120px" prop="main_no">
                        <el-input class="iot-w240" v-model="ManualForm.main_no" placeholder="请输入任务号" maxlength="30"
                                  clearable :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="优先级" label-width="120px" prop="main_priority">
                        <el-select class="iot-w240" v-model="ManualForm.main_priority" placeholder="请选择" clearable>
                            <el-option v-for="item in sortList" :key="item.id" :label="item.id"
                                       :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="类型" label-width="120px" prop="main_mode">
                        <el-select class="iot-w240" v-model="ManualForm.main_mode" placeholder="请选择" clearable
                                   @change="getMainmode">
                            <el-option v-for="item in mainmodeList" :key="item.id" :label="item.modeName"
                                       :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="仓库" label-width="120px" prop="warehouse_id">
                        <el-select class="iot-w240" v-model="ManualForm.warehouse_id" placeholder="请选择">
                            <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name"
                                       :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="库位编码" label-width="120px" prop="main_slot_code">
                        <el-input class="iot-w240" v-model="ManualForm.main_slot_code" placeholder="请输入库位编码"
                                  maxlength="30" clearable></el-input>
                    </el-form-item>

                    <el-form-item v-if="ManualForm.main_mode=='3'" label="移入库位编码" label-width="120px"
                                  prop="main_inslot_code">
                        <el-input class="iot-w240" v-model="ManualForm.main_inslot_code" placeholder="请输入移入库位编码"
                                  maxlength="30" clearable></el-input>
                    </el-form-item>


                    <el-form-item label="托盘" label-width="120px" prop="main_stock_code">
                        <el-input class="iot-w240" v-model="ManualForm.main_stock_code" placeholder="请输入托盘编码"
                                  maxlength="50" clearable></el-input>
                    </el-form-item>

                </div>
            </el-form>
            <span slot="footer" class="dialog-footer">
				<el-button size="small" @click="dialogVisibleA = false">取 消</el-button>
				<el-button size="small" type="primary" @click="click_Manualsubmit">保 存</el-button>
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
        startApi,
        finishApi,
        getrkListApi,
        getkyprkListApi,
        UpdateStateApi,
        GetSlotCodeIdApi,
        GetEncodingRuleCode,
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
                warehouseList: [],
                tunnelInfoList: [],
                sortList: [{id: 1}, {id: 2}, {id: 3}, {id: 4}, {id: 5}],
                sourceList: [
                    {id: 1, modeName: "自动"},
                    {id: 2, modeName: "手动"}
                ],
                statusList: [
                    {id: 1, modeName: "待执行"},
                    {id: 2, modeName: "输送机"},
                    {id: 3, modeName: "堆垛机"},
                    {id: 4, modeName: "RGV"},
                    {id: 5, modeName: "AGV"},
                    {id: 7, modeName: "暂停中"},
                    {id: 9, modeName: "已完成"}
                ],
                mainmodeList: [
                    {id: 1, modeName: "入库"},
                    {id: 2, modeName: "出库"},
                    {id: 3, modeName: "移库"},
                    {id: 4, modeName: "口对口"},
                    {id: 6, modeName: "空托盘入库"},
                    {id: 7, modeName: "空托盘出库"}
                ],
                warehouseList: [],
                multipleSelection: [],
                radio1: "",
                searchForm: {
                },
                dialogTitle: "查看",
                singleDialogTitle: "修改状态",
                tableData: [],
                lstableData: [],
                isEnableList: [
                    {id: 0, enableName: "正常"},
                    {id: 1, enableName: "停用"}
                ],
                currentPage: 1,
                pageSize: 10,
                total: 0,
                dialogVisible: false,
                singleDialogVisible: false,
                //表单数据
                ruleForm: {
                    main_priority: "",
                    main_execute_flag: ""
                },
                dialogForm: {},
                //表单验证规则
                rules: {
                    unit_name: [
                        {
                            required: true,
                            message: "请输入单位名称",
                            trigger: "blur"
                        }
                    ],
                    unit_is_enable: [
                        {
                            required: true,
                            message: "请选择启用状态",
                            trigger: "blur"
                        }
                    ]
                },
                TaskMainId: '',
                tableData2: [],
                main_mode: '',
                dialogTitleA: '手动生成',
                dialogVisibleA: false,
                ManualForm: {
                    main_no:"",
                    main_priority: "",
                    main_mode: "",
                    warehouse_id: "",
                    main_slot_code: "",
                    main_inslot_code: "",
                    main_stock_code: "",
                },
                ManualFormrules: {
                    main_mode: [
                        {
                            required: true,
                            message: "请选择类型",
                            trigger: "blur"
                        }
                    ],
                    warehouse_id: [
                        {
                            required: true,
                            message: "请选择仓库",
                            trigger: "blur"
                        }
                    ],
                    main_slot_code: [
                        {
                            required: true,
                            message: "请选择库位编码",
                            trigger: "blur"
                        }
                    ],
                },

            };
        },
        mounted() {
            this.GetAll();
            this.GetWarehouseList();
            this.GetTunnelList();
            this.btnInit();

        },
        methods: {
            //获取单号
            async GetEncodingRuleCode() {
                let params = {
                    code: 'TaskCode'
                };
                let code = await GetEncodingRuleCode(params);
                this.ManualForm.main_no = code;
            },
            //查询该仓库下的库位是否存在
            async checkExsit() {
                let params = {
                    slot_code: this.ManualForm.main_slot_code,
                    slot_warehouse_id: this.ManualForm.warehouse_id
                };
                let res = await GetSlotCodeIdApi(params);
                if (res) {
                    let num = res.split("@")[0];
                    let result = res.split("@")[1];
                    if (num == "0") {
                        this.$message({
                            message: '库位编码不存在！',
                            type: 'warning'
                        });
                        return
                    } else {

                        if (this.ManualForm.main_inslot_code && this.ManualForm.main_mode == '3') {
                            let params = {
                                slot_code: this.ManualForm.main_inslot_code,
                                slot_warehouse_id: this.ManualForm.warehouse_id
                            };
                            let res = await GetSlotCodeIdApi(params);
                            if (res) {
                                let num = res.split("@")[0];
                                let result1 = res.split("@")[1];
                                if (num == "0") {
                                    this.$message({
                                        message: '移入库位编码不存在！',
                                        type: 'warning'
                                    });
                                    return
                                } else {

                                    this.$msgbox({
                                        title: "提示",
                                        message: "确定保存吗？",
                                        showCancelButton: true,
                                        type: "success"
                                    }).then(async action => {
                                        let params = {
                                            ...this.ManualForm
                                        };
                                        params.main_inslot_code=result1;
                                        params.main_slot_code=result;
                                        params.main_execute_flag = 1;
                                        params.main_manual_flag=2;
                                        let res = await addApi(params);
                                        if (res) {
                                            this.$message({
                                                type: "success",
                                                message: "保存成功"
                                            });
                                            this.GetAll();
                                            this.dialogVisibleA = false;
                                        }
                                    });

                                }
                            }

                        }
                        else
                        {
                            this.$msgbox({
                                title: "提示",
                                message: "确定保存吗？",
                                showCancelButton: true,
                                type: "success"
                            }).then(async action => {
                                let params = {
                                    ...this.ManualForm
                                };
                                params.main_slot_code=result;
                                params.main_execute_flag = 1;
                                params.main_manual_flag=2;
                                let res = await addApi(params);
                                if (res) {
                                    this.$message({
                                        type: "success",
                                        message: "保存成功"
                                    });
                                    this.GetAll();
                                    this.dialogVisibleA = false;
                                }
                            });
                        }


                    }
                }


            },
            //手动生成
            click_Manualsubmit() {
                this.$refs["dialogFormA"].validate(valid => {
                    if (!valid) {
                        return;
                    }
                    if (!Object.keys(this.ManualForm).length) {
                        this.dialogVisibleA = false;
                        return;
                    }
                    if (this.ManualForm.main_slot_code) {
                        this.checkExsit();
                    } else {

                    }


                })


            },
            //获取类型的值
            getMainmode(val) {
                if (val === 3) {
                    this.ManualFormrules.main_stock_code = null;
                    this.ManualFormrules = Object.assign({}, this.ManualFormrules);
                } else {
                    this.ManualFormrules = Object.assign(
                        {
                            main_inslot_code: [
                                {
                                    required: true,
                                    message: "请选择移入库位编码",
                                    trigger: "blur"
                                }
                            ]
                        },
                        this.ManualFormrules
                    );
                }
            },
            //根据当前用户权限标识初始化按钮状态
            btnInit() {
                this.$Common.DistributePermission.call(this)
            },
            //区域选择
            handarea() {
                this.dialogTitle = "区域选择";
                this.resetForm();
                this.Get(this.multipleSelection[0]);
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
                // this.dialogVisible = true;
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
            //巷道下拉
            async GetTunnelList() {
                let data = await this.$DropBox.getTunnellist();
                if (data) {
                    this.tunnelInfoList = data.items || [];
                }
            },

            //列表
            async GetAll() {
                let params = {
                    MaxResultCount: this.pageSize,
                    SkipCount: (this.currentPage - 1) * this.pageSize,
                    ...this.searchForm
                };
                 if(params.main_execute_flag==null||params.main_execute_flag=="")
                {
                params.main_execute_flag=10;
                }
                let data = await getListApi(params);
                if (data) {
                    this.tableData = data.items || [];
                    this.total = data.totalCount || 0;
                    this.multipleSelection = [];
                }
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
            //点击查看查看
            showdialog() {
                if (this.multipleSelection.length == 0) {
                    //this.text_deleteRow  全局定义的提示  在textConfig.js中
                    this.$message.error(this.text_deleteRow);
                } else if (this.multipleSelection.length > 1) {
                    this.$message.error(this.text_selectOne);
                } else {

                    if(this.multipleSelection[0].main_manual_flag=='1')
                    {
                        this.dialogTitle = '查看'
                        this.dialogVisible = true;
                        let row = this.multipleSelection[0];
                        this.dialogForm.main_stock_code = row.main_stock_code;
                        this.dialogForm.slot_code = row.slot1.slot_code;
                        this.dialogForm.main_no = row.main_no;
                        this.statusList.forEach(ele => {
                            if (row.main_execute_flag === ele.id) {
                                this.dialogForm.main_execute_flag = ele.modeName;
                            }
                        });
                        this.main_mode = row.main_mode;
                        this.TaskMainId = row.id;

                        this.Getlist();
                    }
                    else
                    {
                        this.$message({
                            message: '手动任务无法查看！',
                            type: 'warning'
                        });
                    }

                }
            },
            //双击查看
            dbshowdialog(row, event, column) {
                if(row.main_manual_flag=='1')
                {
                    this.dialogVisible = true;
                    this.dialogForm.main_stock_code = row.main_stock_code;
                    this.dialogForm.slot_code = row.slot1.slot_code;
                    this.dialogForm.main_no = row.main_no;

                    this.statusList.forEach(ele => {
                        if (row.main_execute_flag === ele.id) {
                            this.dialogForm.main_execute_flag = ele.modeName;
                        }
                    });
                    this.TaskMainId = row.id;
                    this.main_mode = row.main_mode;
                    this.Getlist();
                }
                else
                {
                    this.$message({
                        message: '手动任务无法查看！',
                        type: 'warning'
                    });
                }

            },
            async Getlist() {
                let params = {
                    MaxResultCount: this.pageSize,
                    SkipCount: (this.currentPage - 1) * this.pageSize,
                    task_id: this.TaskMainId,
                };
                if (this.main_mode == '1' || this.main_mode == '2' || this.main_mode == '6' || this.main_mode == '7') {
                    let data = await getkyprkListApi(params);
                    if (data) {
                        this.lstableData = data.items || [];
                    }
                } 
                /* let data = await getrkListApi(params);
                 if (data) {
                   this.tableData2 = data.items || [];
                   console.log('-------------开始--------------')
                   console.log(this.tableData2)
                   console.log('-------------结束--------------')
                 }*/
            },
            //任务开始
            handleStart() {
                if (this.multipleSelection.length == 0) {
                    //this.text_deleteRow  全局定义的提示  在textConfig.js中
                    this.$message.error(this.text_deleteRow);
                } else {
                    let rows = this.multipleSelection;
                    this.Start(rows);
                }
            },
            //任务完成
            handleFinish() {
                if (this.multipleSelection.length == 0) {
                    //this.text_deleteRow  全局定义的提示  在textConfig.js中
                    this.$message.error(this.text_deleteRow);
                } else {
                    let rows = this.multipleSelection;
                    this.Finish(rows);
                }
            },
            //调整优先级按钮
            handleSort() {
                if (this.multipleSelection.length == 0) {
                    //this.text_deleteRow  全局定义的提示  在textConfig.js中
                    this.$message.error(this.text_deleteRow);
                } else {
                    this.singleDialogTitle = "调整优先级";
                    this.singleDialogVisible = true;
                    let row = this.multipleSelection[0];
                    this.Get(row);
                }
            },
            //修改状态
            handleModify() {
                if (this.multipleSelection.length == 0) {
                    this.$message.error(this.text_deleteRow);
                } else if (this.multipleSelection.length > 1) {
                    this.$message.error(this.text_selectOne);
                } else {

                    console.log('-------------kaishi 1111------------')
                    console.log(this.multipleSelection[0])

                    this.singleDialogTitle = "修改状态";
                    this.singleDialogVisible = true;


                    this.ruleForm.main_execute_flag = ''
                    this.ruleForm.main_priority = ''


                }
            },
            //点击删除按钮
            handleDelete() {
                if (this.multipleSelection.length == 0) {
                    //this.text_deleteRow  全局定义的提示  在textConfig.js中
                    this.$message.error(this.text_deleteRow);
                } else {
                    let row = this.multipleSelection[0];
                    this.Delete(row);
                }
            },
            click_search() {
                this.currentPage = 1;
                this.GetAll();
            },
            click_create() {
                //this.dialogVisibleA=true;
                this.GetEncodingRuleCode();
                 this.resetForm();

            },
            //表单重置
            async resetForm() {

                      this.dialogVisibleA = true;
                      await this.$nextTick();
                      this.$refs["dialogFormA"].resetFields();

            },
            empty() {
                this.ManualForm.main_priority = ''
                this.ManualForm.main_mode = ''
                this.ManualForm.warehouse_id = ''
                this.ManualForm.main_slot_code = ''
                this.ManualForm.main_inslot_code = ''
                this.ManualForm.main_stock_code = ''
            },
            //点击执行
            click_modify(val) {
                this.$refs["areaForm"].validate(valid => {
                    if (!valid) {
                        return;
                    }
                    let row = this.multipleSelection[0];
                    this.$msgbox({
                        title: "提示",
                        message: "确定修改吗？",
                        showCancelButton: true,
                        type: "warning"
                    }).then(async action => {
                        /*let list=[];
                        row.forEach(item =>{
                          list.push(item.id);
                        })*/
                        let params = row;

                        if (this.ruleForm.main_execute_flag)
                            params.main_execute_flag = this.ruleForm.main_execute_flag
                        if (this.ruleForm.main_priority)
                            params.main_priority = this.ruleForm.main_priority
                        params.slot1 = null;
                        params.exporder_quantity = null;
                        console.log('-------------kaishi ------------')
                        console.log(params)
                        let res = await editApi(params);
                        if (res) {
                            this.$message({
                                type: "success",
                                message: "修改成功"
                            });
                            this.GetAll();
                            this.singleDialogVisible = false;
                        }
                    });
                });
            },
            //根据主键删除
            Delete(row) {
                const h = this.$createElement;
                this.$msgbox({
                    title: "提示",
                    message: h("p", null, [
                        h("span", null, "确定【"),
                        h("span", {style: this.c_danger}, "删除"),
                        h("span", null, "】吗？")
                    ]),
                    showCancelButton: true,
                    type: "warning"
                }).then(async action => {
                    let params = {
                        id: row.id
                    };
                    //使用await方法前要加async
                    let res = await deleteApi(params);
                    if (res) {
                        this.$message({
                            type: "success",
                            message: "删除成功"
                        });
                        this.GetAll();
                        this.dialogVisible = false;
                    }
                });
            },
            //编辑
            Update(row) {
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
                            "修改"
                        ),
                        h("span", null, "】吗？")
                    ]),
                    showCancelButton: true,
                    type: "warning"
                }).then(async action => {
                    let params = row;
                    let res = await editApi(params);
                    if (res) {
                        this.$message({
                            type: "success",
                            message: "修改成功"
                        });
                        this.GetAll();
                        this.singleDialogVisible = false;
                    }
                });
            },
            //开始任务
            Start(rows) {
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
                            "开始"
                        ),
                        h("span", null, "】吗？")
                    ]),
                    showCancelButton: true,
                    type: "warning"
                }).then(async action => {
                    let idList = [];
                    rows.forEach(ele => {
                        idList.push(ele.id);
                    });
                    let res = await startApi(idList);
                    if (res) {
                        this.$message({
                            type: "success",
                            message: "开始成功"
                        });
                        this.GetAll();
                    }
                })
            },
            //任务完成
            Finish(rows) {
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
                            "完成"
                        ),
                        h("span", null, "】吗？")
                    ]),
                    showCancelButton: true,
                    type: "warning"
                }).then(async action => {
                    let idList = [];
                    rows.forEach(ele => {
                        idList.push(ele.id);
                    });
                    let res = await finishApi(idList);
                    if (res) {
                        this.$message({
                            type: "success",
                            message: "完成成功"
                        });
                        this.GetAll();
                    }
                })
            },
            //根据主键获取信息
            async Get(row) {
                let params = {id: row.id};
                let res = await getOneApi(params);
                let obj = {
                    id: row.id,
                    // main_no:res.main_no,
                    main_priority: res.main_priority,
                    // main_mode:main_mode,
                    // main_slot_code:res.main_slot_code,
                    // main_stock_code:res.main_stock_code,
                    main_execute_flag: res.main_execute_flag,
                    // main_manual_flag:res.main_manual_flag,
                    // main_port:res.main_port,
                    // material_name:res.material_name,
                    // exporder_batch_no:res.exporder_batch_no,
                    // exporder_quantity:res.exporder_quantity,
                };
                this.ruleForm = obj;
            },
            //执行状态转换
            executeTransform(row) {
                let status;
                this.statusList.forEach(ele => {
                    if (row.main_execute_flag === ele.id) {
                        status = ele.modeName;
                    }
                });
                return status;
            },
            //日期时间转换
            dateTimeTransform(row) {
                let time = row.creationTime;
                return this.$utils.format(time,'yyyy-MM-dd hh:mm:ss');
            }
        }
    };
</script>