package com.xywg.admin.modular.report.model.dto;

import com.xywg.admin.core.excel.annotation.ExcelField;
import lombok.Data;

/*
 *人员进退场统计导出实体类
 */
@Data
public class PersonJoinReportDto {

	/**
	 * 项目名称
	 */
	@ExcelField(title = "项目名称", order = 1)
	private String projectName;
	/**
	 * 承包单位名称
	 */
	@ExcelField(title = "公司名称", order = 2)
	private String companyName;
	/**
	 * 项目活动类型
	 */
	@ExcelField(title = "项目活动类型", order = 3)
	private String projectType;
	/**
	 * 进场人数
	 */
	@ExcelField(title = "进场工人数", order = 4)
	private String joinCount;
	/**
	 * 退场人数
	 */
	@ExcelField(title = "退场工人数", order = 5)
	private String outCount;
	/**
	 * 累计进场人数
	 */
	@ExcelField(title = "累计进场工人数", order = 6)
	private String totalCount;
	/**
	 * 在场人数
	 */
	@ExcelField(title = "在场人数", order = 7)
	private String nowCount;
}
