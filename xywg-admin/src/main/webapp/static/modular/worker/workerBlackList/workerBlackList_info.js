/**
 * 初始化工人黑名单信息详情对话框
 */
var WorkerBlackListInfoDlg = {
    workerBlackListInfoData : {}
};

/**
 * 清除数据
 */
WorkerBlackListInfoDlg.clearData = function() {
    this.workerBlackListInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkerBlackListInfoDlg.set = function(key, val) {
    this.workerBlackListInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkerBlackListInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
WorkerBlackListInfoDlg.close = function() {
    parent.layer.close(window.parent.WorkerBlackList.layerIndex);
}

/**
 * 收集数据
 */
WorkerBlackListInfoDlg.collectData = function() {
    this
    .set('id')
    .set('idCardType')
    .set('idCardNumber')
    .set('projectCode')
    .set('contractorOrgCode')
    .set('organizationCode')
    .set('teamSysNo')
    .set('teamName')
    .set('blackReason')
    .set('startTime')
    .set('endTime')
    .set('isValid')
    .set('note')
    .set('createDate')
    .set('createUser')
    .set('updateDate')
    .set('updateUser')
    .set('isDel');
}

/**
 * 提交添加
 */
WorkerBlackListInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workerBlackList/add", function(data){
        Feng.success("添加成功!");
        window.parent.WorkerBlackList.table.refresh();
        WorkerBlackListInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workerBlackListInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
WorkerBlackListInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workerBlackList/update", function(data){
        Feng.success("修改成功!");
        window.parent.WorkerBlackList.table.refresh();
        WorkerBlackListInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workerBlackListInfoData);
    ajax.start();
}
