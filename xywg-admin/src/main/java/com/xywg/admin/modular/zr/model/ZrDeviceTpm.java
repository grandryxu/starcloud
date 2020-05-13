package com.xywg.admin.modular.zr.model;

import java.io.Serializable;

/**
 * 考勤设备信息
 */
@SuppressWarnings("serial")
public class ZrDeviceTpm implements Serializable{
	private Long id;
	private String name;  //设备名称
    private Long type;  //设备类型
    private String sn;  //设备号
    private String projectId;  // 工程ID 
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
    
}