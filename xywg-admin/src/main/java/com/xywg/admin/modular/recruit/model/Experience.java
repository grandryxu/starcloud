package com.xywg.admin.modular.recruit.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 班组工作经历表
 * </p>
 *
 * @author wangcw123
 * @since 2018-08-22
 */
@TableName("recruit_experience")
public class Experience extends Model<Experience> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 简历主键
     */
    @TableField("resume_id")
    private Long resumeId;
    /**
     * 工作经历主题
     */
    private String theme;
    /**
     * 工作项目
     */
    @TableField("project_name")
    private String projectName;
    /**
     * 开始时间
     */
    @TableField("start_date")
    private Date startDate;
    /**
     * 结束时间
     */
    @TableField("end_date")
    private Date endDate;
    /**
     * 工作内容
     */
    private String content;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 更新时间
     */
    @TableField("update_date")
    private Date updateDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Experience{" +
        "id=" + id +
        ", resumeId=" + resumeId +
        ", theme=" + theme +
        ", startDate=" + startDate +
        ", endDate=" + endDate +
        ", content=" + content +
        ", remark=" + remark +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        "}";
    }
}
