/**
 * 初始化考勤机管理详情对话框
 */
var ProjectMasterCardDlg = {
    ProjectMasterCardDlg : {} ,
    validateFields: {
        workTime: {
            validators: {
                notEmpty: {
                    message: '门禁卡号不能为空'
                }
            }
        }

    }
};

/**
 * 清除数据
 */
ProjectMasterCardDlg.clearData = function() {
    this.ProjectMasterCardDlg = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectMasterCardDlg.set = function(key, val) {
    this.ProjectMasterCardDlg[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectMasterCardDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProjectMasterCardDlg.close = function() {
    parent.layer.close(window.parent.TeamWorker.layerIndex);
}

/**
 * 收集数据
 */
ProjectMasterCardDlg.collectData = function() {
    this
        .set('pwId')
        .set('cardNumber');
}

/**
 * 提交添加
 */
ProjectMasterCardDlg.addSubmit = function() {
    this.clearData();
    this.collectData();
    if(!this.validate()){
        return;
    }
   
    if($("#cardNumber").val()===""){
        Feng.info("请填写门禁卡号！")
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/device/add", function(data){
        if(data.code === 200) {
            Feng.success("添加成功!");
            ProjectMasterCardDlg.close();
        }else{
            Feng.error("添加失败!" + data.message + "!");
        }
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deviceInfoData);
    ajax.start();
}

/**
 * 验证数据是否为空
 */
ProjectMasterCardDlg.validate = function () {
    $('#ProjectMasterCardInfoForm').data("bootstrapValidator").resetForm();
    $('#ProjectMasterCardInfoForm').bootstrapValidator('validate');
    return $("#ProjectMasterCardInfoForm").data('bootstrapValidator').isValid();
};


/**
 * 提交修改
 */
ProjectMasterCardDlg.setCard = function() {
    this.clearData();
    this.collectData();
    if(!this.validate()){
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/projectWorker/updateCardNumber", function(data){
        if(data.code===600){
            Feng.error(data.message);
            return;
        }else{
            Feng.success("修改成功!");
            window.parent.TeamMember.table.refresh();
            ProjectMasterCardDlg.close();
        }
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set("cardNumber", $("#cardNumber").val());
    ajax.set("id", $("#id").val());
    ajax.start();
}

$(function() {
    Feng.initValidator("ProjectMasterCardInfoForm", ProjectMasterCardDlg.validateFields);
    Feng.initChosen();
});
