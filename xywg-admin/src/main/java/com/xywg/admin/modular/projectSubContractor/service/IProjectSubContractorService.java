package com.xywg.admin.modular.projectSubContractor.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.projectSubContractor.model.AppProjectSubContractorDto;
import com.xywg.admin.modular.projectSubContractor.model.ProjectSubContractor;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目参建企业信息 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-23
 */
public interface IProjectSubContractorService extends IService<ProjectSubContractor> {
    List<Map<String, Object>> getList(Map<String, Object> map, Page page);

    Map<String, Object> getById(Integer projectSubContractorId);

    int changeState(Map<String, Object> map);

    Map<String, Object> getCompanyById(Integer id);

    /**
     * 获取公司的项目列表
     *
     * @param organizationCode
     * @param projectType
     * @param key
     * @param projectStatus
     * @param id
     * @return
     * @yuanyang
     */
    List<AppProjectSubContractorDto> getProjectsByCompany(String organizationCode, Integer projectType, String key, Integer projectStatus, Integer id);

    /**
     * 获取公司的项目列表
     *
     * @param organizationCode
     * @param projectType
     * @param key
     * @param projectStatus
     * @param id
     * @return
     * @cw
     */
    List<AppProjectSubContractorDto> v116GetProjectsByCompany(String organizationCode, Integer projectType, String key, Integer projectStatus, Integer id,Integer pageNo , Integer pageSize, String type, String account);

    /**
     * 获取项目统计个数
     *
     * @param organizationCode
     * @return
     */
    AppProjectSubContractorDto getProjectCount(String organizationCode, String type, String account);

    /**
     * 获取参建公司的某项目的参建基本信息
     *
     * @param organizationCode
     * @param projectCode
     * @return
     */
    AppProjectSubContractorDto getCooperationProject(String organizationCode, String projectCode);

    /**
     * 切换进退场状态
     *
     * @param projectCode
     * @param status
     * @param organizationCodes
     * @return
     */
    Integer toggleJoinStatus(String projectCode, Integer status, String organizationCodes);

    /**
     * 根据项目编号和组织机构代码删除项目参建单位关系
     *
     * @param projectCode
     * @param organizationCodes
     * @return
     */
    Integer deleteByProjectCodeAndOrganizationCodes(String projectCode, String organizationCodes);

    /**
     * 获取公司的某项目的参建单位信息列表
     *
     * @param projectCode
     * @return
     * @author yuanyang
     */
    List<AppProjectSubContractorDto> getCooperationProjectList(String projectCode);

    /**
     * 获取公司的某项目的参建单位信息列表
     *
     * @param projectCode
     * @return
     * @author cw
     */
    List<AppProjectSubContractorDto> v116GetCooperationProjectList(String projectCode,Integer pageNo,Integer pageSize);

    List<ProjectSubContractor> getListByProjectCodeAndOrganizationCode(String projectCode, String organizationCode);

    /**
     * @return
     * @description 获取进场项目数
     * @author chupp
     * @date 2018年6月21日
     */
    int getTotalEntry();

    /**
     * @return
     * @description 获取退场项目数
     * @author chupp
     * @date 2018年6月21日
     */
    int getTotalExit();

    /**
     * 设置项目经理
     *
     * @param map
     * @return
     * @author yuanyang
     */
    boolean setPm(Map<String, Object> map);

    /**
     * 切换进退场状态 根据ids
     */
    Integer toggleJoinStatusWithIds(String ids, Integer status);

    /**
     * 获取登陆公司下所有的项目公司关系 并对其进行筛选  一个项目下有多条记录的保留总包记录
     * @param toggleDeptId
     * @return
     */
    List<ProjectSubContractor> getGroupByProjectSubContractorByDeptId(String toggleDeptId);

    Map<String, Object> getContractorById(Integer projectSubContractorId);


    Long selectProjectCodeAndOrjCode(Object projectCode, String companyOrgcode);
}
