/**
 * 项目信息管理初始化
 */
var pdfMaster = {
    id: "pdfTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
pdfMaster.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true },
        {
            title: '合同名称', field: 'fileName', visible: true, align: 'center', valign: 'middle',
            formatter: function (val, row, index) {
                return "<a href='http://192.168.1.124:8080/labor/" + row.path + "' data-toggle='tooltip' title='查看' target='view_window'>" + val + "</a>";
            }
        },
        {
            title: '操作', visible: true, align: 'center', valign: 'middle', formatter: function (val, row, index) {
            return '<a href="javascript:void(0)" data-toggle="tooltip" title="删除"><i class="fa fa-remove" onclick="deleteFile(' + row.id + ')"></i></a>';
        }
        }

    ];
};

//删除一条数据
deleteFile = function (id) {
    Feng.confirm("确认删除?", function () {
        var ajax = new $ax(Feng.ctxPath + "/fileInfo/delete", function (data) {
            Feng.success("删除成功!");
            pdfMaster.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data + "!");
        });
        ajax.set("fileInfoId", id);
        ajax.start();
    });
}


/**
 * 检查是否选中
 */
pdfMaster.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        pdfMaster.seItem = selected;
        return true;
    }
};

/**
 * 点击添加项目信息
 */
pdfMaster.openAddProjectMaster = function () {
    var index = layer.open({
        type: 2,
        title: '添加项目信息',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: "layer-detail",
        content: Feng.ctxPath + '/projectMaster/projectMaster_add'
    });
    layer.full(index);
    this.layerIndex = index;
};

/**
 * 双击查看
 */
pdfMaster.searchInfo = function (e) {
    var index = layer.open({
        type: 2,
        title: '项目信息详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-detail',
        content: Feng.ctxPath + '/projectMaster/projectMaster_view/' + e.id
    });
    layer.full(index);
    this.layerIndex = index;
};

/**
 * 删除项目信息
 */
pdfMaster.delete = function () {
    if (this.check()) {
        layer.confirm('确认删除？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            var selecteds = pdfMaster.seItem;
            var ids = [];
            for (var i = 0; i < selecteds.length; i++) {
                ids.push(selecteds[i].id);
            }
            var ajax = new $ax(Feng.ctxPath + "/fileInfo/deletes", function (data) {
                Feng.success("删除成功!");
                pdfMaster.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("ids", JSON.stringify(ids));
            ajax.start();

            layer.close(index);
        });
    }
};

pdfMaster.onLoadSuccess = function (data) {
    ProjectMaster.data = data.rows;
}

$(function () {
    var defaultColunms = pdfMaster.initColumn();
    var table = new BSTable(pdfMaster.id, "/projectMaster/getPdfList?id=" + $("#psId").val(), defaultColunms);
    table.setPaginationType("client");
    //table.onDblClickRow = pdfMaster.searchInfo;
    pdfMaster.table = table.init();

});
