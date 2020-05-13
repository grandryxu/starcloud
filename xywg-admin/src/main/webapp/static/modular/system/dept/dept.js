/**
 * 部门管理初始化
 */
var Dept = {
    id: "DeptTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Dept.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', align: 'center', valign: 'middle', width: '50px'},
        {title: '部门简称', field: 'simplename', align: 'center', valign: 'middle', sortable: true},
        {title: '部门全称', field: 'fullname', align: 'center', valign: 'middle', sortable: true},
        {title: '信用代码', field: 'socialCreditNumber', align: 'center', valign: 'middle', sortable: true},
        {title: '排序', field: 'num', align: 'center', valign: 'middle', sortable: true},
        {title: '到期时间', field: 'endTime', align: 'center', valign: 'middle', sortable: true},
        {title: '备注', field: 'tips', align: 'center', valign: 'middle', sortable: true}];
};

/**
 * 检查是否选中
 */
Dept.check = function () {
    var selected = $('#' + this.id).bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Dept.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加部门
 */
Dept.openAddDept = function () {
    var index = layer.open({
        type: 2,
        title: '添加部门',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/dept/dept_add'
    });
    this.layerIndex = index;
    layer.full(index);
};

Dept.openAddDept1 = function () {
    var index = layer.open({
        type: 2,
        title: '添加部门',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/dept/dept_add_data'
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 打开查看企业信息详情
 */
Dept.openSubContractorDetail = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/dept/selectSubContractorIdByDeptId/"+Dept.seItem.id, function (data) {
            var index = layer.open({
                type: 2,
                title: '企业信息详情',
                area: ['100%', '100%'], //宽高
                fix: false, //不固定
                maxmin: true,
                skin: 'layer-no-title',
                content: Feng.ctxPath + '/subContractor/subContractor_update/' + data.subContractorId
            });
            this.layerIndex = index;
        }, function (data) {
            Feng.error("操作失败!" + data.responseJSON.message + "!");
        });
        ajax.start();
    }
};

/**
 * 打开查看部门详情
 */
Dept.openDeptDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '部门详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/dept/dept_update/' + Dept.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 设置有效期
 */
Dept.setTime = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '有效期设置',
            area: ['500px', '520px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/dept/dept_setTime/' + Dept.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除部门
 */
Dept.delete = function () {
    if (this.check()) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/dept/delete", function (data) {
                if (data.code == 600) {
                    Feng.error("删除失败!", data.message);
                    return;
                }
                Dept.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("deptId", Dept.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否刪除该部门?", operation);
    }
};

/**
 * 查询部门列表
 */
Dept.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Dept.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Dept.initColumn();
    var table = new BSTreeTable(Dept.id, "/dept/list", defaultColunms);
    table.setExpandColumn(2);
    //主节点ID
    var rootCodeValue = $('#rootDeptId').val();
    table.setRootCodeValue(rootCodeValue);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("pid");
    table.setExpandAll(true);
    table.init();
    Dept.table = table;
});
