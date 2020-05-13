<template>
	<div class="iot-list" ref="page">
		<div class="iot-form">
			<div class="layout__search">
				<el-form inline label-width="100px" :model="searchForm">
					<el-form-item label="质量状态名称">
						<el-input class="iot-w200" placeholder v-model="searchForm.quality_name"></el-input>
					</el-form-item>
					<el-form-item label="所属公司" label-width="120px" prop="quality_company_id">
						<el-cascader
								v-model="searchForm.quality_company_id"
								:options="Cmyoptions"
								:props="{ checkStrictly: true }"
								clearable  style="width: 300px;"   @change="getlastvalue(1)"></el-cascader>
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
				<el-table-column v-if="visibleSelection" type="selection" width="55"></el-table-column>
				<el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
				<el-table-column align="center" prop="quality_name" label="质量状态名称" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="company.name" label="所属公司" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="quality_color" label="颜色" min-width="100" show-overflow-tooltip>
					<template slot-scope="scope">
						<div style="width:2rem;height:2rem;margin:0 auto;" :style="{'background':scope.row.quality_color}"></div>
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
					<el-form-item label="质量状态名称" label-width="120px" prop="quality_name">
						<el-input class="iot-w240" v-model="ruleForm.quality_name" maxlength="50" placeholder="请输入质量状态名称" clearable></el-input>
					</el-form-item>

					<el-form-item label="质量状态颜色" label-width="120px" prop="quality_color">
						<el-select class="iot-w120" v-model="ruleForm.quality_color" placeholder="请选择">
							<el-option v-for="item in colorList" :key="item.value" :label="item.label" :value="item.value">
								<div style="width:2rem;height:2rem;font-size: 13px" :style="{'background':item.value}"></div>
							</el-option>
						</el-select>
					</el-form-item>
				</div>
				<div class="iot-form-row">
					<el-form-item label="所属公司" label-width="120px" prop="quality_company_id">
						<el-cascader
								v-model="ruleForm.quality_company_id"
								:options="Cmyoptions"
								:props="{ checkStrictly: true }"
								clearable  style="width: 240px;"   @change="getlastvalue(2)"></el-cascader>
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
		deleteListApi,
		getCompanyTreeList,
	} from "./api";
	export default {
		//全局混入提示文字
		mixins: [textConfig],
		components: {
			singlePictureUpload
		},props: ["lang"],

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
				currentPage: 1,
				pageSize: 10,
				total: 0,
				dialogVisible: false,
				//表单数据
				ruleForm: {
					quality_name:"",
					quality_color:"",
					quality_company_id:"",
				},
				//表单验证规则
				rules: {
					quality_name: [{
						required: true,
						message: "请输入质量状态名称",
						trigger: "blur"
					}],
					quality_color: [{
						required: true,
						message: "请选择",
						trigger: "blur"
					}],
					quality_company_id: [{
						required: true,
						message: "请选择所属公司",
						trigger: "blur"
					}]
				},
				deleteList:[],
				colorList:[
					{
						value: '#cccccc',
						label: '淡灰',
					},
					{
						value: '#33cc00',
						label: '浅绿'
					},
					{
						value: '#ff0000',
						label: "正红"
					},
					{
						value: '#ffff00',
						label: '淡黄'
					},
					{
						value: '#0000ff',
						label: '正蓝'
					},
					{
						value: '#cc6699',
						label: '淡紫'
					},
					{
						value: '#000000',
						label: '黝黑'
					}
				],
				/*Cmyoptions:[{
					value: 'zhinan',
					label: '指南',

						children: [{
							value: 'yizhi',
							label: '一致'
						}, {
							value: 'fankui',
							label: '反馈'
						}, {
							value: 'xiaolv',
							label: '效率'
						}, {
							value: 'kekong',
							label: '可控'
						}]

				}],*/
				Cmyoptions:[],
				company_id:'',
			};
		},
		mounted() {
			this.btnInit();
			this.GetAll();
			this.GetCompanyTreeList();
		},
		methods: {
			//根据当前用户权限标识初始化按钮状态
			btnInit() {
				this.$Common.DistributePermission.call(this)
			},
			getlastvalue(val){
				let list='';
				if(val==1)
				{
					list=this.searchForm.quality_company_id
				}
				else
				{
					list=this.ruleForm.quality_company_id
				}

				this.company_id=list[list.length-1];
			},
			async GetCompanyTreeList() {

				let params = { Type: 'companytree'};
				let data = await getCompanyTreeList(params);
				if (data) {
					this.Cmyoptions = data;
				}

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
				params.quality_company_id=this.company_id;

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
						}, "保存"),
						h("span", null, "】吗？")
					]),
					showCancelButton: true,
					type: "warning"
				}).then(async action => {
					let params = {
						...this.ruleForm
					};
					params.quality_company_id=this.company_id;

					let res = await addApi(params);

					if(res){
						this.$message({
							type: "success",
							message: "新增成功"
						});
						this.company_id='';
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
						this.deleteList.push(rows[i].id);
						deleteCount++;
					}
					if(this.deleteList.length){
						let res = await deleteListApi(JSON.stringify(this.deleteList));
						if(res){
							this.$message({
								type: "success",
								message: '删除成功'
							});
						}else
						{
							this.$message({
								type: "error",
								message: '系统繁忙请稍后再试'
							});
						}
					}
					if((this.total-deleteCount)%10==0 && this.currentPage!=1)
					{
						this.currentPage-=1;
					}
					this.GetAll();
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
					//this.ruleForm.quality_company_id = null;
					let params = {
						...this.ruleForm
					};
					params.quality_company_id=this.company_id;
					let res = await editApi(params);
					if(res){
						this.$message({
							type: "success",
							message: "修改成功"
						});
						this.company_id='';
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
					quality_name:res.quality_name,
					quality_color:res.quality_color,
					quality_company_id:res.quality_company_id,
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