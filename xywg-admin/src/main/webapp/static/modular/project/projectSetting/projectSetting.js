/**
 * 培训记录管理初始化
 */
var ProjectSetting = {
    id: "ProjectSettingTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    injuryUploadId: '',
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProjectSetting.initColumn = function () {
    return [
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '项目编号', field: 'projectCode', visible: false, align: 'center', valign: 'middle'},
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle',
            formatter: function(value, row, index) {
                if(null != value && '' != value && value.length > 20) {
                    return value.substring(0,20)+'...';
                }
                return value;
            }
        },
        {
            title: '是否同步实名制', field: 'isSynchro', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                var s = '<input type="checkbox" value="0" onchange="updateCheckBox('+index+',0, 1);"/>';
                if(value == 1) {
                    s = '<input type="checkbox" value="1" onchange="updateCheckBox('+index+',0, 0);" checked="checked" />';
                }
                return s;
            }
        },
        {
            title: '是否拉取实名制数据', field: 'isReceive', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                var s = '<input type="checkbox" value="0" onchange="updateCheckBox('+index+',1, 1);"/>';
                if(value == 1) {
                    s = '<input type="checkbox" value="1" checked="checked" onchange="updateCheckBox('+index+',1, 0);" />';
                }
                return s;
            }
        },
        {
            title: '是否添加水印', field: 'isWaterMark', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                var s = '<input type="checkbox" value="0" onchange="updateCheckBox('+index+',2, 1);"/>';
                if(value == 1) {
                    s = '<input type="checkbox" value="1" checked="checked" onchange="updateCheckBox('+index+',2, 0);" />';
                }
                return s;
            }
        },
        {
            title: '操作', visible: true, align: 'center', valign: 'middle', formatter: function (val, row, index) {
                return "<a href='javascript:void(0)' data-toggle='tooltip' title='保存'><i class='fa fa-save text-blue' onClick='updateSetting(" + index + ")'></i></a>";
            }
        }
    ];
};

ProjectSetting.onLoadSuccess = function (data) {
    ProjectSetting.data = data.rows;
}

/**
 * 用户选中
 */
function updateCheckBox(index, type, value) {
    if(type == 0) {
        ProjectSetting.data[index].isSynchro = value;
    }else if(type == 1) {
        ProjectSetting.data[index].isReceive = value;
    }else {
        ProjectSetting.data[index].isWaterMark = value;
    }
}

/**
 * 保存设置
 */
function updateSetting(index) {
    var project = ProjectSetting.data[index];
    $.ajax({
        type:'put',
        url: '/projectSetting/save',
        data: project,
        success:function(data) {
            console.log(JSON.stringify(data));
            if(data.code == 200) {
                Feng.info("操作成功");
            }
        },
        error:function() {
            Feng.error("操作失败，请刷新重试");
        }
    });
}

/**
 * 检查是否选中
 */
ProjectSetting.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("请先选中表格中的一记录！");
    }
    else {
        ProjectSetting.seItem = selected[0];
        return true;
    }
};

ProjectSetting.formParams = function () {
    var queryData = {};
    queryData['projectCode'] = $("#projectCode").val();
    queryData['isSynchro'] = $("#isSynchro").val();
    queryData['isReceive'] = $("#isReceive").val();
    queryData['isWatermark'] = $("#isWatermark").val();
    return queryData;
}

/**
 * 查询培训记录列表
 */
ProjectSetting.search = function () {
    console.log(ProjectSetting.formParams());
    ProjectSetting.table.refresh({query: ProjectSetting.formParams()});
};

ProjectSetting.success=function(){
    Feng.success("操作成功！");
}

$(function () {
    Feng.initChosen();
    var defaultColunms = ProjectSetting.initColumn();
    var table = new BSTable(ProjectSetting.id, "/projectSetting/list", defaultColunms);
    table.onLoadSuccess = ProjectSetting.onLoadSuccess;
    table.setPaginationType("server");
    ProjectSetting.table = table.init();
});
