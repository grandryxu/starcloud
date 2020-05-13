import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/UnitInfoService/GetAll",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/UnitInfoService/Create",params);
    return res;
}

//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/UnitInfoService/Get",{params});
    return res;
}

//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/UnitInfoService/Update",params);
    return res;
}

//删除
export const deleteApi = async function(params){
    let res = await http.delete("/api/services/app/UnitInfoService/Delete",{params});
    return res;
}
//批量删除
export const deleteListApi = async function(params){
    let res = await http.post("/api/services/app/UnitInfoService/DelteList",params);
    return res;
}