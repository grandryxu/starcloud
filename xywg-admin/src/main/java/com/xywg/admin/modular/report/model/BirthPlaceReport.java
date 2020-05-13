package com.xywg.admin.modular.report.model;

import lombok.Data;

/**
 * 籍贯统计实体类
 * @author wangshibo
 *	2018年6月20日
 * 上午11:23:04
 */
@Data
public class BirthPlaceReport {
	/**
	 * 籍贯
	 */
	private String birthPlace;
	/**
	 * 人数
	 */
	private Integer count;
}
