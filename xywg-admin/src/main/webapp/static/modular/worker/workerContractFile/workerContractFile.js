/**
 * 合同文件管理初始化
 */
var WorkerContractFile = {
    id: "WorkerContractFileTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
WorkerContractFile.initColumn = function () {
    return [
    	{field: 'selectItem', checkbox: true},
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle',sortable: true},
        {title: '施工单位', field: 'organizationName', visible: true, align: 'center', valign: 'middle',sortable: true},
        {title: '劳务公司', field: 'laborCompanyName', visible: true, align: 'center', valign: 'middle',sortable: true },
        {title: '班组老板', field: 'teamName', visible: true, align: 'center', valign: 'middle',sortable: true},
        {title: '工人姓名',field: 'workerName',visible: true,align: 'center',valign: 'middle',sortable: true},
        {title: '身份证号码',field: 'idCardNumber',visible: true,align: 'center',valign: 'middle',sortable: true,
            formatter: function (value, row, index) {
                if (row.idCardType === 1) {
                    return Feng.hiddenIdCard(value);
                } else {
                    return value;
                }
            }
        },
        {title: '合同数量', field: 'contractCount', visible: true, align: 'center', valign: 'middle',sortable: true},
        {title: '支付凭证数量', field: 'payCount', visible: true, align: 'center', valign: 'middle',sortable: true},
        {title: '退场凭证数量', field: 'joinCount', visible: true, align: 'center', valign: 'middle',sortable: true},
        {title: '其他数量', field: 'otherCount', visible: true, align: 'center', valign: 'middle',sortable: true},
        {title: '待工程部审核', field: 'projectCount', visible: true, align: 'center', valign: 'middle',sortable: true},
        {title: '待总部审核', field: 'companyCount', visible: true, align: 'center', valign: 'middle',sortable: true}
    ];
};

/**
 * 检查是否选中
 */
WorkerContractFile.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        WorkerContractFile.seItem = selected[0];
        return true;
    }
};


/**
 * 双击查看
 */
WorkerContractFile.searchInfo = function (e) {
    var url = Feng.ctxPath + '/contractFile/detail' ;
    if(e.pwId){
        url += "?pwId="+e.pwId;
    }
    var index = layer.open({
        type: 2,
        title: '附件信息',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: url
    });
    this.layerIndex = index;
}


/**
 * 点击添加合同文件
 */
WorkerContractFile.openAddWorkerContractFile = function () {
    var index = layer.open({
        type: 2,
        title: '添加合同文件',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/WorkerContractFile/WorkerContractFile_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看合同文件详情
 */
WorkerContractFile.openWorkerContractFileDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '合同文件详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/WorkerContractFile/WorkerContractFile_update/' + WorkerContractFile.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除合同文件
 */
WorkerContractFile.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/WorkerContractFile/delete", function (data) {
            Feng.success("删除成功!");
            WorkerContractFile.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("WorkerContractFileId", this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询合同文件列表
 */
WorkerContractFile.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['status'] = $("#status").val();
    queryData['projectCode'] = $("#projectCode").val();
    WorkerContractFile.table.refresh({query: queryData});
};


/**
 *
 * @param type 0：工程部审核 1：总部审核
 */
WorkerContractFile.review = function(type){
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }
    var ids = "";
    for(var i=0;i<selected.length;i++){
        ids += selected[i].id + ",";
        if(type === 0){
            //工程部审核
            if(selected[i].projectCount == 0){
                Feng.info("项目内"+ selected[i].projectName + "人员" +selected[i].workerName+"没有待工程部审核数据，请确认！");
                return false;
            }
        }else{
            //总部审核
            if(selected[i].companyCount == 0){
                Feng.info("项目内"+ selected[i].projectName + "人员" +selected[i].workerName+"没有待审核总部审核数据，请确认！");
                return false;
            }
        }
    }
    ids = ids.substring(0,ids.length-1);
    var status = 0;
    var ajax = new $ax(Feng.ctxPath + "/contractFile/batchReview", function (data) {
    	WorkerContractFile.table.refresh();
        Feng.success("操作成功!");
    }, function (data) {
        Feng.error("操作失败!" + data.responseJSON.message + "!");
    });
    ajax.set("ids",ids);
    layer.confirm('确认是否通过？', {
        btn: ['通过', '退回'] //按钮
    }, function (index) {
        layer.close(index);
        if(type === 0){
            status = 1;
        }else if(type === 1){
            status = 3;
        }
        ajax.set("status",status);
        ajax.start();
    }, function (index) {
        layer.close(index);
        if(type === 0){
            status = 2;
        }else if(type === 1){
            status = 4;
        }
        ajax.set("status",status);
        ajax.start();
    })
}



$(function () {
	Feng.initChosen();
    var defaultColunms = WorkerContractFile.initColumn();
    var table = new BSTable(WorkerContractFile.id, "/contractFile/listFiles", defaultColunms);
    table.setPaginationType("server");
    table.onDblClickRow = WorkerContractFile.searchInfo;
    WorkerContractFile.table = table.init();
});
