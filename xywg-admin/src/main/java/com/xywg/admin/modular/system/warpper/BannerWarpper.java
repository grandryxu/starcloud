package com.xywg.admin.modular.system.warpper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;

import java.util.List;
import java.util.Map;

/**
 * 用户管理的包装类
 *
 * @author wangcw
 * @date 2017年2月13日 下午10:47:03
 */
public class BannerWarpper extends BaseControllerWarpper {

    public BannerWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("linkName",(Integer) map.get("link")==0?"否":"是");
        if((Integer)map.get("type")!=null){
            int type=(Integer)map.get("type");
            if(type==1){
                map.put("typeName","企业端");
            }else if(type==2){
                map.put("typeName","工人端");
            }else{
                map.put("typeName","pc端");
            }
        }
        int chooseType=(Integer)map.get("chooseType");
        if(chooseType==1){
            map.put("chooseTypeName","轮播图");
        }else{
            map.put("chooseTypeName","名言警句");
        }

    }

}
