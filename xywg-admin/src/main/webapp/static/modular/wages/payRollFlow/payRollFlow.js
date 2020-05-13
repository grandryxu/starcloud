/**
 * 工资流水管理初始化
 */
var PayRollFlow = {
    id: "PayRollFlowTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PayRollFlow.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
           /* {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},*/
            {title: '工资单编号', field: 'payRollCode', visible: true, align: 'center', valign: 'middle'},
            {title: '项目编号', field: 'projectCode', visible: true, align: 'center', valign: 'middle'},
           /* {title: '', field: 'organizationCode', visible: true, align: 'center', valign: 'middle'},*/
            {title: '证件类型', field: 'idCardType', visible: true, align: 'center', valign: 'middle'},
            {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
                return Feng.hiddenIdCard(data);
            }},
            {title: '流水来源', field: 'type', visible: true, align: 'center', valign: 'middle'},
            {title: '发放时间', field: 'time', visible: true, align: 'center', valign: 'middle'},
            {title: '应发工资', field: 'payAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '已发金额', field: 'actualAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '剩余金额', field: 'balanceAmount', visible: true, align: 'center', valign: 'middle'},
        /* {title: '添加人', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
         {title: '添加时间', field: 'createUser', visible: true, align: 'center', valign: 'middle'},
         {title: '修改时间', field: 'updateDate', visible: true, align: 'center', valign: 'middle'},
         {title: '修改人', field: 'updateUser', visible: true, align: 'center', valign: 'middle'},
         {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
         {title: '是否删除 1：是 0:否', field: 'isDel', visible: true, align: 'center', valign: 'middle'}*/
    ];
};

/**
 * 检查是否选中
 */
PayRollFlow.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PayRollFlow.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加工资流水
 */
PayRollFlow.openAddPayRollFlow = function () {
    var index = layer.open({
        type: 2,
        title: '添加工资流水',
        area: ['900px', '620px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/payRollFlow/payRollFlow_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看工资流水详情
 */
PayRollFlow.openPayRollFlowDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '工资流水详情',
            area: ['800px', '620px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/payRollFlow/payRollFlow_update/' + PayRollFlow.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除工资流水
 */
PayRollFlow.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/payRollFlow/delete", function (data) {
            Feng.success("删除成功!");
            PayRollFlow.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("payRollFlowId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询工资流水列表
 */
PayRollFlow.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    PayRollFlow.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PayRollFlow.initColumn();
    var table = new BSTable(PayRollFlow.id, "/payRollFlow/list", defaultColunms);
    table.setPaginationType("client");
    PayRollFlow.table = table.init();
});
