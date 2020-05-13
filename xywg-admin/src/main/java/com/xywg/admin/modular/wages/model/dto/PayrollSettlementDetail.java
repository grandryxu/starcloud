package com.xywg.admin.modular.wages.model.dto;

import lombok.Data;

/**
 * Created by jingyun_hu on 2018/6/13.
 */
@Data
public class PayrollSettlementDetail {
    //应发金额
    private String payAmount;
    //实发金额
    private String actualAmount;
    //剩余工资
    private String balanceAmount;
    //创建日期
    private String createDate;
    //单子类型(1:工资单详细,2:结算单详细)
    private String documentType;


}
