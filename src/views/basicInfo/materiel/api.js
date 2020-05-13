import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/GoodsInfoService/GetAll",{params});
    return res;
}
//新增
export const addApi = async function(params){
    let res = await http.post("/api/services/app/GoodsInfoService/Create",params);
    return res;
}

//获取单条
export const getOneApi = async function(params){
    let res = await http.get("/api/services/app/GoodsInfoService/Get",{params});
    return res;
}

//编辑
export const editApi = async function(params){
    let res = await http.put("/api/services/app/GoodsInfoService/Update",params);
    return res;
}

//删除
export const deleteApi = async function(params){
    let res = await http.delete("/api/services/app/GoodsInfoService/Delete",{params});
    return res;
}

//获取监控策略
export const getstrategyMonitorListApi = async function(params){
    let res = await http.get("/api/services/app/StrategyMonitorService/GetAll",{params});
    return res;
}

//获取上架策略
export const getstrategyWarehousingListApi = async function(params){
    let res = await http.get("/api/services/app/StrategyWarehousingService/GetAll",{params});
    return res;
}

//获取分配策略
export const getstrategyDistributionListApi = async function(params){
    let res = await http.get("/api/services/app/StrategyDistributionService/GetAll",{params});
    return res;
}

//获取区域
export const getAreaListApi = async function(params){
    let res = await http.get("/api/services/app/AreaInfoService/GetAreaList",{params});
    return res;
}


//获取仓库区域
export const getWarehouseAreaListApi = async function(params){
    let res = await http.get("/api/services/app/WarehouseInfoService/GetWarehouseAreaList",{params});
    return res;
}



//获取垛型
export const getPackListApi = async function(params){
    let res = await http.get("/api/services/app/PackInfoService/GetAll",{params});
    return res;
}

//获取单位
export const getUnitListApi = async function(params){
    let res = await http.get("/api/services/app/UnitInfoService/GetAll",{params});
    return res;
}

//批量配置策略
export const editStrategyApi = async function(params){
    let res = await http.post("/api/services/app/GoodsInfoService/EditStrategy",params);
    return res;
}




/*//批量增加
export const addListApi = async function(params){
    let res = await http.post("/api/services/app/GoodsInfoService/CreateList",params);
    return res;
}

//批量编辑
export const editListApi = async function(params){
    let res = await http.put("/api/services/app/GoodsInfoService/UpdateList",params);
    return res;
}*/

//批量删除
export const deleteListApi = async function(params){
    let res = await http.post("/api/services/app/GoodsInfoService/DelteList",params);
    return res;
}
