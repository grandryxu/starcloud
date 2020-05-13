package com.xywg.admin.modular.device.model.dto;

import java.util.List;
import java.util.Map;

public class ParamDto {
	
	private List<Map<String,String>> workerList;
	private String projectCode;
	private String endDate;
	
	public List<Map<String, String>> getWorkerList() {
		return workerList;
	}
	public void setWorkerList(List<Map<String, String>> workerList) {
		this.workerList = workerList;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
