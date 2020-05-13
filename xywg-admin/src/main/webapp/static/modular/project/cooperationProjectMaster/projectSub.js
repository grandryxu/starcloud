/**
 * 劳务分包管理初始化
 */
var ProjectSub = {
    id: "projectSubTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};


/**
 * 初始化表格的列
 */
ProjectSub.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true, visible: false},
        {
            title: '分部工程', field: 'subId', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return "<select  class='parentSelect form-control form-control-sss'  id='"+ row.id +"' index='" + index + "'  value = '" + value + "' onchange='ProjectSub.onChange(this)'/>";
            }
        },
        {
            title: '子分部工程', field: 'childNumId', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return "<select  class='childSelect form-control form-control-sss' index='" + index + "' value = '" + value + "'>" +
                    "<option value=''>请选择分布工程</option></select>";
            }
        },
        {
            title: '操作', visible: true, align: 'center', valign: 'middle', formatter: function (val, row, index) {
            return "<a href='javascript:void(0)' datas='" + row.id + "'  data-toggle='tooltip' title='删除'  onclick='ProjectSub.deleteRow(this)'><i class='fa fa-remove text-blue'></i></a>";
        }
        }

    ];
};

//增加行
ProjectSub.addRow = function(){
    var ajax = new $ax(Feng.ctxPath + "/projectSub/addDefault", function (data) {
        ProjectSub.table.refresh();
    }, function (data) {
        Feng.error("新增失败!" + data.responseJSON.message + "!");
    });
    ajax.set({projectCode:$("#projectCode").val()});
    ajax.start();
}

//父下拉框值修改事件
ProjectSub.onChange = function (select) {
    var ajax = new $ax(Feng.ctxPath + "/partitionedProject/getChildrenByNum", function (data) {
        //加载子分布工程list
        var options = "<option value=''>请选择子分部工程</option>";
        for (var i = 0; i < data.length; i++) {
            options += "<option value='" + data[i].num + "'>" + data[i].name + "</option>";
        }
        var childSelect = $($(select).parent().next().children("select"));
        childSelect.html(options);
        childSelect.on("change", function () {
            if($(childSelect).val()===''){
                Feng.error("请选择子分部工程!");
                return;
            }
            var projectSub = {};
            projectSub.id = $(select).attr("id");
            projectSub.subId = $(select).val()
            projectSub.childNumId = $(childSelect).val();
            projectSub.projectCode = $("#projectCode").val();
            var ajax = new $ax(Feng.ctxPath + "/projectSub/update", function (data) {
                Feng.success("修改成功");
                ProjectSub.table.refresh();
            }, function (data) {
                Feng.error("修改失败!" + data.responseJSON.message + "!");
            });
            ajax.set(projectSub);
            ajax.start();
        })
    }, function (data) {
        Feng.error("查询子分部工程失败!" + data.responseJSON.message + "!");
    });
    ajax.set({num: $(select).val()});
    ajax.start();
};

//删除行
ProjectSub.deleteRow = function (deletedRow) {
    Feng.confirm("是否删除该条记录？", function () {
        $(deletedRow).parent().parent().remove();
        var ajax = new $ax(Feng.ctxPath + "/projectSub/delete", function (data) {
            Feng.success("删除成功!");
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set({projectSubId: $(deletedRow).attr("datas")});
        ajax.start();
    });
}

//表格加载成功事件
ProjectSub.onLoadSuccess = function (list) {
    ProjectSub.dataList = list.rows;
    var selects = $("#" + ProjectSub.id + " .parentSelect");
    selects.html($("#projectSubOption").html());
    selects.each(function (index, data) {
        var options = $(data).children("option");
        for (var i = 0; i < options.length; i++) {
            if (options[i].value === $(data).attr("value")) {
                options[i].selected = "selected";
            }
        }
        //加载子分布工程
        ProjectSub.onChange($(data));
        //让子默认选中
        var childSelects = $("#" + ProjectSub.id + " .childSelect");
        childSelects.each(function (index,data) {
            var options = $(data).children("option");
            for(var i=0 ; i<options.length; i++){
                if(options[i].value === $(data).attr("value")){
                    options[i].selected = "selected";
                }
            }
        })
    })
}

$(function () {
    var defaultColunms = ProjectSub.initColumn();
    var table = new BSTable(ProjectSub.id, "/projectSub/list?projectCode=" + $("#projectCode").val(), defaultColunms);
    table.setPaginationType("server");
    table.onLoadSuccess = ProjectSub.onLoadSuccess;
    ProjectSub.table = table.init();
});
