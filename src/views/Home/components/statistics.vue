<template>
  <div class="pie">
      <div id="mainPie" style="width:100%;height:100%;"></div>
  </div>
</template>


<script>
import echarts from "echarts";
export default {
  props: {
    title: {
      type: String,
      default: "当前任务"
    },
    count: {
      type: Number,
      default: 3
    },
    pieData:{
        type:Object,
        default:{
        "warehouse_code": "",
        "warehouse_name": "",
        "warehouse_type": 0,
        "empty_count": 0,
        "not_empty_count": 0
      }
    }
  },
  data() {
    return {};
  },
  mounted() {
    this.drawMainPie();
  },
  methods: {
    drawMainPie() {
      let mainPie = this.$echarts.init(document.getElementById("mainPie"));
      let pieData = this.pieData;
      mainPie.setOption({
        title: {
          text: pieData.warehouse_name,
          // left: 20,
          // top:0,     
          //   textStyle:{
          //       fontSize:12
          //   }
        },
        legend: {
        orient: 'vertical',
        right: 10,
        top:30
        },
        tooltip: {
          show: true,
          trigger: "item",
        },
        series: [
          {
            type: "pie",
            radius: "60%",
            top:100,
            
          }
        ],
        dataset: {
          source: [
            ["使用率", "已使用", "未使用"],
            ["已使用", pieData.empty_count],
            ["未使用", pieData.not_empty_count]
          ]
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.pie {
  height: 400px;
  // width: 23%;
  margin: 1rem;
  flex:1
}
</style>