package com.xywg.admin.modular.wages.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by jingyun_hu on 2018/6/5.
 * 结算单
 */
@Data
public class SettlementDto {
    private Long id;
    //结算单编号
    private String settlementCode;
    //项目编号
    private String projectCode;

    private String projectName;
    private String organizationCode;
    /**
     * 班组编号
     */
    private Integer teamSysNo;
    private String teamName;

    //发放月份
    private String payMonth;

    private Integer status;
    private String statusValue;
    /**
     * 应发金额
     */
    private BigDecimal totalAmount;
    /**
     * 实发金额
     */
    private BigDecimal actualAmount;
    /**
     * 剩余工资
     */
    private BigDecimal payedMoney;
    //分包审核人
    private String constructValid;
    //分包审核时间
    private String constructDate;
    //总包审核人
    private String contractValid;
    //总包审核时间
    private String contractDate;
    private Date createDate;
    private String createUser;
    private Date updateDate;
    private String updateUser;
    private Long salaryPerson;
    private Integer isDel;

    /**
     * 结算单详情
     */
    private List<SettlementDetailDto> detailList;

    /**
     * 操作人名称
     */
    private String accountName;

    /**
     * 操作人id
     */
    private Long userId;
}
