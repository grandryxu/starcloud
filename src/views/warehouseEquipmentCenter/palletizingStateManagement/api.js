import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/palletizingInfoService/GetAll",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/palletizingInfoService/Create",params);
    return res;
}

//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/palletizingInfoService/Get",{params});
    return res;
}

//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/palletizingInfoService/Update",params);
    return res;
}

//批量删除
export const deleteApi = async function(params){
    let res = await http.post("/api/services/app/palletizingInfoService/CreateDropAll",params);
    return res;
}

//获取仓库列表
export const getWarehouseInfoListApi = async function(params){
    let res = await http.get("/api/services/app/WarehouseInfoService/GetWarehousList",{params});
    return res;
}