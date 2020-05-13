package com.xywg.admin.modular.report.service;


import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.report.model.DistrictReport;

import java.util.List;

/**
 * 区域统计项目service
 * @author hujingyun
 */
public interface DistrictReportService extends IService<DistrictReport> {

	/**
	 * 根据部门id 按区域分组获取项目统计数据
	 * @param deptId  部门id
	 * @return
	 */
	List<DistrictReport>  getReportListByDistrictAndDeptId(Integer deptId);

}
