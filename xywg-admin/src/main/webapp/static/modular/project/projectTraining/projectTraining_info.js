/**
 * 初始化培训记录详情对话框
 */
var ProjectTrainingInfoDlg = {
    projectTrainingInfoData: {}
};

var ProjectTrain = {
    id: "projectTrain",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
}

ProjectTrain.initColumn = function () {
    return [
        {field: 'selectItem', check: true},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型', field: 'idCardTypeName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '证件号码', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',
            formatter: function (data) {
                return Feng.hiddenIdCard(data);
            }
        },
        {title: '工种', field: 'workKingName', visible: true, align: 'center', valign: 'middle'}
    ];
}

/**
 * 清除数据
 */
ProjectTrainingInfoDlg.clearData = function () {
    this.projectTrainingInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectTrainingInfoDlg.set = function (key, val) {
    this.projectTrainingInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectTrainingInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 检查是否选中
 */
ProjectTrain.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        ProjectTrain.seItem = selected;
        return true;
    }
};

/**
 * 关闭此对话框
 */
ProjectTrainingInfoDlg.close = function () {
    parent.layer.close(window.parent.ProjectTraining.layerIndex);
}
var detailList = {}
/**
 * 收集数据
 */
ProjectTrainingInfoDlg.collectData = function () {
    this
        .set('id')
        .set('projectCode')
        .set('trainingTime')
        .set('trainingDuration')
        .set('trainingName')
        .set('trainingTypeCode')
        .set('trainer')
        .set('description')
        .set('createDate')
        .set('createUser')
        .set('updateDate')
        .set('updateUser')
        .set('isDel');
}
ProjectTrain.tableOnloadSuccess = function (data) {
    detailList = data;
}

/**
 * 提交添加
 */
ProjectTrainingInfoDlg.addSubmit = function () {
    var savedData = {};
    savedData.projectCode = $("#projectCode").val();
    savedData.trainingTime = $("#trainingTime").val();
    savedData.trainingDuration = $("#trainingDuration").val();
    savedData.trainingName = $("#trainingName").val();
    savedData.trainingTypeCode = $("#trainingTypeCode").val();
    savedData.description = $("#description").val();
    savedData.trainer = $("#trainer").val();
    savedData.detailList = detailList;
    if (projectCode == null) {
        Feng.info("请选择项目！")
        return;
    }
    if ($("#team").val() == null) {
        Feng.info("请选择班组！")
        return;
    }
    if (trainingTime == null) {
        Feng.info("请选择培训时间！")
        return;
    }
    if (trainingName == null) {
        Feng.info("请选择培训名称！")
        return;
    }
    if (trainingDuration == null) {
        Feng.info("请选择培训时长！")
        return;
    }
    if (trainingTypeCode == null) {
        Feng.info("请选择培训类型！")
        return;
    }
    if (savedData.detailList.length == 0) {
        Feng.info("请选择工人！");
        return;
    }

    var ajax = new $ax(Feng.ctxPath + "/projectTraining/add", function (data) {
        Feng.success("添加成功!");
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(savedData);
    ajax.start();
    ProjectTrainingInfoDlg.close();
}
//下拉多选
var selectAccount = $(".select-account");
var selectTeamWorker = $(".select-teamWorker");

/**
 *
 */
ProjectTrainingInfoDlg.openTrain = function () {
    if ($("#projectCode").val() == '') {
        Feng.info("请先选择项目！");
        return;
    }
    if (ProjectTrain.check()) {
        var index = layer.open({
            type: 2,
            title: '培训',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: 'layer-no-title',
            content: Feng.ctxPath + '/projectTraining/projectTraining_train?projectCode=' + $("#projectCode").val(),
            end: function () {
                parent.layer.closeAll();
            }
        });
        this.layerIndex = index;
    }

}

/**
 * 提交修改
 */
ProjectTrainingInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/projectTraining/update", function (data) {
        Feng.success("修改成功!");
        window.parent.ProjectTraining.table.refresh();
        ProjectTrainingInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.projectTrainingInfoData);
    ajax.start();
}

ProjectTrainingInfoDlg.search = function () {
    var queryData = {}
    queryData.projectCode = $("#projectCode").val();
    if (ProjectTrain.table == null) {
        var defaultColunms = ProjectTrain.initColumn();
        var table = new BSTable(ProjectTrain.id, "/projectWorker/getWorkerListByProject", defaultColunms);
        table.setPaginationType("server");
        //table.onLoadSuccess=AccountDetailInfoDlg.tableOnloadSuccess;
        table.set("pagination", false);
        table.set("showFooter", true);
        table.setQueryParams(queryData);
        ProjectTrain.table = table.init();
    } else {
        ProjectTrain.table.refresh({query: queryData});
    }
}

$(function () {
    Feng.initChosen();
    $(".select-teamWorker").chosen();
    laydate.render({
        elem: '#trainingTime'
    });
    var defaultColunms = ProjectTrain.initColumn();
    var table = new BSTable(ProjectTrain.id, "/projectWorker/getWorkerListByProject", defaultColunms);
    var queryData = {}
    queryData.projectCode = '';
    table.setPaginationType("server");
    table.set("pagination", false);
    table.set("showFooter", true);
    table.setQueryParams(queryData);
    ProjectTrain.table = table.init();
});
