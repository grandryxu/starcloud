import http from "@/http";
//列表
export const getListApi = async function(params){
    let res = await http.get("/api/services/app/EquipmentLogInfoService/GetAll",{params});
    return res;
}