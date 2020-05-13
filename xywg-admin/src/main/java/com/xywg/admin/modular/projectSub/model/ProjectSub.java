package com.xywg.admin.modular.projectSub.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 项目分包表
 * </p>
 *
 * @author cw123
 * @since 2018-07-10
 */
@TableName("buss_project_sub")
public class ProjectSub extends Model<ProjectSub> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 分布工程编号
     */
    @TableField("sub_id")
    private String subId;
    /**
     * 子分布工程编号
     */
    @TableField("child_num_id")
    private String childNumId;
    /**
     * 项目编号
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 添加人
     */
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 添加时间
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;
    /**
     * 修改时间
     */
    @TableField(value = "update_date" , fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;
    /**
     * 修改人
     */
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
    /**
     * 是否删除 1：是 0:否
     */
    @TableLogic
    @TableField("is_del")
    private Integer isDel;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getChildNumId() {
        return childNumId;
    }

    public void setChildNumId(String childNumId) {
        this.childNumId = childNumId;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
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
        return "ProjectSub{" +
        "id=" + id +
        ", subId=" + subId +
        ", childNumId=" + childNumId +
        ", projectCode=" + projectCode +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", isDel=" + isDel +
        "}";
    }

    public ProjectSub() {
    }

    public ProjectSub(Long id, String subId, String childNumId, String projectCode) {
        this.id = id;
        this.subId = subId;
        this.childNumId = childNumId;
        this.projectCode = projectCode;
    }
}
