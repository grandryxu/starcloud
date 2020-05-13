package com.xywg.admin.modular.bad.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.bad.dto.WorkerBlackDto;
import com.xywg.admin.modular.project.model.ProjectMaster;
import com.xywg.admin.modular.project.service.IProjectMasterService;
import com.xywg.admin.modular.system.model.SwitchType;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.team.model.TeamMaster;
import com.xywg.admin.modular.worker.model.WorkerMaster;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.modular.bad.dao.WorkerBadRecordsMapper;
import com.xywg.admin.modular.bad.dao.WorkerBlackListMapper;
import com.xywg.admin.modular.bad.model.WorkerBlackList;
import com.xywg.admin.modular.bad.model.WorkerBlackListVO;
import com.xywg.admin.modular.bad.service.IWorkerBlackListService;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.worker.model.WorkerMasterVO;

/**
 * <p>
 * 工人黑名单信息 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-06
 */
@Service
public class WorkerBlackListServiceImpl extends ServiceImpl<WorkerBlackListMapper, WorkerBlackList> implements IWorkerBlackListService {
	@Resource
	private IDeptService deptService;

	@Autowired
	private IProjectMasterService projectMasterService;

	@Autowired
	private AccountProjectService accountProjectService;

	@Override
	public List<Map<String, Object>> selectWorkerBlackList(Page<WorkerBlackList> page, Map<String, Object> map) {
		map.put("depts", deptService.getUserDeptAndSubdivisionOrganizationCode());
		return this.baseMapper.selectWorkerBlackList(page, map);
	}

	@Override
	public List<Map<String, Object>> selectCompanyBlackList(Page<WorkerBlackList> page, Map<String, Object> map) {
		List<String> list = deptService.getUserDeptAndSubdivisionOrganizationCode();
		map.put("deptId", ShiroKit.getUser().getDeptId());
		return this.baseMapper.selectCompanyBlackList(page, map);
	}

	@Override
	public List<WorkerMasterVO> selectWorkerInfoById(Integer id) {
		List<String> projectCodes = accountProjectService.getProjectCodes();
		List<WorkerMasterVO> workerMasterVO = this.baseMapper.selectWorkerInfoById(id, projectCodes, Integer.parseInt(ShiroKit.getSessionAttr("toggleDeptId").toString()));
		return workerMasterVO;
	}

	@Override
	public WorkerMasterVO getOwnerNameByProjectCode(String projectCode) {
		WorkerMasterVO workerMasterVO = this.baseMapper.getOwnerNameByProjectCode(projectCode);
		return workerMasterVO;
	}

	@Override
	public List<WorkerMasterVO> getTeamInfoByProjectCode(String projectCode) {
		Integer deptId = Integer.parseInt(ShiroKit.getSessionAttr("toggleDeptId"));
		List<WorkerMasterVO> workerMasterVO = this.baseMapper.getTeamInfoByProjectCode(projectCode, deptId);
		return workerMasterVO;
	}

	@Override
	public SubContractor selectCompanyInfoById(String organizationCode) {
		return this.baseMapper.selectCompanyInfoById(organizationCode);
	}

	@Override
	public WorkerBlackListVO selectWorkerBlackListById(Integer id) {
		return this.baseMapper.selectWorkerBlackListById(id);
	}

	@Override
	public WorkerBlackListVO selectCompanyBlackListById(Integer id) {
		return this.baseMapper.selectCompanyBlackListById(id);
	}


	@Override
	public List<Map<String, Object>> getCompanys() {
		List<String> list = deptService.getUserDeptAndSubdivisionOrganizationCode();
		return this.baseMapper.getCompanys(list, accountProjectService.getProjectCodes());
	}


	@Override
	public List<Map<String, Object>> getProjects() {
		SwitchType switchType = accountProjectService.getSwitchType();
		List<Map<String, Object>> data = null;
		if (switchType.getSwitchType() == 1) {
			//企业级
			Integer deptId = Integer.parseInt(ShiroKit.getSessionAttr("toggleDeptId").toString());
			data = this.baseMapper.getAllMainProjectMasterByDeptId(deptId, accountProjectService.getProjectCodes());
		} else {
			//项目级
			if (switchType.getIsGeneralContractor() == 1) {
				//总包
				data = new ArrayList<Map<String, Object>>();
				ProjectMaster projectMaster = projectMasterService.getProjectByProjectCode(ShiroKit.getSessionAttr("toggleProjectCode").toString());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("projectCode", projectMaster.getProjectCode());
				map.put("projectName", projectMaster.getProjectName());
				data.add(map);
			}
		}
		return data;
	}

	@Override
	public Integer updateBlack() {
		return this.baseMapper.updateBlack();
	}

	@Override
	public List<WorkerBlackDto> getBlackListApp(Integer type, Integer pageNo, Integer pageSize) {
		return this.baseMapper.getBlackListApp(type, (pageNo - 1) * pageSize, pageSize);
	}

	@Override
	public List<TeamMaster> getTeamsByProjectCodeApp(String organizationCode, String projectCode) {
		Map<String, Object> projectMaster = this.projectMasterService.selectProjectByProjectCode(projectCode);
		//参建公司
		Integer isGeneralContractor = 0;
		if (organizationCode.equals(projectMaster.get("contractorOrgCode").toString())) {
			//总包公司
			isGeneralContractor = 1;
		}
		return this.baseMapper.getTeamsByProjectCodeApp(organizationCode, projectCode, isGeneralContractor);
	}

	@Override
	public List<WorkerMaster> getWorkersByTeamSysNoApp(String organizationCode, String projectCode, String teamSysNo) {
		Map<String, Object> projectMaster = this.projectMasterService.selectProjectByProjectCode(projectCode);
		//参建公司
		Integer isGeneralContractor = 0;
		if (organizationCode.equals(projectMaster.get("contractorOrgCode").toString())) {
			//总包公司
			isGeneralContractor = 1;
		}
		return this.baseMapper.getWorkersByTeamSysNoApp(organizationCode, teamSysNo, isGeneralContractor);
	}

	@Override
	public List<WorkerMaster> getWorkersByTeamSysNo(String projectCode, String teamSysNo) {
		//0为参建
		Integer isGeneralContractor = 0;
		List<String> loginComSocialCreditNumbers = deptService.getUserDeptAndSubdivisionOrganizationCode();
		//切换项目的承包公司
		String pSocialCreditNumber = projectMasterService.selectProjectByProjectCode(projectCode).get("contractorOrgCode").toString();
		if (loginComSocialCreditNumbers.contains(pSocialCreditNumber)) {
			//登陆的用户相对项目为总包
			isGeneralContractor = 1;
		}
		return this.baseMapper.getWorkersByTeamSysNo(teamSysNo, isGeneralContractor,loginComSocialCreditNumbers);
	}

	@Override
	public void addWorkerBlackList(List<Object> addList) {
		for (Object o : addList) {
			WorkerBlackList workerBlackList = new WorkerBlackList();
			stringToDateException();
			try {
				BeanUtils.copyProperties(workerBlackList, o);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			workerBlackList.setId(null);
			insert(workerBlackList);

		}
	}



	//解决 BeanUtils.copyProperties()的string转date异常
	private void stringToDateException() {
		ConvertUtils.register(new Converter() {
			@Override
			public Object convert(Class type, Object value) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					if ("".equals(value.toString())){
						return null;
					}
					return simpleDateFormat.parse(value.toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return null;
			}
		}, java.util.Date.class);
	}
}
