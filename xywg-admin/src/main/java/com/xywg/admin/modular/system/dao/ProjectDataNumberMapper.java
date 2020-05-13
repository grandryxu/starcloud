package com.xywg.admin.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.system.model.ProjectDataNumber;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProjectDataNumberMapper extends BaseMapper<ProjectDataNumber> {
    /**
     * 查询列表
     *
     * @param page
     * @param map
     * @return
     */
    List<ProjectDataNumber> selectProjectDataNumberList(@Param("page") Page<ProjectDataNumber> page, @Param("map") Map<String, Object> map);

    /**
     * 根据id查询信息
     *
     * @param id
     * @return
     */
    ProjectDataNumber selectProjectDataNumberById(@Param("id") Long id);

    /**
     * 批量删除
     *
     * @param idList
     * @return
     */
    Integer delProjectDataNumbers(@Param("map") Map<String, Object> map);
}
