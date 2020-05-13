/**
 * 初始化工人管理详情对话框
 */
var WorkerMasterInfoDlg = {
    workerMasterInfoData: {},
    validateFields: {
        workerName: {
            validators: {
                notEmpty: {
                    message: '姓名不能为空'
                },
                stringLength: {
                    max: 25,
                    message: '最大长度为25'
                },
                regexp: {
                    regexp: /^[\u4E00-\u9FA5]{1,5}$|^[A-Za-z]{1,10}$/,
                    message: '只允许中英文'
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
        idCardNumber: {
            validators: {
                notEmpty: {
                    message: '证件号不能为空'
                },
                stringLength: {
                    max: 20,
                    message: '最大长度为20'
                },
                regexp: {
                    regexp: /^[a-zA-Z0-9]*$/,
                    message: '格式不正确'
                }
            }
        },
        startTime: {
            validators: {
                notEmpty: {
                    message: '证件有效期开始时间必填'
                }
            }
        },
        endTime: {
            validators: {
                notEmpty: {
                    message: '证件有效期结束时间必填'
                }
            }
        },
        nation: {
            validators: {
                notEmpty: {
                    message: '民族不能为空'
                }
            }
        },
        address: {
            validators: {
                notEmpty: {
                    message: '地址不能为空'
                },
                stringLength: {
                    max: 100,
                    message: '最大长度为100'
                }
            }
        },
        gender: {
            validators: {
                notEmpty: {
                    message: '性别不能为空'
                }
            }
        },
        birthday: {
            validators: {
                notEmpty: {
                    message: '出生日期不能为空'
                }
            }
        },
        cellPhone: {
            validators: {
                stringLength: {
                    min: 9,
                    max: 20,
                    message: '请输入正确的电话号码'
                },
                notEmpty: {
                    message: '电话号码不能为空'
                }
            }
        },

        birthPlaceCode: {
            validators: {
                notEmpty: {
                    message: '籍贯不能为空'
                }
            }
        },
        workTypeCode: {
            validators: {
                notEmpty: {
                    message: '工种不能为空'
                }
            }
        },
        urgentContractName: {
            validators: {
                stringLength: {
                    max: 25,
                    message: '最大长度为25'
                }
            }
        },
        urgentContractCellphone: {
            validators: {
                stringLength: {
                    min: 9,
                    max: 20,
                    message: '请输入正确的电话号码'
                },
            }
        },
        note: {
            validators: {
                stringLength: {
                    max: 100,
                    message: '最大长度为100'
                }
            }
        },
        cardNumber: {
            validators: {
                stringLength: {
                    max: 100,
                    message: '最大长度为100'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
WorkerMasterInfoDlg.clearData = function () {
    this.workerMasterInfoData = {};

}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkerMasterInfoDlg.set = function (key, val) {
    this.workerMasterInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorkerMasterInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
WorkerMasterInfoDlg.close = function () {
    parent.layer.close(window.parent.WorkerMaster.layerIndex);
}


function IdentityCodeValid(code) {
    var city = {
        11: "北京",
        12: "天津",
        13: "河北",
        14: "山西",
        15: "内蒙古",
        21: "辽宁",
        22: "吉林",
        23: "黑龙江 ",
        31: "上海",
        32: "江苏",
        33: "浙江",
        34: "安徽",
        35: "福建",
        36: "江西",
        37: "山东",
        41: "河南",
        42: "湖北 ",
        43: "湖南",
        44: "广东",
        45: "广西",
        46: "海南",
        50: "重庆",
        51: "四川",
        52: "贵州",
        53: "云南",
        54: "西藏 ",
        61: "陕西",
        62: "甘肃",
        63: "青海",
        64: "宁夏",
        65: "新疆",
        71: "台湾",
        81: "香港",
        82: "澳门",
        91: "国外 "
    };
    var tip = "";
    var pass = true;

    if (!code || !/^(\d{15}$)|(^\d{18}$)|(^\d{17}(^\d|X|x))$/i.test(code)) {
        tip = "请输入正确的身份证号码";
        pass = false;
    }

    else if (!city[code.substr(0, 2)]) {
        tip = "地址编码错误";
        pass = false;
    }
    else {
        //18位身份证需要验证最后一位校验位
        if (code.length == 18) {
            code = code.split('');
            //∑(ai×Wi)(mod 11)
            //加权因子
            var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
            //校验位
            var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
            var sum = 0;
            var ai = 0;
            var wi = 0;
            for (var i = 0; i < 17; i++) {
                ai = code[i];
                wi = factor[i];
                sum += ai * wi;
            }
            var last = parity[sum % 11];
            if (parity[sum % 11] != code[17]) {
                tip = "请输入正确的证件号";
                pass = false;
            }
        }
    }
    if (!pass) Feng.error(tip);
    return pass;
}



/**
 * 根据证件类型和证件号查询信息
 */
//(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/).test(idCardNumber)
$("#search").click(function () {
    var idCardType = $("#idCardType").val();
    var idCardNumber = $("#idCardNumber").val();
    if (idCardType == "1" && !IdentityCodeValid(idCardNumber)) {
        return;
    }
    if ((/[\u4e00-\u9fa5]+/).test(idCardNumber)) {
        Feng.error("请输入正确的证件号");
        return;
    }
    if (idCardType == "") {
        Feng.error("请选择证件类型");
        return;
    }


    $.ajax({
            url: Feng.ctxPath + "/workerMaster/searchWorker",
            data: {
                idCardType: idCardType,
                idCardNumber: idCardNumber
            },
            type: "POST",　　//数据传输方式
            dataType: "JSON",　　//数据返回的类型
            success: function (data) {
                if (data.length == 1) {
                    $("#id").val(data[0].id);
                    $("#workerName").val(data[0].workerName);
                    $("#culturelevelType").val(data[0].culturelevelType);
                    $("#nation").val(data[0].nation);
                    $("#gender").val(data[0].gender);
                    if (data[0].isJoined == 0) {
                        $("input[id=inlineRadioisJoined1]").removeAttr("checked");
                        $("input[id=inlineRadioisJoined2]").attr("checked", "checked");
                    } else {
                        $("input[id=inlineRadioisJoined1]").attr("checked", "checked");
                        $("input[id=inlineRadioisJoined2]").removeAttr("checked");
                    }
                    if (data[0].gender == 1) {
                        $("input[id=inlineRadiogender2]").prop("checked", "checked");
                    } else {
                        $("input[id=inlineRadiogender1]").prop("checked", "checked");
                    }
                    if (data[0].hasBadMedicalHistory == 0) {
                        $("input[id=inlineRadiohasBadMedicalHistory1]").removeAttr("checked");
                        $("input[id=inlineRadiohasBadMedicalHistory2]").attr("checked", "checked");
                    } else {
                        $("input[id=inlineRadiohasBadMedicalHistory1]").attr("checked", "checked");
                        $("input[id=inlineRadiohasBadMedicalHistory2]").removeAttr("checked");
                    }
                    // $("input[name=inlineRadioisJoined]:eq(1)").prop("checked",true);
                    // $("input[name=inlineRadiogender]:eq(1)").prop("checked",true);
                    // $("input[name=inlineRadiohasBadMedicalHistory]:eq(1)").prop("checked",true);
                    $("#birthday").val(data[0].birthday);
                    $("#address").val(data[0].address);
                    $("#cellPhone").val(data[0].cellPhone);
                    $("#workDate").val(data[0].workDate);
                    $("#birthPlaceCode").val(data[0].birthPlaceCode);
                    $("#politicsType").val(data[0].politicsType);
                    $("#startTime").val(data[0].startTime);
                    $("#endTime").val(data[0].endTime);
                    $("#joinedTime").val(data[0].joinedTime);
                    $("#cultureLevelType").val(data[0].cultureLevelType);
                    $("#urgentContractName").val(data[0].urgentContractName);
                    $("#urgentContractCellphone").val(data[0].urgentContractCellphone);
                    $("#note").val(data[0].note);
                    $("#workTypeCode").val(data[0].workTypeCode);
                    $('#workerMasterForm').data("bootstrapValidator").resetForm();
                    $('#workerMasterForm').bootstrapValidator('validate');
                    $("#headImagePreId img").attr("src", Feng.imagePath + data[0].headImage);
                    // $("#headImageBtnId").hide()
                    $("#cardNumber").val(data[0].cardNumber);
                    $('#idCardType').attr('disabled', true);
                    $('#idCardNumber').attr('disabled', true);
                    $('#idCardNumber').attr('disabled', true);
                    $('#workTypeCode').attr('disabled', false);
                    $("input").attr("disabled", true);
                    $("select").attr("disabled", true);
                    $("#headImage").val(data[0].headImage);
                    $("#faceImage").val(data[0].face);
                    $("#isFaceImage").val(data[0].isFace);
                    $("#fileImg").attr('src',data[0].headImage != ''?'/labor/'+data[0].headImage:'/static/img/avatar.png');

                } else {
                    $("#workerName").val("");
                    $("#cellPhone").val("");
                    $("#address").val("");
                    $("#nation").val(01);
                    $("#birthPlaceCode").val("");
                    $("#birthday").val("");
                    $("#gender").val(0);
                    $("#inlineRadiogender1").prop("checked","checked");
                    $("#politicsType").val("");
                    $("#cultureLevelType").val("");
                    $("#urgentContractName").val("");
                    $("#birthPlaceCode").val("");
                    $("#workTypeCode").val("");
                    $("#urgentContractCellphone").val("");
                    $("#joinedTime").val("");
                    $("#workDate").val("");
                    $("#note").val("");
                    $("#cardNumber").val("");
                    $("input").attr("disabled", false);
                    $("select").attr("disabled", false);
                    $("#note").attr('disabled', false);
                }
                $("#cardNumber").attr("disabled",false);
                $("#projectCode").prop("disabled", false).trigger("chosen:updated");
                $("#teamSysNo").attr("disabled", false);
                $("#teamLeader").attr("disabled", false);
                $("#team").attr("disabled", false);
                $("#workTypeCode").attr("disabled", false);
                $("input[name='inlineRadioisTeamLeader']").attr("disabled", false);
                $('#projectCode').chosen({search_contains: true, no_results_text: "暂无结果"});
                $('#teamSysNo').chosen({search_contains: true, no_results_text: "暂无结果"});
            }
        }
    );

})

/**
 * 收集数据
 */
WorkerMasterInfoDlg.collectData = function () {
    this
        .set('id')
        .set('workerName')
        .set('idCardType')
        .set('idCardNumber')
        .set('gender')
        .set('nation')
        .set('birthday')
        .set('birthPlaceCode')
        .set('address')
        .set('headImage')
        .set('politicsType')
        .set('isJoined')
        .set('joinedTime')
        .set('cellPhone')
        .set('cultureLevelType')
        .set('hasBadMedicalHistory')
        .set('urgentContractName')
        .set('urgentContractCellphone')
        .set('workTypeCode')
        .set('workDate')
        .set('iconImage')
        .set('idImage')
        .set('isFace')
        .set('face')
        .set('isAuth')
        .set('note')
        .set('projectCode')
        .set('teamSysNo')
        .set('isTeamLeader')
        .set('idCardDate')
        .set('startTime')
        .set('endTime')
        .set('cardNumber')
}
/**
 * 验证数据是否为空
 */
WorkerMasterInfoDlg.validate = function () {
    $('#workerMasterForm').data("bootstrapValidator").resetForm();
    $('#workerMasterForm').bootstrapValidator('validate');
    return $("#workerMasterForm").data('bootstrapValidator').isValid();
};
/**
 * 提交添加
 */
WorkerMasterInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    //提交信息
    if(WorkerMasterInfoDlg.workerMasterInfoData.isTeamLeader === "0"){
        WorkerMasterInfoDlg.workerMasterInfoData.teamSysNo = $("#teamLeader").val();
    }else{
        WorkerMasterInfoDlg.workerMasterInfoData.teamSysNo = $("#team").val();
    }
    
    var projectCode =WorkerMasterInfoDlg.workerMasterInfoData.projectCode;
    
    var cardNumber = WorkerMasterInfoDlg.workerMasterInfoData.cardNumber;
    if(cardNumber != null && cardNumber != ''){
    	if(!projectCode){
    		Feng.info("请选择项目");
            return;
    	}
    }

    if($("#isEnterprise").val() === "1"){
        if(WorkerMasterInfoDlg.workerMasterInfoData.projectCode && !WorkerMasterInfoDlg.workerMasterInfoData.teamSysNo){
            Feng.info("请选择班组或班组老板");
            return;
        }
    }else{
        if(!WorkerMasterInfoDlg.workerMasterInfoData.projectCode || !WorkerMasterInfoDlg.workerMasterInfoData.teamSysNo){
            Feng.info("请选择项目或班组");
            return;
        }
    }

    //添加图片
    if($('#headImage').val() && $('#headImage').val() != '') {
        this.workerMasterInfoData.headImage = $('#headImage').val();
        this.workerMasterInfoData.face = $('#faceImage').val();
        this.workerMasterInfoData.isFace = $('#isFaceImage').val();
    }

    //判断开始时间和结束时间
    if(($('#startTime').val() && $('#startTime').val() != '' && (!$('#endTime').val() || $('#endTime').val() == ''))
        || ($('#endTime').val() && $('#endTime').val() != '' && (!$('#startTime').val() || $('#startTime').val() == ''))) {
        Feng.info("身份证有效期必须填写完整");
        return;
    }

    if($('#startTime').val() && $('#startTime').val() != ''
        && $('#endTime').val() && $('#endTime').val() != ''
        && $('#startTime').val() > $('#endTime').val()) {
        Feng.info("身份证有效期开始时间不能大于结束时间");
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workerMaster/add", function (data) {
        Feng.success("添加成功!");
        window.parent.WorkerMaster.table.refresh();
        WorkerMasterInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    console.log("提交的数据：",this.workerMasterInfoData.cardNumber);
    ajax.set(this.workerMasterInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
WorkerMasterInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    //提交信息
    if(WorkerMasterInfoDlg.workerMasterInfoData.isTeamLeader === "0"){
        WorkerMasterInfoDlg.workerMasterInfoData.teamSysNo = $("#teamLeader").val();
    }else{
        WorkerMasterInfoDlg.workerMasterInfoData.teamSysNo = $("#team").val();
    }

    WorkerMasterInfoDlg.workerMasterInfoData.workerTypeName = $("#workTypeCode option:selected").html();
    
    if($("#isEnterprise").val() === "1"){
        if(WorkerMasterInfoDlg.workerMasterInfoData.projectCode && !WorkerMasterInfoDlg.workerMasterInfoData.teamSysNo){
            Feng.info("请选择班组或班组老板");
            return;
        }
    }else{
        if(!WorkerMasterInfoDlg.workerMasterInfoData.projectCode || !WorkerMasterInfoDlg.workerMasterInfoData.teamSysNo){
            Feng.info("请选择项目或班组");
            return;
        }
    }

    this.workerMasterInfoData.pwId = $("#pwId").val();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/workerMaster/update", function (data) {
        Feng.success("修改成功!");
        window.parent.WorkerMaster.table.refresh();
        WorkerMasterInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    if($('#headImage').val() && $('#headImage').val() != '') {
        this.workerMasterInfoData.headImage = $('#headImage').val();
        this.workerMasterInfoData.face = $('#faceImage').val();
        this.workerMasterInfoData.isFace = $('#isFaceImage').val();
    }
    ajax.set(this.workerMasterInfoData);
    ajax.start();
}
/**
 * 工人奖励记录信息管理初始化
 */
var WorkerGoodRecords = {
    id: "WorkerGoodRecordsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
/**
 * 不良记录管理初始化
 */
var WorkerBadRecords = {
    id: "WorkerBadRecordsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
/**
 * 工作履历初始化
 */
var WorkExperience = {
    id: "WorkExperienceTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 工人培训初始化
 */
var ProjectTraining = {
    id: "ProjectTrainingsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};


/**
 * 初始化表格的列
 */
WorkerGoodRecords.initColumn = function () {
    return [
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型', field: 'idCardTypeName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '证件编号',
            field: 'idCardNumber',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (data) {
                return Feng.hiddenIdCard(data);
            }
        },
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '所属单位', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '奖励类型', field: 'goodRecordType', visible: true, align: 'center', valign: 'middle'},
        {title: '奖励级别', field: 'goodRecordLevelTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '奖励时间', field: 'occurrenceDate', visible: true, align: 'center', valign: 'middle'}
    ];
};
/**
 * 初始化表格的列
 */
WorkerBadRecords.initColumn1 = function () {
    return [
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型', field: 'idCardTypeName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '证件编号',
            field: 'idCardNumber',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (data) {
                return Feng.hiddenIdCard(data);
            }
        },
        {title: '所属项目', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '所属单位', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '事件类别', field: 'badRecordType', visible: true, align: 'center', valign: 'middle'},
        {title: '事件级别', field: 'badRecordLevelTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '发生时间', field: 'occurrenceDate', visible: true, align: 'center', valign: 'middle'},
        {title: '审核状态', field: 'isAuditName', visible: true, align: 'center', valign: 'middle'}
    ];
};
/**
 * 初始化表格的列
 */
WorkExperience.initColumn2 = function () {
    return [
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型', field: 'idCardTypeName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '证件编号',
            field: 'idCardNumber',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (data) {
                return Feng.hiddenIdCard(data);
            }
        },
        {title: '所属项目', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '所属公司', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '工种名称', field: 'workKind', visible: true, align: 'center', valign: 'middle'},
        {title: '进场状态', field: 'joinStatusName', visible: true, align: 'center', valign: 'middle'},
        {title: '时间', field: 'date', visible: true, align: 'center', valign: 'middle'},
    ];
};


/**
 * 初始化表格的列
 */
ProjectTraining.initColumn3 = function () {
    return [
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '培训时间', field: 'trainingTime', visible: true, align: 'center', valign: 'middle',
            formatter: function (value) {
                if (value != null) {
                    return value.substring(0, 10);
                } else
                    return value;
            }
        },
        {
            title: '培训时长(H)', field: 'trainingDuration', visible: true, align: 'center', valign: 'middle',
            formatter: function (value) {
                return value / 60;
            }
        },
        {title: '课程名称', field: 'trainingName', visible: true, align: 'center', valign: 'middle'},
        {title: '培训类型', field: 'trainingTypeCodeName', visible: true, align: 'center', valign: 'middle'},
        {title: '培训人', field: 'trainer', visible: true, align: 'center', valign: 'middle'},
        {
            title: '培训简述', field: 'description', visible: true, align: 'center', valign: 'middle', width: "600px",
            formatter: function (value) {
                if (value.length > 50) {
                    return "<a data-toggle='tooltip' title='" + value + "'>" + value.substring(0, 50) + "‧‧‧‧‧‧</a>";
                } else
                    return "<a data-toggle='tooltip' title='" + value + "'>" + value + "</a>";
            }
        }
    ];
};


ProjectTraining.formParams = function () {
    var queryData = {};
    queryData['idCardNumber'] = $("#idCardNumber").val();
    queryData['idCardType'] = $("#idCardType").val();
    return queryData;
}
WorkerGoodRecords.formParams = function () {
    var queryData = {};
    queryData['idCardNumber'] = $("#idCardNumber").val();
    queryData['idCardType'] = $("#idCardType").val();
    return queryData;
}
/**
 * 查询参数
 */
WorkerBadRecords.formParams = function () {
    var queryData = {};
    queryData['idCardNumber'] = $("#idCardNumber").val();
    queryData['idCardType'] = $("#idCardType").val();
    return queryData;
}

/**
 * 查询参数
 */
WorkExperience.formParams = function () {
    var queryData = {};
    queryData['idCardNumber'] = $("#idCardNumber").val();
    queryData['idCardType'] = $("#idCardType").val();
    return queryData;
}

$(function () {

    $("input[name='inlineRadioisTeamLeader']").on("change",function () {
        if(!$(this)[0].checked){
            return;
        }
        var value = $(this).val();
        if(value === "0"){
            $("#pTeamLeader").show();
            $("#pTeam").hide();
            $("#classNo").hide();
        }else{
            $("#pTeamLeader").hide();
            $("#pTeam").show();
            $("#classNo").show();
        }
    })

    Feng.initChosen();

    laydate.render({elem: '#birthday', max: 0});
    laydate.render({elem: '#joinedTime', max: 0});
    laydate.render({elem: '#workDate', max: 0});
    laydate.render({elem: '#startTime', max: 0});
    laydate.render({elem: '#endTime'});

    Feng.initValidator("workerMasterForm", WorkerMasterInfoDlg.validateFields);

    //初始化性别选项
    // $("#gender").val($("#genderValue").val());
    $("#politicsType").val($("#politicsTypeValue").val());
    $("#cultureLevelType").val($("#cultureLevelTypeValue").val());
    // $("#hasBadMedicalHistory").val($("#hasBadMedicalHistoryValue").val());
    // $("#isJoined").val($("#isJoinedValue").val());
    // $("#idCardType").val($("#idCardTypeValue").val());
    $("#workTypeCode").val($("#workTypeCodeValue").val());
    $("#birthPlaceCode").val($("#birthPlaceCodeValue").val());
    // $("#nation").val($("#nationValue").val());
    $("#isAuth").val($("#isAuthValue").val());
    $("#isFace").val($("#isFaceValue").val());

    // 初始化头像上传
    var avatarUp = new $WebUpload("headImage");
    avatarUp.setUploadBarId("progressBar");
    avatarUp.setUploadPath(Feng.ctxPath + "/workerMaster/upload");
    avatarUp.init();


    var defaultColunms = WorkerGoodRecords.initColumn();
    var table = new BSTable(WorkerGoodRecords.id, "/workerGoodRecords/getListByIdCard", defaultColunms);
    table.setQueryParams(WorkerGoodRecords.formParams());
    table.setPaginationType("server");
    WorkerGoodRecords.table = table.init();


    var defaultColunms1 = WorkerBadRecords.initColumn1();
    var table1 = new BSTable(WorkerBadRecords.id, "/workerBadRecords/getListByIdCard", defaultColunms1);
    table1.setQueryParams(WorkerBadRecords.formParams());
    table1.setPaginationType("server");
    WorkerBadRecords.table = table1.init();

    var defaultColunms2 = WorkExperience.initColumn2();
    var table2 = new BSTable(WorkExperience.id, "/projectWorker/getListByIdCard", defaultColunms2);
    table2.setQueryParams(WorkExperience.formParams());
    table2.setPaginationType("server");
    WorkExperience.table = table2.init();

    var defaultColunms3 = ProjectTraining.initColumn3();
    var table3 = new BSTable(ProjectTraining.id, "/projectTraining/getTrainRecordList", defaultColunms3);
    table3.setQueryParams(ProjectTraining.formParams());
    table3.setPaginationType("server");
    ProjectTraining.table = table3.init();


    var idNumber = $("#idCardNumber").val();
    var idCard = $("#idCardType").val();
    var ajax = new $ax(Feng.ctxPath + "/payRollFlow/getPayRollFlowByIdCardAndIdNumber", function (data) {
        console.log(data);
        if (JSON.stringify(data)!=='{}') {
            var html = '';
            var tableHtml = '';
            var index = 0;
            for (var k in data) {
                index++;
                var id = "paneself" + index;
                var actualTotal = 0, balanceTotal = 0, payTotal = 0;
                var divtrs = "";
                for (var i = 0; i < data[k].length; i++) {
                    actualTotal += data[k][i].actualAmount;
                    balanceTotal += data[k][i].balanceAmount;
                    if (data[k][i].payType === "工资单") {
                        payTotal += data[k][i].payAmount;
                    }
                    balanceTotal = payTotal-actualTotal;
                    divtrs += '<div class="div-table-tr">';
                    divtrs += '   <div class="div-table-td">' + data[k][i].time + '</div>';
                    divtrs += '   <div class="div-table-td">' + data[k][i].createUser + '</div>';
                    divtrs += '   <div class="div-table-td">' + data[k][i].payAmount.toFixed(2) + '</div>';
                    divtrs += '   <div class="div-table-td">' + data[k][i].actualAmount.toFixed(2) + '</div>';
                    divtrs += '   <div class="div-table-td">' + data[k][i].balanceAmount.toFixed(2) + '</div>';
                    divtrs += '   <div class="div-table-td">' + data[k][i].paySatus + '</div>';
                    divtrs += '   <div class="div-table-td">' + data[k][i].payType + '</div>';
                    divtrs += '</div>';
                }
                var tables = '<div class="div-table"><div class="div-table-tr"><div class="div-table-th">发放时间</div><div class="div-table-th">发放人</div><div class="div-table-th">应发金额(元)</div><div class="div-table-th">实发金额(元)</div><div class="div-table-th">剩余金额(元)</div><div class="div-table-th">发放状态</div><div class="div-table-th">流水来源</div></div>' + divtrs + '</div>'
                var title = '<span class="collapse-span-s">' + data[k][0].projectName + '</span>';
                title += '<span class="collapse-span-s">累计应发：' + payTotal.toFixed(2) + '元</span>';
                title += '<span class="collapse-span-s">累计已发：' + actualTotal.toFixed(2) + '元</span>';
                title += '<span class="collapse-span-s">剩余金额：' + balanceTotal.toFixed(2) + '元</span>';

                tableHtml += '<div class="panel panel-default">';
                tableHtml += '  <div class="panel-heading">';
                tableHtml += '      <h4 class="panel-title">';
                tableHtml += '          <a data-toggle="collapse" data-parent="#accordion" href="#collapse' + id + '" class="collapsed" aria-expanded="false">';
                tableHtml += title;
                tableHtml += '          </a>';
                tableHtml += '      </h4>';
                tableHtml += '  </div>';
                tableHtml += '  <div id="collapse' + id + '" class="panel-collapse collapse ' + (index == 1 ? 'in' : '') + '" aria-expanded="false" style="' + (index == 1 ? '' : 'height: 0px;') + '">';
                tableHtml += '      <div class="panel-body">';
                tableHtml += tables;
                tableHtml += '      </div>';
                tableHtml += '  </div>';
                tableHtml += '</div>';
            }
            $('#accordion').html(tableHtml);
        }else{
            $('#accordion').html('<div style="text-align: center;"><img style="margin: auto;top: 0;left: 0;bottom: 0;right: 0;" src="/static/img/notfound.png"></div>');
        }
    }, function (data) {
    });
    ajax.set({'idCard': idCard, 'idNumber': idNumber});
    ajax.start();


    var ajax = new $ax(Feng.ctxPath + "/personalCertifications/getWorkerCertifications", function (data) {
        if (data.length > 0) {
            var html = '';
            console.log(data);
            var tableHtml = '';
            for (var i = 0; i < data.length; i++) {
                html += '<div class="col-sm-3"><div class=" h-ss-250"><img src="/labor/' + data[i].path + '"></div></div>';
                // html+='<img src="http://192.168.1.124:8080/labor/'+data[i].path+'"/>';
            }
            $('#certifications').html(html);
        } else {
            $('#certifications').html('<div style="text-align: center;"><img style="margin: auto; width: auto;" src="/static/img/notfound.png"></div>');
        }
    }, function (data) {
    });
    ajax.set({'idCardType': idCard, 'idCardNumber': idNumber});
    ajax.start();

    $('.tab-pic-sss').on('click', 'img', function () {
        var src = $(this).attr('src');
        var index = layer.open({
            title: '大图查看',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            btn: [],
            content: '<div class="layer-big-pic"><img style="width:100%;" src="' + src + '"></div>'
        });
    });


    $("#classNo").hide();

    if($("#id").val()){
        if($("input[name='inlineRadioisTeamLeader']")[0].checked){
            $("#pTeamLeader").hide();
            $("#pTeam").show();
            $("#classNo").show();
        }else{
            $("#pTeamLeader").show();
            $("#pTeam").hide();
            $("#classNo").hide();
        }
        WorkerMasterInfoDlg.projectCodeChange($("#teamSysNoValue").val());
    }
});


function getDataRow(h) {
    var row = document.createElement('tr'); //创建行
    var timeCell = document.createElement('td'); //创建第一列id
    timeCell.innerHTML = h.time; //填充数据
    row.appendChild(timeCell); //加入行 ，下面类似
    var payAmountCell = document.createElement('td');//创建第二列name
    payAmountCell.innerHTML = h.payAmount;
    row.appendChild(payAmountCell);
    var actualAmountCell = document.createElement('td');//创建第三列job
    actualAmountCell.innerHTML = h.actualAmount;
    row.appendChild(actualAmountCell);
    var balanceAmountCell = document.createElement('td');//创建第三列job
    balanceAmountCell.innerHTML = h.balanceAmount;
    row.appendChild(actualAmountCell);
    var paySatusCell = document.createElement('td');//创建第三列job
    paySatusCell.innerHTML = h.paySatus;
    row.appendChild(paySatusCell);
    return row; //返回tr数据  
}

/**
 *
 * @param selected 被选中的班组编号
 */
WorkerMasterInfoDlg.projectCodeChange = function (val) {
    var projectCode = $("#projectCode").val();
    if(projectCode){
        var ajax = new $ax(Feng.ctxPath + "/teamMaster/getList?projectCode="+ projectCode, function (data) {
            if(data && data.length > 0){
                var options = "<option value=''>请选择</option>";
                var options2 = "<option value=''>请选择</option>";
                for(var i=0;i<data.length;i++){
                    if(data[i].teamLeaderIdNumber){
                        var sst = $("#teamSysNoValue").val();
                        var ttt = data[i].teamSysNo;
                        console.log("sss"+sst+"ttt"+ttt);
                        if($("#id").val() && ($("#teamSysNoValue").val() == data[i].teamSysNo+"")){
                            options += "<option value='"+ data[i].teamSysNo +"' selected>"+ data[i].teamLeader +"</option>"
                            options2 += "<option value='"+ data[i].teamSysNo +"' selected>"+ data[i].teamName +"</option>"
                        }else{
                            options += "<option value='"+ data[i].teamSysNo +"'>"+ data[i].teamLeader +"</option>"
                            options2 += "<option value='"+ data[i].teamSysNo +"'>"+ data[i].teamName +"</option>"
                        }
                    } else {
                        options2 += "<option value='"+ data[i].teamSysNo +"'>"+ data[i].teamName +"</option>"
                    }
                }
                $("#teamLeader").html(options);
                $("#team").html(options2);
                $("#teamLeader").trigger("chosen:updated");
                $("#team").trigger("chosen:updated");
            }else{
                $("#teamLeader").html("<option value=''>请选择</option>");
                $("#team").html("<option value=''>请选择</option>");
            }
        }, function (data) {
            Feng.error("添加失败!" + data.responseJSON.message + "!");
        });
        $("#pCardNumber").show();
        ajax.set("projectCode",projectCode);
        ajax.start();
    }else{
        $("#teamLeader").html("<option value=''>请选择</option>");
        $("#team").html("<option value=''>请选择</option>");
        $("#pCardNumber").hide();
    }

};

WorkerMasterInfoDlg.openMonitor = function () {
    var sys = WorkerMasterInfoDlg.getBrowseVersion();
    var url = '/workerMaster/workerMaster_monitor';
    console.log(JSON.stringify(sys));
    if((sys.browser === 'chrome' && Number(sys.ver.split('.')[0]) > 60) || (sys.browser === 'firefox' && Number(sys.ver.split(','[0])) > 60)) {
        url = '/workerMaster/workerMaster_monitor_h5';
    }
    var index = layer.open({
        type: 2,
        title: '拍照',
        area: ['80%', '80%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + url
    });
    this.layerIndex = index;
};

WorkerMasterInfoDlg.closeMonitor = function () {
    layer.close(this.layerIndex);
};

WorkerMasterInfoDlg.getBrowseVersion = function() {
    var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var re =/(msie|firefox|chrome|opera|version).*?([\d.]+)/;
    var m = ua.match(re);
    Sys.browser = m?m[1].replace(/version/, "'safari"):"";
    Sys.ver = m?m[2]:"";
    return Sys;
}