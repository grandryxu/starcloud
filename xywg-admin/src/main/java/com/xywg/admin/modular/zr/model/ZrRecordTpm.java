package com.xywg.admin.modular.zr.model;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

/**
 * 考勤设备信息
 */
@ToString
@SuppressWarnings("serial")
public class ZrRecordTpm implements Serializable{
	private String projectMemberId;  //人员进场ID
    private String time;  //考勤时间
    private Long type;  //类型
    private String deviceId;  // 设备ID
    private String address;  // 考勤地址
    private Long pointX;  //X坐标
    private Long pointY;  //Y坐标
	public String getProjectMemberId() {
		return projectMemberId;
	}
	public void setProjectMemberId(String projectMemberId) {
		this.projectMemberId = projectMemberId;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getPointX() {
		return pointX;
	}
	public void setPointX(Long pointX) {
		this.pointX = pointX;
	}
	public Long getPointY() {
		return pointY;
	}
	public void setPointY(Long pointY) {
		this.pointY = pointY;
	}
    
}