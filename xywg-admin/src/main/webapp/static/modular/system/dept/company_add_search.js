/**
 * Created by wangcw on 2018/6/13.
 */
function checkedCallback(button){
    var organizationCode =  $(button).data("organizationcode");
    var simplename =  $(button).data("companyname");
    parent.getChildData(organizationCode,simplename);
};
var but = $('#searchButton');
var list = [
];
but.on('click',function(){
    //搜索
    var ajax = new $ax(Feng.ctxPath + "/subContractor/getAllNoParentSubContractor?condition="+$("#condition").val(),function(data){
        var html = '';
        if(data&&data.length==0){
            layer.msg("暂无数据");
        }
        for(var i =0; i<data.length;i++){
            var obj = data[i];
            var li = '<tr><td>'+ obj.companyName +'</td><td>'+ obj.organizationCode +'</td><td class="td-c">' +
                '<button type="button" data-organizationcode="'+ obj.organizationCode +'" data-companyname="'+ obj.companyName +'" class="btn btn-sm">选择</button></td></tr>';
            html+=li;
        }
        $('.tableContainer-tbody').html(html);
    },function(data){
        Feng.error("暂无数据");
    });
    ajax.set("condition",$("input[name='condition']").val());
    ajax.start();
});
$('#tableContainer').on('click','button',function(){
    checkedCallback(this);
});

var layerIndex = 0;
var createCompany = function () {
    parent.layer.close(parent.layer.index);
    var index = parent.layer.open({
        type: 2,
        title: '添加企业信息',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: false,
        skin:"layer-no-title",
        content: Feng.ctxPath + '/subContractor/subContractor_add'
    });
    layerIndex = index;
    parent.layer.full(index);
}
//
// function getChildData(data) {
//     layer.close(layerIndex);
//     alert(data);
// }

//回车事件
function enter(event){
    if(event.keyCode === 13){
        //回车事件
        but.click();
    }
}