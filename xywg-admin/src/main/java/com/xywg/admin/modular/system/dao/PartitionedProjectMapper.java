package com.xywg.admin.modular.system.dao;

import com.xywg.admin.modular.system.model.PartitionedProject;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-07-18
 */
public interface PartitionedProjectMapper extends BaseMapper<PartitionedProject> {

    /**
     * 根据名称获取其子分布工程
     * @param num
     * @return List<PartitionedProject>
     * @author 蔡伟
     */
    List<PartitionedProject> getChildrenByNum(String num);
}
