/**
 * 企业资质管理初始化
 */
var SubContractorCertifications = {
    id: "SubContractorCertificationsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
SubContractorCertifications.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '企业名称', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '企业组织机构代码', field: 'organizationCode', visible: true, align: 'center', valign: 'middle'},
        {title: '证书编号', field: 'certificationCode', visible: true, align: 'center', valign: 'middle'},
        {title: '证书类型', field: 'certificationTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '证书名称', field: 'certificationName', visible: true, align: 'center', valign: 'middle'},
        {title: '有效开始时间', field: 'validBeginDate', visible: true, align: 'center', valign: 'middle',
            formatter: function (value) {
                if(value!=null){
                    return value.substring(0,10);
                }else
                    return value;
            }},
        {title: '有效结束时间', field: 'validEndDate', visible: true, align: 'center', valign: 'middle',
            formatter: function (value) {
                if(value!=null){
                    return value.substring(0,10);
                }else
                    return value;
            }},
        {title: '最近发放日期', field: 'recentValidDate', visible: true, align: 'center', valign: 'middle',
            formatter: function (value) {
                if(value!=null){
                    return value.substring(0,10);
                }else
                    return value;
            }
        },
        {title: '发证机关', field: 'grantOrg', visible: true, align: 'center', valign: 'middle'},
        {title: '资质状态', field: 'qualificationStatusName', visible: true, align: 'center', valign: 'middle'},
        {title: '资质证书状态', field: 'certificationStatusName', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 检查是否选中
 */
SubContractorCertifications.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else if (selected.length > 1) {
        Feng.info("请先选中表格中的一记录！");
    }
    else {
        SubContractorCertifications.seItem = selected[0];
        return true;
    }
};


/**
 * 点击添加企业资质
 */
SubContractorCertifications.openAddSubContractorCertifications = function () {
    var index = layer.open({
        type: 2,
        title: '添加企业资质',
        area: ['80%', '80%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/subContractorCertifications/subContractorCertifications_addSubContractorCertifications'
    });
    this.layerIndex = index;
};

/**
 * 打开查看企业资质详情
 */
SubContractorCertifications.openSubContractorCertificationsDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '企业资质详情',
            area: ['80%', '80%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/subContractorCertifications/subContractorCertifications_update/' + SubContractorCertifications.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 双击查看
 */
SubContractorCertifications.searchInfo = function (e) {
    var index = layer.open({
        type: 2,
        title: '轮播图管理详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/subContractorCertifications/subContractorCertifications_info/' + e.id
    });
    this.layerIndex = index;
}
/**
 * 删除企业资质
 */
SubContractorCertifications.delete = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        layer.confirm('确认删除？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            var ids = "";
            for (var i = 0; i < selected.length; i++) {
                ids += selected[i].id + ","
            }
            ids = ids.substring(0, ids.length - 1);
            var ajax = new $ax(Feng.ctxPath + "/subContractorCertifications/deleteSubContractorCertifications", function (data) {
                Feng.success("删除成功!");
                SubContractorCertifications.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("ids", ids);
            ajax.start();
            layer.close(index);
        });
    }
};

/**
 * 查询企业资质列表
 */
SubContractorCertifications.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['certificationType'] = $("#certificationType").val();
    queryData['qualificationStatus'] = $("#qualificationStatus").val();
    queryData['certificationStatus'] = $("#certificationStatus").val();
    queryData['startDate1'] = $('#startDate1').val();
    queryData['endDate1'] = $('#endDate1').val();
    queryData['startDate2'] = $('#startDate2').val();
    queryData['endDate2'] = $('#endDate2').val();
    SubContractorCertifications.table.refresh({query: queryData});
};

$(function () {
    var currDate = new Date();
    var startDate1 = laydate.render({
        elem: '#startDate1',
        done: function (value, date) {
            if (value !== '') {
                endDate1.config.min.year = date.year;
                endDate1.config.min.month = date.month - 1;
                endDate1.config.min.date = date.date;
            } else {
                endDate1.config.min.year = '';
                endDate1.config.min.month = '';
                endDate1.config.min.date = '';
            }
        }
    });
    //设置结束时间
    var endDate1 = laydate.render({
        elem: '#endDate1',
        done: function (value, date) {
            if (value !== '') {
                startDate1.config.max.year = date.year;
                startDate1.config.max.month = date.month - 1;
                startDate1.config.max.date = date.date;
            } else {
                startDate1.config.max.year = currDate.getFullYear();
                startDate1.config.max.month = currDate.getMonth()+1;
                startDate1.config.max.date =  currDate.getDate();
            }
        }
    });

    var startDate2 = laydate.render({
        elem: '#startDate2',
        done: function (value, date) {
            if (value !== '') {
                endDate2.config.min.year = date.year;
                endDate2.config.min.month = date.month - 1;
                endDate2.config.min.date = date.date;
            } else {
                endDate2.config.min.year = '';
                endDate2.config.min.month = '';
                endDate2.config.min.date = '';
            }
        }
    });
    //设置结束时间
    var endDate2 = laydate.render({
        elem: '#endDate2',
        done: function (value, date) {
            if (value !== '') {
                startDate2.config.max.year = date.year;
                startDate2.config.max.month = date.month - 1;
                startDate2.config.max.date = date.date;
            } else {
                startDate2.config.max.year = currDate.getFullYear();
                startDate2.config.max.month = currDate.getMonth()+1;
                startDate2.config.max.date =  currDate.getDate();
            }
        }
    });

    //重置的时间集合
    SubContractorCertifications.resetDate = [startDate1,endDate1,startDate2,endDate2];

    var defaultColunms = SubContractorCertifications.initColumn();
    var table = new BSTable(SubContractorCertifications.id, "/subContractorCertifications/SubContractorCertificationsList", defaultColunms);
    var param={"organizationCode":$("#organizationCode").val()};
    table.setQueryParams(param);
    table.setPaginationType("server");
    table.onDblClickRow = SubContractorCertifications.searchInfo;//双击事件所对应的方法 要放在init之前
    SubContractorCertifications.table = table.init();
});


function  resetDate() {
    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = currentDate.getMonth()+1;
    var date = currentDate.getDate();

    for(var i=0;i<SubContractorCertifications.resetDate.length; i++) {
        var dateObject = SubContractorCertifications.resetDate[i];

        //去除最小时间限制
        dateObject.config.min.year = '';
        dateObject.config.min.month = '';
        dateObject.config.min.date = '';

        dateObject.config.max.year = '9999';
        dateObject.config.max.month = '12';
        dateObject.config.max.date = '31';

        //设置当前时间
        dateObject.config.dateTime.year = year;
        dateObject.config.dateTime.month = month;
        dateObject.config.dateTime.date = date;
        // dateObject.startDate.config.dateTime.hours = '时';
        // dateObject.startDate.config.dateTime.minutes = '分';
        // dateObject.startDate.config.dateTime.seconds = '秒';
    }
}