package com.xywg.admin.modular.project.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: wangshibo
 * @Date: 2018/10/26/026 16:28
 * @Description:
 */
@Data
public class PersonPosition {
    private Long id;
    private BigDecimal lng;
    private BigDecimal lat;
}
