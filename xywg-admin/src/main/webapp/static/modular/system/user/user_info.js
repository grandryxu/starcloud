/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var UserInfoDlg = {
    userInfoData: {},
    strDate:null ,
    validateFields: {
        validStatus:{
            validators: {
                notEmpty: {
                    message: '时间类型不能为空'
                }
            }
        },
        account: {
            validators: {
                notEmpty: {
                    message: '账户不能为空'
                },
                regexp: {
                    regexp:  /^[A-Za-z0-9]+$/,
                    message: '只能输入数字或者字母'
                }
            }
        },
        name: {
            validators: {
                notEmpty: {
                    message: '姓名不能为空'
                }
            }
        },
        citySel: {
            validators: {
                notEmpty: {
                    message: '部门不能为空'
                }
            }
        },
        password: {
            validators: {
                notEmpty: {
                    message: '密码不能为空'
                },
                identical: {
                    field: 'rePassword',
                    message: '两次密码不一致'
                }
            }
        },
        rePassword: {
            validators: {
                notEmpty: {
                    message: '密码不能为空'
                },
                identical: {
                    field: 'password',
                    message: '两次密码不一致'
                }
            }
        },
        phone:{
            validators: {
                stringLength: {
                    min: 11,
                    max: 11,
                    message: '请输入11位手机号码'
                },
                regexp: {
                    regexp:  /(^1[3|4|5|7|8]\d{9}$)|(^09\d{8}$)/,
                    message: '请输入正确的手机号码'
                }
            }
        },
    }
};

/**
 * 清除数据
 */
UserInfoDlg.clearData = function () {
    this.userInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserInfoDlg.set = function (key, val) {
    this.userInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserInfoDlg.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
UserInfoDlg.close = function () {
    parent.layer.close(window.parent.MgrUser.layerIndex);
};

/**
 * 点击部门input框时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
UserInfoDlg.onClickDept = function (e, treeId, treeNode) {
    $("#citySel").attr("value", instance.getSelectedVal());
    $("#deptid").attr("value", treeNode.id);
};

/**
 * 显示部门选择的树
 *
 * @returns
 */
UserInfoDlg.showDeptSelectTree = function () {
    var cityObj = $("#citySel");
    var cityOffset = $("#citySel").offset();
    $("#menuContent").css({
        left: cityOffset.left + "px",
        top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
};

/**
 * 显示用户详情部门选择的树
 *
 * @returns
 */
UserInfoDlg.showInfoDeptSelectTree = function () {
    var cityObj = $("#citySel");
    var cityPosition = $("#citySel").position();
    $("#menuContent").css({
        left: cityPosition.left + "px",
        top: cityPosition.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
};

/**
 * 隐藏部门选择的树
 */
UserInfoDlg.hideDeptSelectTree = function () {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
};

/**
 * 收集数据
 */
UserInfoDlg.collectData = function () {
    this.set('id').set('account').set('sex').set('password').set('avatar')
        .set('email').set('name').set('birthday').set('rePassword').set('deptid').set('phone').set('validStatus').set('startTime').set('endTime');
};

/**
 * 验证两个密码是否一致
 */
UserInfoDlg.validatePwd = function () {
    var password = this.get("password");
    var rePassword = this.get("rePassword");
    if (password == rePassword) {
        return true;
    } else {
        return false;
    }
};

/**
 * 验证数据是否为空
 */
UserInfoDlg.validate = function () {
    $('#userInfoForm').data("bootstrapValidator").resetForm();
    $('#userInfoForm').bootstrapValidator('validate');
    return $("#userInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加用户
 */
UserInfoDlg.addSubmit = function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    if (!this.validatePwd()) {
        Feng.error("两次密码输入不一致");
        return;
    }
    var chk_value =[];//定义一个数组
    $('input[name="isEnterprise"]:checked').each(function(){//遍历每一个名字为userProperty的复选框，其中选中的执行函数
        chk_value.push($(this).val());//将选中的值添加到数组chk_value中
    });

    var userFlag=0;
    if($.inArray("UserIsAdministratorAccount",chk_value)>=0){
        userFlag=1;
    }
    this.userInfoData.isEnterprise=userFlag;
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/mgr/add", function (data) {
        Feng.success("添加成功!");
        window.parent.MgrUser.table.refresh();
        UserInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userInfoData);
    ajax.start();
};


/**
 * 提交修改
 */
UserInfoDlg.editSubmit = function () {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    var chk_value =[];//定义一个数组
    $('input[name="isEnterprise"]:checked').each(function(){//遍历每一个名字为userProperty的复选框，其中选中的执行函数
        chk_value.push($(this).val());//将选中的值添加到数组chk_value中
    });

    var userFlag=0;
    if($.inArray("UserIsAdministratorAccount",chk_value)>=0){
        userFlag=1;
    }
    this.userInfoData.isEnterprise=userFlag;
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/mgr/edit", function (data) {
        Feng.success("修改成功!");
        if (window.parent.MgrUser != undefined) {
            window.parent.MgrUser.table.refresh();
            UserInfoDlg.close();
        }
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userInfoData);
    ajax.start();
};

/**
 * 修改密码
 */
UserInfoDlg.chPwd = function () {
    var ajax = new $ax(Feng.ctxPath + "/mgr/changePwd", function (data) {
        Feng.success("修改成功!");
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set("oldPwd");
    ajax.set("newPwd");
    ajax.set("rePwd");
    ajax.start();

};

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
            event.target).parents("#menuContent").length > 0)) {
        UserInfoDlg.hideDeptSelectTree();
    }
}
UserInfoDlg.addDays = function(date, days) {
    var d = new Date(date);
    d.setDate(d.getDate() + days);
    var resultDate = d.getFullYear() + "-";
    if ((d.getMonth() + 1) < 10) {
        resultDate += "0" + (d.getMonth() + 1) + "-";
    } else {
        resultDate += d.getMonth() + 1 + "-";
    }
    if (d.getDate() < 10) {
        resultDate += "0" + (d.getDate());
    } else {
        resultDate += d.getDate();
    }
    return resultDate;
}

$(function () {
    var currDate = new Date();
    var startDate = laydate.render({
        elem: '#startTime',
        done: function (value, date) {
            if (value !== '') {
                completeDate.config.min.year = date.year;
                completeDate.config.min.month = date.month - 1;
                completeDate.config.min.date = date.date;
            } else {
                completeDate.config.min.year = '';
                completeDate.config.min.month = '';
                completeDate.config.min.date = '';
            }
        }
    });
    //设置结束时间
    var completeDate = laydate.render({
        elem: '#endTime',
        done: function (value, date) {
            if (value !== '') {
                startDate.config.max.year = date.year;
                startDate.config.max.month = date.month - 1;
                startDate.config.max.date = date.date;
            } else {
                startDate.config.max.year = currDate.getFullYear();
                startDate.config.max.month = currDate.getMonth()+1;
                startDate.config.max.date =  currDate.getDate();
            }
        }
    });
    Feng.initValidator("userInfoForm", UserInfoDlg.validateFields);

    var ztree = new $ZTree("treeDemo", "/dept/treeSelect");
    ztree.bindOnClick(UserInfoDlg.onClickDept);
    ztree.init();
    instance = ztree;

    //初始化性别选项
    $("#sex").val($("#sexValue").val());

    // 初始化头像上传
    var avatarUp = new $WebUpload("avatar");
    avatarUp.setUploadBarId("progressBar");
    avatarUp.init();

    //开始时间初始化
    var curr_time = new Date();
    UserInfoDlg.strDate = curr_time.getFullYear() + "-";
    if ((curr_time.getMonth() + 1) < 10) {
        UserInfoDlg.strDate += "0" + (curr_time.getMonth() + 1)
            + "-";
    } else {
        UserInfoDlg.strDate += curr_time.getMonth() + 1 + "-";
    }
    if (curr_time.getDate() < 10) {
        UserInfoDlg.strDate += "0" + (curr_time.getDate());
    } else {
        UserInfoDlg.strDate += curr_time.getDate();
    }
    
    $('#validStatus').change(
        function() {
            var validStatusName = $(this).find("option:selected").text();
            if (validStatusName == '自定义') {
                $("#startTime").val('');
                $("#endTime").val('');
                $('#startTime').attr('disabled', false);
                $('#endTime').attr('disabled', false);
            } else if (validStatusName == '一个月') {
                $("#startTime").val(UserInfoDlg.strDate);
                $("#endTime").val(UserInfoDlg.addDays(UserInfoDlg.strDate, 30));
                $('#startTime').attr('disabled', true);
                $('#endTime').attr('disabled', true);
            } else if (validStatusName == '三个月') {
                $("#startTime").val(UserInfoDlg.strDate);
                $("#endTime").val(UserInfoDlg.addDays(UserInfoDlg.strDate, 90));
                $('#startTime').attr('disabled', true);
                $('#endTime').attr('disabled', true);
            } else if (validStatusName == '六个月') {
                $("#startTime").val(UserInfoDlg.strDate);
                $("#endTime").val(UserInfoDlg.addDays(UserInfoDlg.strDate, 180));
                $('#startTime').attr('disabled', true);
                $('#endTime').attr('disabled', true);
            } else if (validStatusName == '一年') {
                $("#startTime").val(UserInfoDlg.strDate);
                $("#endTime").val(UserInfoDlg.addDays(UserInfoDlg.strDate, 360));
                $('#startTime').attr('disabled', true);
                $('#endTime').attr('disabled', true);
            } else if (validStatusName == '二年') {
                $("#startTime").val(UserInfoDlg.strDate);
                $("#endTime").val(UserInfoDlg.addDays(UserInfoDlg.strDate, 720));
                $('#startTime').attr('disabled', true);
                $('#endTime').attr('disabled', true);
            } else if (validStatusName == '五年') {
                $("#startTime").val(UserInfoDlg.strDate);
                $("#endTime").val(UserInfoDlg.addDays(UserInfoDlg.strDate, 1800));
                $('#startTime').attr('disabled', true);
                $('#endTime').attr('disabled', true);
            } else if (validStatusName == '永久') {
                $("#startTime").val(UserInfoDlg.strDate);
                $("#endTime").val("9999-01-01");
                $('#startTime').attr('disabled', true);
                $('#endTime').attr('disabled', true);
            }

        });


    if($('#validStatus').val() === '8' ){
        $('#startTime').attr('disabled', false);
        $('#endTime').attr('disabled', false);
    }

});
layui.use('laydate', function(){
    var laydate = layui.laydate;

    //常规用法
    laydate.render({
        elem: '#birthday'
    });
});

