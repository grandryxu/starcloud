<template>
  <div class="iot-list">
    <el-container style="height: 500px; border: 1px solid #eee">
      <el-container>
        <el-header type="flex">
          <el-form inline label-width="80px">
            <el-form-item label="选择仓库">
              <el-select class="iot-w120" v-model="searchForm.warehouse_id" @change="searchWasehouse">
                <el-option v-for="item in warehouseList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-radio v-model="radioSearch" @change="selectSearchType" label='1'>按层查询</el-radio>
              <el-radio v-model="radioSearch" @change="selectSearchType" label='2'>按排查询</el-radio>
            </el-form-item>
            <el-form-item label="选择查看">
              <el-select class="iot-w120" v-model="searchForm.rowAndColumn" @change="selectLevelOrRow">
                <el-option v-for="item in rowAndColumnList" :key="item.id" :label="item.label" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
        </el-header>
        <!-- 按层查询 -->
        <el-main v-show="radioSearch == 1">
          <el-table ref="layerRef" height="400" border show-header :data="tableDataByLevel">
            <el-table-column fixed align="center" type="index" label="列" width="50">
              <template slot-scope="scope">
                <span>{{+scope.$index + 1 + '列'}}</span>
              </template>
            </el-table-column>
            <el-table-column align="center" v-for="(col,index) in cols1" :key="index" :prop="col.type === 2 ? 'hang' : (index + '')" :label="col.type === 2 ? '巷道' : col.label" :class-name="col.type === 2? 'tunnel-back' : ''">
              <template slot-scope="scope">
                <div class="tunnel-line" v-if="col.type === 2">
                  <div class="tunnel-line-samll"></div>
                  <div class="tunnel-line-large"></div>
                  <div class="tunnel-line-samll"></div>
                </div>
                <div v-else>
                  <div v-if="scope.row[index] && !scope.row[index].slot.slot_closed_status" @mouseenter="showQualityStatusList(scope.row[index].inventorys)" @mouseleave="closeQualityStatusList" class="tunnel-line" :class="[
                    (labelType === 1 && 'status-typeOne-' + scope.row[index].slot.slot_stock_status),
                    (labelType === 2 && scope.row[index].slot.slot_stock_status && 'status-typeOne-4'),
                    (scope.row[index].slot.slot_imp_status && 'status-no-in'),
                    (scope.row[index].slot.slot_exp_status && 'status-no-out'),
                    ]">
                    <div class="inventory-code">{{scope.row[index].slot.slot_code}}</div>
                    <div v-if="labelType === 1 && scope.row[index].inventorys" class="quality-color">
                      <div :style="{
                        'background':item.quality.quality_color || '',
                        'flex':'1',
                        'height':'100%'
                        }" v-for="(item,index) in scope.row[index].inventorys" :key='index'>
                      </div>
                    </div>
                  </div>
                   <div v-else-if="scope.row[index] && scope.row[index].slot.slot_closed_status" class="tunnel-line shield"></div>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </el-main>
        <!-- 按排查询 -->
        <el-main v-show="radioSearch == 2">
          <el-table ref="rowRef" height="400" border show-header :data="tableDataByRow">
            <el-table-column fixed align="center" type="index" label="列" width="50">
              <template slot-scope="scope">
                <span>{{tableDataByRow.length-scope.$index + '层'}}</span>
              </template>
            </el-table-column>
            <!-- <el-table-column fixed align="center" type="index" label="头层" width="50" class-name="quarter-circle-out">
              <template slot-scope="scope">
                <div v-show="scope.$index == 0" class="quarter-circle"></div>
              </template>
            </el-table-column> -->
            <el-table-column align="center" v-for="(col,index) in cols2" :key="index" :prop="index + ''" :label="col.label">
              <template slot-scope="scope">
                <div v-if="scope.row[index] && !scope.row[index].slot.slot_closed_status" @mouseenter="showQualityStatusList(scope.row[index].inventorys)" class="tunnel-line" :class="[
                  (labelType === 2 && scope.row[index].slot.slot_stock_status && 'status-typeOne-4'),
                  (scope.row[index].slot.slot_imp_status && 'status-no-in'),
                  (scope.row[index].slot.slot_exp_status && 'status-no-out'),
                  ]">
                  <div class="inventory-code">{{scope.row[index].slot.slot_code}}</div>
                  <div v-if="labelType === 1 && scope.row[index].inventorys" class="quality-color">
                    <div :style="{
                      'background':item.quality.quality_color || '',
                      'flex':'1',
                      'height':'100%'
                      }" v-for="(item,index) in scope.row[index].inventorys" :key='index'>
                    </div>
                  </div>
                </div>
                <div v-else-if="scope.row[index] && scope.row[index].slot.slot_closed_status" class="tunnel-line shield"></div>
              </template>
            </el-table-column>
          </el-table>
        </el-main>
      </el-container>

      <!-- 质量状态表 -->
      <el-dialog
        class="iot-dialog"
        :title="dialogTitle"
        :visible.sync="dialogVisible"
        width="824px"
        append-to-body
      >
        <el-table
          :data="tableDataByQuality"
          stripe
          style="width: 100%;"
          border
        >
          <el-table-column
            align="center"
            prop="goods"
            label="物料编码"
            min-width="100"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <span>{{scope.row.goods.goods_code}}</span>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="goods"
            label="物料名称"
            min-width="100"
            show-overflow-tooltip
          >
             <template slot-scope="scope">
              <span>{{scope.row.goods.goods_name}}</span>
            </template>
          </el-table-column>
          <el-table-column
            align="center"
            prop="inventory_stock_code"
            label="托盘"
            min-width="100"
            show-overflow-tooltip
          ></el-table-column>
           <el-table-column
            align="center"
            prop="inventory_box_code"
            label="箱码"
            min-width="100"
            show-overflow-tooltip
          ></el-table-column>
           <el-table-column
            align="center"
            prop="inventory_quantity"
            label="库存数量"
            min-width="100"
            show-overflow-tooltip
          ></el-table-column>
           <el-table-column
            align="center"
            prop="quality"
            label="质量状态"
            min-width="100"
            show-overflow-tooltip
          >
            <template v-if="scope.row.quality" slot-scope="scope">
              <span>{{scope.row.quality.quality_name}}</span>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>

      <el-aside width="200px" style="background-color: rgb(238, 241, 246);">
        <div class="flex" style="justify-content: space-evenly;">
          <div>
            <!-- <el-radio v-model="radioType" @change="switchType" label="1">类型1</el-radio> -->
            <div style="font-weight:bolder;">质量状态</div>

            <div class="flex" v-for="(item,index) in qualityStatus" :key='index'>
              <div class="label" :style="{'background':item.quality_color}"></div>
              <span>{{item.quality_name}}</span>
            </div>
          </div>
          <div>
            <!-- <el-radio v-model="radioType" @change="switchType" label="2">类型2</el-radio> -->
            <div style="font-weight:bolder;">库位状态</div>
            <div class="flex">
              <div class="label label-orange"></div>
              <span>巷道</span>
            </div>
            <div class="flex">
              <div class="label label-grey"></div>
              <span>空货位</span>
            </div>
            <div class="flex">
              <div class="label label-purple"></div>
              <span>空托盘</span>
            </div>
            <div class="flex">
              <img class="label" src="@/assets/icon/no-in.png"/>
              <span>入库锁定</span>
            </div>
            <div class="flex">
              <img class="label" src="@/assets/icon/no-out.png"/>
              <span>出库锁定</span>
            </div>
             <div class="flex">
              <img class="label" style="height:14px;background:#cccccc" src="@/assets/icon/shield.png"/>
              <span>屏蔽</span>
            </div>
          </div>
        </div>
      </el-aside>
    </el-container>
  </div>
</template>

<script>
import {getListByLevelApi,getListByRowApi,getSlotLevelApi,getSlotRowApi} from './api'
export default {
  data() {
    return {
      warehouseList: [],
      searchForm: {
        rowAndColumn:''
      },
      radioSearch: "1",
      radioType: "1",
      labelType:1,
      searchType:'lev',
      tableDataByLevel:[],
      tableDataByRow:[],
      rowAndColumnList:[],
      tableDataByQuality:[],
      cols1: [],
      cols2: [],
      warehouseIndex:0,
      dialogTitle:'质量状态',
      dialogVisible:false,
      qualityStatus:[]
    };
  },
  mounted() {
    this.initData()
    this.getQualityStatus()
  },
  methods: {
    //仓库列表
    async GetWarehouseList() {
      let data = await this.$DropBox.getWarehouselist();
      if (data) {
        this.warehouseList = data || [];
      }
    },
    //仓库查询
    searchWasehouse(id){
      this.searchForm.warehouse_id = id;
      this.initData();
    },
    //获取质量状态列表
    async getQualityStatus(){
      let res =await this.$DropBox.getQualitylist();
      console.log(res)
      if(res){
        this.qualityStatus = res.items.reverse()
      }
      
    },
    //数据初始化
    async initData(){
      await this.GetWarehouseList();
      let warehouseId = this.searchForm.warehouse_id ? this.searchForm.warehouse_id : this.warehouseList[this.warehouseIndex].id;
      this.searchForm.warehouse_id = warehouseId;
      if(this.searchType === 'lev'){
        this.searchForm.rowAndColumn = 1;
        await this.getSlotLevel(warehouseId);
        await this.getListByLevel(warehouseId,1);
         this.$nextTick(()=>{
          this.$refs.layerRef.doLayout()
        })
      }else{
        await this.getSlotRow(warehouseId);
        await this.getListByRow(warehouseId,this.rowAndColumnList[0].id);
        this.searchForm.rowAndColumn = this.rowAndColumnList[0].id;
        this.$nextTick(()=>{
          this.$refs.rowRef.doLayout()
        })
      }
     
    },
    //获取按层查询的数据
    async getListByLevel(warehouse_id,level){
      let res = await getListByLevelApi({
        slot_warehouse_id:warehouse_id,
        slot_layer:level
      });
      if(res){
        let newSlotArr = res.slotArr.map((pe,pi,pa)=>{ 
          let tem = [];
          let falg = 0;
            res.clos.forEach((cpe,cpi,cpa)=>{
              if(cpe.type === 1){
                tem[cpi] = pe[falg++];
              }else{
                tem[cpi] = [];
              }
          })
          return pe = tem
        })
        this.cols1 = res.clos;
        this.tableDataByLevel = newSlotArr;
      }
    },
    //获取库位层
    async getSlotLevel(warehouse_id){
      let res = await getSlotLevelApi({warehouse_id:warehouse_id});
      let level = [];
      console.log(res)
      if(res){
        for(let i=1;i<+res + 1;i++){
          level.push({
            id:i,
            label:i+'层'
          })
        }
      }
      this.rowAndColumnList = level
    },
     //获取按排查询的数据
    async getListByRow(warehouse_id,row){
      let res = await getListByRowApi({
        slot_warehouse_id:warehouse_id,
        slot_row_id:row
      });
      if(res){
        this.cols2 = res.clos;
        this.tableDataByRow = res.slotArr.reverse();
      }
    },
    //获取库位排
    async getSlotRow(warehouse_id){
      let res = await getSlotRowApi({warehouse_id:warehouse_id});
      let row = [];
      if(res){
        res.forEach(ele => {
           row.push({
            id:ele.id,
            label:ele.row_name
          })
        })
      }
      this.rowAndColumnList = row
    },
    //切换查询类型
    selectSearchType(val){
      console.log(this.radioSearch)
      if(val == 1){
        this.searchType = 'lev';
      }else{
        this.searchType = 'row';
      }
       
      this.initData();
    },
    //选择那一层或者排
    selectLevelOrRow(val){
      if(this.searchType === 'lev'){
        this.getListByLevel(this.searchForm.warehouse_id,val)
      }else{
        this.getListByRow(this.searchForm.warehouse_id,val)
      }
      
    },
    //切换状态类型
    switchType(type){
      if(type == 1){
        this.labelType =1;
      }else if(type == 2){
        this.labelType =2;
      }
    },
    //鼠标悬停显示质量状态列表
    showQualityStatusList(val){
      if(val&&val.length){
        this.dialogVisible = true;
        this.tableDataByQuality = val;
      }
    },
    //鼠标悬停关闭质量状态列表
    closeQualityStatusList(val){
      // this.dialogVisible = false;
      // this.tableDataByQuality = [];
    }
  },
};
</script>

<style lang="scss" scoped>
$colorArr: (
  (
    name: "grey",
    color: #cccccc
  ),
  (
    name: "peachpuff",
    color: #facd91
  ),
  (
    name: "darkolivegreen",
    color: #bfbf00
  ),
  (
    name: "green",
    color: #33cc00
  ),
  (
    name: "red",
    color: #ff0000
  ),
  (
    name: "yellow",
    color: #ffff00
  ),
  (
    name: "blue",
    color: #0000ff
  ),
  (
    name: "purple",
    color: #cc6699
  ),
  (
    name: "black",
    color: #000000
  ),
  (
    name: "orange",
    color: #FFDF25
  )
);

@for $i from 1 through length($colorArr) {
  $item: nth($colorArr, $i);
  .label-#{map-get($item, name)} {
    background-color: map-get($item, color);
  }
  .status-typeOne-#{$i} {
    background-color: map-get($item, color);
    line-height: 40px;
    position: absolute;
    top:0;
    left: 0;
    width: 100%;
    height: 100%;
  }
}
.status-no-in::after{
  position: absolute;
  right: 0;
  top: 0;
  content: '';
  width: 15px;
  height: 15px;
  z-index: 999;
  background:url(../../../assets/icon/no-in.png) no-repeat;
  background-size:15px;
}
.status-no-out::before{
  position: absolute;
  right: 0;
  bottom: 0;
  content: '';
  width: 15px;
  height: 15px;
  z-index: 999;
  background:url(../../../assets/icon/no-out.png) no-repeat;
  background-size:15px;
}

.shield::before{
  position: absolute;
  right: 0;
  bottom: 0;
  content: '';
  width: 100%;
  height: 100%;
  z-index: 999;
  background:url(../../../assets/icon/shield.png) no-repeat;
  background-size:100% 100%;
  background-color: #cccccc;
}
.inventory-code{
  z-index: 999;
}
.inventory-code,.quality-color{
  display:flex;
  justify-content: space-evenly;
  align-items: center;
  position: absolute !important;
  top:0;
  left: 0;
  width: 100%;
  height: 100%;
}

.el-header {
  color: #333;
  padding-top: 10px;
  border-bottom: 1px #ddd solid;
}

.el-aside {
  color: #333;
}
.labels {
  display: flex;
  justify-content: space-between;
}
.flex {
  display: flex;
  padding: 5px 0;
  img{
    width: 20px;
    height: 20px;
  }
}
.label {
  position: relative;
  border: 1px solid #eee;
  width: 20px;
  height: 14px;
}
.cross::before {
  position: absolute;
  content: "";
  top: 6px;
  content: "";
  width: 18px;
  height: 1px;
  background: #999;
  transform: skew(0deg, 35deg);
}
.cross::after {
  position: absolute;
  top: 6px;
  content: "";
  width: 18px;
  height: 1px;
  background: #999;
  transform: skew(0deg, -35deg);
}
.el-form-item {
  margin-bottom: 0 !important;
}

</style>
