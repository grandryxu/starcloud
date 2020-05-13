/**
 * 初始化企业评价详情对话框
 */

/*初始化表格*/
var evaluateMaster = {
    id: "evaluateTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
};

var score=null;
/**
 * 初始化表格的列
 */
evaluateMaster.initColumn = function () {

    return [
        {field: 'selectItem',visible: false, checkbox: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '', field: 'items', visible: true, align: 'center', valign: 'middle'},

        {title: '', field: 'max', visible: true, align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                score=value;
                return value+"分";
            }
        },
        {title: '', field: 'type', visible: true, align: 'center', valign: 'middle',

            formatter: function (value, row, index) {
                if (value == '单选') {

                    return"<select id='selectOne' name='selectOneAll'> <option value='请选择'>请选择  </option> <option value="+score+" >是  </option> <option value='0'>否  </option>   </select>";
                } else if (value=='范围'){
                    var option="";
                    for (var i = 0; i <= score; i++) {
                        option +=  "<option value="+i+">"+i+"</option>";
                    }
                    return"<select id='selectOne1'  name='selectOneAll'> "+"<option value='请选择'>请选择  </option>"+option+"</select>";
                }
            }
        }


        /*        {title: '招标信息', field: 'status', visible: true, align: 'center', valign: 'middle',
                    formatter: function (value, row, index) {
                        return"<a size='2' color='blue' onclick='SubContractor.openAddSubContractor(\""+ row.id +"\")'>查看</a>";

                    }
                }*/

    ];
};





var ConstructionEvaluateInfoDlg = {
    constructionEvaluateInfoData : {},
    validateFields: {
        massReview: {
            validators: {
                notEmpty: {
                    message: '质量评价不能为空'
                }
            }
        },
        paceReview: {
            validators: {
                 notEmpty: {
                    message: '进度评价不能为空'
                }
            }
        },
        safeReview: {
            validators: {
                 notEmpty: {
                    message: '安全评价不能为空'
                }
            }
        },
        note: {
            validators: {
                stringLength: {
                    max:500,
                    message: '最大长度为500'
                }
            }
        }}
};





/**
 * 清除数据
 */
ConstructionEvaluateInfoDlg.clearData = function() {
    this.constructionEvaluateInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ConstructionEvaluateInfoDlg.set = function(key, val) {
    this.constructionEvaluateInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ConstructionEvaluateInfoDlg.get = function(key) {
    return $("#" + key).val();
}

ConstructionEvaluateInfoDlg .validate = function () {
    $('#evaluationForm').data("bootstrapValidator").resetForm();
    $('#evaluationForm').bootstrapValidator('validate');
    return $("#evaluationForm").data('bootstrapValidator').isValid();
};

/**
 * 关闭此对话框
 */
ConstructionEvaluateInfoDlg.close = function() {
    /*parent.layer.close(window.parent.ConstructionEvaluate.layerIndex);*/
    parent.layer.closeAll();
}

/**
 * 收集数据
 */
ConstructionEvaluateInfoDlg.collectData = function() {
    this
/*    .set('id')
    .set('organizationCode')
    .set('idCardType')
    .set('idCardNumber')
    .set('massReview')
    .set('paceReview')
    .set('safeReview')
    .set('note')
    .set('createDate')
    .set('updateUser')
    .set('createUser')
    .set('updateDate')
    .set('score')
    .set('grade');*/
}




/*评价总分*/
ConstructionEvaluateInfoDlg.addSum = function() {
    //评价分数求和判断是否有未评价项
    var arr =[];
    $("*[name='selectOneAll']").each(function(){
        arr.push($(this).val());
    });
    var sum = 0;
    for(var i=0;i<arr.length;i++){
        if (arr[i]=='请选择'){
            Feng.error("有未评价项！请检查...");
            return false;
        } else {
            sum += parseInt(arr[i]);
        }
    }
    //对该企业进行评价,分数入库
    var ajax = new $ax(Feng.ctxPath + "/lxEvaluate/updateScore", function(data){
        ConstructionEvaluateInfoDlg.close();
        Feng.success("评价成功!");
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set("projectCode",$("#projectCode").val());
    ajax.set("organizationCode",$("#organizationCode").val());
    ajax.set("score",sum);
    ajax.start();

}


/**
 * 提交添加
 */
ConstructionEvaluateInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

/*    var itemText = $("#selectOne option:selected").text();
    console.log('text'+itemText)

    var itemValue1 = $("#selectOne").val();
    console.log('Value:' + itemValue1);*/



   /* console.log( $("*[name='selectOneAll']").val())*/




/*    var grade = $("#grade").val();
    if (grade === "") {
        Feng.error("请获取等级");
        return false;
    }

    var score = $("#score").val();
    if (score === "") {
        Feng.error("分数不能为空");
        return false;
    }
    if (score % 10 != 0) {
        Feng.error("请填写10的倍数");
        return false;
    }
    if (score < 0 || score > 100) {
        Feng.error("范围10-100");
        return false;
    }

    if (!(score % 1 == 0)) {
        Feng.error("请填写整数");
        return false;
    }
    if($("#massReview").val()==0){
        Feng.info("请对质量进行评价！")
        return;
    }
    if($("#paceReview").val()==0){
        Feng.info("请对进度进行评价！")
        return;
    }
    if($("#safeReview").val()==0){
        Feng.info("请对安全进行评价！")
        return;
    }*/
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/constructionEvaluate/add", function(data){

        ConstructionEvaluateInfoDlg.close();
        Feng.success("评价成功!");
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.constructionEvaluateInfoData);
    ajax.start();
}

/*根据分数查看等级*/
ConstructionEvaluateInfoDlg.selectStart = function () {



    var score = $("#score").val();

    if (score === "") {
        Feng.error("分数不能为空");
        return false;
    }
    if (score % 10 != 0) {
        Feng.error("请填写10的倍数");
        return false;
    }
    if (score < 0 || score > 100) {
        Feng.error("范围10-100");
        return false;
    }

    if (!(score % 1 == 0)) {
        Feng.error("请填写整数");
        return false;
    }
    $("#score").attr("disabled", true);


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/lxEvaluate/selectStar", function (data) {
        $("#grade").val(data);

        Feng.success("获取等级成功!");
    }, function (data) {
        Feng.error("失败!" + data.responseJSON.message + "!");
    });
    ajax.set("score", score);
    ajax.start();
}

/*清空等级*/
ConstructionEvaluateInfoDlg.cleanGrade = function () {
    $("#grade").val("");
};
/**
 * 提交修改
 */
ConstructionEvaluateInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/constructionEvaluate/update", function(data){
        Feng.success("修改成功!");
        window.parent.ConstructionEvaluate.table.refresh();
        ConstructionEvaluateInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.constructionEvaluateInfoData);
    ajax.start();
}

$(function() {

    var defaultColunms = evaluateMaster.initColumn();
    var table = new BSTable(evaluateMaster.id, "/lxEvaluate/list", defaultColunms);
    table.setPaginationType("server");
    table.onDblClickRow = evaluateMaster.searchInfo;
    table.onLoadSuccess = evaluateMaster.onLoadSuccess;
    evaluateMaster.table = table.init();

    $(".fixed-table-toolbar").hide();


    $(".rating").rating({
        showClear:false
    });
    Feng.initValidator("evaluationForm", ConstructionEvaluateInfoDlg.validateFields);
});
