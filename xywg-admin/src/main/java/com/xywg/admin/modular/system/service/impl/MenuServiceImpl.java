package com.xywg.admin.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.node.MenuNode;
import com.xywg.admin.core.node.ZTreeNode;
import com.xywg.admin.modular.system.dao.MenuMapper;
import com.xywg.admin.modular.system.model.Menu;
import com.xywg.admin.modular.system.service.IMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 菜单服务
 *
 * @author wangcw
 * @date 2017-05-05 22:20
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public void delMenu(Long menuId) {

        //删除菜单
        this.menuMapper.deleteById(menuId);

        //删除关联的relation
        this.menuMapper.deleteRelationByMenu(menuId);
    }

    @Override
    public void delMenuContainSubMenus(Long menuId) {

        Menu menu = menuMapper.selectById(menuId);

        //删除当前菜单
        delMenu(menuId);

        //删除所有子菜单
        Wrapper<Menu> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("pcodes", "%[" + menu.getCode() + "]%");
        List<Menu> menus = menuMapper.selectList(wrapper);
        for (Menu temp : menus) {
            delMenu(temp.getId());
        }
    }

    @Override
    public List<Map<String, Object>> selectMenus(String condition, String level) {
        return this.baseMapper.selectMenus(condition, level);
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Integer roleId) {
        return this.baseMapper.getMenuIdsByRoleId(roleId);
    }

    /**
     * 根据角色查询去重后的菜单id
     *
     * @param roleList
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    @Override
    public List<Long> getMenuIdsByRoleList(List<Integer> roleList) {
        return this.baseMapper.getMenuIdsByRoleList(roleList);
    }

    /**
     * 管理员获取所有菜单列表id
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    @Override
    public List<Long> getMenuIdsList() {
        return this.baseMapper.getMenuIdsList();
    }



    @Override
    public List<ZTreeNode> menuTreeList() {
        return this.baseMapper.menuTreeList();
    }

    @Override
    public List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds,List<Long>   loginUserMenuIds) {
        return this.baseMapper.menuTreeListByMenuIds(menuIds,loginUserMenuIds);
    }

    @Override
    public int deleteRelationByMenu(Long menuId) {
        return this.baseMapper.deleteRelationByMenu(menuId);
    }

    @Override
    public List<String> getResUrlsByRoleId(Integer roleId) {
        return this.baseMapper.getResUrlsByRoleId(roleId);
    }

    @Override
    public List<MenuNode> getMenusByRoleIds(List<Integer> roleIds, String projectCode, Map<String, String> menuMap) {
        return this.baseMapper.getMenusByRoleIds(roleIds,projectCode, menuMap);
    }
}
