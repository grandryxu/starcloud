import http from "@/http";
//列表
export const getListApi = async function (params) {
  let res = await http.get("/api/services/app/MoveModelMenuService/GetAll", {
    params
  });
  return res;
};
//新增
export const addApi = async function (params) {
  let res = await http.post(
    "/api/services/app/MoveModelMenuService/Create",
    params
  );
  return res;
};

//获取单条
export const getOneApi = async function (params) {
  let res = await http.get("/api/services/app/MoveModelMenuService/Get", {
    params
  });
  return res;
};

//编辑
export const editApi = async function (params) {
  let res = await http.put(
    "/api/services/app/MoveModelMenuService/Update",
    params
  );
  return res;
};

//删除
export const deleteApi = async function (params) {
  let res = await http.delete(
    "/api/services/app/MoveModelMenuService/Update",
    { params }
  );
  return res;
};

//获取树形结构
export const getSystemMenuInfoTree = async function (params) {
  let res = await http.get(
    "/api/services/app/MoveModelMenuService/GetMoveModelMenuTree",
    { params }
  );
  return res;
};

//获取父级下拉数据
export const getSystemMenuInfoSelectedList = async function (params) {
  let res = await http.get(
    "/api/services/app/MoveModelMenuService/GetMoveModelMenuSelectedList",
    { params }
  );
  return res;
};

//获取下级模块列表
export const getSystemSubMenuInfoList = async function (params) {
  let res = await http.get(
    "/api/services/app/MoveModelMenuService/GetMoveSubMenuInfoList",
    { params }
  );
  return res;
};

export const getPermissionList = async function (params) {
  let res = await http.get(
    "/api/services/app/MoveModelMenuService/GetPermissionList",
    { params }
  );
  return res;
}
