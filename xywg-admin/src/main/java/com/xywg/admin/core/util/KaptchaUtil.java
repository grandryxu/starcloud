package com.xywg.admin.core.util;

import com.xywg.admin.config.properties.XywgProperties;

/**
 * 验证码工具类
 */
public class KaptchaUtil {

    /**
     * 获取验证码开关
     */
    public static Boolean getKaptchaOnOff() {
        return SpringContextHolder.getBean(XywgProperties.class).getKaptchaOpen();
    }
}