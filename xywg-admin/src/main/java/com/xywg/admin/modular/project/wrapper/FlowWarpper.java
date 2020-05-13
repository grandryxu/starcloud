package com.xywg.admin.modular.project.wrapper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;

import java.util.Map;

/**
 * @Description:
 * @Author
 * @Date Create in 2018/5/24 10:02
 */
public class FlowWarpper extends BaseControllerWarpper{

    public FlowWarpper(Object list){
        super(list);
    }
    @Override
    public void warpTheMap(Map<String,Object> map){
        /*map.put("organizationTypeName", ConstantFactory.me().getOrganizationTypeName(Integer.parseInt(map.get("organizationType").toString())));
        map.put("businessStatusName", ConstantFactory.me().getBusinessStatusName(Integer.parseInt(map.get("businessStatus").toString())));*/
//       map.put("projectStatusName", ConstantFactory.me().projectStatusName(Integer.parseInt(map.get("projectStatus").toString())));
    }
}
