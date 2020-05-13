package com.xywg.admin.modular.team.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 班组基础信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-22
 */
@TableName("buss_team_master")
public class TeamMaster extends Model<TeamMaster> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 班组编号 外部系统与平台对接创建班组后，由系统返回该编号
     */
    @TableField("team_sys_no")
    private Integer teamSysNo;
    /**
     * 如果来源于项目现场系统，需要填写项目编号
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 所属企业组织机构代码
     */
    @TableField("organization_code")
    private String organizationCode;
    /**
     * 班组名称
     */
    @TableField("team_name")
    private String teamName;
    /**
     * 联系人（默认为班组长）
     */
    @TableField("team_leader")
    private String teamLeader;
    /**
     * 请填入班组长手机号
     */
    private String contact;
    /**
     * 班组长身份证号码
     */
    @TableField("team_leader_id_number")
    private String teamLeaderIdNumber;
    /**
     * 责任人身份证号码
     */
    @TableField("responsible_person_id_number")
    private String responsiblePersonIdNumber;
    /**
     * 联系人
     */
    @TableField(exist = false)
    private String responsiblePersonName;
    /**
     * 项目名称
     */
//    @TableField("project_name")
    @TableField(exist = false)
    private String projectName;
    /**
     * 所属参建单位名称
     */
    @TableField(exist = false)
    private String companyName;
    /**
     * 备注
     */
    private String note;
    /**
     * 添加人
     */
    @TableField(value ="create_date",fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 添加时间
     */
    @TableField(value ="create_user",fill = FieldFill.INSERT)
    private String createUser;
    /**
     * 修改时间
     */
    @TableField(value ="update_date",fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
    /**
     * 修改人
     */
    @TableField(value = "update_user",fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
    /**
     * 是否可用 1：是 0：否
     */
    private Integer status;
    /**
     * 是否删除 1：是 0:否
     */
    @TableField("is_del")
    @TableLogic
    private Integer isDel;

    @TableField(exist = false)
    private Integer leaderId;
    
    public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Integer leaderId) {
		this.leaderId = leaderId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTeamLeaderIdNumber() {
        return teamLeaderIdNumber;
    }

    public void setTeamLeaderIdNumber(String teamLeaderIdNumber) {
        this.teamLeaderIdNumber = teamLeaderIdNumber;
    }

    public String getResponsiblePersonIdNumber() {
        return responsiblePersonIdNumber;
    }

    public void setResponsiblePersonIdNumber(String responsiblePersonIdNumber) {
        this.responsiblePersonIdNumber = responsiblePersonIdNumber;
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
        return "TeamMaster{" +
        "id=" + id +
        ", teamSysNo=" + teamSysNo +
        ", projectCode=" + projectCode +
        ", organizationCode=" + organizationCode +
        ", teamName=" + teamName +
        ", teamLeader=" + teamLeader +
        ", contact=" + contact +
        ", teamLeaderIdNumber=" + teamLeaderIdNumber +
        ", responsiblePersonIdNumber=" + responsiblePersonIdNumber +
        ", note=" + note +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", status=" + status +
        ", isDel=" + isDel +
        "}";
    }

	public String getResponsiblePersonName() {
		return responsiblePersonName;
	}

	public void setResponsiblePersonName(String responsiblePersonName) {
		this.responsiblePersonName = responsiblePersonName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	
}
