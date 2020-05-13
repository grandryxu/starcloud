<template>
	<div class="iot-list" ref="page">
		<div class="iot-form">
			<div class="layout__search">
				<el-form inline label-width="100px" :model="searchForm" >
					<div class="iot-form-row">
					   <el-form-item label="仓库">
							<el-select class="iot-w200" v-model="searchForm.slot_warehouse_id" placeholder="请选择" clearable>
								<el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
							</el-select>
						</el-form-item>
		  	
					   	<el-form-item label="区域">
            				<el-select class="iot-w200" v-model="searchForm.slot_area_id" placeholder="请选择" clearable >
              					<el-option v-for="item in areaList" :key="item.id" :label="item.area_name" :value="item.id"></el-option>
            				</el-select>
          				</el-form-item>
		  		   		<el-form-item label="库存状态">
            				<el-select class="iot-w200" v-model="searchForm.slot_stock_status" placeholder="请选择" clearable>
              					<el-option v-for="item in slot_stock_statusList" :key="item.id" :label="item.slot_stock_statusName" :value="item.id"></el-option>
            				</el-select>
          				</el-form-item>
		  		   		<el-form-item label="屏蔽状态">
            				<el-select class="iot-w200" v-model="searchForm.slot_closed_status" placeholder="请选择" clearable>
              					<el-option v-for="item in slot_closed_statusList" :key="item.id" :label="item.slot_closed_statusName"></el-option>
            				</el-select>
          				</el-form-item>

					</div>
					<div class="iot-form-row">
						<el-form-item label="容积大小">
							<el-select class="iot-w200" v-model="searchForm.slot_size_level" placeholder="请选择" clearable>
								<el-option v-for="item in slotSizeList" :key="item.id" :label="item.size_name" :value="item.id"></el-option>
							</el-select>
						</el-form-item>
						<el-form-item label="入库锁定状态">
							<el-select class="iot-w200" v-model="searchForm.slot_imp_status" placeholder="请选择" clearable>
								<el-option v-for="item in slot_imp_statusList" :key="item.id" :label="item.slot_imp_statusName" :value="item.id"></el-option>
							</el-select>
						</el-form-item>
						<el-form-item label="出库锁定状态">
							<el-select class="iot-w200" v-model="searchForm.slot_exp_status" placeholder="请选择" clearable>
								<el-option v-for="item in slot_exp_statusList" :key="item.id" :label="item.slot_exp_statusName" :value="item.id"></el-option>
							</el-select>
						</el-form-item>
					</div >
					<div class="iot-form-row">	
					 	<el-form-item label="层">
            				<el-input-number :min=1 class="iot-w90" controls-position="right" placeholder v-model="searchForm.slot_layer0"></el-input-number>
							<span class="el-icon-minus" style="color:#ccc"></span>
			         		<el-input-number :min=1 class="iot-w90" controls-position="right" placeholder v-model="searchForm.slot_layer1"></el-input-number>
						</el-form-item>
						<el-form-item label="列">
            				<el-input-number :min=1 class="iot-w90" controls-position="right" placeholder v-model="searchForm.slot_column0"></el-input-number>
							<span class="el-icon-minus" style="color:#ccc"></span>
							<el-input-number :min=1 class="iot-w90" controls-position="right" placeholder v-model="searchForm.slot_column1"></el-input-number>
						</el-form-item>
						<el-form-item label="排">
            				<el-input-number  :min=1 class="iot-w90" controls-position="right" placeholder v-model="searchForm.slot_row0"></el-input-number>
							<span class="el-icon-minus" style="color:#ccc"></span>
							<el-input-number  :min=1 class="iot-w90" controls-position="right" placeholder v-model="searchForm.slot_row1"></el-input-number>
						</el-form-item>
						<el-form-item>
							<el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">查询</el-button>
						</el-form-item>
					</div>
				</el-form>
			</div>

			<div class="layout__btns">
				<div>
					<el-button type="primary" plain  @click="implock">入库锁定</el-button>
					<el-button type="primary" plain  @click="exitlock">出库锁定</el-button>
					<el-button type="primary" plain  @click="closed">屏蔽</el-button>
					<!-- <el-button type="primary" plain icon="el-icon-plus" @click="click_add">新增</el-button>
					<el-button type="primary" plain icon="el-icon-edit-outline" @click="handleEdit">编辑</el-button> -->
					<el-button type="primary" plain icon="el-icon-delete" @click="handleDelete">删除</el-button>
					<el-button type="primary" plain icon="el-icon-edit-outline" @click="handarea" >区域选择</el-button>
					<el-button type="primary" plain icon="el-icon-edit-outline" @click="batchedit" >批量修改</el-button>
					<el-button type="primary" plain icon="el-icon-document" @click="exportExcel">导出</el-button>
					<el-button type="primary" plain icon="el-icon-printer"  @click="printTable">打印</el-button>
				</div>
			</div>
		</div>
		<div class="iot-table">
			<el-table id="out-table" ref="print" :data="tableData" stripe style="width: 100%" border @selection-change="handleSelectionChange">
				<el-table-column v-if="visibleSelection" type="selection" width="55"></el-table-column>
				<el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
				<el-table-column align="center" prop="slot_code" label="库位编码" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="area.area_name" label="区域" min-width="100" show-overflow-tooltip></el-table-column>	
				<el-table-column align="center" prop="row.row_name" label="巷道" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="slot_row" label="排" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="slot_column" label="列" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="slot_layer" label="层" min-width="100" show-overflow-tooltip></el-table-column>	
				<el-table-column align="center" prop="slot_stock_status" label="库存状态" min-width="100" show-overflow-tooltip>
					<template slot-scope="scope">
            			<span v-if="scope.row.slot_stock_status === 0">空闲</span>
            			<span v-else-if="scope.row.slot_stock_status === 1">有库存</span>
			 			<span v-else-if="scope.row.slot_stock_status === 2">入库中</span>
			  			<span v-else-if="scope.row.slot_stock_status === 3">出库中</span>
          			</template>
				</el-table-column>
				<el-table-column align="center" prop="slot_closed_status" label="屏蔽状态" min-width="100" show-overflow-tooltip>
					<template slot-scope="scope">
            			<span v-if="scope.row.slot_closed_status === 0">正常</span>
            			<span v-else-if="scope.row.slot_closed_status === 1">屏蔽</span>
			   		</template>
				</el-table-column>
				<el-table-column align="center" prop="slot_imp_status" label="入库锁定状态" min-width="100" show-overflow-tooltip>
					<template slot-scope="scope">
            			<span v-if="scope.row.slot_imp_status === 0">正常</span>
            			<span v-else-if="scope.row.slot_imp_status === 1">锁定</span>
			   		</template>
				</el-table-column>
				<el-table-column align="center" prop="slot_exp_status" label="出库锁定状态" min-width="100" show-overflow-tooltip>
					<template slot-scope="scope">
            			<span v-if="scope.row.slot_exp_status === 0">正常</span>
            			<span v-else-if="scope.row.slot_exp_status === 1">锁定</span>
			   		</template>
				</el-table-column>	
				<el-table-column align="center" prop="size.size_name" label="容积大小" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="slot_moveable_status" label="是否可移动" min-width="100" show-overflow-tooltip>
					<template slot-scope="scope">
            			<span v-if="scope.row.slot_moveable_status === 0">不可移动</span>
            			<span v-else-if="scope.row.slot_moveable_status === 1">可移动</span>
			   		</template>
				</el-table-column>
			</el-table>
		</div>

		<div class="iot-pagination">
			<el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
		</div>


		<el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="524px" append-to-body>
			<el-form inline ref="areaForm" :model="ruleForm" :rules="rules">
				<div class="iot-form-row">
					<el-form-item label="区域" label-width="100px" prop="slot_area_id">
            			<el-select class="iot-w240" v-model="ruleForm.slot_area_id" placeholder="请选择"  @change="change()">
              				<el-option v-for="item in areaList" :key="item.id" :label="item.area_name" :value="item.id"></el-option>
            			</el-select>
          			</el-form-item>
					<!-- <el-form-item label="单位名称" label-width="100px" prop="unit_name">
						<el-input class="iot-w240" v-model="ruleForm.unit_name" placeholder="请输入单位名称" clearable>
						</el-input>
					</el-form-item>
					<el-form-item label="启用状态" label-width="100px" prop="unit_is_enable">
						<el-select class="iot-w240" v-model="ruleForm.unit_is_enable" placeholder="请选择">
							<el-option v-for="item in isEnableList" :key="item.id" :label="item.enableName"
								:value="item.id"></el-option>
						</el-select>
					</el-form-item> -->
				</div>
            </el-form>
			<span slot="footer" class="dialog-footer">
				<el-button size="small" @click="dialogVisible = false">取 消</el-button>
				<el-button size="small" type="primary" @click="click_submit">保 存</el-button>
			</span>
		</el-dialog>





		<el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisibleA" width="524px" append-to-body>
			<el-form inline ref="editForm" :model="editForm" :rules="rules">

				<!--<div class="iot-form-row">
					<el-form-item label="层">
						<el-input-number :min=1 class="iot-w90" controls-position="right" placeholder v-model="searchForm.slot_layer0"></el-input-number>
						<span class="el-icon-minus" style="color:#ccc"></span>
						<el-input-number :min=1 class="iot-w90" controls-position="right" placeholder v-model="searchForm.slot_layer1"></el-input-number>
					</el-form-item>
					<el-form-item label="列">
						<el-input-number :min=1 class="iot-w90" controls-position="right" placeholder v-model="searchForm.slot_column0"></el-input-number>
						<span class="el-icon-minus" style="color:#ccc"></span>
						<el-input-number :min=1 class="iot-w90" controls-position="right" placeholder v-model="searchForm.slot_column1"></el-input-number>
					</el-form-item>
				</div>

				<div class="iot-form-row">
					<el-form-item label="排">
						<el-input-number  :min=1 class="iot-w90" controls-position="right" placeholder v-model="searchForm.slot_row0"></el-input-number>
						<span class="el-icon-minus" style="color:#ccc"></span>
						<el-input-number  :min=1 class="iot-w90" controls-position="right" placeholder v-model="searchForm.slot_row1"></el-input-number>
					</el-form-item>
				</div>

				<el-divider style="margin: 8px 0px"></el-divider>-->
				<div class="iot-form-row">
					<el-form-item label="入库状态">
						<el-switch
								v-model="editForm.rkStatue"
								active-color="#13ce66"
								inactive-color="#ff4949" >
						</el-switch>
					</el-form-item>
					<el-form-item label="出库状态">
						<el-switch
									v-model="editForm.ckStatue"
								active-color="#13ce66"
								inactive-color="#ff4949" >
						</el-switch>
					</el-form-item>
					<el-form-item label="屏蔽状态" >
						<el-switch
								v-model="editForm.pbStatue"
								active-color="#13ce66"
								inactive-color="#ff4949" >
						</el-switch>
					</el-form-item>
				</div>
				<div class="iot-form-row">

					<el-form-item label="区域"  prop="slot_area_id">
						<el-select class="iot-w240" v-model="editForm.slot_area_id" placeholder="请选择">
							<el-option v-for="item in areaList" :key="item.id" :label="item.area_name" :value="item.id"></el-option>
						</el-select>
					</el-form-item>
				</div>



			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button size="small" @click="dialogVisible = false">取 消</el-button>
				<el-button size="small" type="primary" @click="click_Batchsubmit">保 存</el-button>
			</span>
		</el-dialog>


	</div>
</template>

<script>
import textConfig from '@/mixins/textConfig.js'
import singlePictureUpload from "_c/singlePictureUpload"
import { getListApi, addApi, getOneApi, editApi, deleteApi,editListApi } from "./api";
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
			slot_exp_statusList:[{id:0,slot_exp_statusName:"正常"}, {id:1,slot_exp_statusName:"锁定"}],
			slot_imp_statusList:[{id:0,slot_imp_statusName:"正常"}, {id:1,slot_imp_statusName:"锁定"}],
			slot_closed_statusList:[{id:0,slot_closed_statusName:"正常"}, {id:1,slot_closed_statusName:"屏蔽"}],
			slot_stock_statusList:[{id:0,slot_stock_statusName:"空闲"}, {id:1,slot_stock_statusName:"有库存"},{id:2,slot_stock_statusName:"入库中"},{id:3,slot_stock_statusName:"出库中"}],
			warehouseList: [],
			areaList: [],
			slotSizeList:[],
			multipleSelection: [],
			radio1: "",
			searchForm: {},
			dialogTitle: "新增",
			tableData: [],
			isEnableList: [{id:0,enableName:"正常"}, {id:1,enableName:"停用"}],
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
			},
			dialogVisibleA:false,
			infolist:[],
			BatchData:[],
			editForm: {
				rkStatue: true,
				ckStatue: true,
				pbStatue: true,
			},
		};
	},
	mounted() {
		this.GetWarehouseList();
		this.GetAreaList();
		this.GetSlotSizeList();
		this.GetAll();
	},
	methods: {

		//批量修改
		click_Batchsubmit()
		{
			this.GetBatchAll();

		},
		//强制刷新
		change(){
			this.$forceUpdate()
		},
		//批量修改
		batchedit(){

  			if(this.total>0 && ((this.searchForm.slot_layer0!=undefined && this.searchForm.slot_layer1!=undefined) || (this.searchForm.slot_column0!=undefined && this.searchForm.slot_column1!=undefined) || (this.searchForm.slot_row0!=undefined && this.searchForm.slot_row1!=undefined) )  ){
				this.dialogTitle = "批量修改";
				this.dialogVisibleA = true;
				this.$refs.editForm.resetFields();
				this.editForm.rkStatue=true;
				this.editForm.ckStatue=true;
				this.editForm.pbStatue=true;

			}
			else
			{
				this.$message.error("请选择层/列/排查询条件筛选后，进行批量修改操作！");
			}

		},
		//区域选择
		handarea(){

			/*if(this.multipleSelection.length == 1){
				this.dialogTitle = "区域选择";
				this.resetForm();
				console.log(this.multipleSelection[0].slot_area_id)
				this.Get(this.multipleSelection[0]);
			}else{
				//this.text_selectOne  全局定义的提示  在textConfig.js中
				this.$message.error(this.text_selectOne);
			}*/



			if(this.multipleSelection.length>0){

				this.dialogTitle = "区域选择";
				this.resetForm();
				this.ruleForm.slot_area_id='';





			}else{
				//this.text_selectOne  全局定义的提示  在textConfig.js中
				this.$message.error(this.text_deleteRow);
			}


		},
		//批量修改
		async UpdateSlotList() {
			if (this.multipleSelection.length)
				this.infolist=[];
			this.multipleSelection.forEach(ele => {
				ele.slot_area_id=this.ruleForm.slot_area_id;   //东八库区
				this.infolist.push(ele);
				console.log('-----------------------开始---------------------')
				console.info(this.infolist)
				console.log('-----------------------结束---------------------')
			})

				await editListApi(this.infolist);
			    this.GetAll();
			    this.dialogVisible = false;
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
			//this.$refs["areaForm"].resetFields();
			this.$refs.areaForm.resetFields()
		},
		//仓库列表
		async GetWarehouseList() {			
			let data = await  this.$DropBox.getWarehouselist();				
			if(data){				
				this.warehouseList = data || [];				
			}							
		},
		//仓库区域
		async GetAreaList() {			
			let data = await  this.$DropBox.getArealist();				
			if(data){
				this.areaList = data || [];				
			}							
		},
		//库位容积大小区域
		async GetSlotSizeList() {			
			let data = await  this.$DropBox.getSlotSizelist();				
			if(data){
				this.slotSizeList = data || [];				
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
				this.multipleSelection=[];
			}
			this.$utils.traversePageDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.page);
        	this.$utils.traverseFormValidator(this.rules,this.lang)
		},
		async GetBatchAll() {
			let params = {
				MaxResultCount: 9999999,
				SkipCount: 0,
				...this.searchForm
			};
			let data = await getListApi(params);
			if(data){
				this.BatchData = data.items || [];
				this.BatchData.forEach(ele => {
					ele.slot_area_id=this.editForm.slot_area_id;   //区域
					ele.slot_imp_status=this.editForm.rkStatue===true ? 0 :1 //入库
					ele.slot_exp_status=this.editForm.ckStatue===true ? 0 :1   //出库
					ele.slot_closed_status=this.editForm.pbStatue ===true ? 0 :1  //屏蔽
				})

				console.log('-----------------------开始---------------------')
				console.info(this.BatchData)
				console.log('-----------------------结束---------------------')
				await editListApi(this.BatchData);
				this.GetAll();
				this.dialogVisibleA = false;
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
			if(this.multipleSelection.length){
				this.Update(this.multipleSelection,0);
			}else{
				//this.text_selectOne  全局定义的提示  在textConfig.js中
				this.$message.error(this.text_deleteRow);
			}
		},
		//点击出库锁定
		exitlock() {
			if(this.multipleSelection.length){
				this.Update(this.multipleSelection,1);
			}else{
				//this.text_selectOne  全局定义的提示  在textConfig.js中
				this.$message.error(this.text_deleteRow);
			}
		},
		//点击出库锁定
		closed() {
			if(this.multipleSelection.length){
				this.Update(this.multipleSelection,2);
			}else{
				//this.text_selectOne  全局定义的提示  在textConfig.js中
				this.$message.error(this.text_deleteRow);
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
				//this.Update(parmas,3);
				this.UpdateSlotList();
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
				this.infolist=[];
				params.forEach(ele => {
					if(val==0){

						if(ele.slot_imp_status==0){
							ele.slot_imp_status=1
						}else{
							ele.slot_imp_status=0
						}
					}else if(val==1){
						if(ele.slot_exp_status==0){
							ele.slot_exp_status=1
						}else{
							ele.slot_exp_status=0
						}
					}else if(val==2){
						if(ele.slot_closed_status==0){
							ele.slot_closed_status=1
						}else{
							ele.slot_closed_status=0
						}
					}else if(val==3){
						ele.slot_area_id=this.ruleForm.slot_area_id
					}

				})
				let res = await editListApi(params);
				console.log('------------------开始----------------')
				console.log(params)
				console.log('------------------结束----------------')
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