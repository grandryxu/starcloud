/**
 * 招标月度统计
 */
var myChart = null;
var InviteMonthReport = {
    id: "",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptId: null,
    dataX: [],
    dataY: [],
    reportYear: new Date().getFullYear(),
    reportType: ""

};

$(function () {
    //初始化统计数据
    initializeEcharts();

    laydate.render({
        elem: '#reportYear',
        type: 'year',
        showBottom:false,
        value: new Date().getFullYear(),
        change:function(value,dates,edate){
            $('#reportYear').val(value);
        },
        ready:function(date){
            $("#layui-laydate1").off('click').on('click','.laydate-year-list li',function(){
                $("#layui-laydate1").remove();
            });
        },
    });
});

/**
 * 初始化echarts函数
 */
function initializeEcharts(){
    InviteMonthReport.dataX=[];
    InviteMonthReport.dataY=[];
    myChart = echarts.init(document.getElementById('inviteMonthReportEChartsDiv'));
    myChart.showLoading();
    getData();
}

// 异步加载数据
function getData() {
    var url = '/lxInviteBid/monthReportData';
    $.post(
        url,
        {
            reportYear: InviteMonthReport.reportYear,
            reportType: InviteMonthReport.reportType
        },
        function (data) {
        for (var i = 0; i < data.length; i++) {
            InviteMonthReport.dataX.push(data[i].mon);
            InviteMonthReport.dataY.push(data[i].cou);
        }
        myChart.hideLoading();
        echartsOption();
    });
}

function echartsOption() {
    // 显示标题，图例和空的坐标轴
    var title = "";
    var info = "";
    if (InviteMonthReport.reportType == '') {
        title = '招标月度统计';
        info = '招标数量';
    }
    if (InviteMonthReport.reportType == '5') {
        title = '中标月度统计';
        info = '中标数量';
    }
    myChart.setOption({
        title: {
            text: title
        },
        tooltip: {},
        legend: {
            data: [info]
        },
        toolbox: {
            feature: {
                //自定义工具必须以my开头
                myRefersh:{//自定义按钮 selfbuttons可以随便取名字
                    show:true,//是否显示
                    title:'刷新', //鼠标移动上去显示的文字
                    icon:'image:///static/img/refersh.png',
                    onclick:function() {//点击事件,这里的option1是chart的option信息
                        window.location.reload();
                    }
                },
                saveAsImage: {
                    show: true ,
                    title: '下载'
                }
            }
        },
        xAxis: {
            axisLabel: {
                //这个是倾斜角度，也是考虑到文字过多的时候，方式覆盖采用倾斜
                rotate: 30,
                //这里是考虑到x轴文件过多的时候设置的，如果文字太多，默认是间隔显示，设置为0，标示全部显示，当然，如果x轴都不显示，那也就没有意义了
                //interval :0
            },
            data: InviteMonthReport.dataX
        },
        yAxis: {},
        series: [{
            name: info,
            type: 'bar',
            barWidth : 30,
            itemStyle: {
                normal: {
                    color: "#C23531"
                }
            },
            data: InviteMonthReport.dataY
        }]
    });
}

/**
 * 查询结算单列表
 */
InviteMonthReport.search = function () {
    /*var queryData = {};
    queryData['reportYear'] = $("#reportYear").val();
    queryData['reportType'] = $("#reportType").val();
    InviteMonthReport.table.refresh({query: queryData});*/

    //记录当前搜索的月份
    InviteMonthReport.reportYear = $("#reportYear").val();
    InviteMonthReport.reportType = $("#reportType").val();
    initializeEcharts();
};
