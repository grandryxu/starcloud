var WorkerNativePlaceReport = {
    dataArrayX: [],
	dataArrayY: []
};
$(function() {
	$.get("/projectIndexReprot/getBirthPlaceCountPC").done(function (data) {
        for (var i = 0; i < data.length; i++) {
        	WorkerNativePlaceReport.dataArrayX.push(data[i].birthPlace);
        	WorkerNativePlaceReport.dataArrayY.push(data[i].count);
        }
        init2();
    });
});

function init2(){
    var s1 = echarts.init(document.getElementById("workerNativePlace")),
    option1 = {
        color: ['#4680FF', '#DE4444', '#FF9500'],
        title: {
            text: '人员籍贯分布',
            textStyle: {
                color: '#333',
                fontSize: 14
            },
            left: 10,
            top: 10
        },
        dataZoom: [
            {
                start:0,//默认为0
                end: 70,
                type: 'slider',
                show: true,
                xAxisIndex: [0],
                handleSize: 0,//滑动条的 左右2个滑动条的大小
                height: 8,//组件高度
                left: 50, //左边的距离
                right: 40,//右边的距离
                bottom: 26,//右边的距离
                handleColor: '#ddd',//h滑动图标的颜色
                handleStyle: {
                    borderColor: "#cacaca",
                    borderWidth: "1",
                    shadowBlur: 2,
                    background: "#ddd",
                    shadowColor: "#ddd",
                },
                fillerColor: new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
                    //给颜色设置渐变色 前面4个参数，给第一个设置1，第四个设置0 ，就是水平渐变
                    //给第一个设置0，第四个设置1，就是垂直渐变
                    offset: 0,
                    color: '#1eb5e5'
                }, {
                    offset: 1,
                    color: '#5ccbb1'
                }]),
                backgroundColor: '#ddd',//两边未选中的滑动条区域的颜色
                showDataShadow: false,//是否显示数据阴影 默认auto
                showDetail: false,//即拖拽时候是否显示详细数值信息 默认true
                handleIcon: 'M-292,322.2c-3.2,0-6.4-0.6-9.3-1.9c-2.9-1.2-5.4-2.9-7.6-5.1s-3.9-4.8-5.1-7.6c-1.3-3-1.9-6.1-1.9-9.3c0-3.2,0.6-6.4,1.9-9.3c1.2-2.9,2.9-5.4,5.1-7.6s4.8-3.9,7.6-5.1c3-1.3,6.1-1.9,9.3-1.9c3.2,0,6.4,0.6,9.3,1.9c2.9,1.2,5.4,2.9,7.6,5.1s3.9,4.8,5.1,7.6c1.3,3,1.9,6.1,1.9,9.3c0,3.2-0.6,6.4-1.9,9.3c-1.2,2.9-2.9,5.4-5.1,7.6s-4.8,3.9-7.6,5.1C-285.6,321.5-288.8,322.2-292,322.2z',
                filterMode: 'filter'
            }

        ],
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [{
            type: 'category',
            data: WorkerNativePlaceReport.dataArrayX,
            axisLine: {
                lineStyle: {
                    color: ['#D8DDE4']
                }
            },
            axisLabel: {
                color: "#000"
            }
        }],
        yAxis: [{
            type: 'value',
            splitLine: {
                show: false,
            },
            axisLine: {
                lineStyle: {
                    color: ['#D8DDE4']
                }
            },
            axisLabel: {
                color: "#666"
            }
        }],
        series: [{
            name: '进场',
            type: 'bar',
            barWidth: 14,
            data: WorkerNativePlaceReport.dataArrayY
        }]
    };
    s1.setOption(option1),
    $(window).resize(s1.resize);
}