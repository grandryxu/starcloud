import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/StockTaskingService/GetAll",{params});
    return res;
}
//新增
export const addApi = async function (params) {
    let res = http.post("/api/services/app/StockTaskingService/Create", params);
    return res;
}
//新增明细
export const addDetailApi = async function (params) {
    let res = http.post("/api/services/app/StockTaskingDetailService/CreateList", params);
    return res;
}
//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/StockTaskingService/Get",{params});
    return res;
}
//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/StockTaskingService/Update",params);
    return res;
}
//修改明细
export const editDetailApi = async function(params){
    let res = await http.put("/api/services/app/StockTaskingDetailService/UpdateList",params);
    return res;
}
//删除
export const deleteApi = async function(params){
    let res = await http.delete("/api/services/app/StockTaskingService/Delete",{params});
    return res;
}
//库存列表
export const getInventoryListApi = async function(params){
    let res = await http.get("/api/services/app/InventoryInfoService/GetInventoryForStockTasking",{params});
    return res;
}
//列表
export const getDetailListApi = async function(params){
    let res = await http.get("/api/services/app/StockTaskingDetailService/GetDetailList",{params});
    return res;
}
//生成任务
export const createTaskApi = async function(params){
    let res = await http.post("/api/services/app/StockTaskingService/CreateTask",params);
    return res;
}