package com.xywg.admin.modular.recruitModular.model.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class RecruitResumeDto {

	private Long id;
	private String headImage;
	private String workerName;
	private String idCardType;
	private String idCardNumber;
	private int gender;
	private Date birthday;
	private String cellPhone;
	private String workId;
	private String workName;
	private String educationBackgroundName;
	private String educationBackground;
	private int workYears;
	private String experience;
	private List<CertificationDto> certifications;
}
