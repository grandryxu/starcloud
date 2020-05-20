<template>
	<div class="iot-list" ref="page">
		<div class="iot-form">
			<div class="layout__search">
				<el-form inline label-width="100px" :model="searchForm">
					<el-form-item label="客户编号">
						<el-input class="iot-w200" placeholder v-model="searchForm.custom_code" clearable></el-input>
					</el-form-item>
					<el-form-item label="客户名称">
						<el-input class="iot-w200" placeholder v-model="searchForm.custom_name" clearable></el-input>
					</el-form-item>
					<el-form-item label="客户类型">
						<el-select class="iot-w200" v-model="searchForm.custom_type_id" placeholder="请选择" clearable>
							<el-option v-for="item in customTypeList" :key="item.id" :label="item.customtype_name" :value="item.id"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item>
						<el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">查询</el-button>
					</el-form-item>
				</el-form>
			</div>

			<div class="layout__btns">
				<div ref="permissions">
					<el-button type="primary" plain icon="el-icon-plus" @click="click_add" v-if="canAdd">新增</el-button>
					<el-button type="primary" plain icon="el-icon-edit-outline" @click="handleEdit" v-if="canEdit">编辑</el-button>
					<el-button type="primary" plain icon="el-icon-delete" @click="handleDelete" v-if="canDelete">删除</el-button>
					<el-button type="primary" plain icon="el-icon-document" @click="exportExcel" v-if="canExport">导出</el-button>
					<el-button type="primary" plain icon="el-icon-printer" @click="printTable" v-if="canPrint">打印</el-button>
				</div>
			</div>
		</div>
		<div class="iot-table">
			<el-table id="out-table" :data="tableData" stripe style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange" @row-click="clickRow" ref="pTable">
				<el-table-column v-if="visibleSelection" type="selection" width="55"></el-table-column>
				<el-table-column align="center" type="index" label="序号" width="50" :index="indexMethod"></el-table-column>
				<el-table-column align="center" prop="custom_code" label="客户编号" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="custom_name" label="客户名称" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="customType.customtype_name" label="客户类型" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="custom_linkman" label="联系人" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="custom_phone" label="联系方式" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="custom_address_include" label="地址" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="custom_remark" label="备注" min-width="100" show-overflow-tooltip></el-table-column>
			</el-table>
		</div>
		<div class="iot-pagination">
			<el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
		</div>
		<el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
			<el-form ref="dialogForm" :model="ruleForm" :rules="rules">
				<div class="iot-form-row">
					<el-form-item label="客户编号" label-width="100px" prop="custom_code">
						<el-input class="iot-w240" v-model="ruleForm.custom_code" placeholder="请输入客户编号" clearable maxlength="30"></el-input>
					</el-form-item>
					<el-form-item label="客户名称" label-width="100px" prop="custom_name">
						<el-input class="iot-w240" v-model="ruleForm.custom_name" placeholder="请输入客户名称" clearable maxlength="50"></el-input>
					</el-form-item>
					<el-form-item label="客户类型" label-width="100px" prop="custom_type_id">
						<el-select class="iot-w240" v-model="ruleForm.custom_type_id" placeholder="请选择">
							<el-option v-for="item in customTypeList" :key="item.id" :label="item.customtype_name" :value="item.id"></el-option>
						</el-select>
					</el-form-item>
					<el-row :gutter="2">
						<el-col :span="11">
							<el-form-item label="地址" label-width="100px" prop="address">
								<el-cascader class="iot-min-w240" v-model="ruleForm.address" :options="divisions" :props="props" clearable placeholder="请选择省市区街道" @change="handleChange" ref="addressCascader"></el-cascader>
							</el-form-item>
						</el-col>
						<el-col :span="1">
							<div class="line">—</div>
						</el-col>
						<el-col :span="12">
							<el-form-item prop="custom_address">
								<el-input class="iot-w240" v-model="ruleForm.custom_address" placeholder="具体地址" clearable maxlength="50"></el-input>
							</el-form-item>
						</el-col>
					</el-row>
					<el-form-item label="联系人" label-width="100px" prop="custom_linkman">
						<el-input class="iot-w240" v-model="ruleForm.custom_linkman" placeholder="请输入联系人" clearable maxlength="20"></el-input>
					</el-form-item>
					<el-form-item label="联系方式" label-width="100px" prop="custom_phone">
						<el-input class="iot-w240" v-model="ruleForm.custom_phone" placeholder="请输入(区号和号码之间加-)" clearable maxlength="20"></el-input>
					</el-form-item>
					<el-form-item label="备注" label-width="100px" prop="custom_remark">
						<el-input type="textarea" v-model="ruleForm.custom_remark" :rows="5" maxlength="300" show-word-limit placeholder="请输入备注" clearable></el-input>
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
	import textConfig from '@/mixins/textConfig.js';
	import Utils from "@/utils/index";
	import Companycontrol from "../../../components/companycontrol";
	import {
		getListApi,
		addApi,
		getOneApi,
		editApi,
		deleteApi,
		getTypeInfoListApi
	} from "./api";
	import Divisions from '@/utils/chinaDivisions.js';
	import { Loading } from 'element-ui';
	let myLodading;
	export default {
		//全局混入提示文字
		mixins: [textConfig],
		props: ["lang"],
		components: {
			Companycontrol,
		},
        watch: {
            lang: function(lang) {
			this.$utils.traversePageDom.call(this.$utils,this.$store.state.currentLang,lang,this.$refs.page);
			this.$utils.traverseFormValidator(this.rules,this.lang)
            }
        },
		data() {
			var checkPhone = (rule, value, callback) => {
				if (value === '') {
					callback(new Error('请输入联系方式'));
				}
				setTimeout(() => {
					if(!(/^1[3456789]\d{9}$/.test(value))){ 
						callback(new Error('请输入合法联系方式'));
					} else {
						callback();
					}
				}, 500);
			};
			return {
				visibleSelection:true,
				canAdd: true,
				canEdit: true,
				canDelete: true,
				canExport: true,
				canPrint: true,
				multipleSelection: [],
				radio1: "",
				searchForm: {
				},
				dialogTitle: "新增",
				tableData: [],
				customTypeList: [],
				currentPage: 1,
				pageSize: 10,
				total: 0,
				dialogVisible: false,
				value: [],
				options: [],
				divisions:Divisions,
				props: { 
					value: 'name',
					label:'name',
					children:'children'
				},
				//表单数据
				ruleForm: {
					custom_name:"",
					custom_code:"",
					custom_type_id:"",
					custom_linkman:"",
					custom_phone:"",
					custom_province:"",
					custom_city:"",
					custom_area:"",
					custom_town:"",
					custom_address:"",
					custom_remark:"",
					address:[],
					custom_company_id:'',
				},
				//表单验证规则
				rules: {
					custom_name: [{
						required: true,
						message: "请输入客户名称",
						trigger: "blur"
					}],
					custom_code: [{
						required: true,
						message: "请输入客户编号",
						trigger: "blur"
					}],
					custom_type_id: [{
						required: true,
						message: "请选择客户类型",
						trigger: "change"
					}],
					/*custom_linkman:[{
						required: true,
						message:"请填写联系人",
						trigger: "blur"
					}],*/
					/*custom_phone:[{
						validator: checkPhone,
						trigger: "blur"
					}],*/
					/*custom_address:[{
						required: true,
						message:"请填写具体地址",
						trigger: "blur"
					}],
					address:[{
						required: true,
						message: "请选择省市区",
						trigger: "change"
					}]*/
				},
				companyId:'',
				userId:'',
			};
		},
		mounted() {
			this.GetCustomTypeList();
			this.btnInit();
			this.GetAll();
		},
		created() {
			if (Utils.getStorage("userInfo")) {
				this.ruleForm.custom_company_id = Utils.getStorage("userInfo").data.result.companyId;
				this.userId = Utils.getStorage("userInfo").data.result.userId;
				this.companyId = Utils.getStorage("userInfo").data.result.companyId;
			}
		},
		methods: {
			updateCompanyId(val) {
				if (val)
					this.ruleForm.custom_company_id = val;
				else
					this.ruleForm.custom_company_id = Utils.getStorage("userInfo").data.result.companyId;
			},
			//根据当前用户权限标识初始化按钮状态，供参考
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
				//当表单信息量比较大时需要添加宿主任务以强制loading提前加载
				await this.$nextTick(()=>{
						if(typeof myLodading !== 'undefined')
							myLodading.close();
						if(typeof this.$refs["dialogForm"] !== 'undefined'){
							let defaultFormData = {
								custom_name:"",
								custom_code:"",
								custom_type_id:"",
								custom_linkman:"",
								custom_phone:"",
								custom_province:"",
								custom_city:"",
								custom_area:"",
								custom_town:"",
								custom_address:"",
								custom_remark:"",
								address:[]
							};
							//this.handleChange(null);
							this.ruleForm = Object.assign({}, {}, defaultFormData )
							this.$refs["dialogForm"].resetFields();
						}
				});
				this.$utils.traverseDialogDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.dialogForm);
					
			},
			async GetCustomTypeList(){
				//TODO:需要根据当前账户添加必要参数
				let params = {
					MaxResultCount: 100
				};
				let data = await getTypeInfoListApi(params);
				if(data){
					this.customTypeList = data.items || [];
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
					this.tableData.forEach((v, i, a) => {
						var addr = [];
						v.custom_address_include = addr.concat(v.custom_province,v.custom_city,v.custom_area,v.custom_town,v.custom_address);
					})
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
				myLodading = Loading.service({ lock: true,
				text: 'Loading',
				spinner: 'el-icon-loading',
				background: 'rgba(0, 0, 0, 0.7)' });
				this.handleChange();
				this.dialogVisible = true;
			},
			//点击保存
			click_submit() {
				if (this.dialogTitle == "查看") {
					this.dialogVisible = false;
					return;
				}
				this.$refs["dialogForm"].validate(valid => {
					console.log(this.ruleForm.custom_phone)
					if (!valid) {
						return;
					}else if(!this.isTelephoneNo(this.ruleForm.custom_phone) && this.ruleForm.custom_phone){
						this.$message({
							message:'请填写正确的联系方式',
							type:'warning'
						})
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
					  h("span", { style: this.c_danger }, row.length>0?`删除${row.length}条记录`:"删除"),
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
				this.$refs["dialogForm"].validate(valid => {
					console.log('验证');
					if (!valid) {
						return;
					}});
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
				}
				let adds = [];
				if(!!res.custom_province&&!!res.custom_city&&!!res.custom_area&&!!res.custom_town){
					adds.push(res.custom_province);
					adds.push(res.custom_city);
					adds.push(res.custom_area);
					adds.push(res.custom_town);
				}else{
					// this.dialogVisible = false;
					// this.$message({
					// 	type: "error",
					// 	message: "省市区数据获取异常！"
					// });
					// return;
				}
				let obj = {
					id:row.id,
					custom_name:res.custom_name,
					custom_code:res.custom_code,
					custom_type_id:res.custom_type_id,
					custom_linkman:res.custom_linkman,
					custom_phone:res.custom_phone,
					address:adds,
					custom_address:res.custom_address,
					custom_remark:res.custom_remark,
					custom_province:res.custom_province,
					custom_city:res.custom_city,
					custom_area:res.custom_area,
					custom_town:res.custom_town,
					custom_company_id:res.custom_company_id,
				};
				this.companyId=res.custom_company_id;
				this.ruleForm = obj;
				this.dialogVisible = true;
			},
			handleChange(value) {
				if(typeof value !== 'undefined')
					if(value.length === 4){
						this.ruleForm.custom_province = value[0];
						this.ruleForm.custom_city = value[1];
						this.ruleForm.custom_area = value[2];
						this.ruleForm.custom_town = value[3];
					}
			},
			//判断是否为手机号码或者是固话
			isTelephoneNo(num){
				let reg1 = /^1[^012]\d{9}/;
				let reg2 = /^0\d{2}[-]{1}\d{7,8}/;
				let reg3 = /\d{4}[-]{1}\d{7,8}/;
				if(reg1.test(num) || reg2.test(num) || reg3.test(num)){
					return true
				}else{
					return false
				}
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
<style lang="scss">
	.line{text-align:left;line-height: 32px;}
</style>