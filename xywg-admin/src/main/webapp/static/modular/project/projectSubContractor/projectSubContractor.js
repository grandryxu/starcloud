/**
 * 项目参建单位管理初始化
 */
var ProjectSubContractor = {
    id: "ProjectSubContractorTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProjectSubContractor.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true ,visible: true},
        {title: '企业名称', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '参建类型', field: 'contractorTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '进场时间', field: 'entryTime', visible: true, align: 'center', valign: 'middle'},
        {title: '退场时间', field: 'exitTime', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目经理', field: 'pmName', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ProjectSubContractor.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        ProjectSubContractor.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加项目参建单位
 */
ProjectSubContractor.openAddProjectSubContractor = function () {
    var index = layer.open({
        type: 2,
        title: '添加项目参建单位',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-detail',
        content: encodeURI(Feng.ctxPath + '/projectSubContractor/projectSubContractor_add?projectCode=' + $("#projectCode").val() + "&projectName=" + $("#projectName").val() +"&pageType="+$("#pageType").val())
    });
    layer.full(index);
    this.layerIndex = index;
};

/**
 * 评价页面
 */
ProjectSubContractor.openProjectContractorEvaluation = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '评价',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + 'projectSubContractor/projectSubContractor_evaluation/' + ProjectSubContractor.seItem.id
        });
        this.layerIndex = index;
    }
}

/**
 * 打开查看项目参建单位详情
 */
ProjectSubContractor.openProjectSubContractorDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '项目参建单位详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/projectSubContractor/projectSubContractor_update/' + ProjectSubContractor.seItem.id
        });
        this.layerIndex = index;
    }
};


/**
 * 切换状态
 */
ProjectSubContractor.changeState = function (e) {
    if (this.check()) {
        var id = this.seItem.id;
        var ajax = new $ax(Feng.ctxPath + "/projectSubContractor/changeState", function (data) {
                Feng.success("操作成功");
                ProjectSubContractor.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            }
        );
        ajax.set("id", id);
        ajax.set("status", e);
        ajax.start();
    }
};

/**
 * 删除项目参建单位
 */
ProjectSubContractor.delete = function () {
    if (this.check()) {
        if(this.seItem.contractorType === 16){
            Feng.error("不能删除承包商");
            return;
        }
        if(this.seItem.contractorType === 13 && this.seItem.createOrganizationCode !== $("#loginOrganizationCode").val()){
            Feng.error("只能删除由本公司创建的参建单位!");
            return;
        }//只能删除自己创建的劳务分包
        else if(this.seItem.contractorType !== 13 && this.seItem.createOrganizationCode !== $("#loginOrganizationCode").val()){
            Feng.error("只能删除由本公司创建的劳务分包!");
            return;
        }
        else if(this.seItem.contractorType === 8 && this.seItem.organizationCode !== $("#loginOrganizationCode").val()){
            Feng.error("只能删除专业分包为自己的参建单位!");
            return;
        }else if(this.seItem.contractorType !== 8 && this.seItem.contractorType !== 13){
            Feng.error("只能删除专业分包为自己或由本公司创建的参建单位!");
            return;
        }
        Feng.confirm("是否删除该参建单位？",function(){
            var ajax = new $ax(Feng.ctxPath + "/projectSubContractor/delete", function (data) {
                console.log(123);
                Feng.success("删除成功!");
                if(ProjectSubContractor.seItem.organizationCode === $("#loginOrganizationCode").val() ) {
                    parent.layer.closeAll();
                    parent.pageToggle($("#pageType").val());
                }else{
                    ProjectSubContractor.table.refresh();
                }
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("projectSubContractorId", ProjectSubContractor.seItem.id);
            ajax.start();
        });
    }
};


/**
 * 删除项目参建单位 承包项目 参建单位 删除按钮
 */
ProjectSubContractor.deleteByProjectCodeAndOrganizationCodes = function () {
    var selected = $('#SubContractorTable').bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        var organizationCodes = "";
        for (var i = 0; i < selected.length; i++) {
            organizationCodes += selected[i].organizationCode + ",";
        }
        organizationCodes = organizationCodes.substring(0, organizationCodes.length - 1);
        var ajax = new $ax(Feng.ctxPath + "/projectSubContractor/deleteByProjectCodeAndOrganizationCodes", function (data) {
            Feng.success("删除成功!");
            SubContractor.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set({
            projectCode: $("#projectCode").val(),
            organizationCodes: organizationCodes
        });
        ajax.start();

    }

};

/**
 * 查询项目参建单位列表
 */
ProjectSubContractor.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['projectStatus'] = $("#projectStatus").val();
    queryData['startDate'] = $("#startDate").val();
    queryData['endDate'] = $("#endDate").val();
    queryData['startDate1'] = $("#startDate1").val();
    queryData['endDate1'] = $("#endDate1").val();
    ProjectSubContractor.table.refresh({query: queryData});
};

/**
 * 双击查看
 */
ProjectSubContractor.searchInfo = function (e) {
    var index = layer.open({
        type: 2,
        title: '企业信息详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/projectSubContractor/projectSubContractor_view/' + e.id
    });
    this.layerIndex = index;
}


$(function () {
    var defaultColunms = ProjectSubContractor.initColumn();
    var table = new BSTable(ProjectSubContractor.id, "/projectSubContractor/list?projectCode="+$("#projectCode").val() , defaultColunms);
    table.setPaginationType("server");
    table.onDblClickRow = ProjectSubContractor.searchInfo;//双击事件所对应的方法 要放在init之前
    ProjectSubContractor.table = table.init();
});
