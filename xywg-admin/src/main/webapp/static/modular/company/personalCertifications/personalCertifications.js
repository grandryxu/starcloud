/**
 * 人员资格证书管理初始化
 */
var PersonalCertifications = {
    id: "PersonalCertificationsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PersonalCertifications.initColumn = function () {
    return [
            {field: 'selectItem', radio: true},
            {title: '姓名', field: 'employeeName', visible: true, align: 'center', valign: 'middle'},
            {title: '证件类型', field: 'idCardTypeName', visible: true, align: 'center', valign: 'middle'},
            {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
                return Feng.hiddenIdCard(data);
            }},
            {title: '证书类型', field: 'certificationTypeCodeName', visible: true, align: 'center', valign: 'middle'},
            {title: '专业编码', field: 'professionCode', visible: true, align: 'center', valign: 'middle'},
            {title: '类别/工种 职称证书', field: 'jobTypeName', visible: true, align: 'center', valign: 'middle'},
            {title: '证书等级', field: 'certificationLevelType', visible: true, align: 'center', valign: 'middle'},
            {title: '证书名称', field: 'certificationName', visible: true, align: 'center', valign: 'middle'},
            {title: '证书有效起始日期', field: 'validBeginDate', visible: true, align: 'center', valign: 'middle'},
            {title: '证书有效截止日期', field: 'validEndDate', visible: true, align: 'center', valign: 'middle'},
            {title: '发证机关', field: 'issueBy', visible: true, align: 'center', valign: 'middle'},
            {title: '发证日期', field: 'issueDate', visible: true, align: 'center', valign: 'middle'},
            {title: '资格状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
PersonalCertifications.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PersonalCertifications.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加人员资格证书
 */
PersonalCertifications.openAddPersonalCertifications = function () {
    var index = layer.open({
        type: 2,
        title: '添加人员资格证书',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/personalCertifications/personalCertifications_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看人员资格证书编辑
 */
PersonalCertifications.openPersonalCertificationsDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '人员资格证书详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: 'layer-no-title',
            content: Feng.ctxPath + '/personalCertifications/personalCertifications_update/' + PersonalCertifications.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 双击查看
 */
PersonalCertifications.searchInfo = function () {
    if (PersonalCertifications.check()) {
        var index = layer.open({
            type: 2,
            title: '从业人员详情信息',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: 'layer-no-title',
            content: Feng.ctxPath + '/personalCertifications/personalCertifications_view/' + PersonalCertifications.seItem.id
        });
        this.layerIndex = index;
    }
}
/**
 * 删除人员资格证书
 */
PersonalCertifications.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/personalCertifications/delete", function (data) {
            Feng.success("删除成功!");
            PersonalCertifications.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("personalCertificationsId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询人员资格证书列表
 */
PersonalCertifications.search = function () {
    PersonalCertifications.table.refresh({query: PersonalCertifications.formParams()});
};

PersonalCertifications.formParams = function () {
    var queryData = {};
    queryData['employeeName'] = $("#employeeName").val();
    queryData['idCardNumber'] = $("#idCardNumber").val();
    queryData['certificationTypeCode'] = $("#certificationTypeCode").val();
    queryData['certificationName'] = $("#certificationName").val();
    return queryData;
}

$(function () {
    var defaultColunms = PersonalCertifications.initColumn();
    var table = new BSTable(PersonalCertifications.id, "/personalCertifications/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(PersonalCertifications.formParams());
    table.onDblClickRow = PersonalCertifications.searchInfo;//双击事件所对应的方法 要放在init之前
    PersonalCertifications.table = table.init();
});
