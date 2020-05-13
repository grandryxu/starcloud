package com.xywg.admin.modular.system.service;

import com.xywg.admin.modular.system.model.AppPersonnelRegister;
import com.xywg.admin.modular.system.model.Sign;

import java.util.Map;

/**
 * @author hjy
 * @date 2018/5/29
 */
public interface SignService{

    Map<String,Object> handleSMS(Sign sign);

    /**
     * 发送短信
     */
    Long sendSMS(String mobile,Integer type);

    /**
     * 判断短信是否有效
     * @param appPersonnelRegister  验证码id
     */
    void verifySign (AppPersonnelRegister appPersonnelRegister);

}
