/**
 * 工资统计
 */
var PayrollReport = {
    id: "payrollReportDiv",//表格id
    seItem: null,		//选中的条目
    table: null,
    projectCode: "",
    layerIndex: -1,
    deptId: null,
};

/**
 * 初始化表格的列
 */
PayrollReport.initColumn = function () {
    return [
        {field: 'projectCode', checkbox: false, visible: false},
        {title: '项目编号', field: 'projectCode', visible: true, align: 'center', valign: 'middle'},
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目活动类型', field: 'projectActivityTypeVal', visible: true, align: 'center', valign: 'middle'},
        {title: '应发金额(元)', field: 'payAmount', visible: true, align: 'center', valign: 'middle'},
        {title: '实发金额(元)', field: 'actualAmount', visible: true, align: 'center', valign: 'middle'},
        {title: '剩余金额(元)', field: 'balanceAmount', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 导出
 * @param e
 */
PayrollReport.export = function (e) {
    window.location.href = Feng.ctxPath + "/payrollReport/export?"
        + "deptId=" + PayrollReport.deptId;
}

//双击查看事件
PayrollReport.onDblClickRow = function (e) {
    PayrollReport.projectCode = e.projectCode;
    var index = layer.open({
        type: 2,
        title: '工资单详情',
        area: ['80%', '70%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/payrollReport/detail?projectCode=' + e.projectCode
    });
    this.layerIndex = index;
}


//
// /**
//  * 点击菜单树时执行的方法
//  */
// PayrollReport.onClickDept = function (e, treeId, treeNode) {
//     PayrollReport.deptId = treeNode.id;
//     var queryData = {};
//     queryData['deptId'] = treeNode.id;
//     PayrollReport.table.refresh({query: queryData});
//
// };
//


$(function () {
    //初始化部门树
    initializeDeptTree();
    //初始化表格
    initializeDataTable();
});


/**
 * 初始化部门树
 */

/*function initializeDeptTree() {
    var ajax = new $ax(Feng.ctxPath + "/dept/tree", function (data) {
        var treeData = data;
        return $('#tree').treeview({
            //点击执行的回调
            data: treeData, onNodeSelected: function (e, o) {
                PayrollReport.deptId = o.id;
                var queryData = {};
                queryData['deptId'] = o.id;
                PayrollReport.table.refresh({query: queryData});
            }
        });
    }, function (data) {
    });
    ajax.start();
}*/

/**
 * 初始化表格
 */
function initializeDataTable() {
    var defaultColunms = PayrollReport.initColumn();
    var table = new BSTable(PayrollReport.id, "/payrollReport/list", defaultColunms);
    // table.pagination = false;
    // table.showFooter = true;
    table.setPaginationType("server");
    table.setQueryParams();
    table.onDblClickRow = PayrollReport.onDblClickRow;
    PayrollReport.table = table.init();
}

/**
 * 点击部门ztree列表的选项时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
PayrollReport.onClickDept = function (e, treeId, treeNode) {
    $("#pName").attr("value", PayrollReport.zTreeInstance.getSelectedVal());
    PayrollReport.deptId = treeNode.id;
    refresh();

}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "parentDeptMenu" || $(
            event.target).parents("#parentDeptMenu").length > 0)) {
        PayrollReport.hideDeptSelectTree();
    }
}

/**
 * 显示部门选择的树
 *
 * @returns
 */
PayrollReport.showDeptSelectTree = function () {
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
PayrollReport.hideDeptSelectTree = function () {
    $("#parentDeptMenu").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}

/**
 * 初始化部门数数据
 */
function initializeDeptTree() {
    // Feng.initValidator("deptInfoForm", PayrollReport.validateFields);
    var ztree = new $ZTree("parentDeptMenuTree", "/dept/treeSelect");
    ztree.bindOnClick(PayrollReport.onClickDept);
    ztree.init();
    PayrollReport.zTreeInstance = ztree;
};


/**
 * 刷新数据执行的方法
 */
function refresh() {
    var queryData = {};
    queryData['deptId'] = PayrollReport.deptId;
    PayrollReport.table.refresh({query: queryData});
}