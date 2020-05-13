/**
 * 初始化考勤机管理详情对话框
 */
var ProjectMasterInfoDlg = {
    projectMasterInfoDlg : {} ,
    validateFields: {
        macCode: {
            validators: {
                notEmpty: {
                    message: '编号不能为空'
                }
            }
        }

    }
};

/**
 * 清除数据
 */
ProjectMasterInfoDlg.clearData = function() {
    this.projectMasterInfoDlg = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectMasterInfoDlg.set = function(key, val) {
    this.projectMasterInfoDlg[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectMasterInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProjectMasterInfoDlg.close = function() {
    parent.layer.close(window.parent.ProjectMaster.layerIndex);
}

/**
 * 收集数据
 */
ProjectMasterInfoDlg.collectData = function() {
    this
        .set('projectCode')
        .set('macCode')

}

/**
 * 提交添加
 */
/*ProjectMasterInfoDlg.addSubmit = function() {
    this.clearData();
    this.collectData();
    if(!this.validate()){
        return;
    }
    if($("#projectCode").val()===""){
        Feng.info("请选择项目！");
        return;
    }
    if($("#typeId").val()===""){
        Feng.info("请选择设备类型！");
        return;
    }
    if($("#type").val()===""){
        Feng.info("请选择出入类型！")
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/device/add", function(data){
        if(data.code === 200) {
            Feng.success("添加成功!");
            window.parent.Device.table.refresh();
            ProjectMasterInfoDlg.close();
        }else{
            Feng.error("添加失败!" + data.message + "!");
        }
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deviceInfoData);
    ajax.start();
}*/

/**
 * 验证数据是否为空
 */
ProjectMasterInfoDlg.validate = function () {
    $('#ProjectMasterInfoForm').data("bootstrapValidator").resetForm();
    $('#ProjectMasterInfoForm').bootstrapValidator('validate');
    return $("#ProjectMasterInfoForm").data('bootstrapValidator').isValid();
};


/**
 * 提交修改
 */
ProjectMasterInfoDlg.submitMac = function() {
    console.log($("#projectCode").val());

    this.clearData();
    this.collectData();
    if(!this.validate()){
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/projectMaster/submitMac", function(data){
        if(data.code===600){
            Feng.error(data.message);
            return;
        }else{
            Feng.success("提交成功!");
            ProjectMasterInfoDlg.close();
        }
    },function(data){
        Feng.error("提交失败!" + data.responseJSON.message + "!");
    });
        ajax.set("projectCode",$("#projectCode").val());
    ajax.set("macCode",$("#macCode").val());

    ajax.start();
}

$(function() {
    Feng.initValidator("ProjectMasterInfoForm", ProjectMasterInfoDlg.validateFields);
    Feng.initChosen();
});
