package com.xywg.admin.modular.project.model;

import lombok.Data;

/**
 * 进场工人实体类
 * @author wangshibo
 *	2018年6月6日
 * 下午3:00:23
 */
@Data
public class AppProjectJoinPerson {

	/**
	 * 证件类型
	 */
	private Integer idCardType;
	/**
	 * 工种
	 */
	private String workKind;
	/**
	 * 证件号
	 */
	private String idCardNumber;
	/**
	 * 工人姓名
	 */
	private String workerName;
	/**
	 * 头像
	 */
	private String headImage;
	
	/**
	 * 班组名称
	 */
	private String teamName;


}
