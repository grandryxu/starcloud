/**
 * 项目工人管理初始化
 */
var ProjectWorker = {
    id: "ProjectWorkerTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProjectWorker.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '项目编号', field: 'projectCode', visible: true, align: 'center', valign: 'middle'},
            {title: '所属企业组织机构代码', field: 'organizationCode', visible: true, align: 'center', valign: 'middle'},
            {title: '班组编号', field: 'teamSysNo', visible: true, align: 'center', valign: 'middle'},
            {title: '证件类型 参见人员证件类型字典表', field: 'idCardType', visible: true, align: 'center', valign: 'middle'},
        {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
            return Feng.hiddenIdCard(data);
        }},
            {title: '安全帽', field: 'shImei', visible: true, align: 'center', valign: 'middle'},
            {title: '状态 1:待进场  2：进场  3：退场', field: 'joinStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '当前工种 参见工种字典表中工种编号', field: 'workTypeCode', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号码 工人当前手机号码', field: 'cellPhone', visible: true, align: 'center', valign: 'middle'},
            {title: '制卡时间', field: 'issueCardTime', visible: true, align: 'center', valign: 'middle'},
            {title: '工人进场时间', field: 'entryTime', visible: true, align: 'center', valign: 'middle'},
            {title: '工人退场时间', field: 'exitTime', visible: true, align: 'center', valign: 'middle'},
            {title: '销卡时间', field: 'completeCardTime', visible: true, align: 'center', valign: 'middle'},
            {title: '门禁卡号', field: 'cardNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '门禁卡类型 卡的类型', field: 'cardType', visible: true, align: 'center', valign: 'middle'},
            {title: '是否有劳动合同 0=无，1=有是否与劳务企业签有劳动合同', field: 'hasContract', visible: true, align: 'center', valign: 'middle'},
            {title: '工人劳动合同编号', field: 'contractCode', visible: true, align: 'center', valign: 'middle'},
            {title: '工人住宿类型 0=场外住宿,1=场内住宿', field: 'workerAccommodationType', visible: true, align: 'center', valign: 'middle'},
            {title: '工人角色', field: 'workerRole', visible: true, align: 'center', valign: 'middle'},
            {title: '工资银行卡号', field: 'payRollBankCardNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '发放工资银行名称', field: 'payRollBankName', visible: true, align: 'center', valign: 'middle'},
            {title: '有无购买工伤或意外伤害保险', field: 'hasBuyInsurance', visible: true, align: 'center', valign: 'middle'},
            {title: '添加人', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
            {title: '添加时间', field: 'createUser', visible: true, align: 'center', valign: 'middle'},
            {title: '修改时间', field: 'updateDate', visible: true, align: 'center', valign: 'middle'},
            {title: '修改人', field: 'updateUser', visible: true, align: 'center', valign: 'middle'},
            {title: '评价', field: 'evaluate', visible: true, align: 'center', valign: 'middle'},
            {title: '是否删除', field: 'isDel', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ProjectWorker.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProjectWorker.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加项目工人
 */
ProjectWorker.openAddProjectWorker = function () {
    var index = layer.open({
        type: 2,
        title: '添加项目工人',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/projectWorker/projectWorker_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看项目工人详情
 */
ProjectWorker.openProjectWorkerDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '项目工人详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/projectWorker/projectWorker_update/' + ProjectWorker.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除项目工人
 */
ProjectWorker.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/projectWorker/delete", function (data) {
            Feng.success("删除成功!");
            ProjectWorker.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("projectWorkerId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询项目工人列表
 */
ProjectWorker.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ProjectWorker.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ProjectWorker.initColumn();
    var table = new BSTable(ProjectWorker.id, "/projectWorker/list", defaultColunms);
    table.setPaginationType("client");
    ProjectWorker.table = table.init();
});
