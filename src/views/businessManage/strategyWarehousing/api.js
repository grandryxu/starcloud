import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/StrategyWarehousingService/GetAll",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/StrategyWarehousingService/Create",params);
    return res;
}

//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/StrategyWarehousingService/Get",{params});
    return res;
}

//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/StrategyWarehousingService/Update",params);
    return res;
}

//删除
export const deleteApi = async function(params){
    let res = await http.delete("/api/services/app/StrategyWarehousingService/Delete",{params});
    return res;
}