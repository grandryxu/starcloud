import http from "@/http";
//修改密码
export const changePwd = async function(params){
    let res = await http.post("/api/services/app/User/ChangePassword",params);
    return res;
}
//切换语言
export const changeLanguage = async function(params){
    let res = await http.post("/api/services/app/User/ChangeLanguage", params);
    return res;
}
/*
//重置密码
export const ResetPwd = async function(params){
    let res = await http.post("/api/services/app/User/ResetPassword",params);
    return res;
}*/

//库存日期统计
export const storeDateStatistics = async function(params){
    let res = await http.get("/api/services/app/WarehouseStockService/GetList", params);
    return res;
}

//饼状图统计
export const pieStatistics = async function(params){
    let res = await http.get("/api/services/app/SlotInfoService/GetUtilizationRatePieChart", params);
    return res;
}

//获取盘点任务数
export const GetNowTaskNum = async function(params){
    let res = await http.get("/api/services/app/TaskMainInfoService/GetNowTaskNum", params);
    return res;
}

//获取报警数
export const GetNowAlarmNum = async function(params){
    let res = await http.get("/api/services/app/AlarmService/GetNowAlarmNum", params);
    return res;
}

//获取库位使用率
export const GetNowSlotPercent = async function(params){
    let res = await http.get("/api/services/app/SlotInfoService/GetNowSlotPercent", params);
    return res;
}

//获取盘点任务数
export const GetCheckTaskNum = async function(params){
    let res = await http.get("/api/services/app/StockTaskingService/GetNowTaskNum", params);
    return res;
}

