package com.xywg.attendance.modular.system.service;

import com.xywg.attendance.modular.system.model.Device;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 设备表 服务类
 * </p>
 *
 * @author z
 * @since 2019-02-22
 */
public interface IDeviceService extends IService<Device> {
    /**
     * 根据SN获取
     * @param SN
     * @return
     */
    Device getBySn(String sn);
}
