package com.xywg.admin.task;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xywg.admin.modular.report.service.IDeviceReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 考勤统计定时任务
 * @author wangcw
 */
@JobHandler(value="DeviceReportDealJobHandler")
@Component
public class DeviceReportDealJobHandler extends IJobHandler {

    @Autowired
    private IDeviceReportService deviceReportService;

	@Override
    public ReturnT<String> execute(String param) throws Exception {
        deviceReportService.deviceReportDeal();
		return SUCCESS;
	}
}