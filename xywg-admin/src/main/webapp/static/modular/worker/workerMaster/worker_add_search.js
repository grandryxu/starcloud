/**
 * Created by wangcw on 2018/6/13.
 */

var Worker = {
    id: "WorkerTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    tableDataList:[],
    selectWorker:null
};

function checkedCallback(button){
    var idCard =  $(button).data("idCard");
    console.log(organizationCode);
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cooperationSubContractor/addBussSubContractorConstruction", function(data){
        Feng.success("添加成功!");
        window.parent.SubContractor.table.refresh();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON. message+ "!");
    });
    ajax.set("constructionCode",organizationCode);
    ajax.start();
};
var but = $('#searchButton');
var list = [
];
but.on('click',function(){
    //搜索
    var ajax = new $ax(Feng.ctxPath + "/workerMaster/list?condition="+$("#condition").val(),function(data){

        var html = '';
        for(var i =0; i<data.length;i++){
            var obj = data[i];
            var li = '<tr><td>'+ obj.name +'</td><td>'+ obj.idCard +'</td><td class="td-c"><button type="button" data-idCard="'+ obj.idCard +'" class="btn btn-sm">关联</button></td></tr>';
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

var createWorker = function () {
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

/**
 * 初始化表格的列
 */
Worker.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true,visible:false},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '手机号码', field: 'cellPhone', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型', field: 'idCardTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
            return Feng.hiddenIdCard(data);
        }},
        {title: '操作', visible: true, align: 'center', valign: 'middle',formatter:function  (value, row, index) {
            var id= row.id;
            var isBlack = row.isBlack;
            return "<a href='javascript:void(0)' class='text-success' onclick='Worker.dialogEnd("+index+")'>添加</a>"
        }},

    ];
};


/**
 * 选择工人关闭窗口返回工人ID
 * @param id
 * @param isBlack
 * @auth wangcw
 */
Worker.dialogEnd = function (index) {
    Worker.selectWorker = Worker.tableDataList[index];


    if(Worker.selectWorker.isBlack==0){
        parent.dialogEnd(Worker.selectWorker);
        return;
    }
    //该工人是黑名单的情况
    layer.confirm('工人在黑名单,是否继续添加?', {
        btn: ['确定','取消'] //按钮
    }, function(){
        layer.closeAll();
        parent.dialogEnd(Worker.selectWorker);
    }, function(){
        layer.closeAll();
    });

}


/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
Worker.formParams = function() {
    var queryData = {};
    queryData['projectCode'] = parent.$("#projectCode").val();
    queryData['idCard'] = $('#condition').val();
    queryData['name'] = $('#condition').val();
    queryData['phone'] = $('#condition').val();

    return queryData;
}
/**
 * 表格加载
 */
Worker.onLoadSuccess = function (data) {
    Worker.tableDataList = data.rows;
}

Worker.search = function () {
    if(!Worker.table){
        var table = new BSTable(Worker.id, "/teamMaster/getUnteamWorker", Worker.initColumn());
        table.setPaginationType("server");
        table.pageSize = 5;
        table.setQueryParams(Worker.formParams());
        table.showRefresh = false;
        table.showColumns = false;
        table.onLoadSuccess = Worker.onLoadSuccess;
        Worker.table = table.init();
    }
    Worker.table.refresh({query: Worker.formParams()});
}