package com.xywg.admin.modular.company.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.models.auth.In;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 企业从业人员聘用关系表
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-24
 */
@TableName("buss_company_employe")
public class CompanyEmploye extends Model<CompanyEmploye> {
    public CompanyEmploye setValue(CompanyEmploye companyEmploye, Integer jobType, String job, Integer jobStatus, Date terminationDate, Date retireDate, Date hireDate, String remark, String organizationCode) {
        companyEmploye.setJobType(jobType);
        companyEmploye.setJob(job);
        companyEmploye.setJobStatus(jobStatus);
        companyEmploye.setTerminationDate(terminationDate);
        companyEmploye.setRetireDate(retireDate);
        companyEmploye.setHireDate(hireDate);
        companyEmploye.setRemark(remark);
        companyEmploye.setOrganizationCode(organizationCode);
        return companyEmploye;
    }

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 公司编码
     */
    @TableField("organization_code")
    private String organizationCode;
    /**
     * 企业人员系统编号
     */
    @TableField("employe_sys_no")
    private Integer employeSysNo;
    /**
     * 岗位职务
     */
    private String job;
    /**
     * 聘用方式 0=All=全职
     */
    @TableField("job_type")
    private Integer jobType;
    /**
     * 状态0=Invalid=离职,1=Valid=在职,,2=Retire=退休
     */
    @TableField("job_status")
    private Integer jobStatus;
    /**
     * 入职日期
     */
    @TableField("hire_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;


    /**
     * 离职日期
     */
    @TableField("termination_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date terminationDate;
    /**
     * 退休日期
     */
    @TableField("retire_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date retireDate;

    public Date getRetireDate() {
        return retireDate;
    }

    public void setRetireDate(Date retireDate) {
        this.retireDate = retireDate;
    }

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
    @TableField("is_del")
    @TableLogic
    private Integer isDel;

    /**
     * 添加人
     */
    //@TableField("create_date")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 添加时间
     */
    //@TableField("create_user")
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    private String createUser;
    /**
     * 修改时间
     */
    @TableField(value = "update_date", fill = FieldFill.UPDATE)
    private Date updateDate;
    /**
     * 修改人
     */
    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private String updateUser;

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
