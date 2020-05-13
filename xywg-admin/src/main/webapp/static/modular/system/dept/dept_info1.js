/**
 * 初始化部门详情对话框
 */
var DeptInfoDlg = {
    deptInfoData : {},
    zTreeInstance : null,
    validateFields: {
        simplename: {
            validators: {
                notEmpty: {
                    message: '部门名称不能为空'
                }
            }
        },
        fullname: {
            validators: {
                notEmpty: {
                    message: '部门全称不能为空'
                }
            }
        },
        pName: {
            validators: {
                notEmpty: {
                    message: '上级名称不能为空'
                }
            }
        },
        socialCreditNumber: {
            validators: {
                notEmpty: {
                    message: '社会信用统一代码不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
DeptInfoDlg.clearData = function() {
    this.deptInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeptInfoDlg.set = function(key, val) {
    this.deptInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeptInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
DeptInfoDlg.close = function() {
    parent.layer.close(window.parent.Dept.layerIndex);
}

/**
 * 点击部门ztree列表的选项时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
DeptInfoDlg.onClickDept = function(e, treeId, treeNode) {
    $("#pName").attr("value", DeptInfoDlg.zTreeInstance.getSelectedVal());
    $("#pid").attr("value", treeNode.id);
}

/**
 * 显示部门选择的树
 *
 * @returns
 */
DeptInfoDlg.showDeptSelectTree = function() {
    var pName = $("#pName");
    var pNameOffset = $("#pName").offset();
    $("#parentDeptMenu").css({
        left : pNameOffset.left + "px",
        top : pNameOffset.top + pName.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}

/**
 * 隐藏部门选择的树
 */
DeptInfoDlg.hideDeptSelectTree = function() {
    $("#parentDeptMenu").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}

/**
 * 收集数据
 */
DeptInfoDlg.collectData = function() {
    this.set('id')
        .set('simplename')
        .set('fullname')
        .set('tips')
        .set('num')
        .set('pid')
        .set('socialCreditNumber')
        .set('startTime')
        .set('endTime');
}

/**
 * 验证数据是否为空
 */
DeptInfoDlg.validate = function () {
    $('#deptInfoForm').data("bootstrapValidator").resetForm();
    $('#deptInfoForm').bootstrapValidator('validate');
    return $("#deptInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 提交添加部门
 */
DeptInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    if(DeptInfoDlg.deptInfoData.socialCreditNumber === ''){
        Feng.info("社会信用代码不能为空");
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dept/add", function(data){
        Feng.success("添加成功!");
        window.parent.Dept.table.refresh();
        DeptInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deptInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
DeptInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dept/update", function(data){
        Feng.success("修改成功!");
        window.parent.Dept.table.refresh();
        DeptInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deptInfoData);
    ajax.start();
}

/**
 * 设置有效期
 */
DeptInfoDlg.setTime = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dept/setTime", function(data){
        Feng.success("修改成功!");
        window.parent.Dept.table.refresh();
        DeptInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deptInfoData);
    ajax.start();
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "parentDeptMenu" || $(
            event.target).parents("#parentDeptMenu").length > 0)) {
        DeptInfoDlg.hideDeptSelectTree();
    }
}

/**
 * 打开企业搜索弹框
 * @auth wangcw
 * @param event
 */
function openCompanySearchDialog() {


    var index = layer.open({
        type: 2,
        title: '添加企业信息',
        area: ['50%', '50%'], //宽高
        fix: false, //不固定
        maxmin: false,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/dept/company_add_search'
    });
    DeptInfoDlg.layerIndex = index;
}

function getChildData(socialCreditNumber,simplename) {
    layer.closeAll();
    $('#socialCreditNumber').val(socialCreditNumber);
    $("#simplename").val(simplename);

}

$(function() {
    $("#socialCreditNumber").val("DEPT"+new Date().getTime());
    Feng.initValidator("deptInfoForm", DeptInfoDlg.validateFields);
    var currDate = new Date();
    var startTime = laydate.render({
        elem: '#startTime',
        type: 'date',
        done: function (value, date) {
            if (value !== '') {
                endTime.config.min.year = date.year;
                endTime.config.min.month = date.month - 1;
                endTime.config.min.date = date.date;
            } else {
                endTime.config.min.year = '';
                endTime.config.min.month = '';
                endTime.config.min.date = '';
            }
        }
    });
    //设置结束时间
    var endTime = laydate.render({
        elem: '#endTime',
        type: 'date',
        done: function (value, date) {
            if (value !== '') {
                startTime.config.max.year = date.year;
                startTime.config.max.month = date.month - 1;
                startTime.config.max.date = date.date;
            } else {
                startTime.config.max.year = currDate.getFullYear();
                startTime.config.max.month = currDate.getMonth() + 1;
                startTime.config.max.date = currDate.getDate();
            }
        }
    });
    var ztree = new $ZTree("parentDeptMenuTree", "/dept/treeSelect");
    ztree.bindOnClick(DeptInfoDlg.onClickDept);
    ztree.init();
    DeptInfoDlg.zTreeInstance = ztree;
});
