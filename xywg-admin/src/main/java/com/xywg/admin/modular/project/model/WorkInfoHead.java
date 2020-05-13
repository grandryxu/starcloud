package com.xywg.admin.modular.project.model;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: wangshibo
 * @Date: 2018/10/26/026 16:06
 * @Description:
 */
@Data
public class WorkInfoHead {
    private String teamName;
    private String workerName;
    private String address;
    private String idCardType;
    private String idCardNumber;
    private String gender;
    private String shImei;
    private Integer age;
    /**
     * 血氧
     */
    private Integer bloodOxygen;
    /**
     * 体温
     */
    private BigDecimal temperature;
    /**
     * 姿态
     */
    private String attitude;

    private BigDecimal lng;
    private BigDecimal lat;
}
