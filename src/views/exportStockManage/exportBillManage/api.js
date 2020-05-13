import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/ExportBillheadService/GetAll",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/ExportBillheadService/Create",params);
    return res;
}
//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/ExportBillheadService/Get",{params});
    return res;
}
//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/ExportBillheadService/Update",params);
    return res;
}
//删除
export const deleteApi = async function(params){
    let res = await http.delete("/api/services/app/ExportBillheadService/Delete",{params});
    return res;
}
//物料接口
//列表
export const getListGoodsApi = async function(params){
    let res = await http.get("/api/services/app/ExportBillbodyService/GetAll",{params});
    return res;
}
//新增
export const addGoodsApi = async function(params){
    let res = await http.post("/api/services/app/ExportBillbodyService/Create",params);
    return res;
}
//获取单条
export const getOneGoodsApi = async function(params){
    let res = await http.get("/api/services/app/ExportBillbodyService/Get",{params});
    return res;
}
//编辑
export const editGoodsApi = async function(params){
    let res = await http.put("/api/services/app/ExportBillbodyService/Update",params);
    return res;
}
//删除
export const deleteGoodsApi = async function(params){
    let res = await http.delete("/api/services/app/ExportBillbodyService/Delete",{params});
    return res;
}
//批量增加、编辑、删除
export const saveGoodsListApi = async function(params){
    let res = await http.post("/api/services/app/ExportBillbodyService/SaveList",params);
    return res;
}
//批量增加
export const addGoodsListApi = async function(params){
    let res = await http.post("/api/services/app/ExportBillbodyService/CreateList",params);
    return res;
}
//批量编辑
export const editGoodsListApi = async function(params){
    let res = await http.put("/api/services/app/ExportBillbodyService/UpdateList",params);
    return res;
}
//批量删除
export const deleteGoodsListApi = async function(params){
    let res = await http.post("/api/services/app/ExportBillbodyService/DelteList",params);
    return res;
}
//设定波次号
export const setWaveNoApi = async function(params){
    let res = await http.post("/gapi/services/app/ExportBillheadService/EditWaveNo",params);
    return res;
}
//获取库存列表
export const getInventoryApi = async function(params){
    let res = await http.get("/api/services/app/InventoryInfoService/GetInventoryStatisticsBatch",params);
    return res;
}
//自动出库
export const autoExportApi = async function(params){
    let res = await http.post("/api/services/app/TaskMainInfoService/CreateAutomaticTask",params);
    //let res = await http.post("/api/services/app/ExportBillheadService/CreateAutomaticTask",params);
    return res;
}
//手动出库
export const manualExportApi = async function(params){
    let res = await http.post("/api/services/app/TaskMainInfoService/CreateManualTask",params);
    //let res = await http.post("/api/services/app/ExportBillheadService/CreateManualTask",params);
    return res;
}
//获取手动出库物料
export const manualInventoryApi = async function(params){
    let res = await http.get("/api/services/app/InventoryInfoService/GetInventoryInfoForHead",{params});
    return res;
}
//获取出库单号
export const GetEncodingRuleCode = async function(params){
    let res = await http.get("/api/services/app/EncodingRuleService/GetEncodingRule",{params});
    return res;
}