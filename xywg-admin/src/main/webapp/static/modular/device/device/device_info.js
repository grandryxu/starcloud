/**
 * 初始化考勤机管理详情对话框
 */
var DeviceInfoDlg = {
    deviceInfoData : {} ,
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
        sn: {
            validators: {
                notEmpty: {
                    message: '设备序列号不能为空'
                },
                regexp: {
                    regexp:  /^[a-zA-Z0-9]{1,20}$/,
                    message: '设备序列号只能为数字或字母'
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
DeviceInfoDlg.clearData = function() {
    this.deviceInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeviceInfoDlg.set = function(key, val) {
    this.deviceInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeviceInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
DeviceInfoDlg.close = function() {
    parent.layer.close(window.parent.Device.layerIndex);
}

/**
 * 收集数据
 */
DeviceInfoDlg.collectData = function() {
    this
    .set('id')
    .set('organizationCode')
    .set('projectCode')
    .set('typeId')
    .set('sn')
    .set('name')
    .set('ip')
    .set('mac')
    .set('version')
    .set('code')
    .set('securityKey')
    .set('serverIp')
    .set('serverPort')
    .set('talk')
    .set('state')
    .set('description')
    .set('updateType')
    .set('createDate')
    .set('createUser')
    .set('updateDate')
    .set('updateUser')
    .set('status')
    .set('type')
    .set('remark')
    .set('algVersion')
    .set('isDel');
}

/**
 * 提交添加
 */
DeviceInfoDlg.addSubmit = function() {
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
            DeviceInfoDlg.close();
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
DeviceInfoDlg.validate = function () {
    $('#DeviceInfoForm').data("bootstrapValidator").resetForm();
    $('#DeviceInfoForm').bootstrapValidator('validate');
    return $("#DeviceInfoForm").data('bootstrapValidator').isValid();
};


/**
 * 提交修改
 */
DeviceInfoDlg.editSubmit = function() {

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
        Feng.info("请选择出入类型！");
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/device/update", function(data){
        if(data.code===600){
            Feng.error(data.message);
            return;
        }else{
            Feng.success("修改成功!");
            window.parent.Device.table.refresh();
            DeviceInfoDlg.close();
        }
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deviceInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("DeviceInfoForm", DeviceInfoDlg.validateFields);
    Feng.initChosen();
});
