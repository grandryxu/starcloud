import http from "@/http";

class DropBox{
    static async getAllRoles(){
        let res = await http.get("/api/services/app/Role/GetAll");
        return res;
    }

    static async getAllDepartments() {
        let res = await http.get("/api/services/app/Department/GetAll");
        return res;
    }

    static getAllWarehouseType() {
        return [
            {
                value: 1,
                label: '立库'
            },
            {
                value: 2,
                label: '平库'
            },
            {
                value: 3,
                label: '密集库'
            }
        ]
    }
    //仓库下拉列表
    static async getWarehouselist() {
        let res = await http.get("/api/services/app/WarehouseInfoService/GetWarehousList");
        return res;
    }
    //区域下拉列表
    static async getArealist() {
        let res = await http.get("/api/services/app/AreaInfoService/GetAreaList");
        return res;
    }
    //库位容积大小下拉列表
    static async getSlotSizelist() {
        let res = await http.get("/api/services/app/SlotSizeService/GetSlotSizeList");
        return res;
    }
    //权限下拉列表
    static async getAllPermissions() {
        let res = await http.get("/api/services/app/Role/GetPermissions");
        return res;
    }
    //客户列表
    static async getAllCustoms() {
        let res = await http.get("/api/services/app/CustomInfoService/GetAll");
        return res;
    }
    //巷道下拉
    static async getTunnellist() {
        let res = await http.get("/api/services/app/TunnelInfoService/GetAll");
        return res;
    }
    //库位下拉列表
    static async getSlotList() {
        let res = await http.get("/api/services/app/SlotInfoService/GetAll");
        return res;
    }
    //单据下拉列表
    static async getBillList() {
        let res = await http.get("/api/services/app/BillInfoService/GetAll");
        return res;
    }
    //公司列表
    static async getCompanyList() {
        let res = await http.get("/api/services/app/CompanyInfo/GetAll");
        return res;
    }
     //质量状态
     static async getQualitylist() {
        let res = await http.get("/api/services/app/QualityInfoService/GetAll");
        return res;
     }
     //获取单据类型
     static async getBillTypeList(params) {
        let res = await http.get("/api/services/app/BillInfoService/GetAll",{params});
        return res;
     }
    //获取物料列表
    static async getGoodsInfoList(params) {
        let res = await http.get("/api/services/app/GoodsInfoService/GetAll",{params});
        return res;
    }
    //获取物料列表
    static async getGoodsList() {
        let res = await http.get("/api/services/app/GoodsInfoService/GetGoodsInfoList");
        return res;
    }
    //获取出库物料列表
    static async getInventoryGoodsInfoList() {
        let res = await http.get("/api/services/app/InventoryInfoService/GetAll");
        return res;
    }
    //获取公司树形数据
    static async getCompanyTreeList (params){
        let res = await http.get("/api/services/app/CompanyInfo/GetCompanyTree", {params});
        return res;
    }
   
    
}

//命名静态方法时请用小驼峰书写方式

export default DropBox