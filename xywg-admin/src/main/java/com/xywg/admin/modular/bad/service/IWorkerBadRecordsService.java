package com.xywg.admin.modular.bad.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.bad.dto.BadRecordsResultVO;
import com.xywg.admin.modular.bad.model.WorkerBadRecords;
import com.xywg.admin.modular.bad.model.WorkerBadRecordsVO;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.team.model.TeamMaster;
import com.xywg.admin.modular.worker.model.WorkerMaster;

/**
 * <p>
 * 工人不良行为记录信息 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
public interface IWorkerBadRecordsService extends IService<WorkerBadRecords> {

	/**
     * 获取工人不良记录列表
     * @param map
     * @author shily
     * @return
     */
    List<Map<String, Object>> selectWorkerBadRecords(Page<WorkerBadRecords> page,Map<String, Object> map);
    
    /**
     * 获取班组不良记录列表
     * @param map
     * @author shily
     * @return
     */
    List<Map<String, Object>> selectTeamBadRecords(Page<WorkerBadRecords> page,Map<String, Object> map);
    
    /**
     * 获取公司不良记录列表
     * @param map
     * @author shily
     * @return
     */
    List<Map<String, Object>> selectCompanyBadRecords(Page<WorkerBadRecords> page,Map<String, Object> map);
    
    /**
     * 提交审核
     * @param id
     */
    int submitWorkerBadRecords(Long id, String auditName);
 
    /**
     * 提交审核
     * @param id
     */
    int cancelWorkerBadRecords(Long id);
    
    /**
     * 根据id查询班组不良记录详情
     * @param id
     * @return
     */
    WorkerBadRecordsVO selectTeamBadRecordsById(Integer id);
    
    /**
     * 根据id查询工人不良记录详情
     * @param id
     * @return
     */
    WorkerBadRecordsVO selectWorkerBadRecordsById(Integer id);
    
    /**
     * 根据id查询企业不良记录详情
     * @param id
     * @return
     */
    WorkerBadRecordsVO selectCompanyBadRecordsById(Integer id);
    
    /**
     * 根据组织结构代码获取项目列表
     * @param organizationCode
     * @return
     */
    List<ProjectMaster> getProjectList(String organizationCode);
    
     /**
      * 获取人员姓名(下拉框)
      * @return
      */
    List<Map<String, Object>> getWorkers();
    
    /**
     * 获取班组姓名(下拉框)
     * @return
     */
   List<Map<String, Object>> getTeams();
   
   /**
    * app端获取不良记录列表
    * 
    * @param type
    * @return
    */
   List<WorkerBadRecordsVO> getPageList(Integer type);

    /**
     * app端获取不良记录列表
     *
     * @param type
     * @return
     */
    List<WorkerBadRecordsVO> v116GetPageList(Integer type,Integer pageNo , Integer pageSize);

    /**
     * 根据证件类型和证件编号查询不良记录
     * @param idCardNumber
     * @param idCardType
     * @return
     */
    WorkerBadRecordsVO getByIdCard(String idCardNumber, Integer idCardType);

   /**
    * app端获取待审核不良记录列表
    * 
    * @param type
    * @return
    */
   List<WorkerBadRecordsVO> getToAuditPageList(Long id);

    /**
     * app端获取待审核不良记录列表
     *
     * @param type
     * @return
     */
    List<WorkerBadRecordsVO> v116GetToAuditBadRecordsList(Long id,Integer pageNo , Integer pageSize);

   /**
    * 新增工人不良记录 app端调用
    * 
    * @param workerBadRecords
    * @return
    */
   Boolean insertWorkerBadRecords(WorkerBadRecords workerBadRecords);
   
   /**
    * 查询不良记录待审核总数
    * @return
    */
   BadRecordsResultVO getToAuditCount();
   
   /**
    * 查询不良记录待审核总数明细
    * @return
    */
   public BadRecordsResultVO getToAuditBadRecordsCount(Long id);
   
   /**
    * 审核不良记录
    * @return 
    */
   int auditBadRecords(Long id, Integer isAudit);

    /**
     * 获取工人不良记录(tab页)
     * @param page
     * @param map
     * @return
     * @author yuanyang
     */
    List<Map<String,Object>> getListByIdCard(Page<WorkerBadRecords> page, Map<String, Object> map);

    /**
     * 根据某公司获取不良记录
     * @param page
     * @param map
     * @return
     */
    List<Map<String,Object>> getListBySubContractor(Page<WorkerBadRecords> page, Map<String, Object> map);

    /**
     * 获取某项目下所有的参建公司 不包括登陆公司
     * @param projectCode
     * @return
     */
    List<SubContractor> getCompanys(String projectCode);

    /**
     * 获取项目下所有工人
     * @param projectCode
     * @return
     */
    List<WorkerMaster> selectWorkersByProjectCode(String projectCode);

    /**
     * 根据项目编号获取班组 若为总包 获取全部 若为参建 获取公司下班组
     * @param projectCode
     * @return
     */
    List<TeamMaster> getTeamsByProjectCode(String projectCode);

    /**
     * 获取所有的公司
     * @return
     */
	Object getCompanysAll();
}
