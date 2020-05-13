package com.xywg.admin.modular.led.dao;

import java.util.List;
import java.util.Map;

import com.xywg.admin.modular.led.model.Dto.UserProjectForLedDto;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.led.model.AttendanceToLed;
import com.xywg.admin.modular.led.model.Record;
import com.xywg.admin.modular.led.model.UploadUser;
import com.xywg.admin.modular.led.model.UserProjectForLed;

public interface UserProjectForLedMapper extends BaseMapper<UserProjectForLed>{

	/**
	 * 
	 * @Title: getPrjectUserCount   
	 * @Description: TODO(查询该项目目前的进场人数)   
	 * @param: @param projectName
	 * @param: @return      
	 * @return: int      
	 * @throws   
	 * @author:wangshibo
	 */
	int getPrjectUserCount(@Param("projectName") String projectName);

	/**
	 * 存储上传人员信息
	 * @Title: uploadUser   
	 * @Description: TODO(存储上传人员信息)   
	 * @param: @param uploadUser      
	 * @return: void      
	 * @throws   
	 * @author:wangshibo
	 */
	void uploadUser(UploadUser uploadUser);
	
	/**
	 * 根据身份证号，上传单个人员信息
	 * @Title: getUserInfoByIdToLed   
	 * @Description: TODO(根据身份证号，上传单个人员信息)   
	 * @param: @param projectName
	 * @param: @return      
	 * @return: UserProjectForLed      
	 * @throws   
	 * @author:wangshibo
	 */
	UserProjectForLed getUserInfoByIdToLed(@Param("projectName") String projectName,@Param("idCard") String idCard);

	/**
	 * 根据项目名称，查询出该项目已进场的人员信息
	 * @Title: queryUserInfoByNameToLed   
	 * @Description: TODO(根据项目名称，查询出该项目已进场的人员信息)   
	 * @param: @param projectName
	 * @param: @return      
	 * @return: List<UserProjectForLed>      
	 * @throws   
	 * @author:wangshibo
	 */
	List<UserProjectForLed> queryUserInfoByNameToLed(@Param("projectName") String projectName);

	/**
	 * 批量发送人员信息
	 * @Title: queryUserInfoToLed   
	 * @Description: TODO(批量发送人员信息)   
	 * @param: @return      
	 * @return: List<UserProjectForLed>      
	 * @throws   
	 * @author:wangshibo
	 */
	List<UserProjectForLed> queryUserInfoToLed();

	/**
	 * 根据项目统计今日考勤
	 * @Title: getAttendanceByProjectNameToLed   
	 * @Description: TODO(根据项目统计今日考勤)   
	 * @param: @param projectName
	 * @param: @return      
	 * @return: AttendanceToLed      
	 * @throws   
	 * @author:wangshibo
	 */
	int getCountByProjectNameToLed(@Param("projectName") String projectName);
	
	/**
	 * 根据项目统计今日考勤
	 * @Title: getAttendanceByProjectNameToLed   
	 * @Description: TODO(根据项目统计今日考勤)   
	 * @param: @param projectName
	 * @param: @return      
	 * @return: AttendanceToLed      
	 * @throws   
	 * @author:wangshibo
	 */
	List<Record> getRecordByProjectNameToLed(@Param("projectName") String projectName);

	/**
	 * 根据项目编号查找每个班组今日考勤人数
	 */
	List<Map<String,Object>> getCountByProjectCodeToTv(@Param("projectCode") String projectCode, @Param("tTime") String tTime);

	Map<String,Object>	getWorkerInfoToTv(@Param("projectCode") String projectCode,@Param("idCardNumber") String idCardNumber);

	/**
	 * 获取项目
	 * @param projectCode
	 * @return
	 */
	Map<String,Object> getProjectInfoToTv(@Param("projectCode") String projectCode);


	/**
	 * 获取项目人数
	 * @return
	 */
	int getProjectPeopleNumber(@Param("projectCode") String projectCode);




	/**
	 * 考勤人数
	 * @return
	 */
	int getCheckInNumber(@Param("projectCode") String projectCode,@Param("tTime") String tTime);
	/**
	 * 项目在场人员信息
	 * @return
	 */
	List<Map<String,Object>> getPeopleOnTheSpotInfo(@Param("projectCode") String projectCode,@Param("tTime") String tTime);

	/**
	 * 获取某项目最新进场和离场人员详细信息
	 * @return
	 */
	List<UserProjectForLedDto> getProjectEnteryExitPeople(@Param("projectCode") String projectCode,@Param("number") int number);
	/**
	 * 获取某项目最新进场和离场人员详细信息
	 * @return
	 */
	List<Map<String, Object>> getProjectEnteryExitPeopleThree(@Param("projectCode") String projectCode,@Param("number") int number);

	/**
	 * 根据项目名称查询项目人员名称
	 * @param projectName
	 * @return
	 */
	int getProjectPeopleNumberByName(@Param("projectName") String projectName);

	/**
	 * 根据项目名称获取今天在场人员信息
	 * <p>Title: queryPeronByled</p>
	 * <p>Description: </p>
	 * @author duanfen
	 * @date 2019年8月12日
	 */
	List<Map<String,Object>> queryPeronByled(@Param("projectName") String projectName,@Param("tTime") String tTime);


	/**
	 * 查询今日考勤人数
	 * @param projectName
	 * @return
	 */
	int getCheckInNumberByName(@Param("projectName") String projectName,@Param("tTime") String tTime);


	/**
	 * 考勤人信息
	 * @return
	 */
	List<UserProjectForLedDto> getWorkerInfoByDay(@Param("projectCode") String projectCode, @Param("tTime") String tTime);


	/**
	 * 根据mac查询项目编号
	 * @param mac
	 * @return
	 */
	Map<String, Object> getProjectByMac(@Param("mac") String mac);
}
