import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/ExportBillSyncLogService/GetAll",{params});
    return res;
}
//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/ExportBillSyncLogService/Get",{params});
    return res;
}