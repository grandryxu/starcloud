package com.xywg.admin.modular.project.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.project.dao.CooperationProjectMasterMapper;
import com.xywg.admin.modular.project.dao.ProjectMasterMapper;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.model.vo.ProjectMasterVo;
import com.xywg.admin.modular.project.service.ICooperationProjectMasterService;
import com.xywg.admin.modular.projectSubContractor.dao.ProjectSubContractorMapper;
import com.xywg.admin.modular.projectSubContractor.model.ProjectSubContractor;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目基础信息 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-23
 */
@Service
public class CooperationProjectMasterServiceImpl extends ServiceImpl<ProjectMasterMapper, ProjectMaster> implements ICooperationProjectMasterService {
    @Resource
    private CooperationProjectMasterMapper cooperationProjectMasterMapper;
    @Resource
    private ProjectSubContractorMapper projectSubContractorMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private AccountProjectService accountProjectService;

    @Override
    public List<Map<String, Object>> selectList(Map<String, Object> map, Page page) {
        map.put("deptId", ShiroKit.getUser().getDeptId());
        map.put("projectCodes",accountProjectService.getProjectCodes());
        return cooperationProjectMasterMapper.list(map, page);
    }

    @Override
    public List<Map<String, Object>> getByProjectName(String projectName) {
        return cooperationProjectMasterMapper.getByProjectName(projectName);
    }

    @Override
    public boolean insert(ProjectMaster projectMaster) {
        retBool(this.baseMapper.insert(projectMaster));
        ProjectSubContractor projectSubContractor = new ProjectSubContractor();
        projectSubContractor.setProjectCode(projectMaster.getProjectCode());
        projectSubContractor.setOrganizationCode(projectMaster.getContractorOrgCode());
        projectSubContractor.setContractorType(16);
        projectSubContractorMapper.insert(projectSubContractor);
        return false;
    }

    /**
     * 根据项目编号获取项目
     *
     * @param projectCode
     * @return
     */
    @Override
    public ProjectMaster getProjectByProjectCode(String projectCode) {
        return cooperationProjectMasterMapper.getProjectByProjectCode(projectCode);
    }

    @Override
    public List<Map<String, Object>> getList(Map<String, Object> map) {
        map.put("deptId", ShiroKit.getUser().getDeptId());
        map.put("projectCodes", accountProjectService.getProjectCodes());
        return cooperationProjectMasterMapper.getList(map);
    }

    @Override
    public List<ProjectMasterVo> getAllList(Map<String, Object> map, Page<ProjectMasterVo> page) {
        map.put("isEnterprise",userService.getByAccount(map.get("account").toString()).getIsEnterprise());
        return cooperationProjectMasterMapper.getAllList(map, page);
    }

	@Override
	public List<Map<String, Object>> getSynchroList(Map<String, Object> map) {
        return cooperationProjectMasterMapper.getSynchroList(map);
	}

}
