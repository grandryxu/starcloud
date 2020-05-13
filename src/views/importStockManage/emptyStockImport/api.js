import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/ImportStockService/GetAll",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/ImportStockService/Create",params);
    return res;
}
//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/ImportStockService/Get",{params});
    return res;
}
//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/ImportStockService/Update",params);
    return res;
}
//删除
export const deleteApi = async function(params){
    let res = await http.delete("/api/services/app/ImportStockService/Delete",{params});
    return res;
}
//检测重复托盘号
export const checkDuplicateApi = async function(params){
    let res= await http.post("/api/services/app/ImportStockService/CheckDuplicateStock",params);
    return res;
}
//获取单条
export const GetSlotCodeIdApi = async function(params){
    let res = await http.get("/api/services/app/SlotInfoService/GetSlotCodeId",{params});
    return res;
}
//生成流水任务
export const addMainTaskApi = async function(params){
    let res = await http.post("/api/services/app/TaskMainInfoService/CreateImportStockTask",params);
    //let res = await http.post("/api/services/app/ImportStockService/CreateStockTask",params);
    return res;
}
