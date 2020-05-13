package com.xywg.admin.modular.system.warpper;

import java.util.List;
import java.util.Map;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;

public class TimeSetWarpper extends BaseControllerWarpper  {

	public TimeSetWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	
        if (null != map.get("status") && !"".equals(map.get("status"))) {
	        if ((Integer)map.get("status")==1) {
	        	map.put("statusName","启用");
	        } else if((Integer)map.get("status")==0){
	        	map.put("statusName","禁用");
	        } 
        }
        if(map.get("type").equals("worker_am")){
            map.put("typeName","工人上午打卡区间");
        }
        if(map.get("type").equals("worker_pm")){
            map.put("typeName","工人下午打卡区间");
        }
        if(map.get("type").equals("manager_am")){
            map.put("typeName","管理员上午打卡区间");
        }
        if(map.get("type").equals("manager_pm")){
            map.put("typeName","管理员下午打卡区间");
        }

    }
}
