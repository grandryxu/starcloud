<template>
	<div class="iot-list" ref="page">
		<div class="iot-form">
			<div class="layout__search">
				<el-form inline label-width="100px" :model="searchForm">
					<el-form-item label="策略名称">
						<el-input class="iot-w200" placeholder="请输入策略名称" maxlength="50" v-model="searchForm.warehousing_name"></el-input>
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
				<el-table-column align="center" prop="warehousing_name" label="策略名称" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="warehousing_buzy" label="是否规避繁忙巷道" min-width="100" show-overflow-tooltip>
				    <template slot-scope="scope">
						<div v-for="(item,index) in flagList" :key="index"> 
						    <span v-if="scope.row.warehousing_buzy == item.id">{{item.flagName}}</span>
						</div>
					</template>
				</el-table-column>
				<el-table-column align="center" prop="warehousing_buzy_priority" label="是否规避繁忙巷道优先级" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="warehousing_select" label="按排选择" min-width="100" show-overflow-tooltip>
				    <template slot-scope="scope">
						<div v-for="(item,index) in selectList" :key="index"> 
						    <span v-if="scope.row.warehousing_select == item.id">{{item.selectName}}</span>
						</div>
					</template>
				</el-table-column>
				<el-table-column align="center" prop="warehousing_select_priority" label="按排选择优先级" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="warehousing_remark" label="备注" min-width="100" show-overflow-tooltip></el-table-column>
			</el-table>
		</div>
		<div class="iot-pagination">
			<el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
		</div>
		<el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="700px" append-to-body>
			<el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
				<div class="iot-form-row">
					<el-form-item label="策略名称" label-width="140px" prop="warehousing_name">
						<el-input class="iot-w210" v-model="ruleForm.warehousing_name" maxlength="50" placeholder="请输入策略名称" clearable></el-input>
					</el-form-item>
				</div>
				<div class="iot-form-row">
					<el-form-item label="是否规避繁忙巷道" label-width="140px" prop="warehousing_buzy">
						<el-select class="iot-w210" v-model="ruleForm.warehousing_buzy" placeholder="请选择是否规避繁忙巷道">
							<el-option v-for="item in flagList" :key="item.id" :label="item.flagName" :value="item.id"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="优先级" label-width="80px" prop="warehousing_buzy_priority">
						<el-input class="iot-w210" v-model="ruleForm.warehousing_buzy_priority" type='number' maxlength="20" placeholder="请输入优先级" clearable></el-input>
					</el-form-item>
				</div>
				<div class="iot-form-row">
					<el-form-item label="按排选择顺序" label-width="140px" prop="warehousing_select">
						<el-select class="iot-w210" v-model="ruleForm.warehousing_select" placeholder="请选择按排选择顺序">
							<el-option v-for="item in selectList" :key="item.id" :label="item.selectName" :value="item.id"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="优先级" label-width="80px" prop="warehousing_select_priority">
						<el-input class="iot-w210" v-model="ruleForm.warehousing_select_priority" type='number' maxlength="20" placeholder="请输入优先级" clearable></el-input>
					</el-form-item>
				</div>
				<div class="iot-form-row">
					<el-form-item label="备注" label-width="140px" prop="warehousing_remark">
						<el-input class="iot-w210" v-model="ruleForm.warehousing_remark" type="textarea" :rows="4" :maxlength="300" show-word-limit></el-input>
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
			var validatepriority = (rule, value, callback) => {
			if(this.ruleForm.warehousing_select_priority == this.ruleForm.warehousing_buzy_priority){
				callback(new Error("不能和规避繁忙巷道优先级相同"));
			}else{
				callback();
			}}
			return {
				multipleSelection: [],
				radio1: "",
				searchForm: {
				},
				dialogTitle: "新增",
				tableData: [],
				flagList: [{id:1,flagName:"是"},{id:0,flagName:"否"}],
				selectList: [{id:1,selectName:"从小到大"},{id:2,selectName:"从大到小"}],
				currentPage: 1,
				pageSize: 10,
				total: 0,
				dialogVisible: false,
				//表单数据
				ruleForm: {
					warehousing_name:"",
					warehousing_buzy:"",
					warehousing_buzy_priority:"",
					warehousing_select:"",
					warehousing_select_priority:"",
					warehousing_remark:""
				},
				//表单验证规则
				rules: {
					warehousing_name: [{
						required: true,
						message: "请输入策略名称",
						trigger: "blur"
					},{
							min: 1,
							max: 50,
							message: "请输入1-50位字符",
							trigger: "blur"
					}],
					warehousing_buzy: [{
						required: true,
						message: "请选择是否规避繁忙巷道",
						trigger: "blur"
					}],
					warehousing_buzy_priority: [{
						required: true,
						message: "请输入是否规避繁忙巷道优先级",
						trigger: "blur"
					}],
					warehousing_select: [{
						required: true,
						message: "请选择按排选择顺序",
						trigger: "blur"
					}],
					warehousing_select_priority: [{
						required: true,
						message: "请输入按排选择顺序优先级",
						trigger: "blur"
					},{
					validator:validatepriority, trigger: 'blur'}]
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
					warehousing_name:res.warehousing_name,
					warehousing_buzy:res.warehousing_buzy,
					warehousing_buzy_priority:res.warehousing_buzy_priority,
					warehousing_select:res.warehousing_select,
					warehousing_select_priority:res.warehousing_select_priority,
					warehousing_remark:res.warehousing_remark
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