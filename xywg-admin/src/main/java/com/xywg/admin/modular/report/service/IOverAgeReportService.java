package com.xywg.admin.modular.report.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.report.model.WorkerOverAgeReport;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 超龄工人统计表 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-16
 */
public interface IOverAgeReportService extends IService<WorkerOverAgeReport> {


    List<WorkerOverAgeReport> getWorkerList(Page<WorkerOverAgeReport> page, Map<String, Object> map);

	void download(HttpServletResponse res, HttpServletRequest req, Map<String, Object> params);
}
