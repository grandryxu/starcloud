import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/ImportBillheadService/GetAll",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/ImportBillheadService/Create",params);
    return res;
}
//新增-主从
export const addBillApi = async function(params){
    let res = await http.post("/api/services/app/ImportBillheadService/CreatImportBill",params);
    return res;
}
//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/ImportBillheadService/Get",{params});
    return res;
}
//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/ImportBillheadService/Update",params);
    return res;
}
//编辑-主从
export const editBillApi = async function(params){
    let res = await http.put("/api/services/app/ImportBillheadService/UpdateImportBill",params);
    return res;
}
//删除
export const deleteApi = async function(params){
    let res = await http.delete("/api/services/app/ImportBillheadService/Delete",{params});
    return res;
}

//批量删除
export const deleteListApi = async function(params){
    let res = await http.post("/api/services/app/ImportBillheadService/DelteList",params);
    return res;
}
//批量审核
export const auditListApi = async function(params){
    let res = await http.post("/api/services/app/ImportBillheadService/AuditList",params);
    return res;
}
//批量弃审
export const NoauditListApi = async function(params){
    let res = await http.post("/api/services/app/ImportBillheadService/NoAuditList",params);
    return res;
}
//物料接口
//列表
export const getListGoodsApi = async function(params){
    let res = await http.get("/api/services/app/ImportBillbodyService/GetAll",{params});
    return res;
}
//新增
export const addGoodsApi = async function(params){
    let res = await http.post("/api/services/app/ImportBillbodyService/Create",params);
    return res;
}
//获取单条
export const getOneGoodsApi = async function(params){
    let res = await http.get("/api/services/app/ImportBillbodyService/Get",{params});
    return res;
}
//编辑
export const editGoodsApi = async function(params){
    let res = await http.put("/api/services/app/ImportBillbodyService/Update",params);
    return res;
}
//删除
export const deleteGoodsApi = async function(params){
    let res = await http.delete("/api/services/app/ImportBillbodyService/Delete",{params});
    return res;
}
//批量增加
export const addGoodsListApi = async function(params){
    let res = await http.post("/api/services/app/ImportBillbodyService/CreateList",params);
    return res;
}
//批量编辑
export const editGoodsListApi = async function(params){
    let res = await http.put("/api/services/app/ImportBillbodyService/UpdateList",params);
    return res;
}
//批量删除
export const deleteGoodsListApi = async function(params){
    let res = await http.post("/api/services/app/ImportBillbodyService/DelteList",params);
    return res;
}
//生成立库任务
export const addMainTaskApi = async function(params){
    let res = await http.post("/api/services/app/TaskMainInfoService/CreateImportTask",params);
    //let res = await http.post("/api/services/app/ImportBillheadService/CreateTask",params);
    return res;
}
//查询该仓库下的物料
export const GetWarehousematerialsInfo = async function(params){
    let res = await http.get("/api/services/app/GoodsInfoService/GetWarehousematerials",{params});
    //let res = await http.post("/api/services/app/GoodsInfoService/GetWarehousematerials",params);
    return res;
}
//获取入库单号
export const GetEncodingRuleCode = async function(params){
    let res = await http.get("/api/services/app/EncodingRuleService/GetEncodingRule",{params});
    return res;
}

//新增（新增+编辑+删除走一个接口）
export const CreateImportBillApi = async function(params){
    let res = await http.post("/api/services/app/ImportBillheadService/CreateImportBill",params);
    return res;
}

//更新（新增+编辑+删除走一个接口）
export const UpdateImportBillApi = async function(params){
    let res = await http.put("/api/services/app/ImportBillheadService/UpdateImportBill",params);
    return res;
}

