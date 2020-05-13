/**
 * 计工单详情管理初始化
 */
var AccountDetail = {
    id: "AccountDetailTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};


/**
 * 初始化表格的列
 */
AccountDetail.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '计工id', field: 'accountId', visible: false, align: 'center', valign: 'middle'},
            {title: '身份证类型', field: 'idCardType', visible: false, align: 'center', valign: 'middle'},
            {title: '身份证号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '考勤天', field: 'recordDay', visible: true, align: 'center', valign: 'middle'},
            {title: '计工方式', field: 'type', visible: true, align: 'center', valign: 'middle'},
            {title: '单位', field: 'unit', visible: true, align: 'center', valign: 'middle'},
            {title: '单价', field: 'price', visible: true, align: 'center', valign: 'middle',
                formatter:function(data){
                    return data.toFixed(2);
                }},
            {title: '数量', field: 'number', visible: true, align: 'center', valign: 'middle'},
            {title: '基本工资', field: 'totalAmount', visible: true, align: 'center', valign: 'middle',
                formatter:function(data){
                    return data.toFixed(2);
                }},
            {title: '奖励金额', field: 'rewardAmount', visible: true, align: 'center', valign: 'middle',
                formatter:function(data){
                    return data.toFixed(2);
                }},
            {title: '惩罚金额', field: 'punishAmount', visible: true, align: 'center', valign: 'middle',
                formatter:function(data){
                    return data.toFixed(2);
                }},
            {title: '实际应发金额', field: 'payAmuont', visible: true, align: 'center', valign: 'middle',
                formatter:function(data){
                    return data.toFixed(2);
                }},
            {title: '工单开始时间', field: 'startDate', visible: true, align: 'center', valign: 'middle'},
            {title: '工单结束时间', field: 'endDate', visible: true, align: 'center', valign: 'middle'},
            {title: '是否已签字 1是 0否', field: 'isSign', visible: true, align: 'center', valign: 'middle'},
            {title: '确认签字', field: 'sign', visible: false, align: 'center', valign: 'middle'},
            {title: '签字图片缩略图', field: 'iconSign', visible: false, align: 'center', valign: 'middle'},
            {title: '拍照', field: 'photo', visible: false, align: 'center', valign: 'middle'},
            {title: '图像压缩', field: 'iconPhoto', visible: false, align: 'center', valign: 'middle'},
            {title: '确认签字时间', field: 'signDate', visible: false, align: 'center', valign: 'middle'},

    ];
};

/**
 * 检查是否选中
 */
AccountDetail.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AccountDetail.seItem = selected[0];
        return true;
    }
};



/**
 * 点击添加计工单详情
 */
AccountDetail.openAddAccountDetail = function () {
    var index = layer.open({
        type: 2,
        title: '添加计工单详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/accountDetail/accountDetail_add'
    });
    layer.full(index);
    this.layerIndex = index;
};

/**
 * 打开查看计工单详情详情
 */
AccountDetail.openAccountDetailDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '计工单详情详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/accountDetail/accountDetail_update/' + AccountDetail.seItem.id
        });
        layer.full(index);
        this.layerIndex = index;
    }
};

/**
 * 删除计工单详情
 */
AccountDetail.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/accountDetail/delete", function (data) {
            Feng.success("删除成功!");
            AccountDetail.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("accountDetailId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询计工单详情列表
 */
AccountDetail.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    AccountDetail.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AccountDetail.initColumn();
    var table = new BSTable(AccountDetail.id, "/accountDetail/list", defaultColunms);
    table.setPaginationType("client");
    AccountDetail.table = table.init();

});
