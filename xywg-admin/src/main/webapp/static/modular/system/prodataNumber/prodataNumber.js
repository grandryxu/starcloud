/**
 * 管理初始化
 * @type {{seItem: null, id: string, table: null, layerIndex: number}}
 */
var ProDataNumber = {
    id: "ProDataNumberTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
/**
 * 表格列初始化
 * @returns {*[]}
 */
ProDataNumber.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '数据指纹', field: 'dataNumber', visible: true, align: 'center', valign: 'middle'},
        {title: '区域', field: 'areaName', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '创建人', field: 'createUser', visible: true, align: 'center', valign: 'middle'}
    ];
};
/**
 * 检查是否选中
 */
ProDataNumber.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("只能选中表格中的某一记录！");
        return false;
    } else {
        ProDataNumber.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
ProDataNumber.openAddProdataNumber = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/prodataNumber/prodataNumber_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
/*ProDataNumber.openProdataNumberDetail = function () {
    if (ProDataNumber.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/prodataNumber/prodataNumber_view/' + ProDataNumber.seItem.id
        });
        this.layerIndex = index;
    }
};*/

ProDataNumber.editProdataNumberDetail = function () {
    if (ProDataNumber.check()) {
        var index = layer.open({
            type: 2,
            title: '修改'+ProDataNumber.seItem.projectName,
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/prodataNumber/prodataNumber_update/' + ProDataNumber.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
ProDataNumber.delete = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        layer.confirm('确认删除？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            var ids = "";
            for (var i = 0; i < selected.length; i++) {
                ids += selected[i].id + ","
            }
            ids = ids.substring(0, ids.length - 1);
            var ajax = new $ax(Feng.ctxPath + "/prodataNumber/delete", function (data) {
                Feng.success("删除成功!");
                ProDataNumber.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            console.log("删除id：",ids);
            ajax.set("idList", ids);
            ajax.start();
            layer.close(index);
        });
    }
};
/**
 * 查询参数
 */
ProDataNumber.formParams = function () {
    var queryData = {};
    queryData['projectName'] = $("#projectName").val();
    queryData['dataNumber'] = $("#dataNumber").val();
    return queryData;
};

/**
 * 查询列表
 */
ProDataNumber.search = function () {
    var queryData = {};
    queryData['projectName'] = $("#projectName").val();
    queryData['dataNumber'] = $("#dataNumber").val();
    ProDataNumber.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ProDataNumber.initColumn();
    var table = new BSTable(ProDataNumber.id, "/prodataNumber/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(ProDataNumber.formParams());
    table.onDblClickRow = ProDataNumber.openProdataNumberDetail;
    ProDataNumber.table = table.init();
});
