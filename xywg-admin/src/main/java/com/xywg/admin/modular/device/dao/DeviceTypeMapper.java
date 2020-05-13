package com.xywg.admin.modular.device.dao;

import com.xywg.admin.modular.device.model.DeviceType;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 考勤机类型 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-12
 */
public interface DeviceTypeMapper extends BaseMapper<DeviceType> {

	/**
	 * 
	 * @param @param paramString
	 * @param @return
	 * @Description: 获取id
	 * @author cxl
	 * @date 2018年1月4日 上午10:28:02
	 */
	Long getDeviceTypeId(@Param("type") String type);

	/**
	 * 
	 * @param @param deviceType
	 * @Description: 添加设备
	 * @author cxl
	 * @date 2018年1月4日 上午10:26:27
	 */
	void addDeviceType(@Param("d") DeviceType model);

}
