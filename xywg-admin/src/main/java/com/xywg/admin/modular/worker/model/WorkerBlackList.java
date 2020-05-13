package com.xywg.admin.modular.worker.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 工人黑名单信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-29
 */
@TableName("buss_worker_black_list")
public class WorkerBlackList extends Model<WorkerBlackList> {

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
     * 项目编号 参见项目基础信息中对项目编号的定义
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 承包方组织机构代码 承包方组织机构代码
     */
    @TableField("contractor_org_code")
    private String contractorOrgCode;
    /**
     * 所属企业组织机构代码 所属企业组织机构代码
     */
    @TableField("organization_code")
    private String organizationCode;
    /**
     * 所在班组编号 
     */
    @TableField("team_sys_no")
    private Integer teamSysNo;
    /**
     * 班组名称
     */
    @TableField("team_name")
    private String teamName;
    /**
     * 黑名单原因
     */
    @TableField("black_reason")
    private String blackReason;
    /**
     * 有效期起
     */
    @TableField("start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    /**
     * 有效期止
     */
    @TableField("end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    /**
     * 是否有效 0:否 1：是
     */
    @TableField("is_valid")
    private Integer isValid;
    /**
     * 备注信息
     */
    private String note;
    /**
     * 新增时间
     */
    @TableField("create_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    /**
     * 添加时间
     */
    @TableField("create_user")
    private String createUser;
    /**
     * 新增时间
     */
    @TableField("update_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;
    /**
     * 添加时间
     */
    @TableField("update_user")
    private String updateUser;
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

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getContractorOrgCode() {
        return contractorOrgCode;
    }

    public void setContractorOrgCode(String contractorOrgCode) {
        this.contractorOrgCode = contractorOrgCode;
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

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getBlackReason() {
        return blackReason;
    }

    public void setBlackReason(String blackReason) {
        this.blackReason = blackReason;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
        return "WorkerBlackList{" +
        "id=" + id +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", projectCode=" + projectCode +
        ", contractorOrgCode=" + contractorOrgCode +
        ", organizationCode=" + organizationCode +
        ", teamSysNo=" + teamSysNo +
        ", teamName=" + teamName +
        ", blackReason=" + blackReason +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", isValid=" + isValid +
        ", note=" + note +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", isDel=" + isDel +
        "}";
    }
}
