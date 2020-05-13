/**
 * 初始化企业资质详情对话框
 */
var SubContractorCertificationsInfoDlg = {
    subContractorCertificationsInfoData : {},
    validateFields: {
        organizationCode: {
            validators: {
                notEmpty: {
                    message: '参建公司名称不能为空'
                }
            }
        },
        certificationName: {
            validators: {
                notEmpty: {
                    message: '证书名称不能为空'
                },
                stringLength: {
                    max: 50,
                    message: '证书名称最大长度为50'
                }
            }
        },
        certificationType: {
            validators: {
                notEmpty: {
                    message: '证书类型不能为空'
                }
            }
        },
        certificationCode:{
            validators: {
                notEmpty: {
                    message: '证书编码不能为空'
                },
                stringLength: {
                    max: 100,
                    message: '证书编码最大长度为100'
                },
                regexp: {
                    regexp:  /^[a-zA-Z0-9]{1,100}$/,
                    message: '只能输入字母或数字'
                }
            }
        },
        validBeginDate: {
            validators: {
                notEmpty: {
                    message: '证书有效时间(起)不能为空'
                }
            }
        },
        validEndDate: {
            validators: {
                notEmpty: {
                    message: '证书有效时间(止)不能为空'
                }
            }
        },
        grantOrg: {
            validators: {
                notEmpty: {
                    message: '发证机关不能为空'
                }
            }
        },
        qualificationStatus: {
            validators: {
                notEmpty: {
                    message: '资质状态不能为空'
                }
            }
        },
        certificationStatus: {
            validators: {
                notEmpty: {
                    message: '资质证书状态不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
SubContractorCertificationsInfoDlg.clearData = function() {
    this.subContractorCertificationsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SubContractorCertificationsInfoDlg.set = function(key, val) {
    this.subContractorCertificationsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SubContractorCertificationsInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SubContractorCertificationsInfoDlg.close = function() {
    parent.layer.closeAll();
}

/**
 * 验证数据是否为空
 */
SubContractorCertificationsInfoDlg.validate = function () {
    $('#subConCerForm').data("bootstrapValidator").resetForm();
    $('#subConCerForm').bootstrapValidator('validate');
    return $("#subConCerForm").data('bootstrapValidator').isValid();
};

/**
 * 收集数据
 */
SubContractorCertificationsInfoDlg.collectData = function() {
    this
        .set('id')
        .set('sysNo')
        .set('organizationCode')
        .set('certificationType')
        .set('certificationName')
        .set('certificationCode')
        .set('validBeginDate')
        .set('recentValidDate')
        .set('validEndDate')
        .set('grantOrg')
        .set('qualificationStatus')
        .set('certificationStatus')
        .set('isDel');
}

/**
 * 提交添加
 */
SubContractorCertificationsInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if(!this.validate()){
        return;
    }
    if($("#validBeginDate").val()>$("#validEndDate").val()){
        Feng.info("证书有效时间(起)不能早于证书有效时间(止)！")
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/subContractorCertifications/add", function(data){
        window.parent.SubContractorCertifications.table.refresh();
        SubContractorCertificationsInfoDlg.close();
        Feng.success("添加成功!");
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    this.subContractorCertificationsInfoData.sysNo=11;
    console.log(this.subContractorCertificationsInfoData);
    ajax.set(this.subContractorCertificationsInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SubContractorCertificationsInfoDlg.editSubmit = function() {
    this.clearData();
    this.collectData();

    if(!this.validate()){
        return;
    }
    if($("#validBeginDate").val()>$("#validEndDate").val()){
        Feng.info("证书有效时间(起)不能早于证书有效时间(止)！")
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/subContractorCertifications/update", function(data){
        window.parent.SubContractorCertifications.table.refresh();
        SubContractorCertificationsInfoDlg.close();
        Feng.success("修改成功!");
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.subContractorCertificationsInfoData);
    ajax.start();
}


$(function() {
    var validBeginDate = laydate.render({
        elem: '#validBeginDate',
        max: 0,
        done: function (value, date) {
            if (value !== '') {
                validEndDate.config.min.year = date.year;
                validEndDate.config.min.month = date.month - 1;
                validEndDate.config.min.date = date.date;
            } else {
                validEndDate.config.min.year = '';
                validEndDate.config.min.month = '';
                validEndDate.config.min.date = '';
            }
        }
    });
    //设置结束时间
    var validEndDate = laydate.render({
        elem: '#validEndDate',
        done: function (value, date) {
            if (value !== '') {
                validBeginDate.config.max.year = date.year;
                validBeginDate.config.max.month = date.month - 1;
                validBeginDate.config.max.date = date.date;
            } else {
                validBeginDate.config.max.year = '';
                validBeginDate.config.max.month = '';
                validBeginDate.config.max.date = '';
            }
        }
    });

    var recentValidDate = laydate.render({
        elem: '#recentValidDate',
        max: 0,
        done: function (value, date) {
            if (value !== '') {
                recentValidDate.config.min.year = date.year;
                recentValidDate.config.min.month = date.month - 1;
                recentValidDate.config.min.date = date.date;
            } else {
                recentValidDate.config.min.year = '';
                recentValidDate.config.min.month = '';
                recentValidDate.config.min.date = '';
            }
        }
    });
    Feng.initValidator("subConCerForm", SubContractorCertificationsInfoDlg.validateFields);
});
