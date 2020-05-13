package com.xywg.admin.modular.report.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.report.model.BirthPlaceReport;

public interface BirthPlaceReportMapper extends BaseMapper<BirthPlaceReport>{
	/**
	 * 籍贯统计
	 * 2018年6月20日
	 *上午11:26:47
	 *@author wangshibo
	 *
	 */
	List<BirthPlaceReport> birthPlaceReport(List<String> organizationCodes);
}
