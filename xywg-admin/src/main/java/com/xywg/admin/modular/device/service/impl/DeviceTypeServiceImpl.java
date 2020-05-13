package com.xywg.admin.modular.device.service.impl;

import com.xywg.admin.modular.device.model.DeviceType;
import com.xywg.admin.modular.device.dao.DeviceTypeMapper;
import com.xywg.admin.modular.device.service.IDeviceTypeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>
 * 考勤机类型 服务实现类
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-12
 */
@Service
public class DeviceTypeServiceImpl extends ServiceImpl<DeviceTypeMapper, DeviceType> implements IDeviceTypeService {
    @Override
    public void addDeviceType(List<Object> addList) {
        for (Object o : addList) {
            DeviceType deviceType = new DeviceType();
            stringToDateException();
            try {
                org.apache.commons.beanutils.BeanUtils.copyProperties(deviceType, o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            deviceType.setId(null);
            insert(deviceType);

        }
    }


    //解决 BeanUtils.copyProperties()的string转date异常
    private void stringToDateException() {
        ConvertUtils.register(new Converter() {
            @Override
            public Object convert(Class type, Object value) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    if("".equals(value.toString())){
                        return null;
                    }
                    return simpleDateFormat.parse(value.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }, java.util.Date.class);
    }
}
