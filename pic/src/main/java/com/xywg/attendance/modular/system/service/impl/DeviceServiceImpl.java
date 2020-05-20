package com.xywg.attendance.modular.system.service.impl;

import com.xywg.attendance.modular.system.model.Device;
import com.xywg.attendance.modular.system.dao.DeviceMapper;
import com.xywg.attendance.modular.system.service.IDeviceService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 设备表 服务实现类
 * </p>
 *
 * @author z
 * @since 2019-02-22
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

    @Override
    public Device getBySn(String sn) {
        return this.baseMapper.getByDeviceSn(sn);
    }
}
