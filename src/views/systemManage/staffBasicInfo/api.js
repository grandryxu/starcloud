import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/User/GetAll",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/User/Create",params);
    return res;
}

//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/User/Get",{params});
    return res;
}

//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/User/Update",params);
    return res;
}

//删除
export const deleteApi = async function(params){
    let res = await http.delete("/api/services/app/User/Delete",{params});
    return res;
}

//获取部门目录
export const getCompanyAndDepartmentInfoTree = async function(params){
    let res = await http.get("/api/services/app/CompanyInfo/GetCompanyAndDepartmentInfoTree",{params});
    return res;
}





//获取公司列表
export const getAllCompanyList = async function(params){
    let res = await http.get("/api/services/app/CompanyInfo/GetAll",{MaxResultCount:100});
    return res;
}

//重置密码
export const ResetPwd = async function(params){
    let res = await http.post("/api/services/app/User/ResetPassword",params);
    return res;
}