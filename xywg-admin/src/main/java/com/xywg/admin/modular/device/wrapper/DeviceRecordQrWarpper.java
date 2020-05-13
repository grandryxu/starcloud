package com.xywg.admin.modular.device.wrapper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.Map;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/6/24 14:17
 */
public class DeviceRecordQrWarpper extends BaseControllerWarpper{
    public DeviceRecordQrWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("stateName", ConstantFactory.me().getStateName((Integer) map.get("state")));
        map.put("softStatusName", (Integer) map.get("softStatus") == 0 ? "升级完成" : "正在升级中");
    }
}
