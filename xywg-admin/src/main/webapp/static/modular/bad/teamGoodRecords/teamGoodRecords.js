/**
 * 工人奖励记录管理初始化
 */
var TeamGoodRecords = {
    id: "TeamGoodRecordsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TeamGoodRecords.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '班组编号', field: 'teamSysNo', visible: true, align: 'center', valign: 'middle'},
            {title: '班组名称', field: 'teamName', visible: true, align: 'center', valign: 'middle'},
            {title: '所属项目', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
            {title: '所属单位', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
            {title: '奖励类型', field: 'goodRecordType', visible: true, align: 'center', valign: 'middle'},
            {title: '奖励级别', field: 'goodRecordLevelTypeName', visible: true, align: 'center', valign: 'middle'},
            {title: '奖励时间', field: 'occurrenceDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 查询参数
 */
TeamGoodRecords.formParams = function () {
    var queryData = {};
    queryData['teamName'] = $("#teamName").val();
    queryData['projectName'] = $("#projectName").val();
    queryData['goodRecordLevelType'] = $("#goodRecordLevelType").val();
    queryData['startDate'] = $("#startDate").val();
    queryData['endDate'] = $("#endDate").val();
    return queryData;
}

/**
 * 双击查看
 */
TeamGoodRecords.searchInfo = function () {
    if (TeamGoodRecords.check()) {
        var index = layer.open({
            type: 2,
            title: '班组奖励记录详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/teamGoodRecords/teamGoodRecords_view/' + TeamGoodRecords.seItem.id
        });
        this.layerIndex = index;
    }
}

/**
 * 检查是否选中
 */
TeamGoodRecords.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TeamGoodRecords.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加工人奖励记录
 */
TeamGoodRecords.openAddTeamGoodRecords = function () {
    var index = layer.open({
        type: 2,
        title: '添加班组奖励记录',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/teamGoodRecords/teamGoodRecords_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看工人奖励记录详情
 */
TeamGoodRecords.openTeamGoodRecordsDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '班组奖励记录修改',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/teamGoodRecords/teamGoodRecords_update/' + TeamGoodRecords.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除工人奖励记录
 */
TeamGoodRecords.delete = function () {
    if (this.check()) {
        var self = this;
        layer.confirm('是否确认删除?', {
            btn: ['确定','取消'] //按钮
        }, function(){
            layer.closeAll();
            var ajax = new $ax(Feng.ctxPath + "/teamGoodRecords/delete", function (data) {
                Feng.success("删除成功!");
                TeamGoodRecords.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("teamGoodRecordsId",self.seItem.id);
            ajax.start();
        }, function(){

        });
    }
};

/**
 * 查询工人奖励记录列表
 */
TeamGoodRecords.search = function () {
    var queryData = {};
    queryData['teamName'] = $("#teamName").val();
    queryData['projectName'] = $("#projectName").val();
    queryData['goodRecordLevelType'] = $("#goodRecordLevelType").val();
    queryData['startDate'] = $("#startDate").val();
    queryData['endDate'] = $("#endDate").val();
    if ($("#startDate").val() == '' || $("#endDate").val() == '') {
    	TeamGoodRecords.table.refresh({query: queryData});
       }
    if ($("#startDate").val() != '' && $("#endDate").val() != '') {
       	if ($("#startDate").val() > $("#endDate").val()) {
       		Feng.error("开始时间不能大于结束时间!");
       	} else {
       		TeamGoodRecords.table.refresh({query: queryData});
       	}
    }
    
};

$(function () {
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
                startDate.config.max.month = currDate.getMonth()+1;
                startDate.config.max.date =  currDate.getDate();
            }
        },
        max:0
    });
    //重置的时间集合
    TeamGoodRecords.resetDate = [startDate,endDate];

    var defaultColunms = TeamGoodRecords.initColumn();
    var table = new BSTable(TeamGoodRecords.id, "/teamGoodRecords/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(TeamGoodRecords.formParams());
    table.onDblClickRow = TeamGoodRecords.searchInfo;//双击事件所对应的方法 要放在init之前
    TeamGoodRecords.table = table.init();
});

function  resetDate() {
    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = currentDate.getMonth()+1;
    var date = currentDate.getDate();

    for(var i=0;i<TeamGoodRecords.resetDate.length; i++) {
        var dateObject = TeamGoodRecords.resetDate[i];

        //去除最小时间限制
        dateObject.config.min.year = '';
        dateObject.config.min.month = '';
        dateObject.config.min.date = '';

        dateObject.config.max.year = year;
        dateObject.config.max.month = month-1;
        dateObject.config.max.date = date;

        //设置当前时间
        dateObject.config.dateTime.year = year;
        dateObject.config.dateTime.month = month;
        dateObject.config.dateTime.date = date;
        // dateObject.startDate.config.dateTime.hours = '时';
        // dateObject.startDate.config.dateTime.minutes = '分';
        // dateObject.startDate.config.dateTime.seconds = '秒';
    }
}

