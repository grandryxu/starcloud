package com.xywg.admin.modular.wages.wrapper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;
import com.xywg.admin.modular.wages.service.impl.PayRollDetailServiceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/6/11 21:24
 */
public class AccountWarpper extends BaseControllerWarpper{
    public AccountWarpper(List<Map<String, Object>> list){super(list);}
    private static  Logger log = LoggerFactory.getLogger(AccountWarpper.class);
    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("projectName", ConstantFactory.me().getProjectByProjectCode((String) map.get("projectCode")).getProjectName());
        try{
            map.put("teamName", ConstantFactory.me().getTeamByTeamSysNo((Integer) map.get("teamSysNo")).getTeamName());
        }catch (Exception e){
        	log.error(e.getMessage());
        }
    }
}
