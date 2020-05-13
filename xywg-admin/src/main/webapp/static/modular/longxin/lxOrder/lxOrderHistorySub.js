/**
 * 初始化
 */
var LxOrderHis = {
    id: "WorkerMasterTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

LxOrderHis.selectFile = function () {
    $("#excelFile").trigger("click");
};

LxOrderHis.formParams = function () {
    var queryData = {};
    queryData['key'] = $("#key").val();
    queryData['workTypeCode'] = $("#workTypeCode").val();
    queryData['birthPlaceCode'] = $("#birthPlaceCode").val();
    queryData['gender'] = $("#gender").val();
    queryData['cultureLevelType'] = $("#cultureLevelType").val();
    queryData['age'] = $("#age").val();
    queryData['isDel'] = $("#isDel").val();
    return queryData;
}

/**
 * 初始化表格的列
 */
LxOrderHis.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'business.id', visible: false, align: 'center', valign: 'middle'},
        {title: '项目名称', field: 'business.projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '招标信息', field: 'business.tenderName', visible: true, align: 'center', valign: 'middle'},
        {title: '中标公司', field: 'business.companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '审核状态', field: 'business.flowStatus', visible: true, align: 'center', valign: 'middle',
            formatter:function(data){
                if(data == '1'){
                    return '审核中';
                }else if(data == '2'){
                    return '审核通过';
                }else if(data == '3'){
                    return '审核驳回';
                }
            }}
    ];
};

/**
 * 检查是否选中
 */
LxOrderHis.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("只能选择一条");
    }
    else {
        LxOrderHis.seItem = selected[0];
        return true;
    }
};


/**
  */
LxOrderHis.openWorkerMasterDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '工人管理详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: 'layer-no-title',
            content: Feng.ctxPath + '/workerMaster/workerMaster_update/' + LxOrderHis.seItem.id+"?pwId="+LxOrderHis.seItem.pwId
        });
        this.layerIndex = index;
    }
};


/**
 * 双击查看
 */
LxOrderHis.searchInfo = function (e) {
    // var index = layer.open({
    //     type: 2,
    //     title: '工人详情信息',
    //     area: ['100%', '100%'], //宽高
    //     fix: false, //不固定
    //     maxmin: true,
    //     skin: 'layer-no-title',
    //     content: Feng.ctxPath + '/lx/tendering/view?id=' + e.business.id
    // });
    // this.layerIndex = index;
}





/**
 * 查询工人管理列表
 */

LxOrderHis.search = function () {

    LxOrderHis.table.refresh({query: LxOrderHis.formParams()});
};


$(function () {
    Feng.initChosen();
    var defaultColunms = LxOrderHis.initColumn();
    var table = new BSTable(LxOrderHis.id, "/lxOrder/getAllHistorySub", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(LxOrderHis.formParams());
    table.onDblClickRow = LxOrderHis.searchInfo;//双击事件所对应的方法 要放在init之前
    LxOrderHis.table = table.init();
});
