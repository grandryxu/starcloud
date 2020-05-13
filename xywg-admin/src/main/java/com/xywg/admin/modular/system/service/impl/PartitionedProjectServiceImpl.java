package com.xywg.admin.modular.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.modular.system.dao.PartitionedProjectMapper;
import com.xywg.admin.modular.system.model.PartitionedProject;
import com.xywg.admin.modular.system.service.IPartitionedProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-07-18
 */
@Service
public class PartitionedProjectServiceImpl extends ServiceImpl<PartitionedProjectMapper, PartitionedProject> implements IPartitionedProjectService {

    @Override
    public List<PartitionedProject> getChildrenByNum(String num) {
        return this.baseMapper.getChildrenByNum(num);
    }
}
