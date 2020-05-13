package com.xywg.admin.modular.project.wrapper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.Map;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/6/25 21:00
 */
public class ProjectDetailWarpper extends BaseControllerWarpper {
    public ProjectDetailWarpper(Object list){
        super(list);
    }

    @Override
    public void warpTheMap(Map<String,Object> map){
        map.put("idCardTypeName", ConstantFactory.me().getIdcardTypeName((Integer) map.get("idCardType")));
        map.put("workKingName", ConstantFactory.me().getWorkKindNameByNum(Integer.parseInt((String) map.get("workTypeCode"))));
    }
}
