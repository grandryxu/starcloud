import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/QualityInfoService/GetAll",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/QualityInfoService/Create",params);
    return res;
}

//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/QualityInfoService/Get",{params});
    return res;
}

//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/QualityInfoService/Update",params);
    return res;
}

//删除
export const deleteApi = async function(params){
    let res = await http.delete("/api/services/app/QualityInfoService/Delete",{params});
    return res;
}
//批量删除
export const deleteListApi = async function(params){
    let res = await http.post("/api/services/app/QualityInfoService/DelteList",params);
    return res;
}


//获取公司树形数据
export const getCompanyTreeList = async function(params){
    let res = await http.get("/api/services/app/CompanyInfo/GetCompanyTree", {params});
    return res;
}