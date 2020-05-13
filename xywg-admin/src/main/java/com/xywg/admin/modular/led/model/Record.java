package com.xywg.admin.modular.led.model;

import lombok.Data;

@Data
public class Record {
	/**
	 * 人数
	 */
	private Integer count;
	/**
	 * 工种
	 */
	private Integer workKind;
	/**
	 * 工种名称
	 */
	private String workKindName;
	
}
