/**
 * 工资统计明细
 */
var PayrollReportDetail = {
    id: "payrollReportDetailTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    projectCode: null,
    layerIndex: -1,
    deptId: null,
};

/**
 * 初始化表格的列
 */
PayrollReportDetail.initColumn = function () {
    return [
        {field: 'projectCode', checkbox: false, visible: false},
        {title: '工人名称', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '电话号码', field: 'cellPhone', visible: true, align: 'center', valign: 'middle'},
        {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
            return Feng.hiddenIdCard(data);
        }},
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '出勤天数', field: 'countDays', visible: true, align: 'center', valign: 'middle'},
        {title: '应发金额(元)', field: 'payAmount', visible: true, align: 'center', valign: 'middle'},
        {title: '实发金额(元)', field: 'actualAmount', visible: true, align: 'center', valign: 'middle'},
        {title: '剩余金额(元)', field: 'balanceAmount', visible: true, align: 'center', valign: 'middle'},
    ];
};


$(function () {
    //从父页面获取选择的项目编号
    PayrollReportDetail.projectCode = parent.PayrollReport.projectCode;
    //初始化表格
    initializeDataTable();

});


/**
 * 初始化表格
 */
function initializeDataTable() {
    var defaultColunms = PayrollReportDetail.initColumn();
    var table = new BSTable(PayrollReportDetail.id, "/payrollReport/getDetailByProjectCodeAndWorkerInfo", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams({"projectCode":PayrollReportDetail.projectCode});
    //table.onDblClickRow = PayrollReportDetail.onDblClickRow;
    PayrollReportDetail.table = table.init();
}

/**
 * 刷新列表
 */
PayrollReportDetail.search = function () {
    var queryData = {};
    queryData['projectCode'] = PayrollReportDetail.projectCode;
    queryData['workerInfo'] = $("#workerInfo").val();
    PayrollReportDetail.table.refresh({query: queryData});
};