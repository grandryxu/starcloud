package com.xywg.admin.modular.projectSubContractor.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.projectSubContractor.model.AppProjectSubContractorDto;
import com.xywg.admin.modular.projectSubContractor.model.ProjectSubContractor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目参建企业信息 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-23
 */
public interface ProjectSubContractorMapper extends BaseMapper<ProjectSubContractor> {

    List<Map<String,Object>> getList(@Param("map") Map<String,Object> map, @Param("page") Page page);

    Map<String,Object> getById(@Param("id") Integer projectSubContractorId);

    int changeState(@Param("map") Map<String,Object> map);

    Map<String,Object> getCompanyById(@Param("id") Integer id);

    int add(@Param("map") Map<String,Object> map);
    /**
     * 获取公司的项目列表
     * @param organizationCode
     * @param projectType
     * @param key
     * @param projectStatus
     * @param id
     * @author yuanyang
     * @return
     */
    List<AppProjectSubContractorDto> getProjectsByCompany(@Param("organizationCode")String organizationCode,@Param("projectType") Integer projectType, @Param("key")String key,@Param("projectStatus") Integer projectStatus,@Param("id") Integer id);

    /**
     * 获取公司的项目列表
     * @param organizationCode
     * @param projectType
     * @param key
     * @param projectStatus
     * @param id
     * @author yuanyang
     * @return
     */
    List<AppProjectSubContractorDto> v116GetProjectsByCompany(@Param("organizationCode")String organizationCode,@Param("projectType") Integer projectType, @Param("key")String key,@Param("projectStatus") Integer projectStatus,@Param("id") Integer id,@Param("index") Integer index,@Param("pageSize") Integer pageSize, @Param("type") String type, @Param("account") String account);

    int addBussSubContractorConstruction(@Param("name") String name, @Param("socialCreditNumber") String socialCreditNumber, @Param("organizationCode") String organizationCode);

    /**
     * 获取项目统计个数
     * @param organizationCode
     * @author yuanyang
     * @return
     */
    AppProjectSubContractorDto getProjectCount(@Param("organizationCode") String organizationCode, @Param("type") String type, @Param("account") String account);

    /**
     * 获取参建公司的某项目的参建基本信息
     * @param organizationCode
     * @param projectCode
     * @author yuanyang
     * @return
     */
    AppProjectSubContractorDto getCooperationProject(@Param("organizationCode")String organizationCode, @Param("projectCode")String projectCode);
    /**
     * 切换进退场状态
     * @param projectCode
     * @param status
     * @param organizationCodesList
     * @return
     */
    Integer toggleJoinStatus(@Param("projectCode") String projectCode,@Param("status") Integer status,@Param("organizationCodesList") List<String> organizationCodesList);

    /**
     * 根据组织机构代码和项目编号删除记录
     * @param projectCode
     * @param organizationCodeList
     * @return
     */
    Integer deleteByProjectCodeAndOrganizationCodes(@Param("projectCode") String projectCode,@Param("organizationCodeList") List<String> organizationCodeList);
    /**
     * 获取公司的某项目的参建单位信息列表
     * @param projectCode
     * @author yuanyang
     * @return
     */
    List<AppProjectSubContractorDto> getCooperationProjectList(@Param("projectCode")String projectCode);

    /**
     * 获取公司的某项目的参建单位信息列表
     * @param projectCode
     * @author cw
     * @return
     */
    List<AppProjectSubContractorDto> v116GetCooperationProjectList(@Param("projectCode")String projectCode,@Param("index") Integer index ,@Param("pageSize") Integer pageSize);

    List<ProjectSubContractor> getListByProjectCodeAndOrganizationCode(@Param("projectCode") String projectCode,@Param("organizationCode")String organizationCode);
    
    /**
     * 
     * @description 
     * @author chupp
     * @date 2018年7月20日
     * @param projectCode
     * @param ocList
     * @return
     *
     */
    List<ProjectSubContractor> getListByProjectCodeAndOrganizationCodeFromCompany(@Param("projectCode") String projectCode,@Param("list")List<String> list);

    /**
     * 
     * @description 获取进场项目数
     * @author chupp
     * @date 2018年6月21日
     * @param organizationCode
     * @param projectCodes
     * @return
     *
     */
	Integer getTotalEntry(@Param("list") List<String> list, @Param("projectCodes") List<String> projectCodes);
	
	/**
     * 
     * @description 获取退场项目数
     * @author chupp
     * @date 2018年6月21日
     * @param list
     * @param projectCodes
     * @return
     *
     */
	Integer getTotalExit(@Param("list") List<String> list, @Param("projectCodes") List<String> projectCodes);

    /**
     * 设置项目经理
     * @param map
     * @return
     * @author yuanyang
     */
    boolean setPm(@Param("map")Map<String, Object> map);

    /**
     * 切换进退场状态 根据ids
     */
    Integer toggleJoinStatusWithIds(@Param("ids") String ids,@Param("status") Integer status);

    /**
     * 获取登陆公司下所有的项目公司关系 并对其进行筛选  一个项目下有多条记录的保留总包记录
     * @param toggleDeptId
     * @return
     */
    List<ProjectSubContractor> getGroupByProjectSubContractorByDeptId(String toggleDeptId);

    Map<String, Object> getContractorById(@Param("projectSubContractorId") Integer projectSubContractorId);

    Long selectProjectCodeAndOrjCode(@Param("projectCode") Object projectCode, @Param("companyOrgcode") String companyOrgcode);

}
