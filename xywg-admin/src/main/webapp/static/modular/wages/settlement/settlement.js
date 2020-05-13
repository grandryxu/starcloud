/**
 * 计工单管理初始化
 */
var Settlement = {
    id: "AccountTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    settlementCodeUploadId: '',
    data: [],
    selectImg: [],      //选中的附件,
    lookImgSelectSettlementId: '' //点击查看附件时的结算单编号
};

/**
 * 初始化表格的列
 */
Settlement.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '结算单编号', field: 'settlementCode', visible: true, align: 'center', valign: 'middle'},
        {title: '所属公司', field: 'companyName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目名称', field: 'projectName', visible: true, align: 'center', valign: 'middle'},
        {title: '项目编号', field: 'projectCode', visible: false, align: 'center', valign: 'middle'},
        {title: '班组', field: 'teamName', visible: false, align: 'center', valign: 'middle'},
        {
            title: '应发金额（元）', field: 'totalAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (data) {
                return data.toFixed(2);
            }
        },
        {
            title: '实发金额（元）', field: 'actualAmount', visible: true, align: 'center', valign: 'middle',
            formatter: function (data) {
                return data.toFixed(2);
            }
        },
        {
            title: '剩余金额（元）', field: 'payedMoney', visible: true, align: 'center', valign: 'middle',
            formatter: function (data) {
                return data.toFixed(2);
            }
        },
        {title: '发放状态', field: 'payStatusValue', visible: true, align: 'center', valign: 'middle'},
        {title: '分包审核人', field: 'constructValid', visible: true, align: 'center', valign: 'middle'},
        {title: '总包审核人', field: 'contractValid', visible: true, align: 'center', valign: 'middle'},
        {
            title: '审核状态', field: 'status', visible: true, align: 'center', valign: 'middle',
            formatter: function (data) {
                var value = '';
                if (data == 2) {
                    value = "分包已审核";
                } else if (data == 3) {
                    value = "总包已复核";
                } else if (data == 50) {
                    value = "已发放";
                }
                return value;
            }
        },
        {title: '创建时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
        {
            title: '操作', visible: true, align: 'center', valign: 'middle', formatter: function (val, row, index) {
            var operas = '';
            if (row.fileNums === 0) {
                operas += "<a href='javascript:void(0)' title='暂无附件' onclick='openAccessory(" + row.id + ")'  disabled='disabled'><i class='fa fa-eye-slash'></i></a>";
            } else {
                operas += "<a href='javascript:void(0)' title='查看附件' onclick='openAccessory(" + row.id + ")'><i class='fa fa-eye text-blue'></i></a>"
            }
            operas += "<a href='javascript:void(0)' settlementCode=" + row.id + " class='uploadFile' onClick='uploadFileBut(this)' title='附件上传 仅限图片格式'>&nbsp;&nbsp;<i class='fa fa-upload text-blue'></i></a>";
            return operas;
        }
        }
    ];
};


/**
 * 项目下拉框选择事件
 * @param val
 */
function changeForm(val) {
    var ajax = new $ax(Feng.ctxPath + "/settlement/getTeamMasterByProjectCode", function (data) {
        var htmlStr = "<option value=\"\">请选择</option>";
        for (var i = 0; i < data.length; i++) {
            htmlStr += ("<option value='" + data[i].teamSysNo + "'>" + data[i].teamName + "</option>")
        }
        $("#teamSysNo").html(htmlStr);

    });
    ajax.set("projectCode", val);
    ajax.start();
}

/**
 * 弹出添加页面
 */
Settlement.openAddHtml = function () {
    var index = layer.open({
        type: 2,
        title: '添加结算单',
        area: ['100%', '100%'],
        fix: false, //不固定
        maxmin: true,
        skin: 'layer-no-title',
        content: Feng.ctxPath + '/settlement/settlement_payroll',
        end: function () {
            Settlement.search();
        }
    });
    this.layerIndex = index;
};

/**
 * 编辑页面
 */
Settlement.openUpdateHtml = function () {
    if (this.check()) {

        if (hasOperationAuth()) {
            //项目级总包
            if (Settlement.seItem[0].isGeneralContractorOperation === 0) {
                Feng.error("您无权操作参建单位"+ Settlement.seItem[0].companyName+"的数据!");
                return;
            }
        }

        if (Settlement.seItem.length != 1) {
            Feng.error("编辑结算单时,只能选择一条!");
            return;
        }
        if (Settlement.seItem[0].payStatus != 1 || Settlement.seItem[0].status != 0) {
            Feng.error("只能编辑待发放或者没有被审核的结算单!");
            return;
        }

        var index = layer.open({
            type: 2,
            title: '编辑结算单',
            area: ['100%', '100%'],
            fix: false, //不固定
            maxmin: true,
            skin: 'layer-detail',
            content: Feng.ctxPath + '/settlement/settlement_edit?settlementCode=' + Settlement.seItem[0].settlementCode,
            end: function (data) {
                Settlement.search();
            }
        });
        this.layerIndex = index;
    }
};


/**
 * 检查是否选中
 */
Settlement.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length === 0) {
        Feng.info("请先选中表格中的记录！");
        return false;
        /*} else if(selected.length > 1){
            Feng.info("只能选择表格中的某一记录！");
            return false;*/
    }
    Settlement.seItem = selected;
    return true;
};


/**
 * 删除结算单
 */
Settlement.delete = function () {
    if (this.check()) {
        for (var i = 0; i < Settlement.seItem.length; i++) {
            if (hasOperationAuth()) {
                //项目级总包
                if (Settlement.seItem[i].isGeneralContractorOperation === 0) {
                    Feng.error("您无权操作参建单位"+ Settlement.seItem[i].companyName+"的数据!");
                    return;
                }
            }

            if (Settlement.seItem[i].payStatus !== 1 || Settlement.seItem[i].status !== 0) {
                Feng.error("只能删除待发放或者没有被审核的结算单!");
                return;
            }
        }

        Feng.confirm("确认删除?", function () {
            var ajax = new $ax(Feng.ctxPath + "/settlement/delete", function (data) {
                Feng.success("删除成功!");
                Settlement.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data + "!");
            });

            ajax.set("selected", JSON.stringify(Settlement.seItem));
            ajax.start();
            Settlement.search();

        });
    }

};


//双击查看事件
Settlement.onDblClickRow = function (e) {
    var index = layer.open({
        type: 2,
        title: '结算单详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/settlement/detail?settlementCode=' + e.settlementCode
    });
    this.layerIndex = index;
}


/**
 * 查询结算单列表
 */
Settlement.search = function () {
    var queryData = {};
    queryData['projectCode'] = $("#projectCode").val();
    queryData['teamSysNo'] = $("#teamSysNo").val();
    queryData['beginTime'] = $("#startDate").val();
    queryData['endTime'] = $("#endDate").val();

    Settlement.table.refresh({query: queryData});
};

/**
 * 工资发放
 */
Settlement.payroll = function () {
    if (this.check()) {
        for (var i = 0; i < Settlement.seItem.length; i++) {
            if (hasOperationAuth()) {
                //项目级总包
                if (Settlement.seItem[i].isGeneralContractorOperation === 0) {
                    Feng.error("您无权操作参建单位"+ Settlement.seItem[i].companyName+"的数据!");
                    return;
                }
            }

            if (Settlement.seItem[i].status != 3) {
                Feng.error(Settlement.seItem[i].settlementCode + "不满足发放条件或已经发放完成!");
                return;
            }
        }

        layer.msg('确定发放？', {
            time: 0 //不自动关闭
            , btn: ['确认', '取消']
            , yes: function (index) {
                layer.close(index);

                var ajax = new $ax(Feng.ctxPath + "/settlement/payroll", function (data) {
                    Feng.success("发放成功!");
                    Settlement.table.refresh();
                }, function (data) {
                    Feng.error("发放失败!" + data + "!");
                });
                ajax.set("selected", JSON.stringify(Settlement.seItem));
                ajax.start();
                Settlement.search();
            }
        });
    }
};

/**
 * 上传
 */
function uploadFileBut(val) {
    $("#uploadFileInput").trigger("click");
    Settlement.settlementCodeUploadId = $(val).attr("settlementCode");
    //console.info("结算单id:" + Settlement.settlementCodeUploadId);
}

/**
 * 真正的上传
 */
function fileUpload() {
    var maxsize = 100*1024*1024;
    var filesize = document.getElementById("uploadFileInput").files[0].size;
    if(filesize>maxsize) {
        Feng.error("上传的附件文件不能超过100M！！");
        return;
    }

    if ($('#form1 input[type=file]')[0].files.length === 0) {
        return;
    }
    var formData = new FormData($("#form1")[0]);
    formData.append('settlementCode', Settlement.settlementCodeUploadId);
    $.ajax({
        url: Feng.ctxPath + "/settlement/uploadAccessory",
        type: "POST",
        data: formData,
        contentType: false,
        processData: false,
        async: false,
        success: function (data) {
            if (data.code != 200) {
                Feng.error(data.message);
            } else {
                Feng.success(data.message);
                Settlement.table.refresh();
            }
            $("#uploadFileInput").val("");
        },
        error: function (data) {
            Feng.error(data.message);
        }
    });

}


Settlement.onLoadSuccess = function (data) {
    Settlement.data = data.rows;
};

/**
 * 审核
 */
Settlement.checkInfo = function () {
    if (this.check()) {
        for (var i = 0; i < Settlement.seItem.length; i++) {
            if (hasOperationAuth()) {
                //项目级总包
                if (Settlement.seItem[i].isGeneralContractorOperation === 0) {
                    Feng.error("您无权操作参建单位"+ Settlement.seItem[i].companyName+"的数据!");
                    return;
                }
            }

            if (Settlement.seItem[i].status != '' && Settlement.seItem[i].status != 0) {
                Feng.error("只能审核待审核状态的结算单!");
                return;
            }
        }
        layer.msg('确定审核？', {
            time: 0 //不自动关闭
            , btn: ['确认', '取消']
            , yes: function (index) {
                layer.close(index);
                var ajax = new $ax(Feng.ctxPath + "/settlement/checkInfo", function (data) {
                    Feng.success("审核成功!");
                    Settlement.table.refresh();
                }, function (data) {
                    Feng.error("审核失败!" + data + "!");
                });
                ajax.set("selected", JSON.stringify(Settlement.seItem));
                ajax.start();
                Settlement.search();
            }
        });
    }
};


/**
 * 复核
 */
Settlement.review = function () {
    if (this.check()) {
        for (var i = 0; i < Settlement.seItem.length; i++) {
            if (Settlement.seItem[i].status != 2) {
                Feng.error("只能复核待复核状态的结算单!");
                return;
            }
        }
        layer.msg('确定复核？', {
            time: 0 //不自动关闭
            , btn: ['确认', '取消']
            , yes: function (index) {
                layer.close(index);
                var ajax = new $ax(Feng.ctxPath + "/settlement/review", function (data) {
                    Feng.success("复核成功!");
                    Settlement.table.refresh();
                }, function (data) {
                    Feng.error("复核失败!" + data + "!");
                });
                ajax.set("selected", JSON.stringify(Settlement.seItem));
                ajax.start();
                Settlement.search();
            }
        });
    }
};

$(function () {
    //初始化时间插件
    //重置的时间集合
    Settlement.resetDate = Feng.initStartEndDate();
    var defaultColunms = Settlement.initColumn();
    var table = new BSTable(Settlement.id, "/settlement/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams();
    table.onLoadSuccess = Settlement.onLoadSuccess;
    table.onDblClickRow = Settlement.onDblClickRow;
    Settlement.table = table.init();
    Feng.initChosen();

    if ($("#isEnterprise").val() === "0") {
        $("#payrollBut").hide();
        $("#checkInfoBut").hide();
        $("#reviewBut").hide();
    }

});

/**
 * 查看附件图片
 */
function openAccessory(settlementId) {
    Settlement.lookImgSelectSettlementId = settlementId;
    var imgLength = 0;
    var html = '';
    var ajax = new $ax(Feng.ctxPath + "/settlement/getAccessoryPicture", function (data) {
        var pc = data.data;
        imgLength = pc.length;
        Settlement.pictureNums = imgLength;

        for (var i = 0; i < pc.length; i++) {
            html += ("<div class='col-sm-3' style='margin-top: 5px;'>" +
                "<div onclick='changeSelectGou(this)' style='border:2px solid #DBDBDB;height: 300px;line-height: 300px;text-align: center;'>" +
                "<img class='selectGouImg' style='width:auto;height:auto;max-width:100%;max-height:100%;text-align: center;vertical-align: middle;' " +
                "onmouseover='onmouseoverImg(this)' onmouseout='onmouseoutImg(this)' " +
                "src='" + pc[i].src + "' alt='" + pc[i].alt + "' pid='" + pc[i].pid + "'>" +
                "<img class='changeSelectGouImg'  style='position: absolute;right: 16px;display:none;' src='static/img/select_gou3.png'  >" +
                "</div>" +
                "</div>")
        }
        $("#imagesPicture").html(html);

    }, function (data) {
    });
    ajax.set("id", settlementId + "");
    ajax.start();

    if (imgLength === 0) {
        layer.msg("暂无附件");
        return;
    }
    var index = layer.open({
        type: 1,
        title: '附件详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: $("#accessoryPictureDiv").html(),
        end: function () {
            Settlement.selectImg = [];
        }
    });
}

/**
 * 选中图片
 */
function changeSelectGou(ele) {
    if ($(ele).children(".changeSelectGouImg").css('display') === "none") {
        $(ele).children(".changeSelectGouImg").css('display', "inline-block");
        Settlement.selectImg.push($(ele).children(".selectGouImg").attr('pid') + '');
    } else {
        $(ele).children(".changeSelectGouImg").css('display', "none");
        //Settlement.selectImg.remove($(ele).attr('pid'));
        Settlement.selectImg.splice($.inArray($(ele).children(".selectGouImg").attr('pid') + '', Settlement.selectImg), 1);
    }
}

/**
 * 删除附件
 */
function deletePicture() {
    if(Settlement.selectImg.length === 0){
        Feng.info("请先选中附件！");
        return;
    }
    Feng.confirm("确认删除选中的附件?", function () {
        var ajax = new $ax(Feng.ctxPath + "/settlement/deleteAccessoryPicture", function (data) {
            layer.closeAll();
            if (Settlement.pictureNums === Settlement.selectImg.length) {
                Settlement.table.refresh();
                Feng.success("删除成功!")
            } else {
                openAccessory(Settlement.lookImgSelectSettlementId);
            }
            Settlement.selectImg = [];
            /*Feng.success("删除成功!");*/
        }, function (data) {
            Feng.error("删除失败!" + data + "!");
        });
        ajax.set("ids", JSON.stringify(Settlement.selectImg));
        ajax.start();
    });
}

function onmouseoverImg(eve) {
    //layer.msg(11111111);
}

function onmouseoutImg(eve) {
    //layer.msg(222222);
}

function  resetDate() {
    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = currentDate.getMonth()+1;
    var date = currentDate.getDate();

    for(var i=0;i<Settlement.resetDate.length; i++) {
        var dateObject = Settlement.resetDate[i];

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
