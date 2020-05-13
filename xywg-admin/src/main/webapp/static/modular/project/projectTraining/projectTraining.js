/**
 * 培训记录管理初始化
 */
var ProjectTraining = {
    id: "ProjectTrainingTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    injuryUploadId: '',
    layerIndex: -1,
    selectImg:[],      //选中的附件,
    lookImgSelectSettlementId:'' //点击查看附件时的结算单编号
};

/**
 * 初始化表格的列
 */
ProjectTraining.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '课程名称', field: 'trainingName', visible: true, align: 'center', valign: 'middle'},
        {title: '培训类型', field: 'projectTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '培训时间', field: 'trainingTime', visible: true, align: 'center', valign: 'middle',
            formatter: function (value) {
                if (value != null) {
                    return value.substring(0, 10);
                } else
                    return value;
            }
        },
        {
            title: '培训时长(H)', field: 'trainingDuration', visible: true, align: 'center', valign: 'middle',
            formatter: function (value) {
                return value / 60;
            }
        },

        {title: '培训人', field: 'trainer', visible: true, align: 'center', valign: 'middle'},
        {
            title: '培训简述', field: 'description', visible: true, align: 'center', valign: 'middle' ,width:"600px",
            formatter: function (value) {
                if (value.length > 50) {
                    return "<a data-toggle='tooltip' title='" + value + "'>" + value.substring(0, 50) + "‧‧‧‧‧‧</a>";
                } else
                    return "<a data-toggle='tooltip' title='"+ value +"'>" + value + "</a>";
            }
        },{
            title: '操作', visible: true, align: 'center', valign: 'middle',width:"100px", formatter: function (val, row, index) {
                return "<a href='javascript:void(0)' title='查看附件' onclick='openAccessory(" + row.id + ")'><i class='fa fa-eye text-blue'></i></a>" +
                    "<a href='javascript:void(0)' settlementCode=" + row.id + " class='uploadFile' onClick='uploadFileBut(this)' title='附件上传,仅限PDF'>&nbsp;&nbsp;<i class='fa fa-upload text-blue'></i></a>";
            }
        }
    ];
};



/**
 * 上传
 */
function uploadFileBut(val) {
    ProjectTraining.injuryUploadId = $(val).attr("settlementCode");
    $("#uploadFileInput").trigger("click");
};

/**
 * 查看附件图片
 */
function openAccessory(id) {
    var index = layer.open({
        type: 2,
        title: '培训',
        area: ['750px', '550px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/projectTraining/projectTraining_file/' + id,
    });
    this.layerIndex = index;
}




/**
 * 真正的上传
 */
function fileUpload(page) {
    var maxsize = 100*1024*1024;
    var filesize = document.getElementById("uploadFileInput").files[0].size;
    if(filesize>maxsize) {
        Feng.error("上传的附件文件不能超过100M！！");
        return;
    }
    //上传
    if($('#uploadFileInput')[0].files.length === 0){
        return;
    }
    var formData = new FormData($("#form1")[0]);
    formData.append('settlementCode', ProjectTraining.injuryUploadId);
    $.ajax({
        url: Feng.ctxPath + "/projectTraining/uploadAccessory",
        type: "POST",
        data: formData,
        contentType: false,
        processData: false,
        async:false,
        success: function (data) {
            if(data.code!=200){
                Feng.error(data.message);
            }else{
            	$("#uploadFileInput").val('');
                Feng.success(data.message);
            }
        },
        error: function (data) {
            Feng.error(data.message);
        }
    });
}


/**
 * 检查是否选中
 */
ProjectTraining.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("请先选中表格中的一记录！");
    }
    else {
        ProjectTraining.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加培训记录
 */
ProjectTraining.openAddProjectTraining = function () {
    var index = layer.open({
        type: 2,
        title: '添加培训记录',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        skin: 'layer-no-title',
        maxmin: true,

        content: Feng.ctxPath + '/projectTraining/projectTraining_add',
        end: function () {
            ProjectTraining.table.refresh();
        }
    });
    this.layerIndex = index;
    layer.full(index);
};

/**
 * 打开查看培训记录详情
 */
ProjectTraining.openProjectTrainingDetail = function () {
        var index = layer.open({
            type: 2,
            title: '培训记录详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: 'layer-no-title',
            content: Feng.ctxPath + '/projectTraining/projectTraining_update/' + ProjectTraining.seItem.id,

        });
        this.layerIndex = index;
        layer.full(index);
};

/**
 * 双击查看
 */
ProjectTraining.searchInfo = function (e) {
    var index = layer.open({
        type: 2,
        title: '培训记录详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/projectTraining/projectTraining_view/' + e.id,

    });
    this.layerIndex = index;
    layer.full(index);
}

/**
 * 删除培训记录
 */
ProjectTraining.delete = function () {
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
            var ajax = new $ax(Feng.ctxPath + "/projectTraining/delete", function (data) {
                Feng.success("删除成功!");
                ProjectTraining.table.refresh();
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
 * 查询培训记录列表
 */
ProjectTraining.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ProjectTraining.table.refresh({query: queryData});
};

ProjectTraining.success=function(){
    Feng.success("操作成功！");
}

$(function () {
    var defaultColunms = ProjectTraining.initColumn();
    var table = new BSTable(ProjectTraining.id, "/projectTraining/list", defaultColunms);
    table.onDblClickRow = ProjectTraining.searchInfo;
    table.setPaginationType("server");
    ProjectTraining.table = table.init();
});
