/**
 * 工人管理管理初始化
 */
var Tendering = {
    id: "WorkerMasterTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 导入工人信息
 */
Tendering.selectFile = function () {
    $("#excelFile").trigger("click");
};

//导入
function fileUpload() {
    var myform = new FormData();
//    myform.append('file', $('#excelFile')[0].files[0]);
    var files = $('#excelFile')[0].files;
    if(files.length === 0){
        return;
    }
    myform.append('file', files[0]);
    $.ajax({
        url: Feng.ctxPath + "/workerMaster/excelUpload",
        type: "POST",
        data: myform,
        contentType: false,
        processData: false,
        success: function (data) {
            Feng.success(data);
            $('#excelFile').val("");
            Tendering.table.refresh();
        },
        error: function (data) {
            Feng.error("导入失败!" + data.responseJSON.message + "!");
            $('#excelFile').val("");
        }

    });
}

/**
 * 导出工人管理
 */
Tendering.download = function () {
    window.location.href = Feng.ctxPath + "/workerMaster/download?"
        + "key=" + $("#key").val()
        + "&workTypeCode=" + $("#workTypeCode").val()
        + "&birthPlaceCode=" + $("#birthPlaceCode").val()
        + "&gender=" + $("#gender").val()
        + "&cultureLevelType=" + $("#cultureLevelType").val()
        + "&age=" + $("#age").val();
};

/**
 * 模板
 */
Tendering.mouldDownload = function () {
    window.location.href = "/static/excelMould/工人导入模板.xlsx";
};
Tendering.formParams = function () {
    var queryData = {};
    queryData['key'] = $("#key").val();
    queryData['workTypeCode'] = $("#workTypeCode").val();
    queryData['birthPlaceCode'] = $("#birthPlaceCode").val();
    queryData['gender'] = $("#gender").val();
    queryData['cultureLevelType'] = $("#cultureLevelType").val();
    queryData['age'] = $("#age").val();
    queryData['isDel'] = $("#isDel").val();
    return queryData;
}


/**
 * 初始化表格的列
 */
Tendering.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '文件名称', field: 'fileName', visible: true, align: 'center', valign: 'middle'},
        {title: '文件描述', field: 'tenderResume', visible: true, align: 'center', valign: 'middle'},
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        /*{title: '项目地点', field: 'projectAddress', visible: true, align: 'center', valign: 'middle'},*/
        {title: '提交人', field: 'createUser', visible: true, align: 'center', valign: 'middle'},
        {title: '发布时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '报名截止时间', field: 'deadline', visible: true, align: 'center', valign: 'middle'},
                {title: '招标开标时间', field: 'startTime', visible: true, align: 'center', valign: 'middle'},
        {title: '招标方式', field: 'tenderType', visible: true, align: 'center', valign: 'middle' , formatter:function (data) {
            if(data==0){
                return "邀标";
            }else{
                return "公开招标";
            }

        }},
        {title: '流程状态', field: 'status', visible: true, align: 'center', valign: 'middle',
            formatter: function (data, row, index) {
            	if(data == "-1"){
            		return '<button  class="btn btn-sm btn-primary" style="background-color:#87CEFA" onclick="Tendering.changeFlow('+row.id+ ',' + 0+')" id="bid'+row.id+'"><strong><font color="white">提交审核</font></strong></button>';
            	}else if (data == "0") {
                return "审核中";
            } else if(data == "1") {
                return "审核完成";
            }else{
                return "已驳回";
            }
        }},
        {title: '招标状态', field: 'tenderStatus', visible: true, align: 'center', valign: 'middle',
            formatter: function (data,value) {
            	var startTime = value.startTime;
            	var startDate = new Date(startTime);
            	var nowDate = new Date();
            	var flag = false;
       
            	if(nowDate > startDate){
            		//开标
            		flag = true;
            	}
            	if(value.tenderManStatus == 1){
            		return "废标";
            	}
            	if(value.tenderManStatus == 2){
            		return "流标";
            	}
            	
            	if(data == 5){
    				return "招标完成";
            	}
            	if(flag&&(value.status == 1)){
            		return '开标中';
            	}else{
            		return '未开标';
            	}
        }},
        
        {title: '操作', visible: true, align: 'center', valign: 'middle',
            formatter: function (val, row, index) {
            	var startTime = row.startTime;
            	var startDate = new Date(startTime);
            	var nowDate = new Date();
            	var flag = false;
            	
            	if(nowDate > startDate){
            		//开标
            		flag = true;
            	}
            	if(row.tenderManStatus == 1){
            		return "已废标";
            	}
            	if(row.tenderManStatus == 2){
            		return "已流标";
            	}
            	
            	
            	if(row.tenderStatus == 5){
            		return '<button  class="btn btn-sm btn-primary" style="background-color:green" onclick="Tendering.view('+row.id+ ')" id="bid'+row.id+'"><strong><font color="white">查看</font></strong></button>';
            	}
            	if (row.status == 1) {
                    if(flag){
                        return '<button  class="btn btn-sm btn-primary" style="background-color:green" onclick="Tendering.changeStatus('+row.id+ ',' + 2+')" id="bid'+row.id+'"><strong><font color="white">流标</font></strong></button>';
                    }else{
                        return '<button  class="btn btn-sm btn-primary" style="background-color:green" onclick="Tendering.changeStatus('+row.id+ ',' + 1+')" id="bid'+row.id+'"><strong><font color="white">废标</font></strong></button>';
                    }
                } else {
                    return '<button  class="btn btn-sm btn-primary" style="background-color:green" onclick="Tendering.view('+row.id+ ')" id="bid'+row.id+'"><strong><font color="white">查看</font></strong></button>';
                }

        }},

    ];
};



		/**
		 * 点击添加企业信息
		 */
Tendering.openDetail = function (e,flag) {
		    var index = layer.open({
		        type: 2,
		        title: '定标详情',
		        area: ['100%', '100%'], //宽高
		        fix: false, //不固定
		        maxmin: false,
		        content: Feng.ctxPath + '/lxFixBid/detail?bidId=' + e + "&flag=" + flag
		    });
		    this.layerIndex = index;
		};

Tendering.view = function (e) {
    var index = layer.open({
        type: 2,
        title: '详情信息',
        area: ['80%', '80%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/lx/tendering/view?id=' + e
    });
    this.layerIndex = index;
}

/**
 * 检查是否选中
 */

Tendering.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("只能选中表格中的某一记录！");
        return false;
    } else {
        Tendering.seItem = selected[0];
        return true;
    }
};



/**
 *邀标方式
 */
Tendering.openAddWorkerMaster = function () {
    if (this.check()) {
       var flag2;

        var ajax = new $ax(Feng.ctxPath + '/lx/tendering/selectType?id='+Tendering.seItem.id, function (data) {
           flag2=data
        }, function (data) {

        });
        ajax.start();


        if (flag2!= 0){
            Feng.error("请选择对应的招标方式!");
            return false;
        }




    	var deadLine = Tendering.seItem.deadline;
    	var deadDate = new Date(deadLine);
    	var nowDate = new Date();
    	var flag = deadDate < nowDate;
        if(Tendering.seItem.status!=1){
            layer.confirm('该流程未审核完成', {
                btn: ['确定'] //按钮
            }, function(){
                    layer.closeAll();
            }, function(){
                layer.closeAll();
            });
        }else if(flag){
        	layer.confirm('该招标信息已过截止时间', {
                btn: ['确定'] //按钮
            }, function(){
                layer.closeAll();
            }, function(){
                layer.closeAll();
            });
        }else {
            var index = layer.open({
                type: 2,
                title: '邀标管理',
                area: ['100%', '100%'], //宽高
                fix: false, //不固定
                maxmin: true,
                skin: 'layer-no-title',
                content: Feng.ctxPath + '/lxProject/yb/'+Tendering.seItem.id
            });
            this.layerIndex = index;
        }
    }
};
/*
Tendering.openAddWorkerMaster = function () {
    if (this.check()) {
        var deadLine = Tendering.seItem.deadline;
        var deadDate = new Date(deadLine);
        var nowDate = new Date();
        var flag = deadDate < nowDate;
        if(Tendering.seItem.status!=1){
            layer.confirm('该流程未审核完成', {
                btn: ['确定'] //按钮
            }, function(){
                layer.closeAll();
            }, function(){
                layer.closeAll();
            });
        }else if(flag){
            layer.confirm('该招标信息已过截止时间', {
                btn: ['确定'] //按钮
            }, function(){
                layer.closeAll();
            }, function(){
                layer.closeAll();
            });
        }else {
            var index = layer.open({
                type: 2,
                title: '邀标管理',
                area: ['100%', '100%'], //宽高
                fix: false, //不固定
                maxmin: true,
                skin: 'layer-no-title',
                content: Feng.ctxPath + '/lxProject/yb/'+Tendering.seItem.id
            });
            this.layerIndex = index;
        }
    }
};*/

/*
* 公开招标方式
* */

Tendering.gongkaizhaobiao = function () {
    if (this.check()) {

  /*      var flag1;
        $.get(Feng.ctxPath + '/lx/tendering/selectType?id='+Tendering.seItem.id,function (data) {
            flag1=data
        });
     */

        var flag1;

        var ajax = new $ax(Feng.ctxPath + '/lx/tendering/selectType?id='+Tendering.seItem.id, function (data) {
            flag1=data
        }, function (data) {

        });
        ajax.start();

        console.log(flag1)
        if (flag1!=1){
            Feng.error("请选择对应的招标方式!");
            return false;
        }


        var deadLine = Tendering.seItem.deadline;
        var deadDate = new Date(deadLine);
        var nowDate = new Date();
        var flag = deadDate < nowDate;
        if(Tendering.seItem.status!=1){
            layer.confirm('该流程未审核完成', {
                btn: ['确定'] //按钮
            }, function(){
                layer.closeAll();
            }, function(){
                layer.closeAll();
            });
        }else if(flag){
            layer.confirm('该招标信息已过截止时间', {
                btn: ['确定'] //按钮
            }, function(){
                layer.closeAll();
            }, function(){
                layer.closeAll();
            });
        }else {

            layer.confirm('确定公开招标？', {
                btn: ['确定'] //按钮
            }, function(){
                $.get(Feng.ctxPath + '/lx/tendering/setType?id='+Tendering.seItem.id+"&type=1",function (data) {
                       Feng.success("修改成功");
                })
                layer.closeAll();
            }, function(){
                layer.closeAll();
            });



        }
    }
};




/**
 * 双击查看
 */
Tendering.searchInfo = function (e) {
    var index = layer.open({
        type: 2,
        title: '工人详情信息',
        area: ['80%', '80%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/lx/tendering/view?id=' + e.id
    });
    this.layerIndex = index;
}

Tendering.addzbxx= function(){
        var index = layer.open({
            type: 2,
            title: '新增招标文件',
            area: ['50%', '100%'], //宽高
            fix: false, //不固定
            maxmin: false,
            skin: "layer-detail",
            content: Feng.ctxPath + '/lxProject/tendering?id='+$("#id").val()
        });
        this.layerIndex = index;
        console.log(this.layerIndex)

}

/**
 * 删除工人管理
 */
Tendering.delete = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        layer.confirm('确认删除？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            var id = "";
            for (var i = 0; i < selected.length; i++) {
                if(parseInt(selected[i].status) >= 0){
                    layer.alert("有已审核流程，无法删除")
                    return ;
                }
                id += selected[i].id + ","
            }
            id = id.substring(0, id.length - 1);


            var ajax = new $ax(Feng.ctxPath + "/lx/tendering/delete", function (data) {
                Feng.success("删除成功!");
                Tendering.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", id);
            ajax.start();
            layer.close(index);
        });
    }
};


/**
 * 
 */
Tendering.changeStatus = function (id,status) {
	var message = '';
	if(status == 1){
		message = '确认废标';
	}else{
		message = '确认流标';
	}
	layer.confirm(message + '？', {
        btn: ['确定', '取消'] //按钮
    }, function (index) {
	
            var ajax = new $ax(Feng.ctxPath + "/lx/tendering/changeStatus", function (data) {
                Feng.success("修改成功!");
                Tendering.table.refresh();
            }, function (data) {
                Feng.error("修改失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", id);
            ajax.set("status", status);
            ajax.start();
            layer.close(index);

        });
};



/**
 * 
 */
Tendering.changeFlow = function (id,status) {
	var message = '';
	if(status == 0){
		message = '确认提交审核';
	}
	layer.confirm(message + '？', {
        btn: ['确定', '取消'] //按钮
    }, function (index) {
	
            var ajax = new $ax(Feng.ctxPath + "/lx/tendering/changeFlow", function (data) {
                Feng.success("修改成功!");
                Tendering.table.refresh();
            }, function (data) {
                Feng.error("修改失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", id);
            ajax.set("status", status);
            ajax.start();
            layer.close(index);

        });
};


/**
 * 查询工人管理列表
 */

Tendering.search = function () {

    Tendering.table.refresh({query: Tendering.formParams()});
};




$(function () {
    Feng.initChosen();
    var defaultColunms = Tendering.initColumn();

    var table = new BSTable(Tendering.id, "/lx/tendering/getAll?projectId="+$("#id").val(), defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(Tendering.formParams());
    table.onDblClickRow = Tendering.searchInfo;//双击事件所对应的方法 要放在init之前
    Tendering.table = table.init();
});
