import Utils from '@/utils/index'
import Clone from '@/utils/clone'
export default {
    updatePageSize(state, payload) {
        state.c_pageSize = payload;
    },
    addRouteRecord(state, payload) {
        state.route_record.push(payload)
    },
    removeLoginRecord(state, payload) {
            state.route_record.forEach((el,index,arr) => {
                if (el.name === 'Login') {
                arr.splice(index,1)
            }
            
        })
    },
    closeAllRouteRecord(state, payload) {
        state.route_record = [{
            name: 'home',
            path: '/home/index',
            meta: {
                title: 'dashboard'
            }
        }]
    },
    closeOtherRouteRecord(state, payload) {
        state.route_record = [{
            name: 'home',
            path: '/home/index',
            meta: {
                title: 'dashboard'
            }
        }];
        if (payload.name !== 'home') {
            state.route_record.push(payload);
        }
    },
    closeOneRouteRecord(state, payload) {
        console.log(payload)
        state.route_record.findIndex(function (value,index,arr) {
            if (value.name === payload.name) {
                arr.splice(index, 1);
                return arr
            }
        })
        console.log(state.route_record)
    },
    //拷贝原始完全路由
    COPYORIGINROUTERS(state,routers){
        state.originRouters = Clone(routers)
    },
    //过滤出进入页面的权限
    filterEnterTargetPermissions(state,permissionName){
        let permissions = Utils.getStorage("userInfo").data.result.permissions;
        let currentPermissions = [];
        let regAdd = /Add$/;
        let regDelete = /Delete$/;
        let regUpdate = /Update$/;
        let regGet = /Get$/;
        let regExport = /Export$/;
        let regPrint = /Print$/;
        let regImportLock = /ImportLock$/;
        let regExportLock = /ExportLock$/;
        let regShield = /Shield$/;
        let regAreaSelect = /AreaSelect$/;
        let regBatchUpdate = /BatchUpdate$/;
        let regEnable = /Enable$/;
        let regForbidden = /Forbidden$/;
        let regCreate = /Create$/;
        let regExamine = /Examine$/;
        let regUnExamined = /UnExamined$/;
        let regCancle = /Cancle$/;
        let regOneKey = /OneKey$/;
        let regFinish = /Finish$/;
        let regTask = /Task$/;
        let regBillPrint = /BillPrint$/;
        let regAnalogScan = /AnalogScan$/;
        let regWave = /Wave$/;
        let regAuto = /Auto$/;
        let regManual = /Manual$/;
        let regReview =/Review$/;
        let regStart = /Start$/;
        let regPause = /Pause$/;
        let regResume = /Resume$/;
        let regOrder = /Order$/;
        let regModify = /Modify$/;
        let regOutbound = /Outbound$/;
        let regCheck = /Check$/;
        let regReleased = /Released$/;
        let regFinishs = /Finishs$/;
        let regCancles = /Cancles$/;
        let regManuals = /Manuals$/;
        let regLook = /Look$/;
        console.log(permissions)
        currentPermissions = permissions.filter(per=>{
            return per.indexOf(permissionName) !== -1
        })
        currentPermissions = currentPermissions.map(per=>{
            if(regAdd.test(per)){
                return per = '新增'
            }else if(regDelete.test(per)){
                return per = '删除'
            }else if(regUpdate.test(per)){
                return per = '编辑'
            }else if(regExport.test(per)){
                return per = '导出'
            }else if(regBillPrint.test(per)){
                return per = '单据打印'
            }else if(regPrint.test(per)){
                return per = '打印'
            }else if(regImportLock.test(per)){
                return per = '入库锁定'
            }else if(regExportLock.test(per)){
                return per = '出库锁定'
            }else if(regShield.test(per)){
                return per = '屏蔽'
            }else if(regAreaSelect.test(per)){
                return per = '区域选择'
            }else if(regBatchUpdate.test(per)){
                return per = '批量修改'
            }else if(regEnable.test(per)){
                return per = '启用'
            }else if(regForbidden.test(per)){
                return per = '禁用'
            }else if(regCreate.test(per)){
                return per = '库位生成'
            }else if(regExamine.test(per)){
                return per = '审核'
            }else if(regUnExamined.test(per)){
                return per = '弃审'
            }else if(regCancle.test(per)){
                return per = '作废'
            }else if(regOneKey.test(per)){
                return per = '一键走账'
            }else if(regFinish.test(per)){
                return per = '单据完结'
            }else if(regTask.test(per)){
                return per = '生成任务'
            }else if(regAnalogScan.test(per)){
                return per = '模拟扫描'
            }else if(regWave.test(per)){
                return per = '设定波次'
            }else if(regAuto.test(per)){
                return per = '自动出库'
            }else if(regManual.test(per)){
                return per = '手动出库'
            }else if(regReview.test(per)){
                return per = '复核'
            }else if(regStart.test(per)){
                return per = '开始'
            }else if(regPause.test(per)){
                return per = '暂停'
            }else if(regResume.test(per)){
                return per = '恢复'
            }else if(regOrder.test(per)){
                return per = '调整优先级'
            }else if(regModify.test(per)){
                return per = '修改状态'
            }else if(regOutbound.test(per)){
                return per = '生成出库单'
            }else if(regCheck.test(per)){
                return per = '检测'
            }else if(regReleased.test(per)){
                return per = '质量放行'
            }else if(regFinishs.test(per)){
                return per = '完成'
            }else if(regCancles.test(per)){
                return per = '取消'
            }else if(regManuals.test(per)){
                return per = '手动生成'
            }else if(regLook.test(per)) {
                return per = '查看'
            }
        })
        console.log(currentPermissions)
        state.enterTargetPagePermissions = currentPermissions
    },
    //获取当前vue实例和页面节点dom
    GETCURRENTPAGE(state,currentPage){
        state.currentPage = currentPage;
        console.log(state.currentPage)
    },
    //测试国际化
    INTERNATIONALIZATION(state){
        console.log(state.currentPage)
        state.currentPage.childNodes[0].childNodes[0].childNodes[0].childNodes[0].childNodes[0].innerText ='国际化来了'
    },
    //存储当前语言
    STORECURRENTLANG(state,lang){
        state.currentLang = lang
    }
}