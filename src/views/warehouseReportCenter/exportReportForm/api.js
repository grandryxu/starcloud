import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/ExportBillbodyService/GetAll",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/ExportBillbodyService/Create",params);
    return res;
}

//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/ExportBillbodyService/Get",{params});
    return res;
}

//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/ExportBillbodyService/Update",params);
    return res;
}

//删除
export const deleteApi = async function(params){
    let res = await http.delete("/api/services/app/ExportBillbodyService/Delete",{params});
    return res;
}

//流水列表
export const getOrderListApi = async function(params){
    let res = await http.get("/api/services/app/ExportOrderService/GetAll",{params});
    return res;
}