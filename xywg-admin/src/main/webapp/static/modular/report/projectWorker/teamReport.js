/**
 * 工人管理管理初始化
 */
var TeamReport = {
    id: "TeamReportTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};


TeamReport.formParams = function () {
    var queryData = {};
    queryData['type'] = 1;
    return queryData;
}


/**
 * 初始化表格的列
 */
TeamReport.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: false,visible: false},
        {title: '班组', field: 'name', visible: true, align: 'center', valign: 'middle', footerFormatter: function (row) {
        	
            return row!=''&&row!=undefined?"合计":"";
        }},
        {title: '进场人数', field: 'count', visible: true, align: 'center', valign: 'middle',
        	footerFormatter: function (value,row) {
                var count = 0;
                for (var i = 0; i < value.length; i++) {
                    count += value[i].count;
                }
                if(value.length>0){
                	return count==0?'0':count;
                }else{
                	return "";
                }
            }},
        {title: '出勤人数', field: 'aCount', visible: true, align: 'center', valign: 'middle',
        	footerFormatter: function (value,row) {
                var count = 0;
                for (var i = 0; i < value.length; i++) {
                    count += value[i].aCount;
                }
                if(value.length>0){
                	return count==0?'0':count;
                }else{
                	return "";
                }
            }},
        {title: '在场人数', field: 'iCount', visible: true, align: 'center', valign: 'middle',
        	footerFormatter: function (value,row) {
                var count = 0;
                for (var i = 0; i < value.length; i++) {
                    count += value[i].iCount;
                }
                if(value.length>0){
                	return count==0?'0':count;
                }else{
                	return "";
                }
            }},
       
        {title: '超龄人数', field: 'overCount', visible: true, align: 'center', valign: 'middle',
        	footerFormatter: function (value,row) {
                var count = 0;
                for (var i = 0; i < value.length; i++) {
                    count += value[i].overCount;
                }
                if(value.length>0){
                	return count==0?'0':count;
                }else{
                	return "";
                }
            }},
        {title: '出勤比例', field: 'percent', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
            return parseFloat(data*100).toFixed(2)+'%';
        }/*,
        	footerFormatter: function (value,row) {
                var count = 0;
                var acount = 0;
                for (var i = 0; i < value.length; i++) {
                    acount += value[i].aCount;
                }
                for (var i = 0; i < value.length; i++) {
                    count += value[i].count;
                }
                if(value.length>0){
                	return count==0?'0%':parseFloat(acount/count*100).toFixed(2)+'%';
                }else{
                	return "";
                }
            }*/}
    ];
};

$(function () {
    Feng.initChosen();
    var defaultColunms = TeamReport.initColumn();
    var table = new BSTable(TeamReport.id, "/projectWorker/report/list", defaultColunms);
    table.setPaginationType("client");
    table.setQueryParams(TeamReport.formParams());
    table.showFooter = true;
    TeamReport.table = table.init();
});
