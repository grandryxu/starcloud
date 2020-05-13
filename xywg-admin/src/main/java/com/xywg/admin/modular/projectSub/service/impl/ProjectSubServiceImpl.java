package com.xywg.admin.modular.projectSub.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.projectSub.model.ProjectSub;
import com.xywg.admin.modular.projectSub.dao.ProjectSubMapper;
import com.xywg.admin.modular.projectSub.service.IProjectSubService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.modular.system.service.IPartitionedProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目分包表 服务实现类
 * </p>
 *
 * @author cw123
 * @since 2018-07-10
 */
@Service
public class ProjectSubServiceImpl extends ServiceImpl<ProjectSubMapper, ProjectSub> implements IProjectSubService {

    @Override
    public List<ProjectSub> selectList(Map<String, Object> map, Page<ProjectSub> page) {
        return this.baseMapper.selectList(map,page);
    }

    @Override
    public ProjectSub getDefaultProjectSub() {
        return this.baseMapper.getDefaultProjectSub();
    }
}
