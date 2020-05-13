package com.xywg.admin.modular.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.node.ZTreeNode;
import com.xywg.admin.core.util.Convert;
import com.xywg.admin.modular.system.dao.RelationMapper;
import com.xywg.admin.modular.system.dao.RoleMapper;
import com.xywg.admin.modular.system.model.Relation;
import com.xywg.admin.modular.system.model.Role;
import com.xywg.admin.modular.system.service.IRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RelationMapper relationMapper;

    @Override
    @Transactional(readOnly = false)
    public void setAuthority(Integer roleId, String ids) {

        // 删除该角色所有的权限
        this.roleMapper.deleteRolesById(roleId);

        // 添加新的权限
        for (Long id : Convert.toLongArray(true, Convert.toStrArray(",", ids))) {
            Relation relation = new Relation();
            relation.setRoleid(roleId);
            relation.setMenuid(id);
            this.relationMapper.insert(relation);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void delRoleById(Integer roleId) {
        //删除角色
        this.roleMapper.deleteById(roleId);

        // 删除该角色所有的权限
        this.roleMapper.deleteRolesById(roleId);
    }

    @Override
    public List<Map<String, Object>> selectRoles(String condition,List<Integer> iDepts) {
        return this.baseMapper.selectRoles(condition,iDepts);
    }

    @Override
    public int deleteRolesById(Integer roleId) {
        return this.baseMapper.deleteRolesById(roleId);
    }

    @Override
    public List<ZTreeNode> roleTreeList(List<Integer>     iDepts) {
        return this.baseMapper.roleTreeList(iDepts);
    }

    @Override
    public List<ZTreeNode> roleTreeListByRoleId(String[] roleId,List<Integer>     iDepts) {
        return this.baseMapper.roleTreeListByRoleId(roleId,iDepts);
    }

    /**
     * 获取角色下的子角色
     * @param roleId
     * @return
     */
    @Override
    public List<Role> findChildRolesByPid(Integer roleId) {
        return this.baseMapper.findChildRolesByPid(roleId);
    }

}
