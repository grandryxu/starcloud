package com.xywg.admin.modular.project.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 工伤管理
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-21
 */
@TableName("buss_injury")
public class Injury extends Model<Injury> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 所属企业组织机构代码
     */
    @TableField("organization_code")
    private String organizationCode;
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
     * 身份证类型
     */
    @TableField("id_card_type")
    private Integer idCardType;
    /**
     * 身份证号
     */
    @TableField("id_card_number")
    private String idCardNumber;
    /**
     * 工伤类型
     */
    private Integer type;
    /**
     * 发生时间
     */
    private String time;
    /**
     * 添加时间
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 添加人
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
     * 是否可用 1：是 0：否
     */
    private Integer status;
    /**
     * 备注
     */
    private String remark;
    
    private Integer isDel;
    
    
    private String occureTime;

    /**
     * 工人id
     */
    private Long teamWorker;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    

    public String getOccureTime() {
        return occureTime;
    }

    public void setOccureTime(String occureTime) {
        this.occureTime = occureTime;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    
    
    public Long getTeamWorker() {
        return teamWorker;
    }

    public void setTeamWorker(Long teamWorker) {
        this.teamWorker = teamWorker;
    }


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Injury{" +
        "id=" + id +
        ", organizationCode=" + organizationCode +
        ", projectCode=" + projectCode +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", type=" + type +
        ", time=" + time +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", status=" + status +
        ", remark=" + remark +
        ", isDel=" + isDel +
        "}";
    }
}
