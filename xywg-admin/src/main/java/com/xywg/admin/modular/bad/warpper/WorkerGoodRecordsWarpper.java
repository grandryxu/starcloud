package com.xywg.admin.modular.bad.warpper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

public class WorkerGoodRecordsWarpper extends BaseControllerWarpper  {

	public WorkerGoodRecordsWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    		map.put("idCardTypeName", ConstantFactory.me().getDictsByName("人员证件类型",(Integer) map.get("idCardType")));
        	map.put("goodRecordLevelTypeName", ConstantFactory.me().getDictsByName("工人奖励级别",(Integer) map.get("goodRecordLevelType")));
        	map.put("goodRecordType", ConstantFactory.me().getDictsByName("工人奖励类别",Integer.valueOf(map.get("goodRecordTypeCode").toString())));
    }
}
