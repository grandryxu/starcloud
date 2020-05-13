package com.xywg.admin.modular.led.service;

import com.xywg.admin.modular.led.model.Dto.UserProjectForLedDto;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.led.model.UploadUser;
import com.xywg.admin.modular.led.model.UserProjectForLed;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserProjectForLedService extends IService<UserProjectForLed> {
	/**
	 * 根据项目名称获取项目在场人数
	 * @Title: getPrjectUserCount   
	 * @Description: TODO(根据项目名称获取项目在场人数)   
	 * @param: @param projectName
	 * @param: @return      
	 * @return: String      
	 * @throws   
	 * @author:wangshibo
	 */
	public JSONObject getPrjectUserCount(JSONObject json);
	
	/**
	 * 存储上传人员数据
	 * @Title: uploadUser   
	 * @Description: TODO(存储上传人员数据)   
	 * @param: @param uploadUser      
	 * @return: void      
	 * @throws   
	 * @author:wangshibo
	 */
	public void uploadUser(UploadUser uploadUser);

	/**
	 * 根据身份证号，上传单个人员信息
	 * @Title: getUserInfoByIdToLed   
	 * @Description: TODO(根据身份证号，上传单个人员信息)   
	 * @param: @param json
	 * @param: @return      
	 * @return: JSONObject      
	 * @throws   
	 * @author:wangshibo
	 */
	public JSONObject getUserInfoByIdToLed(JSONObject json);

	/**
	 * 根据项目名称，查询出该项目已进场的人员信息
	 * @Title: queryUserInfoByNameToLed   
	 * @Description: TODO(根据项目名称，查询出该项目已进场的人员信息)   
	 * @param: @param json
	 * @param: @return      
	 * @return: JSONObject      
	 * @throws   
	 * @author:wangshibo
	 */
	public JSONArray queryUserInfoByNameToLed(JSONObject json);

	/**
	 * 批量发送人员信息
	 * @Title: queryUserInfoToLed   
	 * @Description: TODO(批量发送人员信息)   
	 * @param: @return      
	 * @return: JSONObject      
	 * @throws   
	 * @author:wangshibo
	 */
	public JSONArray queryUserInfoToLed();

	/**
	 * 根据项目获取考勤统计
	 * @Title: getAttendanceByProjectName   
	 * @Description: TODO(根据项目获取考勤统计)   
	 * @param: @param json
	 * @param: @return      
	 * @return: JSONObject      
	 * @throws   
	 * @author:wangshibo
	 */
	public JSONObject getAttendanceByProjectNameToLed(JSONObject json);

	/**
	 * 伪登录
	 * @return
	 */
	public JSONObject login();

	/**
	 *
	 * @param projectCode
	 * @return
	 */
	List<Map<String,Object>> getCountByProjectCodeToTv(String projectCode);

	/**
	 * 根据项目、身份证号查询人员详情
	 * @param projectCode
	 * @param idCardNumber
	 * @return
	 */
	Map<String,Object>	getWorkerInfo(String projectCode,String idCardNumber);

	/**
	 * 获取项目简介
	 * @param projectCode
	 * @return
	 */
	Map<String,Object> 	getProjectInfo(String projectCode);

	/**
	 * 获取某项目的统计数据
	 * @param projectCode
	 * @return
	 */
	Map<String,Object> getProjectPeople(String projectCode);

	/**
	 * 获取某项目最新进场和离场人员详细信息
	 * @param projectCode
	 * @return
	 */
	List<UserProjectForLedDto> getProjectEnteryExitPeople(String projectCode, int number);

	List<Map<String, Object>> getProjectEnteryExitPeopleThree(String projectCode,int number);



	/**
	 * 获取某项目的统计数据
	 * @param projectCode
	 * @return
	 */
	JSONObject getProjectPeopleToLed(JSONObject json);



	/**
	 * 根据mac查询项目
	 * @param mac
	 * @return
	 */
	Map<String, Object> getProjectByMac(String mac);
}
