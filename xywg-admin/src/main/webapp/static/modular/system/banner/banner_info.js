/**
 * 初始化轮播图管理详情对话框
 */
var BannerInfoDlg = {
    bannerInfoData : {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '名称不能为空'
                },
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
BannerInfoDlg.clearData = function() {
    this.bannerInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BannerInfoDlg.set = function(key, val) {
    this.bannerInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BannerInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BannerInfoDlg.close = function() {
    parent.layer.close(window.parent.Banner.layerIndex);
}

/**
 * 收集数据
 */
BannerInfoDlg.collectData = function() {
    this
    .set('id')
    .set('organizationCode')
    .set('name')
    .set('fileName')
    .set('url')
    .set('link')
    .set('createDate')
    .set('createUser')
    .set('updateDate')
    .set('updateUser')
    .set('status')
    .set('note')
    .set('type')
    .set('order')
    .set('chooseType')
    .set('motto')
    .set('isDel');
}
/**
 * 验证数据是否为空
 */
BannerInfoDlg.validate = function () {
    $('#bannerForm').data("bootstrapValidator").resetForm();
    $('#bannerForm').bootstrapValidator('validate');
    return $("#bannerForm").data('bootstrapValidator').isValid();
};
/**
 * 提交添加
 */
BannerInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/banner/add", function(data){
        Feng.success("添加成功!");
        window.parent.Banner.table.refresh();
        BannerInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bannerInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BannerInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/banner/update", function(data){
        Feng.success("修改成功!");
        window.parent.Banner.table.refresh();
        BannerInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bannerInfoData);
    ajax.start();
}


//显示类型对应的文本框
function showType(e){
    var chooseType=$("#chooseType").val();
    if(chooseType== 1){
        //轮播图
        $("#fileName").parents('.form-group').show();
        $("#type").parents('.form-group-sss').show();
         $("#motto").parents('.form-group-sss').hide();
    }
    if(chooseType== 2){
        //名言警句
        $("#fileName").parents('.form-group').hide();
        $("#type").parents('.form-group-sss').hide();
        $("#motto").parents('.form-group-sss').show();

    }
}


$(function() {

    Feng.initValidator("bannerForm", BannerInfoDlg.validateFields);
    // 初始化头像上传
    var avatarUp = new $WebUpload("fileName");
    avatarUp.setUploadBarId("progressBar");
    avatarUp.setUploadPath(Feng.ctxPath + "/banner/upload");
    avatarUp.init();


    var chooseType=$("#chooseType").val();
    if(chooseType== 1){
        //轮播图
        $("#fileName").parents('.form-group').show();
        $("#type").parents('.form-group-sss').show();
        $("#motto").parents('.form-group-sss').hide();
    }
    if(chooseType== 2){
        //名言警句
        $("#fileName").parents('.form-group').hide();
        $("#type").parents('.form-group-sss').hide();
        $("#motto").parents('.form-group-sss').show();

    }



});
