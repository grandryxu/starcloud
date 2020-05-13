package com.xywg.admin.modular.device.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.core.shiro.ShiroUser;
import com.xywg.admin.modular.device.model.DeviceCommand;
import com.xywg.admin.modular.wages.model.Settlement;

/**
 * <p>
 * 设备下发命令 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-12
 */
public interface IDeviceCommandService extends IService<DeviceCommand> {
	
	
	/**
	 * 
	* @param @param command
	* @param @return
	* @param @throws Exception 
	* @Description: 新增命令
	* @author cxl  
	* @date 2018年1月8日 下午6:59:43
	 */
	String executeCommand(List<String> deviceIds, int type,List<String> workerIds ) throws Exception;

	/**
	 * 插入命令
	 * @param command
	 * @return
	 */
	Object executeCommand(DeviceCommand command);
	/**
	 * 
	 * @param @param deviceId
	 * @param @return 
	 * @Description: 获取命令个数
	 * @author cxl  
	 * @date 2018年1月8日 下午6:59:04
	 */
	int getDeviceCommandCount(String id);

	/**
	 * 
	 * @param @param paramCommandTpm 
	 * @Description: 设置命令状态
	 * @author cxl  
	 * @date 2018年1月8日 下午6:59:32
	 */
	void setCommandState(DeviceCommand command);

	/**
	 * 
	 * @param @param paramLong
	 * @param @return 
     * @Description: 获取命令
	 * @author cxl  
	 * @date 2018年1月8日 下午6:59:21
	 */
	List<DeviceCommand> getDeviceCommand(String sn);
	
	
	/**
	 * 
	* @param @param deviceId
	* @param @return 
	* @Description: 获取命令个数
	* @author cxl  
	* @date 2018年1月8日 下午6:59:04
	 */
	int getDeviceCommandCount(Long deviceId);
	
	/**
	 * 
	* @param @param paramLong
	* @param @return 
	* @Description: 获取命令
	* @author cxl  
	* @date 2018年1月8日 下午6:59:21
	 */
	List<DeviceCommand> getDeviceCommand(Long paramLong);
	
	/**
	 * 
	* @param @param id
	* @param @param user
	* @param @throws Exception 
	* @Description: 取消命令
	* @author cxl  
	* @date 2018年1月8日 下午6:59:53
	 */
	void cancelCommand(Long id,ShiroUser user) throws Exception ;
	
	/**
	 * 
	* @param @param paramCommandTpm
	* @param @return 
	* @Description: 查询历史命令
	* @author cxl  
	* @date 2018年1月8日 下午7:00:11
	 */
	List<DeviceCommand> queryCommand(Map<String, Object> params);



	List<DeviceCommand> queryCommandBySeries(Page<DeviceCommand> page, Map<String, Object> map);


	/**
	 * 给考勤机下发工人信息
	 * @param command
	 */
	void dispatchUser(DeviceCommand command) ;

    void addDeviceCommand(List<Object> addList);

}
