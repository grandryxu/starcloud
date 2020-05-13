var ProjectTrainingFile = {
    id: "ProjectTrainingFileTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
ProjectTrainingFile.initColumn = function () {
    return [
        {field: 'selectItem', check: true},
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '文件名', field: 'fileName', visible: true, align: 'center', valign: 'middle',
            formatter: function (val, row, index) {
                    return "<a  href='http://192.168.1.124:8080/labor/"+row.path+"' target='view_window'>" + row.fileName+ "</a>";
            }
        },
        {title: '操作', field: 'projectTypeName', visible: true, align: 'center', valign: 'middle',
            formatter: function (val, row, index) {
                return "<a href='javascript:void(0)' data-toggle='tooltip' title='删除'><i class='fa fa-remove' onClick='deleteFile(" + row.id + ")'></i></a>";
            }
        },
    ]}

deleteFile=function (id) {
    Feng.confirm("确认删除?",function(){
        var ajax = new $ax(Feng.ctxPath + "/fileInfo/delete", function (data) {
            Feng.success("删除成功!");
            ProjectTrainingFile.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data + "!");
        });
        ajax.set("fileInfoId", id);
        ajax.start();
    });
}

ProjectTrainingFile.deleteFiles=function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        layer.confirm('确认删除？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            var ids = [];
            for (var i = 0; i < selected.length; i++) {
                ids.push(selected[i].id);
            }
            var ajax = new $ax(Feng.ctxPath + "/fileInfo/deletes", function (data) {
                Feng.success("删除成功!");
                layer.closeAll();
                ProjectTrainingFile.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("ids", JSON.stringify(ids));
            ajax.start();
            layer.close(index);
        });
    }
}


$(function () {
    //加载表格
    var defaultColumsFile =ProjectTrainingFile.initColumn();
    var table = new BSTable(ProjectTrainingFile.id, "/projectTraining/getProjectFileList/"+$("#id").val(), defaultColumsFile);
    table.setPaginationType("client");
    ProjectTrainingFile.table=table.init();
});