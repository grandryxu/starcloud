/**
 * 初始化安全帽管理详情对话框
 */
var SafetyHelmetInfoDlg = {
    safetyHelmetInfoData : {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '设备名称不能为空'
                },
                stringLength: {
                    max: 20,
                    message: '最大长度为20'
                }
            }
        },
        imei: {
            validators: {
                notEmpty: {
                    message: '安全帽唯一识别码不能为空'
                },
                regexp: {
                    regexp:  /^[a-zA-Z0-9]{1,20}$/,
                    message: '安全帽唯一识别码只能为数字或字母'
                }
            }
        },
        remark: {
            validators: {
                stringLength: {
                    max: 255,
                    message: '255'
                }
            }
        }

    }
};

/**
 * 清除数据
 */
SafetyHelmetInfoDlg.clearData = function() {
    this.safetyHelmetInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SafetyHelmetInfoDlg.set = function(key, val) {
    this.safetyHelmetInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SafetyHelmetInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SafetyHelmetInfoDlg.close = function() {
    parent.layer.close(window.parent.SafetyHelmet.layerIndex);
}

/**
 * 收集数据
 */
SafetyHelmetInfoDlg.collectData = function() {
    this
    .set('id')
    .set('organizationCode')
    .set('projectCode')
    .set('name')
    .set('imei')
    .set('type')
    .set('state')
    .set('talk')
    .set('createDate')
    .set('createUser')
    .set('updateDate')
    .set('updateUser')
    .set('status')
    .set('remark');
}

/**
 * 验证数据是否为空
 */
SafetyHelmetInfoDlg.validate = function () {
    $('#SafetyHelmetInfoForm').data("bootstrapValidator").resetForm();
    $('#SafetyHelmetInfoForm').bootstrapValidator('validate');
    return $("#SafetyHelmetInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
SafetyHelmetInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if($("#projectCode").val()==""){
        Feng.info("请选择项目！")
        return;
    }
    if(!this.validate()){
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/safetyHelmet/add", function(data){
        if(data.code === 200){
            Feng.success("添加成功!");
            window.parent.SafetyHelmet.table.refresh();
            SafetyHelmetInfoDlg.close();
        }else{
            Feng.error("添加失败!" + data.message + "!");
        }
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.safetyHelmetInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SafetyHelmetInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if(!this.validate()){
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/safetyHelmet/update", function(data){
        if(data.code===600){
            Feng.error(data.message);
            return;
        }else{
            Feng.success("修改成功!");
            window.parent.SafetyHelmet.table.refresh();
            SafetyHelmetInfoDlg.close();
        }
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.safetyHelmetInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("SafetyHelmetInfoForm", SafetyHelmetInfoDlg.validateFields);
});
