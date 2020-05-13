import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/PackInfoService/GetAll",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/PackInfoService/Create",params);
    return res;
}

//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/PackInfoService/Get",{params});
    return res;
}

//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/PackInfoService/Update",params);
    return res;
}

//删除
export const deleteApi = async function(params){
    let res = await http.post("/api/services/app/PackInfoService/CreateDropAll",params);
    return res;
}