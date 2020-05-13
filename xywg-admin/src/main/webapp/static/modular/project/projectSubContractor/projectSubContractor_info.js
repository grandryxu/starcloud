/**
 * 初始化项目参建单位详情对话框
 */
var ProjectSubContractorInfoDlg = {
    projectSubContractorInfoData: {},
    validateFields: {
        organizationCode: {
            validators: {
                notEmpty: {
                    message: '企业组织机构代码不能为空'
                },
                stringLength: {
                    max: 20,
                    message: '20'
                }
            }
        },
        bankName: {
            validators: {
                stringLength: {
                    max: 200,
                    message: '最大长度为200'
                }
            }
        },
        bankLinkNumber: {
            validators: {
                stringLength: {
                    max: 100,
                    message: '最大长度为100'
                },
                regexp: {
                    regexp: /^[0-9]*$/,
                    message: '请输入正确的银行联号'
                }
            }
        },
        bankNumber: {
            validators: {
                stringLength: {
                    max: 100,
                    message: '最大长度为100'
                },
                regexp: {
                    regexp: /^[0-9]*$/,
                    message: '请输入正确的共管银行账户'
                }
            }
        },
        payMode: {
            validators: {
                stringLength: {
                    max: 20,
                    message: '最大长度为20'
                },
                regexp: {
                    regexp: /^[0-9]*$/,
                    message: '请输入正确的工资发放模式'
                }
            }
        },
        pmName: {
            validators: {
                stringLength: {
                    max: 50,
                    message: '最大长度为50'
                }
            }
        },
        pmIdcardNumber: {
            validators: {
                stringLength: {
                    max: 20,
                    message: '最大长度为20'
                },
                regexp: {
                    regexp: /^[0-9]*$/,
                    message: '请输入正确的身份证号码'
                }
            }
        },
        pmPhone: {
            validators: {
                stringLength: {
                    max: 20,
                    message: '最大长度为20'
                },
                regexp: {
                    regexp: /^[0-9]*$/,
                    message: '请输入正确的项目经理电话'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
ProjectSubContractorInfoDlg.clearData = function () {
    this.projectSubContractorInfoData = {};
}

//公司值变动
ProjectSubContractorInfoDlg.subContractorOnChange = function () {
    var selected = $("#subContractor option:selected");
    if (selected.data("blackstarttime") !== "") {
        layer.confirm("该公司存在黑名单,原因:" + selected.data("blackreason") + " 于" + selected.data("blackstarttime") + "起，止于" + selected.data("blackendtime") + ",请谨慎操作！",
            {
                btn: ['确定'] //按钮
            }, function (index) {
                layer.closeAll();
            })
    }
    $("#organizationCode").val(selected.val());
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectSubContractorInfoDlg.set = function (key, val) {
    this.projectSubContractorInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectSubContractorInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProjectSubContractorInfoDlg.close = function () {
    parent.layer.close(window.parent.ProjectSubContractor.layerIndex);
}

ProjectSubContractorInfoDlg.close1 = function () {
    parent.layer.close(window.parent.ProjectSubContractor1.layerIndex);
}
/**
 * 收集数据
 */
ProjectSubContractorInfoDlg.collectData = function () {
    this
        .set('id')
        .set('projectCode')
        .set('organizationCode')
        .set('contractorType')
        .set('entryTime')
        .set('exitTime')
        .set('bankName')
        .set('bankNumber')
        .set('bankLinkNumber')
        .set('payMode')
        .set('pmName')
        .set('pmIdcardType')
        .set('pmIdcardNumber')
        .set('pmPhone')
        .set('isDel');
}

/**
 * 验证数据是否为空
 */
ProjectSubContractorInfoDlg.validate = function () {
    $('#ProjectSubContractorInfoForm').data("bootstrapValidator").resetForm();
    $('#ProjectSubContractorInfoForm').bootstrapValidator('validate');
    return $("#ProjectSubContractorInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
ProjectSubContractorInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    if ($("#organizationCode").val() === null || $("#organizationCode").val() === '') {
        Feng.error("企业组织机构代码不能为空!");
        return;
    }

    if ($("#contractorType").val() === null || $("#contractorType").val() === '') {
        Feng.error("参建类型不能为空!");
        return;
    }

    if ($("#entryTime").val()!='' && $("#exitTime").val()!= '' && $("#entryTime").val() > $("#exitTime").val()) {
        Feng.error("进场时间不能大于退场时间！");
        return;
    }

        //var num=0;
/*    if (true){

        console.log($("#projectCode").val());
        console.log( $("#organizationCode").val());

        Feng.error("1111");
      return false;
    }*/


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/projectSubContractor/add", function (data) {
        Feng.success("添加成功!");
        window.parent.ProjectSubContractor.table.refresh();
        ProjectSubContractorInfoDlg.close();
        ProjectSubContractorInfoDlg.close1();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.projectSubContractorInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProjectSubContractorInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    if ($("#organizationCode").val() === null || $("#organizationCode").val() === '') {
        Feng.error("企业组织机构代码不能为空!");
        return;
    }

    if ($("#contractorType").val() === null || $("#contractorType").val() === '') {
        Feng.error("参建类型不能为空!");
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/projectSubContractor/update", function (data) {
        Feng.success("修改成功!");
        if ($("#page").val() === "ZBXM") {
            window.parent.ProjectSubContractor.table.refresh();
        } else {
            window.parent.ProjectMaster.table.refresh();
        }
        ProjectSubContractorInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.projectSubContractorInfoData);
    ajax.start();
}

/**
 * 承包商
 * @type {Mixed|jQuery|HTMLElement}
 */
var organizationName = $("#organizationName");
var generalContractorsSuggest = organizationName.bsSuggest({
    keyField: "companyName",    //  input 显示的数据
    param1: "organizationCode",   //item点击事件的需要的数据1
    /* param2:"organizationCode",    //item点击事件的需要的数据2*/
    showContent: "companyName",   //下拉框显示数据
    allowNoKeyword: false,
    multiWord: true,
    separator: ",",
    getDataMethod: "url",
    url: Feng.ctxPath + "/subContractor/selectGeneralContractors/" + organizationName.val(),
    processData: function (json) {
        var i, len, data = {value: []};
        if (!json || json.length === 0) {
            return false
        }
        len = json.length;
        jsonStr = "{'value':[";
        for (i = 0; i < len; i++) {
            data.value.push(json[i])
        }
        data.defaults = "organizationName";
        return data
    }
}).on('onSetSelectValue', function (e, data) {
    $("#organizationCode").val(data.param1 === null ? "" : data.param1);
    /*$("#generalContractorCode").val(data.param2===null?"":data.param2);*/
})
/**
 * 项目
 * @type {Mixed|jQuery|HTMLElement}
 */
var projectName = $("#projectName");
var generalContractorsSuggest = projectName.bsSuggest({
    keyField: "projectName",    //  input 显示的数据
    param1: "projectCode",   //item点击事件的需要的数据1
    /* param2:"organizationCode",    //item点击事件的需要的数据2*/
    showContent: "projectName",   //下拉框显示数据
    allowNoKeyword: false,
    multiWord: true,
    separator: ",",
    getDataMethod: "url",
    url: Feng.ctxPath + "/projectMaster/getByProjectName/" + projectName.val(),
    processData: function (json) {
        var i, len, data = {value: []};
        if (!json || json.length === 0) {
            return false
        }
        len = json.length;
        jsonStr = "{'value':[";
        for (i = 0; i < len; i++) {
            data.value.push(json[i])
        }
        data.defaults = "projectName";
        return data
    }
}).on('onSetSelectValue', function (e, data) {
    $("#projectCode").val(data.param1 === null ? "" : data.param1);
    /*$("#generalContractorCode").val(data.param2===null?"":data.param2);*/
})

$(function () {
    var currDate = new Date();
    var entryTime = laydate.render({
        elem: '#entryTime',
        done: function (value, date) {
            if (value !== '') {
                exitTime.config.min.year = date.year;
                exitTime.config.min.month = date.month - 1;
                exitTime.config.min.date = date.date;
            } else {
                exitTime.config.min.year = '';
                exitTime.config.min.month = '';
                exitTime.config.min.date = '';
            }
        }
    });
    //设置结束时间
    var exitTime = laydate.render({
        elem: '#exitTime',
        done: function (value, date) {
            if (value !== '') {
                entryTime.config.max.year = date.year;
                entryTime.config.max.month = date.month - 1;
                entryTime.config.max.date = date.date;
            } else {
                entryTime.config.max.year = currDate.getFullYear();
                entryTime.config.max.month = currDate.getMonth()+1;
                entryTime.config.max.date =  currDate.getDate();
            }
        }
    });
    Feng.initChosen();

    if ($("#projectName").val() !== null && $("#projectName").val() !== "") {
        projectName.bsSuggest("disable");
    }
    Feng.initValidator("ProjectSubContractorInfoForm", ProjectSubContractorInfoDlg.validateFields);
});
