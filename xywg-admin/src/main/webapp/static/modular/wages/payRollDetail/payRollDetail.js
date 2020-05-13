/**
 * 工资单管理初始化
 */
var PayRoll = {
    id: "PayRollDetailTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1 ,
    selectImg:[],      //选中的附件,
    lookImgSelectPayRollId:'' //点击查看附件时的结算单编号
};


/**
 * 初始化表格的列
 */
PayRoll.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '所属公司', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目编号', field: 'projectCode', visible: false, align: 'center', valign: 'middle'},
        {title: '班组', field: 'teamName', visible: true, align: 'center', valign: 'middle'},
        {title: '应发金额（元）', field: 'payAmount', visible: true, align: 'center', valign: 'middle',
            formatter:function(data){
                return data.toFixed(2);
            }},
        {title: '实发金额（元）', field: 'actualAmount', visible: true, align: 'center', valign: 'middle',
            formatter:function(data){
                return data.toFixed(2);
            }},
        {title: '剩余金额（元）', field: 'balanceAmount', visible: true, align: 'center', valign: 'middle',
            formatter:function(data){
                return data.toFixed(2);
            }},
        {title: '发放类型', field: 'type', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                var str = ''
                if (value === 1) {
                    str = "按工种发放";
                }
                if (value === 2) {
                    str = "按计工单发放";
                }
                return str;
            }},
        {title: '保存状态', field: 'saveStatus', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                var str = ''
                if (value === 1) {
                    str = "暂存";
                }
                if (value === 2) {
                    str = "提交";
                }
                return str;
            }},
        {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                var str = ''
                if(row.saveStatus === 2){
                    if (value === 2) {
                        str = "分包已审核";
                    }else if (value === 3) {
                        str = "总包已复核";
                    }else if (value === 50) {
                        str = "已发放";
                    }else{
                        str = "待审核";
                    }
                }
                return str;
            }},
        {title: '分包审核人', field: 'constructValidName', visible: true, align: 'center', valign: 'middle'},
        {title: '总包审核人', field: 'contractValidName', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
        {
            title: '操作', visible: true, align: 'center', valign: 'middle', formatter: function (val, row, index) {
                var operas = '';
                if(row.fileNums === 0){
                    operas += "<a href='javascript:void(0)' title='暂无附件' onclick='openAccessory(" + row.id + ")'  disabled='disabled'><i class='fa fa-eye-slash'></i></a>";
                }else{
                    operas += "<a href='javascript:void(0)' title='查看附件' onclick='openAccessory(" + row.id + ")'><i class='fa fa-eye text-blue'></i></a>"
                }
            operas += "<a href='javascript:void(0)' payRollId=" + row.id + " class='uploadFile' onClick='uploadFileBut(this)' title='附件上传 仅限图片格式'>&nbsp;&nbsp;<i class='fa fa-upload text-blue'></i></a>";
            return operas;
        }
        }
    ];
};

/**
 * 上传
 */
function uploadFileBut(val) {
    $("#uploadFileInput").val("");
    $("#uploadFileInput").trigger("click");
    PayRoll.PayRollUploadId = $(val).attr("payRollId");
};


/**
 * 真正的上传
 */
function fileUpload(page) {
    var maxsize = 100*1024*1024;
    var filesize = document.getElementById("uploadFileInput").files[0].size;
    if(filesize>maxsize) {
        Feng.error("上传的附件文件不能超过100M！！");
        return;
    }

    if($('#uploadFileInput')[0].files.length === 0){
        return;
    }
    var myform = new FormData($("#form1")[0]);
    myform.append('payRollUploadId', PayRoll.PayRollUploadId);
    $.ajax({
        url: Feng.ctxPath + "/payRoll/uploadAccessory",
        type: "POST",
        data: myform,
        contentType: false,
        processData: false,
        async:false,
        success: function (data) {
            if(data.code!=200){
                Feng.error(data.message);
            }else{
                Feng.success(data.message);
                PayRoll.table.refresh();
            }
        },
        error: function (data) {
            Feng.error(data.message);
        }
    });
}

/**
 * 查看附件图片
 */
function openAccessory(id) {
    PayRoll.lookImgSelectPayRollId = id;
    var imgLength=0;
    var html='';
    var ajax = new $ax(Feng.ctxPath + "/payRoll/getAccessoryPicture", function (data) {
        var pc=data.data;
        imgLength = pc.length;
        PayRoll.pictureNums = imgLength;

        for(var i=0;i<pc.length;i++){
            html+= ("<div class='col-sm-3' style='margin-top: 5px;'>" +
                "<div onclick='changeSelectGou(this)' style='border:2px solid #DBDBDB;height: 300px;line-height: 300px;text-align: center;'>" +
                "<img class='selectGouImg' style='width:auto;height:auto;max-width:100%;max-height:100%;text-align: center;vertical-align: middle;' " +
                "onmouseover='onmouseoverImg(this)' onmouseout='onmouseoutImg(this)' " +
                "src='"+pc[i].src+"' alt='"+pc[i].alt+"' pid='"+pc[i].pid+"'>" +
                "<img class='changeSelectGouImg'  style='position: absolute;right: 16px;display:none;' src='static/img/select_gou3.png'  >" +
                "</div>" +
                "</div>")
        }
        $("#imagesPicture").html(html);

    }, function (data) { });
    ajax.set("id", id);
    ajax.start();

    if(imgLength===0){
        layer.msg("暂无附件");
        return;
    }
    var index = layer.open({
        type: 1,
        title: '附件详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: $("#accessoryPictureDiv").html() ,
        end: function () {
            PayRoll.selectImg = [];
        }
    });
}

/**
 * 选中图片
 */
function changeSelectGou(ele) {
    if($(ele).children(".changeSelectGouImg").css('display')==="none"){
        $(ele).children(".changeSelectGouImg").css('display',"inline-block");
        PayRoll.selectImg.push($(ele).children(".selectGouImg").attr('pid')+'');
    }else{
        $(ele).children(".changeSelectGouImg").css('display',"none");
        //PayRoll.selectImg.remove($(ele).attr('pid'));
        PayRoll.selectImg.splice($.inArray($(ele).children(".selectGouImg").attr('pid')+'',PayRoll.selectImg),1);
    }
}

/**
 * 删除附件
 */
function deletePicture(){
	if(PayRoll.selectImg.length > 0){
	    Feng.confirm("确认删除选中的附件?",function(){
	        var ajax = new $ax(Feng.ctxPath + "/settlement/deleteAccessoryPicture", function (data) {
	            /*Feng.success("删除成功!");*/
	            layer.closeAll();
	            if(PayRoll.pictureNums === PayRoll.selectImg.length){
	                PayRoll.table.refresh();
	                Feng.success("删除成功!")
	            }else{
	                openAccessory(PayRoll.lookImgSelectPayRollId);
	            }
	            PayRoll.selectImg = [];
	        }, function (data) {
	            Feng.error("删除失败!"/* + data + "!"*/);
	        });
	        ajax.set("ids", JSON.stringify(PayRoll.selectImg));
	        ajax.start();
	    });
	}else{
		Feng.info("请先选中附件！");
	}
}


function onmouseoverImg(eve){
    //layer.msg(11111111);
}

function onmouseoutImg(eve){
    //layer.msg(222222);
}

/**
 * 检查是否选中
 */
PayRoll.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        PayRoll.seItem = selected[0];
        return true;
    }
};


/**
 * 导出工资单
 */
PayRoll.export = function () {
    if($("#startDate").val() != '' && $("#endDate").val() != ''){
        if($("#startDate").val() > $("#endDate").val()){
            Feng.error("创建时间的起始时间不能大于结束时间！");
            return;
        }
    }
    window.location.href = Feng.ctxPath + "/payRollDetail/export?"
        + "projectCode=" + $("#projectCode").val()
        + "&teamSysNo=" + $("#team").val()
        + "&startTime=" + $("#startDate").val()
        + "&endTime=" + $("#endDate").val()
};


/**
 * 点击添加工资单
 */
PayRoll.openAddPayRollDetail = function () {
    var index = layer.open({
        type: 2,
        title: '添加工资单',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin:'layer-detail',
        content: Feng.ctxPath + '/payRollDetail/payRollDetail_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看工资单详情
 */
PayRoll.openPayRollDetail = function () {
    if (this.check()) {
        if (hasOperationAuth()) {
            //项目级总包
            if (PayRoll.seItem.isGeneralContractorOperation === 0) {
                Feng.error("您无权操作参建单位"+ PayRoll.seItem.companyName+"的数据!");
                return;
            }
        }

        if (PayRoll.seItem.saveStatus === 2) {
            Feng.error("已提交的无法编辑");
            return false;
        }
        var index = layer.open({
            type: 2,
            title: '工资单详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin:'layer-no-title',
            content: Feng.ctxPath + '/payRollDetail/payRollDetail_update?payRollCode=' + PayRoll.seItem.payRollCode + "&type=" + PayRoll.seItem.type +"&saveStatus=" +PayRoll.seItem.saveStatus
        });
        this.layerIndex = index;
    }
};

/**
 * 删除工资单
 */
PayRoll.delete = function () {
    if (this.check()) {
        var selected = $('#' + PayRoll.id).bootstrapTable('getSelections');
        var ids = '';
        for (var i = 0; i < selected.length; i++) {
            if (hasOperationAuth()) {
                //项目级总包
                if (selected[i].isGeneralContractorOperation === 0) {
                    Feng.error("您无权操作参建单位"+ selected[i].companyName+"的数据!");
                    return;
                }
            }

            if (selected[i].saveStatus === 2) {
                Feng.error("已提交的无法删除!");
                return false;
            }
            ids += selected[i].id + ',';
        }
        ids = ids.substring(0, ids.length - 1);
        Feng.confirm("工资单删除无法恢复,是否继续？",function(){
            var ajax = new $ax(Feng.ctxPath + "/payRoll/deleteIds", function (data) {
                Feng.success("删除成功!");
                PayRoll.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("ids", ids);
            ajax.start();
        });
    }
};

/**
 * 查询工资单列表
 */
PayRoll.search = function () {
    if($("#startDate").val() != '' && $("#endDate").val() != ''){
        if($("#startDate").val() > $("#endDate").val()){
            Feng.error("创建时间的起始时间不能大于结束时间！");
            return;
        }
    }
    var queryData = {};
    queryData['projectCode'] = $("#projectCode").val();
    queryData['teamSysNo'] = $("#team").val();
    queryData['startTime'] = $("#startDate").val();
    queryData['endTime'] = $("#endDate").val();
    PayRoll.table.refresh({query: queryData});
};

/**
 * 项目值变动事件
 */
PayRoll.projectOnChange = function () {
    var value = $("#projectCode").val();
    var ajax = new $ax(Feng.ctxPath + "/teamMaster/getList?projectCode=" + value , function (data) {
        var htmlStr="<option value=\"\">请选择</option>";
        for(var i=0;i<data.length;i++){
            htmlStr+=("<option value='"+data[i].teamSysNo+"'>"+data[i].teamName+"</option>")
        }
        $("#team").html(htmlStr);
    });
    ajax.start();
};

//复审
PayRoll.review = function () {
    var selected = $('#' + PayRoll.id).bootstrapTable('getSelections');
    if(selected.length === 0 ){
        Feng.error("请选择一条记录!");
        return;
    }
    var ids = '';
    for (var i = 0; i < selected.length; i++) {
        if(selected[i].status !== 2){
            Feng.error("只能复审分包已审核的数据!");
            return;
        }
        ids += selected[i].id + ',';
    }
    ids = ids.substring(0, ids.length - 1);
    //审核通过
    var ajax = new $ax(Feng.ctxPath + "/payRollDetail/toggleStatus", function (data) {
        Feng.success("操作成功!");
        PayRoll.table.refresh();
    }, function (data) {
        Feng.error("操作失败!" + data.responseJSON.message + "!");
    });
    ajax.set({
        ids:ids,
        status: 3
    });
    ajax.start();

/*

    layer.open({
        type: 1,
        shade: 0,
        title: '复审', //不显示标题
        area: ['420px', '240px'],
        content: $('#checkHtml'), //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
        btn: ['确定', '取消'] //只是为了演示
        ,yes: function(){
            layer.msg(111);
        }
        ,no: function(){
            layer.closeAll();
        }
    });
*/

}

//审核
PayRoll.toExamine = function () {
    var selected = $('#' + PayRoll.id).bootstrapTable('getSelections');
    if(selected.length === 0 ){
        Feng.error("请选择一条记录!");
        return;
    }
    var ids = '';
    for (var i = 0; i < selected.length; i++) {
        if (hasOperationAuth()) {
            //项目级总包
            if (selected[i].isGeneralContractorOperation === 0) {
                Feng.error("您无权操作参建单位"+ selected[i].companyName+"的数据!");
                return;
            }
        }

        ids += selected[i].id + ',';
        if (selected[i].saveStatus !== 2) {
            Feng.error("只能审核已提交的工资单!");
            return false;
        }
        if(selected[i].status !== 0){
            Feng.error("只能审核待审核的数据!");
            return;
        }
    }
    ids = ids.substring(0, ids.length - 1);
    //审核通过
    var ajax = new $ax(Feng.ctxPath + "/payRollDetail/toggleStatus", function (data) {
        Feng.success("操作成功!");
        PayRoll.table.refresh();
    }, function (data) {
        Feng.error("操作失败!" + data.responseJSON.message + "!");
    });
    ajax.set({
        ids:ids,
        status: 2
    });
    ajax.start();
}


//双击查看事件
PayRoll.onDblClickRow = function (e) {
    var index = layer.open({
        type: 2,
        title: '工资单详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin:'layer-no-title',
        content: Feng.ctxPath + '/payRollDetail/payRollDetail_view?payRollCode=' + e.payRollCode + "&type=" + e.type
    });
    layer.full(index);
    this.layerIndex = index;
}

/**
 * 提交
 */
PayRoll.submit = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/payRoll/submit", function (data) {
            Feng.success("提交成功!");
            PayRoll.table.refresh();
        }, function (data) {
            Feng.error("提交失败!" + data.responseJSON.message + "!");
        });
        var selected = $('#' + PayRoll.id).bootstrapTable('getSelections');
        var ids = '';
        for (var i = 0; i < selected.length; i++) {
            if (hasOperationAuth()) {
                //项目级总包
                if (selected[i].isGeneralContractorOperation === 0) {
                    Feng.error("您无权操作参建单位" + selected[i].companyName + "的数据!");
                    return;
                }
            }

            if (selected[i].saveStatus === 2) {
                Feng.error("只能提交已暂存的工资单!");
                return false;
            }
            ids += selected[i].id + ',';
        }
        ids = ids.substring(0, ids.length - 1);
        ajax.set("ids", ids);
        ajax.start();
    }
}

/**
 * 发放
 */
PayRoll.grantPayRoll = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/payRoll/grantPayRoll", function (data) {
            Feng.success("工资发放成功!");
            PayRoll.table.refresh();
        }, function (data) {
            Feng.error("工资发放失败!" + data.responseJSON.message + "!");
        });
        var selected = $('#' + PayRoll.id).bootstrapTable('getSelections');
        var ids = '';
        for (var i = 0; i < selected.length; i++) {
            if (hasOperationAuth()) {
                //项目级总包
                if (selected[i].isGeneralContractorOperation === 0) {
                    Feng.error("您无权操作参建单位" + selected[i].companyName + "的数据!");
                    return;
                }
            }

            if (selected[i].status !== 3) {
                Feng.error("只能发放总包审核通过的工资单!");
                return false;
            }
            ids += selected[i].id + ',';
        }
        ids = ids.substring(0, ids.length - 1);
        ajax.set("ids", ids);
        ajax.start();
    }
}

$(function () {
    var currDate = new Date();
    var startDate = laydate.render({
        elem: '#startDate',
        type: 'date',
        done: function (value, date) {
            if (value !== '') {
                endDate.config.min.year = date.year;
                endDate.config.min.month = date.month - 1;
                endDate.config.min.date = date.date;
            } else {
                endDate.config.min.year = '';
                endDate.config.min.month = '';
                endDate.config.min.date = '';
            }
        },
        max:0
    });
    //设置结束时间
    var endDate = laydate.render({
        elem: '#endDate',
        type: 'date',
        done: function (value, date) {
            if (value !== '') {
                startDate.config.max.year = date.year;
                startDate.config.max.month = date.month - 1;
                startDate.config.max.date = date.date;
            } else {
                startDate.config.max.year = currDate.getFullYear();
                startDate.config.max.month = currDate.getMonth()+1;
                startDate.config.max.date =  currDate.getDate();
            }
        },
        max:0
    });

    //重置的时间集合
    PayRoll.resetDate = [startDate,endDate];

    var defaultColunms = PayRoll.initColumn();
    var table = new BSTable(PayRoll.id, "/payRoll/list", defaultColunms);
    table.setPaginationType("server");
    table.onDblClickRow = PayRoll.onDblClickRow;
    PayRoll.table = table.init();
    // $("#projectCode").chosen();
    Feng.initChosen();

    var date=new Date;
    var year=date.getFullYear();
    var month=date.getMonth()+1;
    month =(month<10 ? "0"+month:month);
    var currYearMonth = year.toString()+"-"+month.toString();
    laydate.render({
        elem: '#month',type: 'month',value:currYearMonth
    });
});


function  resetDate() {
    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = currentDate.getMonth()+1;
    var date = currentDate.getDate();

    for(var i=0;i<PayRoll.resetDate.length; i++) {
        var dateObject = PayRoll.resetDate[i];

        //去除最小时间限制
        dateObject.config.min.year = '';
        dateObject.config.min.month = '';
        dateObject.config.min.date = '';

        dateObject.config.max.year = year;
        dateObject.config.max.month = month-1;
        dateObject.config.max.date = date;

        //设置当前时间
        dateObject.config.dateTime.year = year;
        dateObject.config.dateTime.month = month;
        dateObject.config.dateTime.date = date;
        // dateObject.startDate.config.dateTime.hours = '时';
        // dateObject.startDate.config.dateTime.minutes = '分';
        // dateObject.startDate.config.dateTime.seconds = '秒';
    }
}
