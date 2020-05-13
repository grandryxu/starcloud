package com.xywg.admin.modular.bad.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.projectSub.service.IProjectSubService;
import com.xywg.admin.modular.system.model.SwitchType;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.team.model.TeamMaster;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.modular.bad.dao.WorkerBadRecordsMapper;
import com.xywg.admin.modular.bad.dto.BadRecordsResultVO;
import com.xywg.admin.modular.bad.model.WorkerBadRecords;
import com.xywg.admin.modular.bad.model.WorkerBadRecordsVO;
import com.xywg.admin.modular.bad.service.IWorkerBadRecordsService;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.system.dao.DeptMapper;
import com.xywg.admin.modular.system.model.Dept;
import com.xywg.admin.modular.system.model.User;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.team.service.ITeamMasterService;
import com.xywg.admin.modular.wages.dao.PayRollMapper;
import com.xywg.admin.modular.wages.dao.SettlementMapper;

/**
 * <p>
 * 工人不良行为记录信息 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
@Service
public class WorkerBadRecordsServiceImpl extends ServiceImpl<WorkerBadRecordsMapper, WorkerBadRecords>
        implements IWorkerBadRecordsService {

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private PayRollMapper payRollMapper;

    @Resource
    private SettlementMapper settlementMapper;

    @Resource
    private IDeptService deptService;

    @Resource
    ITeamMasterService teamMasterService;

    @Autowired
    private AccountProjectService accountProjectService;

    @Autowired
    private IProjectMasterService projectMasterService;

    @Override
    public List<Map<String, Object>> selectWorkerBadRecords(Page<WorkerBadRecords> page, Map<String, Object> map) {
        map.put("deptId", ShiroKit.getUser().getDeptId());
        map.put("projectCodes", accountProjectService.getProjectCodes());
        return this.baseMapper.selectWorkerBadRecords(page, map);
    }

    @Override
    public List<Map<String, Object>> selectTeamBadRecords(Page<WorkerBadRecords> page, Map<String, Object> map) {
        map.put("deptId", ShiroKit.getUser().getDeptId());
        map.put("projectCodes", accountProjectService.getProjectCodes());
        return this.baseMapper.selectTeamBadRecords(page, map);
    }

    @Override
    public List<Map<String, Object>> selectCompanyBadRecords(Page<WorkerBadRecords> page, Map<String, Object> map) {
        SwitchType switchType = accountProjectService.getSwitchType();
        if (switchType.getSwitchType() == 0 && switchType.getIsGeneralContractor() == 0) {
            //项目级参建
            return new ArrayList<>();
        } else {
            Integer deptId = ShiroKit.getUser().getDeptId();
            map.put("deptId", deptId);
            return this.baseMapper.selectCompanyBadRecords(page, map, accountProjectService.getProjectCodes());
        }
    }

    @Override
    public WorkerBadRecordsVO selectTeamBadRecordsById(Integer id) {
        return this.baseMapper.selectTeamBadRecordsById(id);
    }

    @Override
    public WorkerBadRecordsVO selectWorkerBadRecordsById(Integer id) {
        return this.baseMapper.selectWorkerBadRecordsById(id);
    }

    @Override
    public WorkerBadRecordsVO selectCompanyBadRecordsById(Integer id) {
        return this.baseMapper.selectCompanyBadRecordsById(id);
    }

    @Override
    public List<ProjectMaster> getProjectList(String organizationCode) {
        return this.baseMapper.getProjectList(organizationCode, accountProjectService.getProjectCodes());
    }

    @Override
    public int submitWorkerBadRecords(Long id, String auditName) {
        return this.baseMapper.submitWorkerBadRecords(id, auditName);
    }

    @Override
    public int cancelWorkerBadRecords(Long id) {
        return this.baseMapper.cancelWorkerBadRecords(id);
    }

    @Override
    public List<Map<String, Object>> getWorkers() {
        List<String> list = deptService.getUserDeptAndSubdivisionOrganizationCode();
        List<String> projectCodes = accountProjectService.getProjectCodes();
        return this.baseMapper.getWorkers(list, projectCodes);
    }

    @Override
    public List<Map<String, Object>> getTeams() {
        List<String> list = deptService.getUserDeptAndSubdivisionOrganizationCode();
        List<String> projectCodes = accountProjectService.getProjectCodes();
        return this.baseMapper.getTeams(list, projectCodes);
    }

    @Override
    public List<WorkerBadRecordsVO> getPageList(Integer type) {
        return baseMapper.getWorkerBadRecordsList(type);
    }

    @Override
    public List<WorkerBadRecordsVO> v116GetPageList(Integer type, Integer pageNo, Integer pageSize) {
        return baseMapper.v116GetWorkerBadRecordsList(type, (pageNo - 1) * pageSize, pageSize
        );
    }

    @Override
    public WorkerBadRecordsVO getByIdCard(String idCardNumber, Integer idCardType) {
        return this.baseMapper.getByIdCard(idCardNumber, idCardType);
    }

    @Override
    public List<WorkerBadRecordsVO> getToAuditPageList(Long id) {
        List<String> socialCreditNumberList = getUserDeptAndSubdivisionOrganizationCode(id);
        return baseMapper.getToAuditWorkerBadRecordsList(socialCreditNumberList);
    }

    @Override
    public List<WorkerBadRecordsVO> v116GetToAuditBadRecordsList(Long id, Integer pageNo, Integer pageSize) {
        List<String> socialCreditNumberList = getUserDeptAndSubdivisionOrganizationCode(id);
        return baseMapper.v116GetToAuditBadRecordsList(socialCreditNumberList, (pageNo - 1) * pageSize, pageSize);
    }

    @Override
    public Boolean insertWorkerBadRecords(WorkerBadRecords workerBadRecords) {
        Boolean result = teamMasterService.getWorkerInProjectStatus(workerBadRecords.getProjectCode(), workerBadRecords.getIdCardType(), workerBadRecords.getIdCardNumber());
        if (result) {
            baseMapper.insertWorkerBadRecords(workerBadRecords);
        }
        return result;
    }

    @Override
    public BadRecordsResultVO getToAuditCount() {
        return this.baseMapper.getToAuditCount();
    }

    @Override
    public BadRecordsResultVO getToAuditBadRecordsCount(Long id) {
        BadRecordsResultVO resultVo = new BadRecordsResultVO();
        Dept dept = this.baseMapper.getOrganizationCodeByUserId(id);
        List<String> socialCreditNumberList = getUserDeptAndSubdivisionOrganizationCode(id);
        BadRecordsResultVO badRecordsCountVo = this.baseMapper.getToAuditBadRecordsCount(socialCreditNumberList);
        resultVo.setToAuditBadRecordsCount(badRecordsCountVo.getToAuditBadRecordsCount());
        if (dept != null) {
            BadRecordsResultVO payRollCountVo = payRollMapper.getToAuditPayRollCount(dept.getSocialCreditNumber());
            resultVo.setToAuditPayRollCount(payRollCountVo.getToAuditPayRollCount());
            BadRecordsResultVO settlementCountVo = settlementMapper
                    .getToAuditSettlementCount(dept.getSocialCreditNumber());
            resultVo.setToAuditSettlementCount(settlementCountVo.getToAuditSettlementCount());
            resultVo.setCount(payRollCountVo.getToAuditPayRollCount() + settlementCountVo.getToAuditSettlementCount()
                    + badRecordsCountVo.getToAuditBadRecordsCount());
        } else {
            resultVo.setCount(badRecordsCountVo.getToAuditBadRecordsCount());
        }
        return resultVo;
    }

    @Override
    public int auditBadRecords(Long id, Integer isAudit) {
        return this.baseMapper.auditBadRecords(id, isAudit);
    }

    @Override
    public List<Map<String, Object>> getListByIdCard(Page<WorkerBadRecords> page, Map<String, Object> map) {
        map.put("depts", deptService.getUserDeptAndSubdivisionOrganizationCode());
        return this.baseMapper.getListByIdCard(page, map);
    }

    @Override
    public List<Map<String, Object>> getListBySubContractor(Page<WorkerBadRecords> page, Map<String, Object> map) {
        return this.baseMapper.getListBySubContractor(page, map);
    }

    @Override
    public List<SubContractor> getCompanys(String projectCode) {
        return this.baseMapper.getCompanys(projectCode, ShiroKit.getUser().getDeptId());
    }

    @Override
    public List<WorkerMaster> selectWorkersByProjectCode(String projectCode) {
        //0为参建
        Integer isGeneralContractor = 0;
        List<String> loginComSocialCreditNumbers = deptService.getUserDeptAndSubdivisionOrganizationCode();
        //切换项目的承包公司
        String pSocialCreditNumber = projectMasterService.selectProjectByProjectCode(projectCode).get("contractorOrgCode").toString();
        if (loginComSocialCreditNumbers.contains(pSocialCreditNumber)) {
            //登陆的用户相对项目为总包
            isGeneralContractor = 1;
        }
        return this.baseMapper.selectWorkersByProjectCode(projectCode, isGeneralContractor, loginComSocialCreditNumbers);
    }

    @Override
    public List<TeamMaster> getTeamsByProjectCode(String projectCode) {
        //0为参建
        Integer isGeneralContractor = 0;
        List<String> loginComSocialCreditNumbers = deptService.getUserDeptAndSubdivisionOrganizationCode();
        //切换项目的承包公司
        String pSocialCreditNumber = projectMasterService.selectProjectByProjectCode(projectCode).get("contractorOrgCode").toString();
        if (loginComSocialCreditNumbers.contains(pSocialCreditNumber)) {
            //登陆的用户相对项目为总包
            isGeneralContractor = 1;
        }
        return this.baseMapper.getTeamsByProjectCode(projectCode, isGeneralContractor, loginComSocialCreditNumbers);
    }

    /**
     * 查询当前用户所有子部门
     *
     * @return 所有子部门 id 集合
     */
    public List<Integer> getDeptList(Long id) {
        User user = this.baseMapper.getUser(id);
        if (user != null && user.getDeptid() != null) {
            List<Integer> list = deptMapper.getUserDeptAndSubdivision(user.getAccount());
            list.add(user.getDeptid());
            return list;
        }
        return null;
    }

    /**
     * 查询所有子公司社会信用代码集合
     *
     * @return
     */
    public List<String> getUserDeptAndSubdivisionOrganizationCode(Long id) {
        User user = this.baseMapper.getUser(id);
        if (user != null && user.getDeptid() != null) {
            List<String> list = deptMapper.getUserDeptAndSubdivisionOrganizationCode(user.getAccount());
            return list;
        }
        return null;
    }

	@Override
	public List<SubContractor> getCompanysAll() {
		return this.baseMapper.getCompanysAll();
	}

}
