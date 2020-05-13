<template>
  <div class="iot-list">
    <!-- 可视化 -->
    <el-col :span="24" style="display:flex;border-bottom:20px solid #eee;padding:20px;position:relative">
      <el-form inline label-width="50px" style="position:absolute;z-index:999;top:60px;">
        <el-form-item label="仓库">
          <el-select class="iot-w120" v-model="searchForm.warehouse_code" placeholder="请选择" @change="getWarehouseUseRatio" clearable>
            <el-option v-for="item in warehouseUseRatioList" :key="item.warehouse_code" :label="item.warehouse_name" :value="item.warehouse_code"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div id="mainPie" style="flex:1;height:400px;"></div>
      <div id="mainLine" style="flex:1;height:400px;"></div>
    </el-col>
    <div class="iot-form">
      <div style="padding-top:20px;text-align:center;font-size:20px;">仓库使用率</div>
      <div class="layout__search">
        <el-form inline label-width="100px" :model="searchForm">
          <el-form-item label="仓库">
            <el-select class="iot-w200" v-model="searchForm.warehouse_code" placeholder="请选择" clearable>
              <el-option v-for="item in warehouseUseRatioList" :key="item.warehouse_code" :label="item.warehouse_name" :value="item.warehouse_code"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="类型">
            <el-select class="iot-w200" v-model="searchForm.warehouse_type" placeholder="请选择" clearable>
              <el-option v-for="item in typeList" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="日期选择">
            <el-date-picker v-model="searchForm.daterange" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button class="search__btn" type="primary" icon="el-icon-search" @click="click_search">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <div class="iot-table">
      <el-table :data="warehouseUseRatioList" stripe style="width: 100%" border @row-dblclick="click_edit" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" type="index" label="序号" width="50"></el-table-column>
        <el-table-column align="center" prop="warehouse_name" label="仓库" min-width="100" show-overflow-tooltip></el-table-column>
        <el-table-column align="center" prop="warehouse_type" label="仓库类型" min-width="100" show-overflow-tooltip :formatter="toWarehouseType"></el-table-column>
        <el-table-column align="center" prop="empty_count" label="使用率" min-width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{caclRatio(scope.row)}}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column
          align="center"
          prop=""
          label="日期"
          min-width="100"
          show-overflow-tooltip
        ></el-table-column> -->
      </el-table>
    </div>
    <div class="iot-pagination">
      <el-pagination background layout="prev, pager, next" :total="total" :page-size="pageSize" :current-page.sync="currentPage" @current-change="handleCurrentChange"></el-pagination>
    </div>
    <el-dialog class="iot-dialog" :title="dialogTitle" :visible.sync="dialogVisible" width="824px" append-to-body>
      <el-form inline ref="dialogForm" :model="ruleForm" :rules="rules">
        <div class="iot-form-row">
          <el-form-item label="仓库编码" label-width="100px" prop="warehouse_code">
            <el-input class="iot-w240" v-model="ruleForm.warehouse_code" placeholder="请输入仓库编码" clearable></el-input>
          </el-form-item>
          <el-form-item label="仓库名称" label-width="100px" prop="warehouse_name">
            <el-input class="iot-w240" v-model="ruleForm.warehouse_name" placeholder="请输入仓库名称" clearable></el-input>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="仓库类型" label-width="100px" prop="warehouse_type">
            <el-select class="iot-w240" v-model="ruleForm.warehouse_type" placeholder="请选择">
              <el-option v-for="item in typeList" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="库位类型" label-width="100px" prop="warehouse_slot_type">
            <el-select class="iot-w240" v-model="ruleForm.warehouse_slot_type" placeholder="请选择">
              <el-option v-for="item in slotList" :key="item.id" :label="item.slotName" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="启用状态" label-width="100px" prop="warehouse_is_enable">
            <el-select class="iot-w240" v-model="ruleForm.warehouse_is_enable" placeholder="请选择">
              <el-option v-for="item in isEnableList" :key="item.id" :label="item.enableName" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </div>
        <div class="iot-form-row">
          <el-form-item label="备注" label-width="100px" prop="warehouse_remark">
            <el-input type="textarea" :rows="4" :maxlength="200" class="iot-w240" v-model="ruleForm.warehouse_remark" clearable></el-input>
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
import textConfig from "@/mixins/textConfig.js";
import singlePictureUpload from "_c/singlePictureUpload";
import { getListApi, addApi, getOneApi, editApi, deleteApi,useRatioPie } from "./api";
export default {
  //全局混入提示文字
  mixins: [textConfig],
  components: {
    singlePictureUpload
  },
  data() {
    return {
      multipleSelection: [],
      radio1: "",
      searchForm: {},
      dialogTitle: "新增",
      tableData: [],
      typeList: this.$DropBox.getAllWarehouseType(),
      warehouseList:[],
      warehouseUseRatioList:[],
      used:0,
      pendingUse:0,
      isEnableList: [
        { id: 1, enableName: "启用" },
        { id: 2, enableName: "禁用" }
      ],
      slotList: [
        { id: 1, slotName: "层列排" },
        { id: 2, slotName: "排列层" }
      ],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      //表单数据
      ruleForm: {
        warehouse_code: "",
        warehouse_name: "",
        warehouse_type: "",
        warehouse_slot_type: "",
        warehouse_is_enable: "",
        warehouse_remark: ""
      },
      //表单验证规则
      rules: {
        warehouse_name: [
          {
            required: true,
            message: "请输入仓库名称",
            trigger: "blur"
          }
        ],
        warehouse_code: [
          {
            required: true,
            message: "请输入仓库编码",
            trigger: "blur"
          }
        ],
        warehouse_type: [
          {
            required: true,
            message: "请选择仓库类型",
            trigger: "blur"
          }
        ],
        warehouse_slot_type: [
          {
            required: true,
            message: "请选择库位类型",
            trigger: "blur"
          }
        ],
        warehouse_is_enable: [
          {
            required: true,
            message: "请选择启用状态",
            trigger: "blur"
          }
        ]
      }
    };
  },
  mounted() {
    this.GetAll();
    this.GetWarehouseList();
    //this.GetWarehouseUseRatioList();
    this.drawMainPie();
    this.drawMainLine();
  },
  methods: {
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

    //列表
    // async GetAll() {
    //   let params = {
    //     MaxResultCount: this.pageSize,
    //     SkipCount: (this.currentPage - 1) * this.pageSize,
    //     ...this.searchForm
    //   };
    //   let data = await getListApi(params);
    //   if (data) {
    //     this.tableData = data.items || [];
    //     this.total = data.totalCount || 0;
    //   }
    // },
    //仓库列表
    async GetWarehouseList() {
      let data = await this.$DropBox.getWarehouselist();
      if (data) {
        this.warehouseList = data || [];
      }
    },
    //利用率列表
    async GetAll() {
      let params = {
        MaxResultCount: this.pageSize,
        SkipCount: (this.currentPage - 1) * this.pageSize,
        ...this.searchForm
      };
      let data = await useRatioPie(params);
      if (data) {
        this.warehouseUseRatioList = data.items || [];
        this.total = data.totalCount || 0;
      }
    },
    // async GetWarehouseUseRatioList(){
    //   let data = await useRatioPie();
    //   if (data.items) {
    //     this.warehouseUseRatioList = data.items || [];
    //   }
    // },
    //计算仓库使用率
    caclRatio(row){
      return `${(+row.not_empty_count/(+row.empty_count + +row.not_empty_count)).toFixed(2)*100}%`
    },
    //转换仓库类型显示
    toWarehouseType(row){
      let type;
      this.typeList.forEach(ele=>{
        if(ele.value === row.warehouse_type){
          type = ele.label
        }
      });
      return type
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
          h(
            "span",
            {
              style: this.c_primary
            },
            "新增"
          ),
          h("span", null, "】吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        let params = {
          ...this.ruleForm
        };
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
      console.log(this.multipleSelection);
      if (this.multipleSelection.length == 0) {
        //this.text_deleteRow  全局定义的提示  在textConfig.js中
        this.$message.error(this.text_deleteRow);
      } else {
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
          h("span", null, "确定【"),
          h("span", { style: this.c_danger }, "删除"),
          h("span", null, "】吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        let deleteCount = 0;
        for (let i = 0; i < rows.length; i++) {
          let res = await deleteApi({ id: rows[i].id });
          if (res) {
            deleteCount++;
          }
        }
        if (deleteCount) {
          this.$message({
            type: "success",
            message: `成功删除${deleteCount}条,删除失败${rows.length -
              deleteCount}条`
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
          h(
            "span",
            {
              style: this.c_primary
            },
            "编辑"
          ),
          h("span", null, "】吗？")
        ]),
        showCancelButton: true,
        type: "warning"
      }).then(async action => {
        let params = {
          ...this.ruleForm
        };
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
      let obj = {
        id: row.id,
        warehouse_code: res.warehouse_code,
        warehouse_is_enable: res.warehouse_is_enable,
        warehouse_name: res.warehouse_name,
        warehouse_remark: res.warehouse_remark,
        warehouse_slot_type: res.warehouse_slot_type,
        warehouse_type: res.warehouse_type,
      };
      this.ruleForm = obj;
    },
    //获取图表使用率
    getWarehouseUseRatio(val){
     this.warehouseUseRatioList.forEach(ele=>{
       if(ele.warehouse_code === val){
         this.used = ele.not_empty_count;
         this.pendingUse = ele.empty_count;
       }
     })
     this.drawMainPie()
    },
    drawMainPie() {
      let mainPie = this.$echarts.init(document.getElementById("mainPie"));
      let used = this.used;
      let pendingUse = this.pendingUse;
      //创建一个dom表格
      let createDomTable = function() {
        let tableWrap=document.createElement("div");
        let table = document.createElement("table");
        let tr = document.createElement("tr");
        let name = document.createElement("div");
        let tbody = document.createElement("tbody");
        let thData = ['特性','库存','占比']
        for(let key in thData){
          let th = document.createElement("th");
          th.width = "50px";
          th.style.color = "#333";
          tr.appendChild(th);
          th.innerText=thData[key];
        }
        tr.style['border-bottom']="1px solid #797979"
        tableWrap.setAttribute("align","center");
        tableWrap.style.border = "1px solid #797979";
        tableWrap.style.background = "#cdcdcd";
        name.style.background="#000";
        name.innerText = '使用率';
        tableWrap.width = "100%";
        tbody.setAttribute("align","center");
        tbody.style.color="#c32136";
        table.appendChild(tr);
        table.appendChild(tbody);
        tableWrap.appendChild(name);
        tableWrap.appendChild(table);
        //创建第一行
        tbody.insertRow(0);
        tbody.rows[0].insertCell(0);
        tbody.rows[0].cells[0].appendChild(document.createTextNode("A"));
        tbody.rows[0].insertCell(1);
        tbody.rows[0].cells[1].appendChild(document.createTextNode("1020"));
        tbody.rows[0].insertCell(2);
        tbody.rows[0].cells[2].appendChild(document.createTextNode("45%"));
        tbody.insertRow(1);
        tbody.rows[1].insertCell(0);
        tbody.rows[1].cells[0].appendChild(document.createTextNode("B"));
        tbody.rows[1].insertCell(1);
        tbody.rows[1].cells[1].appendChild(document.createTextNode("300"));
        tbody.rows[1].insertCell(2);
        tbody.rows[1].cells[2].appendChild(document.createTextNode("50%"));
        return tableWrap;
      };
      mainPie.setOption({
        title: {
          text: "仓储A存储区A使用率",
          left: "center"
        },
        legend: {
          y: "bottom"
        },
        tooltip: {
          show: true,
          trigger: "item",
          position: function(val, params, dom) {
           if(params.name === '已使用'){
              dom.innerHTML="";
              dom.appendChild(createDomTable());
           }
          }
        },
        series: [
          {
            type: "pie",
            radius: "55%"
          }
        ],
        dataset: {
          source: [
            ["使用率", "已使用", "未使用"],
            ["已使用", used],
            ["未使用", pendingUse]
          ]
        }
      });
    },
    drawMainLine() {
      let mainLine = this.$echarts.init(document.getElementById("mainLine"));
      let startDate = this.$moment(new Date()).format("YYYY-MM-DD");
      let base = +new Date(2019,9,9);
      let oneDay = 24 * 3600 * 1000;
      let date = [];
      let data = [Math.random() * 300];
      for (var i = 1; i < 60; i++) {
        var now = new Date(base += oneDay);
        date.push([now.getFullYear(), now.getMonth() + 1, now.getDate()].join('-'));
        data.push(Math.round((Math.random() - 0.5) * 20 + data[i - 1]));
      }
      mainLine.setOption({
        title: { text: "仓库使用率折线图" },
        tooltip: {
          show: true,
          trigger: "axis"
        },
        legend: {
          top: 20,
          left: "center",
          formatter: function(name) {
            return name;
          }
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          axisLabel: {
            show: true,
            interval: "auto",
            rotate: 40
          },
          data:date
        },
        yAxis: [
          {
            type: "value",
            min: 0,
            max: 100,
            axisLabel: {
              show: true,
              interval: "auto",
              formatter: "{value}%"
            },
            data: [0, 20, 40, 60, 80, 100]
          }
        ],
        dataZoom: [{
            type: 'inside',
            start: 0,
            end: 10
        }, {
            start: 0,
            end: 10,
            handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
            handleSize: '50%',
            handleStyle: {
                color: '#fff',
                shadowBlur: 3,
                shadowColor: 'rgba(0, 0, 0, 0.6)',
                shadowOffsetX: 2,
                shadowOffsetY: 2
            }
        }],
        series: [
          {
            name: "仓储A存储区A",
            type: "line",
            data: [80, 82, 85, 90, 92, 95]
          },
          {
            name: "仓储A存储区B",
            type: "line",
            data: [40, 42, 45, 50, 52, 55]
          }
        ]
      });
    }
  }
};
</script>