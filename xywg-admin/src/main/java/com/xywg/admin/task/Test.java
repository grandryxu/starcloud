package com.xywg.admin.task;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.xywg.admin.core.base.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
//@RequestMapping("/test")
public class Test extends BaseController {
	
	public Test (){
		System.out.println("sssssssssssssss");
	}
	
	@Autowired
    public SmzNtGeneralDataJobHandler ss;
	/*@Autowired
    public SendDataToZRTaskHandler ss;*/
	
	
//	@Scheduled(cron = "0 */2 * * * ?")
	//白天定时跑放开这个注释
//	@Scheduled(cron = "0 0 6,7,8,9,10,11,12,13,14,15,16,17,18 * * ? ")
	public void sss() throws Exception {
		System.out.println("xxxxxxxxxxxx");
		ss.execute("");
	}
	
//	@Scheduled(cron = "0 */2 * * * ?")
	//晚上定时跑放开这个注释
//	@Scheduled(cron = "0 0/10 0,1,2,3,19,20,21,22,23 * * ?")
	public void sss1() throws Exception {
		System.out.println("11111111");
		ss.execute1("");
	}
	
}
