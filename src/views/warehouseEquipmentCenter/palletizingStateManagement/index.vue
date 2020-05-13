<template>
	<div class="iot-list">
		<div class="iot-form">
			<div class="layout__search">
				<el-form inline label-width="100px" :model="searchForm">
					<el-form-item label="设备编码">
						<el-input class="iot-w200" placeholder v-model="searchForm.palletizing_code" clearable></el-input>
					</el-form-item>
					<el-form-item label="设备名称">
						<el-input class="iot-w200" placeholder v-model="searchForm.palletizing_name" clearable></el-input>
					</el-form-item>
                    <el-form-item label="所在仓库">
						<el-select class="iot-w200" v-model="searchForm.palletizing_warehouse_id" placeholder="请选择" clearable>
							<el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
						</el-select>
					</el-form-item>
                    <el-form-item label="在线状态">
						<el-select class="iot-w200" v-model="searchForm.online_state" placeholder="请选择" clearable>
							<el-option v-for="item in onlineStateList" :key="item.id" :label="item.label" :value="item.id"></el-option>
						</el-select>
					</el-form-item>
                    <el-form-item label="报警状态">
						<el-select class="iot-w200" v-model="searchForm.alarm_state" placeholder="请选择" clearable>
							<el-option v-for="item in alarmStateList" :key="item.id" :label="item.label" :value="item.id"></el-option>
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
					<el-button type="primary" plain icon="el-icon-printer" @click="printTable">打印</el-button>
				</div>
			</div>
		</div>
		<div class="iot-table">
			<el-table id="out-table" ref="print" :data="tableData" stripe style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55"></el-table-column>
				<el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
				<el-table-column align="center" prop="palletizing_code" label="设备编码" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="palletizing_name" label="设备名称" min-width="100" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="warehouse.warehouse_name" label="所在仓库" min-width="100" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="online_state_label" label="在线状态" min-width="100" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="alarm_state_label" label="报警状态" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="palletizing_remark" label="备注" min-width="100" show-overflow-tooltip></el-table-column>
			</el-table>
		</div>
		<div class="iot-pagination">
			<el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
		</div>
		<el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
			<el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
				<div class="iot-form-row">
					<el-form-item label="设备编号" label-width="100px" prop="palletizing_code">
						<el-input class="iot-w240" v-model="ruleForm.palletizing_code" placeholder="请输入类别编号" clearable maxlength="20"></el-input>
					</el-form-item>
					<el-form-item label="设备名称" label-width="100px" prop="palletizing_name">
						<el-input class="iot-w240" v-model="ruleForm.palletizing_name" placeholder="请输入类别名称" clearable maxlength="20"></el-input>
					</el-form-item>
                    <el-form-item label="所在仓库" label-width="100px" prop="palletizing_warehouse_id">
						<el-select class="iot-w240" v-model="ruleForm.palletizing_warehouse_id" placeholder="请选择">
							<el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
						</el-select>
					</el-form-item>
                    <el-form-item label="在线状态" label-width="100px" prop="online_state">
						<el-select class="iot-w240" v-model="ruleForm.online_state" placeholder="请选择">
							<el-option v-for="item in onlineStateList" :key="item.id" :label="item.label" :value="item.id"></el-option>
						</el-select>
					</el-form-item>
                    <el-form-item label="报警状态" label-width="100px" prop="alarm_state">
						<el-select class="iot-w240" v-model="ruleForm.alarm_state" placeholder="请选择">
							<el-option v-for="item in alarmStateList" :key="item.id" :label="item.label" :value="item.id"></el-option>
						</el-select>
					</el-form-item>
					<br/>
					<el-form-item label="备注" label-width="100px" prop="palletizing_remark">
						<el-input class="iot-w240" type="textarea" v-model="ruleForm.palletizing_remark" placeholder="请输入备注" clearable :rows="5" maxlength="200" show-word-limit></el-input>
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
        deleteApi,
        getWarehouseInfoListApi
	} from "./api";
	export default {
		//全局混入提示文字
		mixins: [textConfig],
		components: {
			singlePictureUpload
		},
		data() {
			return {
				multipleSelection: [],
				searchForm: {
				},
				dialogTitle: "新增",
                tableData: [],
                warehouseList:[],
                alarmStateList: [{id:1,label:"正常"},{id:2,label:"异常"}],
                onlineStateList: [{id:1,label:"在线"},{id:2,label:"离线"}],
				currentPage: 1,
				pageSize: 10,
				total: 0,
				dialogVisible: false,
				//表单数据
				ruleForm: {
					palletizing_code:"",
					palletizing_name:"",
                    palletizing_remark:"",
                    palletizing_warehouse_id:"",
                    alarm_state:"",
                    online_state:""
				},
				//表单验证规则
				rules: {
					palletizing_name: [{
						required: true,
						message: "请输入设备名称",
						trigger: "blur"
					}],
					palletizing_code: [{
						required: true,
						message: "请输入设备编码",
						trigger: "blur"
                    }],
                    palletizing_warehouse_id: [{
						required: true,
						message: "请选择所在仓库",
						trigger: "blur"
					}],
					online_state: [{
						required: true,
						message: "请选择在线状态",
						trigger: "blur"
                    }],
                    alarm_state: [{
						required: true,
						message: "请选择报警状态",
						trigger: "blur"
					}]
				}
			};
		},
		mounted() {
            this.GetWarehouseAll();
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
            },
            async GetWarehouseAll() {
				let params = {
				};
				let data = await getWarehouseInfoListApi(params);
				if(data){
                    console.log('仓库数据：', data);
					this.warehouseList = data.filter(x => !!x.warehouse_name) || [];
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
					if(this.tableData.length > 0){
						this.tableData.forEach((v, i, a) => {
							let ala = this.alarmStateList.find((val) => (val.id == v.alarm_state));
							let onlinesta = this.onlineStateList.find((val) => (val.id == v.online_state));
							v.alarm_state_label = ala.label;
							v.online_state_label = onlinesta.label;
						});
					}
				}
				
			
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
				this.Get(row);
				this.resetForm();
				
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
					row.forEach((v, i, a) => {
						pra.push(v.id);
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
					this.$message({
						type: "error",
						message: "操作的记录不存在或者已被删除！"
					});
					return;
				}
				let obj = {
					id:row.id,
					palletizing_code:res.palletizing_code,
					palletizing_name:res.palletizing_name,
                    palletizing_remark:res.palletizing_remark,
                    palletizing_warehouse_id:res.palletizing_warehouse_id,
                    alarm_state:res.alarm_state,
                    online_state:res.online_state
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