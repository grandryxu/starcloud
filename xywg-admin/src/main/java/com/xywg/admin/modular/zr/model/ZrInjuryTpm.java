package com.xywg.admin.modular.zr.model;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

/**
 * 考勤设备信息
 */
@ToString
@SuppressWarnings("serial")
public class ZrInjuryTpm implements Serializable{
	private String occurrenceTime;  //发生时间
    private String place;  //发生地点
    private String memo;  //备注
    private String projectId;  //项目ID
    private String projectMemberId;  // 人员入场ID
	public String getOccurrenceTime() {
		return occurrenceTime;
	}
	public void setOccurrenceTime(String occurrenceTime) {
		this.occurrenceTime = occurrenceTime;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectMemberId() {
		return projectMemberId;
	}
	public void setProjectMemberId(String projectMemberId) {
		this.projectMemberId = projectMemberId;
	}
    
}