/**
 * 考勤统计管理初始化
 */
var date = new Date();

var AttendanceReport = {
    id: "AttendanceReportTable",	//表格id
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
AttendanceReport.initColumn = function () {
    return [
        {field: 'selectItem', radio: true, visible: false},
        {title: '项目', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '班组', field: 'teamName', visible: true, align: 'center', valign: 'middle'},
        {title: '工人姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型', field: 'idCardType', visible: true, align: 'center', valign: 'middle'},
        {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
            return Feng.hiddenIdCard(data);
        }},
        {title: '月份', field: 'searchMonth', visible: true, align: 'center', valign: 'middle'},
        {title: '出勤天数', field: 'attendanceDay', visible: true, align: 'center', valign: 'middle'}
    ];
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
    var defaultColunms = AttendanceReport.initColumn();
    var table = new BSTable(AttendanceReport.id, "/workerAttendanceReport/getList", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams({searchMonth:currYearMonth});
    AttendanceReport.table = table.init();
});


AttendanceReport.search = function () {
    var queryData = {};
    queryData['projectCode'] = $("#projectCode").val();
    queryData['teamCode'] = $("#teamSysNo").val();
    queryData['searchMonth'] = $("#chTime").val();
    AttendanceReport.table.refresh({query: queryData});

    //记录当前搜索的月份
    AttendanceReport.searchDay = $("#chTime").val();
};

/**
 * 导出考勤统计
 */
AttendanceReport.download = function () {
    window.location.href = Feng.ctxPath + "/workerAttendanceReport/export?"
        + "teamCode=" + $("#teamSysNo").val()
        + "&projectCode=" + $("#projectCode").val()
        + "&searchMonth=" + $("#chTime").val()
};

function  resetDate() {
    $("#teamSysNo").html("<option value=''>请选择</option>");
}

