package com.xywg.admin.modular.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.system.dao.TimeSetMapper;
import com.xywg.admin.modular.system.model.TimeSet;
import com.xywg.admin.modular.system.model.TimeSetVO;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.system.service.ITimeSetService;
import com.xywg.admin.modular.system.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 时间设置表 服务实现类
 * </p>
 *
 * @author shily
 * @since 2018-06-22
 */
@Service
public class TimeSetServiceImpl extends ServiceImpl<TimeSetMapper, TimeSet> implements ITimeSetService {
    @Resource
    private IDeptService deptService;
    @Autowired
    private AccountProjectService accountProjectService;

    @Override
    public List<Map<String, Object>> getProjects() {
        List<String> list = deptService.getUserDeptAndSubdivisionOrganizationCode();
        return this.baseMapper.getProjects(list);
    }

    @Override
    public TimeSetVO getTime(TimeSet timeSet) {
        String type=timeSet.getType().substring(0, 2);
        if(type.equals("工人")){
            type="worker";
        }else{
            type="manager";
        }
        timeSet.setType(type);
        return this.baseMapper.getTime(timeSet);
    }

    @Override
    public int enable(Long timeSetId) {
        return this.baseMapper.enable(timeSetId);
    }

    @Override
    public int disable(Map<String,Object> map) {
        return this.baseMapper.disable(map);
    }

    @Override
    public TimeSetVO selectTimeSetById(Integer timeSetId) {
        TimeSetVO timeSetVO=this.baseMapper.selectTimeSetById(timeSetId);
        if(timeSetVO.getType().equals("worker_am")){
            timeSetVO.setType("工人上午打卡区间");
        }else if(timeSetVO.getType().equals("worker_pm")){
            timeSetVO.setType("工人下午打卡区间");
        }else if(timeSetVO.getType().equals("manager_am")){
            timeSetVO.setType("管理员上午打卡区间");
        }else if(timeSetVO.getType().equals("manager_pm")){
            timeSetVO.setType("管理员下午打卡区间");
        }
        return timeSetVO;
    }

    @Override
    public List<Map<String, Object>> selectTimeSetList(Page<TimeSetVO> page, Map<String, Object> map) {
        map.put("depts", deptService.getUserDeptAndSubdivisionOrganizationCode());
        map.put("projectCodes", accountProjectService.getProjectCodes());
        return this.baseMapper.selectTimeSetList(page, map);
    }

    @Override
    public void updateTimeSet(TimeSet timeSet) {
        timeSet.setUpdateUser(ShiroKit.getUser().getName());
        baseMapper.updateTimeSet(timeSet);
    }

    /**
     * 设置项目的上下班打卡时间(新增项目时调用)
     *
     * @param projectCode
     * @return
     */
    @Override
    public int addTimeSet(String projectCode,String createUser) {
        return baseMapper.addTimeSet(projectCode,createUser);
    }
}
