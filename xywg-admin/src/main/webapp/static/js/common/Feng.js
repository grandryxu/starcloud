var Feng = {
    ctxPath: "",
    imagePath: "/labor/",
    addCtx: function (ctx) {
        if (this.ctxPath == "") {
            this.ctxPath = ctx;
        }
    },
    confirm: function (tip, ensure) {//询问框
        parent.layer.confirm(tip, {
            btn: ['确定', '取消']
        }, function (index) {
            ensure();
            parent.layer.close(index);
        }, function (index) {
            parent.layer.close(index);
        });
    },
    log: function (info) {
        console.log(info);
    },
    alert: function (info, iconIndex) {
        parent.layer.msg(info, {
            icon: iconIndex
        });
    },
    info: function (info) {
        Feng.alert(info, 0);
    },
    success: function (info) {
        Feng.alert(info, 1);
    },
    error: function (info) {
        Feng.alert(info, 2);
    },
    infoDetail: function (title, info) {
        var display = "";
        if (typeof info == "string") {
            display = info;
        } else {
            if (info instanceof Array) {
                for (var x in info) {
                    display = display + info[x] + "<br/>";
                }
            } else {
                display = info;
            }
        }
        parent.layer.open({
            title: title,
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['950px', '600px'], //宽高
            content: '<div style="padding: 20px;">' + display + '</div>'
        });
    },
    writeObj: function (obj) {
        var description = "";
        for (var i in obj) {
            var property = obj[i];
            description += i + " = " + property + ",";
        }
        layer.alert(description, {
            skin: 'layui-layer-molv',
            closeBtn: 0
        });
    },
    showInputTree: function (inputId, inputTreeContentId, leftOffset, rightOffset) {
        var onBodyDown = function (event) {
            if (!(event.target.id == "menuBtn" || event.target.id == inputTreeContentId || $(event.target).parents("#" + inputTreeContentId).length > 0)) {
                $("#" + inputTreeContentId).fadeOut("fast");
                $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
            }
        };

        if (leftOffset == undefined && rightOffset == undefined) {
            var inputDiv = $("#" + inputId);
            var inputDivOffset = $("#" + inputId).offset();
            $("#" + inputTreeContentId).css({
                left: inputDivOffset.left + "px",
                top: inputDivOffset.top + inputDiv.outerHeight() + "px"
            }).slideDown("fast");
        } else {
            $("#" + inputTreeContentId).css({
                left: leftOffset + "px",
                top: rightOffset + "px"
            }).slideDown("fast");
        }

        $("body").bind("mousedown", onBodyDown);
    },
    baseAjax: function (url, tip) {
        var ajax = new $ax(Feng.ctxPath + url, function (data) {
            Feng.success(tip + "成功!");
        }, function (data) {
            Feng.error(tip + "失败!" + data.responseJSON.message + "!");
        });
        return ajax;
    },
    changeAjax: function (url) {
        return Feng.baseAjax(url, "修改");
    },
    zTreeCheckedNodes: function (zTreeId) {
        var zTree = $.fn.zTree.getZTreeObj(zTreeId);
        var nodes = zTree.getCheckedNodes();
        var ids = "";
        for (var i = 0, l = nodes.length; i < l; i++) {
            ids += "," + nodes[i].id;
        }
        return ids.substring(1);
    },
    zTreeRadiosNodes: function (zTreeId) {
        var zTree = $.fn.zTree.getZTreeObj(zTreeId);
        var nodes = zTree.getSelectedNodes();
        if (nodes.length < 1) {
            return "";
        }
        return nodes[0].id;
    },
    eventParseObject: function (event) {//获取点击事件的源对象
        event = event ? event : window.event;
        var obj = event.srcElement ? event.srcElement : event.target;
        return $(obj);
    },
    sessionTimeoutRegistry: function () {
        $.ajaxSetup({
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            complete: function (XMLHttpRequest, textStatus) {
                //通过XMLHttpRequest取得响应头，sessionstatus，
                var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
                if (sessionstatus == "timeout") {
                    //如果超时就处理 ，指定要跳转的页面
                    window.location = Feng.ctxPath + "/global/sessionError";
                }
            }
        });
    },
    initValidator: function (formId, fields) {
        $('#' + formId).bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: fields,
            live: 'enabled',
            message: '该字段不能为空'
        });
    },
    underLineToCamel: function (str) {
        var strArr = str.split('_');
        for (var i = 1; i < strArr.length; i++) {
            strArr[i] = strArr[i].charAt(0).toUpperCase() + strArr[i].substring(1);
        }
        var result = strArr.join('');
        return result.charAt(0).toUpperCase() + result.substring(1);
    },
    initChosen: function () {
        $('.search-chosen').chosen({search_contains: true, no_results_text: "暂无结果"});

    },
    initStartEndDate: function () {
        var currDate = new Date();
        var startDate = laydate.render({
            elem: '#startDate',
            type: 'datetime',
            done: function (value, date) {
                if (value !== '') {
                    endDate.config.min.year = date.year;
                    endDate.config.min.month = date.month - 1;
                    endDate.config.min.date = date.date;
                } else {
                    endDate.config.min.year = '';
                    endDate.config.min.month = '';
                    endDate.config.min.date = '';
                }
            }
        });
        //设置结束时间
        var endDate = laydate.render({
            elem: '#endDate',
            type: 'datetime',
            done: function (value, date) {
                if (value !== '') {
                    startDate.config.max.year = date.year;
                    startDate.config.max.month = date.month - 1;
                    startDate.config.max.date = date.date;
                } else {
                    startDate.config.max.year = currDate.getFullYear();
                    startDate.config.max.month = currDate.getMonth() + 1;
                    startDate.config.max.date = currDate.getDate();
                }
            }
        });
        return [startDate,endDate];
    },
    initStartEndDateTime: function (format) {
        var currDate = new Date();
        var startDate = laydate.render({
            elem: '#startDate',
            type: 'date',
            done: function (value, date) {
                if (value !== '') {
                    endDate.config.min.year = date.year;
                    endDate.config.min.month = date.month - 1;
                    endDate.config.min.date = date.date;
                } else {
                    endDate.config.min.year = '';
                    endDate.config.min.month = '';
                    endDate.config.min.date = '';
                }
            }
        });
        //设置结束时间
        var endDate = laydate.render({
            elem: '#endDate',
            type: 'date',
            done: function (value, date) {
                if (value !== '') {
                    startDate.config.max.year = date.year;
                    startDate.config.max.month = date.month - 1;
                    startDate.config.max.date = date.date;
                } else {
                    startDate.config.max.year = currDate.getFullYear();
                    startDate.config.max.month = currDate.getMonth() + 1;
                    startDate.config.max.date = currDate.getDate();
                }
            }
        });
        return [startDate,endDate];
    },
    initBeginEndDate: function () {
        var currDate = new Date();
        var startDate = laydate.render({
            elem: '#beginTime',
            type: 'datetime',
            done: function (value, date) {
                if (value !== '') {
                    endDate.config.min.year = date.year;
                    endDate.config.min.month = date.month - 1;
                    endDate.config.min.date = date.date;
                } else {
                    endDate.config.min.year = '';
                    endDate.config.min.month = '';
                    endDate.config.min.date = '';
                }
            }
        });
        //设置结束时间
        var endDate = laydate.render({
            elem: '#endTime',
            type: 'datetime',
            done: function (value, date) {
                if (value !== '') {
                    startDate.config.max.year = date.year;
                    startDate.config.max.month = date.month - 1;
                    startDate.config.max.date = date.date;
                } else {
                    startDate.config.max.year = currDate.getFullYear();
                    startDate.config.max.month = currDate.getMonth() + 1;
                    startDate.config.max.date = currDate.getDate();
                }
            }
        });
    },
    resetForm: function (formId) {
        document.getElementById(formId).reset();
        if ($('#' + formId).find('.search-chosen')) {
            $('#' + formId).find('.search-chosen').each(function () {
                $(this).trigger("chosen:updated");
            });
        }

        if(typeof (resetDate) === "function"){
            resetDate();
        }
    },
    hiddenIdCard: function (idCard) {
        if (idCard && idCard.length > 3) {
            var frontLen = idCard.length / 3;
            var endLen = idCard.length / 3;
            var len = idCard.length - frontLen - endLen;
            var xing = '';
            for (var i = 0; i < len; i++) {
                xing += '*';
            }
            return idCard.substring(0, frontLen) + xing + idCard.substring(idCard.length - endLen);
        }
        return idCard;
    }
};
