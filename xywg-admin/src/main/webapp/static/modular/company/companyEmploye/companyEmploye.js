/**
 * 企业从业人员管理初始化
 */
var CompanyEmploye = {
    id: "CompanyEmployeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
CompanyEmploye.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '公司名称', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
            {title: '人员编号', field: 'employeSysNo', visible: true, align: 'center', valign: 'middle'},
            {title: '人员身份证', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '岗位职务', field: 'job', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'jobStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '入职日期', field: 'hireDate', visible: true, align: 'center', valign: 'middle'},
            {title: '离职日期', field: 'terminationDate', visible: true, align: 'center', valign: 'middle'},
            {title: '备注信息', field: 'remark', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
CompanyEmploye.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        CompanyEmploye.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加企业从业人员
 */
CompanyEmploye.openAddCompanyEmploye = function () {
    var index = layer.open({
        type: 2,
        title: '添加企业从业人员',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/companyEmploye/companyEmploye_add'
    });
    this.layerIndex = index;
};

/**
 * 点击添加2
 */
CompanyEmploye.openAddCompanyEmploye2 = function () {
    var index = layer.open({
        type: 2,
        title: '添加2',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/companyEmploye/add_new2'
    });
    this.layerIndex = index;
};

/**
 * 打开查看企业从业人员详情
 */
CompanyEmploye.openCompanyEmployeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '企业从业人员详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/companyEmploye/companyEmploye_update/' + CompanyEmploye.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除企业从业人员
 */
CompanyEmploye.delete = function () {
    if (this.check()) {
        layer.confirm('确认删除？', {
            btn: ['确定','取消'] //按钮
        }, function(index){
            var ajax = new $ax(Feng.ctxPath + "/companyEmploye/delete", function (data) {
                Feng.success("删除成功!");
                CompanyEmploye.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("companyEmployeId",CompanyEmploye.seItem.id);
            ajax.start();

            layer.close(index);
        });
    }
};

/**
 * 查询企业从业人员列表
 */
CompanyEmploye.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    CompanyEmploye.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = CompanyEmploye.initColumn();
    var table = new BSTable(CompanyEmploye.id, "/companyEmploye/list", defaultColunms);
    table.setPaginationType("client");
    CompanyEmploye.table = table.init();
});
