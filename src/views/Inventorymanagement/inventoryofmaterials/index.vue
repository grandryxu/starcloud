<template>
	<div class="iot-list">
		<div class="iot-form">
			<div class="layout__search">
				<el-form inline label-width="100px" :model="searchForm" >
					<div class="iot-form-row">
						<el-form-item label="物料">
            				<el-input class="iot-w200" placeholder v-model="searchForm.materiel_name"></el-input>
          				</el-form-item>
						<el-form-item label="质量状态">
							<el-select class="iot-w200" v-model="searchForm.quality_name" placeholder="请选择" clearable>
								<el-option v-for="item in qualityList" :key="item.id" :label="item.quality_name" :value="item.id"></el-option>
							</el-select>
						</el-form-item>
		  				<el-form-item>
							<el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">查询</el-button>
						</el-form-item>
					</div>
					<div class="iot-form-row">
	  					<el-form-item label="仓库">
            				<el-select class="iot-w200" v-model="searchForm.slot_warehouse_id" placeholder="请选择" clearable>
              					<el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
            				</el-select>
          				</el-form-item>
		  				<el-form-item label="所属区域">
            				<el-select class="iot-w200" v-model="searchForm.slot_area_id" placeholder="请选择" clearable>
              					<el-option v-for="item in areaList" :key="item.id" :label="item.area_name" :value="item.id"></el-option>
            				</el-select>
          				</el-form-item>
						<el-form-item label="入库日期">
            				<el-date-picker class="iot-w280" clearable v-model="searchForm.startDate" type="daterange" range-separator="-"></el-date-picker>
						</el-form-item>
					</div >
				</el-form>
			</div>
		</div>
		<div class="iot-table">
			<el-table :data="tableData" stripe style="width: 100%" border @selection-change="handleSelectionChange">
 				<el-table-column type="expand">
      				<template slot-scope="props">
		  				<el-table :data="tableData" stripe style="width: 100%;" border>			
        					<el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
							<el-table-column align="center" prop="material_code" label="物料编码" min-width="100" show-overflow-tooltip></el-table-column>
							<el-table-column align="center" prop="mmaterial_name" label="物料名称" min-width="100" show-overflow-tooltip></el-table-column>	
							<el-table-column align="center" prop="guige" label="规格" min-width="100" show-overflow-tooltip></el-table-column>
							<el-table-column align="center" prop="number" label="库存数" min-width="100" show-overflow-tooltip></el-table-column>
							<el-table-column align="center" prop="danwei" label="单位" min-width="100" show-overflow-tooltip></el-table-column>	
							<el-table-column align="center" prop="zhuangtai" label="状态" min-width="100" show-overflow-tooltip></el-table-column>	
						</el-table>
      				</template>
    			</el-table-column>
				<el-table-column type="selection" width="55"></el-table-column>
				<el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
				<el-table-column align="center" prop="material_code" label="物料编码" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="mmaterial_name" label="物料名称" min-width="100" show-overflow-tooltip></el-table-column>	
				<el-table-column align="center" prop="guige" label="规格" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="number" label="库存数" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="danwei" label="单位" min-width="100" show-overflow-tooltip></el-table-column>	
				<el-table-column align="center" prop="zhuangtai" label="状态" min-width="100" show-overflow-tooltip></el-table-column>	
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
		data() {
			return {
				areaList: [],
				qualityList:[],	
					warehouseList: [],				
				multipleSelection: [],
				radio1: "",
				searchForm: {
				},
				dialogTitle: "新增",
				tableData: [{id:1,material_code:"0001",material_name:"物料一",guige:"30*3",number:12,danwei:"个",zhuangtai:"ok"},{id:2,material_code:"0002",material_name:"物料二",guige:"30*3*9",number:30,danwei:"包",zhuangtai:"NG"}],
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
				// rules: {
				// 	unit_name: [{
				// 		required: true,
				// 		message: "请输入单位名称",
				// 		trigger: "blur"
				// 	}],
				// 	unit_is_enable: [{
				// 		required: true,
				// 		message: "请选择启用状态",
				// 		trigger: "blur"
				// 	}]
				// }
			};
		},
		mounted() {
			this.GetAll();
				this.GetAreaList();
			this.GetWarehouseList();
			this.GetQualityList();			
		},
		methods: {
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
			 //质量状态下拉
			async GetQualityList() {			
				let data = await  this.$DropBox.getQualitylist();				
				if(data){
					console.log(data)
					this.qualityList = data.items || [];				
				}							
			},
			 //仓库区域
			async GetAreaList() {			
				let data = await  this.$DropBox.getArealist();				
				if(data){
					this.areaList = data || [];				
				}							
			},

			//列表
			async GetAll() {					
// 				 let ss=this.searchForm.startDate;
//                    let date="";
//                  if(ss){         
// 			date=this.$moment(ss[0]).format("YYYY-MM-DD")+this.$moment(ss[1]).format("YYYY-MM-DD");
// 			console.log(date)
// }
				
				
// 				let params = {
// 					MaxResultCount: this.pageSize,
// 					SkipCount: (this.currentPage - 1) * this.pageSize,
// 					...this.searchForm
// 				};
	
// 				let data = await getListApi(params);
// 				if(data){
// 					this.tableData = data.items || [];
// 					this.total = data.totalCount || 0;
// 					this.multipleSelection=[];
// 				}
				
			
 					this.total =  0;
 					this.multipleSelection=[];
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
<style scope> 
td.el-table__expanded-cell {
    padding: 0px 0px 0px 100px !important;
}
</style>