/**
 * 不良记录管理初始化
 */
var CompanyBadRecords = {
    id: "CompanyBadRecordsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 查询参数
 */
CompanyBadRecords.formParams = function () {
    var queryData = {};
    queryData['companyName'] = $("#companyName").val();
    queryData['projectName'] = $("#projectName").val();
    queryData['badRecordLevelType'] = $("#badRecordLevelType").val();
    queryData['isAudit'] = $("#isAudit").val();
    queryData['startDate'] = $("#startDate").val();
    queryData['endDate'] = $("#endDate").val();
    queryData['isAudit'] = $("#isAudit").val();
    return queryData;
}

/**
 * 初始化表格的列
 */
CompanyBadRecords.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '企业名称', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
            {title: '社会信用代码', field: 'organizationCode', visible: true, align: 'center', valign: 'middle'},
            {title: '所属项目', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
            {title: '事件类别', field: 'badRecordType', visible: true, align: 'center', valign: 'middle'},
            {title: '事件级别', field: 'badRecordLevelTypeName', visible: true, align: 'center', valign: 'middle'},
            {title: '发生时间', field: 'occurrenceDate', visible: true, align: 'center', valign: 'middle'},
            {title: '审核状态', field: 'isAuditName', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 提交审核
 */
CompanyBadRecords.submitWorkerBadRecords = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/workerBadRecords/submitWorkerBadRecords", function (data) {
            Feng.success("操作成功!");
            CompanyBadRecords.table.refresh();
        }, function (data) {
            Feng.error("操作失败!" + data.responseJSON.message + "!");
        });
        ajax.set("workerBadRecordsId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 取消审核
 */
CompanyBadRecords.cancelWorkerBadRecords = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/workerBadRecords/cancelWorkerBadRecords", function (data) {
            Feng.success("操作成功!");
            CompanyBadRecords.table.refresh();
        }, function (data) {
            Feng.error("操作失败!" + data.responseJSON.message + "!");
        });
        ajax.set("workerBadRecordsId",this.seItem.id);
        ajax.start();
    }
};


/**
 * 检查是否选中
 */
CompanyBadRecords.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	CompanyBadRecords.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加不良记录
 */
CompanyBadRecords.openAddCompanyBadRecords = function () {
    var index = layer.open({
        type: 2,
        title: '添加企业不良记录',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/companyBadRecords/companyBadRecords_add'
    });
    this.layerIndex = index;
};

/**
 * 打开修改页面
 */
CompanyBadRecords.openCompanyBadRecordsDetail = function () {
    if (this.check()) {
    	if(CompanyBadRecords.seItem.isAudit == 2) {
    		Feng.error("该条记录已通过审核，不能修改!");
    	} else {
    		var index = layer.open({
                type: 2,
                title: '企业不良记录修改',
                area: ['100%', '100%'], //宽高
                fix: false, //不固定
                maxmin: true,
                content: Feng.ctxPath + '/companyBadRecords/companyBadRecords_update/' + CompanyBadRecords.seItem.id
            });
            this.layerIndex = index;
    	}
        
    }
};

/**
 * 双击查看
 */
CompanyBadRecords.searchInfo = function () {
    if (CompanyBadRecords.check()) {
        var index = layer.open({
            type: 2,
            title: '企业不良记录详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/companyBadRecords/companyBadRecords_view/' + CompanyBadRecords.seItem.id
        });
        this.layerIndex = index;
    }
}

/**
 * 删除不良记录
 */
CompanyBadRecords.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/companyBadRecords/delete", function (data) {
            Feng.success("删除成功!");
            CompanyBadRecords.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("companyBadRecordsId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询不良记录列表
 */
CompanyBadRecords.search = function () {
    var queryData = {};
    queryData['organizationCode'] = $("#organizationCode").val();
    queryData['companyName'] = $("#companyName").val();
    queryData['projectName'] = $("#projectName").val();
    queryData['badRecordLevelType'] = $("#badRecordLevelType").val();
    queryData['isAudit'] = $("#isAudit").val();
    queryData['startDate'] = $("#startDate").val();
    queryData['endDate'] = $("#endDate").val();
    if ($("#startDate").val() == '' || $("#endDate").val() == '') {
   	 CompanyBadRecords.table.refresh({query: queryData});
    }
    if ($("#startDate").val() != '' && $("#endDate").val() != '') {
    	if ($("#startDate").val() > $("#endDate").val()) {
    		Feng.error("开始时间不能大于结束时间!");
    	} else {
    		CompanyBadRecords.table.refresh({query: queryData});
    	}
    }
   
   
};

$(function () {
    var defaultColunms = CompanyBadRecords.initColumn();
    var table = new BSTable(CompanyBadRecords.id, "/companyBadRecords/getListBySubContractor?organizationCode="+$("#organizationCode").val(), defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(CompanyBadRecords.formParams());
    table.onDblClickRow = CompanyBadRecords.searchInfo;//双击事件所对应的方法 要放在init之前
    CompanyBadRecords.table = table.init();
});
