package com.xywg.admin.modular.project.model;

import java.util.Date;

import lombok.Data;

/**
 * 进入考勤人员实体类
 * @author wangshibo
 *	2018年6月11日
 * 下午5:02:56
 */
@Data
public class AppAttenWorkerVo {

	/**
	 * 头像
	 */
	private String headImage;
	/**
	 * 姓名
	 */
	private String workerName;
	/**
	 * 是否认证
	 */
	private Integer isAuth;
	/**
	 * 证件类型
	 */
	private Integer idCardType;
	/**
	 * 证件号
	 */
	private String idCardNumber;
	/**
	 * 打卡时间
	 */
	private Date checkTime;
}
