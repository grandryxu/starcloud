package com.xywg.attendance.modular.system.service.impl;

import com.xywg.attendance.modular.system.model.Project;
import com.xywg.attendance.modular.system.dao.ProjectMapper;
import com.xywg.attendance.modular.system.service.IProjectService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author z
 * @since 2019-02-25
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

}
