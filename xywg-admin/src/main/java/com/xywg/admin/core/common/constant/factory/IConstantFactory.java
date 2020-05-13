package com.xywg.admin.core.common.constant.factory;

import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.system.model.Dict;
import com.xywg.admin.modular.team.model.TeamMaster;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * 常量生产工厂的接口
 *
 * @author wangcw
 * @date 2017-06-14 21:12
 */
public interface IConstantFactory {

    /**
     * 根据用户id获取用户名称
     *
     * @author wangcw
     * @Date 2017/5/9 23:41
     */
    String getUserNameById(Integer userId);

    /**
     * 根据用户id获取用户账号
     *
     * @author wangcw
     * @date 2017年5月16日21:55:371
     */
    String getUserAccountById(Integer userId);

    /**
     * 通过角色ids获取角色名称
     */
    String getRoleName(String roleIds);

    /**
     * 通过角色id获取角色名称
     */
    String getSingleRoleName(Integer roleId);

    /**
     * 通过角色id获取角色英文名称
     */
    String getSingleRoleTip(Integer roleId);

    /**
     * 获取部门名称
     */
    String getDeptName(Integer deptId);

    /**
     * 获取菜单的名称们(多个)
     */
    String getMenuNames(String menuIds);

    /**
     * 获取菜单名称
     */
    String getMenuName(Long menuId);

    /**
     * 获取菜单名称通过编号
     */
    String getMenuNameByCode(String code);

    /**
     * 获取字典名称
     */
    String getDictName(Integer dictId);

    /**
     * 获取通知标题
     */
    String getNoticeTitle(Integer dictId);

    /**
     * 根据字典名称和字典中的值获取对应的名称
     */
    String getDictsByName(String name, Integer val);

    /**
     * 获取性别名称
     */
    String getSexName(Integer sex);

    /**
     * 获取用户登录状态
     */
    String getStatusName(Integer status);

    /**
     * 获取菜单状态
     */
    String getMenuStatusName(Integer status);

    /**
     * 参建类型
     */
    String getContractorTypeName(Integer deptid);

    String getWorkKindNameByNum(Integer num);

    /**
     * 查询字典
     */
    List<Dict> findInDict(Integer id);

    /**
     * 获取被缓存的对象(用户删除业务)
     */
    String getCacheObject(String para);

    /**
     * 获取子部门id
     */
    List<Integer> getSubDeptId(Integer deptid);

    /**
     * 获取所有父部门id
     */
    List<Integer> getParentDeptIds(Integer deptid);

    /**
     * 获取单位性质名称
     */
    String getOrganizationTypeName(Integer deptid);

    /**
     * 获取币种
     */
    String getcurrencyName(Integer deptid);

    /**
     * 获取企业经营状态名称
     */
    String getBusinessStatusName(Integer deptid);

    /**
     * 获取证书类型
     */
    String getCertificationTypeName(Integer deptid);

    /**
     * 获取设备状态
     */
    String getStateName(Integer deptid);

    /**
     * 企业资质状态
     */
    String getQualificationStatusName(Integer deptid);

    /**
     * 企业资质证书状态
     */
    String getCertificationStatusName(Integer deptid);

    /**
     * 培训类型
     */
    String gettrainingTypeCodeName(Integer deptid);

    /**
     * 获取籍贯名称 yuanyang
     */
    String getAreaName(Integer deptid);

    /**
     * 获取工种名称 yuanyang
     */
    String getWorkKindName(Integer num);

    /**
     * 根据项目编号获取项目名称
     */
    ProjectMaster getProjectByProjectCode(String projectCode);

    /**
     * 获取设备类型名称
     * @param typeId
     * @return
     */
    String getTypeIdNameByTypeId(Integer typeId);

    /**
     * 获取设备出入类型名称
     * @param type
     * @return
     */
    String getTypeNameByType(Integer type);

    /**
     * 获取项目状态
     * @param projectStatus
     * @return
     */
    String projectStatusName(int projectStatus);
    
    /**
     * 获取项目活动类型
     */
    String projectCategory(Integer projectCategory);

    /**
     * 根据班组编号获取班组信息
     */
    TeamMaster getTeamByTeamSysNo(Integer teamSysNo);

    /**
     * 根据身份证类型获取身份证类型名称
     * @param pmIdcardType
     * @return
     */
    String getIdcardTypeName(int pmIdcardType);

    /**
     * 获取培训类型
     * @param pmIdcardType
     * @return
     */
    String getProjectType(Integer type);

    /**
     * 获取地区名称
     * @param areaCode
     * @return
     */
    String getAreaNameById(int areaCode);
}
