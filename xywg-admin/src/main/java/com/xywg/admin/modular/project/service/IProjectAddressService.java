package com.xywg.admin.modular.project.service;

import com.xywg.admin.modular.project.model.ProjectAddress;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.project.model.vo.ProjectAddressVo;

import java.util.List;

/**
 * <p>
 * 项目地址 服务类
 * </p>
 *
 * @author caiwei123
 * @since 2018-08-10
 */
public interface IProjectAddressService extends IService<ProjectAddress> {

    /**
     * 根据项目编号获取地址
     * @param projectCode
     * @return
     */
    List<ProjectAddress> selectAddressByProjectCode(String projectCode);

    /**
     * 移除项目下的所有地址
     * @param projectCode
     * @return
     */
    Boolean removeAddressWithProjectCode(String projectCode);

    /**
     * 获取切换公司下的所有的项目地址（每个项目一个）
     * @return List<ProjectAddress>
     * @author 蔡伟
     */
    List<ProjectAddressVo> getProjectAddressByToggleDeptId();

    List<ProjectAddress> queryProjectAddressById(String organizationCode, Long id);
}
