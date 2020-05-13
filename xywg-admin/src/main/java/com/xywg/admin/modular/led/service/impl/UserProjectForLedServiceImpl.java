package com.xywg.admin.modular.led.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xywg.admin.core.util.DateUtil;
import com.xywg.admin.modular.led.model.Dto.UserProjectForLedDto;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xywg.admin.modular.led.dao.UserProjectForLedMapper;
import com.xywg.admin.modular.led.model.AttendanceToLed;
import com.xywg.admin.modular.led.model.UploadUser;
import com.xywg.admin.modular.led.model.UserProjectForLed;
import com.xywg.admin.modular.led.service.UserProjectForLedService;
import com.xywg.admin.modular.smz.utils.ImageUtil;
import com.xywg.admin.modular.worker.service.IWorkerMasterService;

@SuppressWarnings("all")
@Service
public class UserProjectForLedServiceImpl extends ServiceImpl<UserProjectForLedMapper, UserProjectForLed> implements UserProjectForLedService {
	@Autowired
	UserProjectForLedMapper mapper;
	@Autowired
	IWorkerMasterService workerService;

	@Value("${spring.mvc.view.imageLocalPath}")
	private String imagePath;
	@Override
	public JSONObject getPrjectUserCount(JSONObject jsonIn) {
		Map mapIn=JSON.parseObject(jsonIn.toString());
		Integer projectUserCount = mapper.getPrjectUserCount(mapIn.get("projectName").toString());
		Map<String, Object> map = new HashMap<String, Object>(16);
		map.put("userCount", projectUserCount);
		JSONObject fromObject = JSONObject.fromObject(map);
		return fromObject;
	}

	@Override
	public void uploadUser(UploadUser uploadUser) {
		mapper.uploadUser(uploadUser);
	}

	@Override
	public JSONObject getUserInfoByIdToLed(JSONObject json) {
		Map map=JSON.parseObject(json.toString());
		String idCard=map.get("userId").toString();
		String projectName=map.get("projectName").toString();
		//查询人员信息
		UserProjectForLed userInfoByIdToLed = mapper.getUserInfoByIdToLed(projectName, idCard);
		if (userInfoByIdToLed != null) {
			//身份证照片转base64
			String photo = userInfoByIdToLed.getPhoto();
			try {
				userInfoByIdToLed.setPhoto(ImageUtil.GetImageStr(imagePath+ photo));
			} catch (Exception e) {
				userInfoByIdToLed.setPhoto("");
				e.printStackTrace();
			}
		}
		//转为json对象
		JSONObject fromObject = JSONObject.fromObject(userInfoByIdToLed);
		return fromObject;
	}

	@Override
	public JSONArray queryUserInfoByNameToLed(JSONObject json) {
		Map map=JSON.parseObject(json.toString());
		String projectName=map.get("projectName").toString();
		List<UserProjectForLed> list = mapper.queryUserInfoByNameToLed(projectName);
		//循环遍历修改图片
		for (UserProjectForLed userProjectForLed : list) {
			if (userProjectForLed.getPhoto() != null && userProjectForLed.getPhoto() != "") {
				try {
					userProjectForLed.setPhoto(ImageUtil.GetImageStr(imagePath + userProjectForLed.getPhoto()));
				} catch (Exception e) {
					userProjectForLed.setPhoto("");
					System.out.println(userProjectForLed.getUserId());
					e.printStackTrace();
				}
			}
		}
		//转为json对象输出
		JSONArray fromObject = JSONArray.fromObject(list);
		return fromObject;
	}

	@Override
	public JSONArray queryUserInfoToLed() {
		List<UserProjectForLed> list = mapper.queryUserInfoToLed();
		//循环遍历修改图片
		for (UserProjectForLed userProjectForLed : list) {
			if (userProjectForLed.getPhoto() != null && userProjectForLed.getPhoto() != "") {
				try {
					userProjectForLed.setPhoto(ImageUtil.GetImageStr(imagePath + userProjectForLed.getPhoto()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		//转为json对象输出
		JSONArray fromObject = JSONArray.fromObject(list);
		return fromObject;
	}

	@Override
	public JSONObject getAttendanceByProjectNameToLed(JSONObject json) {
		Map map=JSON.parseObject(json.toString());
		String projectName=map.get("projectName").toString();
		AttendanceToLed attendanceToLed=new AttendanceToLed();
		attendanceToLed.setCount(mapper.getCountByProjectNameToLed(projectName));
		attendanceToLed.setRecord(mapper.getRecordByProjectNameToLed(projectName));
		JSONObject jsonObject = JSONObject.fromObject(attendanceToLed);
		return jsonObject;
	}

	@Override
	public JSONObject login() {
		Map<String, Object> map = new HashMap<String, Object>(16);
		map.put("code",200);
		JSONObject fromObject = JSONObject.fromObject(map);
		return  fromObject;
	}

	@Override
	public List<Map<String, Object>> getCountByProjectCodeToTv(String projectCode) {
		return mapper.getCountByProjectCodeToTv(projectCode,DateUtil.getDay());
	}


	@Override
	public Map<String,Object>	getWorkerInfo(String projectCode,String idCardNumber){
		//return mapper.getWorkerInfoToTv(projectCode,DateUtil.getDay());
		return mapper.getWorkerInfoToTv(projectCode, idCardNumber);
	}


	@Override
	public Map<String,Object>	getProjectInfo(String projectCode){
		return mapper.getProjectInfoToTv(projectCode);

	}

	@Override
	public 	Map<String,Object> getProjectPeople(String projectCode) {
		Map<String, Object> result=new HashMap<>();
		result.put("projectPeopleNumber",mapper.getProjectPeopleNumber(projectCode));
		List<Map<String,Object>> allInPeople=mapper.getPeopleOnTheSpotInfo(projectCode,DateUtil.getDay());

		result.put("peopleOnTheSpotNumber",null==allInPeople ? 0 : allInPeople.size());
		result.put("checkInNumber",mapper.getCheckInNumber(projectCode,DateUtil.getDay()));
		result.put("workerIdCardInfoList",allInPeople);

		result.put("checkInIdCardInfoList", mapper.getWorkerInfoByDay(projectCode, DateUtil.getDay()));
		return result;
	}

	@Override
	public List<UserProjectForLedDto> getProjectEnteryExitPeople(String projectCode, int number) {
		return mapper.getProjectEnteryExitPeople(projectCode,number);
	}
	@Override
	public List<Map<String, Object>> getProjectEnteryExitPeopleThree(String projectCode,int number) {
		return mapper.getProjectEnteryExitPeopleThree(projectCode,number);
	}



	@Override
	public JSONObject getProjectPeopleToLed(JSONObject json) {
		Map map = JSON.parseObject(json.toString());
		String projectName = map.get("projectName").toString();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("projectPeopleNumber", mapper.getProjectPeopleNumberByName(projectName));
		List<Map<String, Object>> allInPeople = mapper.queryPeronByled(projectName, DateUtil.getDay());

		result.put("peopleOnTheSpotNumber", null == allInPeople ? 0 : allInPeople.size());
		result.put("checkInNumber", mapper.getCheckInNumberByName(projectName, DateUtil.getDay()));
		result.put("workerIdCardInfoList", allInPeople);

		JSONObject fromObject = JSONObject.fromObject(result);
		return fromObject;
	}



	@Override
	public Map<String, Object> getProjectByMac(String mac) {
		if (StringUtils.isBlank(mac)) {
			return null;
		}
		return mapper.getProjectByMac(mac);
	}

}
