package com.xywg.admin.modular.recuritWorker.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 招聘信息表
 * </p>
 *
 * @author cw123
 * @since 2018-08-17
 */
@TableName("recruit_worker")
public class RecruitWorker extends Model<RecruitWorker> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 所属企业组织机构代码
     */
    @TableField("organization_code")
    private String organizationCode;
    /**
     * 招聘人账号
     */
    private String account;
    /**
     * 招聘类型（1：工人；2：班组；）
     */
    @TableField("recruit_type")
    private String recruitType;
    /**
     * 招聘岗位
     */
    @TableField("recruit_station")
    private String recruitStation;
    /**
     * 工程类型
     */
    @TableField("project_type")
    private Long projectType;
    /**
     * 工种主键集合
     */
    @TableField("work_id_sets")
    private String workIdSets;
    /**
     * 招聘人数
     */
    @TableField("recruit_number")
    private String recruitNumber;
    /**
     * 福利内容集合
     */
    @TableField("weal_content")
    private String wealContent;
    /**
     * 开始年龄
     */
    @TableField("age_start")
    private Integer ageStart;
    /**
     * 结束年龄
     */
    @TableField("age_end")
    private Integer ageEnd;
    /**
     * 招聘要求
     */
    private String claim;
    /**
     * 职位描述
     */
    private String description;
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
     * 招聘联系人姓名
     */
    private String name;
    /**
     * 招聘联系人电话
     */
    private String phone;
    /**
     * 招聘联系人邮箱
     */
    private String email;
    /**
     * 删除标记（1：有效；0：已删除；）
     */
    @TableField("is_del")
    private Integer isDel;
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
    /**
     * 创建者姓名
     */
    @TableField("create_user")
    private String createUser;
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
     * 具体地址
     */
    private String address;
    /**
     * 浏览总人数
     */
    @TableField("browse_count")
    private Integer browseCount;
    /**
     * 申请总人数
     */
    @TableField("record_count")
    private Integer recordCount;


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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRecruitType() {
        return recruitType;
    }

    public void setRecruitType(String recruitType) {
        this.recruitType = recruitType;
    }

    public String getRecruitStation() {
        return recruitStation;
    }

    public void setRecruitStation(String recruitStation) {
        this.recruitStation = recruitStation;
    }

    public Long getProjectType() {
        return projectType;
    }

    public void setProjectType(Long projectType) {
        this.projectType = projectType;
    }

    public String getWorkIdSets() {
        return workIdSets;
    }

    public void setWorkIdSets(String workIdSets) {
        this.workIdSets = workIdSets;
    }

    public String getRecruitNumber() {
        return recruitNumber;
    }

    public void setRecruitNumber(String recruitNumber) {
        this.recruitNumber = recruitNumber;
    }

    public String getWealContent() {
        return wealContent;
    }

    public void setWealContent(String wealContent) {
        this.wealContent = wealContent;
    }

    public Integer getAgeStart() {
        return ageStart;
    }

    public void setAgeStart(Integer ageStart) {
        this.ageStart = ageStart;
    }

    public Integer getAgeEnd() {
        return ageEnd;
    }

    public void setAgeEnd(Integer ageEnd) {
        this.ageEnd = ageEnd;
    }

    public String getClaim() {
        return claim;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(Integer browseCount) {
        this.browseCount = browseCount;
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "RecruitWorker{" +
        "id=" + id +
        ", organizationCode=" + organizationCode +
        ", account=" + account +
        ", recruitType=" + recruitType +
        ", recruitStation=" + recruitStation +
        ", projectType=" + projectType +
        ", workIdSets=" + workIdSets +
        ", recruitNumber=" + recruitNumber +
        ", wealContent=" + wealContent +
        ", ageStart=" + ageStart +
        ", ageEnd=" + ageEnd +
        ", claim=" + claim +
        ", description=" + description +
        ", salaryType=" + salaryType +
        ", salaryContent=" + salaryContent +
        ", salaryText=" + salaryText +
        ", name=" + name +
        ", phone=" + phone +
        ", email=" + email +
        ", isDel=" + isDel +
        ", remark=" + remark +
        ", createDate=" + createDate +
        ", updateDate=" + updateDate +
        ", createUser=" + createUser +
        ", updateUser=" + updateUser +
        ", provincesId=" + provincesId +
        ", citiesId=" + citiesId +
        ", areasId=" + areasId +
        ", address=" + address +
        ", browseCount=" + browseCount +
        ", recordCount=" + recordCount +
        "}";
    }
}
