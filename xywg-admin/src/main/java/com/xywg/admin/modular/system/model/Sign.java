package com.xywg.admin.modular.system.model;

import com.baomidou.mybatisplus.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Sign extends Model<Sign> {

    /**
     * 数据库唯一标识
     */
    private Long id;
    /**
     *手机号
     */
    private String mobile;

    /**
     * 验证码类型 0:注册  1 找回密码
     */
    private Integer type;

    /**
     * 短信验证码
     */
    private String code;

    /**
     * 是否注册成功 1 是 2 否
     */
    private Integer status;

    /**
     * 发送验证码的时间
     */
    private Date registerTime;

    /**
     * 是否有效1有效.2无效
     */
    private Integer isAble;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
