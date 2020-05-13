/**
 * 人员进退场统计
 */
var PersonJoinReport = {
    id: "personJoinReportTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptId: null
};


/**
 * 初始化表格的列
 */
PersonJoinReport.initColumn = function () {
    return [
        {field: 'selectItem', radio: true, visible: false},
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '承包单位名称', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目活动类型', field: 'projectType', visible: true, align: 'center', valign: 'middle'},
        {title: '进场工人数', field: 'joinCount', visible: true, align: 'center', valign: 'middle'},
        {title: '退场工人数', field: 'outCount', visible: true, align: 'center', valign: 'middle'},
        {title: '累计进场工人数', field: 'totalCount', visible: true, align: 'center', valign: 'middle'},
        {title: '在场人数', field: 'nowCount', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 查询参数
 */
PersonJoinReport.formParams = function () {
    var queryData = {};

    queryData['deptId'] = PersonJoinReport.deptId;
    return queryData;
    console.log(queryData);
}

$(function () {
    var defaultColunms = PersonJoinReport.initColumn();
    var table = new BSTable(PersonJoinReport.id, "/personJoinReport/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(PersonJoinReport.formParams());
    PersonJoinReport.table = table.init();
    var ajax = new $ax(Feng.ctxPath + "/dept/tree", function (data) {
        var treeData = data;
        return $('#tree').treeview({
            data: treeData, onNodeSelected: function (e, o) {
                PersonJoinReport.deptId = o.id;
                var queryData = {};
                queryData['deptId'] = PersonJoinReport.deptId;
                PersonJoinReport.table.refresh({query: queryData});
            }
        });
    }, function (data) {
    });
    ajax.start();
});

/**
 * 导出
 */
PersonJoinReport.download = function () {

    window.location.href = Feng.ctxPath + "/personJoinReport/download?"
        + "deptId=" + PersonJoinReport.deptId;

};

/**
 * 点击部门ztree列表的选项时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
PersonJoinReport.onClickDept = function (e, treeId, treeNode) {
    $("#pName").attr("value", PersonJoinReport.zTreeInstance.getSelectedVal());
    PersonJoinReport.deptId = treeNode.id;
    refresh();

}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "parentDeptMenu" || $(
            event.target).parents("#parentDeptMenu").length > 0)) {
        PersonJoinReport.hideDeptSelectTree();
    }
}

/**
 * 显示部门选择的树
 *
 * @returns
 */
PersonJoinReport.showDeptSelectTree = function () {
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
PersonJoinReport.hideDeptSelectTree = function () {
    $("#parentDeptMenu").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}

/**
 * 初始化部门数数据
 */
function initializeDeptTree() {
    // Feng.initValidator("deptInfoForm", PersonJoinReport.validateFields);
    var ztree = new $ZTree("parentDeptMenuTree", "/dept/treeSelect");
    ztree.bindOnClick(PersonJoinReport.onClickDept);
    ztree.init();
    PersonJoinReport.zTreeInstance = ztree;
};


/**
 * 刷新数据执行的方法
 */
function refresh() {
    var queryData = {};
    queryData['deptId'] = PersonJoinReport.deptId;
    PersonJoinReport.table.refresh({query: queryData});
}

$(function () {
    //初始化部门树
    initializeDeptTree();
});

