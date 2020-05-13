var trainInfoData = {
    trainInfoData: {},
    validateFields: {
        trainingTime: {
            validators: {
                notEmpty: {
                    message: '培训时间不能为空'
                }
            }
        },
        trainingDuration: {
            validators: {
                stringLength: {
                    max: 20,
                    message: '最大长度为20'
                }, notEmpty: {
                    message: '培训时长不能为空'
                },
                regexp: {
                    regexp: /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/,
                    message: "只能输入数字且最多两位小数"
                }
            }
        },
        trainingName: {
            validators: {
                stringLength: {
                    max: 100,
                    message: '最大长度为100'
                }, notEmpty: {
                    message: '培训名称不能为空'
                }
            }
        },
        trainer: {
            validators: {
                stringLength: {
                    max: 40,
                    message: '最大长度为40'
                }, notEmpty: {
                    message: '培训人不能为空'
                }
            }
        },
        trainingTypeCode: {
            validators: {
                notEmpty: {
                    message: '培训类型不能为空'
                }
            }
        },
        description: {
            validators: {
                stringLength: {
                    max: 400,
                    message: '最大长度为400'
                }, notEmpty: {
                    message: '培训简述不能为空'
                }
            }
        }
    }
}

var workerListTable = {
    id: "workerListTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
/**
 * 初始化表格的列
 */
workerListTable.initColumn = function () {
    return [
        {field: 'selectItem', radio: true, visible: false},
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
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
};
trainInfoData.validate = function () {
    $('#trainForm').data("bootstrapValidator").resetForm();
    $('#trainForm').bootstrapValidator('validate');
    return $("#trainForm").data('bootstrapValidator').isValid();
};
trainInfoData.clearData = function () {
    this.trainInfoData = {};
}
/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
trainInfoData.set = function (key, val) {
    this.trainInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
trainInfoData.get = function (key) {
    return $("#" + key).val();
}
/**
 * 收集数据
 */
trainInfoData.collectData = function () {
    this
        .set('trainingTime')
        .set('trainingDuration')
        .set('trainingName')
        .set('trainer')
        .set('trainingTypeCode')
        .set('description')
}
var filePath = '';
var fileName = '';
trainInfoData.submit = function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    var savedData = {};
    savedData.projectCode = $("#projectCode").val();
    savedData.trainingTime = $("#trainingTime").val();
    savedData.trainingDuration = $("#trainingDuration").val() * 60;
    savedData.trainingName = $("#trainingName").val();
    savedData.trainingTypeCode = $("#trainingTypeCode").val();
    savedData.description = $("#description").val();
    savedData.trainer = $("#trainer").val();
    savedData.detailList = parent.ProjectTrain.seItem;
    if (trainInfoData.upLoadSuccess === 1) {
        savedData.filePath = filePath;
        savedData.fileName = fileName;
    }
    var ajax = new $ax(Feng.ctxPath + "/projectTraining/add", function (data) {
        parent.layer.closeAll();
        parent.parent.ProjectTraining.success();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(savedData);
    ajax.start();
}

trainInfoData.cancel = function () {
    parent.layer.closeAll();
}
$(function () {
    Feng.initValidator("trainForm", trainInfoData.validateFields);
    laydate.render({
        elem: '#trainingTime'
    });

    var defaultColunms = workerListTable.initColumn();
    var table = new BSTable(workerListTable.id, "/projectTraining/getWorkerList", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams({id: $("#id").val()})
    workerListTable.table = table.init();


    /**
     * 上传附件
     */
    layui.use('upload', function () {
        var upload = layui.upload;
        upload.render({
            elem: '#uploadAccessory'
            , url: '/projectTraining/uploadFile'
            , size: 20000 //限制文件大小，单位 KB
            , accept: 'file'
            , acceptMime: 'application/pdf'
            , auto: true //不自动上传
            , before: function (obj) {
                layer.load(1);
            }
            , done: function (res, index, upload) {
                layer.closeAll('loading');
                Feng.success("上传成功！");
                trainInfoData.upLoadSuccess = 1;
                filePath = res.path;
                fileName = res.fileName;
                /*console.info(fileName);*/
            }
            , error: function (res) {
                /*console.log(res)*/
            }
        });
    });

});


