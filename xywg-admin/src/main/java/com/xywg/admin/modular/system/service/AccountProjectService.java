package com.xywg.admin.modular.system.service;

import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.system.model.AccountProject;
import com.xywg.admin.modular.system.model.SwitchType;

import java.util.List;
import java.util.Map;

/**
 * 账号与项目关联操作服务
 */
public interface AccountProjectService{

    /**
     * 添加账号与项目关联关系
     */
    void  add(List<AccountProject> list);

    /**
     * 删除账号与项目关联关系
     */
    void  delete(List<AccountProject> list);

    /**
     * 根据登陆人,社会信用代码 查询其关联的项目
     * @param deptId
     * @return
     */
    List<ProjectMaster> selectProjectsByAccountAndDeptId(String deptId);


    /**
     * 项目切换 , 将切换后的数据存入session中
     *
     * @param deptId
     * @param projectCode
     * @return
     */
    void switchProject(String deptId, String projectCode);

    /**
     * 退出项目切换
     */
    void reset();

    /**
     * 获取所有切换后的项目
     * @return
     */
    List<String> getProjectCodes();

    /**
     * 登陆Session初始化
     */
    void initSession();

    /**
     * 获取当前的切换状态
     * @return
     */
    SwitchType getSwitchType();

	void updateDefault(Map<String, Object> map);

	void addRelationAndDefault(Map<String, Object> map);

	void updateNoDefault(Map<String, Object> map);

	List<Map<String, String>> getDefaultProject(String account);

	int selectIsExistRelationProject(String account);
}
