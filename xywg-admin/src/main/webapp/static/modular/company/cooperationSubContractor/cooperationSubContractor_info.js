var selectedConstructionCode = '';

/**
 * 初始化企业信息详情对话框
 */
var SubContractorInfoDlg = {
    subContractorInfoData : {},
    validateFields: {
        companyName: {
            validators: {
                notEmpty: {
                    message: '企业名称不能为空'
                },
                stringLength: {
                    max: 80,
                    message: '最大长度为80'
                },
                regexp: {
                    regexp: /^[\u4e00-\u9fa5/\（\）/\(\)]+$/ ,
                    message: '企业名称只能是中文'
                }
            }
        },
        organizationCode: {
            validators: {
                notEmpty: {
                    message: '组织结构编号不能为空'
                },
                stringLength: {
                    max: 10,
                    message: '最大长度为10'
                }/*,
                regexp: {
                    regexp:  /^[a-zA-Z0-9]{8}-[a-zA-Z0-9]$/ ,
                    message: '请输入正确的组织结构编号'
                }*/
            }
        },
        bizRegisterCode: {
            validators: {
                notEmpty: {
                    message: '营业执照注册号不能为空'
                },
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                },
                regexp: {
                    regexp:  /\d{15}/ ,
                    message: '请输入正确的工商营业执照注册号'
                }
            }
        },
        socialCreditNumber: {
            validators: {
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                },
                regexp: {
                    regexp:  /[0-9A-Z]{18}/,
                    message: '请输入正确的社会信用代码'
                },notEmpty: {
                    message: '社会信用代码不能为空'
                }
            }
        },
        address: {
            validators: {
                stringLength: {
                    max: 100,
                    message: '最大长度为100'
                },
//                notEmpty: {
//                    message: '企业营业地址不能为空'
//                }
            }
        },
        zipCode: {
            validators: {
                stringLength: {
                    max: 6,
                    message: '最大长度为6'
                },
                regexp: {
                    regexp:  /[1-9]\d{5}/,
                    message: '请输入正确的邮政编码'
                }
            }
        },
        representativeName: {
            validators: {
                stringLength: {
                    max: 50,
                    message: '最大长度为50'
                },
//                notEmpty: {
//                    message: '法人姓名不能为空'
//                }
            }
        },
        representativeIdcardNumber: {
            validators: {
                regexp: {
                    regexp:  /^[a-zA-Z0-9]{0,100}$/,
                    message: '请输入正确的法人证件号码'
                },
//                notEmpty: {
//                    message: '法人证件号码不能为空'
//                }
            }
        },
        registrationCapital: {
            validators: {
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                },
                regexp: {
                    regexp:  /^[0-9]*$/,
                    message: '请输入正确的注册资本'
                }
            }
        },
        phoneNumber: {
            validators: {
                stringLength: {
                    max: 20,
                    message: '最大长度为20'
                },
                regexp: {
                    regexp:  /0\d{2,3}-\d{7,8}/,
                    message: '请输入正确的电话号码'
                }
            }
        },
        faxNumber: {
            validators: {
                stringLength: {
                    max: 20,
                    message: '最大长度为20'
                },
                regexp: {
                    regexp:  /(^(\d{3,4}-)?\d{7,8})$|^((1[0-9][0-9]\d{8}$))/,
                    message: '请输入正确的传真号码'
                }
            }
        },
        email: {
            validators: {
                stringLength: {
                    max: 100,
                    message: '最大长度为100'
                },
                regexp: {
                    regexp:  /\w@\w*\.\w/,
                    message: '请输入正确的电子邮箱'
                }
            }
        },
        contactPeoplePhone: {
            validators: {
                stringLength: {
                    max: 15,
                    message: '最大长度为15'
                },
                regexp: {
                    regexp: /0\d{2,3}-\d{7,8}/,
                    message: '请输入正确的联系人办公电话'
                }
            }
        },
        businessStatus: {
            validators: {
//                notEmpty: {
//                    message: '经营状态不能为空'
//                }
            }
        },
        contactPeopleName: {
            validators: {
//                notEmpty: {
//                    message: '联系人姓名不能为空'
//                },
                stringLength: {
                    max: 50,
                    message: '最大长度为50'
                },
                regexp: {
                    regexp: /^[\u4E00-\u9FA5A-Za-z]+$/,
                    message: '请输入正确的联系人姓名'
                }
            }
        },
        homeWebsiteUrl: {
            validators: {
                stringLength: {
                    max: 300,
                    message: '最大长度为300'
                }
            }
        },
        memo: {
            validators: {
                stringLength: {
                    max: 200,
                    message: '最大长度为200'
                }
            }
        },
        contactPeopleCellPhone: {
            validators: {
//                notEmpty: {
//                    message: '联系人手机号不能为空'
//                },
                stringLength: {
                    max: 20,
                    message: '最大长度为20'
                },
                regexp: {
                    regexp:  /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/,
                    message: '请输入正确的手机号码'
                }
            }
        }, enterpriseMarketType: {
            validators: {
//                notEmpty: {
//                    message: '企业市域类别不能为空'
//                }
            }
        },
        economicNature: {
            validators: {
//                notEmpty: {
//                    message: '企业经济性质不能为空'
//                }
            }
        }
    }
};


/**
 * 清除数据
 */
SubContractorInfoDlg.clearData = function() {
    this.subContractorInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SubContractorInfoDlg.set = function(key, val) {
    this.subContractorInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SubContractorInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 收集数据
 */
SubContractorInfoDlg.collectData = function() {
    this
    .set('id')
    .set('companyName')
    .set('organizationType')
    .set('organizationCode')
    .set('bizRegisterCode')
    .set('socialCreditNumber')
    .set('address')
    .set('zipCode')
    .set('representativeName')
    .set('representativeIdcardType')
    .set('representativeIdcardNumber')
    .set('registrationCapital')
    .set('capitalCurrency')
    .set('establishDate')
    .set('phoneNumber')
    .set('faxNumber')
    .set('contactPeopleName')
    .set('contactPeoplePhone')
    .set('contactPeopleCellPhone')
    .set('email')
    .set('homeWebsiteUrl')
    .set('businessStatus')
    .set('enterpriseMarketType')
    .set('economicNature')
    .set('memo')
    .set('source')
    .set('isAudit')
    .set('createDate')
    .set('createUser')
    .set('updateDate')
    .set('updateUser')
    .set('status')
    .set('isDel');
}

/**
 * 验证数据是否为空
 */
SubContractorInfoDlg.validate = function () {
    $('#subContractorInfoForm').data("bootstrapValidator").resetForm();
    $('#subContractorInfoForm').bootstrapValidator('validate');
    return $("#subContractorInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 验证数据是否为空
 */
SubContractorInfoDlg.addSubContractorConstruction = function () {
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cooperationSubContractor/addBussSubContractorConstruction", function(data){
        Feng.success("添加成功!");
        window.parent.SubContractor.table.refresh();
        SubContractorInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON. message+ "!");
    });
    ajax.set("constructionCode",selectedConstructionCode);
    ajax.start();
};


//公司值变动
SubContractorInfoDlg.subContractorOnChange = function () {
    $("#constructionCode").val("");
}
var value = "1";
test=function () {
    $("#cRadio input[type=radio]").each(function() {
        if (this.checked) {
            value=($(this).val());
            if(value==1){//只显示社会信用代码 隐藏其他两个
                $('#pOrganizationCode').hide();
                $('#pBizRegisterCode').hide();
                $('#pSocialCreditNumber').val("");
                $('#pSocialCreditNumber').show();
            }else{//显示组织机构代码和营业执照号码   隐藏一个
                $('#pOrganizationCode').val("");
                $('#pBizRegisterCode').val("");
                $('#pOrganizationCode').show();
                $('#pBizRegisterCode').show();
                $('#pSocialCreditNumber').hide();
            }
        }
    });
}

/**
 * 提交添加
 */
SubContractorInfoDlg.addSubmit = function(index) {
    if(value==null){
        Feng.info("请选择是否三证合一");
        return;
    }
    if(value==="1"){
        //三证合一
        $("#organizationCode").val($("#socialCreditNumber").val());
        $("#bizRegisterCode").val($("#socialCreditNumber").val());
    }
    this.clearData();
    this.collectData();
    /*if($('#socialCreditNumber').val().length==0 && $('#bizRegisterCode').val().length==0){
        alert("社会统一信用代码，与营业执照号两者中必有一项为必填");
    }*/
    if(!this.validate()){
        return;
    }

    if($("#provinceCode option:selected").data("code") === 0){
        Feng.info("请选择地区");
        return;
    };

    if(typeof($("#areaCode option:selected").data("code")) === "undefined"){
        if(typeof($("#cityCode option:selected").data("code")) === "undefined"){
            this.subContractorInfoData.areaCode = $("#provinceCode option:selected").data("code");
        }else{
            this.subContractorInfoData.areaCode = $("#cityCode option:selected").data("code");
        }
    }else{
        this.subContractorInfoData.areaCode = $("#areaCode option:selected").data("code");
    }
//    if($("#representativeIdcardType").val() === ''){
//        Feng.info("请选择法人证件类型");
//        return;
//    }

    //是否同步到SMZ
    this.subContractorInfoData.isSynchro = $("input[name='isSynchro']:checked").val();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cooperationSubContractor/add", function(data){
        if(data.code === 200) {
            Feng.success("添加成功!");
            window.parent.SubContractor.table.refresh();
            SubContractorInfoDlg.close();
        }else{
            Feng.error("添加失败!" + data.message+ "!");
        }
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message+ "!");
    });
    ajax.set(this.subContractorInfoData);
    var isSynchro = $("input[name='isSynchro']:checked").val();
    var message = "";
    if(isSynchro === '0'){
        message = '确认提交 ?';
    }else{
        //message = '确认将数据发送给实名制?';
    }
    layer.confirm(message, {
        btn: ['确定', '取消'] //按钮
    }, function (index) {
        layer.close(index);
        ajax.start();
    });
}

/**
 * 提交修改
 */
SubContractorInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cooperationSubContractor/update", function(data){
        Feng.success("修改成功!");
        window.parent.SubContractor.table.refresh();
        SubContractorInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.subContractorInfoData);
    ajax.start();
}


/**
 * 公司搜索
 */
var subContractor = $("#subContractor");
var subContractorSuggest = subContractor.bsSuggest({
    param1: "socialCreditNumber",        //item点击事件的需要的数据1
    keyField: "companyName",  //  input 显示的数据
    showContent: "companyName", //下拉显示的数据
    allowNoKeyword: false,
    multiWord: true,
    separator: ",",
    getDataMethod: "url",
    url: Feng.ctxPath + "/subContractor/getAllSubContractor?companyName="+$("#subContractor").val(),
    processData: function (json) {
        if(json.length === 0){
            $("#subContractorConstructionButton").hide();
            $("#addSubContractorDiv").show();
        }else{
            $("#subContractorConstructionButton").show();
            $("#addSubContractorDiv").hide();
        }
        var i, len, data = {value: []};
        if (!json || json.length === 0) {
            return false
        }
        len = json.length;
        jsonStr = "{'value':[";
        for (i = 0; i < len; i++) {
            data.value.push(json[i])
        }
        data.defaults = "team";
        return data
    }
}).on('onSetSelectValue', function (e, data) {
    $("#constructionCode").val(data.param1);
    selectedConstructionCode = data.param1;
});

/**
 * 组织机构代码变动禁用社会信用代码
 */
SubContractorInfoDlg.organizationCodeOnKeyUp = function(){
    $("#socialCreditNumber").attr("disabled","disabled");
}

/**
 * 社会信用代码变动禁用组织机构代码
 */
SubContractorInfoDlg.socialCreditNumberOnKeyUp = function(){
    $("#organizationCode").attr("disabled","disabled");
}


SubContractorInfoDlg.close = function(){
    parent.layer.closeAll();
}

$(function() {
    laydate.render({
        elem: '#establishDate'
    });
    $('#pOrganizationCode').hide();
    $('#pBizRegisterCode').hide();
    $('#pSocialCreditNumber').show();
    Feng.initValidator("subContractorInfoForm", SubContractorInfoDlg.validateFields);

    $('#smz').hide();

});
