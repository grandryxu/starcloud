/**
 * 初始化工资详情详情对话框
 */
var PayRollDetailInfoDlg = {
    payRollDetailInfoData: {}
};
/**
 * 按工种生成的工资单
 */
var DetailByWorkerType = {
    id: "DetailByTime",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    projectCode: "",
    teamWorkers: [],
    workerNames: ""
};

/**
 * 初始化表格的列
 */
DetailByWorkerType.initColumn = function () {
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
        {title: '考勤天数', field: 'days', visible: true, align: 'center', valign: 'middle'},
        {title: '单价(元)', field: 'price', visible: true, align: 'center', valign: 'middle'},
        {
            title: '基本工资(元)', field: 'amount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return "<a class='amount' data-index='" + index + "'>" + (Number(row.days) * Number(row.price)).toFixed(2) + "</a>";
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
                return "<a class='rewardAmount' data-index='" + index + "'>" + value.toFixed(2) + "</a>";
            }
        },
        {
            title: '惩罚(元)', field: 'punishAmount', visible: true, align: 'center', valign: 'middle',
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
                return "<span class='payAmount'>" + (Number(row.days) * Number(row.price)).toFixed(2) + "</span>"
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
                return "<span class='balanceAmount'>" + (Number(row.days) * Number(row.price)).toFixed(2) + "</span>";
            },
            footerFormatter: function (value) {
                return "<span class='balanceAmount'></span>";
            }
        }
    ];
};

//下拉多选
var selectAccount = $(".select-account");
var selectTeamWorker = $(".select-teamWorker");


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
 * 项目值变动事件
 * @type
 */
DetailByWorkerType.projectOnChange = function () {
    var ajax = new $ax(Feng.ctxPath + "/teamMaster/getList?projectCode=" + DetailByWorkerType.projectCode, function (data) {
        var teamMaster = $("#byWorkerTypeTeam");
        teamMaster.children("option").remove();
        var html = '<option value="">请选择班组</option>';
        for (var i = 0; i < data.length; i++) {
            html += "<option value='" + data[i].teamSysNo + "'>" + data[i].teamName + "</option>"
        }
        teamMaster.append(html);
    }, function (data) {
        Feng.error("班组加载失败!" + data + "!");
    });
    ajax.start();
}


DetailByWorkerType.mouldDownload = function () {
    window.location.href = "/static/excelMould/按工种导入工资模板.xlsx";
};

/**
 * 导入工资单信息
 */
DetailByWorkerType.selectFile = function () {
    if (DetailByWorkerType.payRollDetailList.length === 0) {
        Feng.error("请先查询工资单!");
        return;
    }
    $("#excelFile2").trigger("click");
};


//导入
function fileUpload2() {
    var myform = new FormData();
    myform.append('file', $('#excelFile2')[0].files[0]);
    $.ajax({
        url: Feng.ctxPath + "/payRollDetail/excelUploadByWorkerType",
        type: "POST",
        data: myform,
        contentType: false,
        processData: false,
        success: function (data) {
            //获取已有数据的最大索引
            for (var i = 0; i < data.length; i++) {
                $('#DetailByTime').bootstrapTable('append', data[i]);
                DetailByWorkerType.payRollDetailList.push(data[i]);
            }
            DetailByWorkerType.initEditable();
            $('#excelFile2').val("");
            console.log(DetailByWorkerType.payRollDetailList);
        },
        error: function (data) {
            Feng.error("导入失败!" + data.responseJSON.message + "!");
            $('#excelFile2').val("");
        }

    });
}

/**
 * 选择班组
 */
DetailByWorkerType.teamChange = function () {
    //清空子页面的数据
    DetailByWorkerType.teamWorkers = [];
    DetailByWorkerType.workerNames = "";
    // var teamSysNo = $("#byWorkerTypeTeam").val();
    // DetailByWorkerType.projectCode = $('#byWorkerTypeProject').val();
    // if (DetailByWorkerType.projectCode === "" || DetailByWorkerType.projectCode === null) {
    //     alert("请选择项目");
    //     return false;
    // }
    // if (teamSysNo === "" || teamSysNo === null) {
    //     alert("请选择班组");
    //     return false;
    // }
    //按工种发放
    // var ajax = new $ax(Feng.ctxPath + "/teamMaster/getTeamMemberByTeamCode?teamSysNo=" + teamSysNo, function (data) {
    //     $('#byWorkerTypeSelectTeamWorker').chosen("destroy");
    //     $('#byWorkerTypeSelectTeamWorker').children("option").remove();
    //     var htmlAccount = '';
    //     var persons = [];
    //     for (var i = 0; i < data.length; i++) {
    //         var personObject = {};
    //         var person = data[i];
    //         personObject.idCardType = person.idCardType;
    //         personObject.idCardNumber = person.idCardNumber;
    //         persons.push(personObject);
    //         htmlAccount += ' <option value="' + data[i].idCardType + ';' + data[i].idCardNumber + '" hassubinfo="true" selected>' + data[i].workerName + '</option>';
    //     }
    //     DetailByWorkerType.teamWorkers = persons;
    //     $('#byWorkerTypeSelectTeamWorker').append(htmlAccount);
    //     $('#byWorkerTypeSelectTeamWorker').chosen();
    // }, function (data) {
    //     Feng.error("按工种发放工人加载失败!" + data.responseJSON.message + "!");
    // });
    // ajax.start();
}

//给子页面返回detailList
function getCheckedArray() {
    var data = {};
    data.detailList = DetailByWorkerType.teamWorkers;
    data.workerNames = DetailByWorkerType.workerNames;
    return data;
}

//工人选择触发事件
DetailByWorkerType.chooseWorkers = function () {
    var teamSysNo = $("#byWorkerTypeTeam").val();
    var month =  $("#month").val();
    DetailByWorkerType.projectCode = $('#byWorkerTypeProject').val();
    if (DetailByWorkerType.projectCode === "" || DetailByWorkerType.projectCode === null) {
        Feng.info("请选择项目");
        return false;
    }
    if (teamSysNo === "" || teamSysNo === null) {
        Feng.info("请选择班组");
        return false;
    }
    if (month === "" || teamSysNo === null) {
        Feng.info("请选择工资月份");
        return false;
    }
    var index = layer.open({
        type: 2,
        title: '选择工人',
        area: ['80%', '80%'],
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/payRollDetail/payRoll_workerList?projectCode=' + $("#byWorkerTypeProject").val() + '&teamSysNo=' + $("#byWorkerTypeTeam").val(),
        end: function (data) {
            var names = "";
            for (var i = 0; i < DetailByWorkerType.teamWorkers.length; i++) {
                names += DetailByWorkerType.teamWorkers[i].workerName + ";";
            }
            DetailByWorkerType.workerNames = "" + names;
            if (names.length > 20) {
                names = names.substring(0, 20) + "..."
            }
            $("#byWorkerTypeSelectTeamWorker").val(names);
            DetailByWorkerType.search();
        }

    });
    this.layerIndex = index;
}


/**
 * 移除工资详情
 */
DetailByWorkerType.removeDetail = function () {
    var selected = $('#' + DetailByWorkerType.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中要移除的记录！");
        return false;
    }
    for (var i = 0; i < selected.length; i++) {
        for (var j = 0; j < DetailByWorkerType.teamWorkers.length; j++) {
            if (selected[i].idCardNumber === DetailByWorkerType.teamWorkers[j].idCardNumber) {
                DetailByWorkerType.teamWorkers.splice(j, 1);
            }
        }
    }

    var workerNames = '';
    for (var i = 0; i < DetailByWorkerType.teamWorkers.length; i++) {
        workerNames += DetailByWorkerType.teamWorkers[i].workerName + ";";
    }
    $("#byWorkerTypeSelectTeamWorker").val(workerNames.length > 20 ? workerNames.substring(0, 20) + "..." : workerNames);
    var names = "" + workerNames;
    DetailByWorkerType.workerNames = names;
    this.search();
}

/**
 * 选择计工单值变动事件
 */
DetailByWorkerType.accountOnChange = function () {
    var value = selectAccount.val();
    var accountIds = "";
    if (value !== null) {
        for (var i = 0; i < value.length; i++) {
            accountIds += value[i] + ",";
        }
        accountIds = accountIds.substring(0, accountIds.length - 1);
        choosedAccountIds = accountIds;
        //根据计工单ids获取计工单详情
        var ajax = new $ax(Feng.ctxPath + "/accountDetail/getList?ids=" + accountIds, function (data) {
            selectTeamWorker.chosen("destroy");
            selectTeamWorker.children("option").remove();
            var htmlTeamWorker = '';
            for (var i = 0; i < data.length; i++) {
                htmlTeamWorker += ' <option value="' + data[i].idCardType + ';' + data[i].idCardNumber + '" hassubinfo="true" selected="selected">' + data[i].workerName + '</option>';
            }
            selectTeamWorker.append(htmlTeamWorker);
            selectTeamWorker.chosen();
        }, function (data) {
            Feng.error("计工单加载失败!" + data + "!");
        });
        ajax.set(this.payRollDetailInfoData);
        ajax.start();
    }
}

/**
 * 班组工人值变动事件
 */
// DetailByWorkerType.teamWorkerOnChange = function () {
//     var persons = [];
//     var personArray = $("#byWorkerTypeSelectTeamWorker").val();
//     if (personArray) {
//         for (var i = 0; i < personArray.length; i++) {
//             var personObject = {};
//             var person = personArray[i].split(";");
//             personObject.idCardType = person[0];
//             personObject.idCardNumber = person[1];
//             persons.push(personObject);
//         }
//         DetailByWorkerType.teamWorkers = persons;
//     } else {
//         DetailByWorkerType.teamWorkers = null;
//     }
// }


/**
 * 表格加载成功事件
 */
var reg = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/;
DetailByWorkerType.tableByTimeOnloadSuccess = function (data) {
    DetailByWorkerType.payRollDetailList = data;

    //数据初始化
    for (var i = 0; i < DetailByWorkerType.payRollDetailList.length; i++) {
        DetailByWorkerType.payRollDetailList[i].amount = Number(DetailByWorkerType.payRollDetailList[i].days)
            * Number(DetailByWorkerType.payRollDetailList[i].price);
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
            if (!reg.test(params)) {
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
                DetailByWorkerType.payRollDetailList[index].amount = amount;
            } else if (classList.contains('rewardAmount')) {
                rewardAmount = Number(params);
                DetailByWorkerType.payRollDetailList[index].rewardAmount = rewardAmount;
            } else if (classList.contains('punishAmount')) {
                punishAmount = Number(params);
                DetailByWorkerType.payRollDetailList[index].punishAmount = punishAmount;
            } else if (classList.contains('actualAmount')) {
                actualAmount = Number(params);
                DetailByWorkerType.payRollDetailList[index].actualAmount = actualAmount;
            }

            //计算应发工资
            var payAmount = amount + rewardAmount - punishAmount;
            //计算剩余工资
            var balanceAmount = payAmount - actualAmount;
            //应发工资 赋值
            $($(tr.children("td")[10]).children(".payAmount")).html(payAmount);
            //剩余工资 赋值
            $($(tr.children("td")[12]).children(".balanceAmount")).html(balanceAmount);

            //更改对应对象的集合
            DetailByWorkerType.payRollDetailList[index].payAmount = payAmount;
            DetailByWorkerType.payRollDetailList[index].balanceAmount = balanceAmount;
        },
        type: 'text'
    });

}

DetailByWorkerType.params = {
    projectCode: DetailByWorkerType.projectCode,
    teamSysNo: $("#byWorkerTypeTeam").val(),
    teamWorkers: teamWorkers,
    month: $("#month").val()
}
/**
 * 搜索
 */
DetailByWorkerType.search = function () {
    DetailByWorkerType.projectCode = $('#byWorkerTypeProject').val();
    if (DetailByWorkerType.projectCode === '') {
        Feng.error("请选择项目！");
        return;
    }
    if ($("#byWorkerTypeTeam").val() === '' || $("#byWorkerTypeTeam").val() === null) {
        Feng.error("请选择班组！");
        return;
    }

    //记录此时的查询数据
    payRoll = {};
    payRoll.projectCode = DetailByWorkerType.projectCode;
    payRoll.teamSysNo = $("#byWorkerTypeTeam").val();
    payRoll.type = 1;
    payRoll.teamWorkers = DetailByWorkerType.teamWorkers;
    payRoll.payMonth = $("#month").val();
    payRoll.month = $("#month").val();
    if (payRoll.payMonth === "") {
        Feng.error("请选择工资月份");
        return;
    }

    if (DetailByWorkerType.teamWorkers.length === 0) {
        Feng.error("请选择工人");
        return;
    }

    DetailByWorkerType.table.refresh({query: payRoll});
}


/**
 * 新增
 */
DetailByWorkerType.addSubmit = function (saveStatus) {
    if (DetailByWorkerType.payRollDetailList.length === 0) {
        Feng.error("请录入数据！");
        return false;
    }

    var savedData = {};
    savedData.projectCode = payRoll.projectCode;
    savedData.teamSysNo = payRoll.teamSysNo;
    savedData.type = 1;
    savedData.accountIds = payRoll.accountIds;
    savedData.saveStatus = saveStatus;
    savedData.payRollDetailList = DetailByWorkerType.payRollDetailList;
    savedData.totalAmount = parseFloat($('.fixed-table-footer').find('.payAmount').text()).toFixed(2);
    savedData.actualAmount = parseFloat($('.fixed-table-footer').find('.actualAmount').text()).toFixed(2);
    if (savedData.type === "1") {
        //按工种发放
        if (payRoll.payMonth === "") {
            Feng.error("发放日期不能为空！");
            return;
        }
    }

    if (payRoll.payMonth !== "") {
        savedData.payMonth = payRoll.payMonth + "-15";
    }
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

/**
 * 批量写工资
 * @param saveStatus
 */
DetailByWorkerType.writeAccountAmounts = function () {
    layer.prompt({title: '请输入，并确认', formType: 1}, function (pass, index) {
        layer.close(index);
        layer.prompt({title: '随便写点啥，并确认', formType: 2}, function (text, index) {
            layer.close(index);
            layer.msg('演示完毕！您的口令：' + pass + '<br>您最后写下了：' + text);
        });
    });
}


DetailByWorkerType.initChosen = function () {

    laydate.render({
        elem: '#month',
        type: 'month',
        format: 'yyyy-MM',
        showBottom: false,
        change: function (value, dates, edate) {
            $('#month').val(value);
            if ($(".layui-laydate").length) {
                $(".layui-laydate").remove();
            }
        }
    });

    $("#byWorkerTypeProject").chosen({
        search_contains: true ,
        no_results_text: "暂无结果"
    }).on("change", function (evt, data) {
        //清空子页面数据
        DetailByWorkerType.teamWorkers = [];
        DetailByWorkerType.workerNames = "";

        var projectCode = data.selected;
        var ajax = new $ax(Feng.ctxPath + "/teamMaster/getList?projectCode=" + projectCode, function (data) {
            var teamMaster = $("#byWorkerTypeTeam");
            teamMaster.children("option").remove();
            var html = "<option value=''>请选择班组</option>";
            for (var i = 0; i < data.length; i++) {
                html += "<option value='" + data[i].teamSysNo + "'>" + data[i].teamName + "</option>"
            }
            teamMaster.append(html);
        }, function (data) {
            Feng.error("班组加载失败!" + data.responseJSON.message + "!");
        });
        ajax.set(this.payRollDetailInfoData);
        ajax.start();
    });

    var defaulTimeColunms = DetailByWorkerType.initColumn();
    var timeTable = new BSTable(DetailByTime.id, "/payRollDetail/getSalaryDetailByWorkerInfoNoPages", defaulTimeColunms);
    timeTable.pagination = false;
    timeTable.showFooter = true;
    timeTable.setPaginationType("client");
    timeTable.onLoadSuccess = DetailByWorkerType.tableByTimeOnloadSuccess;
    timeTable.setQueryParams(DetailByWorkerType.params);
    DetailByWorkerType.table = timeTable.init();
}


/**
 * 检查是否选中
 */
DetailByWorkerType.check = function () {
    var selected = $('#' + DetailByWorkerType.id).bootstrapTable('getSelections');
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
DetailByWorkerType.batchAmount = function () {
    if (!DetailByWorkerType.check()) {
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
        DetailByWorkerType.batchUpdate('amount', text);

    });

}

/**
 * 批量奖励
 */
DetailByWorkerType.batchRewardAmount = function () {
    if (!DetailByWorkerType.check()) {
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
        DetailByWorkerType.batchUpdate('rewardAmount', text);
    });

}

/**
 * 批量惩罚
 */
DetailByWorkerType.batchPunishAmount = function () {
    if (!DetailByWorkerType.check()) {
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
        DetailByWorkerType.batchUpdate('punishAmount', text);
    });

}


/**
 * 批量实发工资
 */
DetailByWorkerType.batchActualAmount = function () {
    if (!DetailByWorkerType.check()) {
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
    var reallyWages = parseFloat(baseWages) + parseFloat(rewardAmount) - parseFloat(punishAmount);
    reallyWages = reallyWages.toFixed(2);
    $(data).parents('tr').find('.payAmount').text(reallyWages); //实际工资
    var actualAmout = parseFloat($(data).parents('tr').find('.actualAmount').text());
    DetailByWorkerType.payRollDetailList[index].amount = baseWages;
    DetailByWorkerType.payRollDetailList[index].rewardAmount = rewardAmount;
    DetailByWorkerType.payRollDetailList[index].punishAmount = punishAmount;
    DetailByWorkerType.payRollDetailList[index].payAmount = reallyWages;
    DetailByWorkerType.payRollDetailList[index].balanceAmount = (reallyWages - actualAmout).toFixed(2);
    DetailByWorkerType.payRollDetailList[index].actualAmount = actualAmout;
    $(data).parents('tr').find('.balanceAmount').text(reallyWages - actualAmout); //应发工资
    DetailByWorkerType.sum();


}

/**
 * 单价合计
 */
DetailByWorkerType.sum = function () {
    var amount = $('#' + DetailByWorkerType.id).find("tr").find(".amount");
    var sum = 0;
    $(amount).each(function () {
        sum += parseFloat($(this).text() == '' ? 0 : $(this).text());
    });
    $(".fixed-table-footer").find(".amount").text(sum.toFixed(2));
    /***********************/
    var payAmount = $('#' + DetailByWorkerType.id).find("tr").find(".payAmount");
    var sum = 0;
    $(payAmount).each(function () {
        sum += parseFloat($(this).text() == '' ? 0 : $(this).text());
    });
    $(".fixed-table-footer").find(".payAmount").text(sum.toFixed(2));
    /***********************/
    var actualAmount = $('#' + DetailByWorkerType.id).find("tr").find(".actualAmount");
    var sum = 0;
    $(actualAmount).each(function () {
        sum += parseFloat($(this).text() == '' ? 0 : $(this).text());
    });
    $(".fixed-table-footer").find(".actualAmount").text(sum.toFixed(2));
    /***********************/
    var balanceAmount = $('#' + DetailByWorkerType.id).find("tr").find(".balanceAmount");
    var sum = 0;
    $(balanceAmount).each(function () {
        sum += parseFloat($(this).text() == '' ? 0 : $(this).text());
    });
    $(".fixed-table-footer").find(".balanceAmount").text(sum.toFixed(2));
}