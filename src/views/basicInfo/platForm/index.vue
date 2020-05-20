<template>
	<div class="iot-list" ref="page">
		<div class="iot-form">
			<div class="layout__search">
				<el-form inline label-width="100px" :model="searchForm">
					<el-form-item label="月台编号">
						<el-input class="iot-w200" placeholder="请输入月台编号" maxlength="30" v-model="searchForm.platform_code" clearable></el-input>
					</el-form-item>
					<el-form-item label="月台名称">
						<el-input class="iot-w200" placeholder="请输入月台名称" maxlength="50" v-model="searchForm.platform_name" clearable></el-input>
					</el-form-item>
					<el-form-item label="所在仓库">
						<el-select class="iot-w200" v-model="searchForm.warehouse_id" placeholder="请选择" clearable>
							<el-option v-for="item in wList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="状态">
						<el-select class="iot-w200" v-model="searchForm.platform_state" placeholder="请选择" clearable>
							<el-option v-for="item in pList" :key="item.id" :label="item.label" :value="item.id"></el-option>
						</el-select>
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
					<el-button type="primary" plain icon="el-icon-document" @click="exportExcel">导出</el-button>
					<el-button type="primary" plain icon="el-icon-printer"  @click="printTable">打印</el-button>
				</div>
			</div>
		</div>
		<div class="iot-table">
			<el-table id="out-table" :data="tableData" stripe lazy style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange" @row-click="clickRow" ref="print">
				<el-table-column v-if="visibleSelection" type="selection" width="55"></el-table-column>
				<el-table-column align="center" type="index" label="序号" width="50" :index="indexMethod"></el-table-column>
				<el-table-column align="center" prop="platform_code" label="月台编号" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="platform_name" label="月台名称" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="warehouse.warehouse_name" label="仓库" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="platform_state_label" label="状态" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="platform_remark" label="备注" min-width="100" show-overflow-tooltip></el-table-column>
			</el-table>
		</div>
		<div class="iot-pagination">
			<el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
		</div>
		<el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
			<el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
				<div class="iot-form-row">
					<el-form-item label="月台编号" label-width="100px" prop="platform_code">
						<el-input class="iot-w240" v-model="ruleForm.platform_code" placeholder="请输入月台编号" clearable maxlength="40"></el-input>
					</el-form-item>
					<el-form-item label="月台名称" label-width="100px" prop="platform_name">
						<el-input class="iot-w240" v-model="ruleForm.platform_name" placeholder="请输入月台名称" clearable maxlength="50"></el-input>
					</el-form-item>
					<el-form-item label="所在仓库" label-width="100px" prop="platform_warehouse_id">
						<el-select class="iot-w240" v-model="ruleForm.platform_warehouse_id" placeholder="请选择">
							<el-option v-for="item in wList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="月台状态" label-width="100px" prop="platform_state">
						<el-select class="iot-w240" v-model="ruleForm.platform_state" placeholder="请选择">
							<el-option v-for="item in pList" :key="item.id" :label="item.label" :value="item.id"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="备注" label-width="100px" prop="platform_remark">
						<el-input class="iot-w240" :rows="5" maxlength="200" show-word-limit type="textarea" v-model="ruleForm.platform_remark" placeholder="请输入备注" clearable></el-input>
					</el-form-item>
					<companycontrol v-if="userId==1" :companyId="companyId" @updateCompanyId="updateCompanyId($event)"></companycontrol>
				</div>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button size="small" @click="dialogVisible = false">取 消</el-button>
				<el-button size="small" type="primary" @click="click_submit">保 存</el-button>
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
		deleteApi,
		getWarehouseInfoListApi
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
            lang: function(lang) {
			this.$utils.traversePageDom.call(this.$utils,this.$store.state.currentLang,lang,this.$refs.page);
			this.$utils.traverseFormValidator(this.rules,this.lang)
            }
        },
		data() {
			return {
				visibleSelection:true,
				multipleSelection: [],
				radio1: "",
				searchForm: {
				},
				dialogTitle: "新增",
				tableData: [],
				wList:[],
				pList: [{id:1,label:"空闲"},{id:2,label:"卸货"}],
				currentPage: 1,
				pageSize: 10,
				total: 0,
				dialogVisible: false,
				//表单数据
				ruleForm: {
					platform_code:"",
					platform_name:"",
					platform_warehouse_id:"",
					platform_remark:"",
					platform_state:"",
					platform_company_id:'',
				},
				//表单验证规则
				rules: {
					platform_code: [{
						required: true,
						message: "请输入月台编号",
						trigger: "blur"
					}],
					platform_name: [{
						required: true,
						message: "请输入月台名称",
						trigger: "blur"
					}],
					platform_warehouse_id: [{
						required: true,
						message: "请选择所在仓库",
						trigger: "change"
					}],
					platform_state: [{
						required: true,
						message: "请选择月台状态",
						trigger: "change"
					}]
				},
				companyId:'',
				userId:'',
			};
		},
		mounted() {
			this.btnInit();
			this.GetWarehouseAll();
			this.GetAll(1);
		},
		created() {
			if (Utils.getStorage("userInfo")) {
				this.ruleForm.platform_company_id = Utils.getStorage("userInfo").data.result.companyId;
				this.userId = Utils.getStorage("userInfo").data.result.userId;
				this.companyId = Utils.getStorage("userInfo").data.result.companyId;
			}
		},

		methods: {
			updateCompanyId(val) {
				if (val)
					this.ruleForm.platform_company_id = val;
				else
					this.ruleForm.platform_company_id = Utils.getStorage("userInfo").data.result.companyId;
			},
			//根据当前用户权限标识初始化按钮状态
			btnInit() {
				this.$Common.DistributePermission.call(this)
			},
			handleCurrentChange(val) {
				//this.currentPage = val;
				this.tableData = [];
				this.GetAll(val);
			},
			indexMethod(index) {
				let i = 0;
				i = this.pageSize * (this.currentPage - 1) + index + 1;
				return i;
			},
			//表单重置
			async resetForm() {
				await this.$nextTick(()=>{
					if(typeof this.$refs["dialogForm"] !== 'undefined'){
						let defaultFormData = {
   							platform_code:"",
							platform_name:"",
							platform_warehouse_id:"",
							platform_remark:"",
							platform_state:""
						};
						this.ruleForm = Object.assign({}, {}, defaultFormData )
						this.$refs["dialogForm"].resetFields();
					}
				});
				this.$utils.traverseDialogDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.dialogForm);
			},
			async GetWarehouseAll() {
				let params = {
				};
				let data = await getWarehouseInfoListApi(params);
				if(data){
					this.wList = data || [];
				}
			},
			//列表
			async GetAll(val) {
				
				let params = {
					MaxResultCount: this.pageSize,
					SkipCount: (val - 1) * this.pageSize,
					...this.searchForm
				};
				let data = await getListApi(params);
				if(data){
					this.tableData = data.items || [];
					this.total = data.totalCount || 0;
					if(this.tableData.length > 0){
						this.tableData.forEach((v, i, a) => {
							let ala = this.pList.find(val => val.id === v.platform_state);
							if(!!ala)
								v.platform_state_label = ala.label;
						});
					}
					
				}
				this.currentPage = val;
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
						h("span", null, "确定"),
						h("span", {
							style: this.c_primary
						}, "保存"),
						h("span", null, "吗？")
					]),
					showCancelButton: true,
					type: "warning"
				}).then(async action => {
					let params = {
						...this.ruleForm
					};
					let res = await addApi(params);
					if(res){
						this.$message({
							type: "success",
							message: "新增成功"
						});
						this.GetAll(1);
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
				if(this.multipleSelection.length == 1){
					this.click_edit(this.multipleSelection[0]);
				}else{
					//this.text_selectOne  全局定义的提示  在textConfig.js中
					this.$message.error(this.text_selectOne);
				}
			},
			//点击删除按钮
			handleDelete() {
				if(this.multipleSelection.length == 0){
					this.$message.error(this.text_deleteRow);
				}else{
					let row = this.multipleSelection;
					this.Delete(row);
				}
			},
			click_edit(row) {
				this.dialogTitle = "编辑";
				this.resetForm();
				this.Get(row);
			},
			click_search() {
				this.GetAll(1);
			},
			click_add() {
				this.dialogTitle = "新增";
				this.resetForm();
				this.dialogVisible = true;
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
			//根据主键删除
			Delete(row) {
				const h = this.$createElement;
				this.$msgbox({
					title: "提示",
					message: h("p", null, [
					  h("span", null, "确定"),
					  h("span", { style: this.c_danger }, "删除"),
					  h("span", null, "吗？")
					]),
					showCancelButton: true,
					type: "warning"
				}).then(async action => {
					let pra = [];
					let deleteCount=0;
					row.forEach((v, i, a) => {
						pra.push(v.id);
						deleteCount++;
					});
					let params = {
						idList: pra
					};
					//使用await方法前要加async
					let res = await deleteApi(params);
					if(res){
						this.$message({
							type: "success",
							message: "删除成功"
						});
						if((this.total-deleteCount)%10==0 && this.currentPage!=1)
						{
							this.currentPage-=1;
						}
						this.GetAll(1);
						this.dialogVisible = false;
					}
				});
			},
			//编辑
			Update() {
				const h = this.$createElement;
				this.$msgbox({
					title: "提示",
					message: h("p", null, [
						h("span", null, "确定"),
						h("span", {
							style: this.c_primary
						}, "保存"),
						h("span", null, "吗？")
					]),
					showCancelButton: true,
					type: "warning"
				}).then(async action => {
					let params = {
						...this.ruleForm
					};
					let res = await editApi(params);
					if(res){
						this.$message({
							type: "success",
							message: "修改成功"
						});
						this.GetAll(1);
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
				if(!res){
					this.dialogVisible = false;
					this.$message({
						type: "error",
						message: "操作的记录不存在或者已被删除！"
					});
					return;
				}else{
					this.dialogVisible = true;
				}
				let obj = {
					id:row.id,
					platform_code:row.platform_code,
					platform_name:row.platform_name,
					platform_warehouse_id:row.platform_warehouse_id,
					platform_remark:row.platform_remark,
					platform_state:row.platform_state,
					platform_company_id:res.platform_company_id,
				};
				this.companyId=res.platform_company_id;
				this.ruleForm = obj;
			},
			clickRow(row){
				this.$refs.print.toggleRowSelection(row);
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