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
//                notEmpty: {
//                    message: '开工时间不能为空'
//                }
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
                    regexp: /(^[1-9](\d+)?(\.\d{1,6})?$)|(^0$)|(^\d\.\d{1,6}$)/,
                    message: '请输入正确的承包合同额,保留六位小数'
                }
            }
        },
        completeDate: {
            validators: {
//                notEmpty: {
//                    message: '竣工时间不能为空'
//                }
            }
        },
        buidContacts: {
            validators: {
//                notEmpty: {
//                    message: '建设单位联系人不能为空'
//                },
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                }
            }
        },
        contractorLeader: {
            validators: {
//                notEmpty: {
//                    message: '企业分管领导不能为空'
//                },
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                }
            }
        },
        projectDirector: {
            validators: {
//                notEmpty: {
//                    message: '项目总监不能为空'
//                },
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                }
            }
        },
        buildPhone: {
            validators: {
//                notEmpty: {
//                    message: '建设单位联系电话不能为空'
//                },
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                },
                regexp: {
                    regexp:  /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/,
                    message: '请输入正确的联系人手机号码'
                }
            }
        },
        contractorLeaderPhone: {
            validators: {
//                notEmpty: {
//                    message: '企业分管领导电话不能为空'
//                },
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                },
                regexp: {
                    regexp:  /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/,
                    message: '请输入正确的联系人手机号码'
                }
            }
        },
        projectDirectorPhone: {
            validators: {
//                notEmpty: {
//                    message: '项目总监联系电话不能为空'
//                },
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                },
                regexp: {
                    regexp:  /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/,
                    message: '请输入正确的联系人手机号码'
                }
            }
        },
        personLiable: {
            validators: {
//                notEmpty: {
//                    message: '责任人联系电话不能为空'
//                },
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                },
                regexp: {
                    regexp:  /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/,
                    message: '请输入正确的联系人手机号码'
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
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectMasterInfoDlg.set = function (key, val) {
    this.projectMasterInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
};

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
};


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
        .set('isDel')
        .set('oldDeviceType')

    ;
};

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
};

/**
 * 提交修改
 */
ProjectMasterInfoDlg.editSubmit = function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    if ($("#startDate").val()&&$("#completeDate").val()&&$("#startDate").val() >= $("#completeDate").val()) {
        Feng.error("开工时间不能大于结束时间！");
        return false;
    }

    if($("#provinceCode option:selected").data("code") === 0){
        Feng.info("请选择项目所在地");
        return;
    };

    if($("#generalContractorName").val() === null ||$("#generalContractorName").val()===""){
        Feng.error("请选择承包商")
        return  false ;
    };

 /*   if ($("#deviceType").val() === '1' || $("#deviceType").val() === '3' || $("#deviceType").val() === '6') {
        //安全帽  考勤+定位
        if (ProjectMasterInfoDlg.projectAddress.length === 0) {
            Feng.error("选择考勤范围!");
            return;
        }
    }*/
//    if (ProjectMasterInfoDlg.projectAddress.length === 0) {
//        Feng.error("选择考勤范围!");
//        return;
//    }

    if (typeof($("#areaCodes option:selected").data("code")) === "undefined") {
        if (typeof($("#cityCode option:selected").data("code")) === "undefined") {
            this.projectMasterInfoData.areaCode = $("#provinceCode option:selected").data("code");
        } else {
            this.projectMasterInfoData.areaCode = $("#cityCode option:selected").data("code");
        }
    } else {
        this.projectMasterInfoData.areaCode = $("#areaCodes option:selected").data("code");
    }

    /*if (this.projectMasterInfoData.areaCode == "" || typeof(this.projectMasterInfoData.areaCode) === "undefined") {
        Feng.error("项目行政区划不能为空！");
        return false;
    }*/
    //项目地址
    ProjectMasterInfoDlg.projectMasterInfoData.generalContractorName= $("#generalContractorName").next().find("span")[0].innerHTML;
    ProjectMasterInfoDlg.projectMasterInfoData.projectAddressList = ProjectMasterInfoDlg.projectAddress;
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
};


/**
 * 删除参建单位
 */
ProjectMasterInfoDlg.removeJoinedSubContractor = function (e) {
    e.parentNode.parentNode.removeChild(e.parentNode);
};

//考勤方式值变动事件
ProjectMasterInfoDlg.deviceTypeChange = function () {
    if ($("#deviceType").val() === '1' || $("#deviceType").val() === '3' || $("#deviceType").val() === '6') {
        //定位
        $("#lngFlagEdit").show();
        $("#latFlagEdit").show();
    } else {
        //其他
        $("#lngFlagEdit").hide();
        $("#latFlagEdit").hide();
    }
};


/**
 * 双击查看评价
 */
ProjectMasterInfoDlg.openConstructionEvaluateDetail = function (e) {
    var index = layer.open({
        type: 2,
        title: '企业详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/constructionEvaluate?organizationCode=' + e.organizationCode
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
    }else if ($("#cityCode").val()=="南通市"){
        $("#smz").show();
    }
});

$(function () {


/*    if ($("#cityCode").val()=="南通市") {
        $("#smz").show();
    }else if ($("#cityCode").val()!="南通市") {
        $("#smz").hide();
    }*/
   /* if ($("#cityCode").val()=="南通市") {
        $("#smz").show();
    }*/
    var classProjectName = $("#classList #projectName").html().trim();
    if(classProjectName.length>30){
        $("#classList #projectName").attr("title",classProjectName);
        $("#classList #projectName").html(classProjectName.substring(0,30)+"...");
    }

    laydate.render({elem: '#startDate', max: 0});
    laydate.render({elem: '#completeDate'});
    laydate.render({elem: '#queryStartDate', max: 0});
    laydate.render({elem: '#queryEndDate', max: 0});
    laydate.render({elem: '#queryStartDate1', max: 0});
    laydate.render({elem: '#queryEndDate1', max: 0});
    Feng.initValidator("ProjectMasterInfoForm", ProjectMasterInfoDlg.validateFields);

    var ajax = new $ax(Feng.ctxPath + "/subContractor/getArea", function (data) {
        if (data.provinceName || data.cityName || data.districtName) {
            //初始化三级联动
            $("#distpicker").distpicker({
                province: typeof(data.provinceName) === "undefined" ? "" : data.provinceName,
                city: typeof(data.cityName) === "undefined" ? "" : data.cityName,
                district: typeof(data.districtName)==="undefined"?"":data.districtName
            });
        } else {
            $("#distpicker").distpicker();
        }
    }, function (data) {
    });
    ajax.set({"areaId": $("#areaId").val() + ""});
    ajax.start();

    //地图dialog
    $("#radius").focus(function () {
        var index = layer.open({
            type: 1,
            title: '考勤范围',
            area: ['80%', '80%'], //宽高
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
            },
            success: function () {
                //与 layer控件有冲突 延迟加载
                setTimeout(function () {
                    reloadAddress();
                }, 500);
            }
        });
    });

    initTeam();
    initMap($("#lng").val(), $("#lat").val(), $("#radius").val());


    if ($("#cityCode").val()=="南通市") {
        $("#smz").show();
    }else if ($("#cityCode").val()!="南通市") {
        $("#smz").hide();
    }


});

/**
 * 初始化百度地图
 * @param lng
 * @param lat
 * @param radius  半径
 */
var map;

function initMap(lng, lat, radius) {
    map = new BMap.Map("workAttendanceBaiduMap");          // 创建地图实例
    // var point,grade;
    // if(lng==="" && lat===""){
    //     point=new BMap.Point(116.404, 39.915);
    //     grade=8;
    // }else{
    //     point = new BMap.Point(lng,lat);  // 创建点坐标
    //     grade=15
    // }


    /**
     * 为地图添加点击事件:重新画圆
     */
    map.addEventListener("click", function (e) {

        if ($("#semidiameter").val().trim() === '') {
            Feng.error("请填写半径!");
            return;
        }

        if(!new RegExp("^[0-9]*$").test($("#semidiameter").val().trim())){
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
    ProjectMasterInfoDlg.projectAddress = JSON.parse($("#projectAddressList").val());
    $("#radius").val("已选地址" + ProjectMasterInfoDlg.projectAddress.length + "个");
    if(ProjectMasterInfoDlg.projectAddress.length>0) {
        map.centerAndZoom(new BMap.Point(ProjectMasterInfoDlg.projectAddress[0].lng, ProjectMasterInfoDlg.projectAddress[0].lat), 15);
    }else{
        map.centerAndZoom("南通市", 15);
    }
    map.addControl(new BMap.NavigationControl()); // 添加平移缩放控件
    map.addControl(new BMap.ScaleControl()); // 添加比例尺控件
    map.addControl(new BMap.OverviewMapControl()); //添加缩略地图控件
    //初始化地图,设置中心点坐标和地图级别
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
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


//加载单个地点
function loadAddress(map,lng,lat,radius) {
    var newpoint = new BMap.Point(lng, lat);
    var circle = new BMap.Circle(newpoint,radius);  //创建遮罩
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
        var c = new BMap.Circle(point,radius);
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
function reloadAddress(){
    for(var i=0; i<ProjectMasterInfoDlg.projectAddress.length;i++){
        var lng = ProjectMasterInfoDlg.projectAddress[i].lng;
        var lat = ProjectMasterInfoDlg.projectAddress[i].lat;
        var radius = ProjectMasterInfoDlg.projectAddress[i].radius;
        var address = ProjectMasterInfoDlg.projectAddress[i].address;
        //加载单个地点
        var point = new BMap.Point(lng, lat);
        var circle = new BMap.Circle(point,radius);  //创建遮罩
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
};
