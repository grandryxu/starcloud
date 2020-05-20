import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/QualityCheckService/GetAll",{params});
    return res;
}

//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/QualityCheckService/Get",{params});
    return res;
}

//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/QualityCheckService/Update",params);
    return res;
}

//删除
export const deleteApi = async function(params){
    let res = await http.delete("/api/services/app/QualityCheckService/Delete",{params});
    return res;

}

//获取抽检单号
export const GetEncodingRuleCode = async function(params){
    let res = await http.get("/api/services/app/EncodingRuleService/GetEncodingRule",{params});
    return res;
}

//获取批次号以及批次号对应的物料信息
export const getBatchGoods = async function(params){
    let res= await http.get("/api/services/app/InventoryInfoService/GetBatchGoods",{params});
    return res;
}

//获取库存Inventory信息
export const getCheckInventory = async function(params){
    let res= await http.get("/api/services/app/QualityCheckService/GetCheckInventory",{params});
//   let res= await http.get("/api/services/app/InventoryInfoService/GetAll",{params});
    return res;
}

//新增抽检
export const addCheckApi = async function(params){
    let res = await http.post("/api/services/app/QualityCheckService/Create",params);
    return res;
}

//批量新增物料抽检明细
export const createCheckDetails = async function(params){
    let res=await http.post("/api/services/app/QualityCheckDetailService/CreateList",params);
    return res;
}

//获取抽检明细
export const getAllCheckDetails = async function(params){
    let res= await http.get("/api/services/app/QualityCheckDetailService/GetAll",{params});
    return res;
}

//更新抽检明细
export const updateCheckDetails = async function(params){
    let res= await http.put("/api/services/app/QualityCheckDetailService/Update",params);
    return res;
}

//更新库存表Inventory质量状态
export const updateInventoryQuality = async function(params){
    let res= await http.put("/api/services/app/QualityCheckService/UpdateInventoryQuality",params);
    return res;
}

//对抽检单生成出库单
export const createOutBillByChecked = async function(params){
    let res= await http.post("/api/services/app/QualityCheckService/CreateOutBillByChecked",params);
    return res;
}

//检测放行
export const checkRelease = async function(params){
   let res= await http.post("/api/services/app/QualityCheckService/CheckReleased",params);
   return res;
}

