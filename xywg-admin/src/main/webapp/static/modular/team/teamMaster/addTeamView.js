/**
 * 初始化班组管理详情对话框
 */
var TeamMasterInfoDlg = {
    teamMasterInfoData : {},
    validateFields: {
        teamName: {
            validators: {
                notEmpty: {
                    message: '班组名称不能为空'
                },
                regexp: {
                    regexp: /^[\u4E00-\u9FA5A-Za-z0-9_]+$/,
                    message: '格式不正确'
                },
                stringLength: {
                    max: 50,
                    message: '最大长度为50'
                }
            }
        },
        contact:{
            validators: {

                regexp: {
                    regexp: /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/,
                    message: '请输入正确的手机号码'
                }
            }
        },
        responsiblePersonIdNumber:{
            validators: {
                regexp: {
                    regexp: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
                    message: '身份证号码有误'
                }
            }

        },note: {
            validators: {
                stringLength: {
                    max: 200,
                    message: '最大长度为200'
                }
            }
        },
        teamLeader: {
            validators: {

                stringLength: {
                    max: 50,
                    message: '最大长度为50'
                }
            }

        }
    }
};

/**
 * 清除数据
 */
TeamMasterInfoDlg.clearData = function() {
    this.teamMasterInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TeamMasterInfoDlg.set = function(key, val) {
    this.teamMasterInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TeamMasterInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TeamMasterInfoDlg.close = function() {
  // parent.layer.close(window.parent.TeamMaster.layerIndex);
   // parent.layer.close(window.close());
    parent.layer.closeAll();
}

/**
 * 收集数据
 */
TeamMasterInfoDlg.collectData = function() {
	this
	.set('id')
	.set('teamSysNo')
    .set('leaderId')
	.set('projectCode')
	.set('organizationCode')
	.set('teamName')
	.set('teamLeader')
	.set('contact')
	.set('teamLeaderIdNumber')
	.set('responsiblePersonIdNumber')
	.set('note')
	.set('status')
	.set('isDel');
}

/**
 * 验证数据是否为空
 */
TeamMasterInfoDlg .validate = function () {
    $('#TeamMasterInfoForm').data("bootstrapValidator").resetForm();
    $('#TeamMasterInfoForm').bootstrapValidator('validate');
    return $("#TeamMasterInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
TeamMasterInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if(!this.validate()){
        return;
    }
    this.teamMasterInfoData.projectName = parent.$('#projectName').val();
    this.teamMasterInfoData.projectCode = parent.$('#projectCode').val();

    // this.teamMasterInfoData.organizationCode = parent.$('#buildCorporationCode').val();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teamMaster/add", function(data){
    	//2019-05-15 jln 重新加载班组列表
    	if(window.parent.WorkerMasterInfoDlg != undefined){
    		window.parent.WorkerMasterInfoDlg.projectCodeChange();
    	}
        Feng.success("添加成功!");
       // window.parent.TeamMaster.table.refresh();
        TeamMasterInfoDlg.close();
        Feng.success("添加成功!");
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.teamMasterInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TeamMasterInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    if(!this.validate()){
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teamMaster/update", function(data){
        Feng.success("修改成功!");
        window.parent.TeamMaster.table.refresh();
        TeamMasterInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.teamMasterInfoData);
    ajax.start();
}

$(function() {
	Feng.initValidator("TeamMasterInfoForm", TeamMasterInfoDlg.validateFields);
	Feng.initChosen();
    $("input[name='projectCode']").val(parent.$('#projectCode').val());
    console.info(parent.$('#projectCode').val());
});

/**
 * 打开班组长
 */
TeamMasterInfoDlg.openSelectTeamLeader = function () {
    var index = layer.open({
        type: 2,
        title: '选择班组长',
        area: ['50%', '80%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/workerMaster/openAddWorkerSearchDialog'
    });
    this.layerIndex = index;
}


/**
 * 选择工人弹框结果
 * @param data
 */
var dialogEnd=function (data) {
    layer.closeAll();
    //提交信息
    $("input[name='leaderId']").val(data.id);
    $("input[name='contact']").val(data.cellPhone);
    $("input[name='teamLeaderIdNumber']").val(data.idCardNumber);
    $("input[name='teamLeader']").val(data.workerName);


}
