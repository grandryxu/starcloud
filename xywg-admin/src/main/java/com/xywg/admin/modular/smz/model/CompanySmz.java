package com.xywg.admin.modular.smz.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * 
 * @description company类
 * 
 * @author chupp
 *
 * @date 2018年4月27日
 *
 */
@Data
@JsonInclude(Include.NON_NULL)
public class CompanySmz implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3856227610147460977L;
	
	private String companyCode;
	private String companyType;
	private String lastUpdateDate;
	private String companyName;
	private String userName;
	private String companyLevel;
	private String version;
	private String registerNo;
	private String recordStatus;
	private String companyPhone;
	private String companyAddress;
	private String district;
	private String socialId;
	private String auditStatus;
	private String lastUpdateUser;
	private String createUser;
	private String id;
	private String companyContact;
	private String contactPhone;
	private String createDate;
	
}
