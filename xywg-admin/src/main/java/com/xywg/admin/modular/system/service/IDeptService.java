package com.xywg.admin.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.core.node.ZTreeNode;
import com.xywg.admin.modular.system.model.Dept;
import com.xywg.admin.modular.system.model.OrganizationalStructure;

import java.util.List;
import java.util.Map;

public interface IDeptService extends IService<Dept> {

    /**
     * 删除部门
     */
    void deleteDept(Integer deptId);

    /**
     * 获取ztree的节点列表
     */
    List<ZTreeNode> tree();

    /**
     * 获取所有部门列表
     */
    List<Map<String, Object>> list( String condition, String account);

    /**

     * 获取所有部门列表(未删除)
     * @param account
     * @return
     * @author yuanyang
     */
    List<Map<String, Object>> getList(String account);


    /**
     * 查询当前用户所在部门以及所有子部门
     * @return  部门以及所有子部门 id 集合
     */
    List<Integer> getUserDeptAndSubdivision();

    /**
     * 查詢当前用户所在部门以及子部门的社会信用统一代码集合  PC端
     * @return
     */
    List<String>  getUserDeptAndSubdivisionOrganizationCode();


    /**
     * 查詢当前用户所在部门以及子部门的社会信用统一代码集合  移动端
     * @return
     */
    List<String>  getAPPUserDeptAndSubdivisionOrganizationCode(String organizationCode);

    /**
     * 根据部门id查询所有子部门的社会信用统一代码集合
     * @param deptId
     * @return 返回当前部门以及所有子部门的社会信用统一代码集合
     */
    List<String>  getOrganizationCodeByDeptId(Integer deptId);

    /**
     * 查询当前企业组织
     * @param userId
     * @return
     */
    OrganizationalStructure getOrganizationalStructureByOrganizationCode(String userId);

    /**
     * 获取公司的组织架构
     * @param organizationCode  企业组织结构代码
     */
    OrganizationalStructure getOrganizationalStructure(String organizationCode);

    Dept getDept(Long id);

    List<OrganizationalStructure> getDeptChilds(Long pId);

    /**
     * 获取部门的tree
     * @param deptId
     */
    List<ZTreeNode> treeByDeptId(Integer deptId);

    /**
     * 根据社会信用代码查询部门是否存在
     * @param socialCreditNumber
     * @return dept
     * @author 蔡伟
     */
    Dept hasDeptWithSocialCreditNumber(String socialCreditNumber);

    /**
     * 为子公司添加父公司
     * @param id
     * @param pId
     * @return dept
     * @author 蔡伟
     */
    Integer addPid(Integer id, Integer pId);

    /**
     * 若存在子公司 将父公司加入子公司的子公司的父级
     * @param id
     * @param pId
     * @return dept
     * @author 蔡伟
     */
    Integer updateAllGrantChildrenByChildId(Integer id, Integer pId);

    /**
     * 根据部门id查询对应的公司id
     * @param deptId
     * @return Long
     * @author 蔡伟
     */
    Long selectSubContractorIdByDeptId(Integer deptId);

    /**
     * 设置有效期
     * @param dept
     * @return
     */
    void setTime(Dept dept);

    String selectCompanyName(String contractorOrgCode);
}
