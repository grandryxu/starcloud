/**
 * 初始化从业人员基础信息详情对话框
 */
var EmployeeMasterInfoDlg = {
    employeeMasterInfoData: {},
    validateFields: {
        employeeName: {
            validators: {
                notEmpty: {
                    message: '姓名不能为空'
                },
                stringLength: {
                    max: 50,
                    message: '最大长度为50'
                },
                regexp: {
                    regexp: /^[\u4E00-\u9FA5]{1,5}$|^[A-Za-z]{1,10}$/,
                    message: '只允许中英文'
                }
            }
        },
        idCardType: {
            validators: {
                notEmpty: {
                    message: '证件类型不能为空'
                }
            }
        },
        nation: {
            validators: {
                notEmpty: {
                    message: '民族不能为空'
                }
            }
        },
        professionalType: {
            validators: {
                notEmpty: {
                    message: '职称不能为空'
                }
            }
        },
        address: {
            validators: {
                notEmpty: {
                    message: '地址不能为空'
                },
                stringLength: {
                    max: 200,
                    message: '最大长度为200'
                }
            }
        },
        gender: {
            validators: {
                notEmpty: {
                    message: '性别不能为空'
                }
            }
        },
        birthday: {
            validators: {
                notEmpty: {
                    message: '出生日期不能为空'
                }
            }
        },
        idCardNumber: {
            validators: {
                notEmpty: {
                    message: '证件号不能为空'
                },
                stringLength: {
                    max: 20,
                    message: '最大长度为20'
                },
                regexp: {
                    regexp: /^[a-zA-Z0-9]*$/,
                    message: '格式不正确'
                }
            }
        },
        cellPhone: {
            validators: {
                notEmpty: {
                    message: '手机号不能为空'
                },
                stringLength: {
                    min: 11,
                    max: 11,
                    message: '请输入11位手机号码'
                },
                regexp: {
                    regexp: /^[1][3,4,5,7,8][0-9]{9}$/,
                    message: '请输入正确的手机号码'
                }
            }
        },
        certificationName: {
            validators: {
                notEmpty: {
                    message: '证书名称不能为空'
                },
                stringLength: {
                    max: 25,
                    message: '最大长度为25'
                }
            }
        },
        organizationCode: {
            validators: {
                notEmpty: {
                    message: '所属公司不能为空'
                }
            }
        },
        jobType: {
            validators: {
                notEmpty: {
                    message: '聘用方式不能为空'
                }
            }
        },
        hireDate: {
            validators: {
                notEmpty: {
                    message: '入职日期不能为空'
                }
            }
        },
        jobType: {
            validators: {
                notEmpty: {
                    message: '聘用方式不能为空'
                }
            }
        },
        jobStatus: {
            validators: {
                notEmpty: {
                    message: '状态不能为空'
                }
            }
        },
        remark: {
            validators: {
                stringLength: {
                    max: 100,
                    message: '最大长度为100'
                }
            }
        },
        cellPhone: {
            validators: {
                regexp: {
                    regexp: /^[1][3,4,5,7,8][0-9]{9}$/,
                    message: '请输入正确的手机号码'
                }
            }
        },
        job: {
            validators: {
                stringLength: {
                    max: 20,
                    message: '最大长度为20'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
EmployeeMasterInfoDlg.clearData = function () {
    this.employeeMasterInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EmployeeMasterInfoDlg.set = function (key, val) {
    this.employeeMasterInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EmployeeMasterInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
EmployeeMasterInfoDlg.close = function () {
    parent.layer.close(window.parent.EmployeeMaster.layerIndex);
}
function IdentityCodeValid(code) {
    var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
    var tip = "";
    var pass= true;

    if(!code || !/^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i.test(code)){
        tip = "请输入正确的身份证号码";
        pass = false;
    }

    else if(!city[code.substr(0,2)]){
        tip = "地址编码错误";
        pass = false;
    }
    else{
        //18位身份证需要验证最后一位校验位
        if(code.length == 18){
            code = code.split('');
            //∑(ai×Wi)(mod 11)
            //加权因子
            var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
            //校验位
            var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
            var sum = 0;
            var ai = 0;
            var wi = 0;
            for (var i = 0; i < 17; i++)
            {
                ai = code[i];
                wi = factor[i];
                sum += ai * wi;
            }
            var last = parity[sum % 11];
            if(parity[sum % 11] != code[17]){
                tip = "请输入正确的证件号";
                pass =false;
            }
        }
    }
    if(!pass) Feng.error(tip);;
    return pass;
}

/**
 * 根据证件类型和证件号查询信息
 */

$("#search").click(function () {

    var idCardType = $("#idCardType").val();
    var idCardNumber = $("#idCardNumber").val();
    if (idCardType == "1"&&!IdentityCodeValid(idCardNumber)) {
        return;
    }
    if ((/[\u4e00-\u9fa5]+/).test(idCardNumber)) {
        Feng.error("请输入正确的证件号");
        return;
    }
    if(idCardType==""){
        Feng.error("请选择证件类型");
        return;
    }
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/employeeMaster/searchEmployee", function (data) {
            if (data.length == 1) {
                $('#hireDate').attr('disabled', false);
                $('#terminationDate').attr('disabled', false);
                $('#retireDate').attr('disabled', false);
                $("#employeeName").val(data[0].employeeName);
                $("#culturelevelType").val(data[0].culturelevelType);
                $("#nation").val(data[0].nation);
                if(data[0].gender==1){
                    $("input[id=inlineRadiogender1]").removeAttr("checked");
                    $("input[id=inlineRadiogender2]").attr("checked","checked");
                }else{
                    $("input[id=inlineRadiogender1]").attr("checked","checked");
                    $("input[id=inlineRadiogender2]").removeAttr("checked");
                }
                $("#birthday").val(data[0].birthday);
                $("#address").val(data[0].address);
                $("#cellPhone").val(data[0].cellPhone);
                $("#workDate").val(data[0].workDate);
                $("#professionalType").val(data[0].professionalType);
                $("#professionalLevel").val(data[0].professionalLevel);
                $("#headImagePathPreId img").attr("src",data[0].headImagePath != ''?Feng.imagePath+data[0].headImagePath:'/static/img/avatar.png');
                $("#headImagePathBtnId").hide()
                var id = $("#professionalLevel").val();
                if (id != 0) {
                    var ajax = new $ax(Feng.ctxPath + "/employeeMaster/getProfessionalType", function (data) {
                        var html = '';
                        for (var i = 0; i < data.length; i++) {
                            if (data[i].num == $("#professionalType").val()) {
                                html += '<option value="' + data[i].num + '"selected>' + data[i].name + '</option>';
                            } else {
                                html += '<option value="' + data[i].num + '">' + data[i].name + '</option>';
                            }
                        }
                        $('#professionalType').html(html);

                    }, function (data) {
                        // Feng.error("添加失败!" + data.responseJSON.message + "!");
                    });
                    ajax.set({'id': id});
                    ajax.start();
                }
            } else {
                $("input").attr("disabled",false);
                $("select").attr("disabled",false);
                $('#idCardType').attr('disabled', true);
                $('#idCardNumber').attr('disabled', true);
                $("#remark").attr('disabled', false);

            }
        }, function (data) {
            Feng.error("查询失败!" + data.responseJSON.message + "!");
        });
        var params = {
            idCardType: idCardType,
            idCardNumber: idCardNumber
        };
        ajax.set(params);
        ajax.start();


})
/**
 * 收集数据
 */
EmployeeMasterInfoDlg.collectData = function () {
    this
        .set('id')
        .set('employeeName')
        .set('iconImage')
        .set('idImage')
        .set('isAuth')
        .set('isFace')
        .set('isAudit')
        .set('idCardType')
        .set('idCardNumber')
        .set('gender')
        .set('cultureLevelType')
        .set('nation')
        .set('birthday')
        .set('headImagePath')
        .set('address')
        .set('cellPhone')
        .set('workDate')
        .set('professionalType')
        .set('professionalLevel')
        .set('createDate')
        .set('createUser')
        .set('updateDate')
        .set('updateUser')
        .set('job')
        .set('jobType')
        .set('jobStatus')
        .set('hireDate')
        .set('terminationDate')
        .set('retireDate')
        .set('remark')
        .set('organizationCode')
        .set('isDel');
}


/**
 * 提交添加
 */
EmployeeMasterInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/employeeMaster/add", function (data) {
        Feng.success("添加成功!");
        window.parent.EmployeeMaster.table.refresh();
        EmployeeMasterInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.employeeMasterInfoData);
    ajax.start();
}

/**
 * 验证数据是否为空
 *
 */
EmployeeMasterInfoDlg.validate = function () {
    $('#employeeMasterForm').data("bootstrapValidator").resetForm();
    $('#employeeMasterForm').bootstrapValidator('validate');
    return $("#employeeMasterForm").data('bootstrapValidator').isValid();
};
/**
 * 提交修改
 */
EmployeeMasterInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/employeeMaster/update", function (data) {
        Feng.success("修改成功!");
        window.parent.EmployeeMaster.table.refresh();
        EmployeeMasterInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.employeeMasterInfoData);
    ajax.start();
}

var jobStatusChange = function () {
    var jobStatus = $("#jobStatus").val();

}

var professionalLevelChange = function () {
    var id = $("#professionalLevel").val();
    if (id != 0) {
        var ajax = new $ax(Feng.ctxPath + "/employeeMaster/getProfessionalType", function (data) {
            var html = '';
            html+='<option value="">请选择</option>'
            for (var i = 0; i < data.length; i++) {

                html += '<option value="' + data[i].num + '">' + data[i].name + '</option>';
            }
            $('#professionalType').html(html);

        }, function (data) {
        });
        ajax.set({'id': id});
        ajax.start();
    }
}

var PersonalCertifications = {
    id: "PersonalCertificationsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};


PersonalCertifications.formParams = function () {
    var queryData = {};
    queryData['employeeName'] = $("#employeeName").val();
    queryData['idCardNumber'] = $("#idCardNumber").val();
    queryData['idCardType'] = $("#idCardType").val();
    queryData['certificationTypeCode'] = $("#certificationTypeCode").val();
    queryData['certificationName'] = $("#certificationName").val();
    return queryData;
}
$(function () {
    var hireDate = laydate.render({
        elem: '#hireDate',
        max: 0,
        done: function (value, date) {
            if (value !== '') {
                terminationDate.config.min.year = date.year;
                terminationDate.config.min.month = date.month - 1;
                terminationDate.config.min.date = date.date;
                retireDate.config.min.year = date.year;
                retireDate.config.min.month = date.month - 1;
                retireDate.config.min.date = date.date;
            } else {
                terminationDate.config.min.year = '';
                terminationDate.config.min.month = '';
                terminationDate.config.min.date = '';
                retireDate.config.min.year = '';
                retireDate.config.min.month = '';
                retireDate.config.min.date = '';
            }
        }
    });
    var terminationDate = laydate.render({
        elem: '#terminationDate',
        done: function (value, date) {
            if (value !== '') {
                hireDate.config.max.year = date.year;
                hireDate.config.max.month = date.month - 1;
                hireDate.config.max.date = date.date;
                retireDate.config.min.year = date.year;
                retireDate.config.min.month = date.month - 1;
                retireDate.config.min.date = date.date;
            } else {
                hireDate.config.max.year = '';
                hireDate.config.max.month = '';
                hireDate.config.max.date = '';
                retireDate.config.min.year = '';
                retireDate.config.min.month = '';
                retireDate.config.min.date = '';
            }
        }
    });
    var retireDate = laydate.render({
        elem: '#retireDate',
        done: function (value, date) {
            if (value !== '') {
                hireDate.config.max.year = date.year;
                hireDate.config.max.month = date.month - 1;
                hireDate.config.max.date = date.date;
                terminationDate.config.max.year = date.year;
                terminationDate.config.max.month = date.month - 1;
                terminationDate.config.max.date = date.date;
            } else {
                hireDate.config.max.year = '';
                hireDate.config.max.month = '';
                hireDate.config.max.date = '';
                terminationDate.config.max.year = '';
                terminationDate.config.max.month = '';
                terminationDate.config.max.date = '';
            }
        }
    });

    laydate.render({elem: '#birthday', max: 0});
    laydate.render({elem: '#workDate', max: 0});


    var id = $("#professionalLevel").val();
    if (id != 0) {
        var ajax = new $ax(Feng.ctxPath + "/employeeMaster/getProfessionalType", function (data) {
            var html = '';
            for (var i = 0; i < data.length; i++) {
                if (data[i].num == $("#professionalType").val()) {
                    html += '<option value="' + data[i].num + '"selected>' + data[i].name + '</option>';
                } else {
                    html += '<option value="' + data[i].num + '">' + data[i].name + '</option>';
                }
            }
            $('#professionalType').html(html);

        }, function (data) {
            // Feng.error("添加失败!" + data.responseJSON.message + "!");
        });
        ajax.set({'id': id});
        ajax.start();
    }
    Feng.initValidator("employeeMasterForm", EmployeeMasterInfoDlg.validateFields);
    //初始化性别选项
    // $("#gender").val($("#genderValue").val());
    // $("#idCardType").val($("#idCardTypeValue").val());
    // $("#nation").val($("#nationValue").val());
    $("#isAuth").val($("#isAuthValue").val());
    $("#professionalType").val($("#professionalTypeValue").val());
    $("#cultureLevelType").val($("#cultureLevelTypeValue").val());
    $("#isFace").val($("#isFaceValue").val());
    $("#jobType").val($("#jobTypeValue").val());
    $("#jobStatus").val($("#jobStatusValue").val());
    $("#organizationCode").val($("#organizationCodeValue").val());

    // 初始化头像上传
    var avatarUp = new $WebUpload("headImagePath");
    avatarUp.setUploadBarId("progressBar");
    avatarUp.setUploadPath(Feng.ctxPath + "/workerMaster/upload");
    avatarUp.init();

    var defaultColunms = PersonalCertifications.initColumn();
    var table = new BSTable(PersonalCertifications.id, "/personalCertifications/getListByIdCard", defaultColunms);
    table.setQueryParams(PersonalCertifications.formParams());
    table.onDblClickRow = PersonalCertifications.searchInfo;//双击事件所对应的方法 要放在init之前
    table.setPaginationType("server");
    PersonalCertifications.table = table.init();
});
/**
 * 初始化表格的列
 */
PersonalCertifications.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'employeeName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型', field: 'idCardTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件编号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',formatter:function (data) {
            return Feng.hiddenIdCard(data);
        }},
        {title: '证书类型', field: 'certificationTypeCodeName', visible: true, align: 'center', valign: 'middle'},
        {title: '专业编码', field: 'professionCode', visible: true, align: 'center', valign: 'middle'},
        {title: '类别/工种', field: 'jobTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '证书等级', field: 'certificationLevelTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '证书名称', field: 'certificationName', visible: true, align: 'center', valign: 'middle'},
        {title: '证书有效起始日期', field: 'validBeginDate', visible: true, align: 'center', valign: 'middle'},
        {title: '证书有效截止日期', field: 'validEndDate', visible: true, align: 'center', valign: 'middle'},
        {title: '发证机关', field: 'issueBy', visible: true, align: 'center', valign: 'middle'},
        {title: '发证日期', field: 'issueDate', visible: true, align: 'center', valign: 'middle'},
        {title: '资格状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'},
    ];
};

/**
 * 点击添加人员资格证书
 */
PersonalCertifications.openAddPersonalCertifications = function () {
    var index = layer.open({
        type: 2,
        title: '添加人员资格证书',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/personalCertifications/personalCertifications_addEm/' + $('#idCardNumber').val() + '/' + $('#idCardType').val()
    });
    this.layerIndex = index;
};
/**
 * 打开查看人员资格证书编辑
 */
PersonalCertifications.openPersonalCertificationsDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '人员资格证书详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: 'layer-no-title',
            content: Feng.ctxPath + '/personalCertifications/personalCertifications_update/' + PersonalCertifications.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 双击查看
 */
PersonalCertifications.searchInfo = function (e) {
        var index = layer.open({
            type: 2,
            title: '从业人员详情信息',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            skin: 'layer-no-title',
            content: Feng.ctxPath + '/personalCertifications/personalCertifications_view/' +e.id
        });
        this.layerIndex = index;
}
/**
 * 删除人员资格证书
 */
PersonalCertifications.delete = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        layer.confirm('确认删除？', {
            btn: ['确定', '取消'] //按钮
        }, function (index) {
            var personalCertificationIds = "";
            for (var i = 0; i < selected.length; i++) {
                personalCertificationIds += selected[i].id + ","
            }
            personalCertificationIds = personalCertificationIds.substring(0, personalCertificationIds.length - 1);
            var ajax = new $ax(Feng.ctxPath + "/personalCertifications/delete", function (data) {
                Feng.success("删除成功!");
                PersonalCertifications.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("personalCertificationIds", personalCertificationIds);
            ajax.start();
            layer.close(index);
        });
    }
};


/**
 * 检查是否选中
 */
PersonalCertifications.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        PersonalCertifications.seItem = selected[0];
        return true;
    }
};

function getList() {
    var paretntVal = $('#professionalLevel').val();
    var val = $('#professionalLevel').val();
}



