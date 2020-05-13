/**
 * led管理管理初始化
 */
var Led = {
    id: "LedTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Led.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {
            title: '终端编号', field: 'imei', visible: true, align: 'center', valign: 'middle',
            formatter: function (data) {
                if (data) {
                    return "<span title='" + data + "'>" + (data.length > 20 ? data.substring(0, 20) + "..." : data) + "</span>";
                }
            }
        },
        {
            title: '终端名称', field: 'clientName', visible: true, align: 'center', valign: 'middle',
            formatter: function (data) {
                if (data) {
                    return "<span title='" + data + "'>" + (data.length > 20 ? data.substring(0, 20) + "..." : data) + "</span>";
                }
            }
        },
        {title: '屏宽(厘米)', field: 'screenWidth', visible: true, align: 'center', valign: 'middle'},
        {title: '屏高(厘米)', field: 'screenHeight', visible: true, align: 'center', valign: 'middle'},
        {title: '文字颜色', field: 'fontColor', visible: true, align: 'center', valign: 'middle'},
        {
            title: '模板内容', field: 'displayText', visible: true, align: 'center', valign: 'middle',
            formatter: function (data) {
                debugger;
                if (data) {
                    return "<span title='" + data + "'>" + (data.length > 30 ? data.substring(0, 30) + "..." : data) + "</span>";
                }
            }
        },
        {
            title: '是否启用', field: 'flag', visible: true, align: 'center', valign: 'middle',
            formatter: function (flag) {
                return flag === -1 ? "禁用" : "启用";
            }
        },
    ];
};

/**
 * 检查是否选中
 */
Led.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Led.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加led管理
 */
Led.openAddLed = function () {
    var index = layer.open({
        type: 2,
        title: '添加LED屏',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/led/led_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看led管理详情
 */
Led.openLedDetail = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length !== 1) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Led.seItem = selected[0];
        var index = layer.open({
            type: 2,
            title: 'LED屏详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/led/led_update/' + Led.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除led管理
 */
Led.delete = function () {
    if (this.check()) {
        layer.confirm('确认删除？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            layer.close(index);
            var selected = $('#' + Led.id).bootstrapTable('getSelections');
            var ids = "";
            for (var i = 0; i < selected.length; i++) {
                ids += selected[i].id + ",";
            }
            ids = ids.substring(0, ids.length - 1);
            var ajax = new $ax(Feng.ctxPath + "/led/deletes", function (data) {
                Feng.success("删除成功!");
                Led.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("ids", ids);
            ajax.start();
        });
    }
};

/**
 * 查询led管理列表
 */
Led.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Led.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Led.initColumn();
    var table = new BSTable(Led.id, "/led/list", defaultColunms);
    table.setPaginationType("server");
    Led.table = table.init();
});
