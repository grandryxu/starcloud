package com.xywg.admin.modular.smz.model;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author xieshuaishuai
 * @Date Create in 2018/7/11 18:28
 */
@Data
public class WorkerContractorRuleMo {

    /**
     * 项目ID
     */
    private Long projectId;
    /**
     * 项目编码
     */
    private String projectCode;
    /**
     * 所属企业组织机构代码
     */
    private String organizationCode;
    /**
     * 证件类型
     */
    private int idCardType;
    /**
     * 证件号码
     */
    private String idCardNumber;
    /**
     * 合同编码
     */
    private String contractCode;
    /**
     * 合同期限类型
     */
    private int contrcatPeriodType;
    /**
     * 开始日期
     */
    private Date startDate;
    /**
     * 结束日期
     */
    private Date endDate;
    /**
     * 结算方式
     */
    private int payrollRuletype;
    /**
     * 计量单位
     */
    private int unitypeSysno;
    /**
     * 计量单位
     */
    private Double unitPrice;
    /**
     * 合同路径
     */
    private String winBiddingFile;
    /**
     * 合同主键
     */
    private Long lablorId;
    /**
     * 人员主键
     */
    private Long userId;
    //合同附件
    private String attement;
}
