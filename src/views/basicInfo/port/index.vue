<template>
    <div class="iot-list" ref="page">
        <div class="iot-form">
            <div class="layout__search">
                <el-form inline label-width="100px" :model="searchForm">
                    <el-form-item label="出入口编码">
                        <el-input class="iot-w200" placeholder v-model="searchForm.port_code"></el-input>
                    </el-form-item>
                    <el-form-item label="出入口名称">
                        <el-input class="iot-w200" placeholder v-model="searchForm.port_name"></el-input>
                    </el-form-item>
                    <el-form-item label="出入口类型">
                        <el-select class="iot-w200" v-model="searchForm.port_type" placeholder="请选择出入口类型" clearable>
                            <el-option v-for="item in portTypeList" :key="item.id" :label="item.portType"
                                       :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">查询
                        </el-button>
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
                    <el-button type="primary" plain icon="el-icon-setting" @click="setTunnel">设定巷道</el-button>
                </div>
            </div>
        </div>
        <div class="iot-table">
            <el-table id="out-table" ref="print" :data="tableData" stripe style="width: 100%" border
                      @row-dblclick="click_edit" @selection-change="handleSelectionChange">
                <el-table-column v-if="visibleSelection" type="selection" width="55"></el-table-column>
                <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
                <el-table-column align="center" prop="port_code" label="出入口编码" min-width="100"
                                 show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="port_name" label="出入口名称" min-width="100"
                                 show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="port_type" label="出入口类型" min-width="100" show-overflow-tooltip>
                    <template slot-scope="scope">
                        <span v-if="scope.row.port_type === 1">入口</span>
                        <span v-else-if="scope.row.port_type === 2">出口</span>
                        <span v-else-if="scope.row.port_type === 3">双向</span>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="iot-pagination">
            <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize"
                           :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
        </div>
        <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
            <el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
                <div class="iot-form-row">
                    <el-form-item label="出入口编码" label-width="100px" prop="port_code">
                        <el-input class="iot-w240" v-model="ruleForm.port_code" placeholder="请输入出入口编码" maxlength="30"
                                  clearable></el-input>
                    </el-form-item>
                    <el-form-item label="出入口名称" label-width="170px" prop="port_name">
                        <el-input class="iot-w240" v-model="ruleForm.port_name" placeholder="请输入出入口名称" maxlength="50"
                                  clearable></el-input>
                    </el-form-item>
                </div>
                <div class="iot-form-row">
                    <el-form-item label="出入口类型" label-width="100px" prop="port_type">
                        <el-select class="iot-w240" v-model="ruleForm.port_type" placeholder="请选择出入口类型">
                            <el-option v-for="item in portTypeList" :key="item.id" :label="item.portType"
                                       :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="所在仓库" label-width="170px" prop="port_warehouse_id">
                        <el-select class="iot-w240" v-model="ruleForm.port_warehouse_id" placeholder="请选择" clearable>
                            <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name"
                                       :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                </div>
                <div class="iot-form-row">
                    <companycontrol v-if="userId==1" :companyId="companyId"
                                    @updateCompanyId="updateCompanyId($event)"></companycontrol>
                </div>
                <!-- <div class="iot-form-row">
                    <el-form-item label="修改备注" label-width="100px" prop="remark">
                        <el-input class="iot-w660" v-model="ruleForm.remark" type="textarea" :rows="4" :maxlength="200">
                        </el-input>
                    </el-form-item>
                </div> -->
            </el-form>
            <span slot="footer" class="dialog-footer">
				<el-button size="small" @click="dialogVisible = false">取 消</el-button>
				<el-button size="small" type="primary" @click="click_submit">保 存</el-button>
			</span>
        </el-dialog>


        <el-dialog class="iot-dialog" :title="tunneltitle" :visible.sync="tunneldialogVisible" width="424px"
                   append-to-body>
            <el-form inline ref="tunneldialogForm" :model="tunnelruleForm" :rules="tunnelrules">
                <div class="iot-form-row">
                    <el-form-item label="选择巷道" label-width="100px" prop="tunnellist">
                        <el-select v-model="tunnelruleForm.tunnellist" multiple placeholder="请选择">
                            <el-option
                                    v-for="item in tunneloptions"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </div>

            </el-form>
            <span slot="footer" class="dialog-footer">
				<el-button size="small" @click="tunneldialogVisible = false">取 消</el-button>
				<el-button size="small" type="primary" @click="Tunnelsubmit">保 存</el-button>
			</span>
        </el-dialog>
    </div>
</template>

<script>
    import textConfig from '@/mixins/textConfig.js'
    import singlePictureUpload from "_c/singlePictureUpload"
    import Utils from '@/utils/index'
    import Companycontrol from "../../../components/companycontrol";
    import {
        getListApi,
        addApi,
        getOneApi,
        editApi,
        deleteListApi,
        addtunnelApi,
    } from "./api";

    export default {
        //全局混入提示文字
        mixins: [textConfig],
        components: {
            singlePictureUpload,
            Companycontrol,
        },
        props: ["lang"],

        watch: {
            lang: function (lang) {
                this.$utils.traversePageDom.call(this.$utils, this.$store.state.currentLang, lang, this.$refs.page);
                this.$utils.traverseFormValidator(this.rules, this.lang)
            }
        },

        data() {
            return {
                visibleSelection: true,
                multipleSelection: [],
                radio1: "",
                searchForm: {},
                dialogTitle: "新增",
                tableData: [],
                portTypeList: [{id: 1, portType: "入口"}, {id: 2, portType: "出口"}, {id: 3, portType: "双向"}],
                currentPage: 1,
                pageSize: 10,
                total: 0,
                dialogVisible: false,
                //表单数据
                ruleForm: {
                    port_code: "",
                    port_name: "",
                    port_type: "",
                    port_company_id: '',
                    port_warehouse_id: '',
                },
                //表单验证规则
                rules: {
                    port_code: [{
                        required: true,
                        message: "请输入出入口编码",
                        trigger: "blur"
                    }],
                    port_name: [{
                        required: true,
                        message: "请输入出入口名称",
                        trigger: "blur"
                    }],
                    port_type: [{
                        required: true,
                        message: "请输入出入口类型",
                        trigger: "blur"
                    }],
                    port_warehouse_id: [{
                        required: true,
                        message: "请选择仓库",
                        trigger: "blur"
                    }]
                },
                tunnelrules: {
                    tunnellist: [{
                        required: true,
                        message: "请选择巷道",
                        trigger: "blur"
                    }]
                },
                deleteList: [],
                companyId: '',
                userId: '',
                warehouseList: [],
                tunnelSelection: [],
                tunneldialogVisible: false,
                tunneltitle: '设定巷道',
                tunneloptions: [],
                tunnelruleForm: {
                    tunnellist: '',
                }
            };
        },
        mounted() {
            this.btnInit();
            this.GetAll();
            this.GetWarehouseList();
            this.GetTunnelList();
        },
        created() {
            if (Utils.getStorage("userInfo")) {
                this.ruleForm.port_company_id = Utils.getStorage("userInfo").data.result.companyId;
                this.userId = Utils.getStorage("userInfo").data.result.userId;
                this.companyId = Utils.getStorage("userInfo").data.result.companyId;
            }
        },
        methods: {
            //巷道下拉
            async GetTunnelList() {
                let data = await this.$DropBox.getTunnellist();
                console.log(data, 77777)
                if (data) {
                    let list = [];


                    data.items.forEach(ele => {
                        list.push({
                            value: ele.id,
                            label: ele.tunnel_name,
                        });
                    })
                    this.tunneloptions = list;
                }
            },
            //仓库列表
            async GetWarehouseList() {
                let data = await this.$DropBox.getWarehouselist();
                if (data) {
                    console.log(data)
                    this.warehouseList = data || [];
                }
            },
            updateCompanyId(val) {
                if (val)
                    this.ruleForm.port_company_id = val;
                else
                    this.ruleForm.port_company_id = Utils.getStorage("userInfo").data.result.companyId;
            },
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


                this.$utils.traverseDialogDom.call(this.$utils, this.$store.state.currentLang, this.lang, this.$refs.dialogForm);
            },
            //巷道表单重置
            async restetunnelForm() {
                this.tunneldialogVisible = true;
                await this.$nextTick();
                this.$refs["tunneldialogForm"].resetFields();
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
                this.$utils.traversePageDom.call(this.$utils, this.$store.state.currentLang, this.lang, this.$refs.page);
                this.$utils.traverseFormValidator(this.rules, this.lang)

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
                        h("span", {
                            style: this.c_primary
                        }, "保存"),
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
            },
            setTunnel() {
                if (this.multipleSelection.length == 1) {
                    this.click_setTunnel(this.multipleSelection[0]);
                    this.restetunnelForm();
                } else {
                    //this.text_selectOne  全局定义的提示  在textConfig.js中
                    this.$message.error(this.text_selectOne);
                }
            },
            click_setTunnel() {

            },
            Tunnelsubmit() {


                console.log(this.multipleSelection[0], 7777)
                console.log(this.tunnelruleForm.tunnellist, 8888)
                this.$refs["tunneldialogForm"].validate(valid => {
                    if (!valid) {
                        return;
                    }


                    this.$msgbox({
                        title: "提示",
                        message: "确定保存吗?",
                        showCancelButton: true,
                        type: "warning"
                    }).then(() => {
						this.SaveList()
                    });


                });


            },
			async SaveList() {
				let list = [];
				this.tunnelruleForm.tunnellist.forEach(ele => {
					list.push({
						tunnelPort_tunnel_id: ele,
						tunnelPort_port_id: this.multipleSelection[0].id,
						tunnelPort_company_id: this.multipleSelection[0].port_company_id,
					})

				})

				let res = await addtunnelApi(list);
				if (res) {
					this.$message({
						type: "success",
						message: "保存成功"
					});
					this.GetAll();
					this.tunneldialogVisible = false;
				}
			},
    //点击保存
    click_submit()
    {
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
    }
    ,
    //根据主键删除
    Delete(rows)
    {
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
            let deleteCount = 0;
            for (let i = 0; i < rows.length; i++) {
                this.deleteList.push(rows[i].id);
                deleteCount++;
            }
            if (this.deleteList.length) {
                let res = await deleteListApi(JSON.stringify(this.deleteList));
                if (res) {
                    this.$message({
                        type: "success",
                        message: '删除成功'
                    });
                } else {
                    this.$message({
                        type: "error",
                        message: '系统繁忙请稍后再试'
                    });
                }
            }
            if ((this.total - deleteCount) % 10 == 0 && this.currentPage != 1) {
                this.currentPage -= 1;
            }
            this.GetAll();
        });
    }
    ,
    //编辑
    Update()
    {
        const h = this.$createElement;
        this.$msgbox({
            title: "提示",
            message: h("p", null, [
                h("span", null, "确定【"),
                h("span", {
                    style: this.c_primary
                }, "保存"),
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
    }
    ,
    //根据主键获取信息
    async Get(row)
    {
        let params = {
            id: row.id
        };
        let res = await getOneApi(params);
        let obj = {
            id: row.id,
            port_code: res.port_code,
            port_name: res.port_name,
            port_type: res.port_type,
            port_company_id: res.port_company_id,
            port_warehouse_id: res.port_warehouse_id,
        };
        this.companyId = res.port_company_id;
        this.ruleForm = obj;
    }
    ,
    //导出表格
    exportExcel()
    {
        this.$Common.ExportExcel("#out-table")
    }
    ,
    //打印表格
    printTable()
    {
        this.$Common.PrintTable.call(this, 'print')
    }
    }
    }
    ;
</script>