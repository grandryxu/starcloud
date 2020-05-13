package com.xywg.admin.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.core.node.MenuNode;
import com.xywg.admin.core.node.ZTreeNode;
import com.xywg.admin.modular.system.model.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author wangcw
 * @since 2017-07-11
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据条件查询菜单
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Map<String, Object>> selectMenus(@Param("condition") String condition, @Param("level") String level);

    /**
     * 根据条件查询菜单
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Long> getMenuIdsByRoleId(@Param("roleId") Integer roleId);


    /**
     * 根据角色查询去重后的菜单id
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Long> getMenuIdsByRoleList(List<Integer> roleList);


    /**
     * 管理员获取所有菜单列表id
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Long> getMenuIdsList();



    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    List<ZTreeNode> menuTreeList();

    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    List<ZTreeNode> menuTreeListByMenuIds(@Param("menuIds") List<Long> menuIds, @Param("loginUserMenuIds") List<Long> loginUserMenuIds);

    /**
     * 删除menu关联的relation
     *
     * @param menuId
     * @return
     * @date 2017年2月19日 下午4:10:59
     */
    int deleteRelationByMenu(Long menuId);

    /**
     * 获取资源url通过角色id
     *
     * @param roleId
     * @return
     * @date 2017年2月19日 下午7:12:38
     */
    List<String> getResUrlsByRoleId(Integer roleId);

    /**
     * 根据角色获取菜单
     *
     * @param roleIds
     * @param projectCodes
     * @return
     * @date 2017年2月19日 下午10:35:40
     */
    List<MenuNode> getMenusByRoleIds(@Param("roleIds") List<Integer> roleIds, @Param("projectCode") String projectCodes,@Param("menuMap") Map<String,String> menuMap);
}