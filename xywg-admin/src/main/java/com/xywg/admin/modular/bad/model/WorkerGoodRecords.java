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
 * 工人奖励记录信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-05
 */
@TableName("buss_worker_good_records")
public class WorkerGoodRecords extends Model<WorkerGoodRecords> {

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
    private Integer teamSysNo;
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
     * 奖励类型  参见奖励类型字典
     */
    @TableField("good_record_type_code")
    private String goodRecordTypeCode;
    /**
     * 奖励级别  58=国家级；59=省部级；60=地市级；61=企业级
     */
    @TableField("good_record_level_type")
    private Integer goodRecordLevelType;
    /**
     * 奖励时间
     */
    @TableField("occurrence_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date occurrenceDate;
    /**
     * 奖项说明
     */
    private String details;
    /**
     * 新增时间
     */
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 添加人
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;
    /**
     * 修改时间
     */
    @TableField(value = "update_date", fill = FieldFill.INSERT)
    private Date updateDate;
    /**
     * 修改人
     */
    @TableField(value = "update_user", fill = FieldFill.INSERT)
    private String updateUser;
    /**
     * 是否删除 1：是 0:否
     */
    @TableField("is_del")
    @TableLogic
    private Integer isDel;

    /**
     * 1:工人2:班组
     */
    @TableField("type")
    private Integer type;

    
    public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

    public Integer getTeamSysNo() {
        return teamSysNo;
    }

    public void setTeamSysNo(Integer teamSysNo) {
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

	public String getGoodRecordTypeCode() {
		return goodRecordTypeCode;
	}

	public void setGoodRecordTypeCode(String goodRecordTypeCode) {
		this.goodRecordTypeCode = goodRecordTypeCode;
	}

	public Integer getGoodRecordLevelType() {
        return goodRecordLevelType;
    }

    public void setGoodRecordLevelType(Integer goodRecordLevelType) {
        this.goodRecordLevelType = goodRecordLevelType;
    }

    public Date getOccurrenceDate() {
        return occurrenceDate;
    }

    public void setOccurrenceDate(Date occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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
        return "WorkerGoodRecords{" +
        "id=" + id +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", teamSysNo=" + teamSysNo +
        ", projectCode=" + projectCode +
        ", organizationCode=" + organizationCode +
        ", goodRecordTypeCode=" + goodRecordTypeCode +
        ", goodRecordLevelType=" + goodRecordLevelType +
        ", occurrenceDate=" + occurrenceDate +
        ", details=" + details +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", isDel=" + isDel +
        "}";
    }
}
