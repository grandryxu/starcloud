package com.xywg.admin.modular.company.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-27
 */
@TableName("buss_sub_contractor_certifications")
public class SubContractorCertifications extends Model<SubContractorCertifications> {
	private static Logger log = LoggerFactory.getLogger(SubContractorCertifications.class);
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 系统编号
     */
    @TableField("sys_no")
    private Integer sysNo;
    /**
     * 企业组织机构代码
     */
    @TableField("organization_code")
    private String organizationCode;
    /**
     * 证书类型
     */
    @TableField("certification_type")
    private String certificationType;
    /**
     * 证书名称
     */
    @TableField("certification_name")
    private String certificationName;
    /**
     * 证书编号
     */
    @TableField("certification_code")
    private String certificationCode;
    /**
     * 证书有效时间(起)
     */
    @TableField("valid_begin_date")
    private Date validBeginDate;
    /**
     * 最近发放日期
     */
    @TableField("recent_valid_date")
    private Date recentValidDate;
    /**
     * 证书有效时间(止)
     */
    @TableField("valid_end_date")
    private Date validEndDate;
    /**
     * 发证机关
     */
    @TableField("grant_org")
    private String grantOrg;
    /**
     * 资质状态  1:有效2：注销3：过期4：降级
     */
    @TableField("qualification_status")
    private Integer qualificationStatus;
    /**
     * 资质证书状态1:有效2：注销3：暂扣4：过期
     */
    @TableField("certification_status")
    private Integer certificationStatus;
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

    public Integer getSysNo() {
        return sysNo;
    }

    public void setSysNo(Integer sysNo) {
        this.sysNo = sysNo;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getCertificationType() {
        return certificationType;
    }

    public void setCertificationType(String certificationType) {
        this.certificationType = certificationType;
    }

    public String getCertificationName() {
        return certificationName;
    }

    public void setCertificationName(String certificationName) {
        this.certificationName = certificationName;
    }

    public String getCertificationCode() {
        return certificationCode;
    }

    public void setCertificationCode(String certificationCode) {
        this.certificationCode = certificationCode;
    }

    public Date getValidBeginDate() {
        return validBeginDate;
    }

    public void setValidBeginDate(Date validBeginDate) {
        this.validBeginDate = validBeginDate;
    }

    public Date getRecentValidDate() {
        return recentValidDate;
    }

    public void setRecentValidDate(String recentValidDate) {
        /*this.recentValidDate=recentValidDate;*/
        if(recentValidDate !=""){
            try {
                this.recentValidDate = (Date)new SimpleDateFormat("yyyy-MM-dd").parse(recentValidDate);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
        }
    }

    public Date getValidEndDate() {
        return validEndDate;
    }

    public void setValidEndDate(Date validEndDate) {
        this.validEndDate = validEndDate;
    }

    public String getGrantOrg() {
        return grantOrg;
    }

    public void setGrantOrg(String grantOrg) {
        this.grantOrg = grantOrg;
    }

    public Integer getQualificationStatus() {
        return qualificationStatus;
    }

    public void setQualificationStatus(Integer qualificationStatus) {
        this.qualificationStatus = qualificationStatus;
    }

    public Integer getCertificationStatus() {
        return certificationStatus;
    }

    public void setCertificationStatus(Integer certificationStatus) {
        this.certificationStatus = certificationStatus;
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
        return "SubContractorCertifications{" +
        "id=" + id +
        ", sysNo=" + sysNo +
        ", organizationCode=" + organizationCode +
        ", certificationType=" + certificationType +
        ", certificationName=" + certificationName +
        ", certificationCode=" + certificationCode +
        ", validBeginDate=" + validBeginDate +
        ", recentValidDate=" + recentValidDate +
        ", validEndDate=" + validEndDate +
        ", grantOrg=" + grantOrg +
        ", qualificationStatus=" + qualificationStatus +
        ", certificationStatus=" + certificationStatus +
        ", isDel=" + isDel +
        "}";
    }
}
