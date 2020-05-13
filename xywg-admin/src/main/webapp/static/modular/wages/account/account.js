/**
 * 计工单管理初始化
 */
var Account = {
    id: "AccountTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Account.initColumn = function () {
    return [
        {field: 'selectItem', check: true},
        {title: '所属公司', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目编号', field: 'projectCode', visible: true, align: 'center', valign: 'middle'},
        {title: '班组', field: 'teamName', visible: true, align: 'center', valign: 'middle'},
        //{title: '类型', field: 'type', visible: true, align: 'center', valign: 'middle'},
        {
            title: '截止时间', field: 'closingTime', visible: true, align: 'center', valign: 'middle',
            formatter: function (value) {
                if (value != null) {
                    return value.substring(0, 10);
                } else
                    return value;
            }
        },
        {
            title: '合计发放(元)', field: 'totalAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (data) {
                if (data == null) {
                    return 0.00;
                } else {
                    return data.toFixed(2);
                }
            }
        },
        {
            title: '是否生成工资单', field: 'salaryId', visible: true, align: 'center', valign: 'middle',
            formatter: function (value) {
                if (value == null) {
                    return "否";
                }
                if (value != null) {
                    return "是";
                }
            }
        },
    ];
};

/**
 * 检查是否选中
 */
Account.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("请先选中表格中的一记录！");
    }
    else {
        Account.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加计工单
 */
Account.openAddAccount = function () {
    var index = layer.open({
        type: 2,
        title: '添加计工单',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/accountDetail/accountDetail_add',
        end: function () {
            Account.table.refresh();
        }
    });
    layer.full(index);
    this.layerIndex = index;
};

/**
 * 打开查看计工单详情
 */
Account.openAccountDetail = function () {
    if (this.check()) {
        if (hasOperationAuth()) {
            if (Account.seItem.isGeneralContractorOperation === 0) {
                Feng.error("您无权操作参建单位" + Account.seItem.companyName + "的数据!");
                return;
            }
        }
        if (Account.seItem.salaryId != null) {
            Feng.info("该计工单已生成工资单不能编辑！")
            return;
        }
        var index = layer.open({
            type: 2,
            title: '计工单详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: 'layer-no-title',
            content: Feng.ctxPath + '/account/account_update/' + Account.seItem.id
        });
        layer.full(index);
        this.layerIndex = index;
    }
};

/**
 * 删除计工单
 */
Account.delete = function () {
    if (this.check()) {
        //项目级总包
        if (hasOperationAuth()) {
            if (Account.seItem.isGeneralContractorOperation === 0) {
                Feng.error("您无权操作参建单位" + Account.seItem.companyName + "的数据!");
                return;
            }
        }

        Feng.confirm("确认删除？", function () {
            var ajax = new $ax(Feng.ctxPath + "/account/delete", function (data) {
                if (data.code == 600) {
                    Feng.error(data.message);
                } else {
                    Feng.success("删除成功!");
                    Account.table.refresh();
                }

            }, function (data) {
                Feng.error("删除失败!" + data + "!");
            });
            ajax.set("accountId", Account.seItem.id);
            ajax.start();
        })

    }
};
/**
 * 查询计工单列表
 */
Account.search = function () {
    var queryData = {};
    queryData['projectCode'] = $("#projectCode").val();
    queryData['teamSysNo'] = $("#team").val();
    queryData['startDate'] = $("#startDate").val();
    queryData['endDate'] = $("#endDate").val();
    if (queryData.startDate != '' && queryData.endDate != '') {
        if (queryData.startDate > queryData.endDate) {
            Feng.info("开始时间不能小于结束时间！");
            return;
        }
    }
    Account.table.refresh({query: queryData});
};

Account.searchInfo = function (e) {
    var index = layer.open({
        type: 2,
        title: '计工单信息详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: "layer-no-title",
        content: Feng.ctxPath + '/account/account_view/' + e.id
    });
    this.layerIndex = index;
}

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
    Account.resetDate = [startDate,endDate];

    Feng.initChosen();
    var defaultColunms = Account.initColumn();
    var table = new BSTable(Account.id, "/account/list", defaultColunms);
    table.setPaginationType("server");
    table.onDblClickRow = Account.searchInfo;
    Account.table = table.init();
    $("#projectCode").chosen().on("change", function (evt, data) {
        var projectCode = data.selected;
        var ajax = new $ax(Feng.ctxPath + "/teamMaster/getList?projectCode=" + projectCode, function (data) {
            var teamMaster = $("#team");
            teamMaster.chosen("destroy");

            teamMaster.children("option").remove();
            var html = "<option value=''>请选择班组</option>";
            for (var i = 0; i < data.length; i++) {
                html += "<option value='" + data[i].teamSysNo + "'>" + data[i].teamName + "</option>"
            }
            teamMaster.append(html);
            teamMaster.chosen();
        }, function (data) {
            Feng.error("班组加载失败!" + data.responseJSON.message + "!");
        });
        ajax.start();
    });
});


function  resetDate() {
    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = currentDate.getMonth()+1;
    var date = currentDate.getDate();

    for(var i=0;i<Account.resetDate.length; i++) {
        var dateObject = Account.resetDate[i];

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
