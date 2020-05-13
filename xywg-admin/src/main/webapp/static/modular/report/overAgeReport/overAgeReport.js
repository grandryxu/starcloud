/**
 * 工伤管理管理初始化
 */
var OverAge = {
    id: "OverAgeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    OverAgeUploadId: '',
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
OverAge.initColumn = function () {
    return [
    	    {field: 'selectItem', checkbox: false,visible: false},
            {title: '班组', field: 'teamName', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
            {title: '证件号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '生日', field: 'birthday', visible: true, align: 'center', valign: 'middle'},
            {title: '年龄(岁)', field: 'age', visible: true, align: 'center', valign: 'middle'}
    ];
};


OverAge.onLoadSuccess = function (data) {
	OverAge.data = data.rows;
}



OverAge.setClass=function(){
	if ($("#projectCode").val() != null && $("#projectCode").val() != "") {
		$('#team').removeAttr("disabled");
		
	} else {
		$('#team').attr("disabled","disabled");
	}
	
};


/**
 * 查询工伤管理列表
 */
OverAge.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['teamSysNo'] = $("#team").val();
    OverAge.table.refresh({query: queryData});
};




$(function () {
    var defaultColunms = OverAge.initColumn();
    var table = new BSTable(OverAge.id, "/overAgeReport/list", defaultColunms);
    table.setPaginationType("server");
    table.onDblClickRow = OverAge.searchInfo;//双击事件所对应的方法 要放在init之前
    OverAge.table = table.init();
    $("#projectCode").chosen().on("change", function (evt, data){
        var projectCode=data.selected;
		 var ajax = new $ax(Feng.ctxPath + "/teamMaster/getList?projectCode=" + projectCode, function (data) {
	         var teamMaster = $("#team");
	         teamMaster.children("option").remove();
	         if (projectCode != null && projectCode != "") {
	         	var html = "<option value=''>请选择班组</option>";
			} else {
				var html = "<option value=''>请先选择项目</option>";
			}
	         for (var i = 0; i < data.length; i++) {
	             html += "<option value='" + data[i].teamSysNo + "'>" + data[i].teamName + "</option>"
	         }
	         teamMaster.append(html);
	     }, function (data) {
	         Feng.error("班组加载失败!" + data.responseJSON.message + "!");
	     });
	     ajax.set(this.payRollDetailInfoData);
	     ajax.start();
    });
});
function  resetDate() {
        $("#team").html("<option value=''>请选择</option>");
}

OverAge.download = function () {
    window.location.href = Feng.ctxPath + "/overAgeReport/download?"
        + "teamSysNo=" + $("#team").val()
        + "&condition=" + $("#condition").val()
}; 

