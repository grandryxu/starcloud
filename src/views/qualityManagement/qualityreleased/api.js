import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/SlotInfoService/GetAll",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/SlotInfoService/Create",params);
    return res;
}

//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/SlotInfoService/Get",{params});
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

// 获取待检测放行的库存
export const getWaitForReleasedInventory =async function(params){
    let res= await http.get("/api/services/app/QualityReleasedService/GetWaitForReleasedInventory",{params});
    return res;
}

//检测放行后更新库存表质量状态
export const updateReleasedInventory = async function(params){
   let res= await http.put("/api/services/app/QualityReleasedService/UpdateReleasedInventory",params);
   return res;
}