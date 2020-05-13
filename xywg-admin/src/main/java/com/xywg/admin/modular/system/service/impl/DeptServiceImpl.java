package com.xywg.admin.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.node.ZTreeNode;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.core.shiro.ShiroUser;
import com.xywg.admin.modular.system.dao.DeptMapper;
import com.xywg.admin.modular.system.model.Dept;
import com.xywg.admin.modular.system.model.OrganizationalStructure;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.system.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

    @Resource
    private DeptMapper deptMapper;
    @Autowired
    private IUserService userService;

    @Override
    public void deleteDept(Integer deptId) {
        Dept dept = deptMapper.selectById(deptId);

        Wrapper<Dept> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("pids", "%[" + dept.getId() + "]%");
        List<Dept> subDepts = deptMapper.selectList(wrapper);
        for (Dept temp : subDepts) {
            temp.deleteById();
        }

        dept.deleteById();
    }

    @Override
    public List<ZTreeNode> tree() {
        ShiroUser user = ShiroKit.getUser();
        if (user != null && user.getDeptId() != null) {
            return this.baseMapper.tree(user.getAccount());
        }
        return null;
    }

    @Override
    public List<ZTreeNode> treeByDeptId(Integer deptId) {
        return this.baseMapper.treeByDeptId(deptId);
    }

    @Override
    public Dept hasDeptWithSocialCreditNumber(String socialCreditNumber) {
        return this.baseMapper.hasDeptWithSocialCreditNumber(socialCreditNumber);
    }

    @Override
    public Integer addPid(Integer id, Integer pId) {
        return this.baseMapper.addPid(id,pId);
    }

    @Override
    public Integer updateAllGrantChildrenByChildId(Integer id, Integer pId) {
        return this.baseMapper.updateAllGrantChildrenByChildId(id,pId);
    }

    @Override
    public Long selectSubContractorIdByDeptId(Integer deptId) {
        Long subContractorId = this.baseMapper.selectSubContractorIdByDeptId(deptId);
        if(subContractorId == null){
            throw new XywgException(800,"该部门暂未分配公司");
        }
        return subContractorId;
    }

    @Override
    public void setTime(Dept dept) {
        boolean flag = true;
        if (dept.getStartTime()==null){
            dept.setStartTime(new Date());
        }
        if (dept.getEndTime() == null){
            try {
                dept.setEndTime(new SimpleDateFormat("yyyy-mm-dd").parse("9999-12-30"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        deptMapper.setTime(dept);
        //同时修改账号有效期
        userService.setTimeByDept(dept);
    }

    @Override
    public List<Map<String, Object>> list(String condition, String account) {

        return this.baseMapper.list(condition, account);
    }

    @Override
    public List<Map<String, Object>> getList(String account) {
        return this.baseMapper.getList(account);
    }

    /**
     * 查询当前用户所有子部门
     *
     * @return 所有子部门 id 集合(包含自己的部门)
     */
    @Override
    public List<Integer> getUserDeptAndSubdivision() {
        ShiroUser user = ShiroKit.getUser();
        if (user != null && user.getDeptId() != null) {
            List<Integer> list = deptMapper.getUserDeptAndSubdivision(user.getAccount());
            return list;
        }
        return null;
    }


    /**
     * 查詢当前用户所在部门以及子部门的社会信用统一代码集合  PC端
     * @return
     */
    @Override
    public List<String> getUserDeptAndSubdivisionOrganizationCode() {
        ShiroUser user = ShiroKit.getUser();
        if (user != null && user.getDeptId() != null) {
            List<String> list = deptMapper.getUserDeptAndSubdivisionOrganizationCode(user.getAccount());
            return list;
        }
        return null;
    }

    /**
     * 根据企业信用代码查询所有子部门的社会信用统一代码集合  (移动端)
     *
     * @param organizationCode
     * @return 返回当前部门以及所有子部门的社会信用统一代码集合
     */
    @Override
    public List<String> getAPPUserDeptAndSubdivisionOrganizationCode(String organizationCode) {
        return deptMapper.getAPPUserDeptAndSubdivisionOrganizationCode(organizationCode);
    }


    /**
     * 根据部门id查询所有子部门的社会信用统一代码集合
     *
     * @param deptId
     * @return 返回当前部门以及所有子部门的社会信用统一代码集合
     */
    @Override
    public List<String> getOrganizationCodeByDeptId(Integer deptId) {
        return deptMapper.getOrganizationCodeByDeptId(deptId);
    }


    @Override
    public OrganizationalStructure getOrganizationalStructureByOrganizationCode(String userId) {
        return deptMapper.getOrganizationalStructureByOrganizationCode(userId);
    }

    /**
     * 获取公司的组织架构
     *
     * @param userId 企业组织结构代码
     */
    @Override
    public OrganizationalStructure getOrganizationalStructure(String userId) {
        if (StringUtils.isBlank(userId)) {
            throw new XywgException(600, "用户id不能为空");
        }
        OrganizationalStructure organizationalStructureTop = getOrganizationalStructureByOrganizationCode(userId);

        List<OrganizationalStructure> organizationalStructure =
                deptMapper.getOrganizationalStructure(organizationalStructureTop.getId());
        organizationalStructureTop.setSubList(organizationalStructure);

        return organizationalStructureTop;
    }

    @Override
    public Dept getDept(Long id) {
        return deptMapper.getDept(id);
    }

    @Override
    public List<OrganizationalStructure> getDeptChilds(Long pId){
        return deptMapper.getOrganizationalStructure(pId);
    }


    @Override
    public String selectCompanyName(String contractorOrgCode) {
        String companyName = deptMapper.selectCompanyName(contractorOrgCode);
        if ("部门".equals(companyName)){
            return "龙信集团";
        }else {
            return companyName;
        }
    }
}
