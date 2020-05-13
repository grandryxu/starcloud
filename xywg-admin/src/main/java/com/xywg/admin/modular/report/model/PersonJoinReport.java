package com.xywg.admin.modular.report.model;

import lombok.Data;

/*
 *人员进退场统计实体类 
 */
@Data
public class PersonJoinReport {

	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 承包单位名称
	 */
	private String companyName;
	/**
	 * 项目活动类型
	 */
	private String projectType;
	/**
	 * 进场人数
	 */
	private String joinCount;
	/**
	 * 退场人数
	 */
	private String outCount;
	/**
	 * 累计进场人数
	 */
	private String totalCount;
	/**
	 * 在场人数
	 */
	private String nowCount;
}
