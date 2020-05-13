package com.xywg.admin.rest.modular.tv;

import com.xywg.admin.modular.led.model.Dto.UserProjectForLedDto;
import com.xywg.admin.rest.common.persistence.model.R;
import io.swagger.annotations.Api;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.xywg.admin.core.base.controller.BaseController;
import com.xywg.admin.modular.led.service.UserProjectForLedService;

import java.util.List;
import java.util.Map;

/**
 * LED数据接口
 * @ClassName:  UserProjectForLedController   
 * @Description:TODO(LED数据接口)   
 * @author: wangshibo 
 * @date:   2018年7月17日 上午9:50:53   
 *     
 * @Copyright: 2018 www.wangshibo.com Inc. All rights reserved. 
 * 注意：本内容仅限于江苏星云网格信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */

@RestController
@RequestMapping("/userProjectTv")
@Api(value = "UserProjectForTvController",description = "项目班组控制类")
public class UserProjectForTvController{
	@Autowired
	public UserProjectForLedService service;
	
	/**
	 * 获取某项目下的班组列表
	 * @Title: getTeamUserCount
	 * @throws   
	 * @author:wangshibo
	 */
	@RequestMapping(value="/getTeamUserCount", method = RequestMethod.POST)
	@ResponseBody
	public R getTeamInfoLike(@RequestParam String projectCode) {
		List<Map<String, Object>> teamInfos = service.getCountByProjectCodeToTv(projectCode);
		if(teamInfos==null){
			Object temp=new Object();
			return  R.ok(temp);
		}
		return R.ok((Object)teamInfos);
	}


	/**
	 * 获取某项目下的工人的详细信息
	 * @Title: getWorkerInfo
	 * @throws
	 * @author:wangshibo
	 */
	@RequestMapping(value="/getWorkerInfo", method = RequestMethod.POST)
	@ResponseBody
	public R getWorkerInfo(@RequestParam String projectCode,@RequestParam String idCardNumber,@RequestParam Integer type) {
		Map<String, Object> workerInfo = service.getWorkerInfo(projectCode,idCardNumber);
		//workerInfo.put("type",type);
		if(workerInfo==null){
			Object temp=new Object();
			return  R.ok(temp);
		}
		workerInfo.put("deviceType", type);
		return R.ok((Object)workerInfo);
	}
	/**
	 * 获取某项目简介信息
	 * @Title: getWorkerInfo
	 * @throws
	 * @author:wangshibo
	 */
	@RequestMapping(value="/getProjectInfo", method = RequestMethod.POST)
	@ResponseBody
	public R getProjectInfo(@RequestParam String projectCode){
		Map<String, Object> workerInfo = service.getProjectInfo(projectCode);
		if(workerInfo==null){
			Object temp=new Object();
			return  R.ok(temp);
		}
		return R.ok((Object)workerInfo);
	}
	/**
	 * 获取某项目的统计数据
	 * @Title: getWorkerInfo
	 * @throws
	 * @author:wangshibo
	 */
	@RequestMapping(value="/getProjectPeople", method = RequestMethod.POST)
	@ResponseBody
	public R getProjectPeople(@RequestParam String projectCode){
		Map<String, Object> workerInfo = service.getProjectPeople(projectCode);
		if(workerInfo==null){
			Object temp=new Object();
			return  R.ok(temp);
		}
		return R.ok((Object)workerInfo);
	}


	/**
	 * 获取某项目最新进场和离场人员详细信息
	 * @Title: getWorkerInfo
	 * @throws
	 * @author:wangshibo
	 */
	@RequestMapping(value="/getProjectEnteryExitPeople", method = RequestMethod.POST)
	@ResponseBody
	public R getProjectEnteryExitPeople(@RequestParam String projectCode){
		//List<Map<String, Object>> workerInfo = service.getProjectEnteryExitPeople(projectCode,1);
		List<UserProjectForLedDto> workerInfo = service.getProjectEnteryExitPeople(projectCode, 1);
		if(workerInfo==null){
			Object temp=new Object();
			return  R.ok(temp);
		}
		return R.ok(workerInfo);
	}

	/**
	 * 获取某项目最新进场和离场人员详细信息 3个
	 * @Title: getWorkerInfo
	 * @throws
	 * @author:wangshibo
	 */
	@RequestMapping(value="/getProjectEnteryExitThreePeople", method = RequestMethod.POST)
	@ResponseBody
	public R getProjectEnteryExitThreePeople(@RequestParam String projectCode){
		List<Map<String, Object>> workerInfo = service.getProjectEnteryExitPeopleThree(projectCode,3);
		if(workerInfo==null){
			Object temp=new Object();
			return  R.ok(temp);
		}
		return R.ok(workerInfo);
	}



	/**
	 * 根据mac获取项目编号 @Title: getProjectByMac @throws
	 */
	@RequestMapping(value = "/getProjectByMac", method = RequestMethod.POST)
	@ResponseBody
	public R getProjectByMac(@RequestParam String mac) {
		Map<String, Object> projectInfo = service.getProjectByMac(mac);
		if (projectInfo == null) {
			Object temp = new Object();
			return R.ok(temp);
		}
		return R.ok((Object) projectInfo);
	}
}
