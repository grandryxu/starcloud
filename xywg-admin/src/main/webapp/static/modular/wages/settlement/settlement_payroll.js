/**
 * 结算单工资列表
 */
var SettlementPayroll = {
    id: "settlementPayrollTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    tableData: {},
    callBackData: {},
    workerIdListVal: [],
    projectCodeValue: null,
    detailList: null,
    workerNames: ''
};

/**
 * 重新选择项目的时候,清空已选择的工人
 */
SettlementPayroll.updateSelectedWorker = function () {
    SettlementPayroll.workerIdListVal = [];
    SettlementPayroll.detailList = null;
    SettlementPayroll.workerNames = '';

};

/**
 * 接收来自子页面的数据
 * @param rel
 */
function setRel(rel) {
    var workerIdListVal = [];
    for (var i = 0; i < rel.detailList.length; i++) {
        workerIdListVal.push(rel.detailList[i].id)
    }
    SettlementPayroll.workerIdListVal = workerIdListVal;
    SettlementPayroll.detailList = rel.detailList;
    SettlementPayroll.workerNames = rel.workerNames;
}


//给子页面返回detailList
function getCheckedArray() {
    var data = {};
    data.detailList = SettlementPayroll.detailList;
    data.workerNames = SettlementPayroll.workerNames;
    return data;
}

/**
 * 弹出添加页面
 */
SettlementPayroll.openAddWorkListHtml = function () {
    var index = layer.open({
        type: 2,
        title: '选择工人',
        area: ['80%', '80%'],
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/settlement/settlement_workerList?projectCode=' + $("#projectCode").val(),
        end: function (data) {
            SettlementPayroll.search();
        }
    });
    this.layerIndex = index;
};


/**
 * 查询结算单列表
 */
SettlementPayroll.search = function () {
    SettlementPayroll.projectCodeValue = $("#projectCode").val();
    var queryData = {};
    queryData['projectCode'] = $("#projectCode").val();
    queryData['workerIdList'] = JSON.stringify(SettlementPayroll.workerIdListVal);
    SettlementPayroll.table.refresh({query: queryData});
};


/**
 * 结算单添加页面金额初始化表格的列
 */
SettlementPayroll.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {
            title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle',
            footerFormatter: function () {
                return "合计";
            }
        },
        {title: '证件类型', field: 'idCardTypeVal', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型', field: 'idCardType', visible: false, align: 'center', valign: 'middle'},
        {
            title: '证件编号',
            field: 'idCardNumber',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (data) {
                return Feng.hiddenIdCard(data);
            }
        },
        {title: '工种', field: 'workerTypeName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '累计应发(元)', field: 'addPayAmount', visible: true, align: 'center', valign: 'middle',
            footerFormatter: function (value) {
                var count = 0;
                for (var i = 0; i < value.length; i++) {
                    count += parseFloat(value[i].addPayAmount);
                }
                return count.toFixed(2);
            }
        },
        {
            title: '累计已发(元)', field: 'addActualAmount', visible: true, align: 'center', valign: 'middle',
            footerFormatter: function (value) {
                var count = 0;
                for (var i = 0; i < value.length; i++) {
                    count += parseFloat(value[i].addActualAmount);
                }

                return count.toFixed(2);
            }
        },
        {
            title: '累计剩余(元)', field: 'addBalanceAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value === undefined) {
                    value = "0";
                }
                return "<span class='addBalanceAmount' data-index='" + index + "'>" + value + "</span>";
            },
            footerFormatter: function (value) {
                var count = 0;
                for (var i = 0; i < value.length; i++) {
                    count += parseFloat(value[i].addBalanceAmount);
                }
                return count.toFixed(2);
            }
        },

        {
            title: '奖励(元)', field: 'rewardAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value === undefined) {
                    value = "0";
                }
                return "<a class='rewardAmount' data-index='" + index + "'>" + value + "</a>";
            },
            footerFormatter: function (value) {
                var count = 0;
                for (var i = 0; i < value.length; i++) {
                    count += parseFloat(value[i].rewardAmount);
                }
                return "<span class='rewardAmount'>" + count.toFixed(2) + "</span>";
            }
        },
        {
            title: '惩罚(元)', field: 'punishAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value === undefined) {
                    value = "0";
                }
                return "<a class='punishAmount' data-index='" + index + "'>" + value + "</a>";
            },
            footerFormatter: function (value) {
                var count = 0;
                for (var i = 0; i < value.length; i++) {
                    count += parseFloat(value[i].punishAmount);
                }
                return "<span class='punishAmount' >" + count.toFixed(2) + "</span>";
            }
        },
        {
            title: '结算应发(元)', field: 'settlePayAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value === undefined) {
                    value = "0";
                }
                return "<span class='settlePayAmount'>" + value + "</span>";
            },
            footerFormatter: function (value) {
                var count = 0;
                for (var i = 0; i < value.length; i++) {
                    count += parseFloat(value[i].settlePayAmount);
                }
                return "<span class='settlePayAmount' >" + count.toFixed(2) + "</span>";
            }
        },
        {
            title: '结算实发(元)', field: 'settleActualAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value === undefined) {
                    value = "0";
                }
                return "<a class='settleActualAmount' data-index='" + index + "'>" + value + "</a>";
            },
            footerFormatter: function (value) {
                var count = 0;
                for (var i = 0; i < value.length; i++) {
                    count += parseFloat(value[i].settleActualAmount);
                }
                return "<span class='settleActualAmount' >" + count.toFixed(2) + "</span>";
            }
        }
    ];
};

/**
 * 表格加载成功
 * @param data
 */
SettlementPayroll.tableOnloadSuccess = function (data) {
    SettlementPayroll.tableData = data;
    for(var i=0; i<data.length; i++){
        data[i].settleBalanceAmount = (parseFloat(data[i].settlePayAmount) - parseFloat(data[i].settleActualAmount)).toFixed(2);
    }
    editableRow();
};


/**
 * 检查是否选中
 */
SettlementPayroll.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的记录！");
        return false;
        /*} else if(selected.length > 1){
            Feng.info("只能选择表格中的某一记录！");
            return false;*/
    }
    SettlementPayroll.seItem = selected;
    return true;
};

/**
 * 从列表中移除选择的工人
 */
SettlementPayroll.removeWorker = function () {
    if (!SettlementPayroll.check()) {
        return;
    }
    console.info(123);
    for (var i = 0; i < SettlementPayroll.seItem.length; i++) {
        var workerIdStr = SettlementPayroll.seItem[i].workerId;
        var object = SettlementPayroll.seItem[i];
        //移除元素
        SettlementPayroll.workerIdListVal.splice($.inArray(workerIdStr, SettlementPayroll.workerIdListVal), 1);
        //移除子页面数据
        for (var j = SettlementPayroll.detailList.length - 1; j >= 0 && j < SettlementPayroll.detailList.length; j--) {
            if (("" + object.idCardType) === SettlementPayroll.detailList[j].idCardType && object.idCardNumber === SettlementPayroll.detailList[j].idCardNumber) {
                //从名称中删除此人
                SettlementPayroll.workerNames = SettlementPayroll.workerNames.replace(SettlementPayroll.detailList[j].workerName + ";", "")
                //从子页面list中删除此人
                SettlementPayroll.detailList.splice(j, 1);
            }
        }
    }
    //重新加载列表
    SettlementPayroll.search();
}


/**
 * 批量写数据
 * @param testClass
 */
SettlementPayroll.batchWrite = function (testClass) {
    if (!SettlementPayroll.check()) {
        return;
    }
    layer.prompt({title: '请输入金额', formType: "", maxlength: 20}, function (text, index) {
        $(".layui-layer-content").children("#promptMsg").remove();
        if (checkMoney(text)) {
            layer.close(index);
            SettlementPayroll.batchUpdate(testClass, text);
        } else {

            //$(this).append("<span id='promptMsg'>只能输入数值型金额</span>");
            $(".layui-layer-content").append("<span id='promptMsg' style='color:red'>只能输入数值型金额</span>");
        }
    });
};


/**
 * 批量更新数据
 */
SettlementPayroll.batchUpdate = function (typeClass, text) {
    var selected = $('#' + SettlementPayroll.id).find("input[type='checkbox']:checked");
    $(selected).each(function () {
        var cell = $(this).parents('tr').find('.' + typeClass);
        if (cell.html() && cell.html() != '') {
            cell.text(text);
            calculateWages(cell);
        }
    });
    editableRow();
};

/**
 * 计算工资
 */
var calculateWages = function (data) {
    var index = $(data).data("index");
    //累计剩余工资(工资单明细计算获取)
    var addBalanceAmount = $(data).parents('tr').find('.addBalanceAmount').text();
    ////惩罚金额
    var punishAmount = $(data).parents('tr').find('.punishAmount').text();
    //奖励金额
    var rewardAmount = $(data).parents('tr').find('.rewardAmount').text();
    //结算应发
    var settlePayAmount = parseFloat(addBalanceAmount) + parseFloat(rewardAmount) - parseFloat(punishAmount);
    //设置结算实发的值
    $(data).parents('tr').find('.settlePayAmount').text(settlePayAmount);
    //结算实发
    var settleActualAmount = $(data).parents('tr').find('.settleActualAmount').text();

    //惩罚金额
    SettlementPayroll.tableData[index].punishAmount = parseFloat(punishAmount).toFixed(2);
    //奖励金额
    SettlementPayroll.tableData[index].rewardAmount = parseFloat(rewardAmount).toFixed(2);
    //结算应发
    SettlementPayroll.tableData[index].settlePayAmount = parseFloat(settlePayAmount).toFixed(2);
    //结算实发
    SettlementPayroll.tableData[index].settleActualAmount = parseFloat(settleActualAmount).toFixed(2);
    //结算剩余
    SettlementPayroll.tableData[index].settleBalanceAmount = (parseFloat(settlePayAmount) - parseFloat(settleActualAmount)).toFixed(2);
    sum();
};


/**
 * 单价合计
 */
var sum = function () {
    var baseWages = $('#' + SettlementPayroll.id).find("tr").find(".rewardAmount");
    var sum = 0;
    $(baseWages).each(function () {
        sum += parseFloat($(this).text() == '' ? 0 : $(this).text());
    });
    //结算总奖励金额
    $(".fixed-table-footer").find(".rewardAmount").text(sum.toFixed(2));

    /***********************/
    var reallyWages = $('#' + SettlementPayroll.id).find("tr").find(".punishAmount");
    var sum2 = 0;
    $(reallyWages).each(function () {
        sum2 += parseFloat($(this).text() == '' ? 0 : $(this).text());
    });
    //结算总惩罚金额
    $(".fixed-table-footer").find(".punishAmount").text(sum2.toFixed(2));

    /***********************/
    var reallyWages = $('#' + SettlementPayroll.id).find("tr").find(".settlePayAmount");
    var sum3 = 0;
    $(reallyWages).each(function () {
        sum3 += parseFloat($(this).text() == '' ? 0 : $(this).text());
    });
    //结算应发总金额
    $(".fixed-table-footer").find(".settlePayAmount").text(sum3.toFixed(2));

    var reallyWages = $('#' + SettlementPayroll.id).find("tr").find(".settleActualAmount");
    var sum4 = 0;
    $(reallyWages).each(function () {
        sum4 += parseFloat($(this).text() == '' ? 0 : $(this).text());
    });
    //结算实发总金额
    $(".fixed-table-footer").find(".settleActualAmount").text(sum4.toFixed(2));

};

/**
 * 更新结算单
 */
/*var updateAccountInfo = function (index) {
    console.info(this.accountDetailList[index]);
}*/


/**
 * 添加页面的保存结算单
 */
SettlementPayroll.saveSettlement = function () {
    if (JSON.stringify(SettlementPayroll.tableData) === "{}" || JSON.stringify(SettlementPayroll.tableData) === "[]") {
        layer.msg("空数据无须保存!");
        return;
    }
    layer.msg('确定提交该表数据？', {
        time: 0 //不自动关闭
        , btn: ['确认', '取消']
        , yes: function (index) {
            layer.close(index);
            var ajax = new $ax(Feng.ctxPath + "/settlement/saveSettlement", function (data) {
                Feng.success("操作成功!");
                //SettlementPayroll.table.refresh();
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            });
            ajax.set("SettlementDetailList", JSON.stringify(SettlementPayroll.tableData));
            ajax.set("settlement", JSON.stringify(SettlementPayroll.getSumCell()));
            ajax.start();
        }
    });

};


$(function () {
    var defaultColunms = SettlementPayroll.initColumn();
    var table = new BSTable(SettlementPayroll.id, "/settlement/getWorkerMoneyTable", defaultColunms);
    table.setQueryParams();
    table.onLoadSuccess = SettlementPayroll.tableOnloadSuccess;
    table.pagination = false;
    table.showFooter = true;
    table.setPaginationType("client");
    SettlementPayroll.table = table.init();
    Feng.initChosen();
});


SettlementPayroll.getSumCell = function () {
    //结算应发
    var settlePayAmount = $('.fixed-table-footer').find('.settlePayAmount').text();
    //结算实发
    var settleActualAmount = $('.fixed-table-footer').find('.settleActualAmount').text();

    return {
        //总计：
        //剩余金额应该是剩余金额总额=之前的剩余金额-实际结算金额
        payedMoney: (parseFloat(settlePayAmount) - parseFloat(settleActualAmount)).toFixed(2),
        totalAmount: parseFloat(settlePayAmount).toFixed(2),
        actualAmount: parseFloat(settleActualAmount).toFixed(2),
        projectCode: $("#projectCode").val(),
        payStatus: 1
    }
};

function editableRow() {
    var reg = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/;
    $('td a').editable({
        type: "text",
        title: "请输入金额",
        disabled: false,
        emptytext: "0",
        mode: "popup",
        validate: function (value) {
            if (!$.trim(value)) {
                return '不能为空';
            }
            if (!reg.test(value)) {
                return '只能输入数值型金额';
            }
            $(this).text(value);
            calculateWages(this);
        }
    });
}

/**
 * 校验金额
 * @param text
 * @returns {boolean}
 */
function checkMoney(text) {
    var reg = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/;
    if (reg.test(text)) {
        return true
    } else {
        return false
    }
}
