package com.xywg.admin.modular.company.wrapper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.Map;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/5/27 19:17
 */
public class SubContractorCertificationWarpper extends BaseControllerWarpper{
    public SubContractorCertificationWarpper(Object list){super(list);};
    @Override
    public void warpTheMap(Map<String, Object> map) {
//        map.put("certificationTypeName", ConstantFactory.me().getCertificationTypeName(Integer.parseInt(map.get("certificationType").toString())));
        map.put("certificationStatusName", ConstantFactory.me().getCertificationStatusName(Integer.parseInt(map.get("certificationStatus").toString())));
        map.put("qualificationStatusName",ConstantFactory.me().getQualificationStatusName(Integer.parseInt(map.get("qualificationStatus").toString())));
        map.put("certificationTypeName", ConstantFactory.me().getDictsByName("证书类型", Integer.parseInt( map.get("certificationType").toString())));

        String validDate="";
        validDate=map.get("validBeginDate").toString().substring(0,10)+"-"+map.get("validEndDate").toString().substring(0,10);
        map.put("validDate",validDate);
    }
}
