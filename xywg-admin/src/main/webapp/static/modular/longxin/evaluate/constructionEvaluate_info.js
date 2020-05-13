/**
 * 初始化企业评价详情对话框
 */
var ConstructionEvaluateInfoDlg = {
    constructionEvaluateInfoData: {},
    validateFields: {
        massReview: {
            validators: {
                notEmpty: {
                    message: '质量评价不能为空'
                }
            }
        },
        paceReview: {
            validators: {
                notEmpty: {
                    message: '进度评价不能为空'
                }
            }
        },
        safeReview: {
            validators: {
                notEmpty: {
                    message: '安全评价不能为空'
                }
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
ConstructionEvaluateInfoDlg.clearData = function () {
    this.constructionEvaluateInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ConstructionEvaluateInfoDlg.set = function (key, val) {
    this.constructionEvaluateInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ConstructionEvaluateInfoDlg.get = function (key) {
    return $("#" + key).val();
}

ConstructionEvaluateInfoDlg.validate = function () {
    $('#evaluationForm').data("bootstrapValidator").resetForm();
    $('#evaluationForm').bootstrapValidator('validate');
    return $("#evaluationForm").data('bootstrapValidator').isValid();
};

/**
 * 关闭此对话框
 */
ConstructionEvaluateInfoDlg.close = function () {
    /*parent.layer.close(window.parent.ConstructionEvaluate.layerIndex);*/
    parent.layer.closeAll();
}

/**
 * 收集数据
 */
ConstructionEvaluateInfoDlg.collectData = function () {
    this
        .set('companyId')
        .set('companyName')
        .set('score')
        .set('grade')
        .set('remark');
}
/*

ConstructionEvaluateInfoDlg.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("只能选中表格中的某一记录！");
        return false;
    } else {
        ProjectMaster.seItem = selected[0];
        return true;
    }
};
*/


/**
 * 提交添加
 */
ConstructionEvaluateInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
/*    if ($("#massReview").val() == 0) {
        Feng.info("请对质量进行评价！")
        return;
    }
    if ($("#paceReview").val() == 0) {
        Feng.info("请对进度进行评价！")
        return;
    }
    if ($("#safeReview").val() == 0) {
        Feng.info("请对安全进行评价！")
        return;
    }*/
    var grade = $("#grade").val();
    if (grade === "") {
        Feng.error("请获取等级");
        return false;
    }

    var score = $("#score").val();

    if (score === "") {
        Feng.error("分数不能为空");
        return false;
    }

    if (score % 10 != 0) {
        Feng.error("请填写10的倍数");
        return false;
    }
    if (score < 0 || score > 100) {
        Feng.error("范围10-100");
        return false;
    }

    if (!(score % 1 == 0)) {
        Feng.error("请填写整数");
        return false;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/lxEvaluate/companyEvaluateAdd", function (data) {

        ConstructionEvaluateInfoDlg.close();
       // Feng.success("获取等级成功");
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.constructionEvaluateInfoData);
    ajax.start();
}


/*根据分数查看等级*/
ConstructionEvaluateInfoDlg.selectStart = function () {



    var score = $("#score").val();

    if (score === "") {
        Feng.error("分数不能为空");
        return false;
    }

    if (score % 10 != 0) {
        Feng.error("请填写10的倍数");
        return false;
    }
    if (score < 0 || score > 100) {
        Feng.error("范围10-100");
        return false;
    }

    if (!(score % 1 == 0)) {
        Feng.error("请填写整数");
        return false;
    }
    $("#score").attr("disabled", true);
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/lxEvaluate/selectStar", function (data) {
        $("#grade").val(data);

        Feng.success("获取等级成功!");
    }, function (data) {
        Feng.error("失败!" + data.responseJSON.message + "!");
    });
    ajax.set("score", score);
    ajax.start();
}

/*清空等级*/
ConstructionEvaluateInfoDlg.cleanGrade = function () {
    $("#grade").val("");
};


/**
 * 提交修改
 */
ConstructionEvaluateInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/constructionEvaluate/update", function (data) {
        Feng.success("修改成功!");
        window.parent.ConstructionEvaluate.table.refresh();
        ConstructionEvaluateInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.constructionEvaluateInfoData);
    ajax.start();
}

$(function () {
    $(".rating").rating({
        showClear: false
    });
    Feng.initValidator("evaluationForm", ConstructionEvaluateInfoDlg.validateFields);
});
