/**
 * 考勤异常列表管理初始化
 */
var DeviceRecordExceptionData = {
    id: "DeviceRecordExceptionDataTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
DeviceRecordExceptionData.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '所属公司', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '班组', field: 'teamName', visible: true, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型', field: 'idCardTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle'},
        {title: '考勤时间', field: 'time', visible: true, align: 'center', valign: 'middle'},
        {title: '来源', field: 'sourceName', visible: true, align: 'center', valign: 'middle'},
        {title: '考勤机sn', field: 'deviceSn', visible: true, align: 'center', valign: 'middle'},
        {title: '异常类型', field: 'exceptionTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '考勤类型', field: 'typeName', visible: true, align: 'center', valign: 'middle'},
        {title: '考勤时间段', field: 'deviceTypeName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '操作', visible: true, align: 'center', valign: 'middle', formatter: function (val, row, index) {
                /*"<a href=''><i class='fa fa-remove text-red'></i></a>" +*/
                if (row.exceptionType === '3') {
                    return "<a href='javascript:void(0)' exceptionDeviceId=" + row.id + " ' onClick='changeStatus(this)' >启用</a>";
                }
            }
        }
    ];
};

//启用禁用时的异常数据
function changeStatus(val) {
    var id = $(val).attr("exceptionDeviceId");
    var ajax = new $ax(Feng.ctxPath + "/deviceRecordExceptionData/changeStatus", function (data) {
        Feng.success("启用成功!");
        DeviceRecordExceptionData.table.refresh();
    }, function (data) {
        Feng.error(data.responseJSON.message + "!");
    });
    ajax.set({'id': id});
    ajax.start();
}


/**
 * 检查是否选中
 */
DeviceRecordExceptionData.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("请先选中表格中的一记录！");
    }
    else {
        DeviceRecordExceptionData.seItem = selected[0];
        return true;
    }
};


/**
 * 处理考勤异常
 */
DeviceRecordExceptionData.exceptionDeviceAction = function () {
    if (this.check()) {
        if (hasOperationAuth()) {
            //项目级总包
            if (DeviceRecordExceptionData.seItem.isGeneralContractorOperation === 0) {
                Feng.error("您无权操作参建单位"+ DeviceRecordExceptionData.seItem.companyName+"的数据!");
                return;
            }
        }
   /*     if (DeviceRecordExceptionData.seItem.exceptionType !== '2') {
            Feng.error("必须选择异常类型为'考勤人不在项目中'的数据");
            return;
        }*/
        var index = layer.open({
            type: 2,
            title: '考勤异常列表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/deviceRecordExceptionData/deviceRecordExceptionData_action/' + DeviceRecordExceptionData.seItem.id
        });
        this.layerIndex = index;
    }
};


/**
 * 点击添加考勤异常列表
 */
DeviceRecordExceptionData.openAddDeviceRecordExceptionData = function () {
    var index = layer.open({
        type: 2,
        title: '添加考勤异常列表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/deviceRecordExceptionData/deviceRecordExceptionData_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看考勤异常列表详情
 */
DeviceRecordExceptionData.openDeviceRecordExceptionDataDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '考勤异常列表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/deviceRecordExceptionData/deviceRecordExceptionData_update/' + DeviceRecordExceptionData.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除考勤异常列表
 */
DeviceRecordExceptionData.delete = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        layer.confirm('确认删除？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            var ids = "";
            for (var i = 0; i < selected.length; i++) {
                if (hasOperationAuth()) {
                    //项目级总包
                    if (selected[i].isGeneralContractorOperation === 0) {
                        Feng.error("您无权操作参建单位"+ selected[i].companyName+"的数据!");
                        return;
                    }
                }
                ids += selected[i].id + ","
            }
            ids = ids.substring(0, ids.length - 1);
            var ajax = new $ax(Feng.ctxPath + "/deviceRecordExceptionData/delete", function (data) {
                Feng.success("删除成功!");
                DeviceRecordExceptionData.table.refresh();
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
 * 双击查看
 */
DeviceRecordExceptionData.searchInfo = function (e) {
    var index = layer.open({
        type: 2,
        title: '异常考勤详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/deviceRecordExceptionData/deviceRecordExceptionData_view/' + e.id
    });
    this.layerIndex = index;
}

DeviceRecordExceptionData.formParams = function () {
    var queryData = {};
    queryData['exceptionType'] = $("#exceptionType").val();
    queryData['deviceType'] = $("#deviceType").val();
    queryData['source'] = $("#source").val();
    queryData['projectCode'] = $("#projectCode").val();
    queryData['team'] = $("#team").val();
    queryData['key'] = $("#key").val();
    return queryData;
}

/**
 * 查询考勤异常记录列表
 */
DeviceRecordExceptionData.search = function () {
    var queryData = {};
    queryData['exceptionType'] = $("#exceptionType").val();
    queryData['deviceType'] = $("#deviceType").val();
    queryData['source'] = $("#source").val();
    queryData['projectCode'] = $("#projectCode").val();
    queryData['team'] = $("#team").val();
    queryData['key'] = $("#key").val();
    DeviceRecordExceptionData.table.refresh({query: queryData});
};


$(function () {
    Feng.initChosen();
    var defaultColunms = DeviceRecordExceptionData.initColumn();
    var table = new BSTable(DeviceRecordExceptionData.id, "/deviceRecordExceptionData/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(DeviceRecordExceptionData.formParams());
    table.onDblClickRow = DeviceRecordExceptionData.searchInfo;//双击事件所对应的方法 要放在init之前
    DeviceRecordExceptionData.table = table.init();
    $("#projectCode").chosen().on("change", function (evt, data) {
        var projectCode = data.selected;
        var ajax = new $ax(Feng.ctxPath + "/teamMaster/getList?projectCode=" + projectCode, function (data) {
            var teamMaster = $("#team");
            teamMaster.chosen("destroy");
            teamMaster.children("option").remove();
            var html = "<option value=''>请选择班组</option>";
            for (var i = 0; i < data.length; i++) {
                html += "<option value='" + data[i].teamSysNo + "'>" + data[i].teamName + "</option>"
            }
            teamMaster.append(html);
            teamMaster.chosen({search_contains: true, no_results_text: "暂无结果"});
        }, function (data) {
            Feng.error("班组加载失败!" + data.responseJSON.message + "!");
        });
        ajax.set(this.payRollDetailInfoData);
        ajax.start();
    });


});
