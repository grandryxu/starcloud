/**
 * 初始化工人奖励记录详情对话框
 */
var WorkerGoodRecordsInfoDlg = {
    workerGoodRecordsInfoData : {},
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
        workerId: {
            validators: {
                notEmpty: {
                    message: '姓名不能为空'
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
        },
        projectCode: {
            validators: {
                notEmpty: {
                    message: '所属项目不能为空'
                }
            }
        }
    }
};

/**
 * 验证数据是否为空
 */
WorkerGoodRecordsInfoDlg.validate = function () {
    $('#workerGoodRecordsForm').data("bootstrapValidator").resetForm();
    $('#workerGoodRecordsForm').bootstrapValidator('validate');
    return $("#workerGoodRecordsForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
WorkerGoodRecordsInfoDlg.clearData = function() {
    this.workerGoodRecordsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkerGoodRecordsInfoDlg.set = function(key, val) {
    this.workerGoodRecordsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkerGoodRecordsInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
WorkerGoodRecordsInfoDlg.close = function() {
    parent.layer.close(window.parent.WorkerGoodRecords.layerIndex);
}

/**
 * 收集数据
 */
WorkerGoodRecordsInfoDlg.collectData = function() {
    this
    .set('id')
    .set('type')
    .set('idCardType')
    .set('idCardNumber')
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
WorkerGoodRecordsInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workerGoodRecords/add", function(data){
        Feng.success("添加成功!");
        window.parent.WorkerGoodRecords.table.refresh();
        WorkerGoodRecordsInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workerGoodRecordsInfoData);
    ajax.start();
}

/*$('#workerId').change(function(){
	$("#idCardType").val('');
    $("#idCardNumber").val('');
    $("#projectName").val('');
    $("#projectCode").val('');
    $("#ownerName").val('');
    var id = $(this).val();
    var ajax = new $ax(Feng.ctxPath + '/workerBlackList/workerDetail/' + id, function (data) {
    
    $("#idCardType").val(data.idCardType);
    $("#idCardNumber").val(data.idCardNumber);
    $("#projectName").val(data.projectName);
    $("#projectCode").val(data.projectCode);
    $("#ownerName").val(data.ownerName);
    }, function (data) {
        // Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.start();
});*/


WorkerGoodRecordsInfoDlg.projectChange = function () {
    var projectCode = $("#projectCode").val();
    var html = "<option value=''>请选择</option>"
    $("#idCardType").val('');
    $("#idCardNumber").val('');
    $("#companyName").val('');
    $("#organizationCode").val('');
    $("#workerId").chosen('destroy');
    if (projectCode === '') {
        $("#projectCode1").val('');
        $("#workerId").html(html);
        $("#workerId").chosen({search_contains: true, no_results_text: "暂无结果"});
        return;
    } else {
        $("#projectCode1").val($("#projectCode").val());
        var ajax = new $ax(Feng.ctxPath + '/workerBadRecords/selectWorkersByProjectCode?projectCode=' + projectCode, function (data) {
            if(data && data.length>0){
                for(var i=0 ; i<data.length; i++){
                    html += "<option value='"+ data[i].idCardType + ";" + data[i].idCardNumber  +"'>"+ data[i].workerName +"</option>"
                }
                $("#workerId").html(html);
                $("#workerId").chosen({search_contains: true, no_results_text: "暂无结果"});
            }
        }, function (data) {
            Feng.error("姓名查询失败!" + data.responseJSON.message + "!");
        });
        ajax.start();
    }
}

//工人变动事件
WorkerGoodRecordsInfoDlg.workerChange = function (workerSelect) {
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

/**
 * 提交修改
 */
WorkerGoodRecordsInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workerGoodRecords/update", function(data){
        Feng.success("修改成功!");
        window.parent.WorkerGoodRecords.table.refresh();
        WorkerGoodRecordsInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.workerGoodRecordsInfoData);
    ajax.start();
}

$(function() {
	Feng.initValidator("workerGoodRecordsForm", WorkerGoodRecordsInfoDlg.validateFields);
    Feng.initChosen();
    laydate.render({
        elem: '#occurrenceDate',max:0 
    });
});
