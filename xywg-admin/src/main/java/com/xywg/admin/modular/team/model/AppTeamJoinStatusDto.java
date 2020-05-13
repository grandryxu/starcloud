package com.xywg.admin.modular.team.model;

public class AppTeamJoinStatusDto {
	private Integer teamId;
	private String projectCode;
	private Integer joinStatus;
	private String evalute;
	/**
	 * 操作人证件类型
	 */
	private Integer teamLeaderIdCardType;

	/**
	 * 操作人证件号
	 */
	private String teamLeaderIdCardNumber;

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
