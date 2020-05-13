package com.xywg.admin.modular.bad.model;

/**
 * <p>
 * 工人奖励记录信息
 * </p>
 *
 * @author wangcw123
 * @since 2018-06-05
 */
@SuppressWarnings("serial")
public class WorkerGoodRecordsVO extends WorkerGoodRecords {

    private String workerName;
    
    private String projectName;
    
    private String companyName;
    
    private String ownerName;
    
    private String teamName;
    
    private String teamLeader;


    public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
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
