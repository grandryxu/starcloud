package com.xywg.admin.modular.recruitModular.model;

import java.util.List;

import lombok.Data;

@Data
public class RecruitModularParams {

	private Long id;
	private String keyword;
	private int provincesId;
	private int citiesId;
	private int areasId;
	private String workId;
	private int projectType;
	private String recruitType;
	private String idCardType;
	private String idCardNumber;
	private int pageNo;
	private int pageSize;
	private List<String> workIdList;
	private String mobile;
	private int score;
	private int workYears;
	private String evaluate;
	private String educationBackground;
	private String theme;
	private String experience;
	private long evaluateId;
	private int type;
	private String appealReason;
	private String createUser;
	private String account;
	private String projectCode;
	private String imei;
	private String organizationCode;
	
}
