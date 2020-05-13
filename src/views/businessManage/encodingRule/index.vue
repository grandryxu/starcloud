<template>
	<div class="iot-list" ref="page">
		<div class="iot-form">
			<div class="layout__search">
				<el-form inline label-width="100px" :model="searchForm">
					<el-form-item label="规则编码">
						<el-input class="iot-w200" placeholder="请输入规则编码" maxlength="50" v-model="searchForm.code_code"></el-input>
					</el-form-item>
					<el-form-item label="编码名称">
						<el-input class="iot-w200" placeholder="请输入编码名称" maxlength="50" v-model="searchForm.code_name"></el-input>
					</el-form-item>
					<el-form-item label="前缀">
						<el-input class="iot-w200" placeholder="请输入前缀" maxlength="50" v-model="searchForm.code_prefix"></el-input>
					</el-form-item>
					<el-form-item label="日期类型">
						<el-select class="iot-w200" v-model="searchForm.code_date_type" placeholder="请选择日期类型" clearable>
							<el-option v-for="item in dateTypeList" :key="item.id" :label="item.dateType" :value="item.id"></el-option>
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
			<el-table id="out-table" ref="print" :data="tableData" stripe style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55"></el-table-column>
				<el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
				<el-table-column align="center" prop="code_code" label="规则编码" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="code_name" label="编码名称" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="code_prefix" label="前缀" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="code_date_type" label="日期类型" min-width="100" show-overflow-tooltip>
					<template slot-scope="scope">
						<div v-for="(item,index) in dateTypeList" :key="index"> 
						    <span v-if="scope.row.code_date_type == item.id">{{item.dateType}}</span>
						</div>
					</template>
				</el-table-column>
			</el-table>
		</div>
		<div class="iot-pagination">
			<el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
		</div>
		<el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
			<el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
				<div class="iot-form-row">
					<el-form-item label="规则编码" label-width="120px" prop="code_code">
						<el-input class="iot-w240" v-model="ruleForm.code_code" placeholder="请输入规则编码" maxlength="50" clearable></el-input>
					</el-form-item>
					<el-form-item label="编码名称" label-width="150px" prop="code_name">
						<el-input class="iot-w240" v-model="ruleForm.code_name" maxlength="50" placeholder="请输入编码名称" clearable></el-input>
					</el-form-item>
				</div>
				<div class="iot-form-row">
					<el-form-item label="前缀" label-width="120px" prop="code_prefix">
						<el-input class="iot-w240" v-model="ruleForm.code_prefix" placeholder="请输入前缀" maxlength="50" clearable></el-input>
					</el-form-item>
					<el-form-item label="日期类型" label-width="150px" prop="code_date_type">
						<el-select class="iot-w240" v-model="ruleForm.code_date_type" placeholder="请选择日期类型">
							<el-option v-for="item in dateTypeList" :key="item.id" :label="item.dateType" :value="item.id"></el-option>
						</el-select>
					</el-form-item>
				</div>
				<div class="iot-form-row">
					<el-form-item label="后缀序列长度" label-width="120px" prop="code_suffix_length">
						<el-input class="iot-w240" v-model="ruleForm.code_suffix_length" type='number' placeholder="请输入后缀序列长度" maxlength="50" clearable></el-input>
					</el-form-item>
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
				dateTypeList: [{id:1,dateType:"无"},{id:2,dateType:"年月日"},{id:3,dateType:"年月日小时分钟秒"}],
				currentPage: 1,
				pageSize: 10,
				total: 0,
				dialogVisible: false,
				//表单数据
				ruleForm: {
					code_code:"",
					code_name:"",
					code_prefix:"",
					code_date_type:"",
					code_suffix_length:""
				},
				//表单验证规则
				rules: {
					code_code: [{
						required: true,
						message: "请输入规则编码",
						trigger: "blur"
					}],
					code_name: [{
						required: true,
						message: "请输入编码名称",
						trigger: "blur"
					}],
					// code_prefix: [{
					// 	required: true,
					// 	message: "请输入前缀",
					// 	trigger: "blur"
					// }],
					code_date_type: [{
						required: true,
						message: "请选择日期类型",
						trigger: "blur"
					}],
					code_suffix_length: [{
						required: true,
						message: "请输入后缀序列长度",
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
					code_code:res.code_code,
					code_name:res.code_name,
					code_prefix:res.code_prefix,
					code_date_type:res.code_date_type,
					code_suffix_length:res.code_suffix_length
				};
				this.ruleForm = obj;
			},
			//导出表格
            exportExcel() {
                this.$Common.ExportExcel("#out-table")
            },
            //打印表格
            printTable(){
                this.$print(this.$refs.print)
            }
		}
	};
</script>