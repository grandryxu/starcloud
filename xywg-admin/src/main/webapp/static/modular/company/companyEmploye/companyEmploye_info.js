/**
 * 初始化企业从业人员详情对话框
 */
var CompanyEmployeInfoDlg = {
    companyEmployeInfoData : {}
};
var jobStatusChange = function () {
    var jobStatus = $("#jobStatus").val();
    if (jobStatus==0) {
        alert(1)
        $('#terminationDate').attr('disabled', false);
        $('#retire_date').attr('disabled', false);
    }else if(jobStatus==1){
        alert(2)
        $("#retire_date").css("display")=='none';
        $("#hireDate").css("display")=='none';
    }else{
        alert(3)
        $("#hireDate").css("display")=='none';
    }
}
/**
 * 清除数据
 */
CompanyEmployeInfoDlg.clearData = function() {
    this.companyEmployeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CompanyEmployeInfoDlg.set = function(key, val) {
    this.companyEmployeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CompanyEmployeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CompanyEmployeInfoDlg.close = function() {
    parent.layer.close(window.parent.CompanyEmploye.layerIndex);
}

/**
 * 收集数据
 */
CompanyEmployeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('organizationCode')
    .set('employeSysNo')
    .set('job')
    .set('jobType')
    .set('jobStatus')
    .set('hireDate')
    .set('terminationDate')
    .set('workerRole')
    .set('remark')
    .set('createDate')
    .set('updateUser')
    .set('createUser')
    .set('updateDate')
    .set('isDel');
}

/**
 * 提交添加
 */
CompanyEmployeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/companyEmploye/add", function(data){
        Feng.success("添加成功!");
        window.parent.CompanyEmploye.table.refresh();
        CompanyEmployeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.companyEmployeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CompanyEmployeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/companyEmploye/update", function(data){
        Feng.success("修改成功!");
        window.parent.CompanyEmploye.table.refresh();
        CompanyEmployeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.companyEmployeInfoData);
    ajax.start();
}

$(function() {

});
