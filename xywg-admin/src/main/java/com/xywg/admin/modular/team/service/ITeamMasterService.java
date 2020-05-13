package com.xywg.admin.modular.team.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.team.model.*;
import com.xywg.admin.modular.worker.model.ContractFile;
import com.xywg.admin.modular.worker.model.ContractFileParam;
import com.xywg.admin.modular.worker.model.WorkerMaster;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 班组基础信息 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-22
 */
public interface ITeamMasterService extends IService<TeamMaster> {
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
	 * 列表查询 2018年5月23日 上午10:34:04
	 *
	 * @author wangshibo
	 * @param page
	 * @param b
	 * @param string
	 *
	 */
	List<Map<String, Object>> getTeams(Page<TeamMaster> page, Map<String, Object> map, String string, boolean b);

	/**
	 * 查询班组下的员工 2018年5月23日 下午8:09:36
	 *
	 * @author wangshibo
	 *
	 */
	List<Map<String, Object>> getMemberByTeamCode(Page<WorkerMaster> page, Map<String, Object> map, String string,
			boolean b);

	/**
	 * 查询单条 2018年5月23日 上午10:40:06
	 *
	 * @author wangshibo
	 *
	 */
	TeamMaster getById(@Param("teamId") Integer teamId);

	/**
	 * 设置班组长 2018年5月23日 下午8:39:00
	 *
	 * @author wangshibo
	 *
	 */
	int setTeamLeader(Integer memberId);

	/**
	 * 工人加入班组 2018年5月23日 下午9:08:07
	 *
	 * @author wangshibo
	 *
	 */
	Map<String, Object> addMember(Map<String, Object> map);

	/**
	 * 工人移除班组 2018年5月23日 下午9:08:07
	 *
	 * @author wangshibo
	 *
	 */
	boolean deleteMember(@Param("memberId") Integer memberId);

	/**
	 * 获取没有班组的员工 2018年5月23日 下午9:20:44
	 *
	 * @author wangshibo
	 *
	 */
	List<Map<String, Object>> getUnteamWorker(Page<WorkerMaster> page, Map<String, Object> map, String orderByField,
			boolean asc);

	/**
	 * 工人进场 2018年5月29日 下午9:02:01
	 *
	 * @author wangshibo
	 *
	 */
	void workerJoin(String memberIds);

	/**
	 * 工人退场 2018年5月29日 下午9:02:23
	 *
	 * @author wangshibo
	 *
	 */
	void workerOut(String memberIds);

	/**
	 * 根据班组长姓名模糊查询
	 * 2018年5月31日
	 *上午10:53:19
	 *@author wangshibo
	 *
	 */
	List<AppGetTeamInfoVo> getTeamInfoLike(String teamLeaderName,Integer idCardType,String idCardNumber);

	/**
	 * 根据班组ID获取班组成员信息（APP）
	 * 2018年5月30日
	 *下午8:53:36
	 *@author wangshibo
	 *
	 */
	List<AppTeamMemberVo> getByTeamId(Integer id,Integer joinStatus);

	/**
	 * 班组进退场
	 * 2018年5月30日
	 *下午9:50:33
	 *@author wangshibo
	 *
	 */
	void changeTeamJoinStatus(AppTeamJoinStatusDto appJoinStatusDto);

	/**
	 * 根据班组id和项目code获取人员信息列表（班组长用）
	 * 2018年5月31日
	 *上午10:53:44
	 *@author wangshibo
	 *
	 */
	List<AppTeamMemberVo> getPersonByTeamId(Integer teamId, String projectCode);


	/**
	 * 根据班组id和项目code获取人员信息列表（班组长用）
	 * 2018年5月31日
	 *上午10:53:44
	 *@author wangshibo
	 *
	 */
	List<AppTeamMemberVo> v116GetPersonByTeamId(Integer teamId, String projectCode,Integer pageNo , Integer pageSize);

	/**
	 * 获取班组列表 无分页
	 * @param map
	 * @return
	 */
    List<Map<String,Object>> getList(Map<String, Object> map);
    
    /**
     * 根据班组code查询班组成员
     * 2018年6月4日
     *上午9:59:28
     *@author wangshibo
     *
     */
    List<WorkerMaster> getTeamMemberByTeamCode(Integer teamCode);

    /**
	 * 班组长查询工人列表
	 * 2018年6月5日
	 *下午4:46:46
	 *@author wangshibo
	 *
	 */
	List<AppWorkerByKeyVo> getWorkerByKeyAndTeamId(Integer teamId, String key);

	/**
	 * 班组长查询工人列表
	 * 2018年9月26日
	 *下午4:46:46
	 *@author cw
	 *
	 */
	List<AppWorkerByKeyVo> v116GetWorkerByKeyAndTeamId(Integer teamId, String key,Integer pageNo , Integer pageSize);

	/**
	 * 查询项目下所有的班组
	 * @param projectCode
	 * @return
	 */
	List<TeamMaster>  getTeamMasterByProjectCode(String projectCode);


	/**
	 * 获取项目班组信息
	 * 2018年6月7日
	 *上午11:17:57
	 *@author wangshibo
	 *
	 */
	List<AppTeamInfoVo> getAppTeamsInfo(String organizationCode, String projectCode);

	/**
	 * 
	 * @description 查询劳动合同
	 * @author chupp
	 * @date 2018年6月8日
	 * @param teamMemberShip
	 * @return
	 *
	 */
	Map<String,Object> getLaborContract(TeamMemberShip teamMemberShip);


	/**
	 * 班组长新增工人
	 * 2018年6月8日
	 *下午6:12:55
	 *@author wangshibo
	 *
	 */
	void teamLeaderAddWorker(AppTeamAddWorkerDto addWorkerDto);


	/**
	 * 
	 * @description 保存劳动合同
	 * @author chupp
	 * @date 2018年6月8日
	 * @param contractFile
	 *
	 */
	void saveLaborContract(ContractFileParam contractFile);

	/**
	 * 获取工种
	 * 2018年6月11日
	 *上午9:40:44
	 *@author wangshibo
	 *
	 */
	List<AppWorkKindVo> getWorkKinds();

	/**
	 * 根据班组长id查询其所有项目列表
	 * xieshuaishuai
	 */
	List<Map<String,Object>> getProjectListByTeamer(AppTeamerDto appTeamerDto);

	/**
	 * 根据班组长id查询其所有项目列表
	 * cw
	 */
	List<Map<String,Object>> v116GetProjectListByTeamer(AppTeamerDto appTeamerDto);

	/**
	 * 根据用户id查询其所有项目列表
	 * cw
	 */
	List<Map<String,Object>> v1111GetProjectListByTeamer(AppTeamerDto appTeamerDto);

	/**
	 * 获取参建单位下拉框
	 * 2018年6月22日
	 *上午9:44:51
	 *@author wangshibo
	 *
	 */
	List<UnitConstruct> getUnitContractorList(String projectCode);

	/**
	 * 查询工人是否加入过某项目
	 * 2018年6月26日
	 *下午4:05:18
	 *@author wangshibo
	 *
	 */
	boolean getWorkerInProjectStatus(String projectCode,Integer idCardType,String idCardNumber);

	/**
	 * 修改班组信息
	 * @Title: update   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param teamMaster      
	 * @return: void      
	 * @throws   
	 * @author:wangshibo
	 */
	void update(TeamMaster teamMaster);

	/**
	 * 添加班组评价
	 * @Title: addEvaluate   
	 * @Description: TODO(添加班组评价)   
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
	List<Map<String,Object>> getAllList(Map<String, Object> map);

	/**
	 * 获取班组评价列表
	 * @Title: getTeamEvaluations   
	 * @Description: TODO(获取班组评价列表)   
	 * @param: @param page
	 * @param: @param map
	 * @param: @param orderByField
	 * @param: @param asc
	 * @param: @return      
	 * @return: List<Map<String,Object>>      
	 * @throws   
	 * @author:wangshibo
	 */
	List<Map<String, Object>> getTeamEvaluationsByTeam(Page<TeamEvaluation> page,
			Map<String, Object> map, String orderByField, boolean asc);

	/**
	 * 
	 * @description 保存实名制班组数据（盐城）
	 * @author chupp
	 * @param myc 
	 * @date 2018年7月11日
	 *
	 */
	void saveTeamMasterFromSMZYC(Map<String, String> myc);

	/**
	 *@param m 
	 * @Description:发送劳务通班组数据到实名制
	 *@Author xieshuaishuai
	 *@Date 2018/7/10 17:20
	 */
	boolean getTeamFromLabor(List<Long> ids, Map<String, String> m);

	/**
	 * 
	 * @description 获取实名制劳动合同（盐城）
	 * @author chupp
	 * @param myc 
	 * @date 2018年7月13日
	 *
	 */
	void saveLaborContractFromSMZYC(Map<String, String> myc);

	/**
	 * 
	 * @description 获取实名制班组信息（盐城企业版）
	 * @author chupp
	 * @param myc 
	 * @date 2018年7月18日
	 *
	 */
	void saveTeamMasterFromSMZCompanyYC(Map<String, String> myc);

	/**
	 * 获取班组下的所有工人 蔡伟
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> getTeamMemberByProjectCodeAndTeamCode(Map<String,Object> map);
	void saveTeamMasterFromSMZNT(Map<String, String> mnt, List<SubContractor> list);

	/**
	 *
	 * @description 获取实名制劳动合同（南通）
	 * @author chupp
	 * @param mnt 
	 * @param list 
	 * @date 2018年7月26日
	 *
	 */
	void saveLaborContractFromSMZNT(Map<String, String> mnt, List<SubContractor> list);

	/**
	 *@Description:获取班组下工人列表
	 *@Author xieshuaishuai
	 *@Date 2018/7/26 10:54
	 */
	List<Map<String,Object>> getWorkerListByTeam(Map<String,Object> map,Page page);

	/**
	 * 获取班组下所有人 分页
	 * @auth 蔡伟
	 */
	List<Map<String,Object>> getTeamMemberByProjectCodeAndTeamCodePages(Page<Map<String, Object>> page, Map<String, Object> map);

	/**
	 * 获取项目班组信息
	 * 2018年6月7日
	 *上午11:17:57
	 *@author cw
	 *
	 */
	List<AppTeamInfoVo> v116GetAppTeamsInfo(String organizationCode, String projectCode,Integer pageNo , Integer pageSize);

	/**
	 * 根据班组ID获取班组成员信息分页（APP）
	 * 2018年5月30日
	 *下午8:53:36
	 *@author wangshibo
	 *
	 */
    List<AppTeamMemberVo> v116getByTeamId(Integer id, Integer joinStatus, Integer pageNo, Integer pageSize);
	/**
	 * 设置班组老板
	 * @param teamSysNo
	 * @param idCardNumber
	 * @param workerName
	 * @return
	 */
    Integer setTeamLeaderByTeamSysNo(Integer teamSysNo, String idCardNumber, String workerName);

	/**
	 * 设置班组老板时将原有班组长移除
	 * @param teamSysNo
	 * @return
	 */
	Integer removeTeamLeader(Integer teamSysNo);
	
	/**
	 * 
	 * @param pro 
	 * @param oldTeamSysNo
	 * @param member
	 */
	void updateByOldTeamSysNo(int oldTeamSysNo,TeamMemberShip member);

	void saveTeamMasterFromSMZTY(Map<String, String> mnt);

	boolean insertObj(TeamMaster teamMaster);

	void validateProJectAndWorker(String oldProjectCode, String projectCode, int oldTeamSysNo, TeamMemberShip member);

    void addTeamMasterList(List<Object> addList);


	/**
	 * 根据社会统一信用代码和编号查询数据
	 * @param id
	 * @return
	 */
	List<TeamMaster> queryTeamMasterById(String organizationCode, Long  id,String projectCode);

    List<Map<String, Object>> getTeamMasterByProjectCodeAndOrgCode(Map<String, Object> map, Page<TeamMaster> page);
}
