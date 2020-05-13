/**
 * 考勤机管理管理初始化
 */
var QueryCommandBySeriesHtml = {
    id: "queryCommandBySeriesHtmlTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
QueryCommandBySeriesHtml.initColumn = function () {
    return [
        {field: 'id', checkbox: true, visible: false },
        {title: '设备序列号', field: 'deviceSn', visible: true, align: 'center', valign: 'middle'},
        {title: '命令下发人员身份证号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle'},
        {title: '命令下发人员', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        /*{title: '证件类型', field: 'idCardType', visible: true, align: 'center', valign: 'middle'},
        {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle'},
        {title: '升级设备时的文件ID', field: 'fileId', visible: true, align: 'center', valign: 'middle'},*/
        {title: '命令类型', field: 'type', visible: true, align: 'center', valign: 'middle',
            formatter:function(data){
                var  value='';
                if(data==4){
                    value="升级汉王内核";
                }else if(data==1){
                    value="同步设备时间";
                }else if(data==2){
                    value="重启设备";
                }else if(data==3){
                    value="获取设备信息";
                }
                else if(data==5){
                    value="清空考勤记录";
                }
                else if(data==6){
                    value="删除所有人员";
                }
                else if(data==7){
                    value="手动下发人员信息";
                }
                else if(data==8){
                    value="移除个别人员";
                }
                return value;
            }

        },
        {title: '状态', field: 'state', visible: true, align: 'center', valign: 'middle',
            formatter:function(data){
                var  value='';
                if(data==0){
                    value="未执行";
                }else if(data==1){
                    value="成功";
                }else if(data== -1){
                    value="失败";
                }else if(data==999){
                    value="取消";
                }
                return value;
            }

        },
        {title: '执行时间', field: 'processDate', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
    ];
};

$(function () {
    //初始化时间插件
    var currDate = new Date();
    var startDate = laydate.render({
        elem: '#startDate',
        type: 'date',
        done: function (value, date) {
            if (value !== '') {
                endDate.config.min.year = date.year;
                endDate.config.min.month = date.month - 1;
                endDate.config.min.date = date.date;
            } else {
                endDate.config.min.year = '';
                endDate.config.min.month = '';
                endDate.config.min.date = '';
            }
        },
        max:0
    });
    //设置结束时间
    var endDate = laydate.render({
        elem: '#endDate',
        type: 'date',
        done: function (value, date) {
            if (value !== '') {
                startDate.config.max.year = date.year;
                startDate.config.max.month = date.month - 1;
                startDate.config.max.date = date.date;
            } else {
                startDate.config.max.year = currDate.getFullYear();
                startDate.config.max.month = currDate.getMonth() + 1;
                startDate.config.max.date = currDate.getDate();
            }
        },
        max:0
    });
    //设置开始时间
    var month = currDate.getMonth() + 1;
    var day = currDate.getDate();
    if(month<10){
        month = "0" + month;
    }
    if(day<10){
        day = "0" + day;
    }
    QueryCommandBySeriesHtml.resetDate = [startDate,endDate];
    $("#startDate").val(currDate.getFullYear()+"-"+month+"-"+day);
    $("#endDate").val(currDate.getFullYear()+"-"+month+"-"+day);

    var defaultColunms = QueryCommandBySeriesHtml.initColumn();
    var table = new BSTable(QueryCommandBySeriesHtml.id, "/deviceCommand/queryCommandBySeries?deviceSns="+$("#deviceSns").val(), defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams({"beginTime":currDate.getFullYear()+"-"+month+"-"+day});
    table.onLoadSuccess = QueryCommandBySeriesHtml.onLoadSuccess;
    table.onDblClickRow = QueryCommandBySeriesHtml.onDblClickRow;
    QueryCommandBySeriesHtml.table = table.init();
    Feng.initChosen();
});

/**
 * 查询结算单列表
 */
QueryCommandBySeriesHtml.search = function () {
    var queryData = {};
    queryData['deviceSn'] = $("#deviceSn").val();
    queryData['beginTime'] = $("#startDate").val();
    queryData['endTime'] = $("#endDate").val();
    queryData['projectCode'] = $("#projectCode").val();
    QueryCommandBySeriesHtml.table.refresh({query: queryData});
};

function  resetDate() {
    //设置开始时间
    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var date = currentDate.getDate();
    var month = currentDate.getMonth() + 1;
    var day = currentDate.getDate();
    if(month<10){
        month = "0" + month;
    }
    if(day<10){
        day = "0" + day;
    }



    for(var i=0;i<QueryCommandBySeriesHtml.resetDate.length; i++) {
        var dateObject = QueryCommandBySeriesHtml.resetDate[i];

        //去除最小时间限制
        dateObject.config.min.year = '';
        dateObject.config.min.month = '';
        dateObject.config.min.date = '';

        dateObject.config.max.year = '9999';
        dateObject.config.max.month = '12';
        dateObject.config.max.date = '31';
    }

    $("#startDate").val(year+"-"+month+"-"+day);
    $("#endDate").val(year+"-"+month+"-"+day);
}







