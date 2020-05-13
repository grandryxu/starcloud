/**
 * 初始化工人奖励记录详情对话框
 */
var TeamGoodRecordsInfoDlg = {
	teamGoodRecordsInfoData : {},
	validateFields: {
		details: {
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
                    message: '奖励时间不能为空'
                }
            }
        },
        goodRecordTypeCode: {
            validators: {
                notEmpty: {
                    message: '奖励类型不能为空'
                }
            }
        },
        goodRecordLevelType: {
            validators: {
                notEmpty: {
                    message: '奖励级别不能为空'
                }
            }
        }
    }
};


/**
 * 验证数据是否为空
 */
TeamGoodRecordsInfoDlg.validate = function () {
    $('#teamGoodRecordsForm').data("bootstrapValidator").resetForm();
    $('#teamGoodRecordsForm').bootstrapValidator('validate');
    return $("#teamGoodRecordsForm").data('bootstrapValidator').isValid();
};


/**
 * 清除数据
 */
TeamGoodRecordsInfoDlg.clearData = function() {
    this.teamGoodRecordsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TeamGoodRecordsInfoDlg.set = function(key, val) {
    this.teamGoodRecordsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TeamGoodRecordsInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TeamGoodRecordsInfoDlg.close = function() {
    parent.layer.close(window.parent.TeamGoodRecords.layerIndex);
}

/**
 * 收集数据
 */
TeamGoodRecordsInfoDlg.collectData = function() {
    this
    .set('id')
    .set('type')
    .set('teamSysNo')
    .set('projectCode')
    .set('organizationCode')
    .set('goodRecordTypeCode')
    .set('goodRecordLevelType')
    .set('occurrenceDate')
    .set('details')
    .set('createDate')
}


/**
 * 提交添加
 */
TeamGoodRecordsInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teamGoodRecords/add", function(data){
        Feng.success("添加成功!");
        window.parent.TeamGoodRecords.table.refresh();
        TeamGoodRecordsInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.teamGoodRecordsInfoData);
    ajax.start();
}

//项目change事件
TeamGoodRecordsInfoDlg.projectChange = function (project) {
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
TeamGoodRecordsInfoDlg.teamChange = function (team) {
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
/**
 * 提交修改
 */
TeamGoodRecordsInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teamGoodRecords/update", function(data){
        Feng.success("修改成功!");
        window.parent.TeamGoodRecords.table.refresh();
        TeamGoodRecordsInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.teamGoodRecordsInfoData);
    ajax.start();
}

$(function() {
	Feng.initValidator("teamGoodRecordsForm", TeamGoodRecordsInfoDlg.validateFields);
	Feng.initChosen();
	laydate.render({
        elem: '#occurrenceDate',max:0 
    });
});
