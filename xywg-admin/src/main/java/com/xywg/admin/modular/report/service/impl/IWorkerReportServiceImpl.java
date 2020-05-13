
package com.xywg.admin.modular.report.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xywg.admin.modular.report.model.WorkerReport;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.report.dao.IWorkerReportMapper;
import com.xywg.admin.modular.report.service.IWorkerReportService;
import com.xywg.admin.modular.system.service.IDeptService;
import com.xywg.admin.modular.worker.model.WorkerMasterVO;

/**
 * <p>
 * 人员报告 服务实现类
 * </p>
 *
 * @author shily
 * @since 2018-06-20
 */
@Service
public class IWorkerReportServiceImpl extends ServiceImpl<IWorkerReportMapper, WorkerMasterVO>
		implements IWorkerReportService {

	@Resource
	private IDeptService deptService;

	@Override
	public List<Map<String, Object>> selectWorkerReport(Page<WorkerMasterVO> page, Map<String, Object> map) {
		Integer deptId;
		try{
			deptId = Integer.parseInt(map.get("deptId").toString());
		}catch(NumberFormatException e){
			deptId = 0;
		}
		if (deptId == 0) {
            deptId = ShiroKit.getUser().getDeptId();
        }
        //获取公司以及子公司社会信用代码集合
        List<String> list = deptService.getOrganizationCodeByDeptId(deptId);
		map.put("depts", list);
		return this.baseMapper.selectWorkerReport(page, map);
	}

	@Override
	public List<WorkerReport> getWorkerReport(Map<String, Object> map) {
		Integer deptId;
		try{
			deptId = Integer.parseInt(map.get("deptId").toString());
		}catch(NumberFormatException e){
			deptId = 0;
		}
		if (deptId == 0) {
			deptId = ShiroKit.getUser().getDeptId();
		}
		//获取公司以及子公司社会信用代码集合
		List<String> list = deptService.getOrganizationCodeByDeptId(deptId);
		map.put("depts", list);
		return this.baseMapper.getWorkerReport(map);
	}

}
