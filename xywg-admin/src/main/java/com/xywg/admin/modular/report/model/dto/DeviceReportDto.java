package com.xywg.admin.modular.report.model.dto;

import com.xywg.admin.modular.report.model.DeviceReport;

public class DeviceReportDto extends DeviceReport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1055660143566533389L;
	
	private String type;
    private String deal;
    private String time;

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDeal() {
		return deal;
	}

	public void setDeal(String deal) {
		this.deal = deal;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
