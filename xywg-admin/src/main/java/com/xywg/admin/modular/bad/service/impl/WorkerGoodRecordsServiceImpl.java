package com.xywg.admin.modular.bad.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.bad.dao.WorkerGoodRecordsMapper;
import com.xywg.admin.modular.bad.model.WorkerGoodRecords;
import com.xywg.admin.modular.bad.model.WorkerGoodRecordsVO;
import com.xywg.admin.modular.bad.service.IWorkerGoodRecordsService;
import com.xywg.admin.modular.system.service.AccountProjectService;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.team.model.TeamMasterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工人奖励记录信息 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-05
 */
@Service
public class WorkerGoodRecordsServiceImpl extends ServiceImpl<WorkerGoodRecordsMapper, WorkerGoodRecords> implements IWorkerGoodRecordsService {
	@Resource
	private IDeptService deptService;
	@Autowired
	private AccountProjectService accountProjectService;

	@Override
	public List<Map<String, Object>> selectWorkerGoodRecords(Page<WorkerGoodRecords> page, Map<String, Object> map) {
		map.put("deptId", ShiroKit.getUser().getDeptId());
		map.put("projectCodes", accountProjectService.getProjectCodes());
		return this.baseMapper.selectWorkerGoodRecords(page, map);
	}
	

	@Override
	public List<Map<String, Object>> selectTeamGoodRecords(Page<WorkerGoodRecords> page, Map<String, Object> map) {
		map.put("deptId", ShiroKit.getUser().getDeptId());
		map.put("projectCodes", accountProjectService.getProjectCodes());
		return this.baseMapper.selectTeamGoodRecords(page, map);
	}
	
	@Override
	public WorkerGoodRecordsVO selectWorkerGoodRecordsById(Integer id){
		return this.baseMapper.selectWorkerGoodRecordsById(id);
	}
	
	@Override
	public WorkerGoodRecordsVO selectTeamGoodRecordsById(Integer id){
		return this.baseMapper.selectTeamGoodRecordsById(id);
	}
	
	@Override
	public TeamMasterVO selectTeamInfoById(Integer id){
		return this.baseMapper.selectTeamInfoById(id);
	}

	@Override
	public WorkerGoodRecordsVO getByIdCard(String idCardNumber, Integer idCardType) {
		return this.baseMapper.getByIdCard(idCardNumber,idCardType);
	}

	@Override
	public List<Map<String, Object>> getListByIdCard(Page<WorkerGoodRecords> page, Map<String, Object> map) {
		map.put("depts",deptService.getUserDeptAndSubdivisionOrganizationCode());
		return this.baseMapper.getListByIdCard(page,map);
	}

}
