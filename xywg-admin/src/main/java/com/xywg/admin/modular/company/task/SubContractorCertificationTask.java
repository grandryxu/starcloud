package com.xywg.admin.modular.company.task;

import com.xywg.admin.modular.company.service.ISubContractorCertificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SubContractorCertificationTask {
	@Autowired
	private ISubContractorCertificationsService subContractorCertificationsService;
	/**
	 * 定时修改证书状态
	 */
	//@Scheduled(cron="0 0/1 * * * ?") //每分钟执行一次
	@Scheduled(cron="0 0 0 * * ?")//每天0点开始
	public void updateStatus(){
		subContractorCertificationsService.updateStatus();
	}

/*
	@Scheduled(cron="0/2 0 0/1 * * ?")//2秒执行一次
	public void timeJob1(){
		System.out.println("2s过去了......");
	}*/


	/**
	 * 固定间隔时间执行任务，单位为微秒
	 */
/*	@Scheduled(fixedDelay = 5000)
	public void timeJob() {
		System.out.println("5s过去了......");
	}*/
}
