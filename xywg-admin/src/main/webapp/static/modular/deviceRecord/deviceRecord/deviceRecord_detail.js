/**
 * 初始化工伤管理详情对话框
 */
var DeviceRecordDetailDlg = {
		DeviceRecordDetailData: {},
};


$(function () {
    var occureTimeHidden = $('#timeHidden').val();
    if(occureTimeHidden){
        occureTimeHidden = occureTimeHidden.substr(0,occureTimeHidden.indexOf('.'));
    }
    laydate.render({
        elem: '#time',
        max: 0,
        type: 'datetime',
        value:occureTimeHidden
    });
});