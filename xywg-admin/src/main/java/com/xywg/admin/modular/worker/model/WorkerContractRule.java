package com.xywg.admin.modular.worker.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 项目中工人劳动合同信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-08
 */
@TableName("buss_worker_contract_rule")
public class WorkerContractRule extends Model<WorkerContractRule> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 项目编号
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 安全帽id
     */
    @TableField("sh_imei")
    private String shImei;
    /**
     * 状态 1:待进场  2：进场  3：退场
     */
    @TableField("join_status")
    private Integer joinStatus;
    /**
     * 所属企业组织机构代码
     */
    @TableField("organization_code")
    private String organizationCode;
    /**
     * 证件类型 参见人员证件类型字典表
     */
    @TableField("id_card_type")
    private String idCardType;
    /**
     * 证件编号
     */
    @TableField("id_card_number")
    private String idCardNumber;
    /**
     * 工人劳动合同编号
     */
    @TableField("contract_code")
    private String contractCode;
    /**
     * 合同期限类型 0=固定期限合同,
1=以完成一定工作为期限的合同
     */
    @TableField("contract_period_type")
    private Integer contractPeriodType;
    /**
     * 开始日期 固定期限合同是需要填写：
YYYY-MM-DD
     */
    @TableField("start_date")
    private String startDate;
    /**
     * 结束时期 固定期限合同是需要填写：YYYY-MM-DD
     */
    @TableField("end_date")
    private String endDate;
    /**
     * 结算方式 0=计时,
1=计天,
2=计量,
3=包月,
4=定额,
5=其它
     */
    @TableField("pay_roll_rule_type")
    private Integer payRollRuleType;
    /**
     * 计量单位 为计量时，计量单位：
80=米,
81=平方米,
82=立方米
     */
    @TableField("unit_type_sys_no")
    private Integer unitTypeSysNo;
    /**
     * 单价 根据结算方式，对应的单价
     */
    @TableField("unit_price")
    private BigDecimal unitPrice;
    /**
     * 是否删除 1：是 0:否
     */
    @TableField("is_del")
    private Integer isDel;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getShImei() {
        return shImei;
    }

    public void setShImei(String shImei) {
        this.shImei = shImei;
    }

    public Integer getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(Integer joinStatus) {
        this.joinStatus = joinStatus;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public Integer getContractPeriodType() {
        return contractPeriodType;
    }

    public void setContractPeriodType(Integer contractPeriodType) {
        this.contractPeriodType = contractPeriodType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getPayRollRuleType() {
        return payRollRuleType;
    }

    public void setPayRollRuleType(Integer payRollRuleType) {
        this.payRollRuleType = payRollRuleType;
    }

    public Integer getUnitTypeSysNo() {
        return unitTypeSysNo;
    }

    public void setUnitTypeSysNo(Integer unitTypeSysNo) {
        this.unitTypeSysNo = unitTypeSysNo;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WorkerContractRule{" +
        "id=" + id +
        ", projectCode=" + projectCode +
        ", shImei=" + shImei +
        ", joinStatus=" + joinStatus +
        ", organizationCode=" + organizationCode +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", contractCode=" + contractCode +
        ", contractPeriodType=" + contractPeriodType +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", payRollRuleType=" + payRollRuleType +
        ", unitTypeSysNo=" + unitTypeSysNo +
        ", unitPrice=" + unitPrice +
        ", isDel=" + isDel +
        "}";
    }
}
