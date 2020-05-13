package com.xywg.admin.modular.team.model;

import java.util.Date;

import lombok.Data;

import org.springframework.format.annotation.DateTimeFormat;
/**
 * 班组长新增工人实体类
 * @author wangshibo
 *	2018年6月8日
 * 下午2:51:46
 */
@Data
public class AppTeamAddWorkerDto {

	/**
	 * 身份证姓名
	 */
	private String name;
	/**
	 * 身份证编号
	 */
	private String  idCardNumber;
	/**
	 * 身份证性别
	 */
	private String  gender;
	/**
	 * 身份证民族
	 */
	private String  nation;
	/**
	 * 身份证地址
	 */
	private String  address;
	/**
	 * 身份证生日
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date  birthday;
	/**
	 * 身份证图片地址
	 */
	private String idImage;
	/**
	 * 项目编号
	 */
	private String projectCode;
	/**
	 * 班组id
	 */
	private Integer teamId;
	/**
	 * 操作人证件类型
	 */
	private Integer teamLeaderIdCardType;
	/**
	 * 操作人证件编号
	 */
	private String teamLeaderIdCardNumber;
	/**
	 * 联系电话
	 */
	private String cellPhone;
	
	/**
	 * 工人工种编号
	 */
	private Integer workKind;
}
