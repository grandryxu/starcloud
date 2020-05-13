var WorkerAttReport = {
    dataArrayXX: [],
	dataArrayYY: [],
	dataArrayZZ: []
};
$(function() {
	$.get("/cooSub/getProjectDevice").done(function (data) {
        for (var i = 0; i < data.length; i++) {
        	WorkerAttReport.dataArrayXX.push(data[i].workerName);
        	WorkerAttReport.dataArrayYY.push({value:data[i].totalNum,name:data[i].workerName,bfb:data[i].bfb});
        	// WorkerAttReport.dataArrayZZ.push(data[i].bfb);
        }
        init3();
    });
});

function init3(){
    var s2 = echarts.init(document.getElementById("workerAttendance")),
    option2 = {
        title: {
            text: '项目当天考勤情况',
            left: '50%',
            textAlign: 'center'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                lineStyle: {
                    color: '#ddd'
                }
            },
            backgroundColor: 'rgba(255,255,255,1)',
            padding: [5, 10],
            textStyle: {
                color: '#7588E4',
            },
            extraCssText: 'box-shadow: 0 0 5px rgba(0,0,0,0.3)',
            formatter:function (param) {
                return param[0].data.name+"<br>当日考勤人数:"+param[0].data.value+"<br>考勤率: "+(param[0].data.bfb*100)+"%";
            }
        },
        legend: {
            right: 20,
            orient: 'vertical',
            data: ['人数']
        },
        xAxis: {
            type: 'category',
            data: WorkerAttReport.dataArrayXX,
            boundaryGap: false,
            splitLine: {
                show: true,
                interval: 'auto',
                lineStyle: {
                    color: ['#D4DFF5']
                }
            },
            axisTick: {
                show: false
            },
            axisLine: {
                lineStyle: {
                    color: '#609ee9'
                }
            },
            axisLabel: {
                margin: 10,
                textStyle: {
                    fontSize: 14
                }
            }
        },
        yAxis: {
            type: 'value',
            splitLine: {
                lineStyle: {
                    color: ['#D4DFF5']
                }
            },
            axisTick: {
                show: false
            },
            axisLine: {
                lineStyle: {
                    color: '#609ee9'
                }
            },
            axisLabel: {
                margin: 10,
                textStyle: {
                    fontSize: 14
                }
            }
        },
        series: [{
            name: '人数',
            type: 'line',
            smooth: true,
            showSymbol: false,
            symbol: 'circle',
            symbolSize: 6,
            data: WorkerAttReport.dataArrayYY,
            areaStyle: {
                normal: {
                    color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                        offset: 0,
                        color: 'rgba(199, 237, 250,0.5)'
                    }, {
                        offset: 1,
                        color: 'rgba(199, 237, 250,0.2)'
                    }], false)
                }
            },
            itemStyle: {
                normal: {
                    color: '#f7b851'
                }
            },
            lineStyle: {
                normal: {
                    width: 3
                }
            }
        }]
    };

    s2.setOption(option2),
    $(window).resize(s2.resize);
}