package com.xywg.admin.modular.projectSubContractor.projectSubContractorWrapper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.Map;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/5/25 9:17
 */
public class ProjectSubContractorWrapper extends BaseControllerWarpper{

    public ProjectSubContractorWrapper(Object list){
        super(list);
    }
    @Override
    public void warpTheMap(Map<String,Object> map){
        map.put("contractorTypeName", ConstantFactory.me().getContractorTypeName(Integer.parseInt(map.get("contractorType").toString())));
        map.put("statusName",ConstantFactory.me().getStatusName(Integer.parseInt(map.get("status").toString())));
        if(map.get("pmIdcardType") != null){
        }
    }
}
