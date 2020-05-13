/**
 * 初始化企业黑名单记录详情对话框
 */
var CompanyBlackListInfoDlg = {
		companyBlackListInfoData : {},
	    validateFields: {
	    	blackReason: {
	            validators: {
	                notEmpty: {
	                    message: '加入黑名单原因不能为空'
	                },
	                stringLength: {
	                    max: 450,
	                    message: '最大长度为450'
	                }
	            }
	        },
	        occurrenceDate: {
	            validators: {
	                notEmpty: {
	                    message: '发生时间不能为空'
	                }
	            }
	        },
	        startTime: {
	            validators: {
	                notEmpty: {
	                    message: '生效时间不能为空'
	                }
	            }
	        },
	        endTime: {
	            validators: {
	                notEmpty: {
	                    message: '失效时间不能为空'
	                }
	            }
	        },
	        projectCode: {
	            validators: {
	                notEmpty: {
	                    message: '所属项目不能为空'
	                }
	            }
	        },
	        organizationCode: {
	            validators: {
	                notEmpty: {
	                    message: '企业名称不能为空'
	                }
	            }
	        },
	        validStatus: {
	            validators: {
	                notEmpty: {
	                    message: '时间类型不能为空'
	                }
	            }
	        },
	        note: {
	            validators: {
	            	stringLength: {
	                    max: 450,
	                    message: '最大长度为450'
	                }
	            }
	        }
	    },
	    strDate: null
	};

/**
 * 验证数据是否为空
 */
CompanyBlackListInfoDlg.validate = function () {
    $('#companyBlackListForm').data("bootstrapValidator").resetForm();
    $('#companyBlackListForm').bootstrapValidator('validate');
    return $("#companyBlackListForm").data('bootstrapValidator').isValid();
};



/**
 * 清除数据
 */
CompanyBlackListInfoDlg.clearData = function() {
    this.companyBlackListInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CompanyBlackListInfoDlg.set = function(key, val) {
    this.companyBlackListInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CompanyBlackListInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CompanyBlackListInfoDlg.close = function() {
    parent.layer.close(window.parent.CompanyBlackList.layerIndex);
}

/**
 * 收集数据
 */
CompanyBlackListInfoDlg.collectData = function() {
    this
    .set('id')
    .set('idCardType')
    .set('idCardNumber')
    .set('projectCode')
    .set('contractorOrgCode')
    .set('organizationCode')
    .set('teamSysNo')
    .set('type')
    .set('teamName')
    .set('blackReason')
    .set('occurrenceDate')
    .set('startTime')
    .set('endTime')
    .set('isValid')
    .set('note')
    .set('createDate')
    .set('createUser')
    .set('updateDate')
    .set('updateUser')
    .set('isDel')
    .set('validStatus');
}



/**
 * 提交添加
 */
CompanyBlackListInfoDlg.addSubmit = function() {
    this.clearData();
    this.collectData();

    if($("#projectCode").val()===''){
        Feng.info("请选择所属项目");
        return;
    }
    if($("#organizationCode").val()===''){
        Feng.info("请选择企业名称");
        return;
    }

    if (!this.validate()) {
        return;
    }
    if ($("#startTime").val() > $("#endTime").val()) {
    	Feng.error("生效时间不能大于失效时间!");
    } else {
    	 //提交信息
        var ajax = new $ax(Feng.ctxPath + "/companyBlackList/add", function(data){
            Feng.success("添加成功!");
            window.parent.CompanyBlackList.table.refresh();
            CompanyBlackListInfoDlg.close();
        },function(data){
            Feng.error("添加失败!" + data.responseJSON.message + "!");
        });
        ajax.set(this.companyBlackListInfoData);
        ajax.start();
    }
   
}

/**
 * 提交修改
 */
CompanyBlackListInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if($("#projectCode").val()===''){
        Feng.info("请选择所属项目");
        return;
    }
    if($("#organizationCode").val()===''){
        Feng.info("请选择企业名称");
        return;
    }

    if (!this.validate()) {
        return;
    }
    
    if ($("#startTime").val() > $("#endTime").val()) {
    	Feng.error("生效时间不能大于失效时间!");
    } else {
    	//提交信息
        var ajax = new $ax(Feng.ctxPath + "/companyBlackList/update", function(data){
            Feng.success("修改成功!");
            window.parent.CompanyBlackList.table.refresh();
            CompanyBlackListInfoDlg.close();
        },function(data){
            Feng.error("修改失败!" + data.responseJSON.message + "!");
        });
        ajax.set(this.companyBlackListInfoData);
        ajax.start();
    }
    
    
}


/*$('#organizationCode').change(function(){
	$("#socialCreditNumber").val('');
    var organizationCode = $(this).val();
    var ajax = new $ax(Feng.ctxPath + '/companyBlackList/companyDetail/' + organizationCode, function (data) {
    $("#socialCreditNumber").val(data.socialCreditNumber);
    }, function (data) {
        // Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.start();
});*/

$('#validStatus').change(function() {
    var validStatusName = $(this).find("option:selected").text();
    if (validStatusName == '自定义') {
    	 $("#startTime").val('');
   	 	 $("#endTime").val('');
    	 $('#startTime').attr('disabled', false);
    	 $('#endTime').attr('disabled', false);
    } else if (validStatusName == '一个月') {
    	 $("#startTime").val(CompanyBlackListInfoDlg.strDate);
    	 $("#endTime").val(CompanyBlackListInfoDlg.addDays(CompanyBlackListInfoDlg.strDate, 30));
    	 $('#startTime').attr('disabled', true);
    	 $('#endTime').attr('disabled', true);
    } else if (validStatusName == '三个月') {
    	 $("#startTime").val(CompanyBlackListInfoDlg.strDate);
    	 $("#endTime").val(CompanyBlackListInfoDlg.addDays(CompanyBlackListInfoDlg.strDate, 90));
    	 $('#startTime').attr('disabled', true);
    	 $('#endTime').attr('disabled', true);
    } else if (validStatusName == '六个月') {
   	 	 $("#startTime").val(CompanyBlackListInfoDlg.strDate);
   	 	 $("#endTime").val(CompanyBlackListInfoDlg.addDays(CompanyBlackListInfoDlg.strDate, 180));
   	 	 $('#startTime').attr('disabled', true);
   	     $('#endTime').attr('disabled', true);
    } else if (validStatusName == '一年') {
   	 	 $("#startTime").val(CompanyBlackListInfoDlg.strDate);
   	 	 $("#endTime").val(CompanyBlackListInfoDlg.addDays(CompanyBlackListInfoDlg.strDate, 360));
   	 	 $('#startTime').attr('disabled', true);
   	     $('#endTime').attr('disabled', true);
    } else if (validStatusName == '二年') {
   	 	 $("#startTime").val(CompanyBlackListInfoDlg.strDate);
   	 	 $("#endTime").val(CompanyBlackListInfoDlg.addDays(CompanyBlackListInfoDlg.strDate, 720));
   	 	 $('#startTime').attr('disabled', true);
   	     $('#endTime').attr('disabled', true);
    } else if (validStatusName == '五年') {
   	 	 $("#startTime").val(CompanyBlackListInfoDlg.strDate);
   	 	 $("#endTime").val(CompanyBlackListInfoDlg.addDays(CompanyBlackListInfoDlg.strDate, 1800));
   	 	 $('#startTime').attr('disabled', true);
   	     $('#endTime').attr('disabled', true);
    } else if (validStatusName == '永久') {
   	 	 $("#startTime").val(CompanyBlackListInfoDlg.strDate);
   	 	 $("#endTime").val("9999-01-01");
   	 	 $('#startTime').attr('disabled', true);
   	     $('#endTime').attr('disabled', true);
    } 
    
});

CompanyBlackListInfoDlg.addDays = function(date,days) {
	var d=new Date(date); 
    d.setDate(d.getDate()+days); 
    var resultDate = d.getFullYear()+"-";
    if((d.getMonth() + 1) < 10){
    	resultDate += "0" + (d.getMonth()+1) + "-";
	} else {
		resultDate += d.getMonth() + 1 + "-";
	}
	if (d.getDate() < 10) {
		resultDate += "0" + (d.getDate()) ;
	} else {
		resultDate += d.getDate();
	} 
    return resultDate; 
}


//项目值变动事件
CompanyBlackListInfoDlg.projectChange = function () {
    var projectCode = $("#projectCode").val();
    if(projectCode === ''){
        $("#organizationCode").chosen("destroy");
        var html = "<option value=''>请选择</option>";
        $("#organizationCode").html(html);
        $("#organizationCode").chosen({search_contains: true, no_results_text: "暂无结果"});
    }else{
        var ajax = new $ax(Feng.ctxPath + '/companyBadRecords/getCompanys/' + projectCode, function (data) {
            $("#organizationCode").chosen("destroy");
            var html = "<option value=''>请选择</option>";
            for (var i = 0; i < data.length; i++) {
                html += "<option value='" + data[i].organizationCode + "'>" + data[i].companyName + "</option>"
            }
            $("#organizationCode").html(html);
            $("#organizationCode").chosen({search_contains: true, no_results_text: "暂无结果"});
            //企业值变动事件
            $("#organizationCode").on("change",function () {
                $("#socialCreditNumber").val($("#organizationCode").val());
            })
        }, function (data) {
            Feng.error("数据加载失败!" + data.responseJSON.message + "!");
        });
        ajax.start();
	}
};


//项目值变动事件
CompanyBlackListInfoDlg.initBuss = function () {
        var ajax = new $ax(Feng.ctxPath + '/companyBadRecords/getCompanysAll', function (data) {
            $("#organizationCode").chosen("destroy");
            var html = "<option value=''>请选择</option>";
            for (var i = 0; i < data.length; i++) {
                html += "<option value='" + data[i].organizationCode + "'>" + data[i].companyName + "</option>"
            }
            $("#organizationCode").html(html);
            $("#organizationCode").chosen({search_contains: true, no_results_text: "暂无结果"});
            //企业值变动事件
            $("#organizationCode").on("change",function () {
                $("#socialCreditNumber").val($("#organizationCode").val());
            })
        }, function (data) {
            Feng.error("数据加载失败!" + data.responseJSON.message + "!");
        });
        ajax.start();

};

CompanyBlackListInfoDlg.projectChangeAndReset = function () {
    CompanyBlackListInfoDlg.projectChange();
    $("#socialCreditNumber").val('');
}

$(function() {
	 Feng.initValidator("companyBlackListForm", CompanyBlackListInfoDlg.validateFields);
	 Feng.initChosen();
	 laydate.render({
         elem: '#occurrenceDate',max:0 
     });
	 laydate.render({
         elem: '#startTime',min : 0
     });
	 laydate.render({
         elem: '#endTime',min : 0
     });
	 var curr_time = new Date();
	 CompanyBlackListInfoDlg.strDate = curr_time.getFullYear()+"-";
		if((curr_time.getMonth() + 1) < 10){
			CompanyBlackListInfoDlg.strDate += "0" + (curr_time.getMonth()+1) + "-";
		} else {
			CompanyBlackListInfoDlg.strDate += curr_time.getMonth() + 1 + "-";
		}
		if (curr_time.getDate() < 10) {
			CompanyBlackListInfoDlg.strDate += "0" + (curr_time.getDate()) ;
		} else {
			CompanyBlackListInfoDlg.strDate += curr_time.getDate();
		}
		
		
		CompanyBlackListInfoDlg.initBuss();

   /* //修改页面
    if($("#page").val() === "edit" || $("#page").val() === "view"){
			console.info(123)
        CompanyBlackListInfoDlg.projectChange();
        var organizationCode = $("#organizationCode").data("value");
        $("#organizationCode option").each(function (index,data) {
            if(data.value === organizationCode){
                data.selected = true;
            }
        });
        $("#organizationCode").chosen("destroy");
        $("#organizationCode").chosen({search_contains: true, no_results_text: "暂无结果"});
        //企业值变动事件
        $("#organizationCode").on("change",function () {
            $("#socialCreditNumber").val($("#organizationCode").val());
        })

        $('#organizationCode').prop('disabled', true).trigger("chosen:updated");
    }
*/
	/* $("#organizationCode").chosen().on("change", function (evt, data){
		     var organizationCode=data.selected;
		     $("#socialCreditNumber").val('');
		     $("#socialCreditNumber").val(organizationCode);
		     var projectCode = $("#projectCode");
		     projectCode.children("option").remove();
		     var html = "<option value=''>请选择</option>";
		     if (organizationCode != '') {
		        var ajax = new $ax(Feng.ctxPath + '/companyBadRecords/getProjectList/' + organizationCode, function (data) {
		              for (var i = 0; i < data.length; i++) {
		                  html += "<option value='" + data[i].projectCode + "'>" + data[i].projectName + "</option>"
		               }
		            }, function (data) {
		                Feng.error("数据加载失败!" + data.responseJSON.message + "!");
		            });
		            ajax.start();
		        }
		        projectCode.append(html);
		    });
	*/
});


