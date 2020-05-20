import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/ImportOrderService/GetAll",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/ImportOrderService/Create",params);
    return res;
}

//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/ImportOrderService/Get",{params});
    return res;
}

//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/ImportOrderService/Update",params);
    return res;
}

//删除
export const deleteApi = async function(params){
    let res = await http.delete("/api/services/app/ImportOrderService/Delete",{params});
    return res;
}

//批量增加
export const addListApi = async function(params){
    let res = await http.post("/api/services/app/ImportOrderService/CreateList",params);
    return res;
}

//批量编辑
export const editListApi = async function(params){
    let res = await http.put("/api/services/app/ImportOrderService/UpdateList",params);
    return res;
}

//批量删除
export const deleteListApi = async function(params){
    let res = await http.post("/api/services/app/ImportOrderService/DelteList",params);
    return res;
}
//生成立库任务
export const addMainTaskApi = async function(params){
    let res = await http.post("/api/services/app/TaskMainInfoService/CreateImportTask",params);
    //let res = await http.post("/api/services/app/ImportBillheadService/CreateTask",params);
    return res;
}