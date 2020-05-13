/**
 * 班组管理管理初始化
 */
var TeamMaster = {
    id: "TeamMasterTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 人员列表初始化
 */
var TeamMember = {
    id: "TeamMemberTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化班组表格的列
 */
TeamMaster.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '所属公司', field: 'companyName', visible: false, align: 'center', valign: 'middle',},
        {title: '班组编号', field: 'teamSysNo', visible: false, align: 'center', valign: 'middle'},
        {title: '班组编号', field: 'teamSysNo', visible: false, align: 'center', valign: 'middle'},
        {
            title: '班组名称', field: 'teamName', visible: true, align: 'center', valign: 'middle',
            formatter: function (data) {
                return "<span class='ddd' style='width: 100px;' title='" + data + "'>" + data + "</span>";
            }
        },
        {title: '班组长', field: 'teamLeader', visible: true, align: 'center', valign: 'middle'},
        {
            title: '操作', field: 'operate', visible: true, align: 'center', valign: 'middle',
            events: operateEvents1, formatter: operateFormatter
        }
    ];
};

/**
 * 初始化人员表格的列
 */
TeamMember.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '手机号', field: 'cellPhone', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型', field: 'idCardTypeName', visible: false, align: 'center', valign: 'middle'},
        {
            title: '证件编号',
            field: 'idCardNumber',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (data) {
                return Feng.hiddenIdCard(data);
            }
        },
        {title: '门禁卡号', field: 'cardNumber', visible: true, align: 'center', valign: 'middle'},
        {title: '当前工种', field: 'workTypeCodeName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '类型', field: 'teamWorkerType', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value === undefined) {
                    value = 0;
                }
                if (value == 1) {
                    value = "组员";
                    return "<span class='' data-index='" + index + "'>" + value + "</span>&nbsp;";
                } else if (value == 0) {
                    value = "组长";
                    return "<span class='text-danger' data-index='" + index + "'>" + value + "</span>";
                }
                return "无";
            }
        },
        {
            title: '进/退场', field: 'joinStatus', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if (value === null || value === '' || value === undefined) {
                    value = 0;
                }
                if (value == 3) {
                    value = "退场";
                    return "<span class='text-danger' data-index='" + index + "'>" + value + "</span>&nbsp;";
                } else if (value == 2) {
                    value = "进场";
                    return "<span class='text-success' data-index='" + index + "'>" + value + "</span>";
                } else if (value == 1) {
                    value = "待进场";
                    return "<span class='text-danger' data-index='" + index + "'>" + value + "</span>";
                }
                return "无";
            }
        }


    ];
};

/*function initTeamMember() {
    var defaultColunms1 =TeamMember.initColumn();
    var worktable = new BSTable(TeamMember.id, "/teamMaster/getMemberByTeamId", defaultColunms1);
    worktable.setPaginationType("server");
    worktable.onDblClickRow = TeamMember.searchInfo1;//双击
    TeamMember.table = worktable.init();
}*/


/**
 * 双击查看
 */
TeamMember.searchInfo1 = function (e) {
    console.log(e);
    var url = Feng.ctxPath + '/workerMaster/workerMaster_view/' + e.id;
    if (e.pwId) {
        url += "?pwId=" + e.pwId;
    }
    var index = layer.open({
        type: 2,
        title: '工人详情信息',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: url
    });
    this.layerIndex = index;
}

window.operateEvents1 = {
    'click #span_detail': function (e, value, row, index) {
        if (TeamMember.table) {
            $('#teamName').text(row.teamName);
            $('#teamSysNo').val(row.teamSysNo);
            TeamMember.table.refresh({query: {teamCode: row.teamSysNo}});
        }
    },
    'click #span_detele': function (e, value, row, index) {
        if (hasOperationAuth()) {
            if (row.isGeneralContractorOperation === 0) {
                Feng.error("您无权操作参建单位" + row.companyName + "的数据!");
                return;
            }
        }
        layer.confirm('是否确认删除?', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            layer.closeAll();
            var ajax = new $ax(Feng.ctxPath + "/teamMaster/delete", function (data) {
                if (data.code == 200) {
                    Feng.success("删除成功!");
                    TeamMaster.table.refresh();
                } else {
                    Feng.error(data.message);
                }

            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("teamMasterId", row.id);
            ajax.start();
        }, function () {

        });
    }
};

function operateFormatter(value, row, index) {
    return [
        '<span id="span_detail" class="span-but text-success" style="display:none">查看</span>',
        '<span id="span_detele" class="span-but text-danger">删除</span>'
    ].join('');
}

/**
 * 检查是否选中
 */
TeamMaster.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        TeamMaster.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加班组管理
 */
TeamMaster.openAddTeamMaster = function () {
	//2019-05-15 jln add 在人员管理画面增加添加班组按钮
	console.log(parent.WorkerMaster);
	if($("#projectCode").val() == "" || $("#projectCode").val() == null){
		Feng.info("请先选择项目！");
        return false;
	}
    var index = layer.open({
        type: 2,
        title: '添加班组管理',
        area: ['1000px', '520px'], //宽高
        fix: false, //不固定
        maxmin: false,
        content: Feng.ctxPath + '/teamMaster/teamMaster_add/' + $("#projectCode").val()
    });
    // layer.full(index);
    this.layerIndex = index;
};

/**
 * 评价页面
 */
TeamMaster.openProjectContractorEvaluation = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '评价',
            area: ['850px', '520px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/teamMaster/teamMaster_evaluation/' + TeamMaster.seItem.id
        });
        this.layerIndex = index;
    }
};


/**
 * 打开查看班组管理详情
 */
TeamMaster.openTeamMasterDetail = function () {
    if (this.check()) {
        if (hasOperationAuth()|| $("#page").val() === 'ZBXM') {
            if (TeamMaster.seItem.isGeneralContractorOperation === 0) {
                Feng.error("您无权操作参建单位" + TeamMaster.seItem.companyName + "的数据!");
                return;
            }
        }
        var index = layer.open({
            type: 2,
            title: '编辑班组',
            area: ['860px', '520px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/teamMaster/teamMaster_update/' + TeamMaster.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除班组管理
 */
TeamMaster.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/teamMaster/delete", function (data) {
            if (data.code == 200) {
                Feng.success("删除成功!");
            } else {
                Feng.error(data.message);
            }
            TeamMaster.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("teamMasterId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
TeamMaster.formParams = function () {
    var queryData = {};

    /*   queryData['teamCode'] = $("#teamCode").val();
       queryData['peojectCode'] = $("#peojectCode").val();
       queryData['peojectName'] = $("#peojectName").val();*/
    queryData['peojectCode'] = $("#c").val();
    return queryData;
}


/**
 * 双击查看
 */
TeamMaster.searchInfo = function (e) {
    var index = layer.open({
        type: 2,
        title: '班组信息详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-detail',
        content: Feng.ctxPath + '/teamMaster/teamMaster_info/' + e.id
    });
    layer.full(index);
    this.layerIndex = index;
};

TeamMaster.teamParams = function () {
    var queryData = {};

    queryData['teamName'] = $("#teamName").val();
    

    return queryData;
}
TeamMaster.searchTeam = function () {
	TeamMaster.table.refresh({query: TeamMaster.teamParams()});
};
/**
 * 查询班组管理列表
 */
TeamMaster.search = function () {
    TeamMaster.table.refresh({query: TeamMaster.formParams()});
};

TeamMaster.onOneClickRow = function (e, value, row, index) {
    if (TeamMember.table) {
        if (hasOperationAuth()|| $("#page").val() === 'ZBXM') {
            if (e.isGeneralContractorOperation === 0) {
                //项目级总包无操作权限
                $("#TeamMemberTableToolbar").hide();
            } else {
                $("#TeamMemberTableToolbar").show();
            }
        }
        $('#teamName').text(e.teamName);
        $('#teamSysNo').val(e.teamSysNo);
        TeamMember.table.refresh({query: {teamCode: e.teamSysNo}});
    }
}

$(function () {
    var projectStatus = $('#projectStatus').val();
    //停工/完工不能操作
    if (projectStatus == 4) {
        $('.ap-content button').hide();
        // $('.ap-content button').hidden();
    }

    var defaultColunms = TeamMaster.initColumn();
    var table = new BSTable(TeamMaster.id, "/teamMaster/list?projectCode=" + $("#projectCode").val()+"&page="+$("#page").val(), defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(TeamMaster.formParams());
    table.onDblClickRow = TeamMaster.searchInfo;//双击事件所对应的方法 要放在init之前
    table.onClickRow = TeamMaster.onOneClickRow;//单击事件所对应的方法 要放在init之前
    table.onCheck = TeamMaster.onOneClickRow;//单选
    TeamMaster.table = table.init();

   // initTeamMember();

    var defaultColunms1 =TeamMember.initColumn();
    var worktable = new BSTable(TeamMember.id, "/teamMaster/getMemberByTeamId", defaultColunms1);
    worktable.setPaginationType("server");
    worktable.onDblClickRow = TeamMember.searchInfo1;//双击
    TeamMember.table = worktable.init();
});
