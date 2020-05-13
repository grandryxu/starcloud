package com.xywg.admin.modular.company.wrapper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

/**
 * 的包装类
 *
 * @author yuanyang
 *
 */
public class PersonalCertificationWarpper extends BaseControllerWarpper {

    public PersonalCertificationWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("idCardTypeName", ConstantFactory.me().getDictsByName("人员证件类型",(Integer) map.get("idCardType")));
        map.put("certificationLevelTypeName", ConstantFactory.me().getDictsByName("证书等级",(Integer) map.get("certificationLevelType")));
        int certificationTypeCode= Integer.parseInt(String.valueOf(map.get("certificationTypeCode")));
        map.put("certificationTypeCodeName", ConstantFactory.me().getDictsByName("证书类型",certificationTypeCode));
        int status=(Integer) map.get("status");
        if(status==1){
            map.put("statusName","有效");
        }else if(status==2){
            map.put("statusName","注销");
        }else{
            map.put("statusName","过期");
        }
        if (certificationTypeCode == 14001) {
            map.put("jobTypeName", ConstantFactory.me().getDictsByName("安管证书专业",(Integer) map.get("jobType")));
        } else if (certificationTypeCode == 14002) {
            map.put("jobTypeName", ConstantFactory.me().getDictsByName("岗位证书专业",(Integer) map.get("jobType")));
        } else if (certificationTypeCode == 14003) {
            map.put("jobTypeName", ConstantFactory.me().getDictsByName("特种作业证书",(Integer) map.get("jobType")));
        } else if (certificationTypeCode == 14004) {
            map.put("jobTypeName", ConstantFactory.me().getDictsByName("职称证书专业",(Integer) map.get("jobType")));
        } else if (certificationTypeCode == 14005) {
            map.put("jobTypeName", ConstantFactory.me().getDictsByName("执业注册证书专业",(Integer) map.get("jobType")));
        } else if (certificationTypeCode == 14006) {
            map.put("jobTypeName", ConstantFactory.me().getDictsByName("技术工人资格证书",(Integer) map.get("jobType")));
        } else {
            map.put("jobTypeName", ConstantFactory.me().getDictsByName("职业技能证书",(Integer) map.get("jobType")));
        }
    }
}
