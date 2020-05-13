package com.xywg.admin.modular.device.model.dto;

import com.xywg.admin.modular.device.model.DeviceRecord;
import lombok.Data;

/**
 * * @Package com.xywg.admin.modular.device.model.dto
 * * @Description: DeviceRecord实体扩展
 * * @author caiwei
 * * @date 2018/9/17
 **/
@Data
public class DeviceRecordDto2 extends DeviceRecord{

    private Integer pageSize;

    private Integer pageNo;

    private Integer index;
}
