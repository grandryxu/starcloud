package com.xywg.admin.modular.report.model.dto;

import com.xywg.admin.core.excel.annotation.ExcelField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/7/5 20:37
 */
@Data
public class PayrollReportDto {
    //项目编号
    @ExcelField(title = "项目编号", order = 1)
    private String projectCode;
    //项目名称
    @ExcelField(title = "项目名称", order = 2)
    private String projectName;
    //项目活动类型
    @ExcelField(title = "项目活动类型", order = 3)
    private Integer projectActivityType;
    //项目活动类型
    @ExcelField(title = "项目活动类型", order = 4)
    private String projectActivityTypeVal;
    //应发金额
    @ExcelField(title = "应发金额", order = 5)
    private BigDecimal payAmount;
    //实发金额
    @ExcelField(title = "实发金额", order = 6)
    private BigDecimal actualAmount;
    //剩余金额
    @ExcelField(title = "剩余金额", order = 7)
    private BigDecimal balanceAmount;
}
