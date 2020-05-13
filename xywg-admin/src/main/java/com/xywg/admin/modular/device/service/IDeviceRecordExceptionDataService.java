package com.xywg.admin.modular.device.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.device.model.DeviceRecordExceptionData;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.system.model.Banner;

/**
 * <p>
 * 异常考勤记录表 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-12
 */
public interface IDeviceRecordExceptionDataService extends IService<DeviceRecordExceptionData> {

	/**
     * 录入设备停用考勤信息
     * @param recordExceptionDataList
     */
	void saveDisabledRecord(List<DeviceRecordExceptionData> recordExceptionDataList);

	/**
	 * 分页查询考勤异常列表
	 * @param page
	 * @param map
	 * @return
	 * @author yuanyang
	 */
	List<Map<String,Object>> selectDeviceExceptions(Page<Banner> page, Map<String, Object> map);

	/**
	 * 单条详情
	 * @param id
	 * @return
	 */
    Map<String,Object> getExceptionDeviceById(Integer id);

	/**
	 * 处理异常考勤
	 * @param map
	 * @author yuanyang
	 */
    void action(Map<String, Object> map);

	/**
	 * 启用考勤机禁用时的考勤数据
	 * @param id
	 * @author yuanyang
	 */
	void changeStatus(Integer id);

	/**
	 * 批量删除
	 * @param map
	 */
	void deleteByIds(Map<String, Object> map);

    void addDeviceRecordExceptionData(List<Object> addList);

}
