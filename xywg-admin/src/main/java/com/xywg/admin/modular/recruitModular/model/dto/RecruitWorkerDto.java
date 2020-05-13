package com.xywg.admin.modular.recruitModular.model.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class RecruitWorkerDto {

	private Long id;
	private String recruitStation;
	private String phone;
	private int salaryType;
	private String salaryContent;
	private String salaryText;
	private String companyName;
	private Date updateDate;
	private String wealContent;
	private String workerName;
	private String claim;
	private String description;
	private String address;
	private int isResume;
	private int isEnshrine;
	private int browseCount;
	private int recordCount;
	private int ageStart;
	private int ageEnd;
	private String work;
	private String recruitNumber;
	private String workIdSets;
	private String memo;
	private String organizationTypeName;
	private String mobile;
	private int score;
	private String evaluate;
	private String recruitType;
	private String headImage;
	private Date telegramTime;
	private Date createDate;
	private List<RecruitTelegramDto> recruitTelegramDtoList;
	
}
