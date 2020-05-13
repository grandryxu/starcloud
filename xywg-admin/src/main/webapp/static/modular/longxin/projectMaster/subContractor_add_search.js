
var ProjectMaster = {
    id: "ProjectMasterTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
};


/**
 * 初始化表格的列
 */
ProjectMaster.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '文件名称', field: 'fileName', visible: true, align: 'center', valign: 'middle'},
        {title: '文件描述', field: 'tenderResume', visible: true, align: 'center', valign: 'middle'},
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '流程状态', field: 'tenderContext', visible: true, align: 'center', valign: 'middle',
            formatter: function (data) {
                if (data == "0") {
                    return "审核中";
                } else if(data == "1") {
                    return "审核完成";
                }else{
                    return "已驳回";
                }
            }},
        {title: '项目地点', field: 'projectAddress', visible: true, align: 'center', valign: 'middle'},
        {title: '提交人', field: 'subUserId', visible: true, align: 'center', valign: 'middle'},
        {title: '发布时间', field: 'startTime', visible: true, align: 'center', valign: 'middle'},
        {title: '截止时间', field: 'endTime', visible: true, align: 'center', valign: 'middle'},
        {title: '预计开标时间', field: 'startTime', visible: true, align: 'center', valign: 'middle'},

    ];
};



$(function () {




    var defaultColunms = ProjectMaster.initColumn();
    var table = new BSTable(ProjectMaster.id, "/lx/tendering/getAll?projectId="+$("#projectId").val(), defaultColunms);


    table.setPaginationType("server");
    table.onDblClickRow = ProjectMaster.searchInfo;
    table.onLoadSuccess = ProjectMaster.onLoadSuccess;
    ProjectMaster.table = table.init();

});


















