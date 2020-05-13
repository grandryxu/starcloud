package com.xywg.admin.modular.project.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.project.model.AppProjectListByPersonVo;
import com.xywg.admin.modular.project.model.AppProjectMasterDto;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.model.vo.ProjectPositionVo;
import com.xywg.admin.modular.smz.model.ProjectMo;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目基础信息 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-23
 */
public interface ProjectMasterMapper extends BaseMapper<ProjectMaster> {

    /**
     * 获取拉取实名制的projectId
     * @return
     */
    List<Map<String, Object>> getIsReceiveProjectId();

    List<Map<String,Object>> list(@Param("map") Map<String ,Object> map, @Param("page") Page page);

    List<Map<String,Object>> getByProjectName(@Param("projectName") String projectName);

    Map<String, Object> selectByIdForZH(Long id);

    /**
     * 修改同步智慧工地的状态
     * @param id
     * @param synchro
     */
    void updateSynchro(@Param("id")long id, @Param("synchro")int synchro);

    /**
     * 根据项目编号获取项目名称
     * @param projectCode
     * @return
     */
    ProjectMaster getProjectByProjectCode(String projectCode);

    /**
     * 切换项目状态
     * @param idList
     * @param projectStatus
     * @return
     */
    Integer toggleProjectStatus(@Param("idList") List<Long> idList,@Param("projectStatus") String projectStatus);

    /**
     * 获取我的关注项目列表
     * @param id
     * @return
     * @author yuanyang
     */
    List<AppProjectMasterDto> getFollowProjectList(@Param("id")Integer id,@Param("organizationCode")String organizationCode);

    /**
     * 获取我的关注项目列表
     * @param id
     * @return
     * @author cw
     */
    List<AppProjectMasterDto> v116GetFollowProjectList(@Param("id")Integer id,@Param("organizationCode")String organizationCode,@Param("index") Integer index,@Param("pageSize") Integer pageSize);

    List<ProjectMaster> getList(@Param("map") Map<String, Object> map);
    
    /**
     * app根据登陆者id获取项目
     * 2018年6月19日
     *下午3:39:29
     *@author wangshibo
     *
     */
    List<AppProjectListByPersonVo> getProjectsByUserId(Integer deptid);

    /**
     * app根据登陆者id获取项目
     * 2018年9月18日
     *下午3:39:29
     *@author cw
     *
     */
    List<AppProjectListByPersonVo> v116GetProjectsByUserId(@Param("deptid") Integer deptid,@Param("index") Integer index,@Param("pageSize") Integer pageSize);

    Map<String,Object> selectById(Long id);

    /**
     *@Description:发送项目数据到实名制
     *@Author xieshuaishuai
     *@Date 2018/7/4 16:45
     */
    List<ProjectMo> getProjectFromLabor(List<Long> ids);

    /**
     * 获取可手机考勤的在建项目
     * @Title: getOndoingProjectsByUserId   
     * @Description: (获取可手机考勤的在建项目)
     * @param: @param deptid
     * @param: @return      
     * @return: List<AppProjectListByPersonVo>      
     * @throws   
     * @author:wangshibo
     */
    List<AppProjectListByPersonVo> getOndoingProjectsByUserId(Integer deptid);

    /**
     * 获取可手机考勤的在建项目
     * @Title: getOndoingProjectsByUserId
     * @Description: (获取可手机考勤的在建项目)
     * @param: @param deptid
     * @param: @return
     * @return: List<AppProjectListByPersonVo>
     * @throws
     * @author:wangshibo
     */
    List<AppProjectListByPersonVo> v116GetOndoingProjectsByUserId(@Param("deptid") Integer deptid,@Param("index") Integer index,@Param("pageSize") Integer pageSize);

    /**
     * 根据项目名称判重
     * @param projectMaster
     * @return
     */
    ProjectMaster selectProjectMasterByName(ProjectMaster projectMaster);

    /**
     * 根据项目编号获取详情
     * @param projectCode
     * @return
     */
    Map<String,Object> selectProjectByProjectCode(String projectCode);

    /**
     * 
     * @description 获取项目多地址信息
     * @author chupp
     * @date 2018年8月14日
     * @param projectCode
     * @return
     *
     */
	List<ProjectPositionVo> getProjectPositionList(String projectCode);

    /**
     * 查询已有的虚拟考勤机最大sn
     * @return String 最大sn
     * @author 蔡伟
     */
    String selectMaxVirtualDeviceSn();

    /**
     * 设置工时
     * @param id
     * @param time
     */
    void setTime(ProjectMaster projectMaster);

    /**
     * 根据id获取工时计算单位小时
     * @param id
     * @return
     */
    ProjectMaster getWorkTimeById(Integer id);


	List<String> getRegisterNo();

	List<ProjectMaster> getProjectInfo();

	List<String> getProjectInfoByCor(@Param("maxId")Long maxId, @Param("registerNo")String registerNo);

	List<String> getProjectId(@Param("maxId")Long maxId, @Param("registerNo")String registerNo);
    /**
     * 給項目設置頁面查詢列表
     * @param map
     * @param page
     * @add by hh cao 2019/4/17
     * @return
     */
    List<Map<String, Object>> getListForSetting(@Param("map") Map<String ,Object> map, @Param("page") Page page);

    /**
     * 給項目設置頁面保存設置使用
     * @param params
     * @add by hh cao 2019/4/17
     */
    void saveForSetting(Map<String, Object> params);

	List<ProjectMaster> getZrProjectInfo();

	List<String> getProjectIdNew(@Param("maxId")Long maxId, @Param("registerNo")String registerNo);

	List<Map<String, Object>> getIsReceiveProjectIdNew(@Param("projectId")String projectId);

    String getCompanyOrgCodeById(@Param("companyId") Object companyId);

    /**
     * 根据社会统一信用代码和编号查询数据
     * @param id
     * @return
     */
    List<ProjectMaster> queryProjectById(@Param("organizationCode")String organizationCode,@Param("id")Long  id);


    Integer selectIsEnterprise(String account);

    void updateByProjectCode(@Param("projectCodeByContractor") String projectCodeByContractor, @Param("contractorOrgCode") String contractorOrgCode);

    void updateOrgCodeByProjectCode(@Param("projectCodeByContractor") String projectCodeByContractor, @Param("contractorOrgCode") String contractorOrgCode);


    Integer selectProjectWorkByProjectCode(@Param("projectCodeByContractor") String projectCodeByContractor);

    Integer selectProject(@Param("projectCode") String projectCode);

    void serMac(@Param("projectCode") String projectCode, @Param("macCode") String macCode, @Param("date") Date date);

    void updateMac(@Param("projectCode") String projectCode, @Param("macCode") String macCode, @Param("date") Date date);
}
