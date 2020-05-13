package com.xywg.admin.modular.project.wrapper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.Map;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/6/25 9:43
 */
public class ProjectTrainWorkerWarpper extends BaseControllerWarpper{
    public ProjectTrainWorkerWarpper(Object list){
        super(list);
    }

    @Override
    public void warpTheMap(Map<String,Object> map){
        map.put("idCardTypeName", ConstantFactory.me().getIdcardTypeName(Integer.parseInt(map.get("idCardType")+"")));
        map.put("workKingName", ConstantFactory.me().getWorkKindNameByNum(Integer.parseInt((String) map.get("workTypeCode"))));
    }
}
