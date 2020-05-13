/**
 * 结算单工资列表
 */
var PayRollWorkerList = {
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
PayRollWorkerList.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '姓名', field: 'workerName', visible: true, align: 'center', valign: 'middle'},
        {title: '性别', field: 'gender', visible: true, align: 'center', valign: 'middle',
            formatter:function (val, row, index){
                if(val=== 0){
                    return "男";
                }else{
                    return "女";
                }
            }
        },
        {title: '班组', field: 'teamName', visible: true, align: 'center', valign: 'middle'},
        {title: '工种', field: 'workTypeName', visible: true, align: 'center', valign: 'middle'},
        {title: '证件号', field: 'idCardNumber', visible: true, align: 'center', valign: 'middle'}
    ];
};


/**
 * 查询结算单列表
 */
PayRollWorkerList.search = function () {
    PayRollWorkerList.projectCodeValue=$("#projectCode").val();
    var queryData = {};
    queryData['workerName'] = $("#workerName").val();
    queryData['idCardNumber'] = $("#idCardNumber").val();
    PayRollWorkerList.table.refresh({query: queryData});
};

/**
 * 确定
 * @returns {boolean}
 */
PayRollWorkerList.confirmPersons =function(){
    var index = parent.layer.getFrameIndex(window.name);
    if (PayRollWorkerList.checkedArray.length === 0) {
        Feng.info("请先选中表格中的记录！");
        return false;
    }
    for(var i=0;i<PayRollWorkerList.checkedArray.length;i++){
        //判断父页面是否存在
        var flag = true;
        for(var j=0 ; j<parent.DetailByWorkerType.teamWorkers.length;j++){
            if(PayRollWorkerList.checkedArray[i].idCardType === parent.DetailByWorkerType.teamWorkers[j].idCardType && PayRollWorkerList.checkedArray[i].idCardNumber === parent.DetailByWorkerType.teamWorkers[j].idCardNumber ){
                flag = false;
                break;
            }
        }
        if(flag === true){
            //用户判断不存在 存入父集合
            var personObject = {};
            personObject.idCardType = PayRollWorkerList.checkedArray[i].idCardType;
            personObject.idCardNumber = PayRollWorkerList.checkedArray[i].idCardNumber;
            personObject.workerName = PayRollWorkerList.checkedArray[i].workerName
            parent.DetailByWorkerType.teamWorkers.push(personObject);
        }
    }
    parent.layer.close(index);

};

$(function () {
    //同一班组下 查询之前选中的数据
    var data = parent.getCheckedArray();
    if(data.detailList.length >0){
        PayRollWorkerList.checkedArray = data.detailList;
    }
    if(data.workerNames !== ''){
        $("#workerNames").html(data.workerNames);
    }


    var defaultColunms = PayRollWorkerList.initColumn();
    var table = new BSTable(PayRollWorkerList.id, "/teamMaster/getTeamMemberByProjectCodeAndTeamCodePages?projectCode="+$("#projectCode").val() +"&teamSysNo=" + $("#teamSysNo").val(), defaultColunms);
    table.setPaginationType("server");
    table.onLoadSuccess = PayRollWorkerList.onLoadSuccess;
    PayRollWorkerList.table = table.init();
});


//表格加载成功事件
PayRollWorkerList.onLoadSuccess = function (data) {
    PayRollWorkerList.detailList = data.rows;

    //已选中的加载选中样式
    for(var i=0;i<PayRollWorkerList.detailList.length;i++){
        var object = PayRollWorkerList.detailList[i];
        //判断其是否存在选中的checkedList中 如果存在 选中
        for (var j = 0; j < PayRollWorkerList.checkedArray.length; j++) {
            var idCardType = PayRollWorkerList.checkedArray[j].idCardType;
            var idCardNumber = PayRollWorkerList.checkedArray[j].idCardNumber;
            if (idCardType === object.idCardType && idCardNumber === object.idCardNumber) {
                //已存在 让其选中
                var checkbox = $("#" + PayRollWorkerList.id).find("tbody input[type='checkbox']").eq(i);
                checkbox[0].checked = true;
                break;
            }
        }
    }

    //全选
    $("#" + PayRollWorkerList.id).find("thead input[type='checkbox']").on("change", function () {
        if ($(this)[0].checked === true) {
            //全选
//        	debugger;
            var selecteds = $('#' + PayRollWorkerList.id).bootstrapTable('getSelections');
            for (var i = 0; i < selecteds.length; i++) {
                var object = selecteds[i];
                //判断此人书否存在于array并添加或删除
//                console.log(object, this)
                checkedOperation(object, this);
            }
//            console.log(selecteds, 'selecteds')
//            console.log(PayRollWorkerList.checkedArray)
        } else {
            //移除全选
            for (var i = 0; i < PayRollWorkerList.detailList.length; i++) {
                var object = PayRollWorkerList.detailList[i];
                //判断在checedArray中是否存在
                for (var j = 0; j < PayRollWorkerList.checkedArray.length; j++) {
                    if (object.idCardType === PayRollWorkerList.checkedArray[j].idCardType && object.idCardNumber === PayRollWorkerList.checkedArray[j].idCardNumber) {
                        PayRollWorkerList.checkedArray.splice(j, 1);
                        break;
                    }
                }
            }
            console.log(PayRollWorkerList.checkedArray)

        }
        loadCheckedInput();
    })


    //单独选择
    $("#" + PayRollWorkerList.id).find("tbody input[type='checkbox']").each(function (index, checkbox) {
        $(checkbox).on("change", function () {
            //此时选中的索引
            var index = $(this).data(index).index;
            //索引对应的数据
            var object = PayRollWorkerList.detailList[index];
            //判断此人书否存在于array并添加或删除
            checkedOperation(object, this);
            loadCheckedInput();
            console.log(PayRollWorkerList.checkedArray)
        })
    })
}

//判断checedArray中是否存在数据 没有添加 有移除
function checkedOperation(object, context) {
//判断此人书否存在于array
    var flag = false;
    //存在于选中checkedArray的索引
    var checkedIndex = 0;
    for (var i = 0; i < PayRollWorkerList.checkedArray.length; i++) {
        var idCardType = PayRollWorkerList.checkedArray[i].idCardType;
        var idCardNumber = PayRollWorkerList.checkedArray[i].idCardNumber;
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
            PayRollWorkerList.checkedArray.push(object);
        }
    } else {
        //未选中
        if (flag) {
            //未选中移除数据
            PayRollWorkerList.checkedArray.splice(checkedIndex, 1);
        }
    }
}

function loadCheckedInput() {
    var workerNames = "";
    for (var i = 0; i < PayRollWorkerList.checkedArray.length; i++) {
        workerNames += PayRollWorkerList.checkedArray[i].workerName+";";
    }
    $("#workerNames").html(workerNames);
}