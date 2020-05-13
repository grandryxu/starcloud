package com.xywg.admin.modular.device.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.device.model.dto.DeviceRecordDto;
import com.xywg.admin.modular.device.model.dto.DeviceRecordDto2;
import com.xywg.admin.modular.project.model.AppProjectListByPersonVo;
import com.xywg.admin.modular.report.model.DeviceReport;

/**
 * <p>
 * 考勤记录表 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
public interface IDeviceRecordService extends IService<DeviceRecord> {

	/**
     * 查询考勤记录列表
     * @author duanfen
     * @param page
     * @param map
     * @return
     */
    List<Map<String,Object>> selectList(Page page , Map map);

	/**
	 * 
	 * @description 手机考勤
	 * @author chupp
	 * @date 2018年5月30日
	 * @param deviceRecord
	 *
	 */
	void record(DeviceRecord deviceRecord)throws IOException;

	/**
	 * 
	 * @description 查询工人某天考勤列表
	 * @author chupp
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @param nowDate
	 * @return
	 *
	 */
	List<DeviceRecordDto> getDayRecord(DeviceRecord deviceRecord,String nowDate);

	/**
	 * 
	 * @description 查询工人历史考勤记录
	 * @author chupp
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @return
	 *
	 */
	List<DeviceRecordDto> getRecordsByAll(DeviceRecord deviceRecord);

	/**
	 *
	 * @description 查询工人某天考勤列表
	 * @author cw
	 * @date 2018年9月17日
	 * @param deviceRecord
	 * @param nowDate
	 * @return
	 *
	 */
	List<DeviceRecordDto> v116GetDayRecord(DeviceRecordDto2 deviceRecord, String nowDate);

	/**
	 * 
	 * @description 获取某人在项目下总共出勤天数
	 * @author chupp
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @return
	 *
	 */
	Integer getAllRecordByProject(DeviceRecord deviceRecord);

	/**
	 * 
	 * @description 查询工人在某个项目里的月考勤列表
	 * @author chupp
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @param month
	 * @return
	 *
	 */
	List<String> getMonthRecords(DeviceRecord deviceRecord, String month);

	/**
	 *
	 * @description 查询工人在某个项目里的月考勤列表
	 * @author cw
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @param month
	 * @return
	 *
	 */
	List<String> v116GetMonthRecords(DeviceRecordDto2 deviceRecord, String month);

	/**
	 * 
	 * @description 获取公司的某项目的今日考勤人数
	 * @author chupp
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @return
	 *
	 */
	Integer getWorkerTotalCount(DeviceRecord deviceRecord);

	/**
	 * 
	 * @description 根据考勤记录ID更新是否有效
	 * @author chupp
	 * @date 2018年6月4日
	 * @param id
	 * @param flag
	 *
	 */
	void updateRecordById(Long id, int flag);

	/**
	 * 
	 * @description 获取日期区间考勤天数
	 * @author chupp
	 * @date 2018年6月4日
	 * @param workerList
	 * @param projectCode
	 * @param endDate
	 * @return
	 *
	 */
	List<DeviceRecordDto> getDaySectionRecord(List<Map<String, String>> workerList, String projectCode, String endDate);

	/**
	 * 
	 * @description 获取公司的某项目的最近7天出勤人数
	 * @author chupp
	 * @date 2018年6月4日
	 * @param organizationCode
	 * @param projectCode
	 * @return
	 *
	 */
	List<DeviceRecordDto> getTotalNumFromSevenDays(String organizationCode, String projectCode);

	/**
	 * 
	 * @description 获取班组人员考勤记录
	 * @author chupp
	 * @date 2018年6月4日
	 * @param idCardType
	 * @param idCardNumber
	 * @param teamMemeber
	 * @return
	 *
	 */
	List<DeviceRecordDto> getByClass(String idCardType, String idCardNumber, String teamMemeber);

	/**
	 * 
	 * @description 获取班组指定人员历史考勤记录
	 * @author chupp
	 * @date 2018年6月4日
	 * @param idCardType
	 * @param idCardNumber
	 * @param teamMemeberIdCardType
	 * @param teamMemeberIdCardNumber
	 * @return
	 *
	 */
	List<DeviceRecordDto> getByClassMemeber(String idCardType, String idCardNumber, String teamMemeberIdCardType,
			String teamMemeberIdCardNumber);

	/**
	 * 根据登录这获取项目
	 * 2018年6月13日
	 *上午9:00:12
	 *@author wangshibo
	 *
	 */
	List<AppProjectListByPersonVo> getProjectsByUserId(Integer userId);

	/**
	 * 新增手机考勤
	 * 
	 * @param params
	 * @return int
	 * @author duanfen
	 */
	int saveDevice(DeviceRecordDto record);

	/**
	 * 
	 * @description 查询工人某天考勤信息
	 * @author chupp
	 * @date 2018年6月20日
	 * @param id
	 * @param nowDate
	 * @return
	 *
	 */
	Map<String,Object> getDayRecords(Long id, String nowDate);

	/**
	 * 新增补考勤记录
	 * 
	 * @author: duanfen
	 * @version: 2018年6月21日 上午11:14:06
	 */
    public int saveDeviceRecord(DeviceRecord deviceRecord);

	/**
	 * 
	 * @description 获取今日考勤人数
	 * @author chupp
	 * @date 2018年6月21日
	 * @return
	 *
	 */
	int getTotalDevice();

	/**
	 * 
	 * @description 获取项目当天考勤情况
	 * @author chupp
	 * @date 2018年6月22日
	 * @return
	 *
	 */
	List<DeviceRecordDto> getProjectDevice();

	/**
	 * 根据id查询考勤记录
	 * 
	 * @author: duanfen
	 * @version: 2018年6月22日 下午6:03:57
	 */
	Map<String, Object> findById(Long id,String tableName);

	/**
	 * 
	 * @description 获取公司的某项目的今日考勤人数(手机端)
	 * @author chupp
	 * @date 2018年6月28日
	 * @param deviceRecord
	 * @return
	 *
	 */
	Integer getWorkerTotalCountFromPhone(DeviceRecord deviceRecord);

	/**
	 * 
	 * @description 获取实名制考勤记录（盐城）
	 * @author chupp
	 * @param myc 
	 * @date 2018年7月5日
	 *
	 */
	void saveDeviceRecordFromSMZYC(Map<String, String> myc);

	/**
	 * 
	 * @description 项目考勤详情
	 * @author chupp
	 * @date 2018年7月9日
	 * @param page
	 * @param map
	 * @return
	 *
	 */
	List<Map<String, Object>> getDeviceRecordByProjectCode(Page page, Map map);

	/**
	 *@param m 
	 * @Description: 发送劳务通考勤数据对接到实名制
	 *@Author xieshuaishuai
	 *@Date 2018/7/12 15:31
	 */

	boolean getDeviceRecordFromLabor(Long lastNumber, Map<String, String> m,String tableName) throws Exception;

	boolean getDeviceRecordFromLaborYC(int lastNumber, Map<String, String> m) throws Exception;

	/**
	 * 
	 * @description 获取实名制考勤记录（南通）
	 * @author chupp
	 * @param mnt 
	 * @param list 
	 * @date 2018年7月26日
	 *
	 */
	void saveDeviceRecordFromSMZNT(Map<String, String> mnt, List<SubContractor> list);

	List<DeviceRecordDto> getByClassMemeberV116(String idCardType, String idCardNumber, String teamMemeberIdCardType,
			String teamMemeberIdCardNumber, Integer pageSize, Integer pageNo);

	List<DeviceRecordDto> getByClassV116(String idCardType, String idCardNumber, String teamMemeber, Integer pageSize, Integer pageNo);

	List<String> getMonthRecordsV116(DeviceRecordDto2 deviceRecord, String remark, Integer pageSize, Integer pageNo);

	List<DeviceRecordDto> getDayRecordV116(DeviceRecordDto2 deviceRecord, String remark, Integer pageSize, Integer pageNo);

	List<DeviceRecordDto> getRecordsByAllV116(DeviceRecordDto2 deviceRecord);

	void exportExcel(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map);

	/**
	 * 查询考勤数据图片发送到实名制
	 * @param lastNumber
	 * @param m
	 * @return
	 * @throws Exception
	 */
/*	boolean getDeviceRecordImageFromLabor(Long lastNumber, Map<String, String> m,String tableName) throws Exception;*/
	boolean getDeviceRecordImageFromLabor(Long lastNumber, Map<String, String> m,String tableName) throws Exception;
	/**
	 * 设置加班时间
	 * @param deviceReport
	 */
    void overTime(DeviceReport deviceReport);

	/**
	 * 考勤设置 雨天之类
	 * @param deviceReport
	 */
	void setData(DeviceReport deviceReport);

	void saveDeviceRecordFromSMZTY(Map<String, String> mnt);

	/**
	 * 获取报表
	 * @param page
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getReport(Page page, Map<String, Object> params);

	/**
	 * 导出报表
	 * @param params
	 * @param response
	 */
	void exportReport(Map<String, Object> params, HttpServletResponse response);

	List<Map<String, Object>> getAllForTest(Map<String, Object> params);

	int getAllForTestCount(Map<String, Object> params);

	void updatePic(List<Map<String, Object>> list);

	void saveOldDeviceRecordFromSMZTY(Map<String, String> mnt);

    void addDeviceRecord(List<Object> addList);

    List<DeviceRecord> queryDeviceRecordById(String organizationCode, Long id, String projectyCode);
}
