package com.xywg.admin.modular.worker.model;

/**
 * <p>
 * 工人实名基础信息
 * </p>
 *
 * @author shily
 * @since 2018-06-20
 */
@SuppressWarnings("serial")
public class WorkerMasterVO extends WorkerMaster {
    
    private String idCardNumber;
    
    private Integer idCardType;
    
    private String workerName;
    
    private String teamSysNo;
    
    private String teamName;
    
    private String companyName;
    
    private String projectCode;
    
    private String projectName;

    private String ownerName;
    
    private String contractorName;
    
    private String cellPhone;
    
    private String projectCount;
    
    private String badRecordsCount;
    
    private String goodRecordsCount;
    
    private String blackListCount;
    
    private String payAmount;
    
    private String actualAmount;
    
    private Boolean isNeedCheck;
    
    
    
    public Boolean getIsNeedCheck() {
		return isNeedCheck;
	}

	public void setIsNeedCheck(Boolean isNeedCheck) {
		this.isNeedCheck = isNeedCheck;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getCellPhone() {
		return cellPhone;
	}


	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}


	public String getProjectCount() {
		return projectCount;
	}


	public void setProjectCount(String projectCount) {
		this.projectCount = projectCount;
	}


	public String getBadRecordsCount() {
		return badRecordsCount;
	}


	public void setBadRecordsCount(String badRecordsCount) {
		this.badRecordsCount = badRecordsCount;
	}


	public String getGoodRecordsCount() {
		return goodRecordsCount;
	}


	public void setGoodRecordsCount(String goodRecordsCount) {
		this.goodRecordsCount = goodRecordsCount;
	}


	public String getBlackListCount() {
		return blackListCount;
	}


	public void setBlackListCount(String blackListCount) {
		this.blackListCount = blackListCount;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public String getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}


	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}


	public Integer getIdCardType() {
		return idCardType;
	}


	public void setIdCardType(Integer idCardType) {
		this.idCardType = idCardType;
	}


	public String getOwnerName() {
		return ownerName;
	}


	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getWorkerName() {
		return workerName;
	}


	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}


	public String getTeamSysNo() {
		return teamSysNo;
	}


	public void setTeamSysNo(String teamSysNo) {
		this.teamSysNo = teamSysNo;
	}


	public String getTeamName() {
		return teamName;
	}


	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}


	public String getProjectCode() {
		return projectCode;
	}


	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
    
}
