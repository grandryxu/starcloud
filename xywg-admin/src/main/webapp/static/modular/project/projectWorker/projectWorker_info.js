/**
 * 初始化项目工人详情对话框
 */
var ProjectWorkerInfoDlg = {
    projectWorkerInfoData : {}
};

/**
 * 清除数据
 */
ProjectWorkerInfoDlg.clearData = function() {
    this.projectWorkerInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectWorkerInfoDlg.set = function(key, val) {
    this.projectWorkerInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectWorkerInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProjectWorkerInfoDlg.close = function() {
    parent.layer.close(window.parent.ProjectWorker.layerIndex);
}

/**
 * 收集数据
 */
ProjectWorkerInfoDlg.collectData = function() {
    this
    .set('id')
    .set('projectCode')
    .set('organizationCode')
    .set('teamSysNo')
    .set('idCardType')
    .set('idCardNumber')
    .set('shImei')
    .set('joinStatus')
    .set('workTypeCode')
    .set('cellPhone')
    .set('issueCardTime')
    .set('entryTime')
    .set('exitTime')
    .set('completeCardTime')
    .set('cardNumber')
    .set('cardType')
    .set('hasContract')
    .set('contractCode')
    .set('workerAccommodationType')
    .set('workerRole')
    .set('payRollBankCardNumber')
    .set('payRollBankName')
    .set('hasBuyInsurance')
    .set('createDate')
    .set('createUser')
    .set('updateDate')
    .set('updateUser')
    .set('evaluate')
    .set('isDel');
}

/**
 * 提交添加
 */
ProjectWorkerInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/projectWorker/add", function(data){
        Feng.success("添加成功!");
        window.parent.ProjectWorker.table.refresh();
        ProjectWorkerInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.projectWorkerInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProjectWorkerInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/projectWorker/update", function(data){
        Feng.success("修改成功!");
        window.parent.ProjectWorker.table.refresh();
        ProjectWorkerInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.projectWorkerInfoData);
    ajax.start();
}

$(function() {

});
