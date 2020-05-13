

/**
 * 检查是否选中
 */
TeamMember.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TeamMember.seItem = selected[0];
        return true;
    }
};



/**
 * 添加班组成员
 */
TeamMember.addTeamMember = function (teamCode,idStrs,face) {
    layer.load(1);
    var ajax = new $ax(Feng.ctxPath + "/teamMaster/addMember", function (data) {
        layer.closeAll('loading');
        Feng.success("添加成功!");
        TeamMember.table.refresh();

    }, function (data) {
        layer.closeAll('loading');
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set("teamCode",teamCode);
    ajax.set("idStrs",idStrs);
    ajax.set("teamWorkType",1);
    if(face != null){
    	ajax.set("face",face);
    }
    ajax.start();
};

/**
 * 选择工人弹框结果
 * @param data
 */
var dialogEnd=function (data) {
    layer.closeAll();

    TeamMember.addTeamMember($('#teamSysNo').val(),data.id,data.face);
}



/**
 * 弹出搜索工人列表框
 */
TeamMember.openWorkerDialog = function () {
    if($('#teamName').text()==""){
        layer.msg("请先选择班组");
        return;
    }
    var index = layer.open({
        type: 2,
        title: '添加成员',
        area: ['50%', '80%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/workerMaster/openAddWorkerSearchDialog'
    });
    this.layerIndex = index;
};



/**
 * 查询班组管理列表
 */
TeamMember.search = function () {
	TeamMember.table.refresh({query: TeamMember.formParams()});
};

$(function () {
    // var defaultColunms = TeamMember.initColumn();
    // var table = new BSTable(TeamMember.id, "/teamMaster/getUnteamWorker", defaultColunms);
    // table.setPaginationType("server");
    // table.setQueryParams(TeamMember.formParams());
    // TeamMember.table = table.init();

});
