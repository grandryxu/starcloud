/**
 * 工种分布统计
 */
var myChart = null;
var WorkKindReport = {
    id: "workKindReportTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptId: null,
    dataX: [],
    dataY: []
};


/**
 * 点击菜单树时执行的方法
 */
/*WorkKindReport.onClickDept = function (e, treeId, treeNode) {
    WorkKindReport.deptId = treeNode.id;
    initializeEcharts();
};
*/
$(function () {
    //初始化部门树
    initializeEcharts();


    var ajax = new $ax(Feng.ctxPath + "/dept/tree", function (data) {
        var treeData = data;
        return $('#tree').treeview({data: treeData,onNodeSelected: function (e, o) {
                WorkKindReport.deptId = o.id;
            initializeEcharts();
        }});
    }, function (data) {
    });
    ajax.start();
});

/**
 * 初始化echarts函数
 */
function initializeEcharts(){
    WorkKindReport.dataX=[];
    WorkKindReport.dataY=[];
    myChart = echarts.init(document.getElementById('workKindReportEChartsDiv'));
    myChart.showLoading();
    getData();
}

// 异步加载数据
function getData() {
    var url;
    if (WorkKindReport.deptId == null || WorkKindReport.deptId == "") {
        url = '/workKindReport/list';
    } else {
        url = '/workKindReport/list?deptId='+WorkKindReport.deptId;
    }
    $.get(url).done(function (data) {
        for (var i = 0; i < data.length; i++) {
            WorkKindReport.dataX.push(data[i].workKind);
            WorkKindReport.dataY.push(data[i].count);
        }
        myChart.hideLoading();
        echartsOption();
    });
}

function echartsOption() {
    // 显示标题，图例和空的坐标轴
    myChart.setOption({
        title: {
            text: '工种分布'
        },
        tooltip: {},
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
        legend: {
            data: ['人数']
        },
        xAxis: {
            axisLabel: {
                //这个是倾斜角度，也是考虑到文字过多的时候，方式覆盖采用倾斜
                rotate: 30,
                //这里是考虑到x轴文件过多的时候设置的，如果文字太多，默认是间隔显示，设置为0，标示全部显示，当然，如果x轴都不显示，那也就没有意义了
                //interval :0
            },
            max:20,
            data: WorkKindReport.dataX
        },
        series: [{
            barCategoryGap:'20%'
        }],
        dataZoom: [{
            type: 'slider',
            show: true,
            xAxisIndex: [0],
            left: '9%',
            bottom: -5,
            height: 20,//组件高度
            start: 0,
            end: 90, //初始化滚动条
            handleSize: 0,//滑动条的 左右2个滑动条的大小
            showDataShadow: false,//是否显示数据阴影 默认auto
            showDetail: false//即拖拽时候是否显示详细数值信息 默认true
        }],
        yAxis: {},
        series: [{
            name: '人数',
            type: 'bar',
            itemStyle: {
                normal: {
                    color: "#C23531"
                }
            },
            barWidth : 30,//柱图宽度
            data: WorkKindReport.dataY
        }]
    });
}

/**
 * 点击部门ztree列表的选项时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
WorkKindReport.onClickDept = function (e, treeId, treeNode) {
    $("#pName").attr("value", WorkKindReport.zTreeInstance.getSelectedVal());
    WorkKindReport.deptId = treeNode.id;
    // refresh();
    initializeEcharts();

}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "parentDeptMenu" || $(
            event.target).parents("#parentDeptMenu").length > 0)) {
        WorkKindReport.hideDeptSelectTree();
    }
}

/**
 * 显示部门选择的树
 *
 * @returns
 */
WorkKindReport.showDeptSelectTree = function () {
    var pName = $("#pName");
    var pNameOffset = $("#pName").offset();
    $("#parentDeptMenu").css({
        left: (pNameOffset.left - 5) + "px",
        top: pNameOffset.top + pName.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}

/**
 * 隐藏部门选择的树
 */
WorkKindReport.hideDeptSelectTree = function () {
    $("#parentDeptMenu").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}

/**
 * 初始化部门数数据
 */
function initializeDeptTree() {
    // Feng.initValidator("deptInfoForm", WorkKindReport.validateFields);
    var ztree = new $ZTree("parentDeptMenuTree", "/dept/treeSelect");
    ztree.bindOnClick(WorkKindReport.onClickDept);
    ztree.init();
    WorkKindReport.zTreeInstance = ztree;
};


/**
 * 刷新数据执行的方法
 */
// function refresh() {
//     var queryData = {};
//     queryData['deptId'] = WorkKindReport.deptId;
//     WorkKindReport.table.refresh({query: queryData});
// }

$(function () {
    //初始化部门树
    initializeDeptTree();
});
