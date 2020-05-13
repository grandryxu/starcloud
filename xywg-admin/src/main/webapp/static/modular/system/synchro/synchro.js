/**
 * 版本管理管理初始化
 */
var Synchro = {
    id: "SynchroTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Synchro.initColumn = function () {
    return [
    	{field: 'selectItem', checkbox: true},
        {title: '企业/项目名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '班组名称', field: 'teamName', visible: true, align: 'center', valign: 'middle'},
        {title: '工种', field: 'workKindName', visible: true, align: 'center', valign: 'middle'},
        {title: '工人名称', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '考勤时间', field: 'time', visible: true, align: 'center', valign: 'middle'},
        {title: '培训记录', field: 'trainingName', visible: true, align: 'center', valign: 'middle'},
       /* {title: '培训人', field: 'remark', visible: true, align: 'center', valign: 'middle'},
        {title: '培训附件', field: 'createDate', visible: true, align: 'center', valign: 'middle'},*/
        {title: '是否同步', field: 'state', visible: true, align: 'center', valign: 'middle'},
       
    ];
};


/**
 * 删除版本管理
 */
Synchro.operation = function () {
	 var selected = $('#' + this.id).bootstrapTable('getSelections');
	 if (selected.length == 0) {
	        Feng.info("请选择表格中未同步的记录！");
	        return false;
	    } else {
//	    	var ids = [];
	    	 var ids = "";
             for (var i = 0; i < selected.length; i++) {
            	 if(selected[i].state == '已同步'){
            		 Feng.info("请选择未同步的记录！");
         	         return false;
            	 }else{
            		 ids += selected[i].id + ",";
//            		 ids.push(selected[i].id);
            	 }
             }
             ids = ids.substring(0, ids.length - 1);
             var ajax = new $ax(Feng.ctxPath + "/synchro/synchroLabor", function (data) {
                 Feng.success("操作成功!");
                 Synchro.table.refresh();
             }, function (data) {
                 Feng.error("操作失败!" + data.responseJSON.message + "!");
             });
             ajax.set({
                 ids : ids,
                 type: $("#projectCode").val()
             });
             traditional: true;
             ajax.start();
    }
};

/**
 * 查询版本管理列表
 */
Synchro.search = function () {
    var queryData = {};
    queryData['type'] = $("#type").val();
    queryData['projectCode'] = $("#projectCode").val();
    Synchro.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Synchro.initColumn();
    var table = new BSTable(Synchro.id, "/synchro/list", defaultColunms);
    table.setPaginationType("server");
    Synchro.table = table.init();
    $("#type").chosen().on("change", function (evt, data){
    	var type=data.selected;
    	if(type == 'buss_sub_contractor' || type == "buss_work_kind" ){
    		$("#hidden").attr("style","display:none;");//隐藏div
    	}else{
    		$("#hidden").attr("style","display:block;");//显示div
    	}
    });
});


