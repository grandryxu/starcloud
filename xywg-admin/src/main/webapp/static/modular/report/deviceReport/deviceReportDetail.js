/**
 * 考勤统计管理初始化
 */
var DeviceReport = {
    id: "DeviceReportTable1",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1

};

/**
 * 初始化表格的列
 */
DeviceReport.initColumn = function () {
    return [
    	{field: 'selectItem', radio: true,visible: false},
        {title: '考勤图片', field: 'iconPhoto', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
            if(data){
                return "<img src='"+Feng.imagePath+data+"' style='width:100px;height: 100px;' />";
            }else{
                return "无";
            }
        }},
        {title: '考勤时间', field: 'time', visible: true, align: 'center', valign: 'middle'},
        {title: '考勤地址', field: 'address', visible: true, align: 'center', valign: 'middle'},
        {title: '是否有效', field: 'isValid', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
            if(data=1){
                return "有效";
            }else{
                return "无效";
            }

        }},
        {title: '考勤类型', field: 'deviceType', visible: true, align: 'center', valign: 'middle',
            formatter:function (data) {
            if(data==3){
                return "上班";
            }else{
                return "下班";
            }

        }}
    ];
};


$(function () {
    //初始化表格
    initializeDataTable();
});

/**
 * 初始化表格
 */
function initializeDataTable() {
    var defaultColunms = DeviceReport.initColumn();
    var table = new BSTable(DeviceReport.id, "/deviceRecord/getDayRecordsData", defaultColunms);
    // table.pagination = false;
    // table.showFooter = true;
    //table.setPaginationType("server");
    table.setQueryParams({"sid":$("#sid").val(),"sday":$("#sday").val()});
    table.pagination = false;
    table.showFooter = true;
    table.setPaginationType("client");
    //table.onDblClickRow = DeviceReport.onDblClickRow;
    DeviceReport.table = table.init();
}