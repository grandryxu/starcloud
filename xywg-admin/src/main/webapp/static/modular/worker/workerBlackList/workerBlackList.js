/**
 * 工人黑名单信息管理初始化
 */
var WorkerBlackList = {
    id: "WorkerBlackListTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
WorkerBlackList.initColumn = function () {
    return [
            {field: 'selectItem', radio: true},
            {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
            {title: '证件类型', field: 'idCardTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
            return Feng.hiddenIdCard(data);
        }},
            {title: '项目编号', field: 'projectCode', visible: true, align: 'center', valign: 'middle'},
            {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
            {title: '承包企业名称', field: 'contractorOrgName', visible: true, align: 'center', valign: 'middle'},
            {title: '承包方组织机构代码', field: 'contractorOrgCode', visible: true, align: 'center', valign: 'middle'},
            {title: '参建企业名称', field: 'organizationName', visible: true, align: 'center', valign: 'middle'},
            {title: '参建企业组织机构代码', field: 'organizationCode', visible: true, align: 'center', valign: 'middle'},
            {title: '班组编号 ', field: 'teamSysNo', visible: true, align: 'center', valign: 'middle'},
            {title: '班组名称', field: 'teamName', visible: true, align: 'center', valign: 'middle'},
            {title: '有效期起', field: 'startTime', visible: true, align: 'center', valign: 'middle'},
            {title: '有效期止', field: 'endTime', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
WorkerBlackList.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        WorkerBlackList.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加工人黑名单信息
 */
WorkerBlackList.openAddWorkerBlackList = function () {
    var index = layer.open({
        type: 2,
        title: '添加工人黑名单信息',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/workerBlackList/workerBlackList_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看工人黑名单信息详情
 */
WorkerBlackList.openWorkerBlackListDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '工人黑名单信息详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/workerBlackList/workerBlackList_update/' + WorkerBlackList.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除工人黑名单信息
 */
WorkerBlackList.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/workerBlackList/delete", function (data) {
            Feng.success("删除成功!");
            WorkerBlackList.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("workerBlackListId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询工人黑名单信息列表
 */
WorkerBlackList.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    WorkerBlackList.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = WorkerBlackList.initColumn();
    var table = new BSTable(WorkerBlackList.id, "/workerBlackList/list", defaultColunms);
    table.setPaginationType("server");
    WorkerBlackList.table = table.init();
    Feng.initStartEndDate();
});
