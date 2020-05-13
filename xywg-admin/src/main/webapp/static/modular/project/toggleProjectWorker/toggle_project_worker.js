/**
 * 工人管理管理初始化
 */
var WorkerMaster = {
    id: "WorkerMasterTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 导入工人信息
 */
WorkerMaster.selectFile = function () {
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
            WorkerMaster.table.refresh();
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
WorkerMaster.download = function () {
	//alert($("#key").val())
	let cols = getTrueColumns();
    window.location.href = encodeURI(Feng.ctxPath + "/workerMaster/download?"
        + "key=" + $("#key").val()
        + "&workTypeCode=" + $("#workTypeCode").val()
        + "&birthPlaceCode=" + $("#birthPlaceCode").val()
        + "&gender=" + $("#gender").val()
        + "&cultureLevelType=" + $("#cultureLevelType").val()
        + "&age=" + $("#age").val()
        + "&teamSysNo=" + $("#teamSysNo").val()
    	+ "&cols=" + cols);
    
};

function getTrueColumns(){
	let tCols = [];
	let columns = $('#WorkerMasterTable').bootstrapTable('getVisibleColumns');
	for(let c in columns) {  
		let col = columns[c];
        if(col.hasOwnProperty("title")&&col.title != ''&&col.visible == true){
        	tCols.push(col.field);
        }
    }; 
    return tCols;
}

/**
 * dao
 */
WorkerMaster.mouldDownload = function () {
    window.location.href = "/static/excelMould/工人导入模板.xlsx";
};
WorkerMaster.formParams = function () {
    var queryData = {};
    queryData['key'] = $("#key").val();
    queryData['workTypeCode'] = $("#workTypeCode").val();
    queryData['birthPlaceCode'] = $("#birthPlaceCode").val();
    queryData['gender'] = $("#gender").val();
    queryData['cultureLevelType'] = $("#cultureLevelType").val();
    queryData['age'] = $("#age").val();
    queryData['teamSysNo'] = $("#teamSysNo").val();
    queryData['isDel'] = $("#isDel").val();
    return queryData;
}


/**
 * 初始化表格的列
 */
WorkerMaster.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '序号', field: 'pwId', visible: false, align: 'center', valign: 'middle'},
        {title: '公司', field: 'companyName', visible: false, align: 'center', valign: 'middle',},
        {title: '公司组织机构代码', field: 'organizationCode', visible: false, align: 'center', valign: 'middle'},
        
        {title: '班组', field: 'teamName', visible: true, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        
        {title: '证件类型', field: 'idCardTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
            return Feng.hiddenIdCard(data);
        }},
        {title: '工种', field: 'workTypeCodeName', visible: true, align: 'center', valign: 'middle'},
        {title: '手机号码', field: 'cellPhone', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'joinStatus', visible: true, align: 'center', valign: 'middle',
       	 formatter: function (value, row, index) {
                if (row.joinStatus === 1) {
                    //身份证
                    return '待进场 ';
                } else if(row.joinStatus === 2){
               	 return '进场 ';
                }else {
               	 return '退场 ';
                }
            }},
            {title: '进场时间', field: 'entryTime', visible: true, align: 'center', valign: 'middle'},
            {title: '退场时间', field: 'exitTime', visible: true, align: 'center', valign: 'middle'},
        {title: '民族', field: 'nationName', visible: false, align: 'center', valign: 'middle'},
        {title: '性别', field: 'genderName', visible: true, align: 'center', valign: 'middle'},
        {title: '出生日期', field: 'birthday', visible: true, align: 'center', valign: 'middle'},
        {title: '籍贯', field: 'birthPlaceCodeName', visible: true, align: 'center', valign: 'middle'},
        {title: '安全帽', field: 'shImei', visible: false, align: 'center', valign: 'middle'},
        
        {title: '是否实名', field: 'isAuthName', visible: true, align: 'center', valign: 'middle'},
        {title: '人脸录入', field: 'isFaceName', visible: true, align: 'center', valign: 'middle'},
       /* {title: '是否删除', field: 'isDelName', visible: true, align: 'center', valign: 'middle'},*/
        
    ];
};

/**
 * 检查是否选中
 */
WorkerMaster.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("请先选中表格中的一记录！");
    }
    else {
        WorkerMaster.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加工人管理
 */
WorkerMaster.openAddWorkerMaster = function () {
    var index = layer.open({
        type: 2,
        title: '添加工人管理',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/workerMaster/workerMaster_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看工人管理详情
 */
WorkerMaster.openWorkerMasterDetail = function () {


    if (this.check()) {
        console.log(WorkerMaster.seItem.id);
        var url = Feng.ctxPath + '/workerMaster/workerMaster_update/' + WorkerMaster.seItem.id;
        if (WorkerMaster.seItem.pwId) {
            url += "?pwId=" + WorkerMaster.seItem.pwId;
        }
        var index = layer.open({
            type: 2,
            title: '工人管理详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: 'layer-no-title',
            content: url
        });
        this.layerIndex = index;
    }


};


/**
 * 双击查看
 */
WorkerMaster.searchInfo = function (e) {
    var url = Feng.ctxPath + '/workerMaster/workerMaster_view/' + e.id;

    if (e.pwId) {
        url += "?pwId=" + e.pwId;
    }
        var index = layer.open({
            type: 2,
            title: '工人详情信息',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: 'layer-no-title',
            content: url
        });
        this.layerIndex = index;
}




/**
 * 查询工人管理列表
 */

WorkerMaster.search = function () {

    WorkerMaster.table.refresh({query: WorkerMaster.formParams()});
};


/**
 * 安全帽分配
 */
WorkerMaster.assignHelmet = function () {
    if (this.check()) {
        if(WorkerMaster.seItem.isDel === 1){
            Feng.info("该用户已删除");
            return;
        }
        var ajax = new $ax(Feng.ctxPath + "/safetyHelmet/getUnusedSafetyHelmet?projectCode="+$("#projectCode").val(), function (data) {
            if(data.length === 0 ){
                Feng.info("该项目下暂无可用安全帽");
                return;
            }
            if(WorkerMaster.seItem.shImei){
                layer.confirm("该用户已绑定安全帽" + WorkerMaster.seItem.shImei +",确定重新分配?",function (index) {
                    layer.close(index);
                    openHelmet(data);
                })
            }else{
                openHelmet(data);
            }
        }, function (data) {

        });
        ajax.start();
    }

}

/**
 * 删除工人管理
 */
WorkerMaster.deletes = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        var ids = [];
        for (var i = 0; i < selected.length; i++) {
//            if (selected[i].projectCode) {
//                Feng.info("用户" + selected[i].workerName + "已在项目中，不能删除");
//                return;
//            } else {
            ids.push(selected[i].pwId)
//            }
        }
        layer.confirm('确认删除？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            var ajax = new $ax(Feng.ctxPath + "/workerMaster/delete", function (data) {
                Feng.success("删除成功!");
                WorkerMaster.table.refresh();
                layer.close(index);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("ids", JSON.stringify(ids));
            ajax.start();
        });
    }
};


function openHelmet(data) {
    var typeSelectDom = '<div class="fn-div" style="margin-top: 6px;"><select id="chooseSafetyHelmet" class="fn-input input-search-s">';
    for(var i=0;i<data.length;i++){
        typeSelectDom += '<option value="'+ data[i].imei +'">'+data[i].imei+'</option>';
    }
    typeSelectDom += '</select></div>';
    layer.open({
        type: 1,
        skin: 'layui-layer-reward', //样式类名
        anim: 2,
        title: '安全帽分配',
        area: ['480px', '380px'],
        btn: ['确定', '取消'],
        shadeClose: true, //开启遮罩关闭
        content: typeSelectDom,
        success:function(){
            $("#chooseSafetyHelmet").chosen({search_contains: true, no_results_text: "暂无结果"});
        },
        yes: function (index, layero) {
            layer.confirm('确认为用户'+ WorkerMaster.seItem.workerName +'分配安全帽'+ $("#chooseSafetyHelmet option:selected").html() +'?', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                var ajax = new $ax(Feng.ctxPath + "/projectWorker/bindSafetyHelmet", function (data) {
                    layer.closeAll();
                    WorkerMaster.table.refresh();
                    Feng.success("安全帽分配成功!");
                });
                ajax.set({
                    projectCode:$("#projectCode").val(),
                    idCardType: WorkerMaster.seItem.idCardType ,
                    idCardNumber: WorkerMaster.seItem.idCardNumber,
                    shImei:$("#chooseSafetyHelmet").val()
                });
                ajax.start();
            }, function () {

            });
        }
    });
}
$(function () {
    Feng.initChosen();
    var defaultColunms = WorkerMaster.initColumn();
    var table = new BSTable(WorkerMaster.id, "/workerMaster/toggleList", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(WorkerMaster.formParams());
    table.onDblClickRow = WorkerMaster.searchInfo;//双击事件所对应的方法 要放在init之前
    WorkerMaster.table = table.init();
});
