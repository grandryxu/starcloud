package com.xywg.admin.modular.report.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jingyun_hu
 * @date 2018/6/21
 * 工人工资明细统计POJO
 */
@Data
public class WorkerPayrollDetailReport {
    /**
     * 工人名称
     */
    private String workerName;
    /**
     * 工人电话号码
     */
    private String cellPhone;
    /**
     * 身份证件类型代码
     */
    private String idCardType;
    /**
     * 证件代码
     */
    private String idCardNumber;
    /**
     * 项目编号
     */
    private String projectCode;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 总共的出勤天数
     */
    private String countDays;
    /**
     * 应发工资
     */
    private BigDecimal payAmount;
    /**
     * 实发工资
     */
    private BigDecimal actualAmount;
    /**
     * 剩余工资
     */
    private BigDecimal balanceAmount;


}
