package com.xywg.admin.modular.company.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 企业从业人员聘用关系表
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-24
 */

public class EmployeeMasterVo extends Model<EmployeeMasterVo> {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String companyName;
    public static Map<String, String> getTitleMap() {
        Map<String, String> titleMap = new HashedMap();
        titleMap.put("姓名", "employeeName");
        titleMap.put("证件类型", "idCardType");
        titleMap.put("证件号", "idCardNumber");
        titleMap.put("手机号码", "cellPhone");
//        titleMap.put("证件类型", "idCardType");
        titleMap.put("性别", "gender");
        titleMap.put("民族", "nation");
        titleMap.put("出生日期", "birthday");
        titleMap.put("住址", "address");
        titleMap.put("政治面貌", "politicsType");
        titleMap.put("职称等级", "professionalLevel");
        titleMap.put("职称", "professionalType");
        titleMap.put("文化程度", "cultureLevelType");
        titleMap.put("开始工作时间", "workDate");
        titleMap.put("公司", "organizationCode");
        titleMap.put("聘用方式", "jobType");
        titleMap.put("岗位职务", "job");
        titleMap.put("备注", "remark");
        return titleMap;
    }

    /**
     * 姓名
     */
    private String employeeName;

    /**
     * 手机号码
     */
    private String cellPhone;
    /**
     * 证件类型
     */
    private Integer idCardType;
    /**
     * 证件编号
     */
    private String idCardNumber;
    /**
     * 民族
     */
    private String nation;
    /**
     * 性别
     */
    private Integer gender;

    /**
     * 出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    /**
     * 住址
     */
    private String address;

    /**
     * 文化程度
     */
    private Integer cultureLevelType;

    /**
     * 职称
     */
    private Integer professionalType;
    /**
     * 职称等级
     */
    private Integer professionalLevel;

    /**
     * 开始工作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date workDate;
    /**
     * 是否实名
     */
    @TableField("is_auth")
    private Integer isAuth;
    /**
     * 是否人脸录入
     */
    @TableField("is_face")
    private Integer isFace;


    /**
     * 审核状态 0 待审核  1 审核通过 2 审核不通过
     */
    private Integer isAudit;
    /**
     * 默认头像
     */
    @TableField("head_image_path")
    private String headImagePath;
    /**
     * 身份证照片
     */
    private String idImage;
    /**
     * 用户图像压缩
     */
    private String iconImage;
    /**
     * 添加人
     */
    private Date createDate;
    /**
     * 添加时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String createUser;
    /**
     * 修改时间
     */
    private Date updateDate;
    /**
     * 修改人
     */
    private String updateUser;
    /**
     * 公司编码 
     */
    private String organizationCode;
    /**
     * 企业人员系统编号
     */
    private Integer employeSysNo;
    /**
     * 岗位职务
     */
    private String job;
    /**
     * 聘用方式 0=All=全职
     */
    private Integer jobType;
    /**
     * 状态0=Invalid=离职,1=Valid=在职,,2=Retire=退休
     */
    private Integer jobStatus;
    /**
     * 入职日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;


    /**
     * 离职日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date terminationDate;
    /**
     * 退休日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date retireDate;

    /**
     * 角色1=Employe=职员2=Worker=工人
     */
    @TableField("worker_role")
    private Integer workerRole;
    /**
     * 备注信息
     */
    private String remark;


    /**
     * 是否删除 1：是 0:否
     */
    @TableLogic
    private Integer isDel;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
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

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCultureLevelType() {
        return cultureLevelType;
    }

    public void setCultureLevelType(Integer cultureLevelType) {
        this.cultureLevelType = cultureLevelType;
    }

    public Integer getProfessionalType() {
        return professionalType;
    }

    public void setProfessionalType(Integer professionalType) {
        this.professionalType = professionalType;
    }

    public Integer getProfessionalLevel() {
        return professionalLevel;
    }

    public void setProfessionalLevel(Integer professionalLevel) {
        this.professionalLevel = professionalLevel;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Integer getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(Integer isAuth) {
        this.isAuth = isAuth;
    }

    public Integer getIsFace() {
        return isFace;
    }

    public void setIsFace(Integer isFace) {
        this.isFace = isFace;
    }

    public Integer getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Integer isAudit) {
        this.isAudit = isAudit;
    }

    public String getHeadImagePath() {
        return headImagePath;
    }

    public void setHeadImagePath(String headImagePath) {
        this.headImagePath = headImagePath;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
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

    public Date getRetireDate() {
        return retireDate;
    }

    public void setRetireDate(Date retireDate) {
        this.retireDate = retireDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public Integer getEmployeSysNo() {
        return employeSysNo;
    }

    public void setEmployeSysNo(Integer employeSysNo) {
        this.employeSysNo = employeSysNo;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    public Integer getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(Integer jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public Integer getWorkerRole() {
        return workerRole;
    }

    public void setWorkerRole(Integer workerRole) {
        this.workerRole = workerRole;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CompanyEmploye{" +
        "id=" + id +
        ", organizationCode=" + organizationCode +
        ", employeSysNo=" + employeSysNo +
        ", job=" + job +
        ", jobType=" + jobType +
        ", jobStatus=" + jobStatus +
        ", hireDate=" + hireDate +
        ", terminationDate=" + terminationDate +
        ", workerRole=" + workerRole +
        ", remark=" + remark +
        ", isDel=" + isDel +
        "}";
    }
}
