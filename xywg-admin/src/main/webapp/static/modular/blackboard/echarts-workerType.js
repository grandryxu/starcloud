$(function() {
    var s = echarts.init(document.getElementById("workerType")),

        option = {
            color: ['#3398DB'],
            title: {
                text: '工种类型',
                textStyle: {
                    //color: '#fff'
                }
            },
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : ['瓦工', '文明工', '油漆工', '砌筑工', '木工', '架子工', '电工'],
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'工种类型',
                    type:'bar',
                    barWidth: '60%',
                    data:[10, 52, 200, 334, 390, 330, 220]
                }
            ]
        };
    s.setOption(option),
        $(window).resize(s.resize);
});