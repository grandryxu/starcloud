package com.xywg.admin.modular.system.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 评价申诉页面
 * </p>
 *
 * @author cw123
 * @since 2018-08-20
 */
@TableName("recruit_appeal")
public class Appeal extends Model<Appeal> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 评价id
     */
    @TableField("evaluate_id")
    private Long evaluateId;
    /**
     * 申诉类型
     */
    private Integer type;
    /**
     * 申诉理由
     */
    @TableField("appeal_reason")
    private String appealReason;
    /**
     * 审核状态 0：待审核 1：通过 2：驳回
     */
    @TableField("is_audit")
    private Integer isAudit;
    /**
     * 驳回理由
     */
    @TableField("reject_reason")
    private String rejectReason;
    /**
     * 审核人
     */
    @TableField("audit_user")
    private String auditUser;
    /**
     * 新增时间
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(Long evaluateId) {
        this.evaluateId = evaluateId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAppealReason() {
        return appealReason;
    }

    public void setAppealReason(String appealReason) {
        this.appealReason = appealReason;
    }

    public Integer getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Integer isAudit) {
        this.isAudit = isAudit;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Appeal{" +
        "id=" + id +
        ", evaluateId=" + evaluateId +
        ", type=" + type +
        ", appealReason=" + appealReason +
        ", isAudit=" + isAudit +
        ", rejectReason=" + rejectReason +
        ", auditUser=" + auditUser +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        "}";
    }
}
