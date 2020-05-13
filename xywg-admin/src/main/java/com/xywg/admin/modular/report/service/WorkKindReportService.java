package com.xywg.admin.modular.report.service;

import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.report.model.WorkKindReport;

import java.util.List;

/**
 * <p>
 * 工人统计表 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-16
 */
public interface WorkKindReportService extends IService<WorkKindReport> {


    List<WorkKindReport> getWorkKindList(Integer deptId);
}
