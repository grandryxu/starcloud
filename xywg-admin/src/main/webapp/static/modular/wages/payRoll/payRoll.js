/**
 * 工资单管理初始化
 */
var PayRoll = {
    id: "PayRollTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PayRoll.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目编号', field: 'projectCode', visible: false, align: 'center', valign: 'middle'},
        {title: '班组编号', field: 'teamName', visible: false, align: 'center', valign: 'middle'},
        {title: '应发金额（元）', field: 'teamName', visible: true, align: 'right', valign: 'middle',
            formatter:function(data){
                return data.toFixed(2);
            }},
        {title: '实发金额（元）', field: 'teamName', visible: true, align: 'right', valign: 'middle',
            formatter:function(data){
                return data.toFixed(2);
            }},
        {title: '剩余金额（元）', field: 'teamName', visible: true, align: 'right', valign: 'middle',
            formatter:function(data){
                return data.toFixed(2);
            }},
        {title: '发放类型', field: 'type', visible: true, align: 'center', valign: 'middle'},
        {title: '保存状态', field: 'saveStatus', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'},
        {title: '分包审核人', field: 'constructValid', visible: false, align: 'center', valign: 'middle'},
        {title: '总包审核人', field: 'contractValid', visible: false, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
PayRoll.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PayRoll.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加工资单
 */
PayRoll.openAddPayRoll = function () {
    var index = layer.open({
        type: 2,
        title: '添加工资单',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin:"layer-no-title",
        content: Feng.ctxPath + '/payRoll/payRoll_add'
    });
    layer.full(index);
    this.layerIndex = index;
};

/**
 * 打开查看工资单详情
 */
PayRoll.openPayRollDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '工资单详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin:"layer-no-title",
            content: Feng.ctxPath + '/payRoll/payRoll_update/' + PayRoll.seItem.id
        });
        layer.full(index);
        this.layerIndex = index;
    }
};

/**
 * 删除工资单
 */
PayRoll.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/payRoll/delete", function (data) {
            Feng.success("删除成功!");
            PayRoll.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("payRollId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询工资单列表
 */
PayRoll.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    PayRoll.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PayRoll.initColumn();
    var table = new BSTable(PayRoll.id, "/payRoll/list", defaultColunms);
    table.setPaginationType("client");
    PayRoll.table = table.init();
});
