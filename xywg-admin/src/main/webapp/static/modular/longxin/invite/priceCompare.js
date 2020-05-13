var myChart = null;
var PriceCompare = {
    datasetSource: [],
    series: []

};

$(function () {
    //初始化统计数据
    initializeEcharts();
});

/**
 * 初始化echarts函数
 */
function initializeEcharts(){
    PriceCompare.datasetSource=[];
    PriceCompare.series=[];
    myChart = echarts.init(document.getElementById('priceCompareEChartsDiv'));
    myChart.showLoading();
    getData();
}

// 异步加载数据
function getData() {
    var url = '/lxInviteBid/priceCompareData';
    $.post(
        url,
        {
            'tenderId':tenderId
        },
        function (data) {
            PriceCompare.datasetSource.push(data.companyList);
            for (var i = 0; i < data.prices.length; i++) {
                PriceCompare.datasetSource.push(data.prices[i]);
                PriceCompare.series.push({type: 'line', smooth: true, seriesLayoutBy: 'row'});
            }
            myChart.hideLoading();
            echartsOption();
        });
}

function echartsOption() {
    // 显示标题，图例和空的坐标轴
    var title = "竞标价格对比";
    myChart.setOption({
        title: {
            text: title
        },
        color: [
            '#2ec7c9','#b6a2de','#5ab1ef','#ffb980','#d87a80','#8d98b3','#e5cf0d','#97b552','#95706d','#dc69aa',
            '#07a2a4','#9a7fd1','#588dd5','#f5994e','#c05050', '#59678c','#c9ab00','#7eb00a','#6f5553','#c14089'
        ],
        tooltip: {
            trigger: 'axis'
        },
        legend: {},
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
        xAxis: {type: 'category'},
        yAxis: {},
        dataset: {
            source: PriceCompare.datasetSource
        },
        series: PriceCompare.series
    });

    myChart.on('click', function (params) {
        priceCompareDetail(params);
    });
}

function  priceCompareDetail(a){
    var index = layer.open({
        type: 2,
        title: '竞标价格详情对比',
        area: ['80%', '80%'], //宽高
        fix: false, //不固定
        maxmin: false,
        content: Feng.ctxPath + '/lxInviteBid/priceCompareDetail?tenderId=' + tenderId + '&priceName=' + a.seriesName
    });
    this.layerIndex = index;
}
