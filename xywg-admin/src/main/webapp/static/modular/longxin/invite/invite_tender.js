/**
 * 企业信息管理初始化
 */
var SubContractor = {
    id: "SubContractorTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SubContractor.initColumn = function () {
    return [
        {field: 'selectItem', check: false},
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '招标信息', field: 'tenderName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '操作', visible: true, align: 'center', valign: 'middle',width:"100px", formatter: function (val, row, index) {
            	var bidPrice = row.bidPrice;
            	 var deadline= new Date(Date.parse(row.deadline));
            	 var nowDate= new Date();
            	 // console.log(row);

                if (flowStatus == 1) {
                    if(nowDate > deadline){
                        if(!bidPrice){
                            return '<button disabled="disabled" class="btn btn-sm btn-primary" style="background-color:#DEDEDE" onclick="SubContractor.openAddPrice('+row.id + ',' + row.tenderId + ',' + true + ')"><strong>招标截止</strong></button>'
                                ;
                        }
                        return '<button  class="btn btn-sm btn-primary" style="background-color:#FFB5C5" onclick="SubContractor.openAddPrice('+row.id + ',' + row.tenderId + ',' + true + ')"><strong>预览</strong></button>'
                            ;
                    }else{
                        return '<button  class="btn btn-sm btn-primary" style="background-color:#8470FF" onclick="SubContractor.openAddPrice('+row.id + ',' + row.tenderId + ',' + false + ')" id="bid'+row.id+'"><strong>参与竞标</strong></button>';
                    }
                } else {
                    return '<button disabled="disabled" class="btn btn-sm btn-primary" style="background-color:#DEDEDE"><strong>企业未审核</strong></button>';
                }
            }
        }
        
        
    ];
};

/**
 * 检查是否选中
 */
SubContractor.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        SubContractor.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加企业信息
 */
SubContractor.openAddPrice = function (e,a,flag) {
    var index = layer.open({
        type: 2,
        title: flag?'查看竞标价格':'添加竞标价格',
        area: ['60%', '70%'], //宽高
        fix: false, //不固定
        maxmin: false,
        content: Feng.ctxPath + '/lxInviteBid/price_add?bidId=' + e +'&tenderId=' + a + '&flag=' + flag
        // content: Feng.ctxPath + '/subContractor/getAllSubContractor'
    });
    this.layerIndex = index;
};




/**
 * 打开查看企业信息详情
 */
SubContractor.openSubContractorDetail = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("只能选中一条记录！");
        return false;
    } else {
        SubContractor.seItem = selected[0];
    }
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '企业信息详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: 'layer-no-title',
            content: Feng.ctxPath + '/lxSubContractor/lxSubContractor_update/' + SubContractor.seItem.id
        });

        this.layerIndex = index;
    }
};
/**
 * 检查是否选中
 */
SubContractor.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        SubContractor.seItem = selected[0];
        return true;
    }
};

/**
 * 切换状态
 */
SubContractor.changeState = function (e) {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        var ids = "";
        for (var i = 0; i < selected.length; i++) {
            ids += selected[i].id + ","
        }
        ids = ids.substring(0, ids.length - 1);
        var ajax = new $ax(Feng.ctxPath + "/cooperationSubContractor/changeState", function (data) {
                Feng.success("操作成功");
                SubContractor.table.refresh();
            }, function (data) {
                Feng.error("操作失败" + data.responseJSON.message + "!");
            }
        );
        ajax.set("status", e);
        ajax.set("ids", ids);
        ajax.start();
    }
}

/**
 * 删除企业信息
 */
SubContractor.delete = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("请选中一条数据！");
        return false;
    } else {
        SubContractor.seItem = selected[0];
    }
    var self = this;
    layer.confirm('是否确认删除?', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        layer.closeAll();
        var ajax = new $ax(Feng.ctxPath + "/subContractor/deleteConstruction", function (data) {
            Feng.success("删除成功!");
            SubContractor.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("organizationCode", self.seItem.organizationCode);
        ajax.start();
    }, function () {

    });

};

/**
 * 查询企业信息列表
 */
SubContractor.search = function () {
    var queryData = {};
    queryData.projectName = $("#projectName").val().trim();
    queryData.tenderName = $("#tenderName").val().trim();
    SubContractor.table.refresh({query: queryData});
};

/**
 * 双击查看
 */
SubContractor.searchInfo = function (e) {
//    var index = layer.open({
//        type: 2,
//        title: '企业信息详情',
//        area: ['100%', '100%'], //宽高
//        fix: false, //不固定
//        maxmin: true,
//        skin: 'layer-detail',
//        content: Feng.ctxPath + '/lxSubContractor/lxSubContractor_view/' + e.id
//    });
//    this.layerIndex = index;
}
/**
 * 新增企业资质
 */
SubContractor.openSubContractorCertifications = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: "新增企业资质",
            area: ['800px', '420px'],
            fix: false,
            maxmin: true,
            content: Feng.ctxPath + '/subContractorCertifications/subContractorCertifications_add/' + SubContractor.seItem.organizationCode
        });
    }
}


$(function () {
    Feng.initChosen();
    Feng.initStartEndDate();
    var defaultColunms = SubContractor.initColumn();
    var table = new BSTable(SubContractor.id, "/lxInviteBid/tenderlist", defaultColunms);
    table.setPaginationType("server");
    table.onDblClickRow = SubContractor.searchInfo;//双击事件所对应的方法 要放在init之前
    SubContractor.table = table.init();
});


//************************************************* 导入导出 Start ********************************************************//
/**
 * 导入工人信息
 */
SubContractor.selectFile = function () {
    $('#excelFile').val("");
    $("#excelFile").trigger("click");
};

//导入
SubContractor.fileUpload = function () {
    var myform = new FormData();
//    myform.append('file', $('#excelFile')[0].files[0]);
    var files = $('#excelFile')[0].files;
    if(files.length === 0){
        return;
    }
    myform.append('file', files[0]);
    $.ajax({
        url: Feng.ctxPath + "/cooperationSubContractor/excelUpload",
        type: "POST",
        data: myform,
        contentType: false,
        processData: false,
        success: function (data) {
            Feng.info("导入成功!");
            SubContractor.table.refresh();
        },
        error: function (data) {
            Feng.error("导入失败!" + data.responseJSON.message + "!");
        }

    });
}

/**
 * 导出工人管理
 */
SubContractor.download = function () {
    window.location.href = encodeURI(Feng.ctxPath + "/cooperationSubContractor/download?"
        + "&condition=" + $("#condition").val()
        + "&organizationType=" + $("#organizationType").val()
        + "&businessStatus=" + $("#businessStatus").val())
};

/**
 * dao
 */
SubContractor.mouldDownload = function () {
    window.location.href = "/static/excelMould/subContractorModule.xlsx";
};


//************************************************* 导入导出 End ********************************************************//