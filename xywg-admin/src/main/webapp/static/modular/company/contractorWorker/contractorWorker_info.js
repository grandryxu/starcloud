/**
 * 初始化企业工人详情对话框
 */
var ContractorWorkerInfoDlg = {
    contractorWorkerInfoData : {}
};

/**
 * 清除数据
 */
ContractorWorkerInfoDlg.clearData = function() {
    this.contractorWorkerInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ContractorWorkerInfoDlg.set = function(key, val) {
    this.contractorWorkerInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ContractorWorkerInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ContractorWorkerInfoDlg.close = function() {
    parent.layer.close(window.parent.ContractorWorker.layerIndex);
}

/**
 * 收集数据
 */
ContractorWorkerInfoDlg.collectData = function() {
    this
    .set('id')
    .set('idCardType')
    .set('idCardNumber')
    .set('workName')
    .set('organizationCode')
    .set('contractorName');
}

/**
 * 提交添加
 */
ContractorWorkerInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/contractorWorker/add", function(data){
        Feng.success("添加成功!");
        window.parent.ContractorWorker.table.refresh();
        ContractorWorkerInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.contractorWorkerInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ContractorWorkerInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/contractorWorker/update", function(data){
        Feng.success("修改成功!");
        window.parent.ContractorWorker.table.refresh();
        ContractorWorkerInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.contractorWorkerInfoData);
    ajax.start();
}

$(function() {

});
