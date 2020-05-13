/**
 * 工人黑名单记录管理初始化
 */
var CompanyBlackList = {
    id: "CompanyBlackListTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CompanyBlackList.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '企业名称', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '社会信用代码', field: 'socialCreditNumber', visible: true, align: 'center', valign: 'middle'},
        {title: '所属项目', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '发生时间', field: 'occurrenceDate', visible: true, align: 'center', valign: 'middle'},
        {title: '黑名单原因', field: 'blackReason', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
CompanyBlackList.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        CompanyBlackList.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加工人黑名单记录
 */
CompanyBlackList.openAddCompanyBlackList = function () {
    var index = layer.open({
        type: 2,
        title: '添加企业黑名单记录',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/companyBlackList/companyBlackList_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看工人黑名单记录详情
 */
CompanyBlackList.openCompanyBlackListDetail = function () {
    if (this.check()) {
        if (CompanyBlackList.seItem.isGeneralContractorOperation === 0) {
            Feng.info("只有总包公司具有操作权限");
            return;
        }
        var index = layer.open({
            type: 2,
            title: '企业黑名单记录修改',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/companyBlackList/companyBlackList_update/' + CompanyBlackList.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除工人黑名单记录
 */
CompanyBlackList.delete = function () {
    if (this.check()) {
        if(CompanyBlackList.seItem.isGeneralContractorOperation === 0){
            Feng.info("只有总包公司具有操作权限");
            return;
        }
        var self = this;
        layer.confirm('是否确认删除?', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            layer.closeAll();
            var ajax = new $ax(Feng.ctxPath + "/companyBlackList/delete", function (data) {
                Feng.success("删除成功!");
                CompanyBlackList.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("companyBlackListId", self.seItem.id);
            ajax.start();
        }, function () {

        });
    }
};

/**
 * 查询参数
 */
CompanyBlackList.formParams = function () {
    var queryData = {};
    queryData['companyName'] = $("#companyName").val();
    queryData['organizationCode'] = $("#organizationCode").val();
    queryData['projectName'] = $("#projectName").val();
    queryData['startDate'] = $("#startDate").val();
    queryData['endDate'] = $("#endDate").val();
    return queryData;
}

/**
 * 双击查看
 */
CompanyBlackList.searchInfo = function () {
    if (CompanyBlackList.check()) {
        var index = layer.open({
            type: 2,
            title: '企业黑名单记录详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/companyBlackList/companyBlackList_view/' + CompanyBlackList.seItem.id
        });
        this.layerIndex = index;
    }
}

/**
 * 查询工人黑名单记录列表
 */
CompanyBlackList.search = function () {
    var queryData = {};
    queryData['companyName'] = $("#companyName").val();
    queryData['organizationCode'] = $("#organizationCode").val();
    queryData['projectName'] = $("#projectName").val();
    queryData['startDate'] = $("#startDate").val();
    queryData['endDate'] = $("#endDate").val();
    if ($("#startDate").val() == '' || $("#endDate").val() == '') {
        CompanyBlackList.table.refresh({query: queryData});
    }
    if ($("#startDate").val() != '' && $("#endDate").val() != '') {
        if ($("#startDate").val() > $("#endDate").val()) {
            Feng.error("开始时间不能大于结束时间!");
        } else {
            CompanyBlackList.table.refresh({query: queryData});
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
    CompanyBlackList.resetDate = [startDate, endDate];
    var defaultColunms = CompanyBlackList.initColumn();
    var table = new BSTable(CompanyBlackList.id, "/companyBlackList/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(CompanyBlackList.formParams());
    table.onDblClickRow = CompanyBlackList.searchInfo;//双击事件所对应的方法 要放在init之前
    CompanyBlackList.table = table.init();
});

function resetDate() {
    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = currentDate.getMonth() + 1;
    var date = currentDate.getDate();

    for (var i = 0; i < CompanyBlackList.resetDate.length; i++) {
        var dateObject = CompanyBlackList.resetDate[i];

        //去除最小时间限制
        dateObject.config.min.year = '';
        dateObject.config.min.month = '';
        dateObject.config.min.date = '';

        dateObject.config.max.year = year;
        dateObject.config.max.month = month - 1;
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

