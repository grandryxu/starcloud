/**
 * 初始化版本管理详情对话框
 */
var VersionInfoDlg = {
    versionInfoData: {},
    validateFields: {
        url: {
            validators: {
                notEmpty: {
                    message: '下载路径不能为空'
                }
            }
        },
        version: {
            validators: {
                notEmpty: {
                    message: '版本不能为空'
                },
                stringLength: {
                    max: 20,
                    message: '最大长度为20'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
VersionInfoDlg.clearData = function () {
    this.versionInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
VersionInfoDlg.set = function (key, val) {
    this.versionInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
VersionInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
VersionInfoDlg.close = function () {
    parent.layer.close(window.parent.Version.layerIndex);
}

/**
 * 收集数据
 */
VersionInfoDlg.collectData = function () {
    this
        .set('id')
        .set('phoneType')
        .set('kind')
        .set('url')
        .set('version')
        .set('flag')
        .set('remark')
        .set('versionCode')
        .set('createDate')
        .set('createUser')
        .set('updateDate')
        .set('updateUser');
}

/**
 * 验证数据是否为空
 */
VersionInfoDlg.validate = function () {
    $('#versionInfoForm').data("bootstrapValidator").resetForm();
    $('#versionInfoForm').bootstrapValidator('validate');
    return $("#versionInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
VersionInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if ($("#phoneType").val() === '') {
        Feng.info("请选择手机类型")
        return;
    }
    if ($("#kind").val() === '') {
        Feng.info("请选择App端")
        return;
    }
    if ($("#flag").val() === '') {
        Feng.info("请选择是否强制更新")
        return;
    }

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/version/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Version.table.refresh();
        VersionInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.versionInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
VersionInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();
    if ($("#phoneType").val() === '') {
        Feng.info("请选择手机类型")
        return;
    }
    if ($("#kind").val() === '') {
        Feng.info("请选择App端")
        return;
    }
    if ($("#flag").val() === '') {
        Feng.info("请选择是否强制更新")
        return;
    }

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/version/update", function (data) {
        Feng.success("修改成功!");
        window.parent.Version.table.refresh();
        VersionInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.versionInfoData);
    ajax.start();
}

$(function () {
    Feng.initValidator("versionInfoForm", VersionInfoDlg.validateFields);
});
