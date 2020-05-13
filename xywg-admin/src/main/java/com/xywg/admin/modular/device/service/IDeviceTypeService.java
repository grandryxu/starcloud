package com.xywg.admin.modular.device.service;

import com.xywg.admin.modular.device.model.DeviceType;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 考勤机类型 服务类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-12
 */
public interface IDeviceTypeService extends IService<DeviceType> {

    void addDeviceType(List<Object> addList);

}
