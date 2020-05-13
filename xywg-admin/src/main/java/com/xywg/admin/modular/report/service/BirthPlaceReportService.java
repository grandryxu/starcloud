package com.xywg.admin.modular.report.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.report.model.BirthPlaceReport;
/**
 * 籍贯统计service接口
 * @author wangshibo
 *	2018年6月20日
 * 下午2:13:35
 */
public interface BirthPlaceReportService  extends IService<BirthPlaceReport>{
	/**
	 * 籍贯统计
	 * 2018年6月20日
	 *下午2:16:11
	 *@author wangshibo
	 *
	 */
	List<BirthPlaceReport> birthPlaceReport(Integer deptId);
	
}
