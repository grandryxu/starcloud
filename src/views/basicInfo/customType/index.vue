<template>
	<div class="iot-list" ref="page">
		<div class="iot-form">
			<div class="layout__search">
				<el-form inline label-width="100px" :model="searchForm">
					<el-form-item label="类别编号">
						<el-input class="iot-w200" placeholder v-model="searchForm.customtype_code" clearable></el-input>
					</el-form-item>
					<el-form-item label="类别名称">
						<el-input class="iot-w200" placeholder v-model="searchForm.customtype_name" clearable></el-input>
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
			<el-table id="out-table"  :data="tableData" stripe style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange" @row-click="clickRow" ref="pTable">
				<el-table-column v-if="visibleSelection" type="selection" width="55"></el-table-column>
				<el-table-column align="center" type="index" label="序号" width="50" :index="indexMethod"></el-table-column>
				<el-table-column align="center" prop="customtype_code" label="类别编号" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="customtype_name" label="类别名称" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="customtype_remark" label="备注" min-width="100" show-overflow-tooltip></el-table-column>
			</el-table>
		</div>
		<div class="iot-pagination">
			<el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
		</div>
		<el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
			<el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
				<div class="iot-form-row">
					<el-form-item label="类别编号" label-width="100px" prop="customtype_code">
						<el-input class="iot-w240" v-model="ruleForm.customtype_code" placeholder="请输入类别编号" maxlength="30" clearable></el-input>
					</el-form-item>
					<el-form-item label="类别名称" label-width="100px" prop="customtype_name">
						<el-input class="iot-w240" v-model="ruleForm.customtype_name" placeholder="请输入类别名称" maxlength="50" clearable></el-input>
					</el-form-item>
					<el-form-item label="备注" label-width="100px" prop="customtype_remark">
						<el-input class="iot-w240" type="textarea" v-model="ruleForm.customtype_remark" :rows="5" maxlength="200" show-word-limit placeholder="请输入备注" clearable></el-input>
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
		deleteApi
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
				isEnableList: [{id:0,enableName:"正常"},{id:1,enableName:"停用"}],
				currentPage: 1,
				pageSize: 10,
				total: 0,
				dialogVisible: false,
				//表单数据
				ruleForm: {
					customtype_name:"",
					customtype_code:"",
					customtype_remark:"",
					customtype_company_id:'',
				},
				//表单验证规则
				rules: {
					customtype_name: [{
						required: true,
						message: "请输入类别名称",
						trigger: "blur"
					}],
					customtype_code: [{
						required: true,
						message: "请输入类别编码",
						trigger: "blur"
					}]
				},
				companyId:'',
				userId:'',
			};
		},
		mounted() {
			this.btnInit();
			this.GetAll();
		},
		created() {
			if (Utils.getStorage("userInfo")) {
				this.ruleForm.customtype_company_id = Utils.getStorage("userInfo").data.result.companyId;
				this.userId = Utils.getStorage("userInfo").data.result.userId;
				this.companyId = Utils.getStorage("userInfo").data.result.companyId;
			}
		},
		methods: {
			updateCompanyId(val) {
				if (val)
					this.ruleForm.customtype_company_id = val;
				else
					this.ruleForm.customtype_company_id = Utils.getStorage("userInfo").data.result.companyId;
			},
			//根据当前用户权限标识初始化按钮状态
			btnInit() {
				this.$Common.DistributePermission.call(this)
			},
			clickRow(row){
				this.$refs.pTable.toggleRowSelection(row);
            },
			handleSizeChange(val) {
				this.pageSize = val;
				this.currentPage = 1;
				this.GetAll();
			},
			handleCurrentChange(val) {
				this.tableData = [];
				this.currentPage = val;
				this.GetAll();
			},
			indexMethod(index) {
				return this.pageSize * (this.currentPage - 1) + index + 1;
			},
			//表单重置
			async resetForm() {
				await this.$nextTick(()=>{
					if(typeof this.$refs["dialogForm"] !== 'undefined'){
						let defaultFormData = {
   							customtype_name:"",
							customtype_code:"",
							customtype_remark:"",
							customtype_company_id:''
						};
						this.ruleForm = Object.assign({}, {}, defaultFormData )
						this.$refs["dialogForm"].resetFields();
					}
				});
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
				if(data){
					this.tableData = data.items || [];
					this.total = data.totalCount || 0;
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
					//this.text_deleteRow  全局定义的提示  在textConfig.js中
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
				this.currentPage = 1;
				this.GetAll();
			},
			click_add() {
				this.resetForm();
				this.dialogTitle = "新增";
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
						this.GetAll();
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
					customtype_name:res.customtype_name,
					customtype_code:res.customtype_code,
					customtype_remark:res.customtype_remark,
					customtype_company_id:res.customtype_company_id,
				};
				this.companyId=res.customtype_company_id,
				this.ruleForm = obj;
			},
			//导出表格
			exportExcel() {
				this.$Common.ExportExcel("#out-table")
			},
			//打印表格
			printTable(){
				this.$Common.PrintTable.call(this,'pTable')
			}
		}
	};
</script>