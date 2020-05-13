package com.xywg.admin.modular.project.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.sql.Date;

/**
 * <p>
 * 项目中的培训记录
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-14
 */
@TableName("buss_project_training")
public class ProjectTraining extends Model<ProjectTraining> {

    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 项目编号
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 培训时间
     */
    @TableField("training_time")
    private Date trainingTime;
    /**
     * 培训时长
     */
    @TableField("training_duration")
    private Integer trainingDuration;
    /**
     * 课程名称
     */
    @TableField("training_name")
    private String trainingName;
    /**
     * 培训类型 参见项目培训类型字典表
     */
    @TableField("training_type_code")
    private String trainingTypeCode;
    /**
     * 培训人
     */
    private String trainer;
    /**
     * 培训简述
     */
    private String description;
    /**
     * 添加人
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 添加时间
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
     * 是否删除 1：是 0:否
     */
    @TableField("is_del")
    @TableLogic
    private Integer isDel;

    public ProjectTraining(){

    }
    public ProjectTraining(String projectCode,String description,String trainingName,Date trainingTime,Integer trainingDuration,String trainingTypeCode,String trainer,String createUser,Date createDate){
        this.projectCode=projectCode;
        this.description=description;
        this.trainingName=trainingName;
        this.trainingTime=trainingTime;
        this.trainingDuration=trainingDuration;
        this.trainingTypeCode=trainingTypeCode;
        this.trainer=trainer;
        this.createUser=createUser;
        this.createDate=createDate;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public Date getTrainingTime() {
        return trainingTime;
    }

    public void setTrainingTime(Date trainingTime) {
        this.trainingTime = trainingTime;
    }

    public Integer getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(Integer trainingDuration) {
        this.trainingDuration = trainingDuration;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public String getTrainingTypeCode() {
        return trainingTypeCode;
    }

    public void setTrainingTypeCode(String trainingTypeCode) {
        this.trainingTypeCode = trainingTypeCode;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "ProjectTraining{" +
        "id=" + id +
        ", projectCode=" + projectCode +
        ", trainingTime=" + trainingTime +
        ", trainingDuration=" + trainingDuration +
        ", trainingName=" + trainingName +
        ", trainingTypeCode=" + trainingTypeCode +
        ", trainer=" + trainer +
        ", description=" + description +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", isDel=" + isDel +
        "}";
    }
}
