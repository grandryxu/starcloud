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
    queryData['isDel'] = $("#isDel").val();
    return queryData;
}


/**
 * 初始化表格的列
 */
WorkerMaster.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '公司', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '公司组织机构代码', field: 'organizationCode', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '手机号码', field: 'cellPhone', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型', field: 'idCardTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (value, row, index) {
            if(row.idCardType === 1){
                //身份证
                return Feng.hiddenIdCard(value);
            }else{
                return value;
            }
        }},
        {title: '民族', field: 'nationName', visible: true, align: 'center', valign: 'middle'},
        {title: '性别', field: 'genderName', visible: true, align: 'center', valign: 'middle'},
        {title: '出生日期', field: 'birthday', visible: true, align: 'center', valign: 'middle'},
        {title: '籍贯', field: 'birthPlaceCodeName', visible: true, align: 'center', valign: 'middle'},
        {title: '学历', field: 'cultureLevelTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目工种', field: 'workTypeCodeName', visible: true, align: 'center', valign: 'middle'},
        {title: '实名认证', field: 'isAuthName', visible: true, align: 'center', valign: 'middle'},
        {title: '是否人脸录入', field: 'isFaceName', visible: true, align: 'center', valign: 'middle'},
        {title: '是否删除', field: 'isDelName', visible: true, align: 'center', valign: 'middle'},
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
        var index = layer.open({
            type: 2,
            title: '工人管理详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: 'layer-no-title',
            content: Feng.ctxPath + '/workerMaster/workerMaster_update/' + WorkerMaster.seItem.id+"?pwId="+WorkerMaster.seItem.pwId
        });
        this.layerIndex = index;
    }
};


/**
 * 双击查看
 */
WorkerMaster.searchInfo = function (e) {
        var index = layer.open({
            type: 2,
            title: '工人详情信息',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: 'layer-no-title',
            content: Feng.ctxPath + '/workerMaster/workerMaster_view/' + e.id
        });
        this.layerIndex = index;
}


/**
 * 删除工人管理
 */
WorkerMaster.delete = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        layer.confirm('确认删除？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            var idCardNumber = "";
            for (var i = 0; i < selected.length; i++) {
                idCardNumber += selected[i].idCardNumber + ","
            }
            idCardNumber = idCardNumber.substring(0, idCardNumber.length - 1);
            var idCardType = "";
            for (var i = 0; i < selected.length; i++) {
                idCardType += selected[i].idCardType + ","
            }
            idCardType = idCardType.substring(0, idCardType.length - 1);
            var organizationCode = "";
            for (var i = 0; i < selected.length; i++) {
                organizationCode += selected[i].organizationCode + ","
            }
            organizationCode = organizationCode.substring(0, organizationCode.length - 1);


            var ajax = new $ax(Feng.ctxPath + "/workerMaster/delete", function (data) {
                Feng.success("删除成功!");
                WorkerMaster.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("idCardNumber", idCardNumber);
            ajax.set("idCardType", idCardType);
            ajax.set("organizationCode", organizationCode);
            ajax.start();
            layer.close(index);
        });
    }
};


/**
 * 查询工人管理列表
 */

WorkerMaster.search = function () {

    WorkerMaster.table.refresh({query: WorkerMaster.formParams()});
};


$(function () {
    Feng.initChosen();
    var defaultColunms = WorkerMaster.initColumn();
    var table = new BSTable(WorkerMaster.id, "/workerMaster/getAll?projectCode="+$("#projectCode").val(), defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(WorkerMaster.formParams());
    table.onDblClickRow = WorkerMaster.searchInfo;//双击事件所对应的方法 要放在init之前
    WorkerMaster.table = table.init();
});
