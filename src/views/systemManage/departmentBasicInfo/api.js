import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/Department/GetAll",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/Department/Create",params);
    return res;
}

//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/Department/Get",{params});
    return res;
}

//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/Department/Update",params);
    return res;
}

//删除
export const deleteApi = async function(params){
    let res = await http.delete("/api/services/app/Department/Delete",{params});
    return res;
}

//获取公司下父级部门列表
export const getDepartmentInfoSelectedList = async function(params){
    let res = await http.get("/api/services/app/Department/GetDepartmentInfoSelectedList",{params});
    return res;
}

//获取公司树形数据
export const getCompanyTreeList = async function(params){
    let res = await http.get("/api/services/app/CompanyInfo/GetCompanyTree", {params});
    return res;
}

//获取部门树形数据
export const getDepartmentTreeList = async function(params){
    let res = await http.get("/api/services/app/Department/GetDepartmentTree", {params});
    return res;
}