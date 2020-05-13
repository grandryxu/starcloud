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
public class DeviceExceptionWarpper extends BaseControllerWarpper {

    public DeviceExceptionWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("projectName", ConstantFactory.me().getProjectByProjectCode(map.get("projectCode").toString()).getProjectName());
        map.put("idCardTypeName", ConstantFactory.me().getDictsByName("人员证件类型", (Integer) map.get("idCardType")));
        //异常类型
        if (map.get("exceptionType") != null) {
            if (map.get("exceptionType").equals("1")) {
                map.put("exceptionTypeName", "注册人不在项目中");
            } else if (map.get("exceptionType").equals("2")) {
                map.put("exceptionTypeName", "考勤人不在项目中");
            } else if (map.get("exceptionType").equals("3")) {
                map.put("exceptionTypeName", "禁用时考勤数据");
            }
        }
        if (map.get("source") != null) {
            map.put("sourceName", ConstantFactory.me().getDictsByName("考勤类型", (Integer) map.get("source")));
        }
        if (map.get("type") != null) {
            map.put("typeName", (Integer) map.get("type") == 1 ? "正常" : "补考勤");
        }
        if (map.get("deviceType") != null) {
            map.put("deviceTypeName", (Integer) map.get("deviceType") == 3 ? "上班" : "下班");
        }
    }

}
