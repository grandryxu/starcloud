/**
 * 从业人员基础信息管理初始化
 */
var EmployeeMaster = {
    id: "EmployeeMasterTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};


/**
 * 导入工人信息
 */
EmployeeMaster.selectFile = function () {
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
        url: Feng.ctxPath + "/employeeMaster/excelUpload",
        type: "POST",
        data: myform,
        contentType: false,
        processData: false,
        success: function (data) {
            Feng.success("导入成功!");
            EmployeeMaster.table.refresh();
        },
        error: function (data) {
            Feng.error("导入失败!");
        }

    });
}

/**
 * 导出工人管理
 */
EmployeeMaster.download = function () {
    window.location.href = Feng.ctxPath + "/employeeMaster/download?"
        + "key=" + $("#key").val()
        + "&gender=" + $("#gender").val()
        + "&cultureLevelType=" + $("#cultureLevelType").val()
        + "&age=" + $("#age").val();
};
/**
 * 导入模板下载
 */
EmployeeMaster.mouldDownload = function () {
    window.location.href = "/static/excelMould/employeeMasterMould.xlsx";
};
EmployeeMaster.formParams = function () {
    var queryData = {};
    queryData['key'] = $("#key").val();
    queryData['gender'] = $("#gender").val();
    queryData['cultureLevelType'] = $("#cultureLevelType").val();
    queryData['age'] = $("#age").val();
    return queryData;
}

/**
 * 初始化表格的列
 */
EmployeeMaster.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '姓名', field: 'employeeName', visible: true, align: 'center', valign: 'middle'},
        {title: '公司名称', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
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
        {title: '文化程度', field: 'culturelevelTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '当前职称', field: 'professionalTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '当前职称等级', field: 'professionalLevelName', visible: true, align: 'center', valign: 'middle'},
        {title: '开始工作时间', field: 'workDate', visible: true, align: 'center', valign: 'middle'},
        {title: '在职状态', field: 'jobStatusName', visible: true, align: 'center', valign: 'middle'},
    ];
};


/**
 * 检查是否选中
 */
EmployeeMaster.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if(selected.length >1){
        Feng.info("请先选中表格中的一记录！");
    }
    else {
        EmployeeMaster.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加从业人员基础信息
 */
EmployeeMaster.openAddEmployeeMaster = function () {
    var index = layer.open({
        type: 2,
        title: '添加从业人员基础信息',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/employeeMaster/employeeMaster_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看从业人员基础信息详情
 */
EmployeeMaster.openEmployeeMasterDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '从业人员基础信息详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: 'layer-no-title',
            content: Feng.ctxPath + '/employeeMaster/employeeMaster_update/' + EmployeeMaster.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 双击查看
 */
EmployeeMaster.searchInfo = function (e) {
    var index = layer.open({
        type: 2,
        title: '从业人员详情信息',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-detail',
        content: Feng.ctxPath + '/employeeMaster/employeeMaster_view/' + e.id
    });
    this.layerIndex = index;

}

/**
 * 删除从业人员基础信息
 */
EmployeeMaster.delete = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        layer.confirm('确认删除？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            var employeeMasterIds = "";
            for (var i = 0; i < selected.length; i++) {
                employeeMasterIds += selected[i].id + ","
            }
            employeeMasterIds = employeeMasterIds.substring(0, employeeMasterIds.length - 1);

            var ajax = new $ax(Feng.ctxPath + "/employeeMaster/delete", function (data) {
                Feng.success("删除成功!");
                EmployeeMaster.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("employeeMasterIds", employeeMasterIds);
            ajax.start();

            layer.close(index);
        });
    }
};

/**
 * 查询从业人员基础信息列表
 */
EmployeeMaster.search = function () {
    EmployeeMaster.table.refresh({query: EmployeeMaster.formParams()});
};

$(function () {
    var defaultColunms = EmployeeMaster.initColumn();
    var table = new BSTable(EmployeeMaster.id, "/employeeMaster/getListBySubContractor?organizationCode="+$("#organizationCode").val(), defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(EmployeeMaster.formParams());
   // table.onDblClickRow = EmployeeMaster.searchInfo;//双击事件所对应的方法 要放在init之前
    EmployeeMaster.table = table.init();
});
