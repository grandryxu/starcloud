/**
 * 初始化工资详情详情对话框
 */
var PayRollDetailInfoDlg = {
    payRollDetailInfoData: {}
};

//项目编号
var projectCode = '';

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
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型', field: 'idCardName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型编号', field: 'idCardType', visible: false, align: 'center', valign: 'middle'},
        {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
            return Feng.hiddenIdCard(data);
        }},
        {title: '工种', field: 'workKindName', visible: true, align: 'center', valign: 'middle'},
        {title: '工种编号', field: 'workerType', visible: false, align: 'center', valign: 'middle'},
        {
            title: '基本工资', field: 'amount', visible: true, align: 'center', valign: 'right',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='amount' data-index='" + index + "'>" + value + "</a>";
            }
        },
        {
            title: '奖励(元)', field: 'rewardAmount', visible: true, align: 'center', valign: 'right',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='rewardAmount' data-index='" + index + "'>" + value + "</a>";
            }
        },
        {
            title: '惩罚(元)', field: 'punishAmount', visible: true, align: 'center', valign: 'right',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='punishAmount' data-index='" + index + "'>" + value + "</a>";
            }
        },
        {
            title: '应发工资(元)', field: 'payAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return Number(value);
            }
        },
        {
            title: '实发工资(元)', field: 'actualAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='actualAmount' data-index='" + index + "'>" + value + "</a>";
            }
        },
        {
            title: '剩余工资(元)', field: 'balanceAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return Number(row.payAmount);
            }
        }

    ];
};

/**
 * 初始化表格的列
 */
DetailByTime.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型', field: 'idCardName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型编号', field: 'idCardType', visible: false, align: 'center', valign: 'middle'},
        {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
            return Feng.hiddenIdCard(data);
        }},
        {title: '工种', field: 'workKindName', visible: true, align: 'center', valign: 'middle'},
        {title: '工种编号', field: 'workerType', visible: false, align: 'center', valign: 'middle'},
        {title: '考勤天数', field: 'days', visible: true, align: 'center', valign: 'middle'},
        {title: '单价(元)', field: 'price', visible: true, align: 'center', valign: 'middle'},
        {
            title: '基本工资(元)', field: 'amount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='amount' data-index='" + index + "'>" + (Number(row.days) * Number(row.price)) + "</a>";
            }
        },
        {
            title: '奖励(元)', field: 'rewardAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='rewardAmount' data-index='" + index + "'>" + value + "</a>";
            }
        },
        {
            title: '惩罚(元)', field: 'punishAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='punishAmount' data-index='" + index + "'>" + value + "</a>";
            }
        },
        {
            title: '应发工资(元)', field: 'payAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return (Number(row.days) * Number(row.price));
            }
        },
        {
            title: '实发工资(元)', field: 'actualAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<a class='actualAmount' data-index='" + index + "'>" + value + "</a>";
            }
        },
        {
            title: '剩余工资(元)', field: 'balanceAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return (Number(row.days) * Number(row.price));
            }
        }
    ];
};

//下拉多选
var selectAccount = $(".select-account");
var selectTeamWorker = $(".select-teamWorker");

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
 * 项目值变动事件
 * @type
 */
PayRollDetailInfoDlg.projectOnChange = function(){
    projectCode = $("#project").val();
    var ajax = new $ax(Feng.ctxPath + "/teamMaster/getList?projectCode=" + projectCode, function (data) {
        var teamMaster = $("#team");
        teamMaster.children("option").remove();
        var html = '<option value="">请选择班组</option>';
        for (var i = 0; i < data.length; i++) {
            html += "<option value='" + data[i].teamSysNo + "'>" + data[i].teamName + "</option>"
        }
        teamMaster.append(html);
    }, function (data) {
        Feng.error("班组加载失败!" + data.responseJSON.message + "!");
    });
    ajax.start();

    //清除工资类型
    document.getElementById("type").options[0].selected = true;
}

/**
 * 工资类型值变动时间
 */
PayRollDetailInfoDlg.typeOnChange = function () {
    var project = $("#project").val();
    var teamSysNo = $("#team").val();
    var type = $("#type").val();
    if (project === "" || project === null) {
        alert("请选择项目");
        return false;
    }
    if (teamSysNo === "" || teamSysNo === null) {
        alert("请选择班组");
        return false;
    }

    PayRollDetailInfoDlg.selectInit();

    //清空关联信息
    if ("2" === type) {
        //按计工单发放
        $(".account").show();
        $("#salaryMonth").hide();
        var ajax = new $ax(Feng.ctxPath + "/account/getAccountListByTeamNo?projectCode=" + projectCode + "&teamSysNo=" + teamSysNo, function (data) {
            selectAccount.chosen("destroy");
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
            selectAccount.append(htmlAccount);
            selectAccount.chosen();
        }, function (data) {
            Feng.error("计工单加载失败!" + data.responseJSON.message + "!");
        });
        ajax.start();
    } else if ("1" === type) {
        //按工种发放
        $(".account").hide();
        $("#salaryMonth").show();
        var ajax = new $ax(Feng.ctxPath + "/teamMaster/getTeamMemberByTeamCode?teamSysNo=" + teamSysNo, function (data) {
            selectTeamWorker.chosen("destroy");
            selectTeamWorker.children("option").remove();
            var htmlAccount = '';
            var persons = [];
            for (var i = 0; i < data.length; i++) {
                var personObject = {};
                var person = data[i];
                personObject.idCardType = person.idCardType;
                personObject.idCardNumber = person.idCardNumber;
                persons.push(personObject);
                htmlAccount += ' <option value="' + data[i].idCardType + ';' + data[i].idCardNumber + '" hassubinfo="true" selected>' + data[i].workerName + '</option>';
            }
            teamWorkers = persons;
            selectTeamWorker.append(htmlAccount);
            selectTeamWorker.chosen();
        }, function (data) {
            Feng.error("按工时发放工人加载失败!" + data.responseJSON.message + "!");
        });
        ajax.start();
    } else {

    }
}

//下拉框初始化
PayRollDetailInfoDlg.selectInit = function () {
    selectAccount.chosen("destroy");
    selectAccount.children("option").remove();
    selectAccount.chosen();

    selectTeamWorker.chosen("destroy");
    selectTeamWorker.children("option").remove();
    selectTeamWorker.chosen();
}

/**
 * 班组值变动事件
 */
PayRollDetailInfoDlg.teamChange = function () {
    //清除工资类型
    document.getElementById("type").options[0].selected = true;
}
/**
 * 选择计工单值变动事件
 */
PayRollDetailInfoDlg.accountOnChange = function () {
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
            Feng.error("计工单加载失败!" + data.responseJSON.message + "!");
        });
        ajax.set(this.payRollDetailInfoData);
        ajax.start();
    }
}

/**
 * 班组工人值变动事件
 */
PayRollDetailInfoDlg.teamWorkerOnChange = function () {
    var persons = [];
    var personArray = $(".select-teamWorker").val();
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
PayRollDetailInfoDlg.tableByAccountOnloadSuccess = function (data) {
    //debugger;
    payRollDetailList = data.rows;

    //数据初始化
    for (var i = 0; i < payRollDetailList.length; i++) {
        payRollDetailList[i].actualAmount = 0;
        payRollDetailList[i].balanceAmount = payRollDetailList[i].payAmount;
    }
    $("#DetailByAccount td a").editable({
        url: function (params) {
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
                amount = Number(params.value);
                payRollDetailList[index].amount = amount;
            } else if (classList.contains('rewardAmount')) {
                rewardAmount = Number(params.value);
                payRollDetailList[index].rewardAmount = rewardAmount;
            } else if (classList.contains('punishAmount')) {
                punishAmount = Number(params.value);
                payRollDetailList[index].punishAmount = punishAmount;
            } else if (classList.contains('actualAmount')) {
                actualAmount = Number(params.value);
                payRollDetailList[index].actualAmount = actualAmount;
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
            payRollDetailList[index].payAmount = payAmount;
            payRollDetailList[index].balanceAmount = balanceAmount;
        },
        type: 'text'
    });

}

/**
 * 表格加载成功事件
 */
PayRollDetailInfoDlg.tableByTimeOnloadSuccess = function (data) {
    payRollDetailList = data.rows;

    //数据初始化
    for (var i = 0; i < payRollDetailList.length; i++) {
        payRollDetailList[i].amount = Number(payRollDetailList[i].days) * Number(payRollDetailList[i].price);
        payRollDetailList[i].rewardAmount = 0;
        payRollDetailList[i].punishAmount = 0;
        payRollDetailList[i].actualAmount = 0;
        payRollDetailList[i].payAmount = payRollDetailList[i].amount;
        payRollDetailList[i].balanceAmount = payRollDetailList[i].amount;
    }

    $("#DetailByTime td a").editable({
        url: function (params) {
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
                amount = Number(params.value);
                payRollDetailList[index].amount = amount;
            } else if (classList.contains('rewardAmount')) {
                rewardAmount = Number(params.value);
                payRollDetailList[index].rewardAmount = rewardAmount;
            } else if (classList.contains('punishAmount')) {
                punishAmount = Number(params.value);
                payRollDetailList[index].punishAmount = punishAmount;
            } else if (classList.contains('actualAmount')) {
                actualAmount = Number(params.value);
                payRollDetailList[index].actualAmount = actualAmount;
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
            payRollDetailList[index].payAmount = payAmount;
            payRollDetailList[index].balanceAmount = balanceAmount;
        },
        type: 'text'
    });

}


/**
 * 搜索
 */
PayRollDetailInfoDlg.search = function () {
    //debugger;
    if (projectCode === '') {
        Feng.error("请选择项目！");
        return;
    }
    if ($("#team").val() === '' || $("#team").val() === null) {
        Feng.error("请选择班组！");
        return;
    }
    if ($("#type").val() === '') {
        Feng.error("请选择工资类型！");
        return;
    }

    var type = $('#type').val();
    if ("2" === type) {

        var value = $(".select-account").val();
        var accountIds = "";
        if(value === null || value.length === 0){
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
                month: $("#month").val()
            };

            //记录此时的查询数据
            payRoll = {};
            payRoll.projectCode = projectCode;
            payRoll.teamSysNo = $("#team").val();
            payRoll.type = $("#type").val();
            payRoll.accountIds = accountIds;
            payRoll.payMonth = $("#month").val();

            if (DetailByAccount.table === null) {
                var defaultAccountColunms = DetailByAccount.initColumn();
                var accountTable = new BSTable(DetailByAccount.id, "/accountDetail/getListPages", defaultAccountColunms);
                accountTable.setQueryParams(params);
                accountTable.setPaginationType("server");
                accountTable.onLoadSuccess = PayRollDetailInfoDlg.tableByAccountOnloadSuccess;
                DetailByAccount.table = accountTable.init();
            } else {
                DetailByAccount.table.refresh({query: params});
            }

            //按计工单发放
            $(".table-account").show();
            $(".table-time").hide();

        }

    } else if ("1" === type) {
        //按工时发放

        var params = {
            projectCode: projectCode,
            teamSysNo: $("#team").val(),
            teamWorkers: teamWorkers,
            month: $("#month").val()
        }

        //记录此时的查询数据
        payRoll = {};
        payRoll.projectCode = projectCode;
        payRoll.teamSysNo = $("#team").val();
        payRoll.type = $("#type").val();
        payRoll.payMonth = $("#month").val();

        if (payRoll.payMonth === "") {
            Feng.error("请选择工资月份");
            return;
        }

        if (DetailByTime.table === null) {
            var defaulTimeColunms = DetailByTime.initColumn();
            var timeTable = new BSTable(DetailByTime.id, "/payRollDetail/getSalaryDetailByWorkerInfoPages", defaulTimeColunms);
            timeTable.setQueryParams(params);
            timeTable.setPaginationType("server");
            timeTable.onLoadSuccess = PayRollDetailInfoDlg.tableByTimeOnloadSuccess;
            DetailByTime.table = timeTable.init();
        } else {
            DetailByTime.table.refresh({query: params});
        }

        $(".table-account").hide();
        $(".table-time").show();
    }
}


/**
 * 新增
 */
PayRollDetailInfoDlg.addSubmit = function (saveStatus) {
    var savedData = {};
    savedData.projectCode = payRoll.projectCode;
    savedData.teamSysNo = payRoll.teamSysNo;
    savedData.type = payRoll.type;
    savedData.accountIds = payRoll.accountIds;
    savedData.saveStatus = saveStatus;
    savedData.payRollDetailList = payRollDetailList;

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
        Feng.success("修改成功!");
        parent.layer.close(window.parent.PayRoll.layerIndex);
        window.parent.PayRoll.table.refresh();
    }, function (data) {
        Feng.error("修改失败" + data.responseJSON.message + "!");
    });
    ajax.set(savedData);
    ajax.start();
}

/**
 * 批量写工资
 * @param saveStatus
 */
PayRollDetailInfoDlg.writeAccountAmounts = function () {
    layer.prompt({title: '请输入，并确认', formType: 1}, function (pass, index) {
        layer.close(index);
        layer.prompt({title: '随便写点啥，并确认', formType: 2}, function (text, index) {
            layer.close(index);
            layer.msg('演示完毕！您的口令：' + pass + '<br>您最后写下了：' + text);
        });
    });
}

$(function () {
    laydate.render({elem: '#month', max: 0, type: 'month'});
    Feng.initChosen();
    PayRollDetailInfoDlg.selectInit();
    $("#projectCode").chosen().on("change", function (evt, data) {
        var projectCode = data.selected;
        var ajax = new $ax(Feng.ctxPath + "/teamMaster/getList?projectCode=" + projectCode, function (data) {
            var teamMaster = $("#team");
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


    if (DetailByAccount.table === null) {
        var defaultAccountColunms = DetailByAccount.initColumn();
        var accountTable = new BSTable(DetailByAccount.id, "/accountDetail/getListPages", defaultAccountColunms);
        accountTable.setPaginationType("server");
        accountTable.onLoadSuccess = PayRollDetailInfoDlg.tableByAccountOnloadSuccess;
        DetailByAccount.table = accountTable.init();
    }
});
