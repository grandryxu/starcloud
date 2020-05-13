package com.xywg.admin.modular.project.wrapper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.Map;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/6/25 10:28
 */
public class WorkerTrainRecordWarpper extends BaseControllerWarpper{
    public WorkerTrainRecordWarpper(Object list){
        super(list);
    }

    public void warpTheMap(Map<String,Object> map){
        map.put("trainingTypeCodeName", ConstantFactory.me().gettrainingTypeCodeName(Integer.parseInt((String) map.get("trainingTypeCode"))));
    }
}
