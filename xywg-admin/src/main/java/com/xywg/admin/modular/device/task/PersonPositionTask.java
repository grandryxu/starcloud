//package com.xywg.admin.modular.device.task;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import com.xywg.admin.modular.device.service.IPersonPositionService;
//
///**
// * 安全帽考勤
// * @author duanfen
// * @date 2018年1月15日 下午4:00:03
// * **/
//
//@Configuration
//@EnableScheduling
//public class PersonPositionTask {
//	@Autowired
//	private IPersonPositionService personService;
//
//	@Scheduled(cron = "0 */3 * * * ?")
//	public void record(){
//		this.personService.updatePersonPositionInfo();
//		this.personService.insertRecord();
//
//	}
//}
