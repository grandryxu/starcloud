var SevenDayDevice = {
    dataArrayX: [],
    dataArrayY: []
};

var s1 = echarts.init(document.getElementById("sevenDayDevice"));

SevenDayDevice.init = function (projectCode) {
    //初始化的时候清空之前的数据
    SevenDayDevice.dataArrayX.length = 0;
    SevenDayDevice.dataArrayY.length = 0;
    $.get("/projectIndexReprot/getTotalNumFromSevenDays?projectCode=" + projectCode).done(function (data) {
        for (var i = 0; i < data.length; i++) {
            SevenDayDevice.dataArrayX.push(data[i].date);
            SevenDayDevice.dataArrayY.push(data[i].count);
        }
        loadEchart();
    });
};

function loadEchart() {
    option1 = {
        tooltip: {
            show: true,
            formatter:function (param) {
                return param.value+"人";
            }
        },
        title: {
            text: '项目近七日考勤情况',
            left: '50%',
            textAlign: 'center'
        },
        xAxis: {
            name: '日期',
            type: 'category',
            data: SevenDayDevice.dataArrayX
        },
        yAxis: {
            name: '人数',
            type: 'value'
        },
        series: [{
            data: SevenDayDevice.dataArrayY,
            type: 'line'
        }]
    };
    s1.setOption(option1),
    $(window).resize(s1.resize);
}