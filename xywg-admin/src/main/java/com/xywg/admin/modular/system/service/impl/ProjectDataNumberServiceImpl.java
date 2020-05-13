package com.xywg.admin.modular.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.modular.system.dao.ProjectDataNumberMapper;
import com.xywg.admin.modular.system.model.ProjectDataNumber;
import com.xywg.admin.modular.system.service.IProjectDataNumberService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProjectDataNumberServiceImpl extends ServiceImpl<ProjectDataNumberMapper, ProjectDataNumber> implements IProjectDataNumberService {

    @Override
    public List<ProjectDataNumber> selectList(Page page, Map map) {
        return this.baseMapper.selectProjectDataNumberList(page, map);
    }

    @Override
    public boolean delByIDList(Map<String, Object> map) {
        return this.baseMapper.delProjectDataNumbers(map) > 0;
    }

    @Override
    public ProjectDataNumber getProjectDataNumberByID(long id) {
        return this.baseMapper.selectProjectDataNumberById(id);
    }

    @Override
    public List<ProjectDataNumber> getProjectDataNumberByColumns(Map<String, Object> map) {
        return this.baseMapper.selectByMap(map);
    }

    @Override
    public boolean insert(ProjectDataNumber obj) {
        return this.baseMapper.insert(obj) == 1;
    }

    @Override
    public boolean update(ProjectDataNumber obj) {
        return this.baseMapper.updateById(obj) == 1;
    }


}
