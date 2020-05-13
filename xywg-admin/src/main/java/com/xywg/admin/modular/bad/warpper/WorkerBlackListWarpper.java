package com.xywg.admin.modular.bad.warpper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

public class WorkerBlackListWarpper extends BaseControllerWarpper  {

	public WorkerBlackListWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("idCardTypeName", ConstantFactory.me().getDictsByName("人员证件类型",(Integer) map.get("idCardType")));
       
    }
}
