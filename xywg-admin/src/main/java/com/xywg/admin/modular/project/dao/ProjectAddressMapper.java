package com.xywg.admin.modular.project.dao;

import com.xywg.admin.modular.project.model.ProjectAddress;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.project.model.vo.ProjectAddressVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 项目地址 Mapper 接口
 * </p>
 *
 * @author caiwei123
 * @since 2018-08-10
 */
public interface ProjectAddressMapper extends BaseMapper<ProjectAddress> {

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
     * @param deptId
     * @param toggleDeptId
     */
    List<ProjectAddressVo> getProjectAddressByToggleDeptId(@Param("toggleDeptId") String toggleDeptId,@Param("idList") List<Long> idList);


    ProjectAddress getProjectAddressByProjectCode(@Param("projectCode") String projectCode);

    List<ProjectAddress> queryProjectAddressById(@Param("organizationCode") String organizationCode, @Param("id") Long id);
}
