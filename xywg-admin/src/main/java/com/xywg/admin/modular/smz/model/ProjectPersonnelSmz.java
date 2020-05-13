package com.xywg.admin.modular.smz.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 
 * @description 实名制人员、项目关系类
 * 
 * @author chupp
 *
 * @date 2018年5月2日
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class ProjectPersonnelSmz implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2404420548112854610L;
	
	private String id;
	private String classNo;
	private String createDate;
	private String createUser;
	private String joinStatus;
	private String lastUpdateDate;
	private String lastUpdateUser;
	private String projectId;
	private String recordStatus;
	private String userId;
	private String version;
	private String workKind;
	private Integer type;
	private String comId;
	private String evalution;
	private String joinInDate;
	private String joinOutDate;
	private String classid;
	private String idcardNumber;
	private String contractPeriodtype;
	private String startDate;
	private String endDate;
	private String winBinddingFile;
	private String hzm;
	private String name;  //人员姓名
	private String homeAddress;  //人员地址
	private String mobile; //联系电话
	private Integer gender;  //性别
	private String nation;  //民族
	private String kindCode;  //工种编码
	private String birthday;  //生日
	private String birthdayPlaceCode;  //出生地
	private Long uid; //实名制人员id
	
}
