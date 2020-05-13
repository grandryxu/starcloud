package com.xywg.admin.modular.bad.model;

/**
 * <p>
 * 工人不良行为记录信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-05-30
 */
@SuppressWarnings("serial")
public class WorkerBadRecordsVO extends WorkerBadRecords {

    private String teamId;
    
    private String teamName;
    
    private String teamLeader;
    
    private String contact;
    
    private String teamLeaderIdNumber;
    
    private String projectName;
    
    private String ownerName;
    
    private String workerName;
    
    private String headImage;
    
    private String badRecordType;
    
    private String companyName;
    
    private String socialCreditNumber;
    
    
    public String getSocialCreditNumber() {
		return socialCreditNumber;
	}

	public void setSocialCreditNumber(String socialCreditNumber) {
		this.socialCreditNumber = socialCreditNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBadRecordType() {
		return badRecordType;
	}

	public void setBadRecordType(String badRecordType) {
		this.badRecordType = badRecordType;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamLeader() {
		return teamLeader;
	}

	public void setTeamLeader(String teamLeader) {
		this.teamLeader = teamLeader;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getTeamLeaderIdNumber() {
		return teamLeaderIdNumber;
	}

	public void setTeamLeaderIdNumber(String teamLeaderIdNumber) {
		this.teamLeaderIdNumber = teamLeaderIdNumber;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
