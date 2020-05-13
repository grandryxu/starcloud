var JoinedSubContractors = $(".JoinedSubContractors");
var projectId = {};
/**
 * 初始化项目信息详情对话框
 */
var ProjectMasterInfoDlg = {
    projectMasterInfoData: {} ,
    validateFields: {
        projectName: {
            validators: {
                notEmpty: {
                    message: '项目名称不能为空'
                },
                stringLength: {
                    max: 100,
                    message: '最大长度为100'
                }
            }
        },
        generalContractorCode: {
            validators: {
                stringLength: {
                    max: 40,
                    message: '最大长度为40'
                }
            }
        },
        ownerName: {
            validators: {
                notEmpty: {
                    message: '建设单位名称不能为空'
                },
                stringLength: {
                    max: 80,
                    message: '最大长度为80'
                },
                regexp: {
                    regexp: /^[a-zA-Z]|[\u4e00-\u9fa5]{1,80}$/,
                    message: '请输入正确的建设单位名称'
                }
            }
        },
        builderLicenceNum: {
            validators: {
                notEmpty: {
                    message: '施工许可证编号不能为空'
                },
                stringLength: {
                    max: 50,
                    message: '最大长度为50'
                },
                regexp: {
                    regexp:  /^[a-zA-Z0-9]{1,50}$/,
                    message: '请输入正确的施工许可证编号'
                }
            }
        },
        buildingArea: {
            validators: {
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                },
                regexp: {
                    regexp: /^[0-9]*$/,
                    message: '请输入正确的建筑面积'
                }
            }
        },
        startDate: {
            validators: {
                notEmpty: {
                    message: '开工时间不能为空'
                }
            }
        },
        projectSource: {
            validators: {
                stringLength: {
                    max: 20,
                    message: '最大长度为20'
                }
            }
        },
        address: {
            validators: {
                notEmpty: {
                    message: '项目地址不能为空'
                },
                stringLength: {
                    max: 255,
                    message: '最大长度为255'
                }
            }
        },
        generalContractorName: {
            validators: {
                stringLength: {
                    max: 100,
                    message: '最大长度为100'
                }
            }
        },
        contractorOrgCode: {
            validators: {
                notEmpty: {
                    message: '承包单位组织机构代码不能为空'
                },
                stringLength: {
                    max: 10,
                    message: '最大长度为10'
                }
            }
        },
        buildCorporationCode: {
            validators: {
                notEmpty: {
                    message: '建设单位社会信用代码不能为空'
                },
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                },
                regexp: {
                    regexp:  /[0-9A-Z]{18}/,
                    message: '请输入正确的社会信用代码'
                }
            }
        },
        totalContractAmt: {
            validators: {
                notEmpty: {
                    message: '承包合同额不能为空'
                },
                stringLength: {
                    max: 18,
                    message: '最大长度为18'
                },
                regexp: {
                    regexp: /^[0-9]*$/,
                    message: '请输入正确的承包合同额'
                }
            }
        },
        completeDate: {
            validators: {
                notEmpty: {
                    message: '竣工时间不能为空'
                }
            }
        },
        radius: {
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
ProjectMasterInfoDlg.clearData = function () {
    this.projectMasterInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectMasterInfoDlg.set = function (key, val) {
    this.projectMasterInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProjectMasterInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProjectMasterInfoDlg.close = function () {
    parent.layer.close(window.parent.ProjectMaster.layerIndex);
}

/**
 * 收集数据
 */
ProjectMasterInfoDlg.collectData = function () {
    this
        .set('id')
        //.set('projectCode')
        //.set('buildProjectCode')
        .set('contractorOrgCode')
        .set('generalContractorCode')
        .set('generalContractorName')
        .set('projectName')
        .set('projectActivityType')
        .set('projectDescription')
        .set('projectCategory')
        .set('isMajorProject')
        .set('ownerName')
        .set('buildCorporationCode')
        .set('builderLicenceNum')
        .set('areaCode')
        .set('totalContractAmt')
        .set('buildingArea')
        .set('startDate')
        .set('completeDate')
        .set('projectSource')
        .set('projectStatus')
        .set('lng')
        .set('lat')
        .set('radius')
        .set('address')
        .set('deviceType')
        //.set('createDate')
        //.set('createUser')
        //.set('updateDate')
        //.set('updateUser')
        .set('status')
        .set('isDel');
}

/**
 * 提交添加
 */
ProjectMasterInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if(!this.validate()){
        return;
    }

    //获取参建单位信息
    /*   var result = ProjectMasterInfoDlg.getJoinedSubContractors();
       if(result.flag === false){
           return;
       }
       this.projectMasterInfoData.joinedSubContractorList = result.joinedSubContractorList;*/
    ProjectMasterInfoDlg.projectMasterInfoData.projectCode = "PROJECT" + new Date().getTime();
    ProjectMasterInfoDlg.projectMasterInfoData.buildProjectCode = ProjectMasterInfoDlg.projectMasterInfoData.projectCode;
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cooperationProjectMaster/add", function (data) {
        Feng.success("添加成功!");
        $("input").disable(true);
        $("#projectCode").val(data.projectCode)
        window.parent.ProjectMaster.table.refresh();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.projectMasterInfoData);
    ajax.start();
}


/**
 * 验证数据是否为空
 */
ProjectMasterInfoDlg.validate = function () {
    $('#ProjectMasterInfoForm').data("bootstrapValidator").resetForm();
    $('#ProjectMasterInfoForm').bootstrapValidator('validate');
    return $("#ProjectMasterInfoForm").data('bootstrapValidator').isValid();
};


/**
 * 提交修改
 */
ProjectMasterInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if(!this.validate()){
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/cooperationProjectMaster/update", function (data) {
        Feng.success("修改成功!");
        window.parent.ProjectMaster.table.refresh();
        ProjectMasterInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.projectMasterInfoData);
    ajax.start();
}
//<div class="row">
//<div class="row">

/**
 * 承包商
 * @type {Mixed|jQuery|HTMLElement}
 */
var generalContractorName = $("#generalContractorName");
var generalContractorsSuggest = generalContractorName.bsSuggest({
    keyField: "companyName",    //  input 显示的数据
    param1: "socialCreditNumber",   //item点击事件的需要的数据1
    param2: "organizationCode",    //item点击事件的需要的数据2
    showContent: "companyName",   //下拉框显示数据
    allowNoKeyword: false,
    multiWord: true,
    separator: ",",
    getDataMethod: "url",
    url: Feng.ctxPath + "/subContractor/selectGeneralContractors/" + generalContractorName.val(),
    processData: function (json) {
        var i, len, data = {value: []};
        if (!json || json.length === 0) {
            return false
        }
        len = json.length;
        jsonStr = "{'value':[";
        for (i = 0; i < len; i++) {
            data.value.push(json[i])
        }
        data.defaults = "generalContractorName";
        return data
    }
}).on('onSetSelectValue', function (e, data) {
    $("#contractorOrgCode").val(data.param1 === null ? "" : data.param1);
    $("#generalContractorCode").val(data.param2 === null ? "" : data.param2);
})

/**
 * 建设单位
 * @type {Mixed|jQuery|HTMLElement}
 */
var ownerName = $("#ownerName");
var ownerNameSuggest = ownerName.bsSuggest({
    param1: "socialCreditNumber",        //item点击事件的需要的数据1
    keyField: "companyName",  //  input 显示的数据
    showContent: "companyName", //下拉显示的数据
    allowNoKeyword: false,
    multiWord: true,
    separator: ",",
    getDataMethod: "url",
    url: Feng.ctxPath + "/subContractor/selectGeneralContractors/" + ownerName.val(),
    processData: function (json) {
        var i, len, data = {value: []};
        if (!json || json.length === 0) {
            return false
        }
        len = json.length;
        jsonStr = "{'value':[";
        for (i = 0; i < len; i++) {
            data.value.push(json[i])
        }
        data.defaults = "ownerName";
        return data
    }
}).on('onSetSelectValue', function (e, data) {
    $("#buildCorporationCode").val(data.param1 === null ? "" : data.param1);
})


/**
 * 删除参建单位
 */
ProjectMasterInfoDlg.removeJoinedSubContractor = function (e) {
    e.parentNode.parentNode.removeChild(e.parentNode);
}

/**
 * 锚点验证
 */
ProjectMasterInfoDlg.anchorCheck = function () {
    var projectCode = $("#projectCode").val();
    if (projectCode === "" || projectCode === null) {
        alert("请先添加项目");
        $("#projectInfoTab").addClass("active");
        $("#joinedSubContractorTab").removeClass("active");
        return false;
    } else {
        var defaultColunms = ProjectSubContractor.initColumn();
        var table = new BSTable(ProjectSubContractor.id, "/projectSubContractor/list", defaultColunms);
        table.setPaginationType("server");
        table.setQueryParams({
            projectProjectCode: projectCode
        })
        ProjectSubContractor.table = table.init();
    }
}

$(function () {
    if($("#info").data("flag")=== true){
        //修改页面
        generalContractorName.bsSuggest("disable");
        ownerName.bsSuggest("disable");
    }
    Feng.initValidator("ProjectMasterInfoForm", ProjectMasterInfoDlg.validateFields);
});