/**
 * 轮播图管理管理初始化
 */
var Banner = {
    id: "BannerTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Banner.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '文件名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '终端', field: 'typeName', visible: true, align: 'center', valign: 'middle'},
        {title: '轮播图类型', field: 'chooseTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '是否可用连接', field: 'linkName', visible: true, align: 'center', valign: 'middle'},
        {title: '连接地址', field: 'url', visible: true, align: 'center', valign: 'middle'},
        {title: '备注', field: 'note', visible: true, align: 'center', valign: 'middle'},
        {title: '创建人', field: 'createUser', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
Banner.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("请先选中表格中的一记录！");
    }
    else {
        Banner.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加轮播图管理
 */
Banner.openAddBanner = function () {
    var index = layer.open({
        type: 2,
        title: '添加轮播图管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/banner/banner_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看轮播图管理详情
 */
Banner.openBannerDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '轮播图管理修改',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/banner/banner_update/' + Banner.seItem.id
        });
        this.layerIndex = index;
    }
};


/**
 * 打开修改轮播图排序页面
 */
Banner.order = function () {
        var index = layer.open({
            type: 2,
            title: '排序修改',
            area: ['560px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/banner/banner_order',

        });
        this.layerIndex = index;
};


/**
 * 删除轮播图管理
 */
Banner.delete = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        layer.confirm('确认删除？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            var ids = "";
            for (var i = 0; i < selected.length; i++) {
                ids += selected[i].id + ","
            }
            ids = ids.substring(0, ids.length - 1);
            var ajax = new $ax(Feng.ctxPath + "/banner/delete", function (data) {
                Feng.success("删除成功!");
                Banner.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("ids", ids);
            ajax.start();
            layer.close(index);
        });
    }
};
/**
 * 双击查看
 */
Banner.searchInfo = function (e) {
    var index = layer.open({
        type: 2,
        title: '轮播图管理详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/banner/banner_view/' + e.id
    });
    this.layerIndex = index;
}
/**
 * 查询轮播图管理列表
 */
Banner.search = function () {
    Banner.table.refresh({query: Banner.formParams()});
};
Banner.formParams = function () {
    var queryData = {};
    queryData['type'] = $("#type").val();
    queryData['link'] = $("#link").val();
    queryData['key'] = $("#key").val();
    return queryData;
}

$(function () {

    Feng.initChosen();
    var defaultColunms = Banner.initColumn();
    var table = new BSTable(Banner.id, "/banner/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(Banner.formParams());
    table.onDblClickRow = Banner.searchInfo;//双击事件所对应的方法 要放在init之前
    Banner.table = table.init();


});
