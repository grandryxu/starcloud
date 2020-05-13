package com.xywg.admin.modular.project.wrapper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.Map;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/6/20 20:19
 */
public class ProjectTrainingWrapper extends BaseControllerWarpper {
    public ProjectTrainingWrapper(Object list){
        super(list);
    }

    @Override
    public void warpTheMap(Map<String,Object> map){
        map.put("projectName", ConstantFactory.me().getProjectByProjectCode(map.get("projectCode").toString()).getProjectName());
        map.put("projectTypeName", ConstantFactory.me().getProjectType(Integer.parseInt((String) map.get("trainingTypeCode"))));
    }
}
