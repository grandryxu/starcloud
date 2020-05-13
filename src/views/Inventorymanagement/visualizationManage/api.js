import http from "@/http";
//按层查询的列表
export const getListByLevelApi = async function(params){
    let res = await http.get("/api/services/app/SlotInfoService/GetSlotByLayer",{params});
    return res;
}

//库位层
export const getSlotLevelApi = async function(params){
    let res = await http.get("/api/services/app/RowInfoService/GetLayerCount",{params});
    return res;
}

//按排查询的列表
export const getListByRowApi = async function(params){
    let res = await http.get("/api/services/app/SlotInfoService/GetSlotByRow",{params});
    return res;
}

//库位排
export const getSlotRowApi = async function(params){
    let res = await http.get("/api/services/app/RowInfoService/GetRowCount",{params});
    return res;
}
