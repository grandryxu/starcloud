/**
 * 进退场管理初始化
 */
var EntryExitHistory = {
    id: "EntryExitHistoryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
EntryExitHistory.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '项目编号', field: 'projectCode', visible: true, align: 'center', valign: 'middle'},
            {title: '所属企业组织机构代码', field: 'organizationCode', visible: true, align: 'center', valign: 'middle'},
            {title: '证件类型 参见人员证件类型字典表', field: 'idCardType', visible: true, align: 'center', valign: 'middle'},
        {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
            return Feng.hiddenIdCard(data);
        }},
            {title: '时间', field: 'date', visible: true, align: 'center', valign: 'middle'},
            {title: '类型', field: 'type', visible: true, align: 'center', valign: 'middle'},
            {title: '是否删除 1：是 0:否', field: 'isDel', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
EntryExitHistory.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        EntryExitHistory.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加进退场
 */
EntryExitHistory.openAddEntryExitHistory = function () {
    var index = layer.open({
        type: 2,
        title: '添加进退场',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/entryExitHistory/entryExitHistory_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看进退场详情
 */
EntryExitHistory.openEntryExitHistoryDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '进退场详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/entryExitHistory/entryExitHistory_update/' + EntryExitHistory.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除进退场
 */
EntryExitHistory.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/entryExitHistory/delete", function (data) {
            Feng.success("删除成功!");
            EntryExitHistory.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("entryExitHistoryId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询进退场列表
 */
EntryExitHistory.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    EntryExitHistory.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = EntryExitHistory.initColumn();
    var table = new BSTable(EntryExitHistory.id, "/entryExitHistory/list", defaultColunms);
    table.setPaginationType("client");
    EntryExitHistory.table = table.init();
});
