package com.xywg.admin.modular.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.system.model.ProjectDataNumber;

import java.util.List;
import java.util.Map;

public interface IProjectDataNumberService extends IService<ProjectDataNumber> {
    List<ProjectDataNumber> selectList(Page page, Map map);

    boolean delByIDList(Map<String, Object> map);

    ProjectDataNumber getProjectDataNumberByID(long id);

    List<ProjectDataNumber> getProjectDataNumberByColumns(Map<String, Object> map);

    boolean insert(ProjectDataNumber obj);

    boolean update(ProjectDataNumber obj);
}
