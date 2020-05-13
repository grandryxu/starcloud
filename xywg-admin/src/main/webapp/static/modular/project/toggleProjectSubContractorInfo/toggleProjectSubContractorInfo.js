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
        {field: 'selectItem', checkbox: true, visible: true},
        {title: '企业名称', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
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
 * 点击添加项目参建单位
 */
ProjectSubContractor.openAddProjectSubContractor = function () {
    var index = layer.open({
        type: 2,
        title: '添加项目参建单位',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/projectSubContractor/projectSubContractor_add?projectCode=' + $("#projectCode").val() + "&projectName=" + $("#projectName").val() + "&page=" + $("#page").val(),
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
            var ids = "";
            for (var i = 0; i < selected.length; i++) {
                ids += selected[i].id + ","
            }
            ids = ids.substring(0, ids.length - 1);
            var ajax = new $ax(Feng.ctxPath + "/projectSubContractor/toggleJoinStatusWithIds", function (data) {
                Feng.success("操作成功!");
                ProjectSubContractor.table.refresh();
            }, function (data) {
                Feng.error("操作失败!" + data.responseJSON.message + "!");
            });
            ajax.set({
                ids:ids ,
                status:status
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
            area: ['800px', '420px'], //宽高
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


/**
 * 双击查看评价
 */
ProjectSubContractor.openConstructionEvaluateDetail = function (e) {
    var index = layer.open({
        type: 2,
        title: '企业详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/constructionEvaluate?organizationCode=' + e.organizationCode
    });
    this.layerIndex = index;
};



$(function () {
    var currDate = new Date();

    var queryStartDate = laydate.render({
        elem: '#queryStartDate',
        done: function (value, date) {
            if (value !== '') {
                queryEndDate.config.min.year = date.year;
                queryEndDate.config.min.month = date.month - 1;
                queryEndDate.config.min.date = date.date;
            } else {
                queryEndDate.config.min.year = '';
                queryEndDate.config.min.month = '';
                queryEndDate.config.min.date = '';
            }
        }
    });
    //设置结束时间
    var queryEndDate = laydate.render({
        elem: '#queryEndDate',
        done: function (value, date) {
            if (value !== '') {
                queryStartDate.config.max.year = date.year;
                queryStartDate.config.max.month = date.month - 1;
                queryStartDate.config.max.date = date.date;
            } else {
                queryStartDate.config.max.year = currDate.getFullYear();
                queryStartDate.config.max.month = currDate.getMonth()+1;
                queryStartDate.config.max.date =  currDate.getDate();
            }
        }
    });

    var queryStartDate1 = laydate.render({
        elem: '#queryStartDate1',
        done: function (value, date) {
            if (value !== '') {
                queryEndDate1.config.min.year = date.year;
                queryEndDate1.config.min.month = date.month - 1;
                queryEndDate1.config.min.date = date.date;
            } else {
                queryEndDate1.config.min.year = '';
                queryEndDate1.config.min.month = '';
                queryEndDate1.config.min.date = '';
            }
        }
    });
    //设置结束时间
    var queryEndDate1 = laydate.render({
        elem: '#queryEndDate1',
        done: function (value, date) {
            if (value !== '') {
                queryStartDate1.config.max.year = date.year;
                queryStartDate1.config.max.month = date.month - 1;
                queryStartDate1.config.max.date = date.date;
            } else {
                queryStartDate1.config.max.year = currDate.getFullYear();
                queryStartDate1.config.max.month = currDate.getMonth()+1;
                queryStartDate1.config.max.date =  currDate.getDate();
            }
        }
    });

    //重置的时间集合
    ProjectSubContractor.resetDate = [queryStartDate,queryEndDate,queryStartDate1,queryEndDate1];

    var defaultColunms = ProjectSubContractor.initColumn();
    var table = new BSTable(ProjectSubContractor.id, "/projectSubContractor/toggleList", defaultColunms);
    table.setPaginationType("server");
    table.onDblClickRow = ProjectSubContractor.openConstructionEvaluateDetail;//双击事件所对应的方法 要放在init之前
    ProjectSubContractor.table = table.init();
});


function  resetDate() {
    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = currentDate.getMonth()+1;
    var date = currentDate.getDate();

    for(var i=0;i<ProjectSubContractor.resetDate.length; i++) {
        var dateObject = ProjectSubContractor.resetDate[i];

        //去除最小时间限制
        dateObject.config.min.year = '';
        dateObject.config.min.month = '';
        dateObject.config.min.date = '';

        //去除最大时间限制
        dateObject.config.max.year = '9999';
        dateObject.config.max.month = '12';
        dateObject.config.max.date = '31';

        //设置当前时间
        dateObject.config.dateTime.year = year;
        dateObject.config.dateTime.month = month;
        dateObject.config.dateTime.date = date;
        // dateObject.startDate.config.dateTime.hours = '时';
        // dateObject.startDate.config.dateTime.minutes = '分';
        // dateObject.startDate.config.dateTime.seconds = '秒';
    }
}
