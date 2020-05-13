/**
 * 项目信息管理初始化
 */
var ProjectMasterSetPm = {
    id: "ProjectMasterSetPmTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProjectMasterSetPm.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '姓名', field: 'employeeName', visible: true, align: 'center', valign: 'middle'},
        {title: '公司名称', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '性别', field: 'gender', visible: false, align: 'center', valign: 'middle'},
        {title: '手机号码', field: 'cellPhone', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ProjectMasterSetPm.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("只能选中表格中的某一记录！");
        return false;
    } else {
        ProjectMasterSetPm.seItem = selected[0];
        return true;
    }
};


/**
 * 设置项目经理
 */
ProjectMasterSetPm.setPm = function () {
    var id = $("#id").val();
    if (this.check()) {
        layer.confirm('确认将该人员设为该项目的项目经理？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            var ajax = new $ax(Feng.ctxPath + "/projectSubContractor/setPm", function (data) {
                Feng.success("设置成功!");
                window.parent.ProjectSubContractor.table.refresh();
                parent.layer.closeAll();
            }, function (data) {
                Feng.error("设置失败!" + data.responseJSON.message + "!");
            });
            ajax.set({
                "pmName": ProjectMasterSetPm.seItem.employeeName,
                "idCardNumber": ProjectMasterSetPm.seItem.idCardNumber,
                "idCardType": ProjectMasterSetPm.seItem.idCardType,
                "cellPhone": ProjectMasterSetPm.seItem.cellPhone,
                "id": id
            });
            ajax.start();

            layer.close(index);
        });
    }
};
/**
 * 查询项目信息列表
 */
ProjectMasterSetPm.search = function () {
    var queryData = {};
    queryData['key'] = $("#key").val();
    ProjectMasterSetPm.table.refresh({query: queryData});
};


ProjectMasterSetPm.formParams = function () {
    var queryData = {};
    queryData['key'] = $("#key").val();
    return queryData;
}


ProjectMasterSetPm.onLoadSuccess = function (data) {
    ProjectMasterSetPm.data = data.rows;
}
$(function () {
    Feng.initChosen();
    var defaultColunms = ProjectMasterSetPm.initColumn();
    var table = new BSTable(ProjectMasterSetPm.id, "/employeeMaster/pmList", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(ProjectMasterSetPm.formParams())
    ProjectMasterSetPm.table = table.init();
});
