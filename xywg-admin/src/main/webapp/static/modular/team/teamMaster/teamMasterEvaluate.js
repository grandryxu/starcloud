/**
 * 企业评价管理初始化
 */
var TeamMasterEvaluate = {
    id: "TeamMasterEvaluateTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TeamMasterEvaluate.initColumn = function () {
    return [
        {field: 'selectItem', radio: true,visible: false},
            {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '质量评价星级', field: 'option1', visible: true, align: 'center', valign: 'middle',
                formatter:function (value, row, index) {
                return '<input  type="number"  class="rating" min=0 max=5 step=1 data-size="sm" disabled="disabled" value="'+ value +'">';
            }},
            {title: '进度评价星级', field: 'option2', visible: true, align: 'center', valign: 'middle',
                formatter:function (value, row, index) {
                    return '<input  type="number"  class="rating" min=0 max=5 step=1 data-size="sm" disabled="disabled" value="'+ value +'">';
                }
            },
            {title: '安全评价星级', field: 'option3', visible: true, align: 'center', valign: 'middle',
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
            }
    ];
};

/**
 * 检查是否选中
 */
TeamMasterEvaluate.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TeamMasterEvaluate.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加企业评价
 */
TeamMasterEvaluate.openAddTeamMasterEvaluate = function () {
    var index = layer.open({
        type: 2,
        title: '添加企业评价',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/teamMaster/teamMasterEvaluate_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看企业评价详情
 */
TeamMasterEvaluate.openTeamMasterEvaluateDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '企业评价详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/TeamMasterEvaluate/TeamMasterEvaluate_update/' + TeamMasterEvaluate.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除企业评价
 */
TeamMasterEvaluate.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/TeamMasterEvaluate/delete", function (data) {
            Feng.success("删除成功!");
            TeamMasterEvaluate.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("TeamMasterEvaluateId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
TeamMasterEvaluate.formParams = function() {
    var queryData = {};
    queryData['teamId'] = $("#id").val();
    return queryData;
}

/**
 * 查询企业评价列表
 */
TeamMasterEvaluate.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    TeamMasterEvaluate.table.refresh({query: queryData});
};

/**
 * 表格加载成功回调
 */
TeamMasterEvaluate.onLoadSuccess = function (data) {
    $(".rating").rating({
        showClear:false
    });
};

$(function () {
    var defaultColunms = TeamMasterEvaluate.initColumn();
    var table = new BSTable(TeamMasterEvaluate.id, "/teamMaster/teamMasterEvaluationList", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(TeamMasterEvaluate.formParams());
    table.onLoadSuccess = TeamMasterEvaluate.onLoadSuccess;
    TeamMasterEvaluate.table = table.init();

});

