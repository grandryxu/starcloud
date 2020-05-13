var SelectCompany = {};

//提交
SelectCompany.commit = function () {
    if(SelectCompany.deptId === null){
        Feng.error("请选择公司或项目！");
        return false;
    }

    // if($("#isEnterprise").val() === "0" && SelectCompany.projectCode === ''){
    //     //项目级
    //     Feng.error("请选择项目!");
    //     return;
    // }

    var ajax = new $ax(Feng.ctxPath + "/accountProject/switchProject", function (data) {
        parent.layer.closeAll();
        Feng.info("切换成功!");
    }, function (data) {

    });
    ajax.set({
        deptId: SelectCompany.deptId ,
        projectCode: SelectCompany.projectCode
    });
    ajax.start();
    window.parent.parent.location.reload();
}

//关闭
SelectCompany.close = function () {
    parent.layer.closeAll();
}

//重置 退出项目切换
SelectCompany.reset = function () {
    layer.confirm('确认要重置项目吗？', {
        btn: ['确定', '取消'] //按钮
    }, function (index) {
        var ajax = new $ax(Feng.ctxPath + "/accountProject/reset", function (data) {
            parent.layer.closeAll();
            window.parent.location.reload();
            Feng.info("重置成功!");
        }, function (data) {

        });
        ajax.start();
    })
}


$(function(){
    //加载tree
    var ajax = new $ax(Feng.ctxPath + "/dept/tree", function (data) {
        var treeData = data;
        return $('#tree').treeview({data: treeData,onNodeSelected: function (e, o) {
            SelectCompany.deptId = o.id;
            SelectCompany.projectCode = "";
            var ajax = new $ax(Feng.ctxPath + "/accountProject/selectProjectsByAccountAndDeptId/"+ o.id, function (data) {

                //根据登陆人,社会信用代码 查询其关联的项目
                var html = '';
                for(var i=0;i<data.length;i++){
                    html += '<a class="list-group-item" projectCode="'+ data[i].projectCode+'">'+data[i].projectName +'</a>';
                }
                $(".tree-ssd-right .list-group").html(html);
                $(".tree-ssd-right .list-group .list-group-item").on("click",function () {
                    $(".tree-ssd-right .list-group .list-group-item").removeClass("active");
                    $(this).addClass("active");
                    SelectCompany.projectCode = $(this).attr("projectCode");

                })
            }, function (data) {
            });
            ajax.start();
        }});
    }, function (data) {
    });
    ajax.start();
})



/*
var tree = [{
    text: "南通一建",
    value:1,
    nodes: [{
        text: "江苏分公司",
        value:2,
        nodes: [{
            text: "苏州公公司",
            value:3,
        },
            {
                text: "无锡分公司",
                value:4,
            }
        ]
    },
        {
            text: "上海分公司",
            value:5,
        }
    ]
},
    {
        text: "南通二建",
        value:6,
    },
    {
        text: "南通三建",
        value:7,
    },
    {
        text: "南通四建",
        value:8
    },
    {
        text: "南通五建",
        value:9,
    }
];
function getTree() {
    // Some logic to retrieve, or generate tree structure
    return tree;
}
function addSubmit(){
    var str = JSON.stringify(projectObj);
    localStorage.setItem("projectStr",str);
}
function close(){

}
var projectObj = {};
$('#tree').treeview({data: getTree()});

//选中公司
$('#tree').on('nodeSelected',function(event, data) {
    projectObj.company = data;
});
//选中功能
function callback(){
    var o = localStorage.getItem("projectStr");
    if(o){
        var obj = JSON.parse(o);
        var node = $('#tree').treeview('getNode', obj.company.nodeId);
        if(node.value === obj.company.value){
            $('#tree').treeview('selectNode', [ node.nodeId, { silent: true } ]);
            if(node.parentId){
                $('#tree').treeview('expandNode', [ node.parentId, { silent: true } ]);
            }
        }
        if(obj.projectId){
            // console.log(o);
            $('.tree-ssd-right .list-group-item[projectId="'+ obj.projectId +'"]').addClass('active').siblings().removeClass('active');
        }
        projectObj = obj;
    }
}
callback();

//选中项目
$(".tree-ssd-right").on('click','.list-group-item',function(){
    $(this).addClass("active").siblings().removeClass('active');
    projectObj.projectId = $(this).attr('projectId');
});*/
