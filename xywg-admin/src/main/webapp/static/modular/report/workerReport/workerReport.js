/**
 * 人员统计
 */
var WorkerReport = {
    id: "WorkerReportTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptId: null
};

/**
 * 查询参数
 */
WorkerReport.formParams = function () {
    var queryData = {};
    queryData['workerName'] = $("#workerName").val();
    queryData['idCardNumber'] = $("#idCardNumber").val();
    queryData['cellPhone'] = $("#cellPhone").val();
    queryData['deptId'] = WorkerReport.deptId;
    return queryData;
    console.log(queryData);
}

/**
 * 查询不良记录列表
 */
WorkerReport.search = function () {
    var queryData = {};
    queryData['workerName'] = $("#workerName").val();
    queryData['idCardNumber'] = $("#idCardNumber").val();
    queryData['cellPhone'] = $("#cellPhone").val();
    queryData['deptId'] = WorkerReport.deptId;
    WorkerReport.table.refresh({query: queryData});
    	
   
};

/**
 * 初始化表格的列
 */
WorkerReport.initColumn = function () {
    return [
    	{field: 'selectItem', radio: true},
            {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
            return Feng.hiddenIdCard(data);
        }},
            {title: '联系方式', field: 'cellPhone', visible: true, align: 'center', valign: 'middle'},
            {title: '所在项目数', field: 'projectCount', visible: true, align: 'center', valign: 'middle'},
            {title: '不良记录数', field: 'badRecordsCount', visible: true, align: 'center', valign: 'middle'},
            {title: '奖励记录数', field: 'goodRecordsCount', visible: true, align: 'center', valign: 'middle'},
            {title: '黑名单数', field: 'blackListCount', visible: true, align: 'center', valign: 'middle'},
            {title: '应发工资数', field: 'payAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '已发工资数', field: 'actualAmount', visible: true, align: 'center', valign: 'middle'}
    ];
};



$(function () {
	    var defaultColunms = WorkerReport.initColumn();
	    var table = new BSTable(WorkerReport.id, "/iworkerReport/list", defaultColunms);
	    table.setPaginationType("server");
	    table.setQueryParams(WorkerReport.formParams());
	    WorkerReport.table = table.init();
	    
    var ajax = new $ax(Feng.ctxPath + "/dept/tree", function (data) {
        var treeData = data;
        return $('#tree').treeview({data: treeData,onNodeSelected: function (e, o) {
        	WorkerReport.deptId = o.id;
        	 var queryData = {};
        	    queryData['workerName'] = $("#workerName").val();
        	    queryData['idCardNumber'] = $("#idCardNumber").val();
        	    queryData['cellPhone'] = $("#cellPhone").val();
        	    queryData['deptId'] = WorkerReport.deptId;
        	WorkerReport.table.refresh({query: queryData});
        }});
    }, function (data) {
    });
    ajax.start();
});

WorkerReport.reset =  function () {
    document.getElementById("searchForm").reset();
    if($('#searchForm').find('.dept-tree')){
        $('.dept-tree').val("");
        WorkerReport.deptId = $("#deptId").val();
    }
}

/**
 * 点击部门ztree列表的选项时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
WorkerReport.onClickDept = function (e, treeId, treeNode) {
    $("#pName").val(WorkerReport.zTreeInstance.getSelectedVal());
    WorkerReport.deptId = treeNode.id;
    refresh();

}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "parentDeptMenu" || $(
            event.target).parents("#parentDeptMenu").length > 0)) {
        WorkerReport.hideDeptSelectTree();
    }
}

/**
 * 显示部门选择的树
 *
 * @returns
 */
WorkerReport.showDeptSelectTree = function () {
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
WorkerReport.hideDeptSelectTree = function () {
    $("#parentDeptMenu").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}

/**
 * 初始化部门数数据
 */
function initializeDeptTree() {
    // Feng.initValidator("deptInfoForm", WorkerReport.validateFields);
    var ztree = new $ZTree("parentDeptMenuTree", "/dept/treeSelect");
    ztree.bindOnClick(WorkerReport.onClickDept);
    ztree.init();
    WorkerReport.zTreeInstance = ztree;
};


/**
 * 刷新数据执行的方法
 */
function refresh() {
    var queryData = {};
    queryData['deptId'] = WorkerReport.deptId;
    WorkerReport.table.refresh({query: queryData});
}

$(function () {
    //初始化部门树
    initializeDeptTree();
});


/**
 * 导出工人管理
 */
WorkerReport.download = function () {
    window.location.href = Feng.ctxPath + "/iworkerReport/download?"
        + "&workerName=" + $("#workerName").val()
        + "&idCardNumber=" + $("#idCardNumber").val()
        + "&cellPhone=" + $("#cellPhone").val()
        + "&deptId=" + WorkerReport.deptId;
};
