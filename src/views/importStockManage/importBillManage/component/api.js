import http from "@/http";

//扫描入库
export const ScanaddApi = async function(params){
    let res = await http.post("/api/services/app/ImportOrderService/Create",params);
    return res;
}

