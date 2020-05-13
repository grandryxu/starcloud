/**
 * 项目参建单位管理初始化
 */
var ProjectSubContractor = {
    id: "ProjectSubContractorTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

var ProjectSubContractor1 = {
    id: "ProjectSubContractorTable1",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
/*
ProjectSubContractor1.openAddSubContractor = function (id) {
    console.log("公司"+id)

    console.log("项目id"+$("#projectId").val())



}*/
/**
 * 初始化表格的列
 */
ProjectSubContractor1.initColumn1 = function () {
    return [
        {field: 'selectItem', checkbox: true, visible: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '企业名称', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '招标文件名', field: 'fileName', visible: true, align: 'center', valign: 'middle'},
        {title: '中标时间', field: 'date', visible: true, align: 'center', valign: 'middle'},
/*        {title: '添加到参建单位', field: ' ', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return"<a size='2' color='blue' onclick='ProjectSubContractor1.openAddSubContractor(\""+ row.id +"\")'>添加到参建单位</a>";
            }

        },*/
        /*{title: '进场时间', field: 'entryTime', visible: true, align: 'center', valign: 'middle'},
        {title: '退场时间', field: 'exitTime', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'},*/
        /*        {
                    title: '项目经理',
                    field: 'pmName',
                    visible: true,
                    align: 'center',
                    valign: 'middle',
                    formatter: function (val, row, index) {
                        /!*"<a href=''><i class='fa fa-remove text-red'></i></a>" +*!/
                        if (row.contractorType ===16) {
                            if(row.pmName!=null){
                                return "<a href='javascript:void(0)' projectSubContractorId=" + row.id + " ' onClick='setPm(this)'>" + row.pmName + "</a>";
                            }else{
                                return "<a href='javascript:void(0)' projectSubContractorId=" + row.id + " ' onClick='setPm(this)'>设置</a>";
                            }
                        }
                    }
                }*/
    ];
};
/**
 * 初始化表格的列
 */
ProjectSubContractor.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true, visible: true},
        {title: '企业名称1', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '参建类型', field: 'contractorTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '进场时间', field: 'entryTime', visible: true, align: 'center', valign: 'middle'},
        {title: '退场时间', field: 'exitTime', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '项目经理',
            field: 'pmName',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (val, row, index) {
                /*"<a href=''><i class='fa fa-remove text-red'></i></a>" +*/
                if (row.contractorType ===16) {
                    if(row.pmName!=null){
                        return "<a href='javascript:void(0)' projectSubContractorId=" + row.id + " ' onClick='setPm(this)'>" + row.pmName + "</a>";
                    }else{
                        return "<a href='javascript:void(0)' projectSubContractorId=" + row.id + " ' onClick='setPm(this)'>设置</a>";
                    }
                }
            }
        }
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
 * 检查是否选中
 */
ProjectSubContractor1.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        ProjectSubContractor1.seItem = selected[0];
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
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: encodeURI(Feng.ctxPath + '/projectSubContractor/projectSubContractor_add?projectCode=' + $("#projectCode").val() + "&projectName=" + $("#projectName").val() + "&page=" + $("#page").val()),
    });
    this.layerIndex = index;
};



/*从中标公司添加到参建单位*/
ProjectSubContractor1.openAddProjectSubContractor1 = function () {
    if (this.check()) {

        var  num=0;
        var ajax = new $ax(Feng.ctxPath + "/projectSubContractor/selectProjectCodeAndOrgCode", function (data) {
            num=data;
        }, function (data) {
            Feng.error("查询失败!" + data.responseJSON.message + "!");
        });
        ajax.set("projectCode",$("#projectCode").val());
        ajax.set("companyId",ProjectSubContractor1.seItem.id);
        ajax.start();


        if (num>0){
            Feng.error("此公司已添加到参建单位!");
            return false;
        }

    var index = layer.open({
        type: 2,
        title: '添加项目参建单位',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: encodeURI(Feng.ctxPath + '/projectSubContractor/projectSubContractor_add1?projectCode=' + $("#projectCode").val() + "&projectName=" + $("#projectName").val() + "&page=" + $("#page").val()+"&companyId="+ProjectSubContractor1.seItem.id),
    });
    this.layerIndex = index;
    }
};

/**
 * 打开查看项目参建单位详情
 */
ProjectSubContractor.openProjectSubContractorDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '项目参建单位详情',
            area: ['100%', '100%'], //宽高
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
        var str = "";
        if (e === 1) {
            str = "启用"
        } else {
            str = "禁用"
        }
        layer.confirm('确认要' + str + '吗？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            layer.closeAll();
            var selected = $('#' + ProjectSubContractor.id).bootstrapTable('getSelections');
            var ids = "";
            for (var i = 0; i < selected.length; i++) {
                ids += selected[i].id + ",";
            }
            ids = ids.substring(0, ids.length - 1);
            var ajax = new $ax(Feng.ctxPath + "/projectSubContractor/changeState", function (data) {
                    Feng.success("操作成功");
                    ProjectSubContractor.table.refresh();
                }, function (data) {
                    Feng.error("删除失败!" + data.responseJSON.message + "!");
                }
            );
            ajax.set("ids", ids);
            ajax.set("status", e);
            ajax.start();
        });

    }
};

/**
 * 删除项目参建单位
 */
ProjectSubContractor.delete = function () {
    if (this.check()) {
        if ($("#generalContractorCode").val() === this.seItem.organizationCode) {
            Feng.error("不能删除总承包单位！");
            return;
        }
        Feng.confirm("是否删除该参建单位？", function () {
            var ajax = new $ax(Feng.ctxPath + "/projectSubContractor/delete", function (data) {
                Feng.success("删除成功!");
                ProjectSubContractor.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("projectSubContractorId", ProjectSubContractor.seItem.id);
            ajax.start();
        });
    }
};

/**
 * 查询项目参建单位列表
 */
ProjectSubContractor.search = function () {
    var queryData = {};
    queryData['workKeys'] = $("#workKeys").val();
    queryData['condition'] = $("#condition").val();
    queryData['contractorType'] = $("#queryContractorType").val();
    queryData['startDate'] = $("#queryStartDate").val();
    queryData['endDate'] = $("#queryEndDate").val();
    queryData['startDate1'] = $("#queryStartDate1").val();
    queryData['endDate1'] = $("#queryEndDate1").val();
    ProjectSubContractor.table.refresh({query: queryData});
};

/**
 * 切换进退场
 */
ProjectSubContractor.changeJoinStatus = function (status) {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        layer.confirm('确认该操作？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            layer.closeAll();
            var organizationCodes = "";
            for (var i = 0; i < selected.length; i++) {
                organizationCodes += selected[i].organizationCode + ","
            }
            organizationCodes = organizationCodes.substring(0, organizationCodes.length - 1);
            var ajax = new $ax(Feng.ctxPath + "/projectSubContractor/toggleJoinStatus", function (data) {
                Feng.success("操作成功!");
                ProjectSubContractor.table.refresh();
            }, function (data) {
                Feng.error("操作失败!" + data.responseJSON.message + "!");
            });
            ajax.set({
                projectCode: $("#projectCode").val(),
                status: status,
                organizationCodes: organizationCodes
            });
            ajax.start();
            //ProjectSubContractor.table.refresh();
        });
    }

};

/**
 * 跳转到评价页面
 */
ProjectSubContractor.openProjectContractorEvaluation = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '评价',
            area: ['1200px', '600px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/projectSubContractor/projectSubContractor_evaluation/' + ProjectSubContractor.seItem.id
        });
        this.layerIndex = index;
    }
}


/**
 * 设置项目经理
 */
function setPm(val) {

    var id = $(val).attr("projectSubContractorId");
    var index = layer.open({
        type: 2,
        title: '设置项目经理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/projectSubContractor/projectMaster_setPm/' + id
    });
    this.layerIndex = index;
};


$(function () {

    var projectId=$("#projectId").val()

   /*  console.log(projectId)*/
    var defaultColunms = ProjectSubContractor1.initColumn1();
    var table = new BSTable(ProjectSubContractor1.id, "/lxProject/companyDbList/"+projectId, defaultColunms);

    table.setPaginationType("server");
    table.onDblClickRow = ProjectSubContractor1.searchInfo;
    table.onLoadSuccess = ProjectSubContractor1.onLoadSuccess;
    ProjectSubContractor1.table = table.init();

});
