package com.xywg.admin.modular.report.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.core.shiro.ShiroKit;
import com.xywg.admin.modular.report.dao.BirthPlaceReportMapper;
import com.xywg.admin.modular.report.model.BirthPlaceReport;
import com.xywg.admin.modular.report.service.BirthPlaceReportService;
import com.xywg.admin.modular.system.service.IDeptService;

@Service
public class BirthPlaceReportServiceImpl extends ServiceImpl<BirthPlaceReportMapper, BirthPlaceReport> implements BirthPlaceReportService {
	   @Autowired
	    private IDeptService deptService;
	   
	@Override
	public List<BirthPlaceReport> birthPlaceReport(Integer deptId) {
		 if (deptId == null) {
	            deptId = ShiroKit.getUser().getDeptId();
	        }
	        //获取公司以及子公司社会信用代码集合
	        List<String> list = deptService.getOrganizationCodeByDeptId(deptId);
		List<BirthPlaceReport> birthPlaceReport = baseMapper.birthPlaceReport(list);
		return birthPlaceReport;
	}

}
