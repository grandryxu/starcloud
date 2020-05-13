/**
 * 初始化工资详情详情对话框
 */
var PayRollDetailInfoDlg = {
    payRollDetailInfoData: {}
};

//工资类型
var type = $("#type").val();
//项目编号
var projectCode;

//新增的详情数据
var payRollDetailList = [];
//新增的工资单
var payRoll = {};

/**
 * 按计工单生成的工资单
 */
var DetailByAccount = {
    id: "DetailByAccount",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 按工时生成的工资单
 */
var DetailByTime = {
    id: "DetailByTime",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
DetailByAccount.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle',
            footerFormatter: function () {
                return "合计";
            }},
        {title: '证件类型', field: 'idCardName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型编号', field: 'idCardType', visible: false, align: 'center', valign: 'middle'},
        {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
            return Feng.hiddenIdCard(data);
        }},
        {title: '工种', field: 'workKindName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '基本工资', field: 'amount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='amount' data-index='"+ index +"'>" + value.toFixed(2) + "</a>";
            },
            footerFormatter: function (value) {
                return "<span class='amount'></span>";
            }
        },
        {
            title: '奖励(元)', field: 'rewardAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='rewardAmount' data-index='"+ index +"'>" + value.toFixed(2) + "</a>";
            }
        },
        {
            title: '惩罚(元)', field: 'punishAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='punishAmount' data-index='"+ index +"'>" + value.toFixed(2) + "</a>";
            }
        },
        {title: '应发工资(元)', field: 'payAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return  Number(value).toFixed(2);
            },
            footerFormatter: function (value) {
                return "<span class='payAmount'></span>";
            }},
        {
            title: '实发工资(元)', field: 'actualAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='actualAmount' data-index='"+ index +"'>" + value.toFixed(2) + "</a>";
            },
            footerFormatter: function (value) {
                return "<span class='actualAmount'></span>";
            }
        },
        {title: '剩余工资(元)', field: 'balanceAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return Number(value).toFixed(2);
            },
            footerFormatter: function (value) {
                return "<span class='balanceAmount'></span>";
            }}

    ];
};

/**
 * 初始化表格的列
 */
DetailByTime.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle',
            footerFormatter: function () {
                return "合计";
            }},
        {title: '证件类型', field: 'idCardName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型编号', field: 'idCardType', visible: false, align: 'center', valign: 'middle'},
        {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
            return Feng.hiddenIdCard(data);
        }},
        {title: '工种', field: 'workKindName', visible: true, align: 'center', valign: 'middle'},
        {title: '考勤天数', field: 'days', visible: true, align: 'center', valign: 'middle'},
        {title: '单价(元)', field: 'price', visible: true, align: 'center', valign: 'middle'},
        {title: '基本工资(元)', field: 'amount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='amount' data-index='"+ index +"'>" + Number(value).toFixed(2) + "</a>";
            },
            footerFormatter: function (value) {
                return "<span class='amount'></span>";
            }},
        {title: '奖励(元)', field: 'rewardAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='rewardAmount' data-index='"+ index +"'>" + value.toFixed(2) + "</a>";
            }},
        {title: '惩罚(元)', field: 'punishAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='punishAmount' data-index='"+ index +"'>" + value.toFixed(2) + "</a>";
            }},
        {title: '应发工资(元)', field: 'payAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return  Number(value).toFixed(2);
            },
            footerFormatter: function (value) {
                return "<span class='payAmount'></span>";
            }},
        {title: '实发工资(元)', field: 'actualAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='actualAmount' data-index='"+ index +"'>" + value.toFixed(2) + "</a>";
            },
            footerFormatter: function (value) {
                return "<span class='actualAmount'></span>";
            }},
        {title: '剩余工资(元)', field: 'balanceAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return  Number(value).toFixed(2);
            },
            footerFormatter: function (value) {
                return "<span class='balanceAmount'></span>";
            }}
    ];
};

PayRollDetailInfoDlg.clearData = function () {
    this.payRollDetailInfoData = {};
}



/**
 * 关闭此对话框
 */
PayRollDetailInfoDlg.close = function () {
    parent.layer.close(window.parent.PayRollDetail.layerIndex);
}


/**
 * 项目
 * @type {Mixed|jQuery|HTMLElement}
 */
var workerType = $("#workerType");
/*var workerTypeSuggest = workerType.bsSuggest({
    param1: "projectCode",        //item点击事件的需要的数据1
    keyField: "projectName",  //  input 显示的数据
    showContent: "projectName", //下拉显示的数据
    allowNoKeyword: false,
    multiWord: true,
    separator: ",",
    getDataMethod: "url",
    url: Feng.ctxPath + "/cooperationProjectMaster/getList?projectName=" + project.val(),
    processData: function (json) {
        var i, len, data = {value: []};
        if (!json || json.length === 0) {
            return false
        }
        len = json.length;
        jsonStr = "{'value':[";
        for (i = 0; i < len; i++) {
            data.value.push(json[i])
        }
        data.defaults = "project";
        return data
    }
}).on('onSetSelectValue', function (e, data) {

})*/

/**
 * 工时表格加载
 * @param data
 */
PayRollDetailInfoDlg.tableByTimeOnloadSuccess = function (data) {
    DetailByTime.sum();
}

/**
 * 计工单表格加载
 * @param data
 */
PayRollDetailInfoDlg.tableByAccountOnloadSuccess = function (data) {
    DetailByAccount.sum();
}

/**
 * 搜索
 */
PayRollDetailInfoDlg.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['workerType'] = $("#workerType").val();
    if(type === '1') {
        DetailByTime.table.refresh({query: queryData});
    }else if(type === '2'){
        DetailByAccount.table.refresh({query: queryData});
    }

}

/**
 * 双击查看
 */
DetailByTime.searchInfo = function (e) {
    var url = Feng.ctxPath + '/workerMaster/workerMaster_view/' + e.id;
    if (e.pwId) {
        url += "?pwId=" + e.pwId;
    }
    var index = layer.open({
        type: 2,
        title: '工人详情信息',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: url
    });
    this.layerIndex = index;
}


$(function () {
    Feng.initChosen();
    var payRollCode = $("#payRollCode").val();
    if(type === "1"){
        //按工时发放
        var defaulTimeColunms = DetailByTime.initColumn();
        var timeTable = new BSTable(DetailByTime.id, "/payRoll/selectDetailByPayRollCodeNoPage?payRollCode=" + payRollCode, defaulTimeColunms);
        timeTable.pagination = false;
        timeTable.showFooter = true;
        timeTable.setPaginationType("client");
        timeTable.onLoadSuccess = PayRollDetailInfoDlg.tableByTimeOnloadSuccess;
        timeTable.onDblClickRow = DetailByTime.searchInfo;
        DetailByTime.table = timeTable.init();
    }else  if(type === "2"){
        //按计工单发放
        var defaultAccountColunms = DetailByAccount.initColumn();
        var accountTable = new BSTable(DetailByAccount.id, "/payRoll/selectDetailByPayRollCodeNoPage?payRollCode=" + payRollCode, defaultAccountColunms);
        accountTable.pagination = false;
        accountTable.showFooter = true;
        accountTable.setPaginationType("client");        accountTable.onLoadSuccess = PayRollDetailInfoDlg.tableByAccountOnloadSuccess;
        DetailByAccount.table = accountTable.init();
    }
});


/**
 * 单价合计
 */
DetailByAccount.sum = function () {
    var amount = $('#' + DetailByAccount.id).find("tr").find(".amount");
    var sum = 0;
    $(amount).each(function () {
        sum += parseFloat($(this).text() == '' ? 0 : $(this).text());
    });
    $(".fixed-table-footer").find(".amount").text(sum.toFixed(2));
    /***********************/
    var payAmount = $('#' + DetailByAccount.id).find("tr").find("td").eq(8);
    var sum = 0;
    $('#' + DetailByAccount.id).find("tr").each(function () {
        sum += Number($(this).find("td").eq(8).text());
    })
    $(".fixed-table-footer").find(".payAmount").text(sum.toFixed(2));
    /***********************/
    var actualAmount = $('#' + DetailByAccount.id).find("tr").find(".actualAmount");
    var sum = 0;
    $(actualAmount).each(function () {
        sum += parseFloat($(this).text() == '' ? 0 : $(this).text());
    });
    $(".fixed-table-footer").find(".actualAmount").text(sum.toFixed(2));
    /***********************/
    var sum = 0;
    $('#' + DetailByAccount.id).find("tr").each(function () {
        sum += Number($(this).find("td").eq(10).text());
    })
    $(".fixed-table-footer").find(".balanceAmount").text(sum.toFixed(2));
}



/**
 * 单价合计
 */
DetailByTime.sum = function () {
    var amount = $('#' + DetailByTime.id).find("tr").find(".amount");
    var sum = 0;
    $(amount).each(function () {
        sum += parseFloat($(this).text() == '' ? 0 : $(this).text());
    });
    $(".fixed-table-footer").find(".amount").text(sum.toFixed(2));
    /***********************/
    var sum = 0;
    $('#' + DetailByTime.id).find("tr").each(function () {
        sum += Number($(this).find("td").eq(10).text());
    })
    $(".fixed-table-footer").find(".payAmount").text(sum.toFixed(2));
    /***********************/
    var actualAmount = $('#' + DetailByTime.id).find("tr").find(".actualAmount");
    var sum = 0;
    $(actualAmount).each(function () {
        sum += parseFloat($(this).text() == '' ? 0 : $(this).text());
    });
    $(".fixed-table-footer").find(".actualAmount").text(sum.toFixed(2));
    /***********************/
    var sum = 0;
    $('#' + DetailByTime.id).find("tr").each(function () {
        sum += Number($(this).find("td").eq(12).text());
    })
    $(".fixed-table-footer").find(".balanceAmount").text(sum.toFixed(2));
}