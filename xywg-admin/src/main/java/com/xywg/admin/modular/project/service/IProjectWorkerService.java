package com.xywg.admin.modular.project.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.company.model.dto.ContractorWorkerDto;
import com.xywg.admin.modular.project.model.*;
import com.xywg.admin.modular.worker.model.ContractFile;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 项目中工人信息 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-28
 */
public interface IProjectWorkerService extends IService<ProjectWorker> {

	/**
	 * 
	 * @description 获取工人在建项目列表
	 * @author chupp
	 * @date 2018年5月30日
	 * @param idCardNumber
	 * @param idCardType
	 * @return
	 *
	 */
	List<Map<String,Object>> getProjectWorkerList(String idCardNumber, String idCardType);

	/**
	 * 获取公司的某项目的工人年龄分布
	 * @param organizationCode
	 * @param projectCode
	 * @author yuanyang
	 * @return
	 */
	AppProjectCountDto getAgeCount(String organizationCode, String projectCode);

	/**
	 * 获取公司的某项目的工人性别分布
	 * @param organizationCode
	 * @param projectCode
	 * @author yuanyang
	 * @return
	 */
	AppProjectCountDto getGenderCount(String organizationCode, String projectCode);
	/**
	 * 获取公司的某项目的工人工种分布
	 * @param organizationCode
	 * @param projectCode
	 * @author yuanyang
	 * @return
	 */
	List<AppProjectCountDto> getWorkTypeCount(String organizationCode, String projectCode);

	/**
	 * 获取公司的某项目的班组数
	 * @param organizationCode
	 * @param projectCode
	 * @author yuanyang
	 * @return
	 */
	AppProjectCountDto getTeamCount(String organizationCode,  String projectCode);

	Integer updateContractInfo(ContractFile contractFile);

	/**
	 * 获取公司的某项目的进场人数
	 * @param organizationCode
	 * @param projectCode
	 * @author yuanyang
	 * @return
	 */
	AppProjectCountDto getJoinedCount(String organizationCode, String projectCode);

	/**
	 * 获取公司的某项目的班组数
	 * @param organizationCode
	 * @param projectCode
	 * @author yuanyang
	 * @return
	 */
	AppProjectCountDto getTeamCountPC(String organizationCode,  String projectCode);

	/**
	 * 获取公司的某项目的进场人数
	 * @param organizationCode
	 * @param projectCode
	 * @author yuanyang
	 * @return
	 */
	AppProjectCountDto getJoinedCountPC(String organizationCode, String projectCode);


	 /**
     * 获取指定人所在项目信息列表
     * 2018年6月4日
     *下午3:17:04
     *@author wangshibo
     *
     */
	List<AppProjectListByPersonVo> getProjectListByPerson(Integer idCardType, String idCardNumber, Integer projectStatus);

	/**
	 * 获取指定人所在项目信息列表
	 * 2018年9月18日
	 *下午3:17:04
	 *@author cw
	 *
	 */
	List<AppProjectListByPersonVo> v116GetProjectListByPerson(Integer idCardType, String idCardNumber, Integer projectStatus,Integer pageNo , Integer pageSize);

	/**
	 * 收藏项目
	 * 2018年6月5日
	 *上午9:44:36
	 *@author wangshibo
	 *
	 */
	void concernProject(String projectCode, Integer isCollect, Integer userId);

	/**
	 * 获取工人信息app
	 * 2018年6月6日
	 *下午2:24:57
	 *@author wangshibo
	 *
	 */
	AppPersonVo getPersonInfo(String idCardNumber, String projectCode, Integer idCardType);

	/**
	 * 获取项目进场工人列表
	 * 2018年6月6日
	 *下午3:10:10
	 *@author wangshibo
	 *
	 */
	List<AppProjectJoinPerson> getProjectJoinPerson(String organizationCode, String projectCode);

	/**
	 * 获取项目进场工人列表
	 * 2018年9月18日
	 *下午3:10:10
	 *@author cw
	 *
	 */
	List<AppProjectJoinPerson> v116GetProjectJoinPerson(String organizationCode, String projectCode,Integer pageNo , Integer pageSize);

	/**
	 * 获取项目中工人籍贯统计
	 * 2018年6月6日
	 *下午4:18:09
	 *@author wangshibo
	 *
	 */
	List<AppBirthPlaceCountVo> getBirthPlaceCount(String organizationCode, String projectCode);

	/**
	 * 获取项目中工人籍贯统计
	 *@author caiwei
	 *
	 */
	List<AppBirthPlaceCountVo> getBirthPlaceCountPC(String organizationCode, String projectCode);


	/**
	 * 获取项目信息
	 * 2018年6月6日
	 *下午6:09:19
	 *@author wangshibo
	 *
	 */
	AppProjectInfoVo getProjectInfo(String organizationCode, String projectCode);

	/**
	 * 获取某班组下面的工人信息
	 * 2018年6月8日
	 *上午9:57:13
	 *@author wangshibo
	 *
	 */
	List<AppPersonVo> getPersonsByTeam(Integer teamId);
	
	/**
	 * 获取今日考勤人员列表
	 * 2018年6月11日
	 *下午5:18:46
	 *@author wangshibo
	 *
	 */
	List<AppAttenWorkerVo> getAttendanceWorkerToday(String projectCode,String organizationCode);

	/**
	 * 获取今日考勤人员列表
	 * 2018年9月18日
	 *下午5:18:46
	 *@author cw
	 *
	 */
	List<AppAttenWorkerVo> v116GetAttendanceWorkerToday(String projectCode,String organizationCode,Integer pageNo , Integer pageSize);

	/**
	 * 查询工人工作履历(tab)
	 * @param page
	 * @param map
	 * @return
	 * @author yuanyang
	 */
    List<Map<String,Object>> getListByIdCard(Page<ProjectWorker> page, Map<String, Object> map);
    
    
    /**
     * 查询项目下面的工人身份证号
	 * 
	 * @author: duanfen
	 * @version: 2018年6月16日 上午9:48:20
     * @param keys2 
     * @param workKind 
     */
    List<ProjectWorker> queryByProject(String projectCode, String team, String workKind,String keys);

	/**
	 * 查询项目下所有工人
	 */
	List<Map<String,Object>> getWorkerListByProject(Map map,Page page);

	/**
	 * 
	 * @description 获取进场工人数
	 * @author chupp
	 * @date 2018年6月21日
	 * @return
	 *
	 */
	int getTotalJoin();

	/**
	 * 
	 * @description 获取项目人员分布
	 * @author chupp
	 * @date 2018年6月22日
	 * @return
	 *
	 */
	List<ContractorWorkerDto> getProjectJoinRange();
	
	/**
	 * 查询工人与项目是否有过关系（人脸用）
	 * 2018年6月23日
	 *下午6:26:48
	 *@author wangshibo
	 *
	 */
	boolean getForFaceUse(Integer workerId,String projectCode);

	/**
	 * 修改安全帽
	 * 2018年6月23日
	 *下午9:42:14
	 *@author wangshibo
	 *
	 */
	void updateShImei(String projectCode, String helmetCode, Integer workerId);

	/**
	 * 
	 * @description 获取实名制项目人员关系（盐城）
	 * @author chupp
	 * @param myc 
	 * @date 2018年7月5日
	 *
	 */
	void saveProjectPersonnelFromSMZYC(Map<String, String> myc);

	/**
	 * 查询该关系下是否存在班组 若有删除拒绝
	 * @param projectSubContractorId
	 * @return
	 */
	List<ProjectWorker> getProjectWorkersByProjectSubContractorId(Long projectSubContractorId);

	/**
	 * 
	 * @description 获取实名制项目工人关系（盐城企业版）
	 * @author chupp
	 * @param myc 
	 * @date 2018年7月18日
	 *
	 */
	void saveProjectPersonnelFromSMZCompanyYC(Map<String, String> myc);

	List<ContractorWorkerDto> getAgeRange(String projectCode);

	/**
	 * 
	 * @description 获取实名制项目人员关系（南通）
	 * @author chupp
	 * @param mnt 
	 * @param list 
	 * @date 2018年7月26日
	 *
	 */
	void saveProjectPersonnelFromSMZNT(Map<String, String> mnt, List<SubContractor> list);

	/**
	 * 通过调用实名制接口获取用户信息
	 * @param mnt
	 * @add by hh cao 2019/4/18
	 */
	void saveProjectWorkerInformation(Map<String, String> mnt);

	/**
	 * 判断人员是否在项目里
	 * @param projectCode
	 * @param idCardType
	 * @param idCardNumber
	 * @return
	 */
	boolean isInProject(String projectCode,Integer idCardType,String idCardNumber);

	/**
	 * 绑定安全帽
	 * @param projectWorker
	 * @return
	 */
    Integer bindSafetyHelmet(ProjectWorker projectWorker);

	/**
	 * 获取某班组下面的工人信息
	 * 2018年6月8日
	 *上午9:57:13
	 *@author cw
	 *
	 */
	List<AppPersonVo> v116GetPersonsByTeam(Integer teamId,Integer pageNo , Integer pageSize);

	/**
	 * 根据用户信息获取用户的关系项目工人公司关系
	 * @param projectCode
	 * @param idCardType
	 * @param idCardNumber
	 * @return
	 */
    ProjectWorker getProjectWorkerByUserInfo(String projectCode, Integer idCardType, String idCardNumber);

	/**
	 * 获取有安全帽的工人信息
	 * @return
	 */
	List<WorkInfoHead> getWorkInfoHeadList();
	

	/**
	 * 根据项目工人信息查询门禁卡号
	 * @return
	 */
	String getCardNumberByPwId(Long pwId);
	
	
	/**
	 * 根据id查询项目名称额班组名称
	 * @param id
	 * @return
	 */
	Map<String, Object> queryProjectInfoById(Long id);
	
	/**
	 * 修改门禁卡号
	 * @param pwId
	 * @param cardNumber
	 */
	void updateCardNumber(Long pwId,String cardNumber);
	
	/**
	 * 查询卡是已经有绑定
	 * @param cardNumber
	 * @return
	 */
	int getCountByCardNumber(String cardNumber,Long id);


	void saveProjectWorkerInformationNew(String userId, String projectId, Map<String, String> mnt);

	/**
	 *
	 * @param projectCode
	 * @param type
	 * @return
	 */
	List<Map<String, Object>> listReport(String projectCode, String type);

	List<Map<String, Object>> listReportEver(String projectCode, String type);

	List<Map> v116GetPersonsByWorkType(String workType, Integer pageNo, Integer pageSize, String projectCode);


	void addBussProjectWorker(List<Object> addList);



	/**
	 * 根据社会统一信用代码和id查询
	 * @param organizationCode
	 * @param id
	 * @return
	 */
	List<ProjectWorker> queryProjectWorkerById(String organizationCode, Long id, String projectCode);
}
