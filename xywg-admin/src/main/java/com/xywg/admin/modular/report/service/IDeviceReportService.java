package com.xywg.admin.modular.report.service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.report.model.DeviceReport;
import com.xywg.admin.modular.report.model.DeviceReportVo;
import com.xywg.admin.modular.report.model.dto.DeviceReportDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 考勤统计表 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-16
 */
public interface IDeviceReportService extends IService<DeviceReport> {

	/**
	 * 
	 * @description 考勤统计
	 * @author chupp
	 * @date 2018年6月16日
	 *
	 */
	void deviceReportDeal();

	/**
	 * 
	 * @description 处理考勤记录
	 * @author chupp
	 * @date 2018年6月16日
	 * @param deviceRecordReport
	 * @param list
	 * @throws ParseException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 *
	 */
	void dealDeviceRecord(DeviceReport deviceRecordReport, List<DeviceReportDto> list)
			throws ParseException, NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException;

	/**
	 * 
	 * @description 获取考勤时间总和
	 * @author chupp
	 * @date 2018年6月16日
	 * @param list
	 * @param longs
	 * @return
	 * @throws ParseException
	 *
	 */
	Double getTimeTotal(List<DeviceReportDto> list, Long[] longs) throws ParseException;

	/**
	 * 
	 * @description 获取区间有效考勤时间
	 * @author chupp
	 * @date 2018年6月16日
	 * @param start
	 * @param end
	 * @param longs
	 * @return
	 *
	 */
	Long getEveryTime(Long start, Long end, Long[] longs);

	/**
	 * 
	 * @description 获取项目实际进出时间
	 * @author chupp
	 * @date 2018年6月16日
	 * @param projectCode
	 * @param prefixDate
	 * @return
	 * @throws ParseException
	 *
	 */
	Long[] getProjectInOutTime(String projectCode, String prefixDate) throws ParseException;

	/**
	 * 
	 * @description 数据查询
	 * @author chupp
	 * @date 2018年6月20日
	 * @param page
	 * @param map
	 * @return
	 *
	 */
	List<DeviceReportVo>
	getList(Page<DeviceReportVo> page, Map<String, Object> map);

	/**
	 * 导出查询
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getExportList(Map<String, Object> params);


	/**
	 * 导出
	 * @param res
	 * @param req
	 * @param params
	 * @author yuanyang
	 */
	void download(HttpServletResponse res, HttpServletRequest req, Map<String, Object> params);

	/**
	 * 加入雨天download
	 * @param res
	 * @param req
	 * @param params
	 */
	void downloadNew(HttpServletResponse res, HttpServletRequest req, Map<String, Object> params);

	/**
	 * 统计工数
	 * @param projectCode
	 * @param deviceReportId
	 */
	void countTotalTime(DeviceReport deviceReport);
}
