<template>
	<div class="iot-list" ref="page">
		<div class="iot-form">
			<div class="layout__search">
				<el-form inline label-width="100px" :model="searchForm">
					<el-form-item label="区域编码">
            			<el-input class="iot-w200" placeholder v-model="searchForm.area_code"></el-input>
          			</el-form-item>
          			<el-form-item label="区域名称">
            			<el-input class="iot-w200" placeholder v-model="searchForm.area_name"></el-input>
          			</el-form-item>
					<el-form-item label="所在仓库">
						<el-select class="iot-w200" v-model="searchForm.area_warehouse_id" placeholder="请选择" clearable>
							<el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
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
			<el-table id="out-table" :data="tableData" ref="print" stripe style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange">
				<el-table-column v-if="visibleSelection" type="selection" width="55"></el-table-column>
				<el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
				<el-table-column align="center" prop="area_code" label="区域编码" min-width="100" show-overflow-tooltip></el-table-column>
        		<el-table-column align="center" prop="area_name" label="区域名称" min-width="100" show-overflow-tooltip></el-table-column>
         		<el-table-column align="center" prop="warehouse.warehouse_name" label="所在仓库" min-width="100" show-overflow-tooltip></el-table-column>
        		<el-table-column align="center" prop="area_remark" label="备注" min-width="100" show-overflow-tooltip></el-table-column>
			</el-table>
		</div>
		<div class="iot-pagination">
			<el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
		</div>
		<el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
			<el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
				<div class="iot-form-row">
					<el-form-item label="区域编码" label-width="100px" prop="area_code">
						<el-input class="iot-w240" v-model="ruleForm.area_code" placeholder="请输入区域编码" maxlength="30" clearable></el-input>
					</el-form-item>	
					<el-form-item label="区域名称" label-width="100px" prop="area_name">
						<el-input class="iot-w240" v-model="ruleForm.area_name" placeholder="请输入区域名称" maxlength="50" clearable></el-input>
					</el-form-item>					
				</div>
				<div class="iot-form-row">			
					<el-form-item label="所在仓库" label-width="100px" prop="area_warehouse_id">
						<el-select class="iot-w240" v-model="ruleForm.area_warehouse_id" placeholder="请选择">
							<el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="备注" label-width="100px" prop="area_remark">
            			<el-input type="textarea" :rows="4" :maxlength="200" show-word-limit class="iot-w240" v-model="ruleForm.area_remark" clearable></el-input>
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
		deleteListApi
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
				visibleSelection:true,
				warehouseList: [],
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
					area_name:"",
					area_code:"",
					area_warehouse_id:"",
					area_remark:""
				},
				//表单验证规则
				rules: {
					area_name: [{
						required: true,
						message: "请输入区域名称",
						trigger: "blur"
					}],
					area_code: [{
						required: true,
						message: "请输入区域编码",
						trigger: "blur"
					}],
						area_warehouse_id: [{
						required: true,
						message: "请选择所在仓库",
						trigger: "blur"
					}]
				},
				deleteList:[],
			};
		},
		mounted() {
			this.btnInit();
			this.GetAll();
			this.GetWarehouseList();
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

           //仓库列表
			async GetWarehouseList() {			
				let data = await  this.$DropBox.getWarehouselist();				
				if(data){
					console.log(data)
					this.warehouseList = data || [];				
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
					  h("span", null, "确定"),
					  h("span", { style: this.c_danger }, "删除"),
					  h("span", null, "吗？")
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
				console.log(res)				
				let obj = {
					id:row.id,
					area_name:res.area_name,
					area_code:res.area_code,
					area_remark:res.area_remark,
					area_warehouse_id:res.area_warehouse_id
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