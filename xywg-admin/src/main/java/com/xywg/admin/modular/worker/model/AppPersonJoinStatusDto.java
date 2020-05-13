package com.xywg.admin.modular.worker.model;

import java.util.List;

public class AppPersonJoinStatusDto {
	
	private String projectCode;
	private Integer joinStatus;
	private String evalute;
	private Integer teamLeaderIdCardType;
	private String teamLeaderIdCardNumber;
	private Integer teamId;

	private List<AppWorkerJoinDto> workerList;
	
	
	
	public List<AppWorkerJoinDto> getWorkerList() {
		return workerList;
	}

	public void setWorkerList(List<AppWorkerJoinDto> workerList) {
		this.workerList = workerList;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public Integer getJoinStatus() {
		return joinStatus;
	}

	public void setJoinStatus(Integer joinStatus) {
		this.joinStatus = joinStatus;
	}

	public String getEvalute() {
		return evalute;
	}

	public void setEvalute(String evalute) {
		this.evalute = evalute;
	}

	public Integer getTeamLeaderIdCardType() {
		return teamLeaderIdCardType;
	}

	public void setTeamLeaderIdCardType(Integer teamLeaderIdCardType) {
		this.teamLeaderIdCardType = teamLeaderIdCardType;
	}

	public String getTeamLeaderIdCardNumber() {
		return teamLeaderIdCardNumber;
	}

	public void setTeamLeaderIdCardNumber(String teamLeaderIdCardNumber) {
		this.teamLeaderIdCardNumber = teamLeaderIdCardNumber;
	}

}
