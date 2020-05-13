package com.xywg.admin.modular.wages.wrapper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/6/21 21:31
 */
public class AccountDetailWarpper extends BaseControllerWarpper {
    public AccountDetailWarpper(List<Map<String, Object>> list){super(list);}

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("idCardTypeName", ConstantFactory.me().getIdcardTypeName((Integer) map.get("idCardType")));
        map.put("workTypeName",ConstantFactory.me().getDictsByName("工种字典数据",Integer.parseInt(map.get("workerType").toString())));
    }
}
