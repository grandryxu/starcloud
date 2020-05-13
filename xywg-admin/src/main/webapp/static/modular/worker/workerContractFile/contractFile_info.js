/**
 * 初始化合同文件详情对话框
 */
var ContractFileInfoDlg = {
    contractFileInfoData : {}
};

/**
 * 清除数据
 */
ContractFileInfoDlg.clearData = function() {
    this.contractFileInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ContractFileInfoDlg.set = function(key, val) {
    this.contractFileInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ContractFileInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ContractFileInfoDlg.close = function() {
    parent.layer.close(window.parent.ContractFile.layerIndex);
}

/**
 * 收集数据
 */
ContractFileInfoDlg.collectData = function() {
    this
    .set('id')
    .set('projectCode')
    .set('organizationCode')
    .set('contractCode')
    .set('teamSysNo')
    .set('idCardType')
    .set('idCardNumber')
    .set('fileName')
    .set('tag')
    .set('filePath')
    .set('facePath')
    .set('isDel');
}

/**
 * 提交添加
 */
ContractFileInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/contractFile/add", function(data){
        Feng.success("添加成功!");
        window.parent.ContractFile.table.refresh();
        ContractFileInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.contractFileInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ContractFileInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/contractFile/update", function(data){
        Feng.success("修改成功!");
        window.parent.ContractFile.table.refresh();
        ContractFileInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.contractFileInfoData);
    ajax.start();
}

$(function() {

});
