package com.xywg.admin.modular.device.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 考勤记录表扩展
 * </p>
 *
 * @author xuehao.shi
 * @since 2019-06-20
 */
public class DeviceRecordExt extends DeviceRecord {

    private static final long serialVersionUID = 1L;

    /**
     * deviceName
     */
    private Long deviceName;

	public Long getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(Long deviceName) {
		this.deviceName = deviceName;
	}
    
}
