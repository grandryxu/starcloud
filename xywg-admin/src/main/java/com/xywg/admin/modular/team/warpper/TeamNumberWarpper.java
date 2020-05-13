package com.xywg.admin.modular.team.warpper;

import com.xywg.admin.core.base.warpper.BaseControllerWarpper;
import com.xywg.admin.core.common.constant.factory.ConstantFactory;
import com.xywg.admin.modular.team.service.impl.TeamMasterServiceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/7/26 16:33
 */
public class TeamNumberWarpper extends BaseControllerWarpper {
	private static Logger log = LoggerFactory.getLogger(TeamNumberWarpper.class);
    public TeamNumberWarpper(List<Map<String, Object>> list) {
        super(list);
    }
    @Override
    protected void warpTheMap(Map<String, Object> map) {
        map.put("workTypeName", ConstantFactory.me().getDictsByName("工种字典数据",Integer.parseInt(map.get("workType").toString())));
        map.put("idCardTypeName", ConstantFactory.me().getDictsByName("人员证件类型",(Integer) map.get("idCardType")));
        map.put("genderName", ConstantFactory.me().getDictsByName("性别", (Integer) map.get("gender")));
        try{
            map.put("teamName", ConstantFactory.me().getTeamByTeamSysNo((Integer) map.get("teamSysNo")).getTeamName());
        }catch (Exception e){
        	log.error(e.getMessage());
        }
    }
}
