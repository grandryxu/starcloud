/**
 * 班组成员管理初始化
 */
var TeamWorker = {
    id: "TeamMemberTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};


/**
 * 检查是否选中
 */
TeamWorker.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        TeamWorker.seItem = selected[0];
        return true;
    }
};

/**
 * 检查是否选中
 */
TeamWorker.checkOne = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("只能选择一条记录！");
        return false;
    } else {
        TeamWorker.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加成员
 */
TeamWorker.openAddTeamMember = function () {
    var index = layer.open({
        type: 2,
        title: '工人详情信息',
        area: ['1200px', '620px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/teamMaster/toAddMember/' + $("#teamCode").val()
    });
    this.layerIndex = index;
};

/**
 * 设置班组长
 */
TeamWorker.setTeamLeader = function () {
    if (this.checkOne()) {
        var ajax = new $ax(Feng.ctxPath + "/teamMaster/setTeamLeader", function (data) {
            Feng.success("操作成功!");
            TeamMember.table.refresh();
            TeamMaster.table.refresh();
        }, function (data) {
            Feng.error("操作失败!" + data.responseJSON.message + "!");
        });
        ajax.set("memberId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 删除班组成员
 */
TeamWorker.delete = function () {
    if (this.checkOne()) {
        var ajax = new $ax(Feng.ctxPath + "/teamMaster/deleteMember", function (data) {
            if (data.code == 200) {
                Feng.success("删除成功!");
            } else {
                Feng.error(data.message);
            }
            TeamMember.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("memberId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 成员进场
 */
TeamWorker.workerJoin = function () {
    if (this.check()) {
        var selected = $('#' + this.id).bootstrapTable('getSelections');
        var memberIds = "";
        for (var i = 0; i < selected.length; i++) {
            memberIds += selected[i].id + ",";
        }
        memberIds = memberIds.substring(0, memberIds.length - 1);
        var ajax = new $ax(Feng.ctxPath + "/teamMaster/workerJoin", function (data) {
            if (data.code == 200) {
                Feng.success("操作成功!");
            } else {
                Feng.error(data.message);
            }
            TeamMember.table.refresh();
        }, function (data) {
            Feng.error("操作失败!" + data.responseJSON.message + "!");
        });
        ajax.set("memberIds", memberIds);
        ajax.start();
    }
};

/**
 * 成员退场
 */
TeamWorker.workerOut = function () {
    if (this.check()) {
        var selected = $('#' + this.id).bootstrapTable('getSelections');
        var memberIds = "";
        for (var i = 0; i < selected.length; i++) {
            memberIds += selected[i].id + ",";
        }
        memberIds = memberIds.substring(0, memberIds.length - 1);
        var ajax = new $ax(Feng.ctxPath + "/teamMaster/workerOut", function (data) {
            if (data.code == 200) {
                Feng.success("操作成功!");
            } else {
                Feng.error(data.message);
            }
            TeamMember.table.refresh();
        }, function (data) {
            Feng.error("操作失败!" + data.responseJSON.message + "!");
        });
        ajax.set("memberIds", memberIds);
        ajax.start();
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
TeamWorker.formParams = function () {
    var queryData = {};

    queryData['teamCode'] = $("#teamCode").val();
    queryData['idCard'] = $("#idCard").val();
    queryData['name'] = $("#name").val();
    queryData['phone'] = $("#phone").val();

    return queryData;
}


/**
 * 查询班组管理列表
 */
TeamWorker.search = function () {
    TeamMember.table.refresh({query: TeamWorker.formParams()});
};

/**
 * 绑定安全帽
 */
TeamWorker.bindHelmet = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length !== 1) {
        Feng.info("请先选中表格中的一条记录！");
        return false;
    }
    var helmets = '<input type="text" class="input-search-s"style="width: 100%;text-align: center;" id="helmetSearch" placeholder="请输入序列号或者名称搜索" onkeyup="TeamWorker.initHelmets($(this).val())"/>'
        + '<ul class="list-group" id="helmets"></ul>';
    layer.open({
        type: 1,
        skin: 'layui-layer-reward', //样式类名
        anim: 2,
        title: '选择安全帽',
        area: ['380px', '300px'],
        btn: ['确定', '取消'],
        shadeClose: true, //开启遮罩关闭
        content: helmets,
        success: function () {
            TeamWorker.initHelmets();
        },
        yes: function (index, layero) {
            var value = '';
            $(".item-helmet").each(function (index, li) {
                if($(li).hasClass("active")){
                    value = $(li).data('imei');
                }
            });
            if (!value) {
                Feng.info("请选择安全帽");
                return;
            }
            var ajax = new $ax(Feng.ctxPath + "/projectWorker/bindSafetyHelmet", function (data) {
                Feng.success("绑定成功!");
                layer.close(index);
            }, function (data) {
                Feng.error("绑定失败!" + data.responseJSON.message + "!");
            });
            ajax.set({
                projectCode: $("#projectCode").val(),
                idCardType: selected[0].idCardType,
                idCardNumber: selected[0].idCardNumber,
                shImei: value
            });
            layer.confirm('确认绑定该安全帽？', {
                btn: ['确定', '取消']
            }, function (index) {
                layer.close(index);
                ajax.start();
            },function () {
            });
        }
    });
}


TeamWorker.cardNumber = function () {
	 var selected = $('#' + this.id).bootstrapTable('getSelections');
     if (selected.length !== 1) {
        Feng.info("请先选中表格中的一条记录！");
        return false;
     }
     if(selected[0].joinStatus == 3){
    	 Feng.info("退场人员不能绑定门禁卡！");
         return false;
     }
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '门禁卡设置',
            area: ['50%', '50%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: "layer-detail",
            content: Feng.ctxPath + '/projectMaster/projectMaster_card/' + selected[0].pwId
        });
        this.layerIndex = index;
    }
};

TeamWorker.initHelmets = function(params){
    var ajax = new $ax(Feng.ctxPath + "/safetyHelmet/getUnusedHelmetsByProjectCode", function (data) {
        if (data && data.length > 0) {
            var items = "";
            for (var i = 0; i < data.length; i++) {
                items += '<li class="list-group-item item-helmet" data-imei="' + data[i].imei + '">' + data[i].name + '</li>';
            }
            $("#helmets").html(items);
            $(".item-helmet").on('click', function () {
                $(".item-helmet").removeClass("active");
                $(this).addClass("active");
            })
        } else {
            $("#helmets").html('<li class="list-group-item item-helmet">暂无安全帽</li>');
        }

    }, function (data) {
        Feng.error("操作失败!" + data.responseJSON.message + "!");
    });
    ajax.set("projectCode", $("#projectCode").val());
    if(params){
        ajax.set("key",params);
    }
    ajax.start();
}

$(function () {
    // var defaultColunms = TeamWorker.initColumn();
    // var table = new BSTable(TeamWorker.id, "/teamMaster/getMemberByTeamId", defaultColunms);
    // table.setPaginationType("server");
    // table.setQueryParams(TeamWorker.formParams());
    // TeamWorker.table = table.init();
});
