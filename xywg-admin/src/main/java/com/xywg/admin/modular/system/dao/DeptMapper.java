package com.xywg.admin.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.core.node.ZTreeNode;
import com.xywg.admin.modular.system.model.Dept;
import com.xywg.admin.modular.system.model.OrganizationalStructure;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author wangcw
 * @since 2017-07-11
 */
public interface DeptMapper extends BaseMapper<Dept> {

    /**
     * 获取ztree的节点列表
     */
    List<ZTreeNode> tree(String account);

    /**
     * 获取所有部门列表
     *@param account  登陆账号
     */
    List<Map<String, Object>> list(@Param("condition") String condition,@Param("account") String account);


    /**
     * 查询当前用户所在部门以及所有子部门
     * @param account  登陆账号
     * @return  部门以及所有子部门 id 集合
     */
    List<Integer>  getUserDeptAndSubdivision(String account);

    /**
     * 根据账号查询所在部门以及所有子部门的社会信用统一代码集合
     * @param getAccount
     * @return 部门以及所有子部门的社会信用统一代码集合
     */
    List<String>  getUserDeptAndSubdivisionOrganizationCode(String getAccount);


    /**
     * 根据企业信用代码查询公司以及子公司的社会信用统一代码集合  移动端
     * @return
     */
    List<String>  getAPPUserDeptAndSubdivisionOrganizationCode(String organizationCode);



    /**
     * 查詢当前用户所在部门以及子部门的社会信用统一代码集合  PC端
     */
    List<String> getOrganizationCodeByDeptId(Integer deptId);


    /**
     * 获取公司的组织架构
     * @param pid
     */
    List<OrganizationalStructure> getOrganizationalStructure(Long pid);


    /**
     * 查询当前企业组织
     * @param socialCreditNumber
     * @return
     */
    OrganizationalStructure getOrganizationalStructureByOrganizationCode(String socialCreditNumber);

    Dept getDept(Long id);

    /**
     * 获取当前登陆者所有部门信息(未删除)
     * @param account
     * @return
     */
    List<Map<String,Object>> getList(String account);

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
    Integer addPid(@Param("id") Integer id,@Param("pId") Integer pId);

    /**
     * 若存在子公司 将父公司加入子公司的子公司的父级
     * @param id
     * @param pId
     * @return dept
     * @author 蔡伟
     */
    Integer updateAllGrantChildrenByChildId(@Param("id") Integer id,@Param("pId") Integer pId);

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
    Integer setTime(Dept dept);

    String selectCompanyName(@Param("contractorOrgCode") String contractorOrgCode);

    String selectOrgCodeByDeptId(@Param("deptId") String deptId);
}