/**
 * 企业资质管理初始化
 */
var SubContractorCertifications = {
    id: "SubContractorCertificationsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SubContractorCertifications.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '系统编号', field: 'sysNo', visible: true, align: 'center', valign: 'middle'},
            {title: '企业组织机构代码', field: 'organizationCode', visible: true, align: 'center', valign: 'middle'},
            {title: '证书类型', field: 'certificationType', visible: true, align: 'center', valign: 'middle'},
            {title: '证书名称', field: 'certificationName', visible: true, align: 'center', valign: 'middle'},
            {title: '证书编号', field: 'certificationCode', visible: true, align: 'center', valign: 'middle'},
            {title: '证书有效时间(起)', field: 'validBeginDate', visible: true, align: 'center', valign: 'middle'},
            {title: '最近发放日期', field: 'recentValidDate', visible: true, align: 'center', valign: 'middle'},
            {title: '证书有效时间(止)', field: 'validEndDate', visible: true, align: 'center', valign: 'middle'},
            {title: '发证机关', field: 'grantOrg', visible: true, align: 'center', valign: 'middle'},
            {title: '资质状态  1:有效2：注销3：过期4：降级', field: 'qualificationStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '资质证书状态1:有效2：注销3：暂扣4：过期', field: 'certificationStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '是否删除 1：是 0:否', field: 'isDel', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SubContractorCertifications.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SubContractorCertifications.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加企业资质
 */
SubContractorCertifications.openAddSubContractorCertifications = function () {
    var index = layer.open({
        type: 2,
        title: '添加企业资质',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/subContractorCertifications/subContractorCertifications_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看企业资质详情
 */
SubContractorCertifications.openSubContractorCertificationsDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '企业资质详情',
            area: ['80%', '80%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/subContractorCertifications/subContractorCertifications_update/' + SubContractorCertifications.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除企业资质
 */
SubContractorCertifications.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/subContractorCertifications/delete", function (data) {
            Feng.success("删除成功!");
            SubContractorCertifications.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("subContractorCertificationsId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询企业资质列表
 */
SubContractorCertifications.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    SubContractorCertifications.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = SubContractorCertifications.initColumn();
    var table = new BSTable(SubContractorCertifications.id, "/subContractorCertifications/list", defaultColunms);
    table.setPaginationType("client");
    SubContractorCertifications.table = table.init();
});
