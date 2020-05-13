package com.xywg.admin.modular.team.model;

import lombok.Data;

/**
 * app班组信息实体类
 * @author wangshibo
 *	2018年6月7日
 * 上午11:03:08
 */
@Data
public class AppTeamInfoVo {
	/**
	 * 班组名称
	 */
	private String teamName;
	/**
	 * 班组人数
	 * 
	 */
	private Integer count;
	/**
	 * 班组id
	 */
	private Integer teamId;
	
}
