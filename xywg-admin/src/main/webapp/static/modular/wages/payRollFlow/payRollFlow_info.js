/**
 * 初始化工资流水详情对话框
 */
var PayRollFlowInfoDlg = {
    payRollFlowInfoData : {}
};

/**
 * 清除数据
 */
PayRollFlowInfoDlg.clearData = function() {
    this.payRollFlowInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PayRollFlowInfoDlg.set = function(key, val) {
    this.payRollFlowInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PayRollFlowInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
PayRollFlowInfoDlg.close = function() {
    parent.layer.close(window.parent.PayRollFlow.layerIndex);
}

/**
 * 收集数据
 */
PayRollFlowInfoDlg.collectData = function() {
    this
    .set('id')
    .set('payRollCode')
    .set('projectCode')
    .set('organizationCode')
    .set('idCardType')
    .set('idCardNumber')
    .set('type')
    .set('time')
    .set('payAmount')
    .set('actualAmount')
    .set('balanceAmount')
    .set('createDate')
    .set('createUser')
    .set('updateDate')
    .set('updateUser')
    .set('remark')
    .set('isDel');
}

/**
 * 提交添加
 */
PayRollFlowInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/payRollFlow/add", function(data){
        Feng.success("添加成功!");
        window.parent.PayRollFlow.table.refresh();
        PayRollFlowInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.payRollFlowInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
PayRollFlowInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/payRollFlow/update", function(data){
        Feng.success("修改成功!");
        window.parent.PayRollFlow.table.refresh();
        PayRollFlowInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.payRollFlowInfoData);
    ajax.start();
}

$(function() {

});
