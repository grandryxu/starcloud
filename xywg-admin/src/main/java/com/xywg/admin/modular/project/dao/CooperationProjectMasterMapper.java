package com.xywg.admin.modular.project.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.model.vo.ProjectMasterVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 参建项目基础信息 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-23
 */
public interface CooperationProjectMasterMapper extends BaseMapper<ProjectMaster> {

    List<Map<String,Object>> list(@Param("map") Map<String, Object> map, @Param("page") Page page);

    List<Map<String,Object>> getByProjectName(@Param("projectName") String projectName);

    /**
     * 根据项目编号获取项目名称
     * @param projectCode
     * @return
     */
    ProjectMaster getProjectByProjectCode(String projectCode);

    /**
     * 获取所有参建项目 无分页
     */
    List<Map<String,Object>> getList(@Param("map") Map<String, Object> map);

    /**
     * 获取所有参建项目 有分页
     */
    List<ProjectMasterVo> getAllList(@Param("map")Map<String, Object> map, @Param("page") Page<ProjectMasterVo> page);
    
    /**
     * 获取所有需要同步项目 无分页
     * @author duanfen
     */
    List<Map<String,Object>> getSynchroList(@Param("map")Map<String, Object> map);
}
