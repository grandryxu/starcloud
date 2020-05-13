package com.xywg.admin.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.system.model.PartitionedProject;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-07-18
 */
public interface IPartitionedProjectService extends IService<PartitionedProject> {

    /**
     * 根据名称获取其子分布工程
     * @param num
     * @return List<PartitionedProject>
     * @author 蔡伟
     */
    List<PartitionedProject> getChildrenByNum(String num);
}
