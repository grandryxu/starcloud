/**
 * 初始化计工单详情对话框
 */
var AccountInfoDlg = {
    accountInfoData: {}
};
var DetailTable = {
    id: "accountDetail",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
var accountDetailList = [];
/**
 * 清除数据
 */
AccountInfoDlg.clearData = function () {
    this.accountInfoData = {};
}

DetailTable.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {
            title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle',
            footerFormatter: function () {
                return "合计";
            }
        },
        {title: '证件类型', field: 'idCardTypeName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '证件号码', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',
            formatter: function (data) {
                return Feng.hiddenIdCard(data);
            }
        },
        {
            title: '工种', field: 'workTypeName', visible: true, align: 'center', valign: 'middle',
            formatter: function (value) {
                if (value.length > 3) {
                    return "<label data-toggle='tooltip'  class='a' title='" + value + "'>" + value.substring(0, 3) + "‧‧‧</label>";
                } else
                    return "<label data-toggle='tooltip' class='a' title='" + value + "'>" + value + "</label>";
            }
        },
        {title: '考勤（天）', field: 'recordDay', visible: true, align: 'center', valign: 'middle'},
        {
            title: '计工方式', field: 'type', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                var typeSelect = "<select class='type' onchange='changeEditType(this)' value='" + value + "' data-index='" + index + "'>";
                if (value == 1) {
                    typeSelect += "<option value='1' selected>计时</option>";
                } else {
                    typeSelect += "<option value='1'>计时</option>";
                }
                if (value == 2) {
                    typeSelect += "<option value='2' selected>计量</option>";
                } else {
                    typeSelect += "<option value='2'>计量</option>";
                }
                if (value == 3) {
                    typeSelect += "<option value='3' selected>包月</option>";
                } else {
                    typeSelect += "<option value='3'>包月</option>";
                }
                typeSelect += "</select>";
                return typeSelect;
            }
        },

        {
            title: '单位', field: 'unitName', visible: true, align: 'center', valign: 'middle', width: '100px',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value === undefined) {
                    value = "";
                }
                return "<div class='cellType' data-index='" + index + "'>" + value + "</div>";
            }
        },
        {
            title: '单价（元）', field: 'price', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value === undefined) {
                    value = 0;
                }
                return "<a class='price' data-index='" + index + "'>" + value.toFixed(2) + "</a>";
            }
        },
        {
            title: '工时/数量', field: 'number', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (row.type == '3') {
                    return "";
                } else if (value === null || value === '' || value === undefined) {
                    value = 0;
                }
                return "<a class='wages-number' data-index='" + index + "'>" + value + "</a>";
            }
        },
        {
            title: '基本工资', field: 'totalAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return "<span class='baseWages' data-index='" + index + "'>" + value.toFixed(2) + "</span>";
            },
            footerFormatter: function (value) {
                return "<span class='baseWages'></span>";
            }
        },
        {
            title: '奖励金额（元）', field: 'rewardAmount', viAsible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value === undefined) {
                    value = 0;
                }
                return "<a class='rewardAmount' data-index='" + index + "'>" + value.toFixed(2) + "</a>";
            }
        },
        {
            title: '扣款金额（元）', field: 'punishAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value === undefined) {
                    value = 0;
                }
                return "<a class='punishAmount' data-index='" + index + "'>" + value.toFixed(2) + "</a>";
            }
        },
        {
            title: '实发金额（元）',
            field: 'payAmuont',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value === undefined) {
                    value = 0;
                }
                return "<span class='reallyWages' data-index='" + index + "'>" + value.toFixed(2) + "</span>";
            },
            footerFormatter: function (value) {
                return "<span class='reallyWages'></span>";
            }
        }
    ];
}

var flag = $("#flag").val();
/**
 * 表格加载成功
 * @param data
 */
var reg = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/;
AccountInfoDlg.tableOnloadSuccess = function (data) {
    accountDetailList = data;
    if (flag == 1) {
        $("td a").editable({
            validate: function (params) {
                if (!$.trim(params)) {
                    return '不能为空';
                }
                if (!reg.test(params)) {
                    return '只能输入数值型金额';
                }
                $(this).text(params);
                calculateWages(this);
            },
            type: 'text'
        });

    }
    if (flag == 0) {
        $(".type").attr("disabled", "disabled");
        $(".unit").attr("disabled", "disabled");
        $("#ensure").hide();
        $("#cancel").hide();
    }
    sum();
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AccountInfoDlg.set = function (key, val) {
    this.accountInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AccountInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AccountInfoDlg.close = function () {
    parent.layer.close(window.parent.Account.layerIndex);
}

/**
 * 收集数据
 */
AccountInfoDlg.collectData = function () {
    this
        .set('id')
        .set('projectCode')
        .set('teamSysNo')
        .set('type')
        .set('closingTime')
        .set('totalAmount')
        .set('accountPerson')
        .set('createDate')
        .set('createUser')
        .set('updateDate')
        .set('updateUser')
        .set('note')
        .set('salaryId')
        .set('isDel');
}

/**
 * 提交修改
 */
AccountInfoDlg.editSubmit = function () {
    Feng.success("修改成功!");
    window.parent.Account.table.refresh();
    AccountInfoDlg.close();
}

$(function () {
    var accountId = $("#id").val();
    var deafultColunms = DetailTable.initColumn();
    var table = new BSTable(DetailTable.id, "/accountDetail/getAccountDetailByAccountIdNoPage?accountId=" + accountId, deafultColunms);
    table.pagination = false;
    table.showFooter = true;
    table.setPaginationType("client");
    table.onLoadSuccess = AccountInfoDlg.tableOnloadSuccess;
    table.onDblClickRow = AccountInfoDlg.searchInfo;
    table.showFooter = true;
    DetailTable.table = table.init();
});


/**
 * 计算工资
 */
var calculateWages = function (data) {
    var index = $(data).data("index");
    //
    // 基本工资=单价*工时
    // 实际应发=基本工资+奖励金额-扣款金额
    //
    var price = $(data).parents('tr').find('.price').text();
    var number = $(data).parents('tr').find('.wages-number').text();
    var baseWages = price * number;
    $(data).parents('tr').find('.baseWages').text(baseWages);//基本工资
    var rewardAmount = $(data).parents('tr').find('.rewardAmount').text();
    var punishAmount = $(data).parents('tr').find('.punishAmount').text();
    var reallyWages = parseInt(baseWages) + parseInt(rewardAmount) - parseInt(punishAmount);
    $(data).parents('tr').find('.reallyWages').text(reallyWages);//实际工资

    accountDetailList[index].price = price;
    accountDetailList[index].number = number;
    accountDetailList[index].totalAmount = baseWages;
    accountDetailList[index].rewardAmount = rewardAmount;
    accountDetailList[index].punishAmount = punishAmount;
    accountDetailList[index].payAmuont = reallyWages;
    accountDetailList[index].type = $(data).parents('tr').find('.type').val();
    // accountDetailList[index].type = $(data).parents('tr').find('.type').val();
    var index = $(data).data("index");
    updateAccountInfo(index);
    sum();
}

/**
 * 单价合计
 */
var sum = function () {
    var baseWages = $('#' + DetailTable.id).find("tr").find(".baseWages");
    var sum = 0;
    $(baseWages).each(function () {
        sum += parseFloat($(this).text() == '' ? 0 : $(this).text());
    });
    $(".fixed-table-footer").find(".baseWages").text(sum.toFixed(2));
    /***********************/
    var reallyWages = $('#' + DetailTable.id).find("tr").find(".reallyWages");
    var sum = 0;
    $(reallyWages).each(function () {
        sum += parseFloat($(this).text() == '' ? 0 : $(this).text());
    });
    $(".fixed-table-footer").find(".reallyWages").text(sum.toFixed(2));
}

/**
 * 切换计工类型
 */
var changeEditType = function (data) {
    if ($(data).val() == 2) {
        accountDetailList[$(data).data("index")].unit = 1;
        $(data).parent().next().find(".cellType").html("<select class='unit'  onchange='changeEditUnit(this)' data-index='" + $(data).data("index") + "'>\n" +
            "<option value='1'>米</option>\n" +
            "<option value='2'>平方米</option>\n" +
            "<option value='3'>立方米</option>\n" +
            "</select>");
        $(data).parents('tr').find('.wages-number').show();
        $(data).parents('tr').find('.wages-number').text(0);
    } else if ($(data).val() == 3) {
        accountDetailList[$(data).data("index")].unit = "";
        $(data).parent().next().find(".cellType").html("");
        $(data).parents('tr').find('.wages-number').hide();
        $(data).parents('tr').find('.wages-number').text();
    } else {
        accountDetailList[$(data).data("index")].unit = "";
        $(data).parent().next().find(".cellType").html("");
        $(data).parents('tr').find('.cellType').hide();
        $(data).parents('tr').find('.wages-number').show();
        $(data).parents('tr').find('.wages-number').text(0);
    }
    calculateWages(data);

}


/**
 * 切换单位
 * @param data
 */
var changeEditUnit = function (data) {
    var index = $(data).data("index");
    accountDetailList[index].unit = $(data).val();
    updateAccountInfo(index);
}


/**
 * 更新计工单
 */
var updateAccountInfo = function (index) {
    console.info(this.accountDetailList[index]);
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/accountDetail/updateDetail", function (data) {
        Feng.success("修改成功!");
        window.parent.Account.table.refresh();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.accountDetailList[index]);
    ajax.start();
}


/**
 * 检查是否选中
 */
AccountInfoDlg.check = function () {
    var selected = $('#' + DetailTable.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    return true;
};



/**
 * 批量计工方式
 */
AccountInfoDlg.batchType = function () {
    if(!AccountInfoDlg.check()){return;}
    // layer.prompt({title: '输入奖励金额', formType: 0}, function(text, index){
    //     layer.close(index);
    //     AccountDetailInfoDlg.batchUpdate('',text);
    // });
    var typeSelectDom = '<select id="moneyChecked" class="input-search-s">';
    typeSelectDom += '<option value="1">计时</option>';
    typeSelectDom += '<option value="2">计量</option>';
    typeSelectDom += '<option value="3">包月</option>';
    typeSelectDom += '</select>';
    layer.open({
        type: 1,
        skin: 'layui-layer-reward', //样式类名
        anim: 2,
        title:'选择计工方式',
        area: ['380px', '200px'],
        btn: ['确定', '取消'],
        shadeClose: true, //开启遮罩关闭
        content: typeSelectDom,
        yes: function(index, layero){
            var v = $('#moneyChecked').val();
            var selected = $('#' + DetailTable.id).find("input[type='checkbox']:checked");
            $(selected).each(function () {
                var cell = $(this).parents('tr').find('select.type');
                cell.val(v);
                cell.each(function (index,data) {
                    changeEditType(data);
                })
            });
            layer.close(index);
        }
    });

}


/**
 * 批量单价
 */
AccountInfoDlg.batchPrice = function () {
    if(!AccountInfoDlg.check()){return;}

    layer.prompt({
        title: '输入单价',
        formType: 0
    }, function (text, index) {
        if (!reg.test(text)) {
            Feng.info("只能输入数值型金额");
            return;
        }
        layer.close(index);
        AccountInfoDlg.batchUpdate('price', text);

    });


}

/**
 * 批量工时/数量
 */
AccountInfoDlg.batchNumber = function () {
    if(!AccountInfoDlg.check()){return;}

    layer.prompt({
        title: '输入工时/数量',
        formType: 0
    }, function (text, index) {
        if (!reg.test(text)) {
            Feng.info("只能输入数值型金额");
            return;
        }
        layer.close(index);
        AccountInfoDlg.batchUpdate('wages-number', text);
    });

}


/**
 * 批量更新数据
 */
AccountInfoDlg.batchUpdate = function (typeClass, text) {
    var selected = $('#' + DetailTable.id).find("input[type='checkbox']:checked");
    $(selected).each(function () {
        var cell = $(this).parents('tr').find('.' + typeClass);
        if (cell.html() && cell.html() != '') {
            cell.text(text);
            calculateWages(cell);
        }
    });
    $("td a").editable({
        url: function (params) {
            $(this).text(params.value);
            calculateWages(this);
        },
        type: 'text'
    });
}