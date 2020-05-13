package com.xywg.admin.modular.bad.task;

import com.xywg.admin.modular.bad.service.IWorkerBlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 黑名单过期
 * @author duanfen
 * @date 2018年1月15日 下午4:00:03
 * **/

@Configuration
@EnableScheduling
public class BlackTask {
	@Autowired
	private IWorkerBlackListService iWorkerBlackListService;

	//每天早上0点执行
	@Scheduled(cron = "0 0 0 * * ?")
	public void updateBlack(){
		this.iWorkerBlackListService.updateBlack();
	}
}
