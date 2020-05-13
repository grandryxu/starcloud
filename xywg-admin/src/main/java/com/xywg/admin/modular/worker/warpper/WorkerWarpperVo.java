package com.xywg.admin.modular.worker.warpper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/6/22 10:47
 */
public class WorkerWarpperVo  extends BaseControllerWarpper{
    public WorkerWarpperVo(List<Map<String, Object>> list) {

        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("idCardTypeName", ConstantFactory.me().getDictsByName("人员证件类型",(Integer) map.get("idCardType")));
        map.put("workTypeName",ConstantFactory.me().getDictsByName("工种字典数据",Integer.parseInt(map.get("workType").toString())));
    }
}
