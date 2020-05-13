/**
 * 初始化工资详情详情对话框
 */
var PayRollDetailInfoDlg = {
    payRollDetailInfoData: {}
};
/**
 * 按计工单生成的工资单
 */
var DetailByAccount = {
    id: "DetailByAccount",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    accountDetailList: null
};
/**
 * 初始化表格的列
 */
DetailByAccount.initColumn = function () {
    return [
        {field: 'selectItem', check: true},
        {
            title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle',
            footerFormatter: function () {
                return "合计";
            }
        },
        {title: '证件类型', field: 'idCardName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型编号', field: 'idCardType', visible: false, align: 'center', valign: 'middle'},
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
        {title: '工种', field: 'workKindName', visible: true, align: 'center', valign: 'middle'},
        {title: '工种编号', field: 'workerType', visible: false, align: 'center', valign: 'middle'},
        {
            title: '基本工资', field: 'amount', visible: true, align: 'center', valign: 'right',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='amount' data-index='" + index + "'>" + value.toFixed(2) + "</a>";
            },
            footerFormatter: function (value) {
                return "<span class='amount'></span>";
            }
        },
        {
            title: '奖励(元)', field: 'rewardAmount', visible: true, align: 'center', valign: 'right',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='rewardAmount' data-index='" + index + "'>" + value.toFixed(2) + "</a>";
            }
        },
        {
            title: '惩罚(元)', field: 'punishAmount', visible: true, align: 'center', valign: 'right',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='punishAmount' data-index='" + index + "'>" + value.toFixed(2) + "</a>";
            }
        },
        {
            title: '应发工资(元)', field: 'payAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return "<span class='payAmount'>" + Number(value).toFixed(2) + "</span>";
            },
            footerFormatter: function (value) {
                return "<span class='payAmount'></span>";
            }
        },
        {
            title: '实发工资(元)', field: 'actualAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='actualAmount' data-index='" + index + "'>" + value.toFixed(2) + "</a>";
            },
            footerFormatter: function (value) {
                return "<span class='actualAmount'></span>";
            }
        },
        {
            title: '剩余工资(元)', field: 'balanceAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return "<span class='balanceAmount'>" + Number(row.payAmount).toFixed(2) + "</span>";
            },
            footerFormatter: function (value) {
                return "<span class='balanceAmount'></span>";
            }
        }

    ];
};

//下拉多选
var selectAccount = $("#selectAccount");
var selectTeamWorker = $("#selectTeamWorker");

DetailByAccount.teamChange = function () {
    var teamSysNo = $("#byOrderTeam").val();
    if ($('#byOrderProject').val() === "" || $('#byOrderProject').val() === null) {
        layer.msg("请先选择项目");
        return false;
    }
    if (teamSysNo === "" || teamSysNo === null) {
        layer.msg("请选择班组");
        return false;
    }
    //按计工单发放
    var ajax = new $ax(Feng.ctxPath + "/account/getAccountListByTeamNo?projectCode=" + $('#byOrderProject').val() + "&teamSysNo=" + teamSysNo, function (data) {
        $("#selectAccount").chosen("destroy");
        var htmlAccount = '';
        if (data.length !== 0) {
            //有计工单
            for (var i = 0; i < data.length; i++) {
                htmlAccount += ' <option value="' + data[i].id + '" hassubinfo="true">' + data[i].projectName + ' ' + data[i].closingTime.substring(0, 10) + '</option>';
            }
        } else {
            //没有计工单
            Feng.info("该班组下没有计工单!");
        }
        $("#selectAccount").html(htmlAccount);
        $("#selectAccount").chosen({search_contains: true, no_results_text: "暂无结果"});
    }, function (data) {
        Feng.error("计工单加载失败!" + data.responseJSON.message + "!");
    });
    ajax.start();
}

//班组工人
var teamWorkers;

/**
 * 清除数据
 */
PayRollDetailInfoDlg.clearData = function () {
    this.payRollDetailInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PayRollDetailInfoDlg.set = function (key, val) {
    this.payRollDetailInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PayRollDetailInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
PayRollDetailInfoDlg.close = function () {
    parent.layer.close(window.parent.PayRoll.layerIndex);
    window.parent.PayRoll.table.refresh();
}

/**
 * 选择计工单值变动事件
 */
DetailByAccount.accountOnChange = function () {
    var value = $("#selectAccount").val();
    var accountIds = "";
    if (value !== null) {
        for (var i = 0; i < value.length; i++) {
            accountIds += value[i] + ",";
        }
        accountIds = accountIds.substring(0, accountIds.length - 1);
        choosedAccountIds = accountIds;
        //根据计工单ids获取计工单详情
        var ajax = new $ax(Feng.ctxPath + "/accountDetail/getList?ids=" + accountIds, function (data) {
            if(data && data.length > 10){
                $('#bzgrsl').removeClass('col-sm-4');
                $('#bzgrsl').addClass('col-sm-12');
            }
            if(data && data.length <= 10){
                $('#bzgrsl').removeClass('col-sm-12');
                $('#bzgrsl').addClass('col-sm-4');
            }
            $("#selectTeamWorker").chosen("destroy");
            $("#selectTeamWorker").children("option").remove();
            var htmlTeamWorker = '';
            for (var i = 0; i < data.length; i++) {
                htmlTeamWorker += ' <option value="' + data[i].idCardType + ';' + data[i].idCardNumber + '" hassubinfo="true" selected="selected">' + data[i].workerName + '</option>';
            }
            $("#selectTeamWorker").append(htmlTeamWorker);
            $("#selectTeamWorker").chosen({search_contains: true, no_results_text: "暂无结果"});
        }, function (data) {
            Feng.error("计工单加载失败!" + data.responseJSON.message + "!");
        });
        ajax.set(this.payRollDetailInfoData);
        ajax.start();
    } else {
        $("#selectTeamWorker").chosen("destroy");
        $("#selectTeamWorker").html("");
        $("#selectTeamWorker").chosen({search_contains: true, no_results_text: "暂无结果"});
    }
}

/**
 * 班组工人值变动事件
 */
DetailByAccount.teamWorkerOnChange = function () {
    var persons = [];
    var personArray = $("#selectTeamWorker").val();
    if (personArray) {
        for (var i = 0; i < personArray.length; i++) {
            var personObject = {};
            var person = personArray[i].split(";");
            personObject.idCardType = person[0];
            personObject.idCardNumber = person[1];
            persons.push(personObject);
        }
        teamWorkers = persons;
    } else {
        teamWorkers = null;
    }
}

/**
 * 表格加载成功事件
 */
var reg = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/;
DetailByAccount.tableByAccountOnloadSuccess = function (data) {
    DetailByAccount.accountDetailList = data;
    //数据初始化
    for (var i = 0; i < DetailByAccount.accountDetailList.length; i++) {
        DetailByAccount.accountDetailList [i].actualAmount = 0;
        DetailByAccount.accountDetailList [i].balanceAmount = DetailByAccount.accountDetailList [i].payAmount;
    }
    DetailByAccount.initEditable();
}

DetailByAccount.initEditable = function () {
    $("#DetailByAccount td a").editable("destroy");
    $("#DetailByAccount td a").editable({
        validate: function (params) {
            if (!$.trim(params)) {
                return '不能为空';
            }
            if (!reg.test(params)) {
                return '只能输入数值型金额';
            }
            $(this).text(params);
            DetailByAccount.calculateWages(this);
        },
        type: 'text'
    });
    DetailByAccount.sum();
}
DetailByAccount.mouldDownload = function () {
    window.location.href = "/static/excelMould/按计工单导入工资模板.xlsx";
};
/**
 * 导入工资单信息
 */
DetailByAccount.selectFile = function () {
    if (DetailByAccount.accountDetailList.length === 0) {
        Feng.error("请先查询工资单!");
        return;
    }
    $("#excelFile").trigger("click");
};


//导入
function fileUpload() {
    var myform = new FormData();
    var byOrderProject = $("#byOrderProject").val();
    var byOrderTeam = $("#byOrderTeam").val();
//    myform.append('file', $('#excelFile')[0].files[0]);
    var files = $('#excelFile')[0].files;
    if(files.length === 0){
        return;
    }
    myform.append('file', files[0]);
    $.ajax({
        url: Feng.ctxPath + "/payRollDetail/excelUploadByOrder",
        type: "POST",
        data: myform,
        contentType: false,
        processData: false,
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                $('#DetailByAccount').bootstrapTable('append', data[i]);
                DetailByAccount.accountDetailList.push(data[i]);
            }
            $("#DetailByAccount td a").editable({
                validate: function (params) {
                    if (!$.trim(params)) {
                        return '不能为空';
                    }
                    if (!reg.test(params)) {
                        return '只能输入数值型金额';
                    }
                    $(this).text(params);
                    DetailByAccount.calculateWages(this);
                },
                type: 'text'
            });
            DetailByAccount.sum();

            $('#excelFile').val("");
        },
        error: function (data) {
            Feng.error("导入失败!" + data.responseJSON.message + "!");
            $('#excelFile').val("");
        }

    });
}


/**
 * 搜索
 */
DetailByAccount.search = function () {
    //debugger;
    if ($('#byOrderProject').val() === '') {
        Feng.error("请选择项目！");
        return;
    }
    if ($("#byOrderTeam").val() === '' || $("#byOrderTeam").val() === null) {
        Feng.error("请选择班组！");
        return;
    }

    var value = $("#selectAccount").val();
    var accountIds = "";
    if (value === null || value.length === 0) {
        Feng.error("请选择计工单!");
        return;
    }
    if (value !== null) {
        for (var i = 0; i < value.length; i++) {
            accountIds += value[i] + ",";
        }
        accountIds = accountIds.substring(0, accountIds.length - 1);
        var params = {
            ids: accountIds,
        };
        //记录此时的查询数据
        payRoll = {};
        payRoll.projectCode = $('#byOrderProject').val();
        payRoll.teamSysNo = $("#byOrderTeam").val();
        payRoll.type = 1;
        payRoll.accountIds = accountIds;
        DetailByAccount.table.refresh({query: params});
    }
}


/**
 * 新增
 */
DetailByAccount.addSubmit = function (saveStatus) {
    if (DetailByAccount.accountDetailList.length === 0) {
        Feng.error("请录入数据！");
        return false;
    }

    var savedData = {};
    savedData.projectCode = payRoll.projectCode;
    savedData.teamSysNo = payRoll.teamSysNo;
    savedData.type = 2;
    savedData.accountIds = payRoll.accountIds;
    savedData.saveStatus = saveStatus;
    savedData.payRollDetailList = DetailByAccount.accountDetailList;
    savedData.payMonth = "2018-06-15";
    savedData.totalAmount = parseFloat($('.fixed-table-footer').find('.payAmount').text()).toFixed(2);
    savedData.actualAmount = parseFloat($('.fixed-table-footer').find('.actualAmount').text()).toFixed(2);
    // if (savedData.type === "1") {
    //     //按工种发放
    //     if (payRoll.payMonth === "") {
    //         Feng.error("发放日期不能为空！");
    //         return;
    //     }
    // }
    // if (payRoll.payMonth !== "") {
    //     savedData.payMonth = payRoll.payMonth + "-15";
    // }

    var ajax = new $ax(Feng.ctxPath + "/payRollDetail/addPayRollAndDetail", function (data) {
        Feng.success("新增成功!");
        parent.layer.close(window.parent.PayRoll.layerIndex);
        window.parent.PayRoll.table.refresh();
    }, function (data) {
        Feng.error("新增失败" + data.responseJSON.message + "!");
    });
    ajax.set(savedData);
    ajax.start();
}


$(function () {
    $('#myTab a').click(function (e) {
        e.preventDefault(); //阻止a链接的跳转行为
        $(this).tab('show'); //显示当前选中的链接及关联的content
    });


    $("#byOrderProject").chosen({search_contains: true, no_results_text: "暂无结果"}).on("change", function (evt, data) {
        var projectCode = data.selected;
        var ajax = new $ax(Feng.ctxPath + "/teamMaster/getList?projectCode=" + projectCode, function (data) {
            var teamMaster = $("#byOrderTeam");
            teamMaster.children("option").remove();
            var html = "<option value=''>请选择班组</option>";
            for (var i = 0; i < data.length; i++) {
                html += "<option value='" + data[i].teamSysNo + "'>" + data[i].teamName + "</option>"
            }
            teamMaster.html(html);
        }, function (data) {
            Feng.error("班组加载失败!" + data.responseJSON.message + "!");
        });
        ajax.set(this.payRollDetailInfoData);
        ajax.start();
    });
    $("#selectTeamWorker").chosen({search_contains: true, no_results_text: "暂无结果"});
    $("#selectAccount").chosen({search_contains: true, no_results_text: "暂无结果"});

    if (DetailByAccount.table === null) {
        var defaultAccountColunms = DetailByAccount.initColumn();
        var accountTable = new BSTable(DetailByAccount.id, "/accountDetail/getListNoPages", defaultAccountColunms);
        accountTable.pagination = false;
        accountTable.showFooter = true;
        accountTable.setPaginationType("client");
        accountTable.onLoadSuccess = DetailByAccount.tableByAccountOnloadSuccess;
        DetailByAccount.table = accountTable.init();
    }

    DetailByWorkerType.initChosen();
});


/**
 * 检查是否选中
 */
DetailByAccount.check = function () {
    var selected = $('#' + DetailByAccount.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    return true;
};

/****************************************************计算相关*****************************************************/


/**
 * 批量修改工资
 */
DetailByAccount.batchAmount = function () {
    if (!DetailByAccount.check()) {
        return;
    }
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
    if (!DetailByAccount.check()) {
        return;
    }
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
    if (!DetailByAccount.check()) {
        return;
    }
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
    if (!DetailByAccount.check()) {
        return;
    }
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
    reallyWages = reallyWages.toFixed(2);
    var actualAmout = parseFloat($(data).parents('tr').find('.actualAmount').text());
    $(data).parents('tr').find('.payAmount').text(reallyWages); //应发工资
    DetailByAccount.accountDetailList[index].amount = baseWages;
    DetailByAccount.accountDetailList[index].rewardAmount = rewardAmount;
    DetailByAccount.accountDetailList[index].punishAmount = punishAmount;
    DetailByAccount.accountDetailList[index].payAmount = reallyWages;
    DetailByAccount.accountDetailList[index].actualAmount = actualAmout;
    DetailByAccount.accountDetailList[index].balanceAmount = (reallyWages - actualAmout).toFixed(2);
    $(data).parents('tr').find('.balanceAmount').text(reallyWages - actualAmout); //应发工资
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
    var payAmount = $('#' + DetailByAccount.id).find("tr").find(".payAmount");
    var sum = 0;
    $(payAmount).each(function () {
        sum += parseFloat($(this).text() == '' ? 0 : $(this).text());
    });
    $(".fixed-table-footer").find(".payAmount").text(sum.toFixed(2));
    /***********************/
    var actualAmount = $('#' + DetailByAccount.id).find("tr").find(".actualAmount");
    var sum = 0;
    $(actualAmount).each(function () {
        sum += parseFloat($(this).text() == '' ? 0 : $(this).text());
    });
    $(".fixed-table-footer").find(".actualAmount").text(sum.toFixed(2));
    /***********************/
    var balanceAmount = $('#' + DetailByAccount.id).find("tr").find(".balanceAmount");
    var sum = 0;
    $(balanceAmount).each(function () {
        sum += parseFloat($(this).text() == '' ? 0 : $(this).text());
    });
    $(".fixed-table-footer").find(".balanceAmount").text(sum.toFixed(2));
}