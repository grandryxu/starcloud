package com.xywg.admin.modular.wages.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 工资单
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
@TableName("buss_pay_roll")
public class PayRoll extends Model<PayRoll> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 工资单编号 工资单编号18位：GZD+6位分包商系统编号+YYYYMM+3位序号；
     */
    @TableField("pay_roll_code")
    private String payRollCode;
    /**
     * 项目编号
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 所属企业组织机构代码
     */
    @TableField("organization_code")
    private String organizationCode;
    /**
     * 班组编号
     */
    @TableField("team_sys_no")
    private Integer teamSysNo;
    /**
     * 发放月份
     */
    @TableField("pay_month")
    private Date payMonth;
    /**
     * 发放类型   1 按工时发放  2按计工单发放
     */
    private Integer type;
    /**
     * 状态   2=分包已审核,3=总包已复核，50=已发放，初始为空
     */
    private Integer status;
    /**
     * 保存状态  1：暂存  2 提交
     */
    @TableField("save_status")
    private Integer saveStatus;
    /**
     * 发放状态 1:待发放  2：部分发放  3：已发放
     */
    @TableField("pay_status")
    private Integer payStatus;
    /**
     * 建筑活动工程编码（总包）来源JZHD0101
     */
    @TableField("contractor_project_code")
    private String contractorProjectCode;
    /**
     * 建筑活动工程编码（分包） 来源JZHD0101
     */
    @TableField("sub_contractor_project_code")
    private String subContractorProjectCode;
    /**
     * 附件 存放工资单附件路径(上传附件时有对应的附件上传接口)
     */
    @TableField("attach_files")
    private String attachFiles;
    /**
     * 应发总额
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;
    /**
     * 分包审核人
     */
    @TableField("construct_valid")
    private Long constructValid;
    /**
     * 分包审核时间
     */
    @TableField("construct_date")
    private Date constructDate;
    /**
     * 总包审核人
     */
    @TableField("contract_valid")
    private Long contractValid;
    /**
     * 总包审核时间
     */
    @TableField("contract_date")
    private Date contractDate;
    /**
     * 添加人
     */
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 添加时间
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;
    /**
     * 修改时间
     */
    @TableField(value = "update_date", fill = FieldFill.UPDATE)
    private Date updateDate;
    /**
     * 修改人
     */
    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private String updateUser;
    
   
    /**
     * 实发金额
     */
    @TableField("actual_amount")
    private BigDecimal actualAmount;
    /**
     * 创建人id
     */
    @TableField("salary_person")
    private Long salaryPerson;
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

    public String getPayRollCode() {
        return payRollCode;
    }

    public void setPayRollCode(String payRollCode) {
        this.payRollCode = payRollCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public Integer getTeamSysNo() {
        return teamSysNo;
    }

    public void setTeamSysNo(Integer teamSysNo) {
        this.teamSysNo = teamSysNo;
    }

    public Date getPayMonth() {
        return payMonth;
    }

    public void setPayMonth(Date payMonth) {
        this.payMonth = payMonth;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(Integer saveStatus) {
        this.saveStatus = saveStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getContractorProjectCode() {
        return contractorProjectCode;
    }

    public void setContractorProjectCode(String contractorProjectCode) {
        this.contractorProjectCode = contractorProjectCode;
    }

    public String getSubContractorProjectCode() {
        return subContractorProjectCode;
    }

    public void setSubContractorProjectCode(String subContractorProjectCode) {
        this.subContractorProjectCode = subContractorProjectCode;
    }

    public String getAttachFiles() {
        return attachFiles;
    }

    public void setAttachFiles(String attachFiles) {
        this.attachFiles = attachFiles;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getConstructValid() {
        return constructValid;
    }

    public void setConstructValid(Long constructValid) {
        this.constructValid = constructValid;
    }

    public Date getConstructDate() {
        return constructDate;
    }

    public void setConstructDate(Date constructDate) {
        this.constructDate = constructDate;
    }

    public Long getContractValid() {
        return contractValid;
    }

    public void setContractValid(Long contractValid) {
        this.contractValid = contractValid;
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Long getSalaryPerson() {
        return salaryPerson;
    }

    public void setSalaryPerson(Long salaryPerson) {
        this.salaryPerson = salaryPerson;
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
        return "PayRoll{" +
        "id=" + id +
        ", payRollCode=" + payRollCode +
        ", projectCode=" + projectCode +
        ", organizationCode=" + organizationCode +
        ", teamSysNo=" + teamSysNo +
        ", payMonth=" + payMonth +
        ", type=" + type +
        ", status=" + status +
        ", saveStatus=" + saveStatus +
        ", payStatus=" + payStatus +
        ", contractorProjectCode=" + contractorProjectCode +
        ", subContractorProjectCode=" + subContractorProjectCode +
        ", attachFiles=" + attachFiles +
        ", totalAmount=" + totalAmount +
        ", constructValid=" + constructValid +
        ", constructDate=" + constructDate +
        ", contractValid=" + contractValid +
        ", contractDate=" + contractDate +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", salaryPerson=" + salaryPerson +
        ", isDel=" + isDel +
        "}";
    }
}
