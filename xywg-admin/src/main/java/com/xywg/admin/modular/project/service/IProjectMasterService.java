package com.xywg.admin.modular.project.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.core.base.tips.Tip;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.project.model.AppProjectListByPersonVo;
import com.xywg.admin.modular.project.model.AppProjectMasterDto;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.model.vo.ProjectMasterVo;

import io.swagger.models.auth.In;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目基础信息 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-23
 */
public interface IProjectMasterService extends IService<ProjectMaster> {

	boolean insert(ProjectMasterVo projectMaster);

    Map<String,Object> selectById(Long id);

    List<Map<String,Object>> selectList(Map<String ,Object> map , Page page);

    List<Map<String,Object>> getByProjectName(String projectName);

	/**
	 * 修改同步状态 智慧工地
	 * @param id
	 * @param synchro
	 */
	void updateSynchro(long id, int synchro);

    /**
     * 根据项目编号获取项目
     * @param projectCode
     * @return
     */
    ProjectMaster getProjectByProjectCode(String projectCode);

    /**
     * 切换的项目状态
     * @param ids
     * @param projectStatus
     * @return
     */
    Integer toggleProjectStatus(String ids, String projectStatus);

    /**
     * 获取我的关注项目列表
     * @param id
	 * @param organizationCode
     * @return
     * @author yuanyang
     */
    List<AppProjectMasterDto> getFollowProjectList(Integer id,String organizationCode);

    /**
     * 获取所有承包项目
     * @param map
     * @return
     */
    List<ProjectMaster> getList(Map<String, Object> map);

    /**
     * 
     * @description 上传劳动合同
     * @author chupp
     * @date 2018年6月7日
	 * @param contractFile
	 * @param projectCode
	 * @param cOrganizationCode
	 *
     */
    Tip uploadLaborContract(MultipartFile[] files, String projectCode, String cOrganizationCode);

    /**
     * app根据登录者获取所有项目
     * 2018年6月19日
     *下午3:08:06
     *@author wangshibo
     *
     */
	List<AppProjectListByPersonVo> getProjectsByUserId(Long userId);

	/**
	 * app根据登录者获取所有项目
	 * 2018年9月18日
	 *下午3:08:06
	 *@author cw
	 *
	 */
	List<AppProjectListByPersonVo> v116GetProjectsByUserId(Long userId,Integer pageNo , Integer pageSize);

	/**
	 * 
	 * @description 获取实名制项目信息（盐城）
	 * @author chupp
	 * @param myc 
	 * @date 2018年7月3日
	 *
	 */
	void saveProjectFromSMZYC(Map<String, String> myc);

	/**
	 *@param m 
	 * @Description:发送项目数据到实名制
	 *@Author xieshuaishuai
	 *@Date 2018/7/4 16:45
	 */
	boolean getProjectFromLabor(List<Long> ids, Map<String, String> m);

	/**
	 * 获取可手机考勤的在建项目
	 * @Title: getOndoingProjectsByUserId   
	 * @Description: (这获取可手机考勤的在建项目)
	 * @param: @param userId
	 * @param: @return      
	 * @return: List<AppProjectListByPersonVo>      
	 * @throws   
	 * @author:wangshibo
	 */
	List<AppProjectListByPersonVo> getOndoingProjectsByUserId(Long userId);

	/**
	 * 获取可手机考勤的在建项目
	 * @Title: getOndoingProjectsByUserId
	 * @Description: (这获取可手机考勤的在建项目)
	 * @param: @param userId
	 * @param: @return
	 * @return: List<AppProjectListByPersonVo>
	 * @throws
	 * @author:cw
	 */
	List<AppProjectListByPersonVo> v116GetOndoingProjectsByUserId(Long userId,Integer pageNo , Integer pageSize);

	/**
	 * 
	 * @description 获取实名制项目数据（盐城企业版）
	 * @author chupp
	 * @param myc 
	 * @date 2018年7月17日
	 *
	 */
	void saveProjectFromSMZCompanyYC(Map<String, String> myc);


	/**
	 * 根据项目编号获取详情
	 * @param projectCode
	 * @return
	 */
	Map<String,Object> selectProjectByProjectCode(String projectCode);

	/**
	 * 
	 * @description 获取实名制项目信息（南通）
	 * @author chupp
	 * @param mnt 
	 * @param list 
	 * @date 2018年7月26日
	 *
	 */
	void saveProjectFromSMZNT(Map<String, String> mnt, List<SubContractor> list);

	/**
	 * 根据id更新项目
	 * @param projectMaster
	 * @return
	 */
	boolean updateById(ProjectMasterVo projectMaster);

	/**
	 * 获取我的关注项目列表
	 * @param id
	 * @param organizationCode
	 * @return
	 * @author cw
	 */
	List<AppProjectMasterDto> v116GetFollowProjectList(Integer id,String organizationCode,Integer pageSize ,Integer pageNo);


	/**
	 * 设置工时计算
	 * @param id
	 * @param time
	 */
	void setTime(ProjectMaster projectMaster);

	/**
	 * 获取计工单位小时
	 * @param projectMasterId
	 * @return
	 */
	ProjectMaster getWorkTimeById(Integer projectMasterId);

	
	/**
	 * 保存南通实名制项目id数据
	 * @param mnt
	 */
	void saveProjectFromSMZTY(Map<String, String> mnt);
	
	void saveCompanyFromSMZTY(Map<String, String> mnt);

	void saveProjectFromSMZByCor(Map<String, String> mnt);


	/**
	 * 查詢項目設置頁面數據
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getListForSetting(Map<String, Object> params, Page page);

	/**
	 * 保存設置修改
	 * @param params
	 */
	void saveForSetting(Map<String, Object> params);

    String getCompanyOrgCodeById(Object companyId);

	void addProjectMasterList(List<Object> addList);

	void updateProjectMasterList(List<Object> updateList);

    /**
     * 同步项目到智慧工地
     * @param pt
     */
	void syncZh(ProjectMaster pt);


	/**
	 * 根据社会统一信用代码和编号查询数据
	 * @param id
	 * @return
	 */
	List<ProjectMaster> queryProjectById(String organizationCode,Long  id);

	Boolean setMac(String projectCode, String macCode);
}
