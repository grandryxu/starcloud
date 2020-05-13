import http from "@/http";
//获取托盘库存列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/InventoryInfoService/GetStockInventory",{params});
    return res;
}
//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/InventoryInfoService/GetStockInventoryDetail",{params});
    return res;
}
//生成任务
export const addMainTaskApi = async function(params){
    let res = await http.post("/api/services/app/TaskMainInfoService/CreateExportTask",params);
    //let res = await http.post("/api/services/app/ExportStockService/CreateTask",params);
    return res;
}