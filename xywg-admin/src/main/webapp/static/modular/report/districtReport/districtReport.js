/**
 * 项目区域统计
 */
var myChart = null;
var DistrictReport = {
    id: "districtReportTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptId: null,
    dataX: [],
    dataY: []
};

//
// /**
//  * 点击菜单树时执行的方法
//  */
// DistrictReport.onClickDept = function (e, treeId, treeNode) {
//     DistrictReport.deptId = treeNode.id;
//     initializeEcharts();
// };

$(function () {

    initializeEcharts();
    //初始化部门数数据
    initializeDeptTree();
    //初始化部门树
    var ajax = new $ax(Feng.ctxPath + "/dept/tree", function (data) {
        var treeData = data;
        return $('#tree').treeview({data: treeData,onNodeSelected: function (e, o) {
            DistrictReport.deptId = o.id;
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
    DistrictReport.dataX=[];
    DistrictReport.dataY=[];
    myChart = echarts.init(document.getElementById('districtReportEChartsDiv'));
    myChart.showLoading();
    getData();
}

// 异步加载数据
function getData() {
    var url;
    if (DistrictReport.deptId == null || DistrictReport.deptId == "") {
        url = '/districtReport/list';
    } else {
        url = '/districtReport/list?deptId='+DistrictReport.deptId;
    }
    $.get(url).done(function (data) {
        for (var i = 0; i < data.length; i++) {
            DistrictReport.dataX.push(data[i].birthPlace);
            DistrictReport.dataY.push(data[i].amount);
        }
        myChart.hideLoading();
        echartsOption();
    });
}

function echartsOption() {
    // 显示标题，图例和空的坐标轴
    myChart.setOption({
        title: {
            text: '项目区域统计'
        },
        tooltip: {},
        legend: {
            data: ['项目数量']
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
            data: DistrictReport.dataX
        },
        yAxis: {},
        series: [{
            name: '项目数量',
            type: 'bar',
            barWidth : 30,
            itemStyle: {
                normal: {
                    color: "#C23531"
                }
            },
            data: DistrictReport.dataY
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
DistrictReport.onClickDept = function (e, treeId, treeNode) {
    $("#pName").attr("value", DistrictReport.zTreeInstance.getSelectedVal());
    DistrictReport.deptId = treeNode.id;
    // refresh();
    initializeEcharts();
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "parentDeptMenu" || $(
            event.target).parents("#parentDeptMenu").length > 0)) {
        DistrictReport.hideDeptSelectTree();
    }
}

/**
 * 显示部门选择的树
 *
 * @returns
 */
DistrictReport.showDeptSelectTree = function () {
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
DistrictReport.hideDeptSelectTree = function () {
    $("#parentDeptMenu").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}

/**
 * 初始化部门数数据
 */
function initializeDeptTree() {
    // Feng.initValidator("deptInfoForm", DistrictReport.validateFields);
    var ztree = new $ZTree("parentDeptMenuTree", "/dept/treeSelect");
    ztree.bindOnClick(DistrictReport.onClickDept);
    ztree.init();
    DistrictReport.zTreeInstance = ztree;
};


/**
 * 刷新数据执行的方法
 */
// function refresh() {
//     var queryData = {};
//     queryData['deptId'] = DistrictReport.deptId;
//     DistrictReport.table.refresh({query: queryData});
// }
