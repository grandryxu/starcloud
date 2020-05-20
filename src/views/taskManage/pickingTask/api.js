import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/PickingTaskService/GetAll",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/PickingTaskService/Create",params);
    return res;
}

//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/PickingTaskService/Get",{params});
    return res;
}

//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/PickingTaskService/Update",params);
    return res;
}

//删除
export const deleteApi = async function(params){
    let res = await http.delete("/api/services/app/PickingTaskService/Delete",{params});
    return res;
}

//开始任务
export const startApi = async function(params){
    let res = await http.post("/api/services/app/PickingTaskService/TaskBegin",params);
    return res;
}

//完成任务
export const finishApi = async function(params){
    let res = await http.post("/api/services/app/PickingTaskService/TaskEnd",params);
    return res;
}

//获取空托盘入库流水列表
export const getkyprkListApi = async function(params){
    let res = await http.get("/api/services/app/ImportStockService/GetAll",{params});
    return res;
}

//修改状态
export const UpdateStateApi = async function(params){
    let res = await http.post("/api/services/app/PickingTaskService/TaskExecuteChange",params);
    return res;
}
//获取单条
export const GetSlotCodeIdApi = async function(params){
    let res = await http.get("/api/services/app/SlotInfoService/GetSlotCodeId",{params});
    return res;
}
//获取任务号
export const GetEncodingRuleCode = async function(params){
    let res = await http.get("/api/services/app/EncodingRuleService/GetEncodingRule",{params});
    return res;
}
