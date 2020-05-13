var ProjectWorker4Report = {
    dataArrayN4: [],
    dataArrayX4: [],
    dataArrayY4: [],
    dataArrayZ4: []
};

//y轴最大值
var yMax = 0;
var dataLength = 0;

$(function () {
    $.get("/cooSub/getProjectJoinRange").done(function (data) {
        dataLength = data.length;
        for (var i = 0; i < data.length; i++) {
            ProjectWorker4Report.dataArrayN4.push(data[i].projectName);
            ProjectWorker4Report.dataArrayX4.push(data[i].joinIn);
            ProjectWorker4Report.dataArrayY4.push(data[i].joinOut);
            ProjectWorker4Report.dataArrayZ4.push(data[i].count);
            yMax = Math.max(data[i].joinIn, data[i].joinOut, data[i].count, yMax);
        }
        init4();
    });
});

function init4() {
    var s4 = echarts.init(document.getElementById("projectWorker"));
    var option4 = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            },
            formatter: function (params) {
                var str = '';
                var name = params[0].name;
                var joinIn = params[0].value;
                var joinOut = params[1].value;
                var count = params[2].value;
                //名称格式转换
                var nameStr = "";
                //i<按7个字符循环的次数
                for (var i = 0; i < (name.length % 7 === 0 ? parseInt(name.length / 7) : (parseInt(name.length / 7) + 1)); i++) {
                    nameStr += name.substring(i * 7, (i + 1) * 7) + "</br>"
                }
                str = nameStr
                    + "进场:" + joinIn
                    + "</br>" + "退场:" + joinOut
                    + "</br>" + "累计:" + count;
                return str;
            }
        },
        color: ['#4680FF', '#DE4444', '#FF9500'],
        title: {
            text: '项目人员分布',
            textStyle: {
                color: '#333',
                fontSize: 14
            },
            left: 10,
            top: 10
        },
        dataZoom: [
            {
                start: 0,//默认为0
                end: 60,
                type: dataLength<=7?"inside":"slider",
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
        legend: {
            data: ['进场', '退场', '累计'],
            top: 30
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: ProjectWorker4Report.dataArrayN4,
                axisLine: {
                    lineStyle: {
                        color: ['#D8DDE4']
                    }
                },
                axisLabel: {
                    color: "#000",
                    interval: 0,
                    rotate: 0,
                    formatter: function (params) {
                        var interval = 7;
                        if(dataLength >=10 && dataLength <15){
                            interval = 5;
                        }else if(dataLength >=15 && dataLength<20){
                            interval = 3;
                        }else if(dataLength>=20){
                            interval = 2;
                        }
                        return params.length > interval ? params.substring(0, interval) + "..." : params;
                    }
                }
            }],
        yAxis: [{
            scale: true,
            max: yMax,
            min: 0,
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
            data: ProjectWorker4Report.dataArrayX4
        },
            {
                name: '退场',
                type: 'bar',
                barWidth: 14,
                data: ProjectWorker4Report.dataArrayY4
            },
            {
                name: '累计',
                type: 'bar',
                barWidth: 14,
                data: ProjectWorker4Report.dataArrayZ4
            }
        ]
    };
    s4.setOption(option4),
        $(window).resize(s4.resize);
}