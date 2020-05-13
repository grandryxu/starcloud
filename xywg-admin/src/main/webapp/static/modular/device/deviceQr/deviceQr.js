/**
 * 二维码管理管理初始化
 */
var DeviceQr = {
    id: "DeviceQrTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
DeviceQr.initColumn = function () {
    return [
        {field: 'selectItem', check: true},
        {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '项目id', field: 'projectCode', visible: false, align: 'center', valign: 'middle'},
        {title: '设备名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '所属项目', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '设备状态', field: 'stateName', visible: true, align: 'center', valign: 'middle'},
        {title: '设备序列号', field: 'sn', visible: true, align: 'center', valign: 'middle'},
        {title: '版本', field: 'version', visible: true, align: 'center', valign: 'middle'},
        {title: '软件版本', field: 'softVersion', visible: true, align: 'center', valign: 'middle'},
        {title: '升级状态', field: 'softStatusName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '最后通讯时间', field: 'talkTime', visible: true, align: 'center', valign: 'middle'
        },
        {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
DeviceQr.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("请先选中表格中的一记录！");
    }
    else {
        DeviceQr.seItem = selected[0];
        return true;
    }
    /*var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        DeviceQr.seItem = selected[0];
        return true;
    }*/
};

/**
 * 点击添加二维码管理
 */
DeviceQr.openAddDeviceQr = function () {
    var index = layer.open({
        type: 2,
        title: '添加二维码管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/deviceQr/deviceQr_add?projectCode=' + $("#projectCode").val() + "&projectName=" + $("#projectName").val()
    });
    this.layerIndex = index;
};

/**
 * 打开查看二维码管理详情
 */
DeviceQr.openDeviceQrDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '二维码管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/deviceQr/deviceQr_update/' + DeviceQr.seItem.id
        });
        this.layerIndex = index;
    }
};


/**
 * 上传功能
 */
DeviceQr.selectFile = function () {
    $("#file").trigger("click");
};

//上传版本
function fileUpload() {
    var myform = new FormData();
    myform.append('file', $('#file')[0].files[0]);
    $.ajax({
        url: Feng.ctxPath + "/deviceQr/upload",
        type: "POST",
        data: myform,
        contentType: false,
        processData: false,
        success: function (data) {
            Feng.success("上传成功!");
            $('#file').val("");
            DeviceQr.table.refresh();

            var ajax = new $ax(Feng.ctxPath + "/deviceQr/getVersion", function (data) {
                var html = '';
                for (var i = 0; i < data.length; i++) {
                    html += '<option value="' + data[i].name + '">' + data[i].name + '</option>';
                }
                $('#version').html(html);

            }, function (data) {
                // Feng.error("添加失败!" + data.responseJSON.message + "!");
            });
            ajax.start();

        },
        error: function (data) {
            Feng.error("上传失败!" + data.responseJSON.message + "!");
            $('#file').val("");
        }

    });
}


/**
 * 删除二维码管理
 */
DeviceQr.delete = function () {
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
            var ajax = new $ax(Feng.ctxPath + "/deviceQr/delete", function (data) {
                Feng.success("删除成功!");
                DeviceQr.table.refresh();
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
 * 版本升级
 */
DeviceQr.update = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }

    layer.open({
        type: 1,
        skin: 'layui-layer-reward', //样式类名
        title: '选择软件版本',
        anim: 2,
        area: ['380px', '200px'],
        content: $('#version'),
        shade: false,
        btn: ['确认', '取消'],
        btn1: function (index, layero) {
            //确认
            var version = $("#version").val();
            if (version === "") {
                Feng.error("请选择版本");
                return false;
            }
            else {
                for (var i = 0; i < selected.length; i++) {
                    DeviceQr.seItem = selected[i];
                    if (DeviceQr.seItem.softStatusName === 1) {
                        Feng.error("所选设备正在升级");
                        return false;
                    }
                }
                layer.close(index);
                layer.confirm('确认升级？', {
                    btn: ['确定', '取消'] //按钮
                }, function (index) {
                    var sns = "";
                    var version = "";
                    for (var i = 0; i < selected.length; i++) {
                        sns += selected[i].sn + ","
                    }
                    sns = sns.substring(0, sns.length - 1);
                    version = $("#version").val();
                    var ajax = new $ax(Feng.ctxPath + "/deviceQr/upCode", function (data) {
                        Feng.success("升级成功!");
                        DeviceQr.table.refresh();
                    }, function (data) {
                        Feng.error("升级失败!" + data.responseJSON.message + "!");
                    });
                    ajax.set("sns", sns);
                    ajax.set("version", version);
                    ajax.start();
                    layer.close(index);
                });
            }
        }
    });
};

DeviceQr.remoteRestart = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    layer.confirm('确认远程重启？', {
        btn: ['确定', '取消']
    }, function (index) {
        var sns = "";
        for (var i = 0; i < selected.length; i++) {
            sns += selected[i].sn + ","
        }
        sns = sns.substring(0, sns.length - 1);
        var ajax = new $ax(Feng.ctxPath + "/deviceQr/remoteRestart", function (data) {
            Feng.success("重启成功!");
            DeviceQr.table.refresh();
        }, function (data) {
            Feng.error("重启失败!");
        });
        ajax.set("sns", sns);
        ajax.start();
        layer.close(index);
    });
};

/**
 * 查询二维码管理列表
 */
DeviceQr.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['projectCode'] = $("#projectCode").val();
    DeviceQr.table.refresh({query: queryData});
};

$(function () {


    Feng.initChosen();
    var defaultColunms = DeviceQr.initColumn();
    var table = new BSTable(DeviceQr.id, "/deviceQr/list", defaultColunms);
    table.setPaginationType("server");
    DeviceQr.table = table.init();
});
