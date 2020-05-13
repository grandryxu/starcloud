/**
 * 初始化轮播图管理详情对话框
 */
var BannerOrderDlg = {
    bannerOrderData: {}
};


/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BannerOrderDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BannerOrderDlg.close = function () {
    parent.layer.close(window.parent.Banner.layerIndex);
}


/**
 * 提交修改
 */
BannerOrderDlg.updateSubmit = function () {

    var arr = [];
    $('#warp-content').children('li').each(function (index, element) {
        // console.log(element)
        var id = element.getAttribute('imgid');
        arr.push(id);
    });
    var ids = arr.join(',');

    var ajax = new $ax(Feng.ctxPath + "/banner/updateOrder", function (data) {
        Feng.success("排序成功！")
        BannerOrderDlg.close();
    }, function (data) {
        Feng.error("排序失败!" + data.responseJSON.message + "!");
    });
    ajax.set({'ids': ids});
    ajax.start();


}

function changeOrder() {
    var type = $("#type").val();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/banner/getBannersByType", function (data) {

        var html = '';
        for (var i = 0; i < data.length; i++) {
            var o = data[i];
            html += '<li imgid="' + o.id + '"><img src="' + Feng.imagePath + o.fileName + '"></li>';
        }
        $('#warp-content').html(html);
        console.log(data);
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set({'type': type});
    ajax.start();
}

$(function () {
});
