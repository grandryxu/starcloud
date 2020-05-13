/**
 * 企业信息管理初始化
 */
var SubContractor = {
    id: "SubContractorTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SubContractor.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '企业名称', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '单位性质', field: 'organizationTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '组织机构编号', field: 'organizationCode', visible: true, align: 'center', valign: 'middle'},
        {title: '法人', field: 'representativeName', visible: true, align: 'center', valign: 'middle'},
        {title: '注册地区编码', field: 'areaCode', visible: true, align: 'center', valign: 'middle'},
        {title: '注册资本（万）', field: 'registrationCapital', visible: true, align: 'center', valign: 'middle'},
        {title: '联系人姓名', field: 'contactPeopleName', visible: false, align: 'center', valign: 'middle'},
        {title: '联系人手机号码', field: 'contactPeopleCellPhone', visible: false, align: 'center', valign: 'middle'},
        {title: '企业联系邮箱', field: 'email', visible: true, align: 'false', valign: 'middle'},
        {title: '企业经营状态', field: 'businessStatusName', visible: true, align: 'center', valign: 'middle'},
        {title: '企业网址', field: 'homeWebsiteUrl', visible: false, align: 'center', valign: 'middle'},
        {title: '成立日期', field: 'establishDate', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'},
        {title: '备注', field: 'memo', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SubContractor.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        SubContractor.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加企业信息
 */
SubContractor.openAddSubContractor = function () {
    var index = layer.open({
        type: 2,
        title: '添加企业信息',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/cooperationSubContractor/cooperationSubContractor_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看企业信息详情
 */
SubContractor.openSubContractorDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '企业信息详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/cooperationSubContractor/cooperationSubContractor_update/' + SubContractor.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 检查是否选中
 */
SubContractor.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        SubContractor.seItem = selected[0];
        return true;
    }
};

/**
 * 切换状态
 */
SubContractor.changeState = function (e) {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        var ids = "";
        for (var i = 0; i < selected.length; i++) {
            ids += selected[i].id + ","
        }
        ids = ids.substring(0, ids.length - 1);
        var ajax = new $ax(Feng.ctxPath + "/cooperationSubContractor/changeState", function (data) {
                Feng.success("操作成功");
                SubContractor.table.refresh();
            }, function (data) {
                Feng.error("操作失败" + data.responseJSON.message + "!");
            }
        );
        ajax.set("status", e);
        ajax.set("ids", ids);
        ajax.start();
    }
}

/**
 * 删除企业信息
 */
SubContractor.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/cooperationSubContractor/delete", function (data) {
            Feng.success("删除成功!");
            SubContractor.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("subContractorId", this.seItem.id);
        ajax.start();
    }
};


/**
 * 切换进退场
 */
SubContractor.changeJoinStatus = function (status) {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        var organizationCodes = "";
        for (var i = 0; i < selected.length; i++) {
            organizationCodes += selected[i].organizationCode + ","
        }
        organizationCodes = organizationCodes.substring(0, organizationCodes.length - 1);
        var ajax = new $ax(Feng.ctxPath + "/projectSubContractor/toggleJoinStatus", function (data) {
            Feng.success("操作成功!");
            SubContractor.table.refresh();
        }, function (data) {
            Feng.error("操作失败!" + data.responseJSON.message + "!");
        });
        ajax.set({
            projectCode: $("#projectCode").val(),
            status: status,
            organizationCodes: organizationCodes
        });
        ajax.start();

    }

};

/**
 * 查询企业信息列表
 */
SubContractor.search = function () {
    var queryData = {};
    queryData.condition = $("#condition").val();
    queryData.organizationType = $("#organizationType").val();
    queryData.businessStatus = $("#businessStatus").val();
    queryData.startDate = $("#startDate").val();
    queryData.endDate = $("#endDate").val();
    SubContractor.table.refresh({query: queryData});
};

/**
 * 双击查看
 */
SubContractor.searchInfo = function (e) {
    var index = layer.open({
        type: 2,
        title: '企业信息详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/cooperationSubContractor/cooperationSubContractor_view/' + e.id
    });
    this.layerIndex = index;
}


$(function () {
    Feng.initStartEndDate();
    var defaultColunms = SubContractor.initColumn();
    var table = new BSTable(SubContractor.id, "/cooperationSubContractor/list", defaultColunms);
    table.setPaginationType("server");
    table.onDblClickRow = SubContractor.searchInfo;//双击事件所对应的方法 要放在init之前
    SubContractor.table = table.init();
});
