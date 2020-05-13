/**
 * 考勤机管理管理初始化
 */
var Device = {
    id: "DeviceTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Device.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '设备名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '设备类型', field: 'typeIdName', visible: true, align: 'center', valign: 'middle'},
        {title: '出入类型', field: 'typeName', visible: true, align: 'center', valign: 'middle'},
        {title: '设备序列号', field: 'sn', visible: true, align: 'center', valign: 'middle'},
        {title: '所属项目', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '设备状态', field: 'stateName', visible: true, align: 'center', valign: 'middle'},
        {title: '最后通信时间', field: 'talk', visible: true, align: 'center', valign: 'middle'},
        {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
Device.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if(selected.length > 1){
        Feng.info("只能选择表格中的某一记录！");
        return false;
    }else {
        Device.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加考勤机管理
 */
Device.openAddDevice = function () {
    var index = layer.open({
        type: 2,
        title: '添加考勤机管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/device/device_add?projectCode=' + $("#projectCode").val() + "&projectName=" + $("#projectName").val()
    });
    this.layerIndex = index;
};

/**
 * 打开查看考勤机管理详情
 */
Device.openDeviceDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '考勤机管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/device/device_update?id=' + Device.seItem.id + '&projectCode=' + $("#projectCode").val() + "&projectName=" + $("#projectName").val()
        });
        this.layerIndex = index;
    }
};

/**
 * 删除考勤机管理
 */
Device.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/device/delete", function (data) {
            Feng.success("删除成功!");
            Device.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("deviceId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询考勤机管理列表
 */
Device.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['state'] = $("#state").val();
    Device.table.refresh({query: queryData});
};

/**
 * 双击查看
 */
Device.searchInfo = function (e) {
        var index = layer.open({
            type: 2,
            title: '考勤机管理查看',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/device/device_view?id=' + e.id + '&projectCode=' + $("#projectCode").val() + "&projectName=" + $("#projectName").val()
        });
        this.layerIndex = index;
};


//启用 禁用
Device.toggle = function (state) {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("至少选中表格中的某一记录！");
        return false;
    } else {
        Device.seItem = selected;
    }
    var ids = "";
    for(var i=0;i<selected.length;i++){
        ids += selected[i].id + ",";
    }
    ids = ids.substring(0,ids.length-1);
    if (state === 1) {
        if (confirm("确定要启用考勤机吗?")) {
            Device.toggleAjax(ids ,state);
        }
    } else {
        if (confirm("确定要禁用考勤机吗?")) {
            Device.toggleAjax(ids ,state);
        }
    }
    Device.table.refresh();
}

//启用禁用 提交
Device.toggleAjax = function (ids, state) {
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/device/toggle", function (data) {
        Feng.success("操作成功!");
    }, function (data) {
        Feng.error("操作失败!" + data.responseJSON.message + "!");
    });
    ajax.set({
        state: state ,
        ids: ids
    });
    ajax.start();
}


$(function () {
    var defaultColunms = Device.initColumn();
    var table = new BSTable(Device.id, "/device/list?projectCode=" + $("#projectCode").val(), defaultColunms);
    table.setPaginationType("server");
    table.onDblClickRow = Device.searchInfo;
    Device.table = table.init();
});

/**
 * 设备命令下发
 */
Device.commandFun = function (comm) {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("至少选中表格中的某一记录！");
        return false;
    } else {
        Device.seItem = selected;
    }
    var arr = [];
    for(var i=0;i<selected.length;i++){
        arr.push(selected[i].sn);
    }
    if (comm == 1) {
        str = "是否同步这<span style= 'color : red'>" + arr.length + "</span>台设备的时间？";
    } else if (comm == 2) {
        str = "确定要重启这<span style= 'color : red'>" + arr.length + "</span>台设备？";
    } else if (comm == 3) {
        str = "确定要获取这<span style= 'color : red'>" + arr.length + "</span>台设备信息？";
    } else if (comm == 5) {
        str = "确定清空这<span style= 'color : red'>" + arr.length + "</span>台设备上的考勤记录？";
    } else if (comm == 6) {
        str = "是否删除这<span style= 'color : red'>" + arr.length + "</span>台设备上的所有人员？";
    }/* else if (comm == 7) {
        str = "是否下发这<span style= 'color : red'>" + arr.length + "</span>台设备上的人员信息？";
    } */else {
    	Feng.info("请选择考勤机命令");
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/deviceCommand/executeCommand", function (data) {
        Feng.success("操作成功!");
    }, function (data) {
        Feng.error("操作失败!" + data.responseJSON.message + "!");
    });
    ajax.set("deviceIds",JSON.stringify(arr));
    ajax.set("type",comm);
    ajax.set("workerId",null);
    ajax.start();
	 
};

