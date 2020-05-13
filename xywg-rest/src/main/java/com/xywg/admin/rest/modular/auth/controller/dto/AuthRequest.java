package com.xywg.admin.rest.modular.auth.controller.dto;

import com.xywg.admin.rest.modular.auth.validator.dto.Credence;

/**
 * 认证的请求dto
 *
 * @author wangcw
 * @Date 2017/8/24 14:00
 */
public class AuthRequest implements Credence {

    private String account;
    private String password;
    private Integer userType;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Override
    public String getCredenceName() {
        return this.account;
    }

    @Override
    public String getCredenceCode() {
        return this.password;
    }
}
