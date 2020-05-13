package com.xywg.admin.modular.bad.warpper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

public class WorkerBadRecordsWarpper extends BaseControllerWarpper  {

	public WorkerBadRecordsWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	
    	if (null != map.get("idCardType") && !"".equals(map.get("idCardType"))) {
    		map.put("idCardTypeName", ConstantFactory.me().getDictsByName("人员证件类型",Integer.parseInt(String.valueOf(map.get("idCardType")))));
    	}
    	if (null != map.get("badRecordTypeCode") && !"".equals(map.get("badRecordTypeCode")) && !"null".equals(map.get("badRecordTypeCode"))) {
    		map.put("badRecordType", ConstantFactory.me().getDictsByName("不良记录类别",Integer.valueOf(map.get("badRecordTypeCode").toString())));
    	}
    	if (null != map.get("badRecordLevelType") && !"".equals(map.get("badRecordLevelType"))) {
    		map.put("badRecordLevelTypeName", ConstantFactory.me().getDictsByName("事件级别",(Integer) map.get("badRecordLevelType")));
    	}
        if (null != map.get("joinStatus") && !"".equals(map.get("joinStatus"))) {
	        if((Integer)map.get("joinStatus")==1){
	            map.put("joinStatusName","待进场");
	        }else if((Integer)map.get("joinStatus")==2){
	            map.put("joinStatusName","进场");
	        }else{
	            map.put("joinStatusName","退场");
	        }
        }
        if (null != map.get("isAudit") && !"".equals(map.get("isAudit"))) {
	        if ((Integer)map.get("isAudit")==1) {
	        	map.put("isAuditName","待审核");
	        } else if((Integer)map.get("isAudit")==2){
	        	map.put("isAuditName","通过");
	        } else if((Integer)map.get("isAudit")==3){
	        	map.put("isAuditName","不通过");
	        }
        }

    }
}
