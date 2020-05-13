/**
 * 初始化班组评价详情对话框
 */
var TeamMasterEvaluateInfoDlg = {
    teamMasterEvaluateInfoData: {},
    validateFields: {
    	option1: {
            validators: {

                between: {min: 1, max: 5, message: "质量评价不能为空"}


            }
        },
        option2: {
            validators: {
                between: {min: 1, max: 5, message: '进度评价不能为空'}

            }
        },
        option3: {
            validators: {
                between: {min: 1, max: 5, message: '安全评价不能为空'}


            }
        },
        note: {
            validators: {
                stringLength: {
                    max: 500,
                    message: '最大长度为500'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
TeamMasterEvaluateInfoDlg.clearData = function () {
    this.teamMasterEvaluateInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TeamMasterEvaluateInfoDlg.set = function (key, val) {
    this.teamMasterEvaluateInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TeamMasterEvaluateInfoDlg.get = function (key) {
    return $("#" + key).val();
}

TeamMasterEvaluateInfoDlg.validate = function () {
    $('#evaluationForm').data("bootstrapValidator").resetForm();
    $('#evaluationForm').bootstrapValidator('validate');
    return $("#evaluationForm").data('bootstrapValidator').isValid();
};

/**
 * 关闭此对话框
 */
TeamMasterEvaluateInfoDlg.close = function () {
    parent.layer.close(window.parent.TeamMaster.layerIndex);
    /*parent.layer.closeAll();*/
}

/**
 * 收集数据
 */
TeamMasterEvaluateInfoDlg.collectData = function () {
    this
        .set('id')
        .set('organizationCode')
        .set('projectCode')
        .set('teamSysNo')
        .set('option1')
        .set('option2')
        .set('option3')
        .set('note');
}

/**
 * 提交添加
 */
TeamMasterEvaluateInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();
/*    if (!this.validate()) {
        return;
    }*/

       if($("#option1").val()==0){
            console.info($("#massReview").val());
           Feng.error("请对质量进行评价！")
            return;
        }
        if($("#option2").val()==0){
            Feng.error("请对进度进行评价！")
            return;
        }
        if($("#option3").val()==0){
            Feng.error("请对安全进行评价！")
            return;
        }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teamMaster/addEvaluate", function (data) {
        Feng.success("添加成功!");
        TeamMasterEvaluateInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.teamMasterEvaluateInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TeamMasterEvaluateInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/teamMaster/updateEvaluate", function (data) {
        Feng.success("修改成功!");
        window.parent.ConstructionEvaluate.table.refresh();
        TeamMasterEvaluateInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.teamMasterEvaluateInfoData);
    ajax.start();
}

$(function () {
    $(".rating").rating({
        showClear: false
    });
    Feng.initValidator("evaluationForm", TeamMasterEvaluateInfoDlg.validateFields);
});
