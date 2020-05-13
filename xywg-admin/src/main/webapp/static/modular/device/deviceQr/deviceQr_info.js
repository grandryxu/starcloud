/**
 * 初始化二维码管理详情对话框
 */
var DeviceQrInfoDlg = {
    deviceQrInfoData : {},
    validateFields: {
        name: {
            validators: {
                stringLength: {
                    max: 255,
                    message: '最大长度为255'
                }, notEmpty: {
                message: '设备名称不能为空'
            }
            }
        },
        sn: {
            validators: {
                stringLength: {
                    max: 255,
                    message: '最大长度为255'
                }, notEmpty: {
                    message: '序列号不能为空'
                },
                regexp: {
                    regexp: /^[A-Za-z0-9]+$/,
                    message: "只能输入数字或字母"
                }
            }
                },
        version: {
            validators: {
                stringLength: {
                    max: 255,
                    message: '最大长度为255'
                }
            }
        },
        remark: {
            validators: {
                stringLength: {
                    max: 255,
                    message: '最大长度为255'
                }
            }
        }
    }
}

/**
 * 清除数据
 */
DeviceQrInfoDlg.clearData = function() {
    this.deviceQrInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeviceQrInfoDlg.set = function(key, val) {
    this.deviceQrInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeviceQrInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
DeviceQrInfoDlg.close = function() {
    parent.layer.close(window.parent.DeviceQr.layerIndex);
}

DeviceQrInfoDlg.validate = function () {
    $('#deviceQrForm').data("bootstrapValidator").resetForm();
    $('#deviceQrForm').bootstrapValidator('validate');
    return $("#deviceQrForm").data('bootstrapValidator').isValid();
};

/**
 * 收集数据
 */
DeviceQrInfoDlg.collectData = function() {
    this
    .set('id')
    .set('projectCode')
    .set('name')
    .set('sn')
    .set('state')
    .set('version')
    .set('talkTime')
    .set('remark')
    .set('createDate')
    .set('createUser')
    .set('updateDate')
    .set('updateUser')
    .set('status')
    .set('isDel');
}

/**
 * 提交添加
 */
DeviceQrInfoDlg.addSubmit = function() {
    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    if($("#projectCode").val()=="") {
        Feng.info("请选择项目！");
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/deviceQr/add", function(data){
        if(data.code===600){
            Feng.error(data.message);
            return;
        }else{
            Feng.success("添加成功!");
            window.parent.DeviceQr.table.refresh();
            DeviceQrInfoDlg.close();
        }
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deviceQrInfoData);
    ajax.start();
}


/**
 * 提交修改
 */
DeviceQrInfoDlg.editSubmit = function() {
    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    if($("#projectCode").val()=="") {
        Feng.info("请选择项目！");
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/deviceQr/update", function(data){
        if(data.code===600){
            Feng.error(data.message);
            return;
        }else{
            Feng.success("修改成功!");
            window.parent.DeviceQr.table.refresh();
            DeviceQrInfoDlg.close();
        }
        /*Feng.success("修改成功!");
        window.parent.DeviceQr.table.refresh();
        DeviceQrInfoDlg.close();*/
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deviceQrInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("deviceQrForm", DeviceQrInfoDlg.validateFields);
    laydate.render({
        elem: '#talkTime'
    });
});
