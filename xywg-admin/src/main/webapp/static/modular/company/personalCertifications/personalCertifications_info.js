/**
 * 初始化人员资格证书详情对话框
 */
var PersonalCertificationsInfoDlg = {
    personalCertificationsInfoData: {},
    validateFields: {
        employeeName: {
            validators: {
                notEmpty: {
                    message: '姓名不能为空'
                },
                stringLength: {
                    max: 50,
                    message: '最大长度为50'
                }
            }
        },
        jobType:{
            validators: {
                notEmpty: {
                    message: '类别/工种不能为空'
                }
            }
        },
        issueDate:{
            validators: {
                notEmpty: {
                    message: '发证日期不能为空'
                }
            }
        },
        validBeginDate:{
            validators: {
                notEmpty: {
                    message: '证书有效起始日期不能为空'
                }
            }
        },
        validEndDate:{
            validators: {
                notEmpty: {
                    message: '证书有效截止日期不能为空'
                }
            }
        },
        idCardType: {
            validators: {
                notEmpty: {
                    message: '证件类型不能为空'
                }
            }
        },
        certificationTypeCode: {
            validators: {
                notEmpty: {
                    message: '证书类型不能为空'
                }
            }
        },
        certificationLvelType: {
            validators: {
                stringLength: {
                    max: 20,
                    message: '最大长度为20'
                }
            }
        },
        certificationName: {
            validators: {
                notEmpty: {
                    message: '证书名称不能为空'
                },
                stringLength: {
                    max: 25,
                    message: '最大长度为25'
                }
            }
        },
        issueBy: {
            validators: {
                stringLength: {
                    max: 25,
                    message: '最大长度为25'
                }
            }
        }
    }

};

/**
 * 清除数据
 */
PersonalCertificationsInfoDlg.clearData = function () {
    this.personalCertificationsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PersonalCertificationsInfoDlg.set = function (key, val) {
    this.personalCertificationsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PersonalCertificationsInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
PersonalCertificationsInfoDlg.close = function () {
    parent.layer.close(window.parent.PersonalCertifications.layerIndex);
}

/**
 * 收集数据
 */
PersonalCertificationsInfoDlg.collectData = function () {
    this
        .set('id')
        .set('idCardType')
        .set('idCardNumber')
        .set('certificationTypeCode')
        .set('professionCode')
        .set('jobType')
        .set('certificationLevelType')
        .set('certificationName')
        .set('validBeginDate')
        .set('validEndDate')
        .set('issueBy')
        .set('issueDate')
        .set('status')
        .set('isDel');
}
/**
 * 验证数据是否为空
 */
PersonalCertificationsInfoDlg.validate = function () {
    $('#personalCertificationsForm').data("bootstrapValidator").resetForm();
    $('#personalCertificationsForm').bootstrapValidator('validate');
    return $("#personalCertificationsForm").data('bootstrapValidator').isValid();
};
/**
 * 提交添加
 */
PersonalCertificationsInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/personalCertifications/add", function (data) {
        Feng.success("添加成功!");
        window.parent.PersonalCertifications.table.refresh();
        PersonalCertificationsInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.personalCertificationsInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
PersonalCertificationsInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/personalCertifications/update", function (data) {
        Feng.success("修改成功!");
        window.parent.PersonalCertifications.table.refresh();
        PersonalCertificationsInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.personalCertificationsInfoData);
    ajax.start();
}


$('#employeeName').change(function () {
    var id = $(this).val();
    var ajax = new $ax(Feng.ctxPath + '/employeeMaster/detail/' + id, function (data) {
        $("#idCardType").val(data.idCardType);
        $("#idCardNumber").val(data.idCardNumber);
    }, function (data) {
        // Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.start();
});
$('#jobType').change(function () {
    var professionCode = $(this).val();
    $('#professionCode').val(professionCode);
});

var certificationTypeCodeChange = function () {
    var id = $("#certificationTypeCode").val();
    if (id != 0) {
        var ajax = new $ax(Feng.ctxPath + "/personalCertifications/getJobType", function (data) {
            var html = '';
            if(data=== null || data.length === 0 || data[0]=== null ){
                html+='<option value="">请选择</option>'
            }else{
                for (var i = 0; i < data.length; i++) {
                    html += '<option value="' + data[i].num + '">' + data[i].name + '</option>';
                }
            }
            $('#jobType').html(html);

        }, function (data) {
            // Feng.error("添加失败!" + data.responseJSON.message + "!");
        });
        ajax.set({'id': id});
        ajax.start();


    }
}


$(function () {
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
    laydate.render({elem: '#issueDate', max: 0});
    var id = $("#certificationTypeCode").val();
    if (id != 0) {
        var ajax = new $ax(Feng.ctxPath + "/personalCertifications/getJobType", function (data) {
            var html = '';
            for (var i = 0; i < data.length; i++) {
                if (data[i].num == $("#jobType").val()) {
                    html += '<option value="' + data[i].num + '"selected>' + data[i].name + '</option>';
                } else {
                    html += '<option value="' + data[i].num + '">' + data[i].name + '</option>';
                }
            }
            $('#jobType').html(html);

        }, function (data) {
            // Feng.error("添加失败!" + data.responseJSON.message + "!");
        });
        ajax.set({'id': id});
        ajax.start();
    }
    $("#jobType").val($("#jobTypeValue").val());

    Feng.initValidator("personalCertificationsForm", PersonalCertificationsInfoDlg.validateFields);

});
