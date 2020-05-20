<template>
  <div class="iot-home">
    <!-- <el-row class="iot-home__bar" :gutter="15">
      <el-col :span="6">
        <div class="grid-content home_total">
          <div class="home_icon">
            <div class="home_icon_val">10000</div>
            <div class="home_icon_label">总设备数</div>
          </div>
          <div class="home_link">
            <router-link to="/device/index">详情 ></router-link>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content home_binding">
          <div class="home_icon">
            <div class="home_icon_val">10000</div>
            <div class="home_icon_label">已绑定备数</div>
          </div>
          <div class="home_link">
            <router-link to="/device/index">详情 ></router-link>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content home_new">
          <div class="home_icon">
            <div class="home_icon_val">10000</div>
            <div class="home_icon_label">当天新绑设备数</div>
          </div>
          <div class="home_link">
            <router-link to="/device/index">详情 ></router-link>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-content home_warning">
          <div class="home_icon">
            <div class="home_icon_val">10000</div>
            <div class="home_icon_label">当前告警数</div>
          </div>
          <div class="home_link">
            <router-link to="/alarm/list">详情 ></router-link>
          </div>
        </div>
      </el-col>
    </el-row> -->
    <div class="iot-home__map" ref="map">
      <div class="wrap">
          <div class="bar" v-for="(item,index) in storeStatusList" :key="index" :style="{background:item.backColor}">
            <div class="bar-title">{{item.title}}</div>
            <div class="bar-count">{{item.count}}</div>
            <div class="bar-view" @click="viewDetail(item.detailLink)">
              查看详情<el-icon class="el-icon-arrow-right"></el-icon>
            </div>
          </div>
      </div>

      <el-col :span="24" style="display:flex;border-bottom:20px solid #eee;padding:20px;position:relative">
      
      <div id="mainLine" style="height:400px;width:50%"></div>
      <div style="height:400px;width:50%;position:relative">
        <el-form inline label-width="50px" style="position:absolute;z-index:999;top:60px;">
          <el-form-item label="仓库">
            <el-select class="iot-w120" v-model="searchForm.id" placeholder="请选择" @change="getWarehouseUseRatio" clearable>
              <el-option v-for="item in warehouseUseRatioList" :key="item.id" :label="item.warehouse_name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div v-for="(item,index) in pieDataList" :key="index" >
          <statistics :pieData="item"></statistics>
        </div>
      </div>
    </el-col>


    </div>
    <!-- <el-row class="iot-home__charts" :gutter="15">
      <el-col :span="12">
        <div class="grid-content" ref="device"></div>
      </el-col>
      <el-col :span="12">
        <div class="grid-content" ref="warn"></div>
      </el-col>
    </el-row> -->
  </div>
</template>

<script>
import echarts from "echarts";
import statistics from './components/statistics'
import {storeDateStatistics,pieStatistics,GetNowTaskNum,GetNowAlarmNum,GetNowSlotPercent,GetCheckTaskNum,useRatioPie} from './api'
import {
  mapMutations
} from 'vuex'
export default {
  name: "home",
  components:{
    statistics
  },
  data() {
    return {
      storeStatusList:[
        {
          title:'当前任务',
          count:0,
          backColor:'cadetblue',
          detailLink:'task-maintask'
        },
        {
          title:'库存报警数',
          count:0,
          backColor:'lightcoral',
          detailLink:'warehouseWarning-threshold'
        },
        {
          title:'仓库使用率',
          count:0,
          backColor:'lightskyblue',
          detailLink:'warehouseReport-useRatio'
        },
        {
          title:'库存盘点',
          count:0,
          backColor:'darkorange',
          detailLink:'Inventory-stocking'
        },
      ],
      pieDataList:[],
      lineDataList:[],
      warehouseUseRatioList:[],
      searchForm:{},
      currentPage:1,
      pageSize:10
    };
  },
  mounted() {
    // this.getConfig();
    // let option = {
    //   title: {
    //     top: 15,
    //     left: 15,
    //     text: "全国设备绑定分布",
    //     textStyle: {
    //       fontSize: 18,
    //       color: "#333"
    //     }
    //   },
    //   tooltip: {
    //     trigger: "axis",
    //     axisPointer: {
    //       lineStyle: {
    //         color: "#ff4040",
    //         width: 3
    //       }
    //     },
    //     backgroundColor: "#333333",
    //     formatter: params => {
    //       let obj = params[0];
    //       return `<div style="text-align:left;"><div>${obj.name}绑定设备</div><div style="font-weight:bold;color:#1279d5;">${obj.value}</div></div>`;
    //     }
    //   },
    //   xAxis: {
    //     type: "category",
    //     boundaryGap: false,
    //     axisTick: {
    //       show: false
    //     },
    //     data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"]
    //   },
    //   yAxis: {
    //     type: "value",
    //     splitLine: {
    //       lineStyle: {
    //         type: "dashed"
    //       }
    //     },
    //     axisTick: {
    //       show: false
    //     }
    //   },
    //   series: [{
    //     data: [820, 932, 901, 934, 1290, 1330, 1320],
    //     type: "line",
    //     itemStyle: {
    //       color: "#0072ff",
    //       borderWidth: 2
    //     },
    //     lineStyle: {
    //       color: "#0072ff"
    //     },
    //     areaStyle: {
    //       color: "#7fb8ff"
    //     }
    //   }]
    // };
    // echarts.init(this.$refs.device).setOption(option);
    // let option2 = {
    //   title: {
    //     top: 15,
    //     left: 15,
    //     text: "近7日告警数",
    //     textStyle: {
    //       fontSize: 18,
    //       color: "#333"
    //     }
    //   },
    //   tooltip: {
    //     trigger: "axis",
    //     axisPointer: {
    //       lineStyle: {
    //         color: "#ff4040",
    //         width: 3
    //       }
    //     },
    //     backgroundColor: "#333333",
    //     formatter: params => {
    //       let obj = params[0];
    //       return `<div style="text-align:left;"><div>${obj.name}告警</div><div style="font-weight:bold;color:#1bd1be;">${obj.value}</div></div>`;
    //     }
    //   },
    //   xAxis: {
    //     type: "category",
    //     boundaryGap: false,
    //     axisTick: {
    //       show: false
    //     },
    //     data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"]
    //   },
    //   yAxis: {
    //     type: "value",
    //     splitLine: {
    //       lineStyle: {
    //         type: "dashed"
    //       }
    //     },
    //     axisTick: {
    //       show: false
    //     }
    //   },
    //   series: [{
    //     data: [820, 932, 901, 934, 1290, 1330, 1320],
    //     type: "line",
    //     itemStyle: {
    //       color: "#1bd1be",
    //       borderWidth: 2
    //     },
    //     lineStyle: {
    //       color: "#1bd1be"
    //     },
    //     areaStyle: {
    //       color: "#8de8de"
    //     }
    //   }]
    // };
    // echarts.init(this.$refs.warn).setOption(option2);
    this.getAllStatusNum();
    this.getLineList();
    this.GetUseRatioPie()
  },
  methods: {
    ...mapMutations(['updatePageSize']),
    getConfig() {
    },
    //利用率列表
    async GetUseRatioPie() {
      let params = {
        MaxResultCount: this.pageSize,
        SkipCount: (this.currentPage - 1) * this.pageSize,
        ...this.searchForm
      };
      // let data = await useRatioPie(params);
      let data = await this.$DropBox.getWarehouselist();
      if (data) {
        this.warehouseUseRatioList = data || [];
        this.getPieList(this.warehouseUseRatioList[0].id)
      }
    },
    //获取图表使用率
    async getWarehouseUseRatio(id){
      console.log(id)
      await this.getPieList(id);
    },
    //统计可视化折线图
    drawMainLine() {
      let mainLine = this.$echarts.init(document.getElementById("mainLine"));
      let startDate = this.$moment(new Date()).format("YYYY-MM-DD");
      let oneDay = 24 * 3600 * 1000;
      let base = +new Date() -6*oneDay;
      let date = [];
      let data = [Math.random() * 300];
      for (var i = 1; i < 15; i++) {
        var now = new Date(base);
        base+=oneDay;
        date.push([now.getFullYear(), now.getMonth() + 1, now.getDate()].join('-'));
        data.push(Math.round((Math.random() - 0.5) * 20 + data[i - 1]));
      }
      mainLine.setOption({
        title: { text: "库存日期统计" },
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
              formatter: "{value}"
            },
            data: [0, 20, 40, 60, 80, 100]
          }
        ],
        dataZoom: [{
            type: 'slider',
            start:0,
            end: 50,
        }, {
            start: 0,
            end: 50,
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
         ...this.lineDataList
        ]
      });
    },
    //获取折线图数据
    async getLineList(){
      let res = await storeDateStatistics({days_count:15});
      if(res.length){
        res.forEach(ele=>{
          this.lineDataList.push( {
            name: ele[0].warehouse.warehouse_name,
            type: "line",
            data: ele.reverse().map(e=>e.warehouse_stock)
          })
        })
        this.drawMainLine()
      }
    },
    //获取饼状图列表
    async getPieList(id){
      let res = await pieStatistics({slot_warehouse_id:id});
      if(res){
        if(!res.items.length){
          this.$message({
            message:'无此仓库数据',
            type:'warning'
          })
        }
        this.pieDataList = res.items;
      }
    },
    //获取所有状态数
    async getAllStatusNum(){
      let nowTaskNum = GetNowTaskNum();
      let nowAlarmNum = GetNowAlarmNum();
      let nowSlotPercent = GetNowSlotPercent();
      let checkTaskNum = GetCheckTaskNum();
      Promise.all([nowTaskNum,nowAlarmNum,nowSlotPercent,checkTaskNum]).then(([a,b,c,d])=>{
        [a,b,c,d].forEach((ele,index)=>{
          if(typeof ele == "object"){
            if(index === 2){
              this.storeStatusList[index].count = (ele.not_empty_count/(+ele.not_empty_count + +ele.empty_count)).toFixed(4)*100 + '%';//计算仓库使用率
            }else{
              this.storeStatusList[index].count = ele.listCount
            }
          }
        })
      })
    },
    //查看详情
    viewDetail(name){
      this.$router.push({name:name})
    }
  }

};

</script>

<style lang="scss">
.iot-home__bar {
  padding-bottom: 15px;

  .grid-content {
    height: 120px;
    border-radius: 4px;
    padding: 0 32px 0 40px;
    text-align: left;
    color: #fff;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .home_icon {
      height: 68px;
      padding-left: 85px;
      background-repeat: no-repeat;
      background-position: 0 center;
      display: flex;
      flex-direction: column;
      justify-content: center;
    }

    .home_icon_val {
      font-size: 33px;
    }

    .home_link {
      height: 50px;
    }

    a {
      color: #fff;
      text-decoration-line: none;
    }
  }

  .home_total {
    background-color: #438cf2;

    .home_icon {
      background-image: url("~@/assets/icon/home_icon01_total.png");
    }
  }

  .home_binding {
    background-color: #10c6c0;

    .home_icon {
      background-image: url("~@/assets/icon/home_icon02_binding.png");
    }
  }

  .home_new {
    background-color: #39b1e3;

    .home_icon {
      background-image: url("~@/assets/icon/home_icon03_new.png");
    }
  }

  .home_warning {
    background-color: #9376f3;

    .home_icon {
      background-image: url("~@/assets/icon/home_icon04_warning.png");
    }
  }
}

.iot-home__map {
  // height: 950px;
  border: 1px solid #e5e5e5;
  border-radius: 4px;
  .wrap {
  height: 120px;
  margin: 1rem;
  display: flex;
  justify-content: space-evenly;
  .bar {
    width: 100%;
    color: #ffffff;
    border-radius: 0.2rem;
    margin-right:1rem;
    .bar-title {
      padding: 1rem;
      font-size: 1.5rem;
    }
    .bar-count {
      text-align: right;
      padding-right: 1rem;
      font-size: 2rem;
    }
    .bar-view {
      text-align: center;
      position: relative;
      padding: 0.1rem 0.3rem;
      cursor: pointer;
    }
    .bar-view::before {
      content: "";
      position: absolute;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.1);
    }
  }
}
}

.iot-home__charts {
  padding-top: 15px;

  .grid-content {
    height: 400px;
    border: 1px solid #e5e5e5;
    border-radius: 4px;
  }
}
</style>