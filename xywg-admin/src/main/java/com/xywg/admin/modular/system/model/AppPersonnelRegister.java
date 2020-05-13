package com.xywg.admin.modular.system.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hjy
 * @date 2018/5/30
 * 工人登记信息实体类
 */
@Data
public class AppPersonnelRegister implements Serializable {

    private static final long serialVersionUID = 1L;
    private String account;
    private String mobile;
    private String password;
    private String codeId;
    private String code;
    private String phone;
    /**
     * 证件类型
     */
    private Integer idCardType;
    /**
     * 证件编号
     */
    private String idCardNumber;
}
