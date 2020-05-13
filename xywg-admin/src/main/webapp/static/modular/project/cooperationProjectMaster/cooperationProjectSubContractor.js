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
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '项目编号', field: 'projectCode', visible: true, align: 'center', valign: 'middle'},
            {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
            {title: '企业组织机构代码', field: 'organizationCode', visible: true, align: 'center', valign: 'middle'},
            {title: '企业名称', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
            {title: '参建类型', field: 'contractorTypeName', visible: true, align: 'center', valign: 'middle'},
            {title: '进场时间', field: 'entryTime', visible: true, align: 'center', valign: 'middle'},
            {title: '退场时间', field: 'exitTime', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'},
            {title: '发放工资的银行名称', field: 'bankName', visible: true, align: 'center', valign: 'middle'},
            {title: '发放工资的共管银行账户', field: 'bankNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '银行联号', field: 'bankLinkNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '工资发放模式', field: 'payMode', visible: true, align: 'center', valign: 'middle'},
            {title: '项目经理名称', field: 'pmName', visible: true, align: 'center', valign: 'middle'},
            {title: '身份类型', field: 'pmIdcardType', visible: true, align: 'center', valign: 'middle'},
            {title: '身份证号码', field: 'pmIdcardNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '项目经理电话', field: 'pmPhone', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ProjectSubContractor.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
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
        content: Feng.ctxPath + '/projectSubContractor/projectSubContractor_add?projectCode='+$("#projectCode").val() +"&projectName=" +$("#projectName").val()+"&pageType="+$("#pageType").val() ,
    });
    this.layerIndex = index;
};

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
ProjectSubContractor.changeState=function (e) {
    if(this.check()){
        var id=this.seItem.id;
        var ajax=new $ax(Feng.ctxPath+"/projectSubContractor/changeState",function (data) {
           Feng.success("操作成功");
            ProjectSubContractor.table.refresh();
        },function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
            }
        );
        ajax.set("id",id);
        ajax.set("status",e);
        ajax.start();
    }
};

/**
 * 删除项目参建单位
 */
ProjectSubContractor.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/projectSubContractor/delete", function (data) {
            Feng.success("删除成功!");
            ProjectSubContractor.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("projectSubContractorId",this.seItem.id);
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
    queryData['endDate'] =$("#endDate").val();
    queryData['startDate1'] = $("#startDate1").val();
    queryData['endDate1'] =$("#endDate1").val();
    ProjectSubContractor.table.refresh({query: queryData});
};

$(function () {

});
