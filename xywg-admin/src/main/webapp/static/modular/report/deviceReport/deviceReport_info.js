/**
 * 初始化考勤统计详情对话框
 */
var DeviceReportInfoDlg = {
    deviceReportInfoData : {}
};

/**
 * 清除数据
 */
DeviceReportInfoDlg.clearData = function() {
    this.deviceReportInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeviceReportInfoDlg.set = function(key, val) {
    this.deviceReportInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeviceReportInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
DeviceReportInfoDlg.close = function() {
    parent.layer.close(window.parent.DeviceReport.layerIndex);
}

/**
 * 收集数据
 */
DeviceReportInfoDlg.collectData = function() {
    this
    .set('id')
    .set('projectCode')
    .set('organizationCode')
    .set('companyName')
    .set('idCardType')
    .set('idCardNumber')
    .set('workerName')
    .set('projectName')
    .set('teamSysNo')
    .set('teamName')
    .set('time')
    .set('totalCount')
    .set('totalDate')
    .set('totalTime')
    .set('overTime')
    .set('day01')
    .set('day02')
    .set('day03')
    .set('day04')
    .set('day05')
    .set('day06')
    .set('day07')
    .set('day08')
    .set('day09')
    .set('day10')
    .set('day11')
    .set('day12')
    .set('day13')
    .set('day14')
    .set('day15')
    .set('day16')
    .set('day17')
    .set('day18')
    .set('day19')
    .set('day20')
    .set('day21')
    .set('day22')
    .set('day23')
    .set('day24')
    .set('day25')
    .set('day26')
    .set('day27')
    .set('day28')
    .set('day29')
    .set('day30')
    .set('day31');
}

/**
 * 提交添加
 */
DeviceReportInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/deviceReport/add", function(data){
        Feng.success("添加成功!");
        window.parent.DeviceReport.table.refresh();
        DeviceReportInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deviceReportInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
DeviceReportInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/deviceReport/update", function(data){
        Feng.success("修改成功!");
        window.parent.DeviceReport.table.refresh();
        DeviceReportInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deviceReportInfoData);
    ajax.start();
}

/**
 * 提交添加
 */
DeviceReportInfoDlg.addOverTimeSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/deviceReport/overTime", function(data){
        Feng.success("添加成功!");
        window.parent.DeviceReport.table.refresh();
        DeviceReportInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deviceReportInfoData);
    ajax.start();
}

$(function() {

});
