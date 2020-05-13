import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/ExportStockService/GetAll",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/ExportStockService/Create",params);
    return res;
}
//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/ExportStockService/Get",{params});
    return res;
}
//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/ExportStockService/Update",params);
    return res;
}
//删除
export const deleteApi = async function(params){
    let res = await http.delete("/api/services/app/ExportStockService/Delete",{params});
    return res;
}
//获取库存列表
export const getInventoryApi = async function(params){
    let res = await http.get("/api/services/app/InventoryInfoService/GetStockService",{params});
    return res;
}