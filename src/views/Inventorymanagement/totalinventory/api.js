import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/InventoryInfoService/GetTotalInventory",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/SlotInfoService/Create",params);
    return res;
}
//获取总库存详情数据
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/InventoryInfoService/GetTotalInventoryDetail",{params});
    return res;
}
//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/SlotInfoService/Update",params);
    return res;
}
//删除
export const deleteApi = async function(params){
    let res = await http.delete("/api/services/app/SlotInfoService/Delete",{params});
    return res;
}