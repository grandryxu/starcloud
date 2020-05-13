/**
 * 初始化led管理详情对话框
 */
var LedInfoDlg = {
    ledInfoData : {},
    validateFields: {
        imei: {
            validators: {
                notEmpty: {
                    message: '终端编号不能为空'
                },
                stringLength: {
                    max: 50,
                    message: '最大长度为50'
                }
            }
        },
        clientName: {
            validators: {
                notEmpty: {
                    message: '终端名称不能为空'
                },
                stringLength: {
                    max: 500,
                    message: '最大长度为500'
                }
            }
        },
        screenWidth: {
            validators: {
                notEmpty: {
                    message: '屏宽不能为空'
                },
                stringLength: {
                    max: 100,
                    message: '最大长度为100'
                },
                regexp: {
                    regexp: /^[0-9]*$/,
                    message: '请输入正确的屏宽'
                }
            }
        },
        screenHeight: {
            validators: {
                notEmpty: {
                    message: '屏高不能为空'
                },
                stringLength: {
                    max: 100,
                    message: '最大长度为100'
                },
                regexp: {
                    regexp: /^[0-9]*$/,
                    message: '请输入正确的屏高'
                }
            }
        },
        stayTime: {
            validators: {
                notEmpty: {
                    message: '停留时间不能为空'
                },
                stringLength: {
                    max: 10,
                    message: '最大长度为10'
                },
                regexp: {
                    regexp: /^[0-9]*$/,
                    message: '请输入正确的停留时间'
                }
            }
        },
        displayText: {
            validators: {
                notEmpty: {
                    message: '内容模板不能为空'
                },
                stringLength: {
                    max: 500,
                    message: '最大长度为500'
                }
            }
        },
        displaySpeed: {
            validators: {
                notEmpty: {
                    message: '显示速度不能为空'
                },
                stringLength: {
                    max: 10,
                    message: '最大长度为10'
                },
                regexp: {
                    regexp: /^[0-9]*$/,
                    message: '请输入正确的显示速度'
                }
            }
        },
        fontSize: {
            validators: {
                notEmpty: {
                    message: '字体大小不能为空'
                },
                stringLength: {
                    max: 10,
                    message: '最大长度为10'
                },
                regexp: {
                    regexp: /^[0-9]*$/,
                    message: '请输入正确的字体大小'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
LedInfoDlg.clearData = function() {
    this.ledInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
LedInfoDlg.set = function(key, val) {
    this.ledInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
LedInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
LedInfoDlg.close = function() {
    parent.layer.close(window.parent.Led.layerIndex);
}

/**
 * 收集数据
 */
LedInfoDlg.collectData = function() {
    this
    .set('id')
    .set('imei')
    .set('displayType')
    .set('displaySpeed')
    .set('stayTime')
    .set('screenWidth')
    .set('screenHeight')
    .set('fontColor')
    .set('fontSize')
    .set('projectCode')
    .set('clientName')
    .set('displayText')
    .set('flag')
    .set('status')
    .set('joFlag');
}

/**
 * 验证数据是否为空
 */
LedInfoDlg.validate = function () {
    $('#LedInfoForm').data("bootstrapValidator").resetForm();
    $('#LedInfoForm').bootstrapValidator('validate');
    return $("#LedInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
LedInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //下拉框验证
    if($("#projectCode").val() === ''){
        Feng.info("请选择项目");
        return;
    }
    if($("#displayType").val() === ''){
        Feng.info("请选择显示特技");
        return;
    }
    if($("#fontColor").val() === ''){
        Feng.info("请选择字体颜色");
        return;
    }
    if($("#flag").val() === ''){
        Feng.info("请选择是否启用");
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/led/add", function(data){
        Feng.success("添加成功!");
        window.parent.Led.table.refresh();
        LedInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ledInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
LedInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();


    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //下拉框验证
    if($("#projectCode").val() === ''){
        Feng.info("请选择项目");
        return;
    }
    if($("#displayType").val() === ''){
        Feng.info("请选择显示特技");
        return;
    }
    if($("#displaySpeed").val() === ''){
        Feng.info("请选择显示速度");
        return;
    }
    if($("#status").val() === ''){
        Feng.info("请选择是否启用");
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/led/update", function(data){
        Feng.success("修改成功!");
        window.parent.Led.table.refresh();
        LedInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.ledInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("LedInfoForm", LedInfoDlg.validateFields);
});
