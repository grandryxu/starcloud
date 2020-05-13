package com.xywg.admin.modular.team.model;

import lombok.Data;
/**
 * app班组长条件查询工人实体类
 * @author wangshibo
 *	2018年6月5日
 * 下午4:37:37
 */
@Data
public class AppWorkerByKeyVo {
	/**
	 * 证件号
	 */
	private String idCardNumber;
	
	/**
	 * 证件类型
	 */
	private Integer idCardType;
	/**
	 * 工人姓名
	 */
	private String workerName;
	
	/**
	 * 是否加入班组
	 */
	private Integer entryFlag;
	
	/**
	 * 头像
	 */
	private String headImage;
	/**
	 * 用户id
	 */
	private Integer id;
	
	/**
	 * 是否黑名单
	 */
	private Integer isBlack;
}
