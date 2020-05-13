package com.xywg.admin.modular.bad.model;

/**
 * <p>
 * 工人黑名单信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-06
 */
@SuppressWarnings("serial")
public class WorkerBlackListVO extends WorkerBlackList{
   
    private String projectName;
    
    private String ownerName;
    
    private String companyName;
    
    private String socialCreditNumber;
    
    private Integer validStatus;
    

    public Integer getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(Integer validStatus) {
		this.validStatus = validStatus;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSocialCreditNumber() {
		return socialCreditNumber;
	}

	public void setSocialCreditNumber(String socialCreditNumber) {
		this.socialCreditNumber = socialCreditNumber;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

}
