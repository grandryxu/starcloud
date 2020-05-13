package com.xywg.admin.modular.project.model;

import java.util.Date;

import lombok.Data;

@Data
public class PorjectWorkerDto {
	/**
	 * 项目编号
	 */
	private String projectCode;
	/**
	 * 企业编号
	 */
	private String organizationCode;

	/**
	 * 班组编号
	 */
	private Integer teamSysNo;

	/**
	 * 证件号
	 */
	private String idCardNumber;

	/**
	 * 工人名称
	 */
	private String workerName;

	/**
	 * 证号类型
	 */
	private Integer idCardType;
	/**
	 * 状态 1:待进场 2：进场 3：退场
	 */
	private Integer joinStatus;
	/**
	 * 当前工种 参见工种字典表中工种编号
	 */
	private String workTypeCode;
	/**
	 * 手机号码 工人当前手机号码
	 */
	private String cellPhone;
	/**
	 * 制卡时间
	 */
	private Date issueCardTime;
	/**
	 * 工人进场时间
	 */
	private Date entryTime;
	/**
	 * 工人退场时间
	 */
	private Date exitTime;
	/**
	 * 销卡时间
	 */
	private Date completeCardTime;
	/**
	 * 门禁卡号
	 */
	private String cardNumber;
	/**
	 * 门禁卡类型 卡的类型 1=正式卡, 3=临工卡(指的短期进入工地工作的卡，默认这类工人不会记入工资结算)
	 */
	private Integer cardType;
	/**
	 * 是否有劳动合同 0=无， 1=有 是否与劳务企业签有劳动合同
	 */
	private Integer hasContract;
	/**
	 * 工人劳动合同编号
	 */
	private String contractCode;
	/**
	 * 工人住宿类型 0=场外住宿, 1=场内住宿
	 */
	private Integer workerAccommodationType;
	/**
	 * 工人角色 1=Employe=职员 2=Worker=工人
	 */
	private Integer workerRole;
	/**
	 * 工资银行卡号
	 */
	private String payRollBankCardNumber;
	/**
	 * 发放工资银行名称
	 */
	private String payRollBankName;
	/**
	 * 有无购买工伤或意外伤害保险 0=No=无, 1=Yes=有
	 */
	private Integer hasBuyInsurance;
	/**
	 * 添加人
	 */
	private Date createDate;

	private Integer teamWorkerType;

	/**
	 * 企业名称
	 */
	private String companyName;

	/**
	 * 班组名称
	 */
	private String teamName;

	/**
	 * 工种名称
	 */
	private String workTypeName;

	/**
	 * 出生日期 身份证上获取的出生日期，格式：1990-04-08
	 */
	private Date birthday;

	/**
	 * 性别
	 */
	private Integer gender;
	/**
	 * 民族
	 */
	private String nation;

	/**
	 * 籍贯
	 */
	private String birthPlaceCode;
	/**
	 * 地址
	 */
	private String address;

	/**
	 * 有效期开始时间
	 */
	private Date startDate;

	/**
	 * 身份证有效结束时间
	 */
	private Date endDate;

}
