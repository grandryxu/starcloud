/**
 * 初始化企业资质详情对话框
 */
var SubContractorCertificationsInfoDlg = {
    subContractorCertificationsInfoData : {}
};

/**
 * 清除数据
 */
SubContractorCertificationsInfoDlg.clearData = function() {
    this.subContractorCertificationsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SubContractorCertificationsInfoDlg.set = function(key, val) {
    this.subContractorCertificationsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SubContractorCertificationsInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SubContractorCertificationsInfoDlg.close = function() {
    parent.layer.close(window.parent.SubContractorCertifications.layerIndex);
}

/**
 * 收集数据
 */
SubContractorCertificationsInfoDlg.collectData = function() {
    this
    .set('id')
    .set('sysNo')
    .set('organizationCode')
    .set('certificationType')
    .set('certificationName')
    .set('certificationCode')
    .set('validBeginDate')
    .set('recentValidDate')
    .set('validEndDate')
    .set('grantOrg')
    .set('qualificationStatus')
    .set('certificationStatus')
    .set('isDel');
}

/**
 * 提交添加
 */
SubContractorCertificationsInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/subContractorCertifications/add", function(data){
        Feng.success("添加成功!");
        window.parent.SubContractorCertifications.table.refresh();
        SubContractorCertificationsInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.subContractorCertificationsInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SubContractorCertificationsInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/subContractorCertifications/update", function(data){
        Feng.success("修改成功!");
        window.parent.SubContractorCertifications.table.refresh();
        SubContractorCertificationsInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.subContractorCertificationsInfoData);
    ajax.start();
}

$(function() {

});
