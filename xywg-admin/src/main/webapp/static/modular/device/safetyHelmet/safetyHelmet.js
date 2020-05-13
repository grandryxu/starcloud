/**
 * 安全帽管理管理初始化
 */
var SafetyHelmet = {
    id: "SafetyHelmetTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SafetyHelmet.initColumn = function () {
    return [
            {field: 'selectItem', checkbox: true},
            {title: '设备名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '序列号', field: 'imei', visible: true, align: 'center', valign: 'middle'},
            {title: '所属项目', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
            {title: '设备状态', field: 'stateName', visible: true, align: 'center', valign: 'middle'},
            {title: '最后通信时间', field: 'talk', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
SafetyHelmet.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        SafetyHelmet.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加安全帽管理
 */
SafetyHelmet.openAddSafetyHelmet = function () {
    var index = layer.open({
        type: 2,
        title: '添加安全帽管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/safetyHelmet/safetyHelmet_add?projectCode='+$("#projectCode").val()+"&projectName="+$("#projectName").val()
    });
    this.layerIndex = index;
};

/**
 * 打开查看安全帽管理详情
 */
SafetyHelmet.openSafetyHelmetDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '安全帽管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/safetyHelmet/safetyHelmet_update?id=' + SafetyHelmet.seItem.id +'&projectCode='+$("#projectCode").val()+"&projectName="+$("#projectName").val()
        });
        this.layerIndex = index;
    }
};

/**
 * 删除安全帽管理
 */
SafetyHelmet.delete = function () {
    if (this.check()) {
        layer.confirm('确认删除？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            layer.closeAll();
            var selected = $('#' + SafetyHelmet.id).bootstrapTable('getSelections');
            var ids = "";
            for (var i = 0; i < selected.length; i++) {
                ids += selected[i].id + ",";
            }
            ids = ids.substring(0, ids.length - 1);
            var ajax = new $ax(Feng.ctxPath + "/safetyHelmet/deleteSafetyHelmets", function (data) {
                Feng.success("删除成功!");
                SafetyHelmet.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("ids",ids);
            ajax.start();
        })
    }
};

/**
 * 查询安全帽管理列表
 */
SafetyHelmet.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val().trim();
    queryData['projectCode'] = $("#projectCode").val();
    SafetyHelmet.table.refresh({query: queryData});
};

/**
 *  双击查看
 */
SafetyHelmet.searchInfo = function (e) {
        var index = layer.open({
            type: 2,
            title: '安全帽管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/safetyHelmet/safetyHelmet_view?id=' + e.id +'&projectCode='+$("#projectCode").val()+"&projectName="+$("#projectName").val()
        });
        this.layerIndex = index;
};


$(function () {
    Feng.initChosen();
    var defaultColunms = SafetyHelmet.initColumn();
    var table = new BSTable(SafetyHelmet.id, "/safetyHelmet/list", defaultColunms);
    table.setPaginationType("server");
    table.onDblClickRow = SafetyHelmet.searchInfo;
    SafetyHelmet.table = table.init();
});
