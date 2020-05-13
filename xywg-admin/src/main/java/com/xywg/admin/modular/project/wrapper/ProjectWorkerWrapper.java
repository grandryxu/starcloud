package com.xywg.admin.modular.project.wrapper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.Map;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/5/24 10:02
 */
public class ProjectWorkerWrapper extends BaseControllerWarpper{
    public ProjectWorkerWrapper(Object list){
        super(list);
    }
    @Override
    public void warpTheMap(Map<String,Object> map){
        /*map.put("organizationTypeName", ConstantFactory.me().getOrganizationTypeName(Integer.parseInt(map.get("organizationType").toString())));
        map.put("businessStatusName", ConstantFactory.me().getBusinessStatusName(Integer.parseInt(map.get("businessStatus").toString())));*/
//        map.put("projectStatusName", ConstantFactory.me().projectStatusName(Integer.parseInt(map.get("projectStatus").toString())));
        map.put("idCardTypeName", ConstantFactory.me().getDictsByName("人员证件类型",Integer.parseInt(String.valueOf(map.get("idCardType")))));
        if(map.get("type") != null && map.get("type") != "") {
        	 int type=(Integer) map.get("type");
             String typeName="";
             if(type==0){
                 typeName="进场";
             }else{
                 typeName="退场";
             }
             map.put("typeName",typeName);
        }
        
        if(map.get("joinStatus") != null && map.get("joinStatus") != "") {
        	 int joinStatus=(Integer) map.get("joinStatus");
        	if(joinStatus == 1) {
        		 map.put("joinStatusName","待进场");
        	} else if(joinStatus == 2) {
       		    map.put("joinStatusName","进场");
        	}else if(joinStatus == 3) {
         		map.put("joinStatusName","退场");
          	}
        }
       

    }
}
