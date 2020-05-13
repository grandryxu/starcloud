package com.xywg.admin.modular.wages.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by jingyun_hu on 2018/6/7.
 * 结算单明细
 */
@Data
public class SettlementDetailDto {
    private long id;
    private String settlementCode;
    private String workerName;
    private String idCardType;
    private String idCardNumber;
    private String workerType;
    private String price;
    private Integer days;
    private BigDecimal addPayAmount;
    private BigDecimal addActualAmount;
    private BigDecimal addBalanceAmount;
    private String payStatus;
    private BigDecimal punishAmount;
    private BigDecimal rewardAmount;
    private BigDecimal settlePayAmount;
    private BigDecimal settleActualAmount;
    private BigDecimal settleBalanceAmount;
    private String time;
    private String sign;
    private String iconSign;
    private String photo;
    private String iconPhoto;
    private Integer certificateType;

    private String headImage;
    private String remark;
    private Integer isSign;
    private String createUser;
    private Date createDate;
    private String payrollDetailIds;

}
