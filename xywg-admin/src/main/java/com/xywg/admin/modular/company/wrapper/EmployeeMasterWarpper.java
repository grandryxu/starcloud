package com.xywg.admin.modular.company.wrapper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

/**
 * 的包装类
 *
 * @author yuanyang
 */
public class EmployeeMasterWarpper extends BaseControllerWarpper {

    public EmployeeMasterWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("genderName", ConstantFactory.me().getDictsByName("性别", (Integer) map.get("gender")));
        map.put("nationName", ConstantFactory.me().getDictsByName("民族", Integer.parseInt(String.valueOf(map.get("nation")))));
        map.put("idCardTypeName", ConstantFactory.me().getDictsByName("人员证件类型", (Integer) map.get("idCardType")));
        map.put("culturelevelTypeName", ConstantFactory.me().getDictsByName("文化程度", (Integer) map.get("cultureLevelType")));
        map.put("professionalLevelName", ConstantFactory.me().getDictsByName("职称等级", (Integer) map.get("professionalLevel")));
        int professionalLevel = (Integer) map.get("professionalLevel");
        int professionalType = (Integer) map.get("professionalType");
        if (professionalLevel == 1) {
            map.put("professionalTypeName", ConstantFactory.me().getDictsByName("初级职称人员类别", professionalType));
        } else if (professionalLevel == 2) {
            map.put("professionalTypeName", ConstantFactory.me().getDictsByName("中级职称人员类别", professionalType));
        } else {
            map.put("professionalTypeName", ConstantFactory.me().getDictsByName("高级职称人员类别", professionalType));
        }
        if (map.get("jobStatus") != "" && map.get("jobStatus") != null) {
            int jobStatus = (Integer) map.get("jobStatus");
            if (jobStatus == 0) {
                map.put("jobStatusName", "离职");
            } else if (jobStatus == 1) {
                map.put("jobStatusName", "在职");
            } else if (jobStatus == 2) {
                map.put("jobStatusName", "退休");
            } else {
                map.put("jobStatusName", "");
            }
        }
        if (map.get("isAuth") != "" && map.get("isAuth") != null) {
            map.put("isAuthName", (Integer) map.get("isAuth") == 1 ? "是" : "否");
        }
        if (map.get("isFace") != "" && map.get("isFace") != null) {
            map.put("isFaceName", (Integer) map.get("isFace") == 1 ? "是" : "否");
        }
    }
}
