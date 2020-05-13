import http from "@/http";
//列表
export const getListApi = async function (params) {
  let res = await http.get("/api/services/app/SystemMenuInfoService/GetAll", {
    params
  });
  return res;
};
//新增
export const addApi = async function (params) {
  let res = await http.post(
    "/api/services/app/SystemMenuInfoService/Create",
    params
  );
  return res;
};

//获取单条
export const getOneApi = async function (params) {
  let res = await http.get("/api/services/app/SystemMenuInfoService/Get", {
    params
  });
  return res;
};

//编辑
export const editApi = async function (params) {
  let res = await http.put(
    "/api/services/app/SystemMenuInfoService/Update",
    params
  );
  return res;
};

//删除
export const deleteApi = async function (params) {
  let res = await http.delete(
    "/api/services/app/SystemMenuInfoService/Delete",
    { params }
  );
  return res;
};

//获取树形结构
export const getSystemMenuInfoTree = async function (params) {
  let res = await http.get(
    "/api/services/app/SystemMenuInfoService/GetSystemMenuInfoTree",
    { params }
  );
  return res;
};

//获取父级下拉数据
export const getSystemMenuInfoSelectedList = async function (params) {
  let res = await http.get(
    "/api/services/app/SystemMenuInfoService/GetSystemMenuInfoSelectedList",
    { params }
  );
  return res;
};

//获取下级模块列表
export const getSystemSubMenuInfoList = async function (params) {
  let res = await http.get(
    "/api/services/app/SystemMenuInfoService/GetSystemSubMenuInfoList",
    { params }
  );
  return res;
};

export const getPermissionList = async function (params) {
  let res = await http.get(
    "/api/services/app/SystemMenuInfoService/GetPermissionList",
    { params }
  );
  return res;
}
