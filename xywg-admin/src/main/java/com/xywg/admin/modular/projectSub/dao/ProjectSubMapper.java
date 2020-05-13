package com.xywg.admin.modular.projectSub.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.projectSub.model.ProjectSub;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目分包表 Mapper 接口
 * </p>
 *
 * @author cw123
 * @since 2018-07-10
 */
public interface ProjectSubMapper extends BaseMapper<ProjectSub> {

    /**
     * 根据项目编号查询对应的劳务分包
     * @param map
     * @param page
     * @return
     */
    List<ProjectSub> selectList(@Param("map") Map<String, Object> map, @Param("page") Page<ProjectSub> page);

    /**
     * 获取一条默认的劳务分包信息
     * @return
     */
    ProjectSub getDefaultProjectSub();
}
