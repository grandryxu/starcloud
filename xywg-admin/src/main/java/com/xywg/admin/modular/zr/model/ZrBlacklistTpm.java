package com.xywg.admin.modular.zr.model;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

/**
 * 黑名单信息
 */
@ToString
@SuppressWarnings("serial")
public class ZrBlacklistTpm implements Serializable{
	private Long type;  // 1.有效，2.过期 
    private String startTime;  //开始时间
    private String endTime;  //过期时间
    private String projectMemberId;  // 劳务平台人员ID
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getProjectMemberId() {
		return projectMemberId;
	}
	public void setProjectMemberId(String projectMemberId) {
		this.projectMemberId = projectMemberId;
	}
}