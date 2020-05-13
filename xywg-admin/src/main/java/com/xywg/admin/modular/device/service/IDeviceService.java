package com.xywg.admin.modular.device.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.modular.company.model.SubContractor;
import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.model.DeviceRecord;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 考勤机 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
public interface IDeviceService extends IService<Device> {

    /**
     * 查询考勤机列表
     * @param page
     * @param map
     * @return
     */
    List<Map<String,Object>> selectList(Page page , Map map);

    /**
     * 考勤机启用禁用
     *
     * @param ids
     * @param state
     * @return
     */
    Integer toggle(String ids, Integer state);

    /**
     * 根据设备类型字典id获取设备
     * @param menuId
     * @return
     */
    List<Device> getDevicesByDeviceType(Long menuId);

    /**
	 * 
	 * @param @param sn
	 * @param @return
	 * @Description: 心跳
	 * @author cxl
	 * @date 2018年1月9日 上午11:27:08
	 */
	Device deviceHeartbeatArrived(String sn);

	/**
	 * 
	 * @param @param paramString
	 * @Description: 获取设备
	 * @author cxl
	 * @date 2018年1月9日 上午11:27:44
	 */
	void askDeviceInfo(String sn);

	/**
	 * 
	 * @param @param paramDeviceTpm
	 * @Description: 设置设备
	 * @author cxl
	 * @date 2018年1月9日 上午11:27:59
	 */
	void setDeviceInfo(Device device);

	/**
	 * 
	 * @param @param paramList
	 * @Description: 更新心跳
	 * @author cxl
	 * @date 2018年1月9日 上午11:27:31
	 */
	void heartbeat(List<Long> devices);

	/**
	 * 
	 * @param @param paramList
	 * @param @throws Exception
	 * @Description: 保存记录
	 * @author cxl
	 * @date 2018年1月9日 上午11:27:19
	 */
	void saveRecord(List<DeviceRecord> recordList)throws IOException;
	

	/**
	 * 根据id查询考勤设备
	 * 
	 * @author: duanfen
	 * @version: 2018年6月14日 下午3:10:59
	 */
	Device selectDeviceById(Long id);

	/**
	 * 批量删除安全帽
	 * @param ids
	 * @return
	 */
	Integer deleteDevices(String ids);

	/**
	 * 
	 * @description 获取实名制考勤设备（盐城）
	 * @author chupp
	 * @param myc 
	 * @date 2018年7月4日
	 *
	 */
	void saveDeviceFromSMZYC(Map<String, String> myc);

	/**
	 *@param m 
	 * @Description: 发送考勤设备数据对接到实名制
	 *@Author xieshuaishuai
	 *@Date 2018/7/12 14:18
	 */
	boolean getDeviceFromLabor(List<Long> ids, Map<String, String> m);

	int updateDevice(Device device);

	/**
	 * 
	 * @description 获取实名制考勤设备（南通）
	 * @author chupp
	 * @param mnt 
	 * @param list 
	 * @date 2018年7月26日
	 *
	 */
	void saveDeviceFromSMZNT(Map<String, String> mnt, List<SubContractor> list);
	

	/**
	 * 根据SN获取
	 * @param sn
	 * @return
	 */
	Device getBySn(String sn);

	void saveDeviceFromSMZTY(Map<String, String> mnt);

    void addDevice(List<Object> addList);


	/**
	 * 根据社会统一信用代码和编号查询数据
	 * @param id
	 * @return
	 */
    List<Device> queryDeviceById(String organizationCode, Long id,String projectCode);
}
