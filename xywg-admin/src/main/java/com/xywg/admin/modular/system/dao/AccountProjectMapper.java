package com.xywg.admin.modular.system.dao;


import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.system.model.AccountProject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author hjy
 */
public interface AccountProjectMapper{

    /**
     * 添加账号与项目关联关系
     */
    void  insert(List<AccountProject> list);

    /**
     * 删除账号与项目关联关系
     */
    void  delete(List<AccountProject> list);

    /**
     * 根据登陆人,社会信用代码 查询其关联的项目
     * @param deptId
     * @param account
     * @param isEnterprise
     * @return
     */
    List<ProjectMaster> selectProjectsByAccountAndDeptId(@Param("deptId") String deptId, @Param("account") String account, @Param("isEnterprise") Integer isEnterprise);

	void updateDefault(Map<String, Object> map);

	void addRelationAndDefault(Map<String, Object> map);

	void updateNoDefault(Map<String, Object> map);

	List<Map<String, String>> getDefaultProject(String account);

	int selectIsExistRelationProject(String account);
}