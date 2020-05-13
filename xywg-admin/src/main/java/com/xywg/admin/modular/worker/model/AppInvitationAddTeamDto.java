package com.xywg.admin.modular.worker.model;

import lombok.Data;

/**
 * 邀请工人加入班组实体类
 * @author wangshibo
 *	2018年6月5日
 * 下午2:08:11
 */
@Data
public class AppInvitationAddTeamDto {
	/**
	 * 工人证件编号
	 */
	private String  idCardNumber;
	
	/**
	 * 工人证件类型
	 */
	private Integer idCardType;
	/**
	 * 项目编号
	 */
	private String  projectCode;
	
	/**
	 * 班组id
	 */
	private Integer teamId;
	/**
	 * 操作人证件编号
	 */
	private String  teamLeaderIdCardNumber;
	
	/**
	 * 操作人证件类型
	 */
	private Integer teamLeaderIdCardType;
	/**
	 * 班组长用户id
	 */
	private Integer sendId;
	/**
	 * 工人用户id
	 */
	private Integer receiveId;
	
}
