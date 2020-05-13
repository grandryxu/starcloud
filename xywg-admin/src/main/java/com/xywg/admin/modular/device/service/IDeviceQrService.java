package com.xywg.admin.modular.device.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.xywg.admin.core.base.tips.Tip;
import com.xywg.admin.modular.device.model.DeviceQr;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 二维码设备维护 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
public interface IDeviceQrService extends IService<DeviceQr> {

    List<Map<String,Object>> list(Map<String,Object> map, Page page);

    int saveDeviceQr(DeviceQr deviceQr);

    int updateDeviceQr(DeviceQr deviceQr);

    void deleteByIds(Map<String,Object> map);

    /**
	 * 
	 * @description 终端代码升级
	 * @author chupp
	 * @date 2018年8月6日
	 * @param map
	 * @return
	 *
	 */
	Tip upCode(Map<String,Object> map);

	/**
	 * 上传软件
	 * @param map
	 * @yuanyang
	 */
    void upload(Map<String, String> map);

	/**
	 * 查询所有版本号
	 * @return
	 * @author yuanyang
	 */
    List<Map<String,String>> selectVersions();

    DeviceQr getOneById(Long id);

	Tip remoteRestart(Map<String, Object> map);

    void addDeviceQr(List<Object> addList);
}

