/**
 * 考勤记录管理初始化
 */
var DeviceRecord = {
    id: "DeviceRecordTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
DeviceRecord.initColumn = function () {
    return [
        {field: 'selectItem', radio: true, visible: false},
        {title: '班组名称', field: 'teamName', visible: true, align: 'center', valign: 'middle'},
        {title: '名称', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '考勤时间', field: 'time', visible: true, align: 'center', valign: 'middle'},
        {
            title: '是否有效', field: 'isValid', visible: true, align: 'center', valign: 'middle',
            formatter: function (data) {
                if (data = 1) {
                    return "有效";
                } else {
                    return "无效";
                }
            }
        },
        {title: '考勤类型', field: 'typeName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '图片',
            field: 'iconPhoto',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (data) {
                if (data) {
                    return "<img src='"+Feng.imagePath+data+"' style='width:100px;height: 100px;' />";
                } else {
                    return "无";
                }
            }
        }
    ];
};

/**
 * 检查是否选中
 */
DeviceRecord.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        DeviceRecord.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加考勤记录
 */
DeviceRecord.openAddDeviceRecord = function () {
    var index = layer.open({
        type: 2,
        title: '补考勤',
        area: ['800px', '500px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/deviceRecord/deviceRecord_add'
    });
    this.layerIndex = index;
};


DeviceRecord.openDeviceRecordDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '考勤记录详情',
            area: ['900px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/deviceRecord/deviceRecord_update/' + DeviceRecord.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 打开查看考勤记录详情
 */
DeviceRecord.openDeviceRecordDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '考勤记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/deviceRecord/deviceRecord_update/' + DeviceRecord.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除考勤记录
 */
DeviceRecord.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/deviceRecord/delete", function (data) {
            Feng.success("删除成功!");
            DeviceRecord.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("deviceRecordId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询考勤记录列表
 */
DeviceRecord.search = function () {
    var queryData = {};
    queryData['workerName'] = $("#workerName").val();
    queryData['teamSysNo'] = $("#team").val();
    DeviceRecord.table.refresh({query: queryData});
};



/**
 * 双击查看
 */
DeviceRecord.searchInfo = function () {
    if (DeviceRecord.check()) {
        var index = layer.open({
            type: 2,
            title: '考勤记录详情',
            area: ['800px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/deviceRecord/detail/' + DeviceRecord.seItem.id
        });
        this.layerIndex = index;
    }
}

$(function () {
    var defaultColunms = DeviceRecord.initColumn();
    var table = new BSTable(DeviceRecord.id, "/deviceRecord/getDeviceRecordByProjectCode?projectCode="+$("#projectCode").val(), defaultColunms);
    table.setPaginationType("server");
    table.onDblClickRow = DeviceRecord.searchInfo;//双击事件所对应的方法 要放在init之前
    DeviceRecord.table = table.init();
});
