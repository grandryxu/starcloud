/**
 * 项目工人管理初始化
 */
var ProjectWorkerDevice = {
    id: "ProjectWorkerDeviceTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProjectWorkerDevice.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ProjectWorkerDevice.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProjectWorker.seItem = selected[0];
        return true;
    }
};


/**
 * 关闭此对话框
 */
ProjectWorkerDevice.close = function() {
    parent.layer.close(window.parent.Device.layerIndex);
}
/**
 * 设备命令下发
 */
ProjectWorkerDevice.commandFun = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("至少选中表格中的某一记录！");
        return false;
    } else {
        ProjectWorkerDevice.seItem = selected;
    }
    var arr = [];
    var userList = [];
    for(var i=0;i<selected.length;i++){
        arr.push(selected[i].idCardNumber+","+selected[i].idCardType);
        userList.push({
            id: selected[i].id,
            idCardType: selected[i].idCardType,
            idCardNumber: selected[i].idCardNumber,
            workerName: selected[i].workerName
        })
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/deviceCommand/executeCommand", function (data) {
        Feng.success("操作成功!");
        ProjectWorkerDevice.close();
    }, function (data) {
        Feng.error("操作失败!" + data.responseJSON.message + "!");
    });
    var sn =$("#deviceSns").val();
    var devices = sn.split(',');
    var deviceJson = JSON.stringify(devices);
    ajax.set("deviceIds",deviceJson);
    ajax.set("type",$("#type").val());
    ajax.set("userList", JSON.stringify(userList));
    ajax.set("workerId",JSON.stringify(arr));
    ajax.start();


};
function trim(str){ //删除左右两端的空格
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * 查询项目工人列表
 */
ProjectWorkerDevice.search = function () {
    var queryData = {};
    queryData['keys'] = trim($("#keys").val());
    queryData['projectCode'] = $("#projectCode").val();
    queryData['team'] = $("#team").val();
    queryData['workKind'] = $("#workKind").val();
    ProjectWorkerDevice.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ProjectWorkerDevice.initColumn();
    var table = new BSTable(ProjectWorkerDevice.id, "/projectWorker/byproject", defaultColunms);
    table.setPaginationType("client");
    ProjectWorkerDevice.table = table.init();
});
