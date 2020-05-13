/**
 * 版本管理管理初始化
 */
var Version = {
    id: "VersionTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Version.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {
            title: '手机类型', field: 'phoneType', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if(value === 1){
                    return "IOS";
                }else if(value === 2){
                    return "安卓";
                }
            }
        },
        {title: 'App端', field: 'kind', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if(value === 1){
                    return "企业端";
                }else if(value === 2){
                    return "工人端";
                }
            }},
        {title: '下载路径', field: 'url', visible: true, align: 'center', valign: 'middle'},
        {title: '版本', field: 'version', visible: true, align: 'center', valign: 'middle'},
        {title: '版本code', field: 'versionCode', visible: true, align: 'center', valign: 'middle'},
        {title: '是否强制更新', field: 'flag', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if(value === 1){
                    return "强制更新";
                }else if(value === 2){
                    return "不强制更新";
                }
            }},
        {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
        {title: '新增时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
        {title: '新增人', field: 'createUser', visible: true, align: 'center', valign: 'middle'},
        {title: '最新版本', field: 'isNew', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if(value === 1){
                    return "<label style='color:#4680ff;'>new</label>";
                }
            }
        }
    ];
};

/**
 * 检查是否选中
 */
Version.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Version.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加版本管理
 */
Version.openAddVersion = function () {
    var index = layer.open({
        type: 2,
        title: '添加版本管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/version/version_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看版本管理详情
 */
Version.openVersionDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '版本管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/version/version_update/' + Version.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除版本管理
 */
Version.delete = function () {
    if (this.check()) {
        Feng.confirm("确认删除该版本?",function () {
            var ajax = new $ax(Feng.ctxPath + "/version/delete", function (data) {
                Feng.success("删除成功!");
                Version.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("versionId", Version.seItem.id);
            ajax.start();
        })
    }
};

/**
 * 查询版本管理列表
 */
Version.search = function () {
    var queryData = {};
    queryData['phoneType'] = $("#phoneType").val();
    queryData['kind'] = $("#kind").val();
    Version.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Version.initColumn();
    var table = new BSTable(Version.id, "/version/list", defaultColunms);
    table.setPaginationType("server");
    Version.table = table.init();
});
