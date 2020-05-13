/**
 * 初始化考勤异常列表详情对话框
 */
var DeviceRecordExceptionDataInfoDlg = {
    deviceRecordExceptionDataInfoData : {}
};

$("#idCardNumber").blur(function(e) {
        // var exceptionIdCardType=$("#exceptionIdCardType").val();
        // var exceptionIdCardNumber=$("#exceptionIdCardNumber").val();
        var idCardType=$("#idCardType").val();
        var idCardNumber=$("#idCardNumber").val();

        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/workerMaster/searchWorker", function(data){
            if(data.length>0){
                $("#workerName").val(data[0].workerName);
            }else{
                $("#workerName").val("");

            }
        },function(data){
            Feng.error(data.responseJSON.message + "!");
        });
        ajax.set({'idCardType': idCardType, 'idCardNumber': idCardNumber});
        ajax.start();
});
$("#idCardType").blur(function(e) {
    // var exceptionIdCardType=$("#exceptionIdCardType").val();
    // var exceptionIdCardNumber=$("#exceptionIdCardNumber").val();
    var idCardType=$("#idCardType").val();
    var idCardNumber=$("#idCardNumber").val();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workerMaster/searchWorker", function(data){
        if(data.length>0){
            $("#workerName").val(data[0].workerName);
        }else{
            $("#workerName").val("");

        }
    },function(data){
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.set({'idCardType': idCardType, 'idCardNumber': idCardNumber});
    ajax.start();
});






/**
 * 清除数据
 */
DeviceRecordExceptionDataInfoDlg.clearData = function() {
    this.deviceRecordExceptionDataInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeviceRecordExceptionDataInfoDlg.set = function(key, val) {
    this.deviceRecordExceptionDataInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeviceRecordExceptionDataInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
DeviceRecordExceptionDataInfoDlg.close = function() {
    parent.layer.close(window.parent.DeviceRecordExceptionData.layerIndex);
}

/**
 * 收集数据
 */
DeviceRecordExceptionDataInfoDlg.collectData = function() {
    this
    .set('id')
    .set('organizationCode')
    .set('projectCode')
    .set('teamSysNo')
    .set('deviceSn')
    .set('idCardType')
    .set('idCardNumber')
    .set('idCard')
    .set('deviceId')
    .set('shId')
    .set('time')
    .set('type')
    .set('photo')
    .set('lng')
    .set('lat')
    .set('source')
    .set('createDate')
    .set('createUser')
    .set('updateDate')
    .set('updateUser')
    .set('status')
    .set('remark')
    .set('exceptionType')
    .set('txFace')
    .set('algVersion')
    .set('personDataType')
    .set('deviceType')
    .set('userName');
}

/**
 * 提交添加
 */
DeviceRecordExceptionDataInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/deviceRecordExceptionData/add", function(data){
        Feng.success("添加成功!");
        window.parent.DeviceRecordExceptionData.table.refresh();
        DeviceRecordExceptionDataInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deviceRecordExceptionDataInfoData);
    ajax.start();
}

/**
 * 处理异常考勤
 */
DeviceRecordExceptionDataInfoDlg.action = function() {
    var exceptionIdCardType=$("#exceptionIdCardType").val();
    var exceptionIdCardNumber=$("#exceptionIdCardNumber").val();
    var idCardType=$("#idCardType").val();
    var idCardNumber=$("#idCardNumber").val();
    var projectCode=$("#projectCode").val();
    var id=$("#id").val();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/deviceRecordExceptionData/action", function(data){
        Feng.success("处理成功!");
        window.parent.DeviceRecordExceptionData.table.refresh();
        DeviceRecordExceptionDataInfoDlg.close();
    },function(data){
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.set({
        'id': id,
        'idCardType': idCardType,
        'idCardNumber': idCardNumber,
        'exceptionIdCardType': exceptionIdCardType,
        'exceptionIdCardNumber': exceptionIdCardNumber,
        'projectCode': projectCode});
    ajax.start();
}

$(function() {

});
