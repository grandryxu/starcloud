package com.xywg.admin.modular.team.model;

public class AppGetTeamInfoVo {
	/**
	 * 班组长姓名
	 */
	private String teamLeaderName;
	/**
	 * 班组名称
	 */
	private String teamName;
	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 头像
	 */
	private String headImage;
	/**
	 * 是否加入班组
	 */
	private Integer joinStatus;
	/**
	 * 班组id
	 */
	private Integer id;
	/**
	 * 班组长用户id
	 */
	private Integer teamLeaderId;
	
	
	public Integer getTeamLeaderId() {
		return teamLeaderId;
	}

	public void setTeamLeaderId(Integer teamLeaderId) {
		this.teamLeaderId = teamLeaderId;
	}

	public String getTeamLeaderName() {
		return teamLeaderName;
	}

	public void setTeamLeaderName(String teamLeaderName) {
		this.teamLeaderName = teamLeaderName;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public Integer getJoinStatus() {
		return joinStatus;
	}

	public void setJoinStatus(Integer joinStatus) {
		this.joinStatus = joinStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
