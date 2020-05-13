package com.xywg.admin.modular.bad.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 工人不良行为记录信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
@TableName("buss_worker_bad_records")
public class WorkerBadRecords extends Model<WorkerBadRecords> {

    private static final long serialVersionUID = 1L;

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
     * 班组编号
     */
    @TableField("team_sys_no")
    private Long teamSysNo;
    /**
     * 项目编号 项目编号，参见项目基础信息中对项目编号的定义 
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 工人在该项目中所属企业组织机构代码
     */
    @TableField("organization_code")
    private String organizationCode;
    /**
     * 类型   1:工人  2：班组  3：企业
     */
    private Integer type;
    /**
     * 事件类别 参见不良记录的事件类别字典
     */
    @TableField("bad_record_type_code")
    private String badRecordTypeCode;
    /**
     * 事件级别 52=轻度,53=一般,54=严重
     */
    @TableField("bad_record_level_type")
    private Integer badRecordLevelType;
    /**
     * 发生时间
     */
    @TableField("occurrence_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date occurrenceDate;
    /**
     * 事件发详细缘由
     */
    private String reason;
    /**
     * 事件处理结果
     */
    @TableField("process_result")
    private String processResult;
    /**
     * 审核状态  1：待审核  1：通过  2：不通过
     */
    @TableField("is_audit")
    private Integer isAudit;
    /**
     * 添加人
     */
    //@TableField("create_date")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 添加时间
     */
    //@TableField("create_user")
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
     * 是否删除 1：是 0:否
     */
    @TableField("is_del")
    @TableLogic
    private Integer isDel;

    private String badRecordType;
    
    
    public String getBadRecordType() {
		return badRecordType;
	}

	public void setBadRecordType(String badRecordType) {
		this.badRecordType = badRecordType;
	}

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

    public Long getTeamSysNo() {
		return teamSysNo;
	}

	public void setTeamSysNo(Long teamSysNo) {
		this.teamSysNo = teamSysNo;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBadRecordTypeCode() {
        return badRecordTypeCode;
    }

    public void setBadRecordTypeCode(String badRecordTypeCode) {
        this.badRecordTypeCode = badRecordTypeCode;
    }

    public Integer getBadRecordLevelType() {
        return badRecordLevelType;
    }

    public void setBadRecordLevelType(Integer badRecordLevelType) {
        this.badRecordLevelType = badRecordLevelType;
    }

    public Date getOccurrenceDate() {
        return occurrenceDate;
    }

    public void setOccurrenceDate(Date occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getProcessResult() {
        return processResult;
    }

    public void setProcessResult(String processResult) {
        this.processResult = processResult;
    }

    public Integer getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Integer isAudit) {
        this.isAudit = isAudit;
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
        return "WorkerBadRecords{" +
        "id=" + id +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", projectCode=" + projectCode +
        ", organizationCode=" + organizationCode +
        ", type=" + type +
        ", badRecordTypeCode=" + badRecordTypeCode +
        ", badRecordLevelType=" + badRecordLevelType +
        ", occurrenceDate=" + occurrenceDate +
        ", reason=" + reason +
        ", processResult=" + processResult +
        ", isAudit=" + isAudit +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", isDel=" + isDel +
        "}";
    }
}
