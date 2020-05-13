/**
 * 系统管理--用户管理的单例对象
 */
var MgrUser = {
    id: "managerTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptid:0
};

/**
 * 初始化表格的列
 */
MgrUser.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '账号', field: 'account', align: 'center', valign: 'middle', sortable: true},
        {title: '姓名', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '性别', field: 'sexName', align: 'center', valign: 'middle', sortable: true},
        {title: '角色', field: 'roleName', align: 'center', valign: 'middle', sortable: true},
        {title: '部门', field: 'deptName', align: 'center', valign: 'middle', sortable: true},
        {title: '邮箱', field: 'email',visible: false, align: 'center', valign: 'middle', sortable: true},
        {title: '电话', field: 'phone', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'createtime', align: 'center', valign: 'middle', sortable: true},
        {title: '到期时间', field: 'endTime', align: 'center', valign: 'middle', sortable: true},
        {title: '状态', field: 'statusName', visible: false,align: 'center', valign: 'middle', sortable: true}];
    return columns;
};

/**
 * 检查是否选中
 */
MgrUser.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        MgrUser.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加管理员
 */
MgrUser.openAddMgr = function () {
    var index = layer.open({
        type: 2,
        title: '添加管理员',
        area: ['800px', '560px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/mgr/user_add'
    });
    this.layerIndex = index;
};

/**
 * 点击修改按钮时
 * @param userId 管理员id
 */
MgrUser.openChangeUser = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '编辑管理员',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/mgr/user_edit/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 点击角色分配
 * @param
 */
MgrUser.roleAssign = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '角色分配',
            area: ['300px', '400px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/mgr/role_assign/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 点击分配项目
 * @param
 */
MgrUser.projectAssign = function () {
    if (this.check()) {
        if(this.seItem.deptid === null || this.seItem.deptid === '' || this.seItem.deptid === undefined){
            Feng.error("请先给该用户分配部门!");
            return;
        }
        var index = layer.open({
            type: 2,
            title: '项目分配',
            area: ['80%', '80%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/mgr/project_assign/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除用户
 */
MgrUser.delMgrUser = function () {
    if (this.check()) {

        var operation = function(){
            var userId = MgrUser.seItem.id;
            var ajax = new $ax(Feng.ctxPath + "/mgr/delete", function () {
                Feng.success("删除成功!");
                MgrUser.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("userId", userId);
            ajax.start();
        };

        Feng.confirm("是否删除用户" + MgrUser.seItem.account + "?",operation);
    }
};

/**
 * 冻结用户账户
 * @param userId
 */
MgrUser.freezeAccount = function () {
    if (this.check()) {
        var userId = this.seItem.id;
        var ajax = new $ax(Feng.ctxPath + "/mgr/freeze", function (data) {
            Feng.success("冻结成功!");
            MgrUser.table.refresh();
        }, function (data) {
            Feng.error("冻结失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userId", userId);
        ajax.start();
    }
};

/**
 * 解除冻结用户账户
 * @param userId
 */
MgrUser.unfreeze = function () {
    if (this.check()) {
        var userId = this.seItem.id;
        var ajax = new $ax(Feng.ctxPath + "/mgr/unfreeze", function (data) {
            Feng.success("解除冻结成功!");
            MgrUser.table.refresh();
        }, function (data) {
            Feng.error("解除冻结失败!");
        });
        ajax.set("userId", userId);
        ajax.start();
    }
}

/**
 * 重置密码
 */
MgrUser.resetPwd = function () {
    if (this.check()) {
        var userId = this.seItem.id;
        parent.layer.confirm('是否重置密码为123456？', {
            btn: ['确定', '取消'],
            shade: false //不显示遮罩
        }, function () {
            var ajax = new $ax(Feng.ctxPath + "/mgr/reset", function (data) {
                Feng.success("重置密码成功!");
            }, function (data) {
                Feng.error("重置密码失败!");
            });
            ajax.set("userId", userId);
            ajax.start();
        });
    }
};

MgrUser.resetSearch = function () {
    $("#companySel").change("");
    $("#name").val("");
    $("#beginTime").val("");
    $("#endTime").val("");

    MgrUser.search();
}

MgrUser.search = function () {
    var queryData = {};

    queryData['deptid'] = MgrUser.deptId;//MgrUser.deptid;
    queryData['name'] = $("#name").val();
    queryData['beginTime'] = $("#startDate").val();
    queryData['endTime'] = $("#endDate").val();

    MgrUser.table.refresh({query: queryData});
}

// MgrUser.onClickDept = function (e, treeId, treeNode) {
//     MgrUser.deptid = treeNode.id;
//     MgrUser.search();
// };

$(function () {
    var defaultColunms = MgrUser.initColumn();
    var table = new BSTable("managerTable", "/mgr/list", defaultColunms);
    table.setPaginationType("client");
    MgrUser.table = table.init();
    // var ztree = new $ZTree("deptTree", "/dept/tree");
    // ztree.bindOnClick(MgrUser.onClickDept);
    // ztree.init();
    //初始化部门树
    initializeDeptTree();
    // var ajax = new $ax(Feng.ctxPath + "/dept/treeList", function (data) {
    //     debugger
    //     var treeData = data;
    //     var companySel = $("#companySel");
    //     var html = "";
    //     for (var i = 0; i < data.length; i++) {
    //         html += "<option value='" + data[i].id + "'>" + data[i].name + "</option>"
    //     }
    //     companySel.append(html);
    // }, function (data) {
    // });
    // ajax.start();

    //Feng.initBeginEndDate();
    //初始化时间插件
    MgrUser.resetDate = Feng.initStartEndDate();
});

/**
 * 初始化部门数数据
 */
function initializeDeptTree() {
    // Feng.initValidator("deptInfoForm", WorkerReport.validateFields);
    var ztree = new $ZTree("parentDeptMenuTree", "/dept/treeSelect");
    ztree.bindOnClick(MgrUser.onClickDept);
    ztree.init();
    MgrUser.zTreeInstance = ztree;
};

/**
 * 点击部门ztree列表的选项时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
MgrUser.onClickDept = function (e, treeId, treeNode) {
    $("#pName").val(MgrUser.zTreeInstance.getSelectedVal());
    MgrUser.deptId = treeNode.id;
    refresh();

}

/**
 * 显示部门选择的树
 *
 * @returns
 */
MgrUser.showDeptSelectTree = function () {
    var pName = $("#pName");
    var pNameOffset = $("#pName").offset();
    $("#parentDeptMenu").css({
        left: (pNameOffset.left - 5) + "px",
        top: pNameOffset.top + pName.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
}

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "parentDeptMenu" || $(
        event.target).parents("#parentDeptMenu").length > 0)) {
        MgrUser.hideDeptSelectTree();
    }v116GetPersonsByTeam
}

/**
 * 隐藏部门选择的树
 */
MgrUser.hideDeptSelectTree = function () {
    $("#parentDeptMenu").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
}

function  resetDate() {
    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = currentDate.getMonth()+1;
    var date = currentDate.getDate();

    for(var i=0;i<MgrUser.resetDate.length; i++) {
        var dateObject = MgrUser.resetDate[i];

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
