package com.xywg.admin.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.core.node.ZTreeNode;
import com.xywg.admin.modular.system.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 角色表 Mapper 接口
 * </p>
 *
 * @author wangcw
 * @since 2017-07-11
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据条件查询角色列表
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Map<String, Object>> selectRoles(@Param("condition") String condition,@Param("list")List<Integer> iDepts);

    /**
     * 删除某个角色的所有权限
     *
     * @param roleId 角色id
     * @return
     * @date 2017年2月13日 下午7:57:51
     */
    int deleteRolesById(@Param("roleId") Integer roleId);

    /**
     * 获取角色列表树
     *
     * @return
     * @date 2017年2月18日 上午10:32:04
     */
    List<ZTreeNode> roleTreeList(List<Integer> iDepts);

    /**
     * 获取角色列表树
     *
     * @return
     * @date 2017年2月18日 上午10:32:04
     */
    List<ZTreeNode> roleTreeListByRoleId(@Param("roleId")String[] roleId,@Param("iDepts")List<Integer> iDepts);

    /**
     *
     * @param roleId
     * @return
     */
    List<Role> findChildRolesByPid(Integer roleId);
}