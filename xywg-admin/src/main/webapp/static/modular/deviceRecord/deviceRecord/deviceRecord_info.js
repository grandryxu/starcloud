/**
 * 初始化考勤记录详情对话框
 */
var DeviceRecordInfoDlg = {
    deviceRecordInfoData : {},
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
        recordTime: {
            validators: {
                notEmpty: {
                    message: '时间不能为空'
                }
            }
        },
        deviceType: {
            validators: {
                notEmpty: {
                    message: '考勤类型不能为空'
                }
            }
        }
    }
};


/**
 * 验证数据是否为空
 */
DeviceRecordInfoDlg.validate = function () {
    $('#deviceRecordForm').data("bootstrapValidator").resetForm();
    $('#deviceRecordForm').bootstrapValidator('validate');
    return $("#deviceRecordForm").data('bootstrapValidator').isValid();
};
/**
 * 清除数据
 */
DeviceRecordInfoDlg.clearData = function() {
    this.deviceRecordInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeviceRecordInfoDlg.set = function(key, val) {
    this.deviceRecordInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeviceRecordInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
DeviceRecordInfoDlg.close = function() {
    parent.layer.close(window.parent.DeviceRecord.layerIndex);
}

/**
 * 收集数据
 */
DeviceRecordInfoDlg.collectData = function() {
	this
    .set('projectCode')
    .set('teamSysNo')
    .set('recordTime')
    .set('remark')
    .set('deviceType')
}
/**
 * 提交添加
 */
DeviceRecordInfoDlg.addSubmit = function() {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    
    var ajax = new $ax(Feng.ctxPath + "/deviceRecord/saveDeviceRecord", function(data){
        Feng.success("添加成功!");
        window.parent.DeviceRecord.table.refresh();
        DeviceRecordInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    
    //teamWorker是工人id，为了简单好写，赋值给了考勤id
     this.deviceRecordInfoData.id=$("#teamWorker").val();
    ajax.set(this.deviceRecordInfoData);
    ajax.start();
}

/**
 * 班组工人下拉
 */
DeviceRecordInfoDlg.teamOnChange=function () {
    var project = $("#projectCode").val();
    var teamSysNo = $("#teamSysNo").val();
    if (project === "" || project === null) {
        Feng.info("请选择项目");
        return false;
    }
    if (teamSysNo === "" || teamSysNo === null) {
        Feng.info("请选择班组");
        return false;
    }
    var ajax=new $ax(Feng.ctxPath + "/teamMaster/getTeamMemberByTeamCode?teamSysNo=" + teamSysNo, function (data) {
    var teamMaster = $("#teamWorker");
    teamMaster.children("option").remove();
    var html = "<option value=''>请选择工人</option>";
    for (var i = 0; i < data.length; i++) {
        html += "<option value='" + data[i].id + "'>" + data[i].workerName + "</option>"
    }
        teamMaster.append(html);
    }, function (data) {
        Feng.error("工人加载失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.payRollDetailInfoData);
    ajax.start();
}

$(function() {
	Feng.initValidator("deviceRecordForm", DeviceRecordInfoDlg.validateFields);
    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = currentDate.getMonth()+1;
    var date = currentDate.getDate();
	var recordTime = laydate.render({ elem: '#recordTime' ,type:'datetime'  });

	//最小值 昨天当前时间
    recordTime.config.min.year = year;
    recordTime.config.min.month = month-1;
    recordTime.config.min.date = date-1;
    recordTime.config.min.hours = currentDate.getHours();
    recordTime.config.min.minutes = currentDate.getMinutes();
    recordTime.config.min.seconds = currentDate.getSeconds();

    //最大值 当前时间
    recordTime.config.max.year = year;
    recordTime.config.max.month = month-1;
    recordTime.config.max.date = date;
    recordTime.config.max.hours = currentDate.getHours();
    recordTime.config.max.minutes = currentDate.getMinutes();
    recordTime.config.max.seconds = currentDate.getSeconds();

    $("#projectCode").chosen().on("change", function (evt, data){
	        var projectCode=data.selected;
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
	    });
	 });
