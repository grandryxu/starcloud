/**
 * 结算单工资列表
 */
var accountWorkerList = {
    id: "accountWorkerTable",	//表格id
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
accountWorkerList.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '班组', field: 'teamName', visible: true, align: 'center', valign: 'middle'},
        {title: '性别', field: 'genderName', visible: true, align: 'center', valign: 'middle'},
        {title: '工种', field: 'workTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件类型', field: 'idCardTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件号码', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle',
        formatter:function (data) {
        return Feng.hiddenIdCard(data);
    }
        }
    ];
};
/**
 * 查询结算单列表
 */
accountWorkerList.search = function () {
    var queryData = {};
    queryData['projectCode'] = $("#projectCode").val();
    queryData['team'] = $("#team").val();
    queryData['workerName'] = $("#workerName").val();
    queryData['idCardNumber'] = $("#idCardNumber").val();
    accountWorkerList.table.refresh({query: queryData});
};

/**
 * 确定
 * @returns {boolean}
 */
accountWorkerList.add =function(){
    var index = parent.layer.getFrameIndex(window.name);
    if (accountWorkerList.checkedArray.length === 0) {
        Feng.info("请先选中表格中的记录！");
        return false;
    }
    // var workerIdList=[];
    // for(var i=0;i<accountWorkerList.checkedArray.length;i++){
    //     workerIdList.push(accountWorkerList.checkedArray[i].id);
    //     //workerIdList+= selected[i].id +",";
    // }
    accountWorkerList.callBackData = {
        projectCode : $("#projectCode").val(),
        // workerIdList : workerIdList ,
        detailList: accountWorkerList.checkedArray ,
        workerNames: $("#workerNames").html()
    };
    // console.info("workerIdList:"+workerIdList);
    parent.setRel(accountWorkerList.callBackData);
    parent.layer.close(index);
};

$(function () {
    //同一班组下 查询之前选中的数据
    var data = parent.getCheckedArray();
    if(data.detailList !== null){
        accountWorkerList.checkedArray = data.detailList;
    }
    if(data.workerNames !== ''){
        $("#workerNames").html(data.workerNames);
    }

    var param={"team": $("#team").val(),"projectCode":$("#projectCode").val()}
    var defaultColunms = accountWorkerList.initColumn();
    var table = new BSTable(accountWorkerList.id, "/teamMaster/getWorkerListByTeam", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(param);
    table.onLoadSuccess = accountWorkerList.onLoadSuccess;
    accountWorkerList.table = table.init();
});


//表格加载成功事件
accountWorkerList.onLoadSuccess = function (data) {
    accountWorkerList.detailList = data.rows;

    //已选中的加载选中样式
    for(var i=0;i<accountWorkerList.detailList.length;i++){
        var object = accountWorkerList.detailList[i];
        //判断其是否存在选中的checkedList中 如果存在 选中
        for (var j = 0; j < accountWorkerList.checkedArray.length; j++) {
            var idCardType = accountWorkerList.checkedArray[j].idCardType;
            var idCardNumber = accountWorkerList.checkedArray[j].idCardNumber;
            if (idCardType === object.idCardType && idCardNumber === object.idCardNumber) {
                //已存在 让其选中
                var checkbox = $("#" + accountWorkerList.id).find("tbody input[type='checkbox']").eq(i);
                checkbox[0].checked = true;
                break;
            }
        }
    }

    //全选
    $("#" + accountWorkerList.id).find("thead input[type='checkbox']").on("change", function () {
        if ($(this)[0].checked === true) {
            //全选
            var selecteds = $('#' + accountWorkerList.id).bootstrapTable('getSelections');
            for (var i = 0; i < selecteds.length; i++) {
                var object = selecteds[i];
                //判断此人书否存在于array并添加或删除
                checkedOperation(object, this);
            }
            //console.log(accountWorkerList.checkedArray)
        } else {
            //移除全选
            for (var i = 0; i < accountWorkerList.detailList.length; i++) {
                var object = accountWorkerList.detailList[i];
                //判断在checedArray中是否存在
                for (var j = 0; j < accountWorkerList.checkedArray.length; j++) {
                    if (object.idCardType === accountWorkerList.checkedArray[j].idCardType && object.idCardNumber === accountWorkerList.checkedArray[j].idCardNumber) {
                        accountWorkerList.checkedArray.splice(j, 1);
                        break;
                    }
                }
            }
            //console.log(accountWorkerList.checkedArray)

        }
        loadCheckedInput();
    })


    //单独选择
    $("#" + accountWorkerList.id).find("tbody input[type='checkbox']").each(function (index, checkbox) {
        $(checkbox).on("change", function () {
            //此时选中的索引
            var index = $(this).data(index).index;
            //索引对应的数据
            var object = accountWorkerList.detailList[index];
            //判断此人书否存在于array并添加或删除
            checkedOperation(object, this);
            loadCheckedInput();
            //console.log(accountWorkerList.checkedArray)
        })
    })
}

//判断checedArray中是否存在数据 没有添加 有移除
function checkedOperation(object, context) {
//判断此人书否存在于array
    var flag = false;
    //存在于选中checkedArray的索引
    var checkedIndex = 0;
    for (var i = 0; i < accountWorkerList.checkedArray.length; i++) {
        var idCardType = accountWorkerList.checkedArray[i].idCardType;
        var idCardNumber = accountWorkerList.checkedArray[i].idCardNumber;
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
            accountWorkerList.checkedArray.push(object);
        }
    } else {
        //未选中
        if (flag) {
            //未选中移除数据
            accountWorkerList.checkedArray.splice(checkedIndex, 1);
        }
    }
}

function loadCheckedInput() {
    var workerNames = "";
    for (var i = 0; i < accountWorkerList.checkedArray.length; i++) {
        workerNames += accountWorkerList.checkedArray[i].workerName+";";
    }
    $("#workerNames").html(workerNames);
}
