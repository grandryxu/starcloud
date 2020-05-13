/**
 * 项目培训详情管理初始化
 */
var ProjectTrainRecord = {
    id: "ProjectTrainRecordTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProjectTrainRecord.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '培训id', field: 'trainId', visible: true, align: 'center', valign: 'middle'},
            {title: '证件类型', field: 'idCardType', visible: true, align: 'center', valign: 'middle'},
        {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
            return Feng.hiddenIdCard(data);
        }},
    ];
};

/**
 * 检查是否选中
 */
ProjectTrainRecord.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProjectTrainRecord.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加项目培训详情
 */
ProjectTrainRecord.openAddProjectTrainRecord = function () {
    var index = layer.open({
        type: 2,
        title: '添加项目培训详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/projectTrainRecord/projectTrainRecord_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看项目培训详情详情
 */
ProjectTrainRecord.openProjectTrainRecordDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '项目培训详情详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/projectTrainRecord/projectTrainRecord_update/' + ProjectTrainRecord.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除项目培训详情
 */
ProjectTrainRecord.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/projectTrainRecord/delete", function (data) {
            Feng.success("删除成功!");
            ProjectTrainRecord.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("projectTrainRecordId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询项目培训详情列表
 */
ProjectTrainRecord.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ProjectTrainRecord.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ProjectTrainRecord.initColumn();
    var table = new BSTable(ProjectTrainRecord.id, "/projectTrainRecord/list", defaultColunms);
    table.setPaginationType("client");
    ProjectTrainRecord.table = table.init();
});
