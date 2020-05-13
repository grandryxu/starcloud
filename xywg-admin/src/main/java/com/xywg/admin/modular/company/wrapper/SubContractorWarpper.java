package com.xywg.admin.modular.company.wrapper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 字典列表的包装
 *
 * @author wangcw
 * @date 2017年4月25日 18:10:31
 */
public class SubContractorWarpper extends BaseControllerWarpper {

    public SubContractorWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    	if(map.get("organizationType") != null) {
    		 if(!"".equals(map.get("organizationType").toString())){
	            map.put("organizationTypeName", ConstantFactory.me().getOrganizationTypeName(Integer.parseInt(map.get("organizationType").toString())));
	        }
    	}
    	if(map.get("businessStatus") != null && StringUtils.isNotEmpty(map.get("businessStatus").toString())) {
    		 map.put("businessStatusName", ConstantFactory.me().getBusinessStatusName(Integer.parseInt(map.get("businessStatus").toString())));
    	}
        if(map.get("status") != null) {
        	map.put("statusName",ConstantFactory.me().getStatusName(Integer.parseInt(map.get("status").toString())));
        }
    }
}
