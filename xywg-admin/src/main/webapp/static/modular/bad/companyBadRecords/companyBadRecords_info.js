/**
 * 初始化不良记录详情对话框
 */
var CompanyBadRecordsInfoDlg = {
    CompanyBadRecordsInfoData: {},
    validateFields: {
        organizationCode: {
            validators: {
                notEmpty: {
                    message: '企业名称不能为空'
                }
            }
        },
        projectCode: {
            validators: {
                notEmpty: {
                    message: '项目名称不能为空'
                }
            }
        },
        reason: {
            validators: {
                stringLength: {
                    max: 450,
                    message: '最大长度为450'
                }
            }
        },
        occurrenceDate: {
            validators: {
                notEmpty: {
                    message: '发生时间不能为空'
                }
            }
        },
        badRecordTypeCode: {
            validators: {
                notEmpty: {
                    message: '事件类别不能为空'
                }
            }
        },
        badRecordLevelType: {
            validators: {
                notEmpty: {
                    message: '事件级别不能为空'
                }
            }
        },
        organizationCode: {
            validators: {
                notEmpty: {
                    message: '企业名称不能为空'
                }
            }
        }
    }
};

/**
 * 验证数据是否为空
 */
CompanyBadRecordsInfoDlg.validate = function () {
    $('#CompanyBadRecordsInfoForm').data("bootstrapValidator").resetForm();
    $('#CompanyBadRecordsInfoForm').bootstrapValidator('validate');
    return $("#CompanyBadRecordsInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
CompanyBadRecordsInfoDlg.clearData = function () {
    this.companyBadRecordsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CompanyBadRecordsInfoDlg.set = function (key, val) {
    this.companyBadRecordsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CompanyBadRecordsInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CompanyBadRecordsInfoDlg.close = function () {
    parent.layer.close(window.parent.CompanyBadRecords.layerIndex);
}

/**
 * 收集数据
 */
CompanyBadRecordsInfoDlg.collectData = function () {
    this
        .set('id')
        .set('projectCode')
        .set('organizationCode')
        .set('type')
        .set('badRecordTypeCode')
        .set('badRecordLevelType')
        .set('occurrenceDate')
        .set('reason')
        .set('processResult')
}

/**
 * 验证数据是否为空
 */
CompanyBadRecordsInfoDlg.validate = function () {
    $('#companyBadRecordsForm').data("bootstrapValidator").resetForm();
    $('#companyBadRecordsForm').bootstrapValidator('validate');

    return $("#companyBadRecordsForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
CompanyBadRecordsInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if($("#projectCode").val()===''){
        Feng.info("请选择所属项目");
        return;
    }
    if($("#organizationCode").val()===''){
        Feng.info("请选择企业名称");
        return;
    }

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/companyBadRecords/add", function (data) {
        Feng.success("添加成功!");
        window.parent.CompanyBadRecords.table.refresh();
        CompanyBadRecordsInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.companyBadRecordsInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CompanyBadRecordsInfoDlg.editSubmit = function () {
    this.clearData();
    this.collectData();

    if($("#projectCode").val()===''){
        Feng.info("请选择所属项目");
        return;
    }
    if($("#organizationCode").val()===''){
        Feng.info("请选择企业名称");
        return;
    }

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/companyBadRecords/update", function (data) {
        Feng.success("修改成功!");
        window.parent.CompanyBadRecords.table.refresh();
        CompanyBadRecordsInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.companyBadRecordsInfoData);
    ajax.start();
}

//项目值变动事件
CompanyBadRecordsInfoDlg.projectChange = function () {
    var projectCode = $("#projectCode").val();
    if(projectCode === ''){
        $("#organizationCode").chosen("destroy");
        var html = "<option value=''>请选择</option>";
        $("#organizationCode").html(html);
        $("#organizationCode").chosen({search_contains: true, no_results_text: "暂无结果"});
    }else{
        var ajax = new $ax(Feng.ctxPath + '/companyBadRecords/getCompanys/' + projectCode, function (data) {
            $("#organizationCode").chosen("destroy");
            var html = "<option value=''>请选择</option>";
            for (var i = 0; i < data.length; i++) {
                html += "<option value='" + data[i].organizationCode + "'>" + data[i].companyName + "</option>"
            }
            $("#organizationCode").html(html);
            $("#organizationCode").chosen({search_contains: true, no_results_text: "暂无结果"});
            //企业值变动事件
            $("#organizationCode").on("change",function () {
                $("#socialCreditNumber").val($("#organizationCode").val());
            })
        }, function (data) {
            Feng.error("数据加载失败!" + data.responseJSON.message + "!");
        });
        ajax.start();
    }
};

CompanyBadRecordsInfoDlg.projectChangeAndReset = function () {
    CompanyBadRecordsInfoDlg.projectChange();
    $("#socialCreditNumber").val('');
}

$(function () {
    Feng.initValidator("companyBadRecordsForm", CompanyBadRecordsInfoDlg.validateFields);
    Feng.initChosen();
    //时间渲染
    laydate.render({
        elem: '#occurrenceDate', max: 0
    });

    //修改页面
    if($("#page").val() === "edit" || $("#page").val() === "view"){
        CompanyBadRecordsInfoDlg.projectChange();
        var organizationCode = $("#organizationCode").data("value");
        $("#organizationCode option").each(function (index,data) {
            if(data.value === organizationCode){
                data.selected = true;
            }
        });
        $("#organizationCode").chosen("destroy");
        $("#organizationCode").chosen({search_contains: true, no_results_text: "暂无结果"});
        //企业值变动事件
        $("#organizationCode").on("change",function () {
            $("#socialCreditNumber").val($("#organizationCode").val());
        })

        $('#organizationCode').prop('disabled', true).trigger("chosen:updated");
    }

});
