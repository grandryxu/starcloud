package com.xywg.admin.modular.workerEnshrine.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 职位收藏表
 * </p>
 *
 * @author wsb123
 * @since 2018-08-21
 */
@TableName("recruit_worker_enshrine")
public class WorkerEnshrine extends Model<WorkerEnshrine> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 证件类型 参见人员证件类型字典表
     */
    @TableField("id_card_type")
    private String idCardType;
    /**
     * 证件编号
     */
    @TableField("id_card_number")
    private String idCardNumber;
    /**
     * 招聘主键
     */
    @TableField("recruit_id")
    private Long recruitId;
    /**
     * 删除标记（1：有效；0：已删除）
     */
    @TableField("is_del")
    private Integer isDel;
    /**
     * 备注
     */
    private String remark;
    /**
     * 添加人
     */
    @TableField(value ="create_user",fill = FieldFill.INSERT)
    private String createUser;
    /**
     * 创建时间
     */
    @TableField(value ="create_date",fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 修改人
     */
    @TableField(value = "update_user",fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
    /**
     * 更新时间
     */
    @TableField(value ="update_date",fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    public WorkerEnshrine() {
    }

    public WorkerEnshrine(String idCardType, String idCardNumber, Long recruitId) {
        this.idCardType = idCardType;
        this.idCardNumber = idCardNumber;
        this.recruitId = recruitId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public Long getRecruitId() {
        return recruitId;
    }

    public void setRecruitId(Long recruitId) {
        this.recruitId = recruitId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "WorkerEnshrine{" +
        "id=" + id +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", recruitId=" + recruitId +
        ", isDel=" + isDel +
        ", remark=" + remark +
        ", createUser=" + createUser +
        ", createDate=" + createDate +
        ", updateUser=" + updateUser +
        ", updateDate=" + updateDate +
        "}";
    }
}
