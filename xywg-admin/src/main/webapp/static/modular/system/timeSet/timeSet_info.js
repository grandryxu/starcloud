/**
 * 初始化时间设置详情对话框
 */
var TimeSetInfoDlg = {
    timeSetInfoData : {},
    validateFields: {
		   start: {
	            validators: {
	            	 notEmpty: {
		                    message: '上班打卡时间不能为空'
		             }
	            }
	        },	
	        end: {
	            validators: {
	            	 notEmpty: {
		                    message: '下班打卡时间不能为空'
		             }
	            }
	        },	
		    remark: {
	            validators: {
	                stringLength: {
	                    max: 250,
	                    message: '最大长度为250'
	                }
	            }
	        }
	    }
};

/**
 * 验证数据是否为空
 */
TimeSetInfoDlg .validate = function () {
    $('#TimeSetForm').data("bootstrapValidator").resetForm();
    $('#TimeSetForm').bootstrapValidator('validate');
    return $("#TimeSetForm").data('bootstrapValidator').isValid();
};

/**
 * 清除数据
 */
TimeSetInfoDlg.clearData = function() {
    this.timeSetInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TimeSetInfoDlg.set = function(key, val) {
    this.timeSetInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TimeSetInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TimeSetInfoDlg.close = function() {
    parent.layer.close(window.parent.TimeSet.layerIndex);
}

/**
 * 收集数据
 */
TimeSetInfoDlg.collectData = function() {
    this
    .set('id')
    .set('projectCode')
    .set('type')
    .set('start')
    .set('end')
    .set('remark');
}

/**
 * 提交修改
 */
TimeSetInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    
   var starts = $("#start").val();
   var ends = $("#end").val();
    if ($("#start").val() > $("#end").val()) {
    	Feng.info("上班时间不能大于下班时间!");
    } else {
    	var ajax = new $ax(Feng.ctxPath + "/timeSet/getTime", function (data) {
    		if (data.start > ends || data.end < starts) {
    			//提交信息
    	        var ajax = new $ax(Feng.ctxPath + "/timeSet/update", function(data){
    	            Feng.success("修改成功!");
    	            window.parent.TimeSet.table.refresh();
    	            TimeSetInfoDlg.close();
    	        },function(data){
    	            Feng.error("修改失败!" + data.responseJSON.message + "!");
    	        });
    	        ajax.set(TimeSetInfoDlg.timeSetInfoData);
    	        ajax.start();
    		} else {
    			Feng.info("打卡区间发生冲突!");
    		}
        }, function (data) {
            Feng.error("数据加载失败!" + data.responseJSON.message + "!");
        });
    	 ajax.set(this.timeSetInfoData);
         ajax.start();
    }
}


$(function() {
	Feng.initValidator("TimeSetForm", TimeSetInfoDlg.validateFields);
	laydate.render({
        elem: '#start',type:'time'
    });
	laydate.render({ elem: '#end' ,type:'time'  });

});
