/**
 * 初始化企业信息详情对话框
 */
var SubContractorInfoDlg = {
    subContractorInfoData : {},
    validateFields: {
    	bidPrice: {
            validators: {
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                },
                regexp: {
                    regexp:  /(^[1-9](\d+)?(\.\d{1,6})?$)|(^0$)|(^\d\.\d{1,6}$)/,
                    message: '请输入正确的注册资本'
                }
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
 * 关闭此对话框
 */
SubContractorInfoDlg.close = function() {
    parent.layer.closeAll();
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
    .set('areaCode')
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

var value;
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
 * 验证数据是否为空
 */
SubContractorInfoDlg.validate = function () {
    $('#subContractorInfoForm').data("bootstrapValidator").resetForm();
    $('#subContractorInfoForm').bootstrapValidator('validate');
    return $("#subContractorInfoForm").data('bootstrapValidator').isValid();
};

function isRealNum(val){
    // isNaN()函数 把空串 空格 以及NUll 按照0来处理 所以先去除
    if(val === "" || val ==null){
        return false;
    }
    if(!isNaN(val)){
        return true;
    }else{
        return false;
    }
} 


/**
 * 提交添加
 */
SubContractorInfoDlg.addSubmit = function() {
	var bidId = $("#bidId").val();
	var bidPrice = '';
	var flag = false;
	 $("input[name='price']").each(function(i,val) {
		 if(flag){return;}
		 if(!isRealNum($(this).val())){
			 flag = true;
			 return;
		 }
		 if(i==0){
			 bidPrice += $(this).val() ;
		 }else{
			 bidPrice += "|" +  $(this).val() ;
		 }
	});
	 if(flag){
		 Feng.error("价格应为数字");
		 return;
	 }
	 this.subContractorInfoData.inviteId = bidId;
	 this.subContractorInfoData.bidPrice = bidPrice;
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/lxInviteBid/addFixMark", function(data){
            window.parent.SubContractor.table.refresh();
            parent.layer.closeAll();
           Feng.success("竞标成功!");
            
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message+ "!");
    });
    ajax.set(this.subContractorInfoData);
    ajax.start();
}


/**
 * 提交修改
 */
SubContractorInfoDlg.editSubmit = function() {
	
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    if($("#provinceCode option:selected").data("code") === 0){
        Feng.info("请选择地区");
        return;
    };
    //省市区
    if(typeof($("#areaCodes option:selected").data("code")) === "undefined"){
        if(typeof($("#cityCode option:selected").data("code")) === "undefined"){
            this.subContractorInfoData.areaCode = $("#provinceCode option:selected").data("code");
        }else{
            this.subContractorInfoData.areaCode = $("#cityCode option:selected").data("code");
        }
    }else{
        this.subContractorInfoData.areaCode = $("#areaCodes option:selected").data("code");
    }
//    if($("#representativeIdcardType").val() === ''){
//        Feng.info("请选择法人证件类型");
//        return;
//    }
    if($("#qualifyLevel").val()){
    	this.subContractorInfoData.qualifyLevel = $("#qualifyLevel").val();
    }
    
    if($("#businessType").val()){
    	this.subContractorInfoData.businessType = $("#businessType").val();
    }
    
    if($("#businessScope").val()){
    	this.subContractorInfoData.businessScope = $("#businessScope").val();
    }
    
    
    if($("#representativePhone").val()){
    	this.subContractorInfoData.representativePhone = $("#representativePhone").val();
    }
    
    if($("#businessMaterial").val()){
    	this.subContractorInfoData.businessMaterial = $("#businessMaterial").val();
    }
    
    //是否同步到SMZ
    this.subContractorInfoData.isSynchro = $('input[name="isSynchro"]:checked').val(); 
    
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/lxSubContractor/update", function(data){
        Feng.success("修改成功!");
        try{
            window.parent.SubContractor.table.refresh();
        } catch(e){
        }
        SubContractorInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.subContractorInfoData);
    ajax.start();
};

$(function() {
    $("#inlineRadio1").attr("disabled", true);
    $("#inlineRadio2").attr("disabled", true);
    $("#inlineRadioEdit1").attr("disabled", true);
    $("#inlineRadioEdit2").attr("disabled", true);
    var bizRegisterCode=$("#bizRegisterCode").val();
    var organizationCode=$("#organizationCode").val();
    if(bizRegisterCode==organizationCode){
        $('#pOrganizationCode').hide();
        $('#pBizRegisterCode').hide();
        $('#organizationCodeParentId').hide();
        $('#bizRegisterCodeParentId').hide();
        $("#inlineRadio1").attr("checked", true);
        $("#inlineRadioEdit1").attr("checked", true);
    }else{
        $("#pSocialCreditNumber").hide();
        $('#pSocialCreditNumberParentId').hide();
        $('#socialCreditNumberParentId').hide();
        $("#inlineRadio2").attr("checked", true);
        $("#inlineRadioEdit2").attr("checked", true);
    }
    laydate.render({
        elem: '#establishDate'
    });
    Feng.initValidator("subContractorInfoForm", SubContractorInfoDlg.validateFields);
    var ajax = new $ax(Feng.ctxPath + "/subContractor/getArea", function(data){
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
