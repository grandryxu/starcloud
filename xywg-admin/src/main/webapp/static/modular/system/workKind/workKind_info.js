/**
 * 初始化详情对话框
 */
var WorkKindInfoDlg = {
    workKindInfoData: {}
};

/**
 * 清除数据
 */
WorkKindInfoDlg.clearData = function () {
    this.workKindInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkKindInfoDlg.set = function (key, val) {
    this.workKindInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkKindInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
WorkKindInfoDlg.close = function () {
    parent.layer.close(window.parent.WorkKind.layerIndex);
}

/**
 * 收集数据
 */
WorkKindInfoDlg.collectData = function () {
    this
        .set('id')
        .set('num')
        .set('organizationCode')
        .set('name')
        .set('amount')
        .set('createDate')
        .set('createUser')
        .set('updateDate')
        .set('updateUser')
        .set('status')
        .set('remark');
}

/**
 * 提交添加
 */
WorkKindInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workKind/add", function (data) {
        Feng.success("添加成功!");
        window.parent.WorkKind.table.refresh();
        WorkKindInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workKindInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
WorkKindInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workKind/update", function (data) {
        Feng.success("修改成功!");
        window.parent.WorkKind.table.refresh();
        WorkKindInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workKindInfoData);
    ajax.start();
}

$(function () {

});
