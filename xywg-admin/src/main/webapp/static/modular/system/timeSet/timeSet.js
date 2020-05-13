/**
 * 时间设置管理初始化
 */
var TimeSet = {
    id: "TimeSetTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TimeSet.initColumn = function () {
    return [
        {field: 'selectItem', check: true},
            {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
            {title: '类型', field: 'typeName', visible: true, align: 'center', valign: 'middle'},
            {title: '上班时间', field: 'start', visible: true, align: 'center', valign: 'middle'},
            {title: '下班时间', field: 'end', visible: true, align: 'center', valign: 'middle'},
            {title: '创建人', field: 'createUser', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
            {title: '是否启用', field: 'statusName', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
TimeSet.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TimeSet.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加时间设置
 */
TimeSet.openAddTimeSet = function () {
    var index = layer.open({
        type: 2,
        title: '添加时间设置',
        area: ['800px', '520px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/timeSet/timeSet_add'
    });
    this.layerIndex = index;
};

/**
 * 打开编辑时间设置画面
 */
TimeSet.openTimeSetDetail = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    if(selected.length>1){
        Feng.info("只能选择一条记录！");
        return;
    }
    if (this.check()) {
        if(selected[0].isGeneralContractorOperation === 0){
            Feng.info("只有总包公司具有操作权限");
            return;
        }
        var index = layer.open({
            type: 2,
            title: '时间设置修改',
            area: ['600px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/timeSet/timeSet_update/' + TimeSet.seItem.id
        });
        //2019-04-29 jln add 打开画面默认最大化
        layer.full(index);
        this.layerIndex = index;
    }
};

/**
 * 双击查看
 */
TimeSet.searchInfo = function () {
    if (TimeSet.check()) {
        var index = layer.open({
            type: 2,
            title: '时间设置详情',
            area: ['800px', '520px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/timeSet/timeSet_view/' + TimeSet.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 启用
 */
TimeSet.enable = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        if(selected[0].isGeneralContractorOperation === 0){
            Feng.info("只有总包公司具有操作权限");
            return;
        }
        layer.confirm('确认启用？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            var ids = "";
            for (var i = 0; i < selected.length; i++) {
                ids += selected[i].id + ","
            }
            ids = ids.substring(0, ids.length - 1);
            var ajax = new $ax(Feng.ctxPath + "/timeSet/disable", function (data) {
                Feng.success("操作成功!");
                TimeSet.table.refresh();
            }, function (data) {
                Feng.error("操作失败!" + data.responseJSON.message + "!");
            });
            ajax.set("ids", ids);
            ajax.set("status",1);
            ajax.start();
            layer.close(index);
        });
    }
};

/**
 * 禁用
 */
TimeSet.disable = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        if(selected[0].isGeneralContractorOperation === 0){
            Feng.info("只有总包公司具有操作权限");
            return;
        }
        layer.confirm('确认禁用？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            var ids = "";
            for (var i = 0; i < selected.length; i++) {
                ids += selected[i].id + ","
            }
            ids = ids.substring(0, ids.length - 1);
            var ajax = new $ax(Feng.ctxPath + "/timeSet/disable", function (data) {
                Feng.success("操作成功!");
                TimeSet.table.refresh();
            }, function (data) {
                Feng.error("操作失败!" + data.responseJSON.message + "!");
            });
            ajax.set("ids", ids);
            ajax.set("status",0);
            ajax.start();
            layer.close(index);
        });
    }
};

/**
 * 查询时间设置列表
 */
TimeSet.search = function () {
    var queryData = {};
    queryData['projectName'] = $("#projectCode").val();
    TimeSet.table.refresh({query: queryData});
};

$(function () {
    Feng.initChosen();
    var defaultColunms = TimeSet.initColumn();
    var table = new BSTable(TimeSet.id, "/timeSet/list", defaultColunms);
    table.setPaginationType("server");
    table.onDblClickRow = TimeSet.searchInfo;//双击事件所对应的方法 要放在init之前
    TimeSet.table = table.init();
});
