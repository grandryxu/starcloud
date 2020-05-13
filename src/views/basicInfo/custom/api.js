import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/CustomInfoService/GetAll",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/CustomInfoService/Create",params);
    return res;
}

//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/CustomInfoService/Get",{params});
    return res;
}

//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/CustomInfoService/Update",params);
    return res;
}

//批量删除
export const deleteApi = async function(params){
    let res = await http.post("/api/services/app/CustomInfoService/CreateDropAll", params);
    return res;
}

//获取客户类型列表
export const getTypeInfoListApi = async function(params){
    let res = await http.get("/api/services/app/CustomTypeInfoService/GetAll",{params});
    return res;
}
