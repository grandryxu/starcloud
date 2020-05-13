/**
 * 企业信息管理初始化
 */
var InviteReport = {
    id: "InviteReportTable",	//表格id
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
InviteReport.initColumn = function () {
    return [
        {field: '', checkbox: false, visible: false},
        {title: '文件名', field: 'tenderName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目名', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '中标单位', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '发布时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '中标时间', field: 'date', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 查询企业信息列表
 */
InviteReport.search = function () {
    var queryData = {};
    queryData.projectName = $("#projectName").val().trim();
    queryData.startDate = $("#startDate").val().trim();
    queryData.endDate = $("#endDate").val().trim();
    queryData.projectStatus = $("#projectStatus").val().trim();
    InviteReport.table.refresh({query: queryData});
};

$(function () {
    Feng.initStartEndDateTime();
    var defaultColunms = InviteReport.initColumn();
    var table = new BSTable(InviteReport.id, "/lxInviteBid/reportlist", defaultColunms);
    table.setPaginationType("server");
    InviteReport.table = table.init();
});
