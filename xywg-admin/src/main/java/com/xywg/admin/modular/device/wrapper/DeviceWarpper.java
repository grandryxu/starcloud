package com.xywg.admin.modular.device.wrapper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 字典列表的包装
 *
 * @author wangcw
 * @date 2017年4月25日 18:10:31
 */
public class DeviceWarpper extends BaseControllerWarpper {

    //正常
    private final String NORMAL = "1";
    //停用
    private final String STOP = "0";
    //删除
    private final String DELETE = "-1";


    public DeviceWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        if(map.get("projectCode")!=null&&ConstantFactory.me().getProjectByProjectCode(map.get("projectCode").toString())!=null) {
            map.put("projectName", ConstantFactory.me().getProjectByProjectCode(map.get("projectCode").toString()).getProjectName());
        }
        if(map.get("typeId")!=null){
            map.put("typeIdName", ConstantFactory.me().getTypeIdNameByTypeId(Integer.parseInt(map.get("typeId").toString())));
        }
        if(map.get("type")!=null) {
            map.put("typeName", ConstantFactory.me().getTypeNameByType(Integer.parseInt(map.get("type").toString())));
        }
        if(map.get("state") != null) {
    	    if(NORMAL.equals(map.get("state").toString())){
                map.put("stateName", "正常");
            }else if(STOP.equals(map.get("state").toString())){
                map.put("stateName", "停用");
            }else{
                map.put("stateName", "删除");
            }
        }
    }

}
