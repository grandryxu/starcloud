/**
 * 初始化进退场详情对话框
 */
var EntryExitHistoryInfoDlg = {
    entryExitHistoryInfoData : {}
};

/**
 * 清除数据
 */
EntryExitHistoryInfoDlg.clearData = function() {
    this.entryExitHistoryInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EntryExitHistoryInfoDlg.set = function(key, val) {
    this.entryExitHistoryInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EntryExitHistoryInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
EntryExitHistoryInfoDlg.close = function() {
    parent.layer.close(window.parent.EntryExitHistory.layerIndex);
}

/**
 * 收集数据
 */
EntryExitHistoryInfoDlg.collectData = function() {
    this
    .set('id')
    .set('projectCode')
    .set('organizationCode')
    .set('idCardType')
    .set('idCardNumber')
    .set('date')
    .set('type')
    .set('isDel');
}

/**
 * 提交添加
 */
EntryExitHistoryInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/entryExitHistory/add", function(data){
        Feng.success("添加成功!");
        window.parent.EntryExitHistory.table.refresh();
        EntryExitHistoryInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.entryExitHistoryInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
EntryExitHistoryInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/entryExitHistory/update", function(data){
        Feng.success("修改成功!");
        window.parent.EntryExitHistory.table.refresh();
        EntryExitHistoryInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.entryExitHistoryInfoData);
    ajax.start();
}

$(function() {

});
