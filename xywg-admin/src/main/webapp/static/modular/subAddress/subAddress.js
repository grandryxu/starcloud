var map = new BMap.Map("subMap");    // 创建Map实例
var projectAddress = [];

//当浏览器窗口大小改变时，设置显示内容的高度
window.onresize = function () {
    //动态设置页面高度
    $("#subMap").height($(window).height());
};

$(function () {
    //up down是事件
    $("#animate-slide-up").on("click",function () {
        $("#animate-slide-up").hide();
        $("#myZoomCtrl-info").hide();
        $("#animate-slide-down").show();
    });
    $("#animate-slide-down").on("click",function () {
        $("#animate-slide-up").show();
        $("#myZoomCtrl-info").show();
        $("#animate-slide-down").hide();
    });
    //动态设置页面高度
    $("#subMap").height($(window).height());
    //下拉多选
    Feng.initChosen();
    // 百度地图API功能
    // 初始化地图,设置中心点坐标和地图级别
    map.centerAndZoom(new BMap.Point(105.5566406250, 48.4608820009), 6);
    //添加地图类型控件
    map.addControl(new BMap.MapTypeControl({
        mapTypes: [
            BMAP_NORMAL_MAP,
            BMAP_HYBRID_MAP
        ]
    }));
    // 设置地图显示的城市 此项是必须设置的
    //map.setCurrentCity("北京");
    //开启鼠标滚轮缩放
    map.enableScrollWheelZoom(true);
    //设置地图边界 西南角 东北角
    var bound = new BMap.Bounds(new BMap.Point(47.9882812500, 6.3152985383), new BMap.Point(171.7382812500, 62.9152330395));
    try {
        BMapLib.AreaRestriction.setBounds(map, bound);
    } catch (e) {
        console.log(e);
    }
    //查询地址
    var ajax = new $ax(Feng.ctxPath + "/projectAddress/getProjectAddressByToggleDeptId", function (data) {
        projectAddress = data;
        //等控件加载完成后 加载marker
        setTimeout(function () {
            loadAddress(data);
        }, 500);
    }, function (data) {
        Feng.error("地址查询失败!" + data.responseJSON.message + "!");
    });
    ajax.start();
    // 创建控件
    var myZoomCtrl = new ZoomControl();
    // 添加到地图当中
    map.addControl(myZoomCtrl);
    $("#myZoomCtrl").show();

});


//加载坐标
function loadAddress(list) {
    for (var i = 0; i < list.length; i++) {
        var lng = list[i].lng;
        var lat = list[i].lat;
        var projectCode = list[i].projectCode;
        var point = new BMap.Point(lng, lat);
        var marker = new BMap.Marker(point);// 创建标注
        marker.projectCode=projectCode;
        marker.addEventListener("click", function (e) {
                /*点击点跳转到该项目详情*/
            var id=null;
            var ajax = new $ax(Feng.ctxPath + "/lxProject/getIdByProjectCode", function (data) {
                id = data;
            }, function (data) {
                Feng.error("查询失败!" + data.responseJSON.message + "!");
            });
            ajax.set("projectCode", this.projectCode);
            ajax.start();


            var index = layer.open({
                type: 2,
                title: '项目信息详情',
                area: ['100%', '100%'], //宽高
                fix: false, //不固定
                maxmin: true,
                skin: 'layer-detail',
                content: Feng.ctxPath + '/projectMaster/projectMaster_view/' + id
            });
            layer.full(index);
            this.layerIndex = index;


            /*   var point = new BMap.Point(e.point.lng, e.point.lat);
               map.centerAndZoom(point, map.getZoom() + 4 <= 20 ? map.getZoom() + 4 : 20);*/



        });
        var opts = {
            width: 200,     // 信息窗口宽度
            //height: 70,     // 信息窗口高度
            title: "<text style='color:#4680ff;font-weight: bold'>" + list[i].projectName + "</text>", // 信息窗口标题
            enableMessage: true//设置允许信息窗发送短息
            //message:"亲耐滴，晚上一起吃个饭吧？戳下面的链接看下地址喔~"
        };
        var cooType = list[i].cooType === '0' ? "总包" : "参建";
        //监听事件为闭包函数 只能将其作为数据绑定 待函数调用时取出绑定数据
        marker.infoWindow = new BMap.InfoWindow("地址：" + list[i].address + "</br>参建类型：" + cooType + "</br>人数：" + list[i].workerNum, opts);  // 创建信息窗口对象
        //marker 鼠标悬浮事件
        marker.addEventListener("mouseover", function (e) {
            var point = new BMap.Point(e.point.lng, e.point.lat);
            this.openInfoWindow(this.infoWindow, point); //开启信息窗口
        });
        //marker 鼠标离开事件
        marker.addEventListener("mouseout", function (e) {
            this.closeInfoWindow();
        });
        map.addOverlay(marker);             // 将标注添加到地图中
    }
}

function projectChange() {
    var projectCode = $("#projectCode").val();

    //样式
    if(projectCode !== '' ){
        $("#myZoomCtrl-info").show();
        $("#animate-slide-down").hide();
        $("#animate-slide-up").show();
    }else{
        $("#myZoomCtrl-info").hide();
        $("#animate-slide-up").hide();
        $("#animate-slide-down").hide();
        return;
    }
    //获取项目进场人数
    getJoinedCount(projectCode);
    //获取今日考勤人数
    getTodayDevice(projectCode);
    //获取项目工人7天内考勤天数
    SevenDayDevice.init(projectCode);
    for(var i=0; i<projectAddress.length; i++){
        if(projectAddress[i].projectCode === projectCode){
            var lng = projectAddress[i].lng;
            var lat = projectAddress[i].lat;
            $("#projectName").html(projectAddress[i].projectName.length>15?projectAddress[i].projectName.substring(0,15)+"...":projectAddress[i].projectName);
            $("#projectName").attr("title",projectAddress[i].projectName);
            $("#address").html(projectAddress[i].address.length>15?projectAddress[i].address.substring(0,15)+"...":projectAddress[i].address);
            $("#address").attr("title",projectAddress[i].address);
            $("#cooType").html(projectAddress[i].cooType==='0'?"总包":"参建");
            $("#workerNum").html(projectAddress[i].workerNum);
            var point = new BMap.Point(lng, lat);
            map.centerAndZoom(point, 20);
            break;
        }
    }
}

//获取项目进场工人数
function getJoinedCount(projectCode) {
    //查询地址
    var ajax = new $ax(Feng.ctxPath + "/projectIndexReprot/getJoinedCount", function (data) {
        $("#totalJoin").html(data.entryCount);
    }, function (data) {
        Feng.error("项目进场数查询失败!" + data.responseJSON.message + "!");
    });
    if(projectCode !== undefined){
        ajax.set({projectCode:projectCode});
    }
    ajax.start();
}

//获取项目今日考勤人数
function getTodayDevice(projectCode) {
    //查询地址
    var ajax = new $ax(Feng.ctxPath + "/projectIndexReprot/getWorkerTotalCount", function (data) {
        $("#totalDevice").html(data);
    }, function (data) {
        Feng.error("今日考勤人数查询失败!" + data.responseJSON.message + "!");
    });
    if(projectCode !== undefined){
        ajax.set({projectCode:projectCode});
    }
    ajax.start();
}

    /*****************************************************  添加自定义页面    ***************************************************************/
    // 定义一个控件类,即function
    function ZoomControl(){
        // 默认停靠位置和偏移量
        this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
        this.defaultOffset = new BMap.Size(30, 30);
    }

    // 通过JavaScript的prototype属性继承于BMap.Control
    ZoomControl.prototype = new BMap.Control();

    // 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
    // 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
    ZoomControl.prototype.initialize = function(map){
        // 创建一个DOM元素
        var div = document.createElement("div");
        // 添加文字说明
        div.appendChild($("#myZoomCtrl")[0]);
        // 设置样式
        div.style.width = '500px';
        div.style.cursor = "pointer";
        // div.style.border = "1px solid gray";
        // div.style.backgroundColor = "white";
        // 添加DOM元素到地图中
        map.getContainer().appendChild(div);
        // 将DOM元素返回
        return div;
    }

