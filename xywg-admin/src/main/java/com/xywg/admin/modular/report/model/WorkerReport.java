package com.xywg.admin.modular.report.model;

import com.xywg.admin.core.excel.annotation.ExcelField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * * @Package com.xywg.admin.modular.report.model
 * * @Description: ${todo}
 * * @author caiwei
 * * @date 2018/7/5
 **/
@Data
public class WorkerReport {

    /**
     * 工人名称
     */
    @ExcelField(title = "姓名", order = 1)
    private String workerName;

    /**
     * 手机号码
     */
    @ExcelField(title = "证件编号", order = 2)
    private String idCardNumber;

    /**
     * 身份证类型
     */
    @ExcelField(title = "联系方式", order = 3)
    private String cellPhone;

    /**
     * 项目个数
     */
    @ExcelField(title = "所在项目数", order = 4)
    private Integer projectCount;

    /**
     * 不良记录数
     */
    @ExcelField(title = "不良记录数", order = 5)
    private Integer badRecordsCount;

    /**
     * 奖励记录数
     */
    @ExcelField(title = "奖励记录数", order = 6)
    private Integer goodRecordsCount;

    /**
     * 黑名单数
     */
    @ExcelField(title = "黑名单数", order = 7)
    private Integer blackListCount;

    /**
     * 应发总额
     */
    @ExcelField(title = "应发工资数", order = 8)
    private BigDecimal payAmount;

    /**
     * 实发总额
     */
    @ExcelField(title = "已发工资数", order = 9)
    private BigDecimal actualAmount;
}
