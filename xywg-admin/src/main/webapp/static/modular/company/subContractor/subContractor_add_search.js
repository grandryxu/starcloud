/**
 * Created by wangcw on 2018/6/13.
 */
function checkedCallback(button){
    var organizationCode =  $(button).data("organizationcode");
    var hasAssociated = $(button).data("hasAssociated");
    if($(button).data("blackstarttime") !== "undefined"){
        layer.confirm("该公司存在黑名单,原因:" + $(button).data("blackreason") +" 于"+ $(button).data("blackstarttime") +"起，止于"+ $(button).data("blackendtime")+",是否继续关联？", {
            btn: ['确定','取消'] //按钮
        }, function(){
            //提交信息
            var ajax = new $ax(Feng.ctxPath + "/cooperationSubContractor/addBussSubContractorConstruction", function(data){
                Feng.success("关联成功!");
                window.parent.SubContractor.table.refresh();
                parent.layer.closeAll();
            },function(data){
                Feng.error("关联失败!" + data.responseJSON. message+ "!");
            });
            ajax.set("constructionCode",organizationCode);
            ajax.start();
        }, function(){

        });
    }else{
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/cooperationSubContractor/addBussSubContractorConstruction", function(data){
            Feng.success("关联成功!");
            window.parent.SubContractor.table.refresh();
            parent.layer.closeAll();
        },function(data){
            Feng.error("关联失败!" + data.responseJSON. message+ "!");
        });
        ajax.set("constructionCode",organizationCode);
        ajax.start();
    }
}

var but = $('#searchButton');
var list = [
];
but.on('click',function(){
    //搜索
    var ajax = new $ax(Feng.ctxPath + "/subContractor/getAllSubContractor?condition="+encodeURI($("#condition").val()),function(data){
        var html = '';
        for(var i = 0; i<data.length;i++){
            var obj = data[i];
            if(obj.hasAssociated == 0){
                var li = '<tr><td>'+ obj.companyName +'</td><td>'+ obj.organizationCode +'</td><td class="td-c"><button type="button" data-organizationcode="'+ obj.organizationCode +'" data-blackendtime="'+ obj.blackEndTime +'" data-blackstarttime="'+ obj.blackStartTime +'" data-blackreason="'+ obj.blackReason +'" class="btn btn-sm" style="color: #4780ff;">关联</button></td></tr>';
            }else{
                var li = '<tr><td>'+ obj.companyName +'</td><td>'+ obj.organizationCode +'</td><td class="td-c" style="color: #999999;">已关联</tr>';
            }
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

var createSubContractor = function () {
    parent.layer.closeAll();
    var index = parent.layer.open({
        type: 2,
        title: '添加企业信息',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: false,
        skin:"layer-no-title",
        content: Feng.ctxPath + '/cooperationSubContractor/cooperationSubContractor_add'
    });
    layer.full(index);
}


//回车事件
function enter(event){
    if(event.keyCode === 13){
        //回车事件
        but.click();
    }
}
