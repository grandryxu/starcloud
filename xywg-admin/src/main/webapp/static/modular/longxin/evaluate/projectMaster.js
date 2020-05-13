/**
 * 项目信息管理初始化
 */
var ProjectMaster = {
    id: "ProjectMasterTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
};

var SubContractor = {
    id: "SubContractorTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};


/**
 * 招标描述信息
 */
SubContractor.openAddSubContractor = function (id) {
    var index = layer.open({
        type: 2,
        title: '招标详情',
        area: ['80%', '80%'], //宽高
        fix: false, //不固定
        maxmin: false,
        content: Feng.ctxPath + '/lxProject/subContractor_add_search/'+ id
        // content: Feng.ctxPath + '/subContractor/getAllSubContractor'
    });
    this.layerIndex = index;
};

/**
 * 初始化表格的列
 */
ProjectMaster.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目分类', field: 'projectCategoryName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目所在地', field: 'address', visible: true, align: 'center', valign: 'middle'},
        {title: '所属公司', field: 'companyName', visible: true, align: 'center', valign: 'middle'},

/*        {title: '招标信息', field: 'status', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {




                return"<a size='2' color='blue' onclick='SubContractor.openAddSubContractor(\""+ row.id +"\")'>查看</a>";

            }
        }*/



    ];
};

ProjectMaster.updateStaus=function(){
    var index = layer.open({


        content: Feng.ctxPath + '/lxProject/updateStatus/' + ProjectMaster.seItem.id
    });




}



ProjectMaster.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val().trim();
   /* queryData['projectStatus'] = $("#projectStatus").val();
    queryData['startDate1'] = $('#startDate1').val();
    queryData['endDate1'] = $('#endDate1').val();
    queryData['deviceType'] = $('#deviceType').val();
    queryData['startDate2'] = $('#startDate2').val();
    queryData['endDate2'] = $('#endDate2').val();*/
    ProjectMaster.table.refresh({query: queryData});
};


//pdf展示页面
ProjectMaster.showPdf = function (id) {
    var index = layer.open({
        type: 2,
        title: '合同详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: "layer-detail",
        content: Feng.ctxPath + '/projectMaster/showPdf?id='+ id
    });
    layer.full(index);
    this.layerIndex = index;
}

/**
 * 检查是否选中
 */
ProjectMaster.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("只能选中表格中的某一记录！");
        return false;
    } else {
        ProjectMaster.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加项目信息
 */
ProjectMaster.openAddProjectMaster = function () {
    var index = layer.open({
        type: 2,
        title: '添加项目信息',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: "layer-detail",
        content: Feng.ctxPath + '/lxProject/projectMaster_add'
    });
    layer.full(index);
    this.layerIndex = index;
};

/**
 * 双击查看
 */
ProjectMaster.searchInfo = function (e) {
    var index = layer.open({
        type: 2,
        title: '项目信息详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-detail',
        content: Feng.ctxPath + '/lxProject/projectMaster_view/' + e.id
    });
    layer.full(index);
    this.layerIndex = index;
};

/**
 * 打开查看项目信息详情
 */
ProjectMaster.openProjectMasterDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '项目信息详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: "layer-detail",
            content: Feng.ctxPath + '/lxProject/projectMaster_update/' + ProjectMaster.seItem.id
        });
        this.layerIndex = index;
    }
};

/*
var SubContractor = {
    id: "SubContractorTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};


/!**
 * 招标描述信息
 *!/
SubContractor.openAddSubContractor = function (id) {
    var index = layer.open({
        type: 2,
        title: '招标详情',
        area: ['80%', '80%'], //宽高
        fix: false, //不固定
        maxmin: false,
        content: Feng.ctxPath + '/lxProject/subContractor_add_search/'+ id
        // content: Feng.ctxPath + '/subContractor/getAllSubContractor'
    });
    this.layerIndex = index;
};*/

/**
 * 打开工时设置
 */
/*ProjectMaster.openProjectMasterSetTime = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '工数工时设置',
            area: ['30%', '30%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: "layer-detail",
            content: Feng.ctxPath + '/projectMaster/projectMaster_setTime/' + ProjectMaster.seItem.id
        });
        this.layerIndex = index;
    }
};*/


/**
 * 切换项目状态
 */
/*ProjectMaster.toggleProjectStatus = function (projectStatus) {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        if (projectStatus === 3) {
            //开工
            for (var i = 0; i < selected.length; i++) {
                var status = selected[i].projectStatus;
                if (status !== "1" && status !== "2" && status !== "5") {
                    Feng.info("只有筹备、立项、停工的项目才可以开工！");
                    return false;
                }
            }
        } else if (projectStatus === 5) {
            //停工
            for (var i = 0; i < selected.length; i++) {
                var status = selected[i].projectStatus;
                if (status !== "3") {
                    Feng.info("只有在建的项目才可以停工！");
                    return false;
                }
            }
        } else if (projectStatus === 4) {
            //完工
            for (var i = 0; i < selected.length; i++) {
                var status = selected[i].projectStatus;
                if (status !== "3" && status !== "5") {
                    Feng.info("只有在建、停工的项目才可以完工！");
                    return false;
                }
            }
        }
        var ids = "";
        for (var i = 0; i < selected.length; i++) {
            ids += selected[i].id + ",";
        }
        ids = ids.substring(0, ids.length - 1);
        var ajax = new $ax(Feng.ctxPath + "/projectMaster/toggleProjectStatus", function (data) {
            Feng.success("操作成功!");
            ProjectMaster.table.refresh();
        }, function (data) {
            Feng.error("操作失败!" + data.responseJSON.message + "!");
        });
        ajax.set({
            ids: ids,
            projectStatus: projectStatus
        });
        ajax.start();
    }
};*/

/**
 * 删除项目信息
 */
ProjectMaster.delete = function () {
    if (this.check()) {
        layer.confirm('确认删除？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            var ajax = new $ax(Feng.ctxPath + "/lxProject/delete", function (data) {
             if(data=="ERROR"){
                 Feng.error("删除失败,存在招标信息!");
             }else {
                 Feng.success("删除成功!");
                 ProjectMaster.table.refresh();
             }
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("projectMasterId", ProjectMaster.seItem.id);
            ajax.start();
            layer.close(index);
        });
    }
};

/**
 * 查询项目信息列表
 */
/*ProjectMaster.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val().trim();
    queryData['projectStatus'] = $("#projectStatus").val();
    queryData['startDate1'] = $('#startDate1').val();
    queryData['endDate1'] = $('#endDate1').val();
    queryData['deviceType'] = $('#deviceType').val();
    queryData['startDate2'] = $('#startDate2').val();
    queryData['endDate2'] = $('#endDate2').val();
    ProjectMaster.table.refresh({query: queryData});
};*/

/**
 * 安全帽管理
 */
/*ProjectMaster.openSafetyHelmetManager = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '安全帽管理',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/safetyHelmet?projectCode=' + ProjectMaster.seItem.projectCode + '&projectName=' + ProjectMaster.seItem.projectName +'&projectStatus=' + ProjectMaster.seItem.projectStatus
        });
        this.layerIndex = index;
    }
};*/

/**
 * 考勤机管理
 */
/*ProjectMaster.openDeviceManager = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '考勤机管理',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/device?projectCode=' + ProjectMaster.seItem.projectCode + '&projectName=' + ProjectMaster.seItem.projectName+'&projectStatus=' + ProjectMaster.seItem.projectStatus
        });
        this.layerIndex = index;
    }
};*/

/**
 * 上传劳动合同
 */
/*function uploadLaborContract(index) {
    ProjectMaster.seItem = ProjectMaster.data[index];
    $('#excelFile').val("");
    $("#excelFile").trigger("click");
};*/

/**
 * 上传劳动合同
 */
/*ProjectMaster.uploadLaborContract = function () {
    if (this.check()) {
        $("#excelFile").trigger("click");
    }
};*/

/**
 * 上传劳动合同 导入
 */
/*function fileUpload() {
    if($('#excelFile')[0].files.length === 0){
        return;
    }
    var maxsize = 100*1024*1024;
    var filesize = document.getElementById("excelFile").files[0].size;
    if(filesize>maxsize) {
        Feng.error("上传的附件文件不能超过100M！！");
        return;
    }
    var projectCode = ProjectMaster.seItem.projectCode;
    var cOrganizationCode = ProjectMaster.seItem.cOrganizationCode;

    var myform = new FormData($("#form1")[0]);
    myform.append('projectCode', projectCode);
    myform.append('cOrganizationCode', cOrganizationCode);
    $.ajax({
        url: Feng.ctxPath + "/projectMaster/uploadLaborContract",
        type: "POST",
        data: myform,
        contentType: false,
        processData: false,
        async:false,
        success: function (data) {
            if(data.code === 501){
                Feng.error("上传文件格式错误,只能上传pdf文件!");
            }else{
                Feng.success("上传成功!");
                ProjectMaster.table.refresh();
            }
        },
        error: function (data) {
            Feng.error(data.message);
        }
    });
}*/

/**
 * 二维码管理
 */
ProjectMaster.openDeviceQrManager = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '二维码管理',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/deviceQr?projectCode=' + ProjectMaster.seItem.projectCode + '&projectName=' + ProjectMaster.seItem.projectName+'&projectStatus=' + ProjectMaster.seItem.projectStatus
        });
        this.layerIndex = index;
    }
}
ProjectMaster.onLoadSuccess = function (data) {
    ProjectMaster.data = data.rows;
}

ProjectMaster.tendering = function(){


    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '新增招标文件',
            area: ['90%', '90%'], //宽高

            fix: false, //不固定
            maxmin: false,
            skin: "layer-detail",
            content: Feng.ctxPath + '/lx/tendering?id='+ProjectMaster.seItem.id
        });
        this.layerIndex = index;

    }
    
}
$(function () {
    var defaultColunms = ProjectMaster.initColumn();
    var table = new BSTable(ProjectMaster.id, "/lxProject/list", defaultColunms);


    table.setPaginationType("server");
    table.onDblClickRow = ProjectMaster.searchInfo;
    table.onLoadSuccess = ProjectMaster.onLoadSuccess;
    ProjectMaster.table = table.init();
    var currDate = new Date();
    var startDate1 = laydate.render({
        elem: '#startDate1',
        done: function (value, date) {
            if (value !== '') {
                endDate1.config.min.year = date.year;
                endDate1.config.min.month = date.month - 1;
                endDate1.config.min.date = date.date;
            } else {
                endDate1.config.min.year = '';
                endDate1.config.min.month = '';
                endDate1.config.min.date = '';
            }
        }
    });
    //设置结束时间
    var endDate1 = laydate.render({
        elem: '#endDate1',
        done: function (value, date) {
            if (value !== '') {
                startDate1.config.max.year = date.year;
                startDate1.config.max.month = date.month - 1;
                startDate1.config.max.date = date.date;
            } else {
                startDate1.config.max.year = currDate.getFullYear();
                startDate1.config.max.month = currDate.getMonth()+1;
                startDate1.config.max.date =  currDate.getDate();
            }
        }
    });

    var startDate2 = laydate.render({
        elem: '#startDate2',
        done: function (value, date) {
            if (value !== '') {
                endDate2.config.min.year = date.year;
                endDate2.config.min.month = date.month - 1;
                endDate2.config.min.date = date.date;
            } else {
                endDate2.config.min.year = '';
                endDate2.config.min.month = '';
                endDate2.config.min.date = '';
            }
        }
    });
    //设置结束时间
    var endDate2 = laydate.render({
        elem: '#endDate2',
        done: function (value, date) {
            if (value !== '') {
                startDate2.config.max.year = date.year;
                startDate2.config.max.month = date.month - 1;
                startDate2.config.max.date = date.date;
            } else {
                startDate2.config.max.year = currDate.getFullYear();
                startDate2.config.max.month = currDate.getMonth()+1;
                startDate2.config.max.date =  currDate.getDate();
            }
        }
    });

    //重置的时间集合
    ProjectMaster.resetDate = [startDate1,startDate2,endDate1,endDate2];
});

function  resetDate() {
    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = currentDate.getMonth()+1;
    var date = currentDate.getDate();

    for(var i=0;i<ProjectMaster.resetDate.length; i++) {
        var dateObject = ProjectMaster.resetDate[i];

        //去除最小时间限制
        dateObject.config.min.year = '';
        dateObject.config.min.month = '';
        dateObject.config.min.date = '';

        //去除最大时间限制
        dateObject.config.max.year = '9999';
        dateObject.config.max.month = '12';
        dateObject.config.max.date = '31';

        //设置当前时间
        dateObject.config.dateTime.year = year;
        dateObject.config.dateTime.month = month;
        dateObject.config.dateTime.date = date;
        // dateObject.startDate.config.dateTime.hours = '时';
        // dateObject.startDate.config.dateTime.minutes = '分';
        // dateObject.startDate.config.dateTime.seconds = '秒';
    }

    /**
     * 批量工时/数量
     */
    ProjectMaster.setTime = function () {
        if(!ProjectMaster.check()){return;}
        layer.prompt({
            title: '输入工时/数量',
            formType: 0
        }, function (text, index) {
            if (!reg.test(text)) {
                Feng.info("只能输入数值型金额");
                return;
            }
            layer.close(index);
        });

    }






}
