package com.xywg.admin.modular.device.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.wages.model.Settlement;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.core.shiro.ShiroUser;
import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.model.DeviceCommand;

/**
 * <p>
 * 设备下发命令 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-12
 */
public interface DeviceCommandMapper extends BaseMapper<DeviceCommand> {
	/**
	 * 
	* @param @param paramLong
	* @param @param paramDate
	* @param @return 
	* @Description: 获取命令
	* @author cxl  
	* @date 2018年1月4日 上午9:15:14
	 */
	List<DeviceCommand> getDeviceCommand(@Param("sn") String sn, @Param("begin") Date paramDate);
	
	
	/**
	 * 
	* @param @param paramLong
	* @param @param paramDate
	* @param @return 
	* @Description: 命令数
	* @author cxl  
	* @date 2018年1月4日 下午3:29:51
	 */
	int getDeviceCommandCount(
			@Param("deviceId") String deviceId , @Param("begin") Date paramDate);
	
	
	/**
	 * 
	* @param @param paramCommandTpm 
	* @Description: 改变状态
	* @author cxl  
	* @date 2018年1月4日 上午9:29:17
	 */
	void setCommandState(DeviceCommand paramCommandTpm);
	
	
	/**
	 * 
	* @param @param paramString 
	* @Description: 插command
	* @author cxl  
	* @date 2018年1月4日 下午2:27:45
	 */
	void askDeviceInfo(@Param("sn") String paramString);
	
	
	
	/**
	 * 
	* @param @param paramCommandTpm
	* @param @param paramDate
	* @param @return 
	* @Description: 查询是否有相同命令
	* @author cxl  
	* @date 2018年1月5日 上午11:21:11
	 */
	List<Device> getSameCommandDevice(
			@Param("command") DeviceCommand paramCommandTpm,
			@Param("begin") Date paramDate);
	
	
	/**
	 * 
	* @param @param paramCommandTpm 
	* @Description: 执行命令
	* @author cxl  
	* @date 2018年1月5日 上午11:24:28
	 */
	void executeCommand(
			@Param("command") DeviceCommand paramCommandTpm);
	
	
	
	/**
	 * 
	* @param @param paramLong
	* @param @param paramUserTpm
	* @param @return 
	* @Description: 撤销命令
	* @author cxl  
	* @date 2018年1月5日 下午2:28:27
	 */
	int cancelCommand(@Param("id") Long paramLong,
			@Param("admin") ShiroUser user);
	
	
	/**
	 * 
	* @param @param paramCommandTpm
	* @param @return 
	* @Description: 查询历史信息
	* @author cxl  
	* @date 2018年1月8日 下午6:40:52
	 */
	public List<DeviceCommand> queryCommand(@Param("query") Map<String, Object> params);
	
	
	/**
	 * 
	* @param @param paramList
	* @param @return 
	* @Description: 判断是否有设备
	* @author cxl  
	* @date 2018年1月9日 上午11:32:57
	 */
	String getDistrictDeviceInfo(@Param("list") List<String> paramList);


	List<DeviceCommand> queryCommandBySeries(@Param("page")Page<DeviceCommand> page, @Param("map")Map<String, Object> map);


}
