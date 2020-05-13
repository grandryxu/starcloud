/**
 * 初始化项目信息详情对话框
 */
var ProjectMasterInfoDlg = {
    projectMasterInfoData: {},
    currentLng: 0,  //当时所选经度
    currentLat: 0,  //当时所选纬度
    currentRadius: 0, //记录此时选择的半径
    projectAddress: [],  //经纬度地址集合
    validateFields: {
        projectName: {
            validators: {
                notEmpty: {
                    message: '项目名称不能为空'
                },
                stringLength: {
                    max: 100,
                    message: '最大长度为100'
                }
            }
        },
        generalContractorCode: {
            validators: {
                stringLength: {
                    max: 40,
                    message: '最大长度为40'
                }
            }
        },
        ownerName: {
            validators: {
//                notEmpty: {
//                    message: '建设单位名称不能为空'
//                },
                stringLength: {
                    max: 80,
                    message: '最大长度为80'
                },
                regexp: {
                    regexp: /^[a-zA-Z]|[\u4e00-\u9fa5]{1,80}$/,
                    message: '格式不正确'
                }
            }
        },
        builderLicenceNum: {
            validators: {
                stringLength: {
                    max: 50,
                    message: '最大长度为50'
                },
                regexp: {
                    regexp: /^[a-zA-Z0-9]{1,50}$/,
                    message: '格式不正确'
                }
            }
        },
        buildingArea: {
            validators: {
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                },
                regexp: {
                    regexp: /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,4})))$/,
                    message: '请输入正确的建筑面积,保留四位小数'
                }
            }
        },
        startDate: {
            validators: {
                notEmpty: {
                    message: '开工时间不能为空'
                }
            }
        },
        projectSource: {
            validators: {
                stringLength: {
                    max: 20,
                    message: '最大长度为20'
                }
            }
        },
        address: {
            validators: {
//                notEmpty: {
//                    message: '项目地址不能为空'
//                },
                stringLength: {
                    max: 255,
                    message: '最大长度为255'
                }
            }
        },
        generalContractorName: {
            validators: {
                stringLength: {
                    max: 100,
                    message: '最大长度为100'
                }
            }
        },
        contractorOrgCode: {
            validators: {
                notEmpty: {
                    message: '承包单位组织机构代码不能为空'
                },
                stringLength: {
                    max: 10,
                    message: '最大长度为10'
                }
            }
        },
        contractorOrgCode: {
            validators: {
                notEmpty: {
                    message: '承包单位组织机构代码不能为空'
                },
                stringLength: {
                    max: 10,
                    message: '最大长度为10'
                }
            }
        },
        buildCorporationCode: {
            validators: {
//                notEmpty: {
//                    message: '建设单位社会信用代码不能为空'
//                },
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                },
                regexp: {
                    regexp: /[0-9A-Z]{18}/,
                    message: '请输入正确的社会信用代码'
                }
            }
        },
        totalContractAmt: {
            validators: {
//                notEmpty: {
//                    message: '承包合同额不能为空'
//                },
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                },
                regexp: {
                    regexp: /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/,
                    message: '请输入正确的承包合同额,保留两位小数'
                }
            }
        },
        completeDate: {
            validators: {
                notEmpty: {
                    message: '竣工时间不能为空'
                }
            }
        },
        buidContacts: {
            validators: {
                notEmpty: {
                    message: '建设单位联系人不能为空'
                },
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                }
            }
        },
        contractorLeader: {
            validators: {
                notEmpty: {
                    message: '企业分管领导不能为空'
                },
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                }
            }
        },
        projectDirector: {
            validators: {
                notEmpty: {
                    message: '项目总监不能为空'
                },
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                }
            }
        },
        buildPhone: {
            validators: {
                stringLength: {
                    min: 9,
                    max: 20,
                    message: '请输入正确的电话号码'
                },
                notEmpty: {
                    message: '电话号码不能为空'
                }
            }
        },
        contractorLeaderPhone: {
            validators: {
                stringLength: {
                    min: 9,
                    max: 20,
                    message: '请输入正确的电话号码'
                },
                notEmpty: {
                    message: '电话号码不能为空'
                }
            }
        },
        projectDirectorPhone: {
            validators: {
                stringLength: {
                    min: 9,
                    max: 20,
                    message: '请输入正确的电话号码'
                },
                notEmpty: {
                    message: '电话号码不能为空'
                }
            }
        },
        personLiable: {
            validators: {
                stringLength: {
                    min: 9,
                    max: 20,
                    message: '请输入正确的电话号码'
                },
                notEmpty: {
                    message: '电话号码不能为空'
                }
            }
        },
        radius: {
            validators: {
                stringLength: {
                    max: 20,
                    message: '最大长度为20'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
ProjectMasterInfoDlg.clearData = function () {
    this.projectMasterInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectMasterInfoDlg.set = function (key, val) {
    this.projectMasterInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectMasterInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProjectMasterInfoDlg.close = function () {
    parent.layer.close(window.parent.ProjectMaster.layerIndex);
}



/**
 * 收集数据
 */
ProjectMasterInfoDlg.collectData = function () {
    this
        .set('id')
        //.set('projectCode')
        //.set('buildProjectCode')
        .set('contractorOrgCode')
        .set('generalContractorCode')
        .set('projectName')
        .set('projectActivityType')
        .set('projectDescription')
        .set('projectCategory')
        .set('isMajorProject')
        .set('ownerName')
        .set('buildCorporationCode')
        .set('builderLicenceNum')
        .set('areaCode')
        .set('totalContractAmt')
        .set('buildingArea')
        .set('startDate')
        .set('completeDate')
        .set('projectSource')
        .set('projectStatus')
        .set('lng')
        .set('lat')
        .set('address')
        .set('deviceType')
        .set('status')
        .set('buidContacts')
        .set('buildPhone')
        .set('contractorLeader')
        .set('contractorLeaderPhone')
        .set('projectDirector')
        .set('projectDirectorPhone')
        .set('isCivilization')
        .set('isHighGrade')
        .set('isGovernment')
        .set('personLiable')
        .set('isDel');
}


function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
        event.target).parents("#menuContent").length > 0)) {
        ProjectMasterInfoDlg.hideDeptSelectTree();
    }
}

ProjectMasterInfoDlg.hideDeptSelectTree = function () {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
};

ProjectMasterInfoDlg.showDeptSelectTree = function () {
    var cityObj = $("#citySel");
    var cityOffset = $("#citySel").offset();
    $("#menuContent").css({
        left: cityOffset.left + "px",
        top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
};

/**
 * 点击部门input框时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
ProjectMasterInfoDlg.onClickDept = function (e, treeId, treeNode) {
    $("#citySel").attr("value", instance.getSelectedVal());
    $("#deptid").attr("value", treeNode.id);
};

/**
 * 提交添加
 */
ProjectMasterInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();
 /*   if (!$("#generalContractorName").val()) {
        Feng.info("请选择承包商！");
        return;
    }*/
/*    if ($("#startDate").val()==="") {
        Feng.error("开工时间不能为空！");
    }*/
    if (!this.validate()) {
        return;
    }

    if ($("#startDate").val()&&$("#completeDate").val()&&$("#startDate").val() >= $("#completeDate").val()) {
        Feng.error("开工时间不能大于结束时间！");
        return false;
    }

    if ($("#deviceType").val() === "") {
        Feng.error("请选择考勤方式！");
        return false;
    }

    if ($("#provinceCode option:selected").data("code") === 0) {
        Feng.info("请选择项目所在地");
        return;
    }
    ;

    if (typeof($("#areaCode option:selected").data("code")) === "undefined") {
        if (typeof($("#cityCode option:selected").data("code")) === "undefined") {
            this.projectMasterInfoData.areaCode = $("#provinceCode option:selected").data("code");
        } else {
            this.projectMasterInfoData.areaCode = $("#cityCode option:selected").data("code");
        }
    } else {
        this.projectMasterInfoData.areaCode = $("#areaCode option:selected").data("code");
    }

    if (this.projectMasterInfoData.areaCode === "") {
        Feng.error("项目行政区划不能为空！");
        return false;
    }

    /*    if ($("#deviceType").val() === '1' || $("#deviceType").val() === '3' || $("#deviceType").val() === '6') {
            //安全帽  考勤+定位
            if (ProjectMasterInfoDlg.projectAddress.length === 0) {
                Feng.error("选择考勤范围!");
                return;
            }
        }*/
    if (ProjectMasterInfoDlg.projectAddress.length === 0) {
        Feng.error("选择考勤范围!");
        return;
    }

    //获取参建单位信息
    /*   var result = ProjectMasterInfoDlg.getJoinedSubContractors();
       if(result.flag === false){
           return;
       }
       this.projectMasterInfoData.joinedSubContractorList = result.joinedSubContractorList;*/
    ProjectMasterInfoDlg.projectMasterInfoData.projectCode = "PROJECT" + new Date().getTime();
    ProjectMasterInfoDlg.projectMasterInfoData.buildProjectCode = ProjectMasterInfoDlg.projectMasterInfoData.projectCode;
    ProjectMasterInfoDlg.projectMasterInfoData.generalContractorName = $("#generalContractorName option:selected").html();
    //项目地址
    ProjectMasterInfoDlg.projectMasterInfoData.projectAddressList = ProjectMasterInfoDlg.projectAddress;
    //是否同步到SMZ
    ProjectMasterInfoDlg.projectMasterInfoData.isSynchro = $("input[name='isSynchro']:checked").val();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/projectMaster/add", function (data) {
        $(".operation").hide();
        Feng.success("添加成功!");
        $("#projectCode").val(data.projectCode)
        $("#projectInfo input").attr("disabled", "disabled");
        $("#projectInfo select").attr("disabled", "disabled");
        $('#generalContractorName').prop('disabled', true).trigger("chosen:updated");
        $("#joinedSubContractorTab").show();
        $("#toJoinedSubContractor").click();
        window.parent.ProjectMaster.table.refresh();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.projectMasterInfoData);
    var isSynchro = $("input[name='isSynchro']:checked").val();
    var message = "";
    if (isSynchro === '0') {
        message = '确认新增项目?';

    } else {
        message = '确认新增项目?';
    }
    layer.confirm(message, {
        btn: ['确定', '取消'] //按钮

    }, function (index) {
        layer.close(index);
        ajax.start();
        ProjectMasterInfoDlg.close();
    });
}


/**
 * 验证数据是否为空
 */
ProjectMasterInfoDlg.validate = function () {
    $('#ProjectMasterInfoForm').data("bootstrapValidator").resetForm();
    $('#ProjectMasterInfoForm').bootstrapValidator('validate');
    return $("#ProjectMasterInfoForm").data('bootstrapValidator').isValid();
};

//承包商名称改变事件
ProjectMasterInfoDlg.generalContractorNameChange = function () {
    var value = $("#generalContractorName").val();
    $("#contractorOrgCode").val(value);
    $("#generalContractorCode").val(value);
}
/**
 * 提交修改
 */
ProjectMasterInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    if ($("#startDate").val() >= $("#completeDate").val()) {
        Feng.error("开工时间不能大于结束事件！");
        return false;
    }

    this.projectMasterInfoData.areaCode = $("#areaCode").find("option[selected]").data("code");
    if (this.projectMasterInfoData.areaCode === "") {
        Feng.error("项目行政区划不能为空！");
        return false;
    }
    this.projectMasterInfoData.isSynchro = $("input[name='isSynchro']:checked").val();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/projectMaster/update", function (data) {
        Feng.success("修改成功!");
        window.parent.ProjectMaster.table.refresh();
        ProjectMasterInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    
    ajax.set(this.projectMasterInfoData);
    ajax.start();
}


/**
 * 删除参建单位
 */
ProjectMasterInfoDlg.removeJoinedSubContractor = function (e) {
    e.parentNode.parentNode.removeChild(e.parentNode);
}

//考勤方式值变动事件
ProjectMasterInfoDlg.deviceTypeChange = function () {
    if ($("#deviceType").val() === '1' || $("#deviceType").val() === '3' || $("#deviceType").val() === '6') {
        //定位
        $("#lngFlag").show();
        $("#latFlag").show();
        $("#radiusFlag").show();
    } else {
        //其他
        $("#lngFlag").hide();
        $("#latFlag").hide();
        $("#radiusFlag").hide();
    }
}


/**
 * 双击查看评价
 */
ProjectMasterInfoDlg.openConstructionEvaluateDetail = function (e) {


    //console.log($("#projectCode").val());
    var index = layer.open({
        type: 2,
        title: '企业详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/constructionEvaluate?organizationCode=' + e.organizationCode+'&projectCode='+$("#projectCode").val()
    });
    this.layerIndex = index;
};

/**
 * 锚点验证
 */
ProjectMasterInfoDlg.anchorCheck = function () {
    var projectCode = $("#projectCode").val();
    if (projectCode === "" || projectCode === null) {
        Feng.error("请先添加项目");
        return false;
    } else {
        var defaultColunms = ProjectSubContractor.initColumn();
        var table = new BSTable(ProjectSubContractor.id, "/projectSubContractor/list?projectCode=" + projectCode, defaultColunms);
        table.onDblClickRow = ProjectMasterInfoDlg.openConstructionEvaluateDetail;
        table.setPaginationType("server");
        ProjectSubContractor.table = table.init();
    }
};

//考勤范围变化
ProjectMasterInfoDlg.radiusChange = function () {
    map.getRadius($("#radius").val());
};

//考勤地图
ProjectMasterInfoDlg.mapConfirm = function () {
    ProjectMasterInfoDlg.close();
}

/*不是南通市隐藏*/
$("#cityCode").change(function () {

    if ($("#cityCode").val()=="南通市"){
        $("#smz").show();
    }else {
        $("#smz").hide();
    }


});
/*不是江苏省隐藏*/
$("#provinceCode").change(function () {

    if ($("#provinceCode").val()!="江苏省") {
        $("#smz").hide();

    }
});



$(function () {

    $("#smz").hide();

    Feng.initChosen();
    //地图dialog
    $("#radius").focus(function () {
        var index = layer.open({
            type: 1,
            title: '考勤范围',
            area: ['80%', '60%'], //宽高
            fix: false, //不固定
            maxmin: false,
            closeBtn: 1,
            shade: false,
            content: $('#layer-baidu-map'),
            btn: ['确认'],
            btn1: function (index, layero) {
                $("#radius").val("已选地址" + ProjectMasterInfoDlg.projectAddress.length + "个");
                layer.close(index);
            },
            cancel: function () {
                //右上角关闭回调
                $("#radius").val("已选地址" + ProjectMasterInfoDlg.projectAddress.length + "个");
            }
        });
    });


    var ztree = new $ZTree("treeDemo", "/dept/treeSelect");
    ztree.bindOnClick(ProjectMasterInfoDlg.onClickDept);
    ztree.init();
    instance = ztree;

    //页面初始化的时候隐藏经纬度必填*
    $("#lngFlag").hide();
    $("#latFlag").hide();
    $("#radiusFlag").hide();

    //如果新增页面 隐藏跳转到参建单位
    if ($("#projectCode").val() === '') {
        $("#joinedSubContractorTab").hide();
    } else {
        $("#joinedSubContractorTab").show();
    }

    var currDate = new Date();
    var startDate = laydate.render({
        elem: '#startDate',
        done: function (value, date) {
            if (value !== '') {
                completeDate.config.min.year = date.year;
                completeDate.config.min.month = date.month - 1;
                completeDate.config.min.date = date.date;
            } else {
                completeDate.config.min.year = '';
                completeDate.config.min.month = '';
                completeDate.config.min.date = '';
            }
        }
    });
    //设置结束时间
    var completeDate = laydate.render({
        elem: '#completeDate',
        done: function (value, date) {
            if (value !== '') {
                startDate.config.max.year = date.year;
                startDate.config.max.month = date.month - 1;
                startDate.config.max.date = date.date;
            } else {
                startDate.config.max.year = currDate.getFullYear();
                startDate.config.max.month = currDate.getMonth() + 1;
                startDate.config.max.date = currDate.getDate();
            }
        }
    });

    var queryStartDate = laydate.render({
        elem: '#queryStartDate',
        done: function (value, date) {
            if (value !== '') {
                queryEndDate.config.min.year = date.year;
                queryEndDate.config.min.month = date.month - 1;
                queryEndDate.config.min.date = date.date;
            } else {
                queryEndDate.config.min.year = '';
                queryEndDate.config.min.month = '';
                queryEndDate.config.min.date = '';
            }
        }
    });
    //设置结束时间
    var queryEndDate = laydate.render({
        elem: '#queryEndDate',
        done: function (value, date) {
            if (value !== '') {
                queryStartDate.config.max.year = date.year;
                queryStartDate.config.max.month = date.month - 1;
                queryStartDate.config.max.date = date.date;
            } else {
                queryStartDate.config.max.year = currDate.getFullYear();
                queryStartDate.config.max.month = currDate.getMonth() + 1;
                queryStartDate.config.max.date = currDate.getDate();
            }
        }
    });

    var queryStartDate1 = laydate.render({
        elem: '#queryStartDate1',
        done: function (value, date) {
            if (value !== '') {
                queryEndDate1.config.min.year = date.year;
                queryEndDate1.config.min.month = date.month - 1;
                queryEndDate1.config.min.date = date.date;
            } else {
                queryEndDate1.config.min.year = '';
                queryEndDate1.config.min.month = '';
                queryEndDate1.config.min.date = '';
            }
        }
    });
    //设置结束时间
    var queryEndDate1 = laydate.render({
        elem: '#queryEndDate1',
        done: function (value, date) {
            if (value !== '') {
                queryStartDate1.config.max.year = date.year;
                queryStartDate1.config.max.month = date.month - 1;
                queryStartDate1.config.max.date = date.date;
            } else {
                queryStartDate1.config.max.year = currDate.getFullYear();
                queryStartDate1.config.max.month = currDate.getMonth() + 1;
                queryStartDate1.config.max.date = currDate.getDate();
            }
        }
    });

    //重置的时间集合
    ProjectMasterInfoDlg.resetDate = [queryStartDate, queryStartDate1, queryEndDate, queryEndDate1];

    Feng.initValidator("ProjectMasterInfoForm", ProjectMasterInfoDlg.validateFields);
    //Feng.initStartEndDate();
    initTeam();

    if ($("#pageType").val() === 'view') {
        ProjectMasterInfoDlg.projectAddress = JSON.parse($("#projectAddressList").val());
        $("#radius").val("已选地址" + ProjectMasterInfoDlg.projectAddress.length + "个");
        if (ProjectMasterInfoDlg.projectAddress.length > 0) {
            var map = new BMap.Map("container");          // 创建地图实例
            var point = new BMap.Point(ProjectMasterInfoDlg.projectAddress[0].lng, ProjectMasterInfoDlg.projectAddress[0].lat);  // 创建点坐标
            map.addControl(new BMap.NavigationControl()); // 添加平移缩放控件
            map.addControl(new BMap.ScaleControl()); // 添加比例尺控件
            map.addControl(new BMap.OverviewMapControl()); //添加缩略地图控件
            map.centerAndZoom(point, 15);
            map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
            //重绘地图
            for (var i = 0; i < ProjectMasterInfoDlg.projectAddress.length; i++) {
                var lng = ProjectMasterInfoDlg.projectAddress[i].lng;
                var lat = ProjectMasterInfoDlg.projectAddress[i].lat;
                var radius = ProjectMasterInfoDlg.projectAddress[i].radius;
                var address = ProjectMasterInfoDlg.projectAddress[i].address;
                loadAddress(map, lng, lat, radius);

                //设置文本
                var opts = {
                    position: new BMap.Point(lng, lat),    // 指定文本标注所在的地理位置
                    offset: new BMap.Size(-address.length * 12 / 2, -10)    //设置文本偏移量
                }
                var label = new BMap.Label(address, opts);  // 创建文本标注对象
                label.setStyle({
                    width: 100,
                    color: "white",
                    fontSize: "12px",
                    height: "20px",
                    lineHeight: "20px",
                    fontFamily: "微软雅黑",
                    background: "#4680ff",
                    border: 0
                });
                map.addOverlay(label);
            }
        } else {
            $("#container").hide();
        }
    }
});

function resetDate() {
    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = currentDate.getMonth() + 1;
    var date = currentDate.getDate();

    for (var i = 0; i < ProjectMasterInfoDlg.resetDate.length; i++) {
        var dateObject = ProjectMasterInfoDlg.resetDate[i];

        //去除最小时间限制
        dateObject.config.min.year = '';
        dateObject.config.min.month = '';
        dateObject.config.min.date = '';

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

//centre:椭圆中心点,X:横向经度,Y:纵向纬度
function add_oval(centre, x, y) {
    var assemble = new Array();
    var angle;
    var dot;
    var tangent = x / y;
    for (i = 0; i < 36; i++) {
        angle = (2 * Math.PI / 36) * i;
        dot = new BMap.Point(centre.lng + Math.sin(angle) * y * tangent, centre.lat + Math.cos(angle) * y);
        assemble.push(dot);
    }
    return assemble;
}

/************************************************ 班组列表 *************************************************************/
/**
 * 初始化班组表格的列
 */
var initColumn = function () {
    return [
        {field: 'selectItem', radio: true, visible: false},
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle',},
        {title: '班组编号', field: 'teamSysNo', visible: true, align: 'center', valign: 'middle'},
        {title: '参建公司', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '班组名称', field: 'teamName', visible: true, align: 'center', valign: 'middle'},
        {title: '联系人', field: 'teamLeader', visible: true, align: 'center', valign: 'middle'},
        {title: '联系电话', field: 'contact', visible: true, align: 'center', valign: 'middle'},
    ];
};


var initTeam = function () {
    Feng.initChosen();
    laydate.render({elem: '#startDate'});
    laydate.render({elem: '#completeDate'});
    $(".lng-lat-required").hide();
    var defaultColunms = initColumn();
    var table = new BSTable("TeamMasterTable", "/teamMaster/list?projectCode=" + $("#projectCode").val(), defaultColunms);
    table.setPaginationType("server");
    table.init();
}


//********百度电子围栏**********//
var map = new BMap.Map("workAttendanceBaiduMap");
//创建Map实例
map.centerAndZoom("南通市", 15);
map.addControl(new BMap.NavigationControl()); // 添加平移缩放控件
map.addControl(new BMap.ScaleControl()); // 添加比例尺控件
map.addControl(new BMap.OverviewMapControl()); //添加缩略地图控件
//初始化地图,设置中心点坐标和地图级别
map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

map.addEventListener("click", function (e) {
    if ($("#semidiameter").val().trim() === '') {
        Feng.error("请填写半径!");
        return;
    }

    if (!new RegExp("^[0-9]*$").test($("#semidiameter").val().trim())) {
        Feng.info("半径的格式应为正整数");
        return;
    }

    map.clearOverlays();
    //添加编辑圆操作
    //单击地图，形成折线覆盖物
    $("#lng").val(e.point.lng);
    $("#lat").val(e.point.lat);

    //加载地图
    loadAddress(map, e.point.lng, e.point.lat, $("#semidiameter").val());

    //记录此时所选经纬度
    ProjectMasterInfoDlg.currentLng = e.point.lng;
    ProjectMasterInfoDlg.currentLat = e.point.lat;
    ProjectMasterInfoDlg.currentRadius = $("#semidiameter").val();

    //重绘已保存的地图
    reloadAddress();
});

//加载单个地点
function loadAddress(map, lng, lat, radius) {
    var newpoint = new BMap.Point(lng, lat);
    var circle = new BMap.Circle(newpoint, radius);  //创建遮罩
    map.addOverlay(circle); //增加遮罩
    var marker = new BMap.Marker(newpoint); // 创建标注
    marker.enableDragging();
    map.addOverlay(marker);              // 将标注添加到地图中


    // 开启marker拖拽监听事件
    marker.addEventListener("dragend", function (e) {
        //clearOverlays 会清除所有的标注 marker circle
        map.clearOverlays();
        var x = e.point.lng; //经度
        var y = e.point.lat; //纬度
        //记录此时所选经纬度
        ProjectMasterInfoDlg.currentLng = x;
        ProjectMasterInfoDlg.currentLat = y;
        ProjectMasterInfoDlg.currentRadius = $("#semidiameter").val();
        //创建遮罩
        var point = new BMap.Point(x, y);
        var c = new BMap.Circle(point, radius);
        map.addOverlay(c);
        marker.setPosition(point); // 创建标注
        map.addOverlay(marker); // 将标注添加到地图中
        //重绘已保存的地图
        reloadAddress();
    });
}


//确认地址
ProjectMasterInfoDlg.confirmAddress = function () {
    //判断是否相离
    for (var i = 0; i < ProjectMasterInfoDlg.projectAddress.length; i++) {
        var radiusSum = Number(ProjectMasterInfoDlg.projectAddress[i].radius) + Number(ProjectMasterInfoDlg.currentRadius);
        var distance = map.getDistance(new BMap.Point(ProjectMasterInfoDlg.projectAddress[i].lng, ProjectMasterInfoDlg.projectAddress[i].lat), new BMap.Point(ProjectMasterInfoDlg.currentLng, ProjectMasterInfoDlg.currentLat));
        if (radiusSum >= distance) {
            Feng.info("项目工地范围不能重复");
            return;
        }
    }

    layer.prompt({
        title: '工地地址',
        formType: 0,
        yes: function (index, layero) {
            // 获取文本框输入的值
            var value = layero.find(".layui-layer-input").val();
            if (value === '') {
                Feng.info('请输入工地地址');
                return;
            }
            if (ProjectMasterInfoDlg.currentRadius === 0) {
                Feng.info("请选择地图");
                layer.close(index);
                return;
            }
            layer.close(index);
            var confirmAddress = {};
            confirmAddress.address = value;
            confirmAddress.lng = ProjectMasterInfoDlg.currentLng;
            confirmAddress.lat = ProjectMasterInfoDlg.currentLat;
            confirmAddress.radius = ProjectMasterInfoDlg.currentRadius;
            ProjectMasterInfoDlg.projectAddress.push(confirmAddress);

            //重载地图
            reloadAddress();
            Feng.success("添加成功!");
            $("#semidiameter").val("");
        }
    });


}

//根据projectAddress重载项目名称
function reloadAddressNames() {
    var addressNames = "";
    for (var i = 0; i < ProjectMasterInfoDlg.projectAddress.length; i++) {
        addressNames +=
            "<div class='col-sm-4'  style='padding-left:0;margin-bottom:5px;'><div style='width:98%;background: #e7e9ed;padding:8px;'>" + ProjectMasterInfoDlg.projectAddress[i].address +
            "<span>&nbsp;&nbsp;" + ProjectMasterInfoDlg.projectAddress[i].radius + "m</span>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)' class='pull-right' onclick='removeAddress(" + i + ")' class='close-link'><i style='color: black;' class='fa fa-times'></i></a>" +
            "</div></div>";
    }
    $("#projectAddress").html(addressNames);
}

//删除地址 并重新加载
function removeAddress(index) {
    ProjectMasterInfoDlg.projectAddress.splice(index, 1);
    map.clearOverlays();
    reloadAddress();
}

//根据projectAddress重载选中地址 重绘已存在list中的数据
function reloadAddress() {
    for (var i = 0; i < ProjectMasterInfoDlg.projectAddress.length; i++) {
        var lng = ProjectMasterInfoDlg.projectAddress[i].lng;
        var lat = ProjectMasterInfoDlg.projectAddress[i].lat;
        var radius = ProjectMasterInfoDlg.projectAddress[i].radius;
        var address = ProjectMasterInfoDlg.projectAddress[i].address;
        //加载单个地点
        var point = new BMap.Point(lng, lat);
        var circle = new BMap.Circle(point, radius);  //创建遮罩
        map.addOverlay(circle); //增加遮罩
        var marker = new BMap.Marker(point); // 创建标注
        map.addOverlay(marker);              // 将标注添加到地图中

        //设置文本
        var opts = {
            position: new BMap.Point(lng, lat),    // 指定文本标注所在的地理位置
            offset: new BMap.Size(-address.length * 12 / 2, 0)    //设置文本偏移量
        }
        var label = new BMap.Label(address, opts);  // 创建文本标注对象
        label.setStyle({
            width: 100,
            color: "white",
            fontSize: "12px",
            height: "20px",
            lineHeight: "20px",
            fontFamily: "微软雅黑",
            background: "#4680ff",
            border: 0
        });
        map.addOverlay(label);
    }

    //重载名称
    reloadAddressNames();
}

var cpoint;

//定位到学校
ProjectMasterInfoDlg.search = function search() {
    var myAddress = $("#searchValue").val();
    var myGeo = new BMap.Geocoder();
    myGeo.getPoint(myAddress, function (point) {
        //我输入的是“知春路”，第一步getPoint是地址解析。
        if (point) {
            cpoint = point;
            map.centerAndZoom(point, 16);

            map.addOverlay(new BMap.Marker(point));
            myGeo.getLocation(point, function (rs) {
                //这里弹出“知春路”的详细地址信息，第二步getLocation是反地址解析。
                var addComp = rs.addressComponents;
            });
        }
    }, "江苏省");
}

ProjectMasterInfoDlg.clearInfo = function clearInfo() {
    $('#searchValue').val("");
    $('#semidiameter').val("");
}
//********百度电子围栏**********//

