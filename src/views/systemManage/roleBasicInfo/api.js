import http from "@/http";
//列表
export const getListApi = async function (params) {
    let res = await http.get("/api/services/app/Role/GetAll", { params });
    return res;
}
//新增
export const addApi = async function (params) {
    let res = await http.post("/api/services/app/Role/Create", params);
    return res;
}

//获取单条
export const getOneApi = async function (params) {
    let res = await http.get("/api/services/app/Role/Get", { params });
    return res;
}

//编辑
export const editApi = async function (params) {
    let res = await http.put("/api/services/app/Role/Update", params);
    return res;
}

//删除
export const deleteApi = async function (params) {
    let res = await http.delete("/api/services/app/Role/Delete", { params });
    return res;
}

//更新角色权限
export const updateRolePermissions = async function (params) {
    let res = await http.put("/api/services/app/Role/UpdateRolePermissions", params);
    return res;
}

export const getPermissionList = async function (params) {
    let res = await http.get(
        "/api/services/app/SystemMenuInfoService/GetPermissionList",
        { params }
    );
    return res;
}

export const getRoleForEdit = async function (params) {
    let res = await http.get(
        "/api/services/app/Role/GetRoleForEdit",
        { params }
    );
    return res;
}