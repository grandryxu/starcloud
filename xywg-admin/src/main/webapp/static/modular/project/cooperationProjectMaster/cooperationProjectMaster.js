/**
 * 专业承包项目
 */
var ProjectMaster = {
    professionProjectTableId: "professionProjectTable",	//表格id
    amateurProjectTableId: "amateurProjectTable",	//表格id
    seItem: null,		//选中的条目
    amateurProjectTabletTable: null,
    professionProjectTable: null,
    layerIndex: -1,
    data: [],
    dataful: [],
    ch: 1
};
/**
 * 初始化表格的列
 */
ProjectMaster.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目编号', field: 'projectCode', visible: true, align: 'center', valign: 'middle'},
        {title: '参建公司名称', field: 'cCompanyName', visible: true, align: 'center', valign: 'middle'},
        {title: '参建公司社会信用代码', field: 'cOrganizationCode', visible: true, align: 'center', valign: 'middle'},
        {title: '承包单位名称', field: 'generalContractorName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目分类', field: 'projectCategoryName', visible: true, align: 'center', valign: 'middle'},
        {title: '考勤方式', field: 'deviceTypeName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '开工时间', field: 'startDate', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
               // return value.substring(0, 10);
                return value;
            }
        },
        {
            title: '竣工时间', field: 'completeDate', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                //return value.substring(0, 10);
                return value;
            }
        },
        {title: '项目状态', field: 'projectStatusName', visible: true, align: 'center', valign: 'middle'},
        {title: '合同查看', field:'psId' , visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                if(row.fileNums === "0" ){
                    return "<label>暂无合同</label>";
                }else{
                    return "<a href='javascript:void(0)' onclick='ProjectMaster.showPdf("+ value +");'>查看</a>";
                }
            }
        },
        {
            title: '操作', visible: true, align: 'center', valign: 'middle', formatter: function (val, row, index) {
            return "<a href='javascript:void(0)' data-toggle='tooltip' title='上传合同,仅限PDF格式'><i class='fa fa-upload text-blue' onClick='uploadLaborContract(" + index + ")'></i></a>";
        }
        },

    ];
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
        content: Feng.ctxPath + '/cooperationProjectMaster/cooperationProjectMaster_add'
    });
    layer.full(index);
    this.layerIndex = index;
};

/**
 * 专业承包上传劳动合同
 */
/*ProjectMaster.uploadLaborContract1 = function () {
    if (this.check()) {
        $("#excelFile1").trigger("click");
    }
};*/

/**
 * 上传劳动合同
 */
function uploadLaborContract(index) {
    var chdata = ProjectMaster.ch;
    if (chdata == 1) {
        //专业承包
        ProjectMaster.seItem = ProjectMaster.data[index];
        $('#excelFile1').val("");
        $("#excelFile1").trigger("click");

    } else if (chdata == 2) {
        //非专业承包
        ProjectMaster.seItem = ProjectMaster.dataful[index];
        $('#excelFile2').val("");
        $("#excelFile2").trigger("click");

    }
};


/**
 * 非专业承包上传劳动合同
 */
/*ProjectMaster.uploadLaborContract2 = function () {
    if (this.check()) {
        $("#excelFile2").trigger("click");
    }
};*/

/**
 * 上传劳动合同 导入
 */
function fileUpload() {
    var projectCode = ProjectMaster.seItem.projectCode;
    var cOrganizationCode = ProjectMaster.seItem.cOrganizationCode;
    var myform = null;
    if(ProjectMaster.ch == 1){
        if($('#excelFile1')[0].files.length === 0){
            return;
        }
        var maxsize = 100*1024*1024;
        var filesize = document.getElementById("excelFile1").files[0].size;
        if(filesize>maxsize) {
            Feng.error("上传的附件文件不能超过100M！！");
            return;
        }
        myform = new FormData($("#form1")[0]);
    }else if(ProjectMaster.ch == 2 ){
        if($('#excelFile2')[0].files.length === 0){
            return;
        }
        var maxsize = 100*1024*1024;
        var filesize = document.getElementById("excelFile2").files[0].size;
        if(filesize>maxsize) {
            Feng.error("上传的附件文件不能超过100M！！");
            return;
        }
        myform = new FormData($("#form2")[0]);
    }
    myform.append('cOrganizationCode', cOrganizationCode);
    myform.append('projectCode', projectCode);
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
                if(ProjectMaster.ch == 1){
                    ProjectMaster.professionProjectTable.refresh();
                    ProjectMaster.ch = 1;
                }else if(ProjectMaster.ch == 2 ){
                    ProjectMaster.amateurProjectTable.refresh();
                    ProjectMaster.ch = 2;
                }
            }
        },
        error: function (data) {
            Feng.error(data.message);
        }
    });
}

/**
 * 页面切换
 */
function pageToggle(pageType) {
    if(pageType === "1"){
        //切换到专业分包页面
        $("#zyfb").click();
        ProjectMaster.professionProjectTable.refresh();
    }else if(pageType === "2"){
        //切换到非专业分包页面
        $("#fzyfb").click();
        ProjectMaster.amateurProjectTable.refresh();
    }
}
/**
 * 上传劳动合同 导入
 */
/*function fileUpload2() {
    var projectCode = ProjectMaster.seItem.projectCode;
    var myform = new FormData();
    myform.append('projectCode', projectCode);
    myform.append('file', $('#excelFile2')[0].files[0]);
    $.ajax({
        url: Feng.ctxPath + "/projectMaster/uploadLaborContract",
        type: "POST",
        data: myform,
        contentType: false,
        processData: false,
        success: function (data) {
            Feng.success(data.message);
        },
        error: function (data) {
            Feng.error(data.message);
        }
    });
}*/

function chfun(chdata) {
    ProjectMaster.ch = chdata;
}

/**
 * 双击查看
 */
ProjectMaster.searchInfoProfession = function (e) {
    var index = layer.open({
        type: 2,
        title: '项目信息详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-detail',
        content: Feng.ctxPath + '/cooperationProjectMaster/cooperationProjectMaster_view?projectMasterId=' + e.id + "&pageType=" + 1
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
        content: Feng.ctxPath + '/cooperationProjectMaster/cooperationProjectMaster_view?projectMasterId=' + e.id + "&pageType=" + 2
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
            skin: 'layer-detail',
            content: Feng.ctxPath + '/cooperationProjectMaster/cooperationProjectMaster_update/' + ProjectMaster.seItem.id
        });
        layer.full(index);
        this.layerIndex = index;
    }
};

/**
 * 删除项目信息
 */
ProjectMaster.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/projectMaster/delete", function (data) {
            Feng.success("删除成功!");
            ProjectMaster.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("projectMasterId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 专业承包查询项目信息列表
 */
ProjectMaster.search1 = function () {
    var queryData = {};
    queryData['condition'] = $("#condition1").val().trim();
    queryData['projectStatus'] = $("#projectStatus1").val();
    queryData['deviceType'] = $('#deviceType1').val();
    queryData['startDate1'] = $('#startDate11').val();
    queryData['endDate1'] = $('#endDate11').val();
    queryData['startDate2'] = $('#startDate12').val();
    queryData['endDate2'] = $('#endDate12').val();
    ProjectMaster.professionProjectTable.refresh({query: queryData});
};

/**
 * 非专业承包查询项目信息列表
 */
ProjectMaster.search2 = function () {
    var queryData = {};
    queryData['condition'] = $("#condition2").val().trim();
    queryData['projectStatus'] = $("#projectStatus2").val();
    queryData['deviceType'] = $('#deviceType2').val();
    queryData['startDate1'] = $('#startDate21').val();
    queryData['endDate1'] = $('#endDate21').val();
    queryData['startDate2'] = $('#startDate22').val();
    queryData['endDate2'] = $('#endDate22').val();
    ProjectMaster.amateurProjectTable.refresh({query: queryData});
};

/**
 * 安全帽管理
 */
ProjectMaster.openSafetyHelmetManager = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '安全帽管理',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/safetyHelmet?projectCode=' + ProjectMaster.seItem.projectCode + '&projectName=' + ProjectMaster.seItem.projectName
        });
        this.layerIndex = index;
    }
};

/**
 * 考勤机管理
 */
ProjectMaster.openDeviceManager = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '考勤机管理',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/device?projectCode=' + ProjectMaster.seItem.projectCode + '&projectName=' + ProjectMaster.seItem.projectName
        });
        this.layerIndex = index;
    }
};
ProjectMaster.onLoadSuccess = function (data) {
    ProjectMaster.data = data.rows;
}
ProjectMaster.onLoadSuccessful = function (data) {
    ProjectMaster.dataful = data.rows;
}

$(function () {
    var currDate = new Date();
    var startDate11 = laydate.render({
        elem: '#startDate11',
        done: function (value, date) {
            if (value !== '') {
                endDate11.config.min.year = date.year;
                endDate11.config.min.month = date.month - 1;
                endDate11.config.min.date = date.date;
            } else {
                endDate11.config.min.year = '';
                endDate11.config.min.month = '';
                endDate11.config.min.date = '';
            }
        }
    });
    //设置结束时间
    var endDate11 = laydate.render({
        elem: '#endDate11',
        done: function (value, date) {
            if (value !== '') {
                startDate11.config.max.year = date.year;
                startDate11.config.max.month = date.month - 1;
                startDate11.config.max.date = date.date;
            } else {
                startDate11.config.max.year = currDate.getFullYear();
                startDate11.config.max.month = currDate.getMonth()+1;
                startDate11.config.max.date =  currDate.getDate();
            }
        }
    });

    var startDate12 = laydate.render({
        elem: '#startDate12',
        done: function (value, date) {
            if (value !== '') {
                endDate12.config.min.year = date.year;
                endDate12.config.min.month = date.month - 1;
                endDate12.config.min.date = date.date;
            } else {
                endDate12.config.min.year = '';
                endDate12.config.min.month = '';
                endDate12.config.min.date = '';
            }
        }
    });
    //设置结束时间
    var endDate12 = laydate.render({
        elem: '#endDate12',
        done: function (value, date) {
            if (value !== '') {
                startDate12.config.max.year = date.year;
                startDate12.config.max.month = date.month - 1;
                startDate12.config.max.date = date.date;
            } else {
                startDate12.config.max.year = currDate.getFullYear();
                startDate12.config.max.month = currDate.getMonth()+1;
                startDate12.config.max.date =  currDate.getDate();
            }
        }
    });

    var startDate21 = laydate.render({
        elem: '#startDate21',
        done: function (value, date) {
            if (value !== '') {
                endDate21.config.min.year = date.year;
                endDate21.config.min.month = date.month - 1;
                endDate21.config.min.date = date.date;
            } else {
                endDate21.config.min.year = '';
                endDate21.config.min.month = '';
                endDate21.config.min.date = '';
            }
        }
    });
    //设置结束时间
    var endDate21 = laydate.render({
        elem: '#endDate21',
        done: function (value, date) {
            if (value !== '') {
                startDate21.config.max.year = date.year;
                startDate21.config.max.month = date.month - 1;
                startDate21.config.max.date = date.date;
            } else {
                startDate21.config.max.year = currDate.getFullYear();
                startDate21.config.max.month = currDate.getMonth()+1;
                startDate21.config.max.date =  currDate.getDate();
            }
        }
    });

    var startDate22 = laydate.render({
        elem: '#startDate22',
        done: function (value, date) {
            if (value !== '') {
                endDate22.config.min.year = date.year;
                endDate22.config.min.month = date.month - 1;
                endDate22.config.min.date = date.date;
            } else {
                endDate22.config.min.year = '';
                endDate22.config.min.month = '';
                endDate22.config.min.date = '';
            }
        }
    });
    //设置结束时间
    var endDate22 = laydate.render({
        elem: '#endDate22',
        done: function (value, date) {
            if (value !== '') {
                startDate22.config.max.year = date.year;
                startDate22.config.max.month = date.month - 1;
                startDate22.config.max.date = date.date;
            } else {
                startDate22.config.max.year = currDate.getFullYear();
                startDate22.config.max.month = currDate.getMonth()+1;
                startDate22.config.max.date =  currDate.getDate();
            }
        }
    });

    //重置的时间集合
    ProjectMaster.resetDate = [startDate11,startDate12,startDate21,startDate22,endDate11,endDate12,endDate21,endDate22];

    Feng.initStartEndDate();
    var defaultColunms = ProjectMaster.initColumn();

    var professionProjectTable = new BSTable(ProjectMaster.professionProjectTableId, "/cooperationProjectMaster/list?pageType=1&projectCode=" + $("#projectCode").val(), defaultColunms);
    professionProjectTable.setPaginationType("server");
    professionProjectTable.onDblClickRow = ProjectMaster.searchInfoProfession;
    professionProjectTable.onLoadSuccess = ProjectMaster.onLoadSuccess;
    ProjectMaster.professionProjectTable = professionProjectTable.init();

    var amateurProjectTable = new BSTable(ProjectMaster.amateurProjectTableId, "/cooperationProjectMaster/list?pageType=2&projectCode=" + $("#projectCode").val(), defaultColunms);
    amateurProjectTable.setPaginationType("server");
    amateurProjectTable.onDblClickRow = ProjectMaster.searchInfo;
    amateurProjectTable.onLoadSuccess = ProjectMaster.onLoadSuccessful;
    ProjectMaster.amateurProjectTable = amateurProjectTable.init();

    //专业分包搜索
    $("#tab-1").on('click','.fn-condition .fn-down',function(){
        $("#tab-1 .fn-need-hide").show();
        $('#tab-1 .fn-condition').hide();
        var html = $('#tab-1 .fn-condition').html();
        $("#tab-1 .fn-buttons .col-sm-4").html(html);
    });

    $("#tab-1").on('click','.fn-buttons .fn-up',function(){
        $("#tab-1 .fn-need-hide").hide();
        $('#tab-1 .fn-condition').show();
    });

    //非专业分包搜索
    $("#tab-2").on('click','.fn-condition .fn-down',function(){
        $("#tab-2 .fn-need-hide").show();
        $('#tab-2 .fn-condition').hide();
        var html = $('#tab-2 .fn-condition').html();
        $("#tab-2 .fn-buttons .col-sm-4").html(html);
    });

    $("#tab-2").on('click','.fn-buttons .fn-up',function(){
        $("#tab-2 .fn-need-hide").hide();
        $('#tab-2 .fn-condition').show();
    });
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
}


