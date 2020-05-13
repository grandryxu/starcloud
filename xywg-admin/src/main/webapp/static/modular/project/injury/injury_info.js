/**
 * 初始化工伤管理详情对话框
 */
var InjuryInfoDlg = {
    injuryInfoData: {},
    validateFields: {
        remark: {
            validators: {
                stringLength: {
                    max: 255,
                    message: '最大长度为255'
                }
            }
        },
        projectCode: {
            validators: {
                notEmpty: {
                    message: '项目不能为空'
                }
            }
        },
        teamSysNo: {
            validators: {
                notEmpty: {
                    message: '班组不能为空'
                }
            }
        },
        teamWorker: {
            validators: {
                notEmpty: {
                    message: '工人不能为空'
                }
            }
        },
        occureTime: {
            validators: {
                notEmpty: {
                    message: '时间不能为空'
                }
            }
        },
        type: {
            validators: {
                notEmpty: {
                    message: '工伤类型不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
InjuryInfoDlg.clearData = function () {
    this.injuryInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
InjuryInfoDlg.set = function (key, val) {
    this.injuryInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
InjuryInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
InjuryInfoDlg.close = function () {
    parent.layer.close(window.parent.Injury.layerIndex);
}

/**
 * 收集数据
 */
InjuryInfoDlg.collectData = function () {
    this
        .set('id')
        .set('projectCode')
        .set('teamWorker')
        .set('type')
        .set('occureTime')
        .set('remark');
}

/**
 * 验证数据是否为空
 */
InjuryInfoDlg.validate = function () {
    $('#injuryForm').data("bootstrapValidator").resetForm();
    $('#injuryForm').bootstrapValidator('validate');
    return $("#injuryForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
InjuryInfoDlg.addSubmit = function () {
    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/injury/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Injury.table.refresh();
        InjuryInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.injuryInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
InjuryInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/injury/update", function (data) {
        Feng.success("修改成功!");
        window.parent.Injury.table.refresh();
        InjuryInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.injuryInfoData);
    ajax.start();
}

/**
 * 班组工人下拉
 */
InjuryInfoDlg.teamOnChange = function () {
    var project = $("#projectCode").val();
    var teamSysNo = $("#teamSysNo").val();
    if (project === "" || project === null) {
        alert("请选择项目");
        return false;
    }
    if (teamSysNo === "" || teamSysNo === null) {
        alert("请选择班组");
        return false;
    }
    if(teamSysNo){
        var ajax = new $ax(Feng.ctxPath + "/teamMaster/getTeamMemberByTeamCode?teamSysNo=" + teamSysNo, function (data) {
            var teamMaster = $("#teamWorker");
            var html = "<option value=''>请选择工人</option>";
            for (var i = 0; i < data.length; i++) {
                html += "<option value='" + data[i].id + "'>" + data[i].workerName + "</option>"
            }
            teamMaster.html(html);
        }, function (data) {
            Feng.error("工人加载失败!" + data.responseJSON.message + "!");
        });
        ajax.set(this.payRollDetailInfoData);
        ajax.start();
    }else{
        $("#teamWorker").html("<option value=''>请选择工人</option>");
    }

}

$(function () {
    Feng.initValidator("injuryForm", InjuryInfoDlg.validateFields);
    var occureTimeHidden = $('#occureTimeHidden').val();
    if(occureTimeHidden){
        occureTimeHidden = occureTimeHidden.substr(0,occureTimeHidden.indexOf('.'));
    }
    laydate.render({
        elem: '#occureTime',
        max: 1,
        type: 'datetime',
        value:occureTimeHidden
    });
    $("#projectCode").chosen().on("change", function (evt, data) {
        $("#teamWorker").html("<option value=''>请选择</option>");
        var projectCode = data.selected;
        if(projectCode){
            var ajax = new $ax(Feng.ctxPath + "/teamMaster/getList?projectCode=" + projectCode, function (data) {
                var teamMaster = $("#teamSysNo");
                teamMaster.children("option").remove();
                var html = "<option value=''>请选择班组</option>";
                for (var i = 0; i < data.length; i++) {
                    html += "<option value='" + data[i].teamSysNo + "'>" + data[i].teamName + "</option>"
                }
                teamMaster.append(html);
            }, function (data) {
                Feng.error("班组加载失败!" + data.responseJSON.message + "!");
            });
            ajax.set(this.payRollDetailInfoData);
            ajax.start();
        }else{
            $("#teamSysNo").html("<option value=''>请选择班组</option>");
        }

    });
    var idFlag = $('#id').val();
    if (idFlag != null && idFlag != "") {
    	$('#projectName').attr("disabled","disabled");
    	$('#workerName').attr("disabled","disabled");
    	$('#teamName').attr("disabled","disabled");
    	$('#idCardNumber').attr("disabled","disabled");
	}
	
});