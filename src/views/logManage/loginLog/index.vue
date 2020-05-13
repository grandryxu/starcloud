<template>
	<div class="iot-list" ref="page">
		<div class="iot-form">
			<div class="layout__search">
				<el-form inline label-width="100px" :model="searchForm">
					<el-form-item label="人员">
						<el-input class="iot-w200" placeholder="请输入人员" maxlength="30" v-model="searchForm.UserNameOrEmailAddress"></el-input>
					</el-form-item>
					<el-form-item label="操作时间">
						<el-date-picker v-model="searchForm.CreationTime" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" format="yyyy-MM-dd"	value-format="yyyy-MM-dd"></el-date-picker>
					</el-form-item>
					<el-form-item>
						<el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">查询</el-button>
					</el-form-item>
				</el-form>
			</div>
			<div class="layout__btns">
				<div ref="permissions">
					<el-button type="primary" plain icon="el-icon-document" @click="exportExcel">导出</el-button>
					<el-button type="primary" plain icon="el-icon-printer" @click="printTable">打印</el-button>
				</div>
			</div>
		</div>
		<div class="iot-table">
			<el-table id="out-table" ref="print" :data="tableData" stripe style="width: 100%" border
				@selection-change="handleSelectionChange">
				<el-table-column type="selection" width="55">
				</el-table-column>
				<el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
				<el-table-column align="center" prop="userNameOrEmailAddress" label="人员" min-width="100" show-overflow-tooltip>
				</el-table-column>
				<el-table-column align="center" prop="result" label="操作结果" min-width="100" show-overflow-tooltip>
					<template slot-scope="scope">
						<el-tag :type="computeResult(scope.row.result)">{{scope.row.des}}</el-tag>
					</template>
				</el-table-column>
				<el-table-column align="center" prop="creationTime" label="操作时间" min-width="100" show-overflow-tooltip :formatter="dateFormat">
				</el-table-column>
			</el-table>
		</div>
		<div class="iot-pagination">
			<el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
		</div>
	</div>
</template>

<script>
	import textConfig from '@/mixins/textConfig.js'
	import {
		getListApi
	} from "./api";
	export default {
		//全局混入提示文字
		mixins: [textConfig],
		components: {
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
					CreationTime:''
				},
				dialogTitle: "新增",
				tableData: [],
				isEnableList: [{id:0,enableName:"正常"},{id:1,enableName:"停用"}],
				currentPage: 1,
				pageSize: 10,
				total: 0
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
			//列表
			async GetAll() {
				if(this.searchForm.CreationTime !== null){
						if(this.searchForm.CreationTime !== '')
							this.searchForm.DateRange = this.searchForm.CreationTime[0] + '/' + this.searchForm.CreationTime[1];
						else{
							this.searchForm.DateRange = '';
						}
					}else{
							this.searchForm.DateRange = '';
						}
				let params = {
					MaxResultCount: this.pageSize,
					SkipCount: (this.currentPage - 1) * this.pageSize,
					...this.searchForm
				};
				let data = await getListApi(params);
				if(data){
					this.tableData = data.items || [];
					this.tableData.forEach((v,i,a)=>{
						if(v.result === 1)
							v.des = "成功";
						if(v.result === 2 || v.result === 3)
							v.des= "账户错误";
						if(v.result > 3)
							v.des = "账户异常"
					})
					this.total = data.totalCount || 0;
				}
				this.$utils.traversePageDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.page);
                this.$utils.traverseFormValidator(this.rules,this.lang)
			},
			//当checkbox发生变化时
			handleSelectionChange(val) {
				this.multipleSelection = val;
			},
			click_search() {
				this.currentPage = 1;
				this.GetAll();
			},
			//时间格式化
			dateFormat:function(row,column){
				return this.$moment(row.creationTime).format('YYYY-MM-DD HH:mm')
			},
			//登录结果可视化
			computeResult(val){
				return val > 1?(val>3?'dander':'warning'):'success';
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