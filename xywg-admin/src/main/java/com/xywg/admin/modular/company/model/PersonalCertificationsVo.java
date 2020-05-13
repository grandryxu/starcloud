package com.xywg.admin.modular.company.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-29
 */
@TableName("buss_personal_certifications")
public class PersonalCertificationsVo extends Model<PersonalCertificationsVo> {

    private static final long serialVersionUID = 1L;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     * 姓名
     */

    private String employeeName;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 证件类型 参见人员证件类型字典表
     */
    @TableField("id_card_type")
    private Integer idCardType;
    /**
     * 证件编号
     */
    @TableField("id_card_number")
    private String idCardNumber;
    /**
     * 参见“证书类型”字典表中类型编号
     */
    @TableField("certification_type_code")
    private String certificationTypeCode;
    /**
     * 专业编码 安管、岗位证书、职称证书及执业注册证书专业分别见相应字典表
     */
    @TableField("profession_code")
    private Integer professionCode;
    /**
     * 类别/工种 职称证书参见职称人员类别字典表；执业注册证书见执业注册类别字典表；技术工人资格证书参见技术工人工种字典表；职业技能证书参
     */
    @TableField("job_type")
    private Integer jobType;
    /**
     * 证书等级
     */
    @TableField("certification_level_type")
    private Integer certificationLevelType;
    /**
     * 证书名称
     */
    @TableField("certification_name")
    private String certificationName;
    /**
     * 证书有效起始日期 精确到天，如2018-03-19
     */
    @TableField("valid_begin_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validBeginDate;
    /**
     * 证书有效截止日期 精确到天，如2018-03-19
     */
    @TableField("valid_end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validEndDate;
    /**
     * 发证机关
     */
    @TableField("issue_by")
    private String issueBy;
    /**
     * 发证日期
     */
    @TableField("issue_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date issueDate;
    /**
     * 资格状态 1：有效
2：注销
3：过期
     */
    private Integer status;
    /**
     * 是否删除 1：是 0:否
     */
    @TableField("is_del")
    @TableLogic
    private Integer isDel;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(Integer idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getCertificationTypeCode() {
        return certificationTypeCode;
    }

    public void setCertificationTypeCode(String certificationTypeCode) {
        this.certificationTypeCode = certificationTypeCode;
    }

    public Integer getProfessionCode() {
        return professionCode;
    }

    public void setProfessionCode(Integer professionCode) {
        this.professionCode = professionCode;
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    public Integer getCertificationLevelType() {
        return certificationLevelType;
    }

    public void setCertificationLevelType(Integer certificationLevelType) {
        this.certificationLevelType = certificationLevelType;
    }

    public String getCertificationName() {
        return certificationName;
    }

    public void setCertificationName(String certificationName) {
        this.certificationName = certificationName;
    }

    public Date getValidBeginDate() {
        return validBeginDate;
    }

    public void setValidBeginDate(Date validBeginDate) {
        this.validBeginDate = validBeginDate;
    }

    public Date getValidEndDate() {
        return validEndDate;
    }

    public void setValidEndDate(Date validEndDate) {
        this.validEndDate = validEndDate;
    }

    public String getIssueBy() {
        return issueBy;
    }

    public void setIssueBy(String issueBy) {
        this.issueBy = issueBy;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        return "PersonalCertifications{" +
        "id=" + id +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", certificationTypeCode=" + certificationTypeCode +
        ", professionCode=" + professionCode +
        ", jobType=" + jobType +
        ", certificationLevelType=" + certificationLevelType +
        ", certificationName=" + certificationName +
        ", validBeginDate=" + validBeginDate +
        ", validEndDate=" + validEndDate +
        ", issueBy=" + issueBy +
        ", issueDate=" + issueDate +
        ", status=" + status +
        ", isDel=" + isDel +
        "}";
    }
}
