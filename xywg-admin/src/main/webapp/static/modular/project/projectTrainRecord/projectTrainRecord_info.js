/**
 * 初始化项目培训详情详情对话框
 */
var ProjectTrainRecordInfoDlg = {
    projectTrainRecordInfoData : {}
};

/**
 * 清除数据
 */
ProjectTrainRecordInfoDlg.clearData = function() {
    this.projectTrainRecordInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectTrainRecordInfoDlg.set = function(key, val) {
    this.projectTrainRecordInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectTrainRecordInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProjectTrainRecordInfoDlg.close = function() {
    parent.layer.close(window.parent.ProjectTrainRecord.layerIndex);
}

/**
 * 收集数据
 */
ProjectTrainRecordInfoDlg.collectData = function() {
    this
    .set('trainId')
    .set('idCardType')
    .set('idCardNumber');
}

/**
 * 提交添加
 */
ProjectTrainRecordInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/projectTrainRecord/add", function(data){
        Feng.success("添加成功!");
        window.parent.ProjectTrainRecord.table.refresh();
        ProjectTrainRecordInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.projectTrainRecordInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProjectTrainRecordInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/projectTrainRecord/update", function(data){
        Feng.success("修改成功!");
        window.parent.ProjectTrainRecord.table.refresh();
        ProjectTrainRecordInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.projectTrainRecordInfoData);
    ajax.start();
}

$(function() {

});
