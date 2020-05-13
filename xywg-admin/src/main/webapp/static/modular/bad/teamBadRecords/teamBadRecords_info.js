/**
 * 初始化不良记录详情对话框
 */
var TeamBadRecordsInfoDlg = {
    teamBadRecordsInfoData: {},
    validateFields: {
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
        teamId: {
            validators: {
                notEmpty: {
                    message: '班组名称不能为空'
                }
            }
        }
    }
};

/**
 * 验证数据是否为空
 */
TeamBadRecordsInfoDlg.validate = function () {
    $('#teamBadRecordsForm').data("bootstrapValidator").resetForm();
    $('#teamBadRecordsForm').bootstrapValidator('validate');
    return $("#teamBadRecordsForm").data('bootstrapValidator').isValid();
};


/**
 * 清除数据
 */
TeamBadRecordsInfoDlg.clearData = function () {
    this.teamBadRecordsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TeamBadRecordsInfoDlg.set = function (key, val) {
    this.teamBadRecordsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TeamBadRecordsInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TeamBadRecordsInfoDlg.close = function () {
    parent.layer.close(window.parent.TeamBadRecords.layerIndex);
}

/**
 * 收集数据
 */
TeamBadRecordsInfoDlg.collectData = function () {
    this
        .set('id')
        .set('idCardType')
        .set('teamSysNo')
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
TeamBadRecordsInfoDlg.validate = function () {
    $('#teamBadRecordsForm').data("bootstrapValidator").resetForm();
    $('#teamBadRecordsForm').bootstrapValidator('validate');
    return $("#teamBadRecordsForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
TeamBadRecordsInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teamBadRecords/add", function (data) {
        Feng.success("添加成功!");
        window.parent.TeamBadRecords.table.refresh();
        TeamBadRecordsInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.teamBadRecordsInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TeamBadRecordsInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teamBadRecords/update", function (data) {
        Feng.success("修改成功!");
        window.parent.TeamBadRecords.table.refresh();
        TeamBadRecordsInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.teamBadRecordsInfoData);
    ajax.start();
}

//项目change事件
TeamBadRecordsInfoDlg.projectChange = function (project) {
    var projectCode = $(project).val();
    var html = "<option value=''>请选择</option>"
    $("#teamId").chosen('destroy');
    $("#teamSysNo").val('');
    $("#teamLeader").val('');
    $("#organizationCode").val('');
    $("#companyName").val('');
    if (projectCode) {
        var ajax = new $ax(Feng.ctxPath + "/teamBadRecords/getTeamsByProjectCode", function (data) {
            if (data) {
                for (var i = 0; i < data.length; i++) {
                    html += "<option value='" + data[i].id + "'>" + data[i].teamName + "</option>"
                }
            }
            $("#teamId").html(html);
            $("#teamId").chosen({search_contains: true, no_results_text: "暂无结果"});
        }, function (data) {
            Feng.error("班组查询失败!" + data.responseJSON.message + "!");
        });
        ajax.set("projectCode", projectCode);
        ajax.start();
    } else {
        $("#teamId").html(html);
        $("#teamId").chosen({search_contains: true, no_results_text: "暂无结果"});
    }
}

//班组change事件
TeamBadRecordsInfoDlg.teamChange = function (team) {
    var id = $(team).val();
    if (id) {
        var ajax = new $ax(Feng.ctxPath + '/workerGoodRecords/teamDetail/' + id, function (data) {
            $("#teamSysNo").val(data.teamSysNo);
            $("#teamLeader").val(data.teamLeader);
            $("#organizationCode").val(data.organizationCode);
            $("#companyName").val(data.companyName);
        }, function (data) {
            Feng.error("班组详情查询失败!" + data.responseJSON.message + "!");
        });
        ajax.start();
    } else {
        $("#teamSysNo").val('');
        $("#teamLeader").val('');
        $("#organizationCode").val('');
        $("#companyName").val('');
    }
};

$(function () {
    Feng.initValidator("teamBadRecordsForm", TeamBadRecordsInfoDlg.validateFields);
    Feng.initChosen();
    laydate.render({
        elem: '#occurrenceDate', max: 0
    });
});
