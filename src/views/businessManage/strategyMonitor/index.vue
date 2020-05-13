<template>
	<div class="iot-list" ref="page">
		<div class="iot-form">
			<div class="layout__search">
				<el-form inline label-width="100px" :model="searchForm">
					<el-form-item label="策略名称">
						<el-input class="iot-w200" placeholder="请输入策略名称" maxlength="50" v-model="searchForm.monitor_name"></el-input>
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
					<el-button type="primary" plain icon="el-icon-printer" @click="printTable">打印</el-button>
				</div>
			</div>
		</div>
		<div class="iot-table">
			<el-table id="out-table" ref="print" :data="tableData" stripe style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55"></el-table-column>
				<el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
				<el-table-column align="center" prop="monitor_name" label="策略名称" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="monitor_expired_days" label="过期天数" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="monitor_days_max" label="最大库龄天数" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="monitor_stock_max" label="最大库存" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="monitor_stock_min" label="最小库存" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="monitor_recheck_days" label="复检天数" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="monitor_remark" label="备注" min-width="100" show-overflow-tooltip></el-table-column>
			</el-table>
		</div>
		<div class="iot-pagination">
			<el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
		</div>
		<el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="750px" append-to-body>
			<el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
				<div class="iot-form-row">
					<el-form-item label="策略名称" label-width="120px" prop="monitor_name">
						<el-input class="iot-w240" v-model="ruleForm.monitor_name" maxlength="50" placeholder="请输入策略名称" clearable></el-input>
					</el-form-item>
					<el-form-item label="过期天数" label-width="90px" prop="monitor_expired_days">
						<el-input class="iot-w240" v-model="ruleForm.monitor_expired_days" type='number' placeholder="请输入过期天数" maxlength="20" clearable></el-input>
					</el-form-item>
				</div>
				<div class="iot-form-row">
					<el-form-item label="最大库龄天数" label-width="120px" prop="monitor_days_max">
						<el-input class="iot-w240" v-model="ruleForm.monitor_days_max" type='number' placeholder="请输入最大库龄天数" maxlength="20" clearable></el-input>
					</el-form-item>
					<el-form-item label="最大库存" label-width="90px" prop="monitor_stock_max">
						<el-input class="iot-w240" v-model="ruleForm.monitor_stock_max" type='number' placeholder="请输入最大库存" maxlength="20" clearable></el-input>
					</el-form-item>
				</div>
				<div class="iot-form-row">
					<el-form-item label="最小库存" label-width="120px" prop="monitor_stock_min">
						<el-input class="iot-w240" v-model="ruleForm.monitor_stock_min" type='number' placeholder="请输入最小库存" maxlength="20" clearable></el-input>
					</el-form-item>
					<el-form-item label="复检天数" label-width="90px" prop="monitor_recheck_days">
						<el-input class="iot-w240" v-model="ruleForm.monitor_recheck_days" type='number' placeholder="请输入复检天数" maxlength="20" clearable></el-input>
					</el-form-item>
				</div>
				<div class="iot-form-row">
					<el-form-item label="备注" label-width="120px" prop="monitor_remark">
						<el-input class="iot-w240" v-model="ruleForm.monitor_remark" type="textarea" :rows="4" :maxlength="200" show-word-limit></el-input>
					</el-form-item>
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
				multipleSelection: [],
				radio1: "",
				searchForm: {
				},
				dialogTitle: "新增",
				tableData: [],
				productList: [{id:1,salesName:"测试"}],
				currentPage: 1,
				pageSize: 10,
				total: 0,
				dialogVisible: false,
				//表单数据
				ruleForm: {
					monitor_name:"",
					monitor_expired_days:"",
					monitor_days_max:"",
					monitor_stock_max:"",
					monitor_stock_min:"",
					monitor_recheck_days:"",
					monitor_remark: ""
				},
				//表单验证规则
				rules: {
					monitor_name: [{
						required: true,
						message: "请输入策略名称",
						trigger: "blur"
					}],
					monitor_expired_days: [{
						required: true,
						message: "请输入过期天数",
						trigger: "blur"
					}],
					monitor_days_max: [{
						required: true,
						message: "请输入最大库龄天数",
						trigger: "blur"
					}],
					monitor_stock_max: [{
						required: true,
						message: "请输入最大库存",
						trigger: "blur"
					}],
					monitor_stock_min: [{
						required: true,
						message: "请输入最小库存",
						trigger: "blur"
					}],
					monitor_recheck_days: [{
						required: true,
						message: "请输入复检天数",
						trigger: "blur"
					}]
				}
			};
		},
		mounted() {
			this.GetAll();
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
						h("span", null, "确定【"),
						h("span", {
							style: this.c_primary
						}, "新增"),
						h("span", null, "】吗？")
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
							deleteCount++
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
			//编辑
			Update() {
				const h = this.$createElement;
				this.$msgbox({
					title: "提示",
					message: h("p", null, [
						h("span", null, "确定【"),
						h("span", {
							style: this.c_primary
						}, "编辑"),
						h("span", null, "】吗？")
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
				let obj = {
					id:row.id,
					monitor_name:res.monitor_name,
					monitor_expired_days:res.monitor_expired_days,
					monitor_days_max:res.monitor_days_max,
					monitor_stock_max:res.monitor_stock_max,
					monitor_stock_min: res.monitor_stock_min,
					monitor_recheck_days: res.monitor_recheck_days,
					monitor_remark: res.monitor_remark
				};
				this.ruleForm = obj;
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