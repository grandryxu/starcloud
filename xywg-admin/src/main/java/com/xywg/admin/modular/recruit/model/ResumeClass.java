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
 * 班组简历表
 * </p>
 *
 * @author wangcw123
 * @since 2018-08-22
 */
@TableName("recruit_resume_class")
public class ResumeClass extends Model<ResumeClass> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
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
     * 联系人姓名
     */
    @TableField("com_username")
    private String comUsername;
    /**
     * 联系人电话
     */
    @TableField("com_phone")
    private String comPhone;
    /**
     * 工种主键集合（用英文逗号分隔）
     */
    @TableField("work_id_sets")
    private String workIdSets;
    /**
     * 工程类型
     */
    @TableField("project_type")
    private Integer projectType;
    /**
     * 工作年限
     */
    @TableField("work_years")
    private Integer workYears;
    /**
     * 工人数量
     */
    @TableField("worker_total")
    private String workerTotal;
    /**
     * 团队简介
     */
    @TableField("team_brief")
    private String teamBrief;
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
     * 创建者姓名
     */
    @TableField("create_user")
    private String createUser;
    /**
     * 更新时间
     */
    @TableField("update_date")
    private Date updateDate;
    /**
     * 更新者姓名
     */
    @TableField("update_user")
    private String updateUser;
    /**
     * 省主键
     */
    @TableField("provinces_id")
    private String provincesId;
    /**
     * 市主键
     */
    @TableField("cities_id")
    private String citiesId;
    /**
     * 县（区）主键
     */
    @TableField("areas_id")
    private String areasId;
    /**
     * 期望省主键
     */
    @TableField("wish_provinces_id")
    private String wishProvincesId;
    /**
     * 期望市主键
     */
    @TableField("wish_cities_id")
    private String wishCitiesId;
    /**
     * 期望县（区）主键
     */
    @TableField("wish_areas_id")
    private String wishAreasId;
    /**
     * 是否发布（1：是；0：否）
     */
    @TableField("is_default")
    private String isDefault;
    /**
     * 工资类型（1：面议；2：按天结算；3：工资区间；4：X平/X元）
     */
    @TableField("salary_type")
    private String salaryType;
    /**
     * 工资内容
     */
    @TableField("salary_content")
    private String salaryContent;
    /**
     * 工资内容
     */
    @TableField("salary_text")
    private String salaryText;
    /**
     * 是否删除 1：是 0:否
     */
    @TableField("is_del")
    private Integer isDel;


    public String getWishProvincesId() {
        return wishProvincesId;
    }

    public void setWishProvincesId(String wishProvincesId) {
        this.wishProvincesId = wishProvincesId;
    }

    public String getWishCitiesId() {
        return wishCitiesId;
    }

    public void setWishCitiesId(String wishCitiesId) {
        this.wishCitiesId = wishCitiesId;
    }

    public String getWishAreasId() {
        return wishAreasId;
    }

    public void setWishAreasId(String wishAreasId) {
        this.wishAreasId = wishAreasId;
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

    public String getComUsername() {
        return comUsername;
    }

    public void setComUsername(String comUsername) {
        this.comUsername = comUsername;
    }

    public String getComPhone() {
        return comPhone;
    }

    public void setComPhone(String comPhone) {
        this.comPhone = comPhone;
    }

    public String getWorkIdSets() {
        return workIdSets;
    }

    public void setWorkIdSets(String workIdSets) {
        this.workIdSets = workIdSets;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public Integer getWorkYears() {
        return workYears;
    }

    public void setWorkYears(Integer workYears) {
        this.workYears = workYears;
    }

    public String getWorkerTotal() {
        return workerTotal;
    }

    public void setWorkerTotal(String workerTotal) {
        this.workerTotal = workerTotal;
    }

    public String getTeamBrief() {
        return teamBrief;
    }

    public void setTeamBrief(String teamBrief) {
        this.teamBrief = teamBrief;
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

    public String getProvincesId() {
        return provincesId;
    }

    public void setProvincesId(String provincesId) {
        this.provincesId = provincesId;
    }

    public String getCitiesId() {
        return citiesId;
    }

    public void setCitiesId(String citiesId) {
        this.citiesId = citiesId;
    }

    public String getAreasId() {
        return areasId;
    }

    public void setAreasId(String areasId) {
        this.areasId = areasId;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getSalaryType() {
        return salaryType;
    }

    public void setSalaryType(String salaryType) {
        this.salaryType = salaryType;
    }

    public String getSalaryContent() {
        return salaryContent;
    }

    public void setSalaryContent(String salaryContent) {
        this.salaryContent = salaryContent;
    }

    public String getSalaryText() {
        return salaryText;
    }

    public void setSalaryText(String salaryText) {
        this.salaryText = salaryText;
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
        return "ResumeClass{" +
        "id=" + id +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", comUsername=" + comUsername +
        ", comPhone=" + comPhone +
        ", workIdSets=" + workIdSets +
        ", projectType=" + projectType +
        ", workYears=" + workYears +
        ", workerTotal=" + workerTotal +
        ", teamBrief=" + teamBrief +
        ", remark=" + remark +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", provincesId=" + provincesId +
        ", citiesId=" + citiesId +
        ", areasId=" + areasId +
        ", isDefault=" + isDefault +
        ", salaryType=" + salaryType +
        ", salaryContent=" + salaryContent +
        ", salaryText=" + salaryText +
        ", isDel=" + isDel +
        "}";
    }
}
