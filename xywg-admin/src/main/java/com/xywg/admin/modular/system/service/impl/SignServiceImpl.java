package com.xywg.admin.modular.system.service.impl;

import com.xywg.admin.core.common.constant.Const;
import com.xywg.admin.core.common.exception.BizExceptionEnum;
import com.xywg.admin.core.exception.XywgException;
import com.xywg.admin.core.util.AuthCode;
import com.xywg.admin.core.util.DateUtils;
import com.xywg.admin.core.util.SMSUtils;
import com.xywg.admin.modular.system.dao.SignMapper;
import com.xywg.admin.modular.system.model.AppPersonnelRegister;
import com.xywg.admin.modular.system.model.Sign;
import com.xywg.admin.modular.system.model.User;
import com.xywg.admin.modular.system.service.IUserService;
import com.xywg.admin.modular.system.service.SignService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hjy
 * @date 2018/5/29
 */
@Service
public class SignServiceImpl implements SignService {
    @Autowired
    private SignMapper signMapper;
    @Autowired
    private IUserService userService;
    /**
     * 处理短信验证码的用途,先验证用途后再发送短信
     */
    @Override
    public Map<String,Object> handleSMS(Sign sign){
        if (StringUtils.isBlank(sign.getMobile()) || sign.getType() == null) {
            throw new XywgException(700,"参数mobile,type不能为空");
        }
        //账号注册
        if(sign.getType()==0){
            // 判断账号是否重复
            User theUser = userService.getByAccountAPP(sign.getMobile(),"0");
            if (theUser != null) {
                throw new XywgException(BizExceptionEnum.USER_ALREADY_REG);
            }
        }
        //找回密码
        if(sign.getType()==1){
            User theUser = userService.getByAccountAPP(sign.getMobile(),"0");
            if (theUser == null) {
                throw new XywgException(BizExceptionEnum.USER_NOT_EXISTED);
            }
        }
        Map<String,Object> map=new HashMap<>(1);
        map.put("codeId",sendSMS(sign.getMobile(), sign.getType()));
        return map;
    }

    /**
     * 发送短信  返回id
     */
    @Override
    public Long sendSMS(String mobile, Integer type) {
        String code = AuthCode.getRandNum(4);
        //真正的发送短信
        try {
            SMSUtils.sendSMS(mobile, code);
        } catch (Exception e) {
            throw new XywgException(700,"短信发送失败");
        }

        Sign sign = new Sign();
        sign.setCode(code);
        sign.setIsAble(1);
        sign.setMobile(mobile);
        sign.setRegisterTime(new Date());
        sign.setType(type);
        signMapper.insert(sign);
        return sign.getId();
    }

    /**
     * 判断短信是否有效
     */
    @Override
    public void verifySign(AppPersonnelRegister appPersonnelRegister) {
        Sign sign = signMapper.getSign(appPersonnelRegister.getCodeId());

        if (sign == null || !appPersonnelRegister.getCode().equals(sign.getCode())) {
            throw new XywgException(600, "验证码不正确");
        }
        if (DateUtils.pastMinutes(sign.getRegisterTime()) > Const.SIGN_CODE_VALID_TIME) {
            throw new XywgException(600, "验证码已失效");
        }
    }
}
