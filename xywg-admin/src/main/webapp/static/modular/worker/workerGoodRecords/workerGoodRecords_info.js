/**
 * 初始化工人奖励记录信息详情对话框
 */
var WorkerGoodRecordsInfoDlg = {
    workerGoodRecordsInfoData : {}
};

/**
 * 清除数据
 */
WorkerGoodRecordsInfoDlg.clearData = function() {
    this.workerGoodRecordsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkerGoodRecordsInfoDlg.set = function(key, val) {
    this.workerGoodRecordsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkerGoodRecordsInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
WorkerGoodRecordsInfoDlg.close = function() {
    parent.layer.close(window.parent.WorkerGoodRecords.layerIndex);
}

/**
 * 收集数据
 */
WorkerGoodRecordsInfoDlg.collectData = function() {
    this
    .set('id')
    .set('idCardType')
    .set('idCardNumber')
    .set('projectCode')
    .set('organizationCode')
    .set('goodRecordTypeCode')
    .set('goodRecordLevelType')
    .set('occurrenceDate')
    .set('details')
    .set('createDate')
    .set('createUser')
    .set('updateDate')
    .set('updateUser')
    .set('isDel');
}

/**
 * 提交添加
 */
WorkerGoodRecordsInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workerGoodRecords/add", function(data){
        Feng.success("添加成功!");
        window.parent.WorkerGoodRecords.table.refresh();
        WorkerGoodRecordsInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workerGoodRecordsInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
WorkerGoodRecordsInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workerGoodRecords/update", function(data){
        Feng.success("修改成功!");
        window.parent.WorkerGoodRecords.table.refresh();
        WorkerGoodRecordsInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workerGoodRecordsInfoData);
    ajax.start();
}

$(function() {

});
