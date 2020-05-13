/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var ProDataNumberDlg = {
    ProDataNumberData: {},
    strDate:null ,
    validateFields: {
        projectName: {
            validators: {
                notEmpty: {
                    message: '项目名称不能为空'
                }
            }
        },
        dataNumber: {
            validators: {
                notEmpty: {
                    message: '数据指纹不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
ProDataNumberDlg.clearData = function () {
    this.ProDataNumberData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProDataNumberDlg.set = function (key, val) {
    this.ProDataNumberData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 获取对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProDataNumberDlg.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
ProDataNumberDlg.close = function () {
    parent.layer.closeAll();
};
/**
 * 收集数据
 */
ProDataNumberDlg.collectData = function() {
    this
        .set('id')
        .set('projectName')
        .set('dataNumber')
        .set('area');
};

/**
 * 验证数据是否为空
 */
ProDataNumberDlg.validate = function () {
    $('#proDataNumberInfoForm').data("bootstrapValidator").resetForm();
    $('#proDataNumberInfoForm').bootstrapValidator('validate');
    return $("#proDataNumberInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
ProDataNumberDlg.addSubmit = function() {
    this.clearData();
    this.collectData();

    if(!this.validate()){
        return;
    }
    if ($("#provinceCode option:selected").data("code") === 0) {
        Feng.info("请选择区域");
        return;
    }

    if (typeof($("#areaCode option:selected").data("code")) === "undefined") {
        if (typeof($("#cityCode option:selected").data("code")) === "undefined") {
            this.ProDataNumberData.area = $("#provinceCode option:selected").data("code");
        } else {
            this.ProDataNumberData.area = $("#cityCode option:selected").data("code");
        }
    } else {
        this.ProDataNumberData.area = $("#areaCode option:selected").data("code");
    }

    if (this.ProDataNumberData.area === "") {
        Feng.error("项目区域不能为空！");
        return false;
    }
    this.ProDataNumberData.area = $("#cityCode option:selected").data("code");
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/prodataNumber/add", function(data){
        if(data.code === -2){
            Feng.error("添加失败!" + data.message);
            return;
        }
        Feng.success("添加成功!");
        try{
            window.parent.ProDataNumber.table.refresh();
        } catch(e){
        }
        ProDataNumberDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message);
    });
    ajax.set(this.ProDataNumberData);
    ajax.start();
};

/**
 * 提交修改
 */
ProDataNumberDlg.editSubmit = function() {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    if ($("#provinceCode option:selected").data("code") === 0) {
        Feng.info("请选择区域");
        return;
    }

    if (typeof($("#areaCode option:selected").data("code")) === "undefined") {
        if (typeof($("#cityCode option:selected").data("code")) === "undefined") {
            this.ProDataNumberData.area = $("#provinceCode option:selected").data("code");
        } else {
            this.ProDataNumberData.area = $("#cityCode option:selected").data("code");
        }
    } else {
        this.ProDataNumberData.area = $("#areaCode option:selected").data("code");
    }

    if (this.ProDataNumberData.area === "") {
        Feng.error("项目区域不能为空！");
        return false;
    }
    this.ProDataNumberData.area = $("#cityCode option:selected").data("code");

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/prodataNumber/update", function(data){
        if(data.code === -2){
            Feng.error("修改失败!" + data.message);
            return;
        }
        Feng.success("修改成功!");
        try{
            window.parent.ProDataNumber.table.refresh();
        } catch(e){
        }
        ProDataNumberDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message);
    });
    console.log(JSON.stringify(this.ProDataNumberData));
    ajax.set(this.ProDataNumberData);
    ajax.start();
};

$(function() {
    Feng.initValidator("proDataNumberInfoForm", ProDataNumberDlg.validateFields);
    var ajax = new $ax(Feng.ctxPath + "/subContractor/getArea", function(data){
        console.log(data);
        if(data.provinceName || data.cityName || data.districtName){
            //初始化三级联动
            $("#distpicker").distpicker({
                province: typeof(data.provinceName)==="undefined"?"":data.provinceName,
                city: typeof(data.cityName)==="undefined"?"":data.cityName,
                district: typeof(data.districtName)==="undefined"?"":data.districtName
            });
        }else{
            $("#distpicker").distpicker();
        }
    },function(data){
        $("#distpicker").distpicker();
    });
    ajax.set({"areaId":$("#areaId").val()+""});
    ajax.start();
});

