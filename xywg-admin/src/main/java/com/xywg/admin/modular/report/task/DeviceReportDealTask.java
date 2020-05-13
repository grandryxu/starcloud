//package com.xywg.admin.modular.report.task;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import com.xywg.admin.modular.report.service.IDeviceReportService;
//
//@Configuration
//@EnableScheduling
//public class DeviceReportDealTask {
//
//	@Autowired
//    private IDeviceReportService deviceReportService;
//
//	/**
//     *
//     * @description 考勤统计定时任务
//     * @author chupp
//     * @date 2018年6月20日
//     *
//     */
//    @Scheduled(cron = "0 30 1,2 * * ?")
//	public void deviceReportDealTask() {
//		deviceReportService.deviceReportDeal();
//	}
//}
