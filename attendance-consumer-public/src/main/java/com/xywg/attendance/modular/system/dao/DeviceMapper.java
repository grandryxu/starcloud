package com.xywg.attendance.modular.system.dao;

import com.xywg.attendance.modular.system.model.Device;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 设备表 Mapper 接口
 * </p>
 *
 * @author z
 * @since 2019-02-22
 */
public interface DeviceMapper extends BaseMapper<Device> {
    Device getByDeviceSn(@Param("sn") String sn);
}
