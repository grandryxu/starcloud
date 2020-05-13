/**
 * 项目信息管理初始化
 */
var ProjectMaster = {
    id: "ProjectMasterTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1 ,
    //被查询的公司
    queryDeptId: null
};



/**
 * 初始化表格的列
 */
ProjectMaster.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '参建公司名称', field: 'cCompanyName', visible: true, align: 'center', valign: 'middle'},
        {title: '承包单位名称', field: 'generalContractorName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目分类', field: 'projectCategoryName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '开工时间', field: 'startDate', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return value.substring(0, 10);
            }
        },
        {
            title: '竣工时间', field: 'completeDate', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return value.substring(0, 10);
            }
        },
        {title: '项目状态', field: 'projectStatusName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '关联状态', field: 'isRelation', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === '0') {
                    return "<span style='color: deepskyblue'>未关联</span>";
                } else if (value === '1') {
                    return "<span style='color: slategray'>已关联</span>";
                }
            }
        },
        {
            title: '是否默认', field: 'isDefault', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === '0') {
                    return "<span style='color: slategray'>否</span>";
                } else if (value === '1') {
                    return "<span style='color: slategray'>是</span>";
                }
            }
        }
    ];
};
/**
 * 查询搜索
 */
ProjectMaster.search = function () {
    var queryData = {};
    queryData['projectName'] = $("#projectName").val();
    queryData['isRelation'] = $("#isRelation").val();
    queryData['deptId'] = ProjectMaster.queryDeptId;
    ProjectMaster.table.refresh({query: queryData});
};

/**
 * 检查是否选中
 */
ProjectMaster.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        ProjectMaster.seItem = selected[0];
        return true;
    }
};

/**
 * 关联
 */
ProjectMaster.relation = function () {
    if (this.check()) {
        var selected = $('#' + ProjectMaster.id).bootstrapTable('getSelections');
        var accountProjects = [];
        for (var i = 0; i < selected.length; i++) {
            if(selected[i].isRelation === '1'){
                Feng.error(selected[i].projectName + "已关联,请勿重复关联!")
                return;
            }
            accountProjects.push({
                account:$("#account").val(),
                projectCode:selected[i].projectCode ,
                organizationCode: selected[i].cOrganizationCode
            });
        }
        Feng.confirm("是否确认关联？",function(){
            var ajax = new $ax(Feng.ctxPath + "/accountProject/add", function (data) {
                Feng.success("关联成功!");
                ProjectMaster.table.refresh();
            }, function (data) {
                Feng.error("关联失败!" + data.responseJSON.message + "!");
            });
            ajax.set({list:JSON.stringify(accountProjects)});
            ajax.start();
        });
    }
}

/**
 *  解除关联
 */
ProjectMaster.delete = function () {
    if (this.check()) {
        var selected = $('#' + ProjectMaster.id).bootstrapTable('getSelections');
        var accountProjects = [];
        for (var i = 0; i < selected.length; i++) {
            if(selected[i].isRelation === '0'){
                Feng.error(selected[i].projectName + "未关联,无法解除关联!")
                return;
            }
            accountProjects.push({
                account:$("#account").val(),
                projectCode:selected[i].projectCode ,
                organizationCode: selected[i].cOrganizationCode
            });
        }
        Feng.confirm("是否解除关联？", function () {
            var ajax = new $ax(Feng.ctxPath + "/accountProject/delete", function (data) {
                Feng.success("解除关联成功!");
                ProjectMaster.table.refresh();
            }, function (data) {
                Feng.error("解除关联失败!" + data.responseJSON.message + "!");
            });
            ajax.set({list:JSON.stringify(accountProjects)});
            ajax.start();
        });
    }
}

/**
 * tree的点击事件
 * @param e
 * @param treeId
 * @param treeNode
 */
ProjectMaster.onClickDept = function (e, treeId, treeNode) {

};

$(function () {
    ProjectMaster.queryDeptId = $("#userDeptId").val();
    var defaultColunms = ProjectMaster.initColumn();
    var table = new BSTable(ProjectMaster.id, "/cooperationProjectMaster/getAllList?account=" + $("#account").val(), defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams({deptId:ProjectMaster.queryDeptId});
    ProjectMaster.table = table.init();

    //加载tree
    var ajax = new $ax(Feng.ctxPath + "/dept/treeByDeptId", function (data) {
        var treeData = data;
        return $('#tree').treeview({data: treeData,onNodeSelected: function (e, o) {
            ProjectMaster.queryDeptId = o.id;
            ProjectMaster.search();
        }});
    }, function (data) {
    });
    ajax.set({deptId:ProjectMaster.queryDeptId});
    ajax.start();
});

/**
 * 设为默认
 */
ProjectMaster.setDefault = function () {
    var selected = $('#' + ProjectMaster.id).bootstrapTable('getSelections');
    if(selected.length < 1){
    	Feng.error( "请选择一条数据!")
        return;
    }
    if(selected.length > 1){
    	Feng.error( "只能选择一条数据!")
        return;
    }
    if(selected[0].isDefault === '1'){
    	Feng.error( "该项目已经是默认项目!")
        return;
    }
    
    Feng.confirm("是否确认设为默认？",function(){
        var ajax = new $ax(Feng.ctxPath + "/accountProject/setDefault", function (data) {
            Feng.success("设置成功!");
            ProjectMaster.table.refresh();
        }, function (data) {
            Feng.error("设置失败!" + data.responseJSON.message + "!");
        });
        ajax.set({account:$("#account").val(),
            projectCode:selected[0].projectCode ,
            organizationCode: selected[0].cOrganizationCode,
            isRelation :selected[0].isRelation});
        ajax.start();
    });
    
}
