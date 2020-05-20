package com.xywg.attendance.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 严鹏
 * @date 2019/5/15
 */
@Component
public class ZBusHandlerConfig {
    private static Logger logger = LoggerFactory.getLogger("");
    private String address;


    @Value("${com.xywg.zbus.address}")
    public void setAddress(String address) {
        ZbusHandler.address = address;
        logger.info("ZBus Host Address is " + ZbusHandler.address);
    }

    public String getAddress() {
        return address;
    }
}
