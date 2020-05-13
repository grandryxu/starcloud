package com.xywg.admin.modular.wages.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by jingyun_hu on 2018/6/7.
 * 结算单明细
 */
@Data
public class SettlementDetail {
    private long id;
    private String settlementCode;
    private String workerName;
    private String idCardType;
    private String idCardTypeName;
    private String idCardNumber;
    private String workerType;
    private String workerTypeName;
    private String price;
    private Integer days;
    //累计应发金额
    private BigDecimal addPayAmount;
    //累计实发金额
    private BigDecimal addActualAmount;
    //累计剩余工资
    private BigDecimal addBalanceAmount;
    private String payStatus;
    private String payStatusValue;
    private String status;
    //惩罚金额
    private BigDecimal punishAmount;
    //奖励金额
    private BigDecimal rewardAmount;
    //结算应发
    private BigDecimal settlePayAmount;
    //结算实发
    private BigDecimal settleActualAmount;
    //剩余应发
    private BigDecimal settleBalanceAmount;
    private String time;
    private String sign;
    private String iconSign;
    private String photo;
    private String iconPhoto;
    private Integer certificateType;
    private String updateUser;
    private String createUser;
    //应发金额
    private String payAmount;
    //实发金额
    private String actualAmount;
    //剩余金额
    private String balanceAmount;
    private String rollDetailId;
    //企业组织机构代码
    private String organizationCode;
    //项目编号
    private String projectCode;
    //参加计算工资的工资明细id
    private String payRollDetailConcatIds;
    
    private Integer teamSysNo;

}
