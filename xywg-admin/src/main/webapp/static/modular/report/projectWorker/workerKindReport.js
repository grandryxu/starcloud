/**
 * 工人管理管理初始化
 */
var WorkerKindReport = {
    id: "WorkerKindReportTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};


WorkerKindReport.formParams = function () {
    var queryData = {};
    queryData['type'] = 2;
    return queryData;
}


/**
 * 初始化表格的列
 */
WorkerKindReport.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: false,visible: false},
        {title: '工种', field: 'name', visible: true, align: 'center', valign: 'middle', footerFormatter: function () {
            return "合计";
        }},
        {title: '出勤人数', field: 'aCount', visible: true, align: 'center', valign: 'middle',
        	footerFormatter: function (value) {
                var count = 0;
                for (var i = 0; i < value.length; i++) {
                    count += value[i].aCount;
                }
                return count==0?'0':count;
            }},
        {title: '在场人数', field: 'iCount', visible: true, align: 'center', valign: 'middle',
        	footerFormatter: function (value) {
                var count = 0;
                for (var i = 0; i < value.length; i++) {
                    count += value[i].iCount;
                }
                return count==0?'0':count;
            }}
    ];
};

$(function () {
    Feng.initChosen();
    var defaultColunms = WorkerKindReport.initColumn();
    var table = new BSTable(WorkerKindReport.id, "/projectWorker/report/list", defaultColunms);
    table.setPaginationType("client");
    table.setQueryParams(WorkerKindReport.formParams());
    table.showFooter = true;
    WorkerKindReport.table = table.init();
});
