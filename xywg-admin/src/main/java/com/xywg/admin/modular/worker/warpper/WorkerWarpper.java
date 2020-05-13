package com.xywg.admin.modular.worker.warpper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的包装类
 *
 * @author wangcw
 * @date 2017年2月13日 下午10:47:03
 */
public class WorkerWarpper extends BaseControllerWarpper {

    public WorkerWarpper(List<Map<String, Object>> list) {

        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	if(map.get("gender") != null) {
    		map.put("genderName", ConstantFactory.me().getDictsByName("性别", (Integer) map.get("gender")));
    	}
        if (map.get("nation") != null&&!map.get("nation") .equals("") ) {
            map.put("nationName", ConstantFactory.me().getDictsByName("民族", Integer.parseInt(String.valueOf(map.get("nation")))));
        }
        if(map.get("idCardType") != null) {
        	map.put("idCardTypeName", ConstantFactory.me().getDictsByName("人员证件类型", (Integer) map.get("idCardType")));
        }
        if (map.get("cultureLevelType") != "" && map.get("cultureLevelType") != null) {
            map.put("cultureLevelTypeName", ConstantFactory.me().getDictsByName("文化程度", (Integer) map.get("cultureLevelType")));
        }
        if (map.get("workTypeCode") != "" && map.get("workTypeCode") != null) {
            map.put("workTypeCodeName", ConstantFactory.me().getDictsByName("工种字典数据", Integer.parseInt("".equals(String.valueOf(map.get("workTypeCode"))) ? "0" : String.valueOf(map.get("workTypeCode")))));
        }
        if (map.get("birthPlaceCode") != "" && map.get("birthPlaceCode") != null) {
            map.put("birthPlaceCodeName", ConstantFactory.me().getAreaName(Integer.parseInt("".equals(String.valueOf(map.get("birthPlaceCode"))) ? "0" : String.valueOf(map.get("birthPlaceCode")))));
        }
        if(map.get("isAuth") != null) {
        	map.put("isAuthName", (Integer) map.get("isAuth") == 0 ? "未认证" : "已认证");
        }
        if(map.get("isFace") != null) {
        	map.put("isFaceName", (Integer) map.get("isFace") == 1 ? "是" : "否");
        }
        if(map.get("isJoined") != null) {
        	map.put("isJoinedName", (Integer) map.get("isJoined") == 1 ? "是" : "否");
        }
        if (map.get("hasBadMedicalHistory") != "" && map.get("hasBadMedicalHistory") != null) {
            map.put("hasBadMedicalHistoryName", (Integer) map.get("hasBadMedicalHistory") == 1 ? "是" : "否");
        }
        if (map.get("isDel") != "" && map.get("isDel") != null) {
            map.put("isDelName", (Integer) map.get("isDel") == 1 ? "是" : "否");
        }
        if (map.get("joinStatus") != null) {
        	Integer joinStatus = (Integer) map.get("joinStatus");
        	if(joinStatus == 1) {
        		map.put("joinStatusName", "待进场");
        	}else if(joinStatus == 2) {
        		map.put("joinStatusName", "进场");
        	}else {
        		map.put("joinStatusName", "退场");
        	}
            
        }
    }
}
