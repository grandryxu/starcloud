import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/PortInfoService/GetAll",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/PortInfoService/Create",params);
    return res;
}

//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/PortInfoService/Get",{params});
    return res;
}

//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/PortInfoService/Update",params);
    return res;
}

//删除
export const deleteApi = async function(params){
    let res = await http.delete("/api/services/app/PortInfoService/Delete",{params});
    return res;
}
//批量删除
export const deleteListApi = async function(params){
    let res = await http.post("/api/services/app/PortInfoService/DelteList",params);
    return res;
}


//巷道出入口关联表新增
export const addtunnelApi = async function(params){
    let res = await http.post("/api/services/app/TunnelPortService/CreateList",params);
    return res;
}