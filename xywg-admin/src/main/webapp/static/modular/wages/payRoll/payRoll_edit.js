/**
 * 初始化工资单详情对话框
 */
var PayRollInfoDlg = {
    payRollInfoData : {}
};

/**
 * 清除数据
 */
PayRollInfoDlg.clearData = function() {
    this.payRollInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PayRollInfoDlg.set = function(key, val) {
    this.payRollInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PayRollInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
PayRollInfoDlg.close = function() {
    parent.layer.close(window.parent.PayRoll.layerIndex);
}

/**
 * 收集数据
 */
PayRollInfoDlg.collectData = function() {
    this
    .set('id')
    .set('payRollCode')
    .set('projectCode')
    .set('organizationCode')
    .set('teamSysNo')
    .set('payMonth')
    .set('type')
    .set('status')
    .set('saveStatus')
    .set('payStatus')
    .set('contractorProjectCode')
    .set('subContractorProjectCode')
    .set('attachFiles')
    .set('totalAmount')
    .set('constructValid')
    .set('constructDate')
    .set('contractValid')
    .set('contractDate')
    .set('createDate')
    .set('createUser')
    .set('updateDate')
    .set('updateUser')
    .set('salaryPerson')
    .set('isDel');
}

/**
 * 提交添加
 */
PayRollInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/payRoll/add", function(data){
        Feng.success("添加成功!");
        window.parent.PayRoll.table.refresh();
        PayRollInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.payRollInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
PayRollInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/payRoll/update", function(data){
        Feng.success("修改成功!");
        window.parent.PayRoll.table.refresh();
        PayRollInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.payRollInfoData);
    ajax.start();
}

$(function() {

});
