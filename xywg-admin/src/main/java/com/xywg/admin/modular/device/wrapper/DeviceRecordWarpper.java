package com.xywg.admin.modular.device.wrapper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.Map;

/**
 * 字典列表的包装
 *
 * @author wangcw
 * @date 2017年4月25日 18:10:31
 */
public class DeviceRecordWarpper extends BaseControllerWarpper {

	public DeviceRecordWarpper(Object list) {
		super(list);
	}

	@Override
	public void warpTheMap(Map<String, Object> map) {
		if(map.get("source") != null) {
			map.put("source",ConstantFactory.me().getDictsByName("考勤类型", Integer.parseInt(String.valueOf(map.get("source")))));
		}
		if(map.get("idCardType") != null) {
			map.put("idCardType",ConstantFactory.me().getDictsByName("人员证件类型", Integer.parseInt(String.valueOf(map.get("idCardType")))));
		}
		if (null != map.get("isValid") && !"".equals(map.get("isValid"))) {
			if ((Integer) map.get("isValid") == 1) {
				map.put("validName", "是");
			} else if ((Integer) map.get("isValid") == 0) {
				map.put("validName", "否");
			} else {
				map.put("validName", " ");
			}
		}
        if (null != map.get("deviceType") && !"".equals(map.get("deviceType"))) {
	        if((Integer)map.get("deviceType")==3){
	            map.put("typeName","上班");
	        }else if((Integer)map.get("deviceType")==4){
	            map.put("typeName","下班");
	        }else{
	            map.put("typeName"," ");
	        }
        }
	}
}
