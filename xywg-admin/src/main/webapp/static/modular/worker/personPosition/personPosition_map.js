var points = [];
var i = 0;

var map;
showMap();

function showMap() {
    map = new BMap.Map("allmap");
    map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);
    //map.addControl(new BMap.ScaleControl());
    map.addControl(new BMap.NavigationControl());
    map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
}

$(function() {
    var startDate = laydate.render({
        elem: '#startDate',
        type: 'date',
        done: function (value, date) {

        },
        max:0
    });
    //设置结束时间
    var endDate = laydate.render({
        elem: '#endDate',
        type: 'date',
        done: function (value, date) {

        },
        max:0
    });
});

function getData() {
    map.clearOverlays();   //删除覆盖物
    for(var j=0; j<lines.length; j++) {
        lines[j].remove();
    }
    points = [];
    var startDate = $('#startDate').val();
    var endDate = $('#endDate').val();
    if(startDate == '' || endDate == '') {
        Feng.info('请选择时间段');
        return;
    }
    if(startDate>endDate) {
        Feng.info('开始时间不能在结束时间之后');
        return;
    }
    $.ajax({
        type: 'post',
        url: '/position/getDetail',
        data:{idCardNumber:idCardNumber,shImei:shImei,startDate:startDate,endDate:endDate},
        success:function(data) {
            console.log(data);
            i = 0;
            doMap(data);
        },
        error:function() {
            Feng.info("加载失败，请刷新重试！");
        }
    });
}

var icon = new BMap.Icon('/static/img/trail.png', new BMap.Size(40, 40), {//是引用图标的名字以及大小，注意大小要一样
    anchor: new BMap.Size(21, 41)//这句表示图片相对于所加的点的位置
});

//执行
function doMap(data) {
    if (i == data.length) return;
    var mapPoint = new BMap.Point(data[i].lng, data[i].lat);
    var mkr = new BMap.Marker(mapPoint, {
        icon: icon
    });
    map.addOverlay(mkr); //标点
    /*var label = new BMap.Label(data[i].lng, {
        offset: new BMap.Size(20, -10)
    });
    mkr.setLabel(label);*/
    points.push(mapPoint);
    setZoom(points)
    driveline(points);
    i++;
    setTimeout(function() {
        doMap(data);
    }, 1000);
}
//画折线
var lines = [];

function driveline(points) {
    var line = new BMap.Polyline(points, {
        strokeColor: "#f14411",
        strokeWeight: 4,
        strokeOpacity: 1
    });
    map.addOverlay(line);
    lines[i] = line;
}

//根据点信息实时更新地图显示范围，让轨迹完整显示。设置新的中心点和显示级别
function setZoom(bPoints) {
    var view = map.getViewport(eval(bPoints));
    // var mapZoom = view.zoom;
    var centerPoint = view.center;
    map.centerAndZoom(centerPoint, 16);
}