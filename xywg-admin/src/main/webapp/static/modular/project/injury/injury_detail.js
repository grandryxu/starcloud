/**
 * 初始化工伤管理详情对话框
 */
var InjuryDetailDlg = {
    injuryDetailData: {},
};


$(function () {
    var occureTimeHidden = $('#occureTimeHidden').val();
    if(occureTimeHidden){
        occureTimeHidden = occureTimeHidden.substr(0,occureTimeHidden.indexOf('.'));
    }
    laydate.render({
        elem: '#occureTime',
        max: 0,
        type: 'datetime',
        value:occureTimeHidden
    });
});