/**
 * 企业工人管理初始化
 */
var ContractorWorker = {
    id: "ContractorWorkerTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ContractorWorker.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '证件类型', field: 'idCardType', visible: true, align: 'center', valign: 'middle'},
            {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
                return Feng.hiddenIdCard(data);
            }},
            {title: '工人名称', field: 'workName', visible: true, align: 'center', valign: 'middle'},
            {title: '所属企业组织机构代码', field: 'organizationCode', visible: true, align: 'center', valign: 'middle'},
            {title: '企业名称', field: 'contractorName', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ContractorWorker.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ContractorWorker.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加企业工人
 */
ContractorWorker.openAddContractorWorker = function () {
    var index = layer.open({
        type: 2,
        title: '添加企业工人',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/contractorWorker/contractorWorker_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看企业工人详情
 */
ContractorWorker.openContractorWorkerDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '企业工人详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/contractorWorker/contractorWorker_update/' + ContractorWorker.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除企业工人
 */
ContractorWorker.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/contractorWorker/delete", function (data) {
            Feng.success("删除成功!");
            ContractorWorker.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("contractorWorkerId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询企业工人列表
 */
ContractorWorker.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ContractorWorker.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ContractorWorker.initColumn();
    var table = new BSTable(ContractorWorker.id, "/contractorWorker/list", defaultColunms);
    table.setPaginationType("client");
    ContractorWorker.table = table.init();
});
