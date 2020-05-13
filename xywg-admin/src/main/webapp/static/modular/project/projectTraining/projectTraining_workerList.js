/**
 * 结算单工资列表
 */
var projectTrainingWorker = {
    id: "projectTrainingWorkerTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    projectCodeValue: null,
    callBackData: {},
    checkedArray: [],   //选中的数据
    detailList: null   //表格加载成功的数据
};

/**
 * 初始化表格的列
 */
projectTrainingWorker.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型', field: 'idCardTypeName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '证件号码', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',
            formatter: function (data) {
                return Feng.hiddenIdCard(data);
            }
        },
        {title: '工种', field: 'workKingName', visible: true, align: 'center', valign: 'middle'}
    ];
};


/**
 * 查询结算单列表
 */
projectTrainingWorker.search = function () {
    var queryData = {};
    queryData['workerName'] = $("#workerName").val();
    queryData['idCardNumber'] = $("#idCardNumber").val();
    projectTrainingWorker.table.refresh({query: queryData});
};

/**
 * 确定
 * @returns {boolean}
 */
projectTrainingWorker.addSettlementDetail = function () {
    var index = parent.layer.getFrameIndex(window.name);
    if(projectTrainingWorker.checkedArray.length === 0){
        Feng.info("请选择工人");
        return;
    }
    //console.info("workerIdList:"+workerIdList);
    parent.setCallBack({
        detailList:projectTrainingWorker.checkedArray,
        workerNames: $("#workerNames").html()
    });
    parent.layer.close(index);

};

$(function () {
    //同一项目下 查询之前选中的数据
    var data = parent.getCheckedArray();
    if(data.detailList !== null){
        projectTrainingWorker.checkedArray = data.detailList;
    }
    if(data.workerNames !== ''){
        $("#workerNames").html(data.workerNames);
    }

    var defaultColunms = projectTrainingWorker.initColumn();
    var table = new BSTable(projectTrainingWorker.id, "/projectWorker/getWorkerListByProject?projectCode=" + $("#projectCode").val(), defaultColunms);
    table.setPaginationType("server");
    table.onLoadSuccess = projectTrainingWorker.onLoadSuccess;
    projectTrainingWorker.table = table.init();
});

//表格加载成功事件
projectTrainingWorker.onLoadSuccess = function (data) {
    projectTrainingWorker.detailList = data.rows;

    //已选中的加载选中样式
    for(var i=0;i<projectTrainingWorker.detailList.length;i++){
        var object = projectTrainingWorker.detailList[i];
        //判断其是否存在选中的checkedList中 如果存在 选中
        for (var j = 0; j < projectTrainingWorker.checkedArray.length; j++) {
            var idCardType = projectTrainingWorker.checkedArray[j].idCardType;
            var idCardNumber = projectTrainingWorker.checkedArray[j].idCardNumber;
            if (idCardType === object.idCardType && idCardNumber === object.idCardNumber) {
                //已存在 让其选中
                var checkbox = $("#" + projectTrainingWorker.id).find("tbody input[type='checkbox']").eq(i);
                checkbox[0].checked = true;
                break;
            }
        }
    }

    //全选
    $("#" + projectTrainingWorker.id).find("thead input[type='checkbox']").on("change", function () {
        if ($(this)[0].checked === true) {
            //全选
            var selecteds = $('#' + projectTrainingWorker.id).bootstrapTable('getSelections');
            for (var i = 0; i < selecteds.length; i++) {
                var object = selecteds[i];
                //判断此人书否存在于array并添加或删除
                checkedOperation(object, this);
            }
            //console.log(projectTrainingWorker.checkedArray)
        } else {
            //移除全选
            for (var i = 0; i < projectTrainingWorker.detailList.length; i++) {
                var object = projectTrainingWorker.detailList[i];
                //判断在checedArray中是否存在
                for (var j = 0; j < projectTrainingWorker.checkedArray.length; j++) {
                    if (object.idCardType === projectTrainingWorker.checkedArray[j].idCardType && object.idCardNumber === projectTrainingWorker.checkedArray[j].idCardNumber) {
                        projectTrainingWorker.checkedArray.splice(j, 1);
                        break;
                    }
                }
            }
            //console.log(projectTrainingWorker.checkedArray)

        }
        loadCheckedInput();
    })


    //单独选择
    $("#" + projectTrainingWorker.id).find("tbody input[type='checkbox']").each(function (index, checkbox) {
        $(checkbox).on("change", function () {
            //此时选中的索引
            var index = $(this).data(index).index;
            //索引对应的数据
            var object = projectTrainingWorker.detailList[index];
            //判断此人书否存在于array并添加或删除
            checkedOperation(object, this);
            loadCheckedInput();
            //console.log(projectTrainingWorker.checkedArray)
        })
    })
}

//判断checedArray中是否存在数据 没有添加 有移除
function checkedOperation(object, context) {
//判断此人书否存在于array
    var flag = false;
    //存在于选中checkedArray的索引
    var checkedIndex = 0;
    for (var i = 0; i < projectTrainingWorker.checkedArray.length; i++) {
        var idCardType = projectTrainingWorker.checkedArray[i].idCardType;
        var idCardNumber = projectTrainingWorker.checkedArray[i].idCardNumber;
        if (idCardType === object.idCardType && idCardNumber === object.idCardNumber) {
            flag = true;
            checkedIndex = i;
            break;
        }
    }
    if ($(context)[0].checked === true) {
        //选中
        if (!flag) {
            //选中添加数据
            projectTrainingWorker.checkedArray.push(object);
        }
    } else {
        //未选中
        if (flag) {
            //未选中移除数据
            projectTrainingWorker.checkedArray.splice(checkedIndex, 1);
        }
    }
}

function loadCheckedInput() {
    var workerNames = "";
    for (var i = 0; i < projectTrainingWorker.checkedArray.length; i++) {
        workerNames += projectTrainingWorker.checkedArray[i].workerName+";";
    }
    $("#workerNames").html(workerNames);
}
