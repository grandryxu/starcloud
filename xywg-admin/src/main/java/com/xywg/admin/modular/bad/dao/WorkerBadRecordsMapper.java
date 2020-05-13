package com.xywg.admin.modular.bad.dao;

import java.util.List;
import java.util.Map;

import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.team.model.TeamMaster;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.bad.dto.BadRecordsResultVO;
import com.xywg.admin.modular.bad.model.WorkerBadRecords;
import com.xywg.admin.modular.bad.model.WorkerBadRecordsVO;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.system.model.Dept;
import com.xywg.admin.modular.system.model.User;

/**
 * <p>
 * 工人不良行为记录信息 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
public interface WorkerBadRecordsMapper extends BaseMapper<WorkerBadRecords> {

	/**
	 * 查询工人不良信息分页列表
	 * @param page
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectWorkerBadRecords(@Param("page") Page<WorkerBadRecords> page, @Param("map") Map<String, Object> map);
	
	/**
	 * 查询班组不良信息分页列表
	 * @param page
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> selectTeamBadRecords(@Param("page") Page<WorkerBadRecords> page, @Param("map") Map<String, Object> map);
	
	/**
	 * 查询公司不良信息分页列表
	 * @param page
	 * @param map
	 * @param projectCodes
     * @return
	 */
	List<Map<String, Object>> selectCompanyBadRecords(@Param("page") Page<WorkerBadRecords> page, @Param("map") Map<String, Object> map,@Param("projectCodes") List<String> projectCodes);
	
	/**
	 * 提交审核
	 * @param id
	 */
	int submitWorkerBadRecords(@Param("id") Long id, @Param("auditName") String auditName);
	
	/**
	 * 取消审核
	 * @param id
	 */
	int cancelWorkerBadRecords(@Param("id") Long id);
	
	/**
	 * 根据id查询班组不良记录详情
	 */
	WorkerBadRecordsVO selectTeamBadRecordsById(@Param("id") Integer id);
	
	/**
	 * 根据id查询工人不良记录详情
	 */
	WorkerBadRecordsVO selectWorkerBadRecordsById(@Param("id") Integer id);
	
	/**
	 * 根据id查询企业不良记录详情
	 * @param id
	 * @return
	 */
	WorkerBadRecordsVO selectCompanyBadRecordsById(@Param("id") Integer id);
	
	/**
	 * 根据组织结构代码获取项目列表
	 * @param organizationCode
	 * @return
	 */
	List<ProjectMaster> getProjectList(@Param("organizationCode") String organizationCode, @Param("projectCodes") List<String> projectCodes);

	/**
	 * 查询工人姓名下拉列表
	 * @return
	 */
	List<Map<String,Object>> getWorkers(@Param("list") List<String> list, @Param("projectCodes") List<String> projectCodes);

	/**
	 * 查询工人姓名下拉列表
	 * @return
	 */
	List<Map<String,Object>> getTeams(@Param("list") List<String> list, @Param("projectCodes") List<String> projectCodes);


	/**
	 * app端查询不良记录列表
	 * @param type
	 * @return
	 */
	List<WorkerBadRecordsVO> getWorkerBadRecordsList(@Param("type") Integer type);

	/**
	 * app端查询不良记录列表
	 * @param type
	 * @return
	 * @author cw
	 */
	List<WorkerBadRecordsVO> v116GetWorkerBadRecordsList(@Param("type") Integer type,@Param("index") Integer index,@Param("pageSize") Integer pageSize
	);

	/**
	 * app端查询工人待审核不良记录列表
	 * @param idCardNumberList
	 * @return
	 */
	List<WorkerBadRecordsVO> getToAuditWorkerBadRecordsList(@Param("socialCreditNumberList") List<String> socialCreditNumberList);

    /**
     * app端查询工人待审核不良记录列表
     * @param idCardNumberList
     * @return
     * @author cw
     */
    List<WorkerBadRecordsVO> v116GetToAuditBadRecordsList(@Param("socialCreditNumberList") List<String> socialCreditNumberList,@Param("index") Integer index,@Param("pageSize") Integer pageSize);
	
	/**
	 * 新增不良记录 app端调用
	 * @param workerBadRecords
	 * @return
	 */
	int insertWorkerBadRecords(WorkerBadRecords workerBadRecords);
	/**
	 * 查询不良记录待审核总数
	 * @return
	 */
	BadRecordsResultVO getToAuditCount();
	
	/**
	 * 查询不良记录待审核总数详细
	 * @return
	 */
	BadRecordsResultVO getToAuditBadRecordsCount(@Param("socialCreditNumberList") List<String> socialCreditNumberList);
	
	/**
	 * 提交审核
	 * @param id
	 * @param isAudit
	 * @return
	 */
	int auditBadRecords(@Param("id") Long id, @Param("isAudit") Integer isAudit);


	/**
	 * 根据证件类型和证件编号查询不良记录
	 * @param idCardNumber
	 * @param idCardType
	 * @author yuanyang
	 * @return
	 */
    WorkerBadRecordsVO getByIdCard(@Param("idCardNumber")String idCardNumber, @Param("idCardType")Integer idCardType);

	/**
	 * 获取工人不良记录(tab页)
	 * @param page
	 * @param map
	 * @return
	 * @author yuanyang
	 */
    List<Map<String,Object>> getListByIdCard(@Param("page") Page<WorkerBadRecords> page, @Param("map") Map<String, Object> map);

	
	/**
	 * 根据id获取用户信息
	 * @param id
	 * @return
	 */
	User getUser(@Param("id") Long id);
	
	/**
	 * 通过部门id list 查询社会信用代码list
	 * @param deptIds
	 * @return
	 */
	List<String> getSocialCreditNumberList(@Param("deptIds") List<Integer> deptIds);
	
	/**
	 * 根据用户id获取所在公司社会信用统一代码
	 * @param organizationCode
	 * @return
	 */
	Dept getOrganizationCodeByUserId(@Param("id") Long id);
	
	/**
	 * 通过社会信用代码list查询证件编号List
	 * @param socialCreditNumberList
	 * @return
	 */
	List<String> getIdCardNumberList(@Param("socialCreditNumberList") List<String> socialCreditNumberList);

	/**
	 * 获取参建单位code集合
	 * @param list
	 * @return
	 */
	List<String> getOrganizationCode(@Param("list") List<String> list);
	
	/**
	 * 根据某公司获取其不良记录
	 * @param page
	 * @param map
	 * @return
	 */
    List<Map<String,Object>> getListBySubContractor(@Param("page") Page<WorkerBadRecords> page, @Param("map") Map<String, Object> map);

	/**
	 * 更新班组不良记录
	 * @author 蔡伟
	 * @param workerBadRecords
	 * @return
	 */
	@Override
	Integer updateById(WorkerBadRecords workerBadRecords);


	/**
	 * 获取某项目下所有的参建公司 不包括登陆公司
	 * @param projectCode
	 * @return
	 * @author 蔡伟
	 */
    List<SubContractor> getCompanys(@Param("projectCode") String projectCode, @Param("deptId") Integer deptId);

	/**
	 * 获取项目下的工人 总包获取所有 参建获取参建公司下的工人
	 * * @param projectCode
	 * @param isGeneralContractor
	 * @param loginComSocialCreditNumbers
	 * @return
	 */
    List<WorkerMaster> selectWorkersByProjectCode(@Param("projectCode") String projectCode,@Param("isGeneralContractor") Integer isGeneralContractor,@Param("loginComSocialCreditNumbers") List<String> loginComSocialCreditNumbers);

	/**
	 * 根据项目编号获取班组 若为总包 获取全部 若为参建 获取公司下班组
	 * @param projectCode
	 * @param isGeneralContractor
	 * @param loginComSocialCreditNumbers
	 * @return
	 */
    List<TeamMaster> getTeamsByProjectCode(@Param("projectCode") String projectCode,@Param("isGeneralContractor") Integer isGeneralContractor,@Param("loginComSocialCreditNumbers") List<String> loginComSocialCreditNumbers);

    /**
     * 获取所有公司
     * @return
     */
    List<SubContractor> getCompanysAll();
}
