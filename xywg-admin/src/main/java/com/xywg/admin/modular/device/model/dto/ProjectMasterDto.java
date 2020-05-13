package com.xywg.admin.modular.device.model.dto;

import java.io.Serializable;

public class ProjectMasterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5044480570919191217L;

	private String id;
	private String projectName;
	private String num;
	private String isDel;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsDel() {
		return isDel;
	}
	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
}
