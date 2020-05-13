import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/RowInfoService/GetAll",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/RowInfoService/Create",params);
    return res;
}

//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/RowInfoService/Get",{params});
    return res;
}

//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/RowInfoService/Update",params);
    return res;
}

//删除
export const deleteApi = async function(params){
    let res = await http.delete("/api/services/app/RowInfoService/Delete",{params});
    return res;
}

//查询排位
export const  getRowInfoByRowNo =async function(params){
    let res = await http.get("/api/services/app/RowInfoService/GetRowInfoByRowNo",{params});
    return res;
}

//根据类型查询已有排号
export const getRowInfoByType = async function(params){
   let res= await http.get("/api/services/app/RowInfoService/GetRowInfoByType",{params});
   return res;
}

//查询所有
export const getAllRowInfoQuery = async function(params){
   let res= await http.get("/api/services/app/RowInfoService/GetAllRowInfoQuery",{params});
   return res;
}
// 根据当前库位排生成库位
export const generateSlotByStock = async function(params){
    let res=await http.get("/api/services/app/RowInfoService/GetGenerateSlot",{params});
    return res;
}

//根据仓库获取库位排序
export const getRowOrder = async function(params){
    let res= await http.get("/api/services/app/RowInfoService/GetRowOrders",{params});
    return res;
}