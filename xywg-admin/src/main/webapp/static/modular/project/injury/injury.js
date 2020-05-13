/**
 * 工伤管理管理初始化
 */
var Injury = {
    id: "InjuryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    injuryUploadId: '',
    layerIndex: -1,
    selectImg:[],      //选中的附件,
    lookImgSelectSettlementId:'' //点击查看附件时的结算单编号
};

/**
 * 初始化表格的列
 */
Injury.initColumn = function () {
    return [
        	{field: 'selectItem', check: true},
            {title: '编号', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '所属企业', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
            {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
            {title: '所属班组', field: 'teamName', visible: true, align: 'center', valign: 'middle'},
            {title: '工人名称', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
            {title: '证件类型', field: 'idCardType', visible: true, align: 'center', valign: 'middle'},
            {title: '证件号码', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',
                formatter:function (data) {
                    return Feng.hiddenIdCard(data);
                }
            },
            {title: '工伤类型', field: 'type', visible: true, align: 'center', valign: 'middle'},
            {title: '发生时间', field: 'time', visible: true, align: 'center', valign: 'middle'},
            {title: '添加时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
            {title: '添加人', field: 'createUser', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle',
            	 formatter: function (value) {
                     if(!value||value==""){
                    	 return "";
                     }
                     return "<span title='"+value+"' class='ddd' style='width:100px'>"+value+"</span>";
                 }
            	
            },
        {
            title: '操作', visible: true, align: 'center', valign: 'middle', formatter: function (val, row, index) {
            return "<a href='javascript:void(0)' title='查看附件' onclick='openAccessory(" + row.id + ")'><i class='fa fa-eye text-blue'></i></a>" +
                "<a href='javascript:void(0)' settlementCode=" + row.id + " class='uploadFile' onClick='uploadFileBut(this)' title='附件上传,仅限图片'>&nbsp;&nbsp;<i class='fa fa-upload text-blue'></i></a>";
        }
        },
    ];
};

/**
 * 上传
 */
function uploadFileBut(val) {
    $("#uploadFileInput").trigger("click");
    Injury.injuryUploadId = $(val).attr("settlementCode");
    console.info("结算单id:" + Injury.injuryUploadId);
};

/**
 * 查看附件图片
 */
function openAccessory(id) {
    Injury.lookImgSelectSettlementId = id;
    var imgLength=0;
    var html='';
    var ajax = new $ax(Feng.ctxPath + "/injury/getAccessoryPicture", function (data) {
        var pc=data.data;
        imgLength = pc.length;

        for(var i=0;i<pc.length;i++){
            html+= ("<div class='col-sm-3' style='margin-top: 5px'>" +
                "<img onclick='changeSelectGou(this)' style='height: 300px; width:100%' src='"+pc[i].src+"' alt='"+pc[i].alt+"' pid='"+pc[i].pid+"'>" +
                "<img style='position: absolute;right: 16px;display:none;' src='static/img/select_gou3.png'  ></div>")
        }
        $("#imagesPicture").html(html);

    }, function (data) { });
    ajax.set("id", id+"");
    ajax.start();

    if(imgLength===0){
        layer.msg("没有附件");
        return;
    }
    var index = layer.open({
        type: 1,
        title: '附件详情',
        area: ['90%', '650px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: $("#accessoryPictureDiv").html()
    });
    Injury.selectImg=[];
}
/**
 * 删除附件
 */
function deletePicture(){
    if(Injury.selectImg.length === 0){
        Feng.info("请先选中附件！");
        return;
    }
    Feng.confirm("确认删除选中的附件?",function(){
        var ajax = new $ax(Feng.ctxPath + "/settlement/deleteAccessoryPicture", function (data) {
            /*Feng.success("删除成功!");*/
            layer.closeAll();
//            openAccessory(Injury.lookImgSelectSettlementId);
        }, function (data) {
            Feng.error("删除失败!" + data + "!");
        });
        ajax.set("ids", JSON.stringify(Injury.selectImg));
        ajax.start();
    });
}

/**
 * 选中图片
 */
function changeSelectGou(ele) {
    if($(ele).next().css('display')==="none"){
        $(ele).next().css('display',"inline-block");
        Injury.selectImg.push($(ele).attr('pid')+'');
    }else{
        $(ele).next().css('display',"none");
        //Settlement.selectImg.remove($(ele).attr('pid'));
        Injury.selectImg.splice($.inArray($(ele).attr('pid')+'',Injury.selectImg),1);
    }
}
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
    //上传
    if($('#uploadFileInput')[0].files.length === 0){
        return;
    }

    var myform = new FormData();
    myform.append('settlementCode', Injury.injuryUploadId);
    myform.append('file', $('#uploadFileInput')[0].files[0]);
    $.ajax({
        url: Feng.ctxPath + "/injury/uploadAccessory",
        type: "POST",
        data: myform,
        contentType: false,
        processData: false,
        success: function (data) {
            if(data.code!=200){
                Feng.error(data.message);
            }else{
                Feng.success(data.message);
            }
        },
        error: function (data) {
            Feng.error(data.message);
        }
    });
}

Injury.onLoadSuccess = function (data) {
    Injury.data = data.rows;
}

/**
 * 检查是否选中
 */
Injury.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Injury.seItem = selected[0];
        return true;
    }
};

/**
 * 双击查看
 */
Injury.searchInfo = function () {
        var index = layer.open({
            type: 2,
            title: '工伤详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: 'layer-detail',
            content: Feng.ctxPath + '/injury/detail/' + Injury.seItem.id
        });
        this.layerIndex = index;
}




/**
 * 点击添加工伤管理
 */
Injury.openAddInjury = function () {
    var index = layer.open({
        type: 2,
        title: '添加工伤管理',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/injury/injury_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看工伤管理详情
 */
Injury.openInjuryDetail = function () {
    if (Injury.check()) {
        if (hasOperationAuth()) {
            //项目级总包
            if (Injury.seItem.isGeneralContractorOperation === 0) {
                Feng.error("您无权操作参建单位"+ Injury.seItem.companyName+"的数据!");
                return;
            }
        }
        var index = layer.open({
            type: 2,
            title: '工伤管理详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: 'layer-no-title',
            content: Feng.ctxPath + '/injury/injury_update/' + Injury.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除工伤管理
 */
Injury.delete = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        for(var i=0;i<selected.length;i++){
            if (hasOperationAuth()) {
                //项目级总包
                if (selected[i].isGeneralContractorOperation === 0) {
                    Feng.error("您无权操作参建单位"+ selected[i].companyName+"的数据!");
                    return;
                }
            }
        }
        layer.confirm('确认删除？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            var ids = "";
            for (var i = 0; i < selected.length; i++) {
                ids += selected[i].id + ","
            }
            ids = ids.substring(0, ids.length - 1);
            var ajax = new $ax(Feng.ctxPath + "/injury/delete", function (data) {
                Feng.success("删除成功!");
                Injury.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("ids", ids);
            ajax.start();
            layer.close(index);
        });
    }
};

/**
 * 查询工伤管理列表
 */
Injury.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['type'] = $("#type").val();
    queryData['projectCode'] = $("#projectCode").val();
    queryData['teamSysNo'] = $("#team").val();
    Injury.table.refresh({query: queryData});
};


Injury.setClass=function(){
//	$('#team').attr("disabled","disabled");
	if ($("#projectCode").val() != null && $("#projectCode").val() != "") {
		$('#team').removeAttr("disabled");
		
	} else {
		$('#team').attr("disabled","disabled");
	}
	
};



$(function () {
    var defaultColunms = Injury.initColumn();
    var table = new BSTable(Injury.id, "/injury/list", defaultColunms);
    table.setPaginationType("server");
    table.onDblClickRow = Injury.searchInfo;//双击事件所对应的方法 要放在init之前
    Injury.table = table.init();
    $("#projectCode").chosen().on("change", function (evt, data){
        var projectCode=data.selected;
		 var ajax = new $ax(Feng.ctxPath + "/teamMaster/getList?projectCode=" + projectCode, function (data) {
	         var teamMaster = $("#team");
	         teamMaster.children("option").remove();
	         if (projectCode != null && projectCode != "") {
				
	         	var html = "<option value=''>请选择班组</option>";
			} else {
				var html = "<option value=''>请先选择项目</option>";
			}
	         for (var i = 0; i < data.length; i++) {
	             html += "<option value='" + data[i].teamSysNo + "'>" + data[i].teamName + "</option>"
	         }
	         teamMaster.append(html);
	     }, function (data) {
	         Feng.error("班组加载失败!" + data.responseJSON.message + "!");
	     });
	     ajax.set(this.payRollDetailInfoData);
	     ajax.start();
    });
    $('#team').attr("disabled","disabled");
});
function  resetDate() {
        $("#team").html("<option value=''>请选择</option>");
}

