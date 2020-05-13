package com.xywg.admin.modular.project.wrapper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.Map;

/**
 * 工伤
 * 
 * @author: duanfen
 * @version: 2018年6月22日 上午9:35:54
 */
public class InjuryWrapper extends BaseControllerWarpper {
	public InjuryWrapper(Object list) {
		super(list);
	}

	@Override
	public void warpTheMap(Map<String, Object> map) {
		if(map.get("type") != null) {
			map.put("type",ConstantFactory.me().getDictsByName("工伤类型", Integer.parseInt(String.valueOf(map.get("type")))));
		}
		if(map.get("idCardType") != null) {
			map.put("idCardType",ConstantFactory.me().getDictsByName("人员证件类型", Integer.parseInt(String.valueOf(map.get("idCardType")))));
		}
	}
}
