/**
 * 初始化计工单详情详情对话框
 */
var AccountDetailInfoDlg = {
    accountDetailInfoData: {},
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    tableData: {},
    callBackData: {},
    workerIdListVal: [],
    projectCodeValue: null,
    detailList:null, //子页面（工人页面）返回的数据,
    workerNames:"" //子页面的工人名称
};

//新增的详情数据
var accountDetailList = [];
//新增的记功单
var account = {};

var AccountDetailes = {
    id: "accountDetailes", //表格id
    seItem: null, //选中的条目
    table: null,
    layerIndex: -1
}
AccountDetailes.initColumn = function () {
    return [{
            field: 'selectItem',
            check: true
        },
        {
            title: 'id',
            field: 'id',
            visible: false,
            align: 'center',
            valign: 'middle',

        },
        {
            title: '姓名',
            field: 'workerName',
            visible: true,
            align: 'center',
            valign: 'middle',
            footerFormatter: function () {
                return "合计";
            }
        },
        {
            title: '证件类型',
            field: 'idCardTypeName',
            visible: true,
            align: 'center',
            valign: 'middle',

        },
        {
            title: '证件号码',
            field: 'idCardNumber',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter:function (data) {
                return Feng.hiddenIdCard(data);
            }
        },
        {
            title: '工种',
            field: 'workTypeName',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (value) {
                if (value.length > 3) {
                    return "<label data-toggle='tooltip'  class='a' title='" + value + "'>" + value.substring(0, 3) + "‧‧‧</label>";
                } else
                    return "<label data-toggle='tooltip' class='a' title='"+ value +"'>" + value + "</label>";
            }
        },
        {
            title: '考勤（天）',
            field: 'recordDay',
            visible: true,
            align: 'center',
            valign: 'middle'
        },
        {
            title: '计工方式',
            field: 'type',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '') {
                    value = 0;
                }
                return "<select class='type' onchange='changeEditType(this)' data-index='" + index + "'>" +
                    "<option value='1'>计时</option>" +
                    "<option value='2'>计量</option>" +
                    "<option value='3'>包月</option>" +
                    "</select>";
            }
        },
        {
            title: '单位',
            field: 'unit',
            visible: true,
            align: 'center',
            valign: 'middle',
            width: '100px',
            formatter: function (value, row, index) {
                return "<div class='cellType' data-index='" + index + "'></div>";
            }
        },
        {
            title: '单价（元）',
            field: 'price',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value === undefined) {
                    value = 0;
                }
                return "<a class='price' data-index='" + index + "'>" + value + "</a>";
            }
        },
        {
            title: '工时/数量',
            field: 'number',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                return "<a class='wages-number' data-index='" + index + "'>0</a>";
            }
        },
        {
            title: '基本工资（元）',
            field: 'totalAmount',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                return "<span class='baseWages' data-index='" + index + "'>0</span>";
            },
            footerFormatter: function (value) {
                return "<span class='baseWages'></span>";
            }
        },
        {
            title: '奖励金额（元）',
            field: 'rewardAmount',
            viAsible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value === undefined) {
                    value = 0;
                }
                return "<a class='rewardAmount' data-index='" + index + "'>" + value + "</a>";
            }
        },
        {
            title: '扣款金额（元）',
            field: 'punishAmount',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value === undefined) {
                    value = 0;
                }
                return "<a class='punishAmount' data-index='" + index + "'>" + value + "</a>";
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
                return "<span class='reallyWages' data-index='" + index + "'>" + value + "</span>";
            },
            footerFormatter: function (value) {
                return "<span class='reallyWages'></span>";
            }
        }
    ];
}

/**
 * 清除数据
 */
AccountDetailInfoDlg.clearData = function () {
    this.accountDetailInfoData = {};
}





/**
 * 重新选择项目的时候,清空已选择的工人 以及子页面的数据
 */
AccountDetailInfoDlg.updateSelectedWorker=function (){
    AccountDetailInfoDlg.workerIdListVal=[];
    AccountDetailInfoDlg.detailList = null;
    AccountDetailInfoDlg.workerNames = '';
};
/**
 * 接收来自子页面的数据
 * @param rel
 */
function setRel(rel) {
    var workerIdListVal = [];
    for(var i=0;i<rel.detailList.length;i++){
        workerIdListVal.push(rel.detailList[i].id)
    }
    AccountDetailInfoDlg.workerIdListVal = workerIdListVal;
    AccountDetailInfoDlg.detailList = rel.detailList;
    AccountDetailInfoDlg.workerNames = rel.workerNames;
}


/**
 * 子页面从父页面获取的数据
 * @param rel
 */
function getCheckedArray() {
   var data = {};
   data.detailList = AccountDetailInfoDlg.detailList;
   data.workerNames = AccountDetailInfoDlg.workerNames;
   return data;
}

/**
 * 查询结算单列表
 */
AccountDetailInfoDlg.search = function () {
    AccountDetailInfoDlg.projectCodeValue = $("#projectCode").val();
    var queryData = {};
    queryData.team = $("#team").val();
    queryData.closingTime = $("#closingTime").val();
    queryData.projectCode = $("#projectCode").val();
    var teamWorker="";
    for(var i=0;i<AccountDetailInfoDlg.workerIdListVal.length;i++){
        teamWorker+=AccountDetailInfoDlg.workerIdListVal[i]+",";
    }
    teamWorker=teamWorker.substring(0,teamWorker.length-1);
    queryData.ids = teamWorker;
    AccountDetailes.table.refresh({
        query: queryData
    });
};

/**
 * 删除工人
 */
AccountDetailInfoDlg.deleteWorker=function () {
    var selected = $('#' + AccountDetailes.id).bootstrapTable('getSelections');
    if(selected.length>0){
        Feng.confirm("确认移除？",function () {
            for (var i = 0; i < selected.length; i++) {
                var object = selected[i];
                var workerIdStr = selected[i].id;
                //移除元素
                AccountDetailInfoDlg.workerIdListVal.splice($.inArray(workerIdStr, AccountDetailInfoDlg.workerIdListVal), 1);
                //移除子页面数据
                for(var j=AccountDetailInfoDlg.detailList.length-1;j>=0&&j<AccountDetailInfoDlg.detailList.length;j--){
                    if(object.idCardType=== AccountDetailInfoDlg.detailList[j].idCardType && object.idCardNumber=== AccountDetailInfoDlg.detailList[j].idCardNumber){
                        //从名称中删除此人
                        AccountDetailInfoDlg.workerNames = AccountDetailInfoDlg.workerNames.replace(AccountDetailInfoDlg.detailList[j].workerName+";", "")
                        //子页面集合中删除此人
                        AccountDetailInfoDlg.detailList.splice(j,1);
                    }
                }
            }
            //重新加载列表
            AccountDetailInfoDlg.search();
        })
    }else{
        Feng.info("请选择要移除的人！")
    }
}






/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AccountDetailInfoDlg.set = function (key, val) {
    this.accountDetailInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}
/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AccountDetailInfoDlg.get = function (key) {
    return $("#" + key).val();
}
/**
 * 关闭此对话框
 */
AccountDetailInfoDlg.close = function () {
    parent.layer.closeAll();
}


/**
 * 收集数据s
 */
AccountDetailInfoDlg.collectData = function () {
    this
        .set('id')
        .set('accountId')
        .set('idCardType')
        .set('idCardNumber')
        .set('recordDay')
        .set('type')
        .set('unit')
        .set('price')
        .set('number')
        .set('totalAmount')
        .set('rewardAmount')
        .set('punishAmount')
        .set('payAmuont')
        .set('startDate')
        .set('endDate')
        .set('isSign')
        .set('sign')
        .set('iconSign')
        .set('photo')
        .set('iconPhoto')
        .set('signDate')
        .set('createDate')
        .set('createUser')
        .set('updateDate')
        .set('updateUser')
        .set('note');
}

//下拉多选
var selectAccount = $(".select-account");
var selectTeamWorker = $(".select-teamWorker");

/**
 * 提交添加
 */
AccountDetailInfoDlg.addSubmit = function () {
    var savedData = {};
    console.log( $("#projectCode").val()+"=="+$("#team").val()+"=="+$("#closingTime").val())
    savedData.projectCode = $("#projectCode").val();
    savedData.teamSysNo = $("#team").val();
    savedData.closingTime = $("#closingTime").val();
    savedData.accountDetailListStr = JSON.stringify(accountDetailList);
    console.log(savedData);
    var ajax = new $ax(Feng.ctxPath + "/accountDetail/addAccountAndAccountDetail", function (data) {
       console.log("成功");

    }, function (data) {
       // debugger;
        console.log("失败");
        Feng.error("计工单加载失败!" + data.responseJSON.message + "!");
    });
    if (accountDetailList.length == 0) {
        Feng.info("请选择工人！");
        return;
    }
    if (savedData.closingTime == null) {
        Feng.info("请选上次计工时间！");
        return;
    }
    ajax.set(savedData);
    ajax.start();
    AccountDetailInfoDlg.close();

}

/**
 * 提交修改
 */
AccountDetailInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/accountDetail/update", function (data) {
        Feng.success("修改成功!");
        window.parent.AccountDetail.table.refresh();
        AccountDetailInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.start();
}

AccountDetailInfoDlg.openWorkerList=function () {
    if ($("#projectCode").val() == '') {
        Feng.info("请选择项目！");
        return;
    }
    if ($("#team").val() == '') {
        Feng.info("请选择班组！");
        return;
    }
    if ($("#closingTime").val() == '') {
        Feng.info("请选择截止日期！");
        return;
    }
    var index = layer.open({
        type: 2,
        title: '选择工人',
        area: ['80%', '80%'],
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/account/account_workerList?projectCode=' + $("#projectCode").val()+"&team="+ $("#team").val(),
        end: function (data) {
            AccountDetailInfoDlg.search();
        }
    });
    this.layerIndex = index;
}

/**
 * 表格加载成功batchType
 * @param data
 */
var reg = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/;

AccountDetailes.tableOnloadSuccess = function (data) {
    accountDetailList = data;
    //初始化 默认type 1
     for(var i=0;i<accountDetailList.length;i++){
         accountDetailList[i].type = 1;
     }
    $("td a").editable({
        validate: function (params) {
            if (!$.trim(params)) {
                return '不能为空';
            }
            if(!reg.test(params)){
                return '只能输入数值型金额';
            }
            $(this).text(params);
            calculateWages(this);
        },
        type: 'text'
    });
    sum();
}
/**
 * 多选下拉框初始化
 * @param data
 */
AccountDetailInfoDlg.selectInit = function (data) {
    selectAccount.chosen();
    selectTeamWorker.chosen();
}

/**
 * 切换计工类型
 */
var changeEditType = function (data) {
    if ($(data).val() == 2) {
        $(data).parent().next().find(".cellType").html("<select class='unit' data-index='" + $(data).data("index") + "'>\n" +
            "<option value='1'>米</option>\n" +
            "<option value='2'>平方米</option>\n" +
            "<option value='3'>立方米</option>\n" +
            "</select>");
        $(data).parents('tr').find('.wages-number').show();
        $(data).parents('tr').find('.wages-number').text(0);
    } else if ($(data).val() == 3) {
        $(data).parent().next().find(".cellType").html("");
        $(data).parents('tr').find('.wages-number').hide();
        $(data).parents('tr').find('.wages-number').text(1);
    } else {
        $(data).parent().next().find(".cellType").html("");
        $(data).parents('tr').find('.wages-number').show();
        $(data).parents('tr').find('.wages-number').text(0);

    }
    $("td a").editable({
        url: function (params) {
            $(this).text(params.value);
            calculateWages(this);
        },
        type: 'text'
    });
    calculateWages(data);
    var index = $(data).data("index");
    accountDetailList[index].type = $(data).val();

}


/**
 * 检查是否选中
 */
AccountDetailes.check = function () {
    var selected = $('#' + AccountDetailes.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    return true;
};


/**
 * 批量计工方式
 */
AccountDetailInfoDlg.batchType = function () {
    if(!AccountDetailes.check()){return;}
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
            var selected = $('#' + AccountDetailes.id).find("tbody input[type='checkbox']:checked");
            $(selected).each(function () {
                var cell = $(this).parents('tr').find('.type');
                var options = $(cell).find("option");
                for(var i=0;i<options.length; i++){
                    if(v === options[i].value){
                        options[i].selected = true;
                        break;
                    }
                }
                calculateWages(cell);
            });
            layer.close(index);
        }
    });

}


/**
 * 批量单价
 */
AccountDetailInfoDlg.batchPrice = function () {
    if(!AccountDetailes.check()){return;}

    layer.prompt({
        title: '输入单价',
        formType: 0
    }, function (text, index) {
        if (!reg.test(text)) {
            Feng.info("只能输入数值型金额");
            return;
        }
        layer.close(index);
        AccountDetailInfoDlg.batchUpdate('price', text);

    });


}

/**
 * 批量工时/数量
 */
AccountDetailInfoDlg.batchNumber = function () {
    if(!AccountDetailes.check()){return;}

    layer.prompt({
        title: '输入工时/数量',
        formType: 0
    }, function (text, index) {
        if (!reg.test(text)) {
            Feng.info("只能输入数值型金额");
            return;
        }
        layer.close(index);
        AccountDetailInfoDlg.batchUpdate('wages-number', text);
    });

}

/**
 * 批量更新数据
 */
AccountDetailInfoDlg.batchUpdate = function (typeClass, text) {
    var selected = $('#' + AccountDetailes.id).find("input[type='checkbox']:checked");
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

/**
 * 计算工资
 */
var calculateWages = function (data) {

    var index = $(data).data("index");
    //
    // 基本工资=单价*工时
    // 实际应发=基本工资+奖励金额-扣款金额
    //
    var type = $($(data).parents('tr').find('.type')).val();
    var price = $(data).parents('tr').find('.price').text();
    var number = $(data).parents('tr').find('.wages-number').text();
    var baseWages = price * number;
    $(data).parents('tr').find('.baseWages').text(baseWages); //基本工资
    var rewardAmount = $(data).parents('tr').find('.rewardAmount').text();
    var punishAmount = $(data).parents('tr').find('.punishAmount').text();
    var reallyWages = baseWages + parseInt(rewardAmount) - parseInt(punishAmount);
    $(data).parents('tr').find('.reallyWages').text(reallyWages); //实际工资
    accountDetailList[index].type = type;
    accountDetailList[index].price = price;
    accountDetailList[index].number = number;
    accountDetailList[index].totalAmount = baseWages;
    accountDetailList[index].rewardAmount = rewardAmount;
    accountDetailList[index].punishAmount = punishAmount;
    accountDetailList[index].payAmuont = reallyWages;
    sum();
}

/**
 * 单价合计
 */
var sum = function () {
    var baseWages = $('#' + AccountDetailes.id).find("tr").find(".baseWages");
    var sum = 0;
    $(baseWages).each(function () {
        sum += parseFloat($(this).text() == '' ? 0 : $(this).text());
    });
    $(".fixed-table-footer").find(".baseWages").text(sum.toFixed(2));
    /***********************/
    var reallyWages = $('#' + AccountDetailes.id).find("tr").find(".reallyWages");
    var sum = 0;
    $(reallyWages).each(function () {
        sum += parseFloat($(this).text() == '' ? 0 : $(this).text());
    });
    $(".fixed-table-footer").find(".reallyWages").text(sum.toFixed(2));
}


$(function () {
    AccountDetailInfoDlg.selectInit();
    var defaultColunms = AccountDetailes.initColumn();
    var table = new BSTable(AccountDetailes.id, "/workerMaster/getPersonListByTeamCodeNoPage", defaultColunms);
    table.pagination = false;
    table.showFooter = true;
    table.setPaginationType("client");
    table.onLoadSuccess = AccountDetailes.tableOnloadSuccess;
    AccountDetailes.table = table.init();
    $("#projectCode").chosen({search_contains: true, no_results_text: "暂无结果"}).on("change", function (evt, data) {
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
    laydate.render({
        elem: '#closingTime'
    });
});