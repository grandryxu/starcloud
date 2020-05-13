<template>
	<div class="iot-list" ref="page">
		<div class="iot-form">
			<div class="layout__search">
				<el-form inline label-width="100px" :model="searchForm">
                    <el-form-item label="设备编号">
						<el-input class="iot-w200" placeholder="请输入设备编号" maxlength="50" v-model="searchForm.equipment_code" clearable></el-input>
					</el-form-item>
					<el-form-item label="设备名称">
						<el-input class="iot-w200" placeholder="请输入设备名称" maxlength="50" v-model="searchForm.equipment_name" clearable></el-input>
					</el-form-item>
                    <el-form-item label="类型">
						<el-select class="iot-w200" v-model="searchForm.equipment_type" placeholder="请选择" clearable>
							<el-option v-for="item in equipmentTypeList" :key="item.id" :label="item.label" :value="item.id"></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="操作人">
						<el-input class="iot-w200" placeholder v-model="searchForm.opt_user_name" clearable></el-input>
					</el-form-item>
					<el-form-item label="时间">
						<el-date-picker v-model="searchForm.CreationTime" type="daterange" range-separator="-" start-placeholder="起始日期" end-placeholder="结束日期" format="yyyy-MM-dd" value-format="yyyy-MM-dd"></el-date-picker>
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
			<el-table id="out-table" ref="print" :data="tableData" stripe style="width: 100%" border>
				<el-table-column type="selection" width="55"></el-table-column>
				<el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
				<el-table-column align="center" prop="equipment_code" label="设备" min-width="100" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="equipment_type_name" label="类型" min-width="100" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="equipment_log_type_name" label="日志类型" min-width="100" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="equipment_log_content" label="内容" min-width="100" show-overflow-tooltip></el-table-column>
                <el-table-column align="center" prop="equipment_execution_state_name" label="执行状态" min-width="100" show-overflow-tooltip></el-table-column>
				<el-table-column align="center" prop="executionTime" label="生成时间" min-width="100" show-overflow-tooltip :formatter="dateFormat"></el-table-column>
                <el-table-column align="center" prop="opt_user_name" label="操作人" min-width="100" show-overflow-tooltip></el-table-column>
			</el-table>
		</div>
		<div class="iot-pagination">
			<el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange">
			</el-pagination>
		</div>
	</div>
</template>

<script>
	import textConfig from '@/mixins/textConfig.js'
	import {
        getListApi
	} from "./api";
	export default {
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
                modelList: [],
                optTypeList: [],
				searchForm: {
				},
				tableData: [],
				equipmentTypeList: [{id:1,label:"堆垛机"},{id:2,label:"RGV"},{id:3,label:"码垛机"},{id:4,label:"拆垛机"},{id:5,label:"AGV"},{id:6,label:"LED"}],
				logTypeList: [{id:1,label:"正常"},{id:2,label:"报警"},{id:3,label:"异常"}],
                exeStateList: [{id:1,label:"开始执行"},{id:2,label:"执行中"},{id:3,label:"执行完成"}],
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
				if(typeof this.searchForm.CreationTime !== 'undefined'){
					if(this.searchForm.CreationTime !== null)
						if(this.searchForm.CreationTime !== '')
							this.searchForm.date_range = this.searchForm.CreationTime[0] + '$' + this.searchForm.CreationTime[1];
				}
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
							let etype = this.equipmentTypeList.find((val) => (val.id == v.equipment_type));
							let logtype = this.logTypeList.find((val) => (val.id == v.equipment_log_type));
							let estate = this.exeStateList.find((val) => (val.id == v.equipment_execution_state));
							v.equipment_type_name = etype.label;
							v.equipment_log_type_name = logtype.label;
							v.equipment_execution_state_name = estate.label;
						});
					}
				}
				this.$utils.traversePageDom.call(this.$utils,this.$store.state.currentLang,this.lang,this.$refs.page);
                this.$utils.traverseFormValidator(this.rules,this.lang)
			},
			click_search() {
				this.currentPage = 1;
				this.GetAll();
			},
			//时间格式化
			dateFormat:function(row,column){
				return this.dateFormatter(row.creationTime);
            },
            dateFormatter(val){
                return this.$moment(val).format('YYYY-MM-DD HH:mm')
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