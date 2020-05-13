/**
 * 结算单工资列表
 */
var settlementWorkerList = {
    id: "settlementWorkerTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    projectCodeValue:null,
    callBackData:{},
    checkedArray: [],   //选中的数据
    detailList: null   //表格加载成功的数据
};

/**
 * 初始化表格的列
 */
settlementWorkerList.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '性别', field: 'gender', visible: true, align: 'center', valign: 'middle'},
        {title: '班组', field: 'teamName', visible: true, align: 'center', valign: 'middle'},
        {title: '工种', field: 'workTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle'}
    ];
};


/**
 * 查询结算单列表
 */
settlementWorkerList.search = function () {
    settlementWorkerList.projectCodeValue=$("#projectCode").val();
    var queryData = {};
    queryData['projectCode'] = $("#projectCode").val();
    queryData['workerName'] = $("#workerName").val();
    queryData['idCardNumber'] = $("#idCardNumber").val();
    settlementWorkerList.table.refresh({query: queryData});
};

/**
 * 确定
 * @returns {boolean}
 */
settlementWorkerList.addSettlementDetail =function(){
    var index = parent.layer.getFrameIndex(window.name);
    if (settlementWorkerList.checkedArray.length === 0) {
        Feng.info("请先选中表格中的记录！");
        return false;
    }
    settlementWorkerList.callBackData = {
        projectCode : $("#projectCode").val(),
        detailList: settlementWorkerList.checkedArray,
        workerNames:$("#workerNames").html()
    };
    //console.info("workerIdList:"+workerIdList);
    parent.setRel(settlementWorkerList.callBackData);

    parent.layer.close(index);

};

$(function () {
    //同一班组下 查询之前选中的数据
    var data = parent.getCheckedArray();
    if(data.detailList !== null){
        settlementWorkerList.checkedArray = data.detailList;
    }
    if(data.workerNames !== ''){
        $("#workerNames").html(data.workerNames);
    }

    var defaultColunms = settlementWorkerList.initColumn();
    var table = new BSTable(settlementWorkerList.id, "/settlement/getWorkerMasterByProjectCode", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams({"projectCode": $("#projectCode").val()});
    table.onLoadSuccess = settlementWorkerList.onLoadSuccess;
    settlementWorkerList.table = table.init();
});

//表格加载成功事件
settlementWorkerList.onLoadSuccess = function (data) {
    settlementWorkerList.detailList = data.rows;

    //已选中的加载选中样式
    for(var i=0;i<settlementWorkerList.detailList.length;i++){
        var object = settlementWorkerList.detailList[i];
        //判断其是否存在选中的checkedList中 如果存在 选中
        for (var j = 0; j < settlementWorkerList.checkedArray.length; j++) {
            var idCardType = settlementWorkerList.checkedArray[j].idCardType;
            var idCardNumber = settlementWorkerList.checkedArray[j].idCardNumber;
            if (idCardType === object.idCardType && idCardNumber === object.idCardNumber) {
                //已存在 让其选中
                var checkbox = $("#" + settlementWorkerList.id).find("tbody input[type='checkbox']").eq(i);
                checkbox[0].checked = true;
                break;
            }
        }
    }

    //全选
    $("#" + settlementWorkerList.id).find("thead input[type='checkbox']").on("change", function () {
        if ($(this)[0].checked === true) {
            //全选
            var selecteds = $('#' + settlementWorkerList.id).bootstrapTable('getSelections');
            for (var i = 0; i < selecteds.length; i++) {
                var object = selecteds[i];
                //判断此人书否存在于array并添加或删除
                checkedOperation(object, this);
            }
            //console.log(settlementWorkerList.checkedArray)
        } else {
            //移除全选
            for (var i = 0; i < settlementWorkerList.detailList.length; i++) {
                var object = settlementWorkerList.detailList[i];
                //判断在checedArray中是否存在
                for (var j = 0; j < settlementWorkerList.checkedArray.length; j++) {
                    if (object.idCardType === settlementWorkerList.checkedArray[j].idCardType && object.idCardNumber === settlementWorkerList.checkedArray[j].idCardNumber) {
                        settlementWorkerList.checkedArray.splice(j, 1);
                        break;
                    }
                }
            }
            //console.log(settlementWorkerList.checkedArray)

        }
        loadCheckedInput();
    })


    //单独选择
    $("#" + settlementWorkerList.id).find("tbody input[type='checkbox']").each(function (index, checkbox) {
        $(checkbox).on("change", function () {
            //此时选中的索引
            var index = $(this).data(index).index;
            //索引对应的数据
            var object = settlementWorkerList.detailList[index];
            //判断此人书否存在于array并添加或删除
            checkedOperation(object, this);
            loadCheckedInput();
            //console.log(settlementWorkerList.checkedArray)
        })
    })
}

//判断checedArray中是否存在数据 没有添加 有移除
function checkedOperation(object, context) {
//判断此人书否存在于array
    var flag = false;
    //存在于选中checkedArray的索引
    var checkedIndex = 0;
    for (var i = 0; i < settlementWorkerList.checkedArray.length; i++) {
        var idCardType = settlementWorkerList.checkedArray[i].idCardType;
        var idCardNumber = settlementWorkerList.checkedArray[i].idCardNumber;
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
            settlementWorkerList.checkedArray.push(object);
        }
    } else {
        //未选中
        if (flag) {
            //未选中移除数据
            settlementWorkerList.checkedArray.splice(checkedIndex, 1);
        }
    }
}

function loadCheckedInput() {
    var workerNames = "";
    for (var i = 0; i < settlementWorkerList.checkedArray.length; i++) {
        workerNames += settlementWorkerList.checkedArray[i].workerName+";";
    }
    $("#workerNames").html(workerNames);
}
