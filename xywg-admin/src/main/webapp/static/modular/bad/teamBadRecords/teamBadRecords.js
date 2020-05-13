/**
 * 不良记录管理初始化
 */
var TeamBadRecords = {
    id: "TeamBadRecordsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 查询参数
 */
TeamBadRecords.formParams = function () {
    var queryData = {};
    queryData['teamName'] = $("#teamName").val();
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
TeamBadRecords.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '班组编号', field: 'teamSysNo', visible: true, align: 'center', valign: 'middle'},
        {title: '班组名称', field: 'teamName', visible: true, align: 'center', valign: 'middle'},
        {title: '所属项目', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '所属单位', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '事件类别', field: 'badRecordType', visible: true, align: 'center', valign: 'middle'},
        {title: '事件级别', field: 'badRecordLevelTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '发生时间', field: 'occurrenceDate', visible: true, align: 'center', valign: 'middle'},
        {title: '审核状态', field: 'isAuditName', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
TeamBadRecords.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        TeamBadRecords.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加不良记录
 */
TeamBadRecords.openAddTeamBadRecords = function () {
    var index = layer.open({
        type: 2,
        title: '添加班组不良记录',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/teamBadRecords/teamBadRecords_add'
    });
    this.layerIndex = index;
};

/**
 * 打开修改页面
 */
TeamBadRecords.openTeamBadRecordsDetail = function () {
    if (this.check()) {
        if (this.checkPass()) {
            if (TeamBadRecords.seItem.isAudit == 2) {
                Feng.error("该条记录已通过审核，不能修改!");
            } else {
                var index = layer.open({
                    type: 2,
                    title: '班组不良记录修改',
                    area: ['100%', '100%'], //宽高
                    fix: false, //不固定
                    maxmin: true,
                    content: Feng.ctxPath + '/teamBadRecords/teamBadRecords_update/' + TeamBadRecords.seItem.id
                });
                this.layerIndex = index;
            }

        }
    }
};
TeamBadRecords.checkPass = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected[0].isAuditName !== '待审核') {
        Feng.error("只能修改或删除待审核的记录");
        return false;
    } else {
        return true
    }
}
/**
 * 删除不良记录
 */
TeamBadRecords.delete = function () {
    if (this.check()) {
        if (this.checkPass()) {
            if (TeamBadRecords.seItem.isAudit == 2) {
                Feng.error("该条记录已通过审核，不能删除!");
            } else {
                var self = this;
                layer.confirm('是否确认删除?', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    layer.closeAll();
                    var ajax = new $ax(Feng.ctxPath + "/teamBadRecords/delete", function (data) {
                        Feng.success("删除成功!");
                        TeamBadRecords.table.refresh();
                    }, function (data) {
                        Feng.error("删除失败!" + data.responseJSON.message + "!");
                    });
                    ajax.set("teamBadRecordsId", self.seItem.id);
                    ajax.start();
                }, function () {

                });
            }
        }
    }
};

/**
 * 双击查看
 */
TeamBadRecords.searchInfo = function () {
    if (TeamBadRecords.check()) {
        var index = layer.open({
            type: 2,
            title: '班组不良记录详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/teamBadRecords/teamBadRecords_view/' + TeamBadRecords.seItem.id
        });
        this.layerIndex = index;
    }
}


/**
 * 提交审核
 */
TeamBadRecords.submitWorkerBadRecords = function () {
    if (this.check()) {
        Feng.confirm("确认审核通过？", function () {
            var ajax = new $ax(Feng.ctxPath + "/workerBadRecords/submitWorkerBadRecords", function (data) {
                Feng.success("操作成功!");
                TeamBadRecords.table.refresh();
            }, function (data) {
                Feng.error("操作失败!" + data.responseJSON.message + "!");
            });
            ajax.set("workerBadRecordsId", TeamBadRecords.seItem.id);
            ajax.start();
        })
    }
};

/**
 * 取消审核
 */
TeamBadRecords.cancelWorkerBadRecords = function () {
    if (this.check()) {
        Feng.confirm("确认审核不通过？", function () {
            var ajax = new $ax(Feng.ctxPath + "/workerBadRecords/cancelWorkerBadRecords", function (data) {
                Feng.success("操作成功!");
                TeamBadRecords.table.refresh();
            }, function (data) {
                Feng.error("操作失败!" + data.responseJSON.message + "!");
            });
            ajax.set("workerBadRecordsId", TeamBadRecords.seItem.id);
            ajax.start();
        })
    }
};

/**
 * 查询不良记录列表
 */
TeamBadRecords.search = function () {
    var queryData = {};
    queryData['teamName'] = $("#teamName").val();
    queryData['projectName'] = $("#projectName").val();
    queryData['badRecordLevelType'] = $("#badRecordLevelType").val();
    queryData['isAudit'] = $("#isAudit").val();
    queryData['startDate'] = $("#startDate").val();
    queryData['endDate'] = $("#endDate").val();
    queryData['isAudit'] = $("#isAudit").val();
    if ($("#startDate").val() == '' || $("#endDate").val() == '') {
        TeamBadRecords.table.refresh({query: queryData});
    }
    if ($("#startDate").val() != '' && $("#endDate").val() != '') {
        if ($("#startDate").val() > $("#endDate").val()) {
            Feng.error("开始时间不能大于结束时间!");
        } else {
            TeamBadRecords.table.refresh({query: queryData});
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
        max: 0
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
        max: 0
    });
    //重置的时间集合
    TeamBadRecords.resetDate = [startDate,endDate];

    var defaultColunms = TeamBadRecords.initColumn();
    var table = new BSTable(TeamBadRecords.id, "/teamBadRecords/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(TeamBadRecords.formParams());
    table.onDblClickRow = TeamBadRecords.searchInfo;//双击事件所对应的方法 要放在init之前
    TeamBadRecords.table = table.init();
});

function  resetDate() {
    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = currentDate.getMonth()+1;
    var date = currentDate.getDate();

    for(var i=0;i<TeamBadRecords.resetDate.length; i++) {
        var dateObject = TeamBadRecords.resetDate[i];

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

