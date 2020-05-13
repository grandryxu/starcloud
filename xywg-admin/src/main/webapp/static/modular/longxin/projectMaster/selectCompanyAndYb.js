
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
        {title: '公司名称', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '营业地址', field: 'address', visible: true, align: 'center', valign: 'middle'},
        {title: '组织机构编号', field: 'organizationCode', visible: true, align: 'center', valign: 'middle'},

    ];
};



$(function () {


    var zbId = $("#tenderId").val()



    var defaultColunms = ProjectMaster.initColumn();
    var table = new BSTable(ProjectMaster.id, "/lxProject/companyListAndYb/"+zbId, defaultColunms);


    table.setPaginationType("server");
    table.onDblClickRow = ProjectMaster.searchInfo;
    table.onLoadSuccess = ProjectMaster.onLoadSuccess;
    ProjectMaster.table = table.init();

});


















