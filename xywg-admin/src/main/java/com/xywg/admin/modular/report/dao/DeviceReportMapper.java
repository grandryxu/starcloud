package com.xywg.admin.modular.report.dao;

import java.util.List;
import java.util.Map;

import com.xywg.admin.modular.report.model.DeviceReportVo;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.report.model.DeviceReport;

/**
 * <p>
 * 考勤统计表 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-16
 */
public interface DeviceReportMapper extends BaseMapper<DeviceReport> {

	/**
	 * 
	 * @description 根据主要属性查询考勤统计记录
	 * @author chupp
	 * @date 2018年6月16日
	 * @param deviceReport
	 * @return
	 *
	 */
	List<DeviceReport> getDeviceReportBy5Fields(@Param("d") DeviceReport deviceReport);

	/**
	 * 
	 * @description 保存考勤统计记录
	 * @author chupp
	 * @date 2018年6月16日
	 * @param dr
	 *
	 */
	void saveDeviceReport(@Param("d") DeviceReport dr);

	/**
	 * 
	 * @description 更新考勤统计记录
	 * @author chupp
	 * @date 2018年6月16日
	 * @param dr
	 *
	 */
	void updateDeviceReport(@Param("d") DeviceReport dr);

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
	List<DeviceReportVo> getList(@Param("page")Page<DeviceReportVo> page, @Param("map")Map<String, Object> map);

	/**
	 * 导出查询
	 * @param params
	 * @return
	 * @author yuanyang
	 */
	List<Map<String, Object>> getExportList(@Param("map")Map<String, Object> params);

	/**
	 * 导出查询(新增加下雨天之类)
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getExportNewList(@Param("map")Map<String, Object> params);

	/**
	 * 设置总工数
	 * @param workTime
	 * @param deviceReportId
	 */
    void countTotalTime(@Param("workTime") Integer workTime, @Param("id") Long deviceReportId);

    void countTotalTimeByProjectCode(@Param("workTime") Integer workTime, @Param("projectCode") String projectCode);
}
