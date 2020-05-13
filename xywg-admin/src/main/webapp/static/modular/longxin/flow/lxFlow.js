/**
 * 初始化
 */
var lxFlow = {
    id: "lxFlowTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

lxFlow.selectFile = function () {
    $("#excelFile").trigger("click");
};

lxFlow.formParams = function () {
    var queryData = {};
    // queryData['key'] = $("#key").val();
    // queryData['workTypeCode'] = $("#workTypeCode").val();
    // queryData['birthPlaceCode'] = $("#birthPlaceCode").val();
    // queryData['gender'] = $("#gender").val();
    // queryData['cultureLevelType'] = $("#cultureLevelType").val();
    // queryData['age'] = $("#age").val();
    // queryData['isDel'] = $("#isDel").val();
    return queryData;
}

lxFlow.history= function(){


    var index = layer.open({
        type: 2,
        title: '工人管理详情',
        area: ['80%', '80%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/lxFlow/history'
    });
    this.layerIndex = index;

}

/**
 * 初始化表格的列
 */
lxFlow.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
         {title: '流程名称', field: 'displayName', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
lxFlow.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("只能选择一条");
    }
    else {
        lxFlow.seItem = selected[0];


        return true;
    }
};

/**
 * 点击添加工人管理
 */
lxFlow.bohui = function () {

    if(this.check()){
        var path = "../lxFlow/shenhe?orderId=" + lxFlow.seItem.order.id  + "&result=false&bussId=" + lxFlow.seItem.business.id;
        $.post(path, { },function(data){
            if(data.code!=200){
                Feng.error(data.message);
            }else{
                lxFlow.search();
                Feng.success(data.message);
            }

        })
    }
};

/**
 * 点击添加工人管理
 */
lxFlow.openAddWorkerMaster = function () {

    if(this.check()){
        var path = "../lxFlow/shenhe?orderId=" + lxFlow.seItem.order.id  + "&result=true&bussId=" + lxFlow.seItem.business.id;
        $.post(path, { },function(data){
            if(data.code!=200){
                Feng.error(data.message);
            }else{
                lxFlow.search();
                Feng.success(data.message);
            }
        })
    }
};

/**
  */
lxFlow.add = function () {
        var index = layer.open({
            type: 2,
            title: '新增流程',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: 'layer-no-title',
            content:   '/processDesign/design'
        });
        this.layerIndex = index;
};


/**
 * 双击查看
 */
lxFlow.searchInfo = function (e) {

    var index = layer.open({
        type: 2,
        title: '工人详情信息',
        area: ['80%', '80%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: "/processDesign/edit?id="+ e.id +"&dName=" +e.displayName
    });
    lxFlow.layerIndex = index;

}

/**
 * 双击查看
 */
lxFlow.delete = function (e) {


    if(this.check()){
        layer.confirm('确认删除？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            var path = "../processDesign/del?id=" +  lxFlow.seItem.id;
            $.post(path, { },function(data){
                layer.close(index);

                    lxFlow.search();
                    Feng.success("删除成功！");

            })
        });

    }
}







/**
 * 查询工人管理列表
 */

lxFlow.search = function () {

    lxFlow.table.refresh({query: lxFlow.formParams()});
};


$(function () {
    Feng.initChosen();
    var defaultColunms = lxFlow.initColumn();
    var table = new BSTable(lxFlow.id, "/lxFlow/getAll", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(lxFlow.formParams());
    table.onDblClickRow = lxFlow.searchInfo;//双击事件所对应的方法 要放在init之前
    lxFlow.table = table.init();
});
