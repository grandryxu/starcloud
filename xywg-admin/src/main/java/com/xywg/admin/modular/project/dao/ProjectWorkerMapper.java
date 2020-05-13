package com.xywg.admin.modular.project.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.company.model.dto.ContractorWorkerDto;
import com.xywg.admin.modular.project.model.*;
import com.xywg.admin.modular.system.model.SwitchType;
import com.xywg.admin.modular.worker.model.ContractFile;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目中工人信息 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
public interface ProjectWorkerMapper extends BaseMapper<ProjectWorker> {

	ProjectWorker getByWorkerAndTeam(@Param("teamCode") Integer teamCode,@Param("idType") String idType,@Param("idCardNumber") String idCardNumber);

	/**
	 * 签订合同的时候更新相应合同签订信息
	 * @param contractFile
	 * @return
	 */
	Integer updateContractInfo(ContractFile contractFile);

	/**
	 * 
	 * @description 获取工人项目列表
	 * @author chupp
	 * @date 2018年5月30日
	 * @param idCardNumber
	 * @param idCardType
	 * @return
	 *
	 */
	List<ProjectWorker> getProjectWorkerList(@Param("n") String idCardNumber, @Param("t") String idCardType);
	
	/**
	 * 
	 * @description 获取工人项目列表(考勤专用)
	 * @author chupp
	 * @date 2018年7月16日
	 * @param idCardNumber
	 * @param idCardType
	 * @return
	 *
	 */
	List<ProjectWorker> getProjectWorkerListFromDevice(@Param("n") String idCardNumber, @Param("t") String idCardType);


	/**
	 * 获取公司的某项目的工人年龄分布
	 * @param organizationCode
	 * @param projectCode
	 * @author yuanyang
	 * @return
	 */
	AppProjectCountDto getAgeCount(@Param("organizationCode") String organizationCode, @Param("projectCode")  String projectCode);

	/**
	 * 获取公司的某项目的工人性别分布
	 * @param organizationCode
	 * @param projectCode
	 * @author yuanyang
	 * @return
	 */
	AppProjectCountDto getGenderCount(@Param("organizationCode")String organizationCode,@Param("projectCode") String projectCode);
	/**
	 * 获取公司的某项目的工人工种分布
	 * @param organizationCode
	 * @param projectCode
	 * @author yuanyang
	 * @return
	 */
	List<AppProjectCountDto> getWorkTypeCount(@Param("organizationCode")String organizationCode, @Param("projectCode") String projectCode);

	/**
	 * 获取公司的某项目的班组数
	 *
	 * @param organizationCode
	 * @param deptId
	 * @param projectCode
	 * @param switchType
     * @author yuanyang
	 * @return
	 */
	AppProjectCountDto getTeamCountPC(@Param("organizationCode") String organizationCode, @Param("deptId") Integer deptId, @Param("projectCode") String projectCode, @Param("switchType") SwitchType switchType);

	/**
	 *
	 *
	 * @param organizationCode
	 * @param deptId
	 * @param projectCode
	 * @param switchType
	 * @author yuanyang
	 * @return
	 */
	AppProjectCountDto getJoinedCountPC(@Param("organizationCode") String organizationCode, @Param("deptId") Integer deptId, @Param("projectCode") String projectCode, @Param("switchType") SwitchType switchType);
    
    /**
     * 获取指定人所属项目信息列表
     * 2018年6月4日
     *下午3:12:35
     *@author wangshibo
     *
     */
    List<AppProjectListByPersonVo> getProjectListByPerson(@Param("idCardType")Integer idCardType,  @Param("idCardNumber")String idCardNumber,@Param("projectStatus") Integer projectStatus);


	/**
	 * 获取指定人所属项目信息列表
	 * 2018年6月4日
	 *下午3:12:35
	 *@author wangshibo
	 *
	 */
	List<AppProjectListByPersonVo> v116GetProjectListByPerson(@Param("idCardType")Integer idCardType,  @Param("idCardNumber")String idCardNumber,@Param("projectStatus") Integer projectStatus,@Param("index") Integer index,@Param("pageSize") Integer pageSize);

	/**
     * 收藏项目
     * 2018年6月5日
     *上午9:46:44
     *@author wangshibo
     *
     */
    void concernProject(@Param("projectCode")String projectCode,@Param("userId") Integer userId);
    
    /**
     * 取消关注
     * 2018年6月5日
     *上午10:17:16
     *@author wangshibo
     *
     */
    void cancleConcern(@Param("projectCode")String projectCode,@Param("userId") Integer userId);
    /**
     * 再次关注
     * 2018年6月5日
     *上午10:17:16
     *@author wangshibo
     *
     */
    void concernAgain(@Param("projectCode")String projectCode,@Param("userId") Integer userId);
    /**
     * 查询某人与某项目的关注关系
     * 2018年6月14日
     *上午11:04:18
     *@author wangshibo
     *
     */
    List<Map<String,Object>> searchConcern(@Param("projectCode")String projectCode,@Param("userId") Integer userId);
    /**
     * 获取项目下某个工人的信息app
     * 2018年6月6日
     *下午2:21:34
     *@author wangshibo
     *
     */
    AppPersonVo getPersonInfo(@Param("projectCode")String projectCode,@Param("idCardType")Integer idCardType,  @Param("idCardNumber")String idCardNumber);
    
    /**
     * 获取项目进场工人列表（app）
     * 2018年6月6日
     *下午3:09:15
     *@author wangshibo
     *
     */
    List<AppProjectJoinPerson>  getProjectJoinPerson(@Param("projectCode")String projectCode,@Param("organizationCode")String organizationCode);

	/**
	 * 获取项目进场工人列表（app）
	 * 2018年09月18日
	 *下午3:09:15
	 *@author cw
	 *
	 */
	List<AppProjectJoinPerson>  v116GetProjectJoinPerson(@Param("projectCode")String projectCode,@Param("organizationCode")String organizationCode,@Param("index") Integer index,@Param("pageSize") Integer pageSize
	);

    /**
     * 项目工人籍贯统计
     * 2018年6月6日
     *下午4:14:46
     *@author wangshibo
     *
     */
    List<AppBirthPlaceCountVo> getBirthPlaceCountPC(@Param("deptId") Integer deptId, @Param("projectCode") String projectCode, @Param("switchType") SwitchType switchType);
    
    /**
     * 获取项目信息app
     * 2018年6月6日
     *下午6:06:02
     *@author wangshibo
     *
     */
    AppProjectInfoVo getProjectInfo(@Param("projectCode")String projectCode,@Param("organizationCode")String organizationCode);
    
    /**
     * 获取班组下的工人信息（app）
     * 2018年6月8日
     *上午10:00:52
     *@author wangshibo
     *
     */
   List<AppPersonVo> getPersonsByTeam(@Param("teamId")Integer teamId);

	/**
	 * 获取班组下的工人信息（app）
	 * 2018年6月8日
	 *上午10:00:52
	 *@author wangshibo
	 *
	 */
	List<AppPersonVo> v116GetPersonsByTeam(@Param("teamId")Integer teamId,@Param("index") Integer index,@Param("pageSize") Integer pageSize);

   /**
    * 获取今日考勤人员列表
    * 2018年6月11日
    *下午5:17:43
    *@author wangshibo
    *
    */
   List<AppAttenWorkerVo> getAttendanceWorkerToday(@Param("projectCode")String projectCode,@Param("organizationCode")String organizationCode);

	/**
	 * 获取今日考勤人员列表
	 * 2018年9月18日
	 *下午5:17:43
	 *@author cw
	 *
	 */
	List<AppAttenWorkerVo> v116GetAttendanceWorkerToday(@Param("projectCode")String projectCode,@Param("organizationCode")String organizationCode,@Param("index") Integer index,@Param("pageSize") Integer pageSize
	);

	/**
	 * 获取工人工作履历列表(tab)
	 * @param page
	 * @param map
	 * @return
	 * @author yuanyang
	 */
    List<Map<String,Object>> getListByIdCard(@Param("page") Page<ProjectWorker> page, @Param("map") Map<String, Object> map);
    
    /**
     * 
     * @description 更新签署标记
     * @author chupp
     * @date 2018年6月12日
     * @param id
     *
     */
    void updateTeamSign(String id);
    
    /**
     * 查询项目下面的工人身份证号
	 * 
	 * @author: duanfen
	 * @version: 2018年6月16日 上午9:48:20
     * @param keys2 
     * @param workKind 
     */
    List<ProjectWorker> queryByProject(@Param("projectCode") String projectCode,@Param("team") String team,@Param("workKind") String workKind,@Param("keys") String keys);
	/**
	 * 查询项目下所有工人
	 */
	List<Map<String,Object>> getWorkerListByProject(@Param("map") Map map,@Param("deptId") Integer deptId, @Param("page") Page page);

	/**
	 * 
	 * @description 获取进场工人数
	 * @author chupp
	 * @date 2018年6月21日
	 * @param organizationCode
	 * @param projectCodes
	 * @return
	 *
	 */
	Integer getTotalJoin(@Param("list") List<String> list, @Param("projectCodes") List<String> projectCodes);

	/**
	 * 
	 * @description 获取项目人员分布
	 * @author chupp
	 * @date 2018年6月22日
	 * @param list
	 * @param projectCodes
	 * @return
	 *
	 */
	List<ContractorWorkerDto> getProjectJoinRange(@Param("list") List<String> list, @Param("projectCodes") List<String> projectCodes);
	
	/**
	 * 查询工人与项目是否有过关系（人脸用）
	 * 2018年6月23日
	 *下午6:25:14
	 *@author wangshibo
	 *
	 */
	List<Map<String,Object>> getForFaceUse(@Param("projectCode") String projectCode,@Param("idCardType")Integer idCardType,@Param("idCardNumber")String idCardNumber);

	/**
	 * 修改安全帽
	 * 2018年6月23日
	 *下午9:44:46
	 *@author wangshibo
	 *
	 */
	void updateShImei(@Param("projectCode") String projectCode,@Param("helmetCode")  String helmetCode, @Param("idCardType") Integer idCardType, @Param("idCardNumber") String idCardNumber);

	/**
	 * 查询该关系下是否存在班组 若有删除拒绝
	 * @param projectSubContractorId
	 * @return
	 * @author 蔡伟
	 */
    List<ProjectWorker> getProjectWorkersByProjectSubContractorId(Long projectSubContractorId);

	/**
	 * 根据证件类型和证件号判断工人是否在项目中
	 * @param projectCode
	 * @param idCardType
	 * @param idCardNumber
	 * @author yuanyang
	 * @return
	 */
	List<ProjectWorker> isInProject(@Param("projectCode")String projectCode,@Param("idCardType")Integer idCardType,@Param("idCardNumber")String idCardNumber);

	/**
	 * 获取工人在某项目下的安全帽imei
	 * @param projectCode
	 * @param idCardType
	 * @param idCardNumber
	 * @author wangshibo
	 * @return
	 */
	String getShImeiByPerson(@Param("teamCode")Integer teamCode,@Param("idCardType")Integer idCardType,@Param("idCardNumber")String idCardNumber);

	/**
	 * 获取当前选择项目下工人年龄分部
	 * @param projectCode
	 * @param switchType
	 * @param deptId
	 * @return
	 * @author 蔡伟
	 */
    List<ContractorWorkerDto> getAgeRange(@Param("projectCode") String projectCode, @Param("switchType") SwitchType switchType,@Param("deptId") Integer deptId);

	/**
	 * APP
	 * @param organizationCode
	 * @param projectCode
	 * @return
	 */
	List<AppBirthPlaceCountVo> getBirthPlaceCount(@Param("organizationCode") String organizationCode, @Param("projectCode") String projectCode);


	/**
	 * 获取公司的某项目的班组数
	 * @param list
	 * @param projectCode
	 * @author yuanyang
	 * @return
	 */
	AppProjectCountDto getTeamCount(@Param("list") List<String> list, @Param("projectCode") String projectCode);

	/**
	 *
	 * @param list
	 * @param projectCode
	 * @author yuanyang
	 * @return
	 */
	AppProjectCountDto getJoinedCount(@Param("list") List<String> list,  @Param("projectCode")String projectCode);

	/**
	 * 绑定安全帽
	 * @return
	 * @param projectWorker
	 */
    Integer bindSafetyHelmet(ProjectWorker projectWorker);

	/**
	 * 根据用户信息获取用户项目工人公司详情
	 * @param projectCode
	 * @param idCardType
	 * @param idCardNumber
	 * @return
	 */
    ProjectWorker getProjectWorkerByUserInfo(@Param("projectCode") String projectCode,@Param("idCardType") Integer idCardType,@Param("idCardNumber") String idCardNumber);

	/**
	 * 获取有安全帽的工人信息
	 * @return
	 */
	List<WorkInfoHead> getWorkInfoHeadList();

    /**
     * 获取工人最新定位信息
     * @param idCardType
     * @param idCardNumber
     * @param imei
     * @return
     */
	PersonPosition getPersonLastPosition(@Param("idCardType") String idCardType, @Param("idCardNumber") String idCardNumber,@Param("imei") String imei);
	
	/**
	 * 根据id查询项目名称额班组名称
	 * @param id
	 * @return
	 */
	Map<String, Object> queryProjectInfoById(@Param("id") Long id);
	
	/**
	 * 根据项目工人信息查询门禁卡号
	 * @return
	 */
	String getCardNumberByPwId(@Param("id") Long id);
	
	/**
	 * 修改门禁卡号
	 * @param pwId
	 * @param cardNumber
	 */
	void updateCardNumber(@Param("id")Long id,@Param("cardNumber")String cardNumber);
	
	/**
	 * 查询卡是已经有绑定
	 * @param cardNumber
	 * @return
	 */
	int getCountByCardNumber(@Param("cardNumber")String cardNumber,@Param("id") Long id);

	/**
	 * 根据id批量删除人员关系
	 */
	void batchDeleteInfoById(@Param("list") List<String> list);


	/**
	 * 项目统计
	 * @param projectCode
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> listReport(@Param("projectCode") String projectCode, @Param("type") String type, @Param("time")String time);

	List<Map<String, Object>> selectTeamReport(@Param("projectCode")String projectCode,@Param("type") String type, @Param("time") String time);

	List<Map<String, Object>> selectTeamReportEver(@Param("projectCode")String projectCode,@Param("type") String type, @Param("time") String time);

	List<Map> v116GetPersonsByWorkType(@Param("workType")String workType, @Param("index")int index, @Param("pageSize")Integer pageSize, @Param("projectCode")String projectCode);

	int getWorkerByTeam(@Param("projectCode")String projectCode, @Param("type")String type, @Param("sysNo")String sysNo);

    Map<String, Long> selectProjectWorkerByOrgCodeAndPrjCodeAndIdCard(@Param("projectCode") String projectCode, @Param("organizationCode") String organizationCode, @Param("teamSysNo") Integer teamSysNo, @Param("idCardNumber") String idCardNumber);

	/**
     * 根据uuid查询
     * <p>
     * Title: queryProjectWorkerByUuid
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @author duanfen
     * @date 2019年7月23日
     */
    PorjectWorkerDto queryProjectWorkerByUuid(@Param("uuid")String uuid);

    List<ProjectWorker> queryProjectWorkerById(@Param("organizationCode") String organizationCode, @Param("id") Long id, @Param("projectCode") String projectCode);

	List<ProjectWorker> getProjectWorkerInfoBy(@Param("projectCode") String projectCode,@Param("idCardType")Integer idCardType,@Param("idCardNumber")String idCardNumber);

}
