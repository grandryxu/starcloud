/**
 * 初始化
 */
var LxOrder = {
    id: "WorkerMasterTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

LxOrder.selectFile = function () {
    $("#excelFile").trigger("click");
};

LxOrder.formParams = function () {
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

LxOrder.history= function(){


    var index = layer.open({
        type: 2,
        title: '工人管理详情',
        area: ['80%', '80%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/lxOrder/historyReg'
    });
    this.layerIndex = index;

}

/**
 * 初始化表格的列
 */
LxOrder.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'order.id', visible: false, align: 'center', valign: 'middle'},
        {title: 'businessid', field: 'business.id', visible: false, align: 'center', valign: 'middle'},
         {title: '公司名称', field: 'business.companyName', visible: true, align: 'center', valign: 'middle'},
         {title: '组织机构代码', field: 'business.organizationCode', visible: true, align: 'center', valign: 'middle'}


    ];
};

/**
 * 检查是否选中
 */
LxOrder.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("只能选择一条");
    }
    else {
        LxOrder.seItem = selected[0];

        console.log(LxOrder.seItem)
        return true;
    }
};

/**
 * 点击添加工人管理
 */
LxOrder.bohui = function () {

    if(this.check()){
        var path = "../lxOrder/shenheReg?orderId=" + LxOrder.seItem.order.id  + "&result=false&bussId=" + LxOrder.seItem.business.id + "&socialCreditNumber=" + LxOrder.seItem.business.socialCreditNumber;
        $.post(path, { },function(data){
            if(data.code!=200){
                Feng.error(data.message);
            }else{
                LxOrder.search();
                Feng.success(data.message);
            }

        })
    }




};

/**
 * 点击添加工人管理
 */
LxOrder.openAddWorkerMaster = function () {

    if(this.check()){
        var path = "../lxOrder/shenheReg?orderId=" + LxOrder.seItem.order.id  + "&result=true&bussId=" + LxOrder.seItem.business.id + "&socialCreditNumber=" + LxOrder.seItem.business.socialCreditNumber;
        $.post(path, { },function(data){
            if(data.code!=200){
                Feng.error(data.message);
            }else{
                LxOrder.search();
                Feng.success(data.message);
            }

        })
    }




};

/**
  */
LxOrder.openWorkerMasterDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '工人管理详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: 'layer-no-title',
            content: Feng.ctxPath + '/workerMaster/workerMaster_update/' + LxOrder.seItem.id+"?pwId="+LxOrder.seItem.pwId
        });
        this.layerIndex = index;
    }
};


/**
 * 双击查看
 */
LxOrder.searchInfo = function (e) {
    // console.log(e)
    var index = layer.open({
        type: 2,
        title: '公司详情信息',
        area: ['80%', '80%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/lxSubContractor/lxSubContractor_view/' + e.business.id
    });
    this.layerIndex = index;
}





/**
 * 查询工人管理列表
 */

LxOrder.search = function () {

    LxOrder.table.refresh({query: LxOrder.formParams()});
};


$(function () {
    Feng.initChosen();
    var defaultColunms = LxOrder.initColumn();
    var table = new BSTable(LxOrder.id, "/lxOrder/getAllReg", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(LxOrder.formParams());
    table.onDblClickRow = LxOrder.searchInfo;//双击事件所对应的方法 要放在init之前
    LxOrder.table = table.init();
});
