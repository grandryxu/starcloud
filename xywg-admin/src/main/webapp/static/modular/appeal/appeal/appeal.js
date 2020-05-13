/**
 * 申诉模块管理初始化
 */
var Appeal = {
    id: "AppealTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Appeal.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '企业名称', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '招聘主题', field: 'recruitStation', visible: true, align: 'center', valign: 'middle'},
        {title: '评价人', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '评价内容', field: 'evaluate', visible: true, align: 'center', valign: 'middle'},
        {
            title: '评分', field: 'score', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return '<input  type="number"  class="rating" min=0 max=5 step=1 data-size="sm" disabled="disabled" value="' + value + '">';
            }
        },
        {title: '评价时间', field: 'evaluateDate', visible: true, align: 'center', valign: 'middle'},
        {title: '申诉时间', field: 'appealDate', visible: true, align: 'center', valign: 'middle'},
        {title: '申诉类型', field: 'typeName', visible: true, align: 'center', valign: 'middle'},
        {title: '申诉内容', field: 'appealReason', visible: true, align: 'center', valign: 'middle'},
        {
            title: '状态', field: 'isAudit', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                var isAuditStr = '';
                if (row.isAudit === 0) {
                    isAuditStr = '待审核';
                } else if (row.isAudit === 1) {
                    isAuditStr = '通过';
                } else if (row.isAudit === 2) {
                    isAuditStr = '驳回';
                }
                return isAuditStr;
            }
        }
    ];
};

/**
 * 检查是否选中
 */
Appeal.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Appeal.seItem = selected;
        return true;
    }
};

/**
 * 点击添加申诉模块
 */
Appeal.openAddAppeal = function () {
    var index = layer.open({
        type: 2,
        title: '添加申诉模块',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/appeal/appeal_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看申诉模块详情
 */
Appeal.openAppealDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '申诉模块详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/appeal/appeal_update/' + Appeal.seItem.id
        });
        this.layerIndex = index;
    }
};

//通过 驳回
Appeal.operation = function (o) {
    if (this.check()) {
        var selected = this.seItem;
        var ids = '';
        for (var i = 0; i < selected.length; i++) {
            if (selected[i].isAudit !== 0) {
                Feng.info(selected[i].recruitStation + '已操作,只能操作待审核的数据');
                return;
            }
            ids += selected[i].id + ',';
        }
        ids = ids.substring(0, ids.length - 1);

        var ajax = new $ax(Feng.ctxPath + "/appeal/operation", function (data) {
            if (o === 2) {
                //驳回
                Feng.success("驳回成功");
            } else if (o === 1) {
                //通过
                Feng.success("通过成功");
            }
            Appeal.table.refresh();
        }, function (data) {
            if (o === 2) {
                Feng.error("驳回失败!" + data.responseJSON.message + "!");
            }else{
                Feng.error("通过失败!" + data.responseJSON.message + "!");
            }
        });
        if(o === 2){
            //驳回填写驳回理由
            var textAreaDom = '<div class="col-sm-12"><textarea id="rejectReason" class="textarea-sss" cols="4"  style="resize:none;" placeholder="请输入驳回理由" onkeyup="numberTip()"></textarea>' +
                '<div><label style="float: right" >你还可以输入<label style="color: red" id="wordNums">255</label>个字</label></div>' +
                '</div>';
            layer.open({
                type: 1,
                skin: 'layui-layer-reward', //样式类名
                anim: 2,
                title:'填写驳回理由',
                area: ['380px', '240px'],
                btn: ['保存', '关闭'],
                shadeClose: true, //开启遮罩关闭
                content: textAreaDom,
                 success: function (layero) {
                var btn = layero.find('.layui-layer-btn');
                btn.css('text-align', 'center');
                 },
                btn1: function(index, layero){
                    if($("#rejectReason").val() === ''){
                        Feng.info("请输入驳回理由");
                        return false;
                    }
                    ajax.set({
                        ids: ids,
                        isAudit: o ,
                        rejectReason: $("#rejectReason").val()
                    });
                    ajax.start();
                    layer.close(index);
                },
                btn2: function(index, layero){
                    layer.close(index);
                }
            });
        }else{
            layer.confirm('确认通过该条申诉', {
                btn: ['确定', '取消'] //按钮
            }, function (index) {
                layer.close(index);
                ajax.set({
                    ids: ids,
                    isAudit: o
                });
                ajax.start();
            })
        }
    }
};

function numberTip(){
    var value = $("#rejectReason").val();
    if(value.length>255){
        $("#rejectReason").val(value.substring(0,255));
        $("#wordNums").html(0);
    }else{
        $("#wordNums").html(255-value.length);
    }
}

/**
 * 删除申诉模块
 */
Appeal.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/appeal/delete", function (data) {
            Feng.success("删除成功!");
            Appeal.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("appealId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询申诉模块列表
 */
Appeal.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['type'] = $("#type").val();
    queryData['startDate'] = $("#startDate").val();
    queryData['endDate'] = $("#endDate").val();
    queryData['isAudit'] = $("#isAudit").val();
    Appeal.table.refresh({query: queryData});
};

$(function () {
    Appeal.resetDate = Feng.initStartEndDate();
    var defaultColunms = Appeal.initColumn();
    var table = new BSTable(Appeal.id, "/appeal/list", defaultColunms);
    table.setPaginationType("server");
    table.onLoadSuccess = Appeal.onLoadSuccess;
    Appeal.table = table.init();
});

//表格加载成功事件
Appeal.onLoadSuccess = function () {
    $(".rating").rating({
        showClear:false
    });
}

function resetDate() {
    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = currentDate.getMonth() + 1;
    var date = currentDate.getDate();

    for (var i = 0; i < Appeal.resetDate.length; i++) {
        var dateObject = Appeal.resetDate[i];

        //去除最小时间限制
        dateObject.config.min.year = '';
        dateObject.config.min.month = '';
        dateObject.config.min.date = '';

        dateObject.config.max.year = '9999';
        dateObject.config.max.month = '12';
        dateObject.config.max.date = '31';

        //设置当前时间
        dateObject.config.dateTime.year = year;
        dateObject.config.dateTime.month = month;
        dateObject.config.dateTime.date = date;
        // dateObject.startDate.config.dateTime.hours = '时';
        // dateObject.startDate.config.dateTime.minutes = '分';
        // dateObject.startDate.config.dateTime.seconds = '秒';
    }
}