package com.xywg.admin.modular.project.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 项目基础信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-23
 */
@TableName("buss_project_master")
public class ProjectMaster extends Model<ProjectMaster> {
	private static Logger log = LoggerFactory.getLogger(ProjectMaster.class);
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 项目编号
     */
    @TableField("project_code")
    private String projectCode;
    
    /**
     * 项目人工编号
     */
    @TableField("project_man_code")
    private String projectManCode;
    /**
     * 建设项目编码
     */
    @TableField("build_project_code")
    private String buildProjectCode;
    /**
     * 承包单位组织机构代码
     */
    @TableField("contractor_org_code")
    private String contractorOrgCode;
    /**
     * 承包单位编号
     */
    @TableField("general_contractor_code")
    private String generalContractorCode;
    /**
     * 承包单位名称
     */
    @TableField("general_contractor_name")
    private String generalContractorName;
    /**
     * 项目名称
     */
    @TableField("project_name")
    private String projectName;
    /**
     * 项目活动类型
     */
    @TableField("project_activity_type")
    private String projectActivityType;
    /**
     * 项目简介
     */
    @TableField("project_description")
    private String projectDescription;

    /**
     * 是否需要添加水印
     */
    @TableField("is_watermark")
    private Integer isWatermark;

    /**
     * 项目分类
     */
    @TableField("project_category")
    private String projectCategory;
    /**
     * 重点项目
     */
    @TableField("is_major_project")
    private Integer isMajorProject;
    /**
     * 建设单位名称
     */
    @TableField("owner_name")
    private String ownerName;
    /**
     * 建设单位组织机构代码或统一社会信用代码
     */
    @TableField("build_corporation_code")
    private String buildCorporationCode;
    /**
     * 施工许可证编号
     */
    @TableField("builder_licence_num")
    private String builderLicenceNum;
    /**
     * 项目行政区划,参见地区字典数据
     */
    @TableField("area_code")
    private String areaCode;
    /**
     * 承包合同额 单位 万元
     */
    @TableField("total_contract_amt")
    private BigDecimal totalContractAmt;
    /**
     * 建筑面积 单位 平方米
     */
    @TableField("building_area")
    private BigDecimal buildingArea;
    /**
     * 开工日期 精确到天，格式：yyyy-mm-dd
     */
    @TableField("start_date")
    private Date startDate;
    /**
     * 竣工日期 精确到天，格式：yyyy-mm-dd
     */
    @TableField("complete_date")
    private Date completeDate;
    /**
     * 项目来源
     */
    @TableField("project_source")
    private String projectSource;
    /**
     * 状态 参见项目状态字典表
     */
    @TableField("project_status")
    private String projectStatus;

    /*
    项目状态
    * */
    @TableField(exist = false)
    private String projectStatusName;


    /**
     * 虚拟考勤机id
     */
    @TableField("virtual_id")
    private Long virtualId;
    /**
     * 经度
     */
    private BigDecimal lng;
    /**
     * 纬度
     */
    private BigDecimal lat;
    /**
     * 距离
     */
    private Double radius;
    
   /** 中标时间
    */
   @TableField(exist = false)
   private Date date;
   
   /** 招标编码
    */
   @TableField(exist = false)
   private String tenderCode;
   
   /** 招标价格
    */
   @TableField(exist = false)
   private String bidPrice;
    /**
     * 添加时间
     */
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;
    /**
     * 添加人
     */
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
    /**
     * 是否可用 1：是 0：否
     */
    private Integer status;
    /**
     * 是否删除 1：是 0:否
     */
    @TableLogic
    @TableField("is_del")
    private Integer isDel;

    @TableField("address")
    private String address;

    @TableField("device_type")
    private Integer deviceType;

    /**
     * 是否同步到SMZ 0否1是
     */
    @TableField("is_synchro")
    private Integer isSynchro;

    /**
     * 建设单位联系人
     */
    @TableField("build_contacts")
    private String buidContacts;
    
    
    public String getProjectManCode() {
		return projectManCode;
	}

	public void setProjectManCode(String projectManCode) {
		this.projectManCode = projectManCode;
	}

	/**
     * 建设单位联系人电话
     */
    @TableField("build_phone")
    private String buildPhone;
    /**
     * 企业分管领导
     */
    @TableField("contractor_leader")
    private String contractorLeader;
    /**
     * 企业分管领导电话
     */
    @TableField("contractor_leader_phone")
    private String contractorLeaderPhone;
    /**
     * 项目总监
     */
    @TableField("project_director")
    private String projectDirector;
    /**
     * 项目总监联系电话
     */
    @TableField("project_director_phone")
    private String projectDirectorPhone;
    /**
     * 是否文明工地
     */
    //@TableField("is_civilization")
    @TableField(exist = false)
    private String isCivilization;
    /**
     * 是否申报优质工地
     */
    //@TableField("is_high_grade")
    @TableField(exist = false)
    private String isHighGrade;
    /**
     * 是否政府工程
     */
    //@TableField("is_government")
    @TableField(exist = false)
    private String isGovernment;
    /**
     * 责任人联系电话
     */
    @TableField("person_liable")
    private String personLiable;

    /**
     * 合同编号 jln add
     */
    @TableField("contract_no")
    private String contractNo;

    @TableField("work_time")
    private Integer workTime;

    @TableField(exist = false)
    private Integer contractorType;//参建类型
    @TableField(exist = false)
    private String bankName;  //发放银行名称
    @TableField(exist = false)
    private String bankNumber;  //发放银行账号
    
    
    
    public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getContractorType() {
		return contractorType;
	}

	public void setContractorType(Integer contractorType) {
		this.contractorType = contractorType;
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

	public Integer getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Integer workTime) {
        this.workTime = workTime;
    }

    public String getBuidContacts() {
        return buidContacts;
    }

    public void setBuidContacts(String buidContacts) {
        this.buidContacts = buidContacts;
    }

    public String getBuildPhone() {
        return buildPhone;
    }

    public void setBuildPhone(String buildPhone) {
        this.buildPhone = buildPhone;
    }

    public String getContractorLeader() {
        return contractorLeader;
    }

    public void setContractorLeader(String contractorLeader) {
        this.contractorLeader = contractorLeader;
    }

    public String getContractorLeaderPhone() {
        return contractorLeaderPhone;
    }

    public void setContractorLeaderPhone(String contractorLeaderPhone) {
        this.contractorLeaderPhone = contractorLeaderPhone;
    }

    public String getProjectDirector() {
        return projectDirector;
    }

    public void setProjectDirector(String projectDirector) {
        this.projectDirector = projectDirector;
    }

    public String getProjectDirectorPhone() {
        return projectDirectorPhone;
    }

    public void setProjectDirectorPhone(String projectDirectorPhone) {
        this.projectDirectorPhone = projectDirectorPhone;
    }

    public String getIsCivilization() {
        return isCivilization;
    }

    public void setIsCivilization(String isCivilization) {
        this.isCivilization = isCivilization;
    }

    public String getIsHighGrade() {
        return isHighGrade;
    }

    public void setIsHighGrade(String isHighGrade) {
        this.isHighGrade = isHighGrade;
    }

    public String getIsGovernment() {
        return isGovernment;
    }

    public void setIsGovernment(String isGovernment) {
        this.isGovernment = isGovernment;
    }

    public String getPersonLiable() {
        return personLiable;
    }

    public void setPersonLiable(String personLiable) {
        this.personLiable = personLiable;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
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

    public String getBuildProjectCode() {
        return buildProjectCode;
    }

    public void setBuildProjectCode(String buildProjectCode) {
        this.buildProjectCode = buildProjectCode;
    }

    public String getContractorOrgCode() {
        return contractorOrgCode;
    }

    public void setContractorOrgCode(String contractorOrgCode) {
        this.contractorOrgCode = contractorOrgCode;
    }

    public String getGeneralContractorCode() {
        return generalContractorCode;
    }

    public void setGeneralContractorCode(String generalContractorCode) {
        this.generalContractorCode = generalContractorCode;
    }

    public String getGeneralContractorName() {
        return generalContractorName;
    }

    public void setGeneralContractorName(String generalContractorName) {
        this.generalContractorName = generalContractorName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectActivityType() {
        return projectActivityType;
    }

    public void setProjectActivityType(String projectActivityType) {
        this.projectActivityType = projectActivityType;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public Integer getIsMajorProject() {
        return isMajorProject;
    }

    public void setIsMajorProject(Integer isMajorProject) {
        this.isMajorProject = isMajorProject;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getBuildCorporationCode() {
        return buildCorporationCode;
    }

    public void setBuildCorporationCode(String buildCorporationCode) {
        this.buildCorporationCode = buildCorporationCode;
    }

    public String getBuilderLicenceNum() {
        return builderLicenceNum;
    }

    public void setBuilderLicenceNum(String builderLicenceNum) {
        this.builderLicenceNum = builderLicenceNum;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public BigDecimal getTotalContractAmt() {
        return totalContractAmt;
    }

    public void setTotalContractAmt(BigDecimal totalContractAmt) {
        this.totalContractAmt = totalContractAmt;
    }

    public BigDecimal getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(BigDecimal buildingArea) {
        this.buildingArea = buildingArea;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        if(startDate !=""){
            try {
                this.startDate = (Date)new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
        }
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        if(completeDate !=""){
            try {
                this.completeDate = (Date)new SimpleDateFormat("yyyy-MM-dd").parse(completeDate);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
        }
    }

    
    public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getProjectSource() {
        return projectSource;
    }

    public void setProjectSource(String projectSource) {
        this.projectSource = projectSource;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Long getVirtualId() {
        return virtualId;
    }

    public void setVirtualId(Long virtualId) {
        this.virtualId = virtualId;
    }

    public Integer getIsSynchro() {
        return isSynchro;
    }

    public void setIsSynchro(Integer isSynchro) {
        this.isSynchro = isSynchro;
    }

    public Integer getIsWatermark() {
        return isWatermark;
    }

    public void setIsWatermark(Integer isWatermark) {
        this.isWatermark = isWatermark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ProjectMaster{" +
        "id=" + id +
        ", projectCode=" + projectCode +
        ", buildProjectCode=" + buildProjectCode +
        ", contractorOrgCode=" + contractorOrgCode +
        ", generalContractorCode=" + generalContractorCode +
        ", generalContractorName=" + generalContractorName +
        ", projectName=" + projectName +
        ", projectActivityType=" + projectActivityType +
        ", projectDescription=" + projectDescription +
        ", projectCategory=" + projectCategory +
        ", isMajorProject=" + isMajorProject +
        ", ownerName=" + ownerName +
        ", buildCorporationCode=" + buildCorporationCode +
        ", builderLicenceNum=" + builderLicenceNum +
        ", areaCode=" + areaCode +
        ", totalContractAmt=" + totalContractAmt +
        ", buildingArea=" + buildingArea +
        ", startDate=" + startDate +
        ", completeDate=" + completeDate +
        ", projectSource=" + projectSource +
        ", projectStatus=" + projectStatus +
        ", lng=" + lng +
        ", lat=" + lat +
        ", radius=" + radius +
        ", createDate=" + createDate +
        ", createUser=" + createUser +
        ", updateDate=" + updateDate +
        ", updateUser=" + updateUser +
        ", status=" + status +
        ", isDel=" + isDel +
        ", contractorType=" + contractorType +
        ", bankName=" + bankName +
        ", bankNumber=" + bankNumber +
        "}";
    }


    public boolean equalsAandB(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectMaster that = (ProjectMaster) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(projectCode, that.projectCode) &&
                Objects.equals(projectManCode, that.projectManCode) &&
                Objects.equals(buildProjectCode, that.buildProjectCode) &&
                Objects.equals(contractorOrgCode, that.contractorOrgCode) &&
                Objects.equals(generalContractorCode, that.generalContractorCode) &&
                Objects.equals(generalContractorName, that.generalContractorName) &&
                Objects.equals(projectName, that.projectName) &&
                Objects.equals(projectActivityType, that.projectActivityType) &&
                Objects.equals(projectDescription, that.projectDescription) &&
                Objects.equals(isWatermark, that.isWatermark) &&
                Objects.equals(projectCategory, that.projectCategory) &&
                Objects.equals(isMajorProject, that.isMajorProject) &&
                Objects.equals(ownerName, that.ownerName) &&
                Objects.equals(buildCorporationCode, that.buildCorporationCode) &&
                Objects.equals(builderLicenceNum, that.builderLicenceNum) &&
                Objects.equals(areaCode, that.areaCode) &&
                Objects.equals(totalContractAmt, that.totalContractAmt) &&
                Objects.equals(buildingArea, that.buildingArea) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(completeDate, that.completeDate) &&
                Objects.equals(projectSource, that.projectSource) &&
                Objects.equals(projectStatus, that.projectStatus) &&
                Objects.equals(projectStatusName, that.projectStatusName) &&
                Objects.equals(virtualId, that.virtualId) &&
                Objects.equals(lng, that.lng) &&
                Objects.equals(lat, that.lat) &&
                Objects.equals(radius, that.radius) &&
                Objects.equals(date, that.date) &&
                Objects.equals(tenderCode, that.tenderCode) &&
                Objects.equals(bidPrice, that.bidPrice) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(updateDate, that.updateDate) &&
                Objects.equals(updateUser, that.updateUser) &&
                Objects.equals(status, that.status) &&
                Objects.equals(isDel, that.isDel) &&
                Objects.equals(address, that.address) &&
                Objects.equals(deviceType, that.deviceType) &&
                Objects.equals(isSynchro, that.isSynchro) &&
                Objects.equals(buidContacts, that.buidContacts) &&
                Objects.equals(buildPhone, that.buildPhone) &&
                Objects.equals(contractorLeader, that.contractorLeader) &&
                Objects.equals(contractorLeaderPhone, that.contractorLeaderPhone) &&
                Objects.equals(projectDirector, that.projectDirector) &&
                Objects.equals(projectDirectorPhone, that.projectDirectorPhone) &&
                Objects.equals(isCivilization, that.isCivilization) &&
                Objects.equals(isHighGrade, that.isHighGrade) &&
                Objects.equals(isGovernment, that.isGovernment) &&
                Objects.equals(personLiable, that.personLiable) &&
                Objects.equals(contractNo, that.contractNo) &&
                Objects.equals(workTime, that.workTime) &&
                Objects.equals(contractorType, that.contractorType) &&
                Objects.equals(bankName, that.bankName) &&
                Objects.equals(bankNumber, that.bankNumber);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectMaster that = (ProjectMaster) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(projectCode, that.projectCode) &&
                Objects.equals(projectManCode, that.projectManCode) &&
                Objects.equals(buildProjectCode, that.buildProjectCode) &&
                Objects.equals(contractorOrgCode, that.contractorOrgCode) &&
                Objects.equals(generalContractorCode, that.generalContractorCode) &&
                Objects.equals(generalContractorName, that.generalContractorName) &&
                Objects.equals(projectName, that.projectName) &&
                Objects.equals(projectActivityType, that.projectActivityType) &&
                Objects.equals(projectDescription, that.projectDescription) &&
                Objects.equals(isWatermark, that.isWatermark) &&
                Objects.equals(projectCategory, that.projectCategory) &&
                Objects.equals(isMajorProject, that.isMajorProject) &&
                Objects.equals(ownerName, that.ownerName) &&
                Objects.equals(buildCorporationCode, that.buildCorporationCode) &&
                Objects.equals(builderLicenceNum, that.builderLicenceNum) &&
                Objects.equals(areaCode, that.areaCode) &&
                Objects.equals(totalContractAmt, that.totalContractAmt) &&
                Objects.equals(buildingArea, that.buildingArea) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(completeDate, that.completeDate) &&
                Objects.equals(projectSource, that.projectSource) &&
                Objects.equals(projectStatus, that.projectStatus) &&
                Objects.equals(projectStatusName, that.projectStatusName) &&
                Objects.equals(virtualId, that.virtualId) &&
                Objects.equals(lng, that.lng) &&
                Objects.equals(lat, that.lat) &&
                Objects.equals(radius, that.radius) &&
                Objects.equals(date, that.date) &&
                Objects.equals(tenderCode, that.tenderCode) &&
                Objects.equals(bidPrice, that.bidPrice) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(updateDate, that.updateDate) &&
                Objects.equals(updateUser, that.updateUser) &&
                Objects.equals(status, that.status) &&
                Objects.equals(isDel, that.isDel) &&
                Objects.equals(address, that.address) &&
                Objects.equals(deviceType, that.deviceType) &&
                Objects.equals(isSynchro, that.isSynchro) &&
                Objects.equals(buidContacts, that.buidContacts) &&
                Objects.equals(buildPhone, that.buildPhone) &&
                Objects.equals(contractorLeader, that.contractorLeader) &&
                Objects.equals(contractorLeaderPhone, that.contractorLeaderPhone) &&
                Objects.equals(projectDirector, that.projectDirector) &&
                Objects.equals(projectDirectorPhone, that.projectDirectorPhone) &&
                Objects.equals(isCivilization, that.isCivilization) &&
                Objects.equals(isHighGrade, that.isHighGrade) &&
                Objects.equals(isGovernment, that.isGovernment) &&
                Objects.equals(personLiable, that.personLiable) &&
                Objects.equals(contractNo, that.contractNo) &&
                Objects.equals(workTime, that.workTime) &&
                Objects.equals(contractorType, that.contractorType) &&
                Objects.equals(bankName, that.bankName) &&
                Objects.equals(bankNumber, that.bankNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, projectCode, projectManCode, buildProjectCode, contractorOrgCode, generalContractorCode, generalContractorName, projectName, projectActivityType, projectDescription, isWatermark, projectCategory, isMajorProject, ownerName, buildCorporationCode, builderLicenceNum, areaCode, totalContractAmt, buildingArea, startDate, completeDate, projectSource, projectStatus, projectStatusName, virtualId, lng, lat, radius, date, tenderCode, bidPrice, createDate, createUser, updateDate, updateUser, status, isDel, address, deviceType, isSynchro, buidContacts, buildPhone, contractorLeader, contractorLeaderPhone, projectDirector, projectDirectorPhone, isCivilization, isHighGrade, isGovernment, personLiable, contractNo, workTime, contractorType, bankName, bankNumber);
    }
}
