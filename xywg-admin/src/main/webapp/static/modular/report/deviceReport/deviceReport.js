/**
 * 考勤统计管理初始化
 */
var date = new Date();

var DeviceReport = {
    id: "DeviceReportTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    searchDay:getInitTime()
};

function getInitTime(){
    var date=new Date();
    var year=date.getFullYear();
    var month=date.getMonth()+1;
    month =(month<10 ? "0"+month:month);
    var mydate = (year.toString()+"-"+month.toString());
    return mydate;
}
/**
 * 初始化表格的列
 */
DeviceReport.initColumn = function () {
    return [
    	{field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '项目编号', field: 'projectCode', visible: false, align: 'center', valign: 'middle'},
        {title: '企业组织机构代码', field: 'organizationCode', visible: true, align: 'center', valign: 'middle'},
        {title: '企业名称', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型', field: 'idCardTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
            return Feng.hiddenIdCard(data);
        }},
        {title: '工人姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '班组编号', field: 'teamSysNo', visible: true, align: 'center', valign: 'middle'},
        {title: '班组名称', field: 'teamName', visible: true, align: 'center', valign: 'middle'},
        {title: '统计所在月份', field: 'time', visible: true, align: 'center', valign: 'middle'},
        {title: '总工数', field: 'totalTime', visible: true, align: 'center', valign: 'middle',
        	formatter:function (data,row,index) {
        		return parseFloat(data).toFixed(2);
             }},
        {title: '总工时', field: 'totalCount', visible: true, align: 'center', valign: 'middle',
             	formatter:function (data,row,index) {
            		return parseFloat(data).toFixed(2);
                 }},
        {title: '加班工时', field: 'overTime', visible: true, align: 'center', valign: 'middle'},
        {title: '出勤天数', field: 'totalDate', visible: true, align: 'center', valign: 'middle'},
        {title: '01', field: 'day01', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {
            if((new Date())<new Date((DeviceReport.searchDay+"-01"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='01'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='01'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='01'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-01"))){
                return {css:{}};
            }
            if (value === -1 || value ===""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '02', field: 'day02', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {
            if((new Date())<new Date((DeviceReport.searchDay+"-02"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='02'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='02'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='02'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-02"))){
                return {css:{}};
            }
            if (value === -1 || value ===""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '03', field: 'day03', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-03"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='03'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='03'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='03'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-03"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '04', field: 'day04', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-04"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='04'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='04'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='04'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-04"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '05', field: 'day05', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-05"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='05'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='05'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='05'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-05"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '06', field: 'day06', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-06"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='06'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='06'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='06'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-06"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '07', field: 'day07', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {
            if((new Date())<new Date((DeviceReport.searchDay+"-07"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='07'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='07'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='07'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-07"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '08', field: 'day08', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-08"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='08'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='08'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='08'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-08"))){
                return {css:{}};
            }

            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '09', field: 'day09', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-09"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='09'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='09'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='09'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-09"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '10', field: 'day10', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-10"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='10'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='10'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='10'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-10"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '11', field: 'day11', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-11"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='11'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='11'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='11'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-11"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '12', field: 'day12', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-12"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='12'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='12'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='12'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-12"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '13', field: 'day13', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-13"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='13'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='13'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='13'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-13"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '14', field: 'day14', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-14"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='14'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='14'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='14'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-14"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '15', field: 'day15', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-15"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='15'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='15'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='15'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-15"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '16', field: 'day16', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-16"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='16'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='16'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='16'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-16"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '17', field: 'day17', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {
            if((new Date())<new Date((DeviceReport.searchDay+"-17"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='17'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='17'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='17'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-17"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '18', field: 'day18', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-18"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='18'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='18'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='18'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-18"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '19', field: 'day19', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-19"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='19'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='19'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='19'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-19"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '20', field: 'day20', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-20"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='20'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='20'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='20'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-20"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '21', field: 'day21', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-21"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='21'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='21'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='21'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-21"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '22', field: 'day22', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-22"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='22'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='22'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='22'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-22"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '23', field: 'day23', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-23"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='23'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='23'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='23'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-23"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '24', field: 'day24', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-24"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='24'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='24'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='24'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){

            if((new Date())<new Date((DeviceReport.searchDay+"-24"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '25', field: 'day25', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-25"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='25'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='25'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='25'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-25"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '26', field: 'day26', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-26"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='26'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='26'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='26'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-26"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '27', field: 'day27', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-27"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='27'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='27'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='27'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-27"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '28', field: 'day28', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-28"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='28'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='28'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='28'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-28"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '29', field: 'day29', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {

            if((new Date())<new Date((DeviceReport.searchDay+"-29"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='29'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='29'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='29'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-29"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '30', field: 'day30', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {
            if((new Date())<new Date((DeviceReport.searchDay+"-30"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='30'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='30'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='30'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-30"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/},
        {title: '31', field: 'day31', visible: true, align: 'center', valign: 'middle',formatter:function (data,row,index) {
            if((new Date())<new Date((DeviceReport.searchDay+"-31"))){
                return "";
            }
            if(data === -1 || data === ""){
                return "<span  data-id='"+row.id+"' data-day='31'></span>"
            }else if(data === 0){
                return "<img src='/static/img/warning.png' data-id='"+row.id+"' data-day='31'></img>"
            }else{
                return "<span data-id='"+row.id+"' data-day='31'>"+data+"</span>"
            }
        }/*,cellStyle:function(value,row,index){
            if((new Date())<new Date((DeviceReport.searchDay+"-31"))){
                return {css:{}};
            }
            if(value === -1 || value === ""){
                return {css:{"background-color":"#ff0000"}}
            }else if(value==0){
                return {css:{"background-color":"#ff8100"}}
            }else{
                return {css:{}};
            }
        }*/}
    ];
};

/**
 * 检查是否选中
 */
DeviceReport.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        DeviceReport.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加考勤统计
 */
DeviceReport.openAddDeviceReport = function () {
    var index = layer.open({
        type: 2,
        title: '添加考勤统计',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/deviceReport/deviceReport_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看考勤统计详情
 */
DeviceReport.openDeviceReportDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '考勤统计详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/deviceReport/deviceReport_update/' + DeviceReport.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除考勤统计
 */
DeviceReport.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/deviceReport/delete", function (data) {
            Feng.success("删除成功!");
            DeviceReport.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("deviceReportId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 考勤统计
 */
DeviceReport.delete = function () {
    var ajax = new $ax(Feng.ctxPath + "/deviceReport/deviceReportDeal", function (data) {
        Feng.success("统计成功!");
        DeviceReport.table.refresh();
    }, function (data) {
        Feng.error("统计失败" + data.responseJSON.message + "!");
    });
    ajax.start();
};

/**
 * 项目下拉框选择事件
 * @param val
 */
function changeForm(val){
    var ajax = new $ax(Feng.ctxPath + "/settlement/getTeamMasterByProjectCode", function (data) {
        var htmlStr="<option value=\"\">请选择</option>";
        for(var i=0;i<data.length;i++){
            htmlStr+=("<option value='"+data[i].teamSysNo+"'>"+data[i].teamName+"</option>")
        }
        $("#teamSysNo").html(htmlStr);

    });
    ajax.set("projectCode",val);
    ajax.start();
}

/**
 * 查询考勤统计列表
 */
/*DeviceReport.search = function () {
 var queryData = {};
 queryData['condition'] = $("#condition").val();
 DeviceReport.table.refresh({query: queryData});
 };*/

$(function () {
    var date=new Date;
    var year=date.getFullYear();
    var month=date.getMonth()+1;
    month =(month<10 ? "0"+month:month);
    var currYearMonth = year.toString()+"-"+month.toString();

    laydate.render({
        elem: '#chTime',type: 'month',value:currYearMonth,
        showBottom:false,
        ready:function(date){
            $("#layui-laydate1").off('click').on('click','.laydate-month-list li',function(){
                $("#layui-laydate1").remove();
            });
        },
        change:function(value,dates,edate){
            $('#chTime').val(value);
        }
    });
    var defaultColunms = DeviceReport.initColumn();
    var table = new BSTable(DeviceReport.id, "/deviceReport/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams({chTime:currYearMonth});
    table.onDblClickRow = DeviceReport.onDblClickRow;
    table.onLoadSuccess = DeviceReport.onLoadSuccess;
    DeviceReport.table = table.init();
});
/**
 * 初始化
 */
DeviceReport.onLoadSuccess = function(data){
    $('table td span').bind('click',function(){
        DeviceReport.selectDay($(this).data('id'),$(this).data('day'));
    });
    $('table td img').bind('click',function(){
        DeviceReport.selectDay($(this).data('id'),$(this).data('day'));
    });
}
/**
 * 查询结算单列表
 */
DeviceReport.search = function () {
    var queryData = {};
    queryData['projectCode'] = $("#projectCode").val();
    queryData['teamSysNo'] = $("#teamSysNo").val();
    queryData['workerName'] = $("#workerName").val();
    queryData['chTime'] = $("#chTime").val();
    DeviceReport.table.refresh({query: queryData});

    //记录当前搜索的月份
    DeviceReport.searchDay = $("#chTime").val();
};


/**
 * 导出考勤统计
 */
DeviceReport.download = function () {
    window.location.href = Feng.ctxPath + "/deviceReport/download?"
        + "teamSysNo=" + $("#teamSysNo").val()
        + "&projectCode=" + $("#projectCode").val()
        + "&workerName=" + $("#workerName").val()
        + "&chTime=" + $("#chTime").val()
};




//双击查看事件 hujingyun
DeviceReport.selectDay = function (sid,sday) {
    var index = layer.open({
        type: 2,
        title: '考勤统计详情',
        area: ['80%', '70%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/deviceRecord/getDayRecords?sid='+sid+'&sday='+sday
    });
    this.layerIndex = index;
};

/**
 * 新增加班工时
 */
DeviceReport.overTimeAdd = function () {
    if (this.check()) {
    var index = layer.open({
        type: 2,
        title: '加班',
        area: ['30%', '30%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: "layer-detail",
        content: Feng.ctxPath + '/deviceReport/deviceReport_over/' + DeviceReport.seItem.id
    });
    this.layerIndex = index;
}
};

function  resetDate() {
    $("#teamSysNo").html("<option value=''>请选择</option>");
}

