/**
 * 工人奖励记录信息管理初始化
 */
var WorkerGoodRecords = {
    id: "WorkerGoodRecordsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
WorkerGoodRecords.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '证件类型 参见人员证件类型字典表', field: 'idCardType', visible: true, align: 'center', valign: 'middle'},
            {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
                return Feng.hiddenIdCard(data);
            }},
            {title: '项目编号', field: 'projectCode', visible: true, align: 'center', valign: 'middle'},
            {title: '工人在该项目中所属企业组织机构代码', field: 'organizationCode', visible: true, align: 'center', valign: 'middle'},
            {title: '奖励类型  参见奖励类型字典', field: 'goodRecordTypeCode', visible: true, align: 'center', valign: 'middle'},
            {title: '奖励级别  58=国家级；59=省部级；60=地市级；61=企业级', field: 'goodRecordLevelType', visible: true, align: 'center', valign: 'middle'},
            {title: '奖励时间', field: 'occurrenceDate', visible: true, align: 'center', valign: 'middle'},
            {title: '奖项说明', field: 'details', visible: true, align: 'center', valign: 'middle'},
            {title: '新增时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
            {title: '添加人', field: 'createUser', visible: true, align: 'center', valign: 'middle'},
            {title: '修改时间', field: 'updateDate', visible: true, align: 'center', valign: 'middle'},
            {title: '修改人', field: 'updateUser', visible: true, align: 'center', valign: 'middle'},
            {title: '是否删除 1：是 0:否', field: 'isDel', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
WorkerGoodRecords.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        WorkerGoodRecords.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加工人奖励记录信息
 */
WorkerGoodRecords.openAddWorkerGoodRecords = function () {
    var index = layer.open({
        type: 2,
        title: '添加工人奖励记录信息',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/workerGoodRecords/workerGoodRecords_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看工人奖励记录信息详情
 */
WorkerGoodRecords.openWorkerGoodRecordsDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '工人奖励记录信息详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/workerGoodRecords/workerGoodRecords_update/' + WorkerGoodRecords.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除工人奖励记录信息
 */
WorkerGoodRecords.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/workerGoodRecords/delete", function (data) {
            Feng.success("删除成功!");
            WorkerGoodRecords.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("workerGoodRecordsId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询工人奖励记录信息列表
 */
WorkerGoodRecords.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    WorkerGoodRecords.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = WorkerGoodRecords.initColumn();
    var table = new BSTable(WorkerGoodRecords.id, "/workerGoodRecords/list", defaultColunms);
    table.setPaginationType("client");
    WorkerGoodRecords.table = table.init();
});
