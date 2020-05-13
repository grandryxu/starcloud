package com.xywg.admin.modular.team.model;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppTeamMemberVo {

	/**
	 * 证件类型
	 */
	private Integer idCardType;
	/**
	 * 证件号
	 */
	private String idCardNumber;
	/**
	 * 工人名字
	 */
	private String workerName;
	/**
	 * 是否认证
	 */
	private Integer isAuth;
	/**
	 * 头像
	 */
	private String headImage;
	/**
	 * 考勤数
	 */
	private Integer recordCount;
	/**
	 * 是否有合同
	 */
	private String isSign;
	
	/**
	 * 工人类型（0组长1组员）
	 */
	private Integer workerType;

	/**
	 * 项目工种名称
	 */
	private String workKindName;
}
