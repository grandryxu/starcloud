/**
 * 工人管理管理初始化
 */
var PersonPosition = {
    id: "PersonPositionTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};


PersonPosition.formParams = function () {
    var queryData = {};
    queryData['key'] = $("#key").val();
    queryData['projectCode'] = $("#projectCode").val();
    return queryData;
}


/**
 * 初始化表格的列
 */
PersonPosition.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '项目', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '证件编号',
            field: 'idCardNumber',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                if (row.idCardType == 1) {
                    //身份证
                    return Feng.hiddenIdCard(value);
                } else {
                    return value;
                }
            }
        },
        {title: '手机号码', field: 'cellPhone', visible: true, align: 'center', valign: 'middle'},
        {title: '设备号', field: 'shImei', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PersonPosition.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("请先选中表格中的一记录！");
    }
    else {
        PersonPosition.seItem = selected[0];
        return true;
    }
};

/**
 * 点击查看人员轨迹
 */
PersonPosition.openPersonTrajectoryDlg = function (e) {
    if(PersonPosition.check()) {
        PersonPosition.searchInfo(PersonPosition.seItem);
    }
}

/**
 * 双击查看
 */
PersonPosition.searchInfo = function (e) {
    var url = Feng.ctxPath + '/position/detail?idCardNumber=' + e.idCardNumber;
    if (e.shImei) {
        url += "&shImei=" + e.shImei;
    }
    var index = layer.open({
        type: 2,
        title: '人员轨迹回放',
        area: ['90%', '90%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: url
    });
    this.layerIndex = index;
}



/**
 * 查询工人管理列表
 */

PersonPosition.search = function () {
    PersonPosition.table.refresh({query: PersonPosition.formParams()});
};


$(function () {
    Feng.initChosen();
    var defaultColunms = PersonPosition.initColumn();
    var table = new BSTable(PersonPosition.id, "/position/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(PersonPosition.formParams());
    table.onDblClickRow = PersonPosition.searchInfo;//双击事件所对应的方法 要放在init之前
    PersonPosition.table = table.init();
});
