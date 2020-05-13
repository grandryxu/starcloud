package com.xywg.admin.modular.report.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jingyun_hu
 * @date 2018/6/21
 * 工资统计POJO
 */
@Data
public class PayrollReport {
    //项目编号
    private String projectCode;
    //项目名称
    private String projectName;
    //项目活动类型
    private Integer projectActivityType;
    //项目活动类型
    private String projectActivityTypeVal;
    //应发金额
    private BigDecimal payAmount;
    //实发金额
    private BigDecimal actualAmount;
    //剩余金额
    private BigDecimal balanceAmount;

}
