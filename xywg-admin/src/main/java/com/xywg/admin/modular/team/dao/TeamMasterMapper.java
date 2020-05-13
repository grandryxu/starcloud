package com.xywg.admin.modular.team.dao;

import java.util.List;
import java.util.Map;

import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.smz.model.TeamMemberSmz;
import com.xywg.admin.modular.smz.model.TeamMo;
import com.xywg.admin.modular.system.model.SwitchType;
import com.xywg.admin.modular.team.model.*;

import com.xywg.admin.modular.worker.model.ContractFile;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.worker.model.WorkerMaster;

/**
 * <p>
 * 班组基础信息 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-22
 */
public interface TeamMasterMapper extends BaseMapper<TeamMaster> {

	/**
	 * 逻辑删除 2018年5月22日 下午9:52:14
	 *
	 * @author wangshibo
	 *
	 */
	int setIsdel(@Param("teamId") Integer userId);

	/**
	 * 更新班组合同信息
	 * @param contractFile
	 * @return
	 */
	Integer updateContractInfo(ContractFile contractFile);

	/**
	 * 列表查询 2018年5月23日 上午10:37:25
	 *
	 * @author wangshibo
	 *
	 */
	List<Map<String, Object>> getTeams(@Param("page") Page<TeamMaster> page, @Param("map") Map<String, Object> map, @Param("codes") List<String> codes,
                                       @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc, @Param("switchType") SwitchType switchType);

	/**
	 * 获取单条 2018年5月23日 上午10:37:32
	 *
	 * @author wangshibo
	 *
	 */
	TeamMaster getById(@Param("teamId") Integer teamId);

	TeamMaster getTeamInfoByTeamSysNo(@Param("no") Integer id);

	/**
	 * 根据班组编号获取单条 2018年5月23日 上午10:37:32
	 *
	 * @author wangshibo
	 *
	 */
	TeamMaster getByteamCode(@Param("teamCode") Integer teamCode);

	/**
	 * 查询班组下的工人 2018年5月23日 下午8:00:22
	 *
	 * @author wangshibo
	 *
	 */
	List<Map<String, Object>> getMemberByTeamCode(@Param("page") Page<WorkerMaster> page,
			@Param("map") Map<String, Object> map, @Param("orderByField") String orderByField,
			@Param("isAsc") boolean isAsc);

	/**
	 * 设置班组长 2018年5月23日 下午8:44:31
	 *
	 * @author wangshibo
	 *
	 */
	int setTeamLeader(@Param("memberId") Integer memberId);

	/**
	 * 撤销班组长 2018年5月23日 下午9:08:07
	 *
	 * @author wangshibo
	 *
	 */
	int deleteLeader(@Param("teamCode") Integer teamCode);

	/**
	 * 工人移除班组 2018年5月23日 下午9:08:07
	 *
	 * @author wangshibo
	 *
	 */
	int deleteMember(@Param("memberId") Integer memberId);

	/**
	 * 工人加入班组 2018年5月23日 下午9:08:07
	 *
	 * @author wangshibo
	 *
	 */
	int addMember(TeamMemberShip teamMemberShip);

	/**
	 * 获取没有班组的工人 2018年5月23日 下午9:23:49
	 *
	 * @author wangshibo
	 *
	 */
	List<Map<String, Object>> getUnteamWorker(@Param("page") Page<WorkerMaster> page,
			@Param("map") Map<String, Object> map, @Param("orderByField") String orderByField,
			@Param("isAsc") boolean asc);

	/**
	 * 根据关系id获取关系数据 2018年5月24日 下午3:10:24
	 *
	 * @author wangshibo
	 *
	 */
	Map<String, Object> getTeamMemberByTMid(@Param("memberId") Integer memberId);

	/**
	 * 新增后修改code 2018年5月24日 下午7:30:59
	 *
	 * @author wangshibo
	 *
	 */
	int updateAfterInsert(Integer id);

/*	*//**
	 * 根据查询班组下的工人 2018年5月23日 下午8:00:22
	 *
	 * @author wangshibo
	 *
	 *//*
	List<Map<String, Object>> getMemberByTeamId(@Param("map") Map<String, Object> map);*/

	/**
	 * 查询选中的工人信息 2018年5月25日 下午4:46:30
	 *
	 * @author wangshibo
	 *
	 */
	List<WorkerMaster> selectWorkers(@Param("idStrs") String idStrs);

	/**
	 * 查询班组工人信息（返回对象） 2018年5月28日 下午9:04:47
	 *
	 * @author wangshibo
	 *
	 */
	TeamMemberShip getMemberByTMid(@Param("memberId") Integer memberId);

	/**
	 * 删除项目工人 2018年5月28日 下午9:45:46
	 *
	 * @author wangshibo
	 *
	 */
	void deleteProjectWorker(@Param("id") Long id);

	/**
	 * 工人进场 2018年5月29日 下午9:00:20
	 *
	 * @author wangshibo
	 *
	 */
	void workerJoin(@Param("idType") String idType, @Param("idCardNumber") String idCardNumber,@Param("teamCode") Integer teamCode);

	/**
	 * 工人退场 2018年5月29日 下午9:00:31
	 *
	 * @author wangshibo
	 *
	 */
	void workerOut(@Param("idType") String idType, @Param("idCardNumber") String idCardNumber,
			@Param("evaluate") String evaluate,@Param("teamCode") Integer teamCode);

	/**
	 * 根据班组编号查询企业编号
	 */
	String getOrganizationCodeByTeamCode(@Param("map") Map<String, Object> map);

	/**
	 * APP班组长姓名模糊查询 2018年5月30日 下午4:26:51
	 *
	 * @author wangshibo
	 *
	 */
	List<TeamCodeAndWorkerIdVo> getTeamCodeAndWorkerIdByWorkerName(@Param("teamLeaderName") String teamLeaderName);

	/**
	 * app条件查询工人班组关系（是否加入了班组） 2018年5月30日 下午5:24:14
	 *
	 * @author wangshibo
	 *
	 */
	List<Map<String, Object>> getMemberJoinStatus(@Param("teamCode") Integer teamCode, @Param("idType") Integer idType,
			@Param("idNumber") String idNumber);

	/**
	 * app查询工人与项目关系（是否已加入项目）
	 * 2018年6月22日
	 *下午4:26:06
	 *@author wangshibo
	 *
	 */
	List<Map<String, Object>> getWorkerInProjectStatus(@Param("projectCode") String projectCode, @Param("idCardType") Integer idCardType,
			@Param("idCardNumber") String idCardNumber);
	/**
	 * 根据id获取工人信息（app用） 2018年5月30日 下午5:54:45
	 *
	 * @author wangshibo
	 *
	 */
	WorkerMaster getWorkerById(@Param("id") Integer id);

	/**
	 * 根据班组code获取成员信息（app） 2018年5月30日 下午9:01:57
	 *
	 * @author wangshibo
	 *
	 */
	List<AppTeamMemberVo> getAppMemberByTeamCode(@Param("teamCode") Integer teamCode,@Param("joinStatus") Integer joinStatus);

	/**
	 * 根据班组code获取成员信息分页（app） 2018年9月26日 下午9:01:57
	 *
	 * @author cw
	 *
	 */
	List<AppTeamMemberVo> v116GetAppMemberByTeamCode(@Param("teamCode") Integer teamCode,@Param("joinStatus") Integer joinStatus,@Param("index") Integer index,@Param("pageSize") Integer pageSize);

	/**
	 * 班组进场 2018年5月30日 下午10:08:05
	 *
	 * @author wangshibo
	 *
	 */
	void teamJoin(@Param("projectCode") String projectCode, @Param("teamCode") String teamCode,@Param("pwId") Integer pwId);

	/**
	 * 班组退场 2018年5月30日 下午10:08:12
	 *
	 * @author wangshibo
	 *
	 */
	void teamOut(@Param("projectCode") String projectCode, @Param("teamCode") String teamCode,
			@Param("evaluate") String evaluate,@Param("pwId") Integer pwId);

	/**
	 * 统计工人总考勤天数 2018年6月1日 上午9:44:27
	 *
	 * @author wangshibo
	 *
	 */
	Integer getWorkerRecordDays(@Param("projectCode") String projectCode, @Param("idCardType") Integer idCardType,
			@Param("idCardNumber") String idCardNumber);

	/**
	 * 获取班组列表 无分页
	 * 
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getList(@Param("map") Map<String, Object> map);

	/**
	 * 查询班组下的工人（不分页）
	 */
	List<WorkerMaster> getTeamMemberByTeamCode(Integer teamCode);

	/**
	 * 查询工人是否在班组
	 */
	List<Map<String, Object>> getWorkerInTeam(@Param("teamId") Integer teamId, @Param("idCardType") Integer idCardType,
			@Param("idCardNumber") String idCardNumber);

	/**
	 * 条件查询已认证的工人app 2018年6月5日 下午4:41:08
	 *
	 * @author wangshibo
	 *
	 */
	List<AppWorkerByKeyVo> getWorkerByKey(@Param("key") String key,@Param("projectCode") String projectCode);

	/**
	 * 条件查询已认证的工人app 2018年9月26日 下午4:41:08
	 *
	 * @author cw
	 *
	 */
	List<AppWorkerByKeyVo> v116GetWorkerByKey(@Param("key") String key,@Param("projectCode") String projectCode,@Param("index") Integer index,@Param("pageSize") Integer pageSize);


	List<TeamMaster> getTeamMasterByProjectCode(@Param("projectCode")String projectId,@Param("list")List<String> list );

	/**
	 * 获取班组人数
	 * 2018年6月7日
	 *上午11:21:53
	 *@author wangshibo
	 *
	 */
	Integer getMemberCountByTeamCode(@Param("teamCode") Integer teamCode);
	
	/**
	 * 获取项目下的班组
	 * 2018年6月7日
	 *上午11:25:25
	 *@author wangshibo
	 *
	 */
	List<AppTeamInfoVo>  getAppTeamsByProject(@Param("projectCode") String projectCode,@Param("organizationCode") String organizationCode);

	/**
	 * 获取项目下的班组
	 * 2018年6月7日
	 *上午11:25:25
	 *@author cw
	 *
	 */
	List<AppTeamInfoVo>  v116GetAppTeamsInfo(@Param("projectCode") String projectCode,@Param("organizationCode") String organizationCode,@Param("index") Integer index ,@Param("pageSize") Integer pageSize);
	/**
	 * 
	 * @description 更新合同签署状态
	 * @author chupp
	 * @date 2018年6月8日
	 * @param object
	 *
	 */
	void updateTeamSign(String id);
	
	/**
	 * 获取工种
	 * 2018年6月11日
	 *上午9:54:12
	 *@author wangshibo
	 *
	 */
	List<AppWorkKindVo> getWorkKinds();

	/**
	 * 根据班组长id查询其所有项目列表
	 * xieshuaishuai
	 * @param appTeamerDto
	 * @return
	 */
	List<Map<String,Object>> getProjectListByTeamer(@Param("map")AppTeamerDto appTeamerDto);

	/**
	 * 根据班组长id查询其所有项目列表
	 * cw
	 * @param appTeamerDto
	 * @return
	 */
	List<Map<String,Object>> v116GetProjectListByTeamer(@Param("map")AppTeamerDto appTeamerDto);

	/**
	 * 根据名称查询某项目下的班组
	 * 2018年6月12日
	 *上午11:06:42
	 *@author wangshibo
	 *
	 */
	TeamMaster getByName(@Param("name")String name,@Param("projectCode")String projectCode);
	
	
	/**
	 * 
	 * @Title: checkNameForEdit   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param name
	 * @param: @param projectCode
	 * @param: @return      
	 * @return: TeamMaster      
	 * @throws   
	 * @author:wangshibo
	 */
	TeamMaster checkNameForEdit(TeamMaster teamMaster);
	
	/**
	 * 根据手机号查询用户id
	 * 2018年6月13日
	 *下午4:32:18
	 *@author wangshibo
	 *
	 */
	Integer getUserIdByPhone(@Param("phone")String phone);
	
	/**
	 * 参建单位下拉框
	 * 2018年6月22日
	 *上午9:35:20
	 *@author wangshibo
	 *
	 */
	List<UnitConstruct> getUnitContractorList(@Param("projectCode")String projectCode,@Param("codes") List<String> codes);
	
	/**
	 * 获取班组的班长的项目关系id
	 * 2018年6月23日
	 *下午8:29:20
	 *@author wangshibo
	 *
	 */
	Integer getPWidOfTeamLeader(@Param("teamCode") Integer teamCode);

	/**
	 * 修改班组联系人信息
	 * 2018年6月27日
	 *下午6:31:33
	 *@author wangshibo
	 *
	 */
	int updateLeaderInfo(@Param("leaderName")String leaderName, @Param("idCardNumber")String idCardNumber,@Param("cellPhone") String cellPhone,@Param("teamCode")Integer teamCode);

	/**
	 *@Description:发送劳务通班组数据到实名制
	 *@Author xieshuaishuai
	 *@Date 2018/7/9 14:40
	 */
	List<TeamMo> getTeamFromLabor(List<Long> ids);

	/**
	 * 修改班组信息
	 * @Title: updateTeam   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param teamMaster      
	 * @return: void      
	 * @throws   
	 * @author:wangshibo
	 */
	void updateTeam(TeamMaster teamMaster);

	/**
	 * 添加班组评价
	 * @Title: addEvaluate   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param teamEvaluation      
	 * @return: void      
	 * @throws   
	 * @author:wangshibo
	 */
	void addEvaluate(TeamEvaluation teamEvaluation);


	/**
	 * 获取班组管理列表 无分页 所有班组
	 * @author 蔡伟
	 * @param map
	 * @return list
	 */
    List<Map<String,Object>> getAllList(@Param("map") Map<String, Object> map);

    /**
     * 获取评价列表
     * @Title: getTeamEvaluations   
     * @Description: TODO(这里用一句话描述这个方法的作用)   
     * @param: @param page
     * @param: @param map
     * @param: @param orderByField
     * @param: @param asc
     * @param: @return      
     * @return: List<Map<String,Object>>      
     * @throws   
     * @author:wangshibo
     */
	List<Map<String, Object>> getTeamEvaluationsByTeam(@Param("page") Page<TeamEvaluation> page,
			@Param("map") Map<String, Object> map,@Param("orderByField") String orderByField,@Param("asc") boolean asc);

	/**
	 * 修改安全帽状态
	 * @author :wangshibo
	 * @param imeis
	 */
	void updateShImeiStatus(@Param("imeis")String imeis);

	/**
	 * 获取班组下的所有工人 蔡伟
	 * @param map
	 * @return
	 */
    List<Map<String, Object>> getTeamMemberByProjectCodeAndTeamCode(Map<String,Object> map);
	List<Map<String,Object>> getWorkerListByTeam(@Param("map") Map<String,Object> map,@Param("page") Page page);


	/**
	 * 获取班组下的所有工人 蔡伟 分页
	 * @param map
	 * @return
	 */
    List<Map<String,Object>> getTeamMemberByProjectCodeAndTeamCodePages(@Param("page") Page<Map<String, Object>> page,@Param("map") Map<String, Object> map);



	/**
	 * 查询人脸模版
	 * @author wangshibo
	 * @param idCardType
	 * @param idCardNumber
	 * @return
	 */
    List<BussPersonData> getPersonFaceData(@Param("idCardType")Integer idCardType,@Param("idCardNumber")String idCardNumber);

	/**
	 * 查询用户所有项目
	 * @param appTeamerDto
	 * @return
	 */
	List<Map<String,Object>> v1111GetProjectListByTeamer(@Param("map") AppTeamerDto appTeamerDto);
	
	/**
	 * 设置班组老板
	 * @param teamSysNo
	 * @param idCardNumber
	 * @param workerName
	 * @return
	 */
    Integer setTeamLeaderByTeamSysNo(@Param("teamSysNo") Integer teamSysNo,@Param("idCardNumber") String idCardNumber,@Param("workerName") String workerName);

	/**
	 * 将原有班组老板移除
	 * @param teamSysNo
	 * @return
	 */
	Integer removeTeamLeader(Integer teamSysNo);
	
	/**
	 * 根据项目查询当前工人在该项目老的班组编号
	 * @param projectId
	 * @param idCardNumber
	 * @return Integer
	 */
	Integer getOldTeamSysNoByProjectId(@Param("projectId") Long projectId, @Param("idCardNumber") String idCardNumber, @Param("idCardType") int idCardType);
	
	
	void updateByOldTeamSysNo(@Param("oldTeamSysNo") int oldTeamSysNo,@Param("t")TeamMemberShip member);
	
	String queryOrgnationCode(long projectId);

	boolean insertObj(TeamMaster teamMaster);

    Map<String, Long> selectIdByTeamSysNo(@Param("teamSysNo") Integer teamSysNo);



	/**
	 * 根据证件类型和证件号查询工人基本信息
	 * @param idCardNumber
	 * @param idCardType
	 * @return
	 */
	WorkerMaster getWorkerByIdCard(@Param("idCardNumber")String idCardNumber,@Param("idCardType")Integer idCardType);


	TeamMaster selectByProjectCode(@Param("projectCode")String projectCode,@Param("teamSysNo") int teamSysNo);

    List<TeamMaster> queryTeamMasterById(@Param("organizationCode") String organizationCode, @Param("id") Long id,@Param("projectCode")String projectCode);
	List<TeamMaster> selectByProjectCode1(@Param("projectCode")String projectCode,@Param("teamSysNo") int teamSysNo);

    List<Map<String, Object>> getTeamMasterByProjectCodeAndOrgCode(@Param("map") Map<String, Object> map, @Param("page") Page<TeamMaster> page);
}
