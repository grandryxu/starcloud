/**
 * 企业评价管理初始化
 */
var ConstructionEvaluate = {
    id: "ConstructionEvaluateTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ConstructionEvaluate.initColumn = function () {
    return [
            {field: 'selectItem', radio: true,visible: false},
            {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
            {title: '参建公司名称', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
            {title: '总分', field: 'score', visible: true, align: 'center', valign: 'middle',
            formatter:function (value, row, index) {

                if (value===""){
                    return "暂未评分";
                } else {
                    return value+'分';
                }

            }
        },

     /*   {title: '证件编号', field: 'idCardNumber', visible: false, align: 'center', valign: 'middle',formatter:function (data) {
            return Feng.hiddenIdCard(data);
        }},
            {title: '质量评价星级111111', field: 'massReview', visible: true, align: 'center', valign: 'middle',
                formatter:function (value, row, index) {
                return '<input  type="number"  class="rating" min=0 max=5 step=1 data-size="sm" disabled="disabled" value="'+ value +'">';
            }},
            {title: '进度评价星级', field: 'paceReview', visible: true, align: 'center', valign: 'middle',
                formatter:function (value, row, index) {
                    return '<input  type="number"  class="rating" min=0 max=5 step=1 data-size="sm" disabled="disabled" value="'+ value +'">';
                }
            },
            {title: '安全评价星级', field: 'safeReview', visible: true, align: 'center', valign: 'middle',
                formatter:function (value, row, index) {
                    return '<input  type="number"  class="rating" min=0 max=5 step=1 data-size="sm" disabled="disabled" value="'+ value +'">';
                }
            },
            {title: '评价说明', field: 'note', visible: true, align: 'center', valign: 'middle',
                formatter: function (value) {
                    if (value!=null && value.length > 50) {
                        return "<a data-toggle='tooltip' title='" + value + "'>" + value.substring(0, 50) + "‧‧‧‧‧‧</a>";
                    } else
                        return "<a data-toggle='tooltip' title='"+ value +"'>" + value + "</a>";
                }
            }*/
    ];
};

/**
 * 检查是否选中
 */
ConstructionEvaluate.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ConstructionEvaluate.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加企业评价
 */
ConstructionEvaluate.openAddConstructionEvaluate = function () {
    var index = layer.open({
        type: 2,
        title: '添加企业评价',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/constructionEvaluate/constructionEvaluate_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看企业评价详情
 */
ConstructionEvaluate.openConstructionEvaluateDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '企业评价详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/constructionEvaluate/constructionEvaluate_update/' + ConstructionEvaluate.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除企业评价
 */
ConstructionEvaluate.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/constructionEvaluate/delete", function (data) {
            Feng.success("删除成功!");
            ConstructionEvaluate.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("constructionEvaluateId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询企业评价列表
 */
ConstructionEvaluate.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ConstructionEvaluate.table.refresh({query: queryData});
};

/**
 * 表格加载成功回调
 */
ConstructionEvaluate.onLoadSuccess = function (data) {
    $(".rating").rating({
        showClear:false
    });
};

$(function () {
    var defaultColunms = ConstructionEvaluate.initColumn();
    var table = new BSTable(ConstructionEvaluate.id, "/lxEvaluate/getScoreByCompanyAndProject?projectCode="+$("#projectCode").val()+"&organizationCode="+$("#organizationCode").val(), defaultColunms);
    table.setPaginationType("server");
    //table.setQueryParams({"organizationCode":$("#organizationCode").val()});
    table.onLoadSuccess = ConstructionEvaluate.onLoadSuccess;
    ConstructionEvaluate.table = table.init();

});

