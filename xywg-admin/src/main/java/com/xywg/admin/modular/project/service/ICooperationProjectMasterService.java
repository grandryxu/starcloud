package com.xywg.admin.modular.project.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.model.vo.ProjectMasterVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目基础信息 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-23
 */
public interface ICooperationProjectMasterService extends IService<ProjectMaster> {
    List<Map<String,Object>> selectList(Map<String, Object> map, Page page);

    List<Map<String,Object>> getByProjectName(String projectName);

    public ProjectMaster getProjectByProjectCode(String projectCode);

    /**
     * 获取所有参建项目 无分页
     */
    List<Map<String,Object>> getList(Map<String, Object> map);
    
    /**
     * 获取所有参建项目 有分页
     */
    List<ProjectMasterVo> getAllList(Map<String, Object> map, Page<ProjectMasterVo> page);
    
    /**
     * 获取所有需要同步项目 无分页
     * @author duanfen
     */
    List<Map<String,Object>> getSynchroList(Map<String, Object> map);

}
