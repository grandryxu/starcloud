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
 * 按工种生成的工资单
 */
var DetailByWorkerType = {
    id: "DetailByTime",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    projectCode:"",
    teamWorkers:[],
};

/**
 * 初始化表格的列
 */
DetailByAccount.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle',
            footerFormatter: function () {
                return "合计";
            }},
        {title: '证件类型', field: 'idCardName', visible: true, align: 'center', valign: 'middle'},
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
                return "<a class='amount' data-index='"+ index +"' data-id='"+ row.id +"'>" + value + "</a>";
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
                return "<a class='rewardAmount' data-index='"+ index +"' data-id='"+ row.id +"'>" + value + "</a>";
            }
        },
        {
            title: '惩罚(元)', field: 'punishAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='punishAmount' data-index='"+ index +"' data-id='"+ row.id +"'>" + value + "</a>";
            }
        },
        {title: '应发工资(元)', field: 'payAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return Number(value);
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
                return "<a class='actualAmount' data-index='"+ index +"' data-id='"+ row.id +"'>" + value + "</a>";
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
                return Number(value);
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
        {field: 'selectItem', checkbox: true},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle',
            footerFormatter: function () {
                return "合计";
            }},
        {title: '证件类型', field: 'idCardName', visible: true, align: 'center', valign: 'middle'},
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
                return "<a class='amount' data-index='"+ index +"'data-id='"+ row.id +"'> " + Number(value) + "</a>";
            },
            footerFormatter: function (value) {
                return "<span class='amount'></span>";
            }
        },
        {title: '奖励(元)', field: 'rewardAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='rewardAmount' data-index='"+ index +"' data-id='"+ row.id +"'>" + value + "</a>";
            }},
        {title: '惩罚(元)', field: 'punishAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='punishAmount' data-index='"+ index +"' data-id='"+ row.id +"'>" + value + "</a>";
            }},
        {title: '应发工资(元)', field: 'payAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return  Number(value);
            },
            footerFormatter: function (value) {
                return "<span class='payAmount'></span>";
            }},
        {title: '实发工资(元)', field: 'actualAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='actualAmount' data-index='"+ index +"' data-id='"+ row.id +"'>" + value + "</a>";
            },
            footerFormatter: function (value) {
                return "<span class='actualAmount'></span>";
            }},
        {title: '剩余工资(元)', field: 'balanceAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return "<span class='balanceAmount'>"+ Number(value)+"</span>";
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
    parent.layer.closeAll();
    window.parent.PayRoll.table.refresh();
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
var reg = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/;

PayRollDetailInfoDlg.tableByTimeOnloadSuccess = function (data) {
    DetailByWorkerType.payRollDetailList = data;
    //数据初始化
    for (var i = 0; i < DetailByWorkerType.payRollDetailList.length; i++) {
        DetailByWorkerType.payRollDetailList[i].amount = Number(DetailByWorkerType.payRollDetailList[i].days) * Number(DetailByWorkerType.payRollDetailList[i].price);
        DetailByWorkerType.payRollDetailList[i].rewardAmount = 0;
        DetailByWorkerType.payRollDetailList[i].punishAmount = 0;
        DetailByWorkerType.payRollDetailList[i].actualAmount = 0;
        DetailByWorkerType.payRollDetailList[i].payAmount = DetailByWorkerType.payRollDetailList[i].amount;
        DetailByWorkerType.payRollDetailList[i].balanceAmount = DetailByWorkerType.payRollDetailList[i].amount;
    }
    DetailByWorkerType.initEditable();
    DetailByWorkerType.sum();
}

DetailByWorkerType.initEditable = function () {
    $("#DetailByTime td a").editable("destroy");
    $("#DetailByTime td a").editable({
        validate: function (params) {
            if (!$.trim(params)) {
                return '不能为空';
            }
            if(!reg.test(params)){
                return '只能输入数值型金额';
            }
            //当前修改的class
            var tr = $(this).parent().parent();

            //基本工资
            var amount = Number($($(tr.children("td")[7]).children("a")[0]).html());
            //奖励
            var rewardAmount = Number($($(tr.children("td")[8]).children("a")[0]).html());
            //惩罚
            var punishAmount = Number($($(tr.children("td")[9]).children("a")[0]).html());
            //实发工资
            var actualAmount = Number($($(tr.children("td")[11]).children("a")[0]).html());

            //判断当前修改的是哪个字段
            var index = $(this).data("index");
            var classList = $(this).context.classList;
            if (classList.contains('amount')) {
                amount = Number(params);
            } else if (classList.contains('rewardAmount')) {
                rewardAmount = Number(params);
            } else if (classList.contains('punishAmount')) {
                punishAmount = Number(params);
            } else if (classList.contains('actualAmount')) {
                actualAmount = Number(params);
            }

            //计算应发工资
            var payAmount = amount + rewardAmount - punishAmount;
            //计算剩余工资
            var balanceAmount = payAmount - actualAmount;

            //应发工资 赋值
            $((tr.children("td")[10])).html(payAmount);
            //剩余工资 赋值
            $((tr.children("td")[12])).html(balanceAmount);

            //更改对应对象的集合
            var updateObject = {
                id: $(this).data("id") ,
                amount : amount ,
                rewardAmount: rewardAmount ,
                punishAmount: punishAmount,
                actualAmount: actualAmount,
                payAmount: payAmount,
                balanceAmount: balanceAmount
            }
            //更新字段
            var ajax = new $ax(Feng.ctxPath + "/payRollDetail/update", function (data) {
                DetailByTime.table.refresh();
                Feng.success("新增成功!");
            }, function (data) {
                Feng.error("新增失败" + data.responseJSON.message + "!");
            });
            ajax.set(updateObject);
            ajax.start();
        },
        type: 'text'
    });
}

/**
 * 计工单表格加载
 * @param data
 */
PayRollDetailInfoDlg.tableByAccountOnloadSuccess = function (data) {
    DetailByAccount.accountDetailList = data;


    //数据初始化
    for(var i=0;i<DetailByAccount.accountDetailList.length;i++){
        DetailByAccount.accountDetailList[i].actualAmount = 0;
        DetailByAccount.accountDetailList[i].balanceAmount = DetailByAccount.accountDetailList[i].payAmount;
    }
    DetailByAccount.initEditable();
    DetailByAccount.sum();
}

DetailByAccount.initEditable = function () {
    $("#DetailByAccount td a").editable("destroy");
    $("#DetailByAccount td a").editable({
        validate: function (params) {
            if (!$.trim(params)) {
                return '不能为空';
            }
            if(!reg.test(params)){
                return '只能输入数值型金额';
            }
            //当前修改的class
            var tr = $(this).parent().parent();

            //基本工资
            var amount = Number($($(tr.children("td")[5]).children("a")[0]).html());
            //奖励
            var rewardAmount = Number($($(tr.children("td")[6]).children("a")[0]).html());
            //惩罚
            var punishAmount = Number($($(tr.children("td")[7]).children("a")[0]).html());
            //实发工资
            var actualAmount = Number($($(tr.children("td")[9]).children("a")[0]).html());

            //判断当前修改的是哪个字段
            var index = $(this).data("index");
            var classList = $(this).context.classList;
            if (classList.contains('amount')) {
                amount = Number(params);
            } else if (classList.contains('rewardAmount')) {
                rewardAmount = Number(params);
            } else if (classList.contains('punishAmount')) {
                punishAmount = Number(params);
            } else if (classList.contains('actualAmount')) {
                actualAmount = Number(params);
            }

            //计算应发工资
            var payAmount = amount + rewardAmount - punishAmount;
            //计算剩余工资
            var balanceAmount = payAmount - actualAmount;

            //应发工资 赋值
            $((tr.children("td")[8])).html(payAmount);
            //剩余工资 赋值
            $((tr.children("td")[10])).html(balanceAmount);

            //更改对应对象的集合
            var updateObject = {
                id: $(this).data("id") ,
                amount : amount ,
                rewardAmount: rewardAmount ,
                punishAmount: punishAmount,
                actualAmount: actualAmount,
                payAmount: payAmount,
                balanceAmount: balanceAmount
            }
            //更新字段
            var ajax = new $ax(Feng.ctxPath + "/payRollDetail/update", function (data) {
                DetailByAccount.table.refresh();
                Feng.success("修改成功!");
            }, function (data) {
                Feng.error("修改失败" + data.responseJSON.message + "!");
            });
            ajax.set(updateObject);
            ajax.start();
        },
        type: 'text'
    });
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
 * 检查是否选中
 */
DetailByAccount.check = function () {
    var selected = $('#' + DetailByAccount.id).bootstrapTable('getSelections');
    if(selected.length === 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    return true;
};

/**
 * 检查是否选中
 */
DetailByWorkerType.check = function () {
    var selected = $('#' + DetailByWorkerType.id).bootstrapTable('getSelections');
    if(selected.length === 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    return true;
};



/**
 * 提交
 */
PayRollDetailInfoDlg.submit = function () {
    var ajax = new $ax(Feng.ctxPath + "/payRoll/submit", function (data) {
        Feng.success("提交成功!");
        PayRollDetailInfoDlg.close();
        window.parent.PayRoll.table.refresh();
    }, function (data) {
        Feng.error("提交失败!" + data.responseJSON.message + "!");
    });
    ajax.set("ids", $("#id").val());
    ajax.start();
}

$(function () {
    var payRollCode = $("#payRollCode").val();
    if(type === "1"){
        //按工时发放
        $("#DetailByTimeToolbar").show();
        $("#DetailByAccountToolbar").hide();
        var defaulTimeColunms = DetailByTime.initColumn();
        var timeTable = new BSTable(DetailByTime.id, "/payRoll/selectDetailByPayRollCodeNoPage?payRollCode=" + payRollCode, defaulTimeColunms);
        timeTable.pagination = false;
        timeTable.showFooter = true;
        timeTable.setPaginationType("client");        timeTable.onLoadSuccess = PayRollDetailInfoDlg.tableByTimeOnloadSuccess;
        DetailByTime.table = timeTable.init();
    }else  if(type === "2"){
        //按计工单发放
        $("#DetailByAccountToolbar").show();
        $("#DetailByTimeToolbar").hide();
        var defaultAccountColunms = DetailByAccount.initColumn();
        var accountTable = new BSTable(DetailByAccount.id, "/payRoll/selectDetailByPayRollCodeNoPage?payRollCode=" + payRollCode, defaultAccountColunms);
        accountTable.pagination = false;
        accountTable.showFooter = true;
        accountTable.setPaginationType("client");        accountTable.onLoadSuccess = PayRollDetailInfoDlg.tableByAccountOnloadSuccess;
        DetailByAccount.table = accountTable.init();
    }
});



/****************************************************DetailByAccount计算相关*****************************************************/


/**
 * 批量修改工资
 */
DetailByAccount.batchAmount = function () {
    if(!DetailByAccount.check()){return;}
    layer.prompt({
        title: '输入工资',
        formType: 0
    }, function (text, index) {
        if (!reg.test(text)) {
            Feng.info("只能输入数值型金额");
            return;
        }
        layer.close(index);
        DetailByAccount.batchUpdate('amount', text);

    });

}

/**
 * 批量奖励
 */
DetailByAccount.batchRewardAmount = function () {
    if(!DetailByAccount.check()){return;}
    layer.prompt({
        title: '输入奖励(元)',
        formType: 0
    }, function (text, index) {
        if (!reg.test(text)) {
            Feng.info("只能输入数值型金额");
            return;
        }
        layer.close(index);
        DetailByAccount.batchUpdate('rewardAmount', text);
    });

}

/**
 * 批量惩罚
 */
DetailByAccount.batchPunishAmount = function () {
    if(!DetailByAccount.check()){return;}
    layer.prompt({
        title: '输入惩罚(元)',
        formType: 0
    }, function (text, index) {
        if (!reg.test(text)) {
            Feng.info("只能输入数值型金额");
            return;
        }
        layer.close(index);
        DetailByAccount.batchUpdate('punishAmount', text);
    });

}


/**
 * 批量实发工资
 */
DetailByAccount.batchActualAmount = function () {
    if(!DetailByAccount.check()){return;}
    layer.prompt({
        title: '输入实发(元)',
        formType: 0
    }, function (text, index) {
        if (!reg.test(text)) {
            Feng.info("只能输入数值型金额");
            return;
        }
        layer.close(index);
        DetailByAccount.batchUpdate('actualAmount', text);
    });

}


/**
 * 批量更新数据
 */
DetailByAccount.batchUpdate = function (typeClass, text) {
    var selected = $('#' + DetailByAccount.id).find("input[type='checkbox']:checked");
    $(selected).each(function () {
        var cell = $(this).parents('tr').find('.' + typeClass);
        if (cell.html() && cell.html() != '') {
            cell.text(text);
            DetailByAccount.calculateWages(cell);
        }
    });
    DetailByAccount.initEditable();
    //修改完页面 批量更新工资单
    var ajax = new $ax(Feng.ctxPath + "/payRollDetail/updates", function (data) {
        Feng.success("修改成功!");
    }, function (data) {
        Feng.error("修改失败" + data.responseJSON.message + "!");
    });
    ajax.set({"payRollDetailList":DetailByAccount.accountDetailList});
    ajax.start();
}

/**
 * 计算工资
 */
DetailByAccount.calculateWages = function (data) {
    var index = $(data).data("index");
    //
    // 基本工资=单价*工时
    // 实际应发=基本工资+奖励金额-扣款金额
    //
    var baseWages = $(data).parents('tr').find('.amount').text(); //基本工资
    var rewardAmount = $(data).parents('tr').find('.rewardAmount').text();
    var punishAmount = $(data).parents('tr').find('.punishAmount').text();
    var reallyWages = parseFloat(baseWages) + parseFloat(rewardAmount) - parseFloat(punishAmount);
    var actualAmount = parseFloat($(data).parents('tr').find('.actualAmount').text());
    $(data).parents('tr').find('.payAmount').text(reallyWages); //应发工资
    DetailByAccount.accountDetailList[index].amount = baseWages;
    DetailByAccount.accountDetailList[index].rewardAmount = rewardAmount;
    DetailByAccount.accountDetailList[index].punishAmount = punishAmount;
    DetailByAccount.accountDetailList[index].payAmount = reallyWages;
    DetailByAccount.accountDetailList[index].actualAmount = actualAmount;
    DetailByAccount.accountDetailList[index].balanceAmount =  reallyWages - actualAmount;
    $(data).parents('tr').find('td').eq(8).text(reallyWages); //应发工资
    $(data).parents('tr').find('td').eq(10).text(reallyWages - actualAmount); //实发

    DetailByAccount.sum();
}

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

/****************************************************DetailByAccount计算相关*****************************************************/



/****************************************************DetailByWorkerType计算相关*****************************************************/


/**
 * 批量修改工资
 */
DetailByWorkerType.batchAmount = function () {
    if(!DetailByWorkerType.check()){return;}
    layer.prompt({
        title: '输入工资',
        formType: 0
    }, function (text, index) {
        if (!reg.test(text)) {
            Feng.info("只能输入数值型金额");
            return;
        }
        layer.close(index);
        DetailByWorkerType.batchUpdate('amount', text);

    });


}

/**
 * 批量奖励
 */
DetailByWorkerType.batchRewardAmount = function () {
    if(!DetailByWorkerType.check()){return;}
    layer.prompt({
        title: '输入奖励(元)',
        formType: 0
    }, function (text, index) {
        if (!reg.test(text)) {
            Feng.info("只能输入数值型金额");
            return;
        }
        layer.close(index);
        DetailByWorkerType.batchUpdate('rewardAmount', text);
    });

}

/**
 * 批量惩罚
 */
DetailByWorkerType.batchPunishAmount = function () {
    if(!DetailByWorkerType.check()){return;}
    layer.prompt({
        title: '输入惩罚(元)',
        formType: 0
    }, function (text, index) {
        if (!reg.test(text)) {
            Feng.info("只能输入数值型金额");
            return;
        }
        layer.close(index);
        DetailByWorkerType.batchUpdate('punishAmount', text);
    });

}


/**
 * 批量实发工资
 */
DetailByWorkerType.batchActualAmount = function () {
    if(!DetailByWorkerType.check()){return;}
    layer.prompt({
        title: '输入实发(元)',
        formType: 0
    }, function (text, index) {
        if (!reg.test(text)) {
            Feng.info("只能输入数值型金额");
            return;
        }
        layer.close(index);
        DetailByWorkerType.batchUpdate('actualAmount', text);
    });

}


/**
 * 批量更新数据
 */
DetailByWorkerType.batchUpdate = function (typeClass, text) {
    var selected = $('#' + DetailByWorkerType.id).find("input[type='checkbox']:checked");
    $(selected).each(function () {
        var cell = $(this).parents('tr').find('.' + typeClass);
        if (cell.html() && cell.html() != '') {
            cell.text(text);
            DetailByWorkerType.calculateWages(cell);
        }
    });
    DetailByWorkerType.initEditable();
    //修改完页面 批量更新工资单
    var ajax = new $ax(Feng.ctxPath + "/payRollDetail/updates", function (data) {
        Feng.success("修改成功!");
    }, function (data) {
        Feng.error("修改失败" + data.responseJSON.message + "!");
    });
    ajax.set({"payRollDetailList":DetailByWorkerType.payRollDetailList});
    ajax.start();

}

/**
 * 计算工资
 */
DetailByWorkerType.calculateWages = function (data) {

    var index = $(data).data("index");
    //
    // 基本工资=单价*工时
    // 实际应发=基本工资+奖励金额-扣款金额
    //
    var baseWages = $(data).parents('tr').find('.amount').text(); //基本工资
    var rewardAmount = $(data).parents('tr').find('.rewardAmount').text();
    var punishAmount = $(data).parents('tr').find('.punishAmount').text();
    var reallyWages = parseInt(baseWages) + parseInt(rewardAmount) - parseInt(punishAmount);
    $(data).parents('tr').find('.payAmount').text(reallyWages); //实际工资
    var actualAmout = Number($(data).parents('tr').find('.actualAmount').text());
    DetailByWorkerType.payRollDetailList[index].amount = baseWages;
    DetailByWorkerType.payRollDetailList[index].rewardAmount = rewardAmount;
    DetailByWorkerType.payRollDetailList[index].punishAmount = punishAmount;
    DetailByWorkerType.payRollDetailList[index].payAmount = reallyWages;
    DetailByWorkerType.payRollDetailList[index].balanceAmount =  reallyWages - actualAmout;
    DetailByWorkerType.payRollDetailList[index].actualAmount = actualAmout;
    $(data).parents('tr').find('td').eq(10).text(reallyWages); //应发工资
    $(data).parents('tr').find('td').eq(12).text(reallyWages - actualAmout); //实发工资
    $("td a").editable();
    DetailByWorkerType.sum();
}

/**
 * 单价合计
 */
DetailByWorkerType.sum = function () {

    var amount = $('#' + DetailByWorkerType.id).find("tr").find(".amount");
    var sum = 0;
    $(amount).each(function () {
        sum += parseInt($(this).text() == '' ? 0 : $(this).text());
    });
    $(".fixed-table-footer").find(".amount").text(sum);
    /***********************/
    var sum = 0;
    $('#' + DetailByWorkerType.id).find("tr").each(function () {
        sum += Number($(this).find("td").eq(10).text());
    })
    $(".fixed-table-footer").find(".payAmount").text(sum);
    /***********************/
    var actualAmount = $('#' + DetailByWorkerType.id).find("tr").find(".actualAmount");
    var sum = 0;
    $(actualAmount).each(function () {
        sum += parseInt($(this).text() == '' ? 0 : $(this).text());
    });
    $(".fixed-table-footer").find(".actualAmount").text(sum);
    /***********************/
    var sum = 0;
    $('#' + DetailByWorkerType.id).find("tr").each(function () {
        sum += Number($(this).find("td").eq(12).text());
    })
    $(".fixed-table-footer").find(".balanceAmount").text(sum);
}

/****************************************************DetailByWorkerType计算相关*****************************************************/