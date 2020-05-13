package com.xywg.admin.modular.device.dao;

import com.xywg.admin.modular.device.model.DeviceRecord;
import com.xywg.admin.modular.device.model.dto.DeviceRecordDto;
import com.xywg.admin.modular.device.model.dto.DeviceRecordDto2;
import com.xywg.admin.modular.project.model.AppProjectListByPersonVo;
import com.xywg.admin.modular.project.model.ProjectWorker;
import com.xywg.admin.modular.report.model.DeviceReport;
import com.xywg.admin.modular.report.model.dto.DeviceReportDto;

import java.util.List;
import java.util.Map;

import com.xywg.admin.modular.smz.model.DeviceRecordMo;
import com.xywg.admin.modular.system.model.SwitchType;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * <p>
 * 考勤记录表 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
public interface DeviceRecordMapper extends BaseMapper<DeviceRecord> {

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
	List<DeviceRecordDto> getDayRecord(@Param("d") DeviceRecord deviceRecord,@Param("n") String nowDate);

	/**
	 * 查詢可见org
	 * @param deptId
	 * @return
	 */
	List<String> findOrgCodes(Integer deptId);

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
	List<DeviceRecordDto> v116GetDayRecord(@Param("d") DeviceRecordDto2 deviceRecord, @Param("n") String nowDate);

	/**
	 * 
	 * @description 查询工人历史考勤记录
	 * @author chupp
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @return
	 *
	 */
	List<DeviceRecordDto> getRecordsByAll(@Param("d") DeviceRecord deviceRecord);

	/**
	 * 
	 * @description 获取某人在项目下总共出勤天数
	 * @author chupp
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @return
	 *
	 */
	Integer getAllRecordByProject(@Param("d") DeviceRecord deviceRecord);

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
	List<String> getMonthRecords(@Param("d") DeviceRecord deviceRecord, @Param("m") String month);

	/**
	 *
	 * @description 查询工人在某个项目里的月考勤列表
	 * @author cw
	 * @date 2018年9月17日
	 * @param deviceRecord
	 * @param month
	 * @return
	 *
	 */
	List<String> v116GetMonthRecords(@Param("d") DeviceRecordDto2 deviceRecord, @Param("m") String month);

	/**
	 * 
	 * @description 获取公司的某项目的今日考勤人数
	 * @author chupp
	 * @date 2018年5月31日
	 * @param deviceRecord
	 * @return
	 *
	 */
	Integer getWorkerTotalCount(@Param("d") DeviceRecord deviceRecord);
	Integer getWorkerTotalCountFromCompany(@Param("list") List<String> list, @Param("pc") String projectCode);
	/**
	 * 
	 * @description 根据考勤记录ID更新是否有效
	 * @author chupp
	 * @date 2018年6月4日
	 * @param id
	 * @param flag
	 *
	 */
	void updateRecordById(@Param("i") Long id, @Param("f") int flag);

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
	List<DeviceRecordDto> getDaySectionRecord(@Param("wl") List<Map<String, String>> workerList, 
			@Param("pc") String projectCode, @Param("ed") String endDate);

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
	List<DeviceRecordDto> getTotalNumFromSevenDays(@Param("oc") String organizationCode, @Param("pc") String projectCode);
	
	List<DeviceRecordDto> getTotalNumFromSevenDaysFromCompany(@Param("list") List<String> list, @Param("pc") String projectCode);

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
	List<DeviceRecordDto> getByClass(@Param("ict") String idCardType, @Param("icn") String idCardNumber, 
			@Param("tm") String teamMemeber);

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
	List<DeviceRecordDto> getByClassMemeber(@Param("ict") String idCardType, @Param("icn") String idCardNumber, 
			@Param("tmict") String teamMemeberIdCardType, @Param("tmicn") String teamMemeberIdCardNumber);

	/**
	 * 根据登录用户获取项目
	 * 2018年6月13日
	 *上午9:01:55
	 *@author wangshibo
	 *
	 */
	public List<AppProjectListByPersonVo> getProjectsByUserId(@Param("userId")Integer userId);

	/**
     * @param @param paramList
     * @Description: 插入考勤记录
     * @author cxl
     * @date 2017年12月27日 下午2:17:41
     */
	void saveRecord(@Param("deviceRecords") List<DeviceRecord> recordSave);

	/**
     * @param @param  paramList
     * @param @return
     * @Description: TODO
     * @author cxl
     * @date 2017年12月28日 上午9:57:08
     */
	List<ProjectWorker> getProPerInfo(@Param("deviceRecords") List<DeviceRecord> recordSave);

	/**
	 * 
	 * @description 查询未统计考勤记录
	 * @author chupp
	 * @date 2018年6月16日
	 * @return
	 *
	 */
	List<DeviceReport> getRecordListByIsDeal();

	/**
	 * 
	 * @description 按照5属性查询考勤记录,公司,项目,班组,人员,时间
	 * @author chupp
	 * @date 2018年6月16日
	 * @param deviceReport
	 * @return
	 *
	 */
	List<DeviceReportDto> getRecordListBy5Fields(@Param("d") DeviceReport deviceReport);

	/**
	 * 
	 * @description 更新考勤记录处理标记
	 * @author chupp
	 * @date 2018年6月16日
	 * @param deviceReport
	 *
	 */
	void updateIsDeal(@Param("d") DeviceReport deviceReport);

	/**
	 * 新增手机考勤
	 * 
	 * @param params
	 * @return ResultVo
	 * @author duanfen
	 */
	int saveDevice(@Param("t") DeviceRecordDto record);

	/**
	 * 获取考勤机列表
	 * 
	 * @author: duanfen
	 * @version: 2018年6月21日 下午2:31:16
	 */
    List<Map<String,Object>> list(@Param("page") Page page, @Param("map") Map map, @Param("switchType") SwitchType switchType);

	/**
	 * 新列表
	 * @param map
	 * @param switchType
	 * @return
	 */

	List<Map<String,Object>> getList(@Param("map") Map map, @Param("switchType") SwitchType switchType);
	List<Map<String,Object>> getList1(@Param("page") Page page,@Param("map") Map map, @Param("switchType") SwitchType switchType);

	/**
	 * 新列表
	 * @param map
	 * @param switchType
	 * @return
	 */
	List<Map<String,Object>> getList(@Param("map") Map map, @Param("switchType") SwitchType switchType, @Param("page") Page page);
	/**
	 * 自定义分页
	 * @param map
	 * @param switchType
	 * @return
	 */
	int listCount(@Param("map") Map map, @Param("switchType") SwitchType switchType);

	/**
     *  新增补考勤
	 * 
	 * @author: duanfen
	 * @version: 2018年6月21日 下午2:30:44
     */
	int insertRepair(@Param("t") DeviceRecord record);

	/**
	 * 
	 * @description 获取今日考勤人数
	 * @author chupp
	 * @date 2018年6月21日
	 * @param organizationCode
	 * @param time
	 * @param projectCodes
     * @return
	 *
	 */
	Integer getTotalDevice(@Param("list") List<String> list, @Param("time") String time, @Param("projectCodes") List<String> projectCodes);

	/**
	 * 
	 * @description 获取项目当天考勤情况
	 * @author chupp
	 * @date 2018年6月22日
	 * @param list
	 * @param time
	 * @param projectCodes
     * @return
	 *
	 */
	List<DeviceRecordDto> getProjectDevice(@Param("list") List<String> list, @Param("time") String time, @Param("projectCodes") List<String> projectCodes);

	/**
	 * 根据id查询考勤记录
	 * 
	 * @author: duanfen
	 * @version: 2018年6月22日 下午6:03:57
	 */
	Map<String, Object> findById(@Param("map") Map<String,Object> map);
	
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
	List<Map<String,Object>> getDeviceRecordByProjectCode(@Param("page") Page page , @Param("map") Map map);

	/**
	 * 新增考勤记录(考勤异常处理完)
	 * @param deviceRecord
	 * @author yuanyang
	 */
	int insertDeviceRecord(@Param("t")DeviceRecord deviceRecord);

	List<DeviceRecordMo> getDeviceRecordFromLabor(@Param("lastNumber") Long lastNumber);

	List<DeviceRecordMo> getDeviceRecordFromLaborYC(@Param("lastNumber") int lastNumber);

	Integer getWorkerTotalCountReport(@Param("deviceRecord") DeviceRecord deviceRecord, @Param("switchType") SwitchType switchType,@Param("deptId") Integer deptId);

	/**
	 * 
	 * @description 
	 * @author chupp
	 * @date 2018年9月3日
	 * @param lablorId
	 * @param sn
	 *
	 */
	void updateSn(@Param("id") Long lablorId, @Param("sn") String sn);

	/**
	 * 
	 * @description 
	 * @author chupp
	 * @date 2018年9月12日
	 * @param projectCode
	 * @param deviceId
	 *
	 */
	void updateSnList(@Param("pc") String projectCode, @Param("di") Long deviceId);

	List<DeviceRecordDto> getByClassMemeberV116(@Param("ict") String idCardType, @Param("icn") String idCardNumber, 
			@Param("tmict") String teamMemeberIdCardType, @Param("tmicn") String teamMemeberIdCardNumber, 
			@Param("ps") int pageSize, @Param("pn") int pageNo);

	List<DeviceRecordDto> getByClassV116(@Param("ict") String idCardType, @Param("icn") String idCardNumber, 
			@Param("tm") String teamMemeber, @Param("ps") int pageSize, @Param("pn") int pageNo);

	List<String> getMonthRecordsV116(@Param("d") DeviceRecord deviceRecord, @Param("m") String month, @Param("ps") int pageSize, @Param("pn") int pageNo);

	List<DeviceRecordDto> getDayRecordV116(@Param("d") DeviceRecord deviceRecord,@Param("n") String nowDate, @Param("ps") int pageSize, @Param("pn") int pageNo);

	List<DeviceRecordDto> getRecordsByAllV116(@Param("d") DeviceRecordDto2 deviceRecord);
	List<Map<String, Object>> getExportExcel(@Param("map") Map map, @Param("switchType") SwitchType switchType);
	
	/**
	 * 查询考勤图片发送到实名制
	 * @author duanfen
	 * @param lastNumber
	 * @return
	 */
	List<DeviceRecordMo> getDeviceRecordImageFromLabor(@Param("lastNumber") Long lastNumber);


	/**
	 * 添加加班
	 * @param deviceReport
	 */
    void overTime(DeviceReport deviceReport);

	/**
	 * 统计设置
	 * @param deviceReport
	 */
	void setData(DeviceReport deviceReport);

	/**
	 *临时获取，之后要删除
	 * @param params
	 * @return
	 */
	int getAllForTestCount(Map<String, Object> params);

	/**
	 * 临时处理图片加水印问题
	 * @return
	 */
	List<Map<String, Object>> getAllForTest(Map<String, Object> params);

	/**
	 * 临时处理 水印修改
	 * @param list
	 */
	void updatePic(List<Map<String, Object>> list);

	/**
	 * 查询报表列表 分页
	 * @param page
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getReport(@Param("page")Page<Map<String, Object>> page, @Param("map")Map<String, Object> map);

	/**
	 * 獲取导出的数据列表
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> getReport(@Param("map")Map<String, Object> map);

	List<DeviceRecordMo> getZrAttendance(@Param("id") Long id);

	String queryUserNameByParam(@Param("id")Long id,@Param("idCardNumber") String idCardNumber);
	/**
	 * 查询单条考勤发送到实名制
	 * @author duanfen
	 * @param lastNumber
	 * @return
	 */
	List<DeviceRecordMo> getDeviceRecordByIdToSmz(@Param("id") Long id);

    List<DeviceRecord> queryDeviceRecordById(@Param("organizationCode") String organizationCode, @Param("id") Long id, @Param("projectyCode") String projectyCode);

}