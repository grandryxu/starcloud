/**
 * 初始化工人黑名单记录详情对话框
 */
var WorkerBlackListInfoDlg = {
	workerBlackListInfoData : {},
	validateFields : {
		blackReason : {
			validators : {
				notEmpty : {
					message : '加入黑名单原因不能为空'
				},
				stringLength : {
					max : 450,
					message : '最大长度为450'
				}
			}
		},
		occurrenceDate : {
			validators : {
				notEmpty : {
					message : '发生时间不能为空'
				}
			}
		},
		startTime : {
			validators : {
				notEmpty : {
					message : '生效时间不能为空'
				}
			}
		},
		endTime : {
			validators : {
				notEmpty : {
					message : '失效时间不能为空'
				}
			}
		},
		workerId : {
			validators : {
				notEmpty : {
					message : '姓名不能为空'
				}
			}
		},
		validStatus : {
			validators : {
				notEmpty : {
					message : '时间类型不能为空'
				}
			}
		},
		note : {
			validators : {
				stringLength : {
					max : 450,
					message : '最大长度为450'
				}
			}
		},
		projectCode: {
            validators: {
                notEmpty: {
                    message: '所属项目不能为空'
                }
            }
        },
        teamSysNo: {
            validators: {
                notEmpty: {
                    message: '班组名称不能为空'
                }
            }
        }
	},
	strDate : null
};

/**
 * 验证数据是否为空
 */
WorkerBlackListInfoDlg.validate = function() {
	$('#workerBlackListForm').data("bootstrapValidator").resetForm();
	$('#workerBlackListForm').bootstrapValidator('validate');
	return $("#workerBlackListForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
WorkerBlackListInfoDlg.clearData = function() {
	this.workerBlackListInfoData = {};
}

/**
 * 设置对话框中的数据
 * 
 * @param key
 *            数据的名称
 * @param val
 *            数据的具体值
 */
WorkerBlackListInfoDlg.set = function(key, val) {
	this.workerBlackListInfoData[key] = (typeof val == "undefined") ? $(
			"#" + key).val() : val;
	return this;
}

/**
 * 设置对话框中的数据
 * 
 * @param key
 *            数据的名称
 * @param val
 *            数据的具体值
 */
WorkerBlackListInfoDlg.get = function(key) {
	return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
WorkerBlackListInfoDlg.close = function() {
	parent.layer.close(window.parent.WorkerBlackList.layerIndex);
}

/**
 * 收集数据
 */
WorkerBlackListInfoDlg.collectData = function() {
	this
	.set('id')
	.set('idCardType')
	.set('idCardNumber')
	.set('projectCode')
	.set('contractorOrgCode')
	.set('organizationCode')
	.set('teamSysNo')
	.set('type')
	.set('teamName')
	.set('blackReason')
	.set('occurrenceDate')
	.set('isValid')
	.set('note')
	.set('startTime')
	.set('endTime')
	.set('createDate')
	.set('createUser')
	.set('updateDate')
	.set('updateUser')
	.set('isDel')
	.set('validStatus');
}

/**
 * 提交添加
 */
WorkerBlackListInfoDlg.addSubmit = function() {

	this.clearData();
	this.collectData();

	if (!this.validate()) {
		return;
	}

	if ($("#startTime").val() > $("#endTime").val()) {
		Feng.error("生效时间不能大于失效时间!");
	} else {
		// 提交信息
		var ajax = new $ax(Feng.ctxPath + "/workerBlackList/add",
				function(data) {
					Feng.success("添加成功!");
					window.parent.WorkerBlackList.table.refresh();
					WorkerBlackListInfoDlg.close();
				}, function(data) {
					Feng.error("添加失败!" + data.responseJSON.message + "!");
				});
		ajax.set(this.workerBlackListInfoData);
		ajax.start();
	}

}

/**
 * 提交修改
 */
WorkerBlackListInfoDlg.editSubmit = function() {

	this.clearData();
	this.collectData();

	if (!this.validate()) {
		return;
	}

	if ($("#startTime").val() > $("#endTime").val()) {
		Feng.error("生效时间不能大于失效时间!");
	} else {
		// 提交信息
		var ajax = new $ax(Feng.ctxPath + "/workerBlackList/update", function(data) {
			Feng.success("修改成功!");
			window.parent.WorkerBlackList.table.refresh();
			WorkerBlackListInfoDlg.close();
		}, function(data) {
			Feng.error("修改失败!" + data.responseJSON.message + "!");
		});
		ajax.set(this.workerBlackListInfoData);
		ajax.start();
	}

}

$('#validStatus').change(
		function() {
			var validStatusName = $(this).find("option:selected").text();
			if (validStatusName == '自定义') {
				$("#startTime").val('');
				$("#endTime").val('');
				$('#startTime').attr('disabled', false);
				$('#endTime').attr('disabled', false);
			} else if (validStatusName == '一个月') {
				$("#startTime").val(WorkerBlackListInfoDlg.strDate);
				$("#endTime").val(WorkerBlackListInfoDlg.addDays(WorkerBlackListInfoDlg.strDate, 30));
				$('#startTime').attr('disabled', true);
				$('#endTime').attr('disabled', true);
			} else if (validStatusName == '三个月') {
				$("#startTime").val(WorkerBlackListInfoDlg.strDate);
				$("#endTime").val(WorkerBlackListInfoDlg.addDays(WorkerBlackListInfoDlg.strDate, 90));
				$('#startTime').attr('disabled', true);
				$('#endTime').attr('disabled', true);
			} else if (validStatusName == '六个月') {
				$("#startTime").val(WorkerBlackListInfoDlg.strDate);
				$("#endTime").val(WorkerBlackListInfoDlg.addDays(WorkerBlackListInfoDlg.strDate, 180));
				$('#startTime').attr('disabled', true);
				$('#endTime').attr('disabled', true);
			} else if (validStatusName == '一年') {
				$("#startTime").val(WorkerBlackListInfoDlg.strDate);
				$("#endTime").val(WorkerBlackListInfoDlg.addDays(WorkerBlackListInfoDlg.strDate, 360));
				$('#startTime').attr('disabled', true);
				$('#endTime').attr('disabled', true);
			} else if (validStatusName == '二年') {
				$("#startTime").val(WorkerBlackListInfoDlg.strDate);
				$("#endTime").val(WorkerBlackListInfoDlg.addDays(WorkerBlackListInfoDlg.strDate, 720));
				$('#startTime').attr('disabled', true);
				$('#endTime').attr('disabled', true);
			} else if (validStatusName == '五年') {
				$("#startTime").val(WorkerBlackListInfoDlg.strDate);
				$("#endTime").val(WorkerBlackListInfoDlg.addDays(WorkerBlackListInfoDlg.strDate, 1800));
				$('#startTime').attr('disabled', true);
				$('#endTime').attr('disabled', true);
			} else if (validStatusName == '永久') {
				$("#startTime").val(WorkerBlackListInfoDlg.strDate);
				$("#endTime").val("9999-01-01");
				$('#startTime').attr('disabled', true);
				$('#endTime').attr('disabled', true);
			}

		});

WorkerBlackListInfoDlg.addDays = function(date, days) {
	var d = new Date(date);
	d.setDate(d.getDate() + days);
	var resultDate = d.getFullYear() + "-";
	if ((d.getMonth() + 1) < 10) {
		resultDate += "0" + (d.getMonth() + 1) + "-";
	} else {
		resultDate += d.getMonth() + 1 + "-";
	}
	if (d.getDate() < 10) {
		resultDate += "0" + (d.getDate());
	} else {
		resultDate += d.getDate();
	}
	return resultDate;
}

$(function() {
	Feng.initValidator("workerBlackListForm",
			WorkerBlackListInfoDlg.validateFields);
	Feng.initChosen();
	laydate.render({
		elem : '#occurrenceDate',
		max : 0
	});
	// laydate.render({
	// 	elem : '#startTime',
	// 	min : 0
	// });
	// laydate.render({
	// 	elem : '#endTime',
	// 	min : 0
	// });
	var curr_time = new Date();
	WorkerBlackListInfoDlg.strDate = curr_time.getFullYear() + "-";
	if ((curr_time.getMonth() + 1) < 10) {
		WorkerBlackListInfoDlg.strDate += "0" + (curr_time.getMonth() + 1)
				+ "-";
	} else {
		WorkerBlackListInfoDlg.strDate += curr_time.getMonth() + 1 + "-";
	}
	if (curr_time.getDate() < 10) {
		WorkerBlackListInfoDlg.strDate += "0" + (curr_time.getDate());
	} else {
		WorkerBlackListInfoDlg.strDate += curr_time.getDate();
	}


	$('#teamSysNo').change(function() {
		var teamSysNo = $(this).val();
		$("#teamSysNo1").val('');
		$("#teamSysNo1").val(teamSysNo);
	});
});

//项目change事件
WorkerBlackListInfoDlg.projectChange = function (project) {
    var projectCode = $(project).val();
    var html = "<option value=''>请选择</option>"
    $("#teamId").chosen('destroy');
    $("#teamSysNo").val('');
    $("#workerId").chosen('destroy');
    $("#workerId").html('<option value="">请选择</option>');
    $("#workerId").chosen({search_contains: true, no_results_text: "暂无结果"});
    $("#idCardType option")[0].selected = true;
    $("#idCardNumber").val('');
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
WorkerBlackListInfoDlg.teamChange = function (team) {
    $("#workerId").chosen('destroy');
    $("#idCardType option")[0].selected = true;
    $("#idCardNumber").val('');
    $("#organizationCode").val('');
    $("#companyName").val('');
    var teamSysNo = $(team).val();
    var html = "<option value=''>请选择</option>";
    if (teamSysNo) {
    	$("#teamSysNo").val(teamSysNo);
        var ajax = new $ax(Feng.ctxPath + '/workerBlackList/getWorkersByTeamSysNo', function (data) {
            if(data && data.length>0){
                for(var i=0 ; i<data.length; i++){
                    html += "<option value='"+ data[i].idCardType + ";" + data[i].idCardNumber  +"'>"+ data[i].workerName +"</option>"
                }
                $("#workerId").html(html);
                $("#workerId").chosen({search_contains: true, no_results_text: "暂无结果"});
            }
        }, function (data) {
            Feng.error("班组详情查询失败!" + data.responseJSON.message + "!");
        });
        ajax.set({
			projectCode: $("#projectCode").val(),
			teamSysNo: teamSysNo
		});
        ajax.start();
    }else{
        $("#teamSysNo").val('');
        $("#workerId").html(html);
        $("#workerId").chosen({search_contains: true, no_results_text: "暂无结果"});
	}
};


//工人change事件
WorkerBlackListInfoDlg.workerChange = function (workerSelect) {
    var value = workerSelect.value;
    if(value){
        $("#idCardType").val(value.split(";")[0]);
        $("#idCardNumber").val(value.split(";")[1]);
        var ajax = new $ax(Feng.ctxPath + '/projectWorker/getProjectWorkerByUserInfo', function (data) {
            $("#companyName").val(data.companyName);
            $("#organizationCode").val(data.organizationCode);
        }, function (data) {
            // Feng.error("添加失败!" + data.responseJSON.message + "!");
        });
        ajax.set({
            projectCode: $("#projectCode").val() ,
            idCardType: value.split(";")[0] ,
            idCardNumber: value.split(";")[1]
        });
        ajax.start();
    }else{
        $("#idCardType").val('');
        $("#idCardNumber").val('');
        $("#companyName").val("");
        $("#organizationCode").val("");
    }
}

$(function() {
    initBeginEndDate();
});
/**
 * 初始化时间控件
 */
var initBeginEndDate=function () {
    var currDate = new Date();
    var startDate = laydate.render({
        elem: '#startTime',
        type: 'date',
        done: function (value, date) {
            if (value !== '') {
                endDate.config.min.year = date.year;
                endDate.config.min.month = date.month - 1;
                endDate.config.min.date = date.date;
            } else {
                endDate.config.min.year = '';
                endDate.config.min.month = '';
                endDate.config.min.date = '';
            }
        }
    });
    //设置结束时间
    var endDate = laydate.render({
        elem: '#endTime',
        type: 'date',
        done: function (value, date) {
            if (value !== '') {
                startDate.config.max.year = date.year;
                startDate.config.max.month = date.month - 1;
                startDate.config.max.date = date.date;
            } else {
                startDate.config.max.year = currDate.getFullYear();
                startDate.config.max.month = currDate.getMonth()+1;
                startDate.config.max.date =  currDate.getDate();
            }
        }
    });
}