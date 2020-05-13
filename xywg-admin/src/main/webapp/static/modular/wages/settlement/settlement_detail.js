
var SettlementDetail = {
    id: "settlementDetail",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SettlementDetail.initColumn = function () {
    return [
            {field: 'selectItem', checkbox: false, visible:false},
            {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
            {title: '证件类型', field: 'idCardType', visible: true, align: 'center', valign: 'middle'},
            {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '工种', field: 'workerType', visible: true, align: 'center', valign: 'middle'},
            {title: '累计应发', field: 'addPayAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '累计已发', field: 'addActualAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '剩余金额', field: 'addBalanceAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '奖励', field: 'rewardAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '惩罚', field: 'punishAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '结算应发', field: 'settlePayAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '结算实发', field: 'settleActualAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '发放状态', field: 'payStatusValue', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 查询结算单列表
 */
SettlementDetail.search = function () {
    var queryData = {};
    queryData['workerNameOrCode'] = $("#workerNameOrCode").val();
    queryData['workerType'] = $("#workerType").val();
    queryData['settlementCode']= $("#settlementCode").val();
    SettlementDetail.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = SettlementDetail.initColumn();
    var table = new BSTable(SettlementDetail.id, "/settlement/getDetailListNoPage", defaultColunms);
    table.pagination = false;
    table.showFooter = true;
    table.setPaginationType("client");
    table.setQueryParams({"settlementCode":$("#settlementCode").val()});
    SettlementDetail.table = table.init();
});