/**
 * 管理初始化
 */
var WorkKind = {
    id: "WorkKindTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
WorkKind.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '所属企业', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
            {title: '工种名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '工时薪酬', field: 'amount', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
WorkKind.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        WorkKind.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
WorkKind.openAddWorkKind = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/workKind/workKind_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
WorkKind.openWorkKindDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/workKind/workKind_update/' + WorkKind.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
WorkKind.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/workKind/delete", function (data) {
            Feng.success("删除成功!");
            WorkKind.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("workKindId",this.seItem.id);
        ajax.start();
    }
};
/**
 * 查询参数
 */
WorkKind.formParams = function () {
    var queryData = {};
    queryData['companyName'] = $("#companyName").val();
    return queryData;
}

/**
 * 查询列表
 */
WorkKind.search = function () {
    var queryData = {};
    queryData['companyName'] = $("#companyName").val();
    WorkKind.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = WorkKind.initColumn();
    var table = new BSTable(WorkKind.id, "/workKind/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(WorkKind.formParams());
    WorkKind.table = table.init();
});
