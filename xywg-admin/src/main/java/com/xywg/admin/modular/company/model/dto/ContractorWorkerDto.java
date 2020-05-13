package com.xywg.admin.modular.company.model.dto;

import java.io.Serializable;

public class ContractorWorkerDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1862417709451123541L;
	
	private String ageRange;
	private String count;
	private String placeCode;
	private String projectName;
	private String joinIn;
	private String joinOut;
	
	public String getAgeRange() {
		return ageRange;
	}
	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getPlaceCode() {
		return placeCode;
	}
	public void setPlaceCode(String placeCode) {
		this.placeCode = placeCode;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getJoinIn() {
		return joinIn;
	}
	public void setJoinIn(String joinIn) {
		this.joinIn = joinIn;
	}
	public String getJoinOut() {
		return joinOut;
	}
	public void setJoinOut(String joinOut) {
		this.joinOut = joinOut;
	}
	
}
