package com.xywg.admin.modular.projectSubContractor.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.xywg.admin.modular.project.service.impl.ProjectTrainingServiceImpl;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * <p>
 * 项目参建企业信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-23
 */
@TableName("buss_project_sub_contractor")
public class ProjectSubContractor extends Model<ProjectSubContractor> {
	private static Logger log = LoggerFactory.getLogger(ProjectSubContractor.class);
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 项目编号
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 企业组织机构代码
     */
    @TableField("organization_code")
    private String organizationCode;
    /**
     * 参建类型 8专业分包；9设备分包；10材料分包；11后勤服务；12特殊设备；13劳务分包；14监理单位；15建设单位；16总承包单位；17勘察单位；18设计单位；67其它；89租赁
     */
    @TableField("contractor_type")
    private Integer contractorType;
    /**
     * 进场时间
     */
    @TableField("entry_time")
    private Date entryTime;
    /**
     * 退场时间
     */
    @TableField("exit_time")
    private Date exitTime;
    /**
     * 发放工资的银行名称
     */
    @TableField("bank_name")
    private String bankName;
    /**
     * 发放工资的共管银行账户
     */
    @TableField("bank_number")
    private String bankNumber;
    /**
     * 银行联号
     */
    @TableField("bank_link_number")
    private String bankLinkNumber;
    /**
     * 工资发放模式
     */
    @TableField("pay_mode")
    private Integer payMode;
    /**
     * 项目经理名称
     */
    @TableField("pm_name")
    private String pmName;
    /**
     * 身份类型
     */
    @TableField("pm_idcard_type")
    private Integer pmIdcardType;
    /**
     * 身份证号码
     */
    @TableField("pm_idcard_number")
    private String pmIdcardNumber;
    /**
     * 项目经理电话
     */
    @TableField("pm_phone")
    private String pmPhone;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 是否删除 1：是 0:否
     */
    @TableField("is_del")
    @TableLogic
    private Integer isDel;

    /**
     * 创建该记录公司的社会信用代码
     */
    @TableField("create_organization_code")
    private String createOrganizationCode;

    /**
     * 是否上传合同
     */
    @TableField("has_contract")
    private String hasContract;
    
    public String getHasContract() {
		return hasContract;
	}

	public void setHasContract(String hasContract) {
		this.hasContract = hasContract;
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

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public Integer getContractorType() {
        return contractorType;
    }

    public void setContractorType(Integer contractorType) {
        this.contractorType = contractorType;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        if(entryTime !=""){
            try {
                this.entryTime = (Date)new SimpleDateFormat("yyyy-MM-dd").parse(entryTime);
            } catch (ParseException e) {
            	log.error(e.getMessage());
            }
        }
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        if(exitTime !=""){
            try {
                this.exitTime = (Date)new SimpleDateFormat("yyyy-MM-dd").parse(exitTime);
            } catch (ParseException e) {
            	log.error(e.getMessage());
            }
        }
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankLinkNumber() {
        return bankLinkNumber;
    }

    public void setBankLinkNumber(String bankLinkNumber) {
        this.bankLinkNumber = bankLinkNumber;
    }

    public Integer getPayMode() {
        return payMode;
    }

    public void setPayMode(Integer payMode) {
        this.payMode = payMode;
    }

    public String getPmName() {
        return pmName;
    }

    public void setPmName(String pmName) {
        this.pmName = pmName;
    }

    public Integer getPmIdcardType() {
        return pmIdcardType;
    }

    public void setPmIdcardType(Integer pmIdcardType) {
        this.pmIdcardType = pmIdcardType;
    }

    public String getPmIdcardNumber() {
        return pmIdcardNumber;
    }

    public void setPmIdcardNumber(String pmIdcardNumber) {
        this.pmIdcardNumber = pmIdcardNumber;
    }

    public String getPmPhone() {
        return pmPhone;
    }

    public void setPmPhone(String pmPhone) {
        this.pmPhone = pmPhone;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getCreateOrganizationCode() {
        return createOrganizationCode;
    }

    public void setCreateOrganizationCode(String createOrganizationCode) {
        this.createOrganizationCode = createOrganizationCode;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProjectSubContractor{" +
        "id=" + id +
        ", projectCode=" + projectCode +
        ", organizationCode=" + organizationCode +
        ", contractorType=" + contractorType +
        ", entryTime=" + entryTime +
        ", exitTime=" + exitTime +
        ", bankName=" + bankName +
        ", bankNumber=" + bankNumber +
        ", bankLinkNumber=" + bankLinkNumber +
        ", payMode=" + payMode +
        ", pmName=" + pmName +
        ", pmIdcardType=" + pmIdcardType +
        ", pmIdcardNumber=" + pmIdcardNumber +
        ", pmPhone=" + pmPhone +
        ", isDel=" + isDel +
        ", status=" + status +
        "}";
    }
    public ProjectSubContractor(String projectCode, String organizationCode, String bankName, String bankNumber) {
		this.projectCode = projectCode;
		this.organizationCode = organizationCode;
		this.bankName = bankName;
		this.bankNumber = bankNumber;
		this.contractorType = 16;

	}
    public ProjectSubContractor() {

	}
}
