package com.xywg.admin.modular.company.task;

import com.xywg.admin.modular.company.service.IPersonalCertificationsService;
import com.xywg.admin.modular.report.service.IDeviceReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class PersonCertificationTask {

	@Autowired
	private IPersonalCertificationsService personalCertificationsService;

	/**
	 * 定时修改证书状态
	 */
	//@Scheduled(cron="0 0/1 * * * ?") //每分钟执行一次
	@Scheduled(cron="0 0 0 * * ?")//每天0点开始
	public void updateStatus(){
		personalCertificationsService.updateStatus();
	}
}
