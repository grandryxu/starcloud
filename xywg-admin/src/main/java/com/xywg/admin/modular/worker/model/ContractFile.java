package com.xywg.admin.modular.worker.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 合同附件
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-08
 */
@TableName("buss_contract_file")
public class ContractFile extends Model<ContractFile> {

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
     * 班组编号
     */
    @TableField("team_sys_no")
    private String teamSysNo;

    /**
     * 工人劳动合同编号
     */
    @TableField("contract_code")
    private String contractCode;
    /**
     * 证件类型 参见人员证件类型字典表
     */
    @TableField("id_card_type")
    private String idCardType;
    /**
     * 证件编号
     */
    @TableField("id_card_number")
    private String idCardNumber;
    /**
     * 附件名称
     */
    @TableField("file_name")
    private String fileName;
    /**
     * 附件文件地址
     */
    @TableField("file_path")
    private String filePath;
    
    @TableField("face_path")
    private String facePath;
    /**
     * 是否删除 1：是 0:否
     */
    @TableField("is_del")
    private Integer isDel;

    /**
     * 标签
     */
    @TableField("tag")
    private Integer tag;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;

    /**
     * 项目名称
     */
    @TableField(exist = false)
    private String projectName;

    /**
     * 班组老板
     */
    @TableField(exist = false)
    private String teamLeader;

    /**
     * 合同标签
     */
    @TableField(exist = false)
    private String tagName;

    /**
     * 工人名称
     */
    @TableField(exist = false)
    private String workerName;

    /**
     * 项目工人id
     */
    @TableField("project_worker_id")
    public Long projectWorkerId;

    /**
     * 审核状态
     */
    @TableField("time")
    public Date time;

    /**
     * 审核状态
     */
    @TableField("status")
    public Integer status;

    public Long getProjectWorkerId() {
        return projectWorkerId;
    }

    public void setProjectWorkerId(Long projectWorkerId) {
        this.projectWorkerId = projectWorkerId;
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

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getFacePath() {
		return facePath;
	}

	public void setFacePath(String facePath) {
		this.facePath = facePath;
	}

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public String getTeamSysNo() {
        return teamSysNo;
    }

    public void setTeamSysNo(String teamSysNo) {
        this.teamSysNo = teamSysNo;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    
    @Override
    public String toString() {
        return "ContractFile{" +
        "id=" + id +
        ", projectCode=" + projectCode +
        ", organizationCode=" + organizationCode +
        ", contractCode=" + contractCode +
        ", idCardType=" + idCardType +
        ", idCardNumber=" + idCardNumber +
        ", fileName=" + fileName +
        ", filePath=" + filePath +
        ", facePath=" + facePath +
        ", isDel=" + isDel +
        "}";
    }
}
