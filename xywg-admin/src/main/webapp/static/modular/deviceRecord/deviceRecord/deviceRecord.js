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
    	    {field: 'selectItem', radio: true},
            {title: '编号', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
            {title: '班组名称', field: 'teamName', visible: true, align: 'center', valign: 'middle'},
            {title: '名称', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
            {title: '身份证类型', field: 'idCardType', visible: true, align: 'center', valign: 'middle'},
            {title: '身份证号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '考勤时间', field: 'time', visible: true, align: 'center', valign: 'middle'},
            {title: '来源', field: 'source', visible: true, align: 'center', valign: 'middle'},
            {title: '图片', field: 'iconPhoto', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
                if(data){
                    return "<img src='"+data+"' style='width:100px;height: 100px;' />";
                }else{
                    return "无";
                }

            }},
            {title: '是否有效', field: 'validName', visible: true, align: 'center', valign: 'middle'},
            /*{title: '添加人', field: 'createUser', visible: true, align: 'center', valign: 'middle'},
            {title: '添加时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},*/
            {title: '考勤类型', field: 'typeName', visible: true, align: 'center', valign: 'middle'},
           // {title: '设备名称', field: 'deviceName', visible: true, align: 'center', valign: 'middle'},
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
        area: ['80%', '80%'], //宽高
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
    queryData['condition'] = $("#condition").val();
    queryData['projectCode'] = $("#projectCode").val();
    queryData['team'] = $("#team").val();
    queryData['isValid'] = $("#isValid").val();
    queryData['deviceType'] = $("#deviceType").val();
    queryData['source'] = $("#source").val();
    queryData['startDate'] = $("#startDate").val();
    queryData['endDate'] = $("#endDate").val();
    queryData['deviceName'] = $("#deviceName").val();
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
            content: Feng.ctxPath + '/deviceRecord/detail/' + DeviceRecord.seItem.id+"/"+DeviceRecord.seItem.tableName
        });
        this.layerIndex = index;
    }
}

//导出
DeviceRecord.download = function () {
	if($("#projectCode").val()==''){
		Feng.error("请选择项目!");
		return;
	}
    window.location.href = encodeURI(Feng.ctxPath + "/deviceRecord/exportExcel?"
        + "condition=" + $("#condition").val()
        + "&projectCodes=" + $("#projectCode").val()
        + "&team=" + $("#team").val())
        + "&isValid=" + $("#isValid").val()
        + "&deviceType=" + $("#deviceType").val()
        + "&source=" + $("#source").val()
        + "&startDate=" + $("#startDate").val()
        + "&endDate=" + $("#endDate").val()
}

$(function () {
	    var currentDate = new Date();
	    var year = currentDate.getFullYear();
	    var month = currentDate.getMonth()+1;
	    var date = currentDate.getDate();
		var recordTime = laydate.render({ elem: '#startDate' ,type:'datetime'  });

	

	    var endDate = new Date();
	    var year = endDate.getFullYear();
	    var month = endDate.getMonth()+1;
	    var date = endDate.getDate();
		var recordTimeEnd = laydate.render({ elem: '#endDate' ,type:'datetime'  });

	
	
    Feng.initChosen();
    var defaultColunms = DeviceRecord.initColumn();
    var table = new BSTable(DeviceRecord.id, "/deviceRecord/list", defaultColunms);
    table.setPaginationType("server");
    table.onDblClickRow = DeviceRecord.searchInfo;//双击事件所对应的方法 要放在init之前
    DeviceRecord.table = table.init();
    $("#projectCode").chosen().on("change", function (evt, data){
        var projectCode=data.selected;
        var ajax = new $ax(Feng.ctxPath + "/teamMaster/getList?projectCode=" + projectCode, function (data) {
            var teamMaster = $("#team");
            teamMaster.chosen("destroy");
            teamMaster.children("option").remove();
            var html = "<option value=''>请选择班组</option>";
            for (var i = 0; i < data.length; i++) {
                html += "<option value='" + data[i].teamSysNo + "'>" + data[i].teamName + "</option>"
            }
            teamMaster.append(html);
            teamMaster.chosen({search_contains: true,no_results_text: "暂无结果"});
        }, function (data) {
            Feng.error("班组加载失败!" + data.responseJSON.message + "!");
        });
        ajax.set(this.payRollDetailInfoData);
        ajax.start();
    });
});
