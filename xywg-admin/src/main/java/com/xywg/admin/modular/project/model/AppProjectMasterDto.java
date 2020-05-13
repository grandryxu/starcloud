package com.xywg.admin.modular.project.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 项目基础信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-23
 */
@TableName("buss_project_master")
public class AppProjectMasterDto extends Model<AppProjectMasterDto> {
	private static Logger log = LoggerFactory.getLogger(AppProjectMasterDto.class);
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 项目编号
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * 项目名称
     */
    @TableField("project_name")
    private String projectName;
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
     * 项目行政区划,参见地区字典数据
     */
    @TableField("area_code")
    private String areaCode;

    private String ownerName;

    private String areaname;

    private String area;
    private String address;


    /**
     * 状态 参见项目状态字典表
     */
    @TableField("project_status")
    private String projectStatus;
    /**
     * 开工日期 精确到天，格式：yyyy-mm-dd
     */
    @TableField("start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    /**
     * 竣工日期 精确到天，格式：yyyy-mm-dd
     */
    @TableField("complete_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date completeDate;
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
     * 项目活动类型
     */
    @TableField("project_activity_type")
    private String projectActivityType;
    /**
     * 项目分类
     */
    @TableField("project_category")
    private String projectCategory;
    /**
     * 建设单位组织机构代码或统一社会信用代码
     */
    @TableField("build_corporation_code")
    private String buildCorporationCode;


    /**
     * 项目来源
     */
    @TableField("project_source")
    private String projectSource;
    
    /**
     * 参建类型
     */
    @TableField("project_type")
    private String projectType;

    private Integer deviceType;

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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


    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }


    public String getBuildCorporationCode() {
        return buildCorporationCode;
    }

    public void setBuildCorporationCode(String buildCorporationCode) {
        this.buildCorporationCode = buildCorporationCode;
    }


    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
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
    
    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
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
        ", projectCategory=" + projectCategory +
        ", buildCorporationCode=" + buildCorporationCode +
        ", areaCode=" + areaCode +
        ", startDate=" + startDate +
        ", completeDate=" + completeDate +
        ", projectSource=" + projectSource +
        ", projectStatus=" + projectStatus +
        ", projectType=" + projectType +
        "}";
    }
}
