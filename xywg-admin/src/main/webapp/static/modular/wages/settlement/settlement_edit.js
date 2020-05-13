/**
 * 计工单管理初始化
 */
var SettlementEdit = {
    id: "settlementEditTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    tableData:{}
};

/**
 * 初始化表格的列
 */
SettlementEdit.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle',
            footerFormatter: function () {
                return "合计";
            }},
        {title: '证件类型', field: 'idCardTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型', field: 'idCardType', visible: false, align: 'center', valign: 'middle'},
        {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
            return Feng.hiddenIdCard(data);
        }},
        {title: '工种', field: 'workerTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '累计应发(元)', field: 'addPayAmount', visible: true, align: 'center', valign: 'middle',
            footerFormatter: function (value) {
                var count = 0;
                for(var i=0;i<value.length;i++){
                    count+=parseFloat(value[i].addPayAmount);
                }
                return count.toFixed(2);
            }},
        {title: '累计已发(元)', field: 'addActualAmount', visible: true, align: 'center', valign: 'middle',
            footerFormatter: function (value) {
                var count = 0;
                for(var i=0;i<value.length;i++){

                    count+=parseFloat(value[i].addActualAmount);
                }
                return count.toFixed(2);
            }},
        {title: '累计剩余(元)', field: 'addBalanceAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value===undefined) {
                    value = 0;
                }
                return "<span class='addBalanceAmount' data-index='"+index+"'>" + value + "</span>";
            },
            footerFormatter: function (value) {
                var count = 0;
                for(var i=0;i<value.length;i++){
                    count+=parseFloat(value[i].addBalanceAmount);
                }
                return "<span class='addBalanceAmount'>"+count.toFixed(2)+"</span>";
            }},

        {title: '奖励(元)', field: 'rewardAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value===undefined) {
                    value = 0;
                }
                return "<a class='rewardAmount' data-index='"+index+"'>" + value + "</a>";
            },
            footerFormatter: function (value) {
                var count = 0;
                for(var i=0;i<value.length;i++){
                    count+=parseFloat(value[i].punishAmount);
                }
                return "<span class='rewardAmount'>"+count.toFixed(2)+"</span>";
            }},
        {title: '惩罚(元)', field: 'punishAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value===undefined) {
                    value = 0;
                }
                return "<a class='punishAmount' data-index='"+index+"'>" + value + "</a>";
            },
            footerFormatter: function (value) {
                var count = 0;
                for(var i=0;i<value.length;i++){
                    count+=parseFloat(value[i].rewardAmount);
                }
                return "<span class='punishAmount'>"+count.toFixed(2)+"</span>";
            }},
        {title: '结算应发(元)', field: 'settlePayAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value===undefined) {
                    value = 0;
                }
                return "<span class='settlePayAmount' data-index='"+index+"'>" + value + "</span>";
            },
            footerFormatter: function (value) {
                var count = 0;
                for(var i=0;i<value.length;i++){
                    count+=parseFloat(value[i].settlePayAmount);
                }
                return "<span class='settlePayAmount'>"+count.toFixed(2)+"</span>";
            }},
        {title: '结算实发(元)', field: 'settleActualAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value===undefined) {
                    value = 0;
                }
                return "<a class='settleActualAmount' data-index='"+index+"'>" + value + "</a>";
            },
            footerFormatter: function (value) {
                var count = 0;
                for(var i=0;i<value.length;i++){
                    count+=parseFloat(value[i].settleActualAmount);
                }
                return "<span class='settleActualAmount'>"+count.toFixed(2)+"</span>";
            }}
    ];
};

SettlementEdit.tableOnloadSuccess = function (data) {
    SettlementEdit.tableData = data;
    editableRow();
};


/**
 * 检查是否选中
 */
SettlementEdit.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的记录！");
        return false;
    /*} else if(selected.length > 1){
        Feng.info("只能选择表格中的某一记录！");
        return false;*/
    }
    SettlementEdit.seItem=selected;
    return true;
};

/**
 * 批量写数据
 * @param testClass
 */
SettlementEdit.batchWrite=function (testClass){
    if(!SettlementEdit.check()){
        return ;
    }
    layer.prompt({title: '请输入金额', formType: "",maxlength: 20}, function(text, index){
        $(".layui-layer-content").children("#promptMsg").remove();
        if(checkMoney(text)){
            layer.close(index);
            SettlementEdit.batchUpdate(testClass,text);
        }else{

            //$(this).append("<span id='promptMsg'>只能输入数值型金额</span>");
            $(".layui-layer-content").append("<span id='promptMsg' style='color:red'>只能输入数值型金额</span>");
        }
    });
};


/**
 * 批量更新数据
 */
SettlementEdit.batchUpdate = function (typeClass,text) {
    var selected = $('#' + SettlementEdit.id).find("input[type='checkbox']:checked");
    $(selected).each(function(){
        var cell = $(this).parents('tr').find('.'+typeClass);
        if(cell.html()&&cell.html()!==''){
            cell.text(text);
            calculateWages(cell);
        }
    });
    editableRow();
};

/**
 * 计算工资
 */
var calculateWages=function(data){
    var index = $(data).data("index");
    //累计剩余工资(工资单明细计算获取)
    var addBalanceAmount = $(data).parents('tr').find('.addBalanceAmount').text();
    ////惩罚金额
    var punishAmount  = $(data).parents('tr').find('.punishAmount').text();
    //奖励金额
    var rewardAmount  =$(data).parents('tr').find('.rewardAmount').text();
    //结算应发
    var settlePayAmount =parseFloat(addBalanceAmount)+parseFloat(rewardAmount)-parseFloat(punishAmount);
    //设置结算实发的值
    $(data).parents('tr').find('.settlePayAmount').text(settlePayAmount);
    //结算实发
    var settleActualAmount=$(data).parents('tr').find('.settleActualAmount').text();

    //惩罚金额
    SettlementEdit.tableData[index].punishAmount=parseFloat(punishAmount).toFixed(2);
    //奖励金额
    SettlementEdit.tableData[index].rewardAmount=parseFloat(rewardAmount).toFixed(2);
    //结算应发
    SettlementEdit.tableData[index].settlePayAmount=parseFloat(settlePayAmount).toFixed(2);
    //结算实发
    SettlementEdit.tableData[index].settleActualAmount=parseFloat(settleActualAmount).toFixed(2);
    //结算剩余
    SettlementEdit.tableData[index].settleBalanceAmount=(parseFloat(settlePayAmount)-parseFloat(settleActualAmount)).toFixed(2);

    sum();
};


/**
 * 单价合计
 */
var sum = function () {
    //奖励金额
    var baseWages = $('#' + SettlementEdit.id).find("tr").find(".rewardAmount");
    var sum = 0;
    $(baseWages).each(function(){
        sum+=parseFloat($(this).text()==''?0:$(this).text());
    });
    $(".fixed-table-footer").find(".rewardAmount").text(sum.toFixed(2));
    /***********************/

    //惩罚金额
    var reallyWages = $('#' + SettlementEdit.id).find("tr").find(".punishAmount");
    sum = 0;
    $(reallyWages).each(function(){
        sum+=parseFloat($(this).text()==''?0:$(this).text());
    });
    $(".fixed-table-footer").find(".punishAmount").text(sum.toFixed(2));
    /***********************/

    //结算应发
    var reallyWages = $('#' + SettlementEdit.id).find("tr").find(".settlePayAmount");
    sum = 0;
    $(reallyWages).each(function(){
        sum+=parseFloat($(this).text()==''?0:$(this).text());
    });
    $(".fixed-table-footer").find(".settlePayAmount").text(sum.toFixed(2));
    /***********************/
    //结算实发
    var reallyWages = $('#' + SettlementEdit.id).find("tr").find(".settleActualAmount");
    sum = 0;
    $(reallyWages).each(function(){
        sum+=parseFloat($(this).text()==''?0:$(this).text());
    });
    $(".fixed-table-footer").find(".settleActualAmount").text(sum.toFixed(2));

};


$(function () {
    var defaultColunms = SettlementEdit.initColumn();
    var table = new BSTable(SettlementEdit.id, "/settlement/getSettlementDetailList", defaultColunms);
    table.setQueryParams({"settlementCode":$("#settlementCode").val()});
    table.onLoadSuccess = SettlementEdit.tableOnloadSuccess;
    table.pagination = false;
    table.showFooter = true;
    table.setPaginationType("client");
    SettlementEdit.table = table.init();
});

/**
 * 保存
 */
SettlementEdit.save = function () {
    if(JSON.stringify(SettlementEdit.tableData)==="{}" || JSON.stringify(SettlementEdit.tableData)==="[]"){
        layer.msg("空数据无须保存!");
        return;
    }
    var indexParent = parent.layer.getFrameIndex(window.name);
    layer.msg('确定提交该表数据？', {
        time: 0 //不自动关闭
        , btn: ['确认', '取消']
        , yes: function (index) {
            layer.close(index);
            var ajax = new $ax(Feng.ctxPath + "/settlement/updateSettlementDetailBatch", function (data) {
                Feng.success("操作成功!");
                //SettlementEdit.table.refresh();
                parent.layer.close(indexParent);
            });
            var queryData = {};
            queryData['SettlementDetailList'] =JSON.stringify(SettlementEdit.tableData);
            //需要传列表的list -------->SettlementDetailList       和总计 ---------->settlement
            ajax.set("SettlementDetailList",JSON.stringify(SettlementEdit.tableData));
            ajax.set("settlement",JSON.stringify(SettlementEdit.getSumCell()));
            ajax.start();
        }
    });
};
/**
 * 计算合计
 */
SettlementEdit.getSumCell = function () {
    /*//累计应发金额
    var addPayAmount = $('.fixed-table-footer').find('.addPayAmount').text();
    //累计实发金额
    var addActualAmount = $('.fixed-table-footer').find('.addActualAmount').text();
    //累计剩余工资
    var addBalanceAmount = $('.fixed-table-footer').find('.addBalanceAmount').text();
    //奖励金额
    var rewardAmount= $('.fixed-table-footer').find('.rewardAmount').text();
    //惩罚金额
    var punishAmount = $('.fixed-table-footer').find('.punishAmount').text();*/

    //结算应发
    var settlePayAmount=$('.fixed-table-footer').find('.settlePayAmount').text();
    //结算实发
    var settleActualAmount=$('.fixed-table-footer').find('.settleActualAmount').text();

    return {
        //总计：
        //剩余金额应该是剩余金额总额=之前的剩余金额-实际结算金额
        payedMoney:(parseFloat(settlePayAmount)-parseFloat(settleActualAmount)).toFixed(2),
        totalAmount:parseFloat(settlePayAmount).toFixed(2),
        actualAmount:parseFloat(settleActualAmount).toFixed(2),
        settlementCode: $("#settlementCode").val()
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
            if(!reg.test(value)){
                return '只能输入数值型金额';
            }
            $(this).text(value);
            calculateWages(this);
        }
    });
}


function checkMoney(text){
    var reg = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/;
    if(reg.test(text)){
        return true
    }else{
        return false
    }
}

