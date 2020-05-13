<template>
	<div class="iot-list" ref="page">
		<div class="iot-form">
			<div class="layout__search">
				<el-form inline label-width="100px" :model="searchForm" >
					<div class="iot-form-row">
						<el-form-item label="类型">
            				<el-select class="iot-w200" v-model="searchForm.main_mode" placeholder="请选择" clearable>
              					<el-option v-for="item in mainmodeList" :key="item.id" :label="item.modeName" :value="item.id"></el-option>
            				</el-select>
          				</el-form-item>
					   	<el-form-item label="仓库">
            				<el-select class="iot-w200" v-model="searchForm.warehouse_id" placeholder="请选择" clearable>
              					<el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
            				</el-select>
          				</el-form-item>
		  		   		<el-form-item label="巷道">
            				<el-select class="iot-w200" v-model="searchForm.tunnel_id" placeholder="请选择" clearable>
              					<el-option v-for="item in tunnelInfoList" :key="item.id" :label="item.tunnel_name" :value="item.id"></el-option>
            				</el-select>
          				</el-form-item>
		  				<el-form-item>
							<el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">查询</el-button>
						</el-form-item>
					</div>
					<div class="iot-form-row">
						<el-form-item label="下发时间">
            				<el-date-picker class="iot-w412" clearable v-model="searchForm.startDate" type="datetimerange" range-separator="-"></el-date-picker>
          				</el-form-item>
					</div >
				</el-form>
			</div>
		</div>
		<div class="iot-table">
			<el-table :data="tableData" stripe style="width: 100%" border @selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55"></el-table-column>
				<el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
				<el-table-column align="center" prop="main_no" label="任务号" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="main_priority" label="优先级" min-width="100" show-overflow-tooltip></el-table-column>	
				<el-table-column align="center" prop="main_mode" label="类型" min-width="100" show-overflow-tooltip>
					<template slot-scope="scope">
						<span v-if="scope.row.main_mode == 1">出库</span>
						<span v-else-if="scope.row.main_mode == 2">入库</span>
						<span v-else-if="scope.row.main_mode == 3">移库</span>
						<span v-else-if="scope.row.main_mode == 4">口对口</span>
					</template>
				</el-table-column>
				<el-table-column align="center" prop="tunnel_name" label="巷道" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="main_port" label="入出口" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="main_slot_code" label="库位" min-width="100" show-overflow-tooltip></el-table-column>	
				<el-table-column align="center" prop="main_stock_code" label="托盘" min-width="100" show-overflow-tooltip></el-table-column>	
				<el-table-column align="center" prop="material_name" label="物料名称" min-width="100" show-overflow-tooltip></el-table-column>	
				<el-table-column align="center" prop="exporder_batch_no" label="批次" min-width="100" show-overflow-tooltip></el-table-column>	
				<el-table-column align="center" prop="exporder_quantity" label="数量" min-width="100" show-overflow-tooltip></el-table-column>	
				<el-table-column align="center" prop="reback_quantity" label="回流数量" min-width="100" show-overflow-tooltip></el-table-column>	
				<el-table-column align="center" prop="main_execute_flag" label="执行状态" min-width="100" show-overflow-tooltip>
					<template slot-scope="scope">			
						<span v-if="scope.row.main_execute_flag == 1">待执行</span>
						<span v-else-if="scope.row.main_execute_flag == 2">输送机</span>
						<span v-else-if="scope.row.main_execute_flag == 3">堆垛机</span>
						<span v-else-if="scope.row.main_execute_flag == 4">RGV</span>
						<span v-else-if="scope.row.main_execute_flag == 5">AGV</span>
						<span v-else-if="scope.row.main_execute_flag == 7">暂停中</span>
						<span v-else-if="scope.row.main_execute_flag == 9">已完成</span>
					</template>
				</el-table-column>
				<el-table-column align="center" prop="main_creat_datetime" :formatter="timetostring" label="下发时间" min-width="100" show-overflow-tooltip></el-table-column>	
			</el-table>
		</div>
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
				tunnelInfoList:[],
		mainmodeList:[{id:1,modeName:"入库"},{id:2,modeName:"出库"},{id:3,modeName:"移库"},{id:4,modeName:"口对口"}],
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
					unit_name:"",
					unit_is_enable:""
				},
				//表单验证规则
				rules: {
					unit_name: [{
						required: true,
						message: "请输入单位名称",
						trigger: "blur"
					}],
					unit_is_enable: [{
						required: true,
						message: "请选择启用状态",
						trigger: "blur"
					}]
				}
			};
		},
		mounted() {
			this.GetAll();
			this.GetWarehouseList();
			this.GetTunnelList();			
		},
		methods: {
			timetostring(val){
				return this.$moment(val).format("YYYY-MM-DD HH:mm:ss")
			},
//区域选择
			handarea(){				
  this.dialogTitle = "区域选择";
	  this.resetForm();
	  console.log(this.multipleSelection[0].slot_area_id)
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
				this.dialogVisible = true;
				await this.$nextTick();
				this.$refs["areaForm"].resetFields();
			},
  //仓库列表
			async GetWarehouseList() {			
				let data = await  this.$DropBox.getWarehouselist();				
				if(data){				
					this.warehouseList = data || [];				
				}							
			},
			 //巷道下拉
			async GetTunnelList() {			
				let data = await  this.$DropBox.getTunnellist();				
				if(data){
					console.log(data)
					this.tunnelInfoList = data.items || [];				
				}							
			},

			//列表
			async GetAll() {
				let ss=this.searchForm.startDate;
				let params={};              
                 if(ss){         
			
       params = {
					MaxResultCount: this.pageSize,
					SkipCount: (this.currentPage - 1) * this.pageSize,
					warhouse_id:this.searchForm.warhouse_id,
					main_mode:this.searchForm.main_mode,
					tunnel_id:this.searchForm.tunnel_id,
					main_creat_datetime1:this.searchForm.startDate[0],
					main_creat_datetime2:this.searchForm.startDate[1]				
				};
			}else{
				 params = {
					MaxResultCount: this.pageSize,
					SkipCount: (this.currentPage - 1) * this.pageSize,
					warhouse_id:this.searchForm.warhouse_id,
					main_mode:this.searchForm.main_mode,
					tunnel_id:this.searchForm.tunnel_id							
				};
			}
				
	console.log(params)
				let data = await getListApi(params);
				if(data){		
					this.tableData = data.items || [];
					this.total = data.totalCount || 0;
					this.multipleSelection=[];
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
			//点击入库锁定
			implock() {
				if(this.multipleSelection.length == 1){
					this.Update(this.multipleSelection[0],0);
				}else{
					//this.text_selectOne  全局定义的提示  在textConfig.js中
					this.$message.error(this.text_selectOne);
				}
			},
			//点击出库锁定
			exitlock() {
				if(this.multipleSelection.length == 1){
					this.Update(this.multipleSelection[0],1);
				}else{
					//this.text_selectOne  全局定义的提示  在textConfig.js中
					this.$message.error(this.text_selectOne);
				}
			},
			
			//点击出库锁定
			closed() {
				if(this.multipleSelection.length == 1){
					this.Update(this.multipleSelection[0],2);
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
					let row = this.multipleSelection[0];
					this.Delete(row);
				}
			},
			// click_edit(row,val) {
			
			// 	this.Update(row);
			// },
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
				this.$refs["areaForm"].validate(valid => {
					if (!valid) {
						return;
					}
				let parmas=this.multipleSelection[0];
			
						this.Update(parmas,3);
					
				});
			},
			//根据主键删除
			Delete(row) {
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
					let params = {
						id: row.id
					};
					//使用await方法前要加async
					let res = await deleteApi(params);
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
			//编辑
			Update(row,val) {
				const h = this.$createElement;
				this.$msgbox({
					title: "提示",
					message: h("p", null, [
						h("span", null, "确定【"),
						h("span", {
							style: this.c_primary
						}, "修改"),
						h("span", null, "】吗？")
					]),
					showCancelButton: true,
					type: "warning"
				}).then(async action => {
					let params=row;
					if(val==0){
						if(params.slot_imp_status==0){
params.slot_imp_status=1
						}else{
							params.slot_imp_status=0
						}

					}else if(val==1){
if(params.slot_exp_status==0){
params.slot_exp_status=1
						}else{
							params.slot_exp_status=0
						}
					}else if(val==2){
if(params.slot_closed_status==0){
params.slot_closed_status=1
						}else{
							params.slot_closed_status=0
						}
					}else if(val==3){
	params.slot_area_id=this.ruleForm.slot_area_id
					}					
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
			let params={	id:row.id,
				}
				let res=await getOneApi(params);
				let obj = {
					id:row.id,
					slot_area_id:res.slot_area_id
				};
				this.ruleForm = obj;
			}
		}
	};
</script>