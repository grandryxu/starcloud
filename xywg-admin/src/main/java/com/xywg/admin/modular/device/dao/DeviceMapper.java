package com.xywg.admin.modular.device.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.xywg.admin.modular.device.model.Device;
import com.xywg.admin.modular.device.model.DeviceRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xywg.admin.modular.smz.model.DeviceMo;
import com.xywg.admin.modular.zr.model.SmzZrRelationTpm;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 考勤机 Mapper 接口
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
public interface DeviceMapper extends BaseMapper<Device> {
	/**
     * 获取考勤机列表
     * @param page
     * @param map
     * @return
     */
    List<Map<String,Object>> list(@Param("page") Page page , @Param("map") Map map);

	/**
	 * 根据项目查询考勤设备
	 * @param projectCode
	 * @return
	 */
	List<String> getSnByProject(@Param("projectCode") String projectCode);

	/**
	 * 考勤设备列表
	 * @param ids
	 * @return
	 */
	List<Map<String, Object>> getDevices(List<Long> ids);

	/**
     * 根据序列号查询 安全帽
     * @param device
     * @return
     */
    Device selectDeviceBySn(Device device);

	/**
     * 新增设备
     * @param device
     * @return
     */
    @Override
    Integer insert(Device device);

	/**
     * 批量启用 禁用
     * @param ids
     * @param state
     * @return
     */
    Integer toggle(@Param("ids")String ids,@Param("state") Integer state);

	/**
     * 根据设备类型字典id获取设备
     * @param menuId
     * @return
     */
    List<Device> getDevicesByDeviceType(Long menuId);

	/**
     * 根据设备类型字典numList获取设备
     * @param rawChildNums
     * @return
     */
    List<Device> getDevicesByDeviceTypeList(List<Integer> rawChildNums);

	/**
	 * 
	 * @param @param params
	 * @param @return
	 * @Description: 根据sn获取设备
	 * @author cxl
	 * @date 2018年1月9日 上午11:32:09
	 */
	List<Device> getByDeviceSnMap(Map<String, Object> params);

	/**
	 * 
	 * @param @param paramString 
	 * @Description: 插command
	 * @author cxl  
	 * @date 2018年1月4日 下午2:27:45
	 */
	void askDeviceInfo(@Param("sn") String sn);

	/**
	 * 
	 * @param @param paramDeviceTpm
	 * @Description: 设置设备
	 * @author cxl
	 * @date 2018年1月9日 上午11:32:23
	 */
	void setDeviceInfo(Device device);

	/**
	 * 
	 * @param @param paramList
	 * @Description: 更新心跳
	 * @author cxl
	 * @date 2018年1月9日 上午11:31:51
	 */
	void heartbeat(List<Long> devices);

	/**
	 * 根据id查询考勤设备
	 * 
	 * @author: duanfen
	 * @version: 2018年6月14日 下午3:10:59
	 */
	Device selectDeviceById(@Param("id")Long id);

	/**
	 *  批量删除考勤机
	 * @param ids
	 * @return
	 */
    Integer deleteDevices(String ids);

    /**
     *@Description: 发送考勤设备数据对接到实名制
     *@Author xieshuaishuai
     *@Date 2018/7/12 14:17
     */
    List<DeviceMo> getDeviceFromLabor(List<Long> ids);

    void updateDevice(@Param("d") Device device);

    Device checkBySn(@Param("d") Device device);

    /**
     *@Description:根据id判断这的设备能不能启用
     *@Author xieshuaishuai
     *@Date 2018/7/23 18:16
     */
    Device checkEnable(Long id);

    /**
     * 
     * @description 
     * @author chupp
     * @date 2018年9月3日
     * @param projectCode
     *
     */
    List<Device> getDeviceInfoByProjectCode(@Param("pc") String projectCode);

	/**
	 * 获取项目下所有的考勤机设备的ids
	 * @param projectCode
	 * @return
	 */
	List<String> getAllDeviceSnsByProjectCode(String projectCode);
	
	Device getByDeviceSn(@Param("sn")String sn);

	List<Device> getZrDeviceInfo();

	String queryKey(@Param("s")Long id,@Param("n") String name);

	void addRelation(SmzZrRelationTpm r);

	Map<String, Long> getBySn(@Param("sn") String sn);

	int getDeviceByProjectCodeAndSn(@Param("sn")String sn,@Param("projectCode")String projectCode);

    List<Device> queryDeviceById(@Param("organizationCode") String organizationCode, @Param("id") Long id,@Param("projectCode")String projectCode);
}
