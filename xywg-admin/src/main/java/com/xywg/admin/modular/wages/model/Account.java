package com.xywg.admin.modular.wages.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * <p>
 * 计工单表
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-01
 */
@TableName("buss_account")
public class Account extends Model<Account> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 项目id
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 班组编号 外部系统与平台对接创建班组后，由系统返回该编号
     */
    @TableField("team_sys_no")
    private Integer teamSysNo;
    /**
     * 类型1：包工 2：包点
     */
    private Integer type;
    /**
     * 截止时间
     */
    @TableField("closing_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date closingTime;
    /**
     * 合计发放
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;
    /**
     * 计工单人员
     */
    @TableField("account_person")
    private Long accountPerson;
    /**
     * 添加人
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 添加时间
     */
    @TableField("create_user")
    private String createUser;
    /**
     * 修改时间
     */
    @TableField("update_date")
    private Date updateDate;
    /**
     * 修改人
     */
    @TableField("update_user")
    private String updateUser;
    /**
     * 备注
     */
    private String note;
    /**
     * 绑定的工资单ID
     */
    @TableField("salary_id")
    private Long salaryId;
    /**
     * 是否删除 1：是 0:否
     */
    @TableField("is_del")
    private Integer isDel;

    public Account(){}
    public Account(Integer teamSysNo,String projectCode,Date closingTime,BigDecimal totalAmount){
        this.teamSysNo=teamSysNo;
        this.projectCode=projectCode;
        this.closingTime=closingTime;
        this.totalAmount=totalAmount;
    }


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

    public Integer getTeamSysNo() {
        return teamSysNo;
    }

    public void setTeamSysNo(Integer teamSysNo) {
        this.teamSysNo = teamSysNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Date closingTime) {
        this.closingTime = closingTime;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getAccountPerson() {
        return accountPerson;
    }

    public void setAccountPerson(Long accountPerson) {
        this.accountPerson = accountPerson;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(Long salaryId) {
        this.salaryId = salaryId;
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
        return "Account{" +
        "id=" + id +
        ", projectCode=" + projectCode +
        ", teamSysNo=" + teamSysNo +
        ", type=" + type +
        ", closingTime=" + closingTime +
        ", totalAmount=" + totalAmount +
        ", accountPerson=" + accountPerson +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", note=" + note +
        ", salaryId=" + salaryId +
        ", isDel=" + isDel +
        "}";
    }
}
