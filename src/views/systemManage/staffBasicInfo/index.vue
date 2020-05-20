<template>
	<div class="iot-list" ref="page">
		<div class="iot-form">
			<div class="layout__search">
				<el-form inline label-width="100px" :model="searchForm">
					<el-form-item label="登录名">
						<el-input class="iot-w200" placeholder="请输入登录名" v-model="searchForm.Name" clearable></el-input>
					</el-form-item>
					<el-form-item label="用户名">
						<el-input class="iot-w200" placeholder="请输入名字" v-model="searchForm.Surname" clearable></el-input>
					</el-form-item>
					<el-form-item label="启用状态">
						<el-select class="iot-w200" clearable v-model="searchForm.IsActive" placeholder="全部">
							<el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item>
						<el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">查询</el-button>
					</el-form-item>
				</el-form>
			</div>

			<div class="layout__btns">
				<div ref="permissions">
<!--				<div>-->
					<el-button type="primary" plain icon="el-icon-plus" @click="click_add">新增</el-button>
					<el-button type="primary" plain icon="el-icon-edit-outline" @click="handleEdit">编辑</el-button>
					<el-button type="primary" plain icon="el-icon-delete" @click="handleDelete">删除</el-button>
					<el-button type="primary" plain icon="el-icon-circle-check" @click="handleStart">启用</el-button>
					<el-button type="primary" plain icon="el-icon-warning-outline" @click="handleForbidden">禁用</el-button>
					<el-button type="primary" plain icon="el-icon-delete" @click="handleResetPwd">重置密码</el-button>
				</div>
			</div>
		</div>
		<div class="iot-table">
			<el-table :data="tableData" stripe style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55"></el-table-column>
				<el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
				<el-table-column align="center" prop="emailAddress" label="Email" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="userName" label="登录名" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" label="用户名" min-width="100" show-overflow-tooltip>
					<template slot-scope="scope">
						<span>{{ scope.row.surname + scope.row.name }}</span>
					</template>
				</el-table-column>
				<el-table-column align="center" prop="company.name" :formatter="toCompanyName" label="所属公司" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="department.name" label="所属部门" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="roleNames" label="角色" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="isActive" label="启用状态" min-width="100" show-overflow-tooltip>
					<template slot-scope="scope">
						<span>{{scope.row.isActive == 1 ? '启用':'禁用'}}</span>
					</template>
				</el-table-column>
				<el-table-column align="center" prop="remark" label="备注" min-width="100" show-overflow-tooltip></el-table-column>
					<el-table-column align="center" prop="creationTime" label="创建时间" min-width="170" :formatter="dateTimeTransform" show-overflow-tooltip></el-table-column>
			</el-table>
		</div>
		<div class="iot-pagination">
			<el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
		</div>
		<el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
			<el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">

				<el-form-item label="登录名" label-width="100px" prop="userName">
					<el-input class="iot-w240" v-model="ruleForm.userName" placeholder="请输入登录名" maxlength="50" clearable></el-input>
				</el-form-item>
				<el-form-item label="密码" label-width="100px" prop="password" v-if="dialogTitle === '新增'">
					<el-input class="iot-w240" v-model="ruleForm.password" type="password" placeholder="请输入密码" maxlength="20" clearable></el-input>
				</el-form-item>
				<el-form-item label="所属部门" label-width="100px" prop="department">
					<el-cascader class="iot-w240" v-model="ruleForm.department" :options="departmentOptions" :props="props" @change="handleChange"></el-cascader>
				</el-form-item>
				<el-form-item label="姓氏" label-width="100px" prop="surname">
					<el-input class="iot-w240" v-model="ruleForm.surname" placeholder="请输入姓氏" maxlength="20" clearable></el-input>
				</el-form-item>
				<el-form-item label="名字" label-width="100px" prop="name">
					<el-input class="iot-w240" v-model="ruleForm.name" placeholder="请输入名字" maxlength="20" clearable></el-input>
				</el-form-item>


				<el-form-item label="启用状态" label-width="100px" prop="isActive">
					<el-select class="iot-w240" v-model="ruleForm.isActive" placeholder="请选择是否启用">
						<el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="角色(多选)" label-width="100px" prop="roleNames">
					<el-select class="iot-w240" v-model="ruleForm.roleNames" multiple placeholder="请选择角色"  filterable >
						<el-option v-for="item in roleNamesArray" :key="item.value" :label="item.label"	:value="item.value"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="Email" label-width="100px" prop="emailAddress">
					<el-input class="iot-w240" v-model="ruleForm.emailAddress" placeholder="请输入Email地址" maxlength="50" clearable @blur="checkEmail"></el-input>
				</el-form-item>
				<el-form-item label="备注" label-width="100px" prop="remark">
					<el-input class="iot-w240" v-model="ruleForm.remark" type="textarea" :rows="5" :maxlength="300"	show-word-limit></el-input>
				</el-form-item>
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
	import {
		getListApi,
		addApi,
		getOneApi,
		editApi,
		deleteApi,
		getCompanyAndDepartmentInfoTree,
		getAllCompanyList,
		ResetPwd,
	} from "./api";
	export default {
		//全局混入提示文字
		mixins: [textConfig],
		props: ["lang"],

        watch: {
            lang: function(lang) {
				console.log('*****'+lang)
			this.$utils.traversePageDom.call(this.$utils,this.$store.state.currentLang,lang,this.$refs.page);
			this.$utils.traverseFormValidator(this.rules,this.lang)
            }
        },
		data() {

			return {
				multipleSelection: [],
				radio1: "",
				searchForm: {},
				dialogTitle: "新增",
				tableData: [],
				allCompanyList:[],
				currentPage: 1,
				pageSize: 10,
				total: 0,
				dialogVisible: false,
				statusOptions: [{
						value: true,
						label: '启用'
					},
					{
						value: false,
						label: '禁用'
					}
				],
				roleNamesArray: [],
				departmentOptions: [],
				props: {
					value: 'id',
					label: 'label',
					children: 'children'
				},
				//表单数据
				ruleForm: {
					name: "",
					userName: "",
					isEnable: "",
					password: "",
					remark: "",
					emailAddress: "",
					roleNames: [],
					surname: "",
					department: [],
					authenticationSource: "PC",
					companyId: "",
					departmentId: "",
					isActive:true
				},
				//表单验证规则
				rules: {
					/*emailAddress: [{
						required: true,
						validator: checkEmail,
						trigger: "blur"
					}],*/
					userName: [{
						required: true,
						message: this.$t("staffInfo.plInpuntLoginName"),
						trigger: "blur"
					}],
					name: [{
						required: true,
						message: this.$t("staffInfo.plInpuntuserName"),
						trigger: "blur"
					}],
					surname: [{
						required: true,
						message: this.$t("staffInfo.plInpuntsurnames"),
						trigger: "blur"
					}],
					password: [{
						required: true,
						message: this.$t("staffInfo.plInpuntpassword"),
						trigger: "blur"
					}],
					roleNames: [{
						required: true,
						message: this.$t("staffInfo.plSelectrole"),
						trigger: "change"
					}],
					isActive: [{
						required: true,
						message: "请选择是否启用",
						trigger: "change"
					}],
					department: [{
						required: true,
						message: this.$t("staffInfo.SubordinateDepartments"),
						trigger: "change"
					}],
				},
				forbidden:false,
			};
		},
		mounted() {
			this.GetAll();
			this.btnInit();

			this.getRoleNames();
			this.getDepartmentNames();
			this.getAllCompanys();
		},
		methods: {
			//Email邮箱验证
			checkEmail(e)
			{
					let reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
						if (!(reg.test(e.target.value)) && e.target.value) {
							this.$message({
								message: '请输入合法Email地址',
								type: 'warning'
							});
						}

			},
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
				console.log(params)
				let data = await getListApi(params);
				if (data) {
					console.log('人员列表：', data);
					this.tableData = data.items || [];
					this.total = data.totalCount || 0;
				}
				this.$utils.traversePageDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.page);
                this.$utils.traverseFormValidator(this.rules,this.lang)
			},
			//获取所有公司列表
			async getAllCompanys(){
				let res = await getAllCompanyList();
				if(res){
					this.allCompanyList = res.items || []
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
					//this.$utils.arrayAndStringTransform(params, 'roleNames', ',');
					let res = await addApi(params);
					if (res) {
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
				if (this.multipleSelection.length == 1) {
					this.click_edit(this.multipleSelection[0]);
				} else {
					//this.text_selectOne  全局定义的提示  在textConfig.js中
					this.$message.error(this.text_selectOne);
				}
			},
			//点击删除按钮
			handleDelete() {
				if (this.multipleSelection.length == 0) {
					//this.text_deleteRow  全局定义的提示  在textConfig.js中
					this.$message.error(this.text_deleteRow);
				} else {
					let rows = this.multipleSelection;
					this.Delete(rows);
				}
			},
			//点击启用按钮
			handleStart() {
				if (this.multipleSelection.length == 1) {
					this.forbidden = false;
					this.click_enableOrForbidden(this.multipleSelection[0]);
				} else {
					//this.text_selectOne  全局定义的提示  在textConfig.js中
					this.$message.error(this.text_selectOne);
				}
			},
			//点击禁用按钮
			handleForbidden() {
				if (this.multipleSelection.length == 1) {
					this.forbidden = true;
					this.click_enableOrForbidden(this.multipleSelection[0]);
				} else {
					//this.text_selectOne  全局定义的提示  在textConfig.js中
					this.$message.error(this.text_selectOne);
				}
			},
			//重置密码
			handleResetPwd(){
				if (this.multipleSelection.length == 1) {
					this.forbidden = true;
					this.click_ResetPwd(this.multipleSelection[0]);
				} else {
					//this.text_selectOne  全局定义的提示  在textConfig.js中
					this.$message.error(this.text_selectOne);
				}
			},
			async click_ResetPwd(row) {
				await this.resetForm();
				this.dialogVisible = false;
				await this.Get(row);
				await this.Updatepwd()
			},
			async click_enableOrForbidden(row) {
				await this.resetForm();
				this.dialogVisible = false;
				await this.Get(row);
				await this.UpdateStatus()
			},
			Updatepwd(){
				this.$confirm('确定重置密码吗?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(async action => {
					let params = {
						AdminPassword:'new_wms',
						UserId:this.ruleForm.id,
						NewPassword:'123456',
					};

					let res = await ResetPwd(params);
					if (res) {
						this.forbidden = false;
						this.$message({
							type: "success",
							message: "修改成功"
						});
						this.GetAll();
					}
				});
			},

			UpdateStatus() {

				const h = this.$createElement;
				this.$msgbox({
					title: "提示",
					message: h("p", null, [
						h("span", null, "确定"),
						h(
								"span",
								{
									style: this.c_primary
								},
								this.forbidden ? "禁用" : "启用"
						),
						h("span", null, "吗？")
					]),
					showCancelButton: true,
					type: "warning"
				}).then(async action => {
					let params = {
						...this.ruleForm
					};
					console.log(this.ruleForm)
					if(this.forbidden){
						params.isActive = false;
						params.department=null;
					}else{
						params.isActive = true;
						params.department=null;
					}
					console.log('-----开始------')
					console.log(params)
					console.log('-----结束------')
					let res = await editApi(params);
					if (res) {
						this.forbidden = false;
						this.$message({
							type: "success",
							message: "修改成功"
						});
						this.GetAll();
					}
				});
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
						h("span", {
							style: this.c_danger
						}, "删除"),
						h("span", null, "吗？")
					]),
					showCancelButton: true,
					type: "warning"
				}).then(async action => {
					this.$utils.batcheHandle(rows, {
						'id': 'id'
					}, deleteApi, '删除', this.GetAll)
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
					this.ruleForm.department = null;
					let params = {
						...this.ruleForm
					};
					// params.roleNames =JSON.stringify(params.roleNames);
					//this.$utils.arrayAndStringTransform(params, 'roleNames', ',');
					let res = await editApi(params);
					if (res) {
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
				console.log('res= ', res);
				let department = [];
				department[0] = res.companyId;
				department[1] = res.departmentId;
				let obj = {
					id: row.id,
					name: res.name,
					userName: res.userName,
					surname: res.surname,
					emailAddress: res.emailAddress,
					isActive: res.isActive,
					password: res.password,
					roleNames: res.roleNames,
					remark: res.remark,
					department: department,
					departmentId: res.departmentId,
					companyId: res.companyId,

				};
				this.ruleForm = obj;
				console.log('this.ruleForm = ', this.ruleForm);
			},
			//批量启用
			Start(rows) {
				const h = this.$createElement;
				this.$msgbox({
					title: "提示",
					message: h("p", null, [
						h("span", null, "确定"),
						h("span", {
							style: this.c_danger
						}, "启用"),
						h("span", null, "吗？")
					]),
					showCancelButton: true,
					type: "warning"
				}).then(async action => {
					this.$utils.batcheHandle(rows, {
						'id': 'id'
					}, editApi, '启用', this.GetAll)
				});
			},
			//日期时间转换
			dateTimeTransform(row) {
				let time = row.creationTime;
				//return this.$moment(time).format('YYYY-MM-DD hh:mm:ss')
				return this.$utils.format(time,'yyyy-MM-dd hh:mm:ss')
			},
			//获取所有权限
			async getRoleNames() {
				let allRoles = await this.$DropBox.getAllRoles();
				this.roleNamesArray = [];
				if (allRoles.items.length !== 0) {
					allRoles.items.forEach(item => {
						this.roleNamesArray.push({
							value: item.name
						})
					});
				}
			},
			//获取所有部门
			async getDepartmentNames() {
				// let allDepartments = await this.$DropBox.getAllDepartments();
				// this.departmentOptions = [];
				// if (allDepartments.items.length !== 0) {
				// 	allDepartments.items.forEach(item => {
				// 		this.departmentOptions.push({
				// 			value: item.name,
				// 			label: item.name
				// 		})
				// 	});
				// }
				let params = {};
				let res = await getCompanyAndDepartmentInfoTree(params);
				if (res) {
					this.departmentOptions = res;
				}
			},
			handleChange(val) {
				if (typeof val !== 'undefined')
					if (val.length === 2) {
						this.ruleForm.companyId = val[0];
						this.ruleForm.departmentId = val[1];
					}
			},
			//公司id转名字
			toCompanyName(row){
				console.log(row)
				console.log(this.allCompanyList)
				let name = '';
				this.allCompanyList.forEach(ele=>{
					if(ele.id = row.companyId){
						name = ele.name
					}
				});
				return name;
			}
		}
	};
</script>	